# SLay

[![jitpack](https://jitpack.io/v/7hens/slay.svg)](https://jitpack.io/#7hens/slay)
[![license](https://img.shields.io/github/license/7hens/slay.svg)](https://github.com/7hens/slay/blob/master/LICENSE)

SLay (Scale Layout) 是一种直接缩放布局的屏幕适配方案。

## Quick Start

1) 配置 module 的 build.gradle。

last_version: [![jitpack](https://jitpack.io/v/7hens/slay.svg)](https://jitpack.io/#7hens/slay)

```groovy
dependencies {
    implementation 'com.github.7hens:slay:<last_version>'
}
```

2) 屏幕适配，推荐使用 BaseActivity。

```java
public abstract class BaseActivity extends Activity {
    // 这里推荐将 design 提升为全局变量，并在 Application.onCreate() 中进行初始化。
    // 这里的参数 WIDTH, HEIGHT 代表着设计稿的尺寸，视图会根据这个尺寸进行缩放。
    // 最后的参数表示 WIDTH 和 HEIGHT 所使用的单位（dp,sp,in 等），需要与 xml 中的单位保持一致。
    private SLayDesign design = SLayDesign.create(context, WIDTH, HEIGHT, TypedValue.COMPLEX_UNIT_PX);

    @Override
    public void setContentView(int layoutResID) {
        design.adapter(this).setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view) {
        design.adapter(this).setContentView(view);
    }
}
```

除 Activity 外，SLay 还可以适配 Dialog, 浮窗等。
