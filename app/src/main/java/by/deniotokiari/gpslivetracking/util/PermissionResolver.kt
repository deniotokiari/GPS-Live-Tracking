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

        fun permissionDenied(permission: String)

    }

    companion object {

        private const val PERMISSION_REQUEST_CODE = 4242

        const val PERMISSION_EXPLANATION = 0
        const val PERMISSION_REQUEST = 1
        const val PERMISSION_GRANTED = 2
        const val PERMISSION_DENIED = 3

        fun checkPermissionState(activity: Activity, permission: String): Int {
            return if (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED) {
                PERMISSION_GRANTED
            } else if (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_DENIED) {
                PERMISSION_DENIED
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                    PERMISSION_EXPLANATION
                } else {
                    PERMISSION_REQUEST
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
                PERMISSION_EXPLANATION -> callback.showExplanation(permission)
                PERMISSION_REQUEST -> callback.requestPermission(permission)
                PERMISSION_GRANTED -> callback.permissionGranted(permission)
                PERMISSION_DENIED -> callback.permissionDenied(permission)
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