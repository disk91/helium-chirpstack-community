// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: api/tenant.proto

// Protobuf Java Version: 3.25.1
package io.chirpstack.api;

public interface TenantUserListItemOrBuilder extends
    // @@protoc_insertion_point(interface_extends:api.TenantUserListItem)
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

  /**
   * <pre>
   * Created at timestamp.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp created_at = 3;</code>
   * @return Whether the createdAt field is set.
   */
  boolean hasCreatedAt();
  /**
   * <pre>
   * Created at timestamp.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp created_at = 3;</code>
   * @return The createdAt.
   */
  com.google.protobuf.Timestamp getCreatedAt();
  /**
   * <pre>
   * Created at timestamp.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp created_at = 3;</code>
   */
  com.google.protobuf.TimestampOrBuilder getCreatedAtOrBuilder();

  /**
   * <pre>
   * Last update timestamp.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp updated_at = 4;</code>
   * @return Whether the updatedAt field is set.
   */
  boolean hasUpdatedAt();
  /**
   * <pre>
   * Last update timestamp.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp updated_at = 4;</code>
   * @return The updatedAt.
   */
  com.google.protobuf.Timestamp getUpdatedAt();
  /**
   * <pre>
   * Last update timestamp.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp updated_at = 4;</code>
   */
  com.google.protobuf.TimestampOrBuilder getUpdatedAtOrBuilder();

  /**
   * <pre>
   * Email.
   * </pre>
   *
   * <code>string email = 5;</code>
   * @return The email.
   */
  java.lang.String getEmail();
  /**
   * <pre>
   * Email.
   * </pre>
   *
   * <code>string email = 5;</code>
   * @return The bytes for email.
   */
  com.google.protobuf.ByteString
      getEmailBytes();

  /**
   * <pre>
   * User is admin within the context of the tenant.
   * There is no need to set the is_device_admin and is_gateway_admin flags.
   * </pre>
   *
   * <code>bool is_admin = 6;</code>
   * @return The isAdmin.
   */
  boolean getIsAdmin();

  /**
   * <pre>
   * User is able to modify device related resources (applications,
   * device-profiles, devices, multicast-groups).
   * </pre>
   *
   * <code>bool is_device_admin = 7;</code>
   * @return The isDeviceAdmin.
   */
  boolean getIsDeviceAdmin();

  /**
   * <pre>
   * User is able to modify gateways.
   * </pre>
   *
   * <code>bool is_gateway_admin = 8;</code>
   * @return The isGatewayAdmin.
   */
  boolean getIsGatewayAdmin();
}
