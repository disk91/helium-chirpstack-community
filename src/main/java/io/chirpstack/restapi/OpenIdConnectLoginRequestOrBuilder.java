// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: api/internal.proto

package io.chirpstack.restapi;

public interface OpenIdConnectLoginRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:api.OpenIdConnectLoginRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * OpenId Connect callback code.
   * </pre>
   *
   * <code>string code = 1;</code>
   * @return The code.
   */
  String getCode();
  /**
   * <pre>
   * OpenId Connect callback code.
   * </pre>
   *
   * <code>string code = 1;</code>
   * @return The bytes for code.
   */
  com.google.protobuf.ByteString
      getCodeBytes();

  /**
   * <pre>
   * OpenId Connect callback state.
   * </pre>
   *
   * <code>string state = 2;</code>
   * @return The state.
   */
  String getState();
  /**
   * <pre>
   * OpenId Connect callback state.
   * </pre>
   *
   * <code>string state = 2;</code>
   * @return The bytes for state.
   */
  com.google.protobuf.ByteString
      getStateBytes();
}