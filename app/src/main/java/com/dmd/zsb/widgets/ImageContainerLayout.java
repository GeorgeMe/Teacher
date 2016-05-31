package com.dmd.zsb.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dmd.tutor.utils.DensityUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

//import com.nostra13.universalimageloader.core.ImageLoader;

public class ImageContainerLayout extends LinearLayout
{

    public ImageContainerLayout(Context context)
    {
        super(context);
        mContext = null;
        mImageUrlList = null;
        init(context);
    }

    public ImageContainerLayout(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        mContext = null;
        mImageUrlList = null;
        init(context);
    }

    private void init(Context context)
    {
        mContext = context;
        mImageUrlList = new ArrayList();
        setOrientation(HORIZONTAL);
    }

    public void refreshContent(List list)
    {
        mImageUrlList.addAll(list);
        if(mImageUrlList != null && !mImageUrlList.isEmpty())
        {
            int i = mImageUrlList.size();
            for(int j = 0; j < i; j++)
            {
                ImageView imageview = new ImageView(mContext);
                imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
                LayoutParams layoutparams = new LayoutParams(0, (int)TypedValue.applyDimension(0, getResources().getDimensionPixelSize(9), getResources().getDisplayMetrics()), 1F);
                if(j != i + -1)
                    layoutparams.rightMargin = DensityUtils.dip2px(mContext, 5F);
                addView(imageview, layoutparams);
                Picasso.with(mContext).load((String)mImageUrlList.get(j)).into(imageview);
                //ImageLoader.getInstance().displayImage((String)mImageUrlList.get(j), imageview);
            }

        }
    }

    private Context mContext;
    private List mImageUrlList;
}
