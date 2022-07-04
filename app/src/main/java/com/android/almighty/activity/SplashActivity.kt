package com.android.almighty.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.almighty.R

class SplashActivity : AppCompatActivity() {
    companion object{
        private const val REQUEST_CODE_PERMISSION = 1001
    }
    private val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
    private val permissionList = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        for(index in permissions.indices){
            if(ContextCompat.checkSelfPermission(this,permissions[index]) != PackageManager.PERMISSION_GRANTED){
                permissionList.add(permissions[index])
            }
        }
        if(permissionList.size > 0){
            ActivityCompat.requestPermissions(this,permissions, REQUEST_CODE_PERMISSION)
        }else{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}