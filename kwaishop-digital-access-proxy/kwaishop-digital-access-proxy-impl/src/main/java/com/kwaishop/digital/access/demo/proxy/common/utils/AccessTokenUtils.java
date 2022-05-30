package com.kwaishop.digital.access.demo.proxy.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.kwaishop.digital.access.demo.proxy.client.oauth.KsMerchantApiException;
import com.kwaishop.digital.access.demo.proxy.client.oauth.OauthAccessTokenKsClient;
import com.kwaishop.digital.access.demo.proxy.response.KsAccessTokenResponse;

import lombok.extern.slf4j.Slf4j;


/**
 * @author pengjianfei
 * Created on 2022-05-23
 */
@Slf4j
@Component
public class AccessTokenUtils {

    @Value("${kwaishop.appKey}")
    private String appKey;
    @Value("${kwaishop.appSecret}")
    private String appSecret;
    @Value("${kwaishop.url}")
    private String serverRestUrl;
    @Value("${kwaishop.code}")
    private String code;

    private static final String REFRESH_TOKEN_PATH = "./refresh-token.txt";

    public static String accessToken;
    public static String refreshToken;

    // todo: fixedDelay指定accessToken刷新间隔 24h:8640000
    @Scheduled(initialDelay = 8640000, fixedDelay = 8640000)
    public void refreshAccessTokenWithFixDelay() {
        Retryer<String> retryer = RetryerBuilder.<String> newBuilder()
                .retryIfException()
                .retryIfResult(StringUtils::isEmpty)
                //每次重试之间间隔
                .withWaitStrategy(WaitStrategies.fixedWait(6, TimeUnit.SECONDS))
                //最大重试次数
                .withStopStrategy(StopStrategies.stopAfterAttempt(50))
                //单次请求最大时长，超时则终止当前任务
                //.withAttemptTimeLimiter(AttemptTimeLimiters.fixedTimeLimit(10, TimeUnit.SECONDS))
                .build();
        try {
            Callable<String> callable = new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return getNewAccessToken();
                }
            };
            accessToken = retryer.call(callable);
            log.info("Refresh accessToken success, accessToken={}", accessToken);
        } catch (Exception e) {
            String msg =
                    String.format("Refresh accessToken failed, errorMsg:%s", e);
            log.error(msg);
        }
    }

    @PostConstruct
    public void init() throws KsMerchantApiException {
        OauthAccessTokenKsClient client = new OauthAccessTokenKsClient(appKey, appSecret, serverRestUrl);
        KsAccessTokenResponse response = null;
        try {
            response = client.getAccessToken(code);
        } catch (KsMerchantApiException e) {
            String msg =
                    String.format("Init accessToken and refreshToken failed, errorMsg:%s", e.getErrorMsg());
            log.error(msg);
            throw new KsMerchantApiException(msg);
        }
        if (response == null) {
            log.error("Init accessToken and refreshToken failed, response is null.");
            throw new KsMerchantApiException("KsAccessTokenResponse parse fail.");
        }
        if (response.getResult() != 1) {
            String msg =
                    String.format("Init accessToken and refreshToken failed, errorMsg: %s", response.getError_msg());
            log.error(msg);
            throw new KsMerchantApiException(msg);
        } else {
            accessToken = response.getAccess_token();
            refreshToken = response.getRefresh_token();
            log.info("Init accessToken and refreshToken success, accessToken: {}, refreshToken: {}", accessToken,
                    refreshToken);
        }
    }

    public String getNewAccessToken() throws KsMerchantApiException {
        //  String newRefreshToken = getRefreshTokenFromFile();
        log.info("Refresh begin, refreshToken = {}", refreshToken);
        String newRefreshToken = refreshToken;
        OauthAccessTokenKsClient client = new OauthAccessTokenKsClient(appKey, appSecret, serverRestUrl);

        KsAccessTokenResponse response = null;
        try {
            response = client.refreshAccessToken(newRefreshToken);
        } catch (KsMerchantApiException e) {
            String msg =
                    String.format("AccessTokenUtils.getNewAccessToken failed, appKey:%s  errorMsg:%s", appKey,
                            e.getErrorMsg());
            log.error(msg);
            throw new KsMerchantApiException(msg, e);
        }
        if (response == null) {
            throw new KsMerchantApiException("KsAccessTokenResponse parse fail.");
        }
        if (response.getResult() != 1) {
            String msg = String.format("AccessTokenUtils.getNewAccessToken failed, appkey:%s, response:%s",
                    appKey, JsonUtils.toJSONString(response));
            log.error(msg);
            throw new KsMerchantApiException(response.getResult(), response.getError_msg());
        }

        newRefreshToken = response.getRefresh_token();
        //        setRefreshTokenToFile(newRefreshToken);
        refreshToken = newRefreshToken;
        log.info("Refresh end, new refreshToken = {}", refreshToken);
        return response.getAccess_token();
    }

    public static boolean setRefreshTokenToFile(String refreshToken) {

        try {
            File file = new File(REFRESH_TOKEN_PATH);
            file.createNewFile();
            if (file.exists()) {
                //创建一个用于操作文件的字节输出流对象，创建就必须明确数据存储目的地
                FileOutputStream fos = new FileOutputStream(file);
                //写入
                fos.write(refreshToken.getBytes(StandardCharsets.UTF_8));
                //刷新并关闭流
                fos.flush();
                fos.close();
            }
            return true;
        } catch (IOException e) {
            log.error("refresh-token文件创建失败");
            e.printStackTrace();
        }
        return false;
    }

    public static String getRefreshTokenFromFile() {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader reader = new FileReader(REFRESH_TOKEN_PATH);
                BufferedReader br = new BufferedReader(reader)
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            log.error("refresh-token文件读取失败");
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
