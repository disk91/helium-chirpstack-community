// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: service/iot_config.proto
// Protobuf Java Version: 4.29.2

package com.helium.grpc;

public interface admin_add_key_req_v1OrBuilder extends
    // @@protoc_insertion_point(interface_extends:helium.iot_config.admin_add_key_req_v1)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>bytes pubkey = 1;</code>
   * @return The pubkey.
   */
  com.google.protobuf.ByteString getPubkey();

  /**
   * <code>.helium.iot_config.admin_add_key_req_v1.key_type_v1 key_type = 2;</code>
   * @return The enum numeric value on the wire for keyType.
   */
  int getKeyTypeValue();
  /**
   * <code>.helium.iot_config.admin_add_key_req_v1.key_type_v1 key_type = 2;</code>
   * @return The keyType.
   */
  com.helium.grpc.admin_add_key_req_v1.key_type_v1 getKeyType();

  /**
   * <pre>
   * Signature of the request message signed by an admin key
   * already registered to the config service
   * </pre>
   *
   * <code>bytes signature = 3;</code>
   * @return The signature.
   */
  com.google.protobuf.ByteString getSignature();

  /**
   * <pre>
   * pubkey binary of the signing keypair
   * </pre>
   *
   * <code>bytes signer = 4;</code>
   * @return The signer.
   */
  com.google.protobuf.ByteString getSigner();
}
