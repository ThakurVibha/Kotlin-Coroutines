package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.camera

import android.R.attr
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.example.coroutinesdemo.R
import kotlinx.android.synthetic.main.activity_camera.*



class CameraActivity : AppCompatActivity() {
    companion object {
        private const val VIDEO_CAPTURE = 1
        private const val CAMERA_CAPTURE = 2

    }

    lateinit var videoView: VideoView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        hasCamera()
        onClick()
    }

    private fun onClick() {
        btnTakePicture.setOnClickListener {
            captureImage()
        }
        btnCaptureVideo.setOnClickListener {
            captureVideo()
            val mediaController = MediaController(this)
            mediaController.setAnchorView(videoView)
            videoView.setMediaController(mediaController)
            videoView = findViewById(R.id.captureVideo)
        }
    }


    private fun captureImage() {
        val captureImageIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(captureImageIntent, CAMERA_CAPTURE)
    }

    private fun hasCamera(): Boolean {
        Log.e("TAG", "hasCamera: ")
        return packageManager.hasSystemFeature(
            PackageManager.FEATURE_CAMERA_ANY
        )
    }

    private fun captureVideo() {
        val videoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        startActivityForResult(videoIntent, VIDEO_CAPTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val videoUri = data!!.data
        if ( resultCode == RESULT_OK && requestCode == CAMERA_CAPTURE ) {
            val picture = data!!.extras!!.get("data")
            ivPicture.setImageBitmap(picture as Bitmap?)
            videoView . setVideoURI (videoUri)
            videoView.start()
        }
    }

}