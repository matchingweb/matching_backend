# Matching Backend

아마추어 축구/풋살 매칭 플랫폼 백엔드입니다.

## 개발 순서

1. Spring Boot 프로젝트 생성 - 완료
2. 공통 응답/예외 구조 생성 - 완료
3. User 엔티티 + 회원가입 - 완료
4. Spring Security + JWT 로그인
5. `GET /api/users/me`로 인증 확인
6. Team 엔티티/API
7. Post 엔티티/API
8. 게시글 필터 검색
9. 권한 처리: 내 글만 수정/마감
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
