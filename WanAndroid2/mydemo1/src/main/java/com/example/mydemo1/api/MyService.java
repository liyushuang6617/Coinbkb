package com.example.mydemo1.api;

import com.example.mydemo1.base.BaseResponse;
import com.example.mydemo1.bean.AccountDataBean;
import com.example.mydemo1.bean.ArticleItemBean;
import com.example.mydemo1.bean.ArticleListData;
import com.example.mydemo1.bean.BannerDataBean;
import com.example.mydemo1.bean.CollectionDataBean;
import com.example.mydemo1.bean.CommonDataBean;
import com.example.mydemo1.bean.KnowledgeTreeBean;
import com.example.mydemo1.bean.ListData;
import com.example.mydemo1.bean.LoginDataBean;
import com.example.mydemo1.bean.NavigtionDataBean;
import com.example.mydemo1.bean.ProjectListDataBean;
import com.example.mydemo1.bean.ProjectTreeDataBean;
import com.example.mydemo1.bean.SearchDataBean;
import com.example.mydemo1.bean.SearchHotBean;
import com.example.mydemo1.bean.WxArticleDataTabBean;
import com.example.mydemo1.bean.WxArticleListBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface MyService {

    /**
     * 练习
     *
     * @param url
     * @return
     */
    @GET
    Observable<BaseResponse<List<ListData>>> get(@Url String url);

    /**
     * 置项文章
     * https://www.wanandroid.com/article/top/json
     *
     * @param url
     * @return
     */
    @GET
    Observable<BaseResponse<List<ArticleItemBean>>> getTopArticles(@Url String url);

    /**
     * 广告栏
     * https://www.wanandroid.com/article/top/json
     *
     * @param url
     * @return
     */
    @GET
    Observable<BaseResponse<List<BannerDataBean>>> getBanner(@Url String url);

    /**
     * 首页文章
     * https://www.wanandroid.com/article/list/0/json
     */

    @GET("article/list/{pageNum}/json")
    Observable<BaseResponse<ArticleListData>> getArticleList(@Path("pageNum") int page);

    /**
     * 体系数据
     * https://www.wanandroid.com/tree/json
     *
     * @param url
     * @return
     */
    @GET
    Observable<BaseResponse<List<KnowledgeTreeBean>>> getKnowledgeTree(@Url String url);

    /**
     * 知识体系下的文章
     * https://www.wanandroid.com/article/list/0/json?cid=60
     *
     * @param
     * @return
     */
    @GET("article/list/0/json")
    Observable<BaseResponse<ArticleListData>> getKnowledgeItem(@Query("cid") int cid);

    /**
     * 导航数据
     * https://www.wanandroid.com/navi/json
     *
     * @param url
     * @return
     */
    @GET
    Observable<BaseResponse<List<NavigtionDataBean>>> getNavigtion(@Url String url);

    /**
     * 项目分类
     * https://www.wanandroid.com/project/tree/json
     *
     * @param url
     * @return
     */
    @GET
    Observable<BaseResponse<List<ProjectTreeDataBean>>> getProjectTree(@Url String url);

    /**
     * 项目列表数据
     * https://www.wanandroid.com/project/list/1/json?cid=294
     *
     * @param cid
     * @return
     */
    @GET("project/list/{page}/json")
    Observable<BaseResponse<ProjectListDataBean>> getProjectListItem(@Path("page") int page, @Query("cid") int cid);

    /**
     * 获取公众号列表
     * https://wanandroid.com/wxarticle/chapters/json
     *
     * @param url
     * @return
     */
    @GET
    Observable<BaseResponse<List<WxArticleDataTabBean>>> getWxArticleTab(@Url String url);

    /**
     * 查看某个公众号历史数据
     * https://wanandroid.com/wxarticle/list/408/1/json
     *
     * @param cid
     * @return
     */
    @GET("wxarticle/list/{cid}/2/json")
    Observable<BaseResponse<WxArticleListBean>> getWxArticleList(@Path("cid") int cid);

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

    /**
     * 退出登录
     * https://www.wanandroid.com/user/logout/json
     *
     * @param url
     * @return
     */
    @GET
    Observable<BaseResponse> getUnlogin(@Url String url);

    /**
     * 搜索
     * https://www.wanandroid.com/article/query/0/json
     * 页码：拼接在链接上，从0开始。
     * k ： 搜索关键词
     *
     * @param url
     * @param key
     * @return
     */
    @POST
    @FormUrlEncoded
    Observable<BaseResponse<ArticleListData>> getSearchData(@Url String url, @Field("k") String key);


    /**
     * 搜索热词
     * https://www.wanandroid.com/hotkey/json
     *
     * @param url
     * @return
     */
    @GET
    Observable<BaseResponse<List<SearchHotBean>>> getSearchHotBean(@Url String url);

    /**
     * 常用网站
     * https://www.wanandroid.com/friend/json
     *
     * @param url
     * @return
     */

    @GET
    Observable<BaseResponse<List<CommonDataBean>>> getCommonData(@Url String url);


    /**
     * 收藏
     * https://www.wanandroid.com/lg/collect/list/0/json
     *
     * @param url
     * @return
     */
    @GET
    Observable<BaseResponse<CollectionDataBean>> getCollectionData(@Url String url);

    /**
     * 收藏站内文章
     * https://www.wanandroid.com/lg/collect/8655/json
     *
     * @param
     * @return
     */
    @POST
    Observable<BaseResponse> getCollectionItem(@Url String url);

    /**
     * 通过文章取消收藏
     * https://www.wanandroid.com/lg/uncollect_originId/2333/json
     *
     * @param url
     * @return
     */
    @POST
    Observable<BaseResponse> getNoCollection(@Url String url);

    /**
     * 通过收藏页面取消收藏
     * https://www.wanandroid.com/lg/uncollect/2805/json
     *
     * @return
     */
    @POST
    @FormUrlEncoded
    Observable<BaseResponse> getNoCollection(@Url String url, @Field("originId") int originId);
}
