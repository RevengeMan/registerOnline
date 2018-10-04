package stu.byron.com.onlineregistrationproject.util;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import stu.byron.com.onlineregistrationproject.bean.Consumer;


/**
 * Created by Byron on 2018/9/9.
 */

public class HttpUtil {
    //GET
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
    //POST
    public static void sendOkHttpRequest(String address, HashMap<String ,String> map,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        RequestBody requestBody;
        JSONObject jsonObject=new JSONObject(map);
        requestBody= FormBody.create(MediaType.parse("application/json;charset=utf-8"),jsonObject.toString());
        Request request=new Request.Builder().url(address).post(requestBody).build();
        client.newCall(request).enqueue(callback);
    }

    //一个参数的请求
    public static void sendOkHttpRequest(String address,String key,String value,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        RequestBody requestBody=new FormBody.Builder()
                .add(key,value)
                .build();
        Request request=new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

    //两个参数的请求
    public static void sendOkHttpRequest(String address,String key1,String value1,String key2,String value2,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        RequestBody requestBody=new FormBody.Builder()
                .add(key1,value1)
                .add(key2,value2)
                .build();
        Request request=new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

    //两个参数的请求
    public static void sendOkHttpRequest(String address,String key1,String value1,String key2,String value2,String key3,String value3,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        RequestBody requestBody=new FormBody.Builder()
                .add(key1,value1)
                .add(key2,value2)
                .add(key3,value3)
                .build();
        Request request=new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
