package by.deniotokiari.gpslivetracking.extension

import android.app.Service
import by.deniotokiari.gpslivetracking.AppApplication
import org.kodein.di.Kodein

var Service.kodein: Kodein
    get() = AppApplication.kodein
    set(_) {}