package by.deniotokiari.gpslivetracking.activity

import android.content.Context
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class PermissionsActivityTest {

    @Test
    fun `should have primary dark background color`() {
        val activity: PermissionsActivity = Robolectric.setupActivity(PermissionsActivity::class.java)
    }

}