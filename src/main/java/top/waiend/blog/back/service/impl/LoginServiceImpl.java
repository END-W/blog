package top.waiend.blog.back.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import top.waiend.blog.back.bean.Login;
import top.waiend.blog.back.bean.User;
import top.waiend.blog.back.mapper.LoginMapper;
import top.waiend.blog.back.service.LoginService;
import top.waiend.blog.base.exception.BlogEnum;
import top.waiend.blog.base.exception.BlogException;

import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;

    @Override
    public void insertLoginLog(User user) {
        Login login = new Login();
        login.setUid(user.getUid());
        login.setNickname(user.getNickname());
        login.setLogin_ip(user.getLoginIp());
        login.setLogin_time(user.getLastLoginTime());
        login.setLogin_state("成功");
        int count = loginMapper.insertSelective(login);

        if (count == 0) {
            throw new BlogException(BlogEnum.LOGINLOG_INSERT);
        }
    }

    @Override
    public List<Login> queryLoginLog(User user) {
        Example example = new Example(Login.class);
        // 排序
        example.orderBy("login_time").desc();
        // 条件查询
        example.createCriteria().andEqualTo("uid", user.getUid());
        return loginMapper.selectByExample(example);
    }

    @Override
    public List<Login> list(String context) {
        Example example = new Example(Login.class);
        if (context != null && !context.equals("")) {
            example.createCriteria().andLike("nickname", "%" + context + "%");
        }

        return loginMapper.selectByExample(example);
    }

    @Override
    public void deleteById(String pid) {
        int count = loginMapper.deleteByPrimaryKey(pid);
        if (count == 0) {
            throw new BlogException(BlogEnum.LOGINLOG_DELETE_COMMON);
        }
    }

    @Override
    public void deleteAll() {
        Login login = new Login();
        int count = loginMapper.delete(login);
        if (count == 0) {
            throw new BlogException(BlogEnum.LOGINLOG_DELETE_ALL);
        }
    }

    @Override
    public void deleteCurrent(String uid) {
        Login login = new Login();
        login.setUid(uid);
        int count = loginMapper.delete(login);
        if (count == 0) {
            throw new BlogException(BlogEnum.LOGINLOG_DELETE_CURRENT);
        }
    }
}
