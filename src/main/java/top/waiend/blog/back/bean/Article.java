package top.waiend.blog.back.bean;

import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_article")
@NameStyle(Style.normal)
public class Article {
    @Id
    private String aid; // 主键
    private String title; // 标题
    private String content; // 具体内容
    private String cid; // 文章所属标签(栏目)
    private String visit_count; // 访问量
    private String create_time; // 创建时间
    private String update_time; // 更新时间
    private String is_hot; // 是否热门
    private String logo; // 文章logo
    private String uid; // 发布者外键
    private String isOpen; // 是否公开
    private String thumbsUp; // 文章的点赞数
    private String tagNames; // 标签名称
    private String comments; // 是否被评论

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getVisit_count() {
        return visit_count;
    }

    public void setVisit_count(String visit_count) {
        this.visit_count = visit_count;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(String is_hot) {
        this.is_hot = is_hot;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    public String getThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(String thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

    public String getTagNames() {
        return tagNames;
    }

    public void setTagNames(String tagNames) {
        this.tagNames = tagNames;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
