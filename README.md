# Project: MacroNameServer

# 1. 프로젝트 개요

### 소개

`MacroNameServer`는 **유저 닉네임을 주기적으로 갱신**하는 기능을 담당하는 **마이크로서비스**입니다.  
코어 서비스인 DontGoBack 프로젝트의 서버와 통신하는 역할을 합니다.

보안을 강화하기 위해 **비대칭키 기반의 JWT 인증(RS256)** 을 도입하여  
서버 간 신뢰할 수 있는 통신 구조를 구성하였습니다.

현재는 **간단한 토큰 발급 및 닉네임 응답 기능** 위주로 구현되어 있으며,  
향후에는 다음과 같은 기능을 확장해나갈 예정입니다:

- 닉네임 생성 알고리즘 고도화
- 갱신 이력 관리 및 저장
- 통계 기반 추천 기능 등

### 기간

- 2025.08.01 ~ (진행 중)

### 인원

- 개인 프로젝트

### 기술 스택

|      분류      |          도구           | 버전  |
| :------------: | :---------------------: | :---: |
|      언어      |          Java           |  21   |
|    Backend     |       Spring Boot       | 3.4.0 |
|     DevOps     | GitHub Actions / Docker |   -   |
| Infrastructure |      Raspberry Pi       |   -   |

### 연관 프로젝트

- CoreService GitHub 주소:
  [https://github.com/parkhongseok/projectDontGoBack](https://github.com/parkhongseok/projectDontGoBack)

<br/>
<br/>
<br/>

# 2. 주요 기능

### ① 비대칭키 기반 JWT 통신

- 코어 서비스가 클라이언트 역할을 하며, 클라이언트 ID/시크릿으로 토큰을 요청하면  
  비대칭키(RS256) 방식으로 서명된 JWT를 발급합니다.
- 해당 JWT는 공개키를 통해 검증 가능하며, 서버 간 안전한 인증 수단으로 사용됩니다.

### ② 유저 이름 갱신 RestApi

- CoreService에서 호출 가능한 `/api/v1/update-asset` API를 통해  
  유저의 자산 정보 기반으로 닉네임을 새롭게 발급해 반환합니다.
- 향후 닉네임 정책이 다양해질 것을 고려해 구조적으로 유연하게 설계되어 있습니다.

### ③ 빌드 및 배포 자동화 (예정)

- Docker를 기반으로 컨테이너화하여 배포할 예정이며,  
  CI/CD 파이프라인은 GitHub Actions를 통해 자동화할 계획입니다.

<br/>
<br/>
<br/>

# 3. 아키텍처

본 프로젝트의 아키텍처 결정 기록은 [`docs/architecture/decisions`](./docs/architecture/decisions) 디렉터리에 정리되어 있습니다.

ADR은 각 결정의 **맥락(Context)**, **결정(Decision)**, **결과(Consequences)** 를 중심으로 작성되어 서비스 구조에 대한 명확한 의사결정 흐름을 제공합니다.
