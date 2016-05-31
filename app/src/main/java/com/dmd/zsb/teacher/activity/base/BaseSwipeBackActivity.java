package com.dmd.zsb.teacher.activity.base;

import android.support.v7.widget.Toolbar;

import com.dmd.tutor.base.BaseSwipeBackCompatActivity;
import com.dmd.tutor.utils.StringUtils;
import com.dmd.tutor.widgets.ProgressDialog;
import com.dmd.zsb.TutorApplication;
import com.dmd.zsb.mvp.view.BaseView;
import com.dmd.zsb.teacher.R;

import butterknife.ButterKnife;

/**
 *
 */
public abstract class BaseSwipeBackActivity extends BaseSwipeBackCompatActivity implements BaseView {

    protected Toolbar mToolbar;
    private ProgressDialog progressDialog=null;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mToolbar = ButterKnife.findById(this, R.id.common_toolbar);
        if (null != mToolbar) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected TutorApplication getBaseApplication() {
        return (TutorApplication) getApplication();
    }


    @Override
    public void showError(String msg) {
        toggleShowError(true, msg, null);
    }

    @Override
    public void showException(String msg) {
        toggleShowError(true, msg, null);
    }

    @Override
    public void showNetError() {
        toggleNetworkError(true, null);
    }

    @Override
    public void showLoading(String msg) {
        if (progressDialog==null) {
            if(StringUtils.StringIsEmpty(msg)){
                progressDialog=new ProgressDialog(mContext,getString(R.string.please_later_on));
                progressDialog.show();
            }else {
                progressDialog=new ProgressDialog(mContext,msg);
                progressDialog.show();
            }
        }else {
            progressDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (progressDialog!=null) {
            progressDialog.dismiss();
            progressDialog=null;
        }
    }

}
