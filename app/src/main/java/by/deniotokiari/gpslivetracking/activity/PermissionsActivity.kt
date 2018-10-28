package by.deniotokiari.gpslivetracking.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import by.deniotokiari.gpslivetracking.R
import by.deniotokiari.gpslivetracking.util.PermissionResolver

class PermissionsActivity : AppCompatActivity() {

    private val permissionsState: HashMap<String, Int> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_permissions)
    }

    override fun onStart() {
        super.onStart()

        packageManager
            .getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
            .requestedPermissions
            .forEach { permission ->
                permissionsState[permission] = PermissionResolver.checkPermissionState(this, permission)
            }

        handlePermissions(permissionsState)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        permissions
            .forEach { permission ->
                permissionsState[permission] = PermissionResolver.checkPermissionState(this, permission)
            }

        handlePermissions(permissionsState)
    }

    private fun handlePermissions(permissionsState: HashMap<String, Int>) {
        fun startAppDetailsActivity() {
            val intent = Intent()

            intent.apply {
                action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                addCategory(Intent.CATEGORY_DEFAULT)
                data = Uri.parse("package:$packageName")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            }

            startActivity(intent)
        }

        fun getPermissionsByStatus(vararg statuses: Int): Array<String> {
            return permissionsState
                .filter { (_, status) -> statuses.contains(status) }
                .map { (permission, _) -> permission }
                .toTypedArray()
        }

        val permissionsToRequest: Array<String> = getPermissionsByStatus(PermissionResolver.PERMISSION_REQUEST)

        if (permissionsToRequest.isEmpty()) {
            val permissionsToExplanation: Array<String> = getPermissionsByStatus(PermissionResolver.PERMISSION_EXPLANATION)

            if (permissionsToExplanation.isEmpty()) {
                val permissionsToDenied: Array<String> = getPermissionsByStatus(PermissionResolver.PERMISSION_DENIED)

                if (permissionsToDenied.isEmpty()) {
                    startActivity(Intent(this, MainActivity::class.java))

                    finish()
                } else {
                    val dialog: AlertDialog.Builder = AlertDialog.Builder(this)

                    dialog.apply {
                        setTitle(R.string.PERMISSION_NEED_TITLE)
                        setMessage(R.string.PERMISSION_NEED_BODY)
                        setPositiveButton(R.string.BUTTON_APP_SETTINGS) { dlg, _ ->
                            startAppDetailsActivity()

                            dlg.dismiss()
                        }
                        setNegativeButton(R.string.BUTTON_EXIT) { dlg, _ ->
                            dlg.dismiss()

                            finish()
                        }
                    }

                    dialog
                        .create()
                        .show()
                }
            } else {
                PermissionResolver.requestPermissions(this, permissionsToRequest)
            }
        } else {
            PermissionResolver.requestPermissions(this, permissionsToRequest)
        }
    }

}