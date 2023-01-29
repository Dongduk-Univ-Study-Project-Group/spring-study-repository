package hello.advanced.app.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository   // 해당 어노테이션이 있으면 컴포넌트 스캔의 대상이 된다. -> 스프링 빈으로 자동 등록된다.
@RequiredArgsConstructor
public class OrderRepositoryV0 {
    public void save(String itemId) {
        // 저장 로직
        if (itemId.equals("ex")) {
            throw new IllegalStateException("예외 발생!");
        }

        sleep(1000);   // 상품을 주문하는데 1초 (1000ms) 정도의 시간을 준다고 가정
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
