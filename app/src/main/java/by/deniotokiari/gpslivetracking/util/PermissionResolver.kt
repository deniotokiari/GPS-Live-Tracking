package by.deniotokiari.gpslivetracking.util

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionResolver {

    interface PermissionCallback {

        fun showExplanation(permission: String)

        fun requestPermission(permission: String)

        fun permissionGranted(permission: String)

    }

    companion object {

        private const val PERMISSION_REQUEST_CODE = 4242

        const val SHOW_EXPLANATION = 0
        const val REQUEST_PERMISSION = 1
        const val PERMISSION_GRANTED = 2

        fun checkPermissionState(activity: Activity, permission: String): Int {
            return if (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED) {
                PERMISSION_GRANTED
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                    SHOW_EXPLANATION
                } else {
                    REQUEST_PERMISSION
                }
            }
        }

        fun checkPermissionState(activity: Activity, permission: String, callback: (Int) -> Unit) {
            val result = checkPermissionState(activity, permission)

            callback.invoke(result)
        }

        fun checkPermissionState(activity: Activity, permission: String, callback: PermissionCallback) {
            val result = checkPermissionState(activity, permission)

            when (result) {
                SHOW_EXPLANATION -> callback.showExplanation(permission)
                REQUEST_PERMISSION -> callback.requestPermission(permission)
                PERMISSION_GRANTED -> callback.permissionGranted(permission)
            }
        }

        fun requestPermissions(activity: Activity, permissions: Array<String>) {
            ActivityCompat.requestPermissions(activity, permissions, PERMISSION_REQUEST_CODE)
        }

        fun handlePermissionRequest(requestCode: Int, permissions: Array<String>, grantResults: IntArray): ArrayList<String> {
            val result: ArrayList<String> = ArrayList()

            if (requestCode == PERMISSION_REQUEST_CODE) {
                permissions.forEachIndexed { index, permission ->
                    if (grantResults[index] == PackageManager.PERMISSION_GRANTED) {
                        result.add(permission)
                    }
                }
            }

            return result
        }

    }

}