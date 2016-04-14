package com.dmd.zsb.teacher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dmd.zsb.teacher.R;

public class SeekSortAdapter extends BaseAdapter {
	private String[] strings;
	private Context context;

	public SeekSortAdapter(Context context, String[] strings) {
		this.context = context;
		this.strings = strings;
	}

	@Override
	public int getCount() {
		return strings.length;
	}

	@Override
	public String getItem(int position) {
		return strings[position];
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

		holder.name.setText(strings[position]);
		return convertView;
	}

	class Holder {
		TextView name;
	}

}
