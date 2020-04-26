package cn.thens.slay;

import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * @author 7hens
 */
final class Utils {
    static void onLaidOut(final View view, final Runnable runnable) {
        boolean isLaidOut = view.getWidth() > 0 && view.getHeight() > 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            isLaidOut = view.isLaidOut();
        }
        if (isLaidOut) {
            runnable.run();
            return;
        }
        view.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                        runnable.run();
                    }
                });
    }

}
