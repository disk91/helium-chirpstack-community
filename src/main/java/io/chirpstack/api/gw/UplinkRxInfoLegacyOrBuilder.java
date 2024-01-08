// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: gw/gw.proto

// Protobuf Java Version: 3.25.1
package io.chirpstack.api.gw;

public interface UplinkRxInfoLegacyOrBuilder extends
    // @@protoc_insertion_point(interface_extends:gw.UplinkRxInfoLegacy)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Gateway ID.
   * </pre>
   *
   * <code>bytes gateway_id = 1;</code>
   * @return The gatewayId.
   */
  com.google.protobuf.ByteString getGatewayId();

  /**
   * <pre>
   * RX time (only set when the gateway has a GPS module).
   * </pre>
   *
   * <code>.google.protobuf.Timestamp time = 2;</code>
   * @return Whether the time field is set.
   */
  boolean hasTime();
  /**
   * <pre>
   * RX time (only set when the gateway has a GPS module).
   * </pre>
   *
   * <code>.google.protobuf.Timestamp time = 2;</code>
   * @return The time.
   */
  com.google.protobuf.Timestamp getTime();
  /**
   * <pre>
   * RX time (only set when the gateway has a GPS module).
   * </pre>
   *
   * <code>.google.protobuf.Timestamp time = 2;</code>
   */
  com.google.protobuf.TimestampOrBuilder getTimeOrBuilder();

  /**
   * <pre>
   * RX time since GPS epoch (only set when the gateway has a GPS module).
   * </pre>
   *
   * <code>.google.protobuf.Duration time_since_gps_epoch = 3;</code>
   * @return Whether the timeSinceGpsEpoch field is set.
   */
  boolean hasTimeSinceGpsEpoch();
  /**
   * <pre>
   * RX time since GPS epoch (only set when the gateway has a GPS module).
   * </pre>
   *
   * <code>.google.protobuf.Duration time_since_gps_epoch = 3;</code>
   * @return The timeSinceGpsEpoch.
   */
  com.google.protobuf.Duration getTimeSinceGpsEpoch();
  /**
   * <pre>
   * RX time since GPS epoch (only set when the gateway has a GPS module).
   * </pre>
   *
   * <code>.google.protobuf.Duration time_since_gps_epoch = 3;</code>
   */
  com.google.protobuf.DurationOrBuilder getTimeSinceGpsEpochOrBuilder();

  /**
   * <pre>
   * RSSI.
   * </pre>
   *
   * <code>int32 rssi = 5;</code>
   * @return The rssi.
   */
  int getRssi();

  /**
   * <pre>
   * LoRa SNR.
   * </pre>
   *
   * <code>double lora_snr = 6;</code>
   * @return The loraSnr.
   */
  double getLoraSnr();

  /**
   * <pre>
   * Channel.
   * </pre>
   *
   * <code>uint32 channel = 7;</code>
   * @return The channel.
   */
  int getChannel();

  /**
   * <pre>
   * RF Chain.
   * </pre>
   *
   * <code>uint32 rf_chain = 8;</code>
   * @return The rfChain.
   */
  int getRfChain();

  /**
   * <pre>
   * Board.
   * </pre>
   *
   * <code>uint32 board = 9;</code>
   * @return The board.
   */
  int getBoard();

  /**
   * <pre>
   * Antenna.
   * </pre>
   *
   * <code>uint32 antenna = 10;</code>
   * @return The antenna.
   */
  int getAntenna();

  /**
   * <pre>
   * Location.
   * </pre>
   *
   * <code>.common.Location location = 11;</code>
   * @return Whether the location field is set.
   */
  boolean hasLocation();
  /**
   * <pre>
   * Location.
   * </pre>
   *
   * <code>.common.Location location = 11;</code>
   * @return The location.
   */
  io.chirpstack.api.Location getLocation();
  /**
   * <pre>
   * Location.
   * </pre>
   *
   * <code>.common.Location location = 11;</code>
   */
  io.chirpstack.api.LocationOrBuilder getLocationOrBuilder();

  /**
   * <pre>
   * Fine-timestamp type.
   * </pre>
   *
   * <code>.gw.FineTimestampType fine_timestamp_type = 12;</code>
   * @return The enum numeric value on the wire for fineTimestampType.
   */
  int getFineTimestampTypeValue();
  /**
   * <pre>
   * Fine-timestamp type.
   * </pre>
   *
   * <code>.gw.FineTimestampType fine_timestamp_type = 12;</code>
   * @return The fineTimestampType.
   */
  io.chirpstack.api.gw.FineTimestampType getFineTimestampType();

  /**
   * <pre>
   * Encrypted fine-timestamp data.
   * </pre>
   *
   * <code>.gw.EncryptedFineTimestamp encrypted_fine_timestamp = 13;</code>
   * @return Whether the encryptedFineTimestamp field is set.
   */
  boolean hasEncryptedFineTimestamp();
  /**
   * <pre>
   * Encrypted fine-timestamp data.
   * </pre>
   *
   * <code>.gw.EncryptedFineTimestamp encrypted_fine_timestamp = 13;</code>
   * @return The encryptedFineTimestamp.
   */
  io.chirpstack.api.gw.EncryptedFineTimestamp getEncryptedFineTimestamp();
  /**
   * <pre>
   * Encrypted fine-timestamp data.
   * </pre>
   *
   * <code>.gw.EncryptedFineTimestamp encrypted_fine_timestamp = 13;</code>
   */
  io.chirpstack.api.gw.EncryptedFineTimestampOrBuilder getEncryptedFineTimestampOrBuilder();

  /**
   * <pre>
   * Plain fine-timestamp data.
   * </pre>
   *
   * <code>.gw.PlainFineTimestamp plain_fine_timestamp = 14;</code>
   * @return Whether the plainFineTimestamp field is set.
   */
  boolean hasPlainFineTimestamp();
  /**
   * <pre>
   * Plain fine-timestamp data.
   * </pre>
   *
   * <code>.gw.PlainFineTimestamp plain_fine_timestamp = 14;</code>
   * @return The plainFineTimestamp.
   */
  io.chirpstack.api.gw.PlainFineTimestamp getPlainFineTimestamp();
  /**
   * <pre>
   * Plain fine-timestamp data.
   * </pre>
   *
   * <code>.gw.PlainFineTimestamp plain_fine_timestamp = 14;</code>
   */
  io.chirpstack.api.gw.PlainFineTimestampOrBuilder getPlainFineTimestampOrBuilder();

  /**
   * <pre>
   * Gateway specific context.
   * </pre>
   *
   * <code>bytes context = 15;</code>
   * @return The context.
   */
  com.google.protobuf.ByteString getContext();

  /**
   * <pre>
   * Uplink ID (UUID bytes).
   * Unique and random ID which can be used to correlate the uplink across
   * multiple logs.
   * </pre>
   *
   * <code>bytes uplink_id = 16;</code>
   * @return The uplinkId.
   */
  com.google.protobuf.ByteString getUplinkId();

  /**
   * <pre>
   * CRC status.
   * </pre>
   *
   * <code>.gw.CRCStatus crc_status = 17;</code>
   * @return The enum numeric value on the wire for crcStatus.
   */
  int getCrcStatusValue();
  /**
   * <pre>
   * CRC status.
   * </pre>
   *
   * <code>.gw.CRCStatus crc_status = 17;</code>
   * @return The crcStatus.
   */
  io.chirpstack.api.gw.CRCStatus getCrcStatus();

  /**
   * <pre>
   * Optional meta-data map.
   * </pre>
   *
   * <code>map&lt;string, string&gt; metadata = 18;</code>
   */
  int getMetadataCount();
  /**
   * <pre>
   * Optional meta-data map.
   * </pre>
   *
   * <code>map&lt;string, string&gt; metadata = 18;</code>
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
   * Optional meta-data map.
   * </pre>
   *
   * <code>map&lt;string, string&gt; metadata = 18;</code>
   */
  java.util.Map<java.lang.String, java.lang.String>
  getMetadataMap();
  /**
   * <pre>
   * Optional meta-data map.
   * </pre>
   *
   * <code>map&lt;string, string&gt; metadata = 18;</code>
   */
  /* nullable */
java.lang.String getMetadataOrDefault(
      java.lang.String key,
      /* nullable */
java.lang.String defaultValue);
  /**
   * <pre>
   * Optional meta-data map.
   * </pre>
   *
   * <code>map&lt;string, string&gt; metadata = 18;</code>
   */
  java.lang.String getMetadataOrThrow(
      java.lang.String key);

  io.chirpstack.api.gw.UplinkRxInfoLegacy.FineTimestampCase getFineTimestampCase();
}
