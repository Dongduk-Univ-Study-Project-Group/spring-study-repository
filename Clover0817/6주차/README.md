스프링에서 controller로 이동시 veiw에서 url로 파라미터를 전달할 수 있음.

@PathVariable 과 @RequestParam은 url 파라미터로 전달받은 value를 메서드의 파라미터로 받을 수 있게 해주는 어노테이션

두 방법 중 하나를 택해 사용하거나, 2개를 복합적으로 사용하는 것도 가능

## **@PathVariable**

URI에 변수가 들어가는 경우..

ex) http://localhost:8080/api/user/1234 *1234 -> PathVariable로 처리 가능한 부분

## **사용법**

Controller에서 간단하게 사용 가능

1. @GetMapping(PostMapping, PutMapping 등 상관없음)에 {**변수명**}
2. 메소드 정의에서 위에 쓴 변수명을 그대로 @PathVariable("**변수명**")
3. (Optional) Parameter명은 상관없음- String name, String employName..

```java
@RestController ->스프링에서는 컨트롤러로 사용할 클래스 상단에 @Controller 지정
public class MemberController {
    @GetMapping("/member/{name}")
    public String findByName(@PathVariable("name") String name ) {
        return "Name: " + name;
    }

    @GetMapping("/member/{id}/{name}")
	public String findByNameAndId(@PathVariable("id") String id, @PathVariable("name") String name) {
    	return "ID: " + id + ", name: " + name;
    }

}
```

### **참고)@RequestParam와 @PathVariable 복합 사용법**

코드 실행 시, category = user, name = test가 됨

```jsx
http://www.test.com/user/user?name=test
```

```less
@PostMapping("/user/{category}")
public String save(@PathVariable("category") String category, @RequestParam(value = "name", required = false) String name){
    ....
}
```
