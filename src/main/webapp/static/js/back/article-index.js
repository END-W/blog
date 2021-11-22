// 调用刷新方法
refresh(1,5);

// 定义一个函数，发送请求不同页码对应的数据
function refresh(page, pageSize) {
    // 发送查询所有文章列表的异步请求
    $.post("/blog/article/list", {
        'page': page,
        'pageSize': pageSize,
        'title': $('#title').val()
    }, function (data) {
        // 设置当前文章数量
        $('#count').text(data.total + "篇文章");
        // 清空原来的内容
        $('#articleBody').html("");
        // List<Article>
        var articles = data.list;
        for (var i = 0; i < articles.length; i++) {
            var article = articles[i];
            if (article.isOpen == "0") {
                $('#articleBody').append("<tr>\n" +
                    "                <td class=\"article-title\">"+article.title+"</td>\n" +
                    "                <td>"+article.cid+"</td>\n" +
                    "                <td class=\"hidden-sm\">"+article.tagNames+"</td>\n" +
                    "                <td class=\"hidden-sm\">"+article.thumbsUp+"</td>\n" +
                    "                <td class=\"hidden-sm\">"+article.comments+"</td>\n" +
                    "                <td class=\"hidden-sm\">"+article.visit_count+"</td>\n" +
                    "                <td>"+article.create_time+"</td>\n" +
                    "                <td>\n" +
                    "                    <a href=\"/blog/toView/workbench/article/updateArticle?aid=" + article.aid + "\">修改</a> <a href='javascript:;' onclick=\"deleteById('"+article.aid+"')\">删除</a>&nbsp;\n" +
                    "                    <input type=\"radio\" onclick=change(\'"+article.aid+"\',$(this).val()) name="+article.aid+" value=\"1\"/>公开 <input onclick=change(\'"+article.aid+"\',$(this).val()) type=\"radio\" checked name="+article.aid+" value=\"0\" />私密\n" +
                    "                </td>\n" +
                    "              </tr>");
            } else {
                $('#articleBody').append("<tr>\n" +
                    "                <td class=\"article-title\">"+article.title+"</td>\n" +
                    "                <td>"+article.cid+"</td>\n" +
                    "                <td class=\"hidden-sm\">"+article.tagNames+"</td>\n" +
                    "                <td class=\"hidden-sm\">"+article.thumbsUp+"</td>\n" +
                    "                <td class=\"hidden-sm\">"+article.comments+"</td>\n" +
                    "                <td class=\"hidden-sm\">"+article.visit_count+"</td>\n" +
                    "                <td>"+article.create_time+"</td>\n" +
                    "                <td>\n" +
                    "                    <a href=\"/blog/toView/workbench/article/updateArticle?aid="+ article.aid + "\">修改</a> <a href='javascript:;' onclick=\"deleteById('"+article.aid+"')\">删除</a>&nbsp;\n" +
                    "                    <input type=\"radio\" onclick=change(\'"+article.aid+"\',$(this).val()) name="+article.aid+" value=\"1\" checked/>公开 <input onclick= change(\'"+article.aid+"\',$(this).val()) type=\"radio\" name="+article.aid+" value=\"0\" />私密\n" +
                    "                </td>\n" +
                    "              </tr>");
            }
        }

        // bootstrap的分页插件
        $("#activityPage").bs_pagination({
            currentPage: data.pageNum, // 页码
            rowsPerPage: data.pageSize, // 每页显示的记录条数
            maxRowsPerPage: 20, // 每页最多显示的记录条数
            totalPages: data.pages, // 总页数
            totalRows: data.total, // 总记录条数
            visiblePageLinks: 3, // 显示几个卡片
            showGoToPage: true,
            showRowsPerPage: true,
            showRowsInfo: true,
            showRowsDefaultInfo: true,
            // 回调函数，用户每次点击分页插件进行翻页的时候就会触发该函数
            onChangePage : function(event, obj){
                // currentPage:当前页码 rowsPerPage:每页记录数
                refresh(obj.currentPage, obj.rowsPerPage);
            }
        });
    }, 'json');
}


// 点击搜索按钮，模糊查询文章数据
$('#search').click(function () {
    refresh(1,5);
    $('#title').val("");
});

// 定义改变是否公开函数
function change(aid,value) {
    $.get("/blog/article/isOpen", {
        'aid' : aid,
        'isOpen' : value
    },function (data) {
        layer.alert(data.mess, {icon:6});
    },'json');
}

// 异步删除指定aid文章信息
function deleteById(aid) {
    $.get("/blog/article/deleteById",{'id': aid},function (data) {
        if(data.ok){
            // 删除成功
            layer.alert(data.mess, {icon:6});
            // 刷新页面
            refresh(1,5);
        } else {
            // 删除失败
            layer.alert(data.mess, {icon:6});
        }
    },'json');
}
