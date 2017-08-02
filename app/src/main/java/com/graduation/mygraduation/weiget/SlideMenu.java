package com.graduation.mygraduation.weiget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.graduation.mygraduation.R;
import com.nineoldandroids.view.ViewHelper;

/**
 * 功能：类似于qq侧滑栏的一个自定义view
 *  但是最后没有用上
 */

public class SlideMenu extends HorizontalScrollView {
    /**
     * 主布局
     */
    private LinearLayout mWarrper;
    /**
     * 左侧展示菜单
     */
    private ViewGroup mMenu;
    /**
     * 右侧展示内容
     */
    private ViewGroup mContent;
    /**
     * 屏幕宽度
     */
    private int mScreenWidth;
    /**
     * 菜单栏的宽度
     */
    private int mMenuWidth;
    /**
     * 侧滑菜单距离右侧的一个padding
     */
    private int mMenuRightPadding = 100;//单位 dp
    /**
     * 判断是一次调用
     */
    private boolean once;
    /**
     * 判断是否打开
     */
    private boolean isOpen;

    /**
     * 未使用自定义属性时 默认调用该view
     *
     * @param context 上下文
     * @param attrs   布局文件中的属性
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public SlideMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getRealMetrics(metrics);
        mScreenWidth = metrics.widthPixels;

        mMenuRightPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 65,
                context.getResources().getDisplayMetrics());
    }

    /**
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!once) {
            mWarrper = (LinearLayout) getChildAt(0);
            mMenu = (ViewGroup) mWarrper.getChildAt(0);
            mContent = (ViewGroup) mWarrper.getChildAt(1);
            mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
            mContent.getLayoutParams().width = mScreenWidth;

        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 通过设置偏移量将menu隐藏
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            this.scrollTo(mMenuWidth, 0);
            once = true;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                if (scrollX >= mMenuWidth / 2) {
                    this.smoothScrollTo(mMenuWidth, 0);
                    isOpen = false;
//                    mContent.setBackgroundResource(R.color.transparent);
                } else {
                    this.smoothScrollTo(0, 0);
                    isOpen = true;
//                    mContent.setBackgroundColor(Color.parseColor("#80999999"));
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        /**
         * 菜单有偏移量以及缩放效果
         *      缩放0.7~1.0
         *      透明度0.6~1.0
         * 右侧内容区有一个透明度变化
         *      透明度1.0~0.7
         *
         */
        float scale = (float) (l * 1.0 / mMenuWidth);
        float leftScale = 1.0f - 0.3f * scale;
        float leftAlpha = 0.6f + 0.4f * (1 - scale);
        float rightAlpha = scale * 0.5f + 0.5f;
//        ViewHelper.setTranslationX(mMenu, mMenuWidth * scale );
//        ViewHelper.setScaleX(mMenu, leftScale);
//        ViewHelper.setScaleY(mMenu, leftScale);
//        ViewHelper.setAlpha(mMenu, leftAlpha);
//        ViewHelper.setAlpha(mContent, rightAlpha);

    }

    /**
     * 打开菜单
     */
    public void openMenu() {
        if (isOpen)
            return;
        this.smoothScrollTo(0, 0);
        isOpen = true;
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        if (isOpen) {
            this.smoothScrollTo(mMenuWidth, 0);
            isOpen = false;
        }
    }

    /**
     * 切换菜单状态
     */
    public void toggle() {
        if (isOpen) {
            closeMenu();
        } else {
            openMenu();
        }
    }

}
