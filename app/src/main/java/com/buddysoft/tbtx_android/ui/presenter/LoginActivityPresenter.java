package com.buddysoft.tbtx_android.ui.presenter;

import android.text.TextUtils;

import com.buddysoft.tbtx_android.app.TbtxApplication;
import com.buddysoft.tbtx_android.app.component.AppProductionComponent;
import com.buddysoft.tbtx_android.app.module.UserModule;
import com.buddysoft.tbtx_android.data.LiveManager;
import com.buddysoft.tbtx_android.data.entity.UserEntity;
import com.buddysoft.tbtx_android.data.observer.SimpleObserver;
import com.buddysoft.tbtx_android.ui.activity.account.LoginActivity;
import com.buddysoft.tbtx_android.ui.view.ILoginView;
import com.buddysoft.tbtx_android.util.RegexUtils;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.jude.utils.JUtils;

import org.w3c.dom.Text;

import rx.Observable;
import rx.functions.Func2;


/**
 * Created by Administrator on 2016/4/8.
 */
public class LoginActivityPresenter {

    private ILoginView mILoginView;
    private LiveManager mLiveManager;
    private final AppProductionComponent mAppProductionComponent;

    public LoginActivityPresenter(ILoginView iLoginView) {
        this.mILoginView = iLoginView;

        mAppProductionComponent = ((LoginActivity) mILoginView).getAppProductionComponent();
        Futures.addCallback(mAppProductionComponent.liveManager(), new FutureCallback<LiveManager>() {
            @Override
            public void onSuccess(LiveManager result) {
                mLiveManager = result;
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void login(String user, String password) {

        if (mLiveManager == null) return;

        if (TextUtils.isEmpty(password)) {
            return;
        }

        if (TextUtils.isEmpty(user)) {
            return;
        }

        String mobile = "";
        String username = "";
        boolean b = RegexUtils.isMobilePhoneNumber(username);
        if (b) {
            mobile = username;
        } else {
            username = user;
        }

        Observable.combineLatest(
                Observable.from(mAppProductionComponent.userModuleFactory()),
                mLiveManager.login(username, mobile, password),
                new Func2<UserModule.Factory, UserEntity, UserModule>() {
                    @Override
                    public UserModule call(UserModule.Factory factory, UserEntity userEntity) {
                        return factory.create(userEntity);
                    }
                })
                .subscribe(new SimpleObserver<UserModule>() {

                    @Override
                    public void onNext(UserModule userModule) {
                        TbtxApplication.get((LoginActivity) mILoginView).createUserComponent(userModule);
                        mILoginView.toMainActivity();
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.print(e.getMessage());
                    }
                });
    }
}
