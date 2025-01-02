// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: internal/internal.proto
// Protobuf Java Version: 4.29.2

package io.chirpstack.internal;

/**
 * Protobuf type {@code internal.PassiveRoamingDeviceSession}
 */
public final class PassiveRoamingDeviceSession extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:internal.PassiveRoamingDeviceSession)
    PassiveRoamingDeviceSessionOrBuilder {
private static final long serialVersionUID = 0L;
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 29,
      /* patch= */ 2,
      /* suffix= */ "",
      PassiveRoamingDeviceSession.class.getName());
  }
  // Use PassiveRoamingDeviceSession.newBuilder() to construct.
  private PassiveRoamingDeviceSession(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private PassiveRoamingDeviceSession() {
    sessionId_ = com.google.protobuf.ByteString.EMPTY;
    netId_ = com.google.protobuf.ByteString.EMPTY;
    devAddr_ = com.google.protobuf.ByteString.EMPTY;
    devEui_ = com.google.protobuf.ByteString.EMPTY;
    fNwkSIntKey_ = com.google.protobuf.ByteString.EMPTY;
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return io.chirpstack.internal.InternalProto.internal_static_internal_PassiveRoamingDeviceSession_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return io.chirpstack.internal.InternalProto.internal_static_internal_PassiveRoamingDeviceSession_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            io.chirpstack.internal.PassiveRoamingDeviceSession.class, io.chirpstack.internal.PassiveRoamingDeviceSession.Builder.class);
  }

  private int bitField0_;
  public static final int SESSION_ID_FIELD_NUMBER = 1;
  private com.google.protobuf.ByteString sessionId_ = com.google.protobuf.ByteString.EMPTY;
  /**
   * <pre>
   * Session ID (UUID).
   * Unfortunately we can not use the DevEUI as unique identifier
   * as the PRStartAns DevEUI field is optional.
   * </pre>
   *
   * <code>bytes session_id = 1;</code>
   * @return The sessionId.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getSessionId() {
    return sessionId_;
  }

  public static final int NET_ID_FIELD_NUMBER = 2;
  private com.google.protobuf.ByteString netId_ = com.google.protobuf.ByteString.EMPTY;
  /**
   * <pre>
   * NetID of the hNS.
   * </pre>
   *
   * <code>bytes net_id = 2;</code>
   * @return The netId.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getNetId() {
    return netId_;
  }

  public static final int DEV_ADDR_FIELD_NUMBER = 3;
  private com.google.protobuf.ByteString devAddr_ = com.google.protobuf.ByteString.EMPTY;
  /**
   * <pre>
   * DevAddr of the device.
   * </pre>
   *
   * <code>bytes dev_addr = 3;</code>
   * @return The devAddr.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getDevAddr() {
    return devAddr_;
  }

  public static final int DEV_EUI_FIELD_NUMBER = 4;
  private com.google.protobuf.ByteString devEui_ = com.google.protobuf.ByteString.EMPTY;
  /**
   * <pre>
   * DevEUI of the device (optional).
   * </pre>
   *
   * <code>bytes dev_eui = 4;</code>
   * @return The devEui.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getDevEui() {
    return devEui_;
  }

  public static final int LORAWAN_1_1_FIELD_NUMBER = 5;
  private boolean lorawan11_ = false;
  /**
   * <pre>
   * LoRaWAN 1.1.
   * </pre>
   *
   * <code>bool lorawan_1_1 = 5;</code>
   * @return The lorawan11.
   */
  @java.lang.Override
  public boolean getLorawan11() {
    return lorawan11_;
  }

  public static final int F_NWK_S_INT_KEY_FIELD_NUMBER = 6;
  private com.google.protobuf.ByteString fNwkSIntKey_ = com.google.protobuf.ByteString.EMPTY;
  /**
   * <pre>
   * LoRaWAN 1.0 NwkSKey / LoRaWAN 1.1 FNwkSIntKey.
   * </pre>
   *
   * <code>bytes f_nwk_s_int_key = 6;</code>
   * @return The fNwkSIntKey.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getFNwkSIntKey() {
    return fNwkSIntKey_;
  }

  public static final int LIFETIME_FIELD_NUMBER = 7;
  private com.google.protobuf.Timestamp lifetime_;
  /**
   * <pre>
   * Lifetime.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp lifetime = 7;</code>
   * @return Whether the lifetime field is set.
   */
  @java.lang.Override
  public boolean hasLifetime() {
    return ((bitField0_ & 0x00000001) != 0);
  }
  /**
   * <pre>
   * Lifetime.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp lifetime = 7;</code>
   * @return The lifetime.
   */
  @java.lang.Override
  public com.google.protobuf.Timestamp getLifetime() {
    return lifetime_ == null ? com.google.protobuf.Timestamp.getDefaultInstance() : lifetime_;
  }
  /**
   * <pre>
   * Lifetime.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp lifetime = 7;</code>
   */
  @java.lang.Override
  public com.google.protobuf.TimestampOrBuilder getLifetimeOrBuilder() {
    return lifetime_ == null ? com.google.protobuf.Timestamp.getDefaultInstance() : lifetime_;
  }

  public static final int F_CNT_UP_FIELD_NUMBER = 8;
  private int fCntUp_ = 0;
  /**
   * <pre>
   * Uplink frame-counter.
   * </pre>
   *
   * <code>uint32 f_cnt_up = 8;</code>
   * @return The fCntUp.
   */
  @java.lang.Override
  public int getFCntUp() {
    return fCntUp_;
  }

  public static final int VALIDATE_MIC_FIELD_NUMBER = 9;
  private boolean validateMic_ = false;
  /**
   * <pre>
   * Validate MIC.
   * </pre>
   *
   * <code>bool validate_mic = 9;</code>
   * @return The validateMic.
   */
  @java.lang.Override
  public boolean getValidateMic() {
    return validateMic_;
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
    if (!sessionId_.isEmpty()) {
      output.writeBytes(1, sessionId_);
    }
    if (!netId_.isEmpty()) {
      output.writeBytes(2, netId_);
    }
    if (!devAddr_.isEmpty()) {
      output.writeBytes(3, devAddr_);
    }
    if (!devEui_.isEmpty()) {
      output.writeBytes(4, devEui_);
    }
    if (lorawan11_ != false) {
      output.writeBool(5, lorawan11_);
    }
    if (!fNwkSIntKey_.isEmpty()) {
      output.writeBytes(6, fNwkSIntKey_);
    }
    if (((bitField0_ & 0x00000001) != 0)) {
      output.writeMessage(7, getLifetime());
    }
    if (fCntUp_ != 0) {
      output.writeUInt32(8, fCntUp_);
    }
    if (validateMic_ != false) {
      output.writeBool(9, validateMic_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!sessionId_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(1, sessionId_);
    }
    if (!netId_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(2, netId_);
    }
    if (!devAddr_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(3, devAddr_);
    }
    if (!devEui_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(4, devEui_);
    }
    if (lorawan11_ != false) {
      size += com.google.protobuf.CodedOutputStream
        .computeBoolSize(5, lorawan11_);
    }
    if (!fNwkSIntKey_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(6, fNwkSIntKey_);
    }
    if (((bitField0_ & 0x00000001) != 0)) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(7, getLifetime());
    }
    if (fCntUp_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt32Size(8, fCntUp_);
    }
    if (validateMic_ != false) {
      size += com.google.protobuf.CodedOutputStream
        .computeBoolSize(9, validateMic_);
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
    if (!(obj instanceof io.chirpstack.internal.PassiveRoamingDeviceSession)) {
      return super.equals(obj);
    }
    io.chirpstack.internal.PassiveRoamingDeviceSession other = (io.chirpstack.internal.PassiveRoamingDeviceSession) obj;

    if (!getSessionId()
        .equals(other.getSessionId())) return false;
    if (!getNetId()
        .equals(other.getNetId())) return false;
    if (!getDevAddr()
        .equals(other.getDevAddr())) return false;
    if (!getDevEui()
        .equals(other.getDevEui())) return false;
    if (getLorawan11()
        != other.getLorawan11()) return false;
    if (!getFNwkSIntKey()
        .equals(other.getFNwkSIntKey())) return false;
    if (hasLifetime() != other.hasLifetime()) return false;
    if (hasLifetime()) {
      if (!getLifetime()
          .equals(other.getLifetime())) return false;
    }
    if (getFCntUp()
        != other.getFCntUp()) return false;
    if (getValidateMic()
        != other.getValidateMic()) return false;
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
    hash = (37 * hash) + SESSION_ID_FIELD_NUMBER;
    hash = (53 * hash) + getSessionId().hashCode();
    hash = (37 * hash) + NET_ID_FIELD_NUMBER;
    hash = (53 * hash) + getNetId().hashCode();
    hash = (37 * hash) + DEV_ADDR_FIELD_NUMBER;
    hash = (53 * hash) + getDevAddr().hashCode();
    hash = (37 * hash) + DEV_EUI_FIELD_NUMBER;
    hash = (53 * hash) + getDevEui().hashCode();
    hash = (37 * hash) + LORAWAN_1_1_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
        getLorawan11());
    hash = (37 * hash) + F_NWK_S_INT_KEY_FIELD_NUMBER;
    hash = (53 * hash) + getFNwkSIntKey().hashCode();
    if (hasLifetime()) {
      hash = (37 * hash) + LIFETIME_FIELD_NUMBER;
      hash = (53 * hash) + getLifetime().hashCode();
    }
    hash = (37 * hash) + F_CNT_UP_FIELD_NUMBER;
    hash = (53 * hash) + getFCntUp();
    hash = (37 * hash) + VALIDATE_MIC_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
        getValidateMic());
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static io.chirpstack.internal.PassiveRoamingDeviceSession parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.internal.PassiveRoamingDeviceSession parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.internal.PassiveRoamingDeviceSession parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.internal.PassiveRoamingDeviceSession parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.internal.PassiveRoamingDeviceSession parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.internal.PassiveRoamingDeviceSession parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.internal.PassiveRoamingDeviceSession parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static io.chirpstack.internal.PassiveRoamingDeviceSession parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static io.chirpstack.internal.PassiveRoamingDeviceSession parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static io.chirpstack.internal.PassiveRoamingDeviceSession parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.chirpstack.internal.PassiveRoamingDeviceSession parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static io.chirpstack.internal.PassiveRoamingDeviceSession parseFrom(
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
  public static Builder newBuilder(io.chirpstack.internal.PassiveRoamingDeviceSession prototype) {
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
   * Protobuf type {@code internal.PassiveRoamingDeviceSession}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:internal.PassiveRoamingDeviceSession)
      io.chirpstack.internal.PassiveRoamingDeviceSessionOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return io.chirpstack.internal.InternalProto.internal_static_internal_PassiveRoamingDeviceSession_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return io.chirpstack.internal.InternalProto.internal_static_internal_PassiveRoamingDeviceSession_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              io.chirpstack.internal.PassiveRoamingDeviceSession.class, io.chirpstack.internal.PassiveRoamingDeviceSession.Builder.class);
    }

    // Construct using io.chirpstack.internal.PassiveRoamingDeviceSession.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessage
              .alwaysUseFieldBuilders) {
        getLifetimeFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      sessionId_ = com.google.protobuf.ByteString.EMPTY;
      netId_ = com.google.protobuf.ByteString.EMPTY;
      devAddr_ = com.google.protobuf.ByteString.EMPTY;
      devEui_ = com.google.protobuf.ByteString.EMPTY;
      lorawan11_ = false;
      fNwkSIntKey_ = com.google.protobuf.ByteString.EMPTY;
      lifetime_ = null;
      if (lifetimeBuilder_ != null) {
        lifetimeBuilder_.dispose();
        lifetimeBuilder_ = null;
      }
      fCntUp_ = 0;
      validateMic_ = false;
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return io.chirpstack.internal.InternalProto.internal_static_internal_PassiveRoamingDeviceSession_descriptor;
    }

    @java.lang.Override
    public io.chirpstack.internal.PassiveRoamingDeviceSession getDefaultInstanceForType() {
      return io.chirpstack.internal.PassiveRoamingDeviceSession.getDefaultInstance();
    }

    @java.lang.Override
    public io.chirpstack.internal.PassiveRoamingDeviceSession build() {
      io.chirpstack.internal.PassiveRoamingDeviceSession result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public io.chirpstack.internal.PassiveRoamingDeviceSession buildPartial() {
      io.chirpstack.internal.PassiveRoamingDeviceSession result = new io.chirpstack.internal.PassiveRoamingDeviceSession(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(io.chirpstack.internal.PassiveRoamingDeviceSession result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.sessionId_ = sessionId_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.netId_ = netId_;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.devAddr_ = devAddr_;
      }
      if (((from_bitField0_ & 0x00000008) != 0)) {
        result.devEui_ = devEui_;
      }
      if (((from_bitField0_ & 0x00000010) != 0)) {
        result.lorawan11_ = lorawan11_;
      }
      if (((from_bitField0_ & 0x00000020) != 0)) {
        result.fNwkSIntKey_ = fNwkSIntKey_;
      }
      int to_bitField0_ = 0;
      if (((from_bitField0_ & 0x00000040) != 0)) {
        result.lifetime_ = lifetimeBuilder_ == null
            ? lifetime_
            : lifetimeBuilder_.build();
        to_bitField0_ |= 0x00000001;
      }
      if (((from_bitField0_ & 0x00000080) != 0)) {
        result.fCntUp_ = fCntUp_;
      }
      if (((from_bitField0_ & 0x00000100) != 0)) {
        result.validateMic_ = validateMic_;
      }
      result.bitField0_ |= to_bitField0_;
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof io.chirpstack.internal.PassiveRoamingDeviceSession) {
        return mergeFrom((io.chirpstack.internal.PassiveRoamingDeviceSession)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(io.chirpstack.internal.PassiveRoamingDeviceSession other) {
      if (other == io.chirpstack.internal.PassiveRoamingDeviceSession.getDefaultInstance()) return this;
      if (other.getSessionId() != com.google.protobuf.ByteString.EMPTY) {
        setSessionId(other.getSessionId());
      }
      if (other.getNetId() != com.google.protobuf.ByteString.EMPTY) {
        setNetId(other.getNetId());
      }
      if (other.getDevAddr() != com.google.protobuf.ByteString.EMPTY) {
        setDevAddr(other.getDevAddr());
      }
      if (other.getDevEui() != com.google.protobuf.ByteString.EMPTY) {
        setDevEui(other.getDevEui());
      }
      if (other.getLorawan11() != false) {
        setLorawan11(other.getLorawan11());
      }
      if (other.getFNwkSIntKey() != com.google.protobuf.ByteString.EMPTY) {
        setFNwkSIntKey(other.getFNwkSIntKey());
      }
      if (other.hasLifetime()) {
        mergeLifetime(other.getLifetime());
      }
      if (other.getFCntUp() != 0) {
        setFCntUp(other.getFCntUp());
      }
      if (other.getValidateMic() != false) {
        setValidateMic(other.getValidateMic());
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
              sessionId_ = input.readBytes();
              bitField0_ |= 0x00000001;
              break;
            } // case 10
            case 18: {
              netId_ = input.readBytes();
              bitField0_ |= 0x00000002;
              break;
            } // case 18
            case 26: {
              devAddr_ = input.readBytes();
              bitField0_ |= 0x00000004;
              break;
            } // case 26
            case 34: {
              devEui_ = input.readBytes();
              bitField0_ |= 0x00000008;
              break;
            } // case 34
            case 40: {
              lorawan11_ = input.readBool();
              bitField0_ |= 0x00000010;
              break;
            } // case 40
            case 50: {
              fNwkSIntKey_ = input.readBytes();
              bitField0_ |= 0x00000020;
              break;
            } // case 50
            case 58: {
              input.readMessage(
                  getLifetimeFieldBuilder().getBuilder(),
                  extensionRegistry);
              bitField0_ |= 0x00000040;
              break;
            } // case 58
            case 64: {
              fCntUp_ = input.readUInt32();
              bitField0_ |= 0x00000080;
              break;
            } // case 64
            case 72: {
              validateMic_ = input.readBool();
              bitField0_ |= 0x00000100;
              break;
            } // case 72
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

    private com.google.protobuf.ByteString sessionId_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <pre>
     * Session ID (UUID).
     * Unfortunately we can not use the DevEUI as unique identifier
     * as the PRStartAns DevEUI field is optional.
     * </pre>
     *
     * <code>bytes session_id = 1;</code>
     * @return The sessionId.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getSessionId() {
      return sessionId_;
    }
    /**
     * <pre>
     * Session ID (UUID).
     * Unfortunately we can not use the DevEUI as unique identifier
     * as the PRStartAns DevEUI field is optional.
     * </pre>
     *
     * <code>bytes session_id = 1;</code>
     * @param value The sessionId to set.
     * @return This builder for chaining.
     */
    public Builder setSessionId(com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      sessionId_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Session ID (UUID).
     * Unfortunately we can not use the DevEUI as unique identifier
     * as the PRStartAns DevEUI field is optional.
     * </pre>
     *
     * <code>bytes session_id = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearSessionId() {
      bitField0_ = (bitField0_ & ~0x00000001);
      sessionId_ = getDefaultInstance().getSessionId();
      onChanged();
      return this;
    }

    private com.google.protobuf.ByteString netId_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <pre>
     * NetID of the hNS.
     * </pre>
     *
     * <code>bytes net_id = 2;</code>
     * @return The netId.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getNetId() {
      return netId_;
    }
    /**
     * <pre>
     * NetID of the hNS.
     * </pre>
     *
     * <code>bytes net_id = 2;</code>
     * @param value The netId to set.
     * @return This builder for chaining.
     */
    public Builder setNetId(com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      netId_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * NetID of the hNS.
     * </pre>
     *
     * <code>bytes net_id = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearNetId() {
      bitField0_ = (bitField0_ & ~0x00000002);
      netId_ = getDefaultInstance().getNetId();
      onChanged();
      return this;
    }

    private com.google.protobuf.ByteString devAddr_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <pre>
     * DevAddr of the device.
     * </pre>
     *
     * <code>bytes dev_addr = 3;</code>
     * @return The devAddr.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getDevAddr() {
      return devAddr_;
    }
    /**
     * <pre>
     * DevAddr of the device.
     * </pre>
     *
     * <code>bytes dev_addr = 3;</code>
     * @param value The devAddr to set.
     * @return This builder for chaining.
     */
    public Builder setDevAddr(com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      devAddr_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * DevAddr of the device.
     * </pre>
     *
     * <code>bytes dev_addr = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearDevAddr() {
      bitField0_ = (bitField0_ & ~0x00000004);
      devAddr_ = getDefaultInstance().getDevAddr();
      onChanged();
      return this;
    }

    private com.google.protobuf.ByteString devEui_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <pre>
     * DevEUI of the device (optional).
     * </pre>
     *
     * <code>bytes dev_eui = 4;</code>
     * @return The devEui.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getDevEui() {
      return devEui_;
    }
    /**
     * <pre>
     * DevEUI of the device (optional).
     * </pre>
     *
     * <code>bytes dev_eui = 4;</code>
     * @param value The devEui to set.
     * @return This builder for chaining.
     */
    public Builder setDevEui(com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      devEui_ = value;
      bitField0_ |= 0x00000008;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * DevEUI of the device (optional).
     * </pre>
     *
     * <code>bytes dev_eui = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearDevEui() {
      bitField0_ = (bitField0_ & ~0x00000008);
      devEui_ = getDefaultInstance().getDevEui();
      onChanged();
      return this;
    }

    private boolean lorawan11_ ;
    /**
     * <pre>
     * LoRaWAN 1.1.
     * </pre>
     *
     * <code>bool lorawan_1_1 = 5;</code>
     * @return The lorawan11.
     */
    @java.lang.Override
    public boolean getLorawan11() {
      return lorawan11_;
    }
    /**
     * <pre>
     * LoRaWAN 1.1.
     * </pre>
     *
     * <code>bool lorawan_1_1 = 5;</code>
     * @param value The lorawan11 to set.
     * @return This builder for chaining.
     */
    public Builder setLorawan11(boolean value) {

      lorawan11_ = value;
      bitField0_ |= 0x00000010;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * LoRaWAN 1.1.
     * </pre>
     *
     * <code>bool lorawan_1_1 = 5;</code>
     * @return This builder for chaining.
     */
    public Builder clearLorawan11() {
      bitField0_ = (bitField0_ & ~0x00000010);
      lorawan11_ = false;
      onChanged();
      return this;
    }

    private com.google.protobuf.ByteString fNwkSIntKey_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <pre>
     * LoRaWAN 1.0 NwkSKey / LoRaWAN 1.1 FNwkSIntKey.
     * </pre>
     *
     * <code>bytes f_nwk_s_int_key = 6;</code>
     * @return The fNwkSIntKey.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getFNwkSIntKey() {
      return fNwkSIntKey_;
    }
    /**
     * <pre>
     * LoRaWAN 1.0 NwkSKey / LoRaWAN 1.1 FNwkSIntKey.
     * </pre>
     *
     * <code>bytes f_nwk_s_int_key = 6;</code>
     * @param value The fNwkSIntKey to set.
     * @return This builder for chaining.
     */
    public Builder setFNwkSIntKey(com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      fNwkSIntKey_ = value;
      bitField0_ |= 0x00000020;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * LoRaWAN 1.0 NwkSKey / LoRaWAN 1.1 FNwkSIntKey.
     * </pre>
     *
     * <code>bytes f_nwk_s_int_key = 6;</code>
     * @return This builder for chaining.
     */
    public Builder clearFNwkSIntKey() {
      bitField0_ = (bitField0_ & ~0x00000020);
      fNwkSIntKey_ = getDefaultInstance().getFNwkSIntKey();
      onChanged();
      return this;
    }

    private com.google.protobuf.Timestamp lifetime_;
    private com.google.protobuf.SingleFieldBuilder<
        com.google.protobuf.Timestamp, com.google.protobuf.Timestamp.Builder, com.google.protobuf.TimestampOrBuilder> lifetimeBuilder_;
    /**
     * <pre>
     * Lifetime.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp lifetime = 7;</code>
     * @return Whether the lifetime field is set.
     */
    public boolean hasLifetime() {
      return ((bitField0_ & 0x00000040) != 0);
    }
    /**
     * <pre>
     * Lifetime.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp lifetime = 7;</code>
     * @return The lifetime.
     */
    public com.google.protobuf.Timestamp getLifetime() {
      if (lifetimeBuilder_ == null) {
        return lifetime_ == null ? com.google.protobuf.Timestamp.getDefaultInstance() : lifetime_;
      } else {
        return lifetimeBuilder_.getMessage();
      }
    }
    /**
     * <pre>
     * Lifetime.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp lifetime = 7;</code>
     */
    public Builder setLifetime(com.google.protobuf.Timestamp value) {
      if (lifetimeBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        lifetime_ = value;
      } else {
        lifetimeBuilder_.setMessage(value);
      }
      bitField0_ |= 0x00000040;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Lifetime.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp lifetime = 7;</code>
     */
    public Builder setLifetime(
        com.google.protobuf.Timestamp.Builder builderForValue) {
      if (lifetimeBuilder_ == null) {
        lifetime_ = builderForValue.build();
      } else {
        lifetimeBuilder_.setMessage(builderForValue.build());
      }
      bitField0_ |= 0x00000040;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Lifetime.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp lifetime = 7;</code>
     */
    public Builder mergeLifetime(com.google.protobuf.Timestamp value) {
      if (lifetimeBuilder_ == null) {
        if (((bitField0_ & 0x00000040) != 0) &&
          lifetime_ != null &&
          lifetime_ != com.google.protobuf.Timestamp.getDefaultInstance()) {
          getLifetimeBuilder().mergeFrom(value);
        } else {
          lifetime_ = value;
        }
      } else {
        lifetimeBuilder_.mergeFrom(value);
      }
      if (lifetime_ != null) {
        bitField0_ |= 0x00000040;
        onChanged();
      }
      return this;
    }
    /**
     * <pre>
     * Lifetime.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp lifetime = 7;</code>
     */
    public Builder clearLifetime() {
      bitField0_ = (bitField0_ & ~0x00000040);
      lifetime_ = null;
      if (lifetimeBuilder_ != null) {
        lifetimeBuilder_.dispose();
        lifetimeBuilder_ = null;
      }
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Lifetime.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp lifetime = 7;</code>
     */
    public com.google.protobuf.Timestamp.Builder getLifetimeBuilder() {
      bitField0_ |= 0x00000040;
      onChanged();
      return getLifetimeFieldBuilder().getBuilder();
    }
    /**
     * <pre>
     * Lifetime.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp lifetime = 7;</code>
     */
    public com.google.protobuf.TimestampOrBuilder getLifetimeOrBuilder() {
      if (lifetimeBuilder_ != null) {
        return lifetimeBuilder_.getMessageOrBuilder();
      } else {
        return lifetime_ == null ?
            com.google.protobuf.Timestamp.getDefaultInstance() : lifetime_;
      }
    }
    /**
     * <pre>
     * Lifetime.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp lifetime = 7;</code>
     */
    private com.google.protobuf.SingleFieldBuilder<
        com.google.protobuf.Timestamp, com.google.protobuf.Timestamp.Builder, com.google.protobuf.TimestampOrBuilder> 
        getLifetimeFieldBuilder() {
      if (lifetimeBuilder_ == null) {
        lifetimeBuilder_ = new com.google.protobuf.SingleFieldBuilder<
            com.google.protobuf.Timestamp, com.google.protobuf.Timestamp.Builder, com.google.protobuf.TimestampOrBuilder>(
                getLifetime(),
                getParentForChildren(),
                isClean());
        lifetime_ = null;
      }
      return lifetimeBuilder_;
    }

    private int fCntUp_ ;
    /**
     * <pre>
     * Uplink frame-counter.
     * </pre>
     *
     * <code>uint32 f_cnt_up = 8;</code>
     * @return The fCntUp.
     */
    @java.lang.Override
    public int getFCntUp() {
      return fCntUp_;
    }
    /**
     * <pre>
     * Uplink frame-counter.
     * </pre>
     *
     * <code>uint32 f_cnt_up = 8;</code>
     * @param value The fCntUp to set.
     * @return This builder for chaining.
     */
    public Builder setFCntUp(int value) {

      fCntUp_ = value;
      bitField0_ |= 0x00000080;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Uplink frame-counter.
     * </pre>
     *
     * <code>uint32 f_cnt_up = 8;</code>
     * @return This builder for chaining.
     */
    public Builder clearFCntUp() {
      bitField0_ = (bitField0_ & ~0x00000080);
      fCntUp_ = 0;
      onChanged();
      return this;
    }

    private boolean validateMic_ ;
    /**
     * <pre>
     * Validate MIC.
     * </pre>
     *
     * <code>bool validate_mic = 9;</code>
     * @return The validateMic.
     */
    @java.lang.Override
    public boolean getValidateMic() {
      return validateMic_;
    }
    /**
     * <pre>
     * Validate MIC.
     * </pre>
     *
     * <code>bool validate_mic = 9;</code>
     * @param value The validateMic to set.
     * @return This builder for chaining.
     */
    public Builder setValidateMic(boolean value) {

      validateMic_ = value;
      bitField0_ |= 0x00000100;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Validate MIC.
     * </pre>
     *
     * <code>bool validate_mic = 9;</code>
     * @return This builder for chaining.
     */
    public Builder clearValidateMic() {
      bitField0_ = (bitField0_ & ~0x00000100);
      validateMic_ = false;
      onChanged();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:internal.PassiveRoamingDeviceSession)
  }

  // @@protoc_insertion_point(class_scope:internal.PassiveRoamingDeviceSession)
  private static final io.chirpstack.internal.PassiveRoamingDeviceSession DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new io.chirpstack.internal.PassiveRoamingDeviceSession();
  }

  public static io.chirpstack.internal.PassiveRoamingDeviceSession getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<PassiveRoamingDeviceSession>
      PARSER = new com.google.protobuf.AbstractParser<PassiveRoamingDeviceSession>() {
    @java.lang.Override
    public PassiveRoamingDeviceSession parsePartialFrom(
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

  public static com.google.protobuf.Parser<PassiveRoamingDeviceSession> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<PassiveRoamingDeviceSession> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public io.chirpstack.internal.PassiveRoamingDeviceSession getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

