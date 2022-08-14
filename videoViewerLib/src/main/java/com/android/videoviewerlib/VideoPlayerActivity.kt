package com.android.videoviewerlib

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.videoviewerlib.VideoPlayerListene
import kotlinx.android.synthetic.main.video_ijk_view.*
import tv.danmaku.ijk.media.player.IMediaPlayer

const val PARAM_PATH = "path"
const val PARAM_NAME = "name"

class VideoPlayerActivity : AppCompatActivity() {
    private val TAG: String = "VideoPlayerActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.video_ijk_view)
        val videoPlayerIjk = video_ijk
        Log.i(TAG, "onCreate: 0")
        with(videoPlayerIjk) {
            Log.i(TAG, "onCreate: 1")
            setListener(object : VideoPlayerListene() {
                override fun onBufferingUpdate(p0: IMediaPlayer?, p1: Int) {

                }

                override fun onCompletion(p0: IMediaPlayer?) {

                }

                override fun onPrepared(p0: IMediaPlayer?) {

                }

                override fun onInfo(p0: IMediaPlayer?, p1: Int, p2: Int): Boolean {
                    return true
                }

                override fun onVideoSizeChanged(
                    p0: IMediaPlayer?,
                    p1: Int,
                    p2: Int,
                    p3: Int,
                    p4: Int
                ) {

                }

                override fun onError(p0: IMediaPlayer?, p1: Int, p2: Int): Boolean {
                    return true
                }

                override fun onSeekComplete(p0: IMediaPlayer?) {

                }

            })
            Log.i(TAG, "onCreate: 2")
            val path = intent.getStringExtra(PARAM_PATH) ?: ""
            if (path.isEmpty()) {
                Log.i(TAG, "未获取到文件")
            } else {
                Log.i(TAG, "文件路径：$path")
                setVideoPath(path)
                start()
            }
        }
    }
}