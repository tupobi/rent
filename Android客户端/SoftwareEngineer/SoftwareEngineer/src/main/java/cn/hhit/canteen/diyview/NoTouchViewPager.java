package cn.hhit.canteen.diyview;

/**
 * Created by Administrator on 2018/4/22/022.
 */

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoTouchViewPager extends ViewPager {
    public NoTouchViewPager(Context context) {
        super(context);
    }

    public NoTouchViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
    }
}