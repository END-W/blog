package top.waiend.blog.back.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.waiend.blog.back.bean.User;
import top.waiend.blog.back.service.UserService;
import top.waiend.blog.base.bean.ResultVo;
import top.waiend.blog.base.exception.BlogException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 通过自定义异常定义系统中出现异常的情况
     * 使用枚举实现用户操作失败的定义情况
     * 使用ResultVo给客户端返回具体的结果信息
     * @param user
     * @param code
     * @param httpSession
     */
    @RequestMapping("/user/login")
    @ResponseBody
    public ResultVo login(User user, String code, HttpSession httpSession) {
        ResultVo resultVo = new ResultVo();
        // 从session获取正确的验证码
        String rightCode = (String) httpSession.getAttribute("code");
        try {
            user = userService.login(user, code, rightCode);
            resultVo.setOk(true);
            // 把登录用户存放到session中
            httpSession.setAttribute("user", user);
        } catch (BlogException e) {
            resultVo.setMess(e.getMessage());
        }

        return resultVo;
    }

    // 用户登录成功后跳转到后台首页
    @RequestMapping("/user/index")
    public String index() {
        return "workbench/index";
    }

    // 退出功能
    @RequestMapping("/user/loginOut")
    public String loginOut(HttpSession session) {
        //清除session中的用户
        session.removeAttribute("user");
        //重定向到登录页面
        return "redirect:/login.jsp";
    }

    // 异步校验当前登录用户输入的原始密码是否正确
    @RequestMapping("/user/verifyOldPwd")
    @ResponseBody
    public ResultVo verifyOldPwd(String oldPwd, HttpSession session) {
        ResultVo resultVo = new ResultVo();
        try{
            // 获取当前登录用户
            User user = (User) session.getAttribute("user");
            userService.verifyOldPwd(oldPwd, user);
            resultVo.setOk(true);
        } catch (BlogException e) {
            resultVo.setMess(e.getMessage());
        }
        return resultVo;
    }

    // 异步校验用户输入的原始密码是否正确
    @RequestMapping("/user/verifyOldPwd2")
    @ResponseBody
    public ResultVo verifyOldPwd2(String oldPwd, String uid) {
        ResultVo resultVo = new ResultVo();
        try{
            userService.verifyOldPwd2(oldPwd, uid);
            resultVo.setOk(true);
        } catch (BlogException e) {
            resultVo.setMess(e.getMessage());
        }
        return resultVo;
    }

    // 异步校验当前登录用户的用户名是否重复
    @RequestMapping("/user/verifyNickName")
    @ResponseBody
    public ResultVo verifyNickName(String nickname) {
        ResultVo resultVo = new ResultVo();
        try{
            userService.verifyNickName(nickname);
            resultVo.setOk(true);
        } catch (BlogException e) {
            resultVo.setMess(e.getMessage());
        }
        return resultVo;
    }

    // 异步校验用户名是否重复
    @RequestMapping("/user/verifyNickName2")
    @ResponseBody
    public ResultVo verifyNickName2(String nickname) {
        ResultVo resultVo = new ResultVo();
        try{
            userService.verifyNickName2(nickname);
            resultVo.setOk(true);
        } catch (BlogException e) {
            resultVo.setMess(e.getMessage());
        }
        return resultVo;
    }

    // 异步校验添加用户的用户名是否重复
    @RequestMapping("/user/verifyNickName3")
    @ResponseBody
    public ResultVo verifyNickName3(String nickname) {
        ResultVo resultVo = new ResultVo();
        try{
            userService.verifyNickName3(nickname);
            resultVo.setOk(true);
        } catch (BlogException e) {
            resultVo.setMess(e.getMessage());
        }
        return resultVo;
    }

    // 异步修改用户信息
    @RequestMapping("/user/changeUser")
    @ResponseBody
    public ResultVo changeUser(User user, HttpSession session) {
        ResultVo resultVo = new ResultVo();
        // 获取当前登录用户
        User user2 = (User) session.getAttribute("user");
        try{
            userService.changeUser(user, user2);
            resultVo.setOk(true);
            resultVo.setMess("修改用户信息成功");
        } catch (BlogException e) {
            resultVo.setMess(e.getMessage());
        }
        return resultVo;
    }

    // 异步添加用户
    @RequestMapping("/user/addUser")
    @ResponseBody
    public ResultVo addUser(User user) {
        ResultVo resultVo = new ResultVo();
        try {
            userService.addUser(user);
            resultVo.setOk(true);
            resultVo.setMess("添加用户成功");
        } catch (BlogException e) {
            resultVo.setMess(e.getMessage());
        }
        return resultVo;
    }

    // 异步更新用户信息
    @RequestMapping("/user/updateUser")
    @ResponseBody
    public void updateUser(HttpServletRequest request, HttpSession session) {
        // 获取当前登录用户
        User user = (User) session.getAttribute("user");
        session.setAttribute("lastLoginTime", user.getLastLoginTime());
        session.setAttribute("loginIp", user.getLoginIp());
        String cAddr = request.getRemoteAddr();

        try {
            // 更新用户信息
            userService.updateUser(cAddr, user);
        } catch (BlogException e) {
            System.out.println(e.getMessage());
        }
    }

    // 异步查询管理员人数
    @RequestMapping("/user/queryCount")
    @ResponseBody
    public int queryCount() {
        return userService.queryCount();
    }

    // 异步查询所有用户
    @RequestMapping("/user/list")
    @ResponseBody
    public PageInfo<User> list(int page, int pageSize, String context) {
        //参数1:当前页码 参数2:每页记录数 pageSize 该方法等同于 limit a,b
        PageHelper.startPage(page, pageSize);
        List<User> users = userService.list(context);
        return new  PageInfo<>(users);
    }

    // 异步删除指定uid用户
    @RequestMapping("/user/deleteById")
    @ResponseBody
    public ResultVo deleteById(String uid) {
        ResultVo resultVo = new ResultVo();
        try {
            userService.deleteById(uid);
            resultVo.setOk(true);
            resultVo.setMess("删除用户成功");
        } catch (BlogException e) {
            resultVo.setMess(e.getMessage());
        }
        return resultVo;
    }

    // 异步修改用户状态
    @RequestMapping("/user/state")
    @ResponseBody
    public ResultVo state(String uid, String state) {
        ResultVo resultVo = new ResultVo();
        try {
            userService.state(uid, state);
            resultVo.setOk(true);
            resultVo.setMess("修改用户状态成功");
        } catch (BlogException e) {
            resultVo.setMess(e.getMessage());
        }
        return resultVo;
    }

    // 异步查询指定uid用户
    @RequestMapping("/user/queryById")
    @ResponseBody
    public User queryById(String uid) {
       return userService.queryById(uid);
    }
}
