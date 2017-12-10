package com.anxpp.demo.schedule;

/**
 * Created by anxpp.com on 2017/12/10.
 */
public class DefaultRetry implements Retry {

    private final static int MAX_RETRY_TIMES = 5;

    private int times = 1;


    /**
     * 获取延迟执行时间
     */
    @Override
    public int delay() {
        return 1 << times++;
    }

    /**
     * 获取当前重试次数
     */
    @Override
    public int times() {
        return times;
    }

    /**
     * 是否继续重试
     */
    @Override
    public boolean keep() {
        return times < MAX_RETRY_TIMES;
    }

    /**
     * 是否重试失败
     */
    @Override
    public boolean failed() {
        return false;
    }
}