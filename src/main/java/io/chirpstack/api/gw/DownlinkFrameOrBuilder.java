// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: gw/gw.proto

// Protobuf Java Version: 3.25.1
package io.chirpstack.api.gw;

public interface DownlinkFrameOrBuilder extends
    // @@protoc_insertion_point(interface_extends:gw.DownlinkFrame)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Downlink ID.
   * </pre>
   *
   * <code>uint32 downlink_id = 3;</code>
   * @return The downlinkId.
   */
  int getDownlinkId();

  /**
   * <pre>
   * Downlink ID (UUID).
   * Deprecated: use downlink_id.
   * </pre>
   *
   * <code>bytes downlink_id_legacy = 4;</code>
   * @return The downlinkIdLegacy.
   */
  com.google.protobuf.ByteString getDownlinkIdLegacy();

  /**
   * <pre>
   * Downlink frame items.
   * This makes it possible to send multiple downlink opportunities to the
   * gateway at once (e.g. RX1 and RX2 in LoRaWAN). The first item has the
   * highest priority, the last the lowest. The gateway will emit at most
   * one item.
   * </pre>
   *
   * <code>repeated .gw.DownlinkFrameItem items = 5;</code>
   */
  java.util.List<io.chirpstack.api.gw.DownlinkFrameItem> 
      getItemsList();
  /**
   * <pre>
   * Downlink frame items.
   * This makes it possible to send multiple downlink opportunities to the
   * gateway at once (e.g. RX1 and RX2 in LoRaWAN). The first item has the
   * highest priority, the last the lowest. The gateway will emit at most
   * one item.
   * </pre>
   *
   * <code>repeated .gw.DownlinkFrameItem items = 5;</code>
   */
  io.chirpstack.api.gw.DownlinkFrameItem getItems(int index);
  /**
   * <pre>
   * Downlink frame items.
   * This makes it possible to send multiple downlink opportunities to the
   * gateway at once (e.g. RX1 and RX2 in LoRaWAN). The first item has the
   * highest priority, the last the lowest. The gateway will emit at most
   * one item.
   * </pre>
   *
   * <code>repeated .gw.DownlinkFrameItem items = 5;</code>
   */
  int getItemsCount();
  /**
   * <pre>
   * Downlink frame items.
   * This makes it possible to send multiple downlink opportunities to the
   * gateway at once (e.g. RX1 and RX2 in LoRaWAN). The first item has the
   * highest priority, the last the lowest. The gateway will emit at most
   * one item.
   * </pre>
   *
   * <code>repeated .gw.DownlinkFrameItem items = 5;</code>
   */
  java.util.List<? extends io.chirpstack.api.gw.DownlinkFrameItemOrBuilder> 
      getItemsOrBuilderList();
  /**
   * <pre>
   * Downlink frame items.
   * This makes it possible to send multiple downlink opportunities to the
   * gateway at once (e.g. RX1 and RX2 in LoRaWAN). The first item has the
   * highest priority, the last the lowest. The gateway will emit at most
   * one item.
   * </pre>
   *
   * <code>repeated .gw.DownlinkFrameItem items = 5;</code>
   */
  io.chirpstack.api.gw.DownlinkFrameItemOrBuilder getItemsOrBuilder(
      int index);

  /**
   * <pre>
   * Gateway ID.
   * Deprecated: use gateway_id
   * </pre>
   *
   * <code>bytes gateway_id_legacy = 6;</code>
   * @return The gatewayIdLegacy.
   */
  com.google.protobuf.ByteString getGatewayIdLegacy();

  /**
   * <pre>
   * Gateway ID.
   * </pre>
   *
   * <code>string gateway_id = 7;</code>
   * @return The gatewayId.
   */
  java.lang.String getGatewayId();
  /**
   * <pre>
   * Gateway ID.
   * </pre>
   *
   * <code>string gateway_id = 7;</code>
   * @return The bytes for gatewayId.
   */
  com.google.protobuf.ByteString
      getGatewayIdBytes();
}
