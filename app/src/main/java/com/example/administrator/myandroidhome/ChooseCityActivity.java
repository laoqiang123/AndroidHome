package com.example.administrator.myandroidhome;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/1/2 0002.
 */
public class ChooseCityActivity extends AppCompatActivity {
    private ListView choosecitylistview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosecity_layout);
        final List<CityInfo> list = loadCity();
        choosecitylistview = (ListView) findViewById(R.id.choosecitylistview);
        ChooseAdapter adapter = new ChooseAdapter(list,ChooseCityActivity.this);
       final  SharedPreferences sp  = ChooseCityActivity.this.getSharedPreferences("citypin",MODE_PRIVATE);
       choosecitylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = null;
                AlertDialog alert = null;
                builder = new AlertDialog.Builder(ChooseCityActivity.this);
                alert = builder
                        .setTitle("您选择的城市：")
                        .setMessage(list.get(position).getName())
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences.Editor ed =  sp.edit();
                                ed.putString("citypinyin",list.get(position).getPinyin());
                                ed.commit();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                alert.show();

            }
        });
        choosecitylistview.setAdapter(adapter);

    }
    public List<CityInfo> loadCity(){
        List<CityInfo> list = new ArrayList<>();
        InputStream is = null;
        BufferedReader br = null;
        CityInfo ci = null;
        String content = null;
        try {
            is = getAssets().open("city_pinyin.txt");
            br = new BufferedReader(new InputStreamReader(is));
            while((content = br.readLine())!=null){
                ci = new CityInfo();
                String[] s = content.split(",");
                ci.setPinyin(s[0]);
                Log.e("log", s[0] + "    " + s[1] + "jjj");
                ci.setName(s[1]);
                list.add(ci);
                ci = null;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                br.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
