package cn.hhit.canteen.page_rented;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.hhit.canteen.R;

/**
 * Created by Administrator on 2018/4/22/022.
 */

@SuppressLint("ValidFragment")
public class FgRented extends Fragment {

    private final Context mContext;

    public FgRented(Context context) {
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fg_after_meal, container, false);

        return rootView;
    }
}
