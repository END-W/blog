package top.waiend.blog.base.util;

import org.springframework.web.multipart.MultipartFile;
import top.waiend.blog.back.bean.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Description : 用于上传图片工具类
 */
public class FileUploadUtil {

    //上传md的图片
    public static Map<String,Object> fileUpload(MultipartFile img, HttpSession session, HttpServletRequest request){
        /*
            准备把上传图片保存在upload目录下，还有子目录 upload/时间/用户名/很多的图片
        */
        String upload = "/upload";
        String realPath = session.getServletContext().getRealPath(upload);
        // 获取登录用户
        User user = (User) session.getAttribute("user");
        realPath += File.separator + user.getUsername() + File.separator + DateTimeUtil.getDate();;

        File file = new File(realPath);
        if(!file.exists()){
            // 创建带层级的目录 mkdir:只能创建一级目录
            file.mkdirs();
        }
        // 相同用户可能会上传相同的文件名的图片，防止文件重名
        // 获取用户名
        String fileName = img.getOriginalFilename();

        // 1223434324文件名.png/jpg
        fileName = System.currentTimeMillis() + fileName;

        // 定义用于给Editormd返回的map数据
        Map<String,Object> map = new HashMap<>();
        // 获取客户端发出请求时的 URL---> 协议、服务器名、端口号、项目名
        StringBuffer preUrl = request.getRequestURL();
        preUrl.delete(preUrl.indexOf(request.getServletPath()), preUrl.length());
        // 获取回调地址
        String url = preUrl.toString() + upload +File.separator + user.getUsername()
                     + File.separator + DateTimeUtil.getDate() + File.separator + fileName;
        System.out.println(url);
        try {
            img.transferTo(new File(realPath + File.separator + fileName));
            // 返回success:1(数字) url:图片回调地址(图片在服务器存储路径)
            map.put("success",1);
            map.put("url",url);
            map.put("message","上传图片成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}
