package com.buddysoft.tbtx_android.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.buddysoft.tbtx_android.R;
import com.buddysoft.tbtx_android.app.TbtxApplication;
import com.buddysoft.tbtx_android.ui.base.ToolbarActivity;
import com.buddysoft.tbtx_android.ui.component.MainActivityComponent;
import com.buddysoft.tbtx_android.ui.fragment.FriendFragment;
import com.buddysoft.tbtx_android.ui.fragment.HomeFragment;
import com.buddysoft.tbtx_android.ui.fragment.MessageFragment;
import com.buddysoft.tbtx_android.ui.fragment.UserFragment;
import com.buddysoft.tbtx_android.ui.module.LoginActivityModule;
import com.buddysoft.tbtx_android.ui.module.MainActivityModule;
import com.buddysoft.tbtx_android.ui.view.IMainView;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends ToolbarActivity implements IMainView, RadioGroup.OnCheckedChangeListener {

    @Bind(R.id.frame_main)
    FrameLayout frameMain;
    @Bind(R.id.rb_home)
    RadioButton rbHome;
    @Bind(R.id.rb_msg)
    RadioButton rbMsg;
    @Bind(R.id.rb_friend)
    RadioButton rbFriend;
    @Bind(R.id.rb_user)
    RadioButton rbUser;
    @Bind(R.id.rg_menu)
    RadioGroup rgMenu;
    private FragmentManager mFragmentManager;
    private HomeFragment mHomeFragment;
    private FriendFragment mFriendFragment;
    private MessageFragment mMessageFragment;
    private UserFragment mUserFragment;
    private MainActivityComponent mMainActivityComponent;

    public MainActivityComponent getMainActivityComponent() {
        return mMainActivityComponent;
    }

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_main, -1, MODE_NONE);
        ButterKnife.bind(this);
        mFragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void setUpView() {
        rgMenu.setOnCheckedChangeListener(this);
        rbHome.setChecked(true);
    }

    @Override
    protected void setUpData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void setupActivityComponent() {
        mMainActivityComponent = TbtxApplication.get(this).getUserComponent().plus(new MainActivityModule(this));
        mMainActivityComponent.inject(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fragmentTran = mFragmentManager
                .beginTransaction();
        hideFragments(fragmentTran);
        switch (checkedId) {
            case R.id.rb_home:
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                    fragmentTran.add(R.id.frame_main, mHomeFragment);
                }
                fragmentTran.show(mHomeFragment);
                break;
            case R.id.rb_msg:
                if (mMessageFragment == null) {
                    mMessageFragment = new MessageFragment();
                    fragmentTran.add(R.id.frame_main, mMessageFragment);
                }
                fragmentTran.show(mMessageFragment);
                break;
            case R.id.rb_friend:
                if (mFriendFragment == null) {
                    mFriendFragment = new FriendFragment();
                    fragmentTran.add(R.id.frame_main, mFriendFragment);
                }
                fragmentTran.show(mFriendFragment);
                break;
            case R.id.rb_user:
                if (mUserFragment == null) {
                    mUserFragment = new UserFragment();
                    fragmentTran.add(R.id.frame_main, mUserFragment);
                }
                fragmentTran.show(mUserFragment);
                break;
        }
        fragmentTran.commitAllowingStateLoss();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (mHomeFragment != null) {
            transaction.hide(mHomeFragment);
        }
        if (mUserFragment != null) {
            transaction.hide(mUserFragment);
        }
        if (mFriendFragment != null) {
            transaction.hide(mFriendFragment);
        }
        if (mMessageFragment != null) {
            transaction.hide(mMessageFragment);
        }
    }
}
