package io.chirpstack.json;

import io.chirpstack.json.sub.UplinkEventDeviceInfo;
import io.chirpstack.json.sub.UplinkEventTxInfo;

import java.util.List;

public class DownlinkEvent {
    /*
    {
   "downlinkId":3392200569,
   "time":"2022-11-25T20:29:56.036380953+00:00",
   "deviceInfo":{
      "tenantId":"52f14cd4-c6f1-4fbd-8f87-4025e1d49242",
      "tenantName":"ChirpStack",
      "applicationId":"997b3f5d-6f2d-4b51-b4eb-b50009055bf3",
      "applicationName":"test application",
      "deviceProfileId":"5f9e8dd6-0f37-4d74-8e9b-4fbefeb22131",
      "deviceProfileName":"ABP Profile",
      "deviceName":"test 2 mkr1300",
      "devEui":"d0a98e24b24d93dc"
   },
   "queueItemId":"bde8b4b0-d417-4d50-af95-6eca754f1a0f",
   "fCntDown":522,
   "gatewayId":"3c408850a5b4f27c",
   "txInfo":{
      "frequency":867900000,
      "power":14,
      "modulation":{
         "lora":{
            "bandwidth":125000,
            "spreadingFactor":8,
            "codeRate":"CR_4_5",
            "polarizationInversion":true
         }
      },
      "timing":{
         "delay":{
            "delay":"1s"
         }
      },
      "context":"8oD8KA=="
   }
}
     */

    // @ApiModelProperty(notes = "downlink uniq identifier ", required = false)
    protected long downlinkId;

    // @ApiModelProperty(notes = "time ", required = false)
    protected String time;

    // @ApiModelProperty(notes = "device & context detailed information", required = false)
    protected UplinkEventDeviceInfo deviceInfo;

    // @ApiModelProperty(notes = "downlink queue reference Id ", required = false)
    protected String queueItemId;

    // @ApiModelProperty(notes = "frame counter ", required = false)
    protected int fCntDown;

    // @ApiModelProperty(notes = "gateway address", required = false)
    protected String gatewayId;

    // @ApiModelProperty(notes = "RF information", required = false)
    protected UplinkEventTxInfo txInfo;

    // ---


    public long getDownlinkId() {
        return downlinkId;
    }

    public void setDownlinkId(long downlinkId) {
        this.downlinkId = downlinkId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public UplinkEventDeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(UplinkEventDeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getQueueItemId() {
        return queueItemId;
    }

    public void setQueueItemId(String queueItemId) {
        this.queueItemId = queueItemId;
    }

    public int getfCntDown() {
        return fCntDown;
    }

    public void setfCntDown(int fCntDown) {
        this.fCntDown = fCntDown;
    }

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public UplinkEventTxInfo getTxInfo() {
        return txInfo;
    }

    public void setTxInfo(UplinkEventTxInfo txInfo) {
        this.txInfo = txInfo;
    }
}
