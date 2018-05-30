package com.zxxapp.mall.maintenance.http;

import com.example.http.HttpUtils;
import com.zxxapp.mall.maintenance.bean.FrontpageBean;
import com.zxxapp.mall.maintenance.bean.GankIoDataBean;
import com.zxxapp.mall.maintenance.bean.GankIoDayBean;
import com.zxxapp.mall.maintenance.bean.HotMovieBean;
import com.zxxapp.mall.maintenance.bean.MovieDetailBean;
import com.zxxapp.mall.maintenance.bean.PaymentBean;
import com.zxxapp.mall.maintenance.bean.RequestBaseBean;
import com.zxxapp.mall.maintenance.bean.RequestDataArrayBean;
import com.zxxapp.mall.maintenance.bean.RequestDataBean;
import com.zxxapp.mall.maintenance.bean.RequestDataListArrayBean;
import com.zxxapp.mall.maintenance.bean.RequestListArrayBean;
import com.zxxapp.mall.maintenance.bean.RequestPaginationBean;
import com.zxxapp.mall.maintenance.bean.RequestUploadBean;
import com.zxxapp.mall.maintenance.bean.ResultBean;
import com.zxxapp.mall.maintenance.bean.ShopDataBean;
import com.zxxapp.mall.maintenance.bean.UserLoginBean;
import com.zxxapp.mall.maintenance.bean.account.AreaBean;
import com.zxxapp.mall.maintenance.bean.account.CartResult;
import com.zxxapp.mall.maintenance.bean.account.LoginResult;
import com.zxxapp.mall.maintenance.bean.account.UserAddressBean;
import com.zxxapp.mall.maintenance.bean.account.UserCenterBean;
import com.zxxapp.mall.maintenance.bean.article.ArticleBean;
import com.zxxapp.mall.maintenance.bean.book.BookBean;
import com.zxxapp.mall.maintenance.bean.book.BookDetailBean;
import com.zxxapp.mall.maintenance.bean.goods.GoodsDetailBean;
import com.zxxapp.mall.maintenance.bean.goods.ServiceListBean;
import com.zxxapp.mall.maintenance.bean.payment.PayBean;
import com.zxxapp.mall.maintenance.bean.shop.CategoryBean;
import com.zxxapp.mall.maintenance.bean.shop.OrderBean;
import com.zxxapp.mall.maintenance.bean.shop.ServiceBean;
import com.zxxapp.mall.maintenance.bean.shop.ShopBean;
import com.zxxapp.mall.maintenance.bean.shopping.CartCount;
import com.zxxapp.mall.maintenance.bean.shopping.OrderByAccountIdBean;
import com.zxxapp.mall.maintenance.bean.shopping.PreOrderBean;
import com.zxxapp.mall.maintenance.bean.shopping.ShopListBean;

import org.json.JSONObject;

import java.math.BigDecimal;

import javax.xml.transform.Result;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by jingbin on 16/11/21.
 * 网络请求类（一个接口一个方法）
 */
public interface HttpClient {

    class Builder {
        public static HttpClient getDouBanService() {
            return HttpUtils.getInstance().getDouBanServer(HttpClient.class);
        }
        public static HttpClient getTingServer() {
            return HttpUtils.getInstance().getTingServer(HttpClient.class);
        }
        public static HttpClient getGankIOServer() {
            return HttpUtils.getInstance().getGankIOServer(HttpClient.class);
        }

        public static HttpClient getGoodsServer() {
            return HttpUtils.getInstance().getGoodsServer(HttpClient.class);
        }
        public static HttpClient getZhiXiuServer() {
            return HttpUtils.getInstance().getZhiXiuServer(HttpClient.class);
        }

        public static HttpClient getZMServer() {
            return HttpUtils.getInstance().getZMServer(HttpClient.class);
        }

        public static HttpClient getUserServer() {
            return HttpUtils.getInstance().getUserServer(HttpClient.class);
        }
    }

    @GET("category/getService")
    Observable<ServiceListBean> getServiceList(@Query("categoryId") String categoryId, @Query("categoryType") String categoryType);

    @GET("shop/getShopListByServiceId")
    Observable<ShopListBean> getShopListByServiceId(@Query("serviceId") String serviceId);

    @GET("order/getOrderByAccountId")
    Observable<OrderByAccountIdBean> getOrderByAccountId(@Query("token") String token, @Query("pageNo") String pageNo, @Query("pageLimit") String pageLimit);

    @GET("order/payment")
    Observable<PayBean> payment(@Query("orderNo") String orderNo, @Query("payment") String payment);




    /**
     * 商户接口开始
     */
    @GET("shop/getShopByToken")
    Observable<RequestListArrayBean<ShopBean>> getShopByToken(@Query("token") String token);

    @GET("auth/checkAuth")
    Observable<RequestBaseBean> checkAuth(@Query("token") String token);

    @FormUrlEncoded
    @POST("shop/createShop")
    Observable<RequestBaseBean> createShop(@Field("token") String token,
                                           @Field("shopName") String title,
                                           @Field("logoImg") String logo,
                                           @Field("address") String address,
                                           @Field("intro") String intro,
                                           @Field("notice") String notice,
                                           @Field("location") String location);


    @Multipart
    @POST("shop/uploadImg")
    Observable<RequestUploadBean> uploadImage(@Part MultipartBody.Part logo);

    @GET("auth/addAuth")
    Observable<RequestBaseBean> addAuth(@Query("token") String token,
                                        @Query("realName") String name,
                                        @Query("identity") String no,
                                        @Query("idPic") String cards,
                                        @Query("businessPic") String license,
                                        @Query("expPic") String pictures);

    @GET("category/getCategory")
    Observable<RequestDataListArrayBean<CategoryBean>> getCategory(@Query("token") String token);

    @GET("category/getService")
    Observable<RequestDataListArrayBean<ServiceBean>> getService(@Query("token") String token,
                                                                 @Query("categoryId") int categoryId,
                                                                 @Query("categoryType") String categoryTypr);

    @GET("shop/addServiceApp")
    Observable<RequestBaseBean> addService(@Query("shopId") int shopId,
                                           @Query("serviceId") int serviceId);

    @GET("shop/delServiceApp")
    Observable<RequestBaseBean> delService(@Query("shopId") int shopId,
                                           @Query("serviceId") int serviceId);

    @GET("shop/getShopServiceByShopIdApp")
    Observable<RequestDataArrayBean<ServiceBean>> getShopServiceList(@Query("shopId") int shopId);

    @GET("order/getOrderListByShopId")
    Observable<RequestPaginationBean<OrderBean>> getOrderList(@Query("shopId") int shopId,
                                                              @Query("status") int status,
                                                              @Query("pageNo") int page,
                                                              @Query("pageLimit") int limit);

    @GET("order/getOrderListByShopId")
    Observable<RequestPaginationBean<OrderBean>> getOrderList(@Query("shopId") int shopId,
                                                              @Query("pageNo") int page,
                                                              @Query("pageLimit") int limit);

    @GET("order/getOrderByOrderNo2")
    Observable<RequestDataBean<OrderBean>> getOrder(@Query("orderNo") String orderNo);

    /**
     * 商户接口结束
     **/













    @GET("v1/goods/index.ashx")
    Observable<ShopDataBean> getGoodsList(@Query("action") String action, @Query("channel_name") String channel_name, @Query("category_id") String category_id, @Query("page") String page_index, @Query("size") String page_size);

    @GET("v1/article/index.ashx")
    Observable<ArticleBean> getArticleList(@Query("action") String action, @Query("channel_name") String channel_name, @Query("category_id") String category_id, @Query("page") String page_index, @Query("size") String page_size);

    @GET("v1/goods/index.ashx")
    Observable<GoodsDetailBean> getGoodsDetailData(@Query("action") String action, @Query("id") String id);


    @GET("v1/user/index.ashx")
    Observable<AreaBean> getProvince(@Query("action") String action);

    @FormUrlEncoded
    @POST("v1/user/index.ashx")
    Observable<AreaBean> getCity(@Query("action") String action, @Field("province") String province);


    @FormUrlEncoded
    @POST("v1/user/index.ashx")
    Observable<UserAddressBean> getUserAddressList(@Query("action") String action, @Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("v1/user/index.ashx")
    Observable<AreaBean> getArea(@Query("action") String action, @Field("city") String province);

    @FormUrlEncoded
    @POST("getmobileLogin")
    Observable<LoginResult> getMobileLogin(@Field("userName") String userName, @Field("pwd") String password);

    @FormUrlEncoded
    @POST("account/editAccountAPI")
    Observable<ResultBean> editAccountAPI(@Field("token") String token,@Field("nickname") String nickname, @Field("phone") String phone);

    @FormUrlEncoded
    @POST("account/myMessageAPI")
    Observable<UserLoginBean> getMyMessageAPI(@Field("token") String token);

    @GET("order/addOrder")
    Observable<ResultBean> addOrder(
            @Query("uploadImg") String uploadImg,
            @Query("phone") String phone,
            @Query("serviceId") String serviceId,
            @Query("shopId") String shopId,
            @Query("location") String location,
            @Query("lng") String lng,
            @Query("lat") String lat,
            @Query("content") String content,
            @Query("token") String token
    );

















    @FormUrlEncoded
    @POST("v1/user/index.ashx")
    Observable<LoginResult> inviteUser(@Query("action") String action, @Field("username") String username, @Field("password") String password, @Field("password") String inviter);

    @FormUrlEncoded
    @POST("v1/user/index.ashx")
    Observable<LoginResult> getUserData(@Query("action") String action, @Field("unionid") String unionid);


    @FormUrlEncoded
    @POST("v1/user/index.ashx")
    Observable<CartResult> getShoppingCartData(@Query("action") String action, @Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("v1/user/index.ashx")
    Observable<CartCount> getShoppingCartCount(@Query("action") String action, @Field("username") String username, @Field("password") String password);


    @FormUrlEncoded
    @POST("v1/user/index.ashx")
    Observable<CartResult> getShoppingCartSelectedData(@Query("action") String action, @Field("username") String username, @Field("password") String password);


    @FormUrlEncoded
    @POST("v1/user/index.ashx")
    Observable<PreOrderBean> submitOrder(
            @Query("action") String action,
            @Field("username") String username,
            @Field("password") String password,
            @Field("book_id") String book_id,
            @Field("bag_quantity") Integer bag_quantity,
            @Field("payment_id") Integer payment_id,
            @Field("express_id") Integer express_id,
            @Field("accept_name") String accept_name,
            @Field("hid_province") String hid_province,
            @Field("hid_city") String hid_city,
            @Field("hid_area") String hid_area,
            @Field("address") String address,
            @Field("mobile") String mobile,
            @Field("txt_temp_inviter") String txt_temp_inviter
    );

    @FormUrlEncoded
    @POST("v1/user/index.ashx")
    Observable<UserCenterBean> getUserCenterInfo(
            @Query("action") String action,
            @Field("username") String username,
            @Field("password") String password
    );


    @FormUrlEncoded
    @POST("v1/user/index.ashx")
    Observable<ResultBean> setShoppingCartQuantity(@Query("action") String action, @Field("username") String username, @Field("password") String password, @Field("article_id") Integer article_id, @Field("goods_id") Integer goods_id, @Field("quantity") Integer quantity);

    @FormUrlEncoded
    @POST("v1/user/index.ashx")
    Observable<ResultBean> setShoppingCartSelect(@Query("action") String action, @Field("username") String username, @Field("password") String password, @Field("article_id") Integer article_id, @Field("goods_id") Integer goods_id, @Field("selected") Integer selected);

    @FormUrlEncoded
    @POST("v1/user/index.ashx")
    Observable<ResultBean> setShoppingCartDelete(@Query("action") String action, @Field("username") String username, @Field("password") String password, @Field("article_id") Integer article_id, @Field("goods_id") Integer goods_id);

    @FormUrlEncoded
    @POST("v1/user/index.ashx")
    Observable<ResultBean> setIntoShoppingCart(@Query("action") String action, @Field("username") String username, @Field("password") String password, @Field("article_id") Integer article_id, @Field("goods_id") Integer goods_id, @Field("quantity") Integer quantity);


    /**
     * 首页轮播图
     */
    @GET("ting?from=android&version=5.8.1.0&channel=ppzs&operator=3&method=baidu.ting.plaza.index&cuid=89CF1E1A06826F9AB95A34DC0F6AAA14")
    Observable<FrontpageBean> getFrontpage();

    /**
     * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * 请求个数： 数字，大于0
     * 第几页：数字，大于0
     * eg: http://gank.io/api/data/Android/10/1
     */
    @GET("data/{type}/{pre_page}/{page}")
    Observable<GankIoDataBean> getGankIoData(@Path("type") String id, @Path("page") int page, @Path("pre_page") int pre_page);



    /**
     * 每日数据： http://gank.io/api/day/年/月/日
     * eg:http://gank.io/api/day/2015/08/06
     */
    @GET("day/{year}/{month}/{day}")
    Observable<GankIoDayBean> getGankIoDay(@Path("year") String year, @Path("month") String month, @Path("day") String day);

    /**
     * 豆瓣热映电影，每日更新
     */
    @GET("v2/movie/in_theaters")
    Observable<HotMovieBean> getHotMovie();

    /**
     * 获取电影详情
     *
     * @param id 电影bean里的id
     */
    @GET("v2/movie/subject/{id}")
    Observable<MovieDetailBean> getMovieDetail(@Path("id") String id);

    /**
     * 获取豆瓣电影top250
     *
     * @param start 从多少开始，如从"0"开始
     * @param count 一次请求的数目，如"10"条，最多100
     */
    @GET("v2/movie/top250")
    Observable<HotMovieBean> getMovieTop250(@Query("start") int start, @Query("count") int count);

    /**
     * 根据tag获取图书
     *
     * @param tag   搜索关键字
     * @param count 一次请求的数目 最多100
     */

    @GET("v2/book/search")
    Observable<BookBean> getBook(@Query("tag") String tag, @Query("start") int start, @Query("count") int count);

    @GET("v2/book/{id}")
    Observable<BookDetailBean> getBookDetail(@Path("id") String id);

    /**
     * 根据tag获取music
     * @param tag
     * @return
     */

//    @GET("v2/music/search")
//    Observable<MusicRoot> searchMusicByTag(@Query("tag")String tag);

//    @GET("v2/music/{id}")
//    Observable<Musics> getMusicDetail(@Path("id") String id);
}