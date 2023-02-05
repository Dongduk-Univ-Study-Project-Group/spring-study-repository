# Spring Study 2주차

2023.01.22 [정채원] 2주차 스터디 제출합니다. 

## 기본 설정

<img src="https://user-images.githubusercontent.com/97737822/213915140-ccc5478a-5825-448e-8694-7174eff14a30.png" width="900" height="400" />

1. 카카오 developer에서 API Key 발급받기

2. 플랫폼 설정 (Redirect URI 등록)



## 카카오 소셜 로그인

<img src="https://user-images.githubusercontent.com/97737822/213914918-ed8d3106-c75f-4444-8a83-3b84f30e70d5.png" width="900" height="400" />

1. 클라이언트가 로그인

2. 카카오 서버는 Redirect URI로 code를 전달

3. code를 이용해서 Access Token을 발급

4. Access Token을 서버로 전송

5. 서버에서는 받은 Access Token을 이용하여 카카오 서버에서 사용자의 정보를 획득 ( ex. nickname, profile, email )

6. 받은 사용자 정보를 이용하여 회원가입 또는 로그인을 진행

7. 사용자 식별 정보를 클라이언트로 전송
