package cn.thens.slay.sample;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author 7hens
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    public void setContentView(int layoutResID) {
        AppSLayDesign.get().adapter(this).setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view) {
        AppSLayDesign.get().adapter(this).setContentView(view);
    }
}
