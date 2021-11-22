// 调用刷新方法
refresh(1,5);

// 定义一个函数，发送请求不同页码对应的数据
function refresh(page, pageSize) {
    // 发送查询所有用户登录日志的异步请求
    $.post('/blog/loginLog/list', {
        'page': page,
        'pageSize': pageSize,
        'context': $('#context').val()
    }, function (data) {
        // 设置当前用户登录日志数量
        $('#count').text(data.total + "条");
        // 清空原来的内容
        $('#loginlogBody').html("");
        // List<Login>
        var logins = data.list;
        for (var i = 0; i < logins.length; i++) {
            $('#loginlogBody').append("<tr>\n" +
                "                   <td>" + (i + 1) + "</td>\n" +
                "                   <td>" + logins[i].nickname + "</td>\n" +
                "                   <td>" + logins[i].login_time + "</td>\n" +
                "                   <td>" + logins[i].login_ip + "</td>\n" +
                "                   <td><a href='javascript:;' onclick=\"deleteById('" + logins[i].pid + "')\">删除</a></td>\n" +
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

// 异步删除指定pid用户登录日志
function deleteById(pid) {
    $.get("/blog/loginLog/deleteById", {'pid': pid}, function (data) {
        common(data);
    }, 'json');
}

// 异步清除所有登录记录
function deleteAll() {
    layer.alert('确定删除所有吗？', {
        time: 0 // 不自动关闭
        ,btn: ['确定', '取消']
        ,yes: function (index) {
            layer.close(index);
            $.get('/blog/loginLog/deleteAll', function (data) {
                common(data);
            }, 'json');
        }
    });
}

// 异步清除本人登录记录
function deleteCurrent() {
    layer.alert('确定删除所有吗？', {
        time: 0 // 不自动关闭
        ,btn: ['确定', '取消']
        ,yes: function (index) {
            layer.close(index);
            $.get('/blog/loginLog/deleteCurrent', function (data) {
                common(data);
            }, 'json');
        }
    });
}

