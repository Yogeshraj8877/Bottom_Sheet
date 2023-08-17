package com.example.bottomsheet

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog


class MainActivity : AppCompatActivity() {
    lateinit var imageView: ImageView
    private val pickImage = 100
    private var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)

        val button=findViewById<Button>(R.id.gallery)
        button.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        val click=findViewById<Button>(R.id.clickBtn)
        click.setOnClickListener {
            val dialog=BottomSheetDialog(this)
            val view=layoutInflater.inflate(R.layout.bottom_sheet_dialog,null)
            val camera=view.findViewById<ImageView>(R.id.image)
            val closeBtn=view.findViewById<Button>(R.id.close)
            val folder=view.findViewById<Button>(R.id.photo)

            folder.setOnClickListener {
                val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                startActivityForResult(gallery, pickImage)
            }

            camera.setOnClickListener {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, pic_id)
            }
            closeBtn.setOnClickListener {
                dialog.dismiss()
            }
            dialog.setCancelable(false)
            dialog.setContentView(view)
            dialog.show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            imageView.setImageURI(imageUri)
        }
        if (requestCode== pic_id){
            val photo=data!!.extras!!["data"] as Bitmap
            imageView.setImageBitmap(photo)
        }
    }
    companion object{
        private const val pic_id=123
    }
}