package cn.hhit.canteen.meal_detail.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hhit.canteen.R;

/**
 * Created by Administrator on 2018/5/27/027.
 */

public class RvMealCommentAdapter extends RecyclerView.Adapter<RvMealCommentAdapter
        .RvMealCommentViewHolder> {
    private Context mContext;
    private List<String> data;

    public RvMealCommentAdapter(Context context, List<String> data) {
        mContext = context;
        this.data = data;
    }

    @Override
    public RvMealCommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RvMealCommentViewHolder(LayoutInflater.from(mContext).inflate(R.layout
                .item_rv_meal_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(RvMealCommentViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
//        return data == null ? 0 : data.size();
        return 20;
    }

    class RvMealCommentViewHolder extends RecyclerView.ViewHolder {

        public RvMealCommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
