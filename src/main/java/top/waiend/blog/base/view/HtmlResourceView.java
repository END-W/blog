package top.waiend.blog.base.view;

import org.springframework.web.servlet.view.InternalResourceView;

import java.io.File;
import java.util.Locale;
import java.util.Objects;

public class HtmlResourceView extends InternalResourceView {
    @Override
    public boolean checkResource(Locale locale) {
        File file = new File(Objects.requireNonNull(this.getServletContext()).getRealPath("/") + getUrl());
        // 判断页面是否存在
        return file.exists();
    }
}
