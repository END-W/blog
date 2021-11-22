package top.waiend.blog.back.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.waiend.blog.back.bean.Login;
import top.waiend.blog.back.bean.User;
import top.waiend.blog.back.service.LoginService;
import top.waiend.blog.base.bean.ResultVo;
import top.waiend.blog.base.exception.BlogException;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;

    // 异步添加用户登录日志
    @RequestMapping("/loginLog/insertLoginLog")
    @ResponseBody
    public void insertLoginLog(HttpSession session) {
        // 获取当前登录用户
        User user = (User) session.getAttribute("user");
        try {
            loginService.insertLoginLog(user);
        } catch (BlogException e) {
            System.out.println(e.getMessage());
        }
    }

    // 异步查询当前用户登录信息
    @RequestMapping("/loginLog/queryLoginLog")
    @ResponseBody
    public PageInfo<Login> queryLoginLog(HttpSession session) {
        // 获取当前登录用户
        User user = (User) session.getAttribute("user");
        //参数1:当前页码 参数2:每页记录数 pageSize 该方法等同于 limit a,b
        PageHelper.startPage(1, 5);
        List<Login> logins = loginService.queryLoginLog(user);
        return new PageInfo<>(logins);
    }

    // 异步查询所有用户登录记录
    @RequestMapping("/loginLog/list")
    @ResponseBody
    public PageInfo<Login> list(int page, int pageSize, String context) {
        //参数1:当前页码 参数2:每页记录数 pageSize 该方法等同于 limit a,b
        PageHelper.startPage(page, pageSize);
        List<Login> logins = loginService.list(context);
        return new  PageInfo<>(logins);
    }

    // 异步删除指定pid用户登录日志
    @RequestMapping("/loginLog/deleteById")
    @ResponseBody
    public ResultVo deleteById(String pid) {
        ResultVo resultVo = new ResultVo();
        try {
            loginService.deleteById(pid);
            resultVo.setOk(true);
            resultVo.setMess("删除用户日志成功");
        } catch (BlogException e) {
            resultVo.setMess(e.getMessage());
        }
        return resultVo;
    }

    // 异步清除所有登录记录
    @RequestMapping("/loginLog/deleteAll")
    @ResponseBody
    public ResultVo deleteAll() {
        ResultVo resultVo = new ResultVo();
        try {
            loginService.deleteAll();
            resultVo.setOk(true);
            resultVo.setMess("删除所有用户日志成功");
        } catch (BlogException e) {
            resultVo.setMess(e.getMessage());
        }
        return resultVo;
    }

    // 异步清除本人登录记录
    @RequestMapping("/loginLog/deleteCurrent")
    @ResponseBody
    public ResultVo deleteCurrent(HttpSession session) {
        ResultVo resultVo = new ResultVo();
        // 获取当前登录用户
        User user = (User) session.getAttribute("user");
        try {
            loginService.deleteCurrent(user.getUid());
            resultVo.setOk(true);
            resultVo.setMess("删除登录用户所有日志成功");
        } catch (BlogException e) {
            resultVo.setMess(e.getMessage());
        }
        return resultVo;
    }
}
