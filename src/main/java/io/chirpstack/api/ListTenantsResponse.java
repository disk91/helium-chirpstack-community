// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: api/tenant.proto
// Protobuf Java Version: 4.29.2

package io.chirpstack.api;

/**
 * Protobuf type {@code api.ListTenantsResponse}
 */
public final class ListTenantsResponse extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:api.ListTenantsResponse)
    ListTenantsResponseOrBuilder {
private static final long serialVersionUID = 0L;
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 29,
      /* patch= */ 2,
      /* suffix= */ "",
      ListTenantsResponse.class.getName());
  }
  // Use ListTenantsResponse.newBuilder() to construct.
  private ListTenantsResponse(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private ListTenantsResponse() {
    result_ = java.util.Collections.emptyList();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return io.chirpstack.api.TenantProto.internal_static_api_ListTenantsResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return io.chirpstack.api.TenantProto.internal_static_api_ListTenantsResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            io.chirpstack.api.ListTenantsResponse.class, io.chirpstack.api.ListTenantsResponse.Builder.class);
  }

  public static final int TOTAL_COUNT_FIELD_NUMBER = 1;
  private int totalCount_ = 0;
  /**
   * <pre>
   * Total number of tenants.
   * </pre>
   *
   * <code>uint32 total_count = 1;</code>
   * @return The totalCount.
   */
  @java.lang.Override
  public int getTotalCount() {
    return totalCount_;
  }

  public static final int RESULT_FIELD_NUMBER = 2;
  @SuppressWarnings("serial")
  private java.util.List<io.chirpstack.api.TenantListItem> result_;
  /**
   * <pre>
   * Result-set.
   * </pre>
   *
   * <code>repeated .api.TenantListItem result = 2;</code>
   */
  @java.lang.Override
  public java.util.List<io.chirpstack.api.TenantListItem> getResultList() {
    return result_;
  }
  /**
   * <pre>
   * Result-set.
   * </pre>
   *
   * <code>repeated .api.TenantListItem result = 2;</code>
   */
  @java.lang.Override
  public java.util.List<? extends io.chirpstack.api.TenantListItemOrBuilder> 
      getResultOrBuilderList() {
    return result_;
  }
  /**
   * <pre>
   * Result-set.
   * </pre>
   *
   * <code>repeated .api.TenantListItem result = 2;</code>
   */
  @java.lang.Override
  public int getResultCount() {
    return result_.size();
  }
  /**
   * <pre>
   * Result-set.
   * </pre>
   *
   * <code>repeated .api.TenantListItem result = 2;</code>
   */
  @java.lang.Override
  public io.chirpstack.api.TenantListItem getResult(int index) {
    return result_.get(index);
  }
  /**
   * <pre>
   * Result-set.
   * </pre>
   *
   * <code>repeated .api.TenantListItem result = 2;</code>
   */
  @java.lang.Override
  public io.chirpstack.api.TenantListItemOrBuilder getResultOrBuilder(
      int index) {
    return result_.get(index);
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
    if (totalCount_ != 0) {
      output.writeUInt32(1, totalCount_);
    }
    for (int i = 0; i < result_.size(); i++) {
      output.writeMessage(2, result_.get(i));
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (totalCount_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt32Size(1, totalCount_);
    }
    for (int i = 0; i < result_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, result_.get(i));
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
    if (!(obj instanceof io.chirpstack.api.ListTenantsResponse)) {
      return super.equals(obj);
    }
    io.chirpstack.api.ListTenantsResponse other = (io.chirpstack.api.ListTenantsResponse) obj;

    if (getTotalCount()
        != other.getTotalCount()) return false;
    if (!getResultList()
        .equals(other.getResultList())) return false;
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
    hash = (37 * hash) + TOTAL_COUNT_FIELD_NUMBER;
    hash = (53 * hash) + getTotalCount();
    if (getResultCount() > 0) {
      hash = (37 * hash) + RESULT_FIELD_NUMBER;
      hash = (53 * hash) + getResultList().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static io.chirpstack.api.ListTenantsResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.ListTenantsResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.ListTenantsResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.ListTenantsResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.ListTenantsResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.ListTenantsResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.ListTenantsResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static io.chirpstack.api.ListTenantsResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static io.chirpstack.api.ListTenantsResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static io.chirpstack.api.ListTenantsResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.chirpstack.api.ListTenantsResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static io.chirpstack.api.ListTenantsResponse parseFrom(
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
  public static Builder newBuilder(io.chirpstack.api.ListTenantsResponse prototype) {
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
   * Protobuf type {@code api.ListTenantsResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:api.ListTenantsResponse)
      io.chirpstack.api.ListTenantsResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return io.chirpstack.api.TenantProto.internal_static_api_ListTenantsResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return io.chirpstack.api.TenantProto.internal_static_api_ListTenantsResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              io.chirpstack.api.ListTenantsResponse.class, io.chirpstack.api.ListTenantsResponse.Builder.class);
    }

    // Construct using io.chirpstack.api.ListTenantsResponse.newBuilder()
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
      totalCount_ = 0;
      if (resultBuilder_ == null) {
        result_ = java.util.Collections.emptyList();
      } else {
        result_ = null;
        resultBuilder_.clear();
      }
      bitField0_ = (bitField0_ & ~0x00000002);
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return io.chirpstack.api.TenantProto.internal_static_api_ListTenantsResponse_descriptor;
    }

    @java.lang.Override
    public io.chirpstack.api.ListTenantsResponse getDefaultInstanceForType() {
      return io.chirpstack.api.ListTenantsResponse.getDefaultInstance();
    }

    @java.lang.Override
    public io.chirpstack.api.ListTenantsResponse build() {
      io.chirpstack.api.ListTenantsResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public io.chirpstack.api.ListTenantsResponse buildPartial() {
      io.chirpstack.api.ListTenantsResponse result = new io.chirpstack.api.ListTenantsResponse(this);
      buildPartialRepeatedFields(result);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartialRepeatedFields(io.chirpstack.api.ListTenantsResponse result) {
      if (resultBuilder_ == null) {
        if (((bitField0_ & 0x00000002) != 0)) {
          result_ = java.util.Collections.unmodifiableList(result_);
          bitField0_ = (bitField0_ & ~0x00000002);
        }
        result.result_ = result_;
      } else {
        result.result_ = resultBuilder_.build();
      }
    }

    private void buildPartial0(io.chirpstack.api.ListTenantsResponse result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.totalCount_ = totalCount_;
      }
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof io.chirpstack.api.ListTenantsResponse) {
        return mergeFrom((io.chirpstack.api.ListTenantsResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(io.chirpstack.api.ListTenantsResponse other) {
      if (other == io.chirpstack.api.ListTenantsResponse.getDefaultInstance()) return this;
      if (other.getTotalCount() != 0) {
        setTotalCount(other.getTotalCount());
      }
      if (resultBuilder_ == null) {
        if (!other.result_.isEmpty()) {
          if (result_.isEmpty()) {
            result_ = other.result_;
            bitField0_ = (bitField0_ & ~0x00000002);
          } else {
            ensureResultIsMutable();
            result_.addAll(other.result_);
          }
          onChanged();
        }
      } else {
        if (!other.result_.isEmpty()) {
          if (resultBuilder_.isEmpty()) {
            resultBuilder_.dispose();
            resultBuilder_ = null;
            result_ = other.result_;
            bitField0_ = (bitField0_ & ~0x00000002);
            resultBuilder_ = 
              com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders ?
                 getResultFieldBuilder() : null;
          } else {
            resultBuilder_.addAllMessages(other.result_);
          }
        }
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
              totalCount_ = input.readUInt32();
              bitField0_ |= 0x00000001;
              break;
            } // case 8
            case 18: {
              io.chirpstack.api.TenantListItem m =
                  input.readMessage(
                      io.chirpstack.api.TenantListItem.parser(),
                      extensionRegistry);
              if (resultBuilder_ == null) {
                ensureResultIsMutable();
                result_.add(m);
              } else {
                resultBuilder_.addMessage(m);
              }
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

    private int totalCount_ ;
    /**
     * <pre>
     * Total number of tenants.
     * </pre>
     *
     * <code>uint32 total_count = 1;</code>
     * @return The totalCount.
     */
    @java.lang.Override
    public int getTotalCount() {
      return totalCount_;
    }
    /**
     * <pre>
     * Total number of tenants.
     * </pre>
     *
     * <code>uint32 total_count = 1;</code>
     * @param value The totalCount to set.
     * @return This builder for chaining.
     */
    public Builder setTotalCount(int value) {

      totalCount_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Total number of tenants.
     * </pre>
     *
     * <code>uint32 total_count = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearTotalCount() {
      bitField0_ = (bitField0_ & ~0x00000001);
      totalCount_ = 0;
      onChanged();
      return this;
    }

    private java.util.List<io.chirpstack.api.TenantListItem> result_ =
      java.util.Collections.emptyList();
    private void ensureResultIsMutable() {
      if (!((bitField0_ & 0x00000002) != 0)) {
        result_ = new java.util.ArrayList<io.chirpstack.api.TenantListItem>(result_);
        bitField0_ |= 0x00000002;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilder<
        io.chirpstack.api.TenantListItem, io.chirpstack.api.TenantListItem.Builder, io.chirpstack.api.TenantListItemOrBuilder> resultBuilder_;

    /**
     * <pre>
     * Result-set.
     * </pre>
     *
     * <code>repeated .api.TenantListItem result = 2;</code>
     */
    public java.util.List<io.chirpstack.api.TenantListItem> getResultList() {
      if (resultBuilder_ == null) {
        return java.util.Collections.unmodifiableList(result_);
      } else {
        return resultBuilder_.getMessageList();
      }
    }
    /**
     * <pre>
     * Result-set.
     * </pre>
     *
     * <code>repeated .api.TenantListItem result = 2;</code>
     */
    public int getResultCount() {
      if (resultBuilder_ == null) {
        return result_.size();
      } else {
        return resultBuilder_.getCount();
      }
    }
    /**
     * <pre>
     * Result-set.
     * </pre>
     *
     * <code>repeated .api.TenantListItem result = 2;</code>
     */
    public io.chirpstack.api.TenantListItem getResult(int index) {
      if (resultBuilder_ == null) {
        return result_.get(index);
      } else {
        return resultBuilder_.getMessage(index);
      }
    }
    /**
     * <pre>
     * Result-set.
     * </pre>
     *
     * <code>repeated .api.TenantListItem result = 2;</code>
     */
    public Builder setResult(
        int index, io.chirpstack.api.TenantListItem value) {
      if (resultBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureResultIsMutable();
        result_.set(index, value);
        onChanged();
      } else {
        resultBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <pre>
     * Result-set.
     * </pre>
     *
     * <code>repeated .api.TenantListItem result = 2;</code>
     */
    public Builder setResult(
        int index, io.chirpstack.api.TenantListItem.Builder builderForValue) {
      if (resultBuilder_ == null) {
        ensureResultIsMutable();
        result_.set(index, builderForValue.build());
        onChanged();
      } else {
        resultBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <pre>
     * Result-set.
     * </pre>
     *
     * <code>repeated .api.TenantListItem result = 2;</code>
     */
    public Builder addResult(io.chirpstack.api.TenantListItem value) {
      if (resultBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureResultIsMutable();
        result_.add(value);
        onChanged();
      } else {
        resultBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <pre>
     * Result-set.
     * </pre>
     *
     * <code>repeated .api.TenantListItem result = 2;</code>
     */
    public Builder addResult(
        int index, io.chirpstack.api.TenantListItem value) {
      if (resultBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureResultIsMutable();
        result_.add(index, value);
        onChanged();
      } else {
        resultBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <pre>
     * Result-set.
     * </pre>
     *
     * <code>repeated .api.TenantListItem result = 2;</code>
     */
    public Builder addResult(
        io.chirpstack.api.TenantListItem.Builder builderForValue) {
      if (resultBuilder_ == null) {
        ensureResultIsMutable();
        result_.add(builderForValue.build());
        onChanged();
      } else {
        resultBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <pre>
     * Result-set.
     * </pre>
     *
     * <code>repeated .api.TenantListItem result = 2;</code>
     */
    public Builder addResult(
        int index, io.chirpstack.api.TenantListItem.Builder builderForValue) {
      if (resultBuilder_ == null) {
        ensureResultIsMutable();
        result_.add(index, builderForValue.build());
        onChanged();
      } else {
        resultBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <pre>
     * Result-set.
     * </pre>
     *
     * <code>repeated .api.TenantListItem result = 2;</code>
     */
    public Builder addAllResult(
        java.lang.Iterable<? extends io.chirpstack.api.TenantListItem> values) {
      if (resultBuilder_ == null) {
        ensureResultIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, result_);
        onChanged();
      } else {
        resultBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <pre>
     * Result-set.
     * </pre>
     *
     * <code>repeated .api.TenantListItem result = 2;</code>
     */
    public Builder clearResult() {
      if (resultBuilder_ == null) {
        result_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000002);
        onChanged();
      } else {
        resultBuilder_.clear();
      }
      return this;
    }
    /**
     * <pre>
     * Result-set.
     * </pre>
     *
     * <code>repeated .api.TenantListItem result = 2;</code>
     */
    public Builder removeResult(int index) {
      if (resultBuilder_ == null) {
        ensureResultIsMutable();
        result_.remove(index);
        onChanged();
      } else {
        resultBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <pre>
     * Result-set.
     * </pre>
     *
     * <code>repeated .api.TenantListItem result = 2;</code>
     */
    public io.chirpstack.api.TenantListItem.Builder getResultBuilder(
        int index) {
      return getResultFieldBuilder().getBuilder(index);
    }
    /**
     * <pre>
     * Result-set.
     * </pre>
     *
     * <code>repeated .api.TenantListItem result = 2;</code>
     */
    public io.chirpstack.api.TenantListItemOrBuilder getResultOrBuilder(
        int index) {
      if (resultBuilder_ == null) {
        return result_.get(index);  } else {
        return resultBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <pre>
     * Result-set.
     * </pre>
     *
     * <code>repeated .api.TenantListItem result = 2;</code>
     */
    public java.util.List<? extends io.chirpstack.api.TenantListItemOrBuilder> 
         getResultOrBuilderList() {
      if (resultBuilder_ != null) {
        return resultBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(result_);
      }
    }
    /**
     * <pre>
     * Result-set.
     * </pre>
     *
     * <code>repeated .api.TenantListItem result = 2;</code>
     */
    public io.chirpstack.api.TenantListItem.Builder addResultBuilder() {
      return getResultFieldBuilder().addBuilder(
          io.chirpstack.api.TenantListItem.getDefaultInstance());
    }
    /**
     * <pre>
     * Result-set.
     * </pre>
     *
     * <code>repeated .api.TenantListItem result = 2;</code>
     */
    public io.chirpstack.api.TenantListItem.Builder addResultBuilder(
        int index) {
      return getResultFieldBuilder().addBuilder(
          index, io.chirpstack.api.TenantListItem.getDefaultInstance());
    }
    /**
     * <pre>
     * Result-set.
     * </pre>
     *
     * <code>repeated .api.TenantListItem result = 2;</code>
     */
    public java.util.List<io.chirpstack.api.TenantListItem.Builder> 
         getResultBuilderList() {
      return getResultFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilder<
        io.chirpstack.api.TenantListItem, io.chirpstack.api.TenantListItem.Builder, io.chirpstack.api.TenantListItemOrBuilder> 
        getResultFieldBuilder() {
      if (resultBuilder_ == null) {
        resultBuilder_ = new com.google.protobuf.RepeatedFieldBuilder<
            io.chirpstack.api.TenantListItem, io.chirpstack.api.TenantListItem.Builder, io.chirpstack.api.TenantListItemOrBuilder>(
                result_,
                ((bitField0_ & 0x00000002) != 0),
                getParentForChildren(),
                isClean());
        result_ = null;
      }
      return resultBuilder_;
    }

    // @@protoc_insertion_point(builder_scope:api.ListTenantsResponse)
  }

  // @@protoc_insertion_point(class_scope:api.ListTenantsResponse)
  private static final io.chirpstack.api.ListTenantsResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new io.chirpstack.api.ListTenantsResponse();
  }

  public static io.chirpstack.api.ListTenantsResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ListTenantsResponse>
      PARSER = new com.google.protobuf.AbstractParser<ListTenantsResponse>() {
    @java.lang.Override
    public ListTenantsResponse parsePartialFrom(
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

  public static com.google.protobuf.Parser<ListTenantsResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ListTenantsResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public io.chirpstack.api.ListTenantsResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

