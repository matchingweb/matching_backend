package com.matching.backend;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class ApiIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void signupLoginAndGetMe() throws Exception {
        String email = uniqueEmail();
        signup(email);

        String token = login(email);

        mockMvc.perform(get("/api/users/me")
                        .header("Authorization", bearer(token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.email").value(email))
                .andExpect(jsonPath("$.data.nickname").value("테스트유저"));
    }

    @Test
    void duplicateSignupReturnsConflict() throws Exception {
        String email = uniqueEmail();
        signup(email);

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(signupPayload(email))))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error.code").value("USER_409_1"));
    }

    @Test
    void authenticatedEndpointRequiresToken() throws Exception {
        mockMvc.perform(get("/api/users/me"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error.code").value("COMMON_401"));
    }

    @Test
    void onlyTeamOwnerCanUpdateTeam() throws Exception {
        String ownerToken = signupAndLogin(uniqueEmail());
        String otherToken = signupAndLogin(uniqueEmail());
        Long teamId = createTeam(ownerToken, "권한테스트팀");

        Map<String, Object> updatePayload = new LinkedHashMap<>();
        updatePayload.put("name", "다른 사람이 바꾼 팀명");

        mockMvc.perform(patch("/api/teams/{teamId}", teamId)
                        .header("Authorization", bearer(otherToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(updatePayload)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error.code").value("TEAM_403_1"));

        mockMvc.perform(patch("/api/teams/{teamId}", teamId)
                        .header("Authorization", bearer(ownerToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(updatePayload)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("다른 사람이 바꾼 팀명"));
    }

    @Test
    void postFilterSearchReturnsMatchingPosts() throws Exception {
        String token = signupAndLogin(uniqueEmail());
        Long teamId = createTeam(token, "필터테스트팀");
        String uniqueRegion = "테스트지역-" + UUID.randomUUID();

        createPost(token, teamId, uniqueRegion, "필터에 걸리는 글");

        mockMvc.perform(get("/api/posts")
                        .header("Authorization", bearer(token))
                        .param("boardType", "MERCENARY")
                        .param("roleType", "RECRUITING")
                        .param("status", "OPEN")
                        .param("region", uniqueRegion))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.content", hasSize(1)))
                .andExpect(jsonPath("$.data.totalElements").value(1))
                .andExpect(jsonPath("$.data.content[0].title").value("필터에 걸리는 글"))
                .andExpect(jsonPath("$.data.content[0].boardType").value("MERCENARY"))
                .andExpect(jsonPath("$.data.content[0].roleType").value("RECRUITING"))
                .andExpect(jsonPath("$.data.content[0].status").value("OPEN"));
    }

    @Test
    void onlyAuthorCanClosePost() throws Exception {
        String authorToken = signupAndLogin(uniqueEmail());
        String otherToken = signupAndLogin(uniqueEmail());
        Long teamId = createTeam(authorToken, "마감권한팀");
        Long postId = createPost(authorToken, teamId, "마감권한지역", "마감 권한 글");

        mockMvc.perform(patch("/api/posts/{postId}/close", postId)
                        .header("Authorization", bearer(otherToken)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error.code").value("POST_403_1"));

        mockMvc.perform(patch("/api/posts/{postId}/close", postId)
                        .header("Authorization", bearer(authorToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.status").value("CLOSED"));
    }

    private String signupAndLogin(String email) throws Exception {
        signup(email);
        return login(email);
    }

    private void signup(String email) throws Exception {
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(signupPayload(email))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.email").value(email));
    }

    private String login(String email) throws Exception {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("email", email);
        payload.put("password", "password123");

        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(payload)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.tokenType").value("Bearer"))
                .andReturn();

        JsonNode root = objectMapper.readTree(result.getResponse().getContentAsString());
        return root.path("data").path("accessToken").asText();
    }

    private Long createTeam(String token, String name) throws Exception {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("name", name);
        payload.put("logoUrl", "https://example.com/logo.png");
        payload.put("homeRegion", "대전광역시 유성구");
        payload.put("homeStadium", "송강동 풋살장");
        payload.put("ageGroup", "20대 후반 ~ 30대");
        payload.put("level", "MIDDLE");
        payload.put("fee", 30000);

        MvcResult result = mockMvc.perform(post("/api/teams")
                        .header("Authorization", bearer(token))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(payload)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andReturn();

        JsonNode root = objectMapper.readTree(result.getResponse().getContentAsString());
        return root.path("data").path("id").asLong();
    }

    private Long createPost(String token, Long teamId, String region, String title) throws Exception {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("teamId", teamId);
        payload.put("boardType", "MERCENARY");
        payload.put("roleType", "RECRUITING");
        payload.put("title", title);
        payload.put("matchDate", "2026-09-20T18:00:00");
        payload.put("location", region + " 풋살장");
        payload.put("content", "테스트 게시글입니다.");

        MvcResult result = mockMvc.perform(post("/api/posts")
                        .header("Authorization", bearer(token))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(payload)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andReturn();

        JsonNode root = objectMapper.readTree(result.getResponse().getContentAsString());
        return root.path("data").path("id").asLong();
    }

    private Map<String, Object> signupPayload(String email) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("email", email);
        payload.put("password", "password123");
        payload.put("nickname", "테스트유저");
        payload.put("age", 28);
        payload.put("gender", "MALE");
        payload.put("region", "대전광역시 유성구");
        payload.put("position", "CM");
        payload.put("skillLevel", "중");
        payload.put("career", "풋살 5년");
        payload.put("videoUrl", "https://youtube.com/example");
        return payload;
    }

    private String uniqueEmail() {
        return "test-" + UUID.randomUUID() + "@example.com";
    }

    private String bearer(String token) {
        return "Bearer " + token;
    }

    private String toJson(Object value) throws Exception {
        return objectMapper.writeValueAsString(value);
    }
}
