package hello.advanced.app.v2;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service  // 해당 어노테이션이 있으면 컴포넌트 스캔의 대상이 되며 자동으로 스프링 Bean으로 등록된다.
@RequiredArgsConstructor   // 생성자 자동 생성 (final)
public class OrderServiceV2 {
    private final OrderRepositoryV2 orderRepositoryV1;   // 해당 레포지토리를 사용하려면 의존 관계가 주입되어야한다.
    private final HelloTraceV2 trace;

//    @Autowired
//    public orderService(OrderRepositoryV0 orderRepositoryV0) {
//        // @RequiredArgsConstructor 어노테이션이 없으면 다음과 같이 생성자를 만들어줘야한다.
//        this.orderRepositoryV0 = orderRepositoryV0;
//    }
    // 스프링에서 생성자가 하나만 있으면 자동으로 @Autowired가 붙게되어 의존관계 주입이 자동으로 이루어진다.

    public void orderItem(TraceId traceId, String itemId) {

        TraceStatus status = null;
        try {
            // TraceStatus status = trace.begin("OrderController.request()");
            status = trace.beginSync(traceId, "OrderService.orderItem()");
            orderRepositoryV1.save(status.getTraceId(), itemId);
            trace.end(status);
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
