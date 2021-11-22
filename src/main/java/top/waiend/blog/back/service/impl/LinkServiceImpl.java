package top.waiend.blog.back.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import top.waiend.blog.back.bean.Link;
import top.waiend.blog.back.mapper.LinkMapper;
import top.waiend.blog.back.service.LinkService;
import top.waiend.blog.base.exception.BlogEnum;
import top.waiend.blog.base.exception.BlogException;

import java.util.List;

@Service
public class LinkServiceImpl implements LinkService {
    @Autowired
    private LinkMapper linkMapper;

    @Override
    public List<Link> list(String context) {
        Example example = new Example(Link.class);
        if (context != null && !context.equals("")) {
            example.createCriteria().andLike("site_name", "%" + context +"%");
        }
        List<Link> links = linkMapper.selectByExample(example);
        return links;
    }

    @Override
    public void deleteById(String id) {
        int count = linkMapper.deleteByPrimaryKey(id);
        if (count == 0) {
            throw new BlogException(BlogEnum.LINK_DELETE);
        }
    }

    @Override
    public Link queryById(String id) {
        return linkMapper.selectByPrimaryKey(id);
    }

    @Override
    public void saveOrUpdate(Link link) {
        if (link.getId() == null) {
            link.setSort("1");
            int count = linkMapper.insertSelective(link);
            if (count == 0) {
                throw new BlogException(BlogEnum.LINK_INSERT);
            }
        } else {
            int count = linkMapper.updateByPrimaryKeySelective(link);
            if (count == 0) {
                throw new BlogException(BlogEnum.LINK_UPDATE);
            }
        }
    }

    @Override
    public List<Link> queryAll() {
        return linkMapper.selectAll();
    }
}
