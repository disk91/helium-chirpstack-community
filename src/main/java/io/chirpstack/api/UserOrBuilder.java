// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: api/user.proto

// Protobuf Java Version: 3.25.1
package io.chirpstack.api;

public interface UserOrBuilder extends
    // @@protoc_insertion_point(interface_extends:api.User)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * User ID (UUID).
   * Will be set automatically on create.
   * </pre>
   *
   * <code>string id = 1;</code>
   * @return The id.
   */
  java.lang.String getId();
  /**
   * <pre>
   * User ID (UUID).
   * Will be set automatically on create.
   * </pre>
   *
   * <code>string id = 1;</code>
   * @return The bytes for id.
   */
  com.google.protobuf.ByteString
      getIdBytes();

  /**
   * <pre>
   * Set to true to make the user a global administrator.
   * </pre>
   *
   * <code>bool is_admin = 4;</code>
   * @return The isAdmin.
   */
  boolean getIsAdmin();

  /**
   * <pre>
   * Set to false to disable the user.
   * </pre>
   *
   * <code>bool is_active = 5;</code>
   * @return The isActive.
   */
  boolean getIsActive();

  /**
   * <pre>
   * E-mail of the user.
   * </pre>
   *
   * <code>string email = 6;</code>
   * @return The email.
   */
  java.lang.String getEmail();
  /**
   * <pre>
   * E-mail of the user.
   * </pre>
   *
   * <code>string email = 6;</code>
   * @return The bytes for email.
   */
  com.google.protobuf.ByteString
      getEmailBytes();

  /**
   * <pre>
   * Optional note to store with the user.
   * </pre>
   *
   * <code>string note = 7;</code>
   * @return The note.
   */
  java.lang.String getNote();
  /**
   * <pre>
   * Optional note to store with the user.
   * </pre>
   *
   * <code>string note = 7;</code>
   * @return The bytes for note.
   */
  com.google.protobuf.ByteString
      getNoteBytes();
}
