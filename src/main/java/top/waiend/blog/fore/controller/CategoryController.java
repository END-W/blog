package top.waiend.blog.fore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.waiend.blog.back.bean.Category;
import top.waiend.blog.fore.service.CategoryService;

import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    // 获取文章栏目
    @RequestMapping("/nav/list")
    @ResponseBody
    public List<Category> list() {
        return categoryService.list();
    }
}
