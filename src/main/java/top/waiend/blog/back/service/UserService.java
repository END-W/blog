package top.waiend.blog.back.service;

import top.waiend.blog.back.bean.User;

import java.util.List;

public interface UserService {
    User login(User user, String code, String rightCode);

    void verifyOldPwd(String oldPwd, User user);

    void verifyOldPwd2(String oldPwd, String uid);

    void updateUser(String cAddr, User user);

    int queryCount();

    void changeUser(User user, User user2);

    List<User> list(String context);

    void deleteById(String uid);

    void state(String uid, String state);

    User queryById(String uid);

    void addUser(User user);

    void verifyNickName2(String nickname);

    void verifyNickName(String nickname);

    void verifyNickName3(String nickname);
}
