// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: common/common.proto

// Protobuf Java Version: 3.25.1
package io.chirpstack.api;

/**
 * Protobuf enum {@code common.Aggregation}
 */
public enum Aggregation
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <pre>
   * Hour.
   * </pre>
   *
   * <code>HOUR = 0;</code>
   */
  HOUR(0),
  /**
   * <pre>
   * Day.
   * </pre>
   *
   * <code>DAY = 1;</code>
   */
  DAY(1),
  /**
   * <pre>
   * Month.
   * </pre>
   *
   * <code>MONTH = 2;</code>
   */
  MONTH(2),
  UNRECOGNIZED(-1),
  ;

  /**
   * <pre>
   * Hour.
   * </pre>
   *
   * <code>HOUR = 0;</code>
   */
  public static final int HOUR_VALUE = 0;
  /**
   * <pre>
   * Day.
   * </pre>
   *
   * <code>DAY = 1;</code>
   */
  public static final int DAY_VALUE = 1;
  /**
   * <pre>
   * Month.
   * </pre>
   *
   * <code>MONTH = 2;</code>
   */
  public static final int MONTH_VALUE = 2;


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
  public static Aggregation valueOf(int value) {
    return forNumber(value);
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   */
  public static Aggregation forNumber(int value) {
    switch (value) {
      case 0: return HOUR;
      case 1: return DAY;
      case 2: return MONTH;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<Aggregation>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      Aggregation> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<Aggregation>() {
          public Aggregation findValueByNumber(int number) {
            return Aggregation.forNumber(number);
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
    return io.chirpstack.api.CommonProto.getDescriptor().getEnumTypes().get(6);
  }

  private static final Aggregation[] VALUES = values();

  public static Aggregation valueOf(
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

  private Aggregation(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:common.Aggregation)
}

