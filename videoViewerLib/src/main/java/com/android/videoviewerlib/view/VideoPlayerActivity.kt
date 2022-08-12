package com.android.videoviewerlib.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.videoviewerlib.R
import kotlinx.android.synthetic.main.video_ijk_view.*

class VideoPlayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.video_ijk_view)
        val videoPlayerIjk = video_ijk_view
        videoPlayerIjk.setListener()
    }
}