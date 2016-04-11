package com.buddysoft.tbtx_android.ui.activity.album;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.buddysoft.tbtx_android.R;
import com.buddysoft.tbtx_android.app.TbtxApplication;
import com.buddysoft.tbtx_android.ui.base.ToolbarActivity;
import com.buddysoft.tbtx_android.ui.module.AddAlbumActivityModule;
import com.buddysoft.tbtx_android.ui.presenter.AddAlbumActivityPresenter;
import com.buddysoft.tbtx_android.ui.view.IAddAlbumView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;


public class AddAlbumActivity extends ToolbarActivity implements IAddAlbumView, View.OnClickListener {

    @Bind(R.id.btn_save)
    Button mBtnSave;

    @Inject
    AddAlbumActivityPresenter mPresenter;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_add_album, R.string.title_add_album, MODE_BACK);
        ButterKnife.bind(this);
    }

    @Override
    protected void setUpView() {
        initEvent();
    }

    private void initEvent() {
        mBtnSave.setOnClickListener(this);
    }

    @Override
    protected void setUpData() {

    }

    @Override
    protected void setupActivityComponent() {
        TbtxApplication.get(this).getUserComponent().plus(new AddAlbumActivityModule(this)).inject(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save:
                mPresenter.createAlbum("xxx", "");
                break;
        }
    }
}
