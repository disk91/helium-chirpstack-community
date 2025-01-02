// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: service/iot_config.proto
// Protobuf Java Version: 4.29.2

package com.helium.grpc;

/**
 * Protobuf type {@code helium.iot_config.route_devaddr_ranges_res_v1}
 */
public final class route_devaddr_ranges_res_v1 extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:helium.iot_config.route_devaddr_ranges_res_v1)
    route_devaddr_ranges_res_v1OrBuilder {
private static final long serialVersionUID = 0L;
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 29,
      /* patch= */ 2,
      /* suffix= */ "",
      route_devaddr_ranges_res_v1.class.getName());
  }
  // Use route_devaddr_ranges_res_v1.newBuilder() to construct.
  private route_devaddr_ranges_res_v1(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private route_devaddr_ranges_res_v1() {
    signer_ = com.google.protobuf.ByteString.EMPTY;
    signature_ = com.google.protobuf.ByteString.EMPTY;
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.helium.grpc.IotConfig.internal_static_helium_iot_config_route_devaddr_ranges_res_v1_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.helium.grpc.IotConfig.internal_static_helium_iot_config_route_devaddr_ranges_res_v1_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.helium.grpc.route_devaddr_ranges_res_v1.class, com.helium.grpc.route_devaddr_ranges_res_v1.Builder.class);
  }

  public static final int TIMESTAMP_FIELD_NUMBER = 1;
  private long timestamp_ = 0L;
  /**
   * <pre>
   * in seconds since unix epoch
   * </pre>
   *
   * <code>uint64 timestamp = 1;</code>
   * @return The timestamp.
   */
  @java.lang.Override
  public long getTimestamp() {
    return timestamp_;
  }

  public static final int SIGNER_FIELD_NUMBER = 2;
  private com.google.protobuf.ByteString signer_ = com.google.protobuf.ByteString.EMPTY;
  /**
   * <pre>
   * pubkey binary of the signing keypair
   * </pre>
   *
   * <code>bytes signer = 2;</code>
   * @return The signer.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getSigner() {
    return signer_;
  }

  public static final int SIGNATURE_FIELD_NUMBER = 3;
  private com.google.protobuf.ByteString signature_ = com.google.protobuf.ByteString.EMPTY;
  /**
   * <pre>
   * Signature over the response by the config service
   * </pre>
   *
   * <code>bytes signature = 3;</code>
   * @return The signature.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getSignature() {
    return signature_;
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
    if (timestamp_ != 0L) {
      output.writeUInt64(1, timestamp_);
    }
    if (!signer_.isEmpty()) {
      output.writeBytes(2, signer_);
    }
    if (!signature_.isEmpty()) {
      output.writeBytes(3, signature_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (timestamp_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt64Size(1, timestamp_);
    }
    if (!signer_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(2, signer_);
    }
    if (!signature_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(3, signature_);
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
    if (!(obj instanceof com.helium.grpc.route_devaddr_ranges_res_v1)) {
      return super.equals(obj);
    }
    com.helium.grpc.route_devaddr_ranges_res_v1 other = (com.helium.grpc.route_devaddr_ranges_res_v1) obj;

    if (getTimestamp()
        != other.getTimestamp()) return false;
    if (!getSigner()
        .equals(other.getSigner())) return false;
    if (!getSignature()
        .equals(other.getSignature())) return false;
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
    hash = (37 * hash) + TIMESTAMP_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getTimestamp());
    hash = (37 * hash) + SIGNER_FIELD_NUMBER;
    hash = (53 * hash) + getSigner().hashCode();
    hash = (37 * hash) + SIGNATURE_FIELD_NUMBER;
    hash = (53 * hash) + getSignature().hashCode();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.helium.grpc.route_devaddr_ranges_res_v1 parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.helium.grpc.route_devaddr_ranges_res_v1 parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.helium.grpc.route_devaddr_ranges_res_v1 parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.helium.grpc.route_devaddr_ranges_res_v1 parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.helium.grpc.route_devaddr_ranges_res_v1 parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.helium.grpc.route_devaddr_ranges_res_v1 parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.helium.grpc.route_devaddr_ranges_res_v1 parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static com.helium.grpc.route_devaddr_ranges_res_v1 parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static com.helium.grpc.route_devaddr_ranges_res_v1 parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static com.helium.grpc.route_devaddr_ranges_res_v1 parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.helium.grpc.route_devaddr_ranges_res_v1 parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static com.helium.grpc.route_devaddr_ranges_res_v1 parseFrom(
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
  public static Builder newBuilder(com.helium.grpc.route_devaddr_ranges_res_v1 prototype) {
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
   * Protobuf type {@code helium.iot_config.route_devaddr_ranges_res_v1}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:helium.iot_config.route_devaddr_ranges_res_v1)
      com.helium.grpc.route_devaddr_ranges_res_v1OrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.helium.grpc.IotConfig.internal_static_helium_iot_config_route_devaddr_ranges_res_v1_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.helium.grpc.IotConfig.internal_static_helium_iot_config_route_devaddr_ranges_res_v1_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.helium.grpc.route_devaddr_ranges_res_v1.class, com.helium.grpc.route_devaddr_ranges_res_v1.Builder.class);
    }

    // Construct using com.helium.grpc.route_devaddr_ranges_res_v1.newBuilder()
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
      timestamp_ = 0L;
      signer_ = com.google.protobuf.ByteString.EMPTY;
      signature_ = com.google.protobuf.ByteString.EMPTY;
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.helium.grpc.IotConfig.internal_static_helium_iot_config_route_devaddr_ranges_res_v1_descriptor;
    }

    @java.lang.Override
    public com.helium.grpc.route_devaddr_ranges_res_v1 getDefaultInstanceForType() {
      return com.helium.grpc.route_devaddr_ranges_res_v1.getDefaultInstance();
    }

    @java.lang.Override
    public com.helium.grpc.route_devaddr_ranges_res_v1 build() {
      com.helium.grpc.route_devaddr_ranges_res_v1 result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.helium.grpc.route_devaddr_ranges_res_v1 buildPartial() {
      com.helium.grpc.route_devaddr_ranges_res_v1 result = new com.helium.grpc.route_devaddr_ranges_res_v1(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(com.helium.grpc.route_devaddr_ranges_res_v1 result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.timestamp_ = timestamp_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.signer_ = signer_;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.signature_ = signature_;
      }
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.helium.grpc.route_devaddr_ranges_res_v1) {
        return mergeFrom((com.helium.grpc.route_devaddr_ranges_res_v1)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.helium.grpc.route_devaddr_ranges_res_v1 other) {
      if (other == com.helium.grpc.route_devaddr_ranges_res_v1.getDefaultInstance()) return this;
      if (other.getTimestamp() != 0L) {
        setTimestamp(other.getTimestamp());
      }
      if (other.getSigner() != com.google.protobuf.ByteString.EMPTY) {
        setSigner(other.getSigner());
      }
      if (other.getSignature() != com.google.protobuf.ByteString.EMPTY) {
        setSignature(other.getSignature());
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
              timestamp_ = input.readUInt64();
              bitField0_ |= 0x00000001;
              break;
            } // case 8
            case 18: {
              signer_ = input.readBytes();
              bitField0_ |= 0x00000002;
              break;
            } // case 18
            case 26: {
              signature_ = input.readBytes();
              bitField0_ |= 0x00000004;
              break;
            } // case 26
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

    private long timestamp_ ;
    /**
     * <pre>
     * in seconds since unix epoch
     * </pre>
     *
     * <code>uint64 timestamp = 1;</code>
     * @return The timestamp.
     */
    @java.lang.Override
    public long getTimestamp() {
      return timestamp_;
    }
    /**
     * <pre>
     * in seconds since unix epoch
     * </pre>
     *
     * <code>uint64 timestamp = 1;</code>
     * @param value The timestamp to set.
     * @return This builder for chaining.
     */
    public Builder setTimestamp(long value) {

      timestamp_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * in seconds since unix epoch
     * </pre>
     *
     * <code>uint64 timestamp = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearTimestamp() {
      bitField0_ = (bitField0_ & ~0x00000001);
      timestamp_ = 0L;
      onChanged();
      return this;
    }

    private com.google.protobuf.ByteString signer_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <pre>
     * pubkey binary of the signing keypair
     * </pre>
     *
     * <code>bytes signer = 2;</code>
     * @return The signer.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getSigner() {
      return signer_;
    }
    /**
     * <pre>
     * pubkey binary of the signing keypair
     * </pre>
     *
     * <code>bytes signer = 2;</code>
     * @param value The signer to set.
     * @return This builder for chaining.
     */
    public Builder setSigner(com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      signer_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * pubkey binary of the signing keypair
     * </pre>
     *
     * <code>bytes signer = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearSigner() {
      bitField0_ = (bitField0_ & ~0x00000002);
      signer_ = getDefaultInstance().getSigner();
      onChanged();
      return this;
    }

    private com.google.protobuf.ByteString signature_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <pre>
     * Signature over the response by the config service
     * </pre>
     *
     * <code>bytes signature = 3;</code>
     * @return The signature.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getSignature() {
      return signature_;
    }
    /**
     * <pre>
     * Signature over the response by the config service
     * </pre>
     *
     * <code>bytes signature = 3;</code>
     * @param value The signature to set.
     * @return This builder for chaining.
     */
    public Builder setSignature(com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      signature_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Signature over the response by the config service
     * </pre>
     *
     * <code>bytes signature = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearSignature() {
      bitField0_ = (bitField0_ & ~0x00000004);
      signature_ = getDefaultInstance().getSignature();
      onChanged();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:helium.iot_config.route_devaddr_ranges_res_v1)
  }

  // @@protoc_insertion_point(class_scope:helium.iot_config.route_devaddr_ranges_res_v1)
  private static final com.helium.grpc.route_devaddr_ranges_res_v1 DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.helium.grpc.route_devaddr_ranges_res_v1();
  }

  public static com.helium.grpc.route_devaddr_ranges_res_v1 getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<route_devaddr_ranges_res_v1>
      PARSER = new com.google.protobuf.AbstractParser<route_devaddr_ranges_res_v1>() {
    @java.lang.Override
    public route_devaddr_ranges_res_v1 parsePartialFrom(
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

  public static com.google.protobuf.Parser<route_devaddr_ranges_res_v1> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<route_devaddr_ranges_res_v1> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.helium.grpc.route_devaddr_ranges_res_v1 getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

