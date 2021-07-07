package com.tencent.qcloud.tim.demo.login;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.testposition.GpsUtils;
import com.kylindev.totalk.app.LoginActivity;
import com.kylindev.totalk.bjxt.SpUtil;
import com.kylindev.totalk.qgs.PointActivity;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMGroupManager;
import com.tencent.imsdk.TIMManager;
import com.tencent.qcloud.tim.demo.R;
import com.tencent.qcloud.tim.demo.signature.GenerateTestUserSig;
import com.tencent.qcloud.tim.demo.utils.Constants;
import com.tencent.qcloud.tim.demo.utils.DemoLog;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import static com.tencent.qcloud.tim.demo.qingzang.SendActivity.diaohao;

/**
 * <p>
 * Demo的登录Activity
 * 用户名可以是任意非空字符，但是前提需要按照下面文档修改代码里的 SDKAPPID 与 PRIVATEKEY
 * https://github.com/tencentyun/TIMSDK/tree/master/Android
 * <p>
 */

public class LoginForDevActivity extends Activity {

    private static final String TAG = LoginForDevActivity.class.getSimpleName();
    private static final int REQ_PERMISSION_CODE = 0x100;
    private Button mLoginView;
    private EditText mUserAccount;
    private String account;
    private SpUtil mSpUtil;

    //权限检查
    public static boolean checkPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(TUIKit.getAppContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(TUIKit.getAppContext(), Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(TUIKit.getAppContext(), Manifest.permission.RECORD_AUDIO)) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(TUIKit.getAppContext(), Manifest.permission.READ_PHONE_STATE)) {
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(TUIKit.getAppContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (permissions.size() != 0) {
                String[] permissionsArray = permissions.toArray(new String[1]);
                ActivityCompat.requestPermissions(activity,
                        permissionsArray,
                        REQ_PERMISSION_CODE);
                return false;
            }
        }

        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //GpsUtils.initLocation(this);
        setContentView(R.layout.login_for_dev_layout);
        final Intent intent=new Intent(this, LoginActivity.class);

        mSpUtil = new SpUtil(getApplication(),"controluncaughtException");
        /*Button bt_test=findViewById(R.id.test_pos);
        bt_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
                finish();
//                finish();
            }
        });*/


        mLoginView = findViewById(R.id.login_btn);
        // 用户名可以是任意非空字符，但是前提需要按照下面文档修改代码里的 SDKAPPID 与 PRIVATEKEY
        // https://github.com/tencentyun/TIMSDK/tree/master/Android
        mUserAccount = findViewById(R.id.login_user);
        mUserAccount.setText("1001023");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        checkPermission(this);
        mLoginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取userSig函数
                String userSig = GenerateTestUserSig.genTestUserSig(mUserAccount.getText().toString());
                if (mUserAccount.getText().toString().matches("1001030")){
                    ToastUtil.toastLongMessage("不能用机控器账号，老实的按规矩来");
                    return;
                }
                TUIKit.login(mUserAccount.getText().toString(), userSig, new IUIKitCallBack() {
                    @Override
                    public void onError(String module, final int code, final String desc) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                ToastUtil.toastLongMessage("登录失败, errCode = " + code + ", errInfo = " + desc);
                            }
                        });
                        DemoLog.i(TAG, "imLogin errorCode = " + code + ", errorInfo = " + desc);
                    }

                    @Override
                    public void onSuccess(Object data) {
                        SharedPreferences shareInfo = getSharedPreferences(Constants.USERINFO, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = shareInfo.edit();
                        editor.putBoolean(Constants.AUTO_LOGIN, true);
                        editor.commit();

                        String name = mSpUtil.getName();
                        if (name.equals("1")) {
                            Intent intent = new Intent(LoginForDevActivity.this, PointActivity.class);
                            //Intent intent = new Intent(LoginForDevActivity.this, PointActivity.class);
//                        MainApp.setLevel(mUserAccount.getText().toString().substring(0,1));
                            String account=mUserAccount.getText().toString();
                            intent.putExtra("account",account);
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(LoginForDevActivity.this, LoginActivity.class);
                            //Intent intent = new Intent(LoginForDevActivity.this, PointActivity.class);
//                        MainApp.setLevel(mUserAccount.getText().toString().substring(0,1));
                            String account=mUserAccount.getText().toString();
                            intent.putExtra("account",account);
                            startActivity(intent);
                        }


                        finish();
                    }
                });
            }
        });

        mLoginView.performClick();
        //auto();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        if (newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE) {
//            setContentView(R.layout.imageswitch);
            //横屏
        } else {
//            setContentView(R.layout.editcontact);//竖屏
        }

        super.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return true;
    }

    /**
     * 系统请求权限回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQ_PERMISSION_CODE:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    ToastUtil.toastLongMessage("未全部授权，部分功能可能无法使用！");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //自动登录
    public void auto() {
        // 获取userSig函数
        String user = mUserAccount.getText().toString();
        String userSig = GenerateTestUserSig.genTestUserSig(user);
        if (mUserAccount.getText().toString().matches("1001030")){
            ToastUtil.toastLongMessage("不能用机控器账号，老实的按规矩来");
            return;
        }
        TUIKit.login(user, userSig, new IUIKitCallBack() {
            @Override
            public void onError(String module, final int code, final String desc) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        ToastUtil.toastLongMessage("登录失败, errCode = " + code + ", errInfo = " + desc);
                        Log.e("登录失败","登录失败, errCode = " + code + ", errInfo = " + desc);
                    }
                });
                DemoLog.i(TAG, "imLogin errorCode = " + code + ", errorInfo = " + desc);
            }

            @Override
            public void onSuccess(Object data) {
                SharedPreferences shareInfo = getSharedPreferences(Constants.USERINFO, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = shareInfo.edit();
                editor.putBoolean(Constants.AUTO_LOGIN, true);
                editor.commit();
                TIMConversation conversation = TIMManager.getInstance().getConversation(
                        TIMConversationType.Group,   //会话类型：
                        "01");//会话帐号//群ID
                JoinGroup("01");
                //分三级  调度员d  领导l  普通用户cLoginForDevActivity.this, LoginActivity.class
                Intent intent = new Intent(LoginForDevActivity.this, LoginActivity.class);
//                        MainApp.setLevel(mUserAccount.getText().toString().substring(0,1));
                account = mUserAccount.getText().toString();
                intent.putExtra("account", account);
                startActivity(intent);
                finish();
            }
        });
    }

    //进群
    private void JoinGroup(String id) {
        TIMGroupManager.getInstance().applyJoinGroup(id, "", new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
            }

            @Override
            public void onSuccess() {
            }
        });
    }
}
