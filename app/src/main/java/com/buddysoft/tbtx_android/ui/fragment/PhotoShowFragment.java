package com.buddysoft.tbtx_android.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buddysoft.tbtx_android.R;
import com.buddysoft.tbtx_android.app.C;
import com.buddysoft.tbtx_android.data.entity.AlbumDetailEntity;
import com.buddysoft.tbtx_android.ui.activity.album.AlbumPhotoDetailActivity;
import com.buddysoft.tbtx_android.ui.base.BaseFragment;
import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;


/**
 * A simple {@link } subclass.
 */
public class PhotoShowFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.iv_pic)
    PhotoView mPhotoView;
    @Bind(R.id.tv_desc)
    TextView mTvDesc;
    private AlbumDetailEntity.ItemsEntity mItem;

    public PhotoShowFragment() {
        // Required empty public constructor
    }


    @Override
    protected void setUpContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_photo_show, container, false);
        ButterKnife.bind(this, mRootView);
    }

    @Override
    protected void setUpView() {
        initEvent();
    }

    private void initEvent() {
        mTvDesc.setOnClickListener(this);
    }

    @Override
    protected void setUpData() {
        Bundle bundle = getArguments();
        mItem = bundle.getParcelable(C.IntentKey.MESSAGE_EXTRA_KEY);

        Glide.with(mPhotoView.getContext())
                .load(mItem.getOriginal())
                .centerCrop()
                .placeholder(R.color.colorPrimary)
                .crossFade()
                .into(mPhotoView);
        mTvDesc.setText("赞 " + mItem.getPraiseCount() + "  评 " + mItem.getCommentCount() + "  详情");
    }


    @Override
    protected void setupFragmentComponent() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_desc:
                toDetail();
                break;
        }
    }

    private void toDetail() {
        Intent intent = new Intent(getActivity(), AlbumPhotoDetailActivity.class);
        intent.putExtra(C.IntentKey.MESSAGE_EXTRA_KEY, mItem);
        startActivity(intent);
    }
}
