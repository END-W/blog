package top.waiend.blog.back.service;

import top.waiend.blog.back.bean.Login;
import top.waiend.blog.back.bean.User;

import java.util.List;

public interface LoginService {
    void insertLoginLog(User user);

    List<Login> queryLoginLog(User user);

    List<Login> list(String context);

    void deleteById(String pid);

    void deleteAll();

    void deleteCurrent(String uid);
}
