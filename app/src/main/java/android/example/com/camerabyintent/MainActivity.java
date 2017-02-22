package android.example.com.camerabyintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
//    private static final int REQ_MY_CAMERA_IMAGE = 100;
//    private static final int REQ_MY_CAMERA_VIDEO = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void takeSmallPicture(View view){
        startActivity(new Intent(this,SmallImageActivity.class));
    }

    public void takeLargePicture(View view){
        startActivity(new Intent(this,LargeImageActivity.class));
    }

    public void takeVideo(View view){
        startActivity(new Intent(this,VideoActivity.class));

    }

//    public void myCamera(View view) {
//        if (Util.checkCameraHardware(this)){
//            Intent intent=new Intent(this, MyCameraActivity.class);
//            intent.putExtra(MyCameraActivity.MEDIA_CAPTURE_TYPE,MyCameraActivity.MEDIA_IMAGE);
//            startActivityForResult(intent, REQ_MY_CAMERA_IMAGE);
//        }
//        else {
//            Toast.makeText(this,"This device does not have camera !",Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//    public void myCameraVideo(View view) {
//        if (Util.checkCameraHardware(this)){
//            Intent intent=new Intent(this, MyCameraActivity.class);
//            intent.putExtra(MyCameraActivity.MEDIA_CAPTURE_TYPE,MyCameraActivity.MEDIA_VIDEO);
//            startActivityForResult(intent, REQ_MY_CAMERA_VIDEO);
//        }
//        else {
//            Toast.makeText(this,"This device does not have camera !",Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode==RESULT_OK){
//            if (requestCode==REQ_MY_CAMERA_IMAGE){
//                Log.e(TAG,"Image Path: "+data.getStringExtra(MyCameraActivity.CAPTURED_MEDIA_PATH));
//            }
//            else if (requestCode==REQ_MY_CAMERA_VIDEO){
//                Log.e(TAG,"Video Path: "+data.getStringExtra(MyCameraActivity.CAPTURED_MEDIA_PATH));
//            }
//        }
//    }
}