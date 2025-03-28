// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: api/device.proto
// Protobuf Java Version: 4.29.2

package io.chirpstack.api;

/**
 * Protobuf type {@code api.GetRandomDevAddrResponse}
 */
public final class GetRandomDevAddrResponse extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:api.GetRandomDevAddrResponse)
    GetRandomDevAddrResponseOrBuilder {
private static final long serialVersionUID = 0L;
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 29,
      /* patch= */ 2,
      /* suffix= */ "",
      GetRandomDevAddrResponse.class.getName());
  }
  // Use GetRandomDevAddrResponse.newBuilder() to construct.
  private GetRandomDevAddrResponse(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private GetRandomDevAddrResponse() {
    devAddr_ = "";
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return io.chirpstack.api.DeviceProto.internal_static_api_GetRandomDevAddrResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return io.chirpstack.api.DeviceProto.internal_static_api_GetRandomDevAddrResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            io.chirpstack.api.GetRandomDevAddrResponse.class, io.chirpstack.api.GetRandomDevAddrResponse.Builder.class);
  }

  public static final int DEV_ADDR_FIELD_NUMBER = 1;
  @SuppressWarnings("serial")
  private volatile java.lang.Object devAddr_ = "";
  /**
   * <pre>
   * DevAddr.
   * </pre>
   *
   * <code>string dev_addr = 1;</code>
   * @return The devAddr.
   */
  @java.lang.Override
  public java.lang.String getDevAddr() {
    java.lang.Object ref = devAddr_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      devAddr_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * DevAddr.
   * </pre>
   *
   * <code>string dev_addr = 1;</code>
   * @return The bytes for devAddr.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getDevAddrBytes() {
    java.lang.Object ref = devAddr_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      devAddr_ = b;
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
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(devAddr_)) {
      com.google.protobuf.GeneratedMessage.writeString(output, 1, devAddr_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(devAddr_)) {
      size += com.google.protobuf.GeneratedMessage.computeStringSize(1, devAddr_);
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
    if (!(obj instanceof io.chirpstack.api.GetRandomDevAddrResponse)) {
      return super.equals(obj);
    }
    io.chirpstack.api.GetRandomDevAddrResponse other = (io.chirpstack.api.GetRandomDevAddrResponse) obj;

    if (!getDevAddr()
        .equals(other.getDevAddr())) return false;
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
    hash = (37 * hash) + DEV_ADDR_FIELD_NUMBER;
    hash = (53 * hash) + getDevAddr().hashCode();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static io.chirpstack.api.GetRandomDevAddrResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.GetRandomDevAddrResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.GetRandomDevAddrResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.GetRandomDevAddrResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.GetRandomDevAddrResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.GetRandomDevAddrResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.GetRandomDevAddrResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static io.chirpstack.api.GetRandomDevAddrResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static io.chirpstack.api.GetRandomDevAddrResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static io.chirpstack.api.GetRandomDevAddrResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.chirpstack.api.GetRandomDevAddrResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static io.chirpstack.api.GetRandomDevAddrResponse parseFrom(
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
  public static Builder newBuilder(io.chirpstack.api.GetRandomDevAddrResponse prototype) {
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
   * Protobuf type {@code api.GetRandomDevAddrResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:api.GetRandomDevAddrResponse)
      io.chirpstack.api.GetRandomDevAddrResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return io.chirpstack.api.DeviceProto.internal_static_api_GetRandomDevAddrResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return io.chirpstack.api.DeviceProto.internal_static_api_GetRandomDevAddrResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              io.chirpstack.api.GetRandomDevAddrResponse.class, io.chirpstack.api.GetRandomDevAddrResponse.Builder.class);
    }

    // Construct using io.chirpstack.api.GetRandomDevAddrResponse.newBuilder()
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
      devAddr_ = "";
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return io.chirpstack.api.DeviceProto.internal_static_api_GetRandomDevAddrResponse_descriptor;
    }

    @java.lang.Override
    public io.chirpstack.api.GetRandomDevAddrResponse getDefaultInstanceForType() {
      return io.chirpstack.api.GetRandomDevAddrResponse.getDefaultInstance();
    }

    @java.lang.Override
    public io.chirpstack.api.GetRandomDevAddrResponse build() {
      io.chirpstack.api.GetRandomDevAddrResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public io.chirpstack.api.GetRandomDevAddrResponse buildPartial() {
      io.chirpstack.api.GetRandomDevAddrResponse result = new io.chirpstack.api.GetRandomDevAddrResponse(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(io.chirpstack.api.GetRandomDevAddrResponse result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.devAddr_ = devAddr_;
      }
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof io.chirpstack.api.GetRandomDevAddrResponse) {
        return mergeFrom((io.chirpstack.api.GetRandomDevAddrResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(io.chirpstack.api.GetRandomDevAddrResponse other) {
      if (other == io.chirpstack.api.GetRandomDevAddrResponse.getDefaultInstance()) return this;
      if (!other.getDevAddr().isEmpty()) {
        devAddr_ = other.devAddr_;
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
              devAddr_ = input.readStringRequireUtf8();
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

    private java.lang.Object devAddr_ = "";
    /**
     * <pre>
     * DevAddr.
     * </pre>
     *
     * <code>string dev_addr = 1;</code>
     * @return The devAddr.
     */
    public java.lang.String getDevAddr() {
      java.lang.Object ref = devAddr_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        devAddr_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     * DevAddr.
     * </pre>
     *
     * <code>string dev_addr = 1;</code>
     * @return The bytes for devAddr.
     */
    public com.google.protobuf.ByteString
        getDevAddrBytes() {
      java.lang.Object ref = devAddr_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        devAddr_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * DevAddr.
     * </pre>
     *
     * <code>string dev_addr = 1;</code>
     * @param value The devAddr to set.
     * @return This builder for chaining.
     */
    public Builder setDevAddr(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      devAddr_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * DevAddr.
     * </pre>
     *
     * <code>string dev_addr = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearDevAddr() {
      devAddr_ = getDefaultInstance().getDevAddr();
      bitField0_ = (bitField0_ & ~0x00000001);
      onChanged();
      return this;
    }
    /**
     * <pre>
     * DevAddr.
     * </pre>
     *
     * <code>string dev_addr = 1;</code>
     * @param value The bytes for devAddr to set.
     * @return This builder for chaining.
     */
    public Builder setDevAddrBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      devAddr_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:api.GetRandomDevAddrResponse)
  }

  // @@protoc_insertion_point(class_scope:api.GetRandomDevAddrResponse)
  private static final io.chirpstack.api.GetRandomDevAddrResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new io.chirpstack.api.GetRandomDevAddrResponse();
  }

  public static io.chirpstack.api.GetRandomDevAddrResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<GetRandomDevAddrResponse>
      PARSER = new com.google.protobuf.AbstractParser<GetRandomDevAddrResponse>() {
    @java.lang.Override
    public GetRandomDevAddrResponse parsePartialFrom(
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

  public static com.google.protobuf.Parser<GetRandomDevAddrResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<GetRandomDevAddrResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public io.chirpstack.api.GetRandomDevAddrResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

