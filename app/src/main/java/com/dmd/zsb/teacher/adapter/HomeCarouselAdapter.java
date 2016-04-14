package com.dmd.zsb.teacher.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dmd.tutor.base.BaseWebActivity;
import com.dmd.tutor.rollviewpager.adapter.DynamicPagerAdapter;
import com.dmd.zsb.entity.AdvertisementEntity;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * 广告轮播
 * Created by Administrator on 2015/11/23.
 */
public class HomeCarouselAdapter extends DynamicPagerAdapter {
    Context context;
    List<AdvertisementEntity> list;
    public HomeCarouselAdapter(Context context, List<AdvertisementEntity> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public View getView(ViewGroup container, final int position) {
        ImageView view = new ImageView(container.getContext());
        Picasso.with(context).load(list.get(position).getImg_url()).placeholder(new ColorDrawable(Color.parseColor("#f5f5f5"))).into(view);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString(BaseWebActivity.BUNDLE_KEY_URL,list.get(position).getLink());
                bundle.putString(BaseWebActivity.BUNDLE_KEY_TITLE,list.get(position).getTitle());
                Intent intent=new Intent();
                intent.putExtras(bundle);
                intent.setClass(context,BaseWebActivity.class);
                context.startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public int getCount() {
        return list.size();
    }
}