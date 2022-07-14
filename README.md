# VRR
VRR 프로젝트

## 로컬실행 환경 구성
로컬 실행환경 구성을 위해선 아래와 같은 명령을 통해 Docker 실행환경을 구축해야 합니다. (테스트 In-Memory 환경이므로 별도)

```sh
$ docker-compose up -d   # -d 옵션으로 서비스 실행후 콘솔을 빠져나옴
$ docker-compose down   # 생성한 컨테이너를 삭제한다.
```