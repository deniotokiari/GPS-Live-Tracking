package by.deniotokiari.gpslivetracking.extension

import androidx.fragment.app.Fragment
import by.deniotokiari.gpslivetracking.AppApplication
import com.google.android.gms.maps.SupportMapFragment
import org.kodein.di.Kodein

var Fragment.kodein: Kodein
    get() = AppApplication.kodein
    set(_) {}

var SupportMapFragment.kodein: Kodein
    get() = by.deniotokiari.gpslivetracking.AppApplication.kodein
    set(_) {}