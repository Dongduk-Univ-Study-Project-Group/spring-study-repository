package hello.advanced.app.v1;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository   // 해당 어노테이션이 있으면 컴포넌트 스캔의 대상이 된다. -> 스프링 빈으로 자동 등록된다.
@RequiredArgsConstructor   // 없으면 불편하다.
public class OrderRepositoryV1 {
    private final HelloTraceV1 trace;
    public void save(String itemId) {
        TraceStatus status = null;
        try {
            // TraceStatus status = trace.begin("OrderController.request()");
            status = trace.begin("OrderRepository.save()");
            if (itemId.equals("ex")) {
                throw new IllegalStateException("예외 발생!");
            }

            sleep(1000);   // 상품을 주문하는데 1초 (1000ms) 정도의 시간을 준다고 가정

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

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
