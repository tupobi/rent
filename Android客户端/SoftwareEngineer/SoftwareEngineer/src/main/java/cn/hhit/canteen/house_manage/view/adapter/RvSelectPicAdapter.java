package cn.hhit.canteen.house_manage.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DateUtils;
import com.luck.picture.lib.tools.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.hhit.canteen.R;
import cn.hhit.canteen.app.utils.LogUtil;

/**
 * Created by Administrator on 2018/4/9/009.
 */

public class RvSelectPicAdapter extends RecyclerView.Adapter<RvSelectPicAdapter.MyViewHolder> {

    public static final int TYPE_ADD_IMAGE = 1;
    public static final int TYPE_PICTURE = 2;

    private List<LocalMedia> mLocalMediaList = new ArrayList<>();
    private Context mContext;
    private int selectMax = 6;

    public RvSelectPicAdapter(Context context) {
        mContext = context;
    }

    public void setSelectMax(int selectMax) {
        this.selectMax = selectMax;
    }

    public List<LocalMedia> getLocalMediaList() {
        return mLocalMediaList;
    }

    public void setLocalMediaList(List<LocalMedia> localMediaList) {
        mLocalMediaList = localMediaList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_of_rv_pic_selector, parent, false));
    }

    /**
     * 如果是第0个图片或者第mLocalMediaList.size()（最后一张图片）就显示添加图片的TYPE
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (isShowAddItem(position)) {
            return TYPE_ADD_IMAGE;
        } else {
            return TYPE_PICTURE;
        }
    }

    /**
     * 判断是否显示add image添加图标，如果是第0个图片或者第mLocalMediaList.size()（最后一张图片）就返回true
     * @param position
     * @return
     */
    private boolean isShowAddItem(int position) {
        int size = mLocalMediaList.size() == 0 ? 0 : mLocalMediaList.size();
        return position == size;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        //如果是第0个图片或者第mLocalMediaList.size()（最后一张图片）就显示添加图片的TYPE
        if (getItemViewType(position) == TYPE_ADD_IMAGE) {
            holder.mImg.setImageResource(R.drawable.addimg_1x);
            holder.mImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnAddPicClickListener.onAddPicClick();
                }
            });
            holder.ll_del.setVisibility(View.INVISIBLE);
        } else {
            holder.ll_del.setVisibility(View.VISIBLE);
            holder.ll_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = holder.getAdapterPosition();
                    // 这里有时会返回-1造成数据下标越界,具体可参考getAdapterPosition()源码，
                    // 通过源码分析应该是bindViewHolder()暂未绘制完成导致，知道原因的也可联系我~感谢
                    if (index != RecyclerView.NO_POSITION) {
                        mLocalMediaList.remove(index);
                        notifyItemRemoved(index);
                        notifyItemRangeChanged(index, mLocalMediaList.size());
                    }
                }
            });
            LocalMedia media = mLocalMediaList.get(position);
            int mimeType = media.getMimeType();
            String path = "";
            if (media.isCut() && !media.isCompressed()) {
                // 裁剪过
                path = media.getCutPath();
            } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                path = media.getCompressPath();
            } else {
                // 原图
                path = media.getPath();
            }
            // 图片
            if (media.isCompressed()) {
                LogUtil.e("compress image result:" + new File(media.getCompressPath()).length() / 1024 + "k");
                LogUtil.e("压缩地址::" + media.getCompressPath());
            }

            LogUtil.e("原图地址::" + media.getPath());
            int pictureType = PictureMimeType.isPictureType(media.getPictureType());
            if (media.isCut()) {
                LogUtil.e("裁剪地址::" + media.getCutPath());
            }
            long duration = media.getDuration();
            holder.tv_duration.setVisibility(pictureType == PictureConfig.TYPE_VIDEO
                    ? View.VISIBLE : View.GONE);
            if (mimeType == PictureMimeType.ofAudio()) {
                holder.tv_duration.setVisibility(View.VISIBLE);
                Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.picture_audio);
                StringUtils.modifyTextViewDrawable(holder.tv_duration, drawable, 0);
            } else {
                Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.video_icon);
                StringUtils.modifyTextViewDrawable(holder.tv_duration, drawable, 0);
            }
            holder.tv_duration.setText(DateUtils.timeParse(duration));
            if (mimeType == PictureMimeType.ofAudio()) {
                holder.mImg.setImageResource(R.drawable.audio_placeholder);
            } else {
                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL);
                Glide.with(holder.itemView.getContext())
                        .load(path)
                        .apply(options)
                        .into(holder.mImg);
            }
            //itemView 的点击事件
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int adapterPosition = holder.getAdapterPosition();
                        mOnItemClickListener.onItemClick(adapterPosition, v);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mLocalMediaList.size() < selectMax) {
            return mLocalMediaList.size() + 1;
        } else {
            return mLocalMediaList.size();
        }

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView mImg;
        LinearLayout ll_del;
        TextView tv_duration;

        public MyViewHolder(View itemView) {
            super(itemView);
            mImg = (ImageView) itemView.findViewById(R.id.fiv);
            ll_del = (LinearLayout) itemView.findViewById(R.id.ll_del);
            tv_duration = (TextView) itemView.findViewById(R.id.tv_duration);
        }
    }

    protected OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    private OnAddPicClickListener mOnAddPicClickListener;

    public interface OnAddPicClickListener {
        void onAddPicClick();
    }

    public void setOnAddPicClickListener(OnAddPicClickListener onAddPicClickListener) {
        mOnAddPicClickListener = onAddPicClickListener;
    }
}
