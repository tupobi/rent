package cn.hhit.canteen.app.utils;

/**
 * Created by Administrator on 2017/12/3.
 */


import android.content.Context;
import android.content.SharedPreferences;

import cn.hhit.canteen.app.CanteenApplication;


/*
-----------------使用之前先在设置Application,设置Application需要在Manifest中设置name---------------
			android:name=".app.CanteenApplication"

public class CanteenApplication extends Application {
    private static Context mContext;

    public static Context getGlobalApplication() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }
}
*/
public class SpUtils {
    private static SpUtils instance = new SpUtils();
    private static SharedPreferences mSp;

    private SpUtils() {

    }

    public static SpUtils getInstance() {
        if (mSp == null) {
            mSp = CanteenApplication.getGlobalApplication().getSharedPreferences("app", Context.MODE_PRIVATE);
        }
        return instance;
    }

    public void save(String key, Object value) {
        if (value instanceof String) {
            mSp.edit().putString(key, (String) value).commit();
        } else if (value instanceof Boolean) {
            mSp.edit().putBoolean(key, (Boolean) value).commit();
        } else if (value instanceof Integer) {
            mSp.edit().putInt(key, (Integer) value).commit();
        }
    }

    public String getString(String key, String defValue) {
        return mSp.getString(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mSp.getBoolean(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return mSp.getInt(key, defValue);
    }
}
