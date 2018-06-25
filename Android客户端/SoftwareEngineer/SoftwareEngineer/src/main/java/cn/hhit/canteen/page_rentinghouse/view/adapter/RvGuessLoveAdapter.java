package cn.hhit.canteen.page_rentinghouse.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.hhit.canteen.R;

/**
 * Created by Administrator on 2018/4/30/030.
 */

public class RvGuessLoveAdapter extends RecyclerView.Adapter<RvGuessLoveAdapter.MyViewHolder> {
    private List<Integer> mData;
    private Context mContext;

    public RvGuessLoveAdapter(List<Integer> data, Context context) {
        mData = data;
        mContext = context;
    }

    public void setData(List<Integer> data) {
        mData = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_rv_guesslove,
                parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Glide.with(mContext).load(mData.get(position)).into(holder.ivGuessLove);

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
        private ImageView ivGuessLove;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivGuessLove = itemView.findViewById(R.id.iv_guesslove_meal);
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
