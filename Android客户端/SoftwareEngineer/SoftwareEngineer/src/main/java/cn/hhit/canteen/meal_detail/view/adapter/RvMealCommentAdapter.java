package cn.hhit.canteen.meal_detail.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hhit.canteen.R;
import cn.hhit.canteen.app.utils.http.HttpConfig;
import cn.hhit.canteen.meal_detail.model.bean.Comment;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/5/27/027.
 */

public class RvMealCommentAdapter extends RecyclerView.Adapter<RvMealCommentAdapter
        .RvMealCommentViewHolder> {
    private Context mContext;
    private List<Comment> data;

    public RvMealCommentAdapter(Context context, List<Comment> data) {
        mContext = context;
        this.data = data;
    }

    public void setData(List<Comment> data) {
        this.data = data;
    }

    @Override
    public RvMealCommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RvMealCommentViewHolder(LayoutInflater.from(mContext).inflate(R.layout
                .item_rv_meal_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(RvMealCommentViewHolder holder, int position) {
        if (data.get(position).getAvatarUrl() != null && !data.get(position).getAvatarUrl()
                .isEmpty()) {
            Glide.with(mContext).load(HttpConfig.PIC_BASE_URL + data.get(position).getAvatarUrl())
                    .into(holder.mCivCommentAvatar);
        }
        holder.mTvCommentUsername.setText(data.get(position).getUserName());
        holder.mTvCommentDate.setText(data.get(position).getDate());
        holder.mTvCommentContent.setText(data.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class RvMealCommentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.civ_comment_avatar)
        CircleImageView mCivCommentAvatar;
        @BindView(R.id.tv_comment_username)
        TextView mTvCommentUsername;
        @BindView(R.id.tv_comment_date)
        TextView mTvCommentDate;
        @BindView(R.id.tv_comment_content)
        TextView mTvCommentContent;
        @BindView(R.id.ll_comment)
        LinearLayout mLlComment;

        public RvMealCommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
