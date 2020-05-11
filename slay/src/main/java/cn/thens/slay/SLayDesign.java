package cn.thens.slay;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import java.util.concurrent.atomic.AtomicBoolean;


/**
 * @author 7hens
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public final class SLayDesign {
    private static SLayDesign INSTANCE = new SLayDesign();
    private AtomicBoolean isInitialized = new AtomicBoolean(false);
    private SLayUI ui = SLayUI.NO_SCALE;

    public static SLayDesign get() {
        return INSTANCE;
    }

    public void init(Context context, float width, float height, int unit) {
        if (width <= 0 || height <= 0) {
            throw new RuntimeException("both width and height should be greater than zero ");
        }
        if (isInitialized.compareAndSet(false, true)) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            final int widthPx = (int) TypedValue.applyDimension(unit, width, displayMetrics);
            final int heightPx = (int) TypedValue.applyDimension(unit, height, displayMetrics);
            final Resources resources = context.getResources();
            resetUIWithDesign(resources, widthPx, heightPx);
            //noinspection NullableProblems
            context.getApplicationContext().registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    resetUIWithDesign(resources, widthPx, heightPx);
                }

                @Override
                public void onLowMemory() {
                }
            });
        }
    }

    private void resetUIWithDesign(Resources resources, int width, int height) {
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        int orientation = resources.getConfiguration().orientation;
        boolean isVertical = orientation == Configuration.ORIENTATION_PORTRAIT;
        ui = SLayUI.create(1F * displayMetrics.widthPixels / (isVertical ? width : height));
    }

    public Adapter adapter(Activity activity) {
        return adapter(activity.getWindow());
    }

    public Adapter adapter(Dialog dialog) {
        //noinspection ConstantConditions
        return adapter(dialog.getWindow());
    }

    public Adapter adapter(Window window) {
        return adapter((FrameLayout) window.findViewById(android.R.id.content));
    }

    public Adapter adapter(FrameLayout parent) {
        return adapter(parent, parent.getLayoutParams());
    }

    public Adapter adapter(FrameLayout parent, ViewGroup.LayoutParams layoutParams) {
        return new Adapter(parent, layoutParams);
    }

    public class Adapter {
        private final FrameLayout parent;
        private final ViewGroup.LayoutParams layoutParams;

        Adapter(FrameLayout parent, ViewGroup.LayoutParams layoutParams) {
            this.parent = parent;
            this.layoutParams = layoutParams;
        }

        public void setContentView(View view) {
            parent.removeAllViews();
            ui.addScaleChild(parent, layoutParams, view);
        }

        public void setContentView(int layoutRes) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            setContentView(inflater.inflate(layoutRes, parent, false));
        }
    }
}
