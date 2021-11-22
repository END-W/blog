package top.waiend.blog.back.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.waiend.blog.back.bean.Article;
import top.waiend.blog.back.bean.Category;
import top.waiend.blog.back.bean.Tag;
import top.waiend.blog.back.bean.User;
import top.waiend.blog.back.service.ArticleService;
import top.waiend.blog.base.bean.ResultVo;
import top.waiend.blog.base.exception.BlogException;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping("/article/list")
    @ResponseBody
    public PageInfo<Article> list(int page, int pageSize, String title, HttpSession session){
        //获取当前登录用户
        User user = (User) session.getAttribute("user");
        //参数1:当前页码 参数2:每页记录数 pageSize 该方法等同于 limit a,b
        PageHelper.startPage(page, pageSize);
        List<Article> articles = articleService.list(user.getUid(), title);
        return new PageInfo<>(articles);
    }

    // 异步修改文章是否公开
    @RequestMapping("/article/isOpen")
    @ResponseBody
    public ResultVo isOpen(Article article) {
        ResultVo resultVo = new ResultVo();
        try {
            articleService.isOpen(article);
            resultVo.setOk(true);
            if ("0".equals(article.getIsOpen())) {
                resultVo.setMess("文章已私密");
            } else {
                resultVo.setMess("文章已公开");
            }
        } catch (BlogException e) {
            resultVo.setMess(e.getMessage());
        }
        return resultVo;
    }

    //异步查询所有栏目
    @RequestMapping("/article/queryCategory")
    @ResponseBody
    public List<Category> queryCategory(){
        List<Category> categories = articleService.queryCategory();
        return categories;
    }

    //查询栏目下的标签
    @RequestMapping("/article/queryTags")
    @ResponseBody
    public List<Tag> queryTags(String cid){
        List<Tag> tags = articleService.queryTags(cid);
        return tags;
    }

    // 异步发布文章
    @RequestMapping("/article/saveOrUpdate")
    @ResponseBody
    public ResultVo saveOrUpdate(Article article, HttpSession session) {
        ResultVo resultVo = new ResultVo();
        try {
            //获取登录用户
            User user = (User) session.getAttribute("user");
            article.setUid(user.getUid());
            article = articleService.saveOrUpdate(article);
            resultVo.setOk(true);
            if (article.getAid() == null) {
                resultVo.setMess("发布文章成功");
            } else {
                resultVo.setMess("修改文章文章成功");
            }
            resultVo.setT(article);
        } catch (BlogException e) {
            resultVo.setMess(e.getMessage());
        }
        return resultVo;
    }

    // 异步查询文章信息
    @RequestMapping("/article/queryById")
    @ResponseBody
    public Article queryById(String aid){
        return articleService.queryById(aid);
    }

    // 异步查询指定uid用户的文章数量
    @RequestMapping("/article/queryByUid")
    @ResponseBody
    public ResultVo queryByUid(String uid) {
        ResultVo resultVo = new ResultVo();
        int count = articleService.queryByUid(uid);
        resultVo.setMess(String.valueOf(count));
        return resultVo;
    }

    // 异步删除文章
    @RequestMapping("/article/deleteById")
    @ResponseBody
    public ResultVo deleteById(String id){
        ResultVo resultVo = new ResultVo();
        try {
            articleService.deleteById(id);
            resultVo.setOk(true);
            resultVo.setMess("删除文章成功");
        }catch (BlogException e){
            resultVo.setMess(e.getMessage());
        }
        return resultVo;
    }

    // 异步查询信息总览
    @RequestMapping("/article/queryInfoOverview")
    @ResponseBody
    public Article updateInfoOverview(HttpSession session) {
        //获取当前登录用户
        User user = (User) session.getAttribute("user");
        return articleService.queryInfoOverview(user.getUid());
    }
}
