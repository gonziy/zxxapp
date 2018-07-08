package com.zxxapp.mall.maintenance;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.http.HttpUtils;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.zxxapp.mall.maintenance.app.BaseApplication;
import com.zxxapp.mall.maintenance.app.ConstantsImageUrl;
import com.zxxapp.mall.maintenance.base.NoScrollHorizontalViewPager;
import com.zxxapp.mall.maintenance.bean.RongCloudResultBean;
import com.zxxapp.mall.maintenance.bean.account.LoginResult;
import com.zxxapp.mall.maintenance.bean.account.User;
import com.zxxapp.mall.maintenance.databinding.ActivityMainBinding;
import com.zxxapp.mall.maintenance.databinding.NavHeaderMainBinding;
import com.zxxapp.mall.maintenance.helper.account.AccountHelper;
import com.zxxapp.mall.maintenance.http.HttpClient;
import com.zxxapp.mall.maintenance.http.RequestImpl;
import com.zxxapp.mall.maintenance.http.rx.RxBus;
import com.zxxapp.mall.maintenance.http.rx.RxBusBaseMessage;
import com.zxxapp.mall.maintenance.http.rx.RxCodeConstants;
import com.zxxapp.mall.maintenance.model.RongCloudModel;
import com.zxxapp.mall.maintenance.ui.book.BookFragment;
import com.zxxapp.mall.maintenance.ui.chat.FragmentMessage;
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
import java.util.HashMap;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.CSCustomServiceInfo;
import io.rong.imlib.model.Conversation;
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

//    public static String user1 = "vmVRMp4DIaffvoMmV+2xlbshwQQyWY5jwHV9X5bUWd851kDNHowr/0jPvrF/oswE+7jb/leoZPRgLb+fx9bfiw==";
//    String user2 = "9+Pra2KUor5FactPwci/BsEGX3M0bXI0TC2+QY9D/BJk1rdC32LkhNI49v5v6x75FMCoF6ecHHr9ustRksffRg==";
    public static String loginUserToken = "";
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
        initRongIM();

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
    private  void  initRongIM(){
        if(AccountHelper.isLogin()) {

            User user = BaseApplication.getInstance().getUser();
            //loginUserToken = com.zxxapp.mall.maintenance.helper.RongIM.UserUtils.GetRongCloudToken(user.getUserID().toString(),user.getNickName());
            connect(user.getUserID().toString(),user.getNickName()==null?"未命名":user.getNickName());
        }
    }
    public void connect(String userId,String nickName) {
        RongCloudModel model = new RongCloudModel();
        model.setData(userId,nickName);
        model.get(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                RongCloudResultBean resultBean = (RongCloudResultBean)object;
                com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(resultBean.getData());
                String token = jsonObject.getString("token");
                loginUserToken = token;
                if(!token.isEmpty()){
                    RongIM.connect(token, new RongIMClient.ConnectCallback() {
                        @Override
                        public void onTokenIncorrect() {

                        }

                        @Override
                        public void onSuccess(String s) {
                            Log.e("rongcloud", "连接通讯服务器成功—————>" + s);
                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {
                            Log.e("rongcloud", "连接通讯服务器失败—————>" + errorCode.getMessage());
                        }
                    });
                }
            }

            @Override
            public void loadFailed() {

            }

            @Override
            public void addSubscription(Subscription subscription) {

            }
        });



    }

    private void initStatusView() {
        ViewGroup.LayoutParams layoutParams = mBinding.include.viewStatus.getLayoutParams();
        layoutParams.height = StatusBarUtil.getStatusBarHeight(this);
        mBinding.include.viewStatus.setLayoutParams(layoutParams);
    }
    private void  setTitle(String title){
        mBinding.include.titlebarTitle.setText(title);
    }
    private void  setTitleBarDisplay(Boolean display) {
        if (display) {
            mBinding.include.titleToolBar.setVisibility(View.VISIBLE);
        }else {
            mBinding.include.titleToolBar.setVisibility(View.GONE);
        }
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



        ConversationListFragment conversationListFragment = new ConversationListFragment();
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话，该会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//设置系统会话，该会话非聚合显示
                .build();
        conversationListFragment.setUri(uri);

//        FragmentManager fragmentManager = getChildFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.add(R.id.rong_container,conversationListFragment);
//        transaction.commit();
        mFragmentList.add(conversationListFragment);
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
                    setTitle("智修网");
                    setTitleBarDisplay(true);
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
                    setTitle("消息");
                    setTitleBarDisplay(true);
                }
                break;
            case R.id.iv_title_mine:// 个人中心
                if (vpContent.getCurrentItem() != 2) {
                    llTitleMine.setSelected(true);
                    llTitleGank.setSelected(false);
                    llTitleShoppingCart.setSelected(false);
                    vpContent.setCurrentItem(2);
                    setTitle("我的");
                    setTitleBarDisplay(false);
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
        return false;
    }


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



    public void connect(String token) {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {

            }

            @Override
            public void onSuccess(String s) {
                Log.e("rongcloud", "连接通讯服务器成功—————>" + s);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e("rongcloud", "连接通讯服务器失败—————>" + errorCode.getMessage());
            }
        });
    }


    public void list(View view) {
//        startActivity(new Intent(this, ConversationListActivity.class));
        Map<String, Boolean> map = new HashMap<>();
        map.put(Conversation.ConversationType.PRIVATE.getName(), false); // 会话列表需要显示私聊会话, 第二个参数 true 代表私聊会话需要聚合显示
        map.put(Conversation.ConversationType.GROUP.getName(), false);  // 会话列表需要显示群组会话, 第二个参数 false 代表群组会话不需要聚合显示
        RongIM.getInstance().startConversationList(this, map);
    }

    public void convers(View view) {
        RongIM.getInstance().startPrivateChat(this, "10086", "移动");
    }


    public void kefu(View view) {
        //首先需要构造使用客服者的用户信息
        CSCustomServiceInfo.Builder csBuilder = new CSCustomServiceInfo.Builder();
        CSCustomServiceInfo csInfo = csBuilder.nickName("客服").build();

        /**
         * 启动客户服聊天界面。
         *
         * @param context           应用上下文。
         * @param customerServiceId 要与之聊天的客服 Id。
         * @param title             聊天的标题，开发者可以在聊天界面通过 intent.getData().getQueryParameter("title") 获取该值, 再手动设置为标题。
         * @param customServiceInfo 当前使用客服者的用户信息。{@link io.rong.imlib.model.CSCustomServiceInfo}
         */
        RongIM.getInstance().startCustomerServiceChat(this, "KEFU149404318864090", "在线客服",csInfo);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RongIM.getInstance().disconnect();//不设置收不到推送
    }
}
