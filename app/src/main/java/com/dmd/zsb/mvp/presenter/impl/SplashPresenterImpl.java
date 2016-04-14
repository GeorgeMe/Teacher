package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;
import android.util.Log;
import android.view.animation.Animation;

import com.dmd.zsb.db.ZSBDataBase;
import com.dmd.zsb.db.dao.GradeDao;
import com.dmd.zsb.db.dao.SubjectDao;
import com.dmd.zsb.entity.response.SplashResponse;
import com.dmd.zsb.mvp.interactor.SplashInteractor;
import com.dmd.zsb.mvp.interactor.impl.SplashInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.Presenter;
import com.dmd.zsb.mvp.presenter.SplashPresenter;
import com.dmd.zsb.mvp.view.SplashView;
import com.google.gson.JsonObject;


public class SplashPresenterImpl implements SplashPresenter, Presenter, BaseSingleLoadedListener<SplashResponse> {

    private Context mContext = null;
    private SplashView mSplashView = null;
    private SplashInteractor mSplashInteractor = null;
    private GradeDao gradeDao;
    private SubjectDao subjectDao;

    public SplashPresenterImpl(Context context, SplashView splashView) {
        if (null == splashView) {
            throw new IllegalArgumentException("Constructor's parameters must not be Null");
        }
        gradeDao = new GradeDao(ZSBDataBase.getInstance(context));
        subjectDao = new SubjectDao(ZSBDataBase.getInstance(context));
        mContext = context;
        mSplashView = splashView;
        mSplashInteractor = new SplashInteractorImpl(this);
    }

    @Override
    public void initialized() {
        mSplashView.initializeUmengConfig();
        mSplashView.initializeViews(mSplashInteractor.getVersionName(mContext),
                mSplashInteractor.getCopyright(mContext),
                mSplashInteractor.getBackgroundImageResID());

        Animation animation = mSplashInteractor.getBackgroundImageAnimation(mContext);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
/*                SimpleDateFormat sdf = new SimpleDateFormat("HH时mm分ss秒SSS");
                String time = sdf.format(new Date(System.currentTimeMillis()));
                TLog.d("SplashPresenterImpl","执行动画  "+time);*/
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //计时 5秒后进入主页
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mSplashView.animateBackgroundImage(animation);
    }

    @Override
    public void loadingInitData(JsonObject jsonObject) {
        mSplashInteractor.loadingInitData(jsonObject);
    }

    @Override
    public void onSuccess(SplashResponse data) {
        Log.e("onSuccess", "a ");
        gradeDao.saveGrade(data.getGradeList());
        subjectDao.saveSubject(data.getSubjectList());
        mSplashView.navigateToHomePage();
    }

    @Override
    public void onError(String msg) {
        mSplashView.navigateToHomePage();
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
