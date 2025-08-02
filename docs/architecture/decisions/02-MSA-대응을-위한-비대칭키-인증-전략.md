# MSA 대응을 위한 비대칭키 인증 전략

Date: 2025-08-02  
Status: Accepted

## 맥락

DontGoBack 프로젝트는 단일 백엔드 서버 구조에서 벗어나, 기능별로 책임을 분산하는 **마이크로서비스 아키텍처(MSA)** 기반으로 전환 중입니다.  
그 일환으로, **유저 자산 기반 닉네임을 주기적으로 갱신하는 기능을 전담하는 '네임 서버(Name Server)'** 를 별도 서비스로 구성하게 되었습니다.

이러한 서비스 간 분리는 곧 **서버 간 보안 통신**의 필요성을 야기하며,  
기존 사용자-서버 간 인증에서 사용하던 **대칭키 기반 HS256 JWT** 방식으로는 보안과 신뢰성 측면에서 한계가 있었습니다.

이에 따라, **공개키/비공개키를 사용하는 비대칭키 기반의 RS256 JWT** 방식 도입을 결정하였습니다.

<br/>
<br/>

## 결정

다음과 같은 이유로 RS256 기반 JWT 구조를 도입하였습니다:

1. **신뢰 기반 인증 구조 구현**

   - 코어 서비스(Core Service)가 네임 서버에 토큰 발급을 요청할 수 있도록 구성하고,  
     네임 서버는 **비공개키(Private Key)** 를 사용하여 JWT를 서명하고,
     코어 서비스는 **공개키(Public Key)** 로 검증하도록 설계하였습니다.

2. **보안성과 확장성 확보**

   - 비공개키는 외부에 절대 노출되지 않으며, 공개키만을 통해 타 서비스에서도 검증이 가능함
   - 향후 복수의 마이크로서비스 간 연동 시, **공통 공개키 기반 검증 시스템** 확장 가능

3. **`.pem` 파일을 기반으로 한 동적 키 로딩 전략**

   - 네임 서버에서는 RSA 비공개키 및 공개키를 `.pem` 파일로 관리하며,  
     `@Value`를 통해 지정된 경로에서 동적으로 로딩
   - 운영 환경에서는 `.pem` 파일을 Git에 커밋하지 않고, CI/CD 또는 수동 배포를 통해 `.env` 또는 `application-prod.yml`에서 경로 주입

4. **사전 등록된 clientId + clientSecret 방식 도입**

   - 신뢰할 수 있는 서버만 토큰 발급을 요청할 수 있도록,
     `clientId`, `clientSecret`을 사전에 `auth.clients` 설정에 등록하여 요청자를 검증

5. **토큰 발급 API 및 공개키 공유 API 구현**
   - `/token`: 토큰 발급 요청
   - `/public-key`: 공개키 공유를 위한 endpoint 구성

<br/>
<br/>

## 결과 (Consequences)

- 네임 서버는 외부 요청에 대해 **비공개키 기반 JWT 발급 기능**을 안정적으로 제공하며,

  해당 토큰은 코어 서비스에서 **공개키 검증만으로 신뢰 가능**하게 되었습니다.

- MSA 전환 초기에 중요한 신뢰 계층을 수립하였으며, 향후 마이크로서비스 확장에 대비한 인증 구조의 기반을 마련하였습니다.

- 토큰 유효 시간, 발급자(issuer), 서명 키 관리 등 다양한 보안 요소를 **중앙화된 방식**으로 제어할 수 있게 되었습니다.

> 참고: PEM 키를 외부에서 동적으로 로딩하기 위한 `PemKeyLoader` 유틸리티 클래스와,  
> 클라이언트 검증용 `ClientAuthProperties`를 별도로 구성하여 설정 유연성을 확보함

```yml
# application.yml 예시

jwt:
  issuer: ${JWT_ISSUER}
  private-key-path: ${JWT_PRIVATE_KEY_PATH}
  public-key-path: ${JWT_PUBLIC_KEY_PATH}

auth:
  client:
    id: ${AUTH_CLIENT_ID}
    secret: ${AUTH_CLIENT_SECRET}
```

---

```shell
# .gitignore 예시

.env
application-prod.yml
/secrets/*.pem
```
