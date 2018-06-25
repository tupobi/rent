package cn.hhit.canteen.main.model.bean;

public class AppVersion {
    private String versionName;
    private String appName;
    private int isLatest;
    private String date;
    private String downloadUrl;
    private String description;

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getIsLatest() {
        return isLatest;
    }

    public void setIsLatest(int isLatest) {
        this.isLatest = isLatest;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "AppVersion{" +
                "versionName='" + versionName + '\'' +
                ", appName='" + appName + '\'' +
                ", isLatest=" + isLatest +
                ", date='" + date + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
