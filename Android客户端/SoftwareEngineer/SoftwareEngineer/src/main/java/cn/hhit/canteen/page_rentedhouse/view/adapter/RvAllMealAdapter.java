package cn.hhit.canteen.page_rentedhouse.view.adapter;

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
 * Created by Administrator on 2018/5/1/001.
 */

public class RvAllMealAdapter extends RecyclerView.Adapter<RvAllMealAdapter.MyViewHolder> {
    private Context mContext;
    private List<Integer> mData;

    public RvAllMealAdapter(Context context, List<Integer> data) {
        mContext = context;
        this.mData = data;
    }

    @Override

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_rv_all_meal,
                parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Glide.with(mContext).load(mData.get(position)).into(holder.ivAllMeal);
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
        private ImageView ivAllMeal;


        public MyViewHolder(View itemView) {
            super(itemView);

            ivAllMeal = itemView.findViewById(R.id.iv_all_meal);
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
