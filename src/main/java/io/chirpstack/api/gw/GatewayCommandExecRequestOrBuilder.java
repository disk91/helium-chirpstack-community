// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: gw/gw.proto

package io.chirpstack.api.gw;

public interface GatewayCommandExecRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:gw.GatewayCommandExecRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Gateway ID.
   * Deprecated: use gateway_id.
   * </pre>
   *
   * <code>bytes gateway_id_legacy = 1;</code>
   * @return The gatewayIdLegacy.
   */
  com.google.protobuf.ByteString getGatewayIdLegacy();

  /**
   * <pre>
   * Gateway ID.
   * </pre>
   *
   * <code>string gateway_id = 6;</code>
   * @return The gatewayId.
   */
  java.lang.String getGatewayId();
  /**
   * <pre>
   * Gateway ID.
   * </pre>
   *
   * <code>string gateway_id = 6;</code>
   * @return The bytes for gatewayId.
   */
  com.google.protobuf.ByteString
      getGatewayIdBytes();

  /**
   * <pre>
   * Command to execute.
   * This command must be pre-configured in the LoRa Gateway Bridge
   * configuration.
   * </pre>
   *
   * <code>string command = 2;</code>
   * @return The command.
   */
  java.lang.String getCommand();
  /**
   * <pre>
   * Command to execute.
   * This command must be pre-configured in the LoRa Gateway Bridge
   * configuration.
   * </pre>
   *
   * <code>string command = 2;</code>
   * @return The bytes for command.
   */
  com.google.protobuf.ByteString
      getCommandBytes();

  /**
   * <pre>
   * Execution request ID.
   * The same will be returned when the execution of the command has
   * completed.
   * </pre>
   *
   * <code>uint32 exec_id = 7;</code>
   * @return The execId.
   */
  int getExecId();

  /**
   * <pre>
   * Standard input.
   * </pre>
   *
   * <code>bytes stdin = 4;</code>
   * @return The stdin.
   */
  com.google.protobuf.ByteString getStdin();

  /**
   * <pre>
   * Environment variables.
   * </pre>
   *
   * <code>map&lt;string, string&gt; environment = 5;</code>
   */
  int getEnvironmentCount();
  /**
   * <pre>
   * Environment variables.
   * </pre>
   *
   * <code>map&lt;string, string&gt; environment = 5;</code>
   */
  boolean containsEnvironment(
      java.lang.String key);
  /**
   * Use {@link #getEnvironmentMap()} instead.
   */
  @java.lang.Deprecated
  java.util.Map<java.lang.String, java.lang.String>
  getEnvironment();
  /**
   * <pre>
   * Environment variables.
   * </pre>
   *
   * <code>map&lt;string, string&gt; environment = 5;</code>
   */
  java.util.Map<java.lang.String, java.lang.String>
  getEnvironmentMap();
  /**
   * <pre>
   * Environment variables.
   * </pre>
   *
   * <code>map&lt;string, string&gt; environment = 5;</code>
   */
  /* nullable */
java.lang.String getEnvironmentOrDefault(
      java.lang.String key,
      /* nullable */
java.lang.String defaultValue);
  /**
   * <pre>
   * Environment variables.
   * </pre>
   *
   * <code>map&lt;string, string&gt; environment = 5;</code>
   */
  java.lang.String getEnvironmentOrThrow(
      java.lang.String key);
}
