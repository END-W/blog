// 粒子背景特效
$('body').particleground({
    dotColor: '#E8DFE8',
    lineColor: '#1b3273'
});

// 先发送请求
code();

// 向后台发送生成验证码的请求
function code() {
    $.get("/blog/code",function (data) {
        showCheck(data);
    },'json');
}

// canvas绘制图片验证码
function showCheck(code) {
    var c = document.getElementById("myCanvas");
    var ctx = c.getContext("2d");
    ctx.clearRect(0, 0, 1000, 1000);
    ctx.font = "80px 'Hiragino Sans GB'";
    ctx.fillStyle = "#E8DFE8";
    ctx.fillText(code, 0, 100);
}

$('#loginBtn').click(function () {

    // 用户名不能为空
    if($('#nickname').val().length == 0){
        errorAlert('用户名不能为空');
        return;
    }
    // 密码不能为空
    if($('#password').val().length == 0){
        errorAlert('密码不能为空');
        return;
    }

    // 验证码不能为空
    if($('#code').val().length == 0){
        errorAlert('验证码不能为空');
        return;
    }

    // 认证特效
    $('.login').addClass('test'); //倾斜特效
    setTimeout(function () {
        $('.login').addClass('testtwo'); //平移特效
    }, 300);
    setTimeout(function () {
        $('.authent').show().animate({right: -320}, {
            easing: 'easeOutQuint',
            duration: 600,
            queue: false
        });
        $('.authent').animate({opacity: 1}, {
            duration: 200,
            queue: false
        }).addClass('visible');
    }, 500);

    // 进行MD5加密
    var pwd = pidCrypt.MD5($('#password').val());
    $('#password').val(pwd);

    // 以上校验ok发送后台异步登录请求
    $.post("/blog/user/login", {
        'nickname':$('#nickname').val(),
        'password':$('#password').val(),
        'code':$('#code').val()
    }, function (data) {
        setTimeout(function () {
            $('.authent').show().animate({right: 90}, {
                easing: 'easeOutQuint',
                duration: 600,
                queue: false
            });
            $('.authent').animate({opacity: 0}, {
                duration: 200,
                queue: false
            }).addClass('visible');
            $('.login').removeClass('testtwo'); // 平移特效
            $('.authent').hide();
        }, 2000);

        if(!data.ok){
            // 清空原先表单
            $('#nickname').val("");
            $('#password').val("");
            $('#code').val("");

            setTimeout(function () {
                $('.login').removeClass('test');
                errorAlert(data.mess);
                // 登录失败，再次发送验证码
                $.get("/blog/code",function (data) {
                    showCheck(data);
                },'json');
            }, 2400);
        } else{
            // 异步更新用户信息
            $.post('/blog/user/updateUser', function () {
            });
            setTimeout(function () {
                // 异步添加用户登录日志
                $.post('/blog/loginLog/insertLoginLog', function () {
                });
            }, 400);
            setTimeout(function () {
                $('.login').fadeOut(100);
                //登录校验成功，跳转到后台首页
                location.href = "/blog/user/index";
            }, 2000);
        }
    },'json');
});

// 抽取一个弹窗函数
function errorAlert(message) {
    layer.msg(message, {offset:'t'});
}
