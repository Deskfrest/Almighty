package com.android.almighty.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.android.almighty.R
import com.gyf.immersionbar.ImmersionBar
import com.mikepenz.materialdrawer.util.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ImmersionBar.with(this).apply {
            statusBarColor(R.color.purple_200)
            navigationBarColor(R.color.purple_200)
        }
        navController = findNavController(R.id.nav_host_fragment)
        slider.setupWithNavController(navController)
    }
}