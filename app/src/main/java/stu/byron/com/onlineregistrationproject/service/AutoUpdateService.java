package stu.byron.com.onlineregistrationproject.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import stu.byron.com.onlineregistrationproject.bean.Hospital;
import stu.byron.com.onlineregistrationproject.util.Constant;
import stu.byron.com.onlineregistrationproject.util.HttpUtil;
import stu.byron.com.onlineregistrationproject.util.ParseData;

public class AutoUpdateService extends Service {
    public AutoUpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateData();
        AlarmManager manager= (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour=1 * 60 * 60 *1000;//这是6小时的毫秒数
        long triggerAtTime= SystemClock.elapsedRealtime()+anHour;
        Intent i=new Intent(this,AutoUpdateService.class);
        PendingIntent pi=PendingIntent.getActivity(this,0,i,0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        return super.onStartCommand(intent,flags,startId);
    }

    //更新数据
    private void updateData(){
        HttpUtil.sendOkHttpRequest(Constant.BASE_PATH + Constant.HOSPITAL_DATA, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("MainFragment", "Error " + e.getCause());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Log.d("MainFragment", "onResponse: " + responseText.toString());

                ParseData.handleHospitalResponse(responseText);
            }
        });
    }
}
