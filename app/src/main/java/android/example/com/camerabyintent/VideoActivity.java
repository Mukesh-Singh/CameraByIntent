package android.example.com.camerabyintent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {
    private static final String TAG = VideoActivity.class.getSimpleName();
    private VideoView mVideoView;

    private static final int REQUEST_VIDEO_CAPTURE = 1;


    private MediaController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        setTitle("RecordVideo");
        mVideoView=(VideoView)findViewById(R.id.videoView);
        TextView textView=(TextView)findViewById(R.id.controllerAnchor);
        controller = new MediaController(this);
        controller.setAnchorView(textView);
        mVideoView.setMediaController(controller);

    }

    public void recordVideo(View view) {
        dispatchTakeVideoIntent();
    }


    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
        else {
            Toast.makeText(this,getString(R.string.no_app_to_handle_intent),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = intent.getData();
            mVideoView.setVideoURI(videoUri);
            mVideoView.requestFocus();
            mVideoView.start();
        }
    }
}
