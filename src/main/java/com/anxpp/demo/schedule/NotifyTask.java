package com.anxpp.demo.schedule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.util.concurrent.TimeUnit;

/**
 * Created by anxpp.com on 2017/12/10.
 */
@Slf4j
public class NotifyTask extends AbstractTask {

    private MediaType mediaTypeJson = MediaType.parse("application/json; charset=utf-8");

    public NotifyTask(Data data) {
        setData(data);
    }

    @Override
    public boolean exec() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .readTimeout(1, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(1, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(1, TimeUnit.SECONDS)//设置连接超时时间
                .build();
        RequestBody body = null;
        try {
            body = RequestBody.create(mediaTypeJson, new ObjectMapper().writeValueAsString(""));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            Request request = new Request.Builder()
                    .url(((Data) getData()).notifyUrl)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            log.info(((Data) getData()).notifyUrl + "（" + getRetry().times() + "）：" + response.code() + "--" + ScheduleExecutor.instance().getTasks().size() + "--" + ScheduleExecutor.instance().getFailedTasks().size());
            response.body().close();
        } catch (Exception e) {
            log.info(((Data) getData()).notifyUrl + "（" + getRetry().times() + "）：" + e.getLocalizedMessage() + "--" + ScheduleExecutor.instance().getTasks().size() + "--" + ScheduleExecutor.instance().getFailedTasks().size());
            return false;
        }
        return true;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Data implements TaskData {
        // appId 应用ID
        private String appId;
        // tradeNo 交易号
        private String tradeNo;
        // 回调参数
        private Object params;
        // 回调地址
        private String notifyUrl;

        @Override
        public String key() {
            return appId + "_" + tradeNo;
        }
    }
}
