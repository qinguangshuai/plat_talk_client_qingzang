package com.example.testposition;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.mylibrary.db.MyLocation;

import java.util.ArrayList;

public class PositionAdapter extends BaseAdapter {
    ArrayList<MyLocation> locations;
    Context context;
    int type;
    public PositionAdapter(Context context,ArrayList<MyLocation> locations,int type){
        this.locations=locations;
        this.context=context;
        this.type=type;
    }


    @Override
    public int getCount() {
        return locations.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
            @SuppressLint("ViewHolder") View view= View.inflate(context, R.layout.item_location, null);
            TextView tv_p=view.findViewById(R.id.position);
            TextView tv_long=view.findViewById(R.id.longitude);
            TextView tv_la=view.findViewById(R.id.latitude);
            final CheckBox check=view.findViewById(R.id.check);
            final MyLocation location=locations.get(position);
            tv_p.setText(location.getPosition()+","+location.getTrack());

            tv_long.setText(location.getLongitude()+",");
            tv_la.setText(String.valueOf(location.getLatitude()));
            check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(check.isChecked()){//select
                        if(type==1){
                            TestActivity.getTestAct().selectList.add(location);
                        }else{
                            PositionActivity.selectList.add(location);
                        }

                    }else{//cancel
                        if(type==1){
                            TestActivity.getTestAct().selectList.remove(location);
                        }else{
                            PositionActivity.selectList.remove(location);
                        }

                    }
                }
            });
        return view;
    }
}
