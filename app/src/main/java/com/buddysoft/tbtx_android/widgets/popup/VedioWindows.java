package com.buddysoft.tbtx_android.widgets.popup;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.buddysoft.tbtx.R;
import com.buddysoft.tbtx.adapter.CommonAdapter;
import com.buddysoft.tbtx.adapter.ViewHolder;
import com.buddysoft.tbtx.model.Camera;

import java.util.List;

public class VedioWindows extends PopupWindow implements View.OnClickListener {

    private Activity mActivity;
    private GridView mGvView;
    private BaseAdapter mBaseAdapter;

    public OperationInterface getOperationInterface() {
        return mOperationInterface;
    }

    public void setOperationInterface(OperationInterface operationInterface) {
        this.mOperationInterface = operationInterface;
    }

    private OperationInterface mOperationInterface;

    public VedioWindows(final Activity mContext, View parent, List<Camera> cameras) {
        mActivity = mContext;
        View view = View.inflate(mContext, R.layout.video_popupwindows, null);
        view.startAnimation(AnimationUtils.loadAnimation(mContext,
                R.anim.fade_in));
        LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ly_container);
        ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
                R.anim.push_bottom_in_1));
        mGvView = (GridView) view.findViewById(R.id.gv_video);
        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        initView(cameras);
        update();
    }

    private void initView(final List<Camera> cameras) {
        mBaseAdapter = new CommonAdapter<Camera>(mActivity, cameras, R.layout.camera_item) {
            @Override
            public void convert(final ViewHolder helper, final Camera item) {
                CheckBox cb = helper.getView(R.id.cb_camera_name);
                cb.setText(item.getName());
                cb.setChecked(item.getIsCheck());
                cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (mOperationInterface != null) {
                            mOperationInterface.playVideo(item, helper.getPosition());
                        }
                        dismiss();
                    }
                });
            }
        };
        mGvView.setAdapter(mBaseAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    public interface OperationInterface {
        public void playVideo(Camera camera, int position);
    }
}
