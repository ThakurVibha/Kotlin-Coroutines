package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.camera

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.drawable.AdaptiveIconDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.coroutinesdemo.R
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.toast
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_camera.*
import kotlinx.android.synthetic.main.activity_demo_service.*
import java.io.*
import java.util.*


class CameraActivity : AppCompatActivity() {
    var uri: Uri? = null
    lateinit var file: File
    lateinit var videoView: VideoView
    private var requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            for (entry in it.entries) {

            }
        }

    private val takePictureFromCamera =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            capturedImage.setImageBitmap(it)

            //save image to internal storage
            val wrapper = ContextWrapper(applicationContext)
            var file = wrapper.getDir("images", Context.MODE_PRIVATE)
            // Create a file to save the image
            file = File(file, "${UUID.randomUUID()}.jpg")
            try {
                // Get the file output stream
                val stream: OutputStream = FileOutputStream(file)
                // Compress bitmap
                it.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                // Flush the stream
                stream.flush()
                // Close stream
                stream.close()
            } catch (e: IOException) { // Catch the exception
                e.printStackTrace()
            }
            var savedURI = Uri.parse(file.absolutePath)
            Log.e("savedURI", "This is the path$savedURI")
            "Showing last captured image in window".toast(this)
            ivLastCaptured.setImageURI(savedURI)

            //Saving image to gallery
            val savedImage = MediaStore.Images.Media.insertImage(
                contentResolver,
                it,
                "MyImage",
                "Dummy image insertion"
            )
            var imageURi = Uri.parse(savedImage)
            Log.e("imageURiTag", "this is imageURI $imageURi")

        }


    private val openCamera = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            Log.e("//", ": $uri")
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, uri!!))
            } else {
                MediaStore.Images.Media.getBitmap(contentResolver, uri)
            }
            capturedImage.setImageBitmap(bitmap)
        }
    }
    private val takeVideoResult =
        registerForActivityResult(ActivityResultContracts.CaptureVideo()) {
            Log.e("videoTAG", it.toString() )
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

    lateinit var activityLauncher: ActivityResultLauncher<Any?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        videoView = VideoView(this)
        hasCamera()
        activityLauncher = registerForActivityResult(cropActivityResultContract) {
            capturedImage.setImageURI(it)
        }
        onClick()
        showDefaultImage()
    }

    private fun launchVideoResult() {
        takeVideoResult.launch(null)
    }

    private fun showDefaultImage() {
        "Showing a default image for further actions".toast(this)
        capturedImage.setImageResource(R.drawable.flower)
    }

    //Click events
    private fun onClick() {
        btnTakePicture.setOnClickListener {
            Toast.makeText(this, "Starting Camera....Please wait", Toast.LENGTH_SHORT).show()
//            launchResult()
            takePictureFromCamera.launch(null)
        }
        btnCaptureVideo.setOnClickListener {
            launchVideoResult()
        }
        btnCrop.setOnClickListener {
            activityLauncher.launch(it)
        }
        btnPermissions.setOnClickListener {
            requestPermission.launch(
                arrayOf(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_WIFI_STATE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            )
        }
        btnCompress.setOnClickListener {
            compressImage()
        }

    }

    private fun hasCamera(): Boolean {
        Log.e("TAG", "hasCamera: ")
        return packageManager.hasSystemFeature(
            PackageManager.FEATURE_CAMERA_ANY
        )
    }

    private fun compressImage() {
        var stream = ByteArrayOutputStream()
        var drawable =
            ContextCompat.getDrawable(applicationContext, R.drawable.flower)
        var bitmap = drawable!!.toBitmap()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 1, stream)
        val byteArray = stream.toByteArray()
        var compressedImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        "Images compressed".toast(this)
        capturedImage.setImageBitmap(compressedImage)

    }

//    private fun launchResult() {
//        CropImage.startPickImageActivity(this)
//        file = File.createTempFile("tmp_image_file${System.currentTimeMillis()}", ".png", cacheDir)
//            .apply { createNewFile() }
//        uri = FileProvider.getUriForFile(
//            this,
//            "$packageName.provider",
//            file
//        )
//        openCamera.launch(uri)
//    }

}