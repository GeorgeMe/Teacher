package com.dmd.zsb.teacher.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmd.zsb.teacher.R;
import com.dmd.zsb.teacher.activity.LeadActivity;
import com.dmd.zsb.teacher.activity.MainActivity;
import com.dmd.zsb.teacher.activity.SignInActivity;
import com.dmd.tutor.utils.XmlDB;


public class LeadAdapter extends PagerAdapter {

    private int[] imageBg;
    private LayoutInflater mInflater;
    private Context mContext;

    public LeadAdapter(Context context, int[] imageBg) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.imageBg = imageBg;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return imageBg.length;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {

        View imageLayout = mInflater.inflate(R.layout.lead_item, null);
        ImageView image = (ImageView) imageLayout.findViewById(R.id.lead_item);
        TextView start = (TextView) imageLayout.findViewById(R.id.start_expexperience);
        image.setBackgroundResource(imageBg[position]);

        ((ViewPager) view).addView(imageLayout, 0);
        if (imageBg.length - 1 == position) {
            start.setVisibility(View.VISIBLE);
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext, MainActivity.class);
                    (mContext).startActivity(intent);
                    ((LeadActivity) mContext).overridePendingTransition(R.anim.right_in, R.anim.right_out);

                    XmlDB.getInstance(mContext).saveKey("isFirstRunLead", false);
                    ((LeadActivity) mContext).finish();
                }
            });
        }

        return imageLayout;
    }
}

