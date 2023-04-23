// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: service/iot_config.proto

package com.helium.grpc;

/**
 * <pre>
 * Device address range, ex: 16#00000001 to 16#0000000A
 * </pre>
 *
 * Protobuf type {@code helium.iot_config.devaddr_range_v1}
 */
public final class devaddr_range_v1 extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:helium.iot_config.devaddr_range_v1)
    devaddr_range_v1OrBuilder {
private static final long serialVersionUID = 0L;
  // Use devaddr_range_v1.newBuilder() to construct.
  private devaddr_range_v1(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private devaddr_range_v1() {
    routeId_ = "";
  }

  @Override
  @SuppressWarnings({"unused"})
  protected Object newInstance(
      UnusedPrivateParameter unused) {
    return new devaddr_range_v1();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return IotConfig.internal_static_helium_iot_config_devaddr_range_v1_descriptor;
  }

  @Override
  protected FieldAccessorTable
      internalGetFieldAccessorTable() {
    return IotConfig.internal_static_helium_iot_config_devaddr_range_v1_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            devaddr_range_v1.class, Builder.class);
  }

  public static final int ROUTE_ID_FIELD_NUMBER = 1;
  @SuppressWarnings("serial")
  private volatile Object routeId_ = "";
  /**
   * <code>string route_id = 1;</code>
   * @return The routeId.
   */
  @Override
  public String getRouteId() {
    Object ref = routeId_;
    if (ref instanceof String) {
      return (String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      String s = bs.toStringUtf8();
      routeId_ = s;
      return s;
    }
  }
  /**
   * <code>string route_id = 1;</code>
   * @return The bytes for routeId.
   */
  @Override
  public com.google.protobuf.ByteString
      getRouteIdBytes() {
    Object ref = routeId_;
    if (ref instanceof String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (String) ref);
      routeId_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int START_ADDR_FIELD_NUMBER = 2;
  private int startAddr_ = 0;
  /**
   * <code>uint32 start_addr = 2;</code>
   * @return The startAddr.
   */
  @Override
  public int getStartAddr() {
    return startAddr_;
  }

  public static final int END_ADDR_FIELD_NUMBER = 3;
  private int endAddr_ = 0;
  /**
   * <code>uint32 end_addr = 3;</code>
   * @return The endAddr.
   */
  @Override
  public int getEndAddr() {
    return endAddr_;
  }

  private byte memoizedIsInitialized = -1;
  @Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(routeId_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, routeId_);
    }
    if (startAddr_ != 0) {
      output.writeUInt32(2, startAddr_);
    }
    if (endAddr_ != 0) {
      output.writeUInt32(3, endAddr_);
    }
    getUnknownFields().writeTo(output);
  }

  @Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(routeId_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, routeId_);
    }
    if (startAddr_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt32Size(2, startAddr_);
    }
    if (endAddr_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt32Size(3, endAddr_);
    }
    size += getUnknownFields().getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof devaddr_range_v1)) {
      return super.equals(obj);
    }
    devaddr_range_v1 other = (devaddr_range_v1) obj;

    if (!getRouteId()
        .equals(other.getRouteId())) return false;
    if (getStartAddr()
        != other.getStartAddr()) return false;
    if (getEndAddr()
        != other.getEndAddr()) return false;
    if (!getUnknownFields().equals(other.getUnknownFields())) return false;
    return true;
  }

  @Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + ROUTE_ID_FIELD_NUMBER;
    hash = (53 * hash) + getRouteId().hashCode();
    hash = (37 * hash) + START_ADDR_FIELD_NUMBER;
    hash = (53 * hash) + getStartAddr();
    hash = (37 * hash) + END_ADDR_FIELD_NUMBER;
    hash = (53 * hash) + getEndAddr();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static devaddr_range_v1 parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static devaddr_range_v1 parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static devaddr_range_v1 parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static devaddr_range_v1 parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static devaddr_range_v1 parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static devaddr_range_v1 parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static devaddr_range_v1 parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static devaddr_range_v1 parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static devaddr_range_v1 parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static devaddr_range_v1 parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static devaddr_range_v1 parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static devaddr_range_v1 parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(devaddr_range_v1 prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @Override
  protected Builder newBuilderForType(
      BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * <pre>
   * Device address range, ex: 16#00000001 to 16#0000000A
   * </pre>
   *
   * Protobuf type {@code helium.iot_config.devaddr_range_v1}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:helium.iot_config.devaddr_range_v1)
      devaddr_range_v1OrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return IotConfig.internal_static_helium_iot_config_devaddr_range_v1_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return IotConfig.internal_static_helium_iot_config_devaddr_range_v1_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              devaddr_range_v1.class, Builder.class);
    }

    // Construct using com.helium.grpc.devaddr_range_v1.newBuilder()
    private Builder() {

    }

    private Builder(
        BuilderParent parent) {
      super(parent);

    }
    @Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      routeId_ = "";
      startAddr_ = 0;
      endAddr_ = 0;
      return this;
    }

    @Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return IotConfig.internal_static_helium_iot_config_devaddr_range_v1_descriptor;
    }

    @Override
    public devaddr_range_v1 getDefaultInstanceForType() {
      return devaddr_range_v1.getDefaultInstance();
    }

    @Override
    public devaddr_range_v1 build() {
      devaddr_range_v1 result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @Override
    public devaddr_range_v1 buildPartial() {
      devaddr_range_v1 result = new devaddr_range_v1(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(devaddr_range_v1 result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.routeId_ = routeId_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.startAddr_ = startAddr_;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.endAddr_ = endAddr_;
      }
    }

    @Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof devaddr_range_v1) {
        return mergeFrom((devaddr_range_v1)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(devaddr_range_v1 other) {
      if (other == devaddr_range_v1.getDefaultInstance()) return this;
      if (!other.getRouteId().isEmpty()) {
        routeId_ = other.routeId_;
        bitField0_ |= 0x00000001;
        onChanged();
      }
      if (other.getStartAddr() != 0) {
        setStartAddr(other.getStartAddr());
      }
      if (other.getEndAddr() != 0) {
        setEndAddr(other.getEndAddr());
      }
      this.mergeUnknownFields(other.getUnknownFields());
      onChanged();
      return this;
    }

    @Override
    public final boolean isInitialized() {
      return true;
    }

    @Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      if (extensionRegistry == null) {
        throw new NullPointerException();
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
              startAddr_ = input.readUInt32();
              bitField0_ |= 0x00000002;
              break;
            } // case 16
            case 24: {
              endAddr_ = input.readUInt32();
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

    private Object routeId_ = "";
    /**
     * <code>string route_id = 1;</code>
     * @return The routeId.
     */
    public String getRouteId() {
      Object ref = routeId_;
      if (!(ref instanceof String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        routeId_ = s;
        return s;
      } else {
        return (String) ref;
      }
    }
    /**
     * <code>string route_id = 1;</code>
     * @return The bytes for routeId.
     */
    public com.google.protobuf.ByteString
        getRouteIdBytes() {
      Object ref = routeId_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
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
        String value) {
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

    private int startAddr_ ;
    /**
     * <code>uint32 start_addr = 2;</code>
     * @return The startAddr.
     */
    @Override
    public int getStartAddr() {
      return startAddr_;
    }
    /**
     * <code>uint32 start_addr = 2;</code>
     * @param value The startAddr to set.
     * @return This builder for chaining.
     */
    public Builder setStartAddr(int value) {

      startAddr_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <code>uint32 start_addr = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearStartAddr() {
      bitField0_ = (bitField0_ & ~0x00000002);
      startAddr_ = 0;
      onChanged();
      return this;
    }

    private int endAddr_ ;
    /**
     * <code>uint32 end_addr = 3;</code>
     * @return The endAddr.
     */
    @Override
    public int getEndAddr() {
      return endAddr_;
    }
    /**
     * <code>uint32 end_addr = 3;</code>
     * @param value The endAddr to set.
     * @return This builder for chaining.
     */
    public Builder setEndAddr(int value) {

      endAddr_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <code>uint32 end_addr = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearEndAddr() {
      bitField0_ = (bitField0_ & ~0x00000004);
      endAddr_ = 0;
      onChanged();
      return this;
    }
    @Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:helium.iot_config.devaddr_range_v1)
  }

  // @@protoc_insertion_point(class_scope:helium.iot_config.devaddr_range_v1)
  private static final devaddr_range_v1 DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new devaddr_range_v1();
  }

  public static devaddr_range_v1 getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<devaddr_range_v1>
      PARSER = new com.google.protobuf.AbstractParser<devaddr_range_v1>() {
    @Override
    public devaddr_range_v1 parsePartialFrom(
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

  public static com.google.protobuf.Parser<devaddr_range_v1> parser() {
    return PARSER;
  }

  @Override
  public com.google.protobuf.Parser<devaddr_range_v1> getParserForType() {
    return PARSER;
  }

  @Override
  public devaddr_range_v1 getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
