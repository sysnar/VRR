# VRR
VRR 프로젝트

## 프로젝트 문서 
프로젝트 문서는 크게 2가지로 구성되어 있습니다. 
- Swagger API 문서
  - API 요청, 응답에 필요한 Interface 들을 정리합니다.
  - 링크 : https://XXXXX.com/document/swagger/index.html
- Rest Docs 상세 API 기술
  - 오류 코드, 공통 코드, API 사용방법, 사용처 등 API를 활용함에 필요한 세부 정보들을 정리합니다.
  - 링크 : https://XXXXX.com/document/restdocs/index.html

## 로컬실행 환경 구성
로컬 실행환경 구성을 위해선 아래와 같은 명령을 통해 Docker 실행환경을 구축해야 합니다. (테스트 In-Memory 환경이므로 별도)

### 1. 프로젝트 환경변수 설정
- `docker-compose.yml`파일에 사용되는 환경변수를 담은 `.env` 파일을 다운로드 받아주세요.
- 다운로드 받은 `.env` 파일을 `docker-compose.yml` 파일과 동일한 디렉터리 경로에 위치합니다.

### 2. 프로젝트 실행
```sh
$ docker-compose up -d   # -d 옵션으로 서비스 실행후 콘솔을 빠져나옴
$ docker-compose down   # 생성한 컨테이너를 삭제한다.
```

### 3. 모듈 구조
모듈은 크게 3가지로 이루어져있습니다.
- application
- domain
- common

#### common 모듈
common 모듈은 다른 모듈들에서 공통적으로 사용되는 코드들을 포함하는 모듈입니다.  
common 모듈이 의존성을 가지게 되면 common 모듈을 의존하는 다른 모듈들 또한 의존하게 됩니다.  
따라서 라이브러리 등에 의존성을 가지지 않도록 설계하였습니다.  
결과적으로 프로젝트 전반에 사용되는 공통코드, 순수 java 파일들만으로 구성되어 있습니다.

#### domain 모듈
domain 모듈은 서비스를 구성하는 각 도메인 영역을 정의하는 모듈입니다.  
`DDD` 방법론을 활용해 도메인 간의 역할과 책임을 분리하려 노력하였습니다.  
개별 도메인은 domain, service 패키지로 이루어져 있습니다. 
- domain : 객체의 역할과 책임을 가진 `Entity`별 연관관계를 포함하고 있습니다. 
- service : `domain service` 등 핵심적인 비즈니스 로직을 포함하고 있습니다.

#### application 모듈
application 모듈은 서비스 제공을 위한 표현 계층과 얇은 application service 영역을 포함하는 모듈입니다.  
API 제공을 위한 `api(controller)`, `application service`, `조회용 repository`로 구성되어 있습니다.

``` 
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── vrr
    │   │           └── application.api
    │   │               ├── ApiApplication.java
    │   │               ├── domain
    │   │               │   ├── auth
    │   │               │   │   ├── api.v1.auth
    │   │               │   │   ├── repository
    │   │               │   │   └── service
    │   │               │   └── plan
    │   │               │       ├── api.v1.tour
    │   │               │       ├── repository
    │   │               │       └── service
    │   │               └── global
    │   │                   ├── controller
    │   │                   ├── domain
    │   │                   ├── exception
    │   │                   ├── repository
    │   │                   └── service
```

### 4. 테스팅
안정적이고, 변화에 유연하게 대응할 수 있는 코드 베이스를 위해 테스트 코드를 사용하고 있습니다.  
`Unit Test`혹은 `Aceeptance Test`를 상황에 맞게 적용하고 있습니다.
