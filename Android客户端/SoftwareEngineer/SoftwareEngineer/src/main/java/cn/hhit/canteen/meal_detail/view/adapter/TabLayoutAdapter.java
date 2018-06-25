package cn.hhit.canteen.meal_detail.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import java.util.List;


/**
 * Created by Administrator on 2017/11/26.
 */

public class TabLayoutAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;
    private String[] mTitles;
    private Context mContext;

    //给 indicator添加图标 不知道为什么失败了。需要的话再百度
//    private int[] imageResId = {
//            R.mipmap.ic_0,
//            R.mipmap.ic_1,
//            R.mipmap.ic_2
//    };

    public TabLayoutAdapter(Context context, FragmentManager fm, List<Fragment> fragments, String[] titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];

//        Drawable image = mContext.getResources().getDrawable(imageResId[position]);
//        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
//        // Replace blank spaces with image icon
//        SpannableString sb = new SpannableString(mTitles[position] + "   ");
//        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
//        sb.setSpan(imageSpan, mTitles[position].length(), mTitles[position].length() + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        return sb;

//        Drawable image = ContextCompat.getDrawable(mContext, imageResId[position]);
//        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
//        SpannableString sb = new SpannableString(" ");
//        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
//        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        return sb;

    }
}
