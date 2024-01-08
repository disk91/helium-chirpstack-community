// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: gw/gw.proto

// Protobuf Java Version: 3.25.1
package io.chirpstack.api.gw;

public interface UplinkTxInfoLegacyOrBuilder extends
    // @@protoc_insertion_point(interface_extends:gw.UplinkTxInfoLegacy)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Frequency (Hz).
   * </pre>
   *
   * <code>uint32 frequency = 1;</code>
   * @return The frequency.
   */
  int getFrequency();

  /**
   * <pre>
   * Modulation.
   * </pre>
   *
   * <code>.common.Modulation modulation = 2;</code>
   * @return The enum numeric value on the wire for modulation.
   */
  int getModulationValue();
  /**
   * <pre>
   * Modulation.
   * </pre>
   *
   * <code>.common.Modulation modulation = 2;</code>
   * @return The modulation.
   */
  io.chirpstack.api.Modulation getModulation();

  /**
   * <pre>
   * LoRa modulation information.
   * </pre>
   *
   * <code>.gw.LoraModulationInfo lora_modulation_info = 3;</code>
   * @return Whether the loraModulationInfo field is set.
   */
  boolean hasLoraModulationInfo();
  /**
   * <pre>
   * LoRa modulation information.
   * </pre>
   *
   * <code>.gw.LoraModulationInfo lora_modulation_info = 3;</code>
   * @return The loraModulationInfo.
   */
  io.chirpstack.api.gw.LoraModulationInfo getLoraModulationInfo();
  /**
   * <pre>
   * LoRa modulation information.
   * </pre>
   *
   * <code>.gw.LoraModulationInfo lora_modulation_info = 3;</code>
   */
  io.chirpstack.api.gw.LoraModulationInfoOrBuilder getLoraModulationInfoOrBuilder();

  /**
   * <pre>
   * FSK modulation information.
   * </pre>
   *
   * <code>.gw.FskModulationInfo fsk_modulation_info = 4;</code>
   * @return Whether the fskModulationInfo field is set.
   */
  boolean hasFskModulationInfo();
  /**
   * <pre>
   * FSK modulation information.
   * </pre>
   *
   * <code>.gw.FskModulationInfo fsk_modulation_info = 4;</code>
   * @return The fskModulationInfo.
   */
  io.chirpstack.api.gw.FskModulationInfo getFskModulationInfo();
  /**
   * <pre>
   * FSK modulation information.
   * </pre>
   *
   * <code>.gw.FskModulationInfo fsk_modulation_info = 4;</code>
   */
  io.chirpstack.api.gw.FskModulationInfoOrBuilder getFskModulationInfoOrBuilder();

  /**
   * <pre>
   * LR-FHSS modulation information.
   * </pre>
   *
   * <code>.gw.LrFhssModulationInfo lr_fhss_modulation_info = 5;</code>
   * @return Whether the lrFhssModulationInfo field is set.
   */
  boolean hasLrFhssModulationInfo();
  /**
   * <pre>
   * LR-FHSS modulation information.
   * </pre>
   *
   * <code>.gw.LrFhssModulationInfo lr_fhss_modulation_info = 5;</code>
   * @return The lrFhssModulationInfo.
   */
  io.chirpstack.api.gw.LrFhssModulationInfo getLrFhssModulationInfo();
  /**
   * <pre>
   * LR-FHSS modulation information.
   * </pre>
   *
   * <code>.gw.LrFhssModulationInfo lr_fhss_modulation_info = 5;</code>
   */
  io.chirpstack.api.gw.LrFhssModulationInfoOrBuilder getLrFhssModulationInfoOrBuilder();

  io.chirpstack.api.gw.UplinkTxInfoLegacy.ModulationInfoCase getModulationInfoCase();
}
