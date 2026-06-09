package com.parent.springai.intellidetect.config;

import com.huaweicloud.sdk.core.auth.ICredential;
import com.huaweicloud.sdk.core.auth.AbstractCredentials;
import com.huaweicloud.sdk.core.auth.BasicCredentials;
import com.huaweicloud.sdk.core.region.Region;
import com.huaweicloud.sdk.iotda.v5.*;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import javax.annotation.PostConstruct;



public class CreateCommandSolution {
    // 配置参数
    @Value("${iotda.ak}")
    private String ak;
    
    @Value("${iotda.sk}")
    private String sk;
    
    @Value("${iotda.endpoint}")
    private String iotdaEndpoint;
    
    @Value("${iotda.device-id}")
    private String deviceId;
    
    @Value("${iotda.region-id}")
    private String regionId;
    
    private IoTDAClient client;

    /**
     * 构造函数
     */
    public CreateCommandSolution() {
        // 空构造函数，客户端将在@PostConstruct方法中初始化
    }

    /**
     * 初始化客户端（在属性注入完成后调用）
     */
    @PostConstruct
    private void init() {
        this.client = initClient();
    }

    /**
     * 初始化IoT客户端
     * @return 配置好的IoTDAClient实例
     */
    private IoTDAClient initClient() {
        ICredential auth = new BasicCredentials()
                .withDerivedPredicate(AbstractCredentials.DEFAULT_DERIVED_PREDICATE)
                .withAk(this.ak)
                .withSk(this.sk);

        return IoTDAClient.newBuilder()
                .withCredential(auth)
                .withRegion(new Region(this.regionId, this.iotdaEndpoint))
                .build();
    }

    /**
     * 检查最近是否有新事故发生
     * @return true如果有新事故，false否则
     * @throws Exception 如果HTTP请求或JSON解析失败
     */
    private boolean hasRecentAccident() throws Exception {
        URL url = new URL("http://120.46.139.74:8080/IntelliDetect/api/v1/accidents/check-new");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("HTTP GET请求失败，响应码: " + responseCode);
        }

        // 读取响应内容
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String responseBody = br.lines().collect(Collectors.joining());
            
            // 解析JSON响应
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> responseMap = mapper.readValue(responseBody, Map.class);
            
            // 检查响应结果，data字段为1表示有新事故，0表示没有
            Integer data = (Integer) responseMap.get("data");
            return data != null && data == 1;
        }
    }

    /**
     * 检查新事故并发送相应IoT命令
     * 注意：此方法不再自动执行，已被禁用以避免覆盖前端发送的命令
     */
    @Deprecated
    public void checkAndSendCommand() {
        System.out.println("[" + java.time.LocalDateTime.now() + "] 警告：checkAndSendCommand方法已被禁用，不再自动发送命令");
        System.out.println("[" + java.time.LocalDateTime.now() + "] 命令现在仅通过前端接口/api/v1/iot/send-command发送");
    }
}