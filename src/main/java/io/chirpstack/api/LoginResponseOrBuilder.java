// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: api/internal.proto
// Protobuf Java Version: 4.29.2

package io.chirpstack.api;

public interface LoginResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:api.LoginResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * The JWT tag to be used to access chirpstack-application-server interfaces.
   * </pre>
   *
   * <code>string jwt = 1;</code>
   * @return The jwt.
   */
  java.lang.String getJwt();
  /**
   * <pre>
   * The JWT tag to be used to access chirpstack-application-server interfaces.
   * </pre>
   *
   * <code>string jwt = 1;</code>
   * @return The bytes for jwt.
   */
  com.google.protobuf.ByteString
      getJwtBytes();
}
