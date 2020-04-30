package cn.thens.slay.sample;

import android.app.Application;
import android.util.TypedValue;

import cn.thens.slay.SLayDesign;

/**
 * @author 7hens
 */
public class App extends Application {
    private static final double SCALE = 2;
    private static final int WIDTH = (int) (1024 * SCALE);
    private static final int HEIGHT = (int) (512 * SCALE);

    @Override
    public void onCreate() {
        super.onCreate();
        SLayDesign.get().init(this, WIDTH, HEIGHT, TypedValue.COMPLEX_UNIT_PX);
    }
}
