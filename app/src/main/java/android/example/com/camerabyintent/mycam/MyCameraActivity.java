package android.example.com.camerabyintent.mycam;

/**
 * https://developer.android.com/guide/topics/media/camera.html#custom-camera
 */

import android.content.Intent;
import android.example.com.camerabyintent.R;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class MyCameraActivity extends AppCompatActivity implements View.OnClickListener, Camera.PictureCallback {
    private static final String TAG = MyCameraActivity.class.getSimpleName();
    public static final String MEDIA_CAPTURE_TYPE="capture_type";
    public static final String CAPTURED_MEDIA_PATH="captured_media_path";
    public static final int MEDIA_IMAGE=1;
    public static final int MEDIA_VIDEO=2;

    private Camera mCamera;
    private CameraPreview mPreview;
    private Button mCaptureButton;
    private FrameLayout preview;
    private int mRequestedMediaType=MEDIA_IMAGE;
    //private boolean isRecording;
    //private MediaRecorder mMediaRecorder;
    private File mOutputFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_camera);
        // Create our Preview view and set it as the content of our activity.
        mRequestedMediaType=getIntent().getIntExtra(MEDIA_CAPTURE_TYPE,MEDIA_IMAGE);
        preview= (FrameLayout) findViewById(R.id.camera_preview);
        mCaptureButton =(Button)findViewById(R.id.button_capture);
        mCaptureButton.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        // Create an instance of Camera
        initCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        if (mRequestedMediaType==MEDIA_VIDEO){
//            releaseMediaRecorder();
//        }
        releaseCamera();
    }

    private void initCamera(){
        mCamera = getCameraInstance();
        if (mCamera!=null) {
            mPreview = new CameraPreview(this, mCamera);
            preview.addView(mPreview);
        }
    }

    private void releaseCamera(){
        if (mCamera != null){
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }

    @Override
    public void onClick(View v) {
        if (mRequestedMediaType==MEDIA_IMAGE){
            mOutputFile=Util.getOutputMediaFile(MEDIA_TYPE_IMAGE);
        }
        else {
            mOutputFile=Util.getOutputMediaFile(Util.MEDIA_TYPE_VIDEO);
        }


        switch (mRequestedMediaType) {
            case MEDIA_IMAGE:
                if (mCamera != null) {
                    mCamera.takePicture(null, null, this);
                }
                break;
            case  MEDIA_VIDEO:
                //recordVideo();
                break;
        }
    }


    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {

        if (mOutputFile== null){
            Log.d(TAG, "Error creating media file, check storage permissions: " );
            return;
        }

        try {
            FileOutputStream fos = new FileOutputStream(mOutputFile);
            fos.write(data);
            fos.close();
            imageCapturedSuccess();

        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        }
    }

    private void imageCapturedSuccess() {
        Toast.makeText(MyCameraActivity.this,"Image captured successfully, Path is: "+mOutputFile.getAbsolutePath(),Toast.LENGTH_LONG).show();
        Intent intent=new Intent();
        intent.putExtra(CAPTURED_MEDIA_PATH,mOutputFile.getAbsolutePath());
        setResult(RESULT_OK,intent);
        finish();
    }

//    private boolean prepareVideoRecorder(){
//
//        //mCamera = getCameraInstance();
//        mMediaRecorder = new MediaRecorder();
//
//        // Step 1: Unlock and set camera to MediaRecorder
//        mCamera.unlock();
//        mMediaRecorder.setCamera(mCamera);
//
//        // Step 2: Set sources
//        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
//        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
//
//        // Step 3: Set a CamcorderProfile (requires API Level 8 or higher)
//        mMediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
//
//        // Step 4: Set output file
//        mMediaRecorder.setOutputFile(mOutputFile.toString());
//
//        // Step 5: Set the preview output
//        mMediaRecorder.setPreviewDisplay(mPreview.getHolder().getSurface());
//
//        // Step 6: Prepare configured MediaRecorder
//        try {
//            mMediaRecorder.prepare();
//        } catch (IllegalStateException e) {
//            Log.d(TAG, "IllegalStateException preparing MediaRecorder: " + e.getMessage());
//            releaseMediaRecorder();
//            return false;
//        } catch (IOException e) {
//            Log.d(TAG, "IOException preparing MediaRecorder: " + e.getMessage());
//            releaseMediaRecorder();
//            return false;
//        }
//        return true;
//    }
//
//    private void recordVideo() {
//        if (isRecording) {
//            // stop recording and release camera
//            mMediaRecorder.stop();  // stop the recording
//            releaseMediaRecorder(); // release the MediaRecorder object
//            mCamera.lock();         // take camera access back from MediaRecorder
//
//            // inform the user that recording has stopped
//            mCaptureButton.setText(getString(R.string.capture));
//            isRecording = false;
//            releaseMediaRecorder();
//            videoCapturedSuccess();
//
//        } else {
//            // initialize video camera
//            if (prepareVideoRecorder()) {
//                // Camera is available and unlocked, MediaRecorder is prepared,
//                // now you can start recording
//                mMediaRecorder.start();
//
//                // inform the user that recording has started
//                mCaptureButton.setText(getString(R.string.stop));
//                isRecording = true;
//            } else {
//                // prepare didn't work, release the camera
//                releaseMediaRecorder();
//                // inform user
//            }
//        }
//    }
//
//    private void videoCapturedSuccess() {
//        Toast.makeText(MyCameraActivity.this,"Video captured successfully, Path is: "+mOutputFile.getAbsolutePath(),Toast.LENGTH_LONG).show();
//        Intent intent=new Intent();
//        intent.putExtra(CAPTURED_MEDIA_PATH,mOutputFile.getAbsolutePath());
//        setResult(RESULT_OK,intent);
//        finish();
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        setResult(RESULT_CANCELED);
//    }
//
//    private void releaseMediaRecorder(){
//        if (mMediaRecorder != null) {
//            mMediaRecorder.reset();   // clear recorder configuration
//            mMediaRecorder.release(); // release the recorder object
//            mMediaRecorder = null;
//            mCamera.lock();           // lock camera for later use
//        }
//    }


}
