package com.zxxapp.mall.maintenance;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.http.HttpUtils;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.zxxapp.mall.maintenance.app.BaseApplication;
import com.zxxapp.mall.maintenance.app.ConstantsImageUrl;
import com.zxxapp.mall.maintenance.base.NoScrollHorizontalViewPager;
import com.zxxapp.mall.maintenance.bean.account.LoginResult;
import com.zxxapp.mall.maintenance.bean.account.User;
import com.zxxapp.mall.maintenance.databinding.ActivityMainBinding;
import com.zxxapp.mall.maintenance.databinding.NavHeaderMainBinding;
import com.zxxapp.mall.maintenance.helper.account.AccountHelper;
import com.zxxapp.mall.maintenance.http.HttpClient;
import com.zxxapp.mall.maintenance.http.rx.RxBus;
import com.zxxapp.mall.maintenance.http.rx.RxBusBaseMessage;
import com.zxxapp.mall.maintenance.http.rx.RxCodeConstants;
import com.zxxapp.mall.maintenance.ui.book.BookFragment;
import com.zxxapp.mall.maintenance.ui.gank.GankFragment;
import com.zxxapp.mall.maintenance.ui.gank.child.GoodsDetailActivity;
import com.zxxapp.mall.maintenance.ui.menu.NavDownloadActivity;
import com.zxxapp.mall.maintenance.ui.mine.MineActivity;
import com.zxxapp.mall.maintenance.ui.mine.MineFragment;
import com.zxxapp.mall.maintenance.ui.mine.child.LoginActivity;
import com.zxxapp.mall.maintenance.ui.shopping.ShoppingCartFragment;
import com.zxxapp.mall.maintenance.utils.CommonUtils;
import com.zxxapp.mall.maintenance.utils.ImgLoadUtil;
import com.zxxapp.mall.maintenance.utils.PerfectClickListener;
import com.zxxapp.mall.maintenance.utils.SPUtils;
import com.zxxapp.mall.maintenance.utils.SharedPreferencesHelper;
import com.zxxapp.mall.maintenance.utils.StringUtils;
import com.zxxapp.mall.maintenance.utils.TimeUtil;
import com.zxxapp.mall.maintenance.utils.ToastUtil;
import com.zxxapp.mall.maintenance.view.MyFragmentPagerAdapter;
import com.zxxapp.mall.maintenance.view.statusbar.StatusBarUtil;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zxxapp.mall.maintenance.view.webview.WebViewActivity;

import java.util.ArrayList;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by jingbin on 16/11/21.
 * Link to:https://github.com/youlookwhat/CloudReader
 * Contact me:http://www.jianshu.com/u/e43c6e979831
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {


    private static final int SCAN_QR_REQUEST = 103;

    //private FrameLayout llTitleMenu;
    private Toolbar toolbar;
    //private FloatingActionButton fab;
    private NavigationView navView;
    private DrawerLayout drawerLayout;
    private NoScrollHorizontalViewPager vpContent;

    // 一定需要对应的bean
    private ActivityMainBinding mBinding;
    private ImageView llTitleGank;
    private ImageView llTitleShoppingCart;
    private ImageView llTitleMine;

    private static MainActivity mainActivity;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainActivity = this;
        initStatusView();
        initId();
        initRxBus();

        StatusBarUtil.setColorNoTranslucentForDrawerLayout(MainActivity.this, drawerLayout,
                CommonUtils.getColor(R.color.colorImmersionStatusBar));
        initContentFragment();
        //initDrawerLayout();
        initListener();
        //initAccount();
    }

    public static MainActivity getMainActivity(){
        return mainActivity;
    }
    private  void  initAccount(){
        User user = BaseApplication.getInstance().getUser();
        if(AccountHelper.isLogin()) {

                ((TextView) findViewById(R.id.tv_username)).setText(user.getNickName());
                ((TextView) findViewById(R.id.tv_level)).setText(user.getGroupName());


        }
    }

    private void initStatusView() {
        ViewGroup.LayoutParams layoutParams = mBinding.include.viewStatus.getLayoutParams();
        layoutParams.height = StatusBarUtil.getStatusBarHeight(this);
        mBinding.include.viewStatus.setLayoutParams(layoutParams);
    }

    private void initId() {
        drawerLayout = mBinding.drawerLayout;
        navView = mBinding.navView;
        //fab = mBinding.include.fab;
        toolbar = mBinding.include.toolbar;
        //llTitleMenu = mBinding.include.llTitleMenu;
        vpContent = (NoScrollHorizontalViewPager) mBinding.include.vpContent;
        //fab.setVisibility(View.GONE);

        llTitleGank = mBinding.include.ivTitleGank;
        //llTitleArticle = mBinding.include.ivTitleArticle;
        llTitleMine = mBinding.include.ivTitleMine;
        llTitleShoppingCart = mBinding.include.ivTitleShoppingCart;
        vpContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }

    private void initListener() {
        //llTitleMenu.setOnClickListener(this);
        mBinding.include.ivTitleGank.setOnClickListener(this);
        mBinding.include.ivTitleMine.setOnClickListener(this);
        //mBinding.include.ivTitleArticle.setOnClickListener(this);
        mBinding.include.ivTitleShoppingCart.setOnClickListener(this);
        //fab.setOnClickListener(this);
    }

    NavHeaderMainBinding bind;

    /**
     * inflateHeaderView 进来的布局要宽一些
     */
    private void initDrawerLayout() {
        navView.inflateHeaderView(R.layout.nav_header_main);
        View headerView = navView.getHeaderView(0);
        bind = DataBindingUtil.bind(headerView);
//        bind.setListener(this);
//        bind.dayNightSwitch.setChecked(SPUtils.getNightMode());
//
//        ImgLoadUtil.displayCircle(bind.ivAvatar, ConstantsImageUrl.IC_AVATAR);
//        bind.llNavExit.setOnClickListener(this);
//        bind.ivAvatar.setOnClickListener(this);

//        bind.llNavHomepage.setOnClickListener(listener);
//        bind.llNavScanDownload.setOnClickListener(listener);
//        bind.llNavDeedback.setOnClickListener(listener);
//        bind.llNavAbout.setOnClickListener(listener);
//        bind.llNavLogin.setOnClickListener(listener);
    }

    private void initContentFragment() {
        ArrayList<Fragment> mFragmentList = new ArrayList<>();
        mFragmentList.add(new GankFragment());
        mFragmentList.add(new ShoppingCartFragment());
        mFragmentList.add(new MineFragment());

        // 注意使用的是：getSupportFragmentManager
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        vpContent.setAdapter(adapter);
        // 设置ViewPager最大缓存的页面个数(cpu消耗少)
        vpContent.setOffscreenPageLimit(3);
        vpContent.addOnPageChangeListener(this);
        mBinding.include.ivTitleGank.setSelected(true);
        vpContent.setCurrentItem(0);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }


//    private PerfectClickListener listener = new PerfectClickListener() {
//        @Override
//        protected void onNoDoubleClick(final View v) {
//            mBinding.drawerLayout.closeDrawer(GravityCompat.START);
//            mBinding.drawerLayout.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    switch (v.getId()) {
////                        case R.id.ll_nav_homepage:// 主页
////                            WebViewActivity.loadUrl(v.getContext(),CommonUtils.getString(R.string.string_url_all_orders),"全部订单",true);
//////                            NavHomePageActivity.startHome(MainActivity.this);
////                            break;
//                        case R.id.ll_nav_scan_download://扫码下载
//                            NavDownloadActivity.start(MainActivity.this);
//                            break;
////                        case R.id.ll_nav_deedback:// 问题反馈
//////                            NavDeedBackActivity.start(MainActivity.this);
////                            break;
////                        case R.id.ll_nav_about:// 关于云阅
////                            OrderActivity.start(MainActivity.this);
////                            //NavAboutActivity.start(MainActivity.this);
////                            break;
//                        case R.id.ll_nav_login:// 登录GitHub账号
////                            WebViewActivity.loadUrl(v.getContext(), "https://github.com/login", "登录GitHub账号");
//                            //RxBus.getDefault().post(RxCodeConstants.JUMP_TYPE_TO_ONE, new RxBusBaseMessage());
//                            //MineActivity.start(MainActivity.this);
//                            break;
//                    }
//                }
//            }, 260);
//        }
//    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.ll_title_menu:// 开启菜单
//                //drawerLayout.openDrawer(GravityCompat.START);
//                break;
            case R.id.iv_title_gank:// 首页
                if (vpContent.getCurrentItem() != 0) {//不然cpu会有损耗
                    llTitleGank.setSelected(true);
                    llTitleMine.setSelected(false);
                    llTitleShoppingCart.setSelected(false);
                    vpContent.setCurrentItem(0);
                }
                break;
//            case R.id.iv_title_article:// 文章栏
//                if (vpContent.getCurrentItem() != 1) {
//                    llTitleArticle.setSelected(true);
//                    llTitleGank.setSelected(false);
//                    llTitleMine.setSelected(false);
//                    llTitleShoppingCart.setSelected(false);
//                    vpContent.setCurrentItem(1);
//                }
//                break;
            case R.id.iv_title_shopping_cart:// 购物车
                if (vpContent.getCurrentItem() != 1) {
                    llTitleShoppingCart.setSelected(true);
                    llTitleMine.setSelected(false);
                    llTitleGank.setSelected(false);
                    vpContent.setCurrentItem(1);
                }
                break;
            case R.id.iv_title_mine:// 个人中心
                if (vpContent.getCurrentItem() != 2) {
                    llTitleMine.setSelected(true);
                    llTitleGank.setSelected(false);
                    llTitleShoppingCart.setSelected(false);
                    vpContent.setCurrentItem(2);
                }
                break;
            case R.id.iv_avatar: // 头像进入GitHub
                GoodsDetailActivity.start(MainActivity.this,"2126");
                //MineActivity.start(MainActivity.this);
//                WebViewActivity.loadUrl(v.getContext(),CommonUtils.getString(R.string.string_url_cloudreader),"CloudReader");
                break;
            case R.id.ll_nav_exit:// 退出应用
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 夜间模式待完善
     */
    public boolean getNightMode() {
        return SPUtils.getNightMode();
    }

    public void onNightModeClick(View view) {
        if (!SPUtils.getNightMode()) {
//            SkinCompatManager.getInstance().loadSkin(Constants.NIGHT_SKIN);
        } else {
            // 恢复应用默认皮肤
//            SkinCompatManager.getInstance().restoreDefaultTheme();
        }
        SPUtils.setNightMode(!SPUtils.getNightMode());
        bind.dayNightSwitch.setChecked(SPUtils.getNightMode());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.main, menu);
        return false;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_search:
////                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
//
//                Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
//                startActivityForResult(intent, SCAN_QR_REQUEST);
//
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
        return false;
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == SCAN_QR_REQUEST) {
//            //处理扫描结果（在界面上显示）
//            if (null != data) {
//                Bundle bundle = data.getExtras();
//                if (bundle == null) {
//                    return;
//                }
//                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
//                    String result = bundle.getString(CodeUtils.RESULT_STRING);
//
//                    if(!result.isEmpty()){
//                        //验证邀请人
//                        if(result.startsWith(HttpUtils.API_HOST + "inviter.aspx")){
//
//                            if(AccountHelper.isLogin())
//                            {
//                                final String inviter_id = result.replace(HttpUtils.API_HOST + "inviter.aspx?from=","");
//                                //ToastUtil.showToast("邀请人:" + inviter_id);
//
//                                User user = BaseApplication.getInstance().getUser();
//                                Subscription get = HttpClient.Builder.getUserServer().inviteUser("invite",user.getUserName(),user.getPassword(),inviter_id)
//                                        .subscribeOn(Schedulers.io())
//                                        .observeOn(AndroidSchedulers.mainThread())
//                                        .subscribe(new Observer<LoginResult>(){
//
//                                            @Override
//                                            public void onCompleted() {
//                                            }
//
//                                            @Override
//                                            public void onError(Throwable e) {
//                                                ToastUtil.showToast("系统无法读取您的个人信息,请您稍后再试");
//                                            }
//
//                                            @Override
//                                            public void onNext(LoginResult loginResult) {
//                                                if(loginResult.getMsg().equals("邀请成功")){
//                                                    if(loginResult.getData().getInviter()==0) {
//                                                        final NormalDialog dialog = new NormalDialog(MainActivity.this);
//                                                        dialog.content("您的邀请人ID为：" + inviter_id).style(NormalDialog.STYLE_ONE)//
//                                                                .titleTextSize(23)
//                                                                .titleTextColor(Color.parseColor("#fffd625b"))
//                                                                .cornerRadius(10)
//                                                                .titleLineHeight(0)
//                                                                .show();
//                                                        dialog.setOnBtnClickL(
//                                                                new OnBtnClickL() {
//                                                                    @Override
//                                                                    public void onBtnClick() {
//                                                                        SharedPreferencesHelper.getInstance().saveData("inviter_id", inviter_id);
//                                                                        SharedPreferencesHelper.getInstance().saveData("inviter_time", TimeUtil.getDateTime().getTime());
//                                                                        ToastUtil.showToast("您接受了"+SharedPreferencesHelper.getInstance().getData("inviter_id", "").toString()+"邀请,有效期1小时");
//                                                                        dialog.dismiss();
//                                                                    }
//                                                                }
//
//                                                        );
//                                                    }else {
//                                                        final NormalDialog dialog2 = new NormalDialog(MainActivity.this);
//                                                        dialog2.content("您已经被" + String.valueOf(loginResult.getData().getInviter()) +"邀请过了，无法再次被邀请")
//                                                                .btnNum(1)
//                                                                .btnText("我知道了")
//                                                                .titleLineHeight(0)
//                                                                .cornerRadius(10)
//                                                                .titleTextColor(Color.parseColor("#fffd625b"))
//                                                                .show();
//                                                        dialog2.setOnBtnClickL(
//                                                                new OnBtnClickL() {
//                                                                    @Override
//                                                                    public void onBtnClick() {
//                                                                        dialog2.dismiss();
//                                                                    }
//                                                                });
//                                                    }
//
//                                                }else {
//                                                    ToastUtil.showToast("登录信息错误,请您重新登录");
//                                                }
//
//                                            }
//                                        });
//
//
//                            }else {
//                                LoginActivity.start(MainActivity.this);
//                            }
//                        }
//                        else if(result.startsWith(HttpUtils.API_HOST + "goods/show-")){
//                            final String goods_id = result.replace(HttpUtils.API_HOST + "goods/show-","").replace(".aspx","");
//                            if(!goods_id.isEmpty()) {
//                                try {
//                                    int id = Integer.valueOf(goods_id);
//                                    if (id > 0) {
//                                        GoodsDetailActivity.start(MainActivity.this, String.valueOf(id));
//                                    }
//                                } catch (Exception e) {
//
//                                }
//                            }
//
//                        }
//                        else if(result.equals("孙冰")||result.equals("Gonziy")){
//                            WebViewActivity.loadUrl(MainActivity.this,"http://shop.zhenmeizhixiu.com/gonziy.aspx","");
//
//                        }
//                        else{
//                            final NormalDialog dialog=new NormalDialog(this);
//                            dialog.content("解析结果:" + result)//
//                                    .btnNum(1)
//                                    .titleLineHeight(0)
//                                    .cornerRadius(10)
//                                    .titleTextColor(Color.parseColor("#fffd625b"))
//                                    .btnText("确认")//
//                                    .show();
//                        }
//                    }
//
//                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
//                    ToastUtil.showToast("解析二维码失败");
//                }
//            }
//        }
//    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                llTitleGank.setSelected(true);
                llTitleShoppingCart.setSelected(false);
                llTitleMine.setSelected(false);
                break;
            case 1:

                llTitleGank.setSelected(false);
                llTitleShoppingCart.setSelected(true);
                llTitleMine.setSelected(false);
                break;
            case 2:
                llTitleGank.setSelected(false);
                llTitleShoppingCart.setSelected(false);
                llTitleMine.setSelected(true);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                mBinding.drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                // 不退出程序，进入后台
//                moveTaskToBack(true);
                exit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 1000) {
            ToastUtil.showToast("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            moveTaskToBack(true);
        }
    }

    /**
     * 每日推荐点击"新电影热映榜"跳转
     */
    private void initRxBus() {
        RxBus.getDefault().toObservable(RxCodeConstants.JUMP_TYPE_TO_ONE, RxBusBaseMessage.class)
                .subscribe(new Action1<RxBusBaseMessage>() {
                    @Override
                    public void call(RxBusBaseMessage integer) {
                        mBinding.include.vpContent.setCurrentItem(1);
                    }
                });
    }

    public void toViewPager(int page){
        mBinding.include.vpContent.setCurrentItem(page);

    }
}
