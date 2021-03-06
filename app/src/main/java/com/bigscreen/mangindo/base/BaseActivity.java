package com.bigscreen.mangindo.base;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.bigscreen.mangindo.R;
import com.bigscreen.mangindo.deps.AppDeps;

public class BaseActivity extends AppCompatActivity {

    protected Toolbar toolbar;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initToolbar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void showAlert(@Nullable String title, @NonNull String message, @NonNull String buttonText,
                             @Nullable DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(this);
        dlgBuilder.setMessage(message);
        dlgBuilder.setPositiveButton(buttonText, onClickListener);
        if (title != null) dlgBuilder.setTitle(title);
        dlgBuilder.show();
    }

    protected void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    protected void showKeyboard(EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (editText != null) {
            inputMethodManager.showSoftInput(editText, 0);
        }
    }

    protected void setToolbarTitle(String title, boolean showBackButton) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setHomeButtonEnabled(showBackButton);
            getSupportActionBar().setDisplayHomeAsUpEnabled(showBackButton);
        }
    }

    protected void setToolbarSubtitle(String subtitle) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setSubtitle(subtitle);
        }
    }

}
