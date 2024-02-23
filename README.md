## HEALTH-TRAINER-SUPPORT

### 개발환경 세팅 (Local)
#### MySQL
MySQL 실행 후 database 를 추가한다.
```
create database health
```
#### Redis
docker-compose.yml
```
version: '3.0'

services:
  redis:
    hostname: redis
    container_name: redis
    image: redis:latest
    command: redis-server --requirepass healthgood1! --port 6379
    ports:
      - 6379:6379
```
docker-compose up -d

### Branch
- develop 브랜치를 default branch로 설정한다. 모든 feature / fix 등 다른 목적을 가진 branch 에서는 develop 에 squash merge
- feature 브랜치에서 추가적인 작업이 필요할 경우 sub branch 를 생성한다. 이때 Sub branch 에서 부모 feature branch 에 merge 할땐 자유
- sub 브랜치를 생성할땐 식별하기 쉽도록 부모 브랜치를 prefix에 붙인다.

### Response
#### Success
Status Code 200 으로 통일
- pagination X
  ```json
    {
      "success": true,
      "data": T?,
    }
  ```
- pagination

#### Fail
- Server 응답 성공시 Status Code 200
  ```json
    {
      "success": false,
      "code": String,
      "message": String
    }
    ```
    - code: Exception 을 구분하기 위함
    - message: Exception 의 디테일한 내용
- Server 응답 실패시 Status Code 5XX

