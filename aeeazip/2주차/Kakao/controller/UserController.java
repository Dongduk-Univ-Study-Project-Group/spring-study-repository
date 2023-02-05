package com.proj.togedutch.controller;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.config.BaseResponseStatus;
import com.proj.togedutch.entity.Keyword;
import com.proj.togedutch.entity.User;
import com.proj.togedutch.service.AWSS3Service;
import com.proj.togedutch.service.EmailService;
import com.proj.togedutch.service.UserService;
import com.proj.togedutch.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.proj.togedutch.utils.ValidationRegex.isRegexEmail;



@RestController
@RequestMapping("/user")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${cloud.aws.url}")
    private String url;

    @Autowired
    UserService userService;
    @Autowired
    JwtService jwtService;
    @Autowired
    AWSS3Service awsS3Service;



    //user-1
    @ResponseBody
    @PostMapping("/signup")
    public BaseResponse<User> createUser(@RequestPart User user, @RequestPart(value="file",required = false)  MultipartFile file) throws IOException {
        if (user.getEmail() == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_USERS_EMPTY_EMAIL);
        }
        if (!isRegexEmail(user.getEmail())){
            return new BaseResponse<>(BaseResponseStatus.POST_USERS_INVALID_EMAIL);
        }
        try {
            String fileUrl = null;
            if(file != null) {
                fileUrl = url + awsS3Service.uploadUserFile(file);
            }
            user.setImage(fileUrl);
            User newUser = userService.createUser(user);
            return new BaseResponse<>(newUser);
        } catch (BaseException e) {
            logger.debug("에러로그 : " + e.getCause());
            System.out.println("에러로그 : " + e.getCause());
            e.printStackTrace();
            return new BaseResponse<>(e.getStatus());
        }
    }

    //user-2
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<User>> getUsers() {
        try {
            List<User> users = userService.getUsers();
            return new BaseResponse<>(users);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }

    //user-3
    @ResponseBody
    @PostMapping("/login")
    public BaseResponse<User> login(@RequestBody User user) {
        try {
            if (user.getEmail() == null || user.getPassword() == null) {
                return new BaseResponse<>(BaseResponseStatus.FAILED_TO_LOGIN);
            }
            User loginUser = userService.login(user);
            return new BaseResponse<>(loginUser);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //user-4
    /*
    @ResponseBody
    @PatchMapping("/{userIdx}")
    public BaseResponse<User> modifyUser(@PathVariable("userIdx") int userIdx, @RequestBody User user) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }
            user.setUserIdx(userIdx);
            User patchUser = userService.modifyUser(user);
            return new BaseResponse<>(patchUser);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }*/
    //TODO user-4 : 비밀번호 변경 : 테스트필요
    @ResponseBody
    @PatchMapping("/{userIdx}/password")
    public BaseResponse<User> modifyPassword(@PathVariable("userIdx") int userIdx, @RequestBody User user) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            System.out.println(userIdx);
            System.out.println(userIdxByJwt);
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }
            User newUser = userService.modifyPassword(userIdx, user.getPassword());
            return new BaseResponse<>(newUser);

        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //user-5
    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<User> getUser(@PathVariable("userIdx") int userIdx) {
        try {
            User getUser = userService.getUser(userIdx);
            return new BaseResponse<>(getUser);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //user-6
    @ResponseBody
    @DeleteMapping("/{userIdx}")
    public BaseResponse<String> deleteUser(@PathVariable("userIdx") int userIdx) {
        try {
            int delete = userService.deleteUser(userIdx);
            if (delete == 1) {
                return new BaseResponse<>("delete success");
            }
            return new BaseResponse<>("delete fail");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }


    //user-7
    @ResponseBody
    @PatchMapping("/{userIdx}/status")
    public BaseResponse<User> modifyStatus(@PathVariable("userIdx") int userIdx, @RequestParam String status) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }
            User user = userService.modifyStatus(userIdx, status);
            return new BaseResponse<>(user);

        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //user-8
    @ResponseBody
    @PatchMapping("/{userIdx}/image")
    public BaseResponse<User> modifyUserImage(@PathVariable("userIdx") int userIdx, @RequestPart MultipartFile file) throws IOException {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }
            String fileUrl = null;
            if(file != null)
                fileUrl = url + awsS3Service.uploadUserFile(file);
            User user = userService.modifyUserImage(userIdx, fileUrl);
            return new BaseResponse<>(user);

        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //TODO user-11
    @ResponseBody
    @PatchMapping("/{userIdx}/phone")
    public BaseResponse<User> modifyPhone(@PathVariable("userIdx") int userIdx, @RequestBody User user) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }
            System.out.println(user.getPhone());
            User newUser = userService.modifyPhone(userIdx, user.getPhone());
            return new BaseResponse<>(newUser);

        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //TODO user-12
    @ResponseBody
    @GetMapping("/findPwd")
    public BaseResponse<String> findPassword(@RequestParam String email) {
        try {
            if (email == null || email.equals("")) {
                return new BaseResponse<>(BaseResponseStatus.POST_USERS_EMPTY_EMAIL);
            }
            if (!isRegexEmail(email)) {
                return new BaseResponse<>(BaseResponseStatus.POST_USERS_INVALID_EMAIL);
            }
            User returnUser = userService.getUserByEmail(email);
            if (userService.sendEmail(returnUser) == 1)
                return new BaseResponse<>("success");
            else
                return new BaseResponse<>("fail");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }


    //keyword-1
    @ResponseBody
    @PostMapping("/keyword")
    public BaseResponse<Keyword> createKeyword(@RequestBody Keyword keyword) {
        try {
            Keyword newKeyword = userService.createKeyword(keyword);
            return new BaseResponse<>(newKeyword);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //keyword-2
    @ResponseBody
    @GetMapping("/{userIdx}/keyword")
    public BaseResponse<Keyword> getKeyword(@PathVariable("userIdx") int userIdx) {
        try {
            Keyword getKeyword = userService.getKeywordByUserIdx(userIdx);
            return new BaseResponse<>(getKeyword);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //keyword-3
    @ResponseBody
    @PatchMapping("/{userIdx}/keyword")
    public BaseResponse<Keyword> modifyKeyword(@PathVariable("userIdx") int userIdx, @RequestBody Keyword keyword) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }
            User user = userService.getUser(userIdx);
            keyword.setKeywordIdx(user.getKeywordIdx());
            Keyword modifyKeyword = userService.modifyKeyword(keyword);
            return new BaseResponse<>(modifyKeyword);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

}
