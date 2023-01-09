package com.example.demo.Repository;

import com.example.demo.Domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        // Optional에서 객체 꺼낼 때 get()으로 꺼낼 수 있음
        Member result = null;
        if(repository.findById(member.getId()).isPresent())
            result = repository.findById(member.getId()).get();
        assertThat(member).isEqualTo(result); // member와 result가 같은지 검사
    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("test1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("test2");
        repository.save(member2);

        Member result = repository.findByName("test1").get();
        assertThat(result).isEqualTo(member1);

    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("test1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("test2");
        repository.save(member2);

        List<Member> list = repository.findAll();
        assertThat(list.size()).isEqualTo(3);

    }
    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }
}
