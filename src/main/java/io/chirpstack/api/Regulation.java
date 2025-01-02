// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: common/common.proto
// Protobuf Java Version: 4.29.2

package io.chirpstack.api;

/**
 * Protobuf enum {@code common.Regulation}
 */
public enum Regulation
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <pre>
   * Unknown.
   * </pre>
   *
   * <code>REGULATION_UNKNOWN = 0;</code>
   */
  REGULATION_UNKNOWN(0),
  /**
   * <pre>
   * ETSI EN 300 220.
   * </pre>
   *
   * <code>ETSI_EN_300_220 = 1;</code>
   */
  ETSI_EN_300_220(1),
  UNRECOGNIZED(-1),
  ;

  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 29,
      /* patch= */ 2,
      /* suffix= */ "",
      Regulation.class.getName());
  }
  /**
   * <pre>
   * Unknown.
   * </pre>
   *
   * <code>REGULATION_UNKNOWN = 0;</code>
   */
  public static final int REGULATION_UNKNOWN_VALUE = 0;
  /**
   * <pre>
   * ETSI EN 300 220.
   * </pre>
   *
   * <code>ETSI_EN_300_220 = 1;</code>
   */
  public static final int ETSI_EN_300_220_VALUE = 1;


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
  public static Regulation valueOf(int value) {
    return forNumber(value);
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   */
  public static Regulation forNumber(int value) {
    switch (value) {
      case 0: return REGULATION_UNKNOWN;
      case 1: return ETSI_EN_300_220;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<Regulation>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      Regulation> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<Regulation>() {
          public Regulation findValueByNumber(int number) {
            return Regulation.forNumber(number);
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
    return io.chirpstack.api.CommonProto.getDescriptor().getEnumTypes().get(8);
  }

  private static final Regulation[] VALUES = values();

  public static Regulation valueOf(
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

  private Regulation(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:common.Regulation)
}

