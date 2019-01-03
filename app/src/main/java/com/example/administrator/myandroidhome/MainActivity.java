package com.example.administrator.myandroidhome;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView tv_city;
    private ImageButton ib_place;
    private ImageButton ib_refresh;
    final private int city = 110;
    final private int about = 111;
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;
    private TextView tv_aqi, tv_quality, tv_primary_pollutant, tv_time;
    private ListView listview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_city = (TextView) findViewById(R.id.tv_city);
        ib_place = (ImageButton) findViewById(R.id.ib_place);
        ib_refresh = (ImageButton) findViewById(R.id.ib_refresh);
        tv_aqi = (TextView) findViewById(R.id.tv_aqi);
        tv_quality = (TextView) findViewById(R.id.tv_quality);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_primary_pollutant = (TextView) findViewById(R.id.tv_primary_pollutant);
        listview = (ListView) findViewById(R.id.listview);
        final SharedPreferences sp = MainActivity.this.getSharedPreferences("citypin", MODE_PRIVATE);
        List<Air> list = getObjectFromJson(getAirJson("http://www.pm25.in/api/querys/only_aqi.json?city="+sp.getString("citypinyin","changshu")+"&token=5j1znBVAsnSf5xQyNQyq"));
        loadData(list);
        ib_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               List<Air> list = getObjectFromJson(getAirJson("http://www.pm25.in/api/querys/only_aqi.json?city="+sp.getString("citypinyin","changshu")+"&token=5j1znBVAsnSf5xQyNQyq"));
                loadData(list);

            }
        });
        ib_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChooseCityActivity.class);
                startActivity(intent);
            }
        });
    }

    public void loadData(List<Air> list) {
        String time = list.get(list.size() - 1).getTime_point();
        String year = time.substring(0, 10);
        String z = time.substring(11, 19);
        tv_time.setText("监测时间:" + year + "  " + z);
        ListViewAdapter adapter = new ListViewAdapter(list, MainActivity.this);
        listview.setAdapter(adapter);
        tv_city.setText(list.get(list.size() - 1).getArea());
        tv_aqi.setText(list.get(list.size() - 1).getAqi());
        tv_quality.setText("空气质量:" + list.get(list.size() - 1).getQuality());
        tv_primary_pollutant.setText("首要污染物:" + list.get(list.size() - 1).getPrimary_pollutant());
    }

    public String getAirJson(String path) {
        String json = null;
        try {
            json = HttpUtil.getResult(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    public List<Air> getObjectFromJson(String json) {
        Air air = null;
        List<Air> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                air = new Air();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                air.setAqi(jsonObject.getString("aqi"));
                air.setArea(jsonObject.getString("area"));
                air.setPosition_name(jsonObject.getString("position_name"));
                air.setPrimary_pollutant(jsonObject.getString("primary_pollutant"));
                air.setQuality(jsonObject.getString("quality"));
                air.setStation_code(jsonObject.getString("station_code"));
                air.setTime_point(jsonObject.getString("time_point"));
                list.add(air);
                air = null;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, city, 1, "city");
        menu.add(1, about, 2, "about");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case city:
                Intent intent = new Intent(MainActivity.this, ChooseCityActivity.class);
                startActivity(intent);
                break;
            case about:
                builder = new AlertDialog.Builder(MainActivity.this);
                alert = builder
                        .setTitle("学生信息：")
                        .setMessage("zb1017107-李利民")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                alert.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
