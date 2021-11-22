/*
 Navicat Premium Data Transfer

 Source Server         : end
 Source Server Type    : MySQL
 Source Server Version : 50528
 Source Host           : localhost:3306
 Source Schema         : blog

 Target Server Type    : MySQL
 Target Server Version : 50528
 File Encoding         : 65001

 Date: 22/11/2021 22:30:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_article
-- ----------------------------
DROP TABLE IF EXISTS `t_article`;
CREATE TABLE `t_article`  (
  `aid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章标题',
  `digest` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '摘要',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '文章内容',
  `cid` int(10) NULL DEFAULT NULL COMMENT '所属标签分类',
  `visit_count` int(20) NOT NULL COMMENT '访问量',
  `create_time` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发布时间',
  `update_time` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新时间',
  `is_hot` int(255) NOT NULL COMMENT '是否热门 0：不热门 1：热门',
  `logo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章logo',
  `uid` int(11) NULL DEFAULT NULL COMMENT '发布者',
  `isOpen` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '是否公开 0:不公开 1:公开',
  `thumbsUp` bigint(255) NOT NULL COMMENT '点赞数',
  `tagNames` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章标签',
  `comments` int(255) NOT NULL COMMENT '评论数',
  PRIMARY KEY (`aid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 70 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_article
-- ----------------------------
INSERT INTO `t_article` VALUES (59, '学习', '这篇文章是为了介绍自己自学用过的Java视频资料。本套整合教程总共180+G，共450+小时。考虑到绝大部分视频至少要看两遍，而且视频总时长并不代表学习时长，所以零基础初学者总学习时间大约为：600小时视频时长 + 100小时理解 + 100小时练习，至少需要800小时。你可能觉得自己能一天学习8小时，实际上平均下来每天能学4小时都算厉害了。总会有各种原因，比如当天内容太难，公司聚会，要出差等等。如果周末你也是坚持学习，那么最理想状况下，6个半月就可以学完，达到工作后能被人带的水平。但我知道那其实基本不可能。', '> 使用Editor.md保存的html代码 前端页面如何显示\n\n1.引入css\n```css\n<link rel=\"stylesheet\" href=\"/editor.md/css/editormd.css\" />\n```\n2.引入js\n```javascript\n<script  src=\"/js/jquery-1.11.3.min.js\"></script>\n<script  src=\"/editor.md/editormd.js\"></script>\n<script  src=\"/editor.md/lib/marked.min.js\"></script>\n<script  src=\"/editor.md/lib/prettify.min.js\"></script>\n```\n3.模板引擎渲染html,取出html代码\n```css\n<div id=\"content\">\n  {{ $article->editor-html-code }}   // laravel blade\n</div>\n```', 1, 10, '2020-09-20', '2021-11-18 18:32:53', 0, 'http://localhost:8080/blog/upload\\admin\\2021-11-06\\1636186362754822247B47E2CB154F09A38D578E3F768.jpg', 1, '1', 10, 'Java', 12);
INSERT INTO `t_article` VALUES (65, 'Java学习路线推荐2', '这篇文章是为了介绍自己自学用过的Java视频资料。本套整合教程总共180+G，共450+小时。考虑到绝大部分视频至少要看两遍，而且视频总时长并不代表学习时长，所以零基础初学者总学习时间大约为：600小时视频时长 + 100小时理解 + 100小时练习，至少需要800小时。你可能觉得自己能一天学习8小时，实际上平均下来每天能学4小时都算厉害了。总会有各种原因，比如当天内容太难，公司聚会，要出差等等。如果周末你也是坚持学习，那么最理想状况下，6个半月就可以学完，达到工作后能被人带的水平。但我知道那其实基本不可能。', '```\npackage com.bjpowernode.blog.back.controller;\n\nimport com.bjpowernode.blog.back.bean.Article;\nimport com.bjpowernode.blog.back.bean.User;\nimport com.bjpowernode.blog.back.service.ArticleService;\nimport com.bjpowernode.blog.base.bean.ResultVo;\nimport com.bjpowernode.blog.base.constants.BlogConstants;\nimport com.bjpowernode.blog.base.exception.BlogException;\nimport com.bjpowernode.blog.base.util.FileUploadUtil;\nimport org.springframework.beans.factory.annotation.Autowired;\nimport org.springframework.stereotype.Controller;\nimport org.springframework.web.bind.annotation.RequestMapping;\nimport org.springframework.web.bind.annotation.RestController;\nimport org.springframework.web.multipart.MultipartFile;\n\nimport javax.servlet.http.HttpServletRequest;\nimport java.util.Map;\n\n/**\n * Company :  北京动力节点\n * Author :   Andy\n * Date : 2021/1/12\n * Description :\n */\n@Controller\npublic class ArticleController {\n\n    @Autowired\n    private ArticleService articleService;\n\n    //发布和修改文章\n    @RequestMapping(\"/article/saveOrUpdateArticle\")\n    public String saveOrUpdateArticle(Article article, HttpServletRequest request,\n                                        MultipartFile img){\n        //获取文章LOGO图片\n        Map<String,Object> map = FileUploadUtil.fileUpload(img,request);\n        String logo = (String) map.get(\"url\");\n        article.setLogo(logo);\n\n        //将登录用户主键设置到外键中\n        User user =\n                (User) request.getSession().getAttribute(BlogConstants.LOGIN_USER);\n        article.setUid(user.getUid());\n        ResultVo resultVo = new ResultVo();\n        try{\n            articleService.saveOrUpdateArticle(article);\n        }catch (BlogException e){\n            System.out.println(\"添加文章失败\");\n        }\n\n        return \"article\";\n    }\n}\n\n```\n![](http://localhost:8080/blog\\upload\\2021-01-13\\admin\\1610504718461a1.jpg)\n\n                \n                ', 1, 200, '2021-03-13', '2021-11-18 16:51:21', 0, 'http://localhost:8080/blog\\upload\\2021-01-13\\admin\\1610539885667a1.jpg', 1, '1', 6, 'Java', 2);
INSERT INTO `t_article` VALUES (68, '关于Java编程', '这篇文章是为了介绍自己自学用过的Java视频资料。本套整合教程总共180+G，共450+小时。考虑到绝大部分视频至少要看两遍，而且视频总时长并不代表学习时长，所以零基础初学者总学习时间大约为：600小时视频时长 + 100小时理解 + 100小时练习，至少需要800小时。你可能觉得自己能一天学习8小时，实际上平均下来每天能学4小时都算厉害了。总会有各种原因，比如当天内容太难，公司聚会，要出差等等。如果周末你也是坚持学习，那么最理想状况下，6个半月就可以学完，达到工作后能被人带的水平。但我知道那其实基本不可能。', ' ```java\r\npackage com.bjpowernode.blog.back.controller;\r\n\r\nimport com.bjpowernode.blog.back.bean.Article;\r\nimport com.bjpowernode.blog.back.bean.Category;\r\nimport com.bjpowernode.blog.back.bean.Tag;\r\nimport com.bjpowernode.blog.back.bean.User;\r\nimport com.bjpowernode.blog.back.service.ArticleService;\r\nimport com.bjpowernode.blog.base.bean.PaginationVo;\r\nimport com.bjpowernode.blog.base.bean.ResultVo;\r\nimport com.bjpowernode.blog.base.constants.BlogConstants;\r\nimport com.bjpowernode.blog.base.exception.BlogException;\r\nimport com.bjpowernode.blog.base.util.FileUploadUtil;\r\nimport com.github.pagehelper.PageHelper;\r\nimport com.github.pagehelper.PageInfo;\r\nimport org.springframework.beans.factory.annotation.Autowired;\r\nimport org.springframework.stereotype.Controller;\r\nimport org.springframework.ui.Model;\r\nimport org.springframework.web.bind.annotation.RequestMapping;\r\nimport org.springframework.web.bind.annotation.RequestParam;\r\nimport org.springframework.web.bind.annotation.ResponseBody;\r\nimport org.springframework.web.multipart.MultipartFile;\r\n\r\nimport javax.servlet.http.HttpServletRequest;\r\nimport javax.servlet.http.HttpSession;\r\nimport java.util.Arrays;\r\nimport java.util.List;\r\nimport java.util.Map;\r\n\r\n/**\r\n * Company :  北京动力节点\r\n * Author :   Andy\r\n * Date : 2021/1/12\r\n * Description :\r\n */\r\n@Controller\r\npublic class ArticleController {\r\n\r\n    @Autowired\r\n    private ArticleService articleService;\r\n\r\n    //增加点赞数\r\n    @RequestMapping(\"/article/addThumbsUp\")\r\n    @ResponseBody\r\n    public ResultVo addThumbsUp(String aid){\r\n        ResultVo resultVo = new ResultVo();\r\n        try {\r\n            String thumbsUp = articleService.addThumbsUp(aid);\r\n            resultVo.setOk(true);\r\n            resultVo.setMess(thumbsUp);\r\n        }catch (BlogException e){\r\n            resultVo.setOk(true);\r\n            e.printStackTrace();\r\n        }\r\n        return resultVo;\r\n    }\r\n\r\n    //前提查询文章\r\n    @RequestMapping(\"/article/articleList\")\r\n    public String articleList(Model model){\r\n        //查询最新发布的前6篇文章\r\n        List<Article> articles = articleService.queryTop6();\r\n        model.addAttribute(\"articles\",articles);\r\n        return \"../../view/article/article\";\r\n    }\r\n    //根据主键查询文章信息\r\n    @RequestMapping(\"/article/queryById\")\r\n    public String queryById(String aid, Model model){\r\n        Article article = articleService.queryById(aid);\r\n        model.addAttribute(\"article\",article);\r\n        return \"updateArticle\";\r\n    }\r\n\r\n    //根据主键查询文章信息,跳转到前台文章详情页\r\n    @RequestMapping(\"/article/queryByIdToDetail\")\r\n    public String queryByIdToDetail(String aid, Model model){\r\n        Article article = articleService.queryById(aid);\r\n        model.addAttribute(\"article\",article);\r\n        return \"../../view/article/articleDetail\";\r\n    }\r\n\r\n    //异步更新文章是否公开\r\n    @RequestMapping(\"/article/updateIsOpen\")\r\n    @ResponseBody\r\n    public ResultVo updateIsOpen(String isOpen,String aid){\r\n        ResultVo resultVo = new ResultVo();\r\n        try {\r\n            articleService.updateIsOpen(isOpen,aid);\r\n            resultVo.setOk(true);\r\n            if(\"1\".equals(isOpen)){\r\n                //公开\r\n                resultVo.setMess(\"已将文章设为公开\");\r\n            }else{\r\n                //私密\r\n                resultVo.setMess(\"已将文章设为私密\");\r\n            }\r\n\r\n        } catch (BlogException e) {\r\n            e.printStackTrace();\r\n            resultVo.setOk(false);\r\n            if(\"1\".equals(isOpen)){\r\n                //公开\r\n                resultVo.setMess(\"已将文章设为公开失败\");\r\n            }else{\r\n                //私密\r\n                resultVo.setMess(\"已将文章设为私密失败\");\r\n            }\r\n        }\r\n        return resultVo;\r\n    }\r\n\r\n    //选中栏目查询栏目下所有的标签\r\n    @RequestMapping(\"/article/queryByCategory\")\r\n    @ResponseBody\r\n    public List<Tag> queryByCategory(String cid,HttpSession session){\r\n        //从ServletContext中获取栏目信息\r\n        List<Category> categories =\r\n                (List<Category>) session.getServletContext().getAttribute(\"categories\");\r\n        List<Tag> tags = null;\r\n        for (Category category : categories) {\r\n            if(cid.equals(category.getCid())){\r\n                //获取栏目下对应的标签\r\n               tags = category.getTags();\r\n            }\r\n        }\r\n        return tags;\r\n    }\r\n\r\n    //文章列表和条件查询列表\r\n    @RequestMapping(\"/article/list\")\r\n    @ResponseBody\r\n    public PaginationVo list(@RequestParam(defaultValue = \"1\",required = false) int page,\r\n                             @RequestParam(defaultValue = \"2\",required = false) int pageSize,\r\n                             String title, HttpSession session){\r\n        //开启分页功能 limit a,b\r\n        PageHelper.startPage(page, pageSize);\r\n        //获取当前登录用户信息\r\n        User user = (User) session.getAttribute(BlogConstants.LOGIN_USER);\r\n        //查询所有文章信息\r\n        List<Article> articles = articleService.list(user.getUid(),title);\r\n        PageInfo<Article> pageInfo = new PageInfo<>(articles);\r\n        //把前台分页插件需要的数据都封装到paginationVo\r\n        PaginationVo<Article> paginationVo = new PaginationVo<>(pageInfo);\r\n        return paginationVo;\r\n    }\r\n\r\n    //发布和修改文章\r\n    @RequestMapping(\"/article/saveOrUpdateArticle\")\r\n    public String saveOrUpdateArticle(Article article, HttpServletRequest request,\r\n                                        MultipartFile img,String[] tid,String aid){\r\n        //获取文章LOGO图片\r\n        Map<String,Object> map = FileUploadUtil.fileUpload(img,request);\r\n        String logo = (String) map.get(\"url\");\r\n        article.setLogo(logo);\r\n\r\n        //文章标签\r\n        article.setTagNames(Arrays.toString(tid));\r\n\r\n        //将登录用户主键设置到外键中\r\n        User user =\r\n                (User) request.getSession().getAttribute(BlogConstants.LOGIN_USER);\r\n        article.setUid(user.getUid());\r\n        ResultVo resultVo = new ResultVo();\r\n        try{\r\n            articleService.saveOrUpdateArticle(article,aid);\r\n        }catch (BlogException e){\r\n            System.out.println(\"添加文章失败\");\r\n        }\r\n\r\n        return \"article\";\r\n    }\r\n\r\n    //异步删除文章\r\n    @RequestMapping(\"/article/deleteArticle\")\r\n    @ResponseBody\r\n    public ResultVo deleteArticle(String aid){\r\n        ResultVo resultVo = new ResultVo();\r\n        try {\r\n            articleService.deleteArticle(aid);\r\n            resultVo.setOk(true);\r\n            resultVo.setMess(\"删除文章成功\");\r\n        } catch (BlogException e) {\r\n            e.printStackTrace();\r\n            resultVo.setOk(false);\r\n        }\r\n        return resultVo;\r\n    }\r\n}\r\n\r\n```\r\n![](http://localhost:8080/blog\\upload\\2021-01-25\\admin\\16115059327483.png)\r\n|   |   |\r\n| ------------ | ------------ |\r\n|   |   |\r\n|   |   |\r\n\r\n                ', 1, 0, '2021-01-25', '2021-01-25 00:36:27', 0, 'http://localhost:8080/blog\\upload\\2021-01-25\\admin\\1611506187244', 1, '1', 0, 'Java', 5);
INSERT INTO `t_article` VALUES (69, '最好的Java学习', '一门永不过时的编程语言——Java 软件开发，5G时代即将来临，万物互联，更多的终端会使用安卓系统，随之而来的Java开发必然是会越来越火爆，所以学习Java是必要且必须的，下边就是', '\r\n\r\n## 2021-01-25 00:53:20 星期一\r\n\r\n> 第一阶段：JavaSE基础入门\r\n\r\n```javascript\r\n<script  src=\"/js/jquery-1.11.3.min.js\"></script>\r\n<script  src=\"/editor.md/editormd.js\"></script>\r\n<script  src=\"/editor.md/lib/marked.min.js\"></script>\r\n<script  src=\"/editor.md/lib/prettify.min.js\"></script>\r\n```\r\n> 第二阶段：JavaSE核心技术\r\n\r\n![](http://localhost:8080/blog\\upload\\2021-01-25\\admin\\1611506714893伙伴.jpg)\r\n                \r\n                \r\n                \r\n                \r\n                \r\n                \r\n                ', 1, 0, '2021-01-25', '2021-01-25 00:56:52', 0, 'http://localhost:8080/blog\\upload\\2021-01-25\\admin\\1611507412198', 1, '1', 1, 'Java', 5);

-- ----------------------------
-- Table structure for t_category
-- ----------------------------
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category`  (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `cname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '栏目名称',
  `cimg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '栏目logo',
  `cdesc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '栏目概述',
  PRIMARY KEY (`cid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_category
-- ----------------------------
INSERT INTO `t_category` VALUES (1, 'Java', '/blog/static/images/fore/Java.png', '本专栏主要分享Java的各种常见问题，包括Java学习路线，Java基础，框架，微服务，项目，面试题，希望通过这些知识的分享能够提升自己的Java水平');
INSERT INTO `t_category` VALUES (2, 'Python', '/blog/static/images/fore/Python.png', '本专栏主要分享关于Python的基础知识，常用框架(比如Spider)如何使用，项目等等');
INSERT INTO `t_category` VALUES (3, '前端', '/blog/static/images/fore/Web.jpg', '本专栏主要分享关于web前端的学习路线，基础知识，常用框架，面试题，项目等等');
INSERT INTO `t_category` VALUES (4, 'C++', '/blog/static/images/fore/C.jpg', '本专栏主要分享基础知识点，常见问题等等');
INSERT INTO `t_category` VALUES (5, '数据库', '/blog/static/images/fore/data.jpg', '本专栏主要介绍如何对常用数据库(比如MySql,Redis)进行建库，建表，以及增删改查，关系型与非关系数据库的差别等等');
INSERT INTO `t_category` VALUES (6, 'git', '/blog/static/images/fore/git.jpg', '本专栏主要介绍基础的常用命令，开发工具集成git,github如何进行团队协作，国内代码托管中心gitee码云的使用，局域网自建代码托管平台gitlab服务器的部署等等');

-- ----------------------------
-- Table structure for t_link
-- ----------------------------
DROP TABLE IF EXISTS `t_link`;
CREATE TABLE `t_link`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `site_name` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '站点名',
  `site_url` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '站点地址',
  `site_desc` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '站点描述  简单备注下 ',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '排序',
  `site_rel` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '站点是否被推荐',
  `site_imgurl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '站点的logo',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '合作伙伴' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_link
-- ----------------------------
INSERT INTO `t_link` VALUES (1, '亚马逊', 'http://www.baidu.com', '电商', 1, 'nofollow', NULL);
INSERT INTO `t_link` VALUES (2, '淘宝', 'https://www.taobao.com', '购物', 1, 'nofollow', 'https://img3.doubanio.com/view/group_topic/l/public/p400172900.webp');
INSERT INTO `t_link` VALUES (3, '异清轩技术博客', 'http://www.ylsat.com', '异清轩', 1, 'nofollow', 'http://www.ylsat.com/images/logo.png');

-- ----------------------------
-- Table structure for t_login
-- ----------------------------
DROP TABLE IF EXISTS `t_login`;
CREATE TABLE `t_login`  (
  `pid` int(10) NOT NULL AUTO_INCREMENT,
  `login_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `login_time` datetime NOT NULL,
  `login_state` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `uid` int(11) NOT NULL,
  `nickname` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`pid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_login
-- ----------------------------
INSERT INTO `t_login` VALUES (2, '0:0:0:0:0:0:0:1', '2021-11-08 23:05:44', '成功', 1, 'admin');
INSERT INTO `t_login` VALUES (3, '0:0:0:0:0:0:0:1', '2021-11-09 13:43:51', '成功', 1, 'admin');
INSERT INTO `t_login` VALUES (4, '0:0:0:0:0:0:0:1', '2021-11-09 15:17:58', '成功', 1, 'admin');
INSERT INTO `t_login` VALUES (5, '0:0:0:0:0:0:0:1', '2021-11-09 16:36:35', '成功', 1, 'admin');
INSERT INTO `t_login` VALUES (7, '0:0:0:0:0:0:0:1', '2021-11-10 18:15:32', '成功', 1, 'admin');
INSERT INTO `t_login` VALUES (8, '0:0:0:0:0:0:0:1', '2021-11-10 22:55:44', '成功', 1, 'admin');
INSERT INTO `t_login` VALUES (9, '0:0:0:0:0:0:0:1', '2021-11-11 16:12:12', '成功', 1, 'admin');
INSERT INTO `t_login` VALUES (10, '0:0:0:0:0:0:0:1', '2021-11-11 21:46:31', '成功', 1, 'admin');
INSERT INTO `t_login` VALUES (12, '0:0:0:0:0:0:0:1', '2021-11-11 23:02:28', '成功', 1, 'admin');
INSERT INTO `t_login` VALUES (13, '0:0:0:0:0:0:0:1', '2021-11-12 17:47:11', '成功', 1, 'admin');
INSERT INTO `t_login` VALUES (14, '0:0:0:0:0:0:0:1', '2021-11-12 19:31:02', '成功', 1, 'admin');
INSERT INTO `t_login` VALUES (15, '0:0:0:0:0:0:0:1', '2021-11-12 19:35:34', '成功', 1, 'admin');
INSERT INTO `t_login` VALUES (16, '0:0:0:0:0:0:0:1', '2021-11-12 20:17:13', '成功', 1, 'admin');
INSERT INTO `t_login` VALUES (17, '0:0:0:0:0:0:0:1', '2021-11-12 20:58:25', '成功', 1, 'admin');
INSERT INTO `t_login` VALUES (18, '0:0:0:0:0:0:0:1', '2021-11-12 22:02:27', '成功', 1, 'admin');
INSERT INTO `t_login` VALUES (19, '0:0:0:0:0:0:0:1', '2021-11-12 22:10:08', '成功', 1, 'admin');
INSERT INTO `t_login` VALUES (20, '0:0:0:0:0:0:0:1', '2021-11-13 19:43:38', '成功', 1, 'admin');

-- ----------------------------
-- Table structure for t_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE `t_tag`  (
  `tid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标签名称',
  `cid` int(11) NULL DEFAULT NULL COMMENT '所属栏目',
  PRIMARY KEY (`tid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_tag
-- ----------------------------
INSERT INTO `t_tag` VALUES (1, 'Java', 1);
INSERT INTO `t_tag` VALUES (2, 'JavaWeb', 1);
INSERT INTO `t_tag` VALUES (3, 'JDBC', 1);
INSERT INTO `t_tag` VALUES (4, 'SSM', 1);
INSERT INTO `t_tag` VALUES (5, 'SpringBoot', 1);
INSERT INTO `t_tag` VALUES (6, 'SpringCloud', 1);
INSERT INTO `t_tag` VALUES (7, 'Maven', 1);
INSERT INTO `t_tag` VALUES (8, 'Python', 2);
INSERT INTO `t_tag` VALUES (9, 'html', 3);
INSERT INTO `t_tag` VALUES (10, 'css', 3);
INSERT INTO `t_tag` VALUES (11, 'Javascript', 3);
INSERT INTO `t_tag` VALUES (12, 'JQuery', 3);
INSERT INTO `t_tag` VALUES (13, 'Ajax', 3);
INSERT INTO `t_tag` VALUES (14, 'Vue', 3);
INSERT INTO `t_tag` VALUES (15, 'Bootstrap', 3);
INSERT INTO `t_tag` VALUES (16, 'Element-ui', 3);
INSERT INTO `t_tag` VALUES (17, 'C', 4);
INSERT INTO `t_tag` VALUES (18, 'C++', 4);
INSERT INTO `t_tag` VALUES (19, 'Mysql', 5);
INSERT INTO `t_tag` VALUES (20, 'Redis', 5);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `uid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `nickname` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '昵称',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码  采取MD5加密',
  `role` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '0:管理员 1:普通用户',
  `createTime` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `lastLoginTime` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录时间',
  `state` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号是否被锁定 0:锁定 1:未锁定',
  `loginIp` varchar(17) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录ip',
  `phone` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `loginCount` int(11) NULL DEFAULT NULL COMMENT '登录次数',
  `img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE INDEX `nickname`(`nickname`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'admin', 'admin', '14e1b600b1fd579f47433b88e8d85291', '0', '2021-11-01 18:58:25', '2021-11-20 23:33:57', '1', '0:0:0:0:0:0:0:1', '13739242980', 127, NULL);
INSERT INTO `t_user` VALUES (2, 'zs', 'zs', '14e1b600b1fd579f47433b88e8d85291', '1', '2021-11-11 11:25:39', NULL, '1', NULL, '13739242147', 0, NULL);
INSERT INTO `t_user` VALUES (3, '李四', 'lisi', 'd9b1d7db4cd6e70935368a1efb10e377', '1', '2021-11-12 22:21:45', '2021-11-15 14:42:38', '1', '0:0:0:0:0:0:0:1', '13889412555', 7, NULL);

SET FOREIGN_KEY_CHECKS = 1;
