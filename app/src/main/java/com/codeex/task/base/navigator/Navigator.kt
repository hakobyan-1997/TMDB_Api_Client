package com.codeex.task.base.navigator

import android.os.Bundle
import androidx.annotation.IdRes
import com.codeex.task.base.navigator.screens.Screens

interface Navigator {

    /**
     * Set container for fragment transactions
     */
    fun setContainer(@IdRes containerId: Int)

    /**
     * go to previous screen
     */
    fun goBack(recreate: Boolean = false, bundle: Bundle? = null)

    /**
     * navigate to screen in set
     */
    fun navigateTo(
        screen: Screens,
        bundle: Bundle? = null,
        recreate: Boolean = false,
        removeCurrentScreen: Boolean = false,
    )
}