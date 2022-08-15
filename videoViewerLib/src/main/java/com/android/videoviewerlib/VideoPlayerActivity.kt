package com.android.videoviewerlib

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.videoviewerlib.util.UiUtil
import com.android.videoviewerlib.view.VideoPlayerIJK
import kotlinx.android.synthetic.main.video_ijk_view.*
import tv.danmaku.ijk.media.player.IMediaPlayer
import tv.danmaku.ijk.media.player.IjkMediaPlayer

const val PARAM_PATH = "path"
const val PARAM_NAME = "name"

class VideoPlayerActivity : AppCompatActivity() {
    private val TAG: String = "VideoPlayerActivity"

    lateinit var videoPlayerIjk: VideoPlayerIJK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.video_ijk_view)
        UiUtil.setActivityTheme(this)

        videoPlayerIjk = video_ijk

        with(videoPlayerIjk) {
            //实现抽象类，监听其中的方法
            setListener(object : VideoPlayerListene() {
                override fun onBufferingUpdate(p0: IMediaPlayer?, p1: Int) {

                }

                override fun onCompletion(p0: IMediaPlayer?) {

                }

                override fun onPrepared(mp: IMediaPlayer?) {
                    //获取到视频文件长宽分辨率
                    val mVideoWidth = mp?.videoWidth
                    val mVideoHeight = mp?.videoHeight
                    if (mVideoWidth != null && mVideoHeight != null)
                        videoPlayerIjk.mRenderView.setVideoSize(mVideoWidth, mVideoHeight)
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

            val path = intent.getStringExtra(PARAM_PATH) ?: ""
            if (path.isEmpty()) {
                Log.i(TAG, "未获取到文件")
            } else {
                Log.i(TAG, "文件路径：$path")
                //设置视频文件路径
                setVideoPath(path)
                start()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        //释放资源
        with(videoPlayerIjk) {
            stop()
            release()
        }
        IjkMediaPlayer.native_profileEnd()
    }
}