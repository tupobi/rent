package cn.hhit.canteen.login.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.Collections;
import java.util.List;

import cn.hhit.canteen.R;
import cn.hhit.canteen.app.utils.LogUtil;
import cn.hhit.canteen.login.view.helper.ItemTouchHelperAdapter;
import cn.hhit.canteen.login.view.helper.ItemTouchHelperViewHolder;
import cn.hhit.canteen.login.view.helper.OnStartDragListener;


/**
 * Created by Administrator on 2017/12/17.
 */

public class RvFlavourAdapter extends RecyclerView.Adapter<RvFlavourAdapter.ItemViewHolder> implements ItemTouchHelperAdapter {
    private List<String> mData;
    private Context mContext;
    private final OnStartDragListener mDragStartListener;

    public RvFlavourAdapter(List<String> mData, Context mContext, OnStartDragListener dragStartListener) {
        this.mData = mData;
        this.mContext = mContext;
        mDragStartListener = dragStartListener;
    }

    public void setmData(List<String> mData) {
        this.mData = mData;
    }

    public List<String> getmData() {
        return mData;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_rv_flavour, parent, false));
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        if (mData == null || mData.size() == 0) {
            LogUtil.e("没有数据！");
            return;
        }
        holder.tvFlavour.setText(mData.get(position));

        // Start a drag whenever the ivHandler view it touched
        holder.ivHandler.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });

        if (mOnItemClickListener != null) {
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(holder.itemView, position); // 2
                }
            });
        }
        if (mOnItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemLongClickListener.onItemLongClick(holder.itemView, position);
                    //返回true 表示消耗了事件 事件不会继续传递
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mData == null || mData.size() == 0) {
            LogUtil.e("没有数据！");
            return 0;
        }
        return mData.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        //交换数据
        Collections.swap(mData, fromPosition, toPosition);
        //刷新
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
        private TextView tvFlavour;
        private ImageView ivHandler;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvFlavour = itemView.findViewById(R.id.tv_flavour);
            ivHandler = itemView.findViewById(R.id.iv_handle);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

}
