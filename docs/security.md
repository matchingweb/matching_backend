# 보안 설정

## JWT

JWT secret은 환경변수로 주입합니다.

```powershell
$env:JWT_SECRET = "replace-with-a-long-random-secret-key"
```

`JWT_SECRET`은 최소 32바이트 이상이어야 합니다. 로컬 기본값은 개발 편의를 위한 값이며 운영 환경에서 사용하지 않습니다.

Access Token 만료 시간은 밀리초 단위로 설정합니다.

```powershell
$env:JWT_ACCESS_TOKEN_EXPIRATION_MILLIS = "3600000"
```

## CORS

허용할 프론트엔드 origin은 `CORS_ALLOWED_ORIGINS` 환경변수로 설정합니다.

```powershell
$env:CORS_ALLOWED_ORIGINS = "http://localhost:3000,http://localhost:5173"
```

운영 환경에서는 실제 프론트엔드 도메인만 허용합니다.

## 비밀번호 정책

회원가입 비밀번호는 다음 조건을 만족해야 합니다.

- 8자 이상 72자 이하
- 영문자 포함
- 숫자 포함

BCrypt 특성상 72자를 초과하는 비밀번호는 허용하지 않습니다.
