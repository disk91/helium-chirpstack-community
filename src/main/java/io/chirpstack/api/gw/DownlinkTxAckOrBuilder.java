// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: gw/gw.proto
// Protobuf Java Version: 4.29.2

package io.chirpstack.api.gw;

public interface DownlinkTxAckOrBuilder extends
    // @@protoc_insertion_point(interface_extends:gw.DownlinkTxAck)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Gateway ID (deprecated).
   * </pre>
   *
   * <code>bytes gateway_id_legacy = 1;</code>
   * @return The gatewayIdLegacy.
   */
  com.google.protobuf.ByteString getGatewayIdLegacy();

  /**
   * <pre>
   * Gateway ID.
   * </pre>
   *
   * <code>string gateway_id = 6;</code>
   * @return The gatewayId.
   */
  java.lang.String getGatewayId();
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
   * Downlink ID.
   * </pre>
   *
   * <code>uint32 downlink_id = 2;</code>
   * @return The downlinkId.
   */
  int getDownlinkId();

  /**
   * <pre>
   * Downlink ID (deprecated).
   * </pre>
   *
   * <code>bytes downlink_id_legacy = 4;</code>
   * @return The downlinkIdLegacy.
   */
  com.google.protobuf.ByteString getDownlinkIdLegacy();

  /**
   * <pre>
   * Downlink frame items.
   * This list has the same length as the request and indicates which
   * downlink frame has been emitted of the requested list (or why it failed).
   * Note that at most one item has a positive acknowledgement.
   * </pre>
   *
   * <code>repeated .gw.DownlinkTxAckItem items = 5;</code>
   */
  java.util.List<io.chirpstack.api.gw.DownlinkTxAckItem> 
      getItemsList();
  /**
   * <pre>
   * Downlink frame items.
   * This list has the same length as the request and indicates which
   * downlink frame has been emitted of the requested list (or why it failed).
   * Note that at most one item has a positive acknowledgement.
   * </pre>
   *
   * <code>repeated .gw.DownlinkTxAckItem items = 5;</code>
   */
  io.chirpstack.api.gw.DownlinkTxAckItem getItems(int index);
  /**
   * <pre>
   * Downlink frame items.
   * This list has the same length as the request and indicates which
   * downlink frame has been emitted of the requested list (or why it failed).
   * Note that at most one item has a positive acknowledgement.
   * </pre>
   *
   * <code>repeated .gw.DownlinkTxAckItem items = 5;</code>
   */
  int getItemsCount();
  /**
   * <pre>
   * Downlink frame items.
   * This list has the same length as the request and indicates which
   * downlink frame has been emitted of the requested list (or why it failed).
   * Note that at most one item has a positive acknowledgement.
   * </pre>
   *
   * <code>repeated .gw.DownlinkTxAckItem items = 5;</code>
   */
  java.util.List<? extends io.chirpstack.api.gw.DownlinkTxAckItemOrBuilder> 
      getItemsOrBuilderList();
  /**
   * <pre>
   * Downlink frame items.
   * This list has the same length as the request and indicates which
   * downlink frame has been emitted of the requested list (or why it failed).
   * Note that at most one item has a positive acknowledgement.
   * </pre>
   *
   * <code>repeated .gw.DownlinkTxAckItem items = 5;</code>
   */
  io.chirpstack.api.gw.DownlinkTxAckItemOrBuilder getItemsOrBuilder(
      int index);
}
