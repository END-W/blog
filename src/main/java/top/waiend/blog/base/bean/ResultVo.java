package top.waiend.blog.base.bean;

/**
 * 给客户端返回消息的Bean对象
 */
public class ResultVo<T> {
    private String mess; // 给客户端的消息
    private boolean isOk; // 用户操作是否成功
    private T t; // 返回对象数据

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
