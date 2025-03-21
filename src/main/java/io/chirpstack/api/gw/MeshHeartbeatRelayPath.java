// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: gw/gw.proto
// Protobuf Java Version: 4.29.2

package io.chirpstack.api.gw;

/**
 * Protobuf type {@code gw.MeshHeartbeatRelayPath}
 */
public final class MeshHeartbeatRelayPath extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:gw.MeshHeartbeatRelayPath)
    MeshHeartbeatRelayPathOrBuilder {
private static final long serialVersionUID = 0L;
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 29,
      /* patch= */ 2,
      /* suffix= */ "",
      MeshHeartbeatRelayPath.class.getName());
  }
  // Use MeshHeartbeatRelayPath.newBuilder() to construct.
  private MeshHeartbeatRelayPath(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private MeshHeartbeatRelayPath() {
    relayId_ = "";
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return io.chirpstack.api.gw.GatewayProto.internal_static_gw_MeshHeartbeatRelayPath_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return io.chirpstack.api.gw.GatewayProto.internal_static_gw_MeshHeartbeatRelayPath_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            io.chirpstack.api.gw.MeshHeartbeatRelayPath.class, io.chirpstack.api.gw.MeshHeartbeatRelayPath.Builder.class);
  }

  public static final int RELAY_ID_FIELD_NUMBER = 1;
  @SuppressWarnings("serial")
  private volatile java.lang.Object relayId_ = "";
  /**
   * <pre>
   * Relay ID.
   * </pre>
   *
   * <code>string relay_id = 1;</code>
   * @return The relayId.
   */
  @java.lang.Override
  public java.lang.String getRelayId() {
    java.lang.Object ref = relayId_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      relayId_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * Relay ID.
   * </pre>
   *
   * <code>string relay_id = 1;</code>
   * @return The bytes for relayId.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getRelayIdBytes() {
    java.lang.Object ref = relayId_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      relayId_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int RSSI_FIELD_NUMBER = 2;
  private int rssi_ = 0;
  /**
   * <pre>
   * RSSI.
   * </pre>
   *
   * <code>int32 rssi = 2;</code>
   * @return The rssi.
   */
  @java.lang.Override
  public int getRssi() {
    return rssi_;
  }

  public static final int SNR_FIELD_NUMBER = 3;
  private int snr_ = 0;
  /**
   * <pre>
   * SNR.
   * </pre>
   *
   * <code>int32 snr = 3;</code>
   * @return The snr.
   */
  @java.lang.Override
  public int getSnr() {
    return snr_;
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
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(relayId_)) {
      com.google.protobuf.GeneratedMessage.writeString(output, 1, relayId_);
    }
    if (rssi_ != 0) {
      output.writeInt32(2, rssi_);
    }
    if (snr_ != 0) {
      output.writeInt32(3, snr_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(relayId_)) {
      size += com.google.protobuf.GeneratedMessage.computeStringSize(1, relayId_);
    }
    if (rssi_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(2, rssi_);
    }
    if (snr_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(3, snr_);
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
    if (!(obj instanceof io.chirpstack.api.gw.MeshHeartbeatRelayPath)) {
      return super.equals(obj);
    }
    io.chirpstack.api.gw.MeshHeartbeatRelayPath other = (io.chirpstack.api.gw.MeshHeartbeatRelayPath) obj;

    if (!getRelayId()
        .equals(other.getRelayId())) return false;
    if (getRssi()
        != other.getRssi()) return false;
    if (getSnr()
        != other.getSnr()) return false;
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
    hash = (37 * hash) + RELAY_ID_FIELD_NUMBER;
    hash = (53 * hash) + getRelayId().hashCode();
    hash = (37 * hash) + RSSI_FIELD_NUMBER;
    hash = (53 * hash) + getRssi();
    hash = (37 * hash) + SNR_FIELD_NUMBER;
    hash = (53 * hash) + getSnr();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static io.chirpstack.api.gw.MeshHeartbeatRelayPath parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.gw.MeshHeartbeatRelayPath parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.gw.MeshHeartbeatRelayPath parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.gw.MeshHeartbeatRelayPath parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.gw.MeshHeartbeatRelayPath parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.gw.MeshHeartbeatRelayPath parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.gw.MeshHeartbeatRelayPath parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static io.chirpstack.api.gw.MeshHeartbeatRelayPath parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static io.chirpstack.api.gw.MeshHeartbeatRelayPath parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static io.chirpstack.api.gw.MeshHeartbeatRelayPath parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.chirpstack.api.gw.MeshHeartbeatRelayPath parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static io.chirpstack.api.gw.MeshHeartbeatRelayPath parseFrom(
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
  public static Builder newBuilder(io.chirpstack.api.gw.MeshHeartbeatRelayPath prototype) {
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
   * Protobuf type {@code gw.MeshHeartbeatRelayPath}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:gw.MeshHeartbeatRelayPath)
      io.chirpstack.api.gw.MeshHeartbeatRelayPathOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return io.chirpstack.api.gw.GatewayProto.internal_static_gw_MeshHeartbeatRelayPath_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return io.chirpstack.api.gw.GatewayProto.internal_static_gw_MeshHeartbeatRelayPath_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              io.chirpstack.api.gw.MeshHeartbeatRelayPath.class, io.chirpstack.api.gw.MeshHeartbeatRelayPath.Builder.class);
    }

    // Construct using io.chirpstack.api.gw.MeshHeartbeatRelayPath.newBuilder()
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
      relayId_ = "";
      rssi_ = 0;
      snr_ = 0;
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return io.chirpstack.api.gw.GatewayProto.internal_static_gw_MeshHeartbeatRelayPath_descriptor;
    }

    @java.lang.Override
    public io.chirpstack.api.gw.MeshHeartbeatRelayPath getDefaultInstanceForType() {
      return io.chirpstack.api.gw.MeshHeartbeatRelayPath.getDefaultInstance();
    }

    @java.lang.Override
    public io.chirpstack.api.gw.MeshHeartbeatRelayPath build() {
      io.chirpstack.api.gw.MeshHeartbeatRelayPath result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public io.chirpstack.api.gw.MeshHeartbeatRelayPath buildPartial() {
      io.chirpstack.api.gw.MeshHeartbeatRelayPath result = new io.chirpstack.api.gw.MeshHeartbeatRelayPath(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(io.chirpstack.api.gw.MeshHeartbeatRelayPath result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.relayId_ = relayId_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.rssi_ = rssi_;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.snr_ = snr_;
      }
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof io.chirpstack.api.gw.MeshHeartbeatRelayPath) {
        return mergeFrom((io.chirpstack.api.gw.MeshHeartbeatRelayPath)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(io.chirpstack.api.gw.MeshHeartbeatRelayPath other) {
      if (other == io.chirpstack.api.gw.MeshHeartbeatRelayPath.getDefaultInstance()) return this;
      if (!other.getRelayId().isEmpty()) {
        relayId_ = other.relayId_;
        bitField0_ |= 0x00000001;
        onChanged();
      }
      if (other.getRssi() != 0) {
        setRssi(other.getRssi());
      }
      if (other.getSnr() != 0) {
        setSnr(other.getSnr());
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
            case 10: {
              relayId_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000001;
              break;
            } // case 10
            case 16: {
              rssi_ = input.readInt32();
              bitField0_ |= 0x00000002;
              break;
            } // case 16
            case 24: {
              snr_ = input.readInt32();
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

    private java.lang.Object relayId_ = "";
    /**
     * <pre>
     * Relay ID.
     * </pre>
     *
     * <code>string relay_id = 1;</code>
     * @return The relayId.
     */
    public java.lang.String getRelayId() {
      java.lang.Object ref = relayId_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        relayId_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     * Relay ID.
     * </pre>
     *
     * <code>string relay_id = 1;</code>
     * @return The bytes for relayId.
     */
    public com.google.protobuf.ByteString
        getRelayIdBytes() {
      java.lang.Object ref = relayId_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        relayId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * Relay ID.
     * </pre>
     *
     * <code>string relay_id = 1;</code>
     * @param value The relayId to set.
     * @return This builder for chaining.
     */
    public Builder setRelayId(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      relayId_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Relay ID.
     * </pre>
     *
     * <code>string relay_id = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearRelayId() {
      relayId_ = getDefaultInstance().getRelayId();
      bitField0_ = (bitField0_ & ~0x00000001);
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Relay ID.
     * </pre>
     *
     * <code>string relay_id = 1;</code>
     * @param value The bytes for relayId to set.
     * @return This builder for chaining.
     */
    public Builder setRelayIdBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      relayId_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }

    private int rssi_ ;
    /**
     * <pre>
     * RSSI.
     * </pre>
     *
     * <code>int32 rssi = 2;</code>
     * @return The rssi.
     */
    @java.lang.Override
    public int getRssi() {
      return rssi_;
    }
    /**
     * <pre>
     * RSSI.
     * </pre>
     *
     * <code>int32 rssi = 2;</code>
     * @param value The rssi to set.
     * @return This builder for chaining.
     */
    public Builder setRssi(int value) {

      rssi_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * RSSI.
     * </pre>
     *
     * <code>int32 rssi = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearRssi() {
      bitField0_ = (bitField0_ & ~0x00000002);
      rssi_ = 0;
      onChanged();
      return this;
    }

    private int snr_ ;
    /**
     * <pre>
     * SNR.
     * </pre>
     *
     * <code>int32 snr = 3;</code>
     * @return The snr.
     */
    @java.lang.Override
    public int getSnr() {
      return snr_;
    }
    /**
     * <pre>
     * SNR.
     * </pre>
     *
     * <code>int32 snr = 3;</code>
     * @param value The snr to set.
     * @return This builder for chaining.
     */
    public Builder setSnr(int value) {

      snr_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * SNR.
     * </pre>
     *
     * <code>int32 snr = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearSnr() {
      bitField0_ = (bitField0_ & ~0x00000004);
      snr_ = 0;
      onChanged();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:gw.MeshHeartbeatRelayPath)
  }

  // @@protoc_insertion_point(class_scope:gw.MeshHeartbeatRelayPath)
  private static final io.chirpstack.api.gw.MeshHeartbeatRelayPath DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new io.chirpstack.api.gw.MeshHeartbeatRelayPath();
  }

  public static io.chirpstack.api.gw.MeshHeartbeatRelayPath getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<MeshHeartbeatRelayPath>
      PARSER = new com.google.protobuf.AbstractParser<MeshHeartbeatRelayPath>() {
    @java.lang.Override
    public MeshHeartbeatRelayPath parsePartialFrom(
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

  public static com.google.protobuf.Parser<MeshHeartbeatRelayPath> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<MeshHeartbeatRelayPath> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public io.chirpstack.api.gw.MeshHeartbeatRelayPath getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

