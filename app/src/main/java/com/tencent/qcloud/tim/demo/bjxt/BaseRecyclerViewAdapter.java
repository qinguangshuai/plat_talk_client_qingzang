package com.tencent.qcloud.tim.demo.bjxt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 *页面功能-1： 【数据的增,删,改,查】 ✔
 *页面功能-2： 【添加 header  footer 数据】 ✔
 *页面功能-3： 【条目点击事件(连点已处理)】 ✔
 *页面功能-4： 【条目长按事件】 ✔
 *页面功能-5： 【条目点击动画 请在item 的root布局中 添加      android:background="?android:attr/selectableItemBackground"      】      ✔
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<T> mList;      //创建集合 泛型是 传进来的

    private Context mContext;        //传入一个 上下文 对象

    private int mLayout;             //布局文件

    public ItemClick mItemClick;     //单点接口对象

    public ItemLongClick mItemLongClick;     //单点接口对象

    public BaseRecyclerViewAdapter(Context mContext, int mLayout) {
        this.mContext = mContext;
        this.mLayout = mLayout;
        mList = new ArrayList<>();
    }

    //注册setmItemLongClick
    public void setmItemLongClick(ItemLongClick mItemLongClick) {
        this.mItemLongClick = mItemLongClick;
    }

    //注册setItemClick
    public void setmItemClick(ItemClick mItemClick) {
        this.mItemClick = mItemClick;
    }

    //获取集合
    public ArrayList<T> getList() {
        return mList;
    }


    //获取条目内容
    public T getItem(int position) {
        if (position > -1 && position <= mList.size() - 1) {
            return mList.get(position);
        }
        return null;
    }

    //添加数据 覆盖
    public void setList(List list) {
        if (list != null) {
            if (this.mList.size() > 0 && this.mList != null) {
                this.mList.clear();
            }
            this.mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    //在最后增加一条数据
    public void append(T data) {
        if (data != null) {
            addData(mList.size() - 1, data);
        }
    }

    //在头部增加一条数据
    public void incertHead(T data) {
        if (data != null) {
            addData(0, data);
        }
    }

    //在最后增加N条数据
    public void addList(List list) {
        if (list != null && list.size() > 0) {
            mList.addAll(mList.size() - 1, list);
            notifyItemInserted(this.mList.size() - list.size() - 1);
        }
    }

    //在指定位置增加一条数据
    public void addData(int position, T data) {
        if (position > -1 && data != null) {
            mList.add(position, data);
            notifyItemRangeInserted(position, 1);
        }
    }

    //在指定位置增加N条数据
    public void addList(int position, List list) {
        if (position > -1 && position <= mList.size() - 1 && list.size() > 0 && list != null) {
            mList.addAll(position, list);
            notifyItemRangeInserted(position, mList.size());
        }

    }

    //删除N条数据
    public void removeList(List list) {
        if (list.size() > 0 && list != null) {
            mList.removeAll(list);
            notifyItemRemoved(list.size());
        }
    }

    //删除某条数据
    public void remove(T data) {
        if (data != null) {
            mList.remove(data);
        }
    }

    //删除指定位置数据
    public void remove(int position) {
        if (position > -1 && position <= mList.size() - 1) {
            mList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mList.size());
        }
    }

    //清空数据
    public void clearList() {
        if (mList.size() > 0) {
            mList.clear();
        }
        notifyDataSetChanged();
    }



    //让继承的子类重写这个方法
    public abstract void onBindChildViewHolder(RecyclerView.ViewHolder viewHolder, int position, T mItemData);

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(mLayout, viewGroup, false);
        return new Holder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int position) {

        /*
        获取数据
         */
        T mItemData = getItem(position);


        /*
        条目点击事件
         */
        onBindChildViewHolder(viewHolder, position, mItemData);



        /*
        条目点击事件
         */
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mItemClick!=null){
                    mItemClick.setOnItemClick(v, position);
                }
            }
        });


         /*
        条目长按事件
         */
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mItemLongClick!=null){
                    mItemLongClick.setOnItemLongClick(v, position);
                }
                return false;
            }
        });


    }


    @Override
    public int getItemCount() {
        if (mList.size() > 0 && mList != null) {
            return mList.size();
        }
        return 0;
    }


    private class Holder extends RecyclerView.ViewHolder {
        public Holder(View view) {
            super(view);
        }
    }


    //条目单点
    public interface ItemClick {
        void setOnItemClick(View view, int position);
    }


    //条目长按
    public interface ItemLongClick {
        void setOnItemLongClick(View view, int position);
    }

}

