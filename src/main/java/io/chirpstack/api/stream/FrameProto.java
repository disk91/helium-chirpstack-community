// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: stream/frame.proto

// Protobuf Java Version: 3.25.1
package io.chirpstack.api.stream;

public final class FrameProto {
  private FrameProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_stream_UplinkFrameLog_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_stream_UplinkFrameLog_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_stream_DownlinkFrameLog_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_stream_DownlinkFrameLog_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\022stream/frame.proto\022\006stream\032\037google/pro" +
      "tobuf/timestamp.proto\032\023common/common.pro" +
      "to\032\013gw/gw.proto\"\220\002\n\016UplinkFrameLog\022\023\n\013ph" +
      "y_payload\030\001 \001(\014\022!\n\007tx_info\030\002 \001(\0132\020.gw.Up" +
      "linkTxInfo\022!\n\007rx_info\030\003 \003(\0132\020.gw.UplinkR" +
      "xInfo\022\035\n\006m_type\030\004 \001(\0162\r.common.MType\022\020\n\010" +
      "dev_addr\030\005 \001(\t\022\017\n\007dev_eui\030\006 \001(\t\022(\n\004time\030" +
      "\007 \001(\0132\032.google.protobuf.Timestamp\022\030\n\020pla" +
      "intext_f_opts\030\010 \001(\010\022\035\n\025plaintext_frm_pay" +
      "load\030\t \001(\010\"\232\002\n\020DownlinkFrameLog\022(\n\004time\030" +
      "\001 \001(\0132\032.google.protobuf.Timestamp\022\023\n\013phy" +
      "_payload\030\002 \001(\014\022#\n\007tx_info\030\003 \001(\0132\022.gw.Dow" +
      "nlinkTxInfo\022\023\n\013downlink_id\030\004 \001(\r\022\022\n\ngate" +
      "way_id\030\005 \001(\t\022\035\n\006m_type\030\006 \001(\0162\r.common.MT" +
      "ype\022\020\n\010dev_addr\030\007 \001(\t\022\017\n\007dev_eui\030\010 \001(\t\022\030" +
      "\n\020plaintext_f_opts\030\t \001(\010\022\035\n\025plaintext_fr" +
      "m_payload\030\n \001(\010Bo\n\030io.chirpstack.api.str" +
      "eamB\nFrameProtoP\001Z1github.com/chirpstack" +
      "/chirpstack/api/go/v4/stream\252\002\021Chirpstac" +
      "k.Streamb\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.google.protobuf.TimestampProto.getDescriptor(),
          io.chirpstack.api.CommonProto.getDescriptor(),
          io.chirpstack.api.gw.GatewayProto.getDescriptor(),
        });
    internal_static_stream_UplinkFrameLog_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_stream_UplinkFrameLog_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_stream_UplinkFrameLog_descriptor,
        new java.lang.String[] { "PhyPayload", "TxInfo", "RxInfo", "MType", "DevAddr", "DevEui", "Time", "PlaintextFOpts", "PlaintextFrmPayload", });
    internal_static_stream_DownlinkFrameLog_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_stream_DownlinkFrameLog_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_stream_DownlinkFrameLog_descriptor,
        new java.lang.String[] { "Time", "PhyPayload", "TxInfo", "DownlinkId", "GatewayId", "MType", "DevAddr", "DevEui", "PlaintextFOpts", "PlaintextFrmPayload", });
    com.google.protobuf.TimestampProto.getDescriptor();
    io.chirpstack.api.CommonProto.getDescriptor();
    io.chirpstack.api.gw.GatewayProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
