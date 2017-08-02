package com.graduation.mygraduation.net;

import com.google.gson.Gson;
import com.graduation.mygraduation.commons.Constants;
import com.graduation.mygraduation.entity.JokeBean;
import com.graduation.mygraduation.entity.NewsDataBean;
import com.graduation.mygraduation.entity.TodayOfHistoryBean;
import com.graduation.mygraduation.entity.TodayOfHistoryDetailBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * 功能：网络请求的客户端
 *
 * 单例模式
 */
public class NetClient {

    private NetCallback netCallback;

    //单例模式
    private static NetClient netClient;

    private Gson mGson;

    private NetClient() {
        mGson = new Gson();
    }

    //// TODO: 2017/4/19  为什么这会加上一个synchronize方法
    public static NetClient getInstance() {
        if (netClient == null) {
            synchronized (NetClient.class) {
                if (netClient == null) {
                    netClient = new NetClient();
                }
            }
        }
        return netClient;
    }

    /**
     * 根据相应的新闻类型获取新闻数据
     *
     * @param type     新闻的类型
     * @param callback 数据的回调接口
     */
    public void GetNewsData(String type, NetCallback callback) {

        netCallback = callback;

        OkHttpUtils.post()
                .url(Constants.NEWS_DATA_URL)
                .addParams("type", type)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        netCallback.onError(e, id);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        NewsDataBean newsDataBean = mGson.fromJson(response, NewsDataBean.class);
                        netCallback.onSuccess(newsDataBean, id);
                    }
                });

    }

    /**
     * 根据年月，获取 历史上的今天 的概述
     *
     * @param month    查询的c月
     * @param day      查询的日
     * @param callback 查询回调的接口
     */
    public void GetTodayOfHistoryData(int month, int day, NetCallback callback) {
        netCallback = callback;

        String params = month + "/" + day;

        OkHttpUtils.post()
                .url(Constants.TODAY_OF_HISTORY_URP)
                .addParams("date", params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        netCallback.onError(e, id);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        TodayOfHistoryBean todayOfHistoryBean = mGson.fromJson(response, TodayOfHistoryBean.class);
                        netCallback.onSuccess(todayOfHistoryBean, id);
                    }
                });
    }

    /**
     * 根据上一个方法成功查询后回调结果中的e_id，获取详细数据
     *
     * @param e_id     上一个方法成功查询后回调结果中的e_id
     * @param callback 查询回调的接口
     */
    public void GetTodayOfHistoryDetailData(String e_id, NetCallback callback) {

        netCallback = callback;

        OkHttpUtils.post()
                .url(Constants.TODAY_OF_HISTORY_DETAIL_URL)
                .addParams("e_id", e_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        netCallback.onError(e, id);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        TodayOfHistoryDetailBean bean = mGson.fromJson(response, TodayOfHistoryDetailBean.class);
                        netCallback.onSuccess(bean, id);
                    }
                });
    }

//http://api.avatardata.cn/Joke/QueryJokeByTime?key=b11322356536404a9efe787a4767750c&page=2&rows=10&sort=asc&time=1418745237

    /**
     * 更新 最新的笑话
     *
     * @param page     页数
     * @param pagesize 每页的数目
     * @param callback 查询回调接口
     */
    public void GetNowJokeData(int page, int pagesize, NetCallback callback) {

        netCallback = callback;

        OkHttpUtils.post()
                .url(Constants.NewJOKE)
                .addParams("page", page + "")
                .addParams("rows", pagesize + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        netCallback.onError(e, id);
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        JokeBean bean = mGson.fromJson(response, JokeBean.class);
                        netCallback.onSuccess(bean, id);
                    }
                });
    }


    /**
     * 根据指定时间，获取之前的段子数据
     *
     * @param time     指定时间
     * @param callback 查询回调接口
     */
    public void GetNowJokeData(String time, NetCallback callback) {

        netCallback = callback;

        OkHttpUtils.post()
                .url(Constants.DUANZI_URL)
                .addParams("sort", "desc")
                .addParams("rows", 30 + "")
                .addParams("time", time)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        netCallback.onError(e, id);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JokeBean bean = mGson.fromJson(response, JokeBean.class);
                        netCallback.onSuccess(bean, id);
                    }
                });
    }

    /**
     * 获取图片的分类列表
     *
     * @param callback 查询回调接口
     */
    public void GetBeautyList(NetCallback callback) {
        netCallback = callback;
        OkHttpUtils.get()
                .url(Constants.URL_BEAUTYLIST)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        netCallback.onError(e, id);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        netCallback.onSuccess(response, id);
                    }
                });
    }

    /**
     * 获取美女图片的列表
     *
     * @param id       图片分类ID
     * @param page     展示页数
     * @param rows     没页展示数目
     * @param callback 回调接口
     */
    public void GetBeautyPhoList(int id, int page, int rows, NetCallback callback) {
        netCallback = callback;
        OkHttpUtils.get()
                .url(Constants.URL_BEAUTYPHOLIST)
                .addParams("id", String.valueOf(id))
                .addParams("page", String.valueOf(page))
                .addParams("rows", String.valueOf(rows))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        netCallback.onError(e, id);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        netCallback.onSuccess(response, id);
                    }
                });

    }

    public void GetBeautyDetails(int id, NetCallback callback) {
        netCallback = callback;
        OkHttpUtils.get().url(Constants.URL_PHOTODETAILS)
                .addParams("id", String.valueOf(id))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        netCallback.onError(e, id);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        netCallback.onSuccess(response, id);
                    }
                });
    }

    /**
     * 图灵机器人接口 可根据 问题和上下 语境进行问题回答给出答案
     * @param info 问题信息
     * @param callback 回调接口
     */
    public void GetRobotMsg(String info, NetCallback callback) {
        netCallback = callback;
        OkHttpUtils.post()
                .url(Constants.URL)
                .addParams("key", Constants.API_KEY)
                .addParams("info", info)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        netCallback.onError(e, id);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        netCallback.onSuccess(response, id);
                    }
                });


    }

}
