package com.proj.togedutch.controller;

import com.proj.togedutch.service.OAuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@AllArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {
    @Autowired
    private OAuthService oAuthService;

    // 카카오 로그인
    @ResponseBody
    @GetMapping("/kakao")
    public int kakaoLogin(@RequestParam String code){
        String accessToken = oAuthService.getKakaoAccessToken(code);
        System.out.println(accessToken);        // accessToken 출력

        HashMap<String, Object> userInfo = oAuthService.getUserInfo(accessToken);
        String email = String.valueOf(userInfo.get("email"));

        /*
            # return 타입 정리 #

            0 : 일반 로그인 페이지로 redirect
            1 : 로그인 완료 (메인 페이지로 이동)
            -1 : 회원가입 페이지로 이동 (회원가입 페이지에 해당 email 값 띄워주기) -> return 값 변경 필요
         */

        if(email == null)  // 카카오 로그인 이메일 동의 선택 안한 경우
            return 0;

        int loginResult = oAuthService.checkUserInfo(email);
        return loginResult;
    }

}