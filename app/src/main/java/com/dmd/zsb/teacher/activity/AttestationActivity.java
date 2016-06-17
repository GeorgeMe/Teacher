package com.dmd.zsb.teacher.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.dmd.dialog.DialogAction;
import com.dmd.dialog.MaterialDialog;
import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.utils.OnUploadProcessListener;
import com.dmd.tutor.utils.StringUtils;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.mvp.presenter.impl.AttestationPresenterImpl;
import com.dmd.zsb.mvp.view.AttestationView;
import com.dmd.zsb.protocol.response.attestationResponse;
import com.dmd.zsb.teacher.R;
import com.dmd.zsb.teacher.activity.base.BaseActivity;
import com.dmd.zsb.utils.ImageUtil;
import com.dmd.zsb.widgets.ToastView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;

public class AttestationActivity extends BaseActivity implements AttestationView,OnUploadProcessListener {

    @Bind(R.id.top_bar_back)
    TextView topBarBack;
    @Bind(R.id.top_bar_title)
    TextView topBarTitle;
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.spinner)
    Spinner spinner;
    @Bind(R.id.et_number)
    EditText etNumber;
    @Bind(R.id.btn_img_add)
    Button btnImgAdd;
    @Bind(R.id.img_show)
    ImageView imgShow;
    @Bind(R.id.btn_attestaction)
    Button btnAttestaction;

    private AttestationPresenterImpl attestationPresenter;
    private Dialog mDialog;
    private File mFileDir;
    private File mFile;
    private String mFileName = "";
    private final int REQUEST_CAMERA = 1;
    private final int REQUEST_PHOTO = 2;
    private final int REQUEST_PHOTOZOOM = 3;
    private File mFileZoomDir;
    private String mImagePath;
    private String type="";
    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_attestation;
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
        topBarTitle.setText("申请认证");
        imgShow.setVisibility(View.GONE);
        btnAttestaction.setVisibility(View.GONE);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                showToast("没有选择认证类型");
            }
        });
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

    @OnClick({R.id.top_bar_back, R.id.btn_img_add, R.id.img_show, R.id.btn_attestaction})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back:
                finish();
                break;
            case R.id.btn_img_add:
                showDialog();
                break;
            case R.id.img_show:
                new MaterialDialog.Builder(mContext).content("重新选择图片或者拍照？").positiveText("确定").negativeText("取消").onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //提交的参数封装
                        showDialog();
                        dialog.dismiss();
                    }
                }).show();

                break;
            case R.id.btn_attestaction:
                try {
                    attestationPresenter=new AttestationPresenterImpl(mContext,this,this);
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("appkey", Constants.ZSBAPPKEY);
                    jsonObject.put("version", Constants.ZSBVERSION);
                    jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid","sid"));
                    jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid","uid"));
                    jsonObject.put("fileMime", "image/png");
                    jsonObject.put("attestation_name", etName.getText().toString());
                    jsonObject.put("attestation_type", type);
                    jsonObject.put("attestation_number", etNumber.getText().toString());
                    jsonObject.put("attestation_img", mFileName);

                    JSONObject formFile=new JSONObject();
                    if (!StringUtils.StringIsEmpty(mFileName))
                    formFile.put("fileName",mFileName);
                    if (!StringUtils.StringIsEmpty(mImagePath))
                    formFile.put("filePath",mImagePath);
                    formFile.put("parameterName","file");
                    formFile.put("contentType","application/octet-stream");

                    attestationPresenter.onAttestation(jsonObject,formFile);
                }catch (JSONException j){

                }

                break;
        }
    }

    @Override
    public void onAttestation(attestationResponse response) {
        if (response.errno==0){
            showToast(response.msg);
        }else {
            showToast(response.msg);
        }
        finish();
    }

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
                    mFileName=file.getName();
                    Log.e(TAG_LOG,file.getAbsolutePath());
                    Log.e(TAG_LOG,"mFileName   :"+mFileName);
                    Log.e(TAG_LOG,"mImagePath  :"+mImagePath);
                    Picasso.with(mContext).load(file).into(imgShow);
                    btnAttestaction.setVisibility(View.VISIBLE);
                    imgShow.setVisibility(View.VISIBLE);
                    btnImgAdd.setVisibility(View.GONE);
                } else {
                    btnAttestaction.setVisibility(View.GONE);
                    btnImgAdd.setVisibility(View.VISIBLE);
                    imgShow.setVisibility(View.GONE);
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

    @Override
    public void onUploadDone(int responseCode, String message) {

    }

    @Override
    public void onUploadProcess(int uploadSize) {

    }

    @Override
    public void initUpload(int fileSize) {

    }
}
