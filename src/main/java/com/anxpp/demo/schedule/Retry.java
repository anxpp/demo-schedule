package com.anxpp.demo.schedule;

/**
 * 重试策略
 * Created by anxpp.com on 2017/12/10.
 */
public interface Retry {
    /**
     * 获取延迟执行时间
     */
    int delay();

    /**
     * 获取当前重试次数
     */
    int times();

    /**
     * 是否继续重试
     */
    boolean keep();

    /**
     * 是否重试失败
     */
    boolean failed();
}
