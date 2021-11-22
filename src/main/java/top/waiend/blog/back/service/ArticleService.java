package top.waiend.blog.back.service;

import top.waiend.blog.back.bean.Article;
import top.waiend.blog.back.bean.Category;
import top.waiend.blog.back.bean.Tag;

import java.util.List;

public interface ArticleService {
    List<Article> list(String uid, String title);

    void isOpen(Article article);

    List<Category> queryCategory();

    List<Tag> queryTags(String cid);

    Article saveOrUpdate(Article article);

    Article queryById(String aid);

    void deleteById(String id);

    Article queryInfoOverview(String uid);

    int queryByUid(String uid);
}
