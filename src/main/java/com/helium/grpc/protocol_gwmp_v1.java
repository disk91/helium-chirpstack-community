// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: service/iot_config.proto
// Protobuf Java Version: 4.29.2

package com.helium.grpc;

/**
 * <pre>
 * GWMP protocol options (region to port mapping, see
 * https://github.com/Lora-net/packet_forwarder/blob/master/PROTOCOL.TXT)
 * </pre>
 *
 * Protobuf type {@code helium.iot_config.protocol_gwmp_v1}
 */
public final class protocol_gwmp_v1 extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:helium.iot_config.protocol_gwmp_v1)
    protocol_gwmp_v1OrBuilder {
private static final long serialVersionUID = 0L;
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 29,
      /* patch= */ 2,
      /* suffix= */ "",
      protocol_gwmp_v1.class.getName());
  }
  // Use protocol_gwmp_v1.newBuilder() to construct.
  private protocol_gwmp_v1(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private protocol_gwmp_v1() {
    mapping_ = java.util.Collections.emptyList();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.helium.grpc.IotConfig.internal_static_helium_iot_config_protocol_gwmp_v1_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.helium.grpc.IotConfig.internal_static_helium_iot_config_protocol_gwmp_v1_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.helium.grpc.protocol_gwmp_v1.class, com.helium.grpc.protocol_gwmp_v1.Builder.class);
  }

  public static final int MAPPING_FIELD_NUMBER = 1;
  @SuppressWarnings("serial")
  private java.util.List<com.helium.grpc.protocol_gwmp_mapping_v1> mapping_;
  /**
   * <code>repeated .helium.iot_config.protocol_gwmp_mapping_v1 mapping = 1;</code>
   */
  @java.lang.Override
  public java.util.List<com.helium.grpc.protocol_gwmp_mapping_v1> getMappingList() {
    return mapping_;
  }
  /**
   * <code>repeated .helium.iot_config.protocol_gwmp_mapping_v1 mapping = 1;</code>
   */
  @java.lang.Override
  public java.util.List<? extends com.helium.grpc.protocol_gwmp_mapping_v1OrBuilder> 
      getMappingOrBuilderList() {
    return mapping_;
  }
  /**
   * <code>repeated .helium.iot_config.protocol_gwmp_mapping_v1 mapping = 1;</code>
   */
  @java.lang.Override
  public int getMappingCount() {
    return mapping_.size();
  }
  /**
   * <code>repeated .helium.iot_config.protocol_gwmp_mapping_v1 mapping = 1;</code>
   */
  @java.lang.Override
  public com.helium.grpc.protocol_gwmp_mapping_v1 getMapping(int index) {
    return mapping_.get(index);
  }
  /**
   * <code>repeated .helium.iot_config.protocol_gwmp_mapping_v1 mapping = 1;</code>
   */
  @java.lang.Override
  public com.helium.grpc.protocol_gwmp_mapping_v1OrBuilder getMappingOrBuilder(
      int index) {
    return mapping_.get(index);
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
    for (int i = 0; i < mapping_.size(); i++) {
      output.writeMessage(1, mapping_.get(i));
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < mapping_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, mapping_.get(i));
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
    if (!(obj instanceof com.helium.grpc.protocol_gwmp_v1)) {
      return super.equals(obj);
    }
    com.helium.grpc.protocol_gwmp_v1 other = (com.helium.grpc.protocol_gwmp_v1) obj;

    if (!getMappingList()
        .equals(other.getMappingList())) return false;
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
    if (getMappingCount() > 0) {
      hash = (37 * hash) + MAPPING_FIELD_NUMBER;
      hash = (53 * hash) + getMappingList().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.helium.grpc.protocol_gwmp_v1 parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.helium.grpc.protocol_gwmp_v1 parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.helium.grpc.protocol_gwmp_v1 parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.helium.grpc.protocol_gwmp_v1 parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.helium.grpc.protocol_gwmp_v1 parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.helium.grpc.protocol_gwmp_v1 parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.helium.grpc.protocol_gwmp_v1 parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static com.helium.grpc.protocol_gwmp_v1 parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static com.helium.grpc.protocol_gwmp_v1 parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static com.helium.grpc.protocol_gwmp_v1 parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.helium.grpc.protocol_gwmp_v1 parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static com.helium.grpc.protocol_gwmp_v1 parseFrom(
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
  public static Builder newBuilder(com.helium.grpc.protocol_gwmp_v1 prototype) {
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
   * GWMP protocol options (region to port mapping, see
   * https://github.com/Lora-net/packet_forwarder/blob/master/PROTOCOL.TXT)
   * </pre>
   *
   * Protobuf type {@code helium.iot_config.protocol_gwmp_v1}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:helium.iot_config.protocol_gwmp_v1)
      com.helium.grpc.protocol_gwmp_v1OrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.helium.grpc.IotConfig.internal_static_helium_iot_config_protocol_gwmp_v1_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.helium.grpc.IotConfig.internal_static_helium_iot_config_protocol_gwmp_v1_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.helium.grpc.protocol_gwmp_v1.class, com.helium.grpc.protocol_gwmp_v1.Builder.class);
    }

    // Construct using com.helium.grpc.protocol_gwmp_v1.newBuilder()
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
      if (mappingBuilder_ == null) {
        mapping_ = java.util.Collections.emptyList();
      } else {
        mapping_ = null;
        mappingBuilder_.clear();
      }
      bitField0_ = (bitField0_ & ~0x00000001);
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.helium.grpc.IotConfig.internal_static_helium_iot_config_protocol_gwmp_v1_descriptor;
    }

    @java.lang.Override
    public com.helium.grpc.protocol_gwmp_v1 getDefaultInstanceForType() {
      return com.helium.grpc.protocol_gwmp_v1.getDefaultInstance();
    }

    @java.lang.Override
    public com.helium.grpc.protocol_gwmp_v1 build() {
      com.helium.grpc.protocol_gwmp_v1 result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.helium.grpc.protocol_gwmp_v1 buildPartial() {
      com.helium.grpc.protocol_gwmp_v1 result = new com.helium.grpc.protocol_gwmp_v1(this);
      buildPartialRepeatedFields(result);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartialRepeatedFields(com.helium.grpc.protocol_gwmp_v1 result) {
      if (mappingBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          mapping_ = java.util.Collections.unmodifiableList(mapping_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.mapping_ = mapping_;
      } else {
        result.mapping_ = mappingBuilder_.build();
      }
    }

    private void buildPartial0(com.helium.grpc.protocol_gwmp_v1 result) {
      int from_bitField0_ = bitField0_;
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.helium.grpc.protocol_gwmp_v1) {
        return mergeFrom((com.helium.grpc.protocol_gwmp_v1)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.helium.grpc.protocol_gwmp_v1 other) {
      if (other == com.helium.grpc.protocol_gwmp_v1.getDefaultInstance()) return this;
      if (mappingBuilder_ == null) {
        if (!other.mapping_.isEmpty()) {
          if (mapping_.isEmpty()) {
            mapping_ = other.mapping_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureMappingIsMutable();
            mapping_.addAll(other.mapping_);
          }
          onChanged();
        }
      } else {
        if (!other.mapping_.isEmpty()) {
          if (mappingBuilder_.isEmpty()) {
            mappingBuilder_.dispose();
            mappingBuilder_ = null;
            mapping_ = other.mapping_;
            bitField0_ = (bitField0_ & ~0x00000001);
            mappingBuilder_ = 
              com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders ?
                 getMappingFieldBuilder() : null;
          } else {
            mappingBuilder_.addAllMessages(other.mapping_);
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
              com.helium.grpc.protocol_gwmp_mapping_v1 m =
                  input.readMessage(
                      com.helium.grpc.protocol_gwmp_mapping_v1.parser(),
                      extensionRegistry);
              if (mappingBuilder_ == null) {
                ensureMappingIsMutable();
                mapping_.add(m);
              } else {
                mappingBuilder_.addMessage(m);
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

    private java.util.List<com.helium.grpc.protocol_gwmp_mapping_v1> mapping_ =
      java.util.Collections.emptyList();
    private void ensureMappingIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        mapping_ = new java.util.ArrayList<com.helium.grpc.protocol_gwmp_mapping_v1>(mapping_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilder<
        com.helium.grpc.protocol_gwmp_mapping_v1, com.helium.grpc.protocol_gwmp_mapping_v1.Builder, com.helium.grpc.protocol_gwmp_mapping_v1OrBuilder> mappingBuilder_;

    /**
     * <code>repeated .helium.iot_config.protocol_gwmp_mapping_v1 mapping = 1;</code>
     */
    public java.util.List<com.helium.grpc.protocol_gwmp_mapping_v1> getMappingList() {
      if (mappingBuilder_ == null) {
        return java.util.Collections.unmodifiableList(mapping_);
      } else {
        return mappingBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .helium.iot_config.protocol_gwmp_mapping_v1 mapping = 1;</code>
     */
    public int getMappingCount() {
      if (mappingBuilder_ == null) {
        return mapping_.size();
      } else {
        return mappingBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .helium.iot_config.protocol_gwmp_mapping_v1 mapping = 1;</code>
     */
    public com.helium.grpc.protocol_gwmp_mapping_v1 getMapping(int index) {
      if (mappingBuilder_ == null) {
        return mapping_.get(index);
      } else {
        return mappingBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .helium.iot_config.protocol_gwmp_mapping_v1 mapping = 1;</code>
     */
    public Builder setMapping(
        int index, com.helium.grpc.protocol_gwmp_mapping_v1 value) {
      if (mappingBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureMappingIsMutable();
        mapping_.set(index, value);
        onChanged();
      } else {
        mappingBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .helium.iot_config.protocol_gwmp_mapping_v1 mapping = 1;</code>
     */
    public Builder setMapping(
        int index, com.helium.grpc.protocol_gwmp_mapping_v1.Builder builderForValue) {
      if (mappingBuilder_ == null) {
        ensureMappingIsMutable();
        mapping_.set(index, builderForValue.build());
        onChanged();
      } else {
        mappingBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .helium.iot_config.protocol_gwmp_mapping_v1 mapping = 1;</code>
     */
    public Builder addMapping(com.helium.grpc.protocol_gwmp_mapping_v1 value) {
      if (mappingBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureMappingIsMutable();
        mapping_.add(value);
        onChanged();
      } else {
        mappingBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .helium.iot_config.protocol_gwmp_mapping_v1 mapping = 1;</code>
     */
    public Builder addMapping(
        int index, com.helium.grpc.protocol_gwmp_mapping_v1 value) {
      if (mappingBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureMappingIsMutable();
        mapping_.add(index, value);
        onChanged();
      } else {
        mappingBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .helium.iot_config.protocol_gwmp_mapping_v1 mapping = 1;</code>
     */
    public Builder addMapping(
        com.helium.grpc.protocol_gwmp_mapping_v1.Builder builderForValue) {
      if (mappingBuilder_ == null) {
        ensureMappingIsMutable();
        mapping_.add(builderForValue.build());
        onChanged();
      } else {
        mappingBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .helium.iot_config.protocol_gwmp_mapping_v1 mapping = 1;</code>
     */
    public Builder addMapping(
        int index, com.helium.grpc.protocol_gwmp_mapping_v1.Builder builderForValue) {
      if (mappingBuilder_ == null) {
        ensureMappingIsMutable();
        mapping_.add(index, builderForValue.build());
        onChanged();
      } else {
        mappingBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .helium.iot_config.protocol_gwmp_mapping_v1 mapping = 1;</code>
     */
    public Builder addAllMapping(
        java.lang.Iterable<? extends com.helium.grpc.protocol_gwmp_mapping_v1> values) {
      if (mappingBuilder_ == null) {
        ensureMappingIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, mapping_);
        onChanged();
      } else {
        mappingBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .helium.iot_config.protocol_gwmp_mapping_v1 mapping = 1;</code>
     */
    public Builder clearMapping() {
      if (mappingBuilder_ == null) {
        mapping_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        mappingBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .helium.iot_config.protocol_gwmp_mapping_v1 mapping = 1;</code>
     */
    public Builder removeMapping(int index) {
      if (mappingBuilder_ == null) {
        ensureMappingIsMutable();
        mapping_.remove(index);
        onChanged();
      } else {
        mappingBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .helium.iot_config.protocol_gwmp_mapping_v1 mapping = 1;</code>
     */
    public com.helium.grpc.protocol_gwmp_mapping_v1.Builder getMappingBuilder(
        int index) {
      return getMappingFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .helium.iot_config.protocol_gwmp_mapping_v1 mapping = 1;</code>
     */
    public com.helium.grpc.protocol_gwmp_mapping_v1OrBuilder getMappingOrBuilder(
        int index) {
      if (mappingBuilder_ == null) {
        return mapping_.get(index);  } else {
        return mappingBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .helium.iot_config.protocol_gwmp_mapping_v1 mapping = 1;</code>
     */
    public java.util.List<? extends com.helium.grpc.protocol_gwmp_mapping_v1OrBuilder> 
         getMappingOrBuilderList() {
      if (mappingBuilder_ != null) {
        return mappingBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(mapping_);
      }
    }
    /**
     * <code>repeated .helium.iot_config.protocol_gwmp_mapping_v1 mapping = 1;</code>
     */
    public com.helium.grpc.protocol_gwmp_mapping_v1.Builder addMappingBuilder() {
      return getMappingFieldBuilder().addBuilder(
          com.helium.grpc.protocol_gwmp_mapping_v1.getDefaultInstance());
    }
    /**
     * <code>repeated .helium.iot_config.protocol_gwmp_mapping_v1 mapping = 1;</code>
     */
    public com.helium.grpc.protocol_gwmp_mapping_v1.Builder addMappingBuilder(
        int index) {
      return getMappingFieldBuilder().addBuilder(
          index, com.helium.grpc.protocol_gwmp_mapping_v1.getDefaultInstance());
    }
    /**
     * <code>repeated .helium.iot_config.protocol_gwmp_mapping_v1 mapping = 1;</code>
     */
    public java.util.List<com.helium.grpc.protocol_gwmp_mapping_v1.Builder> 
         getMappingBuilderList() {
      return getMappingFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilder<
        com.helium.grpc.protocol_gwmp_mapping_v1, com.helium.grpc.protocol_gwmp_mapping_v1.Builder, com.helium.grpc.protocol_gwmp_mapping_v1OrBuilder> 
        getMappingFieldBuilder() {
      if (mappingBuilder_ == null) {
        mappingBuilder_ = new com.google.protobuf.RepeatedFieldBuilder<
            com.helium.grpc.protocol_gwmp_mapping_v1, com.helium.grpc.protocol_gwmp_mapping_v1.Builder, com.helium.grpc.protocol_gwmp_mapping_v1OrBuilder>(
                mapping_,
                ((bitField0_ & 0x00000001) != 0),
                getParentForChildren(),
                isClean());
        mapping_ = null;
      }
      return mappingBuilder_;
    }

    // @@protoc_insertion_point(builder_scope:helium.iot_config.protocol_gwmp_v1)
  }

  // @@protoc_insertion_point(class_scope:helium.iot_config.protocol_gwmp_v1)
  private static final com.helium.grpc.protocol_gwmp_v1 DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.helium.grpc.protocol_gwmp_v1();
  }

  public static com.helium.grpc.protocol_gwmp_v1 getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<protocol_gwmp_v1>
      PARSER = new com.google.protobuf.AbstractParser<protocol_gwmp_v1>() {
    @java.lang.Override
    public protocol_gwmp_v1 parsePartialFrom(
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

  public static com.google.protobuf.Parser<protocol_gwmp_v1> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<protocol_gwmp_v1> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.helium.grpc.protocol_gwmp_v1 getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

