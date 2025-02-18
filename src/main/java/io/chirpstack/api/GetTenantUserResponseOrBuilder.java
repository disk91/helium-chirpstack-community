// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: api/tenant.proto
// Protobuf Java Version: 4.29.2

package io.chirpstack.api;

public interface GetTenantUserResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:api.GetTenantUserResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Tenant user object.
   * </pre>
   *
   * <code>.api.TenantUser tenant_user = 1;</code>
   * @return Whether the tenantUser field is set.
   */
  boolean hasTenantUser();
  /**
   * <pre>
   * Tenant user object.
   * </pre>
   *
   * <code>.api.TenantUser tenant_user = 1;</code>
   * @return The tenantUser.
   */
  io.chirpstack.api.TenantUser getTenantUser();
  /**
   * <pre>
   * Tenant user object.
   * </pre>
   *
   * <code>.api.TenantUser tenant_user = 1;</code>
   */
  io.chirpstack.api.TenantUserOrBuilder getTenantUserOrBuilder();

  /**
   * <pre>
   * Created at timestamp.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp created_at = 2;</code>
   * @return Whether the createdAt field is set.
   */
  boolean hasCreatedAt();
  /**
   * <pre>
   * Created at timestamp.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp created_at = 2;</code>
   * @return The createdAt.
   */
  com.google.protobuf.Timestamp getCreatedAt();
  /**
   * <pre>
   * Created at timestamp.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp created_at = 2;</code>
   */
  com.google.protobuf.TimestampOrBuilder getCreatedAtOrBuilder();

  /**
   * <pre>
   * Last update timestamp.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp updated_at = 3;</code>
   * @return Whether the updatedAt field is set.
   */
  boolean hasUpdatedAt();
  /**
   * <pre>
   * Last update timestamp.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp updated_at = 3;</code>
   * @return The updatedAt.
   */
  com.google.protobuf.Timestamp getUpdatedAt();
  /**
   * <pre>
   * Last update timestamp.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp updated_at = 3;</code>
   */
  com.google.protobuf.TimestampOrBuilder getUpdatedAtOrBuilder();
}
