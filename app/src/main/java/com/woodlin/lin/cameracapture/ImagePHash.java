package com.woodlin.lin.cameracapture;

import android.app.Activity;

/**
 * Created by lin on 15/12/7.
 */
    public class ImagePHash extends Activity{

//    private Bitmap testimg;
//    private Bitmap matchbitmap;
//    private CameraBridgeViewBase mOpenCvCameraView;
//    private Mat mRgba;
//    private Mat mGray;
//    private Mat mByte;
//    private Scalar CONTOUR_COLOR;
//    private boolean isProcess = false;
//    private String filepath = "/sdcard/test.jpg";
//    private static final String TAG = "Dawn";
//    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
//        @Override
//        public void onManagerConnected(int status) {
//            switch (status) {
//                case LoaderCallbackInterface.SUCCESS: {
//                    Log.i(TAG, "OpenCV loaded successfully");
//                    mOpenCvCameraView.enableView();
//                }
//                break;
//                default: {
//                    super.onManagerConnected(status);
//                }
//                break;
//            }
//        }
//    };
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        setContentView(R.layout.activity_main);
//        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.objectMatch);
//        mOpenCvCameraView.setCvCameraViewListener(this);
//        final ImageView showimg = (ImageView) findViewById(R.id.ImgPhoto);
//        final ImageView matchimg = (ImageView) findViewById(R.id.Imgmatch);
//        Button showButton = (Button) findViewById(R.id.button_show);
//        Button matchButton = (Button) findViewById(R.id.button_match);
//        Button showmatchButton = (Button) findViewById(R.id.button_showmatch);
//        showButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // show img in the Imageview
//                File file = new File(filepath);
//                if (file.exists()) {
//                    testimg = BitmapFactory.decodeFile(filepath);
//                    // 将图片显示到ImageView中
//                    showimg.setImageBitmap(testimg);
//                }
//            }
//        });
//        matchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // show img in the Imageview
//                isProcess = !isProcess;
//            }
//        });
//        showmatchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // show img in the Imageview
//                matchimg.setImageBitmap(matchbitmap);
//            }
//        });
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (mOpenCvCameraView != null)
//            mOpenCvCameraView.disableView();
//
//    }
//
//    public void onResume() {
//        super.onResume();
//        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_10, this,
//                mLoaderCallback);
//    }
//
//    @Override
//    protected void onDestroy() {
//        Log.e("onDestroy", "INITIATED");
//        super.onDestroy();
//
//        if (mOpenCvCameraView != null)
//            mOpenCvCameraView.disableView();
//
//    }
//
//    public void onCameraViewStarted(int width, int height) {
//        mRgba = new Mat(height, width, CvType.CV_8UC3);
//        mByte = new Mat(height, width, CvType.CV_8UC1);
//
//
//    }
//
//    public void onCameraViewStopped() { // Explicitly deallocate Mats
//        mRgba.release();
//    }
//
//    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
//
//        Bitmap s_testimg;
//        Mat testimage = new Mat();
//        Mat grayimage=new Mat();
//        mRgba = inputFrame.rgba();
//        mGray = inputFrame.gray();
//        CONTOUR_COLOR = new Scalar(255);
//        MatOfDMatch matches = new MatOfDMatch();
//        MatOfKeyPoint keypoint_train = new MatOfKeyPoint();
//        MatOfKeyPoint keypoint_test = new MatOfKeyPoint();
//        KeyPoint kpoint = new KeyPoint();
//        Mat mask = Mat.zeros(mGray.size(), CvType.CV_8UC1);
//        Mat output = new Mat(); // Mat train=new Mat(); Mat
//        Mat test = new Mat();
//        Mat train = new Mat();
//        if (isProcess) {
//            FeatureDetector detector_train = FeatureDetector
//                    .create(FeatureDetector.ORB);
//            detector_train.detect(mGray, keypoint_train);
////            Features2d.drawKeypoints(mGray, keypoint_train, output, new Scalar(
////                    2, 254, 255), Features2d.DRAW_RICH_KEYPOINTS);
//
//            DescriptorExtractor descriptor_train = DescriptorExtractor
//                    .create(DescriptorExtractor.ORB);
//            descriptor_train.compute(mGray, keypoint_train, train);
//            s_testimg = Bitmap.createScaledBitmap(testimg, mGray.width(), mGray.height(), false);
//
//            Utils.bitmapToMat(s_testimg, testimage);
//            Imgproc.cvtColor(testimage, grayimage, Imgproc.COLOR_RGB2GRAY);
//
//            FeatureDetector detector_test = FeatureDetector
//                    .create(FeatureDetector.ORB);
//            detector_test.detect(grayimage, keypoint_test);
//
////            Features2d.drawKeypoints(testimage, keypoint_test, output,
////                    new Scalar(2, 254, 255), Features2d.DRAW_RICH_KEYPOINTS);
//            DescriptorExtractor descriptor_test = DescriptorExtractor
//                    .create(DescriptorExtractor.ORB);
//            descriptor_test.compute(grayimage, keypoint_test, test);
//            DescriptorMatcher descriptormatcher = DescriptorMatcher
//                    .create(DescriptorMatcher.BRUTEFORCE_HAMMING);
//            descriptormatcher.match(test, train, matches);
//            Features2d.drawMatches(grayimage,keypoint_test,mGray, keypoint_train, matches, output);
//            matchbitmap= Bitmap.createScaledBitmap(testimg, output.width(), output.height(), false);
//            Utils.matToBitmap(output, matchbitmap);
//
//            return mRgba;
//        }
//
//        return mRgba;
//    }

}
