// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: api/internal.proto

// Protobuf Java Version: 3.25.1
package io.chirpstack.api;

public interface RegionListItemOrBuilder extends
    // @@protoc_insertion_point(interface_extends:api.RegionListItem)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * ID.
   * </pre>
   *
   * <code>string id = 1;</code>
   * @return The id.
   */
  java.lang.String getId();
  /**
   * <pre>
   * ID.
   * </pre>
   *
   * <code>string id = 1;</code>
   * @return The bytes for id.
   */
  com.google.protobuf.ByteString
      getIdBytes();

  /**
   * <pre>
   * Region.
   * </pre>
   *
   * <code>.common.Region region = 2;</code>
   * @return The enum numeric value on the wire for region.
   */
  int getRegionValue();
  /**
   * <pre>
   * Region.
   * </pre>
   *
   * <code>.common.Region region = 2;</code>
   * @return The region.
   */
  io.chirpstack.api.Region getRegion();

  /**
   * <pre>
   * Description.
   * </pre>
   *
   * <code>string description = 3;</code>
   * @return The description.
   */
  java.lang.String getDescription();
  /**
   * <pre>
   * Description.
   * </pre>
   *
   * <code>string description = 3;</code>
   * @return The bytes for description.
   */
  com.google.protobuf.ByteString
      getDescriptionBytes();
}
