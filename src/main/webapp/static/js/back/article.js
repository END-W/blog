var editor;
$(function () {
    editor = editormd("test-editor", {
        width: "100%",
        height: "500px",
        path: "/blog/static/editormd/lib/", // 第三方依赖库
        saveHTMLToTextarea: true, // 获取用户编辑的内容，将其放入到textarea中
        emoji: true, // emoji表情，默认关闭
        syncScrolling: "single",
        /*上传文件配置*/
        imageUpload: true, // 开启上传文件功能
        imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"], // 上传图片格式
        imageUploadURL: "/blog/editorUpload", // 后端上传图片服务地址
        // 下面这一行将使用dark主题
        previewTheme : "dark"
    });
});

// 上传文章logo
// 异步上传文件
$('#img').change(function () {
    $.ajaxFileUpload({
            url: '/blog/fileUpload', // 用于文件上传的服务器端请求地址
            fileElementId: 'img', // 文件上传域的ID
            dataType: 'json', // 返回值类型 一般设置为json
            success: function (data) {
                if (data.success == 1) {
                    alert(data.message);
                    // 把文章logo的url地址设置到隐藏域中
                    $('#logo').val(data.url);
                }
            }
        }
    );
});

// 加载栏目下对应的标签
function addTags(cid) {
    $.get("/blog/article/queryTags", {'cid': cid}, function (data) {
        // List<Tag>
        // 清空内容
        $('#tags').html("");
        for (var i = 0; i < data.length; i++) {
            $('#tags').append("<input type='checkbox' value=" + data[i].tname + " />" + data[i].tname + "&nbsp;&nbsp;&nbsp;");
        }
    }, 'json');
}

// 选中栏目，加载栏目下对应的标签
$('#categories').change(function () {
    addTags($('#categories').val());
});

// 发布文章
function publish() {
    var tags = [];
    // 获取栏目标签
    $('input[type=checkbox]:checked').each(function () {
        tags.push($(this).val());
    });
    // join方法:把数组中的内容默认以,号的分割符把数组内容拼接成字符串
    var tagNames = tags.join();

    $.post("/blog/article/saveOrUpdate", {
        'aid' : $('#aid').val(),
        'cid': $('#categories').val(),
        'tagNames': tagNames,
        'title': $('#title').val(),
        'logo': $('#logo').val(),
        'content': $('#content').val(),
        'isOpen': $('input[type=radio]:checked').val()
    }, function (data) {
        // 返回resultVo
        if (data.ok) {
            layer.alert(data.mess, {
                time: 2000,
                icon: 6
            });
            // 发布成功，修改状态信息和发布时间
            if (!data.t.update_time) {
                $('#state').text("已发布");
                $('#publishTime').html(data.t.create_time);
            } else {
                $('#publishTime').html(getNowFormatDate());
            }
            setTimeout(function () {
                // 跳转到文章列表页面
                location.href = "/blog/toView/workbench/article/index";
            }, 3000);
        } else {
            layer.alert(data.mess, {icon: 6});
        }
    }, 'json');
}
