package com.android.pdfviewerlib

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.pdfviewerlib.tree.TreeAdapter
import com.android.pdfviewerlib.tree.TreeNodeData
import kotlinx.android.synthetic.main.activity_pdf_catalogue.*

/**
 * Pdf目录列表界面
 */
class PdfCatalogueActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_catalogue)
        toolbar.apply {
            title = "目录列表"
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener {
                finish()
            }
        }
        //加载数据
        val catelogues = intent.getSerializableExtra("catelogues") as List<TreeNodeData>
        val adapter = TreeAdapter(this, catelogues)
        adapter.setTreeEvent {treeNodeData ->
            val intent = Intent()
            intent.putExtra("pageNum", treeNodeData.pageNum)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter
    }
}