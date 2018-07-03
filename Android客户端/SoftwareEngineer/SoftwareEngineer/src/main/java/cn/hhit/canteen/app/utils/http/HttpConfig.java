package cn.hhit.canteen.app.utils.http;

/**
 * @author yemao
 * @date 2017/4/9
 * @description 关于网络的配置
 */

public class HttpConfig {

    public static int OK = 100;

    public static int NO_RESULT = 101;

    public static int DATABASE_OPERATE_ERROR = 102;

    public static int HTTP_TIME = 6000;
//debug
//    public static final String BASE_URL = "http://192.168.43.42:8080/SoftwareEngineer/servlet/";
//    public static final String PIC_BASE_URL = "http://192.168.43.42:8080/";

    public static final String VERSION_CHECK_BASE_URL =
            "http://120.78.191.148/AppVersionControler/";
    public static final String PATH = "service";
    public static final String GET_LATEST_VERSION = "versioncheck";
    public static final String BASE_URL = "http://120.78.191.148/SoftwareEngineer/servlet/";
    public static final String PIC_BASE_URL = "http://120.78.191.148/";
    public static final String REGISTER = "Regist";
    public static final String LOGIN = "Login";
    public static final String UPLOAD_AVATAR = "UpdateAvatar";
    public static final String GET_USER_INFO = "GetUserInfo";
    public static final String UPLOAD_HOUSE_INFO = "UploadHouse";
    public static final String GET_ALL_HOUSES_INFO = "GetAllHousesInfo";

    public static final String GET_HOUSE_BY_USERNAME = "GetHouseByUsername";

    public static final String ADD_HISTORY = "AddHistory";
    public static final String ADD_COLLECT = "AddCollect";

    public static final String GET_HOUSES_BY_HISTORY = "GetHouseByHistory";
    public static final String GET_HOUSES_BY_COLLECT = "GetHouseByCollect";

    public static final String ADD_COMMENT = "AddComment";

    public static final String GET_COMMENTS = "GetComments";

}
