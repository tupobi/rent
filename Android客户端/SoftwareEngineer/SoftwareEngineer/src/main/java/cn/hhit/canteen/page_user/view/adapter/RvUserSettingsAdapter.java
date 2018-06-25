package cn.hhit.canteen.page_user.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hhit.canteen.R;

/**
 * Created by Administrator on 2018/5/10/010.
 */

public class RvUserSettingsAdapter extends RecyclerView.Adapter<RvUserSettingsAdapter
        .SettingsHolder> {

    private Context mContext;
    private List<String> data;

    public RvUserSettingsAdapter(Context context, List<String> data) {
        mContext = context;
        this.data = data;
    }

    @Override
    public SettingsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SettingsHolder(LayoutInflater.from(mContext).inflate(R.layout
                .item_rv_user_setting, parent, false));
    }

    @Override
    public void onBindViewHolder(SettingsHolder holder, final int position) {
        if (position == 1) {
            Glide.with(mContext).load(R.drawable.ic_history_selected).into(holder
                    .mIvUserSettingMyCollect);
            holder.mTvUserSettingMyCollect.setText("我的口味");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnRvItemClickListener != null) {
                    mOnRvItemClickListener.onItemClickListener(view, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class SettingsHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_user_setting_my_collect)
        ImageView mIvUserSettingMyCollect;
        @BindView(R.id.tv_user_setting_my_collect)
        TextView mTvUserSettingMyCollect;

        public SettingsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnRvItemClickListener {
        void onItemClickListener(View itemView, int position);
    }

    private OnRvItemClickListener mOnRvItemClickListener;

    public void setOnRvItemClickListener(OnRvItemClickListener onRvItemClickListener) {
        mOnRvItemClickListener = onRvItemClickListener;
    }
}
