// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: api/user.proto
// Protobuf Java Version: 4.29.2

package io.chirpstack.api;

public interface CreateUserRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:api.CreateUserRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * User object to create.
   * </pre>
   *
   * <code>.api.User user = 1;</code>
   * @return Whether the user field is set.
   */
  boolean hasUser();
  /**
   * <pre>
   * User object to create.
   * </pre>
   *
   * <code>.api.User user = 1;</code>
   * @return The user.
   */
  io.chirpstack.api.User getUser();
  /**
   * <pre>
   * User object to create.
   * </pre>
   *
   * <code>.api.User user = 1;</code>
   */
  io.chirpstack.api.UserOrBuilder getUserOrBuilder();

  /**
   * <pre>
   * Password to set for the user.
   * </pre>
   *
   * <code>string password = 2;</code>
   * @return The password.
   */
  java.lang.String getPassword();
  /**
   * <pre>
   * Password to set for the user.
   * </pre>
   *
   * <code>string password = 2;</code>
   * @return The bytes for password.
   */
  com.google.protobuf.ByteString
      getPasswordBytes();

  /**
   * <pre>
   * Add the user to the following tenants.
   * </pre>
   *
   * <code>repeated .api.UserTenant tenants = 3;</code>
   */
  java.util.List<io.chirpstack.api.UserTenant> 
      getTenantsList();
  /**
   * <pre>
   * Add the user to the following tenants.
   * </pre>
   *
   * <code>repeated .api.UserTenant tenants = 3;</code>
   */
  io.chirpstack.api.UserTenant getTenants(int index);
  /**
   * <pre>
   * Add the user to the following tenants.
   * </pre>
   *
   * <code>repeated .api.UserTenant tenants = 3;</code>
   */
  int getTenantsCount();
  /**
   * <pre>
   * Add the user to the following tenants.
   * </pre>
   *
   * <code>repeated .api.UserTenant tenants = 3;</code>
   */
  java.util.List<? extends io.chirpstack.api.UserTenantOrBuilder> 
      getTenantsOrBuilderList();
  /**
   * <pre>
   * Add the user to the following tenants.
   * </pre>
   *
   * <code>repeated .api.UserTenant tenants = 3;</code>
   */
  io.chirpstack.api.UserTenantOrBuilder getTenantsOrBuilder(
      int index);
}
