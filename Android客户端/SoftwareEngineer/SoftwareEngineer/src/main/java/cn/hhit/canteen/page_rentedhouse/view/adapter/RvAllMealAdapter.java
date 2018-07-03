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
import cn.hhit.canteen.house_manage.model.bean.House;

/**
 * Created by Administrator on 2018/5/1/001.
 */

public class RvAllMealAdapter extends RecyclerView.Adapter<RvAllMealAdapter.MyViewHolder> {
    private Context mContext;
    private List<House> mData;

    public RvAllMealAdapter(Context context, List<House> data) {
        mContext = context;
        this.mData = data;
    }

    public void setData(List<House> data) {
        mData = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_rv_all_meal,
                parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Glide.with(mContext).load(HttpConfig.PIC_BASE_URL + mData.get(position).getPic1Url())
                .into(holder.mIvAllMeal);
        holder.mTvThumb.setText("" + (int) (10 + Math.random() * 10));
        holder.mTvCollect.setText("" + (int) (0 + Math.random() * 10));
        holder.mTvHouseName.setText(mData.get(position).getHouseName());
        holder.mTvHouseCityAreaType.setText("[" + mData.get(position).getHouseCity() + "] " +
                mData.get(position).getHouseArea() + " " + mData.get(position).getHouseTyle());
        holder.mTvHouseNowPrice.setText("" + mData.get(position).getMonthPrice());
        holder.mTvHouseOriginPrice.setText("" + (mData.get(position).getMonthPrice() - (int) (100
                + Math.random() * 50)));
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
        return mData == null ? 0 : mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_all_meal)
        ImageView mIvAllMeal;
        @BindView(R.id.tv_house_name)
        TextView mTvHouseName;
        @BindView(R.id.tv_house_city_area_type)
        TextView mTvHouseCityAreaType;
        @BindView(R.id.tv_house_origin_price)
        TextView mTvHouseOriginPrice;
        @BindView(R.id.tv_house_now_price)
        TextView mTvHouseNowPrice;
        @BindView(R.id.tv_thumb)
        TextView mTvThumb;
        @BindView(R.id.tv_collect)
        TextView mTvCollect;


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
