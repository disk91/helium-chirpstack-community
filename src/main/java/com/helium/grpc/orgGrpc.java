package com.helium.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@io.grpc.stub.annotations.GrpcGenerated
public final class orgGrpc {

  private orgGrpc() {}

  public static final java.lang.String SERVICE_NAME = "helium.iot_config.org";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.helium.grpc.org_list_req_v1,
      com.helium.grpc.org_list_res_v1> getListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "list",
      requestType = com.helium.grpc.org_list_req_v1.class,
      responseType = com.helium.grpc.org_list_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.helium.grpc.org_list_req_v1,
      com.helium.grpc.org_list_res_v1> getListMethod() {
    io.grpc.MethodDescriptor<com.helium.grpc.org_list_req_v1, com.helium.grpc.org_list_res_v1> getListMethod;
    if ((getListMethod = orgGrpc.getListMethod) == null) {
      synchronized (orgGrpc.class) {
        if ((getListMethod = orgGrpc.getListMethod) == null) {
          orgGrpc.getListMethod = getListMethod =
              io.grpc.MethodDescriptor.<com.helium.grpc.org_list_req_v1, com.helium.grpc.org_list_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "list"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.helium.grpc.org_list_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.helium.grpc.org_list_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new orgMethodDescriptorSupplier("list"))
              .build();
        }
      }
    }
    return getListMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.helium.grpc.org_get_req_v1,
      com.helium.grpc.org_res_v1> getGetMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "get",
      requestType = com.helium.grpc.org_get_req_v1.class,
      responseType = com.helium.grpc.org_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.helium.grpc.org_get_req_v1,
      com.helium.grpc.org_res_v1> getGetMethod() {
    io.grpc.MethodDescriptor<com.helium.grpc.org_get_req_v1, com.helium.grpc.org_res_v1> getGetMethod;
    if ((getGetMethod = orgGrpc.getGetMethod) == null) {
      synchronized (orgGrpc.class) {
        if ((getGetMethod = orgGrpc.getGetMethod) == null) {
          orgGrpc.getGetMethod = getGetMethod =
              io.grpc.MethodDescriptor.<com.helium.grpc.org_get_req_v1, com.helium.grpc.org_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "get"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.helium.grpc.org_get_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.helium.grpc.org_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new orgMethodDescriptorSupplier("get"))
              .build();
        }
      }
    }
    return getGetMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.helium.grpc.org_create_helium_req_v1,
      com.helium.grpc.org_res_v1> getCreateHeliumMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "create_helium",
      requestType = com.helium.grpc.org_create_helium_req_v1.class,
      responseType = com.helium.grpc.org_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.helium.grpc.org_create_helium_req_v1,
      com.helium.grpc.org_res_v1> getCreateHeliumMethod() {
    io.grpc.MethodDescriptor<com.helium.grpc.org_create_helium_req_v1, com.helium.grpc.org_res_v1> getCreateHeliumMethod;
    if ((getCreateHeliumMethod = orgGrpc.getCreateHeliumMethod) == null) {
      synchronized (orgGrpc.class) {
        if ((getCreateHeliumMethod = orgGrpc.getCreateHeliumMethod) == null) {
          orgGrpc.getCreateHeliumMethod = getCreateHeliumMethod =
              io.grpc.MethodDescriptor.<com.helium.grpc.org_create_helium_req_v1, com.helium.grpc.org_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "create_helium"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.helium.grpc.org_create_helium_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.helium.grpc.org_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new orgMethodDescriptorSupplier("create_helium"))
              .build();
        }
      }
    }
    return getCreateHeliumMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.helium.grpc.org_create_roamer_req_v1,
      com.helium.grpc.org_res_v1> getCreateRoamerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "create_roamer",
      requestType = com.helium.grpc.org_create_roamer_req_v1.class,
      responseType = com.helium.grpc.org_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.helium.grpc.org_create_roamer_req_v1,
      com.helium.grpc.org_res_v1> getCreateRoamerMethod() {
    io.grpc.MethodDescriptor<com.helium.grpc.org_create_roamer_req_v1, com.helium.grpc.org_res_v1> getCreateRoamerMethod;
    if ((getCreateRoamerMethod = orgGrpc.getCreateRoamerMethod) == null) {
      synchronized (orgGrpc.class) {
        if ((getCreateRoamerMethod = orgGrpc.getCreateRoamerMethod) == null) {
          orgGrpc.getCreateRoamerMethod = getCreateRoamerMethod =
              io.grpc.MethodDescriptor.<com.helium.grpc.org_create_roamer_req_v1, com.helium.grpc.org_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "create_roamer"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.helium.grpc.org_create_roamer_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.helium.grpc.org_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new orgMethodDescriptorSupplier("create_roamer"))
              .build();
        }
      }
    }
    return getCreateRoamerMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.helium.grpc.org_update_req_v1,
      com.helium.grpc.org_res_v1> getUpdateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "update",
      requestType = com.helium.grpc.org_update_req_v1.class,
      responseType = com.helium.grpc.org_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.helium.grpc.org_update_req_v1,
      com.helium.grpc.org_res_v1> getUpdateMethod() {
    io.grpc.MethodDescriptor<com.helium.grpc.org_update_req_v1, com.helium.grpc.org_res_v1> getUpdateMethod;
    if ((getUpdateMethod = orgGrpc.getUpdateMethod) == null) {
      synchronized (orgGrpc.class) {
        if ((getUpdateMethod = orgGrpc.getUpdateMethod) == null) {
          orgGrpc.getUpdateMethod = getUpdateMethod =
              io.grpc.MethodDescriptor.<com.helium.grpc.org_update_req_v1, com.helium.grpc.org_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "update"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.helium.grpc.org_update_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.helium.grpc.org_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new orgMethodDescriptorSupplier("update"))
              .build();
        }
      }
    }
    return getUpdateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.helium.grpc.org_disable_req_v1,
      com.helium.grpc.org_disable_res_v1> getDisableMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "disable",
      requestType = com.helium.grpc.org_disable_req_v1.class,
      responseType = com.helium.grpc.org_disable_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.helium.grpc.org_disable_req_v1,
      com.helium.grpc.org_disable_res_v1> getDisableMethod() {
    io.grpc.MethodDescriptor<com.helium.grpc.org_disable_req_v1, com.helium.grpc.org_disable_res_v1> getDisableMethod;
    if ((getDisableMethod = orgGrpc.getDisableMethod) == null) {
      synchronized (orgGrpc.class) {
        if ((getDisableMethod = orgGrpc.getDisableMethod) == null) {
          orgGrpc.getDisableMethod = getDisableMethod =
              io.grpc.MethodDescriptor.<com.helium.grpc.org_disable_req_v1, com.helium.grpc.org_disable_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "disable"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.helium.grpc.org_disable_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.helium.grpc.org_disable_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new orgMethodDescriptorSupplier("disable"))
              .build();
        }
      }
    }
    return getDisableMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.helium.grpc.org_enable_req_v1,
      com.helium.grpc.org_enable_res_v1> getEnableMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "enable",
      requestType = com.helium.grpc.org_enable_req_v1.class,
      responseType = com.helium.grpc.org_enable_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.helium.grpc.org_enable_req_v1,
      com.helium.grpc.org_enable_res_v1> getEnableMethod() {
    io.grpc.MethodDescriptor<com.helium.grpc.org_enable_req_v1, com.helium.grpc.org_enable_res_v1> getEnableMethod;
    if ((getEnableMethod = orgGrpc.getEnableMethod) == null) {
      synchronized (orgGrpc.class) {
        if ((getEnableMethod = orgGrpc.getEnableMethod) == null) {
          orgGrpc.getEnableMethod = getEnableMethod =
              io.grpc.MethodDescriptor.<com.helium.grpc.org_enable_req_v1, com.helium.grpc.org_enable_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "enable"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.helium.grpc.org_enable_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.helium.grpc.org_enable_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new orgMethodDescriptorSupplier("enable"))
              .build();
        }
      }
    }
    return getEnableMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static orgStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<orgStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<orgStub>() {
        @java.lang.Override
        public orgStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new orgStub(channel, callOptions);
        }
      };
    return orgStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static orgBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<orgBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<orgBlockingStub>() {
        @java.lang.Override
        public orgBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new orgBlockingStub(channel, callOptions);
        }
      };
    return orgBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static orgFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<orgFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<orgFutureStub>() {
        @java.lang.Override
        public orgFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new orgFutureStub(channel, callOptions);
        }
      };
    return orgFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     * <pre>
     * List Org (no auth)
     * </pre>
     */
    default void list(com.helium.grpc.org_list_req_v1 request,
        io.grpc.stub.StreamObserver<com.helium.grpc.org_list_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListMethod(), responseObserver);
    }

    /**
     * <pre>
     * Get Org (no auth)
     * </pre>
     */
    default void get(com.helium.grpc.org_get_req_v1 request,
        io.grpc.stub.StreamObserver<com.helium.grpc.org_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMethod(), responseObserver);
    }

    /**
     * <pre>
     * Create Org on Helium Network (auth admin only)
     * </pre>
     */
    default void createHelium(com.helium.grpc.org_create_helium_req_v1 request,
        io.grpc.stub.StreamObserver<com.helium.grpc.org_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateHeliumMethod(), responseObserver);
    }

    /**
     * <pre>
     * Create Org on any network (auth admin only)
     * </pre>
     */
    default void createRoamer(com.helium.grpc.org_create_roamer_req_v1 request,
        io.grpc.stub.StreamObserver<com.helium.grpc.org_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateRoamerMethod(), responseObserver);
    }

    /**
     * <pre>
     * Update any Org (Helium or Roaming)
     * Modify payer and add/remove delegate keys (owner/admin)
     * Modify owner and add/remove devaddr constraints (auth admin only)
     * </pre>
     */
    default void update(com.helium.grpc.org_update_req_v1 request,
        io.grpc.stub.StreamObserver<com.helium.grpc.org_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateMethod(), responseObserver);
    }

    /**
     * <pre>
     * Disable an org, this sends a stream route delete update to HPR
     * for all associated routes (auth admin only)
     * </pre>
     */
    default void disable(com.helium.grpc.org_disable_req_v1 request,
        io.grpc.stub.StreamObserver<com.helium.grpc.org_disable_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDisableMethod(), responseObserver);
    }

    /**
     * <pre>
     * Enable an org, this sends a stream route create update to HPR
     * for all associated routes (auth admin only)
     * </pre>
     */
    default void enable(com.helium.grpc.org_enable_req_v1 request,
        io.grpc.stub.StreamObserver<com.helium.grpc.org_enable_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getEnableMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service org.
   */
  public static abstract class orgImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return orgGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service org.
   */
  public static final class orgStub
      extends io.grpc.stub.AbstractAsyncStub<orgStub> {
    private orgStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected orgStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new orgStub(channel, callOptions);
    }

    /**
     * <pre>
     * List Org (no auth)
     * </pre>
     */
    public void list(com.helium.grpc.org_list_req_v1 request,
        io.grpc.stub.StreamObserver<com.helium.grpc.org_list_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Get Org (no auth)
     * </pre>
     */
    public void get(com.helium.grpc.org_get_req_v1 request,
        io.grpc.stub.StreamObserver<com.helium.grpc.org_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Create Org on Helium Network (auth admin only)
     * </pre>
     */
    public void createHelium(com.helium.grpc.org_create_helium_req_v1 request,
        io.grpc.stub.StreamObserver<com.helium.grpc.org_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateHeliumMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Create Org on any network (auth admin only)
     * </pre>
     */
    public void createRoamer(com.helium.grpc.org_create_roamer_req_v1 request,
        io.grpc.stub.StreamObserver<com.helium.grpc.org_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateRoamerMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Update any Org (Helium or Roaming)
     * Modify payer and add/remove delegate keys (owner/admin)
     * Modify owner and add/remove devaddr constraints (auth admin only)
     * </pre>
     */
    public void update(com.helium.grpc.org_update_req_v1 request,
        io.grpc.stub.StreamObserver<com.helium.grpc.org_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Disable an org, this sends a stream route delete update to HPR
     * for all associated routes (auth admin only)
     * </pre>
     */
    public void disable(com.helium.grpc.org_disable_req_v1 request,
        io.grpc.stub.StreamObserver<com.helium.grpc.org_disable_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDisableMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Enable an org, this sends a stream route create update to HPR
     * for all associated routes (auth admin only)
     * </pre>
     */
    public void enable(com.helium.grpc.org_enable_req_v1 request,
        io.grpc.stub.StreamObserver<com.helium.grpc.org_enable_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getEnableMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service org.
   */
  public static final class orgBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<orgBlockingStub> {
    private orgBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected orgBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new orgBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * List Org (no auth)
     * </pre>
     */
    public com.helium.grpc.org_list_res_v1 list(com.helium.grpc.org_list_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Get Org (no auth)
     * </pre>
     */
    public com.helium.grpc.org_res_v1 get(com.helium.grpc.org_get_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Create Org on Helium Network (auth admin only)
     * </pre>
     */
    public com.helium.grpc.org_res_v1 createHelium(com.helium.grpc.org_create_helium_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateHeliumMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Create Org on any network (auth admin only)
     * </pre>
     */
    public com.helium.grpc.org_res_v1 createRoamer(com.helium.grpc.org_create_roamer_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateRoamerMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Update any Org (Helium or Roaming)
     * Modify payer and add/remove delegate keys (owner/admin)
     * Modify owner and add/remove devaddr constraints (auth admin only)
     * </pre>
     */
    public com.helium.grpc.org_res_v1 update(com.helium.grpc.org_update_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Disable an org, this sends a stream route delete update to HPR
     * for all associated routes (auth admin only)
     * </pre>
     */
    public com.helium.grpc.org_disable_res_v1 disable(com.helium.grpc.org_disable_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDisableMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Enable an org, this sends a stream route create update to HPR
     * for all associated routes (auth admin only)
     * </pre>
     */
    public com.helium.grpc.org_enable_res_v1 enable(com.helium.grpc.org_enable_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getEnableMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service org.
   */
  public static final class orgFutureStub
      extends io.grpc.stub.AbstractFutureStub<orgFutureStub> {
    private orgFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected orgFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new orgFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * List Org (no auth)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.helium.grpc.org_list_res_v1> list(
        com.helium.grpc.org_list_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Get Org (no auth)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.helium.grpc.org_res_v1> get(
        com.helium.grpc.org_get_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Create Org on Helium Network (auth admin only)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.helium.grpc.org_res_v1> createHelium(
        com.helium.grpc.org_create_helium_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateHeliumMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Create Org on any network (auth admin only)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.helium.grpc.org_res_v1> createRoamer(
        com.helium.grpc.org_create_roamer_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateRoamerMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Update any Org (Helium or Roaming)
     * Modify payer and add/remove delegate keys (owner/admin)
     * Modify owner and add/remove devaddr constraints (auth admin only)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.helium.grpc.org_res_v1> update(
        com.helium.grpc.org_update_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Disable an org, this sends a stream route delete update to HPR
     * for all associated routes (auth admin only)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.helium.grpc.org_disable_res_v1> disable(
        com.helium.grpc.org_disable_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDisableMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Enable an org, this sends a stream route create update to HPR
     * for all associated routes (auth admin only)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.helium.grpc.org_enable_res_v1> enable(
        com.helium.grpc.org_enable_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getEnableMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LIST = 0;
  private static final int METHODID_GET = 1;
  private static final int METHODID_CREATE_HELIUM = 2;
  private static final int METHODID_CREATE_ROAMER = 3;
  private static final int METHODID_UPDATE = 4;
  private static final int METHODID_DISABLE = 5;
  private static final int METHODID_ENABLE = 6;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LIST:
          serviceImpl.list((com.helium.grpc.org_list_req_v1) request,
              (io.grpc.stub.StreamObserver<com.helium.grpc.org_list_res_v1>) responseObserver);
          break;
        case METHODID_GET:
          serviceImpl.get((com.helium.grpc.org_get_req_v1) request,
              (io.grpc.stub.StreamObserver<com.helium.grpc.org_res_v1>) responseObserver);
          break;
        case METHODID_CREATE_HELIUM:
          serviceImpl.createHelium((com.helium.grpc.org_create_helium_req_v1) request,
              (io.grpc.stub.StreamObserver<com.helium.grpc.org_res_v1>) responseObserver);
          break;
        case METHODID_CREATE_ROAMER:
          serviceImpl.createRoamer((com.helium.grpc.org_create_roamer_req_v1) request,
              (io.grpc.stub.StreamObserver<com.helium.grpc.org_res_v1>) responseObserver);
          break;
        case METHODID_UPDATE:
          serviceImpl.update((com.helium.grpc.org_update_req_v1) request,
              (io.grpc.stub.StreamObserver<com.helium.grpc.org_res_v1>) responseObserver);
          break;
        case METHODID_DISABLE:
          serviceImpl.disable((com.helium.grpc.org_disable_req_v1) request,
              (io.grpc.stub.StreamObserver<com.helium.grpc.org_disable_res_v1>) responseObserver);
          break;
        case METHODID_ENABLE:
          serviceImpl.enable((com.helium.grpc.org_enable_req_v1) request,
              (io.grpc.stub.StreamObserver<com.helium.grpc.org_enable_res_v1>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getListMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.helium.grpc.org_list_req_v1,
              com.helium.grpc.org_list_res_v1>(
                service, METHODID_LIST)))
        .addMethod(
          getGetMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.helium.grpc.org_get_req_v1,
              com.helium.grpc.org_res_v1>(
                service, METHODID_GET)))
        .addMethod(
          getCreateHeliumMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.helium.grpc.org_create_helium_req_v1,
              com.helium.grpc.org_res_v1>(
                service, METHODID_CREATE_HELIUM)))
        .addMethod(
          getCreateRoamerMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.helium.grpc.org_create_roamer_req_v1,
              com.helium.grpc.org_res_v1>(
                service, METHODID_CREATE_ROAMER)))
        .addMethod(
          getUpdateMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.helium.grpc.org_update_req_v1,
              com.helium.grpc.org_res_v1>(
                service, METHODID_UPDATE)))
        .addMethod(
          getDisableMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.helium.grpc.org_disable_req_v1,
              com.helium.grpc.org_disable_res_v1>(
                service, METHODID_DISABLE)))
        .addMethod(
          getEnableMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.helium.grpc.org_enable_req_v1,
              com.helium.grpc.org_enable_res_v1>(
                service, METHODID_ENABLE)))
        .build();
  }

  private static abstract class orgBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    orgBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.helium.grpc.IotConfig.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("org");
    }
  }

  private static final class orgFileDescriptorSupplier
      extends orgBaseDescriptorSupplier {
    orgFileDescriptorSupplier() {}
  }

  private static final class orgMethodDescriptorSupplier
      extends orgBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    orgMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (orgGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new orgFileDescriptorSupplier())
              .addMethod(getListMethod())
              .addMethod(getGetMethod())
              .addMethod(getCreateHeliumMethod())
              .addMethod(getCreateRoamerMethod())
              .addMethod(getUpdateMethod())
              .addMethod(getDisableMethod())
              .addMethod(getEnableMethod())
              .build();
        }
      }
    }
    return result;
  }
}
