package top.waiend.blog.base.controller;

import top.waiend.blog.base.util.FileUploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class FileUploadController {

    //解决Editormd文件上传
    @RequestMapping("/editorUpload")
    @ResponseBody
    public Map<String,Object> editorUpload(
            @RequestParam(value = "editormd-image-file", required = false)MultipartFile img,
            HttpSession session, HttpServletRequest request){
        Map<String, Object> map = FileUploadUtil.fileUpload(img, session, request);
        return map;
    }

    //上传logo图片
    @RequestMapping("/fileUpload")
    @ResponseBody
    public Map<String,Object> fileUpload(MultipartFile img, HttpSession session, HttpServletRequest request){
        Map<String, Object> map = FileUploadUtil.fileUpload(img, session, request);
        return map;
    }
}
