package cn.hhit.canteen.house_manage.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hhit.canteen.R;

public class AtyHouseManage extends AppCompatActivity {

    @BindView(R.id.fab_add_house)
    FloatingActionButton mFabAddHouse;
    @BindView(R.id.tb_manage_house)
    Toolbar mTbManageHouse;

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, AtyHouseManage.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_house_manage);
        ButterKnife.bind(this);

        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(mTbManageHouse);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mTbManageHouse.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick(R.id.fab_add_house)
    public void onViewClicked() {
        AtyAddHouse.actionStart(AtyHouseManage.this);
    }
}
