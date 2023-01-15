### Spring Study 1주차

(이번주는 여행 일정으로 인해 많은 내용을 학습하기 어려워, 실습 강의는 다음주부터 진행하고 이번주에는 가볍게 정의에 대해 다시한번 정리하고, 프로젝트 기본 세팅을 진행하였습니다.)

## 스프링과 스프링부트(Spring Boot)
> 스프링: 자바 엔터프라이즈 개발을 편하게 해주는 오픈소스 경량급 애플리케이션 프레임워크 

 
> 스프링부트: 스프링으로 애플리케이션을 만들 때에 필요한 설정을 간편하게 처리해주는 프레임워크

 
### 1. 스프링의 특징
1. POJO 프로그래밍을 지향
- POJO란, Plain Old Java Object, 즉 순수 Java만을 통해서 생성한 객체를 의미. 
- 순수 Java만을 사용한다는 것은 Java 및 Java의 스펙에 정의된 기술만 사용한다는 의미로서, 어떤 객체가 외부의 라이브러리나 외부의 모듈을 가져와서 사용하고 있다면, 그 객체는 POJO라고 할 수 없다. (POJO는 다른 기술을 사용하지 않는 순수한 Java만을 사용하여 만든 객체)

``` java
// 필드와 Getter, Setter만 존재하는 기본적인 POJO의 예시 
public class Person {

    private String name;
    private int age;

    ublic String getName() {
    return name;
    }

    public String getAge() {
    return age;
    }

    public void setName(String name) { 
        this.name = name;
    }

    public void setAge (int age) {
    this.age age;
    }
}

```
2. IOC (제어의 역전)
- 프로그램의 생명주기에 대한 제어권이 웹 애플리케이션 컨테이너에 있는 형태이다.
- 사용자가 직접 new 연산자를 통해 인스턴스를 생성하고 메소드를 호출하는 일련의 생명주기에 대한 작업들을 스프링에 위임할 수 있게 된다.
- IOC는 직관적이지 못하므로 IOC를 구현하기 위해 의존성 주입(DI)이 존재한다.

3. DI (의존성 주입)
- 외부에서 두 객체 간의 관계를 결정해주는 디자인 패턴으로, 인터페이스를 사이에 둬서 클래스 레벨에서는 의존관계가 고정되지 않도록 하고 런타임 시에 관게를 동적으로 주입하여 유연성을 확보하고 결합도를 낮출 수 있게 해준다.
``` java
// 생성자를 통한 의존성 주입의 예시
@Component
public class OrderServiceImpl implements OrderService {

 	private final MemberRepository memberRepository
	private final DiscountPolicy discountPolicy;
 
 @Autowired
 public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy 
	discountPolicy) {
            
	this.memberRepository = memberRepository;
	this.discountPolicy = discountPolicy;
    
 	}
}
```

4. AOP (관점지향 프로그래밍)
- 기존 프로젝트에 로깅이나 보안을 설정할때 기존 로직을 수정하지 않고 AOP를 활용하여 독자적으로 추가할 수 있다.
- 분별하게 중복되는 코드를 한 곳에 모아 중복 되는 코드를 제거할 수 있다
- 공통기능을 한 곳에 보관함으로써 공통 기능 하나의 수정으로 모든 핵심기능들의 공통기능을 수정할 수 있다. -> 효율적인 유지보수가 가능하며 재활용성이 극대화된다.

### 2. 스프링부트의 특징
1. 자동화 지원 
- 자주 사용되는 라이브러리들의 버전 관리 자동화를 포함한 스프링 프레임워크 관련 설정의 많은 부분을 자동화 해준다.

2. 내장 웹서버 제공
- 스프링 부트는 내장 웹서버(톰캣 or 리액터 네티)을 가지고 있어서 별도의 작업 없이 빠르게 서버를 실행할 수 있도록 도와준다.

3. 실행 가능한 JAR로 개발 가능
- 스프링 부트는 순수 자바 애플리케이션 프로그램을 실행하는 것처럼 스프링부터 역시 jar로 실행 가능하도록 하여, 애플리케이션 실행에 상당한 편리성을 제공해주고 있다.

### 3. 차이점
- 스프링부트 내부에 톰캣이 포함되어있어 따로 설치하거나 매번 버전을 관리해주어야 하는 수고로움을 덜어준다.
- 스프링에서는 의존성들의 호환되는 버전을 일일이 맞추어야 했지만 spring.boot.starter가 대부분의 dependency를 관리해주기 때문에 스프링 부트를 사용하는 경우, 더 편리하다.
- xml을 설정하지 않아도 된다는 차이가 있다.

결론적으로, 스프링의 확장버전이 스프링부트이며 스프링부트는 개발자가 환경설정에 치우치지 않고 개발에만 집중할 수 있도록 도와준다. 

### 출처
[MangKyu's Diary:티스토리](https://mangkyu.tistory.com/150)

[Spring Framework란? 기본 개념 핵심 정리](https://khj93.tistory.com/entry/Spring-Spring-Framework%EB%9E%80-%EA%B8%B0%EB%B3%B8-%EA%B0%9C%EB%85%90-%ED%95%B5%EC%8B%AC-%EC%A0%95%EB%A6%AC)

[스프링 AOP (Spring AOP) 총정리](https://engkimbs.tistory.com/746)

[스프링 정리](https://leveloper.tistory.com/33)

[[Spring] 의존성 주입(Dependency Injection)이란? (개념/ 예제/ 총 정리)](https://jeongkyun-it.tistory.com/172)

[스프링과 스프링부트(Spring Boot)ㅣ정의, 특징, 사용 이유, 생성 방법](https://www.codestates.com/blog/content/%EC%8A%A4%ED%94%84%EB%A7%81-%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8)

