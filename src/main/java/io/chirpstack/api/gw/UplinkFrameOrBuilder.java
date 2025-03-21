// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: gw/gw.proto
// Protobuf Java Version: 4.29.2

package io.chirpstack.api.gw;

public interface UplinkFrameOrBuilder extends
    // @@protoc_insertion_point(interface_extends:gw.UplinkFrame)
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
   * TX meta-data (deprecated).
   * </pre>
   *
   * <code>.gw.UplinkTxInfoLegacy tx_info_legacy = 2;</code>
   * @return Whether the txInfoLegacy field is set.
   */
  boolean hasTxInfoLegacy();
  /**
   * <pre>
   * TX meta-data (deprecated).
   * </pre>
   *
   * <code>.gw.UplinkTxInfoLegacy tx_info_legacy = 2;</code>
   * @return The txInfoLegacy.
   */
  io.chirpstack.api.gw.UplinkTxInfoLegacy getTxInfoLegacy();
  /**
   * <pre>
   * TX meta-data (deprecated).
   * </pre>
   *
   * <code>.gw.UplinkTxInfoLegacy tx_info_legacy = 2;</code>
   */
  io.chirpstack.api.gw.UplinkTxInfoLegacyOrBuilder getTxInfoLegacyOrBuilder();

  /**
   * <pre>
   * RX meta-data (deprecated).
   * </pre>
   *
   * <code>.gw.UplinkRxInfoLegacy rx_info_legacy = 3;</code>
   * @return Whether the rxInfoLegacy field is set.
   */
  boolean hasRxInfoLegacy();
  /**
   * <pre>
   * RX meta-data (deprecated).
   * </pre>
   *
   * <code>.gw.UplinkRxInfoLegacy rx_info_legacy = 3;</code>
   * @return The rxInfoLegacy.
   */
  io.chirpstack.api.gw.UplinkRxInfoLegacy getRxInfoLegacy();
  /**
   * <pre>
   * RX meta-data (deprecated).
   * </pre>
   *
   * <code>.gw.UplinkRxInfoLegacy rx_info_legacy = 3;</code>
   */
  io.chirpstack.api.gw.UplinkRxInfoLegacyOrBuilder getRxInfoLegacyOrBuilder();

  /**
   * <pre>
   * Tx meta-data.
   * </pre>
   *
   * <code>.gw.UplinkTxInfo tx_info = 4;</code>
   * @return Whether the txInfo field is set.
   */
  boolean hasTxInfo();
  /**
   * <pre>
   * Tx meta-data.
   * </pre>
   *
   * <code>.gw.UplinkTxInfo tx_info = 4;</code>
   * @return The txInfo.
   */
  io.chirpstack.api.gw.UplinkTxInfo getTxInfo();
  /**
   * <pre>
   * Tx meta-data.
   * </pre>
   *
   * <code>.gw.UplinkTxInfo tx_info = 4;</code>
   */
  io.chirpstack.api.gw.UplinkTxInfoOrBuilder getTxInfoOrBuilder();

  /**
   * <pre>
   * Rx meta-data.
   * </pre>
   *
   * <code>.gw.UplinkRxInfo rx_info = 5;</code>
   * @return Whether the rxInfo field is set.
   */
  boolean hasRxInfo();
  /**
   * <pre>
   * Rx meta-data.
   * </pre>
   *
   * <code>.gw.UplinkRxInfo rx_info = 5;</code>
   * @return The rxInfo.
   */
  io.chirpstack.api.gw.UplinkRxInfo getRxInfo();
  /**
   * <pre>
   * Rx meta-data.
   * </pre>
   *
   * <code>.gw.UplinkRxInfo rx_info = 5;</code>
   */
  io.chirpstack.api.gw.UplinkRxInfoOrBuilder getRxInfoOrBuilder();
}
