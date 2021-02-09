package com.kylindev.totalk.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.kylindev.totalk.MainApp;
import com.kylindev.totalk.R;
import com.kylindev.totalk.utils.CameraUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CameraActivity extends AppCompatActivity implements SurfaceHolder.Callback, View.OnClickListener {

    private Camera mCamera;
    private SurfaceView mSurfaceView;
    private Button mStartcamera, mDetermine;
    private ImageButton mCameraSwitch, openLight;
    private ImageView mBack, mBackim, mBackimg;
    private RelativeLayout mRelative, mHiderelative, mBootomRly, mTopRly;
    private View focusIndex;
    private SurfaceHolder mHolder;
    private int cameraPosition = 0;//0代表前置摄像头,1代表后置摄像头,默认打开前置摄像头
    boolean safeToTakePicture = true;
    private float pointX, pointY;
    static final int FOCUS = 1;            // 聚焦
    static final int ZOOM = 2;            // 缩放
    private int mode;                      //0是聚焦 1是放大
    private float dist;
    //放大缩小
    int curZoomValue = 0;

    Camera.Parameters parameters;
    private Handler handler = new Handler();

    /* *
     *   图像数据处理完成后的回调函数
     * */
    private Camera.PictureCallback mJpeg = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(final byte[] data, Camera camera) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.e("data",data+"    data");
                    try {
                        //将照片改为竖直方向
                        bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                        Log.e("bitmap", bitmap +"    bitmap");
                        Matrix matrix = new Matrix();
                        switch (cameraPosition) {
                            case 0://前
                                matrix.preRotate(90);
                                break;
                            case 1:
                                matrix.preRotate(90);
                                break;
                        }
                        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                        saveImageToGallery(getBaseContext(), bitmap);

                        mCamera.stopPreview();
                        //mCamera.startPreview();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    safeToTakePicture = true;
                }
            }).start();
        }
    };
    public static Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fule);
        CameraUtil.init(this);
        initView();
        initData();
    }

    private void initView() {
        mSurfaceView = findViewById(R.id.surface);
        mSurfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoFocus();
            }
        });
        mStartcamera = findViewById(R.id.takePhoto);
        mCameraSwitch = findViewById(R.id.cameraSwitch);
        openLight = findViewById(R.id.openLight);
        mBack = findViewById(R.id.back);
        mRelative = findViewById(R.id.relative);
        mHiderelative = findViewById(R.id.hiderelative);
        mBootomRly = findViewById(R.id.bootomRly);
        mTopRly = findViewById(R.id.topRly);
        mBackimg = findViewById(R.id.backimg);
        mDetermine = findViewById(R.id.determine);
        focusIndex = findViewById(R.id.focus_index);
        mStartcamera.setOnClickListener(this);
        mCameraSwitch.setOnClickListener(this);
        mBackimg.setOnClickListener(this);
        mDetermine.setOnClickListener(this);
        openLight.setOnClickListener(this);
        mBack.setOnClickListener(this);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initData() {
        mHolder = mSurfaceView.getHolder();
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mHolder.addCallback(this); // 回调接口

            /*mSurfaceView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        // 主点按下
                        case MotionEvent.ACTION_DOWN:
                            pointX = event.getX();
                            pointY = event.getY();
                            mode = FOCUS;
                            break;
                        // 副点按下
                        case MotionEvent.ACTION_POINTER_DOWN:
                            dist = spacing(event);
                            // 如果连续两点距离大于10，则判定为多点模式
                            if (spacing(event) > 10f) {
                                mode = ZOOM;
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_POINTER_UP:
                            mode = FOCUS;
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if (mode == FOCUS) {
                            } else if (mode == ZOOM) {
                                float newDist = spacing(event);
                                if (newDist > 10f) {
                                    float tScale = (newDist - dist) / dist;
                                    if (tScale < 0) {
                                        tScale = tScale * 10;
                                    }
                                    addZoomIn((int) tScale);
                                }
                            }
                            break;
                    }
                    return false;
                }
            });*/

            /*mSurfaceView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        pointFocus((int) pointX, (int) pointY);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(focusIndex.getLayoutParams());
                    layout.setMargins((int) pointX - 60, (int) pointY - 60, 0, 0);
                    focusIndex.setLayoutParams(layout);
                    focusIndex.setVisibility(View.VISIBLE);
                    ScaleAnimation sa = new ScaleAnimation(3f, 1f, 3f, 1f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
                    sa.setDuration(800);
                    focusIndex.startAnimation(sa);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            focusIndex.setVisibility(View.INVISIBLE);
                        }
                    }, 700);
                }
            });*/
    }

    private void addZoomIn(int delta) {
        try {
            Camera.Parameters params = mCamera.getParameters();
            Log.d("Camera", "Is support Zoom " + params.isZoomSupported());
            if (!params.isZoomSupported()) {
                return;
            }
            curZoomValue += delta;
            if (curZoomValue < 0) {
                curZoomValue = 0;
            } else if (curZoomValue > params.getMaxZoom()) {
                curZoomValue = params.getMaxZoom();
            }

            if (!params.isSmoothZoomSupported()) {
                params.setZoom(curZoomValue);
                mCamera.setParameters(params);
                return;
            } else {
                mCamera.startSmoothZoom(curZoomValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 两点的距离
     */
    private float spacing(MotionEvent event) {
        if (event == null) {
            return 0;
        }
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * 释放相机资源
     */
    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    public void onResume() {
        super.onResume();
        if (mCamera == null) {
            mCamera = getCamera(cameraPosition);
            if (mHolder != null) {
                startPreview(mCamera, mHolder);
            }
        }
        autoFocus();
    }


    /**
     * 获取Camera实例
     */
    private Camera getCamera(int id) {
        Camera camera = null;
        try {
            camera = Camera.open(id);
        } catch (Exception e) {

        }
        return camera;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startPreview(mCamera, holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mCamera.stopPreview();
        startPreview(mCamera, holder);
        autoFocus();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
    }

    //实现自动对焦
    private void autoFocus() {
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (mCamera == null) {
                    return;
                }
                mCamera.autoFocus(new Camera.AutoFocusCallback() {
                    @Override
                    public void onAutoFocus(boolean success, Camera camera) {
                        if (success) {
                            setupCamera(camera);//实现相机的参数初始化
                            camera.cancelAutoFocus();
                        }
                    }
                });
            }
        };
    }

    //定点对焦的代码
    private void pointFocus(int x, int y) {
        mCamera.cancelAutoFocus();
        parameters = mCamera.getParameters();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            showPoint(x, y);
        }
        mCamera.setParameters(parameters);
        autoFocus();
    }

    private void showPoint(int x, int y) {
        if (parameters.getMaxNumMeteringAreas() > 0) {
            List<Camera.Area> areas = new ArrayList<Camera.Area>();
            //xy变换了
            int rectY = -x * 2000 / CameraUtil.screenWidth + 1000;
            int rectX = y * 2000 / CameraUtil.screenHeight - 1000;

            int left = rectX < -900 ? -1000 : rectX - 100;
            int top = rectY < -900 ? -1000 : rectY - 100;
            int right = rectX > 900 ? 1000 : rectX + 100;
            int bottom = rectY > 900 ? 1000 : rectY + 100;
            Rect area1 = new Rect(left, top, right, bottom);
            areas.add(new Camera.Area(area1, 800));
            parameters.setMeteringAreas(areas);
        }

        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
    }

    /**
     * 预览相机
     */
    private void startPreview(Camera camera, SurfaceHolder holder) {
        try {
            setupCamera(camera);
            camera.setPreviewDisplay(holder);
            //将预览矫正
            CameraUtil.getInstance().setCameraDisplayOrientation(this, cameraPosition, camera);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置
     */
    private void setupCamera(Camera camera) {
        Camera.Parameters parameters = camera.getParameters();

        List<String> focusModes = parameters.getSupportedFocusModes();
//        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
//            // Autofocus mode is supported 自动对焦
//            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
//        }
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
//        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);

        Camera.Size previewSize = CameraUtil.findBestPreviewResolution(camera);
        parameters.setPreviewSize(previewSize.width, previewSize.height);

        Camera.Size pictrueSize = CameraUtil.getInstance().getPropPictureSize(parameters.getSupportedPictureSizes(), 1000);
        parameters.setPictureSize(pictrueSize.width, pictrueSize.height);

        camera.setParameters(parameters);

        int picHeight = CameraUtil.screenWidth * previewSize.width / previewSize.height;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(CameraUtil.screenWidth, picHeight);
        mSurfaceView.setLayoutParams(params);

    }

    //将bitmap保存在本地，然后通知图库更新
    public void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "bjxt");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        MainApp.setName(fileName);
        Log.e("fileName",fileName+"    fileName");
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "保存图片失败", Toast.LENGTH_SHORT).show();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, "保存图片失败", Toast.LENGTH_SHORT).show();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file)));
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.cameraSwitch) {//切换摄像头
            releaseCamera();
            cameraPosition = (cameraPosition + 1) % mCamera.getNumberOfCameras();
            Log.e("cameraPosition", cameraPosition + "    cameraPosition");
            mCamera = getCamera(cameraPosition);
            if (mHolder != null) {
                startPreview(mCamera, mHolder);
            }
        } else if (i == R.id.back) {//返回
            Intent intent = new Intent(CameraActivity.this, CheckActivity.class);
            startActivity(intent);
            finish();
        } else if (i == R.id.openLight) {//打开闪光灯
            turnLight(mCamera);
        } else if (i == R.id.takePhoto) {//拍照
            if (mCamera != null) {
                if (safeToTakePicture) {
                    safeToTakePicture = false;
                    mCamera.takePicture(null, null, mJpeg);
                }
            }
            mHiderelative.setVisibility(View.VISIBLE);
            mBootomRly.setVisibility(View.GONE);
            mTopRly.setVisibility(View.GONE);
        } else if (i == R.id.backimg) {//返回重新拍照
            mCamera.startPreview();
            mHiderelative.setVisibility(View.GONE);
            mBootomRly.setVisibility(View.VISIBLE);
            mTopRly.setVisibility(View.VISIBLE);
        } else if (i == R.id.determine) {
            Intent intent1 = new Intent(CameraActivity.this, CheckActivity.class);
            intent1.putExtra("action","return");
            startActivity(intent1);
        }
    }

    /**
     * 闪光灯开关   开->关->自动
     *
     * @param mCamera
     */
    private void turnLight(Camera mCamera) {
        if (mCamera == null || mCamera.getParameters() == null
                || mCamera.getParameters().getSupportedFlashModes() == null) {
            return;
        }
        Camera.Parameters parameters = mCamera.getParameters();
        //设置对焦模式，自动对焦
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        String flashMode = mCamera.getParameters().getFlashMode();
        List<String> supportedModes = mCamera.getParameters().getSupportedFlashModes();
        if (Camera.Parameters.FLASH_MODE_OFF.equals(flashMode)
                && supportedModes.contains(Camera.Parameters.FLASH_MODE_ON)) {//关闭状态
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
            mCamera.setParameters(parameters);
            openLight.setImageResource(R.drawable.camera_flash_on);
        } else if (Camera.Parameters.FLASH_MODE_ON.equals(flashMode)) {//开启状态
            if (supportedModes.contains(Camera.Parameters.FLASH_MODE_AUTO)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                openLight.setImageResource(R.drawable.camera_flash_auto);
                mCamera.setParameters(parameters);
            } else if (supportedModes.contains(Camera.Parameters.FLASH_MODE_OFF)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                openLight.setImageResource(R.drawable.camera_flash_off);
                mCamera.setParameters(parameters);
            }
        } else if (Camera.Parameters.FLASH_MODE_AUTO.equals(flashMode)
                && supportedModes.contains(Camera.Parameters.FLASH_MODE_OFF)) {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(parameters);
            openLight.setImageResource(R.drawable.camera_flash_off);
        }
    }
}
