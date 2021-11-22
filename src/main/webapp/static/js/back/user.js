//退出系统
function loginOut() {
    layer.alert('确定退出系统吗？', {
        time: 0 //不自动关闭
        ,btn: ['确定', '取消']
        ,yes: function(index) {
            layer.close(index);
            location.href = "/blog/user/loginOut";
        }
    });
}

var username = $('#username').val();
var nickname = $('#nickname').val();
var phone = $('#phone').val();
// 异步修改当前登录用户信息
function updateUser() {
    // 表单序列化 能够把表单的内容拼接成 key=值&key=值...,返回值是字符串
    var form = $('#updateUserForm').serialize();
    form = serializeNotNull(form);
    if ($('#username').val() == "" || $('#nickname').val() == "" || $('#phone').val() == "") {
        layer.msg("请填写必要内容", {offset: 't'});
    } else if ($('#pwd3').val() != "" && $('#oldPwd').val() != "") {
        $.post("/blog/user/changeUser", form, function (data) {
            if (data.ok) {
                $('#seeUserInfo').modal('hide');
                layer.alert(data.mess, {icon: 6});
                setTimeout(function () {
                    // 重新登录,跳转到登录页面
                    // setTimeout:隔多长时间执行指定代码
                    location.href = "/blog/login.jsp";
                },5000);
            }
        },'json');
    } else if ($('#pwd3').val() == "" && $('#newPwd3').val() == "") {
        $.post("/blog/user/changeUser", form, function (data) {
            if (data.ok) {
                $('#seeUserInfo').modal('hide');
                layer.alert(data.mess, {icon: 6});
                setTimeout(function () {
                    // 重新登录,跳转到登录页面
                    // setTimeout:隔多长时间执行指定代码
                    location.href = "/blog/login.jsp";
                },5000);
            }
        },'json');
    } else {
        layer.msg("请填写必要内容", {offset: 't'});
    }
}

// 异步校验当前登录用户旧密码输入是否正确
$('#oldPwd').blur(function () {
    var pwd = $('#oldPwd').val();
    var newPwd = pidCrypt.MD5(pwd);
    $('#oldPwd').val(newPwd);
    $.post("/blog/user/verifyOldPwd", {'oldPwd': $(this).val()}, function (data) {
        if (!data.ok) {
            layer.msg(data.mess, {offset: 't'});
        }
    }, 'json');
    $('#oldPwd').val(pwd);
});

// 异步校验当前登录用户的用户名是否重复
$('#nickname').blur(function () {
    if ($('#nickname').val() != nickname) {
        $.post("/blog/user/verifyNickName", {
            'nickname': $(this).val()
        }, function (data) {
            if (!data.ok) {
                layer.msg(data.mess, {offset: 't'});
            }
        }, 'json');
    }
});

// 异步查询用户登录信息
$.post('/blog/loginLog/queryLoginLog', function (data) {
    var loginList = data.list;
    for (var i = 0; i < loginList.length; i++) {
        $('#userLoginlog').append("<tr>\n" +
            "                   <td>" + loginList[i].login_ip + "</td>\n" +
            "                   <td>" + loginList[i].login_time + "</td>\n" +
            "                   <td>" + loginList[i].login_state + "</td>\n" +
            "                   </tr>");
    }
}, 'json');

// 保持个人信息模态框最新
function newAndCls() {
    $('#username').val(username);
    $('#nickname').val(nickname);
    $('#phone').val(phone);

    cls();
}

// 清除内容
function cls() {
    $('.cls').val("");
}

// 去除序列化表单中的空值
function serializeNotNull(serStr) {
    return serStr.split('&').filter(function (str) {return !str.endsWith('=')}).join('&');
}

// 重复代码片段
function common(data) {
    if (data.ok){
        // 成功
        layer.alert(data.mess, {icon:6});
        // 刷新页面
        refresh(1, 5);
    } else {
        // 失败
        layer.alert(data.mess, {icon:6});
    }
}

// 获取当前时间
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1<10? "0"+(date.getMonth() + 1):date.getMonth() + 1;
    var strDate = date.getDate()<10? "0" + date.getDate():date.getDate();
    var currentdate = date.getFullYear() + seperator1  + month  + seperator1  + strDate
        + " "  + date.getHours()  + seperator2  + date.getMinutes()
        + seperator2 + date.getSeconds();
    return currentdate;
}