package com.example.testposition;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mylibrary.db.DBPosition;
import com.example.mylibrary.db.MyLocation;

import java.util.ArrayList;

import mac.yk.customdialog.CustomDialog;

public class TestActivity extends Activity {

    TextView tv_discovered,tv_calibrated,tv_longitude,tv_latitude,bt_clean;
    Button bt_test,bt_search;
    static Context context;
    PositionAdapter adapter;
    ArrayList<MyLocation> myLocations=new ArrayList<>();
    ListView listView;
    TextView tv_distance,tv_distance2;
    Button bt_test_d;
    ArrayList<MyLocation> selectList=new ArrayList<>();
    EditText et_track;
    LinearLayout ll;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_zuile);
        initView();
//        GpsUtils.initLocation(this);
//        test();
//        wocao();

    }

    private void wocao() {
        ArrayList<MyLocation> list=DBPosition.getInstance(context).selectAll();
        for(int i=0;i<list.size();i++){
            Log.e("wocao",list.get(i).toString());
        }
    }

    private void insert(){
        MyLocation loc1=new MyLocation("a","9","114.44936211627454", "36.54074846957207");
        MyLocation loc2=new MyLocation("b","9","114.44948597752821", "36.54075745698564");
        MyLocation loc3=new MyLocation("c","9","114.44951547400346", "36.54082578143854");
        MyLocation loc4=new MyLocation("d","9","114.44958207744766", "36.540969086140564");
        MyLocation loc5=new MyLocation("e","9","114.44964424225253", "36.54111135230951");
        MyLocation loc6=new MyLocation("f","9","114.44970570259463", "36.541249137465854");
        MyLocation loc7=new MyLocation("g","9","114.4498003410243", "36.54146176613272");

        MyLocation loc8=new MyLocation("a","7","114.44940534353158", "36.54040578146643");
        MyLocation loc9=new MyLocation("b","7","114.44946955159303", "36.54045050712092");
        MyLocation loc10=new MyLocation("c","7","114.44955864755195","36.54065220519226");
        MyLocation loc11=new MyLocation("d","7","114.44966034750306", "36.540880602983464");
        MyLocation loc12=new MyLocation("e","7", "114.44974076736729", "36.54105943267199");
        MyLocation loc13=new MyLocation("f","7","114.44984403238563", "36.54129397033207");
        MyLocation loc14=new MyLocation("g","7","114.44992174395178", "36.54146878747644");
        MyLocation loc15=new MyLocation("h","7","114.44997784545598", "36.54159520666458");


        MyLocation loc16=new MyLocation("a","6","114.44970359268389","36.540856627802725");
        MyLocation loc17=new MyLocation("b","6","114.44975117375792", "36.540964488277844");
        MyLocation loc18=new MyLocation("c","6","114.44980478049959", "36.54108700039779");
        MyLocation loc19=new MyLocation("d","6","114.44987131237123", "36.541237601523825");
        MyLocation loc20=new MyLocation("e","6","114.44994901514627", "36.54141503766243");
        MyLocation loc21=new MyLocation("f","6","114.45004035660796", "36.541616160212016");
        MyLocation loc22=new MyLocation("g","6","114.45014140428566", "36.54184051180623");
        MyLocation loc23=new MyLocation("h","6","114.45019742888012", "36.54197717087358");

        DBPosition.getInstance(context).insert(loc1);
        DBPosition.getInstance(context).insert(loc2);
        DBPosition.getInstance(context).insert(loc3);
        DBPosition.getInstance(context).insert(loc4);
        DBPosition.getInstance(context).insert(loc5);
        DBPosition.getInstance(context).insert(loc6);
        DBPosition.getInstance(context).insert(loc7);
        DBPosition.getInstance(context).insert(loc8);
        DBPosition.getInstance(context).insert(loc9);
        DBPosition.getInstance(context).insert(loc10);
        DBPosition.getInstance(context).insert(loc11);
        DBPosition.getInstance(context).insert(loc12);
        DBPosition.getInstance(context).insert(loc13);
        DBPosition.getInstance(context).insert(loc14);
        DBPosition.getInstance(context).insert(loc15);
        DBPosition.getInstance(context).insert(loc16);
        DBPosition.getInstance(context).insert(loc17);
        DBPosition.getInstance(context).insert(loc18);
        DBPosition.getInstance(context).insert(loc19);
        DBPosition.getInstance(context).insert(loc20);
        DBPosition.getInstance(context).insert(loc21);
        DBPosition.getInstance(context).insert(loc22);
        DBPosition.getInstance(context).insert(loc23);
    }


    public static TestActivity getTestAct(){
        return (TestActivity) context;
    }

    private void initView() {
        ll=findViewById(R.id.ll);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hintKeyBoard();
            }
        });
        bt_clean=findViewById(R.id.bt_clean);
        bt_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    for(int i=0;i<selectList.size();i++){

                    DBPosition.getInstance(getApplicationContext()).delete(selectList.get(i).getPosition());
                        myLocations.remove(selectList.get(i));
                        selectList.remove(i);
                        adapter.notifyDataSetChanged();
                    }
            }
        });

        tv_discovered=findViewById(R.id.tv_discovered);
        tv_calibrated=findViewById(R.id.tv_calibrated);
        tv_longitude=findViewById(R.id.tv_longitude);
        tv_latitude=findViewById(R.id.tv_latitude);
        bt_search=findViewById(R.id.bt_search);
        tv_distance=findViewById(R.id.tv_distance);
        tv_distance2=findViewById(R.id.tv_distance2);
        tv_distance2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double a = ComputeUtil.GpsToDistance(
                            Double.valueOf(tv_longitude.getText().toString().substring(3)),
                            Double.valueOf(tv_latitude.getText().toString().substring(3)),
                            Double.valueOf(selectList.get(0).getLongitude()),
                            Double.valueOf(selectList.get(0).getLatitude()),
                            Double.valueOf(selectList.get(1).getLongitude()),
                            Double.valueOf(selectList.get(1).getLatitude()));

                    tv_distance.setText("距离：" + a);
                }catch (Exception e){

                }
            }
        });
//        tv_distance2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(TestActivity.this,"wocao",Toast.LENGTH_SHORT).show();
//            }
//        });

        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PositionActivity.class);
                startActivity(intent);
            }
        });
        bt_test=findViewById(R.id.bt_test);
        bt_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCusDialog();
            }
        });
        et_track=findViewById(R.id.et_track);
        listView=findViewById(R.id.lv);
        adapter=new PositionAdapter(this,myLocations,1);
        listView.setAdapter(adapter);
    }

    Button bt_ok;
    static EditText et_position;
    Dialog dialog;
    private void showCusDialog() {
        View view1= getLayoutInflater().inflate(R.layout.dialog_test,null,false);
        bt_ok=view1.findViewById(R.id.bt_ok);
        et_position=view1.findViewById(R.id.et_position);
        dialog=new Dialog(view1.getContext());
        dialog.setTitle("输入位置名称");
        dialog.setContentView(view1);
        WindowManager.LayoutParams attrs = dialog.getWindow().getAttributes();
        //attrs.setTitle("Title");
        attrs.width = CommonUtils.dp2px(this,300);// attrs.width =580;
        attrs.height = CommonUtils.dp2px(this,200);// attrs.height = 800;
        dialog.getWindow().setAttributes(attrs);
        dialog.show();
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start=true;
                dialog.dismiss();
                showProgress();
                test_calculate();
//                test();
            }
        });
    }

    void test(){
        MyLocation location1=new MyLocation("a","116.40387397","39.91488908","1");
        MyLocation location2=new MyLocation("b","116.40387320","39.91488908","2");
        MyLocation location3=new MyLocation("c","116.40387600","39.91488908","3");
        MyLocation location4=new MyLocation("d","116.40387500","39.91488908","1");
        MyLocation location5=new MyLocation("e","116.40387200","39.91488908","1");
        myLocations.add(location1);
        myLocations.add(location2);
        myLocations.add(location3);
        myLocations.add(location4);
        myLocations.add(location5);
        DBPosition.getInstance(context).insert(location1);
        DBPosition.getInstance(context).insert(location2);
        DBPosition.getInstance(context).insert(location3);
//        DBPosition.getInstance(context).insert();
        adapter.notifyDataSetChanged();
    }

    static CustomDialog progressDialog;
    private void showProgress() {
        progressDialog= CustomDialog.create(context,"测量中...",false,null);
        progressDialog.show();
    }

    //把数据通过队列存储，然后去掉最高2个和最低两个，20个做平均，每秒两次,一个等待的dialog 大概10s
    private  void test_calculate(){
        Log.e("wocao","finish");
        start=false;
        //计算数据并存储给list中
//        double lon=0.0,la=0.0;
//        for(int i=0;i<locs.size();i++){
//            lon+=locs.get(i).getLongitude();
//            la+=locs.get(i).getLatitude();
//        }

        progressDialog.dismiss();
        MyLocation loc;
        if(location!=null){
        loc=new MyLocation(et_position.getText().toString(),
                et_track.getText().toString(),
                String.valueOf(location.getLongitude()),String.valueOf(location.getLatitude()));
            myLocations.add(loc);
            DBPosition.getInstance(context).insert(loc);
            adapter.notifyDataSetChanged();
            locs.clear();
            count=0;
            locations.add(loc);
        }

    }

    ArrayList<MyLocation> locations=new ArrayList<>();


    static boolean start=false;
    static int count=0;
    static ArrayList<Location> locs=new ArrayList<>();
//    static Location t1;
    @SuppressLint("SetTextI18n")
    Location location;
    public void updatePosition(Location location){
//        t1=location;
//        for(int i=0;i<20;i++){
        this.location=location;
        tv_latitude.setText("纬度："+location.getLatitude());
        tv_longitude.setText("经度："+location.getLongitude());
//        if(start&&count<10){
//            locs.add(location);
//            count++;
//        }
//        if(count==10){
//            test_calculate();
//        }
//        }
    }



    static int dis,cal;
    @SuppressLint("SetTextI18n")
    public  void updateSatellite(int d, int c){
        if(dis!=d&&cal!=c){
            tv_calibrated.setText("已校准卫星："+c);
            tv_discovered.setText("已发现卫星："+d);
            dis=d;
            cal=c;
        }
    }


    public void hintKeyBoard() {
        //拿到InputMethodManager
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //如果window上view获取焦点 && view不为空
        if (imm.isActive() && getCurrentFocus() != null) {
            //拿到view的token 不为空
            if (getCurrentFocus().getWindowToken() != null) {
                //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }

    }


}