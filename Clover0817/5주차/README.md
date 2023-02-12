1. 트랜잭션의 성질<br>
-원자성(Atomicity): 한 트랜잭션 내에서 실행한 작업들은 하나로 간주. 즉, 모두 성공 또는 모두 실패.<br>
-일관성(Consistency): 트랜잭션은 일관성 있는 데이타베이스 상태를 유지(data integrity 만족 등)<br>
-격리성(Isolation): 동시에 실행되는 트랜잭션들이 서로 영향을 미치지 않도록 격리해야<br>
-지속성(Durability): 트랜잭션을 성공적으로 마치면 결과가 항상 저장되어야<br><br>
2. 스프링에서 트랜잭션 처리 방법<br>
스프링에서는 트랜잭션 처리를 지원하는데,
그중 어노테이션 방식으로 @Transactional을 선언하여 사용하는 방법이 일반적. 선언적 트랜잭션이라 부름.
클래스, 메서드위에 @Transactional 이 추가되면, 
이 클래스에 트랜잭션 기능이 적용된 프록시 객체가 생성<br><br>
3. 트랜잭션 설정<br><br>
@Transactional 설정 방식<br>
-xml 방식<br>
-JavaConfig 방식<br><code>
@EnableTransactionManagement<br>
public class AppConfig {<br>
    ...<br>
    @Bean<br>
    public PlatformTransactionManager transactionManager() throws URISyntaxException, GeneralSecurityException, ParseException, IOException {<br>
        return new DataSourceTransactionManager(dataSource());<br>
    }</code><br>
-기본 적용 방식<br><code>
@Transactional<br>
public boolean insertUser(User user){<br>
...<br>
}</code><br><br>
4. 다수의 트랜잭션이 경쟁시 발생할 수 있는 문제<br>
다수의 트랜잭션이 동시에 실행되는 상황에선 트랜잭션 처리방식을 좀 더 고려해야<br>
ex) 특정 트랜잭션이 처리중이고 아직 커밋되지 않았는데 다른 트랜잭션이 그 레코드에 접근한 경우 <br><br>
▶ Problem1 - Dirty Read<br>
트랜잭션 A가 어떤 값을 1에서 2로 변경하고 아직 커밋하지 않은 상황에서 트랜잭션B가 같은 값을 읽는 경우→ 트랜잭션 B는 2가 조회 된다.<br>
트랜잭션 B가 2를 조회 한 후 A가 롤백된다면 결국 트랜잭션B는 잘못된 값을 읽게 된 것. <br>
  즉, 아직 트랜잭션이 완료되지 않은 상황에서 데이터에 접근을 허용할 경우 발생할 수 있는 데이터 불일치<br>
▶ Problem2 - Non-Repeatable Read<br>
트랜잭션 A가 어떤 값 1을 읽고, 이후 A는 같은 쿼리를 또 실행할 예정,<br>
그 사이에 트랜잭션 B가 값 1을 2로 바꾸고 커밋해버리면 A가 같은 쿼리 두번을 날리는 사이 두 쿼리의 결과가 다르게 되어 버림<br>
즉, 한 트랜잭션에서 같은 쿼리를 두번 실행했을 때 발생할 수 있는 데이터 불일치<br>
(Dirty Read에 비해서는 발생 확률이 적다.)<br>
▶ Problem3 - Phantom Read<br>
트랜잭션 A가 어떤 조건을 사용하여 특정 범위의 값들[0,1,2,3,4]을 읽었다.<br>
이후 A는 같은 쿼리를 실행할 예정, <br>
그 사이에 트랜잭션 B가 같은 테이블에 값[5,6,7]을 추가해버리면 <br>
A가 같은 쿼리 두번을 날리는 사이 두 쿼리의 결과가 다르게 되어 버림<br>
즉, 한 트랜잭션에서 일정 범위의 레코드를 두번 이상 읽을 때 발생하는 데이터 불일치<br><br>
5. 스프링 트랜잭션 속성<br>
위와 같은 상황 (다수의 트랜잭션이 경쟁시) 을 방지할 수 있는 속성 등이 존재<br><br>

1|isolation (격리수준)<br>
-트랜잭션에서 일관성이 없는 데이터를 허용하도록 하는 수준을 말한다.<br>
-DEFAULT: 기본 격리 수준(기본설정, DB의 Isolation Level을 따름)<br>
-READ_UNCOMMITTED (level 0): 커밋되지 않는(트랜잭션 처리중인) 데이터에 대한 읽기를 허용. 즉 어떤 사용자가 A라는 데이터를 B라는 데이터로 변경하는 동안 다른 사용자는 B라는 아직 완료되지 않은(Uncommitted 혹은 Dirty) 데이터 B를 읽을 수 있음<br>
-READ_COMMITTED (level 1): 트랜잭션이 커밋 된 확정 데이터만 읽기 허용. 어떠한 사용자가 A라는 데이터를 B라는 데이터로 변경하는 동안 다른 사용자는 해당 데이터에 접근할 수 없음<br>
-REPEATABLE_READ (level 2): 트잭션이 완료될 때까지 SELECT 문장이 사용하는 모든 데이터에 shared lock이 걸리므로 다른 사용자는 그 영역에 해당되는 데이터에 대한 수정이 불가능. 선행 트랜잭션이 읽은 데이터는 트랜잭션이 종료될 때까지 후행 트랜잭션이 갱신하거나 삭제가 불가능 하기때문에 같은 데이터를 두 번 쿼리했을 때 일관성 있는 결과를 리턴<br>
-SERIALIZABLE (level 3): 데이터의 일관성 및 동시성을 위해 MVCC(Multi Version Concurrency Control)을 사용하지 않음. 트랜잭션이 완료될 때까지 SELECT 문장이 사용하는 모든 데이터에 shared lock이 걸리므로 다른 사용자는 그 영역에 해당되는 데이터에 대한 수정 및 입력이 불가능<br>
(MVCC는 다중 사용자 데이터베이스 성능을 위한 기술로 데이터 조회 시 LOCK을 사용하지 않고 데이터의 버전을 관리해 데이터의 일관성 및 동시성을 높이는 기술)<br>
<br>
2|propagation (전파옵션)<br>
트랜잭션 동작 도중 다른 트랜잭션을 호출(실행)하는 상황에 선택할 수 있는 옵션.<br>
@Transactional의 propagation 속성을 통해 피호출 트랜잭션의 입장에서는 호출한 쪽의 트랜잭션을 그대로 사용할 수도 있고, 새롭게 트랜잭션을 생성할 수도 있다.<br>
<br>
3|Readonly 속성<br>
- 트랜잭션을 읽기 전용으로 설정.<br>
- 성능을 최적화하기 위해 사용할 수도 있고 특정 트랜잭션 작업 안에서 쓰기 작업이 일어나는 것을 의도적으로 방지하기 위해 사용할 수도 있음.<br>
- 일부 트랜잭션 매니저의 경우 읽기전용 속성을 무시하고 쓰기 작업을 허용할 수도 있기 때문에 주의해야<br>
- 일반적으로 읽기 전용 트랜잭션이 시작된 이후 INSERT, UPDATE, DELETE 같은 쓰기 작업이 진행되면 예외가 발생<br>
- aop/tx 스키마로 트랜잭션 선언을 할 때는 이름 패턴을 이용해 읽기 전용 속성으로 만드는 경우가 많다. 보통 get이나 find 같은 이름의 메소드를 모두 읽기전용으로 만들어 사용하면 편리<br>
- @Transactional 의 경우는 각 메소드에 일일이 읽기 전용 지정을 해줘야<br>
read-only attribute 또는 readOnly element로 지정<br>
true인 경우 insert, update, delete 실행 시 예외 발생(기본 설정은 false)<br>
ex) @Transactional(readOnly = true)<br>
<br>
4|트랜잭션 롤백 예외(rollback-for, rollbackFor, rollbackForClassName) <br>
- 선언적 트랜잭션에서는 런타임 예외가 발생하면 롤백<br>
- 반면에 예외가 전혀 발생하지 않거나 체크 예외가 발생하면 커밋.<br>
체크 예외를 커밋 대상으로 삼은 이유는 체크 예외가 예외적인 상황에서 사용되기 보단,<br>
리턴 값을 대신해서 비즈니스적인 의미를 담은 결과를 돌려주는 용도로 많이 사용되기 때문<br>
- 스프링에서는 데이터 액세스 기술의 예외는 런타임 예외로 전환돼서 던져지므로 런타임 예외만 롤백 대상으로 삼은 것<br>
- 하지만 기본 동작방식을 바꿀 수도<br><br>
ex ) 체크 예외지만 롤백 대상으로 삼아야 하는 것이 있다면 XML의 rolback-for 애트리뷰트나 애노테이션의 rollbackFor 또는 rollbackForClassName 앨리먼트를 이용해서 예외를 지정하면 됨<br>
rollback-for 나 rollbackForClassName 은 예외 이름을 넣으면 되고, rollbackFor 는 예외 클래스를 직접 넣기<br>
- @Transactional 에서는 다음과 같이 클래스 이름 대신 클래스를 직접 사용해도 됨<br>
ex) @Transactional(readOnly=true, rollbackFor=NoSuchMemberException.class)<br>
<br>
5|timeout 속성<br>
지정한 시간 내에 해당 메소드 수행이 완료되이 않은 경우 rollback 수행.<br>
-1일 경우 no timeout(Default = -1)<br>
ex) @Transactional(timeout=10)<br>
