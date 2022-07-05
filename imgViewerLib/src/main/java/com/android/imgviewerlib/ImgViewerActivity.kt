package com.android.imgviewerlib

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_img_viewer.*
const val PARAM_PATH = "path"
const val PARAM_NAME = "name"

class ImgViewerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_img_viewer)
        //文件路径
        val path = intent.getStringExtra(PARAM_PATH)
        //文件名
        val name = intent.getStringExtra(PARAM_NAME)
        toolbar.apply {
            title = name
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener {
                finish()
            }
        }
        iv_photo.apply {
            setImageBitmap(BitmapFactory.decodeFile(path))
        }
    }
}