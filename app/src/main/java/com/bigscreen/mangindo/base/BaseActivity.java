package com.bigscreen.mangindo.base;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.bigscreen.mangindo.R;
import com.bigscreen.mangindo.deps.AppDeps;

public class BaseActivity extends AppCompatActivity {

    protected Toolbar toolbar;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initToolbar();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    protected AppDeps getAppDeps() {
        return ((MangindoApplication) getApplication()).getAppDeps();
    }

}
