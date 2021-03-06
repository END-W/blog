<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>修改友情链接 - 虎牙博客管理后台</title>
    <script type="text/javascript" src="/blog/static/js/jquery/jquery-2.1.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/blog/static/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/blog/static/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="/blog/static/css/back/style1.css">
    <link rel="icon" href="/blog/static/images/icon/favicon.ico" type="image/x-icon"/>
</head>
<body class="user-select">
<section class="container-fluid">
    <header>
        <nav class="navbar navbar-default navbar-fixed-top">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                            data-target="#bs-example-navbar-collapse-1" aria-expanded="false"><span
                            class="sr-only">切换导航</span> <span class="icon-bar"></span> <span class="icon-bar"></span>
                        <span class="icon-bar"></span></button>
                    <a class="navbar-brand" href="/">END</a></div>
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="javascript:void(0)">消息 <span class="badge">1</span></a></li>
                        <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" role="button"
                                                aria-haspopup="true" aria-expanded="false">${sessionScope.user.nickname} <span
                                class="caret"></span></a>
                            <ul class="dropdown-menu dropdown-menu-left">
                                <li><a title="查看或修改个人信息" data-toggle="modal" data-target="#seeUserInfo" onclick="newAndCls()">个人信息</a></li>
                                <li><a title="查看您的登录记录" data-toggle="modal" data-target="#seeUserLoginlog">登录记录</a></li>
                            </ul>
                        </li>
                        <li><a href="javascript:void(0)" onclick="loginOut()">退出登录</a></li>
                    </ul>
                    <form action="" method="post" class="navbar-form navbar-right" role="search">
                        <div class="input-group">
                            <input type="text" class="form-control" id="context" autocomplete="off" placeholder="键入关键字搜索"
                                   maxlength="15">
                            <span class="input-group-btn">
                                <button class="btn btn-default" id="search" type="button">搜索</button>
                            </span>
                        </div>
                    </form>
                </div>
            </div>
        </nav>
    </header>
    <div class="row">
        <aside class="col-sm-3 col-md-2 col-lg-2 sidebar">
            <ul class="nav nav-sidebar">
                <li><a href="/blog/toView/index">报告</a></li>
            </ul>
            <ul class="nav nav-sidebar">
                <li><a href="/blog/toView/workbench/article/index">文章</a></li>
                <li class="disabled"><a href="javascript:;">公告</a></li>
                <li><a href="comment.html">评论</a></li>
                <li><a data-toggle="tooltip" data-placement="top" title="网站暂无留言功能">留言</a></li>
            </ul>
            <ul class="nav nav-sidebar">
                <li><a href="category.html">栏目</a></li>
                <li class="active"><a class="dropdown-toggle" id="otherMenu" data-toggle="dropdown" aria-haspopup="true"
                       aria-expanded="false">其他</a>
                    <ul class="dropdown-menu" aria-labelledby="otherMenu">
                        <li><a href="/blog/toView/workbench/link/flink">友情链接</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="nav nav-sidebar">
                <li><a class="dropdown-toggle" id="userMenu" data-toggle="dropdown" aria-haspopup="true"
                                      aria-expanded="false">用户</a>
                    <ul class="dropdown-menu" aria-labelledby="userMenu">
                        <li><a href="/blog/toView/workbench/user/manage_user">管理用户</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="/blog/toView/workbench/user/loginlog">管理登录日志</a></li>
                    </ul>
                </li>
                <li><a class="dropdown-toggle" id="settingMenu" data-toggle="dropdown" aria-haspopup="true"
                       aria-expanded="false">设置</a>
                    <ul class="dropdown-menu" aria-labelledby="settingMenu">
                        <li class="disabled"><a>基本设置</a></li>
                        <li class="disabled"><a>用户设置</a></li>
                        <li role="separator" class="divider"></li>
                        <li class="disabled"><a>安全配置</a></li>
                        <li role="separator" class="divider"></li>
                        <li class="disabled"><a>扩展菜单</a></li>
                    </ul>
                </li>
            </ul>
        </aside>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-lg-10 col-md-offset-2 main" id="main">
            <div class="row">
                <form method="post" class="add-flink-form" autocomplete="off" draggable="false">
                    <div class="col-md-9">
                        <h1 class="page-header">修改友情链接</h1>
                        <div class="add-article-box">
                            <h2 class="add-article-box-title"><span>名称</span></h2>
                            <div class="add-article-box-content">
                                <input type="text" id="flink-name" name="site_name" class="form-control" placeholder="在此处输入名称" required autofocus autocomplete="off">
                                <span class="prompt-text">例如：异清轩技术博客</span> </div>
                        </div>
                        <div class="add-article-box">
                            <h2 class="add-article-box-title"><span>WEB地址</span></h2>
                            <div class="add-article-box-content">
                                <input type="text" id="flink-url" name="site_url" class="form-control" placeholder="在此处输入URL地址" required autocomplete="off">
                                <span class="prompt-text">例子：<code>http://www.ylsat.com</code></span> </div>
                        </div>
                        <div class="add-article-box">
                            <h2 class="add-article-box-title"><span>图像地址</span></h2>
                            <div class="add-article-box-content">
                                <input type="text" id="flink-imgurl" name="site_imgurl" class="form-control" placeholder="在此处输入图像地址" required autocomplete="off">
                                <span class="prompt-text">图像地址是可选的，可以为网站LOGO地址等(长度不要太长)</span> </div>
                        </div>
                        <div class="add-article-box">
                            <h2 class="add-article-box-title"><span>描述</span></h2>
                            <div class="add-article-box-content">
                                <textarea class="form-control" id="flink-desc" name="site_desc" autocomplete="off"></textarea>
                                <span class="prompt-text">描述是可选的手工创建的内容总结(长度不要太长)</span> </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <h1 class="page-header">操作</h1>
                        <div class="add-article-box">
                            <h2 class="add-article-box-title"><span>保存</span></h2>
                            <div class="add-article-box-content">
                                <p>
                                    <label>状态：</label>
                                    <span class="article-status-display">已增加</span></p>
                                <p><label>rel：</label><input type="radio" name="site_rel" id="rel1" value="nofollow" />nofollow&nbsp;&nbsp;<input type="radio" name="site_rel" id="rel2" value="none"/>none</p>
                                <p>
                                    <label>修改于：</label>
                                    <span class="article-time-display" id="time"></span>
                                </p>
                            </div>
                            <div class="add-article-box-footer">
                                <input type="hidden" id="id" name="id" value="" />
                                <button class="btn btn-primary" type="button" onclick="publishLink()">更新</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>
<!--个人信息模态框-->
<div class="modal fade" id="seeUserInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <form action="" method="post">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">个人信息</h4>
                </div>
                <div class="modal-body">
                    <table class="table" style="margin-bottom:0px;">
                        <thead>
                        <tr></tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td wdith="20%">姓名:</td>
                            <td width="80%"><input name="username" id="username" type="text" value="${sessionScope.user.username}" class="form-control" maxlength="10" autocomplete="off" required/></td>
                        </tr>
                        <tr>
                            <td wdith="20%">用户名:</td>
                            <td width="80%"><input type="text" name="nickname" id="nickname" value="${sessionScope.user.nickname}" class="form-control" maxlength="10" autocomplete="off" required/></td>
                        </tr>
                        <tr>
                            <td wdith="20%">电话:</td>
                            <td width="80%"><input type="text" name="phone" id="phone" value="${sessionScope.user.phone}" class="form-control" maxlength="13" autocomplete="off" required/></td>
                        </tr>
                        <tr>
                            <td wdith="20%">旧密码:</td>
                            <td width="80%"><input id="oldPwd" type="password" class="form-control cls" name="old_password" maxlength="18" autocomplete="off" required/></td>
                        </tr>
                        <tr>
                            <td wdith="20%">新密码:</td>
                            <td width="80%"><input type="password" class="form-control cls" id="pwd3" name="password" maxlength="18" autocomplete="off" required/></td>
                        </tr>
                        <tr>
                            <td wdith="20%">确认密码:</td>
                            <td width="80%"><input type="password" class="form-control cls" id="newPwd3" name="new_password" maxlength="18" autocomplete="off" required/></td>
                        </tr>
                        </tbody>
                        <tfoot>
                        <tr></tr>
                        </tfoot>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" onclick="updateUser()" class="btn btn-primary">提交</button>
                </div>
            </div>
        </form>
    </div>
</div>
<!--个人登录记录模态框-->
<div class="modal fade" id="seeUserLoginlog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">登录记录</h4>
            </div>
            <div class="modal-body">
                <table class="table" style="margin-bottom:0px;">
                    <thead>
                    <tr>
                        <th>登录IP</th>
                        <th>登录时间</th>
                        <th>状态</th>
                    </tr>
                    </thead>
                    <tbody id="userLoginlog">
                    <%--<tr>
                        <td>::1:55570</td>
                        <td>2016-01-08 15:50:28</td>
                        <td>成功</td>
                    </tr>--%>
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">朕已阅</button>
            </div>
        </div>
    </div>
</div>
<!--提示模态框-->
<div class="modal fade user-select" id="areDeveloping" tabindex="-1" role="dialog"
     aria-labelledby="areDevelopingModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="areDevelopingModalLabel" style="cursor:default;">该功能正在日以继夜的开发中…</h4>
            </div>
            <div class="modal-body"><img src="/blog/static/images/baoman/baoman_01.gif" alt="深思熟虑"/>
                <p style="padding:15px 15px 15px 100px; position:absolute; top:15px; cursor:default;">
                    很抱歉，程序猿正在日以继夜的开发此功能，本程序将会在以后的版本中持续完善！</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">朕已阅</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="/blog/static/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
<script src="/blog/static/js/layer-3.5.1/layer.js" type="text/javascript"></script>
<script src="/blog/static/js/back/pidcrypt.js" type="text/javascript"></script>
<script src="/blog/static/js/back/md5.js" type="text/javascript"></script>
<script src="/blog/static/js/back/user.js" type="text/javascript"></script>
<script>
    // 通过id查询友链
    $.post('/blog/link/queryById', {'id': ${id}}, function (data) {
        $('#id').val(data.id);
        $('#flink-name').val(data.site_name);
        $('#flink-url').val(data.site_url);
        $('#flink-imgurl').val(data.site_imgurl);
        $('#flink-desc').val(data.site_desc);
        if (data.site_rel == 'nofollow') {
            $('#rel1').prop("checked", true);
        } else {
            $('#rel2').prop("checked", true);
        }
    }, 'json');
</script>
<script src="/blog/static/js/back/common-flink.js" type="text/javascript"></script>
</body>
</html>
