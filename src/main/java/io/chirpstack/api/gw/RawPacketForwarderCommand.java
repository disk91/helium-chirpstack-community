// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: gw/gw.proto

package io.chirpstack.api.gw;

/**
 * <pre>
 * RawPacketForwarderEvent contains a raw packet-forwarder command.
 * It can be used to access packet-forwarder features that are not (fully)
 * integrated with the ChirpStack Gateway Bridge.
 * </pre>
 *
 * Protobuf type {@code gw.RawPacketForwarderCommand}
 */
public final class RawPacketForwarderCommand extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:gw.RawPacketForwarderCommand)
    RawPacketForwarderCommandOrBuilder {
private static final long serialVersionUID = 0L;
  // Use RawPacketForwarderCommand.newBuilder() to construct.
  private RawPacketForwarderCommand(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private RawPacketForwarderCommand() {
    gatewayIdLegacy_ = com.google.protobuf.ByteString.EMPTY;
    gatewayId_ = "";
    payload_ = com.google.protobuf.ByteString.EMPTY;
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new RawPacketForwarderCommand();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return io.chirpstack.api.gw.GatewayProto.internal_static_gw_RawPacketForwarderCommand_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return io.chirpstack.api.gw.GatewayProto.internal_static_gw_RawPacketForwarderCommand_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            io.chirpstack.api.gw.RawPacketForwarderCommand.class, io.chirpstack.api.gw.RawPacketForwarderCommand.Builder.class);
  }

  public static final int GATEWAY_ID_LEGACY_FIELD_NUMBER = 1;
  private com.google.protobuf.ByteString gatewayIdLegacy_ = com.google.protobuf.ByteString.EMPTY;
  /**
   * <pre>
   * Gateway ID.
   * Deprecated: use gateway_id.
   * </pre>
   *
   * <code>bytes gateway_id_legacy = 1;</code>
   * @return The gatewayIdLegacy.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getGatewayIdLegacy() {
    return gatewayIdLegacy_;
  }

  public static final int GATEWAY_ID_FIELD_NUMBER = 4;
  @SuppressWarnings("serial")
  private volatile java.lang.Object gatewayId_ = "";
  /**
   * <pre>
   * Gateway ID.
   * </pre>
   *
   * <code>string gateway_id = 4;</code>
   * @return The gatewayId.
   */
  @java.lang.Override
  public java.lang.String getGatewayId() {
    java.lang.Object ref = gatewayId_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      gatewayId_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * Gateway ID.
   * </pre>
   *
   * <code>string gateway_id = 4;</code>
   * @return The bytes for gatewayId.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getGatewayIdBytes() {
    java.lang.Object ref = gatewayId_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      gatewayId_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int PAYLOAD_FIELD_NUMBER = 3;
  private com.google.protobuf.ByteString payload_ = com.google.protobuf.ByteString.EMPTY;
  /**
   * <pre>
   * Payload contains the raw payload.
   * </pre>
   *
   * <code>bytes payload = 3;</code>
   * @return The payload.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getPayload() {
    return payload_;
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
    if (!gatewayIdLegacy_.isEmpty()) {
      output.writeBytes(1, gatewayIdLegacy_);
    }
    if (!payload_.isEmpty()) {
      output.writeBytes(3, payload_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(gatewayId_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 4, gatewayId_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!gatewayIdLegacy_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(1, gatewayIdLegacy_);
    }
    if (!payload_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(3, payload_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(gatewayId_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, gatewayId_);
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
    if (!(obj instanceof io.chirpstack.api.gw.RawPacketForwarderCommand)) {
      return super.equals(obj);
    }
    io.chirpstack.api.gw.RawPacketForwarderCommand other = (io.chirpstack.api.gw.RawPacketForwarderCommand) obj;

    if (!getGatewayIdLegacy()
        .equals(other.getGatewayIdLegacy())) return false;
    if (!getGatewayId()
        .equals(other.getGatewayId())) return false;
    if (!getPayload()
        .equals(other.getPayload())) return false;
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
    hash = (37 * hash) + GATEWAY_ID_LEGACY_FIELD_NUMBER;
    hash = (53 * hash) + getGatewayIdLegacy().hashCode();
    hash = (37 * hash) + GATEWAY_ID_FIELD_NUMBER;
    hash = (53 * hash) + getGatewayId().hashCode();
    hash = (37 * hash) + PAYLOAD_FIELD_NUMBER;
    hash = (53 * hash) + getPayload().hashCode();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static io.chirpstack.api.gw.RawPacketForwarderCommand parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.gw.RawPacketForwarderCommand parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.gw.RawPacketForwarderCommand parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.gw.RawPacketForwarderCommand parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.gw.RawPacketForwarderCommand parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.gw.RawPacketForwarderCommand parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.gw.RawPacketForwarderCommand parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static io.chirpstack.api.gw.RawPacketForwarderCommand parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.chirpstack.api.gw.RawPacketForwarderCommand parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static io.chirpstack.api.gw.RawPacketForwarderCommand parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.chirpstack.api.gw.RawPacketForwarderCommand parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static io.chirpstack.api.gw.RawPacketForwarderCommand parseFrom(
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
  public static Builder newBuilder(io.chirpstack.api.gw.RawPacketForwarderCommand prototype) {
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
   * <pre>
   * RawPacketForwarderEvent contains a raw packet-forwarder command.
   * It can be used to access packet-forwarder features that are not (fully)
   * integrated with the ChirpStack Gateway Bridge.
   * </pre>
   *
   * Protobuf type {@code gw.RawPacketForwarderCommand}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:gw.RawPacketForwarderCommand)
      io.chirpstack.api.gw.RawPacketForwarderCommandOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return io.chirpstack.api.gw.GatewayProto.internal_static_gw_RawPacketForwarderCommand_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return io.chirpstack.api.gw.GatewayProto.internal_static_gw_RawPacketForwarderCommand_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              io.chirpstack.api.gw.RawPacketForwarderCommand.class, io.chirpstack.api.gw.RawPacketForwarderCommand.Builder.class);
    }

    // Construct using io.chirpstack.api.gw.RawPacketForwarderCommand.newBuilder()
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
      gatewayIdLegacy_ = com.google.protobuf.ByteString.EMPTY;
      gatewayId_ = "";
      payload_ = com.google.protobuf.ByteString.EMPTY;
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return io.chirpstack.api.gw.GatewayProto.internal_static_gw_RawPacketForwarderCommand_descriptor;
    }

    @java.lang.Override
    public io.chirpstack.api.gw.RawPacketForwarderCommand getDefaultInstanceForType() {
      return io.chirpstack.api.gw.RawPacketForwarderCommand.getDefaultInstance();
    }

    @java.lang.Override
    public io.chirpstack.api.gw.RawPacketForwarderCommand build() {
      io.chirpstack.api.gw.RawPacketForwarderCommand result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public io.chirpstack.api.gw.RawPacketForwarderCommand buildPartial() {
      io.chirpstack.api.gw.RawPacketForwarderCommand result = new io.chirpstack.api.gw.RawPacketForwarderCommand(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(io.chirpstack.api.gw.RawPacketForwarderCommand result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.gatewayIdLegacy_ = gatewayIdLegacy_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.gatewayId_ = gatewayId_;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.payload_ = payload_;
      }
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof io.chirpstack.api.gw.RawPacketForwarderCommand) {
        return mergeFrom((io.chirpstack.api.gw.RawPacketForwarderCommand)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(io.chirpstack.api.gw.RawPacketForwarderCommand other) {
      if (other == io.chirpstack.api.gw.RawPacketForwarderCommand.getDefaultInstance()) return this;
      if (other.getGatewayIdLegacy() != com.google.protobuf.ByteString.EMPTY) {
        setGatewayIdLegacy(other.getGatewayIdLegacy());
      }
      if (!other.getGatewayId().isEmpty()) {
        gatewayId_ = other.gatewayId_;
        bitField0_ |= 0x00000002;
        onChanged();
      }
      if (other.getPayload() != com.google.protobuf.ByteString.EMPTY) {
        setPayload(other.getPayload());
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
              gatewayIdLegacy_ = input.readBytes();
              bitField0_ |= 0x00000001;
              break;
            } // case 10
            case 26: {
              payload_ = input.readBytes();
              bitField0_ |= 0x00000004;
              break;
            } // case 26
            case 34: {
              gatewayId_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000002;
              break;
            } // case 34
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

    private com.google.protobuf.ByteString gatewayIdLegacy_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <pre>
     * Gateway ID.
     * Deprecated: use gateway_id.
     * </pre>
     *
     * <code>bytes gateway_id_legacy = 1;</code>
     * @return The gatewayIdLegacy.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getGatewayIdLegacy() {
      return gatewayIdLegacy_;
    }
    /**
     * <pre>
     * Gateway ID.
     * Deprecated: use gateway_id.
     * </pre>
     *
     * <code>bytes gateway_id_legacy = 1;</code>
     * @param value The gatewayIdLegacy to set.
     * @return This builder for chaining.
     */
    public Builder setGatewayIdLegacy(com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      gatewayIdLegacy_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Gateway ID.
     * Deprecated: use gateway_id.
     * </pre>
     *
     * <code>bytes gateway_id_legacy = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearGatewayIdLegacy() {
      bitField0_ = (bitField0_ & ~0x00000001);
      gatewayIdLegacy_ = getDefaultInstance().getGatewayIdLegacy();
      onChanged();
      return this;
    }

    private java.lang.Object gatewayId_ = "";
    /**
     * <pre>
     * Gateway ID.
     * </pre>
     *
     * <code>string gateway_id = 4;</code>
     * @return The gatewayId.
     */
    public java.lang.String getGatewayId() {
      java.lang.Object ref = gatewayId_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        gatewayId_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     * Gateway ID.
     * </pre>
     *
     * <code>string gateway_id = 4;</code>
     * @return The bytes for gatewayId.
     */
    public com.google.protobuf.ByteString
        getGatewayIdBytes() {
      java.lang.Object ref = gatewayId_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        gatewayId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * Gateway ID.
     * </pre>
     *
     * <code>string gateway_id = 4;</code>
     * @param value The gatewayId to set.
     * @return This builder for chaining.
     */
    public Builder setGatewayId(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      gatewayId_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Gateway ID.
     * </pre>
     *
     * <code>string gateway_id = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearGatewayId() {
      gatewayId_ = getDefaultInstance().getGatewayId();
      bitField0_ = (bitField0_ & ~0x00000002);
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Gateway ID.
     * </pre>
     *
     * <code>string gateway_id = 4;</code>
     * @param value The bytes for gatewayId to set.
     * @return This builder for chaining.
     */
    public Builder setGatewayIdBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      gatewayId_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }

    private com.google.protobuf.ByteString payload_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <pre>
     * Payload contains the raw payload.
     * </pre>
     *
     * <code>bytes payload = 3;</code>
     * @return The payload.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getPayload() {
      return payload_;
    }
    /**
     * <pre>
     * Payload contains the raw payload.
     * </pre>
     *
     * <code>bytes payload = 3;</code>
     * @param value The payload to set.
     * @return This builder for chaining.
     */
    public Builder setPayload(com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      payload_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Payload contains the raw payload.
     * </pre>
     *
     * <code>bytes payload = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearPayload() {
      bitField0_ = (bitField0_ & ~0x00000004);
      payload_ = getDefaultInstance().getPayload();
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


    // @@protoc_insertion_point(builder_scope:gw.RawPacketForwarderCommand)
  }

  // @@protoc_insertion_point(class_scope:gw.RawPacketForwarderCommand)
  private static final io.chirpstack.api.gw.RawPacketForwarderCommand DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new io.chirpstack.api.gw.RawPacketForwarderCommand();
  }

  public static io.chirpstack.api.gw.RawPacketForwarderCommand getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<RawPacketForwarderCommand>
      PARSER = new com.google.protobuf.AbstractParser<RawPacketForwarderCommand>() {
    @java.lang.Override
    public RawPacketForwarderCommand parsePartialFrom(
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

  public static com.google.protobuf.Parser<RawPacketForwarderCommand> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<RawPacketForwarderCommand> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public io.chirpstack.api.gw.RawPacketForwarderCommand getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

