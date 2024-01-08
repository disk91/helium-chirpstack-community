// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: api/user.proto

// Protobuf Java Version: 3.25.1
package io.chirpstack.api;

public interface ListUsersResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:api.ListUsersResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Total number of users.
   * </pre>
   *
   * <code>uint32 total_count = 1;</code>
   * @return The totalCount.
   */
  int getTotalCount();

  /**
   * <pre>
   * Result-set.
   * </pre>
   *
   * <code>repeated .api.UserListItem result = 2;</code>
   */
  java.util.List<io.chirpstack.api.UserListItem> 
      getResultList();
  /**
   * <pre>
   * Result-set.
   * </pre>
   *
   * <code>repeated .api.UserListItem result = 2;</code>
   */
  io.chirpstack.api.UserListItem getResult(int index);
  /**
   * <pre>
   * Result-set.
   * </pre>
   *
   * <code>repeated .api.UserListItem result = 2;</code>
   */
  int getResultCount();
  /**
   * <pre>
   * Result-set.
   * </pre>
   *
   * <code>repeated .api.UserListItem result = 2;</code>
   */
  java.util.List<? extends io.chirpstack.api.UserListItemOrBuilder> 
      getResultOrBuilderList();
  /**
   * <pre>
   * Result-set.
   * </pre>
   *
   * <code>repeated .api.UserListItem result = 2;</code>
   */
  io.chirpstack.api.UserListItemOrBuilder getResultOrBuilder(
      int index);
}
