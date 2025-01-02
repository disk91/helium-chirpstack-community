// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: gw/gw.proto
// Protobuf Java Version: 4.29.2

package io.chirpstack.api.gw;

public interface FskModulationConfigOrBuilder extends
    // @@protoc_insertion_point(interface_extends:gw.FskModulationConfig)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Bandwidth (kHz).
   * Deprecated: use bandwidth.
   * </pre>
   *
   * <code>uint32 bandwidth_legacy = 1;</code>
   * @return The bandwidthLegacy.
   */
  int getBandwidthLegacy();

  /**
   * <pre>
   * Bandwidth (Hz).
   * </pre>
   *
   * <code>uint32 bandwidth = 3;</code>
   * @return The bandwidth.
   */
  int getBandwidth();

  /**
   * <pre>
   * Bitrate.
   * </pre>
   *
   * <code>uint32 bitrate = 2;</code>
   * @return The bitrate.
   */
  int getBitrate();
}
