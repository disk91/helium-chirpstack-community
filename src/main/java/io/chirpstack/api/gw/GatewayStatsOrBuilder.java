// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: gw/gw.proto

package io.chirpstack.api.gw;

public interface GatewayStatsOrBuilder extends
    // @@protoc_insertion_point(interface_extends:gw.GatewayStats)
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
   * <code>string gateway_id = 17;</code>
   * @return The gatewayId.
   */
  String getGatewayId();
  /**
   * <pre>
   * Gateway ID.
   * </pre>
   *
   * <code>string gateway_id = 17;</code>
   * @return The bytes for gatewayId.
   */
  com.google.protobuf.ByteString
      getGatewayIdBytes();

  /**
   * <pre>
   * Gateway time.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp time = 2;</code>
   * @return Whether the time field is set.
   */
  boolean hasTime();
  /**
   * <pre>
   * Gateway time.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp time = 2;</code>
   * @return The time.
   */
  com.google.protobuf.Timestamp getTime();
  /**
   * <pre>
   * Gateway time.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp time = 2;</code>
   */
  com.google.protobuf.TimestampOrBuilder getTimeOrBuilder();

  /**
   * <pre>
   * Gateway location.
   * </pre>
   *
   * <code>.common.Location location = 3;</code>
   * @return Whether the location field is set.
   */
  boolean hasLocation();
  /**
   * <pre>
   * Gateway location.
   * </pre>
   *
   * <code>.common.Location location = 3;</code>
   * @return The location.
   */
  io.chirpstack.api.Location getLocation();
  /**
   * <pre>
   * Gateway location.
   * </pre>
   *
   * <code>.common.Location location = 3;</code>
   */
  io.chirpstack.api.LocationOrBuilder getLocationOrBuilder();

  /**
   * <pre>
   * Gateway configuration version (this maps to the config_version sent
   * by ChirpStack to the gateway).
   * </pre>
   *
   * <code>string config_version = 4;</code>
   * @return The configVersion.
   */
  String getConfigVersion();
  /**
   * <pre>
   * Gateway configuration version (this maps to the config_version sent
   * by ChirpStack to the gateway).
   * </pre>
   *
   * <code>string config_version = 4;</code>
   * @return The bytes for configVersion.
   */
  com.google.protobuf.ByteString
      getConfigVersionBytes();

  /**
   * <pre>
   * Number of radio packets received.
   * </pre>
   *
   * <code>uint32 rx_packets_received = 5;</code>
   * @return The rxPacketsReceived.
   */
  int getRxPacketsReceived();

  /**
   * <pre>
   * Number of radio packets received with valid PHY CRC.
   * </pre>
   *
   * <code>uint32 rx_packets_received_ok = 6;</code>
   * @return The rxPacketsReceivedOk.
   */
  int getRxPacketsReceivedOk();

  /**
   * <pre>
   * Number of downlink packets received for transmission.
   * </pre>
   *
   * <code>uint32 tx_packets_received = 7;</code>
   * @return The txPacketsReceived.
   */
  int getTxPacketsReceived();

  /**
   * <pre>
   * Number of downlink packets emitted.
   * </pre>
   *
   * <code>uint32 tx_packets_emitted = 8;</code>
   * @return The txPacketsEmitted.
   */
  int getTxPacketsEmitted();

  /**
   * <pre>
   * Additional gateway meta-data.
   * </pre>
   *
   * <code>map&lt;string, string&gt; meta_data = 10;</code>
   */
  int getMetaDataCount();
  /**
   * <pre>
   * Additional gateway meta-data.
   * </pre>
   *
   * <code>map&lt;string, string&gt; meta_data = 10;</code>
   */
  boolean containsMetaData(
      String key);
  /**
   * Use {@link #getMetaDataMap()} instead.
   */
  @Deprecated
  java.util.Map<String, String>
  getMetaData();
  /**
   * <pre>
   * Additional gateway meta-data.
   * </pre>
   *
   * <code>map&lt;string, string&gt; meta_data = 10;</code>
   */
  java.util.Map<String, String>
  getMetaDataMap();
  /**
   * <pre>
   * Additional gateway meta-data.
   * </pre>
   *
   * <code>map&lt;string, string&gt; meta_data = 10;</code>
   */

  /* nullable */
String getMetaDataOrDefault(
      String key,
      /* nullable */
String defaultValue);
  /**
   * <pre>
   * Additional gateway meta-data.
   * </pre>
   *
   * <code>map&lt;string, string&gt; meta_data = 10;</code>
   */

  String getMetaDataOrThrow(
      String key);

  /**
   * <pre>
   * Tx packets per frequency.
   * </pre>
   *
   * <code>map&lt;uint32, uint32&gt; tx_packets_per_frequency = 12;</code>
   */
  int getTxPacketsPerFrequencyCount();
  /**
   * <pre>
   * Tx packets per frequency.
   * </pre>
   *
   * <code>map&lt;uint32, uint32&gt; tx_packets_per_frequency = 12;</code>
   */
  boolean containsTxPacketsPerFrequency(
      int key);
  /**
   * Use {@link #getTxPacketsPerFrequencyMap()} instead.
   */
  @Deprecated
  java.util.Map<Integer, Integer>
  getTxPacketsPerFrequency();
  /**
   * <pre>
   * Tx packets per frequency.
   * </pre>
   *
   * <code>map&lt;uint32, uint32&gt; tx_packets_per_frequency = 12;</code>
   */
  java.util.Map<Integer, Integer>
  getTxPacketsPerFrequencyMap();
  /**
   * <pre>
   * Tx packets per frequency.
   * </pre>
   *
   * <code>map&lt;uint32, uint32&gt; tx_packets_per_frequency = 12;</code>
   */

  int getTxPacketsPerFrequencyOrDefault(
      int key,
      int defaultValue);
  /**
   * <pre>
   * Tx packets per frequency.
   * </pre>
   *
   * <code>map&lt;uint32, uint32&gt; tx_packets_per_frequency = 12;</code>
   */

  int getTxPacketsPerFrequencyOrThrow(
      int key);

  /**
   * <pre>
   * Rx packets per frequency.
   * </pre>
   *
   * <code>map&lt;uint32, uint32&gt; rx_packets_per_frequency = 13;</code>
   */
  int getRxPacketsPerFrequencyCount();
  /**
   * <pre>
   * Rx packets per frequency.
   * </pre>
   *
   * <code>map&lt;uint32, uint32&gt; rx_packets_per_frequency = 13;</code>
   */
  boolean containsRxPacketsPerFrequency(
      int key);
  /**
   * Use {@link #getRxPacketsPerFrequencyMap()} instead.
   */
  @Deprecated
  java.util.Map<Integer, Integer>
  getRxPacketsPerFrequency();
  /**
   * <pre>
   * Rx packets per frequency.
   * </pre>
   *
   * <code>map&lt;uint32, uint32&gt; rx_packets_per_frequency = 13;</code>
   */
  java.util.Map<Integer, Integer>
  getRxPacketsPerFrequencyMap();
  /**
   * <pre>
   * Rx packets per frequency.
   * </pre>
   *
   * <code>map&lt;uint32, uint32&gt; rx_packets_per_frequency = 13;</code>
   */

  int getRxPacketsPerFrequencyOrDefault(
      int key,
      int defaultValue);
  /**
   * <pre>
   * Rx packets per frequency.
   * </pre>
   *
   * <code>map&lt;uint32, uint32&gt; rx_packets_per_frequency = 13;</code>
   */

  int getRxPacketsPerFrequencyOrThrow(
      int key);

  /**
   * <pre>
   * Tx packets per modulation parameters.
   * </pre>
   *
   * <code>repeated .gw.PerModulationCount tx_packets_per_modulation = 14;</code>
   */
  java.util.List<PerModulationCount>
      getTxPacketsPerModulationList();
  /**
   * <pre>
   * Tx packets per modulation parameters.
   * </pre>
   *
   * <code>repeated .gw.PerModulationCount tx_packets_per_modulation = 14;</code>
   */
  PerModulationCount getTxPacketsPerModulation(int index);
  /**
   * <pre>
   * Tx packets per modulation parameters.
   * </pre>
   *
   * <code>repeated .gw.PerModulationCount tx_packets_per_modulation = 14;</code>
   */
  int getTxPacketsPerModulationCount();
  /**
   * <pre>
   * Tx packets per modulation parameters.
   * </pre>
   *
   * <code>repeated .gw.PerModulationCount tx_packets_per_modulation = 14;</code>
   */
  java.util.List<? extends PerModulationCountOrBuilder>
      getTxPacketsPerModulationOrBuilderList();
  /**
   * <pre>
   * Tx packets per modulation parameters.
   * </pre>
   *
   * <code>repeated .gw.PerModulationCount tx_packets_per_modulation = 14;</code>
   */
  PerModulationCountOrBuilder getTxPacketsPerModulationOrBuilder(
      int index);

  /**
   * <pre>
   * Rx packets per modulation parameters.
   * </pre>
   *
   * <code>repeated .gw.PerModulationCount rx_packets_per_modulation = 15;</code>
   */
  java.util.List<PerModulationCount>
      getRxPacketsPerModulationList();
  /**
   * <pre>
   * Rx packets per modulation parameters.
   * </pre>
   *
   * <code>repeated .gw.PerModulationCount rx_packets_per_modulation = 15;</code>
   */
  PerModulationCount getRxPacketsPerModulation(int index);
  /**
   * <pre>
   * Rx packets per modulation parameters.
   * </pre>
   *
   * <code>repeated .gw.PerModulationCount rx_packets_per_modulation = 15;</code>
   */
  int getRxPacketsPerModulationCount();
  /**
   * <pre>
   * Rx packets per modulation parameters.
   * </pre>
   *
   * <code>repeated .gw.PerModulationCount rx_packets_per_modulation = 15;</code>
   */
  java.util.List<? extends PerModulationCountOrBuilder>
      getRxPacketsPerModulationOrBuilderList();
  /**
   * <pre>
   * Rx packets per modulation parameters.
   * </pre>
   *
   * <code>repeated .gw.PerModulationCount rx_packets_per_modulation = 15;</code>
   */
  PerModulationCountOrBuilder getRxPacketsPerModulationOrBuilder(
      int index);

  /**
   * <pre>
   * Tx packets per status.
   * </pre>
   *
   * <code>map&lt;string, uint32&gt; tx_packets_per_status = 16;</code>
   */
  int getTxPacketsPerStatusCount();
  /**
   * <pre>
   * Tx packets per status.
   * </pre>
   *
   * <code>map&lt;string, uint32&gt; tx_packets_per_status = 16;</code>
   */
  boolean containsTxPacketsPerStatus(
      String key);
  /**
   * Use {@link #getTxPacketsPerStatusMap()} instead.
   */
  @Deprecated
  java.util.Map<String, Integer>
  getTxPacketsPerStatus();
  /**
   * <pre>
   * Tx packets per status.
   * </pre>
   *
   * <code>map&lt;string, uint32&gt; tx_packets_per_status = 16;</code>
   */
  java.util.Map<String, Integer>
  getTxPacketsPerStatusMap();
  /**
   * <pre>
   * Tx packets per status.
   * </pre>
   *
   * <code>map&lt;string, uint32&gt; tx_packets_per_status = 16;</code>
   */

  int getTxPacketsPerStatusOrDefault(
      String key,
      int defaultValue);
  /**
   * <pre>
   * Tx packets per status.
   * </pre>
   *
   * <code>map&lt;string, uint32&gt; tx_packets_per_status = 16;</code>
   */

  int getTxPacketsPerStatusOrThrow(
      String key);
}
