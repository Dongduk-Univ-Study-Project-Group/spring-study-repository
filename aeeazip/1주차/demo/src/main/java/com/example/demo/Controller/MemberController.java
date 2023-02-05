package com.example.demo.Controller;

import com.example.demo.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {
    private final MemberService memberService;

    // Autowired : MemberController가 스프링 컨테이너가 뜰 때 생성 -> 이때, 생성자 호출
    // MemberService를 스프링이 가져다가 연결 시켜준다. (이게 의존성 주입!!!!!!!!!!!!!!)
    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }
}
