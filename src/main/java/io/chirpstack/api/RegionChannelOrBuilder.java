// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: api/internal.proto

// Protobuf Java Version: 3.25.1
package io.chirpstack.api;

public interface RegionChannelOrBuilder extends
    // @@protoc_insertion_point(interface_extends:api.RegionChannel)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Frequency (Hz).
   * </pre>
   *
   * <code>uint32 frequency = 1;</code>
   * @return The frequency.
   */
  int getFrequency();

  /**
   * <pre>
   * Min DR.
   * </pre>
   *
   * <code>uint32 dr_min = 2;</code>
   * @return The drMin.
   */
  int getDrMin();

  /**
   * <pre>
   * Max DR.
   * </pre>
   *
   * <code>uint32 dr_max = 3;</code>
   * @return The drMax.
   */
  int getDrMax();
}
