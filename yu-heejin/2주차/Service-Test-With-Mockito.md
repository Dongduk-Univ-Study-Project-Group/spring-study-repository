## @ExtendWith(MockitoExtension.class)

- @ExtendWith: 확장기능 구현
- Mockito: Mock을 지원하는 오픈 소스 테스트 프레임워크

## @Mock, @InjectMocks, @Spy

```java
@Mock
private ContentRepository contentRepository;

@Spy
private ModelMapper modelMapper;

@InjectMocks
private ContentService contentService;
```

### @Mock

- 가짜 객체, 테스트 Run 시 실제 객체가 아닌 Mock 객체 반환

### @InjectMocks

- Mock 객체가 주입될 클래스
- 자신의 멤버 클래스와 테스트 내에 선언된 @Mock, @Spy 객체가 일치하면 이를 주입한다.

### @Spy

- 실제 인스턴스를 사용한다.
- 행위를 지정하지 않으면 실제 인스턴스의 메소드를 호출한다.

## 참고 자료

- [https://gom20.tistory.com/126](https://gom20.tistory.com/126)
- [https://galid1.tistory.com/772](https://galid1.tistory.com/772)
- [https://thalals.tistory.com/274](https://thalals.tistory.com/274)