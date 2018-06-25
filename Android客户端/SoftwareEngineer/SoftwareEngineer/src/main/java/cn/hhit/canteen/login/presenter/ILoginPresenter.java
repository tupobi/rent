package cn.hhit.canteen.login.presenter;

/**
 * Created by Administrator on 2018/6/23/023.
 */

public interface ILoginPresenter {
    void register(String userName, String userPassword, int userType);

    void login(String userName, String userPassword, int userType);
}
