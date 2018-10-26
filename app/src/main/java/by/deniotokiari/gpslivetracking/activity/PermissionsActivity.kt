package by.deniotokiari.gpslivetracking.activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.deniotokiari.gpslivetracking.util.PermissionResolver

class PermissionsActivity : AppCompatActivity() {

    private val permissionCallback: PermissionResolver.PermissionCallback =
        object : PermissionResolver.PermissionCallback {

            override fun showExplanation(permission: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun requestPermission(permission: String) {
                PermissionResolver.requestPermissions(this@PermissionsActivity, arrayOf(permission))
            }

            override fun permissionGranted(permission: String) {
                handlePermissionResult(permission)
            }

        }
    private var fineLocationResult: Boolean = false
    private var internetResult: Boolean = false
    private var networkStateResult: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        PermissionResolver.checkPermissionState(this, Manifest.permission.ACCESS_FINE_LOCATION, permissionCallback)
        PermissionResolver.checkPermissionState(this, Manifest.permission.INTERNET, permissionCallback)
        PermissionResolver.checkPermissionState(this, Manifest.permission.ACCESS_NETWORK_STATE, permissionCallback)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        PermissionResolver
            .handlePermissionRequest(requestCode, permissions, grantResults)
            .forEach { permission ->
                handlePermissionResult(permission)
            }
    }

    private fun handlePermissionResult(permission: String) {
        when (permission) {
            Manifest.permission.ACCESS_FINE_LOCATION -> fineLocationResult = true
            Manifest.permission.INTERNET -> internetResult = true
            Manifest.permission.ACCESS_NETWORK_STATE -> networkStateResult = true
        }

        if (fineLocationResult && internetResult && networkStateResult) {
            startActivity(Intent(this@PermissionsActivity, MainActivity::class.java))
            finish()
        }
    }

}