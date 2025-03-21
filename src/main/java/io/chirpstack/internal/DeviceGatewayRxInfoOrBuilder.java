// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: internal/internal.proto
// Protobuf Java Version: 4.29.2

package io.chirpstack.internal;

public interface DeviceGatewayRxInfoOrBuilder extends
    // @@protoc_insertion_point(interface_extends:internal.DeviceGatewayRxInfo)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * DevEUI (EUI64).
   * </pre>
   *
   * <code>bytes dev_eui = 1;</code>
   * @return The devEui.
   */
  com.google.protobuf.ByteString getDevEui();

  /**
   * <pre>
   * Data-rate.
   * </pre>
   *
   * <code>uint32 dr = 2;</code>
   * @return The dr.
   */
  int getDr();

  /**
   * <pre>
   * Gateway RxInfo elements.
   * </pre>
   *
   * <code>repeated .internal.DeviceGatewayRxInfoItem items = 3;</code>
   */
  java.util.List<io.chirpstack.internal.DeviceGatewayRxInfoItem> 
      getItemsList();
  /**
   * <pre>
   * Gateway RxInfo elements.
   * </pre>
   *
   * <code>repeated .internal.DeviceGatewayRxInfoItem items = 3;</code>
   */
  io.chirpstack.internal.DeviceGatewayRxInfoItem getItems(int index);
  /**
   * <pre>
   * Gateway RxInfo elements.
   * </pre>
   *
   * <code>repeated .internal.DeviceGatewayRxInfoItem items = 3;</code>
   */
  int getItemsCount();
  /**
   * <pre>
   * Gateway RxInfo elements.
   * </pre>
   *
   * <code>repeated .internal.DeviceGatewayRxInfoItem items = 3;</code>
   */
  java.util.List<? extends io.chirpstack.internal.DeviceGatewayRxInfoItemOrBuilder> 
      getItemsOrBuilderList();
  /**
   * <pre>
   * Gateway RxInfo elements.
   * </pre>
   *
   * <code>repeated .internal.DeviceGatewayRxInfoItem items = 3;</code>
   */
  io.chirpstack.internal.DeviceGatewayRxInfoItemOrBuilder getItemsOrBuilder(
      int index);
}
