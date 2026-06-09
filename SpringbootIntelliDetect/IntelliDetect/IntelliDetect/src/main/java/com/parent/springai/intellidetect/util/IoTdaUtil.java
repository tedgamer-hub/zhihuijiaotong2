package com.parent.springai.intellidetect.util;

import com.huaweicloud.sdk.core.auth.AbstractCredentials;
import com.huaweicloud.sdk.core.auth.BasicCredentials;
import com.huaweicloud.sdk.core.region.Region;
import com.huaweicloud.sdk.iotda.v5.IoTDAClient;
import com.huaweicloud.sdk.iotda.v5.model.CreateCommandRequest;
import com.huaweicloud.sdk.iotda.v5.model.CreateCommandResponse;
import com.huaweicloud.sdk.iotda.v5.model.DeviceCommandRequest;

public class IoTdaUtil {

    private String ak = getConfig("IOTDA_ACCESS_KEY", "");
    private String sk = getConfig("IOTDA_SECRET_KEY", "");
    private String iotdaEndpoint = getConfig("IOTDA_ENDPOINT", "");
    private String deviceId = getConfig("IOTDA_DEVICE_ID", "");
    private String regionId = getConfig("IOTDA_REGION_ID", "cn-north-4");


    public void sendAccidentWarningCommand( String command){
        // 创建IoT客户端
        BasicCredentials auth = new BasicCredentials()
                .withDerivedPredicate(AbstractCredentials.DEFAULT_DERIVED_PREDICATE)
                .withAk(ak)
                .withSk(sk);

        IoTDAClient client = IoTDAClient.newBuilder()
                .withCredential(auth)
                .withRegion(new Region(regionId, iotdaEndpoint))
                .build();


        try {
            // 构建命令请求
            CreateCommandRequest request = new CreateCommandRequest();
            request.withDeviceId(deviceId);

            DeviceCommandRequest body = new DeviceCommandRequest();
            // 发送事故警示命令，参数格式与CreateCommandSolution一致
            body.withServiceId("警示下发")
                    .withCommandName("OLED")
                    .withParas("{\"accident_warning\":\"" +command +"\"}");

            request.withBody(body);

            // 发送命令
            CreateCommandResponse response = client.createCommand(request);


            System.out.println("=== 命令发送成功 ===");
            System.out.println("命令ID: " + response.getCommandId());
            System.out.println("响应: " + response.toString());

        } catch (Exception e) {
            System.err.println("命令发送失败: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("测试失败", e);
        }
    }

    private IoTdaUtil() {}

    private static final IoTdaUtil instance = new IoTdaUtil();

    public static IoTdaUtil getInstance() {
        return instance;
    }

    private static String getConfig(String key, String defaultValue) {
        String value = System.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            value = System.getenv(key);
        }
        return value == null || value.trim().isEmpty() ? defaultValue : value;
    }
}
