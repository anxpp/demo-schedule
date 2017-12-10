package com.anxpp.demo;

import com.anxpp.demo.schedule.NotifyTask;
import com.anxpp.demo.schedule.ScheduleExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class DemoApp {

    public static void main(String[] args) {
        SpringApplication.run(DemoApp.class, args);
    }

    @Bean
    public CommandLineRunner start() {
        log.info("Service started...");
        return args -> {
            new NotifyTask(new NotifyTask.Data("BIXIN", "0000001", "", "http://anxpp.com")).start();
            new NotifyTask(new NotifyTask.Data("BIXIN", "0000002", "", "http://baidu.com")).start();
            new NotifyTask(new NotifyTask.Data("BIXIN", "0000003", "", "weibo.com")).start();
            new NotifyTask(new NotifyTask.Data("BIXIN", "0000004", "", "http://weibo.com")).start();
            new NotifyTask(new NotifyTask.Data("BIXIN", "0000005", "", "baidu.com")).start();
            new NotifyTask(new NotifyTask.Data("BIXIN", "0000006", "", "baidu1.com")).start();
            new NotifyTask(new NotifyTask.Data("BIXIN", "0000007", "", "baidu2.com")).start();
            new NotifyTask(new NotifyTask.Data("BIXIN", "0000008", "", "baidu3.com")).start();
            new NotifyTask(new NotifyTask.Data("BIXIN", "0000009", "", "baidu4.com")).start();
            new NotifyTask(new NotifyTask.Data("BIXIN", "00000010", "", "baidu5.com")).start();
            new NotifyTask(new NotifyTask.Data("BIXIN", "00000011", "", "baidu6.com")).start();
            new NotifyTask(new NotifyTask.Data("BIXIN", "00000011", "", "baidu6.com")).start();
            new NotifyTask(new NotifyTask.Data("BIXIN", "00000011", "", "baidu6.com")).start();
            new NotifyTask(new NotifyTask.Data("BIXIN", "00000011", "", "baidu6.com")).start();
            new NotifyTask(new NotifyTask.Data("BIXIN", "00000011", "", "baidu6.com")).start();
            new NotifyTask(new NotifyTask.Data("BIXIN", "00000011", "", "baidu6.com")).start();
            new NotifyTask(new NotifyTask.Data("BIXIN", "00000011", "", "baidu6.com")).start();
            new NotifyTask(new NotifyTask.Data("BIXIN", "00000011", "", "baidu6.com")).start();
            new NotifyTask(new NotifyTask.Data("BIXIN", "00000011", "", "baidu6.com")).start();
            new NotifyTask(new NotifyTask.Data("BIXIN", "00000011", "", "baidu6.com")).start();
            new NotifyTask(new NotifyTask.Data("BIXIN", "00000011", "", "baidu6.com")).start();
        };
    }

    @Bean
    public ScheduleExecutor scheduleExecutor() {
        return ScheduleExecutor.instance();
    }
}
