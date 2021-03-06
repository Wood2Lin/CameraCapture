package com.woodlin.lin.cameracapture;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;



public class MainActivity extends AppCompatActivity {
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    int screenWidth, screenHeight;
    // 定义系统所用的照相机
    Camera camera;
    Thread thread;
    boolean isPreview = false;
    Handler handler;
//    Bitmap bitmap,bitmap1 = null;
    private Mat mIntermediateMat;
    ImageView iv;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {

                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    public MainActivity() {
//        Log.i(TAG, "Instantiated new " + this.getClass());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        surfaceView = (SurfaceView)findViewById(R.id.camera);
        iv = (ImageView)findViewById(R.id.iv);
        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        // 获取屏幕的宽和高
        display.getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;

        surfaceHolder = surfaceView.getHolder();
//         为surfaceHolder添加一个回调监听器
        surfaceHolder.addCallback(new SurfaceHolder.Callback()
        {
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height)
            {
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder)
            {
                // 打开摄像头
                initCamera();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder)
            {
                // 如果camera不为null ,释放摄像头
                if (camera != null)
                {
                    if (isPreview) camera.stopPreview();
                    camera.release();
                    camera = null;
                }
            }
        });
    }

    private void initCamera()
    {
        if (!isPreview)
        {
            // 此处默认打开后置摄像头。
            // 通过传入参数可以打开前置摄像头
            camera = Camera.open(0);  //①
//            camera.setDisplayOrientation(90);
        }
        if (camera != null && !isPreview)
        {
            try
            {
                Camera.Parameters parameters = camera.getParameters();
                // 设置预览照片的大小
                parameters.setPreviewSize(screenWidth, screenHeight);
                // 设置预览照片时每秒显示多少帧的最小值和最大值
                parameters.setPreviewFpsRange(4, 10);
                // 设置图片格式
                parameters.setPictureFormat(ImageFormat.JPEG);
                // 设置JPG照片的质量
                parameters.set("jpeg-quality", 85);
                // 设置照片的大小
                parameters.setPictureSize(screenWidth, screenHeight);
                // 通过SurfaceView显示取景画面
                camera.setPreviewDisplay(surfaceHolder);  //②
                // 开始预览
                camera.startPreview();  //③
                cap();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            isPreview = true;
        }
    }

    /**
     * 此方法在布局文件中调用
     * */
    public void capture()
    {
        if (camera != null)
        {
            // 控制摄像头自动对焦后才拍照
            camera.autoFocus(autoFocusCallback);  //④
//            camera.cancelAutoFocus();
        }
    }

    Camera.AutoFocusCallback autoFocusCallback = new Camera.AutoFocusCallback()
    {
        // 当自动对焦时激发该方法
        @Override
        public void onAutoFocus(boolean success, final Camera camera)
        {
            if (success)
            {
                // takePicture()方法需要传入3个监听器参数
                // 第1个监听器：当用户按下快门时激发该监听器
                // 第2个监听器：当相机获取原始照片时激发该监听器
                // 第3个监听器：当相机获取JPG照片时激发该监听器
                camera.takePicture(new Camera.ShutterCallback()
                {
                    public void onShutter()
                    {
                        // 按下快门瞬间会执行此处代码

                    }
                }, new Camera.PictureCallback()
                {
                    public void onPictureTaken(byte[] data, Camera c)
                    {
                        // 此处代码可以决定是否需要保存原始照片信息

                    }
                }, myJpegCallback);  //⑤
            }
        }
    };
    Camera.PictureCallback myJpegCallback = new Camera.PictureCallback()
    {
        @Override
        public void onPictureTaken(byte[] data, Camera camera)
        {
            // 根据拍照所得的数据创建位图
            final Bitmap bm = BitmapFactory.decodeByteArray(data, 0,
                    data.length);
                onCameraFrame(bm);
//            String fileName=getFileNmae();
//            if (fileName==null) return;
//            // 创建一个位于SD卡上的文件
//            File file = new File(fileName);
//            FileOutputStream outStream = null;
//            try
//            {
//                // 打开指定文件对应的输出流
//                outStream = new FileOutputStream(file);
//                // 把位图输出到指定文件中
//                bm.compress(Bitmap.CompressFormat.JPEG, 100,
//                        outStream);
//                outStream.close();
//                if (bitmap == null){
//                    bitmap = bm;
//                }else {
//                    bitmap = bm;
//                    bitmap1 = bm;
//                    String str = BitmapCompare.similarity(bitmap,bitmap1);
////                    String str = PictureContrast.similarity(bitmap,bitmap1);
//
//                    Toast.makeText(MainActivity.this, str,
//                            Toast.LENGTH_SHORT).show();
//                }

//            }
//            catch (IOException e)
//            {
//                e.printStackTrace();
//            }

            // 重新浏览
            camera.stopPreview();
            camera.startPreview();
            cap();
            isPreview = true;
        }
    };

    /**
     * 返回摄取照片的文件名
     * @return 文件名
     * */
    protected String getFileNmae() {
        // TODO Auto-generated method stub
        String fileName;
        if (!Environment.getExternalStorageState().equals
                (Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "监测到你的手机没有插入SD卡，请插入SD卡后再试",
                    Toast.LENGTH_LONG).show();
            return null;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH.mm.ss", Locale.getDefault());
        fileName= Environment.getExternalStorageDirectory()+File.separator
                +sdf.format(new Date())+".JPG";
        return fileName;
    }

    private void cap(){
        thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);
                    mIntermediateMat = new Mat();
                    capture();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
    }

    public void onCameraFrame(Bitmap bitmap) {
        Mat rgba = new Mat();
        Utils.bitmapToMat(bitmap,rgba);
        Size sizeRgba = rgba.size();

        Mat rgbaInnerWindow;

        int rows = (int) sizeRgba.height;
        int cols = (int) sizeRgba.width;

        int left = cols / 8;
        int top = rows / 8;

        int width = cols * 3 / 4;
        int height = rows * 3 / 4;

//        rgbaInnerWindow = rgba
//                .submat(top, top + height, left, left + width);
        rgbaInnerWindow = rgba.clone();
        Imgproc.cvtColor(rgba, rgbaInnerWindow,
                Imgproc.COLOR_RGB2GRAY);
        Mat circles = rgbaInnerWindow.clone();
//        rgbaInnerWindow = rgba
//                .submat(top, top + height, left, left + width);
        Imgproc.GaussianBlur(rgbaInnerWindow, rgbaInnerWindow, new Size(5,
                5), 2, 2);
        Imgproc.Canny(rgbaInnerWindow, mIntermediateMat, 80, 90);
        Imgproc.HoughCircles(mIntermediateMat, circles,
                Imgproc.CV_HOUGH_GRADIENT, 1, 200, 80, 30, 100, 200);
        Imgproc.cvtColor(mIntermediateMat, rgbaInnerWindow,
                Imgproc.COLOR_GRAY2BGRA, 4);

        for (int x = 0; x < circles.cols(); x++) {
            Log.e("个数:", circles.size() + "");
            double vCircle[] = circles.get(0, x);
            if (vCircle == null)
                break;
            Point pt = new Point(Math.round(vCircle[0]),
                    Math.round(vCircle[1]));
            int radius = (int) Math.round(vCircle[2]);
            Log.d("cv", pt + " radius " + radius);
            Core.circle(rgba, pt, 3, new Scalar(0, 0, 255), 3);
            Core.circle(rgba, pt, radius, new Scalar(255, 0, 0),
                    5);
        }
        Bitmap bm = bitmap;
        Utils.matToBitmap(rgba,bm);
        iv.setImageBitmap(bm);
        rgbaInnerWindow.release();

    }


    @Override
    public void onResume() {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this,
                mLoaderCallback);
    }
}
