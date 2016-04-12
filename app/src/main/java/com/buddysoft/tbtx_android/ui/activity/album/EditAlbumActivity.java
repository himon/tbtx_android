package com.buddysoft.tbtx_android.ui.activity.album;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import com.buddysoft.tbtx_android.R;
import com.buddysoft.tbtx_android.app.C;
import com.buddysoft.tbtx_android.app.TbtxApplication;
import com.buddysoft.tbtx_android.data.entity.EditAlbumEntity;
import com.buddysoft.tbtx_android.ui.base.ToolbarActivity;
import com.buddysoft.tbtx_android.ui.module.EditAlbumActivityModule;
import com.buddysoft.tbtx_android.ui.presenter.EditAlbumActivityPresenter;
import com.buddysoft.tbtx_android.ui.view.IEditAlbumView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EditAlbumActivity extends ToolbarActivity implements IEditAlbumView{

    @Bind(R.id.tv_album_name)
    EditText mEtAlbumName;
    private String mId;
    private String mName;

    @Inject
    EditAlbumActivityPresenter mPresenter;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_edit_album, R.string.title_edit_album, R.menu.menu_edit_album, MODE_BACK);
        ButterKnife.bind(this);
    }

    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpData() {
        Intent intent = getIntent();
        if (intent != null) {
            mId = intent.getStringExtra(C.IntentKey.MESSAGE_EXTRA_KEY);
            mName = intent.getStringExtra(C.IntentKey.MESSAGE_EXTRA_KEY2);
        }
        mEtAlbumName.setText(mName);
    }

    @Override
    protected void setupActivityComponent() {
        TbtxApplication.get(this).getUserComponent().plus(new EditAlbumActivityModule(this)).inject(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                mPresenter.editAlbum(mId, mEtAlbumName.getText().toString(), "");
                break;
        }
        return true;
    }

    @Override
    public void setSaveSuccess(EditAlbumEntity editAlbumEntity) {
        finish();
    }
}
