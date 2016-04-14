package com.buddysoft.tbtx_android.ui.activity.album;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.buddysoft.tbtx_android.R;
import com.buddysoft.tbtx_android.app.C;
import com.buddysoft.tbtx_android.app.TbtxApplication;
import com.buddysoft.tbtx_android.data.entity.AlbumDetailEntity;
import com.buddysoft.tbtx_android.data.entity.BaseEntity;
import com.buddysoft.tbtx_android.data.entity.PhotoIsPraiseEntity;
import com.buddysoft.tbtx_android.ui.base.ToolbarActivity;
import com.buddysoft.tbtx_android.ui.module.AlbumPhotoDetailActivityModule;
import com.buddysoft.tbtx_android.ui.presenter.AlbumPhotoDetailActivityPresenter;
import com.buddysoft.tbtx_android.ui.view.IAlbumPhotoDetailView;
import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AlbumPhotoDetailActivity extends ToolbarActivity implements IAlbumPhotoDetailView, View.OnClickListener {

    @Bind(R.id.iv_photo)
    ImageView mIvPhoto;
    @Bind(R.id.iv_praise)
    ImageView mIvPraise;
    @Bind(R.id.et_commit)
    EditText mEtCommit;

    private AlbumDetailEntity.ItemsEntity mPhotoDetail;

    @Inject
    AlbumPhotoDetailActivityPresenter mPresenter;

    private boolean isPraise = false;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_album_photo_detail, R.string.title_photo_detail, R.menu.menu_album_photo_detail, MODE_BACK);
        ButterKnife.bind(this);
    }

    @Override
    protected void setUpView() {
        initEvent();
    }

    private void initEvent() {
        mIvPraise.setOnClickListener(this);

        mEtCommit.setOnKeyListener(new View.OnKeyListener() {//输入完后按键盘上的搜索键【回车键改为了搜索键】

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    //修改回车键功能
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    //发表评论
                    commitReply("");
                }
                return false;
            }
        });
    }

    private void commitReply(String commentId) {
        mPresenter.commit(mPhotoDetail.getId(), mEtCommit.getText().toString(), commentId);
    }

    @Override
    protected void setUpData() {
        Intent intent = getIntent();
        if (intent != null) {
            mPhotoDetail = intent.getParcelableExtra(C.IntentKey.MESSAGE_EXTRA_KEY);
        }

        Glide.with(mIvPhoto.getContext())
                .load(mPhotoDetail.getOriginal())
                .centerCrop()
                .placeholder(R.color.colorPrimary)
                .crossFade()
                .into(mIvPhoto);
        mPresenter.isPraise(mPhotoDetail.getId());
    }

    @Override
    protected void setupActivityComponent() {
        TbtxApplication.get(this).getUserComponent().plus(new AlbumPhotoDetailActivityModule(this)).inject(this);
    }

    @Override
    public void setIsPraise(PhotoIsPraiseEntity photoIsPraiseEntity) {
        String praise = photoIsPraiseEntity.getObject().getIsPraise();
        if ("yes".equals(praise)) {
            isPraise = true;
        } else {
            isPraise = false;
        }
    }

    @Override
    public void setCanclePraise(BaseEntity entity) {
        isPraise = false;
    }

    @Override
    public void setPraise(BaseEntity entity) {
        isPraise = true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_praise:
                if (isPraise) {
                    mPresenter.canclePraise(mPhotoDetail.getId());
                } else {
                    mPresenter.praise(mPhotoDetail.getId());
                }
                break;
        }
    }
}
