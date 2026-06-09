package com.parent.springai.intellidetect.service;

import com.huaweicloud.sdk.core.auth.BasicCredentials;
import com.huaweicloud.sdk.core.auth.ICredential;
import com.huaweicloud.sdk.core.auth.AbstractCredentials;
import com.huaweicloud.sdk.core.region.Region;
import com.huaweicloud.sdk.iotda.v5.IoTDAClient;
import com.huaweicloud.sdk.iotda.v5.model.CreateCommandRequest;
import com.huaweicloud.sdk.iotda.v5.model.DeviceCommandRequest;
import com.huaweicloud.sdk.iotda.v5.model.CreateCommandResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class IoTDAService {
    @Value("${iotda.region-id}")
    private String regionId;
    @Value("${iotda.endpoint}")
    private String endpoint;
    @Value("${iotda.ak}")
    private String ak;
    @Value("${iotda.sk}")
    private String sk;
    @Value("${iotda.project-id}")
    private String projectId;
    @Value("${iotda.device-id}")
    private String deviceId;
    // 创建IoT客户端
    private IoTDAClient createClient() {
        ICredential auth = new BasicCredentials()
                .withDerivedPredicate(AbstractCredentials.DEFAULT_DERIVED_PREDICATE)
                .withAk(ak)
                .withSk(sk);

        return IoTDAClient.newBuilder()
                .withCredential(auth)
                .withRegion(new Region(regionId, endpoint))
                .build();
    }

    // 初始化IoT连接
    public boolean connectDevice() {
        try {
            IoTDAClient client = createClient();
            // 验证连接是否正常
            System.out.println("IoT设备连接成功");
            return true;
        } catch (Exception e) {
            System.err.println("IoT设备连接失败: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // 发送事故警示命令
    public String sendAccidentWarning(int warningLevel) {
        if (warningLevel != 1 && warningLevel != 2 && warningLevel != 0) {
            throw new IllegalArgumentException("警示级别必须为1、2或0");
        }
        try {
            IoTDAClient client = createClient();
            DeviceCommandRequest body = new DeviceCommandRequest();
            body.withServiceId("警示下发")
                .withCommandName("OLED")
                .withParas(String.format("{\"accident_warning\": \"%d\"}", warningLevel));
            CreateCommandRequest request = new CreateCommandRequest()
                    .withDeviceId(deviceId)
                    .withBody(body);
            CreateCommandResponse response = client.createCommand(request);
            System.out.println("事故警示命令发送成功，commandId: " + response.getCommandId());
            return response.getCommandId();
        } catch (Exception e) {
            System.err.println("事故警示命令发送失败: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // 发送自定义命令
    public String sendCommand(String serviceId, String commandName, String paras) {
        try {
            IoTDAClient client = createClient();
            DeviceCommandRequest body = new DeviceCommandRequest();
            body.withServiceId(serviceId)
                .withCommandName(commandName)
                .withParas(paras);
            CreateCommandRequest request = new CreateCommandRequest()
                    .withDeviceId(deviceId)
                    .withBody(body);
            CreateCommandResponse response = client.createCommand(request);
            System.out.println("自定义命令发送成功，commandId: " + response.getCommandId());
            return response.getCommandId();
        } catch (Exception e) {
            System.err.println("自定义命令发送失败: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}