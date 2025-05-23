// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: gw/gw.proto
// Protobuf Java Version: 4.29.2

package io.chirpstack.api.gw;

/**
 * Protobuf enum {@code gw.CodeRate}
 */
public enum CodeRate
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <code>CR_UNDEFINED = 0;</code>
   */
  CR_UNDEFINED(0),
  /**
   * <pre>
   * LoRa
   * </pre>
   *
   * <code>CR_4_5 = 1;</code>
   */
  CR_4_5(1),
  /**
   * <code>CR_4_6 = 2;</code>
   */
  CR_4_6(2),
  /**
   * <code>CR_4_7 = 3;</code>
   */
  CR_4_7(3),
  /**
   * <code>CR_4_8 = 4;</code>
   */
  CR_4_8(4),
  /**
   * <pre>
   * LR-FHSS
   * </pre>
   *
   * <code>CR_3_8 = 5;</code>
   */
  CR_3_8(5),
  /**
   * <code>CR_2_6 = 6;</code>
   */
  CR_2_6(6),
  /**
   * <code>CR_1_4 = 7;</code>
   */
  CR_1_4(7),
  /**
   * <code>CR_1_6 = 8;</code>
   */
  CR_1_6(8),
  /**
   * <code>CR_5_6 = 9;</code>
   */
  CR_5_6(9),
  /**
   * <pre>
   * LoRa 2.4 gHz
   * </pre>
   *
   * <code>CR_LI_4_5 = 10;</code>
   */
  CR_LI_4_5(10),
  /**
   * <code>CR_LI_4_6 = 11;</code>
   */
  CR_LI_4_6(11),
  /**
   * <code>CR_LI_4_8 = 12;</code>
   */
  CR_LI_4_8(12),
  UNRECOGNIZED(-1),
  ;

  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 29,
      /* patch= */ 2,
      /* suffix= */ "",
      CodeRate.class.getName());
  }
  /**
   * <code>CR_UNDEFINED = 0;</code>
   */
  public static final int CR_UNDEFINED_VALUE = 0;
  /**
   * <pre>
   * LoRa
   * </pre>
   *
   * <code>CR_4_5 = 1;</code>
   */
  public static final int CR_4_5_VALUE = 1;
  /**
   * <code>CR_4_6 = 2;</code>
   */
  public static final int CR_4_6_VALUE = 2;
  /**
   * <code>CR_4_7 = 3;</code>
   */
  public static final int CR_4_7_VALUE = 3;
  /**
   * <code>CR_4_8 = 4;</code>
   */
  public static final int CR_4_8_VALUE = 4;
  /**
   * <pre>
   * LR-FHSS
   * </pre>
   *
   * <code>CR_3_8 = 5;</code>
   */
  public static final int CR_3_8_VALUE = 5;
  /**
   * <code>CR_2_6 = 6;</code>
   */
  public static final int CR_2_6_VALUE = 6;
  /**
   * <code>CR_1_4 = 7;</code>
   */
  public static final int CR_1_4_VALUE = 7;
  /**
   * <code>CR_1_6 = 8;</code>
   */
  public static final int CR_1_6_VALUE = 8;
  /**
   * <code>CR_5_6 = 9;</code>
   */
  public static final int CR_5_6_VALUE = 9;
  /**
   * <pre>
   * LoRa 2.4 gHz
   * </pre>
   *
   * <code>CR_LI_4_5 = 10;</code>
   */
  public static final int CR_LI_4_5_VALUE = 10;
  /**
   * <code>CR_LI_4_6 = 11;</code>
   */
  public static final int CR_LI_4_6_VALUE = 11;
  /**
   * <code>CR_LI_4_8 = 12;</code>
   */
  public static final int CR_LI_4_8_VALUE = 12;


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
  public static CodeRate valueOf(int value) {
    return forNumber(value);
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   */
  public static CodeRate forNumber(int value) {
    switch (value) {
      case 0: return CR_UNDEFINED;
      case 1: return CR_4_5;
      case 2: return CR_4_6;
      case 3: return CR_4_7;
      case 4: return CR_4_8;
      case 5: return CR_3_8;
      case 6: return CR_2_6;
      case 7: return CR_1_4;
      case 8: return CR_1_6;
      case 9: return CR_5_6;
      case 10: return CR_LI_4_5;
      case 11: return CR_LI_4_6;
      case 12: return CR_LI_4_8;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<CodeRate>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      CodeRate> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<CodeRate>() {
          public CodeRate findValueByNumber(int number) {
            return CodeRate.forNumber(number);
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
    return io.chirpstack.api.gw.GatewayProto.getDescriptor().getEnumTypes().get(0);
  }

  private static final CodeRate[] VALUES = values();

  public static CodeRate valueOf(
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

  private CodeRate(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:gw.CodeRate)
}

