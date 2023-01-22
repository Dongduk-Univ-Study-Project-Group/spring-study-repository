## BDD

- BDD란 Danial Terhorst-North와 Charis Matts TDD에서 착안한 방법론으로 행위 주도 개발(Behavior-Driven Development)를 말한다.
- 테스트 대상의 상태의 변화를 시나리오를 기반(Narrative)으로 테스트하는 패턴을 주로 사용한다.
    - 이 때 권장하는 행동 패턴은 Given, When, Then 구조이다.
    - 어떤 상태에서(Given) 어떤 행동을 했을 때(When) 어떤 결과가 되는지(Then)를 테스트한다.

## BDDMockito

- 다양한 테스트 코드를 보면 Mockito를 사용할 때 when().thenReturn()와 given().thenReturn() 패턴이 있다.
    - 전자의 경우는 org.mockito.Mockito가 제공하는 기능이고, 후자는 org.mockito.BddMockito가 제공하는 기능이다.
- BDDMockito는 BDD를 사용해서 테스트 코드 작성 시 시나리오에 맞게 테스트 코드가 읽히도록 개선된 Mockito 프레임워크이다.
- BDDMockito는 Mockito를 상속하고 있고 기능도 동일하다.
    - 다만, BDD 구조로 쉽게 읽힐 수 있도록 도와준다.

## 기존 코드 (Mockito)

```java
@ExtendWith(MockitoExtension.class)
public class StudyServiceTest {

    @Mock
    MemberService memberService;

    @Mock
    StudyRepository studyRepository;

    @Test
    void createStudyService() {
        // Given
        StudyService studyService = new StudyService(memberService, studyRepository);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("keesun@gmail.com");

        Study study = new Study(10, "테스트");

        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);
        
        // When
        studyService.createNewStudy(1L, study);
        
        // Then
        assertEquals(member, study.getOwner());
        verify(memberService, times(1)).notify(study);
        verifyNoMoreInteractions(memberService);
    }
}
```

- Mocking을 하는 것은 행위를 하는 When이 아니라 상태를 설정하는 Given에 해당한다.
- 하지만 다음 코드를 살펴보자.
    
    ```java
    when(memberService.findById(1L)).thenReturn(Optional.of(member));
    when(studyRepository.save(study)).thenReturn(study);
    ```
    
    - Given에 속해야하는 해당 부분은 객체의 동작을 Mocking하는데, 메서드명이 when이다.
    - 이어서 thenReturn도 사용하기 때문에 개발자가 보기에 테스트코드에 혼란을 조성한다.

## BDDMockito를 사용한 코드

- BDDMockito API를 사용하면 위 문제를 아래 코드 두줄로 변경할 수 있다.
    
    ```java
    given(memberService.findById(1L)).willReturn(Optional.of(member));
    given(studyRepository.save(study)).willReturn(study);
    ```
    
- 추가적으로 위에서 Then 절의 끝자락에 있는 verity 메서드도 then().should() 메서드로 작성할 수 있다.
    
    ```java
    then(memberService).should(times(1)).notify(study);
    then(memberService).shouldHaveNoMoreInteractions();
    ```
    
- 전체 코드는 다음과 같다.
    
    ```java
    @Test
    void createNewStudy_shouldSameStudy() {
        // Given
        StudyService studyService = new StudyService(memberService, studyRepository);
    
        Member member = new Member();
        member.setId(1L);
        member.setEmail("keesun@gmail.com");
    
        Study study = new Study(10, "테스트");
    
        given(memberService.findById(1L)).willReturn(Optional.of(member));
        given(studyRepository.save(study)).willReturn(study);
    
        // When
        studyService.createNewStudy(1L, study);
    
        // Then
        assertEquals(member, study.getOwner());
        then(memberService).should(times(1)).notify(study);
        then(memberService).shouldHaveNoMoreInteractions();
    }
    ```
    

## 참고 자료

- [https://jaehoney.tistory.com/220](https://jaehoney.tistory.com/220)