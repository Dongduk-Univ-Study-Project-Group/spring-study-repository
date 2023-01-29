package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor // final 이 사용된 파라미터의 생성자를 자동으로 만들어준다.
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository; // 생성자 주입시 final 사용 가능.
    private final  DiscountPolicy discountPolicy; // DIP를 지킴. (= 인터페이스에만 의존.)
    //private final DiscountPolicy discountPolicy = new FixDiscountPolicy(); - 이전 코드

    @Autowired
    private DiscountPolicy rateDiscountPolicy;

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }


    //    @Autowired - 생성자가 하나일 경우, 자동으로 autowired 됨.
    // new OrderServiceImpl(memberRepository, discountPolicy); - 빈에 등록하면서 자동으로,,

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
