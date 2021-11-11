package com.codeex.task.base.navigator.impl

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.codeex.task.base.navigator.Navigator
import com.codeex.task.base.navigator.screens.Screens

import javax.inject.Inject

class NavigatorImpl @Inject constructor(private val activity: FragmentActivity) : Navigator {

    // transactions container id
    private var containerId: Int = 0

    /**
     * Set container for fragment transactions
     */
    override fun setContainer(containerId: Int) {
        this.containerId = containerId
    }

    /**
     * go to previous screen
     */
    override fun goBack(recreate: Boolean, bundle: Bundle?) {

        val previousScreen = getPreviousScreen()

        if (previousScreen == null) {
            ScreensStack.removeAllScreens()
            activity.finish()
            return
        }

        val currentScreen = getCurrentScreen()

        val currentFragmentTag = currentScreen!!.fragmentClass.name
        val previousFragmentTag = previousScreen.fragmentClass.name

        ScreensStack.removeLast()

        // if fragment exists in backs stack and we need show it only without recreation
        if (!recreate && isFragmentExistsInBackStack(previousFragmentTag)) {
            // Replace fragment
            replaceFragments(currentFragmentTag, previousFragmentTag, bundle)
            // Begin remove fragment transaction
            removeFragment(currentFragmentTag)
            return
        }

        removeFragment(previousFragmentTag)
        hideFragment(currentFragmentTag)
        val fragment = getFragmentByScreen(previousScreen)
        beginFragmentTransaction(containerId, fragment, bundle)
    }

    /**
     * navigate to screen in set
     */
    override fun navigateTo(
        screen: Screens,
        bundle: Bundle?,
        recreate: Boolean,
        removeCurrentScreen: Boolean
    ) {

        val fragmentExists = ScreensStack.isScreenExists(screen)
        val currentScreen = getCurrentScreen()

        // if fragment not exists in back stack
        if (!fragmentExists) {

            val fragment = getFragmentByScreen(screen)

            if (currentScreen != null) {

                val currentScreenTag = currentScreen.fragmentClass.name
                replaceFragments(currentScreenTag, fragment, bundle)

                // if need remove current screen from history and back stack
                if (removeCurrentScreen) {
                    removeFragment(currentScreenTag)
                    ScreensStack.removeLast()
                }

            } else {

                beginFragmentTransaction(containerId, fragment, bundle)
            }

            ScreensStack.addScreen(screen)
            return
        }

        // if current fragment is already is showed and not need recreate
        if (screen == currentScreen && !recreate) return

        val currentFragmentTag = currentScreen?.fragmentClass?.name
        val newFragmentTag = screen.fragmentClass.name

        if (!recreate) {
            replaceFragments(currentFragmentTag, newFragmentTag, bundle)
        } else {
            val fragment = getFragmentByScreen(screen)
            replaceFragments(currentFragmentTag, fragment, bundle)
        }

        if (removeCurrentScreen) {
            removeFragment(currentFragmentTag)
            ScreensStack.removeLast()
        }

        ScreensStack.addScreen(screen)
    }


    /**
     * get current fragment instance
     */
    private fun getCurrentScreenFragment(): Fragment? {

        val currentScreen = getCurrentScreen() ?: return null

        val currentFragment: Fragment? =
            activity.supportFragmentManager.findFragmentByTag(currentScreen.fragmentClass.name)

        if (currentFragment == null || currentFragment.isHidden) {
            return null
        }

        return currentFragment
    }

    /**
     * get current screen
     */
    private fun getCurrentScreen(): Screens? {

        val currentScreenShowedFragmentTag = getCurrentShowedFragmentTag()
        return checkCurrentScreenTag(currentScreenShowedFragmentTag)
    }

    private fun checkCurrentScreenTag(tag: String?): Screens? {

        val currentScreen: Screens? = ScreensStack.getScreenByFragmentClass(tag)

        if (currentScreen == null && tag != null) {
            hideFragment(tag)
        }

        return currentScreen
    }

    private fun replaceFragments(replaceTag: String?, replaceWith: String, bundle: Bundle?) {

        hideFragment(replaceTag, bundle) {
            showFragment(replaceWith, bundle)
        }
    }

    private fun replaceFragments(replaceTag: String?, fragment: Fragment, bundle: Bundle?) {

        hideFragment(replaceTag, bundle) {
            beginFragmentTransaction(containerId, fragment, bundle)
        }
    }

    /**
     * get privies screen tag from history
     */
    private fun getPreviousScreen(): Screens? {
        val currentScreen = getCurrentScreen() ?: return null
        return ScreensStack.getPreviousScreen(currentScreen)
    }

    /**
     * check is fragment exists in back stack
     */
    private fun isFragmentExistsInBackStack(tag: String): Boolean {

        activity.supportFragmentManager.fragments.forEach {
            if (it::class.java.name == tag) {
                return true
            }
        }

        return false
    }

    /**
     * get current showed screen
     */
    private fun getCurrentShowedFragmentTag(): String? {

        activity.supportFragmentManager.fragments.forEach {
            if (!it.isHidden) {
                return it.tag
            }
        }
        return null
    }

    /**
     * Begin add fragment transaction
     */
    private fun beginFragmentTransaction(
        container: Int,
        fragment: Fragment,
        bundle: Bundle? = null,
        hide: Boolean = false
    ) {

        val fragmentTag = fragment::class.java.name

        try {
            fragment.arguments = bundle

            val transaction = activity.supportFragmentManager.beginTransaction()
                .add(container, fragment, fragmentTag)
                .addToBackStack(fragmentTag)

            // Add and hide if need
            if (hide) {
                transaction.hide(fragment)
            }

            transaction.commit()

        } catch (error: Exception) {
            println()
            // TODO important log
        }
    }

    /**
     * Begin remove fragment transaction
     */
    private fun removeFragment(fragmentTag: String?) {

        if (fragmentTag == null) return

        val fragment = activity.supportFragmentManager.findFragmentByTag(fragmentTag)

        fragment?.let {
            if (!fragment.isHidden) {
                activity.supportFragmentManager
                    .beginTransaction()
                    .remove(fragment)
                    .commit()
            }
        }
    }

    /**
     * Begin hide fragment transaction
     */
    private fun hideFragment(
        tag: String?,
        data: Any? = null,
        receiveHideNotification: Boolean = true,
        runOnCommit: (() -> Unit)? = null,
    ) {

        if (tag == null) {
            runOnCommit?.invoke()
            return
        }

        val fragment = activity.supportFragmentManager.findFragmentByTag(tag) ?: return

        activity.supportFragmentManager
            .beginTransaction()
            .hide(fragment)
            .runOnCommit {
                runOnCommit?.invoke()
            }
            .commit()
    }

    /**
     * Begin show fragment transaction
     */
    private fun showFragment(fragmentTag: String, data: Any?) {

        val fragment = activity.supportFragmentManager.findFragmentByTag(fragmentTag) ?: return

        activity.supportFragmentManager
            .beginTransaction()
            .show(fragment)
            .commit()
    }

    /**
     * Create fragment instance depending screen
     */
    private fun getFragmentByScreen(screen: Screens): Fragment {
        return screen.fragmentClass.getConstructor().newInstance()
    }
}