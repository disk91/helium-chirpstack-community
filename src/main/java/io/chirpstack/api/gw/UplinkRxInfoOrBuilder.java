// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: gw/gw.proto

// Protobuf Java Version: 3.25.1
package io.chirpstack.api.gw;

public interface UplinkRxInfoOrBuilder extends
    // @@protoc_insertion_point(interface_extends:gw.UplinkRxInfo)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Gateway ID.
   * </pre>
   *
   * <code>string gateway_id = 1;</code>
   * @return The gatewayId.
   */
  java.lang.String getGatewayId();
  /**
   * <pre>
   * Gateway ID.
   * </pre>
   *
   * <code>string gateway_id = 1;</code>
   * @return The bytes for gatewayId.
   */
  com.google.protobuf.ByteString
      getGatewayIdBytes();

  /**
   * <pre>
   * Uplink ID.
   * </pre>
   *
   * <code>uint32 uplink_id = 2;</code>
   * @return The uplinkId.
   */
  int getUplinkId();

  /**
   * <pre>
   * Gateway RX time (set if the gateway has a GNSS module).
   * </pre>
   *
   * <code>.google.protobuf.Timestamp gw_time = 3;</code>
   * @return Whether the gwTime field is set.
   */
  boolean hasGwTime();
  /**
   * <pre>
   * Gateway RX time (set if the gateway has a GNSS module).
   * </pre>
   *
   * <code>.google.protobuf.Timestamp gw_time = 3;</code>
   * @return The gwTime.
   */
  com.google.protobuf.Timestamp getGwTime();
  /**
   * <pre>
   * Gateway RX time (set if the gateway has a GNSS module).
   * </pre>
   *
   * <code>.google.protobuf.Timestamp gw_time = 3;</code>
   */
  com.google.protobuf.TimestampOrBuilder getGwTimeOrBuilder();

  /**
   * <pre>
   * Network Server RX time (set by the NS on receiving the uplink).
   * </pre>
   *
   * <code>.google.protobuf.Timestamp ns_time = 17;</code>
   * @return Whether the nsTime field is set.
   */
  boolean hasNsTime();
  /**
   * <pre>
   * Network Server RX time (set by the NS on receiving the uplink).
   * </pre>
   *
   * <code>.google.protobuf.Timestamp ns_time = 17;</code>
   * @return The nsTime.
   */
  com.google.protobuf.Timestamp getNsTime();
  /**
   * <pre>
   * Network Server RX time (set by the NS on receiving the uplink).
   * </pre>
   *
   * <code>.google.protobuf.Timestamp ns_time = 17;</code>
   */
  com.google.protobuf.TimestampOrBuilder getNsTimeOrBuilder();

  /**
   * <pre>
   * RX time as time since GPS epoch (set if the gateway has a GNSS module).
   * </pre>
   *
   * <code>.google.protobuf.Duration time_since_gps_epoch = 4;</code>
   * @return Whether the timeSinceGpsEpoch field is set.
   */
  boolean hasTimeSinceGpsEpoch();
  /**
   * <pre>
   * RX time as time since GPS epoch (set if the gateway has a GNSS module).
   * </pre>
   *
   * <code>.google.protobuf.Duration time_since_gps_epoch = 4;</code>
   * @return The timeSinceGpsEpoch.
   */
  com.google.protobuf.Duration getTimeSinceGpsEpoch();
  /**
   * <pre>
   * RX time as time since GPS epoch (set if the gateway has a GNSS module).
   * </pre>
   *
   * <code>.google.protobuf.Duration time_since_gps_epoch = 4;</code>
   */
  com.google.protobuf.DurationOrBuilder getTimeSinceGpsEpochOrBuilder();

  /**
   * <pre>
   * Fine-timestamp.
   * This timestamp can be used for TDOA based geolocation.
   * </pre>
   *
   * <code>.google.protobuf.Duration fine_time_since_gps_epoch = 5;</code>
   * @return Whether the fineTimeSinceGpsEpoch field is set.
   */
  boolean hasFineTimeSinceGpsEpoch();
  /**
   * <pre>
   * Fine-timestamp.
   * This timestamp can be used for TDOA based geolocation.
   * </pre>
   *
   * <code>.google.protobuf.Duration fine_time_since_gps_epoch = 5;</code>
   * @return The fineTimeSinceGpsEpoch.
   */
  com.google.protobuf.Duration getFineTimeSinceGpsEpoch();
  /**
   * <pre>
   * Fine-timestamp.
   * This timestamp can be used for TDOA based geolocation.
   * </pre>
   *
   * <code>.google.protobuf.Duration fine_time_since_gps_epoch = 5;</code>
   */
  com.google.protobuf.DurationOrBuilder getFineTimeSinceGpsEpochOrBuilder();

  /**
   * <pre>
   * RSSI.
   * </pre>
   *
   * <code>int32 rssi = 6;</code>
   * @return The rssi.
   */
  int getRssi();

  /**
   * <pre>
   * SNR.
   * Note: only available for LoRa modulation.
   * </pre>
   *
   * <code>float snr = 7;</code>
   * @return The snr.
   */
  float getSnr();

  /**
   * <pre>
   * Channel.
   * </pre>
   *
   * <code>uint32 channel = 8;</code>
   * @return The channel.
   */
  int getChannel();

  /**
   * <pre>
   * RF chain.
   * </pre>
   *
   * <code>uint32 rf_chain = 9;</code>
   * @return The rfChain.
   */
  int getRfChain();

  /**
   * <pre>
   * Board.
   * </pre>
   *
   * <code>uint32 board = 10;</code>
   * @return The board.
   */
  int getBoard();

  /**
   * <pre>
   * Antenna.
   * </pre>
   *
   * <code>uint32 antenna = 11;</code>
   * @return The antenna.
   */
  int getAntenna();

  /**
   * <pre>
   * Location.
   * </pre>
   *
   * <code>.common.Location location = 12;</code>
   * @return Whether the location field is set.
   */
  boolean hasLocation();
  /**
   * <pre>
   * Location.
   * </pre>
   *
   * <code>.common.Location location = 12;</code>
   * @return The location.
   */
  io.chirpstack.api.Location getLocation();
  /**
   * <pre>
   * Location.
   * </pre>
   *
   * <code>.common.Location location = 12;</code>
   */
  io.chirpstack.api.LocationOrBuilder getLocationOrBuilder();

  /**
   * <pre>
   * Gateway specific context.
   * This value must be returned to the gateway on (Class-A) downlink.
   * </pre>
   *
   * <code>bytes context = 13;</code>
   * @return The context.
   */
  com.google.protobuf.ByteString getContext();

  /**
   * <pre>
   * Additional gateway meta-data.
   * </pre>
   *
   * <code>map&lt;string, string&gt; metadata = 15;</code>
   */
  int getMetadataCount();
  /**
   * <pre>
   * Additional gateway meta-data.
   * </pre>
   *
   * <code>map&lt;string, string&gt; metadata = 15;</code>
   */
  boolean containsMetadata(
      java.lang.String key);
  /**
   * Use {@link #getMetadataMap()} instead.
   */
  @java.lang.Deprecated
  java.util.Map<java.lang.String, java.lang.String>
  getMetadata();
  /**
   * <pre>
   * Additional gateway meta-data.
   * </pre>
   *
   * <code>map&lt;string, string&gt; metadata = 15;</code>
   */
  java.util.Map<java.lang.String, java.lang.String>
  getMetadataMap();
  /**
   * <pre>
   * Additional gateway meta-data.
   * </pre>
   *
   * <code>map&lt;string, string&gt; metadata = 15;</code>
   */
  /* nullable */
java.lang.String getMetadataOrDefault(
      java.lang.String key,
      /* nullable */
java.lang.String defaultValue);
  /**
   * <pre>
   * Additional gateway meta-data.
   * </pre>
   *
   * <code>map&lt;string, string&gt; metadata = 15;</code>
   */
  java.lang.String getMetadataOrThrow(
      java.lang.String key);

  /**
   * <pre>
   * CRC status.
   * </pre>
   *
   * <code>.gw.CRCStatus crc_status = 16;</code>
   * @return The enum numeric value on the wire for crcStatus.
   */
  int getCrcStatusValue();
  /**
   * <pre>
   * CRC status.
   * </pre>
   *
   * <code>.gw.CRCStatus crc_status = 16;</code>
   * @return The crcStatus.
   */
  io.chirpstack.api.gw.CRCStatus getCrcStatus();
}
