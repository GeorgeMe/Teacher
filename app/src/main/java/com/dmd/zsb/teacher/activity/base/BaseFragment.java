package com.dmd.zsb.teacher.activity.base;


import com.dmd.tutor.base.BaseLazyFragment;
import com.dmd.tutor.utils.StringUtils;
import com.dmd.tutor.widgets.ProgressDialog;
import com.dmd.zsb.TutorApplication;
import com.dmd.zsb.mvp.view.BaseView;
import com.dmd.zsb.teacher.R;

/**
 *
 */
public abstract class BaseFragment extends BaseLazyFragment implements BaseView {
    private ProgressDialog progressDialog=null;
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
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

    protected TutorApplication getBaseApplication() {
        return (TutorApplication) getActivity().getApplicationContext();
    }

}
