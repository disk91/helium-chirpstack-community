// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: service/iot_config.proto
// Protobuf Java Version: 4.29.2

package com.helium.grpc;

public interface org_disable_res_v1OrBuilder extends
    // @@protoc_insertion_point(interface_extends:helium.iot_config.org_disable_res_v1)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>uint64 oui = 1;</code>
   * @return The oui.
   */
  long getOui();

  /**
   * <pre>
   * in seconds since unix epoch
   * </pre>
   *
   * <code>uint64 timestamp = 2;</code>
   * @return The timestamp.
   */
  long getTimestamp();

  /**
   * <pre>
   * pubkey binary of the signing keypair
   * </pre>
   *
   * <code>bytes signer = 3;</code>
   * @return The signer.
   */
  com.google.protobuf.ByteString getSigner();

  /**
   * <pre>
   * Signature over the response by the config service
   * </pre>
   *
   * <code>bytes signature = 4;</code>
   * @return The signature.
   */
  com.google.protobuf.ByteString getSignature();
}
