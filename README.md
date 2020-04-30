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

2) 初始化设计稿尺寸，视图会根据这个尺寸进行缩放。

```java
class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 最后的参数表示设计稿尺寸（WIDTH，HEIGHT）所使用的单位， 需要与 xml 中的尺寸单位保持一致。
        SLayDesign.get().init(this, WIDTH, HEIGHT, TypedValue.COMPLEX_UNIT_PX);
    }
}
```

3) 屏幕适配，推荐在 BaseActivity 中使用。

```java
public abstract class BaseActivity extends Activity {
    @Override
    public void setContentView(int layoutResID) {
        SLayDesign.get().adapter(this).setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view) {
        SLayDesign.get().adapter(this).setContentView(view);
    }
}
```

除 Activity 外，SLay 还可以适配 Dialog, 浮窗等。
