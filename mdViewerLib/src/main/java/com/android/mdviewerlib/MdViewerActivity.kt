package com.android.mdviewerlib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.noties.markwon.Markwon
import io.noties.markwon.ext.tables.TablePlugin
import io.noties.markwon.image.ImagesPlugin
import kotlinx.android.synthetic.main.activity_md_viewer.*

const val PARAM_PATH = "path"
const val PARAM_NAME = "name"

/**
 * markDown文件查看界面
 */
class MdViewerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_md_viewer)
        //文件路径
        val path = intent.getStringExtra(PARAM_PATH) ?: ""
        //文件名
        val name = intent.getStringExtra(PARAM_NAME)
        toolbar.apply {
            title = name
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener {
                finish()
            }
        }
        //构造markDown解析对象
        val markWon = Markwon.builder(this)
            .usePlugin(TablePlugin.create(this))    //支持表格
            .usePlugin(ImagesPlugin.create())       //支持图片
            .build()
        markWon.setMarkdown(et_content,TxtUtil.readTxt(path))
    }
}