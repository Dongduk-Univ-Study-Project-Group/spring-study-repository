## 스프링 DB 접근 기술 JPA

### JPA

자바 진영의 ORM 기술 표준으로 애플리케이션과 JDBC 사이에서 동작한다. 

<img src="https://user-images.githubusercontent.com/97737822/218313587-eb806f99-1d5d-41b5-8217-2a83760ee078.png" width="700" height="300" />

> 동작원리
1. 개발자가 JPA에게 명령한다.
2. JPA가 JDBC API 사용해서 SQL 호출한다.
3. 결과를 받아서 동작한다. 




### JPA 장점

> 생산성

INSERT 쿼리를 작성하는 반복적인 작업을 JPA가 대신 처리해준다. 
데이터베이스 설계 중심의 패러다임을 객체 설계 중심으로 역전시킬 수 있다.


> 유지보수

엔티티에 필드가 추가될때마다 대응해야하는데 등록/수정/조회 쿼리를 수정할 필요 없이 JPA가 모두 대신 처리해준다.
유지보수해야하는 코드 수가 줄어든다.


>패러다임의 불일치 해결

상속, 연관관계, 객체 그래프 탐색, 비교하기와 같은 패러다임의 불일치 문제를 해결해준다.


> 성능

애플리케이션과 데이터베이스 사이에서 다양한 성능 최적화 기회를 제공한다. 
JPA는 애플리케이션과 데이터베이스 사이에서 동작한다.


> 데이터 접근 추상화와 벤더 독립성

데이터베이스의 종류에 구애받지 않기 때문에 기술에 종속되지 않을 수 있다. 
데이터베이스를 변경하게되면 JPA에게 다른 데이터베이스를 사용한다고 알려주기만 하면 된다.




### JPA 명명 규칙

- findAll()
	- DB에서 전체 값을 list로 불러올때 사용한다. 

- findOne()
	- 기본키로 값을 1건 조회할 때 사용한다. 

- findByOOO
	- findBy 뒤에 우리가 정의한 Entity 이름을 붙이면 된다.
	- Entity 이름 첫글자는 대무자로 하고, id를 조건으로 검색하면 findById(int id)로 검색하면 된다.
	- and 조건 : findByIdAndName(int id, String name)으로 And를 사용하여 검색한다. 
	- or 조건 : findByIdOrName(int id, String name)으로 Or를 사용하여 검색한다.

- Like / Not Like
	- like를 붙이면 인수에 지정된 텍스트를 포함하는 Entity를 검색한다. (SQL Like 연산자 역할과 동일)
	- NotLike는 반대로 지정된 텍스트를 포함하지 않는 엔티티를 검색한다.
	- ex) findByNameLike : name에서 인수의 텍스트를 검색한다. 

- StartingWith / EndingWith
	- 값에서 지정된 텍스트로 시작하거나, 끝나는 값을 검색한다. 
	- findByNameStartingWith("chaewon")이라면 chaewon으로 시작하는 이름을 검색한다. 
	- findByNameEndingWith("chaewon")이라면 chaewon으로 끝나는 이름을 검색한다. 

- IsNull / IsNotNull
	- 값이 null이거나 null이 아닌 것을 검색한다. 
	- ex) findByNameIsNull() : name이 null인 것을 검색한다. 

- True / False
	- Boolean값으로 true/false인 것을 검색한다. 
	- ex) findByOptionTrue() : option가 true인 것을 검색, 반대는 False()

- Before / After
	- 시간 기준으로 값을 검색한다 .
	- ex) findByCreatedDateBefore(new Date()) : createDate가 현재보다 이전인 값을 검색한다.

- LessThan / GreaterThan
	- 숫자(값)를 기준으로 더 작은, 큰 값을 검색한다. 
	- ex) findByCntLessThan(20) : cnt가 20보다 작은 것을 검색한다.

- Between 
	- 두 숫자(값)의 사이에 있는 값을 기준으로 검색한다. 
	- 시간도 사용 가능하다. 
	- ex) findByCntBetween(0, 20) : 0-20 사이에 있는 Cnt를 검색한다. 

- OrderBy
	- 메소드명에 OrderBy+검색할 Entity+ASC/DESC를 사용한다.
	- ex) findByAddressOrderByCnt(String address) : 주소를 기준으로 순서대로 cnt 결과를 가져온다. 

- countBy
	- 결과의 count를 리턴한다. 

- save
	- 레코드를 저장할 때 사용한다. 
	- insert(), update() 기능을 한다. 

- delete
	- 레코드를 삭제할 때 사용한다. 
