package by.deniotokiari.gpslivetracking.extension

import android.support.v4.app.Fragment
import by.deniotokiari.gpslivetracking.AppApplication
import org.kodein.di.Kodein

var Fragment.kodein: Kodein
    get() = AppApplication.kodein
    set(_) {}