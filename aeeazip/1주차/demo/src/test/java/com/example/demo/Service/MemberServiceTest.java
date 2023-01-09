package com.example.demo.Service;

import com.example.demo.Domain.Member;
import com.example.demo.Repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    private MemberService memberService;
    private MemoryMemberRepository repository;

    @BeforeEach
    public void beforeEach(){
        memberService = new MemberService(repository);
        repository = new MemoryMemberRepository();
    }

    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    @Test
    void join() {
        // given
        Member member = new Member();
        member.setName("chaewon");

        // when
        Long saveId = memberService.join(member);

        // then
        Member result = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(result.getName());
    }

    @Test
    public void validateDuplication(){
        // given
        Member m1 = new Member();
        m1.setName("spring");

        Member m2 = new Member();
        m2.setName("spring");

        // when
        memberService.join(m1);

        // then
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(m2)); // 이 로직을 실행할때 IllegealStateException이 발생한다는 의미
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        /*
        try{
            memberService.join(m2); // validate 검사에서 걸린다. 이미 DB에 들어있는 데이터니까
            fail("예외가 발생했습니다.");
        } catch(IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }

         */

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}