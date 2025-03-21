// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: api/internal.proto
// Protobuf Java Version: 4.29.2

package io.chirpstack.api;

/**
 * Protobuf type {@code api.OAuth2}
 */
public final class OAuth2 extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:api.OAuth2)
    OAuth2OrBuilder {
private static final long serialVersionUID = 0L;
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 29,
      /* patch= */ 2,
      /* suffix= */ "",
      OAuth2.class.getName());
  }
  // Use OAuth2.newBuilder() to construct.
  private OAuth2(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private OAuth2() {
    loginUrl_ = "";
    loginLabel_ = "";
    logoutUrl_ = "";
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return io.chirpstack.api.InternalProto.internal_static_api_OAuth2_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return io.chirpstack.api.InternalProto.internal_static_api_OAuth2_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            io.chirpstack.api.OAuth2.class, io.chirpstack.api.OAuth2.Builder.class);
  }

  public static final int ENABLED_FIELD_NUMBER = 1;
  private boolean enabled_ = false;
  /**
   * <pre>
   * OAuth2 is enabled.
   * </pre>
   *
   * <code>bool enabled = 1;</code>
   * @return The enabled.
   */
  @java.lang.Override
  public boolean getEnabled() {
    return enabled_;
  }

  public static final int LOGIN_URL_FIELD_NUMBER = 2;
  @SuppressWarnings("serial")
  private volatile java.lang.Object loginUrl_ = "";
  /**
   * <pre>
   * Login url.
   * </pre>
   *
   * <code>string login_url = 2;</code>
   * @return The loginUrl.
   */
  @java.lang.Override
  public java.lang.String getLoginUrl() {
    java.lang.Object ref = loginUrl_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      loginUrl_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * Login url.
   * </pre>
   *
   * <code>string login_url = 2;</code>
   * @return The bytes for loginUrl.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getLoginUrlBytes() {
    java.lang.Object ref = loginUrl_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      loginUrl_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int LOGIN_LABEL_FIELD_NUMBER = 3;
  @SuppressWarnings("serial")
  private volatile java.lang.Object loginLabel_ = "";
  /**
   * <pre>
   * Login label.
   * </pre>
   *
   * <code>string login_label = 3;</code>
   * @return The loginLabel.
   */
  @java.lang.Override
  public java.lang.String getLoginLabel() {
    java.lang.Object ref = loginLabel_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      loginLabel_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * Login label.
   * </pre>
   *
   * <code>string login_label = 3;</code>
   * @return The bytes for loginLabel.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getLoginLabelBytes() {
    java.lang.Object ref = loginLabel_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      loginLabel_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int LOGOUT_URL_FIELD_NUMBER = 4;
  @SuppressWarnings("serial")
  private volatile java.lang.Object logoutUrl_ = "";
  /**
   * <pre>
   * Logout url.
   * </pre>
   *
   * <code>string logout_url = 4;</code>
   * @return The logoutUrl.
   */
  @java.lang.Override
  public java.lang.String getLogoutUrl() {
    java.lang.Object ref = logoutUrl_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      logoutUrl_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * Logout url.
   * </pre>
   *
   * <code>string logout_url = 4;</code>
   * @return The bytes for logoutUrl.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getLogoutUrlBytes() {
    java.lang.Object ref = logoutUrl_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      logoutUrl_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int LOGIN_REDIRECT_FIELD_NUMBER = 5;
  private boolean loginRedirect_ = false;
  /**
   * <pre>
   * Login redirect.
   * </pre>
   *
   * <code>bool login_redirect = 5;</code>
   * @return The loginRedirect.
   */
  @java.lang.Override
  public boolean getLoginRedirect() {
    return loginRedirect_;
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
    if (enabled_ != false) {
      output.writeBool(1, enabled_);
    }
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(loginUrl_)) {
      com.google.protobuf.GeneratedMessage.writeString(output, 2, loginUrl_);
    }
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(loginLabel_)) {
      com.google.protobuf.GeneratedMessage.writeString(output, 3, loginLabel_);
    }
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(logoutUrl_)) {
      com.google.protobuf.GeneratedMessage.writeString(output, 4, logoutUrl_);
    }
    if (loginRedirect_ != false) {
      output.writeBool(5, loginRedirect_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (enabled_ != false) {
      size += com.google.protobuf.CodedOutputStream
        .computeBoolSize(1, enabled_);
    }
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(loginUrl_)) {
      size += com.google.protobuf.GeneratedMessage.computeStringSize(2, loginUrl_);
    }
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(loginLabel_)) {
      size += com.google.protobuf.GeneratedMessage.computeStringSize(3, loginLabel_);
    }
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(logoutUrl_)) {
      size += com.google.protobuf.GeneratedMessage.computeStringSize(4, logoutUrl_);
    }
    if (loginRedirect_ != false) {
      size += com.google.protobuf.CodedOutputStream
        .computeBoolSize(5, loginRedirect_);
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
    if (!(obj instanceof io.chirpstack.api.OAuth2)) {
      return super.equals(obj);
    }
    io.chirpstack.api.OAuth2 other = (io.chirpstack.api.OAuth2) obj;

    if (getEnabled()
        != other.getEnabled()) return false;
    if (!getLoginUrl()
        .equals(other.getLoginUrl())) return false;
    if (!getLoginLabel()
        .equals(other.getLoginLabel())) return false;
    if (!getLogoutUrl()
        .equals(other.getLogoutUrl())) return false;
    if (getLoginRedirect()
        != other.getLoginRedirect()) return false;
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
    hash = (37 * hash) + ENABLED_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
        getEnabled());
    hash = (37 * hash) + LOGIN_URL_FIELD_NUMBER;
    hash = (53 * hash) + getLoginUrl().hashCode();
    hash = (37 * hash) + LOGIN_LABEL_FIELD_NUMBER;
    hash = (53 * hash) + getLoginLabel().hashCode();
    hash = (37 * hash) + LOGOUT_URL_FIELD_NUMBER;
    hash = (53 * hash) + getLogoutUrl().hashCode();
    hash = (37 * hash) + LOGIN_REDIRECT_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
        getLoginRedirect());
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static io.chirpstack.api.OAuth2 parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.OAuth2 parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.OAuth2 parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.OAuth2 parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.OAuth2 parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.OAuth2 parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.OAuth2 parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static io.chirpstack.api.OAuth2 parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static io.chirpstack.api.OAuth2 parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static io.chirpstack.api.OAuth2 parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.chirpstack.api.OAuth2 parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static io.chirpstack.api.OAuth2 parseFrom(
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
  public static Builder newBuilder(io.chirpstack.api.OAuth2 prototype) {
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
   * Protobuf type {@code api.OAuth2}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:api.OAuth2)
      io.chirpstack.api.OAuth2OrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return io.chirpstack.api.InternalProto.internal_static_api_OAuth2_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return io.chirpstack.api.InternalProto.internal_static_api_OAuth2_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              io.chirpstack.api.OAuth2.class, io.chirpstack.api.OAuth2.Builder.class);
    }

    // Construct using io.chirpstack.api.OAuth2.newBuilder()
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
      enabled_ = false;
      loginUrl_ = "";
      loginLabel_ = "";
      logoutUrl_ = "";
      loginRedirect_ = false;
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return io.chirpstack.api.InternalProto.internal_static_api_OAuth2_descriptor;
    }

    @java.lang.Override
    public io.chirpstack.api.OAuth2 getDefaultInstanceForType() {
      return io.chirpstack.api.OAuth2.getDefaultInstance();
    }

    @java.lang.Override
    public io.chirpstack.api.OAuth2 build() {
      io.chirpstack.api.OAuth2 result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public io.chirpstack.api.OAuth2 buildPartial() {
      io.chirpstack.api.OAuth2 result = new io.chirpstack.api.OAuth2(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(io.chirpstack.api.OAuth2 result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.enabled_ = enabled_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.loginUrl_ = loginUrl_;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.loginLabel_ = loginLabel_;
      }
      if (((from_bitField0_ & 0x00000008) != 0)) {
        result.logoutUrl_ = logoutUrl_;
      }
      if (((from_bitField0_ & 0x00000010) != 0)) {
        result.loginRedirect_ = loginRedirect_;
      }
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof io.chirpstack.api.OAuth2) {
        return mergeFrom((io.chirpstack.api.OAuth2)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(io.chirpstack.api.OAuth2 other) {
      if (other == io.chirpstack.api.OAuth2.getDefaultInstance()) return this;
      if (other.getEnabled() != false) {
        setEnabled(other.getEnabled());
      }
      if (!other.getLoginUrl().isEmpty()) {
        loginUrl_ = other.loginUrl_;
        bitField0_ |= 0x00000002;
        onChanged();
      }
      if (!other.getLoginLabel().isEmpty()) {
        loginLabel_ = other.loginLabel_;
        bitField0_ |= 0x00000004;
        onChanged();
      }
      if (!other.getLogoutUrl().isEmpty()) {
        logoutUrl_ = other.logoutUrl_;
        bitField0_ |= 0x00000008;
        onChanged();
      }
      if (other.getLoginRedirect() != false) {
        setLoginRedirect(other.getLoginRedirect());
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
              enabled_ = input.readBool();
              bitField0_ |= 0x00000001;
              break;
            } // case 8
            case 18: {
              loginUrl_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000002;
              break;
            } // case 18
            case 26: {
              loginLabel_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000004;
              break;
            } // case 26
            case 34: {
              logoutUrl_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000008;
              break;
            } // case 34
            case 40: {
              loginRedirect_ = input.readBool();
              bitField0_ |= 0x00000010;
              break;
            } // case 40
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

    private boolean enabled_ ;
    /**
     * <pre>
     * OAuth2 is enabled.
     * </pre>
     *
     * <code>bool enabled = 1;</code>
     * @return The enabled.
     */
    @java.lang.Override
    public boolean getEnabled() {
      return enabled_;
    }
    /**
     * <pre>
     * OAuth2 is enabled.
     * </pre>
     *
     * <code>bool enabled = 1;</code>
     * @param value The enabled to set.
     * @return This builder for chaining.
     */
    public Builder setEnabled(boolean value) {

      enabled_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * OAuth2 is enabled.
     * </pre>
     *
     * <code>bool enabled = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearEnabled() {
      bitField0_ = (bitField0_ & ~0x00000001);
      enabled_ = false;
      onChanged();
      return this;
    }

    private java.lang.Object loginUrl_ = "";
    /**
     * <pre>
     * Login url.
     * </pre>
     *
     * <code>string login_url = 2;</code>
     * @return The loginUrl.
     */
    public java.lang.String getLoginUrl() {
      java.lang.Object ref = loginUrl_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        loginUrl_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     * Login url.
     * </pre>
     *
     * <code>string login_url = 2;</code>
     * @return The bytes for loginUrl.
     */
    public com.google.protobuf.ByteString
        getLoginUrlBytes() {
      java.lang.Object ref = loginUrl_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        loginUrl_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * Login url.
     * </pre>
     *
     * <code>string login_url = 2;</code>
     * @param value The loginUrl to set.
     * @return This builder for chaining.
     */
    public Builder setLoginUrl(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      loginUrl_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Login url.
     * </pre>
     *
     * <code>string login_url = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearLoginUrl() {
      loginUrl_ = getDefaultInstance().getLoginUrl();
      bitField0_ = (bitField0_ & ~0x00000002);
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Login url.
     * </pre>
     *
     * <code>string login_url = 2;</code>
     * @param value The bytes for loginUrl to set.
     * @return This builder for chaining.
     */
    public Builder setLoginUrlBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      loginUrl_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }

    private java.lang.Object loginLabel_ = "";
    /**
     * <pre>
     * Login label.
     * </pre>
     *
     * <code>string login_label = 3;</code>
     * @return The loginLabel.
     */
    public java.lang.String getLoginLabel() {
      java.lang.Object ref = loginLabel_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        loginLabel_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     * Login label.
     * </pre>
     *
     * <code>string login_label = 3;</code>
     * @return The bytes for loginLabel.
     */
    public com.google.protobuf.ByteString
        getLoginLabelBytes() {
      java.lang.Object ref = loginLabel_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        loginLabel_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * Login label.
     * </pre>
     *
     * <code>string login_label = 3;</code>
     * @param value The loginLabel to set.
     * @return This builder for chaining.
     */
    public Builder setLoginLabel(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      loginLabel_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Login label.
     * </pre>
     *
     * <code>string login_label = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearLoginLabel() {
      loginLabel_ = getDefaultInstance().getLoginLabel();
      bitField0_ = (bitField0_ & ~0x00000004);
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Login label.
     * </pre>
     *
     * <code>string login_label = 3;</code>
     * @param value The bytes for loginLabel to set.
     * @return This builder for chaining.
     */
    public Builder setLoginLabelBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      loginLabel_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }

    private java.lang.Object logoutUrl_ = "";
    /**
     * <pre>
     * Logout url.
     * </pre>
     *
     * <code>string logout_url = 4;</code>
     * @return The logoutUrl.
     */
    public java.lang.String getLogoutUrl() {
      java.lang.Object ref = logoutUrl_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        logoutUrl_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     * Logout url.
     * </pre>
     *
     * <code>string logout_url = 4;</code>
     * @return The bytes for logoutUrl.
     */
    public com.google.protobuf.ByteString
        getLogoutUrlBytes() {
      java.lang.Object ref = logoutUrl_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        logoutUrl_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * Logout url.
     * </pre>
     *
     * <code>string logout_url = 4;</code>
     * @param value The logoutUrl to set.
     * @return This builder for chaining.
     */
    public Builder setLogoutUrl(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      logoutUrl_ = value;
      bitField0_ |= 0x00000008;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Logout url.
     * </pre>
     *
     * <code>string logout_url = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearLogoutUrl() {
      logoutUrl_ = getDefaultInstance().getLogoutUrl();
      bitField0_ = (bitField0_ & ~0x00000008);
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Logout url.
     * </pre>
     *
     * <code>string logout_url = 4;</code>
     * @param value The bytes for logoutUrl to set.
     * @return This builder for chaining.
     */
    public Builder setLogoutUrlBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      logoutUrl_ = value;
      bitField0_ |= 0x00000008;
      onChanged();
      return this;
    }

    private boolean loginRedirect_ ;
    /**
     * <pre>
     * Login redirect.
     * </pre>
     *
     * <code>bool login_redirect = 5;</code>
     * @return The loginRedirect.
     */
    @java.lang.Override
    public boolean getLoginRedirect() {
      return loginRedirect_;
    }
    /**
     * <pre>
     * Login redirect.
     * </pre>
     *
     * <code>bool login_redirect = 5;</code>
     * @param value The loginRedirect to set.
     * @return This builder for chaining.
     */
    public Builder setLoginRedirect(boolean value) {

      loginRedirect_ = value;
      bitField0_ |= 0x00000010;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Login redirect.
     * </pre>
     *
     * <code>bool login_redirect = 5;</code>
     * @return This builder for chaining.
     */
    public Builder clearLoginRedirect() {
      bitField0_ = (bitField0_ & ~0x00000010);
      loginRedirect_ = false;
      onChanged();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:api.OAuth2)
  }

  // @@protoc_insertion_point(class_scope:api.OAuth2)
  private static final io.chirpstack.api.OAuth2 DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new io.chirpstack.api.OAuth2();
  }

  public static io.chirpstack.api.OAuth2 getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<OAuth2>
      PARSER = new com.google.protobuf.AbstractParser<OAuth2>() {
    @java.lang.Override
    public OAuth2 parsePartialFrom(
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

  public static com.google.protobuf.Parser<OAuth2> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<OAuth2> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public io.chirpstack.api.OAuth2 getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

