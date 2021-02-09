package com.example.mylibrary;

import android.content.Context;

import com.alibaba.android.arouter.facade.template.IProvider;

public interface TestService extends IProvider {
    void showToast(Context context);
    void sendMessage(String uid,String s);
}
