// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: service/iot_config.proto
// Protobuf Java Version: 4.29.2

package com.helium.grpc;

public interface route_update_devaddr_ranges_req_v1OrBuilder extends
    // @@protoc_insertion_point(interface_extends:helium.iot_config.route_update_devaddr_ranges_req_v1)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.helium.iot_config.action_v1 action = 1;</code>
   * @return The enum numeric value on the wire for action.
   */
  int getActionValue();
  /**
   * <code>.helium.iot_config.action_v1 action = 1;</code>
   * @return The action.
   */
  com.helium.grpc.action_v1 getAction();

  /**
   * <code>.helium.iot_config.devaddr_range_v1 devaddr_range = 2;</code>
   * @return Whether the devaddrRange field is set.
   */
  boolean hasDevaddrRange();
  /**
   * <code>.helium.iot_config.devaddr_range_v1 devaddr_range = 2;</code>
   * @return The devaddrRange.
   */
  com.helium.grpc.devaddr_range_v1 getDevaddrRange();
  /**
   * <code>.helium.iot_config.devaddr_range_v1 devaddr_range = 2;</code>
   */
  com.helium.grpc.devaddr_range_v1OrBuilder getDevaddrRangeOrBuilder();

  /**
   * <pre>
   * in milliseconds since unix epoch
   * </pre>
   *
   * <code>uint64 timestamp = 3;</code>
   * @return The timestamp.
   */
  long getTimestamp();

  /**
   * <code>bytes signature = 4;</code>
   * @return The signature.
   */
  com.google.protobuf.ByteString getSignature();

  /**
   * <pre>
   * pubkey binary of the signing keypair
   * </pre>
   *
   * <code>bytes signer = 5;</code>
   * @return The signer.
   */
  com.google.protobuf.ByteString getSigner();
}
