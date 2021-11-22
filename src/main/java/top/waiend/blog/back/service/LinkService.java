package top.waiend.blog.back.service;

import top.waiend.blog.back.bean.Link;

import java.util.List;

public interface LinkService {
    List<Link> list(String context);

    void deleteById(String id);

    Link queryById(String id);

    void saveOrUpdate(Link link);

    List<Link> queryAll();
}
