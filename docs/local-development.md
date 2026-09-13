# 로컬 개발 환경

## 필수 도구

- Java 17 이상
- Maven 3.9 이상

현재 프로젝트는 Spring Boot 3.3.5 기반입니다. 로컬에서 실행하기 전에 아래 명령이 정상 동작해야 합니다.

```powershell
java -version
mvn -v
```

## 환경 점검

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\check-env.ps1
```

## 테스트

```powershell
mvn test
```

## 로컬 실행

```powershell
mvn spring-boot:run
```

또는 실행 스크립트를 사용할 수 있습니다.

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\run-local.ps1
```

## 로컬 확인 URL

- API 문서: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- H2 Console: `http://localhost:8080/h2-console`

H2 접속 정보:

- JDBC URL: `jdbc:h2:mem:matching`
- User Name: `sa`
- Password: 비워둠

## 현재 제한 사항

JDK 또는 Maven이 설치되어 있지 않으면 빌드와 실행은 실패합니다. 이 경우 Java 17 이상과 Maven 3.9 이상을 설치한 뒤 `scripts/check-env.ps1`을 다시 실행하세요.
