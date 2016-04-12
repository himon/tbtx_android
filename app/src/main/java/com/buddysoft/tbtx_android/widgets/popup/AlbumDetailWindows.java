package com.buddysoft.tbtx_android.widgets.popup;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.buddysoft.tbtx_android.R;
import com.buddysoft.tbtx_android.ui.activity.album.AddAlbumActivity;

import org.feezu.liuli.timeselector.TimeSelector;

import java.text.SimpleDateFormat;

public class AlbumDetailWindows extends PopupWindow implements View.OnClickListener {

    private Activity mActivity;
    private Button mBtnPhotoGraph, mBtnOpenalbum, mBtnEditAlbum, mBtnDelAlbum, mBtnCancel;

    public OperationInterface getOperationInterface() {
        return mOperationInterface;
    }

    public void setOperationInterface(OperationInterface operationInterface) {
        this.mOperationInterface = operationInterface;
    }

    private OperationInterface mOperationInterface;

    public AlbumDetailWindows(final Activity mContext, View parent) {
        mActivity = mContext;
        View view = View.inflate(mContext, R.layout.album_detail_popupwindows, null);
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
        mBtnPhotoGraph = (Button) ll_popup.findViewById(R.id.btn_photograph);
        mBtnOpenalbum = (Button) ll_popup.findViewById(R.id.btn_open_album);
        mBtnCancel = (Button) ll_popup.findViewById(R.id.btn_cancel);
        mBtnEditAlbum = (Button) ll_popup.findViewById(R.id.btn_edit_album);
        mBtnDelAlbum = (Button) ll_popup.findViewById(R.id.btn_del_album);
        update();
        mBtnPhotoGraph.setOnClickListener(this);
        mBtnOpenalbum.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
        mBtnEditAlbum.setOnClickListener(this);
        mBtnDelAlbum.setOnClickListener(this);

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
            case R.id.btn_photograph:
                getOperationInterface().photoGraph();
                dismiss();
                break;
            case R.id.btn_open_album:
                getOperationInterface().openAlbum();
                dismiss();
                break;
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_del_album:
                getOperationInterface().delAlbum();
                dismiss();
                break;
            case R.id.btn_edit_album:
                getOperationInterface().editAlbum();
                dismiss();
                break;
        }
    }

    public interface OperationInterface {
        public void openAlbum();

        public void delAlbum();

        public void editAlbum();

        public void photoGraph();
    }
}
