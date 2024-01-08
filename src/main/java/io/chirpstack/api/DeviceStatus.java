// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: api/device.proto

// Protobuf Java Version: 3.25.1
package io.chirpstack.api;

/**
 * Protobuf type {@code api.DeviceStatus}
 */
public final class DeviceStatus extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:api.DeviceStatus)
    DeviceStatusOrBuilder {
private static final long serialVersionUID = 0L;
  // Use DeviceStatus.newBuilder() to construct.
  private DeviceStatus(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private DeviceStatus() {
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new DeviceStatus();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return io.chirpstack.api.DeviceProto.internal_static_api_DeviceStatus_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return io.chirpstack.api.DeviceProto.internal_static_api_DeviceStatus_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            io.chirpstack.api.DeviceStatus.class, io.chirpstack.api.DeviceStatus.Builder.class);
  }

  public static final int MARGIN_FIELD_NUMBER = 1;
  private int margin_ = 0;
  /**
   * <pre>
   * The device margin status
   * -32..32: The demodulation SNR ration in dB
   * </pre>
   *
   * <code>int32 margin = 1;</code>
   * @return The margin.
   */
  @java.lang.Override
  public int getMargin() {
    return margin_;
  }

  public static final int EXTERNAL_POWER_SOURCE_FIELD_NUMBER = 2;
  private boolean externalPowerSource_ = false;
  /**
   * <pre>
   * Device is connected to an external power source.
   * </pre>
   *
   * <code>bool external_power_source = 2;</code>
   * @return The externalPowerSource.
   */
  @java.lang.Override
  public boolean getExternalPowerSource() {
    return externalPowerSource_;
  }

  public static final int BATTERY_LEVEL_FIELD_NUMBER = 3;
  private float batteryLevel_ = 0F;
  /**
   * <pre>
   * Device battery level as a percentage.
   * -1 when the battery level is not available.
   * </pre>
   *
   * <code>float battery_level = 3;</code>
   * @return The batteryLevel.
   */
  @java.lang.Override
  public float getBatteryLevel() {
    return batteryLevel_;
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
    if (margin_ != 0) {
      output.writeInt32(1, margin_);
    }
    if (externalPowerSource_ != false) {
      output.writeBool(2, externalPowerSource_);
    }
    if (java.lang.Float.floatToRawIntBits(batteryLevel_) != 0) {
      output.writeFloat(3, batteryLevel_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (margin_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(1, margin_);
    }
    if (externalPowerSource_ != false) {
      size += com.google.protobuf.CodedOutputStream
        .computeBoolSize(2, externalPowerSource_);
    }
    if (java.lang.Float.floatToRawIntBits(batteryLevel_) != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeFloatSize(3, batteryLevel_);
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
    if (!(obj instanceof io.chirpstack.api.DeviceStatus)) {
      return super.equals(obj);
    }
    io.chirpstack.api.DeviceStatus other = (io.chirpstack.api.DeviceStatus) obj;

    if (getMargin()
        != other.getMargin()) return false;
    if (getExternalPowerSource()
        != other.getExternalPowerSource()) return false;
    if (java.lang.Float.floatToIntBits(getBatteryLevel())
        != java.lang.Float.floatToIntBits(
            other.getBatteryLevel())) return false;
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
    hash = (37 * hash) + MARGIN_FIELD_NUMBER;
    hash = (53 * hash) + getMargin();
    hash = (37 * hash) + EXTERNAL_POWER_SOURCE_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
        getExternalPowerSource());
    hash = (37 * hash) + BATTERY_LEVEL_FIELD_NUMBER;
    hash = (53 * hash) + java.lang.Float.floatToIntBits(
        getBatteryLevel());
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static io.chirpstack.api.DeviceStatus parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.DeviceStatus parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.DeviceStatus parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.DeviceStatus parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.DeviceStatus parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.DeviceStatus parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.DeviceStatus parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static io.chirpstack.api.DeviceStatus parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static io.chirpstack.api.DeviceStatus parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static io.chirpstack.api.DeviceStatus parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.chirpstack.api.DeviceStatus parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static io.chirpstack.api.DeviceStatus parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(io.chirpstack.api.DeviceStatus prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code api.DeviceStatus}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:api.DeviceStatus)
      io.chirpstack.api.DeviceStatusOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return io.chirpstack.api.DeviceProto.internal_static_api_DeviceStatus_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return io.chirpstack.api.DeviceProto.internal_static_api_DeviceStatus_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              io.chirpstack.api.DeviceStatus.class, io.chirpstack.api.DeviceStatus.Builder.class);
    }

    // Construct using io.chirpstack.api.DeviceStatus.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      margin_ = 0;
      externalPowerSource_ = false;
      batteryLevel_ = 0F;
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return io.chirpstack.api.DeviceProto.internal_static_api_DeviceStatus_descriptor;
    }

    @java.lang.Override
    public io.chirpstack.api.DeviceStatus getDefaultInstanceForType() {
      return io.chirpstack.api.DeviceStatus.getDefaultInstance();
    }

    @java.lang.Override
    public io.chirpstack.api.DeviceStatus build() {
      io.chirpstack.api.DeviceStatus result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public io.chirpstack.api.DeviceStatus buildPartial() {
      io.chirpstack.api.DeviceStatus result = new io.chirpstack.api.DeviceStatus(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(io.chirpstack.api.DeviceStatus result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.margin_ = margin_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.externalPowerSource_ = externalPowerSource_;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.batteryLevel_ = batteryLevel_;
      }
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof io.chirpstack.api.DeviceStatus) {
        return mergeFrom((io.chirpstack.api.DeviceStatus)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(io.chirpstack.api.DeviceStatus other) {
      if (other == io.chirpstack.api.DeviceStatus.getDefaultInstance()) return this;
      if (other.getMargin() != 0) {
        setMargin(other.getMargin());
      }
      if (other.getExternalPowerSource() != false) {
        setExternalPowerSource(other.getExternalPowerSource());
      }
      if (other.getBatteryLevel() != 0F) {
        setBatteryLevel(other.getBatteryLevel());
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
              margin_ = input.readInt32();
              bitField0_ |= 0x00000001;
              break;
            } // case 8
            case 16: {
              externalPowerSource_ = input.readBool();
              bitField0_ |= 0x00000002;
              break;
            } // case 16
            case 29: {
              batteryLevel_ = input.readFloat();
              bitField0_ |= 0x00000004;
              break;
            } // case 29
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

    private int margin_ ;
    /**
     * <pre>
     * The device margin status
     * -32..32: The demodulation SNR ration in dB
     * </pre>
     *
     * <code>int32 margin = 1;</code>
     * @return The margin.
     */
    @java.lang.Override
    public int getMargin() {
      return margin_;
    }
    /**
     * <pre>
     * The device margin status
     * -32..32: The demodulation SNR ration in dB
     * </pre>
     *
     * <code>int32 margin = 1;</code>
     * @param value The margin to set.
     * @return This builder for chaining.
     */
    public Builder setMargin(int value) {

      margin_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * The device margin status
     * -32..32: The demodulation SNR ration in dB
     * </pre>
     *
     * <code>int32 margin = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearMargin() {
      bitField0_ = (bitField0_ & ~0x00000001);
      margin_ = 0;
      onChanged();
      return this;
    }

    private boolean externalPowerSource_ ;
    /**
     * <pre>
     * Device is connected to an external power source.
     * </pre>
     *
     * <code>bool external_power_source = 2;</code>
     * @return The externalPowerSource.
     */
    @java.lang.Override
    public boolean getExternalPowerSource() {
      return externalPowerSource_;
    }
    /**
     * <pre>
     * Device is connected to an external power source.
     * </pre>
     *
     * <code>bool external_power_source = 2;</code>
     * @param value The externalPowerSource to set.
     * @return This builder for chaining.
     */
    public Builder setExternalPowerSource(boolean value) {

      externalPowerSource_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Device is connected to an external power source.
     * </pre>
     *
     * <code>bool external_power_source = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearExternalPowerSource() {
      bitField0_ = (bitField0_ & ~0x00000002);
      externalPowerSource_ = false;
      onChanged();
      return this;
    }

    private float batteryLevel_ ;
    /**
     * <pre>
     * Device battery level as a percentage.
     * -1 when the battery level is not available.
     * </pre>
     *
     * <code>float battery_level = 3;</code>
     * @return The batteryLevel.
     */
    @java.lang.Override
    public float getBatteryLevel() {
      return batteryLevel_;
    }
    /**
     * <pre>
     * Device battery level as a percentage.
     * -1 when the battery level is not available.
     * </pre>
     *
     * <code>float battery_level = 3;</code>
     * @param value The batteryLevel to set.
     * @return This builder for chaining.
     */
    public Builder setBatteryLevel(float value) {

      batteryLevel_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Device battery level as a percentage.
     * -1 when the battery level is not available.
     * </pre>
     *
     * <code>float battery_level = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearBatteryLevel() {
      bitField0_ = (bitField0_ & ~0x00000004);
      batteryLevel_ = 0F;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:api.DeviceStatus)
  }

  // @@protoc_insertion_point(class_scope:api.DeviceStatus)
  private static final io.chirpstack.api.DeviceStatus DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new io.chirpstack.api.DeviceStatus();
  }

  public static io.chirpstack.api.DeviceStatus getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<DeviceStatus>
      PARSER = new com.google.protobuf.AbstractParser<DeviceStatus>() {
    @java.lang.Override
    public DeviceStatus parsePartialFrom(
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

  public static com.google.protobuf.Parser<DeviceStatus> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<DeviceStatus> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public io.chirpstack.api.DeviceStatus getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

