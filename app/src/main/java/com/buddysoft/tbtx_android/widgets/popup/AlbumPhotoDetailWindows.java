package com.buddysoft.tbtx_android.widgets.popup;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.buddysoft.tbtx_android.R;

/**
 * Created by lc on 16/4/17.
 */
public class AlbumPhotoDetailWindows extends PopupWindow implements View.OnClickListener{
    private Activity mActivity;
    private Button mBtnDel, mBtnCancel;

    public OperationInterface getOperationInterface() {
        return mOperationInterface;
    }

    public void setOperationInterface(OperationInterface operationInterface) {
        this.mOperationInterface = operationInterface;
    }

    private OperationInterface mOperationInterface;

    public AlbumPhotoDetailWindows(final Activity mContext, View parent) {
        mActivity = mContext;
        View view = View.inflate(mContext, R.layout.album_photo_detail_popupwindows, null);
        view.startAnimation(AnimationUtils.loadAnimation(mContext,
                R.anim.fade_in));
        LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
                R.anim.push_bottom_in_1));

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        mBtnCancel = (Button) ll_popup.findViewById(R.id.btn_cancel);
        mBtnDel = (Button) ll_popup.findViewById(R.id.btn_del_comment);
        update();
        mBtnCancel.setOnClickListener(this);
        mBtnDel.setOnClickListener(this);

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
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_del_comment:
                getOperationInterface().delete();
                dismiss();
                break;
        }
    }

    public interface OperationInterface {

        public void delete();
    }
}
