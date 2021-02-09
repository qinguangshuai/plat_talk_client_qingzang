package com.kylindev.totalk.net;

/**
 * Created by mac-yk on 2017/6/7.
 */

public interface ProgressListener {
    void onProgress(long hasWrittenLen, long totalLen, boolean hasFinish);
}
