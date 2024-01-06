// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: integration/integration.proto

package io.chirpstack.api.as.integration;

public interface StatusEventOrBuilder extends
    // @@protoc_insertion_point(interface_extends:integration.StatusEvent)
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
   * The demodulation signal-to-noise ratio in dB for the last successfully
   * received device-status request by the Network Server.
   * </pre>
   *
   * <code>int32 margin = 5;</code>
   * @return The margin.
   */
  int getMargin();

  /**
   * <pre>
   * Device is connected to an external power source.
   * </pre>
   *
   * <code>bool external_power_source = 6;</code>
   * @return The externalPowerSource.
   */
  boolean getExternalPowerSource();

  /**
   * <pre>
   * Battery level is not available.
   * </pre>
   *
   * <code>bool battery_level_unavailable = 7;</code>
   * @return The batteryLevelUnavailable.
   */
  boolean getBatteryLevelUnavailable();

  /**
   * <pre>
   * Battery level.
   * </pre>
   *
   * <code>float battery_level = 8;</code>
   * @return The batteryLevel.
   */
  float getBatteryLevel();
}