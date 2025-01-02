// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: service/iot_config.proto
// Protobuf Java Version: 4.29.2

package com.helium.grpc;

/**
 * <pre>
 * HTTP Roaming protocol options
 * </pre>
 *
 * Protobuf type {@code helium.iot_config.protocol_http_roaming_v1}
 */
public final class protocol_http_roaming_v1 extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:helium.iot_config.protocol_http_roaming_v1)
    protocol_http_roaming_v1OrBuilder {
private static final long serialVersionUID = 0L;
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 29,
      /* patch= */ 2,
      /* suffix= */ "",
      protocol_http_roaming_v1.class.getName());
  }
  // Use protocol_http_roaming_v1.newBuilder() to construct.
  private protocol_http_roaming_v1(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private protocol_http_roaming_v1() {
    flowType_ = 0;
    path_ = "";
    authHeader_ = "";
    receiverNsid_ = "";
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.helium.grpc.IotConfig.internal_static_helium_iot_config_protocol_http_roaming_v1_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.helium.grpc.IotConfig.internal_static_helium_iot_config_protocol_http_roaming_v1_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.helium.grpc.protocol_http_roaming_v1.class, com.helium.grpc.protocol_http_roaming_v1.Builder.class);
  }

  /**
   * Protobuf enum {@code helium.iot_config.protocol_http_roaming_v1.flow_type_v1}
   */
  public enum flow_type_v1
      implements com.google.protobuf.ProtocolMessageEnum {
    /**
     * <code>sync = 0;</code>
     */
    sync(0),
    /**
     * <code>async = 1;</code>
     */
    async(1),
    UNRECOGNIZED(-1),
    ;

    static {
      com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
        com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
        /* major= */ 4,
        /* minor= */ 29,
        /* patch= */ 2,
        /* suffix= */ "",
        flow_type_v1.class.getName());
    }
    /**
     * <code>sync = 0;</code>
     */
    public static final int sync_VALUE = 0;
    /**
     * <code>async = 1;</code>
     */
    public static final int async_VALUE = 1;


    public final int getNumber() {
      if (this == UNRECOGNIZED) {
        throw new java.lang.IllegalArgumentException(
            "Can't get the number of an unknown enum value.");
      }
      return value;
    }

    /**
     * @param value The numeric wire value of the corresponding enum entry.
     * @return The enum associated with the given numeric wire value.
     * @deprecated Use {@link #forNumber(int)} instead.
     */
    @java.lang.Deprecated
    public static flow_type_v1 valueOf(int value) {
      return forNumber(value);
    }

    /**
     * @param value The numeric wire value of the corresponding enum entry.
     * @return The enum associated with the given numeric wire value.
     */
    public static flow_type_v1 forNumber(int value) {
      switch (value) {
        case 0: return sync;
        case 1: return async;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<flow_type_v1>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static final com.google.protobuf.Internal.EnumLiteMap<
        flow_type_v1> internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<flow_type_v1>() {
            public flow_type_v1 findValueByNumber(int number) {
              return flow_type_v1.forNumber(number);
            }
          };

    public final com.google.protobuf.Descriptors.EnumValueDescriptor
        getValueDescriptor() {
      if (this == UNRECOGNIZED) {
        throw new java.lang.IllegalStateException(
            "Can't get the descriptor of an unrecognized enum value.");
      }
      return getDescriptor().getValues().get(ordinal());
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    public static final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return com.helium.grpc.protocol_http_roaming_v1.getDescriptor().getEnumTypes().get(0);
    }

    private static final flow_type_v1[] VALUES = values();

    public static flow_type_v1 valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      if (desc.getIndex() == -1) {
        return UNRECOGNIZED;
      }
      return VALUES[desc.getIndex()];
    }

    private final int value;

    private flow_type_v1(int value) {
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:helium.iot_config.protocol_http_roaming_v1.flow_type_v1)
  }

  public static final int FLOW_TYPE_FIELD_NUMBER = 1;
  private int flowType_ = 0;
  /**
   * <code>.helium.iot_config.protocol_http_roaming_v1.flow_type_v1 flow_type = 1;</code>
   * @return The enum numeric value on the wire for flowType.
   */
  @java.lang.Override public int getFlowTypeValue() {
    return flowType_;
  }
  /**
   * <code>.helium.iot_config.protocol_http_roaming_v1.flow_type_v1 flow_type = 1;</code>
   * @return The flowType.
   */
  @java.lang.Override public com.helium.grpc.protocol_http_roaming_v1.flow_type_v1 getFlowType() {
    com.helium.grpc.protocol_http_roaming_v1.flow_type_v1 result = com.helium.grpc.protocol_http_roaming_v1.flow_type_v1.forNumber(flowType_);
    return result == null ? com.helium.grpc.protocol_http_roaming_v1.flow_type_v1.UNRECOGNIZED : result;
  }

  public static final int DEDUPE_TIMEOUT_FIELD_NUMBER = 2;
  private int dedupeTimeout_ = 0;
  /**
   * <pre>
   * milliseconds
   * </pre>
   *
   * <code>uint32 dedupe_timeout = 2;</code>
   * @return The dedupeTimeout.
   */
  @java.lang.Override
  public int getDedupeTimeout() {
    return dedupeTimeout_;
  }

  public static final int PATH_FIELD_NUMBER = 3;
  @SuppressWarnings("serial")
  private volatile java.lang.Object path_ = "";
  /**
   * <pre>
   * path component of URL of roaming partner
   * </pre>
   *
   * <code>string path = 3;</code>
   * @return The path.
   */
  @java.lang.Override
  public java.lang.String getPath() {
    java.lang.Object ref = path_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      path_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * path component of URL of roaming partner
   * </pre>
   *
   * <code>string path = 3;</code>
   * @return The bytes for path.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getPathBytes() {
    java.lang.Object ref = path_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      path_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int AUTH_HEADER_FIELD_NUMBER = 4;
  @SuppressWarnings("serial")
  private volatile java.lang.Object authHeader_ = "";
  /**
   * <pre>
   * Authorization Header
   * </pre>
   *
   * <code>string auth_header = 4;</code>
   * @return The authHeader.
   */
  @java.lang.Override
  public java.lang.String getAuthHeader() {
    java.lang.Object ref = authHeader_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      authHeader_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * Authorization Header
   * </pre>
   *
   * <code>string auth_header = 4;</code>
   * @return The bytes for authHeader.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getAuthHeaderBytes() {
    java.lang.Object ref = authHeader_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      authHeader_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int RECEIVER_NSID_FIELD_NUMBER = 5;
  @SuppressWarnings("serial")
  private volatile java.lang.Object receiverNsid_ = "";
  /**
   * <pre>
   * Receiver NSID
   * </pre>
   *
   * <code>string receiver_nsid = 5;</code>
   * @return The receiverNsid.
   */
  @java.lang.Override
  public java.lang.String getReceiverNsid() {
    java.lang.Object ref = receiverNsid_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      receiverNsid_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * Receiver NSID
   * </pre>
   *
   * <code>string receiver_nsid = 5;</code>
   * @return The bytes for receiverNsid.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getReceiverNsidBytes() {
    java.lang.Object ref = receiverNsid_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      receiverNsid_ = b;
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
    if (flowType_ != com.helium.grpc.protocol_http_roaming_v1.flow_type_v1.sync.getNumber()) {
      output.writeEnum(1, flowType_);
    }
    if (dedupeTimeout_ != 0) {
      output.writeUInt32(2, dedupeTimeout_);
    }
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(path_)) {
      com.google.protobuf.GeneratedMessage.writeString(output, 3, path_);
    }
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(authHeader_)) {
      com.google.protobuf.GeneratedMessage.writeString(output, 4, authHeader_);
    }
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(receiverNsid_)) {
      com.google.protobuf.GeneratedMessage.writeString(output, 5, receiverNsid_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (flowType_ != com.helium.grpc.protocol_http_roaming_v1.flow_type_v1.sync.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(1, flowType_);
    }
    if (dedupeTimeout_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt32Size(2, dedupeTimeout_);
    }
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(path_)) {
      size += com.google.protobuf.GeneratedMessage.computeStringSize(3, path_);
    }
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(authHeader_)) {
      size += com.google.protobuf.GeneratedMessage.computeStringSize(4, authHeader_);
    }
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(receiverNsid_)) {
      size += com.google.protobuf.GeneratedMessage.computeStringSize(5, receiverNsid_);
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
    if (!(obj instanceof com.helium.grpc.protocol_http_roaming_v1)) {
      return super.equals(obj);
    }
    com.helium.grpc.protocol_http_roaming_v1 other = (com.helium.grpc.protocol_http_roaming_v1) obj;

    if (flowType_ != other.flowType_) return false;
    if (getDedupeTimeout()
        != other.getDedupeTimeout()) return false;
    if (!getPath()
        .equals(other.getPath())) return false;
    if (!getAuthHeader()
        .equals(other.getAuthHeader())) return false;
    if (!getReceiverNsid()
        .equals(other.getReceiverNsid())) return false;
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
    hash = (37 * hash) + FLOW_TYPE_FIELD_NUMBER;
    hash = (53 * hash) + flowType_;
    hash = (37 * hash) + DEDUPE_TIMEOUT_FIELD_NUMBER;
    hash = (53 * hash) + getDedupeTimeout();
    hash = (37 * hash) + PATH_FIELD_NUMBER;
    hash = (53 * hash) + getPath().hashCode();
    hash = (37 * hash) + AUTH_HEADER_FIELD_NUMBER;
    hash = (53 * hash) + getAuthHeader().hashCode();
    hash = (37 * hash) + RECEIVER_NSID_FIELD_NUMBER;
    hash = (53 * hash) + getReceiverNsid().hashCode();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.helium.grpc.protocol_http_roaming_v1 parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.helium.grpc.protocol_http_roaming_v1 parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.helium.grpc.protocol_http_roaming_v1 parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.helium.grpc.protocol_http_roaming_v1 parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.helium.grpc.protocol_http_roaming_v1 parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.helium.grpc.protocol_http_roaming_v1 parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.helium.grpc.protocol_http_roaming_v1 parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static com.helium.grpc.protocol_http_roaming_v1 parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static com.helium.grpc.protocol_http_roaming_v1 parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static com.helium.grpc.protocol_http_roaming_v1 parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.helium.grpc.protocol_http_roaming_v1 parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static com.helium.grpc.protocol_http_roaming_v1 parseFrom(
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
  public static Builder newBuilder(com.helium.grpc.protocol_http_roaming_v1 prototype) {
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
   * HTTP Roaming protocol options
   * </pre>
   *
   * Protobuf type {@code helium.iot_config.protocol_http_roaming_v1}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:helium.iot_config.protocol_http_roaming_v1)
      com.helium.grpc.protocol_http_roaming_v1OrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.helium.grpc.IotConfig.internal_static_helium_iot_config_protocol_http_roaming_v1_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.helium.grpc.IotConfig.internal_static_helium_iot_config_protocol_http_roaming_v1_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.helium.grpc.protocol_http_roaming_v1.class, com.helium.grpc.protocol_http_roaming_v1.Builder.class);
    }

    // Construct using com.helium.grpc.protocol_http_roaming_v1.newBuilder()
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
      flowType_ = 0;
      dedupeTimeout_ = 0;
      path_ = "";
      authHeader_ = "";
      receiverNsid_ = "";
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.helium.grpc.IotConfig.internal_static_helium_iot_config_protocol_http_roaming_v1_descriptor;
    }

    @java.lang.Override
    public com.helium.grpc.protocol_http_roaming_v1 getDefaultInstanceForType() {
      return com.helium.grpc.protocol_http_roaming_v1.getDefaultInstance();
    }

    @java.lang.Override
    public com.helium.grpc.protocol_http_roaming_v1 build() {
      com.helium.grpc.protocol_http_roaming_v1 result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.helium.grpc.protocol_http_roaming_v1 buildPartial() {
      com.helium.grpc.protocol_http_roaming_v1 result = new com.helium.grpc.protocol_http_roaming_v1(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(com.helium.grpc.protocol_http_roaming_v1 result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.flowType_ = flowType_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.dedupeTimeout_ = dedupeTimeout_;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.path_ = path_;
      }
      if (((from_bitField0_ & 0x00000008) != 0)) {
        result.authHeader_ = authHeader_;
      }
      if (((from_bitField0_ & 0x00000010) != 0)) {
        result.receiverNsid_ = receiverNsid_;
      }
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.helium.grpc.protocol_http_roaming_v1) {
        return mergeFrom((com.helium.grpc.protocol_http_roaming_v1)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.helium.grpc.protocol_http_roaming_v1 other) {
      if (other == com.helium.grpc.protocol_http_roaming_v1.getDefaultInstance()) return this;
      if (other.flowType_ != 0) {
        setFlowTypeValue(other.getFlowTypeValue());
      }
      if (other.getDedupeTimeout() != 0) {
        setDedupeTimeout(other.getDedupeTimeout());
      }
      if (!other.getPath().isEmpty()) {
        path_ = other.path_;
        bitField0_ |= 0x00000004;
        onChanged();
      }
      if (!other.getAuthHeader().isEmpty()) {
        authHeader_ = other.authHeader_;
        bitField0_ |= 0x00000008;
        onChanged();
      }
      if (!other.getReceiverNsid().isEmpty()) {
        receiverNsid_ = other.receiverNsid_;
        bitField0_ |= 0x00000010;
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
            case 8: {
              flowType_ = input.readEnum();
              bitField0_ |= 0x00000001;
              break;
            } // case 8
            case 16: {
              dedupeTimeout_ = input.readUInt32();
              bitField0_ |= 0x00000002;
              break;
            } // case 16
            case 26: {
              path_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000004;
              break;
            } // case 26
            case 34: {
              authHeader_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000008;
              break;
            } // case 34
            case 42: {
              receiverNsid_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000010;
              break;
            } // case 42
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

    private int flowType_ = 0;
    /**
     * <code>.helium.iot_config.protocol_http_roaming_v1.flow_type_v1 flow_type = 1;</code>
     * @return The enum numeric value on the wire for flowType.
     */
    @java.lang.Override public int getFlowTypeValue() {
      return flowType_;
    }
    /**
     * <code>.helium.iot_config.protocol_http_roaming_v1.flow_type_v1 flow_type = 1;</code>
     * @param value The enum numeric value on the wire for flowType to set.
     * @return This builder for chaining.
     */
    public Builder setFlowTypeValue(int value) {
      flowType_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>.helium.iot_config.protocol_http_roaming_v1.flow_type_v1 flow_type = 1;</code>
     * @return The flowType.
     */
    @java.lang.Override
    public com.helium.grpc.protocol_http_roaming_v1.flow_type_v1 getFlowType() {
      com.helium.grpc.protocol_http_roaming_v1.flow_type_v1 result = com.helium.grpc.protocol_http_roaming_v1.flow_type_v1.forNumber(flowType_);
      return result == null ? com.helium.grpc.protocol_http_roaming_v1.flow_type_v1.UNRECOGNIZED : result;
    }
    /**
     * <code>.helium.iot_config.protocol_http_roaming_v1.flow_type_v1 flow_type = 1;</code>
     * @param value The flowType to set.
     * @return This builder for chaining.
     */
    public Builder setFlowType(com.helium.grpc.protocol_http_roaming_v1.flow_type_v1 value) {
      if (value == null) {
        throw new NullPointerException();
      }
      bitField0_ |= 0x00000001;
      flowType_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <code>.helium.iot_config.protocol_http_roaming_v1.flow_type_v1 flow_type = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearFlowType() {
      bitField0_ = (bitField0_ & ~0x00000001);
      flowType_ = 0;
      onChanged();
      return this;
    }

    private int dedupeTimeout_ ;
    /**
     * <pre>
     * milliseconds
     * </pre>
     *
     * <code>uint32 dedupe_timeout = 2;</code>
     * @return The dedupeTimeout.
     */
    @java.lang.Override
    public int getDedupeTimeout() {
      return dedupeTimeout_;
    }
    /**
     * <pre>
     * milliseconds
     * </pre>
     *
     * <code>uint32 dedupe_timeout = 2;</code>
     * @param value The dedupeTimeout to set.
     * @return This builder for chaining.
     */
    public Builder setDedupeTimeout(int value) {

      dedupeTimeout_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * milliseconds
     * </pre>
     *
     * <code>uint32 dedupe_timeout = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearDedupeTimeout() {
      bitField0_ = (bitField0_ & ~0x00000002);
      dedupeTimeout_ = 0;
      onChanged();
      return this;
    }

    private java.lang.Object path_ = "";
    /**
     * <pre>
     * path component of URL of roaming partner
     * </pre>
     *
     * <code>string path = 3;</code>
     * @return The path.
     */
    public java.lang.String getPath() {
      java.lang.Object ref = path_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        path_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     * path component of URL of roaming partner
     * </pre>
     *
     * <code>string path = 3;</code>
     * @return The bytes for path.
     */
    public com.google.protobuf.ByteString
        getPathBytes() {
      java.lang.Object ref = path_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        path_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * path component of URL of roaming partner
     * </pre>
     *
     * <code>string path = 3;</code>
     * @param value The path to set.
     * @return This builder for chaining.
     */
    public Builder setPath(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      path_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * path component of URL of roaming partner
     * </pre>
     *
     * <code>string path = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearPath() {
      path_ = getDefaultInstance().getPath();
      bitField0_ = (bitField0_ & ~0x00000004);
      onChanged();
      return this;
    }
    /**
     * <pre>
     * path component of URL of roaming partner
     * </pre>
     *
     * <code>string path = 3;</code>
     * @param value The bytes for path to set.
     * @return This builder for chaining.
     */
    public Builder setPathBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      path_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }

    private java.lang.Object authHeader_ = "";
    /**
     * <pre>
     * Authorization Header
     * </pre>
     *
     * <code>string auth_header = 4;</code>
     * @return The authHeader.
     */
    public java.lang.String getAuthHeader() {
      java.lang.Object ref = authHeader_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        authHeader_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     * Authorization Header
     * </pre>
     *
     * <code>string auth_header = 4;</code>
     * @return The bytes for authHeader.
     */
    public com.google.protobuf.ByteString
        getAuthHeaderBytes() {
      java.lang.Object ref = authHeader_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        authHeader_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * Authorization Header
     * </pre>
     *
     * <code>string auth_header = 4;</code>
     * @param value The authHeader to set.
     * @return This builder for chaining.
     */
    public Builder setAuthHeader(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      authHeader_ = value;
      bitField0_ |= 0x00000008;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Authorization Header
     * </pre>
     *
     * <code>string auth_header = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearAuthHeader() {
      authHeader_ = getDefaultInstance().getAuthHeader();
      bitField0_ = (bitField0_ & ~0x00000008);
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Authorization Header
     * </pre>
     *
     * <code>string auth_header = 4;</code>
     * @param value The bytes for authHeader to set.
     * @return This builder for chaining.
     */
    public Builder setAuthHeaderBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      authHeader_ = value;
      bitField0_ |= 0x00000008;
      onChanged();
      return this;
    }

    private java.lang.Object receiverNsid_ = "";
    /**
     * <pre>
     * Receiver NSID
     * </pre>
     *
     * <code>string receiver_nsid = 5;</code>
     * @return The receiverNsid.
     */
    public java.lang.String getReceiverNsid() {
      java.lang.Object ref = receiverNsid_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        receiverNsid_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     * Receiver NSID
     * </pre>
     *
     * <code>string receiver_nsid = 5;</code>
     * @return The bytes for receiverNsid.
     */
    public com.google.protobuf.ByteString
        getReceiverNsidBytes() {
      java.lang.Object ref = receiverNsid_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        receiverNsid_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * Receiver NSID
     * </pre>
     *
     * <code>string receiver_nsid = 5;</code>
     * @param value The receiverNsid to set.
     * @return This builder for chaining.
     */
    public Builder setReceiverNsid(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      receiverNsid_ = value;
      bitField0_ |= 0x00000010;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Receiver NSID
     * </pre>
     *
     * <code>string receiver_nsid = 5;</code>
     * @return This builder for chaining.
     */
    public Builder clearReceiverNsid() {
      receiverNsid_ = getDefaultInstance().getReceiverNsid();
      bitField0_ = (bitField0_ & ~0x00000010);
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Receiver NSID
     * </pre>
     *
     * <code>string receiver_nsid = 5;</code>
     * @param value The bytes for receiverNsid to set.
     * @return This builder for chaining.
     */
    public Builder setReceiverNsidBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      receiverNsid_ = value;
      bitField0_ |= 0x00000010;
      onChanged();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:helium.iot_config.protocol_http_roaming_v1)
  }

  // @@protoc_insertion_point(class_scope:helium.iot_config.protocol_http_roaming_v1)
  private static final com.helium.grpc.protocol_http_roaming_v1 DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.helium.grpc.protocol_http_roaming_v1();
  }

  public static com.helium.grpc.protocol_http_roaming_v1 getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<protocol_http_roaming_v1>
      PARSER = new com.google.protobuf.AbstractParser<protocol_http_roaming_v1>() {
    @java.lang.Override
    public protocol_http_roaming_v1 parsePartialFrom(
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

  public static com.google.protobuf.Parser<protocol_http_roaming_v1> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<protocol_http_roaming_v1> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.helium.grpc.protocol_http_roaming_v1 getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

