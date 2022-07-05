package com.android.almighty.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.almighty.R
import com.android.almighty.activity.MainActivity
import com.android.almighty.adapter.ResourceListAdapter
import com.android.almighty.data.model.ResourceModel
import com.android.almighty.databinding.FragmentResourceBinding
import com.android.almighty.util.FileUtil
import com.android.almighty.viewmodel.ResourceViewModel
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.fragment_resource.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

//文件列表
class ResourceFragment : Fragment() {
    private lateinit var binding : FragmentResourceBinding
    private lateinit var viewModel : ResourceViewModel
    private val mAdapter by lazy { ResourceListAdapter() }
    //根目录
    private var rootPath = ""
    //每次进入文件夹或返回上一级目录时，会在该List中进行相应的增减，用于在返回上一级目录时，读取正确的绝对路径
    private var pathList = arrayListOf<String>()


    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_resource, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ImmersionBar.with(this).apply {
            statusBarColor(R.color.theme_color)
            navigationBarColor(R.color.theme_color)
        }
        //onBackPressed的替代方案，用于在Fragment中处理返回键事件
        (activity as FragmentActivity).onBackPressedDispatcher.addCallback(this.viewLifecycleOwner, mBackDispatcher)
        //配置Recyclerview相关属性
        with(recycler_view) {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val resourceModel = (adapter.data as MutableList<ResourceModel>)[position]
            //判断当前点击的item是否是文件
            if(resourceModel.isFile){
                //如果是文件，跳转至对应的组件
            }else{
                //如果是文件夹，将该文件夹的路径添加至路径列表中，在返回上一级目录时，通过该路径获取文件列表
                //如果是文件夹，获取该文件夹下的所有文件，并填充至recyclerview
                pathList.add(resourceModel.absolutePath)
                viewModel.getResourceList((adapter.data as MutableList<ResourceModel>)[position].absolutePath)
            }
        }
        viewModel = ViewModelProvider(this).get(ResourceViewModel::class.java)
        viewModel.resourceList.observe(viewLifecycleOwner, Observer {
            mAdapter.setNewInstance(it as MutableList<ResourceModel>)
        })
        //获取根目录并加载根目录下的文件列表
        context?.apply {
            rootPath = FileUtil.getPath(this)
            pathList.add(rootPath)
            viewModel.getResourceList(rootPath)
        }
        //设置toolbar的点击事件
        toolbar.setNavigationOnClickListener {
            (requireActivity() as MainActivity).openDrawerLayout()
        }
    }

    //onBackPressed的替代方案，用于在Fragment中处理返回键事件
    private val mBackDispatcher = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            //如果路径列表中还有2个或两个以上的路径，则代表当前不是根目录，可以进行返回上一级目录的操作
            if(pathList.size > 1){
                //先将当前路径移除，再读取路径列表的最后一个路径的文件列表
                pathList.removeAt(pathList.size - 1)
                viewModel.getResourceList(pathList[pathList.size -1])
            }else{
                Toast.makeText(requireContext(), "已经是最顶层了", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResourceFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}