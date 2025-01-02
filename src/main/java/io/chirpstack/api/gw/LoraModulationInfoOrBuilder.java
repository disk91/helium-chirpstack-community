// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: gw/gw.proto
// Protobuf Java Version: 4.29.2

package io.chirpstack.api.gw;

public interface LoraModulationInfoOrBuilder extends
    // @@protoc_insertion_point(interface_extends:gw.LoraModulationInfo)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Bandwidth.
   * </pre>
   *
   * <code>uint32 bandwidth = 1;</code>
   * @return The bandwidth.
   */
  int getBandwidth();

  /**
   * <pre>
   * Speading-factor.
   * </pre>
   *
   * <code>uint32 spreading_factor = 2;</code>
   * @return The spreadingFactor.
   */
  int getSpreadingFactor();

  /**
   * <pre>
   * Code-rate.
   * </pre>
   *
   * <code>string code_rate_legacy = 3;</code>
   * @return The codeRateLegacy.
   */
  java.lang.String getCodeRateLegacy();
  /**
   * <pre>
   * Code-rate.
   * </pre>
   *
   * <code>string code_rate_legacy = 3;</code>
   * @return The bytes for codeRateLegacy.
   */
  com.google.protobuf.ByteString
      getCodeRateLegacyBytes();

  /**
   * <pre>
   * Code-rate.
   * </pre>
   *
   * <code>.gw.CodeRate code_rate = 5;</code>
   * @return The enum numeric value on the wire for codeRate.
   */
  int getCodeRateValue();
  /**
   * <pre>
   * Code-rate.
   * </pre>
   *
   * <code>.gw.CodeRate code_rate = 5;</code>
   * @return The codeRate.
   */
  io.chirpstack.api.gw.CodeRate getCodeRate();

  /**
   * <pre>
   * Polarization inversion.
   * </pre>
   *
   * <code>bool polarization_inversion = 4;</code>
   * @return The polarizationInversion.
   */
  boolean getPolarizationInversion();

  /**
   * <pre>
   * Preamble length (for TX).
   * </pre>
   *
   * <code>uint32 preamble = 6;</code>
   * @return The preamble.
   */
  int getPreamble();

  /**
   * <pre>
   * No CRC (for TX).
   * If true, do not send a CRC in the packet.
   * </pre>
   *
   * <code>bool no_crc = 7;</code>
   * @return The noCrc.
   */
  boolean getNoCrc();
}
