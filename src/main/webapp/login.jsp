<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>虎牙博客后台管理</title>
    <link rel="icon" href="/blog/static/images/icon/favicon.ico" type="image/x-icon"/>
    <link href="/blog/static/css/back/default.css" rel="stylesheet" type="text/css"/>
    <!--必要样式-->
    <link href="/blog/static/css/back/styles.css" rel="stylesheet" type="text/css"/>
    <link href="/blog/static/css/back/demo.css" rel="stylesheet" type="text/css"/>
    <link href="/blog/static/css/back/loaders.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class='login'>
    <div class='login_title'>
        <span>管理员登录</span>
    </div>
    <div class='login_fields'>
        <div class='login_fields__user'>
            <div class='icon'>
                <img alt="" src='/blog/static/images/back/user_icon_copy.png'>
            </div>
            <input name="login" id="nickname" placeholder='用户名' maxlength="16" class="nickname" type='text' autofocus
                   autocomplete="off"/>
            <div class='validation'>
                <img alt="" src='/blog/static/images/back/tick.png'>
            </div>
        </div>
        <div class='login_fields__password'>
            <div class='icon'>
                <img alt="" src='/blog/static/images/back/lock_icon_copy.png'>
            </div>
            <input name="pwd" id="password" class="passwordNumder" placeholder='密码' maxlength="16" type='password'
                   autocomplete="off">
            <div class='validation'>
                <img alt="" src='/blog/static/images/back/tick.png'>
            </div>
        </div>
        <div class='login_fields__password'>
            <div class='icon'>
                <img alt="" src='/blog/static/images/back/key.png'>
            </div>
            <input name="code" id="code" placeholder='验证码' maxlength="4" class="ValidateNum" type='text'
                   autocomplete="off">
            <div class='validation' style="opacity: 1; right: -5px;top: -3px;">
                <canvas style="position: relative;top: 5px;cursor: pointer" class="J_codeimg" id="myCanvas"
                        onclick="code()">对不起，您的浏览器不支持canvas，请下载最新版浏览器!
                </canvas>
            </div>
        </div>
        <div class='login_fields__submit'>
            <input type='button' id="loginBtn" value='登录'>
        </div>
    </div>
    <div class='success'>
    </div>
    <div class='disclaimer'>
        <p>欢迎登录虎牙博客管理平台</p>
    </div>
</div>
<div class='authent'>
    <div class="loader" style="height: 60px;width: 60px;margin-left: 28px;margin-top: 40px">
        <div class="loader-inner ball-clip-rotate-multiple">
            <div></div>
            <div></div>
            <div></div>
        </div>
    </div>
    <p>认证中...</p>
</div>
<div class="OverWindows"></div>
<script src="/blog/static/js/jquery/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="/blog/static/js/jquery/jquery-ui.min.js"></script>
<script type="text/javascript" src='/blog/static/js/jquery/stopExecutionOnTimeout.js?t=1'></script>
<script src="/blog/static/js/jquery/Particleground.js" type="text/javascript"></script>
<script src="/blog/static/js/jquery/jquery.mockjax.js" type="text/javascript"></script>
<script src="/blog/static/js/layer-3.5.1/layer.js" type="text/javascript"></script>
<script src="/blog/static/js/back/pidcrypt.js" type="text/javascript"></script>
<script src="/blog/static/js/back/md5.js" type="text/javascript"></script>
<script src="/blog/static/js/back/login.js" type="text/javascript"></script>
</body>
</html>
