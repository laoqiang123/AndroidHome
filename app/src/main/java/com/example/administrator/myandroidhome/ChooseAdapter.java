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
public class ChooseAdapter extends BaseAdapter {
    private List<CityInfo> data;
    private Context context;


    @Override
    public int getCount() {
        return data.size();
    }



    @Override
    public Object getItem(int position) {
        return null;
    }

    public ChooseAdapter(List<CityInfo> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.choosecity_item, parent, false);
        TextView city = (TextView) convertView.findViewById(R.id.tv_city);
        city.setText(data.get(position).getName());
        return convertView;
    }
}
