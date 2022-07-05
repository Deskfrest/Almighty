package com.android.pdfviewerlib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.pdfviewerlib.core.listener.OnLoadCompleteListener
import com.android.pdfviewerlib.core.listener.OnPageChangeListener
import com.android.pdfviewerlib.core.listener.OnPageErrorListener
import com.android.pdfviewerlib.core.scroll.DefaultScrollHandle
import com.android.pdfviewerlib.tree.TreeNodeData
import com.shockwave.pdfium.PdfDocument
import kotlinx.android.synthetic.main.activity_pdf_viewer.*
import java.io.File
import java.io.Serializable

const val PARAM_PATH = "path"
const val PARAM_NAME = "name"

/**
 * PDF预览界面
 */
class PdfViewerActivity : AppCompatActivity(), OnPageChangeListener,
    OnLoadCompleteListener,
    OnPageErrorListener {

    //页码
    private var pageNumber = 0

    //PDF目录集合
    private var catelogues : ArrayList<TreeNodeData>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_viewer)
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
        //目录按钮
        btn_catalogue.setOnClickListener {
            val intent = Intent(this, PdfCatalogueActivity::class.java)
            intent.putExtra("catelogues", catelogues as Serializable)
            startActivityForResult(intent, 200)
        }
        //预览按钮
        btn_preview.setOnClickListener {
            val intent = Intent(this, PdfThumbnailActivity::class.java)
            intent.putExtra(PARAM_PATH,path)
            intent.putExtra(PARAM_NAME,name)
            startActivityForResult(intent, 201)
        }
        //加载pdf
        pdfView.fromFile(File(path))
            .defaultPage(pageNumber)
            .onPageChange(this)
            .enableAnnotationRendering(true)
            .onLoad(this)
            .scrollHandle(DefaultScrollHandle(this))
            .spacing(10) // 单位 dp
            .onPageError(this)
            .load();

    }

    override fun loadComplete(nbPages: Int) {
        //获得文档书签信息
        val bookmarks = pdfView.tableOfContents
        if (catelogues != null) {
            catelogues!!.clear()
        } else {
            catelogues = arrayListOf()
        }
        //将bookmark转为目录数据集合
        bookmarkToCatelogues(catelogues!!, bookmarks, 1);
    }

    override fun onPageChanged(page: Int, pageCount: Int) {
        pageNumber = page
    }

    override fun onPageError(page: Int, t: Throwable?) {

    }

    /**
     * 将bookmark转为目录数据集合（递归）
     *
     * @param catelogues 目录数据集合
     * @param bookmarks  书签数据
     * @param level      目录树级别（用于控制树节点位置偏移）
     */
    private fun bookmarkToCatelogues(catelogues : ArrayList<TreeNodeData>, bookmarks : List<PdfDocument.Bookmark>, level : Int ) {
        for(index in bookmarks.indices){
            val nodeData = TreeNodeData()
            val bookMark = bookmarks[index]
            nodeData.name = bookMark.title
            nodeData.pageNum = bookMark.pageIdx.toInt()
            nodeData.treeLevel = level
            nodeData.isExpanded = false
            catelogues.add(nodeData)
            if(bookMark.children != null && bookMark.children.size > 0){
                val treeNodeDatas = arrayListOf<TreeNodeData>()
                nodeData.subset = treeNodeDatas
                bookmarkToCatelogues(treeNodeDatas, bookMark.children, level + 1)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val pageNum = data?.getIntExtra("pageNum", 0) ?: 0
            if (pageNum > 0) {
                pdfView.jumpTo(pageNum)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //释放内存
        pdfView?.recycle()
    }
}