package com.anxpp.demo.schedule;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by anxpp.com on 2017/12/10.
 */
@Slf4j
public class ScheduleExecutor {

    private static ScheduleExecutor scheduleExecutor = new ScheduleExecutor();

    public static ScheduleExecutor instance() {
        return scheduleExecutor;
    }

    private ScheduleExecutor() {
    }

    /**
     * 首次任务延迟时间
     */
    private final static int FIRST_TASK_DELAY = 1;

    /**
     * 首次任务延迟时间
     */
    private final static TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;

    /**
     * 最小线程池数量
     */
    private final static int MIN_POOL_SIZE = 8;

    /**
     * 执行器
     */
    private ScheduledExecutorService service = Executors.newScheduledThreadPool(MIN_POOL_SIZE);

    /**
     * 待执行任务集合
     */
    private ConcurrentHashMap<String, TaskData> tasks = new ConcurrentHashMap<>();

    /**
     * 失败的任务集合
     */
    private ConcurrentHashMap<String, TaskData> failedTasks = new ConcurrentHashMap<>();

    /**
     * 执行任务
     *
     * @param task 任务
     */
    void exec(AbstractTask task) {
        exec(task, FIRST_TASK_DELAY);
    }

    /**
     * 执行任务
     *
     * @param task  任务
     * @param delay 延迟
     */
    void exec(AbstractTask task, int delay) {
        exec(task, delay, DEFAULT_TIME_UNIT);
    }

    /**
     * 执行任务
     *
     * @param task  任务
     * @param delay 延迟
     */
    void exec(Runnable task, int delay, TimeUnit timeUnit) {
        service.schedule(task, delay, timeUnit);
    }

    /**
     * 添加任务
     *
     * @param data 任务数据
     */
    boolean add(TaskData data) {
        if (getTasks().containsKey(data.key()))
            return false;
        getTasks().put(data.key(), data);
        return true;
    }

    /**
     * 任务执行成功
     *
     * @param data 任务数据
     */
    void complete(TaskData data) {
        getTasks().remove(data.key());
    }

    /**
     * 任务执行失败
     */
    void failed(TaskData data) {
        getTasks().remove(data.key());
        getFailedTasks().put(data.key(), data);
    }

    /**
     * 停止接收任务
     */
    void stop() {
        service.shutdown();
    }

    /**
     * 停止接受任务并结束正在执行的任务
     */
    void stopNow() {
        service.shutdownNow();
    }

    public ConcurrentHashMap<String, TaskData> getTasks() {
        return tasks;
    }

    public ConcurrentHashMap<String, TaskData> getFailedTasks() {
        return failedTasks;
    }
}