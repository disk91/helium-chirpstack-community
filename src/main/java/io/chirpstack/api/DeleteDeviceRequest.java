// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: api/device.proto
// Protobuf Java Version: 4.29.2

package io.chirpstack.api;

/**
 * Protobuf type {@code api.DeleteDeviceRequest}
 */
public final class DeleteDeviceRequest extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:api.DeleteDeviceRequest)
    DeleteDeviceRequestOrBuilder {
private static final long serialVersionUID = 0L;
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 29,
      /* patch= */ 2,
      /* suffix= */ "",
      DeleteDeviceRequest.class.getName());
  }
  // Use DeleteDeviceRequest.newBuilder() to construct.
  private DeleteDeviceRequest(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private DeleteDeviceRequest() {
    devEui_ = "";
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return io.chirpstack.api.DeviceProto.internal_static_api_DeleteDeviceRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return io.chirpstack.api.DeviceProto.internal_static_api_DeleteDeviceRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            io.chirpstack.api.DeleteDeviceRequest.class, io.chirpstack.api.DeleteDeviceRequest.Builder.class);
  }

  public static final int DEV_EUI_FIELD_NUMBER = 1;
  @SuppressWarnings("serial")
  private volatile java.lang.Object devEui_ = "";
  /**
   * <pre>
   * DevEUI (EUI64).
   * </pre>
   *
   * <code>string dev_eui = 1;</code>
   * @return The devEui.
   */
  @java.lang.Override
  public java.lang.String getDevEui() {
    java.lang.Object ref = devEui_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      devEui_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * DevEUI (EUI64).
   * </pre>
   *
   * <code>string dev_eui = 1;</code>
   * @return The bytes for devEui.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getDevEuiBytes() {
    java.lang.Object ref = devEui_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      devEui_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
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
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(devEui_)) {
      com.google.protobuf.GeneratedMessage.writeString(output, 1, devEui_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(devEui_)) {
      size += com.google.protobuf.GeneratedMessage.computeStringSize(1, devEui_);
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
    if (!(obj instanceof io.chirpstack.api.DeleteDeviceRequest)) {
      return super.equals(obj);
    }
    io.chirpstack.api.DeleteDeviceRequest other = (io.chirpstack.api.DeleteDeviceRequest) obj;

    if (!getDevEui()
        .equals(other.getDevEui())) return false;
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
    hash = (37 * hash) + DEV_EUI_FIELD_NUMBER;
    hash = (53 * hash) + getDevEui().hashCode();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static io.chirpstack.api.DeleteDeviceRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.DeleteDeviceRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.DeleteDeviceRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.DeleteDeviceRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.DeleteDeviceRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.DeleteDeviceRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.DeleteDeviceRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static io.chirpstack.api.DeleteDeviceRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static io.chirpstack.api.DeleteDeviceRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static io.chirpstack.api.DeleteDeviceRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.chirpstack.api.DeleteDeviceRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static io.chirpstack.api.DeleteDeviceRequest parseFrom(
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
  public static Builder newBuilder(io.chirpstack.api.DeleteDeviceRequest prototype) {
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
   * Protobuf type {@code api.DeleteDeviceRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:api.DeleteDeviceRequest)
      io.chirpstack.api.DeleteDeviceRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return io.chirpstack.api.DeviceProto.internal_static_api_DeleteDeviceRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return io.chirpstack.api.DeviceProto.internal_static_api_DeleteDeviceRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              io.chirpstack.api.DeleteDeviceRequest.class, io.chirpstack.api.DeleteDeviceRequest.Builder.class);
    }

    // Construct using io.chirpstack.api.DeleteDeviceRequest.newBuilder()
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
      devEui_ = "";
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return io.chirpstack.api.DeviceProto.internal_static_api_DeleteDeviceRequest_descriptor;
    }

    @java.lang.Override
    public io.chirpstack.api.DeleteDeviceRequest getDefaultInstanceForType() {
      return io.chirpstack.api.DeleteDeviceRequest.getDefaultInstance();
    }

    @java.lang.Override
    public io.chirpstack.api.DeleteDeviceRequest build() {
      io.chirpstack.api.DeleteDeviceRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public io.chirpstack.api.DeleteDeviceRequest buildPartial() {
      io.chirpstack.api.DeleteDeviceRequest result = new io.chirpstack.api.DeleteDeviceRequest(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(io.chirpstack.api.DeleteDeviceRequest result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.devEui_ = devEui_;
      }
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof io.chirpstack.api.DeleteDeviceRequest) {
        return mergeFrom((io.chirpstack.api.DeleteDeviceRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(io.chirpstack.api.DeleteDeviceRequest other) {
      if (other == io.chirpstack.api.DeleteDeviceRequest.getDefaultInstance()) return this;
      if (!other.getDevEui().isEmpty()) {
        devEui_ = other.devEui_;
        bitField0_ |= 0x00000001;
        onChanged();
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
              devEui_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000001;
              break;
            } // case 10
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

    private java.lang.Object devEui_ = "";
    /**
     * <pre>
     * DevEUI (EUI64).
     * </pre>
     *
     * <code>string dev_eui = 1;</code>
     * @return The devEui.
     */
    public java.lang.String getDevEui() {
      java.lang.Object ref = devEui_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        devEui_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     * DevEUI (EUI64).
     * </pre>
     *
     * <code>string dev_eui = 1;</code>
     * @return The bytes for devEui.
     */
    public com.google.protobuf.ByteString
        getDevEuiBytes() {
      java.lang.Object ref = devEui_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        devEui_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * DevEUI (EUI64).
     * </pre>
     *
     * <code>string dev_eui = 1;</code>
     * @param value The devEui to set.
     * @return This builder for chaining.
     */
    public Builder setDevEui(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      devEui_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * DevEUI (EUI64).
     * </pre>
     *
     * <code>string dev_eui = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearDevEui() {
      devEui_ = getDefaultInstance().getDevEui();
      bitField0_ = (bitField0_ & ~0x00000001);
      onChanged();
      return this;
    }
    /**
     * <pre>
     * DevEUI (EUI64).
     * </pre>
     *
     * <code>string dev_eui = 1;</code>
     * @param value The bytes for devEui to set.
     * @return This builder for chaining.
     */
    public Builder setDevEuiBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      devEui_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:api.DeleteDeviceRequest)
  }

  // @@protoc_insertion_point(class_scope:api.DeleteDeviceRequest)
  private static final io.chirpstack.api.DeleteDeviceRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new io.chirpstack.api.DeleteDeviceRequest();
  }

  public static io.chirpstack.api.DeleteDeviceRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<DeleteDeviceRequest>
      PARSER = new com.google.protobuf.AbstractParser<DeleteDeviceRequest>() {
    @java.lang.Override
    public DeleteDeviceRequest parsePartialFrom(
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

  public static com.google.protobuf.Parser<DeleteDeviceRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<DeleteDeviceRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public io.chirpstack.api.DeleteDeviceRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

