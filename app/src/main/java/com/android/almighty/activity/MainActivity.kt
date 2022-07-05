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
            statusBarColor(R.color.theme_color)
            navigationBarColor(R.color.theme_color)
        }
        navController = findNavController(R.id.nav_host_fragment)
        slider.setupWithNavController(navController)
    }

    fun openDrawerLayout(){
        slider.drawerLayout?.let { drawerLayout->
            if(!drawerLayout.isOpen){
                drawerLayout.open()
            }
        }
    }
}