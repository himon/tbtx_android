package com.buddysoft.tbtx_android.widgets.popup;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.buddysoft.tbtx_android.R;
import com.buddysoft.tbtx_android.data.entity.CameraEntity;
import com.buddysoft.tbtx_android.ui.adapter.CommonAdapter;
import com.buddysoft.tbtx_android.ui.adapter.ViewHolder;

import java.util.List;

public class AlbumPhotoShowWindows extends PopupWindow implements View.OnClickListener {

    private Activity mActivity;
    private Button mBtnShareWechat, mBtnShareWechatCircle, mBtnSave, mBtnDel, mBtnCancel;

    public OperationInterface getOperationInterface() {
        return mOperationInterface;
    }

    public void setOperationInterface(OperationInterface operationInterface) {
        this.mOperationInterface = operationInterface;
    }

    private OperationInterface mOperationInterface;

    public AlbumPhotoShowWindows(final Activity mContext, View parent, int type) {
        mActivity = mContext;
        View view = View.inflate(mContext, R.layout.album_photo_popupwindows, null);
        view.startAnimation(AnimationUtils.loadAnimation(mContext,
                R.anim.fade_in));
        LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
                R.anim.push_bottom_in_1));

        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        mBtnShareWechat = (Button) ll_popup.findViewById(R.id.btn_share_wechat);
        mBtnShareWechatCircle = (Button) ll_popup.findViewById(R.id.btn_share_wechat_circle);
        mBtnCancel = (Button) ll_popup.findViewById(R.id.btn_cancel);
        mBtnSave = (Button) ll_popup.findViewById(R.id.btn_save_photo);
        mBtnDel = (Button) ll_popup.findViewById(R.id.btn_del_photo);
        update();
        mBtnShareWechat.setOnClickListener(this);
        mBtnShareWechatCircle.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
        mBtnSave.setOnClickListener(this);
        mBtnDel.setOnClickListener(this);

        if(type == 0){
            mBtnDel.setVisibility(View.GONE);
            mBtnSave.setVisibility(View.GONE);
        }

//        if (User.getCurrentUser()!=null&&User.getCurrentUser().getMobileRole() != -1) {
//            int role = User.getCurrentUser().getMobileRole();
//        int role = 5;
//        if (role == Role.RoleAdministrationTeacher.getValue()
//                || role == Role.RoleKindergartenLeader.getValue()
//                || role == Role.RoleFirstTeacher.getValue()
//                || role == Role.RoleSecondTeacher.getValue()) {
//            mBtnAddAlbum.setVisibility(View.VISIBLE);
//        }
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_share_wechat:
                getOperationInterface().shareWechat();
                dismiss();
                break;
            case R.id.btn_share_wechat_circle:
                getOperationInterface().shareWechatCircle();
                dismiss();
                break;
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_save_photo:
                getOperationInterface().save();
                dismiss();
                break;
            case R.id.btn_del_photo:
                getOperationInterface().delete();
                dismiss();
                break;
        }
    }

    public interface OperationInterface {
        public void shareWechat();

        public void shareWechatCircle();

        public void save();

        public void delete();
    }
}
