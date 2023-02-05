## 스프링 DB 접근 기술

### 스프링 데이터 액세스
- 순수 Jdbc : 개발자가 직접 쿼리를 모두 작성해야 한다.
- 스프링 JdbcTemplate : 중복을 모두 제거해서 만든 기술 -> 애플리케이션에서 데이터베이스를 편리하게 다룰 수 있음
- JPA : 쿼리를 개발자가 직접 짜지 않고 JPA가 등록, 수정, 삭제, 조회 쿼리를 만들어서 날려준다. 객체를 쿼리없이 바로 DB에 저장할 수 있다. 

<hr/>

### 순수 Jdbc

> 환경 설정

- build.gradle 파일에 jdbc, mysql 데이터베이스 관련 라이브러리 추가
```
dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-jdbc'
	implementation 'mysql:mysql-connector-java:8.0.30'
}
```

- resources/application.properties에 스프링 부트 데이터베이스 연결 설정 추가
```
server.port=8080
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://엔드포인트/DB명?useSSL=false
spring.datasource.username=관리자 계정명
spring.datasource.password=계정 비밀번호 
```

- DataSource는 데이터베이스 커넥션을 획득할 때 사용하는 객체

- 스프링 부트는 데이터베이스 커넥션 정보를 바탕으로 DataSource를 생성 + 스프링 빈으로 만들어둠 + DI 받아올 수 있음

- 스프링 DI (Dependencies Injection)을 사용하면 기존 코드를 수정하지 안혹, 설정마능로 구현 클래스를 변경 가능

<hr/>

### 스프링 JdbcTemplate

- 순수 Jdbc와 동일한 환경설정을 하면 된다.

- 스프링 JdbcTemplate과 MyBatis 같은 라이브러리는 JDBC API에서 본 반복 코드를 대부분 제거해주지만, 쿼리문은 직접 작성해야 한다.

<hr/>

### JPA

- JPA는 기존의 반복 코드는 물론, 기본적인 SQL도 JPA가 직접 만들어서 실행해준다.

- JPA를 사용하면 SQL과 데이터 중심의 설계에서 객체 중심의 설계로 패러다임을 전환할 수 있다.

- JPA를 사용하면 개발 생산성을 크게 높일 수 있다.


> 환경 설정

- build.gradle 파일에 JPA, mysql 데이터 베이스 관련 라이브러리 추가
```
dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'	
	implementation 'mysql:mysql-connector-java:8.0.30'

	testImplementation('org.springframework.boot:spring-boot-starter-test') {
		exclude group: 'org.junit.vintage', module: 'junit-vintage-engine'
	}
}

```
-> spring-boot-starter-data-jpa 는 내부에 jdbc 관련 라이브러리를 포함한다.

- resources/application.properties에 스프링 부트 데이터베이스 연결 설정 추가
```
server.port=8080
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://엔드포인트/DB명?useSSL=false
spring.datasource.username=관리자 계정명
spring.datasource.password=계정 비밀번호 

spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=none
```

- show-sql : JPA가 생성하는 SQL을 출력한다.

- ddl-auto : JPA는 테이블을 자동으로 생성하는 기능을 제공하는데 none을 사용하면 해당 기능을 끈다.
	- create를 사용하면 엔티티 정보를 바탕으로 테이블도 직접 생성해준다.

<hr/>

### Annotation 정리
- @SpringBootTest : 스프링 컨테이너와 테스트를 함께 실행한다.
- @Transactional : 테스트 시작 전에 트랜잭션을 시작하고 완료 후 롤백한다.
