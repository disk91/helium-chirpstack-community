// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: api/device.proto
// Protobuf Java Version: 4.29.2

package io.chirpstack.api;

public interface GetDeviceQueueItemsRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:api.GetDeviceQueueItemsRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Device EUI (EUI64).
   * </pre>
   *
   * <code>string dev_eui = 1;</code>
   * @return The devEui.
   */
  java.lang.String getDevEui();
  /**
   * <pre>
   * Device EUI (EUI64).
   * </pre>
   *
   * <code>string dev_eui = 1;</code>
   * @return The bytes for devEui.
   */
  com.google.protobuf.ByteString
      getDevEuiBytes();

  /**
   * <pre>
   * Return only the count, not the result-set.
   * </pre>
   *
   * <code>bool count_only = 2;</code>
   * @return The countOnly.
   */
  boolean getCountOnly();
}
