package by.deniotokiari.gpslivetracking.extension

import android.app.Activity
import by.deniotokiari.gpslivetracking.AppApplication
import org.kodein.di.Kodein

var Activity.kodein: Kodein
    get() = AppApplication.kodein
    set(_) {}