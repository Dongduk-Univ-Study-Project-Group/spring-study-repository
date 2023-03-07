package org.example.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

/*
@RunWith(SpringRunner.class)
* 테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자를 실행
* 여기서는 SpringRunner라는 스프링 실행자를 사용
* 스프링 부트 테스트와 JUnit 사이에 연결자 역할

@WebMvcTest(controllers = HelloController.class)
* Web에 집중할 수 있는 Annotation
* 선언할 경우 @Controller, @ControllerAdvice 사용 가능
* @Service, @Repository등은 사용 불가능
*/

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {
    @Autowired  // 스프링이 관리하는 빈 주입 받기
    private MockMvc mvc;    // 웹 API 테스트할 때 사용

    @Test
    public void hello() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))              // hello 주소로 GET 요청
                .andExpect(status().isOk())               // mvc.perform 결과를 검증 + HTTP Header의 status 검증(200인지!)
                .andExpect(content().string(hello));      // Controller에서 "hello" 리턴하기 때문에 값이 맞는지 검증
    }

    @Test
    public void retHelloResponseDto() throws Exception {
        String name = "hello";
        int amount = 1000;


        mvc.perform(get("/hello/dto")
                        .param("name", name)    // 값은 String만 허용 (숫자, 날짜도 등록 시 문자열로 타입 캐스팅 필요)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))    // jsonPath : json 응답값을 필드별로 검증 + $를 기준으로 필드명 명시
                .andExpect(jsonPath("$.amount", is(amount)));

    }

}
