// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: gw/gw.proto

package io.chirpstack.api.gw;

/**
 * Protobuf enum {@code gw.FineTimestampType}
 */
public enum FineTimestampType
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <pre>
   * No fine-timestamp available.
   * </pre>
   *
   * <code>NONE = 0;</code>
   */
  NONE(0),
  /**
   * <pre>
   * Encrypted fine-timestamp.
   * </pre>
   *
   * <code>ENCRYPTED = 1;</code>
   */
  ENCRYPTED(1),
  /**
   * <pre>
   * Plain fine-timestamp.
   * </pre>
   *
   * <code>PLAIN = 2;</code>
   */
  PLAIN(2),
  UNRECOGNIZED(-1),
  ;

  /**
   * <pre>
   * No fine-timestamp available.
   * </pre>
   *
   * <code>NONE = 0;</code>
   */
  public static final int NONE_VALUE = 0;
  /**
   * <pre>
   * Encrypted fine-timestamp.
   * </pre>
   *
   * <code>ENCRYPTED = 1;</code>
   */
  public static final int ENCRYPTED_VALUE = 1;
  /**
   * <pre>
   * Plain fine-timestamp.
   * </pre>
   *
   * <code>PLAIN = 2;</code>
   */
  public static final int PLAIN_VALUE = 2;


  public final int getNumber() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalArgumentException(
          "Can't get the number of an unknown enum value.");
    }
    return value;
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   * @deprecated Use {@link #forNumber(int)} instead.
   */
  @java.lang.Deprecated
  public static FineTimestampType valueOf(int value) {
    return forNumber(value);
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   */
  public static FineTimestampType forNumber(int value) {
    switch (value) {
      case 0: return NONE;
      case 1: return ENCRYPTED;
      case 2: return PLAIN;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<FineTimestampType>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      FineTimestampType> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<FineTimestampType>() {
          public FineTimestampType findValueByNumber(int number) {
            return FineTimestampType.forNumber(number);
          }
        };

  public final com.google.protobuf.Descriptors.EnumValueDescriptor
      getValueDescriptor() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalStateException(
          "Can't get the descriptor of an unrecognized enum value.");
    }
    return getDescriptor().getValues().get(ordinal());
  }
  public final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptorForType() {
    return getDescriptor();
  }
  public static final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptor() {
    return io.chirpstack.api.gw.GatewayProto.getDescriptor().getEnumTypes().get(2);
  }

  private static final FineTimestampType[] VALUES = values();

  public static FineTimestampType valueOf(
      com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
    if (desc.getType() != getDescriptor()) {
      throw new java.lang.IllegalArgumentException(
        "EnumValueDescriptor is not for this type.");
    }
    if (desc.getIndex() == -1) {
      return UNRECOGNIZED;
    }
    return VALUES[desc.getIndex()];
  }

  private final int value;

  private FineTimestampType(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:gw.FineTimestampType)
}

