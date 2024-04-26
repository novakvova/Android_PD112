package com.example.sim;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sim.category.CategoryCreateActivity;
import com.example.sim.utils.CommonUtils;

public class BaseActivity extends AppCompatActivity {

    public BaseActivity() {
        CommonUtils.setContext(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int select = item.getItemId();
        if(select==R.id.m_home) {
            Intent intent = new Intent(BaseActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        if(select==R.id.m_create) {
            Intent intent = new Intent(BaseActivity.this, CategoryCreateActivity.class);
            startActivity(intent);
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }
}