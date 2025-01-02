// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: api/tenant.proto
// Protobuf Java Version: 4.29.2

package io.chirpstack.api;

/**
 * Protobuf type {@code api.ListTenantsRequest}
 */
public final class ListTenantsRequest extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:api.ListTenantsRequest)
    ListTenantsRequestOrBuilder {
private static final long serialVersionUID = 0L;
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 29,
      /* patch= */ 2,
      /* suffix= */ "",
      ListTenantsRequest.class.getName());
  }
  // Use ListTenantsRequest.newBuilder() to construct.
  private ListTenantsRequest(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private ListTenantsRequest() {
    search_ = "";
    userId_ = "";
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return io.chirpstack.api.TenantProto.internal_static_api_ListTenantsRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return io.chirpstack.api.TenantProto.internal_static_api_ListTenantsRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            io.chirpstack.api.ListTenantsRequest.class, io.chirpstack.api.ListTenantsRequest.Builder.class);
  }

  public static final int LIMIT_FIELD_NUMBER = 1;
  private int limit_ = 0;
  /**
   * <pre>
   * Max number of tenants to return in the result-set.
   * </pre>
   *
   * <code>uint32 limit = 1;</code>
   * @return The limit.
   */
  @java.lang.Override
  public int getLimit() {
    return limit_;
  }

  public static final int OFFSET_FIELD_NUMBER = 2;
  private int offset_ = 0;
  /**
   * <pre>
   * Offset in the result-set (for pagination).
   * </pre>
   *
   * <code>uint32 offset = 2;</code>
   * @return The offset.
   */
  @java.lang.Override
  public int getOffset() {
    return offset_;
  }

  public static final int SEARCH_FIELD_NUMBER = 3;
  @SuppressWarnings("serial")
  private volatile java.lang.Object search_ = "";
  /**
   * <pre>
   * If set, the given string will be used to search on name.
   * </pre>
   *
   * <code>string search = 3;</code>
   * @return The search.
   */
  @java.lang.Override
  public java.lang.String getSearch() {
    java.lang.Object ref = search_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      search_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * If set, the given string will be used to search on name.
   * </pre>
   *
   * <code>string search = 3;</code>
   * @return The bytes for search.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getSearchBytes() {
    java.lang.Object ref = search_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      search_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int USER_ID_FIELD_NUMBER = 4;
  @SuppressWarnings("serial")
  private volatile java.lang.Object userId_ = "";
  /**
   * <pre>
   * If set, filters the result set to the tenants of the user.
   * Only global API keys are able to filter by this field.
   * </pre>
   *
   * <code>string user_id = 4;</code>
   * @return The userId.
   */
  @java.lang.Override
  public java.lang.String getUserId() {
    java.lang.Object ref = userId_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      userId_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * If set, filters the result set to the tenants of the user.
   * Only global API keys are able to filter by this field.
   * </pre>
   *
   * <code>string user_id = 4;</code>
   * @return The bytes for userId.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getUserIdBytes() {
    java.lang.Object ref = userId_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      userId_ = b;
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
    if (limit_ != 0) {
      output.writeUInt32(1, limit_);
    }
    if (offset_ != 0) {
      output.writeUInt32(2, offset_);
    }
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(search_)) {
      com.google.protobuf.GeneratedMessage.writeString(output, 3, search_);
    }
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(userId_)) {
      com.google.protobuf.GeneratedMessage.writeString(output, 4, userId_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (limit_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt32Size(1, limit_);
    }
    if (offset_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt32Size(2, offset_);
    }
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(search_)) {
      size += com.google.protobuf.GeneratedMessage.computeStringSize(3, search_);
    }
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(userId_)) {
      size += com.google.protobuf.GeneratedMessage.computeStringSize(4, userId_);
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
    if (!(obj instanceof io.chirpstack.api.ListTenantsRequest)) {
      return super.equals(obj);
    }
    io.chirpstack.api.ListTenantsRequest other = (io.chirpstack.api.ListTenantsRequest) obj;

    if (getLimit()
        != other.getLimit()) return false;
    if (getOffset()
        != other.getOffset()) return false;
    if (!getSearch()
        .equals(other.getSearch())) return false;
    if (!getUserId()
        .equals(other.getUserId())) return false;
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
    hash = (37 * hash) + LIMIT_FIELD_NUMBER;
    hash = (53 * hash) + getLimit();
    hash = (37 * hash) + OFFSET_FIELD_NUMBER;
    hash = (53 * hash) + getOffset();
    hash = (37 * hash) + SEARCH_FIELD_NUMBER;
    hash = (53 * hash) + getSearch().hashCode();
    hash = (37 * hash) + USER_ID_FIELD_NUMBER;
    hash = (53 * hash) + getUserId().hashCode();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static io.chirpstack.api.ListTenantsRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.ListTenantsRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.ListTenantsRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.ListTenantsRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.ListTenantsRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.ListTenantsRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.ListTenantsRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static io.chirpstack.api.ListTenantsRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static io.chirpstack.api.ListTenantsRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static io.chirpstack.api.ListTenantsRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.chirpstack.api.ListTenantsRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static io.chirpstack.api.ListTenantsRequest parseFrom(
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
  public static Builder newBuilder(io.chirpstack.api.ListTenantsRequest prototype) {
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
   * Protobuf type {@code api.ListTenantsRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:api.ListTenantsRequest)
      io.chirpstack.api.ListTenantsRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return io.chirpstack.api.TenantProto.internal_static_api_ListTenantsRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return io.chirpstack.api.TenantProto.internal_static_api_ListTenantsRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              io.chirpstack.api.ListTenantsRequest.class, io.chirpstack.api.ListTenantsRequest.Builder.class);
    }

    // Construct using io.chirpstack.api.ListTenantsRequest.newBuilder()
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
      limit_ = 0;
      offset_ = 0;
      search_ = "";
      userId_ = "";
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return io.chirpstack.api.TenantProto.internal_static_api_ListTenantsRequest_descriptor;
    }

    @java.lang.Override
    public io.chirpstack.api.ListTenantsRequest getDefaultInstanceForType() {
      return io.chirpstack.api.ListTenantsRequest.getDefaultInstance();
    }

    @java.lang.Override
    public io.chirpstack.api.ListTenantsRequest build() {
      io.chirpstack.api.ListTenantsRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public io.chirpstack.api.ListTenantsRequest buildPartial() {
      io.chirpstack.api.ListTenantsRequest result = new io.chirpstack.api.ListTenantsRequest(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(io.chirpstack.api.ListTenantsRequest result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.limit_ = limit_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.offset_ = offset_;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.search_ = search_;
      }
      if (((from_bitField0_ & 0x00000008) != 0)) {
        result.userId_ = userId_;
      }
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof io.chirpstack.api.ListTenantsRequest) {
        return mergeFrom((io.chirpstack.api.ListTenantsRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(io.chirpstack.api.ListTenantsRequest other) {
      if (other == io.chirpstack.api.ListTenantsRequest.getDefaultInstance()) return this;
      if (other.getLimit() != 0) {
        setLimit(other.getLimit());
      }
      if (other.getOffset() != 0) {
        setOffset(other.getOffset());
      }
      if (!other.getSearch().isEmpty()) {
        search_ = other.search_;
        bitField0_ |= 0x00000004;
        onChanged();
      }
      if (!other.getUserId().isEmpty()) {
        userId_ = other.userId_;
        bitField0_ |= 0x00000008;
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
              limit_ = input.readUInt32();
              bitField0_ |= 0x00000001;
              break;
            } // case 8
            case 16: {
              offset_ = input.readUInt32();
              bitField0_ |= 0x00000002;
              break;
            } // case 16
            case 26: {
              search_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000004;
              break;
            } // case 26
            case 34: {
              userId_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000008;
              break;
            } // case 34
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

    private int limit_ ;
    /**
     * <pre>
     * Max number of tenants to return in the result-set.
     * </pre>
     *
     * <code>uint32 limit = 1;</code>
     * @return The limit.
     */
    @java.lang.Override
    public int getLimit() {
      return limit_;
    }
    /**
     * <pre>
     * Max number of tenants to return in the result-set.
     * </pre>
     *
     * <code>uint32 limit = 1;</code>
     * @param value The limit to set.
     * @return This builder for chaining.
     */
    public Builder setLimit(int value) {

      limit_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Max number of tenants to return in the result-set.
     * </pre>
     *
     * <code>uint32 limit = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearLimit() {
      bitField0_ = (bitField0_ & ~0x00000001);
      limit_ = 0;
      onChanged();
      return this;
    }

    private int offset_ ;
    /**
     * <pre>
     * Offset in the result-set (for pagination).
     * </pre>
     *
     * <code>uint32 offset = 2;</code>
     * @return The offset.
     */
    @java.lang.Override
    public int getOffset() {
      return offset_;
    }
    /**
     * <pre>
     * Offset in the result-set (for pagination).
     * </pre>
     *
     * <code>uint32 offset = 2;</code>
     * @param value The offset to set.
     * @return This builder for chaining.
     */
    public Builder setOffset(int value) {

      offset_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Offset in the result-set (for pagination).
     * </pre>
     *
     * <code>uint32 offset = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearOffset() {
      bitField0_ = (bitField0_ & ~0x00000002);
      offset_ = 0;
      onChanged();
      return this;
    }

    private java.lang.Object search_ = "";
    /**
     * <pre>
     * If set, the given string will be used to search on name.
     * </pre>
     *
     * <code>string search = 3;</code>
     * @return The search.
     */
    public java.lang.String getSearch() {
      java.lang.Object ref = search_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        search_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     * If set, the given string will be used to search on name.
     * </pre>
     *
     * <code>string search = 3;</code>
     * @return The bytes for search.
     */
    public com.google.protobuf.ByteString
        getSearchBytes() {
      java.lang.Object ref = search_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        search_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * If set, the given string will be used to search on name.
     * </pre>
     *
     * <code>string search = 3;</code>
     * @param value The search to set.
     * @return This builder for chaining.
     */
    public Builder setSearch(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      search_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * If set, the given string will be used to search on name.
     * </pre>
     *
     * <code>string search = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearSearch() {
      search_ = getDefaultInstance().getSearch();
      bitField0_ = (bitField0_ & ~0x00000004);
      onChanged();
      return this;
    }
    /**
     * <pre>
     * If set, the given string will be used to search on name.
     * </pre>
     *
     * <code>string search = 3;</code>
     * @param value The bytes for search to set.
     * @return This builder for chaining.
     */
    public Builder setSearchBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      search_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }

    private java.lang.Object userId_ = "";
    /**
     * <pre>
     * If set, filters the result set to the tenants of the user.
     * Only global API keys are able to filter by this field.
     * </pre>
     *
     * <code>string user_id = 4;</code>
     * @return The userId.
     */
    public java.lang.String getUserId() {
      java.lang.Object ref = userId_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        userId_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     * If set, filters the result set to the tenants of the user.
     * Only global API keys are able to filter by this field.
     * </pre>
     *
     * <code>string user_id = 4;</code>
     * @return The bytes for userId.
     */
    public com.google.protobuf.ByteString
        getUserIdBytes() {
      java.lang.Object ref = userId_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        userId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * If set, filters the result set to the tenants of the user.
     * Only global API keys are able to filter by this field.
     * </pre>
     *
     * <code>string user_id = 4;</code>
     * @param value The userId to set.
     * @return This builder for chaining.
     */
    public Builder setUserId(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      userId_ = value;
      bitField0_ |= 0x00000008;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * If set, filters the result set to the tenants of the user.
     * Only global API keys are able to filter by this field.
     * </pre>
     *
     * <code>string user_id = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearUserId() {
      userId_ = getDefaultInstance().getUserId();
      bitField0_ = (bitField0_ & ~0x00000008);
      onChanged();
      return this;
    }
    /**
     * <pre>
     * If set, filters the result set to the tenants of the user.
     * Only global API keys are able to filter by this field.
     * </pre>
     *
     * <code>string user_id = 4;</code>
     * @param value The bytes for userId to set.
     * @return This builder for chaining.
     */
    public Builder setUserIdBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      userId_ = value;
      bitField0_ |= 0x00000008;
      onChanged();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:api.ListTenantsRequest)
  }

  // @@protoc_insertion_point(class_scope:api.ListTenantsRequest)
  private static final io.chirpstack.api.ListTenantsRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new io.chirpstack.api.ListTenantsRequest();
  }

  public static io.chirpstack.api.ListTenantsRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ListTenantsRequest>
      PARSER = new com.google.protobuf.AbstractParser<ListTenantsRequest>() {
    @java.lang.Override
    public ListTenantsRequest parsePartialFrom(
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

  public static com.google.protobuf.Parser<ListTenantsRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ListTenantsRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public io.chirpstack.api.ListTenantsRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

