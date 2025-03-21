// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: gw/gw.proto
// Protobuf Java Version: 4.29.2

package io.chirpstack.api.gw;

public interface ChannelConfigurationOrBuilder extends
    // @@protoc_insertion_point(interface_extends:gw.ChannelConfiguration)
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
   * Modulation (deprecated).
   * </pre>
   *
   * <code>.common.Modulation modulation_legacy = 2;</code>
   * @return The enum numeric value on the wire for modulationLegacy.
   */
  int getModulationLegacyValue();
  /**
   * <pre>
   * Modulation (deprecated).
   * </pre>
   *
   * <code>.common.Modulation modulation_legacy = 2;</code>
   * @return The modulationLegacy.
   */
  io.chirpstack.api.Modulation getModulationLegacy();

  /**
   * <pre>
   * LoRa modulation config.
   * </pre>
   *
   * <code>.gw.LoraModulationConfig lora_modulation_config = 3;</code>
   * @return Whether the loraModulationConfig field is set.
   */
  boolean hasLoraModulationConfig();
  /**
   * <pre>
   * LoRa modulation config.
   * </pre>
   *
   * <code>.gw.LoraModulationConfig lora_modulation_config = 3;</code>
   * @return The loraModulationConfig.
   */
  io.chirpstack.api.gw.LoraModulationConfig getLoraModulationConfig();
  /**
   * <pre>
   * LoRa modulation config.
   * </pre>
   *
   * <code>.gw.LoraModulationConfig lora_modulation_config = 3;</code>
   */
  io.chirpstack.api.gw.LoraModulationConfigOrBuilder getLoraModulationConfigOrBuilder();

  /**
   * <pre>
   * FSK modulation config.
   * </pre>
   *
   * <code>.gw.FskModulationConfig fsk_modulation_config = 4;</code>
   * @return Whether the fskModulationConfig field is set.
   */
  boolean hasFskModulationConfig();
  /**
   * <pre>
   * FSK modulation config.
   * </pre>
   *
   * <code>.gw.FskModulationConfig fsk_modulation_config = 4;</code>
   * @return The fskModulationConfig.
   */
  io.chirpstack.api.gw.FskModulationConfig getFskModulationConfig();
  /**
   * <pre>
   * FSK modulation config.
   * </pre>
   *
   * <code>.gw.FskModulationConfig fsk_modulation_config = 4;</code>
   */
  io.chirpstack.api.gw.FskModulationConfigOrBuilder getFskModulationConfigOrBuilder();

  /**
   * <pre>
   * Board index.
   * </pre>
   *
   * <code>uint32 board = 5;</code>
   * @return The board.
   */
  int getBoard();

  /**
   * <pre>
   * Demodulator index (of the given board).
   * </pre>
   *
   * <code>uint32 demodulator = 6;</code>
   * @return The demodulator.
   */
  int getDemodulator();

  io.chirpstack.api.gw.ChannelConfiguration.ModulationConfigCase getModulationConfigCase();
}
