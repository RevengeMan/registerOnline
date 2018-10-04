package stu.byron.com.onlineregistrationproject.db;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Byron on 2018/9/16.
 */

public class SharedPreferencesUtil {
    public static SharedPreferencesUtil sharedPreferencesUtil;
    private static SharedPreferences sharedPreferences=null;
    public static SharedPreferencesUtil getInstance(Context context){
        if (sharedPreferencesUtil==null){
            sharedPreferencesUtil=new SharedPreferencesUtil();
            //建立一个数据库
            sharedPreferences=context.getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        }
        return sharedPreferencesUtil;
    }

    //存入一个字符串
    public void insertData(String key,String value){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    //存入一个boolean
    public void insertData(String key,boolean value){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

    //获取一个字符串
    public String getInfo(String key){
        return sharedPreferences.getString(key,"");
    }

    //获取一个boolean
    public Boolean getResult(String key){
        return sharedPreferences.getBoolean(key,false);
    }
}
