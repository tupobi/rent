package cn.hhit.canteen.app.utils.bean;

/**
 * Created by Administrator on 2018/6/25/025.
 */

public class MessageEvent {
    private String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}