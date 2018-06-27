package cn.hhit.canteen.house_manage.presenter;

import android.content.Context;

import java.util.List;
import java.util.Map;

import cn.hhit.canteen.app.utils.ToastUtil;
import cn.hhit.canteen.app.utils.bean.StringResponse;
import cn.hhit.canteen.house_manage.model.AddHouseModel;
import cn.hhit.canteen.house_manage.model.IAddHouseModel;
import cn.hhit.canteen.house_manage.view.IAddHouseView;

/**
 * Created by Administrator on 2018/6/26/026.
 */

public class AddHousePresenterImpl implements IAddHousePresenter {
    private Context mContext;
    private IAddHouseView mIAddHouseView;
    private IAddHouseModel mIAddHouseModel;

    public AddHousePresenterImpl(Context context, IAddHouseView IAddHouseView) {
        mContext = context;
        mIAddHouseView = IAddHouseView;
        mIAddHouseModel = new AddHouseModel();
    }

    @Override
    public void uploadHouseInfo(Map<String, String> fieldMap, List<String> picFilesList) {
        mIAddHouseView.startProgress();
        mIAddHouseModel.uploadHouseInfo(fieldMap, picFilesList, new IAddHouseModel
                .UploadHouseInfoCallback() {
            @Override
            public void onSuccess(StringResponse stringResponse) {
                if (stringResponse == null || "false".equals(stringResponse.getResponse())) {
                    ToastUtil.showShort(mContext, "上传失败，可能已存在该房源名称");
                } else if ("true".equals(stringResponse.getResponse())) {
                    ToastUtil.showShort(mContext, "上传成功");
                } else {
                    ToastUtil.showShort(mContext, "上传失败");
                }
                mIAddHouseView.stopProgress();
            }

            @Override
            public void onFailed() {
                ToastUtil.showShort(mContext, "服务器异常");
                mIAddHouseView.stopProgress();
            }
        });
    }
}
