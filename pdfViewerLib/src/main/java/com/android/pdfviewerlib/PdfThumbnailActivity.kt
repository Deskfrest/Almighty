package com.android.pdfviewerlib

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.android.pdfviewerlib.preview.GridAdapter
import com.android.pdfviewerlib.preview.PreviewUtils
import com.shockwave.pdfium.PdfDocument
import com.shockwave.pdfium.PdfiumCore
import kotlinx.android.synthetic.main.activity_pdf_thumbnail.*
import java.io.File

/**
 * PDF缩略图查看界面
 */
class PdfThumbnailActivity : AppCompatActivity() {

    private var pdfiumCore : PdfiumCore? = null
    private var pdfDocument : PdfDocument? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_thumbnail)
        //文件路径
        val path = intent.getStringExtra(PARAM_PATH) ?: ""
        //文件名
        val name = intent.getStringExtra(PARAM_NAME)
        toolbar.apply {
            title = name
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener {
                //回收内存
                recycleMemory()
                finish()
            }
        }
        //加载pdf文件
        pdfiumCore = PdfiumCore(this)
        pdfDocument = pdfiumCore!!.newDocument(contentResolver.openFileDescriptor(Uri.fromFile(File(path)), "r"))
        //获得pdf总页数
        val totalCount = pdfiumCore!!.getPageCount(pdfDocument)

        //绑定列表数据
        val adapter = GridAdapter(this, pdfiumCore, pdfDocument, name, totalCount)
        adapter.setGridEvent {position ->
            //回收内存
            recycleMemory()

            //返回前一个页码
            val intent = Intent()
            intent.putExtra("pageNum", position)
            setResult(Activity.RESULT_OK, intent)
            finish();
        }
        recycler_view.layoutManager = GridLayoutManager(this, 3)
        recycler_view.adapter = adapter
    }

    /**
     * 回收内存
     */
    private fun recycleMemory(){
        //关闭pdf对象
        if (pdfiumCore != null && pdfDocument != null) {
            pdfiumCore!!.closeDocument(pdfDocument)
            pdfiumCore = null;
        }
        //清空图片缓存，释放内存空间
        PreviewUtils.getInstance().imageCache.clearCache()
    }
}