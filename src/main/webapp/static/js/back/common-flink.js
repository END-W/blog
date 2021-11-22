function publishLink() {
    var form = $('.add-flink-form').serialize();
    form = serializeNotNull(form);

    if ($('#flink-name').val() != "" && $('#flink-url').val() != "" && $('#flink-imgurl').val() != "") {
        $.post('/blog/link/saveOrUpdate', form, function (data) {
            if (data.ok) {
                layer.alert(data.mess, {
                    time: 2000,
                    icon: 6
                });

                $('.article-status-display').html("已增加");
                $('#time').html(getNowFormatDate());

                setTimeout(function () {
                    // 跳转到友链列表页面
                    location.href = "/blog/toView/workbench/link/flink";
                }, 3000);
            } else {
                layer.alert(data.mess, {icon: 6});
            }
        }, 'json');
    } else {
        layer.msg("请填写必要字段", {offer: 't'});
    }
}