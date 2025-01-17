// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: common/common.proto
// Protobuf Java Version: 4.29.2

package io.chirpstack.api;

/**
 * Protobuf enum {@code common.DeviceClass}
 */
public enum DeviceClass
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <pre>
   * Class-A.
   * </pre>
   *
   * <code>CLASS_A = 0;</code>
   */
  CLASS_A(0),
  /**
   * <pre>
   * Class-B.
   * </pre>
   *
   * <code>CLASS_B = 1;</code>
   */
  CLASS_B(1),
  /**
   * <pre>
   * Class-C.
   * </pre>
   *
   * <code>CLASS_C = 2;</code>
   */
  CLASS_C(2),
  UNRECOGNIZED(-1),
  ;

  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 29,
      /* patch= */ 2,
      /* suffix= */ "",
      DeviceClass.class.getName());
  }
  /**
   * <pre>
   * Class-A.
   * </pre>
   *
   * <code>CLASS_A = 0;</code>
   */
  public static final int CLASS_A_VALUE = 0;
  /**
   * <pre>
   * Class-B.
   * </pre>
   *
   * <code>CLASS_B = 1;</code>
   */
  public static final int CLASS_B_VALUE = 1;
  /**
   * <pre>
   * Class-C.
   * </pre>
   *
   * <code>CLASS_C = 2;</code>
   */
  public static final int CLASS_C_VALUE = 2;


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
  public static DeviceClass valueOf(int value) {
    return forNumber(value);
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   */
  public static DeviceClass forNumber(int value) {
    switch (value) {
      case 0: return CLASS_A;
      case 1: return CLASS_B;
      case 2: return CLASS_C;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<DeviceClass>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      DeviceClass> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<DeviceClass>() {
          public DeviceClass findValueByNumber(int number) {
            return DeviceClass.forNumber(number);
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
    return io.chirpstack.api.CommonProto.getDescriptor().getEnumTypes().get(9);
  }

  private static final DeviceClass[] VALUES = values();

  public static DeviceClass valueOf(
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

  private DeviceClass(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:common.DeviceClass)
}

