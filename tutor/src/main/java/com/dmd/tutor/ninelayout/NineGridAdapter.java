package com.dmd.tutor.ninelayout;

import android.content.Context;
import android.view.View;


import com.google.gson.JsonArray;

import java.util.List;


/**
 * 适配器
 */
public abstract class NineGridAdapter {
    protected Context context;
    protected List list;
    protected int ruler;
    public NineGridAdapter(Context context, List list, int ruler) {
        this.context = context;
        this.list = list;
        this.ruler=ruler;
    }

    public abstract int getCount();

    public abstract String getUrl(int positopn);

    public abstract Object getItem(int position);

    public abstract long getItemId(int position);

    public abstract View getView(int i, View view);

    public abstract int getRuler();
}
