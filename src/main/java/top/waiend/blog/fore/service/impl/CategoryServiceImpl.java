package top.waiend.blog.fore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.waiend.blog.back.bean.Category;
import top.waiend.blog.back.mapper.CategoryMapper;
import top.waiend.blog.fore.service.CategoryService;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> list() {
        return categoryMapper.selectAll();
    }
}
