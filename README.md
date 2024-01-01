## HEALTH-TRAINER-SUPPORT

### 요구사항

#### 로그인
- 소셜로그인(2가지 이상)
- 일반 로그인
- Validation
  - 닉네임 중복체크

#### 채팅


### Branch 전략
- develop 브랜치를 default branch로 설정한다. 모든 feature / fix 등 다른 목적을 가진 branch 에서는 develop 에 squash merge
- feature 브랜치에서 추가적인 작업이 필요할 경우 sub branch 를 생성한다. 이때 Sub branch 에서 부모 feature branch 에 merge 할땐 자유
- sub 브랜치를 생성할땐 식별하기 쉽도록 부모 브랜치를 prefix에 붙인다.

