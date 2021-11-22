// 调用刷新方法
refresh(1,5);

// 定义一个函数，发送请求不同页码对应的数据
function refresh(page, pageSize) {
    // 发送查询所有友链的异步请求
    $.post('/blog/link/list', {
        'page': page,
        'pageSize': pageSize,
        'context': $('#context').val()
    }, function (data) {
        // 设置友链数量
        $('#count').text(data.total + "条");
        // 清空原来的内容
        $('#linkBody').html("");
        // List<Link>
        var links = data.list;
        for (var i = 0; i < links.length; i++) {
            $('#linkBody').append("<tr>\n" +
                "                   <td>" + (i + 1) + "</td>\n" +
                "                   <td class='link-title'>" + links[i].site_name + "</td>\n" +
                "                   <td>" + links[i].site_url + "</td>\n" +
                "                   <td>" + links[i].site_desc + "</td>\n" +
                "                   <td><a href=\"/blog/toView/workbench/link/update-flink?id=" + links[i].id + "\">修改</a> <a href='javascript:;' onclick=\"deleteById('" + links[i].id + "')\">删除</a></td>\n" +
                "                   </tr>");
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

// 点击搜索按钮，模糊查询用户登录日志
$('#search').click(function () {
    refresh(1,5);
    $('#context').val("");
});

// 删除指定id友链
function deleteById(id) {
    $.get('/blog/link/deleteById', {'id': id}, function (data) {
        common(data);
    }, 'json');
}