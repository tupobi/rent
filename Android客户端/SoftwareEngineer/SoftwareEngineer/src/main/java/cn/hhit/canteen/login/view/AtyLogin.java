package cn.hhit.canteen.login.view;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hhit.canteen.R;
import cn.hhit.canteen.app.utils.SpUtils;
import cn.hhit.canteen.login.presenter.ILoginPresenter;
import cn.hhit.canteen.login.presenter.LoginPresenterImpl;
import cn.hhit.canteen.main.view.AtyMain;

public class AtyLogin extends AppCompatActivity implements ILoginView {

    @BindView(R.id.tiet_username)
    TextInputEditText mTietUsername;
    @BindView(R.id.tiet_password)
    TextInputEditText mTietPassword;
    @BindView(R.id.iv_login_bg)
    ImageView mIvLoginBg;
    @BindView(R.id.til_username)
    TextInputLayout mTilUsername;
    @BindView(R.id.til_password)
    TextInputLayout mTilPassword;
    @BindView(R.id.rg_user_type)
    RadioGroup mRgUserType;
    @BindView(R.id.btn_normal_login)
    Button mBtnNormalLogin;
    @BindView(R.id.btn_register)
    Button mBtnRegister;

    public static final String SESSION_USERNAME = "sessionUsername";

    private ILoginPresenter mILoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!"null".equals(SpUtils.getInstance().getString(AtyLogin.SESSION_USERNAME, "null"))) {
            //已登录
            loginSuccess();
            finish();
        }

        setContentView(R.layout.aty_login);
        ButterKnife.bind(this);
        mILoginPresenter = new LoginPresenterImpl(this, this);
        initView();
    }

    private void initView() {
        Glide.with(AtyLogin.this).load(R.drawable.login_bg_1).into(mIvLoginBg);
        mRgUserType.check(R.id.rb_user_renter);
    }

    @OnClick({R.id.btn_normal_login, R.id.btn_register})
    public void onViewClicked(View view) {
        String userName = mTietUsername.getText().toString().trim();
        String userPassword = mTietPassword.getText().toString().trim();
        int userType = 0;
        int checkedRadioButtonId = mRgUserType.getCheckedRadioButtonId();
        if (checkedRadioButtonId == R.id.rb_user_renter) {
            userType = 0;
        } else if (checkedRadioButtonId == R.id.rb_user_owner) {
            userType = 1;
        }
        switch (view.getId()) {
            case R.id.btn_normal_login:
                mILoginPresenter.login(userName, userPassword, userType);
                break;
            case R.id.btn_register:
                mILoginPresenter.register(userName, userPassword, userType);
                break;
        }
    }

    @Override
    public void loginSuccess() {
        AtyMain.actionStart(AtyLogin.this);
        finish();
    }

    @Override
    public void registerSuccess() {
        AtyPreference.actionStart(AtyLogin.this);
        finish();
    }
}
