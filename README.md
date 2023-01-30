## **백엔드 스프링 스터디 레포입니다**

### 요약 정리 누락 현황 ❌

| Github ID | 주차 |
| --- | --- |
| Clover0817 | 2주차, 3주차 |
| yu-heejin | - |
| aeeazip | 2주차, 3주차 |
| isprogrammingfun | 3주차 |
| yubin21 | - |

## ✍🏻 Project Structure
```

│
├─ isprogrammingfun (dir)
│     │ 
│     ├─  1주차 (dir) 
│     │    ├─ README.md // 해당 주차에서 정리할 부분
│     │    ├─ gradle/wrapper // 해당 주차에서 작성한 코드가 있다면 같이 올려주세요
│     │    ├─ src
│     │    ├─ .gitignore
│     │    ├─ build.gradle
│     │    ├─ gradlew
│     │    ├─ gradlew.bat
│     │    └─ settings.gradle
│     │
│     ├─ 2주차 (dir)
│     │    └─ .. 이하 동일
│     │
│     ├─ 3주차 (dir)
│     │    └─ .. 이하 동일
│     │
│     ├─ 4주차 (dir)
│     │    └─ .. 이하 동일
│     │
│     │
│     └─ 5주차 (dir)
│     │    └─ .. 이하 동일
├─ .. 이하 동일
│ 
│
```
## ✍🏻 스터디 작성 가이드

### 1. SETTING

- 해당 프로젝트를 clone 해주세요.
- clone 후, InteliJ에서 해당 프로젝트를 열어주세요.
- 위에 패키지 구조처럼 본인 폴더를 생성해주세요.


> - 본인 github 아이디명의 디렉토리
>     - 1주차
>         - 해당 주차 정리한 .md 파일
>         - 해당 주차에 작성한 소스코드
>     - 2주차
>         - 해당 주차 정리한 .md 파일
>         - 해당 주차에 작성한 소스코드

### 2. COMMIT CONVENTION

주차 별로 작은 단위로 공부를 끝낼 때 마다 commit을 해 주는 게 좋습니다. 

**❗ 해당 작업은 꼭 본인 github 아이디명의 브랜치를 파서 해주세요 절대 main 브랜치에 하면 안됩니다 ❗**  

**브랜치 생성 및 전환 명령어 :** `git checkout -b branch명` 

예시: `git checkout -b isprogrammingfun`

- **Feat**: 새로운 기능 추가
- **Fix**: 버그 수정
- **Docs**: 문서 수정
- **Style**: 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
- **Refactor**: 코드 리펙토링
- **Test**: 테스트 코드, 리펙토링 테스트 코드 추가
- **Chore**: 빌드 업무 수정, 패키지 매니저 수정
- 예시: `git commit -m "Feat: OO 기능 추가"`

### 3. README.md 파일 작성

- 해당 주차에서 공부하면서 알게 된 부분, 중요하다고 생각한 부분을 정리해서 .md 파일을 작성해주세요.
- .md 파일 작성법 : [https://gist.github.com/ihoneymon/652be052a0727ad59601#24-코드](https://gist.github.com/ihoneymon/652be052a0727ad59601#24-%EC%BD%94%EB%93%9C)

### 4. PR

- **해당 주차의 공부가 모두 끝났다면**, 프로젝트 변경 사항을 스터디 레포에 push 합니다.

> `git checkout branch명//브랜치가 본인 브랜치가 아닐 시`</br>
`git add .` </br>
`git status //git add가 잘 되었는지 확인용` </br>
`git commit -m "이 부분은 commit convention에 맞춰 작성해주세요"` </br>
`git push origin 본인 브랜치 명` </br>
> 
- push를 완료했다면 스터디 repository에서 pull request를 진행합니다.
- PR(Pull Request)시 메세지 제목은 다음과 같이 `[본인 이름] 1주차 스터디 제출합니다.` 라고 적은 후 `create pull request` 버튼을 눌러주세요.
- PR : `base: [main]` <- `compare: [본인 github 아이디]`
- **주의사항**
    - push 하기 전에 본인 브랜치가 맞는지 다시 한번 체크해주세요.
    - 머지 시 메인 브랜치와 충돌 날 우려가 있기 때문에 자신이 한 부분만 올려주세요.
    ex) 만약 내가 A를 했다면, 머지 전 메인브랜치를 받은 후 본인 코드를 넣거나 모두 삭제하고 자신이 구현한 부분만 넣을 것
    - pr을 날린 후, 머지가 자동으로 안된다면 충돌난 부분을 체크해주시고, 수정해서 다시 올려주세요.

### 해당 스터디는 1월 9일부터 시작할 예정이며, 매주 일요일이 해당 주차 스터디 마감일입니다.  일요일 오후 11:59까지 올려주시고, 3주 이상 제출하지 않을 시 퇴출시키겠습니다.
