// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: gw/gw.proto

package io.chirpstack.api.gw;

public interface RawPacketForwarderCommandOrBuilder extends
    // @@protoc_insertion_point(interface_extends:gw.RawPacketForwarderCommand)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Gateway ID.
   * Deprecated: use gateway_id.
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
   * <code>string gateway_id = 4;</code>
   * @return The gatewayId.
   */
  java.lang.String getGatewayId();
  /**
   * <pre>
   * Gateway ID.
   * </pre>
   *
   * <code>string gateway_id = 4;</code>
   * @return The bytes for gatewayId.
   */
  com.google.protobuf.ByteString
      getGatewayIdBytes();

  /**
   * <pre>
   * Payload contains the raw payload.
   * </pre>
   *
   * <code>bytes payload = 3;</code>
   * @return The payload.
   */
  com.google.protobuf.ByteString getPayload();
}
