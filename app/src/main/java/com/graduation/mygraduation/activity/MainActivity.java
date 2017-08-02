package com.graduation.mygraduation.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.utils.FileUtils;
import com.blankj.utilcode.utils.SDCardUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.graduation.mygraduation.R;
import com.graduation.mygraduation.fragment.MyFragment;
import com.graduation.mygraduation.fragment.NewsFragment;
import com.graduation.mygraduation.fragment.RobotFragment;
import com.graduation.mygraduation.fragment.ToolsFragment;
import com.graduation.mygraduation.fragment.VideoFragment;
import com.graduation.mygraduation.utils.ActivityUtils;
import com.graduation.mygraduation.utils.GlideCircleTransform;
import com.graduation.mygraduation.weiget.SlideMenu;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 主页 不想玩了
 */
public class MainActivity extends FragmentActivity {


    @Bind(R.id.drawer_activity_main)
    DrawerLayout drawerLayout;
    @Bind(R.id.navigation_left)
    NavigationView navigationView;
    @Bind(R.id.frame_content)
    FrameLayout frame_content;
    @Bind(R.id.bottomBar)
    BottomBar bottomBar;

    private NewsFragment newsFragment;      //新闻数据
    private VideoFragment videoFragment;    //视频数据
    private ToolsFragment toolsFragment;    //工具数据
    private MyFragment myFragment;    // 我的界面
    private RobotFragment robotFragment;  //暂时没想好
    private ActivityUtils utils;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

//        Window window = this.getWindow();
//        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//
//        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        //设置状态栏颜色
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window.setStatusBarColor(Color.parseColor("#1296db"));
//        }
//
//        ViewGroup mContentView = (ViewGroup) this.findViewById(Window.ID_ANDROID_CONTENT);
//        View mChildView = mContentView.getChildAt(0);
//        if (mChildView != null) {
//            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
//            ViewCompat.setFitsSystemWindows(mChildView, true);
//        }


        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
        initLeft();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }


    public void init() {


        /*************************** 第一次进入创建newsFragment ***************************/
        utils = new ActivityUtils(this);
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        newsFragment = new NewsFragment();
        transaction.add(R.id.frame_content, newsFragment, "news");
        transaction.commit();

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_home:
                        showNewsDataFragment();
                        closeDrawerLayout();
                        navigationView.setCheckedItem(R.id.nav_news);
                        break;
                    case R.id.tab_video:
                        navigationView.setCheckedItem(R.id.nav_video);
                        showVideoFragment();
                        closeDrawerLayout();
                        break;
                    case R.id.tab_robot:
                        navigationView.setCheckedItem(R.id.nav_robot);
                        showRobotFragment();
                        closeDrawerLayout();
                        break;
                    case R.id.tab_more:
                        navigationView.setCheckedItem(R.id.nav_more);
                        showMoreFragment();
                        closeDrawerLayout();
                        break;
                    case R.id.tab_about:
                        navigationView.setCheckedItem(R.id.nav_my);
                        showAboutFragment();
                        closeDrawerLayout();
                        break;
                }
            }
        });

    }

    /*****************************
     * 左侧侧滑栏的一些设置
     **********************/
    private void initLeft() {
        ImageView img_title = (ImageView) navigationView.getHeaderView(0).
                findViewById(R.id.nav_title_img);
        ImageView img_back = (ImageView) navigationView.getHeaderView(0).
                findViewById(R.id.nav_back);
        Glide.with(this).load(R.drawable.my_img)
//                .asGif()
                .transform(new GlideCircleTransform(MainActivity.this))
                .into(img_title);

        /*************************** 左侧 侧滑菜单 设置选择事件 ***************************/
        navigationView.setCheckedItem(R.id.nav_news);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                navigationView.setCheckedItem(item.getItemId());
                drawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.nav_news:
                        bottomBar.selectTabAtPosition(0, true);
                        break;
                    case R.id.nav_video:
                        bottomBar.selectTabAtPosition(1, true);
                        break;
                    case R.id.nav_robot:
                        bottomBar.selectTabAtPosition(2, true);
                        break;
                    case R.id.nav_more:
                        bottomBar.selectTabAtPosition(3, true);
                        break;
                    case R.id.nav_my:
                        bottomBar.selectTabAtPosition(4, true);
                        break;
                    case R.id.nav_clear_cache:
                        clearCache();
                        break;
                    case R.id.nav_version_update:
                        Toast.makeText(MainActivity.this, "你就这一个版本，不用更新了！", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });

    }


    /**
     * 关闭左侧 侧滑菜单
     */
    private void closeDrawerLayout() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawers();
        }
    }

    /**
     * 隐藏所有的fragment
     */
    private void hideAllFragment() {
        FragmentTransaction hideTransaction = manager.beginTransaction();
        if (newsFragment != null) {
            hideTransaction.hide(newsFragment);
        }
        if (videoFragment != null) {
            hideTransaction.hide(videoFragment);
        }
        if (robotFragment != null) {
            hideTransaction.hide(robotFragment);
        }
        if (myFragment != null) {
            hideTransaction.hide(myFragment);
        }
        if (toolsFragment != null) {
            hideTransaction.hide(toolsFragment);
        }

        hideTransaction.commit();
    }

    /**
     * 展示 新闻数据 Fragment
     */
    private void showNewsDataFragment() {
        hideAllFragment();
        if (newsFragment == null) {
            newsFragment = new NewsFragment();
        } else {
            manager.beginTransaction().show(newsFragment).commit();
        }
    }

    /**
     * 展示 视频播放 Fragment
     */
    private void showVideoFragment() {
        hideAllFragment();
        if (videoFragment == null) {
            videoFragment = new VideoFragment();
            manager.beginTransaction().add(R.id.frame_content, videoFragment).commit();
        } else {
            manager.beginTransaction().show(videoFragment).commit();
        }
    }


    /**
     * 展示 图灵机器人 Fragment
     */
    private void showRobotFragment() {
        hideAllFragment();
        if (robotFragment == null) {
            robotFragment = new RobotFragment();
            manager.beginTransaction().add(R.id.frame_content, robotFragment).commit();
        } else {
            manager.beginTransaction().show(robotFragment).commit();
        }
    }

    /**
     * 展示 更多 Fragment
     */
    private void showMoreFragment() {
        hideAllFragment();
        if (toolsFragment == null) {
            toolsFragment = new ToolsFragment();
            manager.beginTransaction().add(R.id.frame_content, toolsFragment).commit();
        } else {
            manager.beginTransaction().show(toolsFragment).commit();
        }
    }


    /**
     * 展示 我的 Fragment
     */
    private void showAboutFragment() {
        hideAllFragment();
        if (myFragment == null) {
            myFragment = new MyFragment();
            manager.beginTransaction().add(R.id.frame_content, myFragment).commit();
        } else {
            manager.beginTransaction().show(myFragment).commit();
        }
    }

    long lastTime;

    /**
     * 2秒内连续点击 back 键，退出应用
     */
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawers();
            return;
        }
        long curTime = System.currentTimeMillis();
        if ((curTime - lastTime) > 2000) {
            utils.showToast("再按一次退出应用");
            lastTime = curTime;
        } else {
            finish();
        }
    }


    private void clearCache() {
        String dirSize = FileUtils.getDirSize(getCacheDir());
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("确定要清理缓存")
                .setMessage("缓存大小：" + dirSize)
                .setPositiveButton("清理", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FileUtils.deleteDir(getCacheDir());
                        utils.showToast("清理成功");
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }
}
