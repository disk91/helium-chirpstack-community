// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: gw/gw.proto

// Protobuf Java Version: 3.25.1
package io.chirpstack.api.gw;

public interface DownlinkTxAckItemOrBuilder extends
    // @@protoc_insertion_point(interface_extends:gw.DownlinkTxAckItem)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * The Ack status of this item.
   * </pre>
   *
   * <code>.gw.TxAckStatus status = 1;</code>
   * @return The enum numeric value on the wire for status.
   */
  int getStatusValue();
  /**
   * <pre>
   * The Ack status of this item.
   * </pre>
   *
   * <code>.gw.TxAckStatus status = 1;</code>
   * @return The status.
   */
  io.chirpstack.api.gw.TxAckStatus getStatus();
}
