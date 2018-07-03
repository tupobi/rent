package cn.hhit.canteen.page_rentedhouse.view.adapter;

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
import cn.hhit.canteen.app.utils.http.HttpConfig;

/**
 * Created by Administrator on 2018/4/30/030.
 */

public class RvGuessLoveAdapter extends RecyclerView.Adapter<RvGuessLoveAdapter.MyViewHolder> {
    private List<String> mData;
    private Context mContext;

    public RvGuessLoveAdapter(List<String> data, Context context) {
        mData = data;
        mContext = context;
    }

    public void setData(List<String> data) {
        mData = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_rv_guesslove,
                parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Glide.with(mContext).load(HttpConfig.PIC_BASE_URL + mData.get(position)).into(holder
                .mIvGuessloveMeal);

        holder.mTvGuessLoveThumb.setText("" + (int) (10 + Math.random() * 10));
        holder.mTvGuessLoveCollect.setText("" + (int) (0 + Math.random() * 10));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnRvItemClickListener.onItemClickListener(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_guesslove_meal)
        ImageView mIvGuessloveMeal;
        @BindView(R.id.tv_guess_love_thumb)
        TextView mTvGuessLoveThumb;
        @BindView(R.id.tv_guess_love_collect)
        TextView mTvGuessLoveCollect;

        public MyViewHolder(View itemView) {
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
