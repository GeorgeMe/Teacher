package com.dmd.zsb.teacher.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dmd.dialog.DialogAction;
import com.dmd.dialog.GravityEnum;
import com.dmd.dialog.MaterialDialog;
import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.utils.OnUploadProcessListener;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.mvp.presenter.impl.ChangeAvatarPresenterImpl;
import com.dmd.zsb.mvp.presenter.impl.SignOutPresenterImpl;
import com.dmd.zsb.mvp.view.ChangeAvatarView;
import com.dmd.zsb.mvp.view.SignOutView;
import com.dmd.zsb.teacher.R;
import com.dmd.zsb.teacher.activity.base.BaseActivity;
import com.dmd.zsb.utils.ImageUtil;
import com.dmd.zsb.widgets.ToastView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity implements ChangeAvatarView,SignOutView,OnUploadProcessListener{

    private Dialog mDialog;
    private File mFileDir;
    private File mFile;
    private String mFileName = "";
    private final int REQUEST_CAMERA = 1;
    private final int REQUEST_PHOTO = 2;
    private final int REQUEST_PHOTOZOOM = 3;
    private File mFileZoomDir;
    private String mImagePath;

    @Bind(R.id.top_bar_back)
    TextView topBarBack;
    @Bind(R.id.top_bar_title)
    TextView topBarTitle;
    @Bind(R.id.tv_setting_nickname)
    TextView tvSettingNickname;
    @Bind(R.id.tv_setting_avatar)
    TextView tvSettingAvatar;
    @Bind(R.id.tv_setting_signature)
    TextView tvSettingSignature;
    @Bind(R.id.tv_setting_brief)
    TextView tvSettingBrief;
    @Bind(R.id.tv_setting_change_password)
    TextView tvSettingChangePassword;
    @Bind(R.id.tv_setting_feedback)
    TextView tvSettingFeedback;
    @Bind(R.id.btn_sign_out)
    Button btnSignOut;


    private ChangeAvatarPresenterImpl changeAvatarPresenter;
    private SignOutPresenterImpl signOutPresenter;
    private MaterialDialog dialog;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_setting;
    }

    @Override
    public void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        changeAvatarPresenter=new ChangeAvatarPresenterImpl(this,mContext,this);
        ///settingPresenter=new SettingPresenterImpl(SettingActivity.this,this,this);
        topBarTitle.setText(getResources().getText(R.string.setting_title));
        if (XmlDB.getInstance(mContext).getKeyBooleanValue("isLogin",false)){
            btnSignOut.setVisibility(View.GONE);
        }else {
            btnSignOut.setVisibility(View.GONE);
        }
    }

    @Override
    public void showTip(String msg) {
        showToast(msg);
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @OnClick({R.id.top_bar_back, R.id.tv_setting_nickname, R.id.tv_setting_avatar, R.id.tv_setting_signature, R.id.tv_setting_brief, R.id.tv_setting_change_password, R.id.tv_setting_feedback, R.id.btn_sign_out})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back:
                finish();
                break;
            case R.id.tv_setting_nickname:
                if (XmlDB.getInstance(mContext).getKeyBooleanValue("isLogin",false)){
                    readyGo(NickNameActivity.class);
                }else {
                    showToast("请先登录");
                }
                break;
            case R.id.tv_setting_avatar:
                if (XmlDB.getInstance(mContext).getKeyBooleanValue("isLogin",false)){
                    showDialog();
                }else {
                    showToast("请先登录");
                }
                break;
            case R.id.tv_setting_signature:
                if (XmlDB.getInstance(mContext).getKeyBooleanValue("isLogin",false)){
                    readyGo(SignatureActivity.class);
                }else {
                    showToast("请先登录");
                }
                break;
            case R.id.tv_setting_brief:
                if (XmlDB.getInstance(mContext).getKeyBooleanValue("isLogin",false)){
                    readyGo(BriefActivity.class);
                }else {
                    showToast("请先登录");
                }
                break;
            case R.id.tv_setting_change_password:
                if (XmlDB.getInstance(mContext).getKeyBooleanValue("isLogin",false)){
                    readyGo(ChangePasswordActivity.class);
                }else {
                    showToast("请先登录");
                }
                break;
            case R.id.tv_setting_feedback:
                if (XmlDB.getInstance(mContext).getKeyBooleanValue("isLogin",false)){
                    readyGo(FeedbackActivity.class);
                }else {
                    showToast("请先登录");
                }
                break;
            case R.id.btn_sign_out:
                    new MaterialDialog.Builder(mContext).title("提示").content("确认要退出登录？").negativeText("取消").positiveText("确认").onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    }).onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            onSignOutView();
                            dialog.dismiss();
                        }
                    }).show();
                break;
        }
    }
    @Override
    public void onSignOutView() {
        signOutPresenter=new SignOutPresenterImpl(this,mContext);
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("appkey", Constants.ZSBAPPKEY);
            jsonObject.put("version", Constants.ZSBVERSION);
            jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid","sid"));
            jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid","uid"));
            signOutPresenter.onSignOut(jsonObject);
        }catch (JSONException j){

        }

    }

    @Override
    public void onSuccess() {
        showTip("退出成功");
        finish();
    }

    @Override
    public void onUploadDone(int responseCode, String message) {
        Message msg = Message.obtain();
        msg.what = 3;
        msg.arg1 = responseCode;
        msg.obj = message;
        handler.sendMessage(msg);
    }

    @Override
    public void onUploadProcess(int uploadSize) {
        Message msg = Message.obtain();
        msg.what = 2;
        msg.arg1 = uploadSize;
        msg.obj = "传送中。。。。";
        handler.sendMessage(msg);
    }

    @Override
    public void initUpload(int fileSize) {
        Message msg = Message.obtain();
        msg.what = 1;
        msg.arg1 = fileSize;
        handler.sendMessage(msg);
    }


    @Override
    public void showError(String msg) {
        showToast(msg);
    }

    private Handler handler = new Handler() {
        private  int length=0;
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1://开始打开进度对话框
                    length=msg.arg1;
                    dialog= new MaterialDialog.Builder(SettingActivity.this)
                            .title("上传进度")
                            .content("请稍后...")
                            .contentGravity(GravityEnum.CENTER)
                            .progress(false, 100, true)
                            .cancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {

                                }
                            })
                            .showListener(new DialogInterface.OnShowListener() {
                                @Override
                                public void onShow(DialogInterface dialogInterface) {

                                }
                            }).show();
                    break;
                case 2://进度
                    int i=msg.arg1*100/length;
                    if(i%10==0){
                        dialog.setProgress(i);
                    }
                    break;
                case 3://异常 or 结束 or 完成
                    switch (msg.arg1) {
                        case 10: {
                            showToast(msg.obj.toString());
                            break;
                        }
                        case 11: {
                            XmlDB.getInstance(mContext).saveKey("ChangeAvatar",true);
                            showToast(msg.obj.toString());
                            break;
                        }
                        case 12: {
                            showToast(msg.obj.toString());
                            break;
                        }
                    }
                    dialog.dismiss();
                    dialog=null;
                    break;
            }
        }
    };

    private void showDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.photo_dialog, null);
        mDialog = new Dialog(this, R.style.dialog);
        mDialog.setContentView(view);

        mDialog.setCanceledOnTouchOutside(true);
        mDialog.show();
        LinearLayout requsetCameraLayout = (LinearLayout) view.findViewById(R.id.register_camera);
        LinearLayout requestPhotoLayout = (LinearLayout) view.findViewById(R.id.register_photo);

        requsetCameraLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mDialog.dismiss();
                if (mFileDir == null) {
                    mFileDir = new File(Constants.FILEPATH + "img/");
                    if (!mFileDir.exists()) {
                        mFileDir.mkdirs();
                    }
                }
                mFileName = Constants.FILEPATH + "img/" + "temp.jpg";
                mFile = new File(mFileName);
                Uri imageuri = Uri.fromFile(mFile);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageuri);
                intent.putExtra("return-data", false);
                startActivityForResult(intent, REQUEST_CAMERA);
            }
        });

        requestPhotoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mDialog.dismiss();
                Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(picture, REQUEST_PHOTO);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                File files = new File(mFileName);
                if (files.exists()) {
                    mImagePath = mFileName;
                    mImagePath = startPhotoZoom(Uri.fromFile(new File(mImagePath)));
                }
            } else if (requestCode == REQUEST_PHOTO) {
                Uri selectedImage = data.getData();
                mImagePath = startPhotoZoom(selectedImage);
            } else if (requestCode == REQUEST_PHOTOZOOM) {
                File file = new File(ImageUtil.zoomImage(mImagePath, 350));
                if (file.exists()) {
                    JSONObject jsonObject=new JSONObject();
                    JSONObject formFile=new JSONObject();
                    try {
                        jsonObject.put("appkey", Constants.ZSBAPPKEY);
                        jsonObject.put("version", Constants.ZSBVERSION);
                        jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid","sid"));
                        jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid","uid"));
                        jsonObject.put("fileMime", "image/png");

                        formFile.put("fileName",file.getName());
                        formFile.put("filePath",file.getAbsolutePath());
                        formFile.put("parameterName","file");
                        formFile.put("contentType","application/octet-stream");


                    }catch (JSONException j){

                    }
                    changeAvatarPresenter.onChangeAvatar(jsonObject,formFile);
                } else {
                    ToastView toast = new ToastView(this, getString(R.string.photo_not_exsit));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        }
    }

    private String startPhotoZoom(Uri uri) {

        if (mFileZoomDir == null) {
            mFileZoomDir = new File(Constants.FILEPATH + "img/");
            if (!mFileZoomDir.exists()) {
                mFileZoomDir.mkdirs();
            }
        }

        String fileName;
        fileName = "/temp.jpg";

        String filePath = mFileZoomDir + fileName;
        File loadingFile = new File(filePath);

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 400);
        intent.putExtra("aspectY", 400);
        intent.putExtra("output", Uri.fromFile(loadingFile));// 输出到文件
        intent.putExtra("outputFormat", "PNG");// 返回格式
        intent.putExtra("noFaceDetection", true); // 去除面部检测
        intent.putExtra("return-data", false); // 不要通过Intent传递截获的图片
        startActivityForResult(intent, REQUEST_PHOTOZOOM);

        return filePath;

    }

}
