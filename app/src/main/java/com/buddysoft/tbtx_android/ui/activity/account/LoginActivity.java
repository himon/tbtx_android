package com.buddysoft.tbtx_android.ui.activity.account;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.buddysoft.tbtx_android.R;
import com.buddysoft.tbtx_android.app.TbtxApplication;
import com.buddysoft.tbtx_android.ui.activity.MainActivity;
import com.buddysoft.tbtx_android.ui.base.BaseActivity;
import com.buddysoft.tbtx_android.ui.base.ToolbarActivity;
import com.buddysoft.tbtx_android.ui.module.LoginActivityModule;
import com.buddysoft.tbtx_android.ui.presenter.LoginActivityPresenter;
import com.buddysoft.tbtx_android.ui.view.ILoginView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;


public class LoginActivity extends ToolbarActivity implements ILoginView, View.OnClickListener {

    @Bind(R.id.et_user)
    EditText mEtUser;
    @Bind(R.id.et_password)
    EditText mEtPassword;
    @Bind(R.id.btn_login)
    Button mBtnLogin;
    @Bind(R.id.tv_find_pwd)
    TextView mTvFindPwd;

    @Inject
    LoginActivityPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_login, R.string.title_login, MODE_HOME);
        ButterKnife.bind(this);
    }

    @Override
    protected void setUpView() {
        initView();
    }

    private void initView() {
        mBtnLogin.setOnClickListener(this);
        mTvFindPwd.setOnClickListener(this);
    }

    @Override
    protected void setUpData() {

    }

    @Override
    protected void setupActivityComponent() {
        TbtxApplication.get(this).getAppComponent().plus(new LoginActivityModule(this)).inject(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                mPresenter.login(mEtUser.getText().toString(), mEtPassword.getText().toString());
                break;
        }
    }

    @Override
    public void toMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
