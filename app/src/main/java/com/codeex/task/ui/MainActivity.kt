package com.codeex.task.ui

import android.os.Bundle
import com.codeex.task.R
import com.codeex.task.base.activities.BaseActivity
import com.codeex.task.base.navigator.screens.Screens
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Setting container id to Navigator
        navigator.setContainer(R.id.fragments_container)
        if (savedInstanceState == null) {
            // Opening Movies List fragment
            openFragment(Screens.MOVIES_LIST)
        }
    }
}