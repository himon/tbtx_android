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

public class SearchAlbumWindows extends PopupWindow implements View.OnClickListener {

    private Activity mActivity;
    private Button mBtnNameSearch, mBtnTimeSearch, mBtnAddAlbum, mBtnCancel;
    private TimeSelector timeSelector;
    private String mDefultTime;

    public OperationInterface getOperationInterface() {
        return mOperationInterface;
    }

    public void setOperationInterface(OperationInterface operationInterface) {
        this.mOperationInterface = operationInterface;
    }

    private OperationInterface mOperationInterface;

    public SearchAlbumWindows(final Activity mContext, View parent) {
        mActivity = mContext;
        View view = View.inflate(mContext, R.layout.search_album_popupwindows, null);
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
        mBtnNameSearch = (Button) ll_popup.findViewById(R.id.btn_name_search);
        mBtnTimeSearch = (Button) ll_popup.findViewById(R.id.btn_time_search);
        mBtnCancel = (Button) ll_popup.findViewById(R.id.btn_cancel);
        mBtnAddAlbum = (Button) ll_popup.findViewById(R.id.btn_create_album);
        update();
        mBtnNameSearch.setOnClickListener(this);
        mBtnTimeSearch.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
        mBtnAddAlbum.setOnClickListener(this);
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd   hh:mm");
        mDefultTime = sDateFormat.format(new java.util.Date());

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
            case R.id.btn_name_search:
                mOperationInterface.searchName();
                dismiss();
                break;
            case R.id.btn_time_search:
                timeSelector = new TimeSelector(mActivity, new TimeSelector.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        mOperationInterface.searchTime(time);
                    }
                }, "2014-01-01 12:00", mDefultTime);
                timeSelector.setMode(TimeSelector.MODE.YMD);
                timeSelector.setIsLoop(false);
                timeSelector.show();
                dismiss();
                break;
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_create_album:
                Intent intent = new Intent(mActivity, AddAlbumActivity.class);
                mActivity.startActivity(intent);
                dismiss();
                break;
        }
    }

    public interface OperationInterface {
        public void searchTime(String time);

        public void searchName();
    }
}
