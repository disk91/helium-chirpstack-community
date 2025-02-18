// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: api/tenant.proto
// Protobuf Java Version: 4.29.2

package io.chirpstack.api;

public interface GetTenantUserRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:api.GetTenantUserRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Tenant ID (UUID).
   * </pre>
   *
   * <code>string tenant_id = 1;</code>
   * @return The tenantId.
   */
  java.lang.String getTenantId();
  /**
   * <pre>
   * Tenant ID (UUID).
   * </pre>
   *
   * <code>string tenant_id = 1;</code>
   * @return The bytes for tenantId.
   */
  com.google.protobuf.ByteString
      getTenantIdBytes();

  /**
   * <pre>
   * User ID (UUID).
   * </pre>
   *
   * <code>string user_id = 2;</code>
   * @return The userId.
   */
  java.lang.String getUserId();
  /**
   * <pre>
   * User ID (UUID).
   * </pre>
   *
   * <code>string user_id = 2;</code>
   * @return The bytes for userId.
   */
  com.google.protobuf.ByteString
      getUserIdBytes();
}
