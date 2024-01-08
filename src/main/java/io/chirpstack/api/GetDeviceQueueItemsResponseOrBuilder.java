// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: api/device.proto

// Protobuf Java Version: 3.25.1
package io.chirpstack.api;

public interface GetDeviceQueueItemsResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:api.GetDeviceQueueItemsResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Total number of queue items.
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
   * <code>repeated .api.DeviceQueueItem result = 2;</code>
   */
  java.util.List<io.chirpstack.api.DeviceQueueItem> 
      getResultList();
  /**
   * <pre>
   * Result-set.
   * </pre>
   *
   * <code>repeated .api.DeviceQueueItem result = 2;</code>
   */
  io.chirpstack.api.DeviceQueueItem getResult(int index);
  /**
   * <pre>
   * Result-set.
   * </pre>
   *
   * <code>repeated .api.DeviceQueueItem result = 2;</code>
   */
  int getResultCount();
  /**
   * <pre>
   * Result-set.
   * </pre>
   *
   * <code>repeated .api.DeviceQueueItem result = 2;</code>
   */
  java.util.List<? extends io.chirpstack.api.DeviceQueueItemOrBuilder> 
      getResultOrBuilderList();
  /**
   * <pre>
   * Result-set.
   * </pre>
   *
   * <code>repeated .api.DeviceQueueItem result = 2;</code>
   */
  io.chirpstack.api.DeviceQueueItemOrBuilder getResultOrBuilder(
      int index);
}
