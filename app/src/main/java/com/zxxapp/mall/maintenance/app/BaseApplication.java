package com.zxxapp.mall.maintenance.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidu.mapapi.SDKInitializer;
import com.example.http.HttpUtils;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.cookie.store.MemoryCookieStore;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.bean.RongCloudResultBean;
import com.zxxapp.mall.maintenance.bean.RongCloudTokenBean;
import com.zxxapp.mall.maintenance.bean.account.User;
import com.zxxapp.mall.maintenance.config.AppConfig;
import com.zxxapp.mall.maintenance.helper.account.AccountHelper;
import com.zxxapp.mall.maintenance.helper.jpush.TagAliasOperatorHelper;
import com.zxxapp.mall.maintenance.http.RequestImpl;
import com.zxxapp.mall.maintenance.model.RongCloudModel;
import com.zxxapp.mall.maintenance.utils.DebugUtil;
import com.zxxapp.mall.maintenance.utils.SharedPreferencesHelper;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.zxxapp.mall.maintenance.utils.ToastUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import okhttp3.OkHttpClient;
import rx.Subscription;


import static com.zxxapp.mall.maintenance.helper.jpush.TagAliasOperatorHelper.ACTION_ADD;
import static com.zxxapp.mall.maintenance.helper.jpush.TagAliasOperatorHelper.ACTION_CHECK;
import static com.zxxapp.mall.maintenance.helper.jpush.TagAliasOperatorHelper.ACTION_CLEAN;
import static com.zxxapp.mall.maintenance.helper.jpush.TagAliasOperatorHelper.ACTION_DELETE;
import static com.zxxapp.mall.maintenance.helper.jpush.TagAliasOperatorHelper.ACTION_GET;
import static com.zxxapp.mall.maintenance.helper.jpush.TagAliasOperatorHelper.ACTION_SET;
import static com.zxxapp.mall.maintenance.helper.jpush.TagAliasOperatorHelper.TagAliasBean;
import static com.zxxapp.mall.maintenance.helper.jpush.TagAliasOperatorHelper.sequence;

public class BaseApplication extends Application {

    private static BaseApplication baseApplication;
    private static Context context;
    private AppConfig appConfig;
    public IWXAPI WXApi;
    public boolean isSetAlias = false;


    public static BaseApplication getInstance() {
        return baseApplication;
    }

    @SuppressWarnings("unused")
    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        context = getApplicationContext();
        HttpUtils.getInstance().init(this, DebugUtil.DEBUG);
        SharedPreferencesHelper.init(getApplicationContext());
        appConfig = new AppConfig(getApplicationContext());
        AccountHelper.init(baseApplication);
        ZXingLibrary.initDisplayOpinion(this);
        initTextSize();
        OkGoInit();
        WXInit();
        JPushInit();
        BuglyInit();
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
                "com.zxxapp.mall.maintenance".equals(getCurProcessName(getApplicationContext()))) {

            /**
             * IMKit SDK调用第一步 初始化
             */
            RongIM.init(this);
        }
        BaiduMapInit();
//        if(AccountHelper.isLogin()) {
//            User user = AccountHelper.getUser();
//            RongCloudConnect(user.getUserID().toString(), user.getNickName());
//        }
    }
    public void RongCloudConnect(String userId,String nickName) {
        RongCloudModel model = new RongCloudModel();
        model.setData(userId,nickName);
        model.get(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                RongCloudResultBean resultBean = (RongCloudResultBean)object;
                JSONObject jsonObject = JSON.parseObject(resultBean.getData());
                String token = jsonObject.getString("token");

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

    private void BaiduMapInit(){
        SDKInitializer.initialize(getApplicationContext());
    }

    private void JPushInit() {
        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);
    }

    private void BuglyInit() {
        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));

        Bugly.init(getApplicationContext(), "7b7b49a896", false);

    }
    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {

                return appProcess.processName;
            }
        }
        return null;
    }
    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    public static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 使其系统更改字体大小无效
     */
    private void initTextSize() {
        Resources res = getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    /**
     * 获取用户登录信息
     * @return
     */
    public User getUser() {
//        return  new User();
        if(!isSetAlias) {
            if (appConfig.getUser().getUserID() > 0) {
                TagAliasBean tagAliasBean = new TagAliasBean();
                tagAliasBean.setAction(ACTION_SET);
                sequence++;
                tagAliasBean.setAlias(appConfig.getUser().getUserID().toString());
                tagAliasBean.setAliasAction(true);
                TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(), sequence, tagAliasBean);
                isSetAlias = true;
            }
        }

        return appConfig.getUser();
    }
    public String[] getUserHistory() {
        return appConfig.getUserHistory();
    }

    /**
     * 设置用户信息
     * @param user
     */
    public void setUser(User user) {
        appConfig.setUser(user);

        if(!user.UserName.isEmpty()){
            TagAliasBean tagAliasBean = new TagAliasBean();
            tagAliasBean.setAction(ACTION_SET);
            sequence++;
            tagAliasBean.setAlias(user.getUserID().toString());
            tagAliasBean.setAliasAction(true);
            TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(),sequence,tagAliasBean);
        }


    }
    public void addUserHistory(String username){
        appConfig.addUserHistory(username);
    }

    private void WXInit()
    {
        //注册微信
        WXApi = WXAPIFactory.createWXAPI(getApplicationContext(), null);
        WXApi.registerApp(appConfig.WX_APPID);
    }

    private void OkGoInit()
    {
        OkGo.getInstance().init(this);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);
        //全局的读取超时时间
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //使用sp保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));
        //使用数据库保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));
        //使用内存保持cookie，app退出后，cookie消失
        builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));
    }
    public static Context getContext() {
        return context;
    }

}
