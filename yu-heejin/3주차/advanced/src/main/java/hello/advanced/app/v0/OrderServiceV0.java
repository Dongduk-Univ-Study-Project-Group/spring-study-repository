package hello.advanced.app.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service  // 해당 어노테이션이 있으면 컴포넌트 스캔의 대상이 되며 자동으로 스프링 Bean으로 등록된다.
@RequiredArgsConstructor   // 생성자 자동 생성 (final)
public class OrderServiceV0 {
    private final OrderRepositoryV0 orderRepositoryV0;   // 해당 레포지토리를 사용하려면 의존 관계가 주입되어야한다.

//    @Autowired
//    public orderService(OrderRepositoryV0 orderRepositoryV0) {
//        // @RequiredArgsConstructor 어노테이션이 없으면 다음과 같이 생성자를 만들어줘야한다.
//        this.orderRepositoryV0 = orderRepositoryV0;
//    }
    // 스프링에서 생성자가 하나만 있으면 자동으로 @Autowired가 붙게되어 의존관계 주입이 자동으로 이루어진다.

    public void orderItem(String itemId) {
        orderRepositoryV0.save(itemId);
    }
}
