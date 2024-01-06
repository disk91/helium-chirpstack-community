// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: api/user.proto

package io.chirpstack.restapi;

public final class UserProto {
  private UserProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_api_User_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_api_User_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_api_UserListItem_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_api_UserListItem_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_api_UserTenant_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_api_UserTenant_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_api_CreateUserRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_api_CreateUserRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_api_CreateUserResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_api_CreateUserResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_api_GetUserRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_api_GetUserRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_api_GetUserResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_api_GetUserResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_api_UpdateUserRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_api_UpdateUserRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_api_DeleteUserRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_api_DeleteUserRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_api_ListUsersRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_api_ListUsersRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_api_ListUsersResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_api_ListUsersResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_api_UpdateUserPasswordRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_api_UpdateUserPasswordRequest_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\016api/user.proto\022\003api\032\034google/api/annota" +
      "tions.proto\032\037google/protobuf/timestamp.p" +
      "roto\032\033google/protobuf/empty.proto\"T\n\004Use" +
      "r\022\n\n\002id\030\001 \001(\t\022\020\n\010is_admin\030\004 \001(\010\022\021\n\tis_ac" +
      "tive\030\005 \001(\010\022\r\n\005email\030\006 \001(\t\022\014\n\004note\030\007 \001(\t\"" +
      "\256\001\n\014UserListItem\022\n\n\002id\030\001 \001(\t\022.\n\ncreated_" +
      "at\030\002 \001(\0132\032.google.protobuf.Timestamp\022.\n\n" +
      "updated_at\030\003 \001(\0132\032.google.protobuf.Times" +
      "tamp\022\r\n\005email\030\004 \001(\t\022\020\n\010is_admin\030\005 \001(\010\022\021\n" +
      "\tis_active\030\006 \001(\010\"d\n\nUserTenant\022\021\n\ttenant" +
      "_id\030\001 \001(\t\022\020\n\010is_admin\030\002 \001(\010\022\027\n\017is_device" +
      "_admin\030\003 \001(\010\022\030\n\020is_gateway_admin\030\004 \001(\010\"`" +
      "\n\021CreateUserRequest\022\027\n\004user\030\001 \001(\0132\t.api." +
      "User\022\020\n\010password\030\002 \001(\t\022 \n\007tenants\030\003 \003(\0132" +
      "\017.api.UserTenant\" \n\022CreateUserResponse\022\n" +
      "\n\002id\030\001 \001(\t\"\034\n\016GetUserRequest\022\n\n\002id\030\001 \001(\t" +
      "\"\212\001\n\017GetUserResponse\022\027\n\004user\030\001 \001(\0132\t.api" +
      ".User\022.\n\ncreated_at\030\002 \001(\0132\032.google.proto" +
      "buf.Timestamp\022.\n\nupdated_at\030\003 \001(\0132\032.goog" +
      "le.protobuf.Timestamp\",\n\021UpdateUserReque" +
      "st\022\027\n\004user\030\001 \001(\0132\t.api.User\"\037\n\021DeleteUse" +
      "rRequest\022\n\n\002id\030\001 \001(\t\"1\n\020ListUsersRequest" +
      "\022\r\n\005limit\030\001 \001(\r\022\016\n\006offset\030\002 \001(\r\"K\n\021ListU" +
      "sersResponse\022\023\n\013total_count\030\001 \001(\r\022!\n\006res" +
      "ult\030\002 \003(\0132\021.api.UserListItem\">\n\031UpdateUs" +
      "erPasswordRequest\022\017\n\007user_id\030\001 \001(\t\022\020\n\010pa" +
      "ssword\030\002 \001(\t2\227\004\n\013UserService\022P\n\006Create\022\026" +
      ".api.CreateUserRequest\032\027.api.CreateUserR" +
      "esponse\"\025\202\323\344\223\002\017\"\n/api/users:\001*\022I\n\003Get\022\023." +
      "api.GetUserRequest\032\024.api.GetUserResponse" +
      "\"\027\202\323\344\223\002\021\022\017/api/users/{id}\022Y\n\006Update\022\026.ap" +
      "i.UpdateUserRequest\032\026.google.protobuf.Em" +
      "pty\"\037\202\323\344\223\002\031\032\024/api/users/{user.id}:\001*\022Q\n\006" +
      "Delete\022\026.api.DeleteUserRequest\032\026.google." +
      "protobuf.Empty\"\027\202\323\344\223\002\021*\017/api/users/{id}\022" +
      "I\n\004List\022\025.api.ListUsersRequest\032\026.api.Lis" +
      "tUsersResponse\"\022\202\323\344\223\002\014\022\n/api/users\022r\n\016Up" +
      "datePassword\022\036.api.UpdateUserPasswordReq" +
      "uest\032\026.google.protobuf.Empty\"(\202\323\344\223\002\"\"\035/a" +
      "pi/users/{user_id}/password:\001*BP\n\021io.chi" +
      "rpstack.apiB\tUserProtoP\001Z.github.com/chi" +
      "rpstack/chirpstack/api/go/v4/apib\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.google.api.AnnotationsProto.getDescriptor(),
          com.google.protobuf.TimestampProto.getDescriptor(),
          com.google.protobuf.EmptyProto.getDescriptor(),
        });
    internal_static_api_User_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_api_User_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_api_User_descriptor,
        new String[] { "Id", "IsAdmin", "IsActive", "Email", "Note", });
    internal_static_api_UserListItem_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_api_UserListItem_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_api_UserListItem_descriptor,
        new String[] { "Id", "CreatedAt", "UpdatedAt", "Email", "IsAdmin", "IsActive", });
    internal_static_api_UserTenant_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_api_UserTenant_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_api_UserTenant_descriptor,
        new String[] { "TenantId", "IsAdmin", "IsDeviceAdmin", "IsGatewayAdmin", });
    internal_static_api_CreateUserRequest_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_api_CreateUserRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_api_CreateUserRequest_descriptor,
        new String[] { "User", "Password", "Tenants", });
    internal_static_api_CreateUserResponse_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_api_CreateUserResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_api_CreateUserResponse_descriptor,
        new String[] { "Id", });
    internal_static_api_GetUserRequest_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_api_GetUserRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_api_GetUserRequest_descriptor,
        new String[] { "Id", });
    internal_static_api_GetUserResponse_descriptor =
      getDescriptor().getMessageTypes().get(6);
    internal_static_api_GetUserResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_api_GetUserResponse_descriptor,
        new String[] { "User", "CreatedAt", "UpdatedAt", });
    internal_static_api_UpdateUserRequest_descriptor =
      getDescriptor().getMessageTypes().get(7);
    internal_static_api_UpdateUserRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_api_UpdateUserRequest_descriptor,
        new String[] { "User", });
    internal_static_api_DeleteUserRequest_descriptor =
      getDescriptor().getMessageTypes().get(8);
    internal_static_api_DeleteUserRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_api_DeleteUserRequest_descriptor,
        new String[] { "Id", });
    internal_static_api_ListUsersRequest_descriptor =
      getDescriptor().getMessageTypes().get(9);
    internal_static_api_ListUsersRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_api_ListUsersRequest_descriptor,
        new String[] { "Limit", "Offset", });
    internal_static_api_ListUsersResponse_descriptor =
      getDescriptor().getMessageTypes().get(10);
    internal_static_api_ListUsersResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_api_ListUsersResponse_descriptor,
        new String[] { "TotalCount", "Result", });
    internal_static_api_UpdateUserPasswordRequest_descriptor =
      getDescriptor().getMessageTypes().get(11);
    internal_static_api_UpdateUserPasswordRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_api_UpdateUserPasswordRequest_descriptor,
        new String[] { "UserId", "Password", });
    com.google.protobuf.ExtensionRegistry registry =
        com.google.protobuf.ExtensionRegistry.newInstance();
    registry.add(com.google.api.AnnotationsProto.http);
    com.google.protobuf.Descriptors.FileDescriptor
        .internalUpdateFileDescriptor(descriptor, registry);
    com.google.api.AnnotationsProto.getDescriptor();
    com.google.protobuf.TimestampProto.getDescriptor();
    com.google.protobuf.EmptyProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}