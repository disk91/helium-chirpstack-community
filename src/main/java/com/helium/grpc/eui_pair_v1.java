// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: service/iot_config.proto
// Protobuf Java Version: 4.29.2

package com.helium.grpc;

/**
 * <pre>
 * Device App EUI and Dev EUI
 * </pre>
 *
 * Protobuf type {@code helium.iot_config.eui_pair_v1}
 */
public final class eui_pair_v1 extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:helium.iot_config.eui_pair_v1)
    eui_pair_v1OrBuilder {
private static final long serialVersionUID = 0L;
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 29,
      /* patch= */ 2,
      /* suffix= */ "",
      eui_pair_v1.class.getName());
  }
  // Use eui_pair_v1.newBuilder() to construct.
  private eui_pair_v1(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private eui_pair_v1() {
    routeId_ = "";
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.helium.grpc.IotConfig.internal_static_helium_iot_config_eui_pair_v1_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.helium.grpc.IotConfig.internal_static_helium_iot_config_eui_pair_v1_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.helium.grpc.eui_pair_v1.class, com.helium.grpc.eui_pair_v1.Builder.class);
  }

  public static final int ROUTE_ID_FIELD_NUMBER = 1;
  @SuppressWarnings("serial")
  private volatile java.lang.Object routeId_ = "";
  /**
   * <code>string route_id = 1;</code>
   * @return The routeId.
   */
  @java.lang.Override
  public java.lang.String getRouteId() {
    java.lang.Object ref = routeId_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      routeId_ = s;
      return s;
    }
  }
  /**
   * <code>string route_id = 1;</code>
   * @return The bytes for routeId.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getRouteIdBytes() {
    java.lang.Object ref = routeId_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      routeId_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int APP_EUI_FIELD_NUMBER = 2;
  private long appEui_ = 0L;
  /**
   * <code>uint64 app_eui = 2;</code>
   * @return The appEui.
   */
  @java.lang.Override
  public long getAppEui() {
    return appEui_;
  }

  public static final int DEV_EUI_FIELD_NUMBER = 3;
  private long devEui_ = 0L;
  /**
   * <code>uint64 dev_eui = 3;</code>
   * @return The devEui.
   */
  @java.lang.Override
  public long getDevEui() {
    return devEui_;
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
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(routeId_)) {
      com.google.protobuf.GeneratedMessage.writeString(output, 1, routeId_);
    }
    if (appEui_ != 0L) {
      output.writeUInt64(2, appEui_);
    }
    if (devEui_ != 0L) {
      output.writeUInt64(3, devEui_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(routeId_)) {
      size += com.google.protobuf.GeneratedMessage.computeStringSize(1, routeId_);
    }
    if (appEui_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt64Size(2, appEui_);
    }
    if (devEui_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt64Size(3, devEui_);
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
    if (!(obj instanceof com.helium.grpc.eui_pair_v1)) {
      return super.equals(obj);
    }
    com.helium.grpc.eui_pair_v1 other = (com.helium.grpc.eui_pair_v1) obj;

    if (!getRouteId()
        .equals(other.getRouteId())) return false;
    if (getAppEui()
        != other.getAppEui()) return false;
    if (getDevEui()
        != other.getDevEui()) return false;
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
    hash = (37 * hash) + ROUTE_ID_FIELD_NUMBER;
    hash = (53 * hash) + getRouteId().hashCode();
    hash = (37 * hash) + APP_EUI_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getAppEui());
    hash = (37 * hash) + DEV_EUI_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getDevEui());
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.helium.grpc.eui_pair_v1 parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.helium.grpc.eui_pair_v1 parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.helium.grpc.eui_pair_v1 parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.helium.grpc.eui_pair_v1 parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.helium.grpc.eui_pair_v1 parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.helium.grpc.eui_pair_v1 parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.helium.grpc.eui_pair_v1 parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static com.helium.grpc.eui_pair_v1 parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static com.helium.grpc.eui_pair_v1 parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static com.helium.grpc.eui_pair_v1 parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.helium.grpc.eui_pair_v1 parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static com.helium.grpc.eui_pair_v1 parseFrom(
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
  public static Builder newBuilder(com.helium.grpc.eui_pair_v1 prototype) {
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
   * <pre>
   * Device App EUI and Dev EUI
   * </pre>
   *
   * Protobuf type {@code helium.iot_config.eui_pair_v1}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:helium.iot_config.eui_pair_v1)
      com.helium.grpc.eui_pair_v1OrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.helium.grpc.IotConfig.internal_static_helium_iot_config_eui_pair_v1_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.helium.grpc.IotConfig.internal_static_helium_iot_config_eui_pair_v1_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.helium.grpc.eui_pair_v1.class, com.helium.grpc.eui_pair_v1.Builder.class);
    }

    // Construct using com.helium.grpc.eui_pair_v1.newBuilder()
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
      routeId_ = "";
      appEui_ = 0L;
      devEui_ = 0L;
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.helium.grpc.IotConfig.internal_static_helium_iot_config_eui_pair_v1_descriptor;
    }

    @java.lang.Override
    public com.helium.grpc.eui_pair_v1 getDefaultInstanceForType() {
      return com.helium.grpc.eui_pair_v1.getDefaultInstance();
    }

    @java.lang.Override
    public com.helium.grpc.eui_pair_v1 build() {
      com.helium.grpc.eui_pair_v1 result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.helium.grpc.eui_pair_v1 buildPartial() {
      com.helium.grpc.eui_pair_v1 result = new com.helium.grpc.eui_pair_v1(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(com.helium.grpc.eui_pair_v1 result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.routeId_ = routeId_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.appEui_ = appEui_;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.devEui_ = devEui_;
      }
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.helium.grpc.eui_pair_v1) {
        return mergeFrom((com.helium.grpc.eui_pair_v1)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.helium.grpc.eui_pair_v1 other) {
      if (other == com.helium.grpc.eui_pair_v1.getDefaultInstance()) return this;
      if (!other.getRouteId().isEmpty()) {
        routeId_ = other.routeId_;
        bitField0_ |= 0x00000001;
        onChanged();
      }
      if (other.getAppEui() != 0L) {
        setAppEui(other.getAppEui());
      }
      if (other.getDevEui() != 0L) {
        setDevEui(other.getDevEui());
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
              routeId_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000001;
              break;
            } // case 10
            case 16: {
              appEui_ = input.readUInt64();
              bitField0_ |= 0x00000002;
              break;
            } // case 16
            case 24: {
              devEui_ = input.readUInt64();
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

    private java.lang.Object routeId_ = "";
    /**
     * <code>string route_id = 1;</code>
     * @return The routeId.
     */
    public java.lang.String getRouteId() {
      java.lang.Object ref = routeId_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        routeId_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string route_id = 1;</code>
     * @return The bytes for routeId.
     */
    public com.google.protobuf.ByteString
        getRouteIdBytes() {
      java.lang.Object ref = routeId_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        routeId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string route_id = 1;</code>
     * @param value The routeId to set.
     * @return This builder for chaining.
     */
    public Builder setRouteId(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      routeId_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>string route_id = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearRouteId() {
      routeId_ = getDefaultInstance().getRouteId();
      bitField0_ = (bitField0_ & ~0x00000001);
      onChanged();
      return this;
    }
    /**
     * <code>string route_id = 1;</code>
     * @param value The bytes for routeId to set.
     * @return This builder for chaining.
     */
    public Builder setRouteIdBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      routeId_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }

    private long appEui_ ;
    /**
     * <code>uint64 app_eui = 2;</code>
     * @return The appEui.
     */
    @java.lang.Override
    public long getAppEui() {
      return appEui_;
    }
    /**
     * <code>uint64 app_eui = 2;</code>
     * @param value The appEui to set.
     * @return This builder for chaining.
     */
    public Builder setAppEui(long value) {

      appEui_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <code>uint64 app_eui = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearAppEui() {
      bitField0_ = (bitField0_ & ~0x00000002);
      appEui_ = 0L;
      onChanged();
      return this;
    }

    private long devEui_ ;
    /**
     * <code>uint64 dev_eui = 3;</code>
     * @return The devEui.
     */
    @java.lang.Override
    public long getDevEui() {
      return devEui_;
    }
    /**
     * <code>uint64 dev_eui = 3;</code>
     * @param value The devEui to set.
     * @return This builder for chaining.
     */
    public Builder setDevEui(long value) {

      devEui_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <code>uint64 dev_eui = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearDevEui() {
      bitField0_ = (bitField0_ & ~0x00000004);
      devEui_ = 0L;
      onChanged();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:helium.iot_config.eui_pair_v1)
  }

  // @@protoc_insertion_point(class_scope:helium.iot_config.eui_pair_v1)
  private static final com.helium.grpc.eui_pair_v1 DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.helium.grpc.eui_pair_v1();
  }

  public static com.helium.grpc.eui_pair_v1 getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<eui_pair_v1>
      PARSER = new com.google.protobuf.AbstractParser<eui_pair_v1>() {
    @java.lang.Override
    public eui_pair_v1 parsePartialFrom(
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

  public static com.google.protobuf.Parser<eui_pair_v1> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<eui_pair_v1> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.helium.grpc.eui_pair_v1 getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

