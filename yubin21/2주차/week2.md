## Spring Study 2주차

### 인텔리제이 단축키 (복습)
커멘드+옵션+v  
컨트롤+쉬프트+r : 테스트 실행  
커멘드+o : 전체 검색  
커멘드+e : 히스토리
커멘드+[ : 이전 파일  
커멘드+/ : 해당 줄 주석처리  
`iter`+tab 으로 for문 생성  

### 배운내용
#### 1. 객체 지향 설계와 스프링  
: 스프링의 역사 그리고 스프링 프레임워크에 대해  

객체 지향의 특징
- 추상화  
- 캡슐화  
- 상속  
- **다형성(중요)** - **역할**과 **구현**으로 세상을 구분

#### 2. 스프링 핵심 원리 이해1 - 예제 만들기
비즈니스 요구사항과 설계  
: 회원 도메인의 설계 및 개발 / 주문과 할인 도메인 설계 및 개발 

##### 3. 스프링 핵심 원리 이해2 - 객체 지향 원리 적용  (단순 자바 코드에서 Spring으로 변경.)
할인 정책의 확장  
: 할인 정책이 변경되었을 때 이를 구현하고, OCP와 DIP를 지킨 코드로 변경하기. 

Appconfig를 통한 관심사의 분리  
: 애플리케이션의 전체 동작 방식을 구성(config)하기 위해, 구현 객체를 생성하고, 연결하는 책임을 갖는 AppConfig의 등장.  

AppConfig 리팩터링   
- 구성 정보에서 역할과 구현을 명확하게 분리 
- 역할이 잘 드러남 
- 중복 제거

좋은 객체 지향 설계의 5가지 원칙의 적용
: 5가지 중 SRP, DIP, OCP을 적용.

#### 4. 스프링 컨테이너와 스프링 빈
스프링 컨테이너 생성  
: 스프링 컨테이너를 생성하고, 스프링 빈을 등록하여 이에 대한 의존관계를 설정한다.

컨테이너에 등록된 모든 빈 조회  
: 모든 빈 추력 및 애플리케이션 빈을 출력한다.

스프링 빈 조회  
: 기본/동일한 타입이 둘 이상/상속 관계 의 세가지 경우에서 스프링 빈을 조회한다.

BeanFactory와 ApplicationContext
- ApplicationContext는 BeanFactory의 기능을 상속받는다.
- ApplicationContext는 빈 관리기능 + 편리한 부가 기능을 제공한다.
- BeanFactory를 직접 사용할 일은 거의 없다. 부가기능이 포함된 ApplicationContext를 사용한다. 
- BeanFactory나 ApplicationContext를 스프링 컨테이너라 한다.