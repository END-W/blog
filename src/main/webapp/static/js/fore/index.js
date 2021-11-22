layui.use(['jquery', 'util'], function () {
    var $ = layui.jquery,
        util = layui.util;
    $(window).load(function () {
        $("#loading").fadeOut(500);
        new WOW().init();
    });
    util.fixbar();
    $('.next').click(function () {
        $('html,body').animate({
            scrollTop: $('#section1').outerHeight() + 1
        }, 600);
    });
    $('#menu').on('click', function () {
        var mark = $(this).attr('data-mark');
        if (mark === 'false') {
            $(this).removeClass('menu_open').addClass('menu_close');
            //open
            $('#navgation').removeClass('navgation_close').addClass('navgation_open');
            $(this).attr({ 'data-mark': "true" });
        } else {
            $(this).removeClass('menu_close').addClass('menu_open');
            //close
            $('#navgation').removeClass('navgation_open').addClass('navgation_close');
            $(this).attr({ 'data-mark': "false" });
        }
    });

});

// 获取文章栏目
$.get('/blog/nav/list', function (data) {
    $('.warp-box').append("<div class=\"layui-row\" id='content-row'></div>");
    var t = 0;

    for (var i = 0; i < data.length; i++) {
        $('#content-row').append("<div class=\"layui-col-xs12 layui-col-sm4 layui-col-md4  wow fadeInUp\" data-wow-delay=\"" + t +"s\" style=\"padding: 0 10px\">\n" +
            "           <div class=\"single-news\">\n" +
            "           <div class=\"news-head\">\n" +
            "           <img src=\"" + data[i].cimg + "\" title=\"" + data[i].cname + "专栏\">\n" +
            "           <a href=\"/blog/nav/" + data[i].cname + "/" + data[i].cid + "\" class=\"link\"><i class=\"fa fa-link\"></i></a>\n" +
            "           </div>\n" +
            "           <div class=\"news-content\">\n" +
            "           <h4><span>" + data[i].cname + "专栏</span></h4>\n" +
            "           <p>" + data[i].cdesc + "</p>\n" +
            "           <a href=\"/blog/nav/" + data[i].cname + "/" + data[i].cid + "\" class=\"btn\">阅读更多</a>\n" +
            "           </div></div></div>");

        t += 0.2;
    }
}, 'json');
