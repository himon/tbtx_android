package com.buddysoft.tbtx_android.ui.activity.album;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.buddysoft.tbtx_android.R;
import com.buddysoft.tbtx_android.data.event.AlbumSearchEvent;
import com.buddysoft.tbtx_android.ui.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;


public class AlbumSearchByNameActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.et_key)
    EditText mEtSearch;
    @Bind(R.id.btn_search)
    ImageButton mBtnSearch;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_album_search_by_name);
        ButterKnife.bind(this);
    }

    @Override
    protected void setUpView() {
        initEvent();
    }

    private void initEvent() {
        mBtnSearch.setOnClickListener(this);
    }

    @Override
    protected void setUpData() {

    }

    @Override
    protected void setupActivityComponent() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_search:
                if(TextUtils.isEmpty(mEtSearch.getText().toString())){
                    return;
                }
                EventBus.getDefault().post(new AlbumSearchEvent(mEtSearch.getText().toString()));
                finish();
                break;
        }
    }
}
