package cn.hhit.canteen.meal_detail.model.bean;


public class Comment {
    private String userName;
    private String houseName;
    private String content;
    private String date;
    private String avatarUrl;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String toString() {
        return "Comment{" + "userName='" + userName + '\'' + ", houseName='" + houseName + '\'' + ", content='" + content + '\'' + ", date='" + date + '\'' + ", avatarUrl='" + avatarUrl + '\'' + '}';
    }
}
