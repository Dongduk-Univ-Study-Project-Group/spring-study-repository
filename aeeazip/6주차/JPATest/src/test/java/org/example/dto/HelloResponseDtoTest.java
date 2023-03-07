package org.example.dto;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class HelloResponseDtoTest {
    @Test
    public void lombokTest(){
        String name = "test";
        int amount = 1000;

        // assertThat : assertj라는 테스트 검증 라이브러리를 사용
        // isEqualTo : assertj의 동등 비교 메소드 + assertThat에 있는 값과 isEqualTo의 값을 비교해서 같을 때만 성공
        HelloResponseDto dto = new HelloResponseDto(name, amount);
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);


    }
}
