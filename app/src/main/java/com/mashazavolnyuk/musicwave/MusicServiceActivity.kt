package com.mashazavolnyuk.musicwave

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

import java.util.ArrayList

abstract class MusicServiceActivity : AppCompatActivity() {

    private val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    private var readyToStart = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (checkPermissions()) {
            //TODO
        } else {
            requestPermissions()
        }
    }

    private fun checkPermissions(): Boolean {
        var result: Int
        val listPermissionsNeeded = ArrayList<String>()
        for (p in permissions) {
            result = ContextCompat.checkSelfPermission(this, p)
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p)
            }
        }
        return listPermissionsNeeded.isEmpty()
    }

    protected fun requestPermissions() {
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST) {
            for (i in grantResults.indices) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this@MusicServiceActivity, permissions[i])) {
                        ActivityCompat.requestPermissions(this, arrayOf(permissions[i]), PERMISSION_REQUEST)
                        readyToStart = false
                    } else {
                        showNoStoragePermissionSnackbar()
                        readyToStart = false
                    }
                }
            }//for
            if (readyToStart) {
                //TODO
            }
        }
    }

    fun showNoStoragePermissionSnackbar() {
        Snackbar.make(window.decorView, "Storage permission isn't granted", Snackbar.LENGTH_LONG)
                .setAction("SETTINGS") { v ->
                    openApplicationSettings()
                    Toast.makeText(applicationContext,
                            "Open Permissions and grant the Storage permission",
                            Toast.LENGTH_LONG)
                            .show()
                }.show()
    }

    fun openApplicationSettings() {
        val appSettingsIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:$packageName"))
        startActivityForResult(appSettingsIntent, PERMISSION_REQUEST)
    }

    override fun onActivityReenter(resultCode: Int, data: Intent?) {
        if (resultCode == PERMISSION_REQUEST) {
           //TODO
            return
        }
        super.onActivityReenter(resultCode, data)
    }

    companion object {
        val PERMISSION_REQUEST = 100
    }

    abstract fun makeContent()
}
