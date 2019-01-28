package com.bigscreen.mangindo.base

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bigscreen.mangindo.R

open class BaseActivity : AppCompatActivity() {

    protected var toolbar: Toolbar? = null
    protected val appDeps by lazy { (application as MangindoApplication).getAppDeps() }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        initToolbar()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initToolbar() {
        toolbar = findViewById(R.id.toolbar)
        toolbar?.let { setSupportActionBar(it) }
    }

}
