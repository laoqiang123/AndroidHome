package com.example.administrator.myandroidhome;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 2018/12/31 0031.
 */
public class HttpUtil {
    public static String getResult(final String path) throws Exception {
        MyThread t =  new MyThread(path);
        ExecutorService executor = Executors.newFixedThreadPool(10);
        Future task = executor.submit(t);
        Log.e("log",task.get().toString());
        return task.get().toString();


    }

   static class MyThread implements  Callable<String>{
        private String path;

        public MyThread(String path) {
            this.path = path;
        }

        @Override
        public String call() throws Exception {
            URL obj = null;
            try {
                obj = new URL(path);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection con = null;
            StringBuilder sb = new StringBuilder();

            try {
                con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Content-Type", "application/json");
                con.setReadTimeout(5000);
                con.setConnectTimeout(5000);
                int responseCode = con.getResponseCode();
                BufferedReader input = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String content = null;
                while((content = input.readLine())!=null){
                    sb.append(content);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }
    }
}
