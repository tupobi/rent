package cn.hhit.canteen.login.presenter;

import android.content.Context;

import cn.hhit.canteen.app.utils.LogUtil;
import cn.hhit.canteen.app.utils.SpUtils;
import cn.hhit.canteen.app.utils.ToastUtil;
import cn.hhit.canteen.login.model.ILoginModel;
import cn.hhit.canteen.login.model.LoginModelImpl;
import cn.hhit.canteen.app.utils.bean.StringResponse;
import cn.hhit.canteen.login.view.AtyLogin;
import cn.hhit.canteen.login.view.ILoginView;

/**
 * Created by Administrator on 2018/6/23/023.
 */

public class LoginPresenterImpl implements ILoginPresenter {
    private ILoginView mILoginView;
    private ILoginModel mILoginModel;
    private Context mContext;

    public LoginPresenterImpl(ILoginView iLoginView, Context context) {
        mILoginView = iLoginView;
        mContext = context;
        mILoginModel = new LoginModelImpl();

    }

    @Override
    public void register(final String userName, String userPassword, int userType) {
        if (userName.isEmpty() || userPassword.isEmpty()) {
            ToastUtil.showShort(mContext, "账号密码不能为空！");
            return;
        }
        mILoginModel.register(userName, userPassword, userType, new ILoginModel.RegisterCallback() {
            @Override
            public void onSuccess(StringResponse res) {
                if (res != null) {
                    LogUtil.e("注册响应：" + res.getResponse());
                    LogUtil.e(res.toString());
                    if ("true".equals(res.getResponse())) {
                        ToastUtil.showShort(mContext, "注册成功！");
                        SpUtils.getInstance().save(AtyLogin.SESSION_USERNAME, userName);
                        mILoginView.registerSuccess();
                    } else if ("false".equals(res.getResponse())) {
                        ToastUtil.showShort(mContext, "用户名重复！");
                    }
                } else {
                    ToastUtil.showShort(mContext, "网络异常");
                }
            }

            @Override
            public void onFailed() {
                ToastUtil.showShort(mContext, "服务器异常");
            }
        });
    }

    @Override
    public void login(final String userName, String userPassword, int userType) {
        if (userName.isEmpty() || userPassword.isEmpty()) {
            ToastUtil.showShort(mContext, "账号密码不能为空！");
            return;
        }
        mILoginModel.login(userName, userPassword, userType, new ILoginModel.LoginCallback() {
            @Override
            public void onSuccess(StringResponse res) {
                if (res != null) {
                    LogUtil.e("登录响应：" + res.getResponse());
                    LogUtil.e(res.toString());
                    if ("true".equals(res.getResponse())) {
                        ToastUtil.showShort(mContext, "登录成功！");
                        SpUtils.getInstance().save(AtyLogin.SESSION_USERNAME, userName);
                        mILoginView.loginSuccess();
                    } else if ("false".equals(res.getResponse())) {
                        ToastUtil.showShort(mContext, "账号秘密错误！");
                    }
                } else {
                    ToastUtil.showShort(mContext, "网络异常");
                }
            }

            @Override
            public void onFailed() {
                ToastUtil.showShort(mContext, "服务器异常");
            }
        });
    }

}
