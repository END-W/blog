package top.waiend.blog.back.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import top.waiend.blog.back.bean.User;
import top.waiend.blog.back.mapper.UserMapper;
import top.waiend.blog.back.service.UserService;
import top.waiend.blog.base.exception.BlogEnum;
import top.waiend.blog.base.exception.BlogException;
import top.waiend.blog.base.util.DateTimeUtil;
import top.waiend.blog.base.util.MD5Util;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    // 校验验证码
    @Override
    public User login(User user, String code, String rightCode) {
        if (!rightCode.equals(code)) {
            throw new BlogException(BlogEnum.USER_LOGIN_CODE);
        }

        // 把用户输入的密码进行加密
        String password = user.getPassword();
        password = MD5Util.getMD5(password);
        user.setPassword(password);

        // 判断是否被锁定账户
        user.setState("1");

        // 校验用户名和密码是否正确
        List<User> users = userMapper.select(user);
        if (users.size() == 0) {
            throw new BlogException(BlogEnum.USER_LOGIN_ACCOUNT);
        }

        return users.get(0);
    }

    @Override
    public void verifyOldPwd(String oldPwd, User user) {
        oldPwd = MD5Util.getMD5(oldPwd);
        String password = user.getPassword();
        if(!password.equals(oldPwd)){
            throw new BlogException(BlogEnum.USER_VERIFY_PASS);
        }
    }

    @Override
    public void verifyOldPwd2(String oldPwd, String uid) {
        oldPwd = MD5Util.getMD5(oldPwd);
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("uid", uid).andEqualTo("password", oldPwd);
        User user = userMapper.selectOneByExample(example);
        if (user == null) {
            throw new BlogException(BlogEnum.USER_VERIFY_PASS);
        }
    }

    @Override
    public void verifyNickName(String nickname) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("nickname", nickname);
        User user = userMapper.selectOneByExample(example);
        if (user != null) {
            throw new BlogException(BlogEnum.USER_VERIFY_NICKNAME);
        }
    }

    @Override
    public void verifyNickName2(String nickname) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("nickname", nickname);
        User user = userMapper.selectOneByExample(example);
        if (user != null) {
            throw new BlogException(BlogEnum.USER_VERIFY_NICKNAME);
        }
    }

    @Override
    public void verifyNickName3(String nickname) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("nickname", nickname);
        User user = userMapper.selectOneByExample(example);
        if (user != null) {
            throw new BlogException(BlogEnum.USER_VERIFY_NICKNAME);
        }
    }

    @Override
    public void changeUser(User user, User user2) {
        if (user.getPassword() != null) {
            //给用户输入的新密码加密
            user.setPassword(MD5Util.getMD5(user.getPassword()));
        }
        // count:影响记录数
        int count = userMapper.updateByPrimaryKeySelective(user);
        if(count == 0){
            throw new BlogException(BlogEnum.USER_CHANGE);
        }
    }

    @Override
    public List<User> list(String context) {
        Example example = new Example(User.class);
        if (context != null && !context.equals("")) {
            example.createCriteria().andLike("nickname", "%" + context + "%");
        }
        List<User> users = userMapper.selectByExample(example);
        for (User user : users) {
            user.setPassword("");
        }
        return users;
    }

    @Override
    public void deleteById(String uid) {
        int count = userMapper.deleteByPrimaryKey(uid);
        if (count == 0) {
            throw new BlogException(BlogEnum.USER_DELETE);
        }
    }

    @Override
    public void state(String uid, String state) {
        User user = new User();
        user.setUid(uid);
        user.setState(state);

        int count = userMapper.updateByPrimaryKeySelective(user);
        if (count == 0) {
            throw new BlogException(BlogEnum.USER_UPDATE_STATE);
        }
    }

    @Override
    public User queryById(String uid) {
        User user = userMapper.selectByPrimaryKey(uid);
        user.setPassword("");
        return user;
    }

    @Override
    public void addUser(User user) {
        // 为用户密码加密
        user.setPassword(MD5Util.getMD5(user.getPassword()));
        // 设置用户权限
        user.setRole("1");
        // 设置用户状态
        user.setState("1");
        // 设置用户创建时间
        user.setCreateTime(DateTimeUtil.getSysTime());
        // 设置用户登录次数
        user.setLoginCount("0");
        int count = userMapper.insertSelective(user);
        if (count == 0) {
            throw new BlogException(BlogEnum.USER_ADD);
        }
    }

    @Override
    public void updateUser(String cAddr, User user) {
        // 更新用户信息
        int loginCount = Integer.valueOf(user.getLoginCount()) + 1 ;
        user.setLastLoginTime(DateTimeUtil.getSysTime());
        user.setLoginCount(String.valueOf(loginCount));
        user.setLoginIp(cAddr);

        // count:影响记录数
        int count = userMapper.updateByPrimaryKeySelective(user);
        if(count == 0){
            throw new BlogException(BlogEnum.USER_UPDATE);
        }
    }

    @Override
    public int queryCount() {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("role", "0");
        int count = userMapper.selectCountByExample(example);
        return count;
    }

}
