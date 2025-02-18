// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: api/internal.proto
// Protobuf Java Version: 4.29.2

package io.chirpstack.api;

/**
 * <pre>
 * Defines a tenant to which the user is associated.
 * </pre>
 *
 * Protobuf type {@code api.UserTenantLink}
 */
public final class UserTenantLink extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:api.UserTenantLink)
    UserTenantLinkOrBuilder {
private static final long serialVersionUID = 0L;
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 29,
      /* patch= */ 2,
      /* suffix= */ "",
      UserTenantLink.class.getName());
  }
  // Use UserTenantLink.newBuilder() to construct.
  private UserTenantLink(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private UserTenantLink() {
    tenantId_ = "";
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return io.chirpstack.api.InternalProto.internal_static_api_UserTenantLink_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return io.chirpstack.api.InternalProto.internal_static_api_UserTenantLink_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            io.chirpstack.api.UserTenantLink.class, io.chirpstack.api.UserTenantLink.Builder.class);
  }

  private int bitField0_;
  public static final int CREATED_AT_FIELD_NUMBER = 1;
  private com.google.protobuf.Timestamp createdAt_;
  /**
   * <pre>
   * Created at timestamp.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp created_at = 1;</code>
   * @return Whether the createdAt field is set.
   */
  @java.lang.Override
  public boolean hasCreatedAt() {
    return ((bitField0_ & 0x00000001) != 0);
  }
  /**
   * <pre>
   * Created at timestamp.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp created_at = 1;</code>
   * @return The createdAt.
   */
  @java.lang.Override
  public com.google.protobuf.Timestamp getCreatedAt() {
    return createdAt_ == null ? com.google.protobuf.Timestamp.getDefaultInstance() : createdAt_;
  }
  /**
   * <pre>
   * Created at timestamp.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp created_at = 1;</code>
   */
  @java.lang.Override
  public com.google.protobuf.TimestampOrBuilder getCreatedAtOrBuilder() {
    return createdAt_ == null ? com.google.protobuf.Timestamp.getDefaultInstance() : createdAt_;
  }

  public static final int UPDATED_AT_FIELD_NUMBER = 2;
  private com.google.protobuf.Timestamp updatedAt_;
  /**
   * <pre>
   * Last update timestamp.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp updated_at = 2;</code>
   * @return Whether the updatedAt field is set.
   */
  @java.lang.Override
  public boolean hasUpdatedAt() {
    return ((bitField0_ & 0x00000002) != 0);
  }
  /**
   * <pre>
   * Last update timestamp.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp updated_at = 2;</code>
   * @return The updatedAt.
   */
  @java.lang.Override
  public com.google.protobuf.Timestamp getUpdatedAt() {
    return updatedAt_ == null ? com.google.protobuf.Timestamp.getDefaultInstance() : updatedAt_;
  }
  /**
   * <pre>
   * Last update timestamp.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp updated_at = 2;</code>
   */
  @java.lang.Override
  public com.google.protobuf.TimestampOrBuilder getUpdatedAtOrBuilder() {
    return updatedAt_ == null ? com.google.protobuf.Timestamp.getDefaultInstance() : updatedAt_;
  }

  public static final int TENANT_ID_FIELD_NUMBER = 3;
  @SuppressWarnings("serial")
  private volatile java.lang.Object tenantId_ = "";
  /**
   * <pre>
   * Tenant ID.
   * </pre>
   *
   * <code>string tenant_id = 3;</code>
   * @return The tenantId.
   */
  @java.lang.Override
  public java.lang.String getTenantId() {
    java.lang.Object ref = tenantId_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      tenantId_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * Tenant ID.
   * </pre>
   *
   * <code>string tenant_id = 3;</code>
   * @return The bytes for tenantId.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getTenantIdBytes() {
    java.lang.Object ref = tenantId_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      tenantId_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int IS_ADMIN_FIELD_NUMBER = 4;
  private boolean isAdmin_ = false;
  /**
   * <pre>
   * User is admin within the context of this tenant.
   * There is no need to set the is_device_admin and is_gateway_admin flags.
   * </pre>
   *
   * <code>bool is_admin = 4;</code>
   * @return The isAdmin.
   */
  @java.lang.Override
  public boolean getIsAdmin() {
    return isAdmin_;
  }

  public static final int IS_DEVICE_ADMIN_FIELD_NUMBER = 5;
  private boolean isDeviceAdmin_ = false;
  /**
   * <pre>
   * User is able to modify device related resources (applications,
   * device-profiles, devices, multicast-groups).
   * </pre>
   *
   * <code>bool is_device_admin = 5;</code>
   * @return The isDeviceAdmin.
   */
  @java.lang.Override
  public boolean getIsDeviceAdmin() {
    return isDeviceAdmin_;
  }

  public static final int IS_GATEWAY_ADMIN_FIELD_NUMBER = 6;
  private boolean isGatewayAdmin_ = false;
  /**
   * <pre>
   * User is able to modify gateways.
   * </pre>
   *
   * <code>bool is_gateway_admin = 6;</code>
   * @return The isGatewayAdmin.
   */
  @java.lang.Override
  public boolean getIsGatewayAdmin() {
    return isGatewayAdmin_;
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
    if (((bitField0_ & 0x00000001) != 0)) {
      output.writeMessage(1, getCreatedAt());
    }
    if (((bitField0_ & 0x00000002) != 0)) {
      output.writeMessage(2, getUpdatedAt());
    }
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(tenantId_)) {
      com.google.protobuf.GeneratedMessage.writeString(output, 3, tenantId_);
    }
    if (isAdmin_ != false) {
      output.writeBool(4, isAdmin_);
    }
    if (isDeviceAdmin_ != false) {
      output.writeBool(5, isDeviceAdmin_);
    }
    if (isGatewayAdmin_ != false) {
      output.writeBool(6, isGatewayAdmin_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (((bitField0_ & 0x00000001) != 0)) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getCreatedAt());
    }
    if (((bitField0_ & 0x00000002) != 0)) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, getUpdatedAt());
    }
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(tenantId_)) {
      size += com.google.protobuf.GeneratedMessage.computeStringSize(3, tenantId_);
    }
    if (isAdmin_ != false) {
      size += com.google.protobuf.CodedOutputStream
        .computeBoolSize(4, isAdmin_);
    }
    if (isDeviceAdmin_ != false) {
      size += com.google.protobuf.CodedOutputStream
        .computeBoolSize(5, isDeviceAdmin_);
    }
    if (isGatewayAdmin_ != false) {
      size += com.google.protobuf.CodedOutputStream
        .computeBoolSize(6, isGatewayAdmin_);
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
    if (!(obj instanceof io.chirpstack.api.UserTenantLink)) {
      return super.equals(obj);
    }
    io.chirpstack.api.UserTenantLink other = (io.chirpstack.api.UserTenantLink) obj;

    if (hasCreatedAt() != other.hasCreatedAt()) return false;
    if (hasCreatedAt()) {
      if (!getCreatedAt()
          .equals(other.getCreatedAt())) return false;
    }
    if (hasUpdatedAt() != other.hasUpdatedAt()) return false;
    if (hasUpdatedAt()) {
      if (!getUpdatedAt()
          .equals(other.getUpdatedAt())) return false;
    }
    if (!getTenantId()
        .equals(other.getTenantId())) return false;
    if (getIsAdmin()
        != other.getIsAdmin()) return false;
    if (getIsDeviceAdmin()
        != other.getIsDeviceAdmin()) return false;
    if (getIsGatewayAdmin()
        != other.getIsGatewayAdmin()) return false;
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
    if (hasCreatedAt()) {
      hash = (37 * hash) + CREATED_AT_FIELD_NUMBER;
      hash = (53 * hash) + getCreatedAt().hashCode();
    }
    if (hasUpdatedAt()) {
      hash = (37 * hash) + UPDATED_AT_FIELD_NUMBER;
      hash = (53 * hash) + getUpdatedAt().hashCode();
    }
    hash = (37 * hash) + TENANT_ID_FIELD_NUMBER;
    hash = (53 * hash) + getTenantId().hashCode();
    hash = (37 * hash) + IS_ADMIN_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
        getIsAdmin());
    hash = (37 * hash) + IS_DEVICE_ADMIN_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
        getIsDeviceAdmin());
    hash = (37 * hash) + IS_GATEWAY_ADMIN_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
        getIsGatewayAdmin());
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static io.chirpstack.api.UserTenantLink parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.UserTenantLink parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.UserTenantLink parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.UserTenantLink parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.UserTenantLink parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.UserTenantLink parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.UserTenantLink parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static io.chirpstack.api.UserTenantLink parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static io.chirpstack.api.UserTenantLink parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static io.chirpstack.api.UserTenantLink parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.chirpstack.api.UserTenantLink parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static io.chirpstack.api.UserTenantLink parseFrom(
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
  public static Builder newBuilder(io.chirpstack.api.UserTenantLink prototype) {
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
   * Defines a tenant to which the user is associated.
   * </pre>
   *
   * Protobuf type {@code api.UserTenantLink}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:api.UserTenantLink)
      io.chirpstack.api.UserTenantLinkOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return io.chirpstack.api.InternalProto.internal_static_api_UserTenantLink_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return io.chirpstack.api.InternalProto.internal_static_api_UserTenantLink_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              io.chirpstack.api.UserTenantLink.class, io.chirpstack.api.UserTenantLink.Builder.class);
    }

    // Construct using io.chirpstack.api.UserTenantLink.newBuilder()
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
        getCreatedAtFieldBuilder();
        getUpdatedAtFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      createdAt_ = null;
      if (createdAtBuilder_ != null) {
        createdAtBuilder_.dispose();
        createdAtBuilder_ = null;
      }
      updatedAt_ = null;
      if (updatedAtBuilder_ != null) {
        updatedAtBuilder_.dispose();
        updatedAtBuilder_ = null;
      }
      tenantId_ = "";
      isAdmin_ = false;
      isDeviceAdmin_ = false;
      isGatewayAdmin_ = false;
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return io.chirpstack.api.InternalProto.internal_static_api_UserTenantLink_descriptor;
    }

    @java.lang.Override
    public io.chirpstack.api.UserTenantLink getDefaultInstanceForType() {
      return io.chirpstack.api.UserTenantLink.getDefaultInstance();
    }

    @java.lang.Override
    public io.chirpstack.api.UserTenantLink build() {
      io.chirpstack.api.UserTenantLink result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public io.chirpstack.api.UserTenantLink buildPartial() {
      io.chirpstack.api.UserTenantLink result = new io.chirpstack.api.UserTenantLink(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(io.chirpstack.api.UserTenantLink result) {
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.createdAt_ = createdAtBuilder_ == null
            ? createdAt_
            : createdAtBuilder_.build();
        to_bitField0_ |= 0x00000001;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.updatedAt_ = updatedAtBuilder_ == null
            ? updatedAt_
            : updatedAtBuilder_.build();
        to_bitField0_ |= 0x00000002;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.tenantId_ = tenantId_;
      }
      if (((from_bitField0_ & 0x00000008) != 0)) {
        result.isAdmin_ = isAdmin_;
      }
      if (((from_bitField0_ & 0x00000010) != 0)) {
        result.isDeviceAdmin_ = isDeviceAdmin_;
      }
      if (((from_bitField0_ & 0x00000020) != 0)) {
        result.isGatewayAdmin_ = isGatewayAdmin_;
      }
      result.bitField0_ |= to_bitField0_;
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof io.chirpstack.api.UserTenantLink) {
        return mergeFrom((io.chirpstack.api.UserTenantLink)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(io.chirpstack.api.UserTenantLink other) {
      if (other == io.chirpstack.api.UserTenantLink.getDefaultInstance()) return this;
      if (other.hasCreatedAt()) {
        mergeCreatedAt(other.getCreatedAt());
      }
      if (other.hasUpdatedAt()) {
        mergeUpdatedAt(other.getUpdatedAt());
      }
      if (!other.getTenantId().isEmpty()) {
        tenantId_ = other.tenantId_;
        bitField0_ |= 0x00000004;
        onChanged();
      }
      if (other.getIsAdmin() != false) {
        setIsAdmin(other.getIsAdmin());
      }
      if (other.getIsDeviceAdmin() != false) {
        setIsDeviceAdmin(other.getIsDeviceAdmin());
      }
      if (other.getIsGatewayAdmin() != false) {
        setIsGatewayAdmin(other.getIsGatewayAdmin());
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
              input.readMessage(
                  getCreatedAtFieldBuilder().getBuilder(),
                  extensionRegistry);
              bitField0_ |= 0x00000001;
              break;
            } // case 10
            case 18: {
              input.readMessage(
                  getUpdatedAtFieldBuilder().getBuilder(),
                  extensionRegistry);
              bitField0_ |= 0x00000002;
              break;
            } // case 18
            case 26: {
              tenantId_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000004;
              break;
            } // case 26
            case 32: {
              isAdmin_ = input.readBool();
              bitField0_ |= 0x00000008;
              break;
            } // case 32
            case 40: {
              isDeviceAdmin_ = input.readBool();
              bitField0_ |= 0x00000010;
              break;
            } // case 40
            case 48: {
              isGatewayAdmin_ = input.readBool();
              bitField0_ |= 0x00000020;
              break;
            } // case 48
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

    private com.google.protobuf.Timestamp createdAt_;
    private com.google.protobuf.SingleFieldBuilder<
        com.google.protobuf.Timestamp, com.google.protobuf.Timestamp.Builder, com.google.protobuf.TimestampOrBuilder> createdAtBuilder_;
    /**
     * <pre>
     * Created at timestamp.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp created_at = 1;</code>
     * @return Whether the createdAt field is set.
     */
    public boolean hasCreatedAt() {
      return ((bitField0_ & 0x00000001) != 0);
    }
    /**
     * <pre>
     * Created at timestamp.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp created_at = 1;</code>
     * @return The createdAt.
     */
    public com.google.protobuf.Timestamp getCreatedAt() {
      if (createdAtBuilder_ == null) {
        return createdAt_ == null ? com.google.protobuf.Timestamp.getDefaultInstance() : createdAt_;
      } else {
        return createdAtBuilder_.getMessage();
      }
    }
    /**
     * <pre>
     * Created at timestamp.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp created_at = 1;</code>
     */
    public Builder setCreatedAt(com.google.protobuf.Timestamp value) {
      if (createdAtBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        createdAt_ = value;
      } else {
        createdAtBuilder_.setMessage(value);
      }
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Created at timestamp.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp created_at = 1;</code>
     */
    public Builder setCreatedAt(
        com.google.protobuf.Timestamp.Builder builderForValue) {
      if (createdAtBuilder_ == null) {
        createdAt_ = builderForValue.build();
      } else {
        createdAtBuilder_.setMessage(builderForValue.build());
      }
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Created at timestamp.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp created_at = 1;</code>
     */
    public Builder mergeCreatedAt(com.google.protobuf.Timestamp value) {
      if (createdAtBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0) &&
          createdAt_ != null &&
          createdAt_ != com.google.protobuf.Timestamp.getDefaultInstance()) {
          getCreatedAtBuilder().mergeFrom(value);
        } else {
          createdAt_ = value;
        }
      } else {
        createdAtBuilder_.mergeFrom(value);
      }
      if (createdAt_ != null) {
        bitField0_ |= 0x00000001;
        onChanged();
      }
      return this;
    }
    /**
     * <pre>
     * Created at timestamp.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp created_at = 1;</code>
     */
    public Builder clearCreatedAt() {
      bitField0_ = (bitField0_ & ~0x00000001);
      createdAt_ = null;
      if (createdAtBuilder_ != null) {
        createdAtBuilder_.dispose();
        createdAtBuilder_ = null;
      }
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Created at timestamp.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp created_at = 1;</code>
     */
    public com.google.protobuf.Timestamp.Builder getCreatedAtBuilder() {
      bitField0_ |= 0x00000001;
      onChanged();
      return getCreatedAtFieldBuilder().getBuilder();
    }
    /**
     * <pre>
     * Created at timestamp.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp created_at = 1;</code>
     */
    public com.google.protobuf.TimestampOrBuilder getCreatedAtOrBuilder() {
      if (createdAtBuilder_ != null) {
        return createdAtBuilder_.getMessageOrBuilder();
      } else {
        return createdAt_ == null ?
            com.google.protobuf.Timestamp.getDefaultInstance() : createdAt_;
      }
    }
    /**
     * <pre>
     * Created at timestamp.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp created_at = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilder<
        com.google.protobuf.Timestamp, com.google.protobuf.Timestamp.Builder, com.google.protobuf.TimestampOrBuilder> 
        getCreatedAtFieldBuilder() {
      if (createdAtBuilder_ == null) {
        createdAtBuilder_ = new com.google.protobuf.SingleFieldBuilder<
            com.google.protobuf.Timestamp, com.google.protobuf.Timestamp.Builder, com.google.protobuf.TimestampOrBuilder>(
                getCreatedAt(),
                getParentForChildren(),
                isClean());
        createdAt_ = null;
      }
      return createdAtBuilder_;
    }

    private com.google.protobuf.Timestamp updatedAt_;
    private com.google.protobuf.SingleFieldBuilder<
        com.google.protobuf.Timestamp, com.google.protobuf.Timestamp.Builder, com.google.protobuf.TimestampOrBuilder> updatedAtBuilder_;
    /**
     * <pre>
     * Last update timestamp.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp updated_at = 2;</code>
     * @return Whether the updatedAt field is set.
     */
    public boolean hasUpdatedAt() {
      return ((bitField0_ & 0x00000002) != 0);
    }
    /**
     * <pre>
     * Last update timestamp.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp updated_at = 2;</code>
     * @return The updatedAt.
     */
    public com.google.protobuf.Timestamp getUpdatedAt() {
      if (updatedAtBuilder_ == null) {
        return updatedAt_ == null ? com.google.protobuf.Timestamp.getDefaultInstance() : updatedAt_;
      } else {
        return updatedAtBuilder_.getMessage();
      }
    }
    /**
     * <pre>
     * Last update timestamp.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp updated_at = 2;</code>
     */
    public Builder setUpdatedAt(com.google.protobuf.Timestamp value) {
      if (updatedAtBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        updatedAt_ = value;
      } else {
        updatedAtBuilder_.setMessage(value);
      }
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Last update timestamp.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp updated_at = 2;</code>
     */
    public Builder setUpdatedAt(
        com.google.protobuf.Timestamp.Builder builderForValue) {
      if (updatedAtBuilder_ == null) {
        updatedAt_ = builderForValue.build();
      } else {
        updatedAtBuilder_.setMessage(builderForValue.build());
      }
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Last update timestamp.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp updated_at = 2;</code>
     */
    public Builder mergeUpdatedAt(com.google.protobuf.Timestamp value) {
      if (updatedAtBuilder_ == null) {
        if (((bitField0_ & 0x00000002) != 0) &&
          updatedAt_ != null &&
          updatedAt_ != com.google.protobuf.Timestamp.getDefaultInstance()) {
          getUpdatedAtBuilder().mergeFrom(value);
        } else {
          updatedAt_ = value;
        }
      } else {
        updatedAtBuilder_.mergeFrom(value);
      }
      if (updatedAt_ != null) {
        bitField0_ |= 0x00000002;
        onChanged();
      }
      return this;
    }
    /**
     * <pre>
     * Last update timestamp.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp updated_at = 2;</code>
     */
    public Builder clearUpdatedAt() {
      bitField0_ = (bitField0_ & ~0x00000002);
      updatedAt_ = null;
      if (updatedAtBuilder_ != null) {
        updatedAtBuilder_.dispose();
        updatedAtBuilder_ = null;
      }
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Last update timestamp.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp updated_at = 2;</code>
     */
    public com.google.protobuf.Timestamp.Builder getUpdatedAtBuilder() {
      bitField0_ |= 0x00000002;
      onChanged();
      return getUpdatedAtFieldBuilder().getBuilder();
    }
    /**
     * <pre>
     * Last update timestamp.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp updated_at = 2;</code>
     */
    public com.google.protobuf.TimestampOrBuilder getUpdatedAtOrBuilder() {
      if (updatedAtBuilder_ != null) {
        return updatedAtBuilder_.getMessageOrBuilder();
      } else {
        return updatedAt_ == null ?
            com.google.protobuf.Timestamp.getDefaultInstance() : updatedAt_;
      }
    }
    /**
     * <pre>
     * Last update timestamp.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp updated_at = 2;</code>
     */
    private com.google.protobuf.SingleFieldBuilder<
        com.google.protobuf.Timestamp, com.google.protobuf.Timestamp.Builder, com.google.protobuf.TimestampOrBuilder> 
        getUpdatedAtFieldBuilder() {
      if (updatedAtBuilder_ == null) {
        updatedAtBuilder_ = new com.google.protobuf.SingleFieldBuilder<
            com.google.protobuf.Timestamp, com.google.protobuf.Timestamp.Builder, com.google.protobuf.TimestampOrBuilder>(
                getUpdatedAt(),
                getParentForChildren(),
                isClean());
        updatedAt_ = null;
      }
      return updatedAtBuilder_;
    }

    private java.lang.Object tenantId_ = "";
    /**
     * <pre>
     * Tenant ID.
     * </pre>
     *
     * <code>string tenant_id = 3;</code>
     * @return The tenantId.
     */
    public java.lang.String getTenantId() {
      java.lang.Object ref = tenantId_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        tenantId_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     * Tenant ID.
     * </pre>
     *
     * <code>string tenant_id = 3;</code>
     * @return The bytes for tenantId.
     */
    public com.google.protobuf.ByteString
        getTenantIdBytes() {
      java.lang.Object ref = tenantId_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        tenantId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * Tenant ID.
     * </pre>
     *
     * <code>string tenant_id = 3;</code>
     * @param value The tenantId to set.
     * @return This builder for chaining.
     */
    public Builder setTenantId(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      tenantId_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Tenant ID.
     * </pre>
     *
     * <code>string tenant_id = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearTenantId() {
      tenantId_ = getDefaultInstance().getTenantId();
      bitField0_ = (bitField0_ & ~0x00000004);
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Tenant ID.
     * </pre>
     *
     * <code>string tenant_id = 3;</code>
     * @param value The bytes for tenantId to set.
     * @return This builder for chaining.
     */
    public Builder setTenantIdBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      tenantId_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }

    private boolean isAdmin_ ;
    /**
     * <pre>
     * User is admin within the context of this tenant.
     * There is no need to set the is_device_admin and is_gateway_admin flags.
     * </pre>
     *
     * <code>bool is_admin = 4;</code>
     * @return The isAdmin.
     */
    @java.lang.Override
    public boolean getIsAdmin() {
      return isAdmin_;
    }
    /**
     * <pre>
     * User is admin within the context of this tenant.
     * There is no need to set the is_device_admin and is_gateway_admin flags.
     * </pre>
     *
     * <code>bool is_admin = 4;</code>
     * @param value The isAdmin to set.
     * @return This builder for chaining.
     */
    public Builder setIsAdmin(boolean value) {

      isAdmin_ = value;
      bitField0_ |= 0x00000008;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * User is admin within the context of this tenant.
     * There is no need to set the is_device_admin and is_gateway_admin flags.
     * </pre>
     *
     * <code>bool is_admin = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearIsAdmin() {
      bitField0_ = (bitField0_ & ~0x00000008);
      isAdmin_ = false;
      onChanged();
      return this;
    }

    private boolean isDeviceAdmin_ ;
    /**
     * <pre>
     * User is able to modify device related resources (applications,
     * device-profiles, devices, multicast-groups).
     * </pre>
     *
     * <code>bool is_device_admin = 5;</code>
     * @return The isDeviceAdmin.
     */
    @java.lang.Override
    public boolean getIsDeviceAdmin() {
      return isDeviceAdmin_;
    }
    /**
     * <pre>
     * User is able to modify device related resources (applications,
     * device-profiles, devices, multicast-groups).
     * </pre>
     *
     * <code>bool is_device_admin = 5;</code>
     * @param value The isDeviceAdmin to set.
     * @return This builder for chaining.
     */
    public Builder setIsDeviceAdmin(boolean value) {

      isDeviceAdmin_ = value;
      bitField0_ |= 0x00000010;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * User is able to modify device related resources (applications,
     * device-profiles, devices, multicast-groups).
     * </pre>
     *
     * <code>bool is_device_admin = 5;</code>
     * @return This builder for chaining.
     */
    public Builder clearIsDeviceAdmin() {
      bitField0_ = (bitField0_ & ~0x00000010);
      isDeviceAdmin_ = false;
      onChanged();
      return this;
    }

    private boolean isGatewayAdmin_ ;
    /**
     * <pre>
     * User is able to modify gateways.
     * </pre>
     *
     * <code>bool is_gateway_admin = 6;</code>
     * @return The isGatewayAdmin.
     */
    @java.lang.Override
    public boolean getIsGatewayAdmin() {
      return isGatewayAdmin_;
    }
    /**
     * <pre>
     * User is able to modify gateways.
     * </pre>
     *
     * <code>bool is_gateway_admin = 6;</code>
     * @param value The isGatewayAdmin to set.
     * @return This builder for chaining.
     */
    public Builder setIsGatewayAdmin(boolean value) {

      isGatewayAdmin_ = value;
      bitField0_ |= 0x00000020;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * User is able to modify gateways.
     * </pre>
     *
     * <code>bool is_gateway_admin = 6;</code>
     * @return This builder for chaining.
     */
    public Builder clearIsGatewayAdmin() {
      bitField0_ = (bitField0_ & ~0x00000020);
      isGatewayAdmin_ = false;
      onChanged();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:api.UserTenantLink)
  }

  // @@protoc_insertion_point(class_scope:api.UserTenantLink)
  private static final io.chirpstack.api.UserTenantLink DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new io.chirpstack.api.UserTenantLink();
  }

  public static io.chirpstack.api.UserTenantLink getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<UserTenantLink>
      PARSER = new com.google.protobuf.AbstractParser<UserTenantLink>() {
    @java.lang.Override
    public UserTenantLink parsePartialFrom(
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

  public static com.google.protobuf.Parser<UserTenantLink> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<UserTenantLink> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public io.chirpstack.api.UserTenantLink getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

