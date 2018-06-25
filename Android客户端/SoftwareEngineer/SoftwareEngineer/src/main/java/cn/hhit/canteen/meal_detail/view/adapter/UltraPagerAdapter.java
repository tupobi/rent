/*
 *
 *  MIT License
 *
 *  Copyright (c) 2017 Alibaba Group
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 *
 */

package cn.hhit.canteen.meal_detail.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bm.library.PhotoView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import java.util.List;

import cn.hhit.canteen.R;
import cn.hhit.canteen.app.CanteenApplication;
import cn.hhit.canteen.app.utils.LogUtil;

/**
 * Created by mikeafc on 15/11/26.
 */
public class UltraPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<String> imgListUrls;

    public UltraPagerAdapter(Context context, List<String> imgListUrls) {
        mContext = context;
        this.imgListUrls = imgListUrls;
    }

    @Override
    public int getCount() {
        return imgListUrls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.layout_child, null);
        //new LinearLayout(container.getContext());
        PhotoView photoView = linearLayout.findViewById(R.id.pv);
        //用bm包下的photo滑动事件会被放大事件监听，图片在放大的情况下pager滑不动。。而GitHub包下的photoview不会
        //但是QQ也不可以，搞成黑背景的还不错
        photoView.enable();

        if (mOnPhotoViewImageClickListener != null) {
            photoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnPhotoViewImageClickListener.onPhotoViewImageClick(view, position);
                }
            });
        }

        ImageLoader.getInstance().displayImage(
                imgListUrls.get(position),
                photoView, CanteenApplication.getDisplayOptions(), new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String s, View view) {
                        //show loading ui
                    }

                    @Override
                    public void onLoadingFailed(String s, View view, FailReason failReason) {
                        //hide loading ui
                    }

                    @Override
                    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                        //hide loading ui
                    }

                    @Override
                    public void onLoadingCancelled(String s, View view) {
                        //hide loading ui
                    }
                }, new ImageLoadingProgressListener() {
                    @Override
                    public void onProgressUpdate(String s, View view, int i, int i1) {
                        //loading progress
                        LogUtil.e("i == " + i);//i is loading progress
                        LogUtil.e("i1 == " + i1);//i1 is max progress
                        //usage
//                        mTextView.setText(Math.round(100.0f * i / i1) + "%");
                    }
                });
        container.addView(linearLayout);
        return linearLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        LinearLayout view = (LinearLayout) object;
        container.removeView(view);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }

    /**
     * 实现图片点事件回调
     */
    public interface OnPhotoViewImageClickListener {
        void onPhotoViewImageClick(View view, int position);
    }

    private OnPhotoViewImageClickListener mOnPhotoViewImageClickListener;

    public void setOnPhotoViewImageClickListener(OnPhotoViewImageClickListener onPhotoViewImageClickListener) {
        mOnPhotoViewImageClickListener = onPhotoViewImageClickListener;
    }
}
