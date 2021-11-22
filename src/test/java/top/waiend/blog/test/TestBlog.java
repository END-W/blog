package top.waiend.blog.test;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tk.mybatis.mapper.entity.Example;
import top.waiend.blog.back.bean.Article;
import top.waiend.blog.back.mapper.ArticleMapper;
import top.waiend.blog.base.exception.BlogEnum;
import top.waiend.blog.base.exception.BlogException;

import java.util.List;

public class TestBlog {
    private BeanFactory beanFactory = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
    private ArticleMapper articleMapper = (ArticleMapper) beanFactory.getBean("articleMapper");

    // 测试添加
    @Test
    public void test01() {

        Article article = new Article();
        article.setDigest("文章摘要");
        article.setContent("文章内容");
        articleMapper.insertSelective(article);
    }

    // 测试修改
    @Test
    public void test02() {

        Article article = new Article();
//        article.setAid("71");
        article.setContent("文章内容02");
//        articleMapper.updateByPrimaryKeySelective(article);

        // 需求把cid=5的文章修改 参数：实体类的字节码
        Example example = new Example(Article.class);
        Example.Criteria criteria = example.createCriteria();

        String cid = "5";
        // 参数1：实体类的属性名 参数2：实际参数
        criteria.andEqualTo("cid", cid).andEqualTo("aid", "71");
        articleMapper.updateByExampleSelective(article, example);
    }

    // 测试删除
    @Test
    public void test03() {
//        articleMapper.deleteByPrimaryKey("71");

        /**
         * 1.条件参数是封装在对象中
         * 2.只能做等值的条件
         */
        /*Article article = new Article();
        article.setAid("70");
        articleMapper.delete(article);*/

        Example example = new Example(Article.class);
        example.createCriteria().andGreaterThanOrEqualTo("aid", "70");
        articleMapper.deleteByExample(example);

    }

    //测试查询
    @Test
    public void test04() {
        /*Article article = new Article();
        article.setAid("59");
        Article article1 = articleMapper.selectOne(article);

        System.out.println(article1);*/
        //查询所有
       /* List<Article> articles = articleMapper.selectAll();
        System.out.println(articles);*/

        Example example = new Example(Article.class);
        //_:1个字符 %:0-n个字符
        //需求:查询title中包含学习并且创建时间>2021-01-13文章信息
        String title = "学习";
        String create_time = "2021-01-13";
        example.createCriteria()
                .andLike("title","%" + title + "%")
                .andGreaterThan("create_time",create_time);
        List<Article> articles = articleMapper.selectByExample(example);

        System.out.println(articles);
    }

    //测试自定义异常
    @Test
    public void test05(){
        int a = 0;
        try{
            if(a == 0){
                throw new BlogException(BlogEnum.USER_LOGIN_CODE);
            }
        }catch (BlogException e){
            System.out.println(e.getMessage());
        }

    }
}
