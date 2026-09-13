# 배포 준비

## Docker 이미지 빌드

```powershell
docker build -t matching-backend:local .
```

## Docker 실행

PostgreSQL이 실행 중이라는 전제로 아래처럼 실행합니다.

```powershell
docker run --rm -p 8080:8080 `
  -e SPRING_PROFILES_ACTIVE=prod `
  -e DB_URL=jdbc:postgresql://host.docker.internal:5432/matching `
  -e DB_USERNAME=matching `
  -e DB_PASSWORD=matching `
  -e JWT_SECRET=replace-with-a-long-random-secret-key `
  -e CORS_ALLOWED_ORIGINS=http://localhost:3000 `
  matching-backend:local
```

## 필수 환경변수

| 이름 | 설명 | 예시 |
|---|---|---|
| `SPRING_PROFILES_ACTIVE` | 실행 프로필 | `prod` |
| `DB_URL` | PostgreSQL JDBC URL | `jdbc:postgresql://host:5432/matching` |
| `DB_USERNAME` | DB 사용자명 | `matching` |
| `DB_PASSWORD` | DB 비밀번호 | `matching` |
| `JWT_SECRET` | JWT 서명 secret. 최소 32바이트 | `replace-with-a-long-random-secret-key` |
| `CORS_ALLOWED_ORIGINS` | 허용할 프론트엔드 origin 목록 | `https://example.com` |

## Health Check

```http
GET /actuator/health
```

배포 플랫폼의 health check path는 `/actuator/health`로 설정합니다.

## 배포 전 체크리스트

- Java/Maven 빌드 성공 확인
- `mvn test` 성공 확인
- PostgreSQL 접속 정보 확인
- Flyway 마이그레이션 적용 확인
- `JWT_SECRET` 운영용 값으로 교체
- `CORS_ALLOWED_ORIGINS` 운영 프론트엔드 도메인으로 제한
- Swagger 공개 여부 결정
- `/actuator/health` 정상 응답 확인
- 로그에서 민감 정보가 출력되지 않는지 확인
