package cn.thens.slay;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * @author 7hens
 */
@SuppressWarnings({"WeakerAccess", "UnusedReturnValue", "unused"})
public class SLayParams<LP extends ViewGroup.MarginLayoutParams> {
    public static final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;
    public static final int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;

    private final Rect padding = new Rect();
    protected final SLayUI ui;
    protected final LP lp;

    protected SLayParams(SLayUI ui, LP layoutParams) {
        this.ui = ui;
        this.lp = layoutParams;
        lp.width = WRAP_CONTENT;
        lp.height = WRAP_CONTENT;
    }

    public <V extends View> V bind(V view) {
        view.setPadding(padding.left, padding.top, padding.right, padding.bottom);
        view.setLayoutParams(lp);
        return view;
    }

    public SLayParams<LP> width(int value) {
        lp.width = value > 0 ? ui.scaleX(value) : value;
        return this;
    }

    public SLayParams<LP> height(int value) {
        lp.height = value > 0 ? ui.scaleY(value) : value;
        return this;
    }

    public SLayParams<LP> size(int width, int height) {
        return width(width).height(height);
    }

    public SLayParams<LP> size(int value) {
        return size(value, value);
    }

    public SLayParams paddingLeft(int value) {
        padding.left = ui.scaleX(value);
        return this;
    }

    public SLayParams paddingRight(int value) {
        padding.right = ui.scaleX(value);
        return this;
    }

    public SLayParams paddingTop(int value) {
        padding.top = ui.scaleY(value);
        return this;
    }

    public SLayParams paddingBottom(int value) {
        padding.bottom = ui.scaleY(value);
        return this;
    }

    public SLayParams paddingHorizontal(int horizontal) {
        return paddingLeft(horizontal).paddingRight(horizontal);
    }

    public SLayParams paddingVertical(int vertical) {
        return paddingTop(vertical).paddingBottom(vertical);
    }

    public SLayParams padding(int left, int top, int right, int bottom) {
        return paddingLeft(left)
                .paddingTop(top)
                .paddingRight(right)
                .paddingBottom(bottom);
    }

    public SLayParams padding(int horizontal, int vertical) {
        return paddingHorizontal(horizontal).paddingVertical(vertical);
    }

    public SLayParams padding(int padding) {
        return padding(padding, padding);
    }

    public SLayParams marginLeft(int value) {
        lp.leftMargin = ui.scaleX(value);
        return this;
    }

    public SLayParams marginRight(int value) {
        lp.rightMargin = ui.scaleX(value);
        return this;
    }

    public SLayParams marginTop(int value) {
        lp.topMargin = ui.scaleY(value);
        return this;
    }

    public SLayParams marginBottom(int value) {
        lp.bottomMargin = ui.scaleY(value);
        return this;
    }

    public SLayParams marginHorizontal(int margin) {
        return marginLeft(margin).marginRight(margin);
    }

    public SLayParams marginVertical(int margin) {
        return marginTop(margin).marginBottom(margin);
    }

    public SLayParams margin(int left, int top, int right, int bottom) {
        return marginLeft(left)
                .marginTop(top)
                .marginRight(right)
                .marginBottom(bottom);
    }

    public SLayParams margin(int horizontal, int vertical) {
        return marginHorizontal(horizontal).marginVertical(vertical);
    }

    public SLayParams margin(int margin) {
        return margin(margin, margin);
    }

    public static class Group extends SLayParams<ViewGroup.MarginLayoutParams> {
        public Group(SLayUI ui2) {
            super(ui2, new ViewGroup.MarginLayoutParams(0, 0));
        }
    }

    public static class Linear extends SLayParams<LinearLayout.LayoutParams> {
        public Linear(SLayUI ui2) {
            super(ui2, new LinearLayout.LayoutParams(0, 0));
        }

        public Linear weight(int weight) {
            lp.weight = weight;
            return this;
        }

        public Linear gravity(int gravity) {
            lp.gravity = gravity;
            return this;
        }
    }

    public static class Relative extends SLayParams<RelativeLayout.LayoutParams> {
        public Relative(SLayUI ui2) {
            super(ui2, new RelativeLayout.LayoutParams(0, 0));
        }

        public Relative rule(int verb) {
            lp.addRule(verb);
            return this;
        }

        public Relative rule(int verb, int subject) {
            lp.addRule(verb, subject);
            return this;
        }
    }

    public static class Frame extends SLayParams<FrameLayout.LayoutParams> {
        public Frame(SLayUI ui2) {
            super(ui2, new FrameLayout.LayoutParams(0, 0));
        }

        public Frame gravity(int gravity) {
            lp.gravity = gravity;
            return this;
        }
    }
}
