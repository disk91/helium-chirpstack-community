// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: api/user.proto

package io.chirpstack.restapi;

/**
 * Protobuf type {@code api.UpdateUserPasswordRequest}
 */
public final class UpdateUserPasswordRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:api.UpdateUserPasswordRequest)
    UpdateUserPasswordRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use UpdateUserPasswordRequest.newBuilder() to construct.
  private UpdateUserPasswordRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private UpdateUserPasswordRequest() {
    userId_ = "";
    password_ = "";
  }

  @Override
  @SuppressWarnings({"unused"})
  protected Object newInstance(
      UnusedPrivateParameter unused) {
    return new UpdateUserPasswordRequest();
  }

  @Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return io.chirpstack.restapi.UserProto.internal_static_api_UpdateUserPasswordRequest_descriptor;
  }

  @Override
  protected FieldAccessorTable
      internalGetFieldAccessorTable() {
    return io.chirpstack.restapi.UserProto.internal_static_api_UpdateUserPasswordRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            io.chirpstack.restapi.UpdateUserPasswordRequest.class, io.chirpstack.restapi.UpdateUserPasswordRequest.Builder.class);
  }

  public static final int USER_ID_FIELD_NUMBER = 1;
  private volatile Object userId_;
  /**
   * <pre>
   * User ID.
   * </pre>
   *
   * <code>string user_id = 1;</code>
   * @return The userId.
   */
  @Override
  public String getUserId() {
    Object ref = userId_;
    if (ref instanceof String) {
      return (String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      String s = bs.toStringUtf8();
      userId_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * User ID.
   * </pre>
   *
   * <code>string user_id = 1;</code>
   * @return The bytes for userId.
   */
  @Override
  public com.google.protobuf.ByteString
      getUserIdBytes() {
    Object ref = userId_;
    if (ref instanceof String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (String) ref);
      userId_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int PASSWORD_FIELD_NUMBER = 2;
  private volatile Object password_;
  /**
   * <pre>
   * Password to set.
   * </pre>
   *
   * <code>string password = 2;</code>
   * @return The password.
   */
  @Override
  public String getPassword() {
    Object ref = password_;
    if (ref instanceof String) {
      return (String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      String s = bs.toStringUtf8();
      password_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * Password to set.
   * </pre>
   *
   * <code>string password = 2;</code>
   * @return The bytes for password.
   */
  @Override
  public com.google.protobuf.ByteString
      getPasswordBytes() {
    Object ref = password_;
    if (ref instanceof String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (String) ref);
      password_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  private byte memoizedIsInitialized = -1;
  @Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(userId_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, userId_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(password_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, password_);
    }
    getUnknownFields().writeTo(output);
  }

  @Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(userId_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, userId_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(password_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, password_);
    }
    size += getUnknownFields().getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof io.chirpstack.restapi.UpdateUserPasswordRequest)) {
      return super.equals(obj);
    }
    io.chirpstack.restapi.UpdateUserPasswordRequest other = (io.chirpstack.restapi.UpdateUserPasswordRequest) obj;

    if (!getUserId()
        .equals(other.getUserId())) return false;
    if (!getPassword()
        .equals(other.getPassword())) return false;
    if (!getUnknownFields().equals(other.getUnknownFields())) return false;
    return true;
  }

  @Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + USER_ID_FIELD_NUMBER;
    hash = (53 * hash) + getUserId().hashCode();
    hash = (37 * hash) + PASSWORD_FIELD_NUMBER;
    hash = (53 * hash) + getPassword().hashCode();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static io.chirpstack.restapi.UpdateUserPasswordRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.restapi.UpdateUserPasswordRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.restapi.UpdateUserPasswordRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.restapi.UpdateUserPasswordRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.restapi.UpdateUserPasswordRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.chirpstack.restapi.UpdateUserPasswordRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.chirpstack.restapi.UpdateUserPasswordRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static io.chirpstack.restapi.UpdateUserPasswordRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.chirpstack.restapi.UpdateUserPasswordRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static io.chirpstack.restapi.UpdateUserPasswordRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.chirpstack.restapi.UpdateUserPasswordRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static io.chirpstack.restapi.UpdateUserPasswordRequest parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(io.chirpstack.restapi.UpdateUserPasswordRequest prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @Override
  protected Builder newBuilderForType(
      BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code api.UpdateUserPasswordRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:api.UpdateUserPasswordRequest)
      io.chirpstack.restapi.UpdateUserPasswordRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return io.chirpstack.restapi.UserProto.internal_static_api_UpdateUserPasswordRequest_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return io.chirpstack.restapi.UserProto.internal_static_api_UpdateUserPasswordRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              io.chirpstack.restapi.UpdateUserPasswordRequest.class, io.chirpstack.restapi.UpdateUserPasswordRequest.Builder.class);
    }

    // Construct using io.chirpstack.restapi.UpdateUserPasswordRequest.newBuilder()
    private Builder() {

    }

    private Builder(
        BuilderParent parent) {
      super(parent);

    }
    @Override
    public Builder clear() {
      super.clear();
      userId_ = "";

      password_ = "";

      return this;
    }

    @Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return io.chirpstack.restapi.UserProto.internal_static_api_UpdateUserPasswordRequest_descriptor;
    }

    @Override
    public io.chirpstack.restapi.UpdateUserPasswordRequest getDefaultInstanceForType() {
      return io.chirpstack.restapi.UpdateUserPasswordRequest.getDefaultInstance();
    }

    @Override
    public io.chirpstack.restapi.UpdateUserPasswordRequest build() {
      io.chirpstack.restapi.UpdateUserPasswordRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @Override
    public io.chirpstack.restapi.UpdateUserPasswordRequest buildPartial() {
      io.chirpstack.restapi.UpdateUserPasswordRequest result = new io.chirpstack.restapi.UpdateUserPasswordRequest(this);
      result.userId_ = userId_;
      result.password_ = password_;
      onBuilt();
      return result;
    }

    @Override
    public Builder clone() {
      return super.clone();
    }
    @Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return super.setField(field, value);
    }
    @Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return super.addRepeatedField(field, value);
    }
    @Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof io.chirpstack.restapi.UpdateUserPasswordRequest) {
        return mergeFrom((io.chirpstack.restapi.UpdateUserPasswordRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(io.chirpstack.restapi.UpdateUserPasswordRequest other) {
      if (other == io.chirpstack.restapi.UpdateUserPasswordRequest.getDefaultInstance()) return this;
      if (!other.getUserId().isEmpty()) {
        userId_ = other.userId_;
        onChanged();
      }
      if (!other.getPassword().isEmpty()) {
        password_ = other.password_;
        onChanged();
      }
      this.mergeUnknownFields(other.getUnknownFields());
      onChanged();
      return this;
    }

    @Override
    public final boolean isInitialized() {
      return true;
    }

    @Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      if (extensionRegistry == null) {
        throw new NullPointerException();
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
              userId_ = input.readStringRequireUtf8();

              break;
            } // case 10
            case 18: {
              password_ = input.readStringRequireUtf8();

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

    private Object userId_ = "";
    /**
     * <pre>
     * User ID.
     * </pre>
     *
     * <code>string user_id = 1;</code>
     * @return The userId.
     */
    public String getUserId() {
      Object ref = userId_;
      if (!(ref instanceof String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        userId_ = s;
        return s;
      } else {
        return (String) ref;
      }
    }
    /**
     * <pre>
     * User ID.
     * </pre>
     *
     * <code>string user_id = 1;</code>
     * @return The bytes for userId.
     */
    public com.google.protobuf.ByteString
        getUserIdBytes() {
      Object ref = userId_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        userId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * User ID.
     * </pre>
     *
     * <code>string user_id = 1;</code>
     * @param value The userId to set.
     * @return This builder for chaining.
     */
    public Builder setUserId(
        String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      userId_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * User ID.
     * </pre>
     *
     * <code>string user_id = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearUserId() {
      
      userId_ = getDefaultInstance().getUserId();
      onChanged();
      return this;
    }
    /**
     * <pre>
     * User ID.
     * </pre>
     *
     * <code>string user_id = 1;</code>
     * @param value The bytes for userId to set.
     * @return This builder for chaining.
     */
    public Builder setUserIdBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      userId_ = value;
      onChanged();
      return this;
    }

    private Object password_ = "";
    /**
     * <pre>
     * Password to set.
     * </pre>
     *
     * <code>string password = 2;</code>
     * @return The password.
     */
    public String getPassword() {
      Object ref = password_;
      if (!(ref instanceof String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        password_ = s;
        return s;
      } else {
        return (String) ref;
      }
    }
    /**
     * <pre>
     * Password to set.
     * </pre>
     *
     * <code>string password = 2;</code>
     * @return The bytes for password.
     */
    public com.google.protobuf.ByteString
        getPasswordBytes() {
      Object ref = password_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        password_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * Password to set.
     * </pre>
     *
     * <code>string password = 2;</code>
     * @param value The password to set.
     * @return This builder for chaining.
     */
    public Builder setPassword(
        String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      password_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Password to set.
     * </pre>
     *
     * <code>string password = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearPassword() {
      
      password_ = getDefaultInstance().getPassword();
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Password to set.
     * </pre>
     *
     * <code>string password = 2;</code>
     * @param value The bytes for password to set.
     * @return This builder for chaining.
     */
    public Builder setPasswordBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      password_ = value;
      onChanged();
      return this;
    }
    @Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:api.UpdateUserPasswordRequest)
  }

  // @@protoc_insertion_point(class_scope:api.UpdateUserPasswordRequest)
  private static final io.chirpstack.restapi.UpdateUserPasswordRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new io.chirpstack.restapi.UpdateUserPasswordRequest();
  }

  public static io.chirpstack.restapi.UpdateUserPasswordRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<UpdateUserPasswordRequest>
      PARSER = new com.google.protobuf.AbstractParser<UpdateUserPasswordRequest>() {
    @Override
    public UpdateUserPasswordRequest parsePartialFrom(
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

  public static com.google.protobuf.Parser<UpdateUserPasswordRequest> parser() {
    return PARSER;
  }

  @Override
  public com.google.protobuf.Parser<UpdateUserPasswordRequest> getParserForType() {
    return PARSER;
  }

  @Override
  public io.chirpstack.restapi.UpdateUserPasswordRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
