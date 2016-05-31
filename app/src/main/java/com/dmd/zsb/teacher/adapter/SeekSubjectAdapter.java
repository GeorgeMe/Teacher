package com.dmd.zsb.teacher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dmd.zsb.teacher.R;
import com.dmd.zsb.protocol.table.SubjectsBean;

import java.util.List;

/**
 * Created by Administrator on 2016/1/6.
 */
public class SeekSubjectAdapter extends BaseAdapter {
    private List<SubjectsBean> list;
    private Context context;

    public SeekSubjectAdapter(List<SubjectsBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public SubjectsBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(R.layout.seek_menu_class_item,null);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.name.setText(list.get(position).sub_name);
        return convertView;
    }

    class Holder {
        TextView name;
    }
}
