// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: api/internal.proto
// Protobuf Java Version: 4.29.2

package io.chirpstack.api;

public interface LogItemOrBuilder extends
    // @@protoc_insertion_point(interface_extends:api.LogItem)
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
   * Timestamp.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp time = 2;</code>
   * @return Whether the time field is set.
   */
  boolean hasTime();
  /**
   * <pre>
   * Timestamp.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp time = 2;</code>
   * @return The time.
   */
  com.google.protobuf.Timestamp getTime();
  /**
   * <pre>
   * Timestamp.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp time = 2;</code>
   */
  com.google.protobuf.TimestampOrBuilder getTimeOrBuilder();

  /**
   * <pre>
   * Message.
   * </pre>
   *
   * <code>string description = 3;</code>
   * @return The description.
   */
  java.lang.String getDescription();
  /**
   * <pre>
   * Message.
   * </pre>
   *
   * <code>string description = 3;</code>
   * @return The bytes for description.
   */
  com.google.protobuf.ByteString
      getDescriptionBytes();

  /**
   * <pre>
   * Body.
   * </pre>
   *
   * <code>string body = 4;</code>
   * @return The body.
   */
  java.lang.String getBody();
  /**
   * <pre>
   * Body.
   * </pre>
   *
   * <code>string body = 4;</code>
   * @return The bytes for body.
   */
  com.google.protobuf.ByteString
      getBodyBytes();

  /**
   * <pre>
   * Properties.
   * </pre>
   *
   * <code>map&lt;string, string&gt; properties = 5;</code>
   */
  int getPropertiesCount();
  /**
   * <pre>
   * Properties.
   * </pre>
   *
   * <code>map&lt;string, string&gt; properties = 5;</code>
   */
  boolean containsProperties(
      java.lang.String key);
  /**
   * Use {@link #getPropertiesMap()} instead.
   */
  @java.lang.Deprecated
  java.util.Map<java.lang.String, java.lang.String>
  getProperties();
  /**
   * <pre>
   * Properties.
   * </pre>
   *
   * <code>map&lt;string, string&gt; properties = 5;</code>
   */
  java.util.Map<java.lang.String, java.lang.String>
  getPropertiesMap();
  /**
   * <pre>
   * Properties.
   * </pre>
   *
   * <code>map&lt;string, string&gt; properties = 5;</code>
   */
  /* nullable */
java.lang.String getPropertiesOrDefault(
      java.lang.String key,
      /* nullable */
java.lang.String defaultValue);
  /**
   * <pre>
   * Properties.
   * </pre>
   *
   * <code>map&lt;string, string&gt; properties = 5;</code>
   */
  java.lang.String getPropertiesOrThrow(
      java.lang.String key);
}
