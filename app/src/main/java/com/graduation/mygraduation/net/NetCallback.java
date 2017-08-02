package com.graduation.mygraduation.net;

/**
 * 网络请求的回调接口
 */

public interface NetCallback<T> {


    /**
     * 成功返回的回调
     * @param response 成功返回的信息
     * @param id 成功码
     */
    void onSuccess(T response,int id);

    /**
     * 失败返回的回调
     * @param e  失败是返回的异常
     * @param id 失败码
     */
    void onError(Exception e,int id);
}
