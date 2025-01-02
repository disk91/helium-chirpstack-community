// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: internal/internal.proto
// Protobuf Java Version: 4.29.2

package io.chirpstack.internal;

/**
 * Protobuf type {@code internal.UplinkAdrHistory}
 */
public final class UplinkAdrHistory extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:internal.UplinkAdrHistory)
    UplinkAdrHistoryOrBuilder {
private static final long serialVersionUID = 0L;
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 29,
      /* patch= */ 2,
      /* suffix= */ "",
      UplinkAdrHistory.class.getName());
  }
  // Use UplinkAdrHistory.newBuilder() to construct.
  private UplinkAdrHistory(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private UplinkAdrHistory() {
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return io.chirpstack.internal.InternalProto.internal_static_internal_UplinkAdrHistory_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return io.chirpstack.internal.InternalProto.internal_static_internal_UplinkAdrHistory_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            io.chirpstack.internal.UplinkAdrHistory.class, io.chirpstack.internal.UplinkAdrHistory.Builder.class);
  }

  public static final int F_CNT_FIELD_NUMBER = 1;
  private int fCnt_ = 0;
  /**
   * <pre>
   * Uplink frame-counter.
   * </pre>
   *
   * <code>uint32 f_cnt = 1;</code>
   * @return The fCnt.
   */
  @java.lang.Override
  public int getFCnt() {
    return fCnt_;
  }

  public static final int MAX_SNR_FIELD_NUMBER = 2;
  private float maxSnr_ = 0F;
  /**
   * <pre>
   * Max SNR (of deduplicated frames received by one or multiple gateways).
   * </pre>
   *
   * <code>float max_snr = 2;</code>
   * @return The maxSnr.
   */
  @java.lang.Override
  public float getMaxSnr() {
    return maxSnr_;
  }

  public static final int MAX_RSSI_FIELD_NUMBER = 5;
  private int maxRssi_ = 0;
  /**
   * <pre>
   * Max RSSI.
   * </pre>
   *
   * <code>int32 max_rssi = 5;</code>
   * @return The maxRssi.
   */
  @java.lang.Override
  public int getMaxRssi() {
    return maxRssi_;
  }

  public static final int TX_POWER_INDEX_FIELD_NUMBER = 3;
  private int txPowerIndex_ = 0;
  /**
   * <pre>
   * TX Power (as known by the network-server).
   * </pre>
   *
   * <code>uint32 tx_power_index = 3;</code>
   * @return The txPowerIndex.
   */
  @java.lang.Override
  public int getTxPowerIndex() {
    return txPowerIndex_;
  }

  public static final int GATEWAY_COUNT_FIELD_NUMBER = 4;
  private int gatewayCount_ = 0;
  /**
   * <pre>
   * Number of receiving gateways.
   * </pre>
   *
   * <code>uint32 gateway_count = 4;</code>
   * @return The gatewayCount.
   */
  @java.lang.Override
  public int getGatewayCount() {
    return gatewayCount_;
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (fCnt_ != 0) {
      output.writeUInt32(1, fCnt_);
    }
    if (java.lang.Float.floatToRawIntBits(maxSnr_) != 0) {
      output.writeFloat(2, maxSnr_);
    }
    if (txPowerIndex_ != 0) {
      output.writeUInt32(3, txPowerIndex_);
    }
    if (gatewayCount_ != 0) {
      output.writeUInt32(4, gatewayCount_);
    }
    if (maxRssi_ != 0) {
      output.writeInt32(5, maxRssi_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (fCnt_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt32Size(1, fCnt_);
    }
    if (java.lang.Float.floatToRawIntBits(maxSnr_) != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeFloatSize(2, maxSnr_);
    }
    if (txPowerIndex_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt32Size(3, txPowerIndex_);
    }
    if (gatewayCount_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt32Size(4, gatewayCount_);
    }
    if (maxRssi_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(5, maxRssi_);
    }
    size += getUnknownFields().getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof io.chirpstack.internal.UplinkAdrHistory)) {
      return super.equals(obj);
    }
    io.chirpstack.internal.UplinkAdrHistory other = (io.chirpstack.internal.UplinkAdrHistory) obj;

    if (getFCnt()
        != other.getFCnt()) return false;
    if (java.lang.Float.floatToIntBits(getMaxSnr())
        != java.lang.Float.floatToIntBits(
            other.getMaxSnr())) return false;
    if (getMaxRssi()
        != other.getMaxRssi()) return false;
    if (getTxPowerIndex()
        != other.getTxPowerIndex()) return false;
    if (getGatewayCount()
        != other.getGatewayCount()) return false;
    if (!getUnknownFields().equals(other.getUnknownFields())) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + F_CNT_FIELD_NUMBER;
    hash = (53 * hash) + getFCnt();
    hash = (37 * hash) + MAX_SNR_FIELD_NUMBER;
    hash = (53 * hash) + java.lang.Float.floatToIntBits(
        getMaxSnr());
    hash = (37 * hash) + MAX_RSSI_FIELD_NUMBER;
    hash = (53 * hash) + getMaxRssi();
    hash = (37 * hash) + TX_POWER_INDEX_FIELD_NUMBER;
    hash = (53 * hash) + getTxPowerIndex();
    hash = (37 * hash) + GATEWAY_COUNT_FIELD_NUMBER;
    hash = (53 * hash) + getGatewayCount();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static io.chirpstack.internal.UplinkAdrHistory parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.internal.UplinkAdrHistory parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.internal.UplinkAdrHistory parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.internal.UplinkAdrHistory parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.internal.UplinkAdrHistory parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.internal.UplinkAdrHistory parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.internal.UplinkAdrHistory parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static io.chirpstack.internal.UplinkAdrHistory parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static io.chirpstack.internal.UplinkAdrHistory parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static io.chirpstack.internal.UplinkAdrHistory parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.chirpstack.internal.UplinkAdrHistory parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static io.chirpstack.internal.UplinkAdrHistory parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(io.chirpstack.internal.UplinkAdrHistory prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessage.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code internal.UplinkAdrHistory}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:internal.UplinkAdrHistory)
      io.chirpstack.internal.UplinkAdrHistoryOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return io.chirpstack.internal.InternalProto.internal_static_internal_UplinkAdrHistory_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return io.chirpstack.internal.InternalProto.internal_static_internal_UplinkAdrHistory_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              io.chirpstack.internal.UplinkAdrHistory.class, io.chirpstack.internal.UplinkAdrHistory.Builder.class);
    }

    // Construct using io.chirpstack.internal.UplinkAdrHistory.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      fCnt_ = 0;
      maxSnr_ = 0F;
      maxRssi_ = 0;
      txPowerIndex_ = 0;
      gatewayCount_ = 0;
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return io.chirpstack.internal.InternalProto.internal_static_internal_UplinkAdrHistory_descriptor;
    }

    @java.lang.Override
    public io.chirpstack.internal.UplinkAdrHistory getDefaultInstanceForType() {
      return io.chirpstack.internal.UplinkAdrHistory.getDefaultInstance();
    }

    @java.lang.Override
    public io.chirpstack.internal.UplinkAdrHistory build() {
      io.chirpstack.internal.UplinkAdrHistory result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public io.chirpstack.internal.UplinkAdrHistory buildPartial() {
      io.chirpstack.internal.UplinkAdrHistory result = new io.chirpstack.internal.UplinkAdrHistory(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(io.chirpstack.internal.UplinkAdrHistory result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.fCnt_ = fCnt_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.maxSnr_ = maxSnr_;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.maxRssi_ = maxRssi_;
      }
      if (((from_bitField0_ & 0x00000008) != 0)) {
        result.txPowerIndex_ = txPowerIndex_;
      }
      if (((from_bitField0_ & 0x00000010) != 0)) {
        result.gatewayCount_ = gatewayCount_;
      }
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof io.chirpstack.internal.UplinkAdrHistory) {
        return mergeFrom((io.chirpstack.internal.UplinkAdrHistory)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(io.chirpstack.internal.UplinkAdrHistory other) {
      if (other == io.chirpstack.internal.UplinkAdrHistory.getDefaultInstance()) return this;
      if (other.getFCnt() != 0) {
        setFCnt(other.getFCnt());
      }
      if (other.getMaxSnr() != 0F) {
        setMaxSnr(other.getMaxSnr());
      }
      if (other.getMaxRssi() != 0) {
        setMaxRssi(other.getMaxRssi());
      }
      if (other.getTxPowerIndex() != 0) {
        setTxPowerIndex(other.getTxPowerIndex());
      }
      if (other.getGatewayCount() != 0) {
        setGatewayCount(other.getGatewayCount());
      }
      this.mergeUnknownFields(other.getUnknownFields());
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 8: {
              fCnt_ = input.readUInt32();
              bitField0_ |= 0x00000001;
              break;
            } // case 8
            case 21: {
              maxSnr_ = input.readFloat();
              bitField0_ |= 0x00000002;
              break;
            } // case 21
            case 24: {
              txPowerIndex_ = input.readUInt32();
              bitField0_ |= 0x00000008;
              break;
            } // case 24
            case 32: {
              gatewayCount_ = input.readUInt32();
              bitField0_ |= 0x00000010;
              break;
            } // case 32
            case 40: {
              maxRssi_ = input.readInt32();
              bitField0_ |= 0x00000004;
              break;
            } // case 40
            default: {
              if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                done = true; // was an endgroup tag
              }
              break;
            } // default:
          } // switch (tag)
        } // while (!done)
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.unwrapIOException();
      } finally {
        onChanged();
      } // finally
      return this;
    }
    private int bitField0_;

    private int fCnt_ ;
    /**
     * <pre>
     * Uplink frame-counter.
     * </pre>
     *
     * <code>uint32 f_cnt = 1;</code>
     * @return The fCnt.
     */
    @java.lang.Override
    public int getFCnt() {
      return fCnt_;
    }
    /**
     * <pre>
     * Uplink frame-counter.
     * </pre>
     *
     * <code>uint32 f_cnt = 1;</code>
     * @param value The fCnt to set.
     * @return This builder for chaining.
     */
    public Builder setFCnt(int value) {

      fCnt_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Uplink frame-counter.
     * </pre>
     *
     * <code>uint32 f_cnt = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearFCnt() {
      bitField0_ = (bitField0_ & ~0x00000001);
      fCnt_ = 0;
      onChanged();
      return this;
    }

    private float maxSnr_ ;
    /**
     * <pre>
     * Max SNR (of deduplicated frames received by one or multiple gateways).
     * </pre>
     *
     * <code>float max_snr = 2;</code>
     * @return The maxSnr.
     */
    @java.lang.Override
    public float getMaxSnr() {
      return maxSnr_;
    }
    /**
     * <pre>
     * Max SNR (of deduplicated frames received by one or multiple gateways).
     * </pre>
     *
     * <code>float max_snr = 2;</code>
     * @param value The maxSnr to set.
     * @return This builder for chaining.
     */
    public Builder setMaxSnr(float value) {

      maxSnr_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Max SNR (of deduplicated frames received by one or multiple gateways).
     * </pre>
     *
     * <code>float max_snr = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearMaxSnr() {
      bitField0_ = (bitField0_ & ~0x00000002);
      maxSnr_ = 0F;
      onChanged();
      return this;
    }

    private int maxRssi_ ;
    /**
     * <pre>
     * Max RSSI.
     * </pre>
     *
     * <code>int32 max_rssi = 5;</code>
     * @return The maxRssi.
     */
    @java.lang.Override
    public int getMaxRssi() {
      return maxRssi_;
    }
    /**
     * <pre>
     * Max RSSI.
     * </pre>
     *
     * <code>int32 max_rssi = 5;</code>
     * @param value The maxRssi to set.
     * @return This builder for chaining.
     */
    public Builder setMaxRssi(int value) {

      maxRssi_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Max RSSI.
     * </pre>
     *
     * <code>int32 max_rssi = 5;</code>
     * @return This builder for chaining.
     */
    public Builder clearMaxRssi() {
      bitField0_ = (bitField0_ & ~0x00000004);
      maxRssi_ = 0;
      onChanged();
      return this;
    }

    private int txPowerIndex_ ;
    /**
     * <pre>
     * TX Power (as known by the network-server).
     * </pre>
     *
     * <code>uint32 tx_power_index = 3;</code>
     * @return The txPowerIndex.
     */
    @java.lang.Override
    public int getTxPowerIndex() {
      return txPowerIndex_;
    }
    /**
     * <pre>
     * TX Power (as known by the network-server).
     * </pre>
     *
     * <code>uint32 tx_power_index = 3;</code>
     * @param value The txPowerIndex to set.
     * @return This builder for chaining.
     */
    public Builder setTxPowerIndex(int value) {

      txPowerIndex_ = value;
      bitField0_ |= 0x00000008;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * TX Power (as known by the network-server).
     * </pre>
     *
     * <code>uint32 tx_power_index = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearTxPowerIndex() {
      bitField0_ = (bitField0_ & ~0x00000008);
      txPowerIndex_ = 0;
      onChanged();
      return this;
    }

    private int gatewayCount_ ;
    /**
     * <pre>
     * Number of receiving gateways.
     * </pre>
     *
     * <code>uint32 gateway_count = 4;</code>
     * @return The gatewayCount.
     */
    @java.lang.Override
    public int getGatewayCount() {
      return gatewayCount_;
    }
    /**
     * <pre>
     * Number of receiving gateways.
     * </pre>
     *
     * <code>uint32 gateway_count = 4;</code>
     * @param value The gatewayCount to set.
     * @return This builder for chaining.
     */
    public Builder setGatewayCount(int value) {

      gatewayCount_ = value;
      bitField0_ |= 0x00000010;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Number of receiving gateways.
     * </pre>
     *
     * <code>uint32 gateway_count = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearGatewayCount() {
      bitField0_ = (bitField0_ & ~0x00000010);
      gatewayCount_ = 0;
      onChanged();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:internal.UplinkAdrHistory)
  }

  // @@protoc_insertion_point(class_scope:internal.UplinkAdrHistory)
  private static final io.chirpstack.internal.UplinkAdrHistory DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new io.chirpstack.internal.UplinkAdrHistory();
  }

  public static io.chirpstack.internal.UplinkAdrHistory getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<UplinkAdrHistory>
      PARSER = new com.google.protobuf.AbstractParser<UplinkAdrHistory>() {
    @java.lang.Override
    public UplinkAdrHistory parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      Builder builder = newBuilder();
      try {
        builder.mergeFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(builder.buildPartial());
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(e)
            .setUnfinishedMessage(builder.buildPartial());
      }
      return builder.buildPartial();
    }
  };

  public static com.google.protobuf.Parser<UplinkAdrHistory> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<UplinkAdrHistory> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public io.chirpstack.internal.UplinkAdrHistory getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

