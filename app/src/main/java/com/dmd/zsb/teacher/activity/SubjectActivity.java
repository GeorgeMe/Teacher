package com.dmd.zsb.teacher.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.utils.StringUtils;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.db.ZSBDataBase;
import com.dmd.zsb.db.dao.GradeDao;
import com.dmd.zsb.db.dao.SubjectDao;
import com.dmd.zsb.entity.GradeEntity;
import com.dmd.zsb.entity.SubjectEntity;
import com.dmd.zsb.mvp.presenter.impl.SubjectPresenterImpl;
import com.dmd.zsb.mvp.view.SubjectView;
import com.dmd.zsb.teacher.R;
import com.dmd.zsb.teacher.activity.base.BaseActivity;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class SubjectActivity extends BaseActivity implements SubjectView{

    @Bind(R.id.top_bar_back)
    TextView topBarBack;
    @Bind(R.id.top_bar_title)
    TextView topBarTitle;
    @Bind(R.id.et_subject)
    EditText etSubject;
    @Bind(R.id.btn_save)
    Button btnSave;

    private SubjectPresenterImpl subjectPresenter;
    private OptionsPickerView optionsPickerView;

    private ArrayList<GradeEntity> gradeEntities= getGrades();
    private SubjectEntity subjectEntity;
    private ArrayList<String> arrayList1=new ArrayList<>();
    private ArrayList<ArrayList<String>> arrayList2=new ArrayList<>();

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_subject;
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
        subjectPresenter =new SubjectPresenterImpl(mContext,this);
        optionsPickerView=new OptionsPickerView(mContext);
        gradeEntities= getGrades();

        for (int i=0;i<gradeEntities.size();i++){
            arrayList1.add(gradeEntities.get(i).getGrade_name());
            ArrayList<SubjectEntity> subjectEntities=getSubjects(gradeEntities.get(i).getGrade_id());
            ArrayList<String> strings=new ArrayList<>();
            strings.clear();
            for (int j=0;j<subjectEntities.size();j++){
                strings.add(subjectEntities.get(j).getSub_name());
            }
            arrayList2.add(strings);

        }
        optionsPickerView.setPicker(arrayList1,arrayList2,true);
        optionsPickerView.setSelectOptions(1,1);
        optionsPickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                 subjectEntity=  getSubjectEntity(getGradeEntity(arrayList1.get(options1)).getGrade_id(),arrayList2.get(options1).get(option2));
                etSubject.setText(arrayList1.get(options1)+arrayList2.get(options1).get(option2));
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

    @OnClick({R.id.top_bar_back,R.id.et_subject, R.id.btn_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back:
                finish();
                break;
            case R.id.et_subject:
                optionsPickerView.show();
                break;
            case R.id.btn_save:
                if (StringUtils.StringIsEmpty(etSubject.getText().toString())){
                    showTip("填写科目");
                }else if (subjectEntity==null){
                    showTip("科目不存在");
                }else {
                    JsonObject jsonObject=new JsonObject();
                    jsonObject.addProperty("appkey", Constants.ZSBAPPKEY);
                    jsonObject.addProperty("version", Constants.ZSBVERSION);
                    jsonObject.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid","sid"));
                    jsonObject.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid","uid"));
                    jsonObject.addProperty("subject",subjectEntity.getSub_id());
                    subjectPresenter.onChangeSubject(jsonObject);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subjectEntity=null;
    }

    @Override
    public void toSettingView() {
        finish();
    }

    @Override
    public void showTip(String msg) {
        showToast(msg);
    }

    private ArrayList<GradeEntity> getGrades() {
        GradeDao gradeDao = new GradeDao(ZSBDataBase.getInstance(mContext));
        return gradeDao.getGrades();
    }

    private ArrayList<SubjectEntity> getSubjects(String grade_id) {
        SubjectDao subjectDao = new SubjectDao(ZSBDataBase.getInstance(mContext));
        return subjectDao.getGrades(grade_id);
    }
    public GradeEntity getGradeEntity(String grade_name){
        GradeDao gradeDao = new GradeDao(ZSBDataBase.getInstance(mContext));
        return gradeDao.getGradeEntity(grade_name);
    }
    public SubjectEntity getSubjectEntity(String grade_id,String sub_name){
        SubjectDao subjectDao = new SubjectDao(ZSBDataBase.getInstance(mContext));
        return subjectDao.getSubjectEntity(grade_id,sub_name);
    }
}
