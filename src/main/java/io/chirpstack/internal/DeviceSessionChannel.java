// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: internal/internal.proto
// Protobuf Java Version: 4.29.2

package io.chirpstack.internal;

/**
 * Protobuf type {@code internal.DeviceSessionChannel}
 */
public final class DeviceSessionChannel extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:internal.DeviceSessionChannel)
    DeviceSessionChannelOrBuilder {
private static final long serialVersionUID = 0L;
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 29,
      /* patch= */ 2,
      /* suffix= */ "",
      DeviceSessionChannel.class.getName());
  }
  // Use DeviceSessionChannel.newBuilder() to construct.
  private DeviceSessionChannel(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private DeviceSessionChannel() {
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return io.chirpstack.internal.InternalProto.internal_static_internal_DeviceSessionChannel_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return io.chirpstack.internal.InternalProto.internal_static_internal_DeviceSessionChannel_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            io.chirpstack.internal.DeviceSessionChannel.class, io.chirpstack.internal.DeviceSessionChannel.Builder.class);
  }

  public static final int FREQUENCY_FIELD_NUMBER = 1;
  private int frequency_ = 0;
  /**
   * <pre>
   * Frequency Hz.
   * </pre>
   *
   * <code>uint32 frequency = 1;</code>
   * @return The frequency.
   */
  @java.lang.Override
  public int getFrequency() {
    return frequency_;
  }

  public static final int MIN_DR_FIELD_NUMBER = 2;
  private int minDr_ = 0;
  /**
   * <pre>
   * Min. data-rate.
   * </pre>
   *
   * <code>uint32 min_dr = 2;</code>
   * @return The minDr.
   */
  @java.lang.Override
  public int getMinDr() {
    return minDr_;
  }

  public static final int MAX_DR_FIELD_NUMBER = 3;
  private int maxDr_ = 0;
  /**
   * <pre>
   * Max. data-rate.
   * </pre>
   *
   * <code>uint32 max_dr = 3;</code>
   * @return The maxDr.
   */
  @java.lang.Override
  public int getMaxDr() {
    return maxDr_;
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
    if (frequency_ != 0) {
      output.writeUInt32(1, frequency_);
    }
    if (minDr_ != 0) {
      output.writeUInt32(2, minDr_);
    }
    if (maxDr_ != 0) {
      output.writeUInt32(3, maxDr_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (frequency_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt32Size(1, frequency_);
    }
    if (minDr_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt32Size(2, minDr_);
    }
    if (maxDr_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt32Size(3, maxDr_);
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
    if (!(obj instanceof io.chirpstack.internal.DeviceSessionChannel)) {
      return super.equals(obj);
    }
    io.chirpstack.internal.DeviceSessionChannel other = (io.chirpstack.internal.DeviceSessionChannel) obj;

    if (getFrequency()
        != other.getFrequency()) return false;
    if (getMinDr()
        != other.getMinDr()) return false;
    if (getMaxDr()
        != other.getMaxDr()) return false;
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
    hash = (37 * hash) + FREQUENCY_FIELD_NUMBER;
    hash = (53 * hash) + getFrequency();
    hash = (37 * hash) + MIN_DR_FIELD_NUMBER;
    hash = (53 * hash) + getMinDr();
    hash = (37 * hash) + MAX_DR_FIELD_NUMBER;
    hash = (53 * hash) + getMaxDr();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static io.chirpstack.internal.DeviceSessionChannel parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.internal.DeviceSessionChannel parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.internal.DeviceSessionChannel parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.internal.DeviceSessionChannel parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.internal.DeviceSessionChannel parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.internal.DeviceSessionChannel parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.internal.DeviceSessionChannel parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static io.chirpstack.internal.DeviceSessionChannel parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static io.chirpstack.internal.DeviceSessionChannel parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static io.chirpstack.internal.DeviceSessionChannel parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.chirpstack.internal.DeviceSessionChannel parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static io.chirpstack.internal.DeviceSessionChannel parseFrom(
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
  public static Builder newBuilder(io.chirpstack.internal.DeviceSessionChannel prototype) {
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
   * Protobuf type {@code internal.DeviceSessionChannel}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:internal.DeviceSessionChannel)
      io.chirpstack.internal.DeviceSessionChannelOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return io.chirpstack.internal.InternalProto.internal_static_internal_DeviceSessionChannel_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return io.chirpstack.internal.InternalProto.internal_static_internal_DeviceSessionChannel_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              io.chirpstack.internal.DeviceSessionChannel.class, io.chirpstack.internal.DeviceSessionChannel.Builder.class);
    }

    // Construct using io.chirpstack.internal.DeviceSessionChannel.newBuilder()
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
      frequency_ = 0;
      minDr_ = 0;
      maxDr_ = 0;
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return io.chirpstack.internal.InternalProto.internal_static_internal_DeviceSessionChannel_descriptor;
    }

    @java.lang.Override
    public io.chirpstack.internal.DeviceSessionChannel getDefaultInstanceForType() {
      return io.chirpstack.internal.DeviceSessionChannel.getDefaultInstance();
    }

    @java.lang.Override
    public io.chirpstack.internal.DeviceSessionChannel build() {
      io.chirpstack.internal.DeviceSessionChannel result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public io.chirpstack.internal.DeviceSessionChannel buildPartial() {
      io.chirpstack.internal.DeviceSessionChannel result = new io.chirpstack.internal.DeviceSessionChannel(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(io.chirpstack.internal.DeviceSessionChannel result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.frequency_ = frequency_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.minDr_ = minDr_;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.maxDr_ = maxDr_;
      }
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof io.chirpstack.internal.DeviceSessionChannel) {
        return mergeFrom((io.chirpstack.internal.DeviceSessionChannel)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(io.chirpstack.internal.DeviceSessionChannel other) {
      if (other == io.chirpstack.internal.DeviceSessionChannel.getDefaultInstance()) return this;
      if (other.getFrequency() != 0) {
        setFrequency(other.getFrequency());
      }
      if (other.getMinDr() != 0) {
        setMinDr(other.getMinDr());
      }
      if (other.getMaxDr() != 0) {
        setMaxDr(other.getMaxDr());
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
              frequency_ = input.readUInt32();
              bitField0_ |= 0x00000001;
              break;
            } // case 8
            case 16: {
              minDr_ = input.readUInt32();
              bitField0_ |= 0x00000002;
              break;
            } // case 16
            case 24: {
              maxDr_ = input.readUInt32();
              bitField0_ |= 0x00000004;
              break;
            } // case 24
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

    private int frequency_ ;
    /**
     * <pre>
     * Frequency Hz.
     * </pre>
     *
     * <code>uint32 frequency = 1;</code>
     * @return The frequency.
     */
    @java.lang.Override
    public int getFrequency() {
      return frequency_;
    }
    /**
     * <pre>
     * Frequency Hz.
     * </pre>
     *
     * <code>uint32 frequency = 1;</code>
     * @param value The frequency to set.
     * @return This builder for chaining.
     */
    public Builder setFrequency(int value) {

      frequency_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Frequency Hz.
     * </pre>
     *
     * <code>uint32 frequency = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearFrequency() {
      bitField0_ = (bitField0_ & ~0x00000001);
      frequency_ = 0;
      onChanged();
      return this;
    }

    private int minDr_ ;
    /**
     * <pre>
     * Min. data-rate.
     * </pre>
     *
     * <code>uint32 min_dr = 2;</code>
     * @return The minDr.
     */
    @java.lang.Override
    public int getMinDr() {
      return minDr_;
    }
    /**
     * <pre>
     * Min. data-rate.
     * </pre>
     *
     * <code>uint32 min_dr = 2;</code>
     * @param value The minDr to set.
     * @return This builder for chaining.
     */
    public Builder setMinDr(int value) {

      minDr_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Min. data-rate.
     * </pre>
     *
     * <code>uint32 min_dr = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearMinDr() {
      bitField0_ = (bitField0_ & ~0x00000002);
      minDr_ = 0;
      onChanged();
      return this;
    }

    private int maxDr_ ;
    /**
     * <pre>
     * Max. data-rate.
     * </pre>
     *
     * <code>uint32 max_dr = 3;</code>
     * @return The maxDr.
     */
    @java.lang.Override
    public int getMaxDr() {
      return maxDr_;
    }
    /**
     * <pre>
     * Max. data-rate.
     * </pre>
     *
     * <code>uint32 max_dr = 3;</code>
     * @param value The maxDr to set.
     * @return This builder for chaining.
     */
    public Builder setMaxDr(int value) {

      maxDr_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Max. data-rate.
     * </pre>
     *
     * <code>uint32 max_dr = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearMaxDr() {
      bitField0_ = (bitField0_ & ~0x00000004);
      maxDr_ = 0;
      onChanged();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:internal.DeviceSessionChannel)
  }

  // @@protoc_insertion_point(class_scope:internal.DeviceSessionChannel)
  private static final io.chirpstack.internal.DeviceSessionChannel DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new io.chirpstack.internal.DeviceSessionChannel();
  }

  public static io.chirpstack.internal.DeviceSessionChannel getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<DeviceSessionChannel>
      PARSER = new com.google.protobuf.AbstractParser<DeviceSessionChannel>() {
    @java.lang.Override
    public DeviceSessionChannel parsePartialFrom(
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

  public static com.google.protobuf.Parser<DeviceSessionChannel> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<DeviceSessionChannel> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public io.chirpstack.internal.DeviceSessionChannel getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

