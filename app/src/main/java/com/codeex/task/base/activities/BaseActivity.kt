package com.codeex.task.base.activities

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.codeex.task.base.navigator.Navigator
import com.codeex.task.base.navigator.screens.Screens
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseActivity(@LayoutRes layout: Int) : AppCompatActivity(layout) {
    @Inject
    lateinit var navigator: Navigator

    fun goTo(activity: Class<out AppCompatActivity>, bundle: Bundle? = null, popStack: Boolean) {

        val intent = Intent(this, activity)

        if (bundle != null)
            intent.putExtra("bundle", bundle)

        if (popStack) {

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }

        startActivity(intent)

        if (popStack)
            finish()
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    fun openFragment(
        screen: Screens,
        bundle: Bundle? = null,
        recreate: Boolean = false,
        removeCurrentScreen: Boolean = false
    ) {

        // Navigate to screen
        navigator.navigateTo(screen, bundle, recreate, removeCurrentScreen)
    }

    fun previousScreen(recreate: Boolean = false, bundle: Bundle? = null) {
        navigator.goBack(recreate, bundle)
    }

    override fun onBackPressed() {
        previousScreen()
    }
}