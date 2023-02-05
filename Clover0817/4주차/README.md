# |DB

### **1) DAO(Data Access Object)**

- 데이터 액세스 계층은 DAO 패턴으로 **비즈니스 로직과 데이터 액세스 로직을 분리**하는 것이 원칙
- 비즈니스 로직이 없거나 단순→DAO, 서비스 계층 통합
- 의미 있는 비즈니스 로직→데이터 액세스 계층을 DAO 패턴으로 분리
- DAO 패턴은 서비스계층에 영향을 주지 않고, **데이터 액세스 기술을 변경**할 수 있음

### 2) 커넥션 풀링을 지원하는 DataSource

**커넥션 풀링**은 미리 정해진 개수만큼의 **DB 커넥션을 풀(Pool)에 준비**해두고, 

애플리케이션이 **요청할 때마다 Pool에서 꺼내서 하나씩 할당**해주고 다시 **돌려받아서 Pool에 넣는 식**

- 다중 사용자를 갖는 엔터프라이즈 시스템에서라면 반드시 DB커넥션 풀링 기능을 지원하는 DataSource를 사용해야
- Spring에서는 DataSource를 공유 가능한 **Spring Bean**으로 등록해 주어 사용할 수 있도록 해준다.

### 3)**Spring JDBC**

**JDBC는 모든 자바의 데이터 액세스 기술의 근간.**

엔티티 클래스와 애노테이션을 이용하는 최신 ORM 기술도 내부적으로는 DB와의 연동을 위해 JDBC를 이용한다.

- 안정적이고 유연한 기술이지만, **로우 레벨 기술로 인식**
- 간단한 SQL을 실행하는 데도 **중복된 코드가 반복적으로 사용**
- **장점 :** 대부분의 개발자가 잘 알고 있는 친숙한 데이터 액세스 기술로 별도의 학습 없이 개발이 가능
- **단점 :** Connection과 같은 공유 리소스를 제대로 릴리즈 해주지 않으면 시스템의 자원이 바닥나는 버그를 발생

Spring JDBC는 기존 JDBC의 장점과 단순성을 그대로 유지하면서 **단점은 극복**

**간결한 형태의 API 사용법을 제공**, JDBC API에서 지원되지 않는 편리한 기능을 제공

- **반복적으로 해야 하는 많은 작업들을 대신 함**
- 사용할 때는 실행할 SQL과 바인딩 할 파라미터를 넘겨 주거나, 쿼리의 실행 결과를 어떤 객체에 넘겨 받을지를 지정하는 것만 하면 됨
- Spring JDBC를 사용하려면 먼저, **DB 커넥션을 가져오는 DataSource를 Bean으로 등록해야**


<br>
**작업 예시)**

-**Connection 열기와 닫기**

- Connection과 관련된 모든 작업을 Spring JDBC가 필요한 시점에서 알아서 진행
- 진행 중에 예외가 발생했을 때도 열린 모든 Connection 객체를 닫아줌

-**Statement 준비와 닫기**

- SQL 정보가 담긴 Statement 또는 PreparedStatement를 생성하고 필요한 준비 작업을 해주는 것도 Spring JDBC가 대신 처리
- Statement도 Connection과 마찬가지로 사용이 끝나고 나면 Spring JDBC가 알아서 객체를 닫아줌

-**Statement 실행**

- SQL 담긴 Statement를 실행
- Statement의 실행결과는 다양한 형태로 가져올 수 있음

-**ResultSet Loop 처리**

- ResultSet에 담긴 쿼리 실행 결과가 한 건 이상이면 ResultSet 루프를 만들어서 반복해줌

-**Exception 처리**

- JDBC 작업 중 발생하는 모든 예외는 Spring JDBC 예외 변환기가 처리
- 체크 예외(Checked Exception)인 SQLException을 런타임 예외(Runtime Exception)인 DataAccessException 타입으로 변환

-**Transaction 처리**

- Spring JDBC를 사용하면 Transaction과 관련된 모든 작업에 대해서는 신경 쓸 필요 없음

+**JdbcTemplate 클래스**

 Spring JDBC가 제공하는 클래스 중 jdbcTemplate은 JDBC의 모든 기능을 최대한 활용할 수 있는 **유연성**을 제공하는 클래스

- jdbcTemplate이 제공하는 기능은 **실행,조회,배치**의 3가지 작업
- **실행** : Insert나 Update같이 DB의 데이터에 변경이 일어나는 쿼리를 수행하는 작업
- **조회** : Select를 이용해 데이터를 조회하는 작업
- **배치** : 여러 개의 쿼리를 한 번에 수행해야 하는 작업
<br><br>
# |MVC

### 1)모델(Model) 컴포넌트

-데이터 저장소(ex : 데이터베이스)와 연동하여 사용자가 입력한 데이터나 사용자에게 출력할 데이터를 다루는 일을 함. 

여러 개의 데이터 변경 작업(추가, 변경, 삭제)을 하나의 작업으로 묶는 트랜잭션을 다루는 일도 함

DAO 클래스, Service 클래스에 해당

모델은 데이터와 관련된 컴포넌트로, 데이터베이스 연동, 데이터 CRUD을 구현. 

또한 VO(Value Object, =도메인 객체)도 모델이라고 볼 수 있습니다.

### 2)뷰(View) 컴포넌트

-모델이 처리한 데이터나 그 작업 결과를 가지고 사용자에게 출력할 화면을 만드는 일을 함

생성된 화면은 웹 브라우저가 출력하고, 뷰 컴포넌트는 HTML과 CSS, JavaScript를 사용하여 웹 브라우저가 출력할 UI를 만듦

### 3)컨트롤러(Controller) 컴포넌트

-클라이언트의 요청을 받았을 때 그 요청에 대해 실제 업무를 수행하는 모델 컴포넌트를 호출하는 일을 함

클라이언트가 보낸 데이터가 있다면, 모델을 호출할 때 전달하기 쉽게 데이터를 적절히 가공하는 일을 함

모델이 업무 수행을 완료하면, 그 결과를 가지고 화면을 생성하도록 뷰에게 전달(클라이언트 요청에 대해 모델과 뷰를 결정하여 전달)

Servlet과 JSP를 사용하여 작성할 수 있음

클라이언트 요청에 따라 모델을 적절히 제어

클라이언트 요청을 받아서 분석해야 적절한 모델을 호출

또한, 데이터를 전달할 때 적절히 가공&모델로부터 받은 결과 데이터를 뷰에 전달하는 역할

### 4)Spring MVC의 주요 구성 요소

Controller : @Controller 어노테이션을 사용하여 우리가 개발할 Controller

HandlerMapping : Spring에 내장되어 있어서 개발자가 직접 건들 필요없음. DispatcherServlet이 요청을 최초를 받을 때 어떤 Controller를 호출할지 결정하는 클래스

ModelAndView : View의 이름(ex : jsp 파일 이름)과 View에 바인딩 되어야 할 데이터인 모델 객체를 모두 포함하는 객체.

View : View의 이름(jsp의 파일 이름) 즉, 정보를 보유하는 객체

ViewResolver : Controller에서 어떤 View를 선택할지 결정

### 5)Spring MVC의 요청 처리 과정

DispatcherServlet이 요청을 받으면 안에 있는 HandlerMapping 클래스에 의해서 적절한 Controller가 결정됨. 그 결정에 따라 해당 Controller를 호출 

Controller는 DispatcherServlet에 MedelAndView를 전달(여기에는 데이터와 JSP파일 이름이 들어가 있음) 

DispatcherServlet는 View Resolver를 통해 View를 확보하게 되고 이를 클라이언트에게 응답
