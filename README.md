# Matching Backend

아마추어 축구/풋살 매칭 플랫폼 백엔드입니다.

## 개발 순서

1. Spring Boot 프로젝트 생성 - 완료
2. 공통 응답/예외 구조 생성 - 완료
3. User 엔티티 + 회원가입 - 완료
4. Spring Security + JWT 로그인 - 완료
5. `GET /api/users/me`로 인증 확인 - 완료
6. Team 엔티티/API - 완료
7. Post 엔티티/API - 완료
8. 게시글 필터 검색 - 완료
9. 권한 처리: 내 글만 수정/마감 - 완료
10. Swagger 또는 API 문서 추가

## 실행 준비

- Java 17 이상
- Maven 3.9 이상

```bash
mvn spring-boot:run
```

## 현재 API

### 회원가입

```http
POST /api/auth/signup
Content-Type: application/json
```

```json
{
  "email": "player@example.com",
  "password": "password123",
  "nickname": "대전미드필더",
  "age": 28,
  "gender": "MALE",
  "region": "대전광역시 유성구",
  "position": "CM",
  "skillLevel": "중",
  "career": "풋살 5년",
  "videoUrl": "https://youtube.com/example"
}
```

### 로그인

```http
POST /api/auth/login
Content-Type: application/json
```

```json
{
  "email": "player@example.com",
  "password": "password123"
}
```

인증이 필요한 API는 아래 헤더를 포함해서 요청합니다.

```http
Authorization: Bearer {accessToken}
```

### 내 정보 조회

```http
GET /api/users/me
Authorization: Bearer {accessToken}
```

### 팀 생성

```http
POST /api/teams
Authorization: Bearer {accessToken}
Content-Type: application/json
```

```json
{
  "name": "대전FC",
  "logoUrl": "https://example.com/logo.png",
  "homeRegion": "대전광역시 유성구",
  "homeStadium": "송강동 풋살장",
  "ageGroup": "20대 후반 ~ 30대",
  "level": "MIDDLE",
  "fee": 30000
}
```

### 팀 상세 조회

```http
GET /api/teams/{teamId}
Authorization: Bearer {accessToken}
```

### 팀 수정

```http
PATCH /api/teams/{teamId}
Authorization: Bearer {accessToken}
Content-Type: application/json
```

### 게시글 생성

```http
POST /api/posts
Authorization: Bearer {accessToken}
Content-Type: application/json
```

```json
{
  "teamId": 1,
  "boardType": "MERCENARY",
  "roleType": "RECRUITING",
  "title": "이번 주말 풋살 용병 2명 구합니다",
  "matchDate": "2026-08-22T18:00:00",
  "location": "대전광역시 유성구 송강동 풋살장",
  "content": "중급 정도로 같이 뛰실 분을 찾습니다."
}
```

### 게시글 목록/상세 조회

```http
GET /api/posts
Authorization: Bearer {accessToken}
```

필터 조회 예시:

```http
GET /api/posts?boardType=MERCENARY&roleType=RECRUITING&status=OPEN&region=대전
Authorization: Bearer {accessToken}
```

경기일 기준 필터:

```http
GET /api/posts?matchDateFrom=2026-08-22T00:00:00&matchDateTo=2026-08-23T23:59:59
Authorization: Bearer {accessToken}
```

```http
GET /api/posts/{postId}
Authorization: Bearer {accessToken}
```

### 내가 쓴 게시글 조회

```http
GET /api/posts/me
Authorization: Bearer {accessToken}
```

### 게시글 수정/마감

```http
PATCH /api/posts/{postId}
Authorization: Bearer {accessToken}
Content-Type: application/json
```

```http
PATCH /api/posts/{postId}/close
Authorization: Bearer {accessToken}
```
