package top.waiend.blog.back.bean;

import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_link")
@NameStyle(Style.normal)
public class Link {

    @Id
    private String id;
    private String site_name;
    private String site_url;
    private String site_desc;
    private String sort;
    private String site_rel;
    private String site_imgurl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSite_name() {
        return site_name;
    }

    public void setSite_name(String site_name) {
        this.site_name = site_name;
    }

    public String getSite_url() {
        return site_url;
    }

    public void setSite_url(String site_url) {
        this.site_url = site_url;
    }

    public String getSite_desc() {
        return site_desc;
    }

    public void setSite_desc(String site_desc) {
        this.site_desc = site_desc;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSite_rel() {
        return site_rel;
    }

    public void setSite_rel(String site_rel) {
        this.site_rel = site_rel;
    }

    public String getSite_imgurl() {
        return site_imgurl;
    }

    public void setSite_imgurl(String site_imgurl) {
        this.site_imgurl = site_imgurl;
    }
}
