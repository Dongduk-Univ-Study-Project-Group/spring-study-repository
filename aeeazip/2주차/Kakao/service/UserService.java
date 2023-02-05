package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.secret.Secret;
import com.proj.togedutch.dao.UserDao;
import com.proj.togedutch.entity.EmailMessage;
import com.proj.togedutch.entity.Keyword;
import com.proj.togedutch.entity.User;
import com.proj.togedutch.utils.AES128;
import com.proj.togedutch.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.proj.togedutch.config.BaseResponseStatus.*;


@Service
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserDao userDao;
    @Autowired
    JwtService jwtService;
    @Autowired
    EmailService emailService;

    //user 만들기
    public User createUser(User user) throws BaseException{
        if (checkEmail(user.getEmail()) == 1) {
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }
        String pwd;
        try {
            AES128 aes128 = new AES128(Secret.USER_INFO_PASSWORD_KEY);
            pwd = aes128.encrypt(user.getPassword());
            user.setPassword(pwd);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try {
            int userIdx = userDao.createUser(user);
            logger.debug(String.valueOf(user.getUserIdx()));
            System.out.println(userIdx);
            String jwt = jwtService.createJwt(userIdx);
            User createUser = getUser(userIdx);
            System.out.println(jwt);
            createUser.setJwt(jwt);
            return createUser;
        } catch (Exception e) {
            logger.debug("에러로그S : " + e.getCause());
            System.out.println("에러로그S : " + e.getCause());
            System.out.println("에러로그S : " + e.getMessage());
            e.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public int checkEmail(String email) {
        return userDao.checkEmail(email);
    }
    //user 조회(id)
    public User getUser(int userIdx) throws BaseException{
        try {
            User user = userDao.getUser(userIdx);
            return user;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    //키워드 생성
    public Keyword createKeyword(Keyword keyword) throws BaseException {
        try {
            logger.debug(keyword.getWord1());
            int keywordIdx = userDao.createKeyword(keyword);
            Keyword createKeyword = getKeyword(keywordIdx);
            return createKeyword;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }

    }
    //키워드 조회(키워드 id)
    public Keyword getKeyword(int keywordIdx) throws BaseException{
        try {
            return userDao.getKeyword(keywordIdx);
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public Keyword getKeywordByUserIdx(int userIdx) throws BaseException {
        try {
            User user = userDao.getUser(userIdx);
            return userDao.getKeyword(user.getKeywordIdx());
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public List<User> getUsers() throws BaseException {
        try {
            List<User> users = userDao.getUsers();
            return users;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public User login(User user) throws BaseException {
        User getUser = userDao.getPwd(user);
        String password;
        try {
            password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(getUser.getPassword());
            System.out.println(user.getPassword());
            System.out.println(password);
        } catch (Exception e) {
            System.out.println(e.getCause());
            e.printStackTrace();
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }
        if (user.getPassword().equals(password)) {
            System.out.println("login : " + getUser.getUserIdx());
            int userIdx = getUser.getUserIdx();
            String jwt = jwtService.createJwt(userIdx);
            getUser.setJwt(jwt);
            System.out.println(jwt);
            return getUser;
        } else {
            throw new BaseException(FAILED_TO_LOGIN);
        }
    }

    public User modifyUser(User user) throws BaseException {
        try {
            User patchUser = userDao.modifyUser(user);
            return patchUser;
        } catch (Exception e) {
            throw new BaseException(MODIFY_FAIL_USER);
        }
    }

    public User modifyPassword(int userIdx, String password) throws BaseException {
        String pwd;
        try {
            AES128 aes128 = new AES128(Secret.USER_INFO_PASSWORD_KEY);
            pwd = aes128.encrypt(password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try {
            if (pwd != null) {
                return userDao.modifyPassword(userIdx, pwd);
            }
            else {
                throw new BaseException(USERS_EMPTY_PASSWORD);
            }
        } catch (Exception e) {
            throw new BaseException(MODIFY_FAIL_USER);
        }
    }

    public Keyword modifyKeyword(Keyword keyword) throws BaseException {
        try {
            Keyword patchKeyword = userDao.modifyKeyword(keyword);
            return patchKeyword;
        } catch (Exception e) {
            throw new BaseException(MODIFY_FAIL_USER);
        }
    }

    public int deleteUser(int userIdx) throws BaseException {
        try {
            return userDao.deleteUser(userIdx);
        } catch (Exception e) {
            throw new BaseException(DELETE_FAIL_USER);
        }
    }

    public User modifyStatus(int userIdx, String status) throws BaseException {
        try {
            return userDao.modifyStatus(userIdx, status);
        } catch (Exception e) {
            throw new BaseException(MODIFY_FAIL_USER);
        }
    }

    public User modifyUserImage(int userIdx, String fileUrl) throws BaseException {
        try {
            return userDao.modifyUserImage(userIdx, fileUrl);
        } catch (Exception e) {
            throw new BaseException(MODIFY_FAIL_USER);
        }
    }

    public User modifyPhone(int userIdx, String phone) throws BaseException {
        try {
            return userDao.modifyPhone(userIdx, phone);
        } catch (Exception e) {
            throw new BaseException(MODIFY_FAIL_USER);
        }
    }

    public User getUserByEmail(String email) throws BaseException {
        try {
            return userDao.getUserByEmail(email);
        } catch (Exception e) {
            throw new BaseException(FAILED_TO_FIND_USER);
        }
    }

    //TODO 여기서부터 작성 230122
    public int sendEmail(User user) throws BaseException{
        try {
            String password;
            try {
                password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(user.getPassword());
                System.out.println(user.getPassword());
                System.out.println(password);
            } catch (Exception e) {
                System.out.println(e.getCause());
                e.printStackTrace();
                throw new BaseException(PASSWORD_DECRYPTION_ERROR);
            }
            EmailMessage emailMessage = EmailMessage.builder()
                    .to(user.getEmail())
                    .subject("[가치더치] 회원님의 비밀번호 안내드립니다.")
                    .message("회원님의 비밀번호는 " + password + "입니다.")
                    .build();
            if (emailService.sendMail(emailMessage) == 1)
                return 1;
            return 0;
        } catch (Exception e) {
            throw new BaseException(null);
        }
    }
}
