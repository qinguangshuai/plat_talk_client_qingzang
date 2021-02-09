package com.kylindev.totalk.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.kylindev.totalk.bjxt.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;

import android_serialport_api.SerialPort;
import util.HexUtil;

/**
 * @author BJXT-QGS  AppCompatActivity
 * @version 1.0
 * @date 2019/10/14 14:35
 * @description TODO
 */
public abstract class SerialPortActivity extends Activity {
    protected SerialPort mSerialPort, mSerialPort2, mSerialPort3;
    protected static OutputStream mOutputStream, mOutputStream2;
    private InputStream mInputStream, mInputStream2;
    private ReadThread mReadThread;
    private ReadThread2 mReadThread2;

    /**
     * 发送串口指令
     */
    public void sendString(String data) {
        //发送
        //set 1是接收
        try {
            //字符串转化为16进制字符串
            byte[] mByte = data.getBytes("GB2312");
            //将16进制字符串转化为字节数组
            //byte[] mByte = HexUtil.decodeHex(str16);
            //outputStream.write(ByteUtil.hex2byte(data));
            mOutputStream.write(mByte);
            mOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送串口指令
     */
    public static void sendHexString(String data, String serialCode) {
        //发送

        try {
            //字符串转化为16进制字符串
            //byte[] mByte=data.getBytes("GB2312");
            //将16进制字符串转化为字节数组
            byte[] mByte = HexUtil.decodeHex(data);
            //mOutputStream.write(ByteUtil.hex2byte(data));
            if (StringUtils.equals(serialCode, "485")) {
                //485串口
                mOutputStream2.write(mByte);
                mOutputStream2.flush();
            } else {
                if (mOutputStream != null) {
                    mOutputStream.write(mByte);
                    mOutputStream.flush();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //485
    private class ReadThread2 extends Thread {
        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                int size;
                try {
                    //Thread.sleep(1000);
                    byte[] buffer = new byte[1024];
                    if (mInputStream2 == null) return;
                    size = mInputStream2.read(buffer);
                    if (size > 0) {
                        onDataReceived(buffer, size, 485);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    //232
    private class ReadThread extends Thread {

        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                int size;
                try {
                    byte[] buffer = new byte[1024];
                    if (mInputStream == null) return;
                    size = mInputStream.read(buffer);
                    if (size > 0) {
                        onDataReceived(buffer, size, 232);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            ///dev/ashmem
            mSerialPort = new SerialPort(new File("/dev/ttyS3"), 9600, 0);
            mOutputStream = mSerialPort.getOutputStream();
            mInputStream = mSerialPort.getInputStream();
            Log.i("TAG", "串口打开成功232");
            /* Create a receiving thread */
            mReadThread = new ReadThread();
            mReadThread.start();

            //485口
            mSerialPort2 = new SerialPort(new File("/dev/ttyS2"), 115200, 0);
            mOutputStream2 = mSerialPort2.getOutputStream();
            mInputStream2 = mSerialPort2.getInputStream();
            mReadThread2 = new ReadThread2();
            mReadThread2.start();
        } catch (SecurityException e) {
//            DisplayError(R.string.error_security);
        } catch (IOException e) {
//            DisplayError(R.string.error_unknown);
            e.printStackTrace();
        } catch (InvalidParameterException e) {
//            DisplayError(R.string.error_configuration);
        }
    }

    protected abstract void onDataReceived(final byte[] buffer, final int size, final int type);


}
