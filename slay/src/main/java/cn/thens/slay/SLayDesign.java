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


/**
 * @author 7hens
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public final class SLayDesign {
    private SLayUI ui = SLayUI.NO_SCALE;

    private SLayDesign() {
    }

    public static SLayDesign create(Context context, float width, float height, int unit) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int widthPx = (int) TypedValue.applyDimension(unit, width, displayMetrics);
        int heightPx = (int) TypedValue.applyDimension(unit, height, displayMetrics);
        return create(context, widthPx, heightPx);
    }

    private static SLayDesign create(Context context, final int width, final int height) {
        final SLayDesign instance = new SLayDesign();
        if (width <= 0 || height <= 0) {
            throw new RuntimeException("both width and height should be greater than zero ");
        }
        final Resources resources = context.getResources();
        instance.resetUIWithDesign(resources, width, height);
        context.getApplicationContext().registerComponentCallbacks(new ComponentCallbacks() {
            @Override
            public void onConfigurationChanged(Configuration newConfig) {
                instance.resetUIWithDesign(resources, width, height);
            }

            @Override
            public void onLowMemory() {
            }
        });
        return instance;
    }

    private void resetUIWithDesign(Resources resources, int width, int height) {
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        int orientation = resources.getConfiguration().orientation;
        boolean isVertical = orientation == Configuration.ORIENTATION_PORTRAIT;
        ui = SLayUI.create(isVertical
                ? 1F * displayMetrics.widthPixels / width
                : 1F * displayMetrics.heightPixels / height);
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
        return new Adapter(ui, parent, layoutParams);
    }

    public static class Adapter {
        private final SLayUI ui;
        private final FrameLayout parent;
        private final ViewGroup.LayoutParams layoutParams;

        Adapter(SLayUI ui, FrameLayout parent, ViewGroup.LayoutParams layoutParams) {
            this.ui = ui;
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
