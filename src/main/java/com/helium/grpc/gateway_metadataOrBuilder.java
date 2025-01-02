// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: service/iot_config.proto
// Protobuf Java Version: 4.29.2

package com.helium.grpc;

public interface gateway_metadataOrBuilder extends
    // @@protoc_insertion_point(interface_extends:helium.iot_config.gateway_metadata)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * &#47; The asserted h3 location of the gateway
   * </pre>
   *
   * <code>string location = 1;</code>
   * @return The location.
   */
  java.lang.String getLocation();
  /**
   * <pre>
   * &#47; The asserted h3 location of the gateway
   * </pre>
   *
   * <code>string location = 1;</code>
   * @return The bytes for location.
   */
  com.google.protobuf.ByteString
      getLocationBytes();

  /**
   * <pre>
   * &#47; LoRa region derived from the asserted location
   * </pre>
   *
   * <code>.helium.region region = 2;</code>
   * @return The enum numeric value on the wire for region.
   */
  int getRegionValue();
  /**
   * <pre>
   * &#47; LoRa region derived from the asserted location
   * </pre>
   *
   * <code>.helium.region region = 2;</code>
   * @return The region.
   */
  Region.region getRegion();

  /**
   * <pre>
   * &#47; the transmit gain value of the gateway in dbi x 10
   * / For example 1 dbi = 10, 15 dbi = 150
   * </pre>
   *
   * <code>int32 gain = 3;</code>
   * @return The gain.
   */
  int getGain();

  /**
   * <pre>
   * &#47; The asserted elevation of the gateway
   * </pre>
   *
   * <code>int32 elevation = 4;</code>
   * @return The elevation.
   */
  int getElevation();
}
