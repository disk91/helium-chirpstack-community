// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: gw/gw.proto
// Protobuf Java Version: 4.29.2

package io.chirpstack.api.gw;

public interface MeshHeartbeatOrBuilder extends
    // @@protoc_insertion_point(interface_extends:gw.MeshHeartbeat)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Gateway ID (of the Border Gateway).
   * </pre>
   *
   * <code>string gateway_id = 1;</code>
   * @return The gatewayId.
   */
  java.lang.String getGatewayId();
  /**
   * <pre>
   * Gateway ID (of the Border Gateway).
   * </pre>
   *
   * <code>string gateway_id = 1;</code>
   * @return The bytes for gatewayId.
   */
  com.google.protobuf.ByteString
      getGatewayIdBytes();

  /**
   * <pre>
   * Relay ID.
   * </pre>
   *
   * <code>string relay_id = 2;</code>
   * @return The relayId.
   */
  java.lang.String getRelayId();
  /**
   * <pre>
   * Relay ID.
   * </pre>
   *
   * <code>string relay_id = 2;</code>
   * @return The bytes for relayId.
   */
  com.google.protobuf.ByteString
      getRelayIdBytes();

  /**
   * <pre>
   * Timestamp (second precision).
   * </pre>
   *
   * <code>.google.protobuf.Timestamp time = 3;</code>
   * @return Whether the time field is set.
   */
  boolean hasTime();
  /**
   * <pre>
   * Timestamp (second precision).
   * </pre>
   *
   * <code>.google.protobuf.Timestamp time = 3;</code>
   * @return The time.
   */
  com.google.protobuf.Timestamp getTime();
  /**
   * <pre>
   * Timestamp (second precision).
   * </pre>
   *
   * <code>.google.protobuf.Timestamp time = 3;</code>
   */
  com.google.protobuf.TimestampOrBuilder getTimeOrBuilder();

  /**
   * <pre>
   * Relay path.
   * </pre>
   *
   * <code>repeated .gw.MeshHeartbeatRelayPath relay_path = 4;</code>
   */
  java.util.List<io.chirpstack.api.gw.MeshHeartbeatRelayPath> 
      getRelayPathList();
  /**
   * <pre>
   * Relay path.
   * </pre>
   *
   * <code>repeated .gw.MeshHeartbeatRelayPath relay_path = 4;</code>
   */
  io.chirpstack.api.gw.MeshHeartbeatRelayPath getRelayPath(int index);
  /**
   * <pre>
   * Relay path.
   * </pre>
   *
   * <code>repeated .gw.MeshHeartbeatRelayPath relay_path = 4;</code>
   */
  int getRelayPathCount();
  /**
   * <pre>
   * Relay path.
   * </pre>
   *
   * <code>repeated .gw.MeshHeartbeatRelayPath relay_path = 4;</code>
   */
  java.util.List<? extends io.chirpstack.api.gw.MeshHeartbeatRelayPathOrBuilder> 
      getRelayPathOrBuilderList();
  /**
   * <pre>
   * Relay path.
   * </pre>
   *
   * <code>repeated .gw.MeshHeartbeatRelayPath relay_path = 4;</code>
   */
  io.chirpstack.api.gw.MeshHeartbeatRelayPathOrBuilder getRelayPathOrBuilder(
      int index);
}