// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: api/internal.proto

// Protobuf Java Version: 3.25.1
package io.chirpstack.api;

/**
 * Protobuf type {@code api.ListRegionsResponse}
 */
public final class ListRegionsResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:api.ListRegionsResponse)
    ListRegionsResponseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use ListRegionsResponse.newBuilder() to construct.
  private ListRegionsResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ListRegionsResponse() {
    regions_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new ListRegionsResponse();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return io.chirpstack.api.InternalProto.internal_static_api_ListRegionsResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return io.chirpstack.api.InternalProto.internal_static_api_ListRegionsResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            io.chirpstack.api.ListRegionsResponse.class, io.chirpstack.api.ListRegionsResponse.Builder.class);
  }

  public static final int REGIONS_FIELD_NUMBER = 1;
  @SuppressWarnings("serial")
  private java.util.List<io.chirpstack.api.RegionListItem> regions_;
  /**
   * <pre>
   * Configured regions.
   * </pre>
   *
   * <code>repeated .api.RegionListItem regions = 1;</code>
   */
  @java.lang.Override
  public java.util.List<io.chirpstack.api.RegionListItem> getRegionsList() {
    return regions_;
  }
  /**
   * <pre>
   * Configured regions.
   * </pre>
   *
   * <code>repeated .api.RegionListItem regions = 1;</code>
   */
  @java.lang.Override
  public java.util.List<? extends io.chirpstack.api.RegionListItemOrBuilder> 
      getRegionsOrBuilderList() {
    return regions_;
  }
  /**
   * <pre>
   * Configured regions.
   * </pre>
   *
   * <code>repeated .api.RegionListItem regions = 1;</code>
   */
  @java.lang.Override
  public int getRegionsCount() {
    return regions_.size();
  }
  /**
   * <pre>
   * Configured regions.
   * </pre>
   *
   * <code>repeated .api.RegionListItem regions = 1;</code>
   */
  @java.lang.Override
  public io.chirpstack.api.RegionListItem getRegions(int index) {
    return regions_.get(index);
  }
  /**
   * <pre>
   * Configured regions.
   * </pre>
   *
   * <code>repeated .api.RegionListItem regions = 1;</code>
   */
  @java.lang.Override
  public io.chirpstack.api.RegionListItemOrBuilder getRegionsOrBuilder(
      int index) {
    return regions_.get(index);
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
    for (int i = 0; i < regions_.size(); i++) {
      output.writeMessage(1, regions_.get(i));
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < regions_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, regions_.get(i));
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
    if (!(obj instanceof io.chirpstack.api.ListRegionsResponse)) {
      return super.equals(obj);
    }
    io.chirpstack.api.ListRegionsResponse other = (io.chirpstack.api.ListRegionsResponse) obj;

    if (!getRegionsList()
        .equals(other.getRegionsList())) return false;
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
    if (getRegionsCount() > 0) {
      hash = (37 * hash) + REGIONS_FIELD_NUMBER;
      hash = (53 * hash) + getRegionsList().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static io.chirpstack.api.ListRegionsResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.ListRegionsResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.ListRegionsResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.ListRegionsResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.ListRegionsResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.ListRegionsResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.ListRegionsResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static io.chirpstack.api.ListRegionsResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static io.chirpstack.api.ListRegionsResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static io.chirpstack.api.ListRegionsResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.chirpstack.api.ListRegionsResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static io.chirpstack.api.ListRegionsResponse parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(io.chirpstack.api.ListRegionsResponse prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code api.ListRegionsResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:api.ListRegionsResponse)
      io.chirpstack.api.ListRegionsResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return io.chirpstack.api.InternalProto.internal_static_api_ListRegionsResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return io.chirpstack.api.InternalProto.internal_static_api_ListRegionsResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              io.chirpstack.api.ListRegionsResponse.class, io.chirpstack.api.ListRegionsResponse.Builder.class);
    }

    // Construct using io.chirpstack.api.ListRegionsResponse.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      if (regionsBuilder_ == null) {
        regions_ = java.util.Collections.emptyList();
      } else {
        regions_ = null;
        regionsBuilder_.clear();
      }
      bitField0_ = (bitField0_ & ~0x00000001);
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return io.chirpstack.api.InternalProto.internal_static_api_ListRegionsResponse_descriptor;
    }

    @java.lang.Override
    public io.chirpstack.api.ListRegionsResponse getDefaultInstanceForType() {
      return io.chirpstack.api.ListRegionsResponse.getDefaultInstance();
    }

    @java.lang.Override
    public io.chirpstack.api.ListRegionsResponse build() {
      io.chirpstack.api.ListRegionsResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public io.chirpstack.api.ListRegionsResponse buildPartial() {
      io.chirpstack.api.ListRegionsResponse result = new io.chirpstack.api.ListRegionsResponse(this);
      buildPartialRepeatedFields(result);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartialRepeatedFields(io.chirpstack.api.ListRegionsResponse result) {
      if (regionsBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          regions_ = java.util.Collections.unmodifiableList(regions_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.regions_ = regions_;
      } else {
        result.regions_ = regionsBuilder_.build();
      }
    }

    private void buildPartial0(io.chirpstack.api.ListRegionsResponse result) {
      int from_bitField0_ = bitField0_;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof io.chirpstack.api.ListRegionsResponse) {
        return mergeFrom((io.chirpstack.api.ListRegionsResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(io.chirpstack.api.ListRegionsResponse other) {
      if (other == io.chirpstack.api.ListRegionsResponse.getDefaultInstance()) return this;
      if (regionsBuilder_ == null) {
        if (!other.regions_.isEmpty()) {
          if (regions_.isEmpty()) {
            regions_ = other.regions_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureRegionsIsMutable();
            regions_.addAll(other.regions_);
          }
          onChanged();
        }
      } else {
        if (!other.regions_.isEmpty()) {
          if (regionsBuilder_.isEmpty()) {
            regionsBuilder_.dispose();
            regionsBuilder_ = null;
            regions_ = other.regions_;
            bitField0_ = (bitField0_ & ~0x00000001);
            regionsBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getRegionsFieldBuilder() : null;
          } else {
            regionsBuilder_.addAllMessages(other.regions_);
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
            case 10: {
              io.chirpstack.api.RegionListItem m =
                  input.readMessage(
                      io.chirpstack.api.RegionListItem.parser(),
                      extensionRegistry);
              if (regionsBuilder_ == null) {
                ensureRegionsIsMutable();
                regions_.add(m);
              } else {
                regionsBuilder_.addMessage(m);
              }
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

    private java.util.List<io.chirpstack.api.RegionListItem> regions_ =
      java.util.Collections.emptyList();
    private void ensureRegionsIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        regions_ = new java.util.ArrayList<io.chirpstack.api.RegionListItem>(regions_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        io.chirpstack.api.RegionListItem, io.chirpstack.api.RegionListItem.Builder, io.chirpstack.api.RegionListItemOrBuilder> regionsBuilder_;

    /**
     * <pre>
     * Configured regions.
     * </pre>
     *
     * <code>repeated .api.RegionListItem regions = 1;</code>
     */
    public java.util.List<io.chirpstack.api.RegionListItem> getRegionsList() {
      if (regionsBuilder_ == null) {
        return java.util.Collections.unmodifiableList(regions_);
      } else {
        return regionsBuilder_.getMessageList();
      }
    }
    /**
     * <pre>
     * Configured regions.
     * </pre>
     *
     * <code>repeated .api.RegionListItem regions = 1;</code>
     */
    public int getRegionsCount() {
      if (regionsBuilder_ == null) {
        return regions_.size();
      } else {
        return regionsBuilder_.getCount();
      }
    }
    /**
     * <pre>
     * Configured regions.
     * </pre>
     *
     * <code>repeated .api.RegionListItem regions = 1;</code>
     */
    public io.chirpstack.api.RegionListItem getRegions(int index) {
      if (regionsBuilder_ == null) {
        return regions_.get(index);
      } else {
        return regionsBuilder_.getMessage(index);
      }
    }
    /**
     * <pre>
     * Configured regions.
     * </pre>
     *
     * <code>repeated .api.RegionListItem regions = 1;</code>
     */
    public Builder setRegions(
        int index, io.chirpstack.api.RegionListItem value) {
      if (regionsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureRegionsIsMutable();
        regions_.set(index, value);
        onChanged();
      } else {
        regionsBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <pre>
     * Configured regions.
     * </pre>
     *
     * <code>repeated .api.RegionListItem regions = 1;</code>
     */
    public Builder setRegions(
        int index, io.chirpstack.api.RegionListItem.Builder builderForValue) {
      if (regionsBuilder_ == null) {
        ensureRegionsIsMutable();
        regions_.set(index, builderForValue.build());
        onChanged();
      } else {
        regionsBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <pre>
     * Configured regions.
     * </pre>
     *
     * <code>repeated .api.RegionListItem regions = 1;</code>
     */
    public Builder addRegions(io.chirpstack.api.RegionListItem value) {
      if (regionsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureRegionsIsMutable();
        regions_.add(value);
        onChanged();
      } else {
        regionsBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <pre>
     * Configured regions.
     * </pre>
     *
     * <code>repeated .api.RegionListItem regions = 1;</code>
     */
    public Builder addRegions(
        int index, io.chirpstack.api.RegionListItem value) {
      if (regionsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureRegionsIsMutable();
        regions_.add(index, value);
        onChanged();
      } else {
        regionsBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <pre>
     * Configured regions.
     * </pre>
     *
     * <code>repeated .api.RegionListItem regions = 1;</code>
     */
    public Builder addRegions(
        io.chirpstack.api.RegionListItem.Builder builderForValue) {
      if (regionsBuilder_ == null) {
        ensureRegionsIsMutable();
        regions_.add(builderForValue.build());
        onChanged();
      } else {
        regionsBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <pre>
     * Configured regions.
     * </pre>
     *
     * <code>repeated .api.RegionListItem regions = 1;</code>
     */
    public Builder addRegions(
        int index, io.chirpstack.api.RegionListItem.Builder builderForValue) {
      if (regionsBuilder_ == null) {
        ensureRegionsIsMutable();
        regions_.add(index, builderForValue.build());
        onChanged();
      } else {
        regionsBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <pre>
     * Configured regions.
     * </pre>
     *
     * <code>repeated .api.RegionListItem regions = 1;</code>
     */
    public Builder addAllRegions(
        java.lang.Iterable<? extends io.chirpstack.api.RegionListItem> values) {
      if (regionsBuilder_ == null) {
        ensureRegionsIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, regions_);
        onChanged();
      } else {
        regionsBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <pre>
     * Configured regions.
     * </pre>
     *
     * <code>repeated .api.RegionListItem regions = 1;</code>
     */
    public Builder clearRegions() {
      if (regionsBuilder_ == null) {
        regions_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        regionsBuilder_.clear();
      }
      return this;
    }
    /**
     * <pre>
     * Configured regions.
     * </pre>
     *
     * <code>repeated .api.RegionListItem regions = 1;</code>
     */
    public Builder removeRegions(int index) {
      if (regionsBuilder_ == null) {
        ensureRegionsIsMutable();
        regions_.remove(index);
        onChanged();
      } else {
        regionsBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <pre>
     * Configured regions.
     * </pre>
     *
     * <code>repeated .api.RegionListItem regions = 1;</code>
     */
    public io.chirpstack.api.RegionListItem.Builder getRegionsBuilder(
        int index) {
      return getRegionsFieldBuilder().getBuilder(index);
    }
    /**
     * <pre>
     * Configured regions.
     * </pre>
     *
     * <code>repeated .api.RegionListItem regions = 1;</code>
     */
    public io.chirpstack.api.RegionListItemOrBuilder getRegionsOrBuilder(
        int index) {
      if (regionsBuilder_ == null) {
        return regions_.get(index);  } else {
        return regionsBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <pre>
     * Configured regions.
     * </pre>
     *
     * <code>repeated .api.RegionListItem regions = 1;</code>
     */
    public java.util.List<? extends io.chirpstack.api.RegionListItemOrBuilder> 
         getRegionsOrBuilderList() {
      if (regionsBuilder_ != null) {
        return regionsBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(regions_);
      }
    }
    /**
     * <pre>
     * Configured regions.
     * </pre>
     *
     * <code>repeated .api.RegionListItem regions = 1;</code>
     */
    public io.chirpstack.api.RegionListItem.Builder addRegionsBuilder() {
      return getRegionsFieldBuilder().addBuilder(
          io.chirpstack.api.RegionListItem.getDefaultInstance());
    }
    /**
     * <pre>
     * Configured regions.
     * </pre>
     *
     * <code>repeated .api.RegionListItem regions = 1;</code>
     */
    public io.chirpstack.api.RegionListItem.Builder addRegionsBuilder(
        int index) {
      return getRegionsFieldBuilder().addBuilder(
          index, io.chirpstack.api.RegionListItem.getDefaultInstance());
    }
    /**
     * <pre>
     * Configured regions.
     * </pre>
     *
     * <code>repeated .api.RegionListItem regions = 1;</code>
     */
    public java.util.List<io.chirpstack.api.RegionListItem.Builder> 
         getRegionsBuilderList() {
      return getRegionsFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        io.chirpstack.api.RegionListItem, io.chirpstack.api.RegionListItem.Builder, io.chirpstack.api.RegionListItemOrBuilder> 
        getRegionsFieldBuilder() {
      if (regionsBuilder_ == null) {
        regionsBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            io.chirpstack.api.RegionListItem, io.chirpstack.api.RegionListItem.Builder, io.chirpstack.api.RegionListItemOrBuilder>(
                regions_,
                ((bitField0_ & 0x00000001) != 0),
                getParentForChildren(),
                isClean());
        regions_ = null;
      }
      return regionsBuilder_;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:api.ListRegionsResponse)
  }

  // @@protoc_insertion_point(class_scope:api.ListRegionsResponse)
  private static final io.chirpstack.api.ListRegionsResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new io.chirpstack.api.ListRegionsResponse();
  }

  public static io.chirpstack.api.ListRegionsResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ListRegionsResponse>
      PARSER = new com.google.protobuf.AbstractParser<ListRegionsResponse>() {
    @java.lang.Override
    public ListRegionsResponse parsePartialFrom(
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

  public static com.google.protobuf.Parser<ListRegionsResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ListRegionsResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public io.chirpstack.api.ListRegionsResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

