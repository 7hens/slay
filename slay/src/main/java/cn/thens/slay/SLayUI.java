package cn.thens.slay;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.Arrays;

/**
 * @author 7hens
 */
@SuppressWarnings({"UnusedReturnValue", "WeakerAccess", "unused"})
public final class SLayUI {
    private static final String TAG = "@SLayUI";
    public static final SLayUI NO_SCALE = SLayUI.create(1F);
    private final float scaleX;
    private final float scaleY;

    private SLayUI(float scaleX, float scaleY) {
        if (scaleX <= 0 || scaleY <= 0) {
            throw new IllegalArgumentException("both scaleX and scaleY should be greater than zero");
        }
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    public static SLayUI create(float scaleX, float scaleY) {
        return new SLayUI(scaleX, scaleY);
    }

    public static SLayUI create(float scale) {
        return create(scale, scale);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof SLayUI) {
            SLayUI that = (SLayUI) obj;
            return that.scaleX == scaleX && that.scaleY == scaleY;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new float[]{scaleX, scaleY});
    }

    public View addChild(LinearLayout parent, Builder<SLayParams.Linear> builder) {
        return addChildInternal(parent, builder, new SLayParams.Linear(this));
    }

    public View addChild(RelativeLayout parent, Builder<SLayParams.Relative> builder) {
        return addChildInternal(parent, builder, new SLayParams.Relative(this));
    }

    public View addChild(FrameLayout parent, Builder<SLayParams.Frame> builder) {
        return addChildInternal(parent, builder, new SLayParams.Frame(this));
    }

    public View addChild(ViewGroup parent, Builder<SLayParams.Group> builder) {
        return addChildInternal(parent, builder, new SLayParams.Group(this));
    }

    private <LP extends SLayParams> View addChildInternal(ViewGroup parent, Builder<LP> builder, LP layout) {
        try {
            View child = builder.build(layout);
            if (child != null) {
                layout.bind(child);
                parent.addView(child);
                return child;
            }
        } catch (Throwable e) {
            Log.e(TAG, "could not add child", e);
        }
        return null;
    }

    public void addScaleChild(final FrameLayout parent, final View view) {
        ViewGroup.LayoutParams layoutParams = parent.getLayoutParams();
        if (layoutParams == null) {
            Log.e(TAG, "SLayUI.setContentView: parent's layoutParams could not be null");
            return;
        }
        addScaleChild(parent, layoutParams, view);
    }

    public void addScaleChild(final FrameLayout parent, ViewGroup.LayoutParams parentLP, final View view) {
        if (scaleX == 1F && scaleY == 1F) {
            parent.addView(view);
            return;
        }
        final int matchParent = ViewGroup.LayoutParams.MATCH_PARENT;
        final int wrapContent = ViewGroup.LayoutParams.WRAP_CONTENT;
        int parentWidth = parentLP.width;
        int parentHeight = parentLP.height;
        if (parentWidth == matchParent || parentHeight == matchParent) {
            Utils.onLaidOut(parent, new Runnable() {
                @Override
                public void run() {
                    int width = parent.getMeasuredWidth();
                    int height = parent.getMeasuredHeight();
                    addScaleChild(parent, view, width, height);
                }
            });
        } else if (parentWidth == wrapContent || parentHeight == wrapContent) {
            int unspecifiedSize = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            view.measure(unspecifiedSize, unspecifiedSize);
            int width = view.getMeasuredWidth();
            int height = view.getMeasuredHeight();

            final FrameLayout borderContainer = new FrameLayout(parent.getContext());
            new SLayParams.Frame(this)
                    .gravity(Gravity.CENTER)
                    .size(width, height)
                    .bind(borderContainer);
            addScaleChild(borderContainer, view, width, height);
            parent.addView(borderContainer);
        } else {
            addScaleChild(parent, view, parentWidth, parentHeight);
        }
    }

    private View addScaleChild(FrameLayout parent, View view, int width, int height) {
        final Context context = parent.getContext();
        final FrameLayout scaledContainer = new FrameLayout(context);
        scaledContainer.addView(view);
        scaledContainer.setScaleX(scaleX);
        scaledContainer.setScaleY(scaleY);
        new SLayParams.Frame(create(1 / scaleX, 1 / scaleY))
                .gravity(Gravity.CENTER)
                .width(width)
                .height(height)
                .bind(scaledContainer);
        parent.addView(scaledContainer);
        return scaledContainer;
    }

    private int scale(float origin, float scale) {
        return scale == 1F ? (int) origin : (int) (origin * scale + 0.5f);
    }

    public int scaleX(float value) {
        return scale(value, scaleX);
    }

    public int scaleY(float value) {
        return scale(value, scaleY);
    }

    public interface Builder<L extends SLayParams> {
        View build(L layoutParams);
    }
}
