package cn.hhit.canteen.main.view.adapter;

/**
 * Created by Administrator on 2018/4/22/022.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;


public class MyViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragments;
    private int size;

    public MyViewPagerAdapter(FragmentManager fm, int size, List<Fragment> fragments) {
        super(fm);
        this.size = size;
        this.fragments = fragments;
    }

    public Fragment getItem(int position) {
        return (Fragment) this.fragments.get(position);
    }

    public int getCount() {
        return this.size;
    }

    //重写为空的话，防止销毁视图
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//
//    }
}