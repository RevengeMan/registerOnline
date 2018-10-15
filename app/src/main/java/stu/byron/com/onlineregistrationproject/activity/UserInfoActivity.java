package stu.byron.com.onlineregistrationproject.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import stu.byron.com.onlineregistrationproject.R;
import stu.byron.com.onlineregistrationproject.bean.Consumer;
import stu.byron.com.onlineregistrationproject.db.SharedPreferencesUtil;
import stu.byron.com.onlineregistrationproject.util.Constant;
import stu.byron.com.onlineregistrationproject.util.HttpUtil;
import stu.byron.com.onlineregistrationproject.util.ParseData;
import stu.byron.com.onlineregistrationproject.util.PostMap;
import stu.byron.com.onlineregistrationproject.util.TimeUtil;
import stu.byron.com.onlineregistrationproject.view.CircleImageView;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener{

    private RelativeLayout rl_title_bar,rl_sex;
    private TextView tv_back,tv_main_title,tv_save;

    private EditText  et_real_name,et_id_card;
    private TextView tv_sex,tv_phone,tv_nickName;
    private String spUserName;
    private String realName,idCard,sexString;

    //private  RelativeLayout rl_head;
    public static final int TAKE_PHOTO=1;
    public static final int CHOOSE_PHOTO=2;
    private View inflate;
    private TextView choosePhoto,takePhoto;
    private Dialog dialog;
    private Uri imageUri;
    private CircleImageView iv_head_icon;

    private SharedPreferencesUtil sharedPreferencesUtil=SharedPreferencesUtil.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        spUserName=sharedPreferencesUtil.getInfo("username");
        initView();
        initData();
        setListener();
    }

    private void initView(){
        rl_title_bar=findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        tv_main_title=findViewById(R.id.tv_main_title);
        tv_main_title.setText("个人信息");
        tv_save=findViewById(R.id.tv_save);
        tv_save.setVisibility(View.VISIBLE);
        tv_back=findViewById(R.id.tv_back);

        tv_nickName=findViewById(R.id.et_nickName);
        et_real_name=findViewById(R.id.et_real_name);
        tv_sex=findViewById(R.id.tv_sex);
        tv_phone=findViewById(R.id.tv_mobile);
        rl_sex=findViewById(R.id.rl_sex);
        et_id_card=findViewById(R.id.id_card);
        //rl_head=findViewById(R.id.rl_head);

        iv_head_icon=findViewById(R.id.iv_head_icon);
    }

    private void initData(){
        Consumer consumer=null;
        List<Consumer> consumerList= LitePal.where("cm_nickname=?",spUserName).find(Consumer.class);
        consumer=consumerList.get(0);
        setValue(consumer);
    }

    private void setValue(Consumer consumer){
        tv_nickName.setText(consumer.getNickname());
        et_real_name.setText(consumer.getCm_realname());
        et_id_card.setText(consumer.getCm_idnumber());
        tv_sex.setText(consumer.getSex());
        tv_phone.setText(consumer.getCm_phone());
        Log.e("UserInfo",String.valueOf(consumer.getCm_image()) );
        if (consumer.getCm_image()!=null&&consumer.getCm_image().length>0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(consumer.getCm_image(), 0, consumer.getCm_image().length);
            iv_head_icon.setImageBitmap(bitmap);
        }
    }

    private void setListener(){
        tv_back.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        rl_sex.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                Intent intent1=new Intent();
                intent1.putExtra("tv_back","tv_back");
                setResult(RESULT_OK,intent1);
                UserInfoActivity.this.finish();
                break;
            case R.id.rl_sex:
                String sex=tv_sex.getText().toString();
                sexDialog(sex);
                break;
            case R.id.tv_save:
                getEditString();
                Consumer consumer=new Consumer();
                consumer.setCm_realname(realName);
                consumer.setCm_idnumber(idCard);
                consumer.setSex(sexString);
                consumer.setAdd_time(TimeUtil.getNowTime());
                consumer.updateAll("cm_nickname=?",spUserName);
                List<Consumer> litepalConsumer= LitePal.where("cm_nickname=?",spUserName).find(Consumer.class);
                HashMap<String,String> map=PostMap.ConsumerPacking(litepalConsumer.get(0));
                HttpUtil.sendOkHttpRequest(Constant.BASE_PATH + Constant.UPDATE, map, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseText=response.body().string();
                        if (responseText.equals("success")) {
                            Log.d("UserInfoActivity", "onResponse: " + responseText.toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //Constant.showDialog(UserInfoActivity.this,"修改成功");
                                    UserInfoActivity.this.finish();
                                }
                            });
                        }
                    }

                });
                this.finish();
                break;
            case R.id.takePhoto:
                //设置头像
                //Toast.makeText(UserInfoActivity.this,"点击拍照",Toast.LENGTH_SHORT).show();
                //创建File对象，用于存储拍照后的图片
                File outputImage=new File(getExternalCacheDir(),"output_image.jpg");
                try{
                    if (outputImage.exists()){
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT>=24){
                    imageUri= FileProvider.getUriForFile(UserInfoActivity.this,"stu.byron.com.onlineregistrationproject.fileProvider",outputImage);
                }else {
                    imageUri=Uri.fromFile(outputImage);
                }
                //启动相机程序
                Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);
                break;
            case R.id.choosePhoto:
                //Toast.makeText(UserInfoActivity.this,"点击了从相册选择",Toast.LENGTH_SHORT).show();
                if (ContextCompat.checkSelfPermission(UserInfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
                        PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(UserInfoActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else {
                    openAlbum();
                }
                break;
            default:
                break;
        }
    }

    private void openAlbum(){
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else {
                    Toast.makeText(this,"You denied the permission",Toast.LENGTH_SHORT).show();
                }
                break;
                default:
        }
    }

    public void show(View view){
        dialog = new Dialog(this,R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(this).inflate(R.layout.head_choose_layout, null);
        //初始化控件
        choosePhoto = inflate.findViewById(R.id.choosePhoto);
        takePhoto = inflate.findViewById(R.id.takePhoto);
        choosePhoto.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity( Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;
        //设置Dialog距离底部的距离
        // 将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框}
    }





    private void getEditString(){
        realName=et_real_name.getText().toString().trim();
        idCard=et_id_card.getText().toString().trim();
        sexString=tv_sex.getText().toString().trim();
    }

    private void sexDialog(String sex){
        int sexFlag=0;
        if ("男".equals(sex)){
            sexFlag=0;
        }else if ("女".equals(sex)){
            sexFlag=1;
        }
        final  String items[]={"男","女"};
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("性别");
        builder.setSingleChoiceItems(items, sexFlag, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(UserInfoActivity.this,items[which],Toast.LENGTH_SHORT).show();
                setSex(items[which]);
            }
        });
        builder.create().show();
    }
    private void setSex(String sex){
        tv_sex.setText(sex);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK){
                    try {
                        //将拍摄的照片显示出来
                        Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        iv_head_icon.setImageBitmap(bitmap);
                        dialog.dismiss();
                        /*ByteBuffer buffer=ByteBuffer.allocate(bitmap.getByteCount());
                        byte[] img=buffer.array();*/
                        ByteArrayOutputStream baos=new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
                        byte[] img=baos.toByteArray();
                       /* Log.e("insertImg", img.toString() );
                        //String imgStr=String.valueOf(img);
                        String imgS= Base64.encodeToString(img,Base64.DEFAULT);
                        Log.e("insertImg", imgS);*/
                        Consumer consumer=new Consumer();
                        consumer=LitePal.findAll(Consumer.class).get(0);
                        String cm_id=String.valueOf(consumer.getCm_id());
                        consumer.setCm_image(img);
                        consumer.updateAll("cm_id=?",cm_id);
                        HashMap<String,String> map=new HashMap<>();
                        map=PostMap.ConsumerPacking(consumer);
                        HttpUtil.sendOkHttpRequest(Constant.BASE_PATH+Constant.CHANGE_PICTURE, map, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {

                            }
                        });

                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                Log.e("UserInfoActivity", "onActivityResult: ");
                if (resultCode==RESULT_OK){
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT>=19){
                        //4.4及以上系统使用这个方法处理图片
                        Log.e("UserInfoActivity", "onActivityResult: "+"4.4+");
                        handleImageOnKitKat(data);
                    }else {
                        //4.4以下系统使用这个方法处理图片
                        Log.e("UserInfoActivity", "onActivityResult: "+"4.4-");
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
                default:
                    break;
        }
    }
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data){
        Log.e("UserInfoActivity", "handleImageOnKitKat: " );
        String imagePath=null;
        Uri uri=data.getData();
        if (DocumentsContract.isDocumentUri(this,uri)){
            //如果是document类型的Uri，则通过document id处理
            String docId=DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id=docId.split(":")[1]; //解析出数字格式的id
                String selection= MediaStore.Images.Media._ID+"="+id;
                imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri= ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath=getImagePath(contentUri,null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            //如果是content类型的Uri，则使用普通方式处理
            imagePath=getImagePath(uri,null);
        }else if ("file".equalsIgnoreCase(uri.getScheme())){
            //如果是file类型的Uri，则使用普通方式处理
            imagePath=uri.getPath();
        }
        displayImage(imagePath); //根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data){
        Log.e("UserInfoActivity", "handleImageBeforeKitKat: ");
        Uri uri=data.getData();
        String imagePath=getImagePath(uri,null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri,String selection){
        String path=null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor=getContentResolver().query(uri,null,selection,null,null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath){
        Log.e("UserInfoActivity", "displayImage: ");
        if (imagePath!=null){
            dialog.dismiss();
            Bitmap bitmap=BitmapFactory.decodeFile(imagePath);
            iv_head_icon.setImageBitmap(bitmap);
                        /*ByteBuffer buffer=ByteBuffer.allocate(bitmap.getByteCount());
                        byte[] img=buffer.array();*/
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
            byte[] img=baos.toByteArray();
            Log.e("Image", "displayImage: "+img.toString());
            Consumer consumer=new Consumer();
            consumer=LitePal.findAll(Consumer.class).get(0);
            String cm_id=String.valueOf(consumer.getCm_id());
            consumer.setCm_image(img);
            consumer.updateAll("cm_id=?",cm_id);
            HashMap<String,String> map=new HashMap<>();
            map=PostMap.ConsumerPacking(consumer);
            HttpUtil.sendOkHttpRequest(Constant.BASE_PATH+Constant.CHANGE_PICTURE, map, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                }
            });
        }
    }
}
