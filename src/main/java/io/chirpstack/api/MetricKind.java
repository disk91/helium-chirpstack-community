// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: common/common.proto
// Protobuf Java Version: 4.29.2

package io.chirpstack.api;

/**
 * Protobuf enum {@code common.MetricKind}
 */
public enum MetricKind
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <pre>
   * Incrementing counters that never decrease (these are not reset on each
   * reading).
   * </pre>
   *
   * <code>COUNTER = 0;</code>
   */
  COUNTER(0),
  /**
   * <pre>
   * Counters that do get reset upon reading.
   * </pre>
   *
   * <code>ABSOLUTE = 1;</code>
   */
  ABSOLUTE(1),
  /**
   * <pre>
   * E.g. a temperature value.
   * </pre>
   *
   * <code>GAUGE = 2;</code>
   */
  GAUGE(2),
  UNRECOGNIZED(-1),
  ;

  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 29,
      /* patch= */ 2,
      /* suffix= */ "",
      MetricKind.class.getName());
  }
  /**
   * <pre>
   * Incrementing counters that never decrease (these are not reset on each
   * reading).
   * </pre>
   *
   * <code>COUNTER = 0;</code>
   */
  public static final int COUNTER_VALUE = 0;
  /**
   * <pre>
   * Counters that do get reset upon reading.
   * </pre>
   *
   * <code>ABSOLUTE = 1;</code>
   */
  public static final int ABSOLUTE_VALUE = 1;
  /**
   * <pre>
   * E.g. a temperature value.
   * </pre>
   *
   * <code>GAUGE = 2;</code>
   */
  public static final int GAUGE_VALUE = 2;


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
  public static MetricKind valueOf(int value) {
    return forNumber(value);
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   */
  public static MetricKind forNumber(int value) {
    switch (value) {
      case 0: return COUNTER;
      case 1: return ABSOLUTE;
      case 2: return GAUGE;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<MetricKind>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      MetricKind> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<MetricKind>() {
          public MetricKind findValueByNumber(int number) {
            return MetricKind.forNumber(number);
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
    return io.chirpstack.api.CommonProto.getDescriptor().getEnumTypes().get(7);
  }

  private static final MetricKind[] VALUES = values();

  public static MetricKind valueOf(
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

  private MetricKind(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:common.MetricKind)
}

