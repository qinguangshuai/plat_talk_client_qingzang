package com.tencent.qcloud.tim.demo.Layout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import androidx.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.qcloud.tim.demo.Database.Diaodan;
import com.tencent.qcloud.tim.demo.Database.DiaodanDatabase;
import com.tencent.qcloud.tim.demo.R;
import com.tencent.qcloud.tim.demo.qingzang.SendActivity;

import java.util.List;

import static com.tencent.qcloud.tim.demo.qingzang.SendActivity.gou_list;

public class ReportDetailAdapter extends RecyclerView.Adapter {
    private List<String> list;
    private Context context;
    private OnItemClickListener mListener;


    //声明自定义的监听接口
    private OnRecyclerItemClickListener monItemClickListener;
    private int setcolor = 0;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }


    public interface OnItemClickListener {
        void onItemClick(int position, int setcolor);
    }


    DiaodanDatabase db = null;

    public ReportDetailAdapter(Context context, List<String> list) {
        db = Room.databaseBuilder(context,
                DiaodanDatabase.class, "Diaodan_Database")
                .fallbackToDestructiveMigration()
                .build();
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 1) {
            return new OneViewHolder(View.inflate(context, R.layout.oneviewitem, null));
        } else {
            return new TwoViewHolder(View.inflate(context, R.layout.twoviewitem, null));
        }

    }

    int pdd = 0;

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        /*FindDatabase findDatabase = new FindDatabase();
        findDatabase.execute();*/
        if (getItemViewType(position) == 1) {
            OneViewHolder oneViewHolder = (OneViewHolder) holder;
            oneViewHolder.setData(position);
        } else {
            TwoViewHolder twoViewHolder = (TwoViewHolder) holder;
            twoViewHolder.setData(position);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 1;
        } else {
            return 2;
        }
    }

    class OneViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_danju;

        public OneViewHolder(View itemview) {
            super(itemview);
        }

        public void setData(int position) {
            tv_danju = (TextView) itemView.findViewById(R.id.tv_danju);
            tv_danju.setText(list.get(position));
        }
    }

    private class TwoViewHolder extends RecyclerView.ViewHolder {
        private TextView unit_tv;
        private TextView projectnum_tv;
        private TextView yearplaninvest_tv;
        private TextView nowmonthinvest_tv;
        private TextView onetonowinvest_tv;
        private TextView Investmentcompletion_tv;
        private TextView investmentgrowth_tv;
        private LinearLayout linerLayout_re;


        public TwoViewHolder(View itemView) {
            super(itemView);
        }


        public void setData(final int position) {

            linerLayout_re = (LinearLayout) itemView.findViewById(R.id.linerLayout_re);
            unit_tv = (TextView) itemView.findViewById(R.id.unit_tv);
            projectnum_tv = (TextView) itemView.findViewById(R.id.projectnum_tv);
            yearplaninvest_tv = (TextView) itemView.findViewById(R.id.yearplaninvest_tv);
            nowmonthinvest_tv = (TextView) itemView.findViewById(R.id.nowmonthinvest_tv);
            onetonowinvest_tv = (TextView) itemView.findViewById(R.id.onetonowinvest_tv);
            /*Investmentcompletion_tv = (TextView) itemView.findViewById(R.id.Investmentcompletion_tv);
            investmentgrowth_tv = (TextView) itemView.findViewById(R.id.investmentgrowth_tv);*/
            Log.e("swy", "1");

            String[] str = list.get(position).split(",");


            unit_tv.setText(str[0]);

            projectnum_tv.setText(str[1]);

            yearplaninvest_tv.setText(str[2]);

            nowmonthinvest_tv.setText(str[3]);

            if (str.length < 5) {
            } else {
                onetonowinvest_tv.setText(str[4]);
            }

            System.out.println(gou_list + "2222222222222");

            try {
                if (!gou_list.isEmpty()) {
                    for (int i = 0; i < gou_list.size(); i++) {
                        if (gou_list.get(i).equals(String.valueOf(position))) {
                            linerLayout_re.setBackgroundColor(context.getResources().getColor(R.color.red));
                        }
                    }
                }


                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onItemClick(position, setcolor);

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setIcon(R.drawable.ic_launcher_foreground);
                        builder.setTitle("是否确认勾" + position);

                        View view2 = LayoutInflater.from(context).inflate(R.layout.pop_up_dialog_send, null);
                        //    设置我们自己定义的布局文件作为弹出框的Content
                        builder.setView(view2);

                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                System.out.println(position + "-----swy-----");
                                linerLayout_re.setBackgroundColor(context.getResources().getColor(R.color.red));
                                String diaohao = list.get(0).substring(list.get(0).indexOf("第"), list.get(0).indexOf("号")).replace("第", "");
                                if (diaohao.length() == 1) {
                                    diaohao = "0" + diaohao;
                                }
                                gou_number = position + "";
                                sendMessage(SendActivity.currnumber + "," + SendActivity.caozuoyuan + "," + SendActivity.diaohao + "," + diaohao + "," + position + "," + SendActivity.time_date);
                                UpdateDatabase updateDatabase = new UpdateDatabase();
                                updateDatabase.execute();
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
            } catch (Exception e) {
            }
        }

        public void setNewData(final int position) {
            linerLayout_re = (LinearLayout) itemView.findViewById(R.id.linerLayout_re);
            linerLayout_re.setBackgroundColor(context.getResources().getColor(R.color.red));
        }
    }

    TIMConversation conversation;

    private void sendMessage(String s) {
        conversation = TIMManager.getInstance().getConversation(
                TIMConversationType.Group,   //会话类型：
                SendActivity.diaohao);
        TIMMessage msg = new TIMMessage();
        //添加文本内容
        TIMTextElem elem = new TIMTextElem();
        elem.setText(s);
        if (msg.addElement(elem) != 0) {
            Log.d("wocao", "addElement failed");
            return;
        }


        conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {//发送消息回调
            @Override
            public void onError(int code, String desc) {//发送消息失败
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 含义请参见错误码表
                Log.d("wocao", "send message failed. code: " + code + " errmsg: " + desc);
            }

            @Override
            public void onSuccess(TIMMessage msg) {//发送消息成功
                Log.e("wocao", "SendMsg ok");
            }
        });
        msg = null;
    }

    String gou_number = "";

    private class UpdateDatabase extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String every_add = gou_number + "-";
            List<Diaodan> users = db.DiaodanDAO().getAll();
            if (!(users.isEmpty() || users == null)) {
                String allUsers = "";
                for (Diaodan temp : users) {
                    if (SendActivity.time_date.matches(temp.getCurrent_time())) {
                        Diaodan diaodan = temp;
                        diaodan.setGou_number(temp.gou_number + every_add);
                        db.DiaodanDAO().updateDiaodan(diaodan);
                        allUsers = diaodan.gou_number;
                    }
                }
                System.out.println(gou_number + "---------------" + SendActivity.time_date + "----------" + allUsers);
                return allUsers;
            } else {
                return "";
            }
        }

        @Override
        protected void onPostExecute(String details) {

        }
    }
}