package cn.thens.slay.sample;

import android.content.Context;
import android.util.TypedValue;

import cn.thens.slay.SLayDesign;

/**
 * @author 7hens
 */
public final class AppSLayDesign {
    private static final double SCALE = 1;
    private static final int WIDTH = (int) (1024 * SCALE);
    private static final int HEIGHT = (int) (600 * SCALE);
    private static SLayDesign design;

    public static void initialize(Context context) {
        design = SLayDesign.create(context, WIDTH, HEIGHT, TypedValue.COMPLEX_UNIT_PX);
    }

    public static SLayDesign get() {
        if (design == null) {
            throw new NullPointerException("you should initialize first");
        }
        return design;
    }
}
