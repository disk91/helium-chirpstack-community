// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: api/internal.proto
// Protobuf Java Version: 4.29.2

package io.chirpstack.api;

public interface ApiKeyOrBuilder extends
    // @@protoc_insertion_point(interface_extends:api.ApiKey)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * API key ID.
   * This value will be automatically generated on create.
   * </pre>
   *
   * <code>string id = 1;</code>
   * @return The id.
   */
  java.lang.String getId();
  /**
   * <pre>
   * API key ID.
   * This value will be automatically generated on create.
   * </pre>
   *
   * <code>string id = 1;</code>
   * @return The bytes for id.
   */
  com.google.protobuf.ByteString
      getIdBytes();

  /**
   * <pre>
   * Name.
   * </pre>
   *
   * <code>string name = 2;</code>
   * @return The name.
   */
  java.lang.String getName();
  /**
   * <pre>
   * Name.
   * </pre>
   *
   * <code>string name = 2;</code>
   * @return The bytes for name.
   */
  com.google.protobuf.ByteString
      getNameBytes();

  /**
   * <pre>
   * Is global admin key.
   * </pre>
   *
   * <code>bool is_admin = 3;</code>
   * @return The isAdmin.
   */
  boolean getIsAdmin();

  /**
   * <pre>
   * Tenant ID.
   * In case the API key is intended to manage resources under a single tenant.
   * </pre>
   *
   * <code>string tenant_id = 4;</code>
   * @return The tenantId.
   */
  java.lang.String getTenantId();
  /**
   * <pre>
   * Tenant ID.
   * In case the API key is intended to manage resources under a single tenant.
   * </pre>
   *
   * <code>string tenant_id = 4;</code>
   * @return The bytes for tenantId.
   */
  com.google.protobuf.ByteString
      getTenantIdBytes();
}
