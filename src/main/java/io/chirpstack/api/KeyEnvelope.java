// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: common/common.proto
// Protobuf Java Version: 4.29.2

package io.chirpstack.api;

/**
 * Protobuf type {@code common.KeyEnvelope}
 */
public final class KeyEnvelope extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:common.KeyEnvelope)
    KeyEnvelopeOrBuilder {
private static final long serialVersionUID = 0L;
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 29,
      /* patch= */ 2,
      /* suffix= */ "",
      KeyEnvelope.class.getName());
  }
  // Use KeyEnvelope.newBuilder() to construct.
  private KeyEnvelope(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private KeyEnvelope() {
    kekLabel_ = "";
    aesKey_ = com.google.protobuf.ByteString.EMPTY;
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return io.chirpstack.api.CommonProto.internal_static_common_KeyEnvelope_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return io.chirpstack.api.CommonProto.internal_static_common_KeyEnvelope_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            io.chirpstack.api.KeyEnvelope.class, io.chirpstack.api.KeyEnvelope.Builder.class);
  }

  public static final int KEK_LABEL_FIELD_NUMBER = 1;
  @SuppressWarnings("serial")
  private volatile java.lang.Object kekLabel_ = "";
  /**
   * <pre>
   * KEK label.
   * </pre>
   *
   * <code>string kek_label = 1;</code>
   * @return The kekLabel.
   */
  @java.lang.Override
  public java.lang.String getKekLabel() {
    java.lang.Object ref = kekLabel_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      kekLabel_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * KEK label.
   * </pre>
   *
   * <code>string kek_label = 1;</code>
   * @return The bytes for kekLabel.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getKekLabelBytes() {
    java.lang.Object ref = kekLabel_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      kekLabel_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int AES_KEY_FIELD_NUMBER = 2;
  private com.google.protobuf.ByteString aesKey_ = com.google.protobuf.ByteString.EMPTY;
  /**
   * <pre>
   * AES key (when the kek_label is set, this value must first be decrypted).
   * </pre>
   *
   * <code>bytes aes_key = 2;</code>
   * @return The aesKey.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getAesKey() {
    return aesKey_;
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
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(kekLabel_)) {
      com.google.protobuf.GeneratedMessage.writeString(output, 1, kekLabel_);
    }
    if (!aesKey_.isEmpty()) {
      output.writeBytes(2, aesKey_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(kekLabel_)) {
      size += com.google.protobuf.GeneratedMessage.computeStringSize(1, kekLabel_);
    }
    if (!aesKey_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(2, aesKey_);
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
    if (!(obj instanceof io.chirpstack.api.KeyEnvelope)) {
      return super.equals(obj);
    }
    io.chirpstack.api.KeyEnvelope other = (io.chirpstack.api.KeyEnvelope) obj;

    if (!getKekLabel()
        .equals(other.getKekLabel())) return false;
    if (!getAesKey()
        .equals(other.getAesKey())) return false;
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
    hash = (37 * hash) + KEK_LABEL_FIELD_NUMBER;
    hash = (53 * hash) + getKekLabel().hashCode();
    hash = (37 * hash) + AES_KEY_FIELD_NUMBER;
    hash = (53 * hash) + getAesKey().hashCode();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static io.chirpstack.api.KeyEnvelope parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.KeyEnvelope parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.KeyEnvelope parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.KeyEnvelope parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.KeyEnvelope parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.KeyEnvelope parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.KeyEnvelope parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static io.chirpstack.api.KeyEnvelope parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static io.chirpstack.api.KeyEnvelope parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static io.chirpstack.api.KeyEnvelope parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.chirpstack.api.KeyEnvelope parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static io.chirpstack.api.KeyEnvelope parseFrom(
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
  public static Builder newBuilder(io.chirpstack.api.KeyEnvelope prototype) {
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
   * Protobuf type {@code common.KeyEnvelope}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:common.KeyEnvelope)
      io.chirpstack.api.KeyEnvelopeOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return io.chirpstack.api.CommonProto.internal_static_common_KeyEnvelope_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return io.chirpstack.api.CommonProto.internal_static_common_KeyEnvelope_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              io.chirpstack.api.KeyEnvelope.class, io.chirpstack.api.KeyEnvelope.Builder.class);
    }

    // Construct using io.chirpstack.api.KeyEnvelope.newBuilder()
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
      kekLabel_ = "";
      aesKey_ = com.google.protobuf.ByteString.EMPTY;
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return io.chirpstack.api.CommonProto.internal_static_common_KeyEnvelope_descriptor;
    }

    @java.lang.Override
    public io.chirpstack.api.KeyEnvelope getDefaultInstanceForType() {
      return io.chirpstack.api.KeyEnvelope.getDefaultInstance();
    }

    @java.lang.Override
    public io.chirpstack.api.KeyEnvelope build() {
      io.chirpstack.api.KeyEnvelope result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public io.chirpstack.api.KeyEnvelope buildPartial() {
      io.chirpstack.api.KeyEnvelope result = new io.chirpstack.api.KeyEnvelope(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(io.chirpstack.api.KeyEnvelope result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.kekLabel_ = kekLabel_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.aesKey_ = aesKey_;
      }
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof io.chirpstack.api.KeyEnvelope) {
        return mergeFrom((io.chirpstack.api.KeyEnvelope)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(io.chirpstack.api.KeyEnvelope other) {
      if (other == io.chirpstack.api.KeyEnvelope.getDefaultInstance()) return this;
      if (!other.getKekLabel().isEmpty()) {
        kekLabel_ = other.kekLabel_;
        bitField0_ |= 0x00000001;
        onChanged();
      }
      if (other.getAesKey() != com.google.protobuf.ByteString.EMPTY) {
        setAesKey(other.getAesKey());
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
              kekLabel_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000001;
              break;
            } // case 10
            case 18: {
              aesKey_ = input.readBytes();
              bitField0_ |= 0x00000002;
              break;
            } // case 18
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

    private java.lang.Object kekLabel_ = "";
    /**
     * <pre>
     * KEK label.
     * </pre>
     *
     * <code>string kek_label = 1;</code>
     * @return The kekLabel.
     */
    public java.lang.String getKekLabel() {
      java.lang.Object ref = kekLabel_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        kekLabel_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     * KEK label.
     * </pre>
     *
     * <code>string kek_label = 1;</code>
     * @return The bytes for kekLabel.
     */
    public com.google.protobuf.ByteString
        getKekLabelBytes() {
      java.lang.Object ref = kekLabel_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        kekLabel_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * KEK label.
     * </pre>
     *
     * <code>string kek_label = 1;</code>
     * @param value The kekLabel to set.
     * @return This builder for chaining.
     */
    public Builder setKekLabel(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      kekLabel_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * KEK label.
     * </pre>
     *
     * <code>string kek_label = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearKekLabel() {
      kekLabel_ = getDefaultInstance().getKekLabel();
      bitField0_ = (bitField0_ & ~0x00000001);
      onChanged();
      return this;
    }
    /**
     * <pre>
     * KEK label.
     * </pre>
     *
     * <code>string kek_label = 1;</code>
     * @param value The bytes for kekLabel to set.
     * @return This builder for chaining.
     */
    public Builder setKekLabelBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      kekLabel_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }

    private com.google.protobuf.ByteString aesKey_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <pre>
     * AES key (when the kek_label is set, this value must first be decrypted).
     * </pre>
     *
     * <code>bytes aes_key = 2;</code>
     * @return The aesKey.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getAesKey() {
      return aesKey_;
    }
    /**
     * <pre>
     * AES key (when the kek_label is set, this value must first be decrypted).
     * </pre>
     *
     * <code>bytes aes_key = 2;</code>
     * @param value The aesKey to set.
     * @return This builder for chaining.
     */
    public Builder setAesKey(com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      aesKey_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * AES key (when the kek_label is set, this value must first be decrypted).
     * </pre>
     *
     * <code>bytes aes_key = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearAesKey() {
      bitField0_ = (bitField0_ & ~0x00000002);
      aesKey_ = getDefaultInstance().getAesKey();
      onChanged();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:common.KeyEnvelope)
  }

  // @@protoc_insertion_point(class_scope:common.KeyEnvelope)
  private static final io.chirpstack.api.KeyEnvelope DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new io.chirpstack.api.KeyEnvelope();
  }

  public static io.chirpstack.api.KeyEnvelope getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<KeyEnvelope>
      PARSER = new com.google.protobuf.AbstractParser<KeyEnvelope>() {
    @java.lang.Override
    public KeyEnvelope parsePartialFrom(
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

  public static com.google.protobuf.Parser<KeyEnvelope> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<KeyEnvelope> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public io.chirpstack.api.KeyEnvelope getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

