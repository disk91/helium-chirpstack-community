// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: gw/gw.proto
// Protobuf Java Version: 4.29.2

package io.chirpstack.api.gw;

/**
 * <pre>
 * Not implemented yet.
 * </pre>
 *
 * Protobuf type {@code gw.ImmediatelyTimingInfo}
 */
public final class ImmediatelyTimingInfo extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:gw.ImmediatelyTimingInfo)
    ImmediatelyTimingInfoOrBuilder {
private static final long serialVersionUID = 0L;
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 29,
      /* patch= */ 2,
      /* suffix= */ "",
      ImmediatelyTimingInfo.class.getName());
  }
  // Use ImmediatelyTimingInfo.newBuilder() to construct.
  private ImmediatelyTimingInfo(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private ImmediatelyTimingInfo() {
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return io.chirpstack.api.gw.GatewayProto.internal_static_gw_ImmediatelyTimingInfo_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return io.chirpstack.api.gw.GatewayProto.internal_static_gw_ImmediatelyTimingInfo_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            io.chirpstack.api.gw.ImmediatelyTimingInfo.class, io.chirpstack.api.gw.ImmediatelyTimingInfo.Builder.class);
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
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    size += getUnknownFields().getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof io.chirpstack.api.gw.ImmediatelyTimingInfo)) {
      return super.equals(obj);
    }
    io.chirpstack.api.gw.ImmediatelyTimingInfo other = (io.chirpstack.api.gw.ImmediatelyTimingInfo) obj;

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
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static io.chirpstack.api.gw.ImmediatelyTimingInfo parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.gw.ImmediatelyTimingInfo parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.gw.ImmediatelyTimingInfo parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.gw.ImmediatelyTimingInfo parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.gw.ImmediatelyTimingInfo parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.api.gw.ImmediatelyTimingInfo parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.api.gw.ImmediatelyTimingInfo parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static io.chirpstack.api.gw.ImmediatelyTimingInfo parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static io.chirpstack.api.gw.ImmediatelyTimingInfo parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static io.chirpstack.api.gw.ImmediatelyTimingInfo parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.chirpstack.api.gw.ImmediatelyTimingInfo parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static io.chirpstack.api.gw.ImmediatelyTimingInfo parseFrom(
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
  public static Builder newBuilder(io.chirpstack.api.gw.ImmediatelyTimingInfo prototype) {
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
   * Not implemented yet.
   * </pre>
   *
   * Protobuf type {@code gw.ImmediatelyTimingInfo}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:gw.ImmediatelyTimingInfo)
      io.chirpstack.api.gw.ImmediatelyTimingInfoOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return io.chirpstack.api.gw.GatewayProto.internal_static_gw_ImmediatelyTimingInfo_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return io.chirpstack.api.gw.GatewayProto.internal_static_gw_ImmediatelyTimingInfo_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              io.chirpstack.api.gw.ImmediatelyTimingInfo.class, io.chirpstack.api.gw.ImmediatelyTimingInfo.Builder.class);
    }

    // Construct using io.chirpstack.api.gw.ImmediatelyTimingInfo.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return io.chirpstack.api.gw.GatewayProto.internal_static_gw_ImmediatelyTimingInfo_descriptor;
    }

    @java.lang.Override
    public io.chirpstack.api.gw.ImmediatelyTimingInfo getDefaultInstanceForType() {
      return io.chirpstack.api.gw.ImmediatelyTimingInfo.getDefaultInstance();
    }

    @java.lang.Override
    public io.chirpstack.api.gw.ImmediatelyTimingInfo build() {
      io.chirpstack.api.gw.ImmediatelyTimingInfo result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public io.chirpstack.api.gw.ImmediatelyTimingInfo buildPartial() {
      io.chirpstack.api.gw.ImmediatelyTimingInfo result = new io.chirpstack.api.gw.ImmediatelyTimingInfo(this);
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof io.chirpstack.api.gw.ImmediatelyTimingInfo) {
        return mergeFrom((io.chirpstack.api.gw.ImmediatelyTimingInfo)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(io.chirpstack.api.gw.ImmediatelyTimingInfo other) {
      if (other == io.chirpstack.api.gw.ImmediatelyTimingInfo.getDefaultInstance()) return this;
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

    // @@protoc_insertion_point(builder_scope:gw.ImmediatelyTimingInfo)
  }

  // @@protoc_insertion_point(class_scope:gw.ImmediatelyTimingInfo)
  private static final io.chirpstack.api.gw.ImmediatelyTimingInfo DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new io.chirpstack.api.gw.ImmediatelyTimingInfo();
  }

  public static io.chirpstack.api.gw.ImmediatelyTimingInfo getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ImmediatelyTimingInfo>
      PARSER = new com.google.protobuf.AbstractParser<ImmediatelyTimingInfo>() {
    @java.lang.Override
    public ImmediatelyTimingInfo parsePartialFrom(
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

  public static com.google.protobuf.Parser<ImmediatelyTimingInfo> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ImmediatelyTimingInfo> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public io.chirpstack.api.gw.ImmediatelyTimingInfo getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

