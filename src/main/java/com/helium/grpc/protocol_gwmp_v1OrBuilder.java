// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: service/iot_config.proto
// Protobuf Java Version: 4.29.2

package com.helium.grpc;

public interface protocol_gwmp_v1OrBuilder extends
    // @@protoc_insertion_point(interface_extends:helium.iot_config.protocol_gwmp_v1)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>repeated .helium.iot_config.protocol_gwmp_mapping_v1 mapping = 1;</code>
   */
  java.util.List<com.helium.grpc.protocol_gwmp_mapping_v1> 
      getMappingList();
  /**
   * <code>repeated .helium.iot_config.protocol_gwmp_mapping_v1 mapping = 1;</code>
   */
  com.helium.grpc.protocol_gwmp_mapping_v1 getMapping(int index);
  /**
   * <code>repeated .helium.iot_config.protocol_gwmp_mapping_v1 mapping = 1;</code>
   */
  int getMappingCount();
  /**
   * <code>repeated .helium.iot_config.protocol_gwmp_mapping_v1 mapping = 1;</code>
   */
  java.util.List<? extends com.helium.grpc.protocol_gwmp_mapping_v1OrBuilder> 
      getMappingOrBuilderList();
  /**
   * <code>repeated .helium.iot_config.protocol_gwmp_mapping_v1 mapping = 1;</code>
   */
  com.helium.grpc.protocol_gwmp_mapping_v1OrBuilder getMappingOrBuilder(
      int index);
}
