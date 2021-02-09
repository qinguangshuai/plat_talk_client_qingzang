package com.example.testposition;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mylibrary.db.DBPosition;
import com.example.mylibrary.db.MyLocation;

import java.util.ArrayList;

public class PositionActivity extends Activity {
   static Context context;
    ListView lv;
    PositionAdapter adapter;
    ArrayList<MyLocation> locs=new ArrayList<>();
    Button bt_test,bt_rm,bt_serach;
    TextView tv;
    EditText et_track;
    static ArrayList<MyLocation> selectList=new ArrayList<>();
    LinearLayout ll;

    public static PositionActivity getTestPos() {
        return (PositionActivity) context;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos);
        ll=findViewById(R.id.ll);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hintKeyBoard();
            }
        });
        context = this;

        tv=findViewById(R.id.text);
        bt_test=findViewById(R.id.bt_test);
        bt_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectList.size()==2){


                    Double d=ComputeUtil.GpsToDistance(location.getLongitude(),location.getLatitude(),
                            Double.valueOf(selectList.get(0).getLongitude()),
                            Double.valueOf(selectList.get(0).getLatitude()),
                            Double.valueOf(selectList.get(1).getLongitude()),
                            Double.valueOf(selectList.get(1).getLatitude()));
                    tv.setText("距离："+d);

                }
            }
        });
        lv=findViewById(R.id.listView);
//        locs=DBPosition.getInstance(context);
        Log.e("wocao",locs.size()+"zuile");
        adapter=new PositionAdapter(context,locs,2);
        lv.setAdapter(adapter);

        bt_rm=findViewById(R.id.bt_rm);
        bt_rm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除后size会变小,所以i不加1
                for(int i=0;i<selectList.size();){
                    DBPosition.getInstance(getApplicationContext()).delete(selectList.get(i).getPosition());
                    locs.remove(selectList.get(i));
                    selectList.remove(i);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        et_track=findViewById(R.id.et_track);
        bt_serach=findViewById(R.id.bt_search);
        bt_serach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locs.clear();
               locs.addAll(DBPosition.getInstance(context).selectByTrack(et_track.getText().toString()));
               Log.e("wocao",locs.size()+"wocao");
               adapter.notifyDataSetChanged();
            }
        });


    }

    @Override
    protected void onDestroy() {
        selectList.clear();
        super.onDestroy();
    }

    Location location;
    public void updatePosition(Location location) {
//        Log.e("wocao","zhendezuile"+location.toString());
        this.location=location;
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
