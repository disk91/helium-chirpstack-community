// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: internal/internal.proto

// Protobuf Java Version: 3.25.1
package io.chirpstack.internal;

public interface DeviceSessionOrBuilder extends
    // @@protoc_insertion_point(interface_extends:internal.DeviceSession)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Device address.
   * </pre>
   *
   * <code>bytes dev_addr = 2;</code>
   * @return The devAddr.
   */
  com.google.protobuf.ByteString getDevAddr();

  /**
   * <pre>
   * LoRaWAN mac-version.
   * </pre>
   *
   * <code>.common.MacVersion mac_version = 4;</code>
   * @return The enum numeric value on the wire for macVersion.
   */
  int getMacVersionValue();
  /**
   * <pre>
   * LoRaWAN mac-version.
   * </pre>
   *
   * <code>.common.MacVersion mac_version = 4;</code>
   * @return The macVersion.
   */
  io.chirpstack.api.MacVersion getMacVersion();

  /**
   * <pre>
   * FNwkSIntKey.
   * </pre>
   *
   * <code>bytes f_nwk_s_int_key = 5;</code>
   * @return The fNwkSIntKey.
   */
  com.google.protobuf.ByteString getFNwkSIntKey();

  /**
   * <pre>
   * SNwkSIntKey.
   * </pre>
   *
   * <code>bytes s_nwk_s_int_key = 6;</code>
   * @return The sNwkSIntKey.
   */
  com.google.protobuf.ByteString getSNwkSIntKey();

  /**
   * <pre>
   * NwkSEncKey.
   * </pre>
   *
   * <code>bytes nwk_s_enc_key = 7;</code>
   * @return The nwkSEncKey.
   */
  com.google.protobuf.ByteString getNwkSEncKey();

  /**
   * <pre>
   * AppSKey envelope.
   * </pre>
   *
   * <code>.common.KeyEnvelope app_s_key = 8;</code>
   * @return Whether the appSKey field is set.
   */
  boolean hasAppSKey();
  /**
   * <pre>
   * AppSKey envelope.
   * </pre>
   *
   * <code>.common.KeyEnvelope app_s_key = 8;</code>
   * @return The appSKey.
   */
  io.chirpstack.api.KeyEnvelope getAppSKey();
  /**
   * <pre>
   * AppSKey envelope.
   * </pre>
   *
   * <code>.common.KeyEnvelope app_s_key = 8;</code>
   */
  io.chirpstack.api.KeyEnvelopeOrBuilder getAppSKeyOrBuilder();

  /**
   * <pre>
   * JS Session Key ID.
   * </pre>
   *
   * <code>bytes js_session_key_id = 42;</code>
   * @return The jsSessionKeyId.
   */
  com.google.protobuf.ByteString getJsSessionKeyId();

  /**
   * <pre>
   * Uplink frame-counter.
   * </pre>
   *
   * <code>uint32 f_cnt_up = 9;</code>
   * @return The fCntUp.
   */
  int getFCntUp();

  /**
   * <pre>
   * Downlink frame-counter (ns).
   * </pre>
   *
   * <code>uint32 n_f_cnt_down = 10;</code>
   * @return The nFCntDown.
   */
  int getNFCntDown();

  /**
   * <pre>
   * Downlink frame-counter (as).
   * </pre>
   *
   * <code>uint32 a_f_cnt_down = 11;</code>
   * @return The aFCntDown.
   */
  int getAFCntDown();

  /**
   * <pre>
   * Frame-counter holding the last confirmed downlink frame (n_f_cnt_down or
   * a_f_cnt_down).
   * </pre>
   *
   * <code>uint32 conf_f_cnt = 12;</code>
   * @return The confFCnt.
   */
  int getConfFCnt();

  /**
   * <pre>
   * Skip uplink frame-counter validation.
   * </pre>
   *
   * <code>bool skip_f_cnt_check = 13;</code>
   * @return The skipFCntCheck.
   */
  boolean getSkipFCntCheck();

  /**
   * <pre>
   * RX1 delay.
   * </pre>
   *
   * <code>uint32 rx1_delay = 14;</code>
   * @return The rx1Delay.
   */
  int getRx1Delay();

  /**
   * <pre>
   * RX1 data-rate offset.
   * </pre>
   *
   * <code>uint32 rx1_dr_offset = 15;</code>
   * @return The rx1DrOffset.
   */
  int getRx1DrOffset();

  /**
   * <pre>
   * RX2 data-rate.
   * </pre>
   *
   * <code>uint32 rx2_dr = 16;</code>
   * @return The rx2Dr.
   */
  int getRx2Dr();

  /**
   * <pre>
   * RX2 frequency.
   * </pre>
   *
   * <code>uint32 rx2_frequency = 17;</code>
   * @return The rx2Frequency.
   */
  int getRx2Frequency();

  /**
   * <pre>
   * Enabled uplink channels.
   * </pre>
   *
   * <code>repeated uint32 enabled_uplink_channel_indices = 18;</code>
   * @return A list containing the enabledUplinkChannelIndices.
   */
  java.util.List<java.lang.Integer> getEnabledUplinkChannelIndicesList();
  /**
   * <pre>
   * Enabled uplink channels.
   * </pre>
   *
   * <code>repeated uint32 enabled_uplink_channel_indices = 18;</code>
   * @return The count of enabledUplinkChannelIndices.
   */
  int getEnabledUplinkChannelIndicesCount();
  /**
   * <pre>
   * Enabled uplink channels.
   * </pre>
   *
   * <code>repeated uint32 enabled_uplink_channel_indices = 18;</code>
   * @param index The index of the element to return.
   * @return The enabledUplinkChannelIndices at the given index.
   */
  int getEnabledUplinkChannelIndices(int index);

  /**
   * <pre>
   * Extra user-defined uplink channels.
   * </pre>
   *
   * <code>map&lt;uint32, .internal.DeviceSessionChannel&gt; extra_uplink_channels = 19;</code>
   */
  int getExtraUplinkChannelsCount();
  /**
   * <pre>
   * Extra user-defined uplink channels.
   * </pre>
   *
   * <code>map&lt;uint32, .internal.DeviceSessionChannel&gt; extra_uplink_channels = 19;</code>
   */
  boolean containsExtraUplinkChannels(
      int key);
  /**
   * Use {@link #getExtraUplinkChannelsMap()} instead.
   */
  @java.lang.Deprecated
  java.util.Map<java.lang.Integer, io.chirpstack.internal.DeviceSessionChannel>
  getExtraUplinkChannels();
  /**
   * <pre>
   * Extra user-defined uplink channels.
   * </pre>
   *
   * <code>map&lt;uint32, .internal.DeviceSessionChannel&gt; extra_uplink_channels = 19;</code>
   */
  java.util.Map<java.lang.Integer, io.chirpstack.internal.DeviceSessionChannel>
  getExtraUplinkChannelsMap();
  /**
   * <pre>
   * Extra user-defined uplink channels.
   * </pre>
   *
   * <code>map&lt;uint32, .internal.DeviceSessionChannel&gt; extra_uplink_channels = 19;</code>
   */
  /* nullable */
io.chirpstack.internal.DeviceSessionChannel getExtraUplinkChannelsOrDefault(
      int key,
      /* nullable */
io.chirpstack.internal.DeviceSessionChannel defaultValue);
  /**
   * <pre>
   * Extra user-defined uplink channels.
   * </pre>
   *
   * <code>map&lt;uint32, .internal.DeviceSessionChannel&gt; extra_uplink_channels = 19;</code>
   */
  io.chirpstack.internal.DeviceSessionChannel getExtraUplinkChannelsOrThrow(
      int key);

  /**
   * <pre>
   * Class-B ping-slot data-rate.
   * </pre>
   *
   * <code>uint32 class_b_ping_slot_dr = 20;</code>
   * @return The classBPingSlotDr.
   */
  int getClassBPingSlotDr();

  /**
   * <pre>
   * Class-B ping-slot frequency.
   * </pre>
   *
   * <code>uint32 class_b_ping_slot_freq = 21;</code>
   * @return The classBPingSlotFreq.
   */
  int getClassBPingSlotFreq();

  /**
   * <pre>
   * Class-B ping-slot nb.
   * </pre>
   *
   * <code>uint32 class_b_ping_slot_nb = 22;</code>
   * @return The classBPingSlotNb.
   */
  int getClassBPingSlotNb();

  /**
   * <pre>
   * Nb. transmissions.
   * </pre>
   *
   * <code>uint32 nb_trans = 23;</code>
   * @return The nbTrans.
   */
  int getNbTrans();

  /**
   * <pre>
   * TXPowerIndex which the node is using. The possible values are defined
   * by the lorawan/band package and are region specific. By default it is
   * assumed that the node is using TXPower 0. This value is controlled by
   * the ADR engine.
   * </pre>
   *
   * <code>uint32 tx_power_index = 24;</code>
   * @return The txPowerIndex.
   */
  int getTxPowerIndex();

  /**
   * <pre>
   * DR defines the (last known) data-rate at which the node is operating.
   * This value is controlled by the ADR engine.
   * </pre>
   *
   * <code>uint32 dr = 25;</code>
   * @return The dr.
   */
  int getDr();

  /**
   * <pre>
   * ADR defines if the device has ADR enabled.
   * </pre>
   *
   * <code>bool adr = 26;</code>
   * @return The adr.
   */
  boolean getAdr();

  /**
   * <pre>
   * MaxSupportedTXPowerIndex defines the maximum supported tx-power index
   * by the node, or 0 when not set.
   * </pre>
   *
   * <code>uint32 max_supported_tx_power_index = 27;</code>
   * @return The maxSupportedTxPowerIndex.
   */
  int getMaxSupportedTxPowerIndex();

  /**
   * <pre>
   * MinSupportedTXPowerIndex defines the minimum supported tx-power index
   * by the node (default 0).
   * </pre>
   *
   * <code>uint32 min_supported_tx_power_index = 28;</code>
   * @return The minSupportedTxPowerIndex.
   */
  int getMinSupportedTxPowerIndex();

  /**
   * <pre>
   * Pending rejoin device-session contains a device-session which has not
   * yet been activated by the device (by sending a first uplink).
   * </pre>
   *
   * <code>.internal.DeviceSession pending_rejoin_device_session = 29;</code>
   * @return Whether the pendingRejoinDeviceSession field is set.
   */
  boolean hasPendingRejoinDeviceSession();
  /**
   * <pre>
   * Pending rejoin device-session contains a device-session which has not
   * yet been activated by the device (by sending a first uplink).
   * </pre>
   *
   * <code>.internal.DeviceSession pending_rejoin_device_session = 29;</code>
   * @return The pendingRejoinDeviceSession.
   */
  io.chirpstack.internal.DeviceSession getPendingRejoinDeviceSession();
  /**
   * <pre>
   * Pending rejoin device-session contains a device-session which has not
   * yet been activated by the device (by sending a first uplink).
   * </pre>
   *
   * <code>.internal.DeviceSession pending_rejoin_device_session = 29;</code>
   */
  io.chirpstack.internal.DeviceSessionOrBuilder getPendingRejoinDeviceSessionOrBuilder();

  /**
   * <pre>
   * Uplink history for ADR (last 20 uplink transmissions).
   * This table is reset in case one of parameters has changed:
   *   * DR
   *   * TxPower
   *   * NbTrans
   * </pre>
   *
   * <code>repeated .internal.UplinkAdrHistory uplink_adr_history = 30;</code>
   */
  java.util.List<io.chirpstack.internal.UplinkAdrHistory> 
      getUplinkAdrHistoryList();
  /**
   * <pre>
   * Uplink history for ADR (last 20 uplink transmissions).
   * This table is reset in case one of parameters has changed:
   *   * DR
   *   * TxPower
   *   * NbTrans
   * </pre>
   *
   * <code>repeated .internal.UplinkAdrHistory uplink_adr_history = 30;</code>
   */
  io.chirpstack.internal.UplinkAdrHistory getUplinkAdrHistory(int index);
  /**
   * <pre>
   * Uplink history for ADR (last 20 uplink transmissions).
   * This table is reset in case one of parameters has changed:
   *   * DR
   *   * TxPower
   *   * NbTrans
   * </pre>
   *
   * <code>repeated .internal.UplinkAdrHistory uplink_adr_history = 30;</code>
   */
  int getUplinkAdrHistoryCount();
  /**
   * <pre>
   * Uplink history for ADR (last 20 uplink transmissions).
   * This table is reset in case one of parameters has changed:
   *   * DR
   *   * TxPower
   *   * NbTrans
   * </pre>
   *
   * <code>repeated .internal.UplinkAdrHistory uplink_adr_history = 30;</code>
   */
  java.util.List<? extends io.chirpstack.internal.UplinkAdrHistoryOrBuilder> 
      getUplinkAdrHistoryOrBuilderList();
  /**
   * <pre>
   * Uplink history for ADR (last 20 uplink transmissions).
   * This table is reset in case one of parameters has changed:
   *   * DR
   *   * TxPower
   *   * NbTrans
   * </pre>
   *
   * <code>repeated .internal.UplinkAdrHistory uplink_adr_history = 30;</code>
   */
  io.chirpstack.internal.UplinkAdrHistoryOrBuilder getUplinkAdrHistoryOrBuilder(
      int index);

  /**
   * <pre>
   * Mac-command error count.
   * </pre>
   *
   * <code>map&lt;uint32, uint32&gt; mac_command_error_count = 31;</code>
   */
  int getMacCommandErrorCountCount();
  /**
   * <pre>
   * Mac-command error count.
   * </pre>
   *
   * <code>map&lt;uint32, uint32&gt; mac_command_error_count = 31;</code>
   */
  boolean containsMacCommandErrorCount(
      int key);
  /**
   * Use {@link #getMacCommandErrorCountMap()} instead.
   */
  @java.lang.Deprecated
  java.util.Map<java.lang.Integer, java.lang.Integer>
  getMacCommandErrorCount();
  /**
   * <pre>
   * Mac-command error count.
   * </pre>
   *
   * <code>map&lt;uint32, uint32&gt; mac_command_error_count = 31;</code>
   */
  java.util.Map<java.lang.Integer, java.lang.Integer>
  getMacCommandErrorCountMap();
  /**
   * <pre>
   * Mac-command error count.
   * </pre>
   *
   * <code>map&lt;uint32, uint32&gt; mac_command_error_count = 31;</code>
   */
  int getMacCommandErrorCountOrDefault(
      int key,
      int defaultValue);
  /**
   * <pre>
   * Mac-command error count.
   * </pre>
   *
   * <code>map&lt;uint32, uint32&gt; mac_command_error_count = 31;</code>
   */
  int getMacCommandErrorCountOrThrow(
      int key);

  /**
   * <pre>
   * Last device-status request.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp last_device_status_request = 32;</code>
   * @return Whether the lastDeviceStatusRequest field is set.
   */
  boolean hasLastDeviceStatusRequest();
  /**
   * <pre>
   * Last device-status request.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp last_device_status_request = 32;</code>
   * @return The lastDeviceStatusRequest.
   */
  com.google.protobuf.Timestamp getLastDeviceStatusRequest();
  /**
   * <pre>
   * Last device-status request.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp last_device_status_request = 32;</code>
   */
  com.google.protobuf.TimestampOrBuilder getLastDeviceStatusRequestOrBuilder();

  /**
   * <pre>
   * RejoinRequestEnabled defines if the rejoin-request is enabled on the
   * device.
   * </pre>
   *
   * <code>bool rejoin_request_enabled = 33;</code>
   * @return The rejoinRequestEnabled.
   */
  boolean getRejoinRequestEnabled();

  /**
   * <pre>
   * RejoinRequestMaxCountN defines the 2^(C+4) uplink message interval for
   * the rejoin-request.
   * </pre>
   *
   * <code>uint32 rejoin_request_max_count_n = 34;</code>
   * @return The rejoinRequestMaxCountN.
   */
  int getRejoinRequestMaxCountN();

  /**
   * <pre>
   * RejoinRequestMaxTimeN defines the 2^(T+10) time interval (seconds)
   * for the rejoin-request.
   * </pre>
   *
   * <code>uint32 rejoin_request_max_time_n = 35;</code>
   * @return The rejoinRequestMaxTimeN.
   */
  int getRejoinRequestMaxTimeN();

  /**
   * <pre>
   * Rejoin counter (RJCount0).
   * This counter is reset to 0 after each successful join-accept.
   * </pre>
   *
   * <code>uint32 rejoin_count_0 = 36;</code>
   * @return The rejoinCount0.
   */
  int getRejoinCount0();

  /**
   * <pre>
   * Uplink dwell time.
   * </pre>
   *
   * <code>bool uplink_dwell_time_400ms = 37;</code>
   * @return The uplinkDwellTime400ms.
   */
  boolean getUplinkDwellTime400Ms();

  /**
   * <pre>
   * Downlink dwell time.
   * </pre>
   *
   * <code>bool downlink_dwell_time_400ms = 38;</code>
   * @return The downlinkDwellTime400ms.
   */
  boolean getDownlinkDwellTime400Ms();

  /**
   * <pre>
   * Uplink max. EIRP index.
   * </pre>
   *
   * <code>uint32 uplink_max_eirp_index = 39;</code>
   * @return The uplinkMaxEirpIndex.
   */
  int getUplinkMaxEirpIndex();

  /**
   * <pre>
   * Region configuration ID.
   * </pre>
   *
   * <code>string region_config_id = 40;</code>
   * @return The regionConfigId.
   */
  java.lang.String getRegionConfigId();
  /**
   * <pre>
   * Region configuration ID.
   * </pre>
   *
   * <code>string region_config_id = 40;</code>
   * @return The bytes for regionConfigId.
   */
  com.google.protobuf.ByteString
      getRegionConfigIdBytes();

  /**
   * <pre>
   * Relay state.
   * </pre>
   *
   * <code>.internal.Relay relay = 41;</code>
   * @return Whether the relay field is set.
   */
  boolean hasRelay();
  /**
   * <pre>
   * Relay state.
   * </pre>
   *
   * <code>.internal.Relay relay = 41;</code>
   * @return The relay.
   */
  io.chirpstack.internal.Relay getRelay();
  /**
   * <pre>
   * Relay state.
   * </pre>
   *
   * <code>.internal.Relay relay = 41;</code>
   */
  io.chirpstack.internal.RelayOrBuilder getRelayOrBuilder();
}
