// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: api/user.proto

package io.chirpstack.restapi;

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
  io.chirpstack.restapi.User getUser();
  /**
   * <pre>
   * User object to create.
   * </pre>
   *
   * <code>.api.User user = 1;</code>
   */
  io.chirpstack.restapi.UserOrBuilder getUserOrBuilder();

  /**
   * <pre>
   * Password to set for the user.
   * </pre>
   *
   * <code>string password = 2;</code>
   * @return The password.
   */
  String getPassword();
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
  java.util.List<io.chirpstack.restapi.UserTenant> 
      getTenantsList();
  /**
   * <pre>
   * Add the user to the following tenants.
   * </pre>
   *
   * <code>repeated .api.UserTenant tenants = 3;</code>
   */
  io.chirpstack.restapi.UserTenant getTenants(int index);
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
  java.util.List<? extends io.chirpstack.restapi.UserTenantOrBuilder> 
      getTenantsOrBuilderList();
  /**
   * <pre>
   * Add the user to the following tenants.
   * </pre>
   *
   * <code>repeated .api.UserTenant tenants = 3;</code>
   */
  io.chirpstack.restapi.UserTenantOrBuilder getTenantsOrBuilder(
      int index);
}