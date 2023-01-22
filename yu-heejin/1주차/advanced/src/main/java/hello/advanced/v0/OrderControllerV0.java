package hello.advanced.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  // @Controller + @ResponseBody 합쳐진 것, 컴포넌트 스캔의 대상
@RequiredArgsConstructor
public class OrderControllerV0 {
    // f2를 누르면 에러가 발생한 위치로 바로 이동할 수 있다.
    private final OrderServiceV0 orderServiceV0;

    @GetMapping("/v0/request")
    public String request(String itemId) {
        // GET 방식이기 때문에 ?itemId= 형식의 파라미터로 값을 넘겨줘야한다.
        orderServiceV0.orderItem(itemId);
        return "ok";
    }
}
