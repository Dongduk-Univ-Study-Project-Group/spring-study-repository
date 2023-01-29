package hello.core.member;

public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    // private final MemberRepository memberRepository = new MemoryMemberRepository(); // 구현객체 (MemoryMemberRepository) 를 선택하지 않으면 nullpointerexception.
    // 이때, 추상화(MemberRepository)와 구체화(MemoryMemberRepository) 모두에 의존하는 문제점을 가짐. -> 안좋은 코드(DIP를 위반).

    @Override
    public void join(Member member) {
        memberRepository.save(member); // save 호출시 다형성에 의해 MemoryMemberRepository 내의 save 호출.
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
