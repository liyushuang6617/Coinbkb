package com.example.wanandroid2.api;

import com.example.wanandroid2.base.BaseResponse;
import com.example.wanandroid2.bean.AccountDataBean;
import com.example.wanandroid2.bean.ArticleItemData;
import com.example.wanandroid2.bean.ArticleListData;
import com.example.wanandroid2.bean.BannerData;
import com.example.wanandroid2.bean.LoginDataBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface MyService {

    /**
     * 获取文章列表
     * https://www.wanandroid.com/article/list/0/json
     *
     * @param pageNum
     */
    @GET("article/list/{pageNum}/json")
    Observable<BaseResponse<ArticleListData>> getArticleList(@Path("pageNum") int pageNum);

    /**
     * 广告栏
     * https://www.wanandroid.com/banner/json
     *
     * @return 广告栏数据
     */
    @GET("banner/json")
    Observable<BaseResponse<List<BannerData>>> getBannerData();

    /**
     * 获取首页置顶文章列表
     * https://www.wanandroid.com/article/top/json
     */
    @GET("article/top/json")
    Observable<BaseResponse<List<ArticleItemData>>> getTopArticles();
    /**
     * 登录
     * https://www.wanandroid.com/user/login
     *
     * @param url
     * @param username
     * @param password
     * @return
     */
    @POST
    @FormUrlEncoded
    Observable<BaseResponse<LoginDataBean>> getLoginData(@Url String url, @Field("username") String username, @Field("password") String password);

    /**
     * 注册
     * https://www.wanandroid.com/user/register
     *
     * @param url
     * @param username
     * @param password
     * @param repassword
     * @return
     */
    @POST
    @FormUrlEncoded
    Observable<BaseResponse<AccountDataBean>> getAccOunt(@Url String url, @Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);


}
