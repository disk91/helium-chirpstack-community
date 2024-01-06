// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: integration/integration.proto

package io.chirpstack.api.as.integration;

public interface TxAckEventOrBuilder extends
    // @@protoc_insertion_point(interface_extends:integration.TxAckEvent)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Downlink ID.
   * </pre>
   *
   * <code>uint32 downlink_id = 1;</code>
   * @return The downlinkId.
   */
  int getDownlinkId();

  /**
   * <pre>
   * Timestamp.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp time = 2;</code>
   * @return Whether the time field is set.
   */
  boolean hasTime();
  /**
   * <pre>
   * Timestamp.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp time = 2;</code>
   * @return The time.
   */
  com.google.protobuf.Timestamp getTime();
  /**
   * <pre>
   * Timestamp.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp time = 2;</code>
   */
  com.google.protobuf.TimestampOrBuilder getTimeOrBuilder();

  /**
   * <pre>
   * Device info.
   * </pre>
   *
   * <code>.integration.DeviceInfo device_info = 3;</code>
   * @return Whether the deviceInfo field is set.
   */
  boolean hasDeviceInfo();
  /**
   * <pre>
   * Device info.
   * </pre>
   *
   * <code>.integration.DeviceInfo device_info = 3;</code>
   * @return The deviceInfo.
   */
  DeviceInfo getDeviceInfo();
  /**
   * <pre>
   * Device info.
   * </pre>
   *
   * <code>.integration.DeviceInfo device_info = 3;</code>
   */
  DeviceInfoOrBuilder getDeviceInfoOrBuilder();

  /**
   * <pre>
   * Downlink queue item ID (UUID).
   * </pre>
   *
   * <code>string queue_item_id = 4;</code>
   * @return The queueItemId.
   */
  String getQueueItemId();
  /**
   * <pre>
   * Downlink queue item ID (UUID).
   * </pre>
   *
   * <code>string queue_item_id = 4;</code>
   * @return The bytes for queueItemId.
   */
  com.google.protobuf.ByteString
      getQueueItemIdBytes();

  /**
   * <pre>
   * Downlink frame-counter.
   * </pre>
   *
   * <code>uint32 f_cnt_down = 5;</code>
   * @return The fCntDown.
   */
  int getFCntDown();

  /**
   * <pre>
   * Gateway ID.
   * </pre>
   *
   * <code>string gateway_id = 6;</code>
   * @return The gatewayId.
   */
  String getGatewayId();
  /**
   * <pre>
   * Gateway ID.
   * </pre>
   *
   * <code>string gateway_id = 6;</code>
   * @return The bytes for gatewayId.
   */
  com.google.protobuf.ByteString
      getGatewayIdBytes();

  /**
   * <pre>
   * TX info.
   * </pre>
   *
   * <code>.gw.DownlinkTxInfo tx_info = 7;</code>
   * @return Whether the txInfo field is set.
   */
  boolean hasTxInfo();
  /**
   * <pre>
   * TX info.
   * </pre>
   *
   * <code>.gw.DownlinkTxInfo tx_info = 7;</code>
   * @return The txInfo.
   */
  io.chirpstack.api.gw.DownlinkTxInfo getTxInfo();
  /**
   * <pre>
   * TX info.
   * </pre>
   *
   * <code>.gw.DownlinkTxInfo tx_info = 7;</code>
   */
  io.chirpstack.api.gw.DownlinkTxInfoOrBuilder getTxInfoOrBuilder();
}