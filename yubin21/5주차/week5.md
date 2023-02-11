## Spring Study 5주차

## 배운내용
### 2. 서블릿
#### 프로젝트 생성과 Hello 서블릿
- 스프링부트 환경에서 서블을 등록하고 사용.

#### HttpServletRequest

HttpServletResponse 역할
- HTTP 응답 메시지 생성 
- HTTP 응답코드 지정 헤더 생성
- 바디 생성

Content 편의 메서드 / 쿠키 편의 메서드 / redirect 편의 메서드 생성

#### HTTP 요청 데이터
**HTTP 요청 데이터 - GET 쿼리 파라미터**
- 쿼리 파라미터는 URL에 다음과 같이`?`를 시작으로 보낼 수 있다. 추가 파라미터는 `&`로 구분하면 된다.

**HTTP 요청 데이터 - POST HTML Form**
- 이번에는 HTML의 Form을 사용해서 클라이언트에서 서버로 데이터를 전송해보자.
주로 회원 가입, 상품 주문 등에서 사용하는 방식이다.

**HTTP 요청 데이터 - API 메시지 바디 - 단순 텍스트**
- **HTTP message body**에 데이터를 직접 담아서 요청
  - HTTP API에서 주로 사용, JSON, XML, TEXT 
  - 데이터 형식은 주로 JSON 사용
  - POST, PUT, PATCH
- HTTP 메시지 바디의 데이터를 InputStream을 사용해서 직접 읽을 수 있다.

#### HTTP 응답 데이터

**HTTP 응답 데이터 - 단순 텍스트, HTML**  
HTTP 응답 메시지는 주로 다음 내용을 담아서 전달한다.
- 단순 텍스트 응답
  - 앞에서 살펴봄 ( writer.println("ok"); )
- HTML 응답
- HTTP API - MessageBody JSON 응답

**HTTP 응답 데이터 - API JSON**   
HTTP 응답으로 JSON을 반환할 때는 content-type을 `application/json` 로 지정해야 한다. Jackson 라이브러리가 제공하는 `objectMapper.writeValueAsString()` 를 사용하면 객체를 JSON 문자로 변경할 수 있다.

### 3. 서블릿, JSP, MVC 패턴
기능 요구사항을 확인한고 웹 애플리케이션 만들기

#### 서블릿으로 회원 관리 웹 애플리케이션 만들기
- 서블릿 덕분에 동적으로 원하는 HTML을 마음껏 만들 수 있다. but  매우 복잡하고 비효율 적이다. 자바 코드로 HTML을 만들어 내는 것 보다 차라리 HTML 문서에 동적으로 변경해야 하는 부분만 자바 코드를 넣을 수 있다면 더 편리할 것. -> 템플릿 엔진 등장
- 템플릿 엔진을 사용하면 HTML 문서에서 필요한 곳만 코드를 적용해서 동적으로 변경할 수 있다.
- 템플릿 엔진에는 JSP, Thymeleaf, Freemarker, Velocity등이 있다.

#### JSP로 회원 관리 웹 애플리케이션 만들기
서블릿과 JSP의 한계
- 서블릿으로 개발할 때는 뷰(View)화면을 위한 HTML을 만드는 작업이 자바 코드에 섞여서 지저분하고 복잡했다.
- JSP를 사용한 덕분에 뷰를 생성하는 HTML 작업을 깔끔하게 가져가고, 중간중간 동적으로 변경이 필요한 부분에만 자바 코드를 적용. but JSP가 너무 많은 역할을 한다 ->  MVC 패턴의 등장

#### MVC 패턴 - 개요
**너무 많은 역할**  
- 하나의 서블릿이나 JSP만으로 비즈니스 로직과 뷰 렌더링까지 모두 처리하게 되면, 너무 많은 역할을 하게되고, 결과적으로 유지보수가 어려워진다.  

**변경의 라이프 사이클**  
- 둘 사이에 변경의 라이프 사이클이 다르다는 점이다. 예를 들어서 UI 를 일부 수정하는 일과 비즈니스 로직을 수정하는 일은 각각 다르게 발생할 가능성이 매우 높고 대부분 서로에게 영향을 주지 않는다.

**기능 특화**
-특히 JSP 같은 뷰 템플릿은 화면을 렌더링 하는데 최적화 되어 있기 때문에 이 부분의 업무만 담당하는 것이 가장 효과적이다.

**Model View Controller**
- MVC 패턴은 지금까지 학습한 것 처럼 하나의 서블릿이나, JSP로 처리하던 것을 컨트롤러(Controller)와 뷰(View)라는 영역으로 서로 역할을 나눈 것을 말한다

#### MVC 패턴 - 적용
- 실습 참조

#### MVC 패턴 - 한계
MVC 패턴을 적용한 덕분에 컨트롤러의 역할과 뷰를 렌더링 하는 역할을 명확하게 구분할 수 있다. but 컨트롤러는 딱 봐도 중복이 많고, 필요하지 않는 코드들도 많이 보인다.

**MVC 컨트롤러의 단점**
- 포워드 중복  
View로 이동하는 코드가 항상 중복 호출되어야 한다. 물론 이 부분을 메서드로 공통화해도 되지만, 해당 메서드도 항상 직접 호출해야 한다.
- ViewPath에 중복
- 공통 처리가 어렵다.  
이 문제를 해결하려면 컨트롤러 호출 전에 먼저 공통 기능을 처리해야 한다. 소위 수문장 역할을 하는 기능이 필요하다. 프론트 컨트롤러(Front Controller) 패턴을 도입하면 이런 문제를 깔끔하게 해결할 수 있다.

### 4. MVC 프레임워크 만들기
**FrontController 패턴 특징**
- 프론트 컨트롤러 서블릿 하나로 클라이언트의 요청을 받음
- 프론트 컨트롤러가 요청에 맞는 컨트롤러를 찾아서 호출
- 입구를 하나로!
- 공통 처리 가능
- 프론트 컨트롤러를 제외한 나머지 컨트롤러는 서블릿을 사용하지 않아도 됨

**버전별 실습**

v1: 프론트 컨트롤러를 도입  
기존 구조를 최대한 유지하면서 프론트 컨트롤러를 도입

v2: View 분류  
단순 반복 되는 뷰 로직 분리

v3: Model 추가 서블릿 종속성 제거  
뷰 이름 중복 제거

v4: 단순하고 실용적인 컨트롤러 v3와 거의 비슷  
구현 입장에서 ModelView를 직접 생성해서 반환하지 않도록 편리한 인터페이스 제공

v5: 유연한 컨트롤러 어댑터 도입  
어댑터를 추가해서 프레임워크를 유연하고 확장성 있게 설계  

### 5. 스프링 MVC - 구조 이해
**동작 순서**
1. 핸들러 조회: 핸들러 매핑을 통해 요청 URL에 매핑된 핸들러(컨트롤러)를 조회한다.
2. 핸들러 어댑터 조회: 핸들러를 실행할 수 있는 핸들러 어댑터를 조회한다.
3. 핸들러 어댑터 실행: 핸들러 어댑터를 실행한다.
4. 핸들러 실행: 핸들러 어댑터가 실제 핸들러를 실행한다.
5. ModelAndView 반환: 핸들러 어댑터는 핸들러가 반환하는 정보를 ModelAndView로 변환해서
   반환한다.
6. viewResolver 호출: 뷰 리졸버를 찾고 실행한다.
   - JSP의 경우: InternalResourceViewResolver 가 자동 등록되고, 사용된다.
7. View반환:뷰 리졸버는 뷰의 논리 이름을 물리 이름으로 바꾸고,렌더링 역할을 담당하는 뷰 객체를 반환한다.
   - JSP의 경우 InternalResourceView(JstlView) 를 반환하는데, 내부에 forward() 로직이 있다.
8. 뷰 렌더링:뷰를 통해서 뷰를 렌더링한다.

**핸들러 매핑과 핸들러 어댑터**
HandlerMapping(핸들러 매핑)
- 핸들러 매핑에서 이 컨트롤러를 찾을 수 있어야 한다.
- 예) 스프링 빈의 이름으로 핸들러를 찾을 수 있는 핸들러 매핑이 필요하다.

HandlerAdapter(핸들러 어댑터)
- 핸들러 매핑을 통해서 찾은 핸들러를 실행할 수 있는 핸들러 어댑터가 필요하다.
- 예) Controller 인터페이스를 실행할 수 있는 핸들러 어댑터를 찾고 실행해야 한다.  

스프링은 이미 필요한 핸들러 매핑과 핸들러 어댑터를 대부분 구현해두었다. 개발자가 직접 핸들러 매핑과 핸들러 어댑터를 만드는 일은 거의 없다.

### HttpRequestHandler
`HttpRequestHandler` 핸들러(컨트롤러)는 서블릿과 가장 유사한 형태의 핸들러이다.

1. 핸들러 매핑으로 핸들러 조회
   1. `HandlerMapping` 을 순서대로 실행해서, 핸들러를 찾는다.
   2. 이 경우 빈 이름으로 핸들러를 찾아야 하기 때문에 이름 그대로 빈 이름으로 핸들러를 찾아주는 `BeanNameUrlHandlerMapping` 가 실행에 성공하고 핸들러인 `MyHttpRequestHandler` 를 반환한다.
2. 핸들러 어댑터 조회
   1. `HandlerAdapter` 의 `supports()` 를 순서대로 호출한다.
   2. `HttpRequestHandlerAdapter` 가 `HttpRequestHandler` 인터페이스를 지원하므로 대상이 된다.
3. 핸들러 어댑터 실행
   1. 디스패처 서블릿이 조회한 `HttpRequestHandlerAdapter` 를 실행하면서 핸들러 정보도 함께 넘겨준다.
   2. `HttpRequestHandlerAdapter` 는 핸들러인 `MyHttpRequestHandler` 를 내부에서 실행하고, 그 결과를 반환한다.

### 뷰 리졸버
1. 핸들러 어댑터 호출
   핸들러 어댑터를 통해 new-form 이라는 논리 뷰 이름을 획득한다.
2. ViewResolver 호출
   - `new-form` 이라는 뷰 이름으로 `viewResolver`를 순서대로 호출한다.
   - `BeanNameViewResolver` 는 `new-form` 이라는 이름의 스프링 빈으로 등록된 뷰를 찾아야 하는데 없다. 
   - `InternalResourceViewResolver` 가 호출된다.
3. InternalResourceViewResolver
   이 뷰 리졸버는 `InternalResourceView` 를 반환한다. 
4. 뷰 - InternalResourceView
   `InternalResourceView` 는 JSP처럼 포워드 `forward()` 를 호출해서 처리할 수 있는 경우에 사용한다.
5. view.render()
   `view.render()` 가 호출되고 `InternalResourceView` 는 `forward()` 를 사용해서 JSP를 실행한다.

### 스프링 MVC - 시작하기 -> 컨트롤러 통합 -> 실용적인 방식
- @RequestMapping 을 잘 보면 클래스 단위가 아니라 메서드 단위에 적용된 것을 확인할 수 있다. 따라서
  컨트롤러 클래스를 유연하게 하나로 통합할 수 있다.
- 스프링 MVC는 개발자가 편리하게 개발할 수 있도록 수 많은 편의 기능을 제공한다.
  실무에서는 지금부터 설명하는 방식을 주로 사용한다.
