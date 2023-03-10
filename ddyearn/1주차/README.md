## 스프링 프레임워크의 핵심 기능

### 의존성 주입 (Dependency Injection)
의존하는 부분을 외부에서 주입하는 것  

### 관점 지향 프로그래밍 (Aspect Oriented Programming)
공통 처리 등의 '횡단적 관심사'를 추출하고 프로그램의 여러 곳에서 호출할 수 있게 설정함으로써
개발자는 실현해야 할 기능인 '중심적 관심사'에만 집중해서 작성하면 되는 구조
+ 중심적 관심사(Primary Concern) : 실형해야 할 기능을 나타내는 프로그램
+ 횡단적 관심사(Crosscutting-Concerns) : 본질적인 기능은 아니지만 품질이나 유지보수 등의 관점에서 반드시 필요한 기능을 나타내는 프로그램
  + 예외 처리
  + 로그 정보 화면이나 파일 등으로 출력 처리
  + 데이터베이스의 트랜잭션 제어 등

---

## Gradle 이란?
빌드 도구. 빌드란 '요구된 실행 환경에서 동작할 수 있는 형식에 애플리케이션이나 라이브러리를 조립하는 것'을 말한다.
빌드 도구는 다음의 반복적인 작업을 자동화한다.
+ 필요한 라이브러리를 리포지토리(라이브러리가 저장되어 있는 곳)에서 다운로드한다.
+ 소스코드를 컴파일한다.
+ 테스트를 실행하여 보고서를 출력한다.
+ 클래스 파일의 아카이브(여러 파일이나 폴더를 하나로 정리하는 것)를 생성한다.
+ 아카이브를 스테이징 환경(프로덕션 환경에 가까운 환경) 등에 배포한다.

