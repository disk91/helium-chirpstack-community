// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: gw/gw.proto
// Protobuf Java Version: 4.29.2

package io.chirpstack.api.gw;

public interface UplinkFrameSetOrBuilder extends
    // @@protoc_insertion_point(interface_extends:gw.UplinkFrameSet)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * PHYPayload.
   * </pre>
   *
   * <code>bytes phy_payload = 1;</code>
   * @return The phyPayload.
   */
  com.google.protobuf.ByteString getPhyPayload();

  /**
   * <pre>
   * TX meta-data.
   * </pre>
   *
   * <code>.gw.UplinkTxInfo tx_info = 2;</code>
   * @return Whether the txInfo field is set.
   */
  boolean hasTxInfo();
  /**
   * <pre>
   * TX meta-data.
   * </pre>
   *
   * <code>.gw.UplinkTxInfo tx_info = 2;</code>
   * @return The txInfo.
   */
  io.chirpstack.api.gw.UplinkTxInfo getTxInfo();
  /**
   * <pre>
   * TX meta-data.
   * </pre>
   *
   * <code>.gw.UplinkTxInfo tx_info = 2;</code>
   */
  io.chirpstack.api.gw.UplinkTxInfoOrBuilder getTxInfoOrBuilder();

  /**
   * <pre>
   * RX meta-data set.
   * </pre>
   *
   * <code>repeated .gw.UplinkRxInfo rx_info = 3;</code>
   */
  java.util.List<io.chirpstack.api.gw.UplinkRxInfo> 
      getRxInfoList();
  /**
   * <pre>
   * RX meta-data set.
   * </pre>
   *
   * <code>repeated .gw.UplinkRxInfo rx_info = 3;</code>
   */
  io.chirpstack.api.gw.UplinkRxInfo getRxInfo(int index);
  /**
   * <pre>
   * RX meta-data set.
   * </pre>
   *
   * <code>repeated .gw.UplinkRxInfo rx_info = 3;</code>
   */
  int getRxInfoCount();
  /**
   * <pre>
   * RX meta-data set.
   * </pre>
   *
   * <code>repeated .gw.UplinkRxInfo rx_info = 3;</code>
   */
  java.util.List<? extends io.chirpstack.api.gw.UplinkRxInfoOrBuilder> 
      getRxInfoOrBuilderList();
  /**
   * <pre>
   * RX meta-data set.
   * </pre>
   *
   * <code>repeated .gw.UplinkRxInfo rx_info = 3;</code>
   */
  io.chirpstack.api.gw.UplinkRxInfoOrBuilder getRxInfoOrBuilder(
      int index);
}
