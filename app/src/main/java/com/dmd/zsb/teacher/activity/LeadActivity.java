package com.dmd.zsb.teacher.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.dmd.tutor.viewpagerindicator.CirclePageIndicator;
import com.dmd.zsb.teacher.R;
import com.dmd.zsb.teacher.adapter.LeadAdapter;

public class LeadActivity extends Activity {
    private ViewPager leadViewPager;
    private CirclePageIndicator indicator;


    private int[] imageBg = {R.drawable.b_1, R.drawable.b_2, R.drawable.b_3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        leadViewPager = (ViewPager) findViewById(R.id.lead_viewPager);
        indicator = (CirclePageIndicator)findViewById(R.id.indicator);

        leadViewPager.setAdapter(new LeadAdapter(this, imageBg));
        indicator.setViewPager(leadViewPager,0);

        leadViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                indicator.setCurrentItem(arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }
        });

    }

    @Override
    public void finish() {
        super.finish();
    }
}