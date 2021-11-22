package top.waiend.blog.base.exception;

public enum BlogEnum {
    //第一个001:用户登录模块 第二个001:登录中验证码错误情况
    USER_LOGIN_CODE("001-001","验证码输入错误"),
    USER_LOGIN_ACCOUNT("001-002","用户名或者密码错误"),
    USER_VERIFY_PASS("001-003","输入的旧密码错误"),
    USER_CHANGE("001-004", "修改用户信息失败"),
    USER_UPDATE("001-005","更新用户信息失败"),
    USER_DELETE("001-006", "删除用户失败"),
    USER_UPDATE_STATE("001-007", "修改用户状态失败"),
    USER_ADD("001-008", "添加用户失败"),
    USER_VERIFY_NICKNAME("001-009", "用户名重复"),
    ARTICLE_ISOPEN("002-001", "修改文章是否公开失败"),
    ARTICLE_PUBLISH("002-002", "发布文章失败"),
    ARTICLE_UPDATE("002-003","修改文章失败"),
    ARTICLE_DELETE("002-004","删除文章失败"),
    LOGINLOG_INSERT("003-001", "添加用户登录日志失败"),
    LOGINLOG_DELETE_COMMON("003-002", "删除用户日志失败"),
    LOGINLOG_DELETE_ALL("003-003", "删除所有用户日志失败"),
    LOGINLOG_DELETE_CURRENT("003-004", "删除登录用户所有日志失败"),
    LINK_DELETE("004-001", "删除友链失败"),
    LINK_INSERT("004-002", "增加友链失败"),
    LINK_UPDATE("004-003", "修改友链失败");

    private String typeCode;//属于哪个模块下的操作失败code
    private String message;//具体错误消息

    BlogEnum(String typeCode, String message) {
        this.typeCode = typeCode;
        this.message = message;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
