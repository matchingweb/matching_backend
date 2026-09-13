# 데이터베이스 설정

## 프로필

- `local`: H2 인메모리 DB를 사용합니다. 별도 DB 설치 없이 개발 서버를 실행할 수 있습니다.
- `prod`: PostgreSQL을 사용합니다. DB 접속 정보는 환경변수로 주입합니다.

기본 프로필은 `local`입니다.

## 로컬 H2 실행

```powershell
mvn spring-boot:run
```

H2 Console:

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:matching`
- User Name: `sa`
- Password: 비워둠

## PostgreSQL 실행

Docker가 설치되어 있다면 아래 명령으로 PostgreSQL을 실행할 수 있습니다.

```powershell
docker compose up -d postgres
```

기본 접속 정보:

- Database: `matching`
- Username: `matching`
- Password: `matching`
- Port: `5432`

## prod 프로필 실행

```powershell
$env:SPRING_PROFILES_ACTIVE = "prod"
$env:DB_URL = "jdbc:postgresql://localhost:5432/matching"
$env:DB_USERNAME = "matching"
$env:DB_PASSWORD = "matching"
$env:JWT_SECRET = "replace-with-a-long-random-secret-key"
mvn spring-boot:run
```

`prod` 프로필은 `spring.jpa.hibernate.ddl-auto=validate`를 사용합니다. 운영 실행 전에는 Flyway 마이그레이션을 적용해야 합니다.
