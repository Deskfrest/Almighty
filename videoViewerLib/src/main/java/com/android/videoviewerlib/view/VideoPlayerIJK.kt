package com.android.videoviewerlib.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.FrameLayout
import com.android.videoviewerlib.VideoPlayerListene
import tv.danmaku.ijk.media.player.IMediaPlayer
import tv.danmaku.ijk.media.player.IjkMediaPlayer
import java.io.IOException
import java.nio.file.Path
import java.security.KeyStore
import java.util.jar.Attributes

class VideoPlayerIJK(context: Context, attributeSet: AttributeSet) :
    FrameLayout(context, attributeSet) {
    private val TAG: String = "VideoPlayerIJK"

    var mMediaPlayer: IMediaPlayer? = null
    lateinit var surfaceView: SurfaceView
    private var listener: VideoPlayerListene? = null
    var mVideoPath: String = ""


    fun setVideoPath(path: String) {
//        IjkMediaPlayer.loadLibrariesOnce(null);
//        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        if (mVideoPath.isEmpty()) {
            //第一次进入，创建新的SurfaceView
            Log.i(TAG, "第一次进入")
            mVideoPath = path
            createSurfaceView()
        } else{
            Log.i(TAG, "直接加载")
            load()
        }

    }

    private fun createSurfaceView() {
        surfaceView = SurfaceView(context)
        surfaceView.holder.addCallback(VideoSurfaceCallback())
        val layoutParams =
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, Gravity.CENTER)
        surfaceView.layoutParams = layoutParams
        addView(surfaceView)
    }

    inner class VideoSurfaceCallback : SurfaceHolder.Callback {

        override fun surfaceCreated(p0: SurfaceHolder) {

        }

        override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
            //surfaceview创建成功后，加载视频
            Log.i(TAG, "surfaceChanged: 创建成功")
            load()
        }

        override fun surfaceDestroyed(p0: SurfaceHolder) {

        }

    }

    private fun load() {
        createPlayer()
        try {
            mMediaPlayer?.dataSource = mVideoPath
        } catch (e: IOException) {
            Log.e(TAG, "load: " + e.stackTrace)
        }
    }

    //创建ijk播放器
    private fun createPlayer() {
        mMediaPlayer?.let {
            it.stop()
            it.setDisplay(null)
            it.release()
        }
        val ijkMediaPlayer: IjkMediaPlayer = IjkMediaPlayer()
        mMediaPlayer = ijkMediaPlayer
        if (listener != null) {
            mMediaPlayer?.let {
                with(it) {
                    setOnPreparedListener(listener)
                    setOnInfoListener(listener)
                    setOnSeekCompleteListener(listener)
                    setOnBufferingUpdateListener(listener)
                    setOnErrorListener(listener)
                }
            }
        }
    }

    //提供给外部调用的接口方法
    fun setListener(videoPlayerListene: VideoPlayerListene) {
        listener = videoPlayerListene
        mMediaPlayer?.setOnPreparedListener(listener)
    }


    //控制视频功能的一些方法

    //开始播放
    fun start() {
        mMediaPlayer?.start()
    }

    //释放资源
    fun release() {
        mMediaPlayer?.let {
            it.reset()
            it.release()
            mMediaPlayer = null
        }
    }

    //暂停
    fun pause() {
        mMediaPlayer?.pause()
    }

    //停止
    fun stop() {
        mMediaPlayer?.stop()
    }

    //重置
    fun reset() {
        mMediaPlayer?.reset()
    }

    //获取长度
    fun getDuration(): Long {
        //不为空取正常值，为null，设置默认为0
        return mMediaPlayer?.duration ?: 0
    }

    //获取当前进度
    fun getCurrentPosition(): Long {
        return mMediaPlayer?.currentPosition ?: 0
    }

    //跳转
    fun seekTo(long: Long) {
        mMediaPlayer?.seekTo(long)
    }

}