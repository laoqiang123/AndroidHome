package com.example.administrator.myandroidhome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2019/1/1 0001.
 */
public class ListViewAdapter extends BaseAdapter {
    private List<Air> data;
    private Context context;


    @Override
    public int getCount() {
        return data.size()-1;
    }



    @Override
    public Object getItem(int position) {
        return null;
    }

    public ListViewAdapter(List<Air> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.listview_item, parent, false);
        TextView tv_position_name = (TextView) convertView.findViewById(R.id.tv_position_name);
        TextView tv_aqi = (TextView) convertView.findViewById(R.id.tv_aqi);
        TextView tv_primary_pollutant = (TextView) convertView.findViewById(R.id.tv_primary_pollutant);
        tv_position_name.setText(data.get(position).getPosition_name());
        tv_aqi.setText(data.get(position).getAqi());
        tv_primary_pollutant.setText(data.get(position).getPrimary_pollutant());
        return convertView;
    }
}
