package com.kylindev.totalk.net;

import android.os.Handler;
import android.os.Message;

import com.kylindev.totalk.MainApp;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class test {

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    UploadFileRequestBody fileRequestBody;
    Subscription subscribe;
    public void onStartUpload() {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        File file=new File("/sdcard/Pictures",MainApp.getName());
        fileRequestBody = new UploadFileRequestBody(file, new DefaultProgressListener(
               0,handler));
//        requestBodyMap.put("file\"; filename=\"/sdcard/Pictures\n"+ MainApp.getName(), fileRequestBody);
        ServerAPI serverAPI = RetrofitUtil.createService(ServerAPI.class);
        subscribe = serverAPI.uploadImage(fileRequestBody,"",1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
//                        Toast.makeText();

                    }

                    @Override
                    public void onNext(Result<String> result) {
//                        ServerProto.L.e(TAG, "上传成功");
//                        Attachment s = attachmentResult.getRetData();
//                        onCompletedUpload(s.getAid());
                        if(result.getRetData().equals("ok")){

                        }else{

                        }
                    }
                });
    }

    class DefaultProgressListener implements ProgressListener {

        long completed;
        Handler handler;
        public DefaultProgressListener(long completed,Handler handler) {
            this.completed = completed;
            this.handler=handler;
        }

        @Override
        public void onProgress(long hasWrittenLen, long totalLen, boolean hasFinish) {

        }
    }

}
