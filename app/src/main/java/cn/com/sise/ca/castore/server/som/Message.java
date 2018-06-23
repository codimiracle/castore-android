package cn.com.sise.ca.castore.server.som;

public class Message {
    public static final Message INTERNET_ERROR_MESSAGE;
    public static final Message DATAFORMAT_ERROR_MESSAGE;
    static {
        INTERNET_ERROR_MESSAGE = new Message();
        INTERNET_ERROR_MESSAGE.setType("msg");
        INTERNET_ERROR_MESSAGE.setIcon("error");
        INTERNET_ERROR_MESSAGE.setMessage("网络链接失败！");
        DATAFORMAT_ERROR_MESSAGE = new Message();
        DATAFORMAT_ERROR_MESSAGE.setType("msg");
        DATAFORMAT_ERROR_MESSAGE.setMessage("获取的数据格式不正确，请与系统管理员联系！");
        DATAFORMAT_ERROR_MESSAGE.setIcon("error");
    }
    private String type;
    private String icon;
    private String message;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
