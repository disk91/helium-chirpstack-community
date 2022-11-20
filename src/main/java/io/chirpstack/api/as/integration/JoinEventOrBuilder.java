// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: integration/integration.proto

package io.chirpstack.api.as.integration;

public interface JoinEventOrBuilder extends
    // @@protoc_insertion_point(interface_extends:integration.JoinEvent)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Deduplication ID (UUID).
   * </pre>
   *
   * <code>string deduplication_id = 1;</code>
   * @return The deduplicationId.
   */
  String getDeduplicationId();
  /**
   * <pre>
   * Deduplication ID (UUID).
   * </pre>
   *
   * <code>string deduplication_id = 1;</code>
   * @return The bytes for deduplicationId.
   */
  com.google.protobuf.ByteString
      getDeduplicationIdBytes();

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
   * Device address.
   * </pre>
   *
   * <code>string dev_addr = 4;</code>
   * @return The devAddr.
   */
  String getDevAddr();
  /**
   * <pre>
   * Device address.
   * </pre>
   *
   * <code>string dev_addr = 4;</code>
   * @return The bytes for devAddr.
   */
  com.google.protobuf.ByteString
      getDevAddrBytes();
}
