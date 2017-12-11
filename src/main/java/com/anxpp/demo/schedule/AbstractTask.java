package com.anxpp.demo.schedule;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by anxpp.com on 2017/12/10.
 */
@Slf4j
@Getter
@Setter
public abstract class AbstractTask implements Runnable {

    /**
     * 任务数据
     */
    private TaskData data;

    /**
     * 重试机制
     */
    private Retry retry = new DefaultRetry();

    /**
     * 执行器
     */
    private ScheduleExecutor scheduleExecutor = ScheduleExecutor.instance();

    /**
     * 任务是否首次运行（防止重试时 key 冲突）
     */
    private boolean runFirst = true;

    // 执行任务 由子类实现
    abstract public boolean exec();

    /**
     * 开始任务
     */
    public void start() {
        scheduleExecutor.exec(this);
    }

    @Override
    public void run() {
        // 执行并判断是否需要重试
        if (runFirst && !scheduleExecutor.add(data)) {
            log.info("任务已添加：" + data.key());
            return;
        }
        runFirst = false;
        if (exec())
            scheduleExecutor.complete(data);
        else {
            if (retry.keep()) scheduleExecutor.exec(this, retry.delay());   // 继续重试
            else scheduleExecutor.failed(data);                                   // 重试失败：添加到失败队列
        }
    }
}
