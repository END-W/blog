// 调用刷新方法
refresh(1,5);

// 定义一个函数，发送请求不同页码对应的数据
function refresh(page, pageSize) {
    var i = 0;
    // 发送查询所有用户的异步请求
    $.post('/blog/user/list', {
        'page': page,
        'pageSize': pageSize,
        'context': $('#context').val()
    }, function (data) {
        // 设置当前用户数量
        $('#count').text(data.total + "名用户");
        // 清空原来的内容
        $('#manageUserBody').html("");
        // List<User>
        var users = data.list;
        for (i = 0; i < users.length; i++) {
            // 异步查询指定uid用户的文章数量
            $.ajax({
               url:  '/blog/article/queryByUid',
               data: {'uid': users[i].uid},
               async: false,
               type: 'POST',
               dataType: 'json',
               success: function (data) {
                   var state = users[i].state;
                   if (users[i].nickname == "admin" || users[i].uid == $('#uid2').val()) {
                       $('#manageUserBody').append("<tr>\n" +
                           "                   <td>" + (i + 1) + "</td>\n" +
                           "                   <td>" + users[i].nickname + "</td>\n" +
                           "                   <td>" + users[i].username + "</td>\n" +
                           "                   <td>" + data.mess + "</td>\n" +
                           "                   <td>" + users[i].lastLoginTime + "</td>\n" +
                           "                   <td><a class='no' style='opacity: .65; cursor: not-allowed'>修改</a>\n" +
                           "                       <a class='no' style='opacity: .65; cursor: not-allowed'>删除</a>\n" +
                           "                       <a><input type='button' class='btn btn-default btn-xs disabled' style='color: #333' id='state" + i + "'></a>\n" +
                           "                   </td>\n" +
                           "                   </tr>");
                   } else {
                       $('#manageUserBody').append("<tr>\n" +
                           "                   <td>" + (i + 1) + "</td>\n" +
                           "                   <td>" + users[i].nickname + "</td>\n" +
                           "                   <td>" + users[i].username + "</td>\n" +
                           "                   <td>" + data.mess + "</td>\n" +
                           "                   <td>" + users[i].lastLoginTime + "</td>\n" +
                           "                   <td><a data-toggle='modal' data-target='#seeUser' onclick=\"queryById('" + users[i].uid + "')\">修改</a>\n" +
                           "                       <a href='javascript:;' onclick=\"deleteById('" + users[i].uid + "')\">删除</a>\n" +
                           "                       <a><input type='button' class='btn btn-default btn-xs' style='color: #333' id='state" + i + "' onclick=\"state('" + users[i].uid + "', '" + i + "')\"></a>\n" +
                           "                   </td>\n" +
                           "                   </tr>");
                   }

                   // 判断用户是否被锁定
                   if (state == "1") {
                       $('#state' + i).val("禁用");
                   } else {
                       $('#state' + i).val("启用");
                   }
               }
            });
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

// 点击搜索按钮，模糊查询用户
$('#search').click(function () {
    refresh(1,5);
    $('#context').val("");
});

// 异步删除指定uid用户
function deleteById(uid) {
    $.get("/blog/user/deleteById", {'uid': uid}, function (data) {
        common(data);
    }, 'json');
}

var id = "0";
var username2 = '';
var nickname2 = '';
var phone2 = '';
// 异步查询指定uid用户
function queryById(uid) {
    cls();
    if ($('#uid').val() == "" || id != uid) {
        $('#uid').val(uid);
        $.post('/blog/user/queryById', {'uid': uid}, function (data) {
            username2 = data.username;
            nickname2 = data.nickname;
            phone2 = data.phone;
            $('#username2').val(data.username);
            $('#nickname2').val(data.nickname);
            $('#phone2').val(data.phone);
        }, 'json');
        id = uid;
    } else {
        $('#username2').val(username2);
        $('#nickname2').val(nickname2);
        $('#phone2').val(phone2);
    }
}

// 异步修改指定uid用户信息
function updateUser2() {
    // 表单序列化 能够把表单的内容拼接成 key=值&key=值...,返回值是字符串
    var form = $('#updateUserForm2').serialize();
    form = serializeNotNull(form);
    if ($('#username2').val() == "" || $('#nickname2').val() == "" || $('#phone2').val() == "") {
        layer.msg("请填写必要内容", {offset: 't'});
    } else if ($('#pwd2').val() != "" && $('#oldPwd2').val() != "") {
        $.post("/blog/user/changeUser", form, function (data) {
            if (data.ok) {
                $('#uid').val("");
                $('#seeUser').modal('hide');
                layer.alert(data.mess, {icon: 6});
                refresh(1,5);
            }
        },'json');
    } else if ($('#pwd2').val() == "" && $('#newPwd2').val() == "") {
        $.post("/blog/user/changeUser", form, function (data) {
            if (data.ok) {
                $('#uid').val("");
                $('#seeUser').modal('hide');
                layer.alert(data.mess, {icon: 6});
                refresh(1,5);
            }
        },'json');
    } else {
        layer.msg("请填写必要内容", {offset: 't'});
    }
}

// 异步校验旧密码输入是否正确
$('#oldPwd2').blur(function () {
    var pwd = $('#oldPwd2').val();
    var newPwd = pidCrypt.MD5(pwd);
    $('#oldPwd2').val(newPwd);
    $.post("/blog/user/verifyOldPwd2", {
        'oldPwd': $(this).val(),
        'uid': $('#uid').val()
    }, function (data) {
        if (!data.ok) {
            layer.msg(data.mess, {offset: 't'});
        }
    }, 'json');
    $('#oldPwd2').val(pwd);
});

// 异步校验用户名是否重复
$('#nickname2').blur(function () {
    if ($('#nickname2').val() != nickname2) {
        $.post("/blog/user/verifyNickName2", {
            'nickname': $(this).val()
        }, function (data) {
            if (!data.ok) {
                layer.msg(data.mess, {offset: 't'});
            }
        }, 'json');
    }
});

// 异步校验添加用户的用户名是否重复
$('#nickname3').blur(function () {
    $.post("/blog/user/verifyNickName3", {
        'nickname': $(this).val()
    }, function (data) {
        if (!data.ok) {
            layer.msg(data.mess, {offset: 't'});
        }
    }, 'json');
});

// 异步修改用户状态
function state(uid, i) {
    var state = '';
    if ($('#state' + i).val() == "禁用") {
        state = "0";
    } else {
        state = "1";
    }
    $.get("/blog/user/state", {'uid': uid, 'state': state}, function (data) {
        if (data.ok) {
            if (state == "0") {
                $('#state' + i).val("启用");
            } else {
                $('#state' + i).val("禁用");
            }
            layer.alert(data.mess, {icon:6});
        } else {
            layer.alert(data.mess, {icon:6});
        }
    }, 'json');
}

// 异步添加用户
function addUser() {
    if ($('#username3').val() != "" && $('#nickname3').val() != "" && $('#phone3').val() != "" && $('#pwd').val() != "" && $('#newPwd').val() != "") {
        var pwd = pidCrypt.MD5($('#pwd').val());
        $('#pwd').val(pwd);
        // 表单序列化 能够把表单的内容拼接成 key=值&key=值...,返回值是字符串
        var form = $('#addUserForm').serialize();
        $.post('/blog/user/addUser', form, function (data) {
            $('#addUser').modal('hide');
            common(data);
        }, 'json');
    } else {
        layer.msg("请填写必要内容", {offset: 't'});
    }
}
