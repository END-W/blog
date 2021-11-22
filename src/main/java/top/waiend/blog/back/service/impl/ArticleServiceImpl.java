package top.waiend.blog.back.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import top.waiend.blog.back.bean.Article;
import top.waiend.blog.back.bean.Category;
import top.waiend.blog.back.bean.Tag;
import top.waiend.blog.back.mapper.ArticleMapper;
import top.waiend.blog.back.mapper.CategoryMapper;
import top.waiend.blog.back.mapper.TagMapper;
import top.waiend.blog.back.service.ArticleService;
import top.waiend.blog.base.exception.BlogEnum;
import top.waiend.blog.base.exception.BlogException;
import top.waiend.blog.base.util.DateTimeUtil;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<Article> list(String uid, String title) {
        Example example = new Example(Article.class);
        Example.Criteria criteria = example.createCriteria();
        // 查询当前登录用户的文章
        criteria.andEqualTo("uid", uid);
        if(title != null && !title.equals("")){
            criteria.andLike("title","%" + title + "%");
        }
        List<Article> articles = articleMapper.selectByExample(example);
        // 遍历所有文章
        for (Article article : articles) {
            String cid = article.getCid();
            // 根据cid查询栏目表，查询栏目对象
            Category category = categoryMapper.selectByPrimaryKey(cid);
            article.setCid(category.getCname());
        }
        return articles;
    }

    @Override
    public void isOpen(Article article) {
        int count = articleMapper.updateByPrimaryKeySelective(article);
        if (count == 0) {
            throw new BlogException(BlogEnum.ARTICLE_ISOPEN);
        }
    }

    @Override
    public List<Category> queryCategory() {
        return categoryMapper.selectAll();
    }

    @Override
    public List<Tag> queryTags(String cid) {
        Tag tag = new Tag();
        tag.setCid(cid);
        return tagMapper.select(tag);
    }

    @Override
    public Article saveOrUpdate(Article article) {
        if(article.getAid() == null){
            // 添加
            // 点赞数
            article.setThumbsUp("0");
            // 是否热门
            article.setIs_hot("0");
            // 访问量
            article.setVisit_count("0");
            // 发布时间
            article.setCreate_time(DateTimeUtil.getSysTime());
            // 评论数
            article.setComments("0");
            int count = articleMapper.insertSelective(article);
            if(count == 0){
                // 发布失败
                throw new BlogException(BlogEnum.ARTICLE_PUBLISH);
            }
        } else {
            article.setUpdate_time(DateTimeUtil.getSysTime());
            int count = articleMapper.updateByPrimaryKeySelective(article);
            if(count == 0){
                // 修改失败
                throw new BlogException(BlogEnum.ARTICLE_UPDATE);
            }
        }
        return article;
    }

    @Override
    public Article queryById(String aid) {
        return articleMapper.selectByPrimaryKey(aid);
    }

    @Override
    public void deleteById(String id) {
        int count = articleMapper.deleteByPrimaryKey(id);
        if (count == 0) {
            throw new BlogException(BlogEnum.ARTICLE_UPDATE);
        }
    }

    @Override
    public Article queryInfoOverview(String uid) {
        Article article = new Article();
        Example example = new Example(Article.class);
        Example.Criteria criteria = example.createCriteria();
        // 查询当前登录用户的文章
        criteria.andEqualTo("uid", uid);
        List<Article> articles = articleMapper.selectByExample(example);

        // 设置文章总数
        article.setTitle(String.valueOf(articles.size()));
        // 设置评论数
        int comments = 0;
        // 设置点赞数
        int thumbsUp = 0;
        // 设置访问量
        int visitCount = 0;
        for (Article article1: articles) {
            comments += Integer.valueOf(article1.getComments());
            thumbsUp += Integer.valueOf(article1.getThumbsUp());
            visitCount += Integer.valueOf(article1.getVisit_count());
        }
        article.setComments(String.valueOf(comments));
        article.setThumbsUp(String.valueOf(thumbsUp));
        article.setVisit_count(String.valueOf(visitCount));

        return article;
    }

    @Override
    public int queryByUid(String uid) {
        Example example = new Example(Article.class);
        example.createCriteria().andEqualTo("uid", uid);
        int count = 0;
        count = articleMapper.selectCountByExample(example);
        return count;
    }

}
