package top.waiend.blog.back.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.waiend.blog.back.bean.Link;
import top.waiend.blog.back.service.LinkService;
import top.waiend.blog.base.bean.ResultVo;
import top.waiend.blog.base.exception.BlogException;

import java.util.List;

@Controller
public class LinkController {
    @Autowired
    private LinkService linkService;

    // 发送查询所有友链的异步请求
    @RequestMapping("/link/list")
    @ResponseBody
    public PageInfo<Link> list(int page, int pageSize, String context) {
        //参数1:当前页码 参数2:每页记录数 pageSize 该方法等同于 limit a,b
        PageHelper.startPage(page, pageSize);
        List<Link> links = linkService.list(context);
        return new PageInfo<>(links);
    }

    // 删除指定id友链
    @RequestMapping("/link/deleteById")
    @ResponseBody
    public ResultVo deleteById(String id) {
        ResultVo resultVo = new ResultVo();
        try {
            linkService.deleteById(id);
            resultVo.setOk(true);
            resultVo.setMess("删除友链成功");
        } catch (BlogException e) {
            resultVo.setMess(e.getMessage());
        }
        return resultVo;
    }

    // 通过id查询友链
    @RequestMapping("/link/queryById")
    @ResponseBody
    public Link queryById(String id) {
        return linkService.queryById(id);
    }

    // 添加或更新友链
    @RequestMapping("/link/saveOrUpdate")
    @ResponseBody
    public ResultVo saveOrUpdate(Link link) {
        ResultVo resultVo = new ResultVo();
        try {
            linkService.saveOrUpdate(link);
            resultVo.setOk(true);
            if (link.getId() == null) {
                resultVo.setMess("增加友链成功");
            } else {
                resultVo.setMess("修改友链成功");
            }
        } catch (BlogException e) {
            resultVo.setMess(e.getMessage());
        }
        return resultVo;
    }

    // 查询所有友链
    @RequestMapping("/link/queryAll")
    @ResponseBody
    public List<Link> queryAll() {
        return linkService.queryAll();
    }
}
