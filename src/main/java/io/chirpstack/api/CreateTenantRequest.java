// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: api/tenant.proto
// Protobuf Java Version: 4.29.2

package io.chirpstack.api;

/**
 * Protobuf type {@code api.CreateTenantRequest}
 */
public final class CreateTenantRequest extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:api.CreateTenantRequest)
    CreateTenantRequestOrBuilder {
private static final long serialVersionUID = 0L;
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 29,
      /* patch= */ 2,
      /* suffix= */ "",
      CreateTenantRequest.class.getName());
  }
  // Use CreateTenantRequest.newBuilder() to construct.
  private CreateTenantRequest(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private CreateTenantRequest() {
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return io.chirpstack.api.TenantProto.internal_static_api_CreateTenantRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return io.chirpstack.api.TenantProto.internal_static_api_CreateTenantRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            io.chirpstack.api.CreateTenantRequest.class, io.chirpstack.api.CreateTenantRequest.Builder.class);
  }

  private int bitField0_;
  public static final int TENANT_FIELD_NUMBER = 1;
  private io.chirpstack.api.Tenant tenant_;
  /**
   * <pre>
   * Tenant object to create.
   * </pre>
   *
   * <code>.api.Tenant tenant = 1;</code>
   * @return Whether the tenant field is set.
   */
  @java.lang.Override
  public boolean hasTenant() {
    return ((bitField0_ & 0x00000001) != 0);
  }
  /**
   * <pre>
   * Tenant object to create.
   * </pre>
   *
   * <code>.api.Tenant tenant = 1;</code>
   * @return The tenant.
   */
  @java.lang.Override
  public io.chirpstack.api.Tenant getTenant() {
    return tenant_ == null ? io.chirpstack.api.Tenant.getDefaultInstance() : tenant_;
  }
  /**
   * <pre>
   * Tenant object to create.
   * </pre>
   *
   * <code>.api.Tenant tenant = 1;</code>
   */
  @java.lang.Override
  public io.chirpstack.api.TenantOrBuilder getTenantOrBuilder() {
    return tenant_ == null ? io.chirpstack.api.Tenant.getDefaultInstance() : tenant_;
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
      output.writeMessage(1, getTenant());
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
        .computeMessageSize(1, getTenant());
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
    if (!(obj instanceof io.chirpstack.api.CreateTenantRequest)) {
      return super.equals(obj);
    }
    io.chirpstack.api.CreateTenantRequest other = (io.chirpstack.api.CreateTenantRequest) obj;

    if (hasTenant() != other.hasTenant()) return false;
    if (hasTenant()) {
      if (!getTenant()
          .equals(other.getTenant())) return false;
    }
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
    if (hasTenant()) {
      hash = (37 * hash) + TENANT_FIELD_NUMBER;
      hash = (53 * hash) + getTenant().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static io.chirpstack.api.CreateTenantRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.CreateTenantRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.CreateTenantRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.CreateTenantRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.CreateTenantRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.CreateTenantRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.CreateTenantRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static io.chirpstack.api.CreateTenantRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static io.chirpstack.api.CreateTenantRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static io.chirpstack.api.CreateTenantRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.chirpstack.api.CreateTenantRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static io.chirpstack.api.CreateTenantRequest parseFrom(
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
  public static Builder newBuilder(io.chirpstack.api.CreateTenantRequest prototype) {
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
   * Protobuf type {@code api.CreateTenantRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:api.CreateTenantRequest)
      io.chirpstack.api.CreateTenantRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return io.chirpstack.api.TenantProto.internal_static_api_CreateTenantRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return io.chirpstack.api.TenantProto.internal_static_api_CreateTenantRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              io.chirpstack.api.CreateTenantRequest.class, io.chirpstack.api.CreateTenantRequest.Builder.class);
    }

    // Construct using io.chirpstack.api.CreateTenantRequest.newBuilder()
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
        getTenantFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      tenant_ = null;
      if (tenantBuilder_ != null) {
        tenantBuilder_.dispose();
        tenantBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return io.chirpstack.api.TenantProto.internal_static_api_CreateTenantRequest_descriptor;
    }

    @java.lang.Override
    public io.chirpstack.api.CreateTenantRequest getDefaultInstanceForType() {
      return io.chirpstack.api.CreateTenantRequest.getDefaultInstance();
    }

    @java.lang.Override
    public io.chirpstack.api.CreateTenantRequest build() {
      io.chirpstack.api.CreateTenantRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public io.chirpstack.api.CreateTenantRequest buildPartial() {
      io.chirpstack.api.CreateTenantRequest result = new io.chirpstack.api.CreateTenantRequest(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(io.chirpstack.api.CreateTenantRequest result) {
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.tenant_ = tenantBuilder_ == null
            ? tenant_
            : tenantBuilder_.build();
        to_bitField0_ |= 0x00000001;
      }
      result.bitField0_ |= to_bitField0_;
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof io.chirpstack.api.CreateTenantRequest) {
        return mergeFrom((io.chirpstack.api.CreateTenantRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(io.chirpstack.api.CreateTenantRequest other) {
      if (other == io.chirpstack.api.CreateTenantRequest.getDefaultInstance()) return this;
      if (other.hasTenant()) {
        mergeTenant(other.getTenant());
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
                  getTenantFieldBuilder().getBuilder(),
                  extensionRegistry);
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

    private io.chirpstack.api.Tenant tenant_;
    private com.google.protobuf.SingleFieldBuilder<
        io.chirpstack.api.Tenant, io.chirpstack.api.Tenant.Builder, io.chirpstack.api.TenantOrBuilder> tenantBuilder_;
    /**
     * <pre>
     * Tenant object to create.
     * </pre>
     *
     * <code>.api.Tenant tenant = 1;</code>
     * @return Whether the tenant field is set.
     */
    public boolean hasTenant() {
      return ((bitField0_ & 0x00000001) != 0);
    }
    /**
     * <pre>
     * Tenant object to create.
     * </pre>
     *
     * <code>.api.Tenant tenant = 1;</code>
     * @return The tenant.
     */
    public io.chirpstack.api.Tenant getTenant() {
      if (tenantBuilder_ == null) {
        return tenant_ == null ? io.chirpstack.api.Tenant.getDefaultInstance() : tenant_;
      } else {
        return tenantBuilder_.getMessage();
      }
    }
    /**
     * <pre>
     * Tenant object to create.
     * </pre>
     *
     * <code>.api.Tenant tenant = 1;</code>
     */
    public Builder setTenant(io.chirpstack.api.Tenant value) {
      if (tenantBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        tenant_ = value;
      } else {
        tenantBuilder_.setMessage(value);
      }
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Tenant object to create.
     * </pre>
     *
     * <code>.api.Tenant tenant = 1;</code>
     */
    public Builder setTenant(
        io.chirpstack.api.Tenant.Builder builderForValue) {
      if (tenantBuilder_ == null) {
        tenant_ = builderForValue.build();
      } else {
        tenantBuilder_.setMessage(builderForValue.build());
      }
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Tenant object to create.
     * </pre>
     *
     * <code>.api.Tenant tenant = 1;</code>
     */
    public Builder mergeTenant(io.chirpstack.api.Tenant value) {
      if (tenantBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0) &&
          tenant_ != null &&
          tenant_ != io.chirpstack.api.Tenant.getDefaultInstance()) {
          getTenantBuilder().mergeFrom(value);
        } else {
          tenant_ = value;
        }
      } else {
        tenantBuilder_.mergeFrom(value);
      }
      if (tenant_ != null) {
        bitField0_ |= 0x00000001;
        onChanged();
      }
      return this;
    }
    /**
     * <pre>
     * Tenant object to create.
     * </pre>
     *
     * <code>.api.Tenant tenant = 1;</code>
     */
    public Builder clearTenant() {
      bitField0_ = (bitField0_ & ~0x00000001);
      tenant_ = null;
      if (tenantBuilder_ != null) {
        tenantBuilder_.dispose();
        tenantBuilder_ = null;
      }
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Tenant object to create.
     * </pre>
     *
     * <code>.api.Tenant tenant = 1;</code>
     */
    public io.chirpstack.api.Tenant.Builder getTenantBuilder() {
      bitField0_ |= 0x00000001;
      onChanged();
      return getTenantFieldBuilder().getBuilder();
    }
    /**
     * <pre>
     * Tenant object to create.
     * </pre>
     *
     * <code>.api.Tenant tenant = 1;</code>
     */
    public io.chirpstack.api.TenantOrBuilder getTenantOrBuilder() {
      if (tenantBuilder_ != null) {
        return tenantBuilder_.getMessageOrBuilder();
      } else {
        return tenant_ == null ?
            io.chirpstack.api.Tenant.getDefaultInstance() : tenant_;
      }
    }
    /**
     * <pre>
     * Tenant object to create.
     * </pre>
     *
     * <code>.api.Tenant tenant = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilder<
        io.chirpstack.api.Tenant, io.chirpstack.api.Tenant.Builder, io.chirpstack.api.TenantOrBuilder> 
        getTenantFieldBuilder() {
      if (tenantBuilder_ == null) {
        tenantBuilder_ = new com.google.protobuf.SingleFieldBuilder<
            io.chirpstack.api.Tenant, io.chirpstack.api.Tenant.Builder, io.chirpstack.api.TenantOrBuilder>(
                getTenant(),
                getParentForChildren(),
                isClean());
        tenant_ = null;
      }
      return tenantBuilder_;
    }

    // @@protoc_insertion_point(builder_scope:api.CreateTenantRequest)
  }

  // @@protoc_insertion_point(class_scope:api.CreateTenantRequest)
  private static final io.chirpstack.api.CreateTenantRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new io.chirpstack.api.CreateTenantRequest();
  }

  public static io.chirpstack.api.CreateTenantRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<CreateTenantRequest>
      PARSER = new com.google.protobuf.AbstractParser<CreateTenantRequest>() {
    @java.lang.Override
    public CreateTenantRequest parsePartialFrom(
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

  public static com.google.protobuf.Parser<CreateTenantRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<CreateTenantRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public io.chirpstack.api.CreateTenantRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

