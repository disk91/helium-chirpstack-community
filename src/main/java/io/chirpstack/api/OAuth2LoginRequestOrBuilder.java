// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: api/internal.proto
// Protobuf Java Version: 4.29.2

package io.chirpstack.api;

public interface OAuth2LoginRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:api.OAuth2LoginRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * OAuth2 callback code.
   * </pre>
   *
   * <code>string code = 1;</code>
   * @return The code.
   */
  java.lang.String getCode();
  /**
   * <pre>
   * OAuth2 callback code.
   * </pre>
   *
   * <code>string code = 1;</code>
   * @return The bytes for code.
   */
  com.google.protobuf.ByteString
      getCodeBytes();

  /**
   * <pre>
   * OAuth2 callback state.
   * </pre>
   *
   * <code>string state = 2;</code>
   * @return The state.
   */
  java.lang.String getState();
  /**
   * <pre>
   * OAuth2 callback state.
   * </pre>
   *
   * <code>string state = 2;</code>
   * @return The bytes for state.
   */
  com.google.protobuf.ByteString
      getStateBytes();
}
