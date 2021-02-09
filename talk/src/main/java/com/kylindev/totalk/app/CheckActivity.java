package com.kylindev.totalk.app;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kylindev.totalk.MainApp;
import com.kylindev.totalk.R;
import com.kylindev.totalk.net.ProgressListener;
import com.kylindev.totalk.net.Result;
import com.kylindev.totalk.net.RetrofitUtil;
import com.kylindev.totalk.net.ServerAPI;
import com.kylindev.totalk.net.UploadFileRequestBody;

import java.io.File;

import mac.yk.customdialog.CustomDialog;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CheckActivity extends Activity {
    public static final int CAMERA_OK = 1;
    ImageView bt_camera;
    Button bt_test, bt_admin;
    TextView tv_tips;
    int step = 1;
    Context context;
    ImageView pic1, pic2, pic3, pic4;
    int test = 0;

    SharedPreferences sp;
    private String account = "";
    private String password = "";
    EditText getAccount, getPassword;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        step = MainApp.getStep();
        test = MainApp.getTest();
        Log.e("wocao", "step:" + step);
        setContentView(R.layout.activity_check);

        sp = getSharedPreferences("AdminData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (sp.getString("account", "").isEmpty()) {
            setsave();
        } else if (sp.getString("channal", "").isEmpty()) {
            editor.putString("channal", "1");
            editor.commit();
        } else {
        }

        bt_camera = findViewById(R.id.bt_camera);
        bt_test = findViewById(R.id.bt_test);
        bt_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(CheckActivity.this, "wocao", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CheckActivity.this, ControlActivity.class);
                startActivity(intent);
            }
        });

        bt_admin = findViewById(R.id.bt_admin);
        bt_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(R.drawable.ic_launcher_foreground);
                builder.setTitle("请输入账号密码");

                View view2 = LayoutInflater.from(context).inflate(R.layout.pop_up_dialog, null);
                //    设置我们自己定义的布局文件作为弹出框的Content
                builder.setView(view2);

                getAccount = (EditText) view2.findViewById(R.id.et_account);
                getPassword = (EditText) view2.findViewById(R.id.et_password);


                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        account = getAccount.getText().toString().trim().toLowerCase();
                        password = getPassword.getText().toString().trim().toLowerCase();
                        if (account.matches(sp.getString("account", "")) && password.matches(sp.getString("password", ""))) {
                            Intent intent = new Intent(CheckActivity.this, AdminActivity.class);
                            startActivity(intent);
                        } else if (account.matches(sp.getString("adminaccount", "")) && password.matches(sp.getString("adminpassword", ""))) {
                            Intent intent = new Intent(CheckActivity.this, AdminActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(context, "账号密码错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
            }
        });



        if (Build.VERSION.SDK_INT > 22) {
            if (ContextCompat.checkSelfPermission(CheckActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                //先判断有没有权限 ，没有就在这里进行权限的申请
                ActivityCompat.requestPermissions(CheckActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, CAMERA_OK);
            } else {
                //说明已经获取到摄像头权限了
                Log.i("CustomRecordActivity", "已经获取了权限");
            }
        }
        tv_tips = findViewById(R.id.tv_tips);
//        setTv();
        bt_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!video) {
                    Intent intent = new Intent(CheckActivity.this, CameraActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent2 = new Intent(CheckActivity.this, VideoActivity.class);
                    startActivity(intent2);
                }
            }
        });

        Bitmap bitmap = CameraActivity.bitmap;
        if (bitmap != null) {
            bt_camera.setImageBitmap(bitmap);
        }
//        pic1=findViewById(R.id.pic1);
//        pic2=findViewById(R.id.pic2);
//        pic3=findViewById(R.id.pic3);
//        pic4=findViewById(R.id.pic4);
//        setBitMap();


        if (getIntent().getStringExtra("action") != null) {
            Log.e("wocao", "zuile");
            request();
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void jump() {
        test = 0;
        step++;
        if (step == 3) {
            video = true;
        } else {
            video = false;
        }
        if (step > 3) {
            step = 1;
        }
        MainApp.setStep(step);
        MainApp.setTest(0);
        setTv();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void reset(){
        step=1;
        MainApp.setStep(step);
        MainApp.setTest(0);
        setTv();
    }




    boolean video = false;

    //    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setTv() {
        Log.e("wocao","step"+step);
        switch (step){
            case 1:
//                "折角塞门是否关闭\n" +
//                        "列尾夹钩是否良好\n" +
//                        "列尾锁紧设置是否良好\n" +
//                        "风管连接是否良好";
                tv_tips.setText("请拍摄折角塞门");
                tv_tips.setTextColor(getResources().getColor(R.color.blue));
                break;
            case 2:
                tv_tips.setText("折角塞门已正常开启\n请拍摄风管连接");
                tv_tips.setTextColor(getResources().getColor(R.color.blue));
                break;
            case 3:
                tv_tips.setText("风管连接正常\n请拍摄视频");
                tv_tips.setTextColor(getResources().getColor(R.color.blue));
                bt_camera.setImageDrawable(getDrawable(R.drawable.wocao));
                video=true;
                break;
            case 4:
//                Intent intent=new Intent(CheckActivity.this,ControlActivity.class);
//                startActivity(intent);
                tv_tips.setText("请拍摄折角塞门");
                tv_tips.setTextColor(getResources().getColor(R.color.blue));
                MainApp.setStep(1);
                bt_camera.setImageDrawable(getDrawable(R.drawable.wocao));
                break;
        }
    }

    private void request() {
        onStartUpload();
        showProgress();
    }

    static CustomDialog progressDialog;

    private void showProgress() {
        progressDialog = CustomDialog.create(context, "识别中...", false, null);
        progressDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onResume() {
        super.onResume();
        if(video&&MainApp.getVideo_finish()==1){
            reset();
            MainApp.setVideo_finish(0);
            video=false;
        }
        setTv();

    }

    UploadFileRequestBody fileRequestBody;
    Subscription subscribe;

    public void onStartUpload() {
        Log.e("wocao", "zuile");
        File file = new File(
                "/sdcard/bjxt/" +
                        MainApp.getName()
        );
        Log.e("wocao", file.toString());
        fileRequestBody = new UploadFileRequestBody(file, new DefaultProgressListener(
                0, handler));
//        requestBodyMap.put("file\"; filename=\"/sdcard/Pictures\n"+ MainApp.getName(), fileRequestBody);
        ServerAPI serverAPI = RetrofitUtil.createService(ServerAPI.class);
        subscribe = serverAPI.uploadImage(fileRequestBody, "test.jpg", step)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result<String>>() {
                    @Override
                    public void onCompleted() {
                        progressDialog.dismiss();
                        Log.e("wocao", "upload start1");
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Toast.makeText();
                        progressDialog.dismiss();
                        Log.e("wocao", "liangle2" + e.getMessage());
                    }

                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onNext(Result<String> result) {
                        progressDialog.dismiss();
//                        Log.e("wocao","upload ok");
                        Log.e("wocao", result.toString());
//                        ServerProto.L.e(TAG, "上传成功");
//                        Attachment s = attachmentResult.getRetData();
//                        onCompletedUpload(s.getAid());
//                        progressDialog.dismiss();
                        if (result.getRetData().equals("0") || result.getRetData().equals("close")) {
                            step++;
                            MainApp.setStep(step);
                            setTv();
                            test = 0;
                        } else {
//                            bt_camera.setImageDrawable(getDrawable(R.drawable.wocao));
                            test++;
                            MainApp.setTest(test);
                            Log.e("wocao", "test" + test);
                            if (test != 3) {
                                tv_tips.setText("请重拍" +getStepName());
                                tv_tips.setTextColor(getResources().getColor(R.color.app_red));
                            } else {
                                Log.e("wocao", "jump");
                               showDialog();
                            }


                        }
                    }
                });
    }

    private String getStepName() {
        if(step==1){
            return "折角塞门";
        }else{
            return "风管连接";
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            //do something.
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }
//        public void checkPic(){
//        Log.e("wocao","check");
//            ServerAPI serverAPI = RetrofitUtil.createService(ServerAPI.class);
//            subscribe = serverAPI.checkPic()
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Subscriber<Result<String>>() {
//
//                        @Override
//                        public void onCompleted() {
//
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
////                        Toast.makeText();
////
////                            Log.e("wocao","liangle2"+e.getMessage());
//                            tv_tips.setText("请重拍"+tv_tips.getText().toString().substring(3));
//                        }
//
//                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//                        @Override
//                        public void onNext(Result<String> result) {
////                        ServerProto.L.e(TAG, "上传成功");
//                            Log.e("wocao",result.retData+"zuile");
////                        Attachment s = attachmentResult.getRetData();
////                        onCompletedUpload(s.getAid());
//                            progressDialog.dismiss();
//                            if (result.getRetData().equals("ok")) {
//                                step++;
//                                MainApp.setStep(step);
//                                setTv();
//                                bt_camera.setImageDrawable(getDrawable(R.drawable.wocao));
//                            }else {
//                                tv_tips.setText("请重拍"+tv_tips.getText().toString().substring(3));
//                            }
//                        }
//                    });
//        }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showDialog() {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(context);
        String s="";
        if(step==1){
            s="折角塞门";
        }else{
            s="风管";
        }
        normalDialog.setTitle("请确认"+s+"是否正常");
        normalDialog.setMessage("谨慎确认后，您可以点击跳过");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        jump();
                    }
                });
//            normalDialog.setNegativeButton("关闭",
//                    new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            //...To-do
//                        }
//                    });
        // 显示
        normalDialog.show();
    }



    class DefaultProgressListener implements ProgressListener {

        long completed;
        Handler handler;

        public DefaultProgressListener(long completed, Handler handler) {
            this.completed = completed;
            this.handler = handler;
        }

        @Override
        public void onProgress(long hasWrittenLen, long totalLen, boolean hasFinish) {

        }
    }

    private void setsave() {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("account", "admin");
        editor.putString("password", "999999");
        editor.putString("adminaccount", "useraccount");
        editor.putString("adminpassword", "123456");
        editor.putString("channal", "1");
        editor.commit();
    }

}

