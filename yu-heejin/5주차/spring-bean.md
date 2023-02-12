## Spring Bean

- 스프링 컨테이너에 의해 관리되는 자바 객체(POJO)

## Spring Container

- 스프링 컨테이너는 스프링 Bean의 생명 주기를 관리하고, 생성된 스프링 빈들에게 추가적인 기능을 제공하는 역할을 한다.
- IoC와 DI의 원리가 스프링 컨테이너에 적용된다.
- 개발자는 new 연산자, 인터페이스 호출, 팩토리 호출 방식으로 객체를 생성하고 소멸하지만, 스프링 컨테이너를 사용하면 해당 역할을 대신해준다.
    - 즉, 제어 흐름을 외부에서 관리하게 된다.
- 또한, 객체들 간의 의존 관계를 스프링 컨테이너가 런타임 과정에서 알아서 만들어준다.

## Spring Bean 등록 방식

### Component Scan

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface Component {
```

- 컴포넌트 스캔은 @Component를 명시하여 빈을 추가하는 방법이다.
- 클래스 위에 @Component를 붙이면 스프링이 알아서 스프링 컨테이너에 빈을 등록한다.
- @Component는 EementType.TYPE 설정이 있기 때문에 class 혹은 Interface에만 붙일 수 있다.

### 컴포넌트 스캔의 대상

- @Controller, @Service, @Repository, @Configuration은 @Component의 상속을 받고 있기 때문에 컴포넌트 스캔의 대상이다.
    - @Controller : 스프링 MVC 컨트롤러로 인식된다.
    - @Repository : 스프링 데이터 접근 계층으로 인식하고 해당 계층에서 발생하는 예외는 모두 DataAccessException으로 변환한다.
    - @Service : 특별한 처리는 하지 않으나, 개발자들이 핵심 비즈니스 계층을 인식하는데 도움을 준다.
    - @Configuration : 스프링 설정 정보로 인식하고 스프링 빈이 싱글콘을 유지하도록 추가 처리를 한다.
        - 단, 스프링 빈 scope가 싱글톤이 아니라면 추가 처리를 하지 않는다.

### Java 코드로 등록

- 자바 코드로도 빈을 등록할 수 있다.
- @Configuration 어노테이션을 활용한다.
    
    ```java
    @Configuration
    public class AppConfig {
    
        @Bean
        public MemberRepository memberRepository() {
            return new MemoryMemberRepository();
        }
    
        @Bean
        public MemberService memberService() {
            return new MemberServiceImpl(memberRepository());
        }
    
    }
    ```
    
- 이때, 빈을 등록하기 위해 인스턴스를 생성하는 메소드 위에 @Bean을 명시하면 된다.
    
    ```java
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface Bean {
    ```
    
    - @Bean은 ElementType 설정이 METHOD, ANNOTATION_TYPE이므로 메소드나 어노테이션에만 붙일 수 있다.
- @Configuration에는 @Component가 있기 때문에 컴포넌트 스캔의 대상이 되어 자동 스캔을 통해 빈 등록이 가능하다.

## @Bean vs @Component

### @Bean

- 개발자가 컨트롤이 불가능한 외부 라이브러리들을 Bean으로 등록하고 싶은 경우 사용
- 메소드 또는 어노테이션 단위에 붙일 수 있다.

### @Component

- 개발자가 직접 컨트롤이 가능한 클래스들의 경우에 사용한다.
- 클래스 또는 인터페이스 단위에 붙일 수 있다.

## @Configuration과 싱글톤

- @Configuration은 @Bean에 추가 설정을 줘서 싱글톤으로 만들지 않는 이상 무조건 빈에 대해 싱글톤을 보장한다.
    
    ```java
    @Configuration
    public class AppConfig {
    
        @Bean
        public MemberService memberService() {
            return new MemberServiceImpl(memberRepository());
        }
    
        @Bean
        public OrderService orderService() {
            return new OrderServiceImpl(memberRepository(), discountPolicy());
        }
    
        @Bean
        public MemberRepository memberRepository() {
            return new MemoryMemberRepository();
        }
    }
    ```
    
    - MemberService와 OrderService를 Bean으로 등록할 때 모두 memberRepository() 메소드를 호출하는 것을 알 수 있다.
    - 결과적으로 각각 다른 2개의 MemoryMemberRepository가 생성되어 싱글톤이 깨진다고 생각할 수 있다.
    - 하지만 **@Configuration은 클래스의 바이트 코드를 조작하는 라이브러리인 CGLIB를 사용하여 싱글톤을 보장한다.**
- **CGLIB는 프록시 객체의 일종**으로 AppConfig가 Bean으로 등록될 때, AppConfig 대신 AppConfig를 상속 받은 AppConfig$CGLIB 형태로 프록시 객체가 등록된다.
    
    ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/c08940e5-e35f-439c-bdc0-052c09d8b203/Untitled.png)
    
    - 이름은 appConfig가 되고, 실제 등록되는 스프링 빈은 CGLIB 클래스의 인스턴스가 등록된다.
    - CGLIB는 다음과 같이 구현되어 있다.
        
        ```java
        @Bean
        public MemberRepository memberRepository() {
            if(memorymemberRepository가 이미 스프링 컨테이너에 등록되어있으면?) {
                return 스프링 컨테이너에서 찾아서 반환;
            } else { // 스프링 컨테이너에 없으면
                기존로직을 호출해서 MemoryMemberRepository를 생성하고 스프링 컨테이너에 등록
                return 반환;
            }
        }
        ```
        
- @Bean이 등록된 메소드마다 이미 스프링 빈이 존재하면 존재하는 빈을 반환하고, 스프링 빈이 없으면 생성해서 스프링 빈으로 등록하고 반환하는 코드가 동적으로 만들어진다.

## Bean Lite Mode

```java
@Component
public class AppConfig {

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
```

- CGLIB를 이용하여 바이트 코드 조작을 하지 않는 방식을 의미한다.
- 즉, **Spring Bean의 싱글톤을 보장하지 않는다.**
- Bean Lite Mode로 설정하려면 @Configuration이 아닌 @Component로 변경하면 된다.
    - 이렇게 하면 objectMapperLiteBean() 메소드를 lite mode로 작동하여 매번 다른 객체를 반환해줄 수 있다.
- 추가적으로 ApplicationContext를 사용하여 설정 파일을 가지고 빈을 수동 등록한다면, @Component가 없어도 Bean Lite Mode가 동작한다.

## Spring Bean Scope

- 스프링에서 Singleton과 Prototype Bean Scope를 제공하고 있으며, Spring MVC 웹 애플리케이션을 사용할 경우 Web Scope를 제공한다.

### Singleton

- Singleton Bean은 스프링 컨테이너에서 한 번만 생성되며, 컨테이너가 사라질 때 제거된다.
- 생성된 하나의 인스턴스는 Spring Beans Cache에 저장되고, 해당 빈에 대한 요청과 참조가 있으며, 캐시된 객체를 반환한다.
- 하나만 생성되기 때문에 동일 참조를 보장한다.
- 기본적으로 모든 Bean은 Scope가 명시적으로 지정되지 않으면 싱글톤이다.
- 싱글톤 타입으로 적합한 객체
    - 상태가 없는 공유 객체
    - 읽기 전용으로 상태를 가진 객체
    - 쓰기가 가능한 상태를 지니면서도 사용 빈도가 매우 높은 객체
        - 단, 이때는 동기화 전략이 필요하다.

### Prototype

- prototype Bean은 DI가 발생할 때마다 새로운 객체가 생성되어 주입된다.
- 빈 소멸에 스프링 컨테이너가 관여하지 않고 gc에 의해 빈이 제거된다.
- 대상 클래스에 Scope(”prototype”)을 붙이면 된다.
- 프로토 타입으로 적합한 객체
    - 사용할 때마다 상태가 달라져야하는 객체
    - 쓰기가 가능한 상태가 있는 객체

## Singleton Bean과 Prototype Bean을 혼용할 경우 발생하는 문제

- 아래 처럼 프로토 타입 객체가 싱글톤 객체를 가지고 있는 것은 문제가 되지 않는다.
    
    ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/c48e8563-db16-4611-8aa0-d321fc856366/Untitled.png)
    
- 하지만, 아래와 같이 싱글톤 객체가 프로토 타입 객체를 가지고 있는 경우에는 의도한 것과 다른 결과를 낼 수도 있다.
    
    ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/3e306ba8-87e2-493c-bf5e-755479cc610b/Untitled.png)
    
    - 이미 싱글톤 빈으로 생성되는 시점에 프로토 타입 빈이 생성되어 들어오기 때문에 싱글톤 빈 내부의 프로토 타입 빈을 호출하게 되면 매번 같은 값을 가져온다.

## Web Scope

- 기존의 스프링이 사용하는 싱글톤 scope는 스프링 컨테이너의 시작부터 끝까지 함께하는 scope이다.
- 프로토타입 scope는 생성과 의존 관계 주입 및 초기화까지만 진행하는 scope
- web scope는 웹 환경에서만 동작하는 scope이며, 프로토 타입과 다르게 특정 주기가 끝날 때까지 관리해준다.
    - 따라서 @PreDestroy와 같은 소멸 콜백이 호출된다는 특징이 있다.

### Web Scope 종류

- Request
    - HTTP 요청 하나가 들어오고 나갈 때까지 유지되는 scope
    - 각각의 HTTP 요청마다 별도의 Bean 인스턴스가 생성되고 관리된다.
- Session
    - HTTP Session과 동일한 생명 주기를 가지는 scope
- Application
    - 서블릿 컨텍스트와 동일한 생명 주기를 가지는 scope
- WebSocket
    - 웹 소캣과 동일한 생명 주기를 가지는 scope

### Request scope 동작 방식

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/812b8adc-9b41-4154-8fb3-5378bbf793a4/Untitled.png)

- MyLogger라는 로그 클래스를 Request Scope로 등록하였고, 한 클라이언트 A가 요청을 보냈다고 가정하자.
    - 컨트롤러에서 myLogger 객체를 요청 받았다면, 스프링 컨테이너는 A 전용으로 사용할 수 있는 Bean을 생성하여 컨트롤러에 주입해준다.
    - 그 후 로직이 진행되면서 서비스에서 다시 myLogger 객체가 필요해서 요청을 하면, 방금 A 전용으로 생성했던 Bean을 그대로 활용해 주입받을 수 있다.
    - 이후 요청이 끝나면 Request 빈은 소멸한다.
- 다른 클라이언트 B가 A와 동시에 요청을 보냈다고 가정하자.
    - 클라이언트 B도 역시 컨트롤러와 서비스에서 각각 myLogger 객체가 필요한데, 이 때 클라이언트 A에게 주입해주었던 빈이 아닌 새로 생성해서 전달한다.
    - 따라서 Request Scope를 활용하면 디버깅하기 쉬운 로그 환경을 만들 수 있다.

## Spring Bean 생명 주기

### Singleton Bean

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/27bb0dee-f4de-4db2-a52b-de0476c1d386/Untitled.png)

1. 스프링 컨테이너 생성
2. 스프링 빈 생성
3. 의존관계 주입
4. 초기화 콜백
5. 사용
6. 소멸 전 콜백
7. 스프링 종료

### Prototype Bean

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/96d527f9-be64-4142-bb87-0856e5dc1a49/Untitled.png)

1. 스프링 컨테이너 생성
2. 스프링 빈 생성
3. 의존 관계 주입
4. 초기화 콜백
5. 사용
6. GC에 의해 수거

## 참고 자료

- [https://steady-coding.tistory.com/594](https://steady-coding.tistory.com/594)