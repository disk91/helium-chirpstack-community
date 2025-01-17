// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: api/device.proto
// Protobuf Java Version: 4.29.2

package io.chirpstack.api;

public interface GetDeviceLinkMetricsResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:api.GetDeviceLinkMetricsResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Packets received from the device.
   * </pre>
   *
   * <code>.common.Metric rx_packets = 1;</code>
   * @return Whether the rxPackets field is set.
   */
  boolean hasRxPackets();
  /**
   * <pre>
   * Packets received from the device.
   * </pre>
   *
   * <code>.common.Metric rx_packets = 1;</code>
   * @return The rxPackets.
   */
  io.chirpstack.api.Metric getRxPackets();
  /**
   * <pre>
   * Packets received from the device.
   * </pre>
   *
   * <code>.common.Metric rx_packets = 1;</code>
   */
  io.chirpstack.api.MetricOrBuilder getRxPacketsOrBuilder();

  /**
   * <pre>
   * RSSI (as reported by the gateway(s)).
   * </pre>
   *
   * <code>.common.Metric gw_rssi = 2;</code>
   * @return Whether the gwRssi field is set.
   */
  boolean hasGwRssi();
  /**
   * <pre>
   * RSSI (as reported by the gateway(s)).
   * </pre>
   *
   * <code>.common.Metric gw_rssi = 2;</code>
   * @return The gwRssi.
   */
  io.chirpstack.api.Metric getGwRssi();
  /**
   * <pre>
   * RSSI (as reported by the gateway(s)).
   * </pre>
   *
   * <code>.common.Metric gw_rssi = 2;</code>
   */
  io.chirpstack.api.MetricOrBuilder getGwRssiOrBuilder();

  /**
   * <pre>
   * SNR (as reported by the gateway(s)).
   * </pre>
   *
   * <code>.common.Metric gw_snr = 3;</code>
   * @return Whether the gwSnr field is set.
   */
  boolean hasGwSnr();
  /**
   * <pre>
   * SNR (as reported by the gateway(s)).
   * </pre>
   *
   * <code>.common.Metric gw_snr = 3;</code>
   * @return The gwSnr.
   */
  io.chirpstack.api.Metric getGwSnr();
  /**
   * <pre>
   * SNR (as reported by the gateway(s)).
   * </pre>
   *
   * <code>.common.Metric gw_snr = 3;</code>
   */
  io.chirpstack.api.MetricOrBuilder getGwSnrOrBuilder();

  /**
   * <pre>
   * Packets received by frequency.
   * </pre>
   *
   * <code>.common.Metric rx_packets_per_freq = 4;</code>
   * @return Whether the rxPacketsPerFreq field is set.
   */
  boolean hasRxPacketsPerFreq();
  /**
   * <pre>
   * Packets received by frequency.
   * </pre>
   *
   * <code>.common.Metric rx_packets_per_freq = 4;</code>
   * @return The rxPacketsPerFreq.
   */
  io.chirpstack.api.Metric getRxPacketsPerFreq();
  /**
   * <pre>
   * Packets received by frequency.
   * </pre>
   *
   * <code>.common.Metric rx_packets_per_freq = 4;</code>
   */
  io.chirpstack.api.MetricOrBuilder getRxPacketsPerFreqOrBuilder();

  /**
   * <pre>
   * Packets received by DR.
   * </pre>
   *
   * <code>.common.Metric rx_packets_per_dr = 5;</code>
   * @return Whether the rxPacketsPerDr field is set.
   */
  boolean hasRxPacketsPerDr();
  /**
   * <pre>
   * Packets received by DR.
   * </pre>
   *
   * <code>.common.Metric rx_packets_per_dr = 5;</code>
   * @return The rxPacketsPerDr.
   */
  io.chirpstack.api.Metric getRxPacketsPerDr();
  /**
   * <pre>
   * Packets received by DR.
   * </pre>
   *
   * <code>.common.Metric rx_packets_per_dr = 5;</code>
   */
  io.chirpstack.api.MetricOrBuilder getRxPacketsPerDrOrBuilder();

  /**
   * <pre>
   * Errors.
   * </pre>
   *
   * <code>.common.Metric errors = 6;</code>
   * @return Whether the errors field is set.
   */
  boolean hasErrors();
  /**
   * <pre>
   * Errors.
   * </pre>
   *
   * <code>.common.Metric errors = 6;</code>
   * @return The errors.
   */
  io.chirpstack.api.Metric getErrors();
  /**
   * <pre>
   * Errors.
   * </pre>
   *
   * <code>.common.Metric errors = 6;</code>
   */
  io.chirpstack.api.MetricOrBuilder getErrorsOrBuilder();
}
