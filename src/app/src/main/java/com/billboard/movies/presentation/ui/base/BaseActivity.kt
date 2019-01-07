package com.billboard.movies.presentation.ui.base

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.billboard.movies.R

abstract class BaseActivity : AppCompatActivity() {

    fun setupToolbar(toolbar: Toolbar, titleId: Int) {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = resources.getString(titleId)
        }
    }

    fun addBackButton(toolbar: Toolbar) {
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

    }

}