package com.tencent.qcloud.tim.demo.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.kylindev.totalk.app.QGSActivity;
import com.kylindev.totalk.app.XNBMapActivity;
import com.kylindev.totalk.qgs.PointActivity;
import com.tencent.qcloud.tim.demo.R;
import com.tencent.qcloud.tim.demo.qingzang.SendActivity;
import com.tencent.qcloud.tim.demo.qingzang.XiNingActivity;

import java.util.ArrayList;
import java.util.List;

public class LogInPage extends Activity {
    private List<String> list1 = new ArrayList<String>();
    private List<String> list2 = new ArrayList<String>();
    private Spinner spinnertext1,spinnertext2;
    private ArrayAdapter<String> adapter1,adapter2;

    private String inputpassword,zhishi,caozuoyuan;
    private String TAG, setpassword,setnumber,setdiaohao;
    private int currpassword = 888;
    private EditText password,number,diaohao;
    private Button submit,reset;

    private SharedPreferences sp;
    private SoundPool soundPool;

    /*mode1制式
    * mode2操作员
    * mode3编号
    * mode4密码*/
    public void onCreate(Bundle savedlnstanceState) {
        super.onCreate(savedlnstanceState);
        setContentView(R.layout.login_page);
        //第一步：定义下拉列表内容
        list1.add("A制式");
        list1.add("B制式");
        list2.add("调车长");
        list2.add("制动员");
        spinnertext1 = (Spinner) findViewById(R.id.spinner1);
        spinnertext2 = (Spinner) findViewById(R.id.spinner2);
        password = findViewById(R.id.et_password);
        number = findViewById(R.id.et_number);
        submit = findViewById(R.id.btn_submit);
        reset = findViewById(R.id.btn_reset);
        diaohao = findViewById(R.id.et_number_diaohao);
        Button map = findViewById(R.id.btn_map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInPage.this, PointActivity.class));
            }
        });

        //第二步：为下拉列表定义一个适配器
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list1);
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list2);
        //第三步：设置下拉列表下拉时的菜单样式
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        spinnertext1.setAdapter(adapter1);
        spinnertext2.setAdapter(adapter2);
        sp = getSharedPreferences("yuShe", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();//获取编辑器
        zhishi = sp.getString("mode1","");
        caozuoyuan = sp.getString("mode2","");
        setnumber = sp.getString("mode3","");
        setpassword = sp.getString("mode4","");
        setdiaohao = sp.getString("mode5","");
        if(zhishi.matches("A制式") && caozuoyuan.matches("调车长")){
            spinnertext1.setSelection(0);
            spinnertext2.setSelection(0);
            diaohao.setText(setdiaohao);
        }
        else if(zhishi.matches("B制式") && caozuoyuan.matches("调车长")){
            spinnertext1.setSelection(1);
            spinnertext2.setSelection(0);
            diaohao.setText(setdiaohao);
        }
        else if(zhishi.matches("A制式") && caozuoyuan.matches("制动员")){
            spinnertext1.setSelection(0);
            spinnertext2.setSelection(1);
            diaohao.setText(setdiaohao);
            number.setText(setnumber);
        }
        else if(zhishi.matches("B制式") && caozuoyuan.matches("制动员")){
            spinnertext1.setSelection(1);
            spinnertext2.setSelection(1);
            diaohao.setText(setdiaohao);
            number.setText(setnumber);
        }
        password.setText(setpassword);
        //第五步：添加监听器，为下拉列表设置事件的响应

        spinnertext1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> argO, View argl, int arg2, long arg3) {
                // TODO Auto-generated method stub
                zhishi = adapter1.getItem(arg2);
                /* 将所选spinnertext的值带入myTextView中*/
                /* 将 spinnertext 显示^*/
                argO.setVisibility(View.VISIBLE);
            }
            public void onNothingSelected(AdapterView<?> argO) {
                // TODO Auto-generated method stub
                argO.setVisibility(View.VISIBLE);
            }
        });
        spinnertext2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> argO, View argl, int arg2, long arg3) {
                // TODO Auto-generated method stub
                caozuoyuan = adapter2.getItem(arg2);
                if(caozuoyuan.matches("制动员")){
                    number.setVisibility(View.VISIBLE);
                }
                else if (caozuoyuan.matches("调车长")){
                    number.setVisibility(View.INVISIBLE);
                }
                /* 将所选spinnertext的值带入myTextView中*/
                /* 将 spinnertext 显示^*/
                argO.setVisibility(View.VISIBLE);
            }
            public void onNothingSelected(AdapterView<?> argO) {
                // TODO Auto-generated method stub
                argO.setVisibility(View.VISIBLE);
            }
        });
        //将spinnertext添加到OnTouchListener对内容选项触屏事件处理
        spinnertext1.setOnTouchListener(new Spinner.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                // 将mySpinner隐藏
                v.setVisibility(View.VISIBLE);
                return false;
            }
        });
        spinnertext2.setOnTouchListener(new Spinner.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                // 将mySpinner隐藏
                v.setVisibility(View.VISIBLE);
                return false;
            }
        });
        //焦点改变事件处理
        spinnertext1.setOnFocusChangeListener(new Spinner.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                v.setVisibility(View.VISIBLE);
            }
        });
        spinnertext2.setOnFocusChangeListener(new Spinner.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                v.setVisibility(View.VISIBLE);
            }
        });

        if(password.getText().toString().trim().matches("888")){
            spinnertext1.setEnabled(false);
            spinnertext2.setEnabled(false);
            number.setFocusableInTouchMode(false);
            diaohao.setFocusableInTouchMode(false);
            password.setFocusableInTouchMode(false);
        }
        else if (!password.getText().toString().trim().matches("888") || password.getText() == null){
        }

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputpassword = password.getText().toString().trim();
                sp = getSharedPreferences("yuShe", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();//获取编辑器
                if(inputpassword == null){
                    Toast.makeText(LogInPage.this,"请输入密码",Toast.LENGTH_SHORT).show();
                }
                else if(!inputpassword.matches("888")){
                    Toast.makeText(LogInPage.this,"密码不正确",Toast.LENGTH_SHORT).show();
                }
                else if (inputpassword.matches("888")){
                    if(zhishi.matches("A制式") && caozuoyuan.matches("调车长")){
                        setdiaohao = diaohao.getText().toString();
                        if (setdiaohao.length() == 1){
                            setdiaohao = "0"+ setdiaohao;
                        }
                        editor.putString("mode1", zhishi);
                        editor.putString("mode2", caozuoyuan);
                        editor.putString("mode4", inputpassword);
                        editor.putString("mode5", setdiaohao);
                        editor.commit();//提交修改
                        /*Intent intent = new Intent(LogInPage.this, MainActivity.class);
                        startActivity(intent);*/
                    }
                    else if(zhishi.matches("B制式") && caozuoyuan.matches("调车长")){
                        setdiaohao = diaohao.getText().toString();
                        if (setdiaohao.length() == 1){
                            setdiaohao = "0"+ setdiaohao;
                        }
                        editor.putString("mode1", zhishi);
                        editor.putString("mode2", caozuoyuan);
                        editor.putString("mode4", inputpassword);
                        editor.putString("mode5", setdiaohao);
                        editor.commit();//提交修改
                        Intent intent = new Intent(LogInPage.this, SendActivity.class);
                        startActivity(intent);
                    }
                    else if(zhishi.matches("A制式") && caozuoyuan.matches("制动员")){
                        setdiaohao = diaohao.getText().toString();
                        setnumber = number.getText().toString();
                        if (setnumber.equals("20")){
                            Toast.makeText(LogInPage.this,"调车长为20号", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (setdiaohao.length() == 1 ){
                            setdiaohao = "0"+ setdiaohao;
                        }
                        if (setnumber.length() == 1){
                            setnumber = "0"+ setnumber;
                        }

                        editor.putString("mode1", zhishi);
                        editor.putString("mode2", caozuoyuan);
                        editor.putString("mode3", setnumber);
                        editor.putString("mode4", inputpassword);
                        editor.putString("mode5", setdiaohao);
                        editor.commit();//提交修改
                        /*Intent intent = new Intent(LogInPage.this, MainActivity.class);
                        startActivity(intent);*/
                    }
                    else if(zhishi.matches("B制式") && caozuoyuan.matches("制动员")){

                        setdiaohao = diaohao.getText().toString();
                        setnumber = number.getText().toString();
                        if (setnumber.equals("20")){
                            Toast.makeText(LogInPage.this,"调车长为20号", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (setdiaohao.length() == 1){
                            setdiaohao = "0"+ setdiaohao;
                        }
                        if (setnumber.length() == 1){
                            setnumber = "0"+ setnumber;
                        }
                        editor.putString("mode1", zhishi);
                        editor.putString("mode2", caozuoyuan);
                        editor.putString("mode3", setnumber);
                        editor.putString("mode4", inputpassword);
                        editor.putString("mode5", setdiaohao);
                        editor.commit();//提交修改
                        Intent intent = new Intent(LogInPage.this, SendActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });



    }

    private void showDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("是否确认重新设定");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                spinnertext1.setEnabled(true);
                spinnertext2.setEnabled(true);
                number.setFocusableInTouchMode(true);
                diaohao.setFocusableInTouchMode(true);
                password.setFocusableInTouchMode(true);
                password.setText("");
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();

    }
}