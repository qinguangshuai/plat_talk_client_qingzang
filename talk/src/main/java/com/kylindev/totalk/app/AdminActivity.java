package com.kylindev.totalk.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kylindev.totalk.R;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {


    SharedPreferences sp;
    Button bt_setadmin,bt_setchannal,bt_test,bt_channal1,bt_channal4,bt_channal0;
    private String account = "";
    private String password = "";
    EditText getAccount,getPassword;
    private String channal = "";
    Context context;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminpage);
        context=this;
        sp = getSharedPreferences("AdminData", Context.MODE_PRIVATE);

        bt_setadmin = findViewById(R.id.btn_setadmin);
        bt_setchannal = findViewById(R.id.btn_setchannal);
        bt_test = findViewById(R.id.btn_testt);
        bt_setadmin.setOnClickListener(this);
        bt_setchannal.setOnClickListener(this);
        bt_test.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_setadmin) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
            builder1.setIcon(R.drawable.ic_launcher_foreground);
            builder1.setTitle("请输入新的账号密码");
            //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
                /*ViewPager parent = (ViewPager) view.getParent();
                parent.removeAllViews();*/
            View view2 = LayoutInflater.from(context).inflate(R.layout.pop_up_dialog, null);
            //    设置我们自己定义的布局文件作为弹出框的Content
            builder1.setView(view2);

            getAccount = (EditText) view2.findViewById(R.id.et_account);
            getPassword = (EditText) view2.findViewById(R.id.et_password);
                /*if (!sp.getString("account", "").isEmpty()) {
                    account = sp.getString("account", "");
                    password = sp.getString("password", "");
                    getAccount.setText(account);
                    getPassword.setText(password);
                }*/

            builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    account = getAccount.getText().toString().trim().toLowerCase();
                    password = getPassword.getText().toString().trim().toLowerCase();
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("account", account);
                    editor.putString("password", password);
                    editor.commit();
                }
            });
            builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder1.show();
        }else if (view.getId() == R.id.btn_setchannal){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setIcon(R.drawable.ic_launcher_foreground);
            builder.setTitle("请选择信道");
            final String[] select = {"数字信道(1信道414.050)", "模拟信道(4信道457.700)", "其他信道"};

            builder.setSingleChoiceItems(select, Integer.parseInt(sp.getString("channal","")), new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    channal = select[which];
                    switch (channal){
                        case "数字信道(1信道414.050)":
                            channal ="0";
                            break;
                        case "模拟信道(4信道457.700)":
                            channal ="1";
                            break;
                        case "其他信道":
                            channal ="2";
                            break;
                    }
                }
            });
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("channal", channal);
                    editor.commit();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {

                }
            });
            builder.show();


        }else if (view.getId() == R.id.btn_testt){

        }

    }
}
