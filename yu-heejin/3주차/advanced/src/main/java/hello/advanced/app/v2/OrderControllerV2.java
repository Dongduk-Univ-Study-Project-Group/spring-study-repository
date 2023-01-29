package hello.advanced.app.v2;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV1;
import hello.advanced.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  // @Controller + @ResponseBody 합쳐진 것, 컴포넌트 스캔의 대상
@RequiredArgsConstructor
public class OrderControllerV2 {
    // f2를 누르면 에러가 발생한 위치로 바로 이동할 수 있다.
    private final OrderServiceV2 orderServiceV0;
    // @Component 어노테이션을 갖고 있기 때문에 컴포넌트 스캔의 대상이 되며 스프링 빈으로 등록된다. (싱글톤)
    private final HelloTraceV2 trace;

    @GetMapping("/v2/request")
    public String request(String itemId) {
        // GET 방식이기 때문에 ?itemId= 형식의 파라미터로 값을 넘겨줘야한다.

        // 아래와 같이 코드를 짜면 정상 동작일떄는 로그가 출력되지만, 예외 발생 시 end()가 호출되지 않음
//        TraceStatus status = trace.begin("OrderController.request()");
//        orderServiceV0.orderItem(itemId);
//        trace.end(status);
        // 예외도 처리해야하기 때문에 지저분한 try/catch가 추가된다.
        TraceStatus status = null;
        try {
            // TraceStatus status = trace.begin("OrderController.request()");
            status = trace.begin("OrderController.request()");
            orderServiceV0.orderItem(status.getTraceId(), itemId);
            trace.end(status);
            return "ok";
        } catch (Exception e) {
            // trace.exception(status);    이 경우 status가 블록 안에 있기 때문에 에러 발생
            trace.exception(status, e);
            throw e;     // 예외를 꼭 다시 던져주어야 한다.
            // 예외 로그를 찍었는데 던져주지 않으면 예외를 먹어버리고 정상 흐름으로 동작한다.
            // 애플리케이션의 흐름을 바꿔선 안된다. 예외가 터지면 예외가 나가야한다.
            // 로그 때문에 예외가 사라지면 안된다.
        }
    }
}
