package cn.thens.slay.sample;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import cn.thens.slay.SLayDesign;

/**
 * @author 7hens
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    public void setContentView(int layoutResID) {
        SLayDesign.get().adapter(this).setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view) {
        SLayDesign.get().adapter(this).setContentView(view);
    }
}
