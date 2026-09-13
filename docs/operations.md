# 운영 설정

## Health Check

Actuator health endpoint를 제공합니다.

```http
GET /actuator/health
```

배포 플랫폼의 health check 경로는 `/actuator/health`로 설정합니다.

## Graceful Shutdown

`server.shutdown=graceful`을 사용합니다. 배포 플랫폼이 종료 신호를 보내면 진행 중인 요청을 최대한 마무리한 뒤 종료합니다.

## Timezone

JSON 직렬화 기준 시간대는 `Asia/Seoul`입니다.

```yaml
spring:
  jackson:
    time-zone: Asia/Seoul
```

## Error Logging

예상하지 못한 서버 내부 오류는 `GlobalExceptionHandler`에서 error 로그로 남깁니다. 클라이언트에는 내부 예외 상세를 노출하지 않고 공통 에러 메시지를 반환합니다.

## Request Logging

기본 요청 로그를 남기기 위해 `CommonsRequestLoggingFilter`를 등록했습니다.

- Query string 포함
- Header 미포함
- Payload 미포함

민감 정보가 로그에 남지 않도록 payload와 header는 기록하지 않습니다.
