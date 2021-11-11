package com.codeex.task.base.fragments

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.codeex.task.base.activities.BaseActivity
import com.codeex.task.base.navigator.screens.Screens
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment(@LayoutRes layout: Int) : Fragment(layout) {

    fun openFragment(
        screen: Screens,
        bundle: Bundle? = null,
        recreate: Boolean = false,
        removeCurrentScreen: Boolean = false
    ) {
        if (activity == null) return

        (activity as BaseActivity).openFragment(screen, bundle, recreate, removeCurrentScreen)
    }

    fun goToBack(
        recreate: Boolean = false, bundle: Bundle? = null
    ) {
        if (activity == null) return
        (activity as BaseActivity).navigator.goBack(recreate, bundle)
    }
}