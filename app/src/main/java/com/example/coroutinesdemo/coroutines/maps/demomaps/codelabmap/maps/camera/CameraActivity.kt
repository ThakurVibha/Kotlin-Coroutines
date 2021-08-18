package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.camera

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.coroutinesdemo.R
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.toast
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_camera.*
import kotlinx.android.synthetic.main.activity_demo_service.*
import java.io.File


class CameraActivity : AppCompatActivity() {
    var uri: Uri? = null
    lateinit var file: File
    val opencam = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            Log.e("//", ": " + uri)
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, uri!!))
            } else {
                MediaStore.Images.Media.getBitmap(contentResolver, uri)
            }
            capturedImage.setImageBitmap(bitmap)

        }
    }
    var imageUri: Uri? = null
    lateinit var activityLauncher: ActivityResultLauncher<Any?>
    lateinit var videoView: VideoView

    private val takeImageResult = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        "Hi from camera" + it.toast(this)
//            capturedImage.setImageURI(it)
    }
    private val takeVideoResult =
        registerForActivityResult(ActivityResultContracts.CaptureVideo()) {

        }

    private val cropActivityResultContract = object : ActivityResultContract<Any?, Uri?>() {
        override fun createIntent(context: Context, input: Any?): Intent {
            return CropImage.activity().setAspectRatio(16, 9)
                .setBackgroundColor(R.color.black)
                .setActivityMenuIconColor(R.color.teal_700)
                .setAllowFlipping(true)
                .setBorderCornerLength(8F)

                .getIntent(this@CameraActivity)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return CropImage.getActivityResult(intent).uri
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        videoView = VideoView(this)
        hasCamera()
        onClick()
        activityLauncher = registerForActivityResult(cropActivityResultContract) {
            capturedImage.setImageURI(it)
        }
    }

    private fun launchVideoResult() {
        takeVideoResult.launch(null)
    }

    private fun launchResult() {
        CropImage.startPickImageActivity(this)
//        file = File.createTempFile("tmp_image_file${System.currentTimeMillis()}", ".png", cacheDir).apply { createNewFile() }
//        uri = FileProvider.getUriForFile(
//            this,
//            "$packageName.provider",
//            file
//        )
//        opencam.launch(uri)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val activityResult = CropImage.getActivityResult(data)
        capturedImage.setImageURI(activityResult.uri)
    }

    private fun onClick() {
        btnTakePicture.setOnClickListener {
            Toast.makeText(this, "Starting Camera....Please wait", Toast.LENGTH_SHORT).show()
            launchResult()
        }
        btnCaptureVideo.setOnClickListener {
            launchVideoResult()
        }
        btnCrop.setOnClickListener {
            activityLauncher.launch(it)
        }

    }

    private fun hasCamera(): Boolean {
        Log.e("TAG", "hasCamera: ")
        return packageManager.hasSystemFeature(
            PackageManager.FEATURE_CAMERA_ANY
        )
    }

}