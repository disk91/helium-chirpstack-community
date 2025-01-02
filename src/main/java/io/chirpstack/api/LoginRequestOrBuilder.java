// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: api/internal.proto
// Protobuf Java Version: 4.29.2

package io.chirpstack.api;

public interface LoginRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:api.LoginRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Email of the user.
   * </pre>
   *
   * <code>string email = 1;</code>
   * @return The email.
   */
  java.lang.String getEmail();
  /**
   * <pre>
   * Email of the user.
   * </pre>
   *
   * <code>string email = 1;</code>
   * @return The bytes for email.
   */
  com.google.protobuf.ByteString
      getEmailBytes();

  /**
   * <pre>
   * Password of the user.
   * </pre>
   *
   * <code>string password = 2;</code>
   * @return The password.
   */
  java.lang.String getPassword();
  /**
   * <pre>
   * Password of the user.
   * </pre>
   *
   * <code>string password = 2;</code>
   * @return The bytes for password.
   */
  com.google.protobuf.ByteString
      getPasswordBytes();
}
