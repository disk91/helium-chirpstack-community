// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: service/iot_config.proto
// Protobuf Java Version: 4.29.2

package com.helium.grpc;

public interface skf_v1OrBuilder extends
    // @@protoc_insertion_point(interface_extends:helium.iot_config.skf_v1)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string route_id = 1;</code>
   * @return The routeId.
   */
  java.lang.String getRouteId();
  /**
   * <code>string route_id = 1;</code>
   * @return The bytes for routeId.
   */
  com.google.protobuf.ByteString
      getRouteIdBytes();

  /**
   * <code>uint32 devaddr = 2;</code>
   * @return The devaddr.
   */
  int getDevaddr();

  /**
   * <pre>
   * the hex-encoded string of the binary session key
   * </pre>
   *
   * <code>string session_key = 3;</code>
   * @return The sessionKey.
   */
  java.lang.String getSessionKey();
  /**
   * <pre>
   * the hex-encoded string of the binary session key
   * </pre>
   *
   * <code>string session_key = 3;</code>
   * @return The bytes for sessionKey.
   */
  com.google.protobuf.ByteString
      getSessionKeyBytes();

  /**
   * <code>uint32 max_copies = 4;</code>
   * @return The maxCopies.
   */
  int getMaxCopies();
}
