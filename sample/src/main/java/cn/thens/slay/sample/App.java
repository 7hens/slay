package cn.thens.slay.sample;

import android.app.Application;

/**
 * @author 7hens
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppSLayDesign.initialize(this);
    }
}
