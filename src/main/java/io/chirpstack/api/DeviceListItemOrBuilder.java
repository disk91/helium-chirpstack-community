// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: api/device.proto

package io.chirpstack.api;

public interface DeviceListItemOrBuilder extends
    // @@protoc_insertion_point(interface_extends:api.DeviceListItem)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * DevEUI (EUI64).
   * </pre>
   *
   * <code>string dev_eui = 1;</code>
   * @return The devEui.
   */
  String getDevEui();
  /**
   * <pre>
   * DevEUI (EUI64).
   * </pre>
   *
   * <code>string dev_eui = 1;</code>
   * @return The bytes for devEui.
   */
  com.google.protobuf.ByteString
      getDevEuiBytes();

  /**
   * <pre>
   * Created at timestamp.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp created_at = 2;</code>
   * @return Whether the createdAt field is set.
   */
  boolean hasCreatedAt();
  /**
   * <pre>
   * Created at timestamp.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp created_at = 2;</code>
   * @return The createdAt.
   */
  com.google.protobuf.Timestamp getCreatedAt();
  /**
   * <pre>
   * Created at timestamp.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp created_at = 2;</code>
   */
  com.google.protobuf.TimestampOrBuilder getCreatedAtOrBuilder();

  /**
   * <pre>
   * Last update timestamp.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp updated_at = 3;</code>
   * @return Whether the updatedAt field is set.
   */
  boolean hasUpdatedAt();
  /**
   * <pre>
   * Last update timestamp.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp updated_at = 3;</code>
   * @return The updatedAt.
   */
  com.google.protobuf.Timestamp getUpdatedAt();
  /**
   * <pre>
   * Last update timestamp.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp updated_at = 3;</code>
   */
  com.google.protobuf.TimestampOrBuilder getUpdatedAtOrBuilder();

  /**
   * <pre>
   * Last seen at timestamp.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp last_seen_at = 4;</code>
   * @return Whether the lastSeenAt field is set.
   */
  boolean hasLastSeenAt();
  /**
   * <pre>
   * Last seen at timestamp.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp last_seen_at = 4;</code>
   * @return The lastSeenAt.
   */
  com.google.protobuf.Timestamp getLastSeenAt();
  /**
   * <pre>
   * Last seen at timestamp.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp last_seen_at = 4;</code>
   */
  com.google.protobuf.TimestampOrBuilder getLastSeenAtOrBuilder();

  /**
   * <pre>
   * Name.
   * </pre>
   *
   * <code>string name = 5;</code>
   * @return The name.
   */
  String getName();
  /**
   * <pre>
   * Name.
   * </pre>
   *
   * <code>string name = 5;</code>
   * @return The bytes for name.
   */
  com.google.protobuf.ByteString
      getNameBytes();

  /**
   * <pre>
   * Description.
   * </pre>
   *
   * <code>string description = 6;</code>
   * @return The description.
   */
  String getDescription();
  /**
   * <pre>
   * Description.
   * </pre>
   *
   * <code>string description = 6;</code>
   * @return The bytes for description.
   */
  com.google.protobuf.ByteString
      getDescriptionBytes();

  /**
   * <pre>
   * Device-profile ID (UUID).
   * </pre>
   *
   * <code>string device_profile_id = 7;</code>
   * @return The deviceProfileId.
   */
  String getDeviceProfileId();
  /**
   * <pre>
   * Device-profile ID (UUID).
   * </pre>
   *
   * <code>string device_profile_id = 7;</code>
   * @return The bytes for deviceProfileId.
   */
  com.google.protobuf.ByteString
      getDeviceProfileIdBytes();

  /**
   * <pre>
   * Device-profile name.
   * </pre>
   *
   * <code>string device_profile_name = 8;</code>
   * @return The deviceProfileName.
   */
  String getDeviceProfileName();
  /**
   * <pre>
   * Device-profile name.
   * </pre>
   *
   * <code>string device_profile_name = 8;</code>
   * @return The bytes for deviceProfileName.
   */
  com.google.protobuf.ByteString
      getDeviceProfileNameBytes();

  /**
   * <pre>
   * Device status.
   * </pre>
   *
   * <code>.api.DeviceStatus device_status = 9;</code>
   * @return Whether the deviceStatus field is set.
   */
  boolean hasDeviceStatus();
  /**
   * <pre>
   * Device status.
   * </pre>
   *
   * <code>.api.DeviceStatus device_status = 9;</code>
   * @return The deviceStatus.
   */
  DeviceStatus getDeviceStatus();
  /**
   * <pre>
   * Device status.
   * </pre>
   *
   * <code>.api.DeviceStatus device_status = 9;</code>
   */
  DeviceStatusOrBuilder getDeviceStatusOrBuilder();
}