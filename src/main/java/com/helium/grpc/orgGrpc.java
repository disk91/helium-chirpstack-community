package com.helium.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.53.0)",
    comments = "Source: service/iot_config.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class orgGrpc {

  private orgGrpc() {}

  public static final String SERVICE_NAME = "helium.iot_config.org";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<org_list_req_v1,
      org_list_res_v1> getListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "list",
      requestType = org_list_req_v1.class,
      responseType = org_list_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org_list_req_v1,
      org_list_res_v1> getListMethod() {
    io.grpc.MethodDescriptor<org_list_req_v1, org_list_res_v1> getListMethod;
    if ((getListMethod = orgGrpc.getListMethod) == null) {
      synchronized (orgGrpc.class) {
        if ((getListMethod = orgGrpc.getListMethod) == null) {
          orgGrpc.getListMethod = getListMethod =
              io.grpc.MethodDescriptor.<org_list_req_v1, org_list_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "list"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org_list_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org_list_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new orgMethodDescriptorSupplier("list"))
              .build();
        }
      }
    }
    return getListMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org_get_req_v1,
      org_res_v1> getGetMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "get",
      requestType = org_get_req_v1.class,
      responseType = org_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org_get_req_v1,
      org_res_v1> getGetMethod() {
    io.grpc.MethodDescriptor<org_get_req_v1, org_res_v1> getGetMethod;
    if ((getGetMethod = orgGrpc.getGetMethod) == null) {
      synchronized (orgGrpc.class) {
        if ((getGetMethod = orgGrpc.getGetMethod) == null) {
          orgGrpc.getGetMethod = getGetMethod =
              io.grpc.MethodDescriptor.<org_get_req_v1, org_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "get"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org_get_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new orgMethodDescriptorSupplier("get"))
              .build();
        }
      }
    }
    return getGetMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org_create_helium_req_v1,
      org_res_v1> getCreateHeliumMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "create_helium",
      requestType = org_create_helium_req_v1.class,
      responseType = org_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org_create_helium_req_v1,
      org_res_v1> getCreateHeliumMethod() {
    io.grpc.MethodDescriptor<org_create_helium_req_v1, org_res_v1> getCreateHeliumMethod;
    if ((getCreateHeliumMethod = orgGrpc.getCreateHeliumMethod) == null) {
      synchronized (orgGrpc.class) {
        if ((getCreateHeliumMethod = orgGrpc.getCreateHeliumMethod) == null) {
          orgGrpc.getCreateHeliumMethod = getCreateHeliumMethod =
              io.grpc.MethodDescriptor.<org_create_helium_req_v1, org_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "create_helium"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org_create_helium_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new orgMethodDescriptorSupplier("create_helium"))
              .build();
        }
      }
    }
    return getCreateHeliumMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org_create_roamer_req_v1,
      org_res_v1> getCreateRoamerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "create_roamer",
      requestType = org_create_roamer_req_v1.class,
      responseType = org_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org_create_roamer_req_v1,
      org_res_v1> getCreateRoamerMethod() {
    io.grpc.MethodDescriptor<org_create_roamer_req_v1, org_res_v1> getCreateRoamerMethod;
    if ((getCreateRoamerMethod = orgGrpc.getCreateRoamerMethod) == null) {
      synchronized (orgGrpc.class) {
        if ((getCreateRoamerMethod = orgGrpc.getCreateRoamerMethod) == null) {
          orgGrpc.getCreateRoamerMethod = getCreateRoamerMethod =
              io.grpc.MethodDescriptor.<org_create_roamer_req_v1, org_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "create_roamer"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org_create_roamer_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new orgMethodDescriptorSupplier("create_roamer"))
              .build();
        }
      }
    }
    return getCreateRoamerMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org_update_req_v1,
      org_res_v1> getUpdateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "update",
      requestType = org_update_req_v1.class,
      responseType = org_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org_update_req_v1,
      org_res_v1> getUpdateMethod() {
    io.grpc.MethodDescriptor<org_update_req_v1, org_res_v1> getUpdateMethod;
    if ((getUpdateMethod = orgGrpc.getUpdateMethod) == null) {
      synchronized (orgGrpc.class) {
        if ((getUpdateMethod = orgGrpc.getUpdateMethod) == null) {
          orgGrpc.getUpdateMethod = getUpdateMethod =
              io.grpc.MethodDescriptor.<org_update_req_v1, org_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "update"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org_update_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new orgMethodDescriptorSupplier("update"))
              .build();
        }
      }
    }
    return getUpdateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org_disable_req_v1,
      org_disable_res_v1> getDisableMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "disable",
      requestType = org_disable_req_v1.class,
      responseType = org_disable_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org_disable_req_v1,
      org_disable_res_v1> getDisableMethod() {
    io.grpc.MethodDescriptor<org_disable_req_v1, org_disable_res_v1> getDisableMethod;
    if ((getDisableMethod = orgGrpc.getDisableMethod) == null) {
      synchronized (orgGrpc.class) {
        if ((getDisableMethod = orgGrpc.getDisableMethod) == null) {
          orgGrpc.getDisableMethod = getDisableMethod =
              io.grpc.MethodDescriptor.<org_disable_req_v1, org_disable_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "disable"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org_disable_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org_disable_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new orgMethodDescriptorSupplier("disable"))
              .build();
        }
      }
    }
    return getDisableMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org_enable_req_v1,
      org_enable_res_v1> getEnableMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "enable",
      requestType = org_enable_req_v1.class,
      responseType = org_enable_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org_enable_req_v1,
      org_enable_res_v1> getEnableMethod() {
    io.grpc.MethodDescriptor<org_enable_req_v1, org_enable_res_v1> getEnableMethod;
    if ((getEnableMethod = orgGrpc.getEnableMethod) == null) {
      synchronized (orgGrpc.class) {
        if ((getEnableMethod = orgGrpc.getEnableMethod) == null) {
          orgGrpc.getEnableMethod = getEnableMethod =
              io.grpc.MethodDescriptor.<org_enable_req_v1, org_enable_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "enable"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org_enable_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org_enable_res_v1.getDefaultInstance()))
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
        @Override
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
        @Override
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
        @Override
        public orgFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new orgFutureStub(channel, callOptions);
        }
      };
    return orgFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class orgImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * List Org (no auth)
     * </pre>
     */
    public void list(org_list_req_v1 request,
                     io.grpc.stub.StreamObserver<org_list_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListMethod(), responseObserver);
    }

    /**
     * <pre>
     * Get Org (no auth)
     * </pre>
     */
    public void get(org_get_req_v1 request,
                    io.grpc.stub.StreamObserver<org_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMethod(), responseObserver);
    }

    /**
     * <pre>
     * Create Org on Helium Network (auth admin only)
     * </pre>
     */
    public void createHelium(org_create_helium_req_v1 request,
                             io.grpc.stub.StreamObserver<org_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateHeliumMethod(), responseObserver);
    }

    /**
     * <pre>
     * Create Org on any network (auth admin only)
     * </pre>
     */
    public void createRoamer(org_create_roamer_req_v1 request,
                             io.grpc.stub.StreamObserver<org_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateRoamerMethod(), responseObserver);
    }

    /**
     * <pre>
     * Update any Org (Helium or Roaming)
     * Modify payer and add/remove delegate keys (owner/admin)
     * Modify owner and add/remove devaddr constraints (auth admin only)
     * </pre>
     */
    public void update(org_update_req_v1 request,
                       io.grpc.stub.StreamObserver<org_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateMethod(), responseObserver);
    }

    /**
     * <pre>
     * Disable an org, this sends a stream route delete update to HPR
     * for all associated routes (auth admin only)
     * </pre>
     */
    public void disable(org_disable_req_v1 request,
                        io.grpc.stub.StreamObserver<org_disable_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDisableMethod(), responseObserver);
    }

    /**
     * <pre>
     * Enable an org, this sends a stream route create update to HPR
     * for all associated routes (auth admin only)
     * </pre>
     */
    public void enable(org_enable_req_v1 request,
                       io.grpc.stub.StreamObserver<org_enable_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getEnableMethod(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getListMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                org_list_req_v1,
                org_list_res_v1>(
                  this, METHODID_LIST)))
          .addMethod(
            getGetMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                org_get_req_v1,
                org_res_v1>(
                  this, METHODID_GET)))
          .addMethod(
            getCreateHeliumMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                org_create_helium_req_v1,
                org_res_v1>(
                  this, METHODID_CREATE_HELIUM)))
          .addMethod(
            getCreateRoamerMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                org_create_roamer_req_v1,
                org_res_v1>(
                  this, METHODID_CREATE_ROAMER)))
          .addMethod(
            getUpdateMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                org_update_req_v1,
                org_res_v1>(
                  this, METHODID_UPDATE)))
          .addMethod(
            getDisableMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                org_disable_req_v1,
                org_disable_res_v1>(
                  this, METHODID_DISABLE)))
          .addMethod(
            getEnableMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                org_enable_req_v1,
                org_enable_res_v1>(
                  this, METHODID_ENABLE)))
          .build();
    }
  }

  /**
   */
  public static final class orgStub extends io.grpc.stub.AbstractAsyncStub<orgStub> {
    private orgStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected orgStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new orgStub(channel, callOptions);
    }

    /**
     * <pre>
     * List Org (no auth)
     * </pre>
     */
    public void list(org_list_req_v1 request,
                     io.grpc.stub.StreamObserver<org_list_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Get Org (no auth)
     * </pre>
     */
    public void get(org_get_req_v1 request,
                    io.grpc.stub.StreamObserver<org_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Create Org on Helium Network (auth admin only)
     * </pre>
     */
    public void createHelium(org_create_helium_req_v1 request,
                             io.grpc.stub.StreamObserver<org_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateHeliumMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Create Org on any network (auth admin only)
     * </pre>
     */
    public void createRoamer(org_create_roamer_req_v1 request,
                             io.grpc.stub.StreamObserver<org_res_v1> responseObserver) {
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
    public void update(org_update_req_v1 request,
                       io.grpc.stub.StreamObserver<org_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Disable an org, this sends a stream route delete update to HPR
     * for all associated routes (auth admin only)
     * </pre>
     */
    public void disable(org_disable_req_v1 request,
                        io.grpc.stub.StreamObserver<org_disable_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDisableMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Enable an org, this sends a stream route create update to HPR
     * for all associated routes (auth admin only)
     * </pre>
     */
    public void enable(org_enable_req_v1 request,
                       io.grpc.stub.StreamObserver<org_enable_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getEnableMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class orgBlockingStub extends io.grpc.stub.AbstractBlockingStub<orgBlockingStub> {
    private orgBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected orgBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new orgBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * List Org (no auth)
     * </pre>
     */
    public org_list_res_v1 list(org_list_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Get Org (no auth)
     * </pre>
     */
    public org_res_v1 get(org_get_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Create Org on Helium Network (auth admin only)
     * </pre>
     */
    public org_res_v1 createHelium(org_create_helium_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateHeliumMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Create Org on any network (auth admin only)
     * </pre>
     */
    public org_res_v1 createRoamer(org_create_roamer_req_v1 request) {
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
    public org_res_v1 update(org_update_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Disable an org, this sends a stream route delete update to HPR
     * for all associated routes (auth admin only)
     * </pre>
     */
    public org_disable_res_v1 disable(org_disable_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDisableMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Enable an org, this sends a stream route create update to HPR
     * for all associated routes (auth admin only)
     * </pre>
     */
    public org_enable_res_v1 enable(org_enable_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getEnableMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class orgFutureStub extends io.grpc.stub.AbstractFutureStub<orgFutureStub> {
    private orgFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected orgFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new orgFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * List Org (no auth)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<org_list_res_v1> list(
        org_list_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Get Org (no auth)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<org_res_v1> get(
        org_get_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Create Org on Helium Network (auth admin only)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<org_res_v1> createHelium(
        org_create_helium_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateHeliumMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Create Org on any network (auth admin only)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<org_res_v1> createRoamer(
        org_create_roamer_req_v1 request) {
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
    public com.google.common.util.concurrent.ListenableFuture<org_res_v1> update(
        org_update_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Disable an org, this sends a stream route delete update to HPR
     * for all associated routes (auth admin only)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<org_disable_res_v1> disable(
        org_disable_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDisableMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Enable an org, this sends a stream route create update to HPR
     * for all associated routes (auth admin only)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<org_enable_res_v1> enable(
        org_enable_req_v1 request) {
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
    private final orgImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(orgImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LIST:
          serviceImpl.list((org_list_req_v1) request,
              (io.grpc.stub.StreamObserver<org_list_res_v1>) responseObserver);
          break;
        case METHODID_GET:
          serviceImpl.get((org_get_req_v1) request,
              (io.grpc.stub.StreamObserver<org_res_v1>) responseObserver);
          break;
        case METHODID_CREATE_HELIUM:
          serviceImpl.createHelium((org_create_helium_req_v1) request,
              (io.grpc.stub.StreamObserver<org_res_v1>) responseObserver);
          break;
        case METHODID_CREATE_ROAMER:
          serviceImpl.createRoamer((org_create_roamer_req_v1) request,
              (io.grpc.stub.StreamObserver<org_res_v1>) responseObserver);
          break;
        case METHODID_UPDATE:
          serviceImpl.update((org_update_req_v1) request,
              (io.grpc.stub.StreamObserver<org_res_v1>) responseObserver);
          break;
        case METHODID_DISABLE:
          serviceImpl.disable((org_disable_req_v1) request,
              (io.grpc.stub.StreamObserver<org_disable_res_v1>) responseObserver);
          break;
        case METHODID_ENABLE:
          serviceImpl.enable((org_enable_req_v1) request,
              (io.grpc.stub.StreamObserver<org_enable_res_v1>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class orgBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    orgBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return IotConfig.getDescriptor();
    }

    @Override
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
    private final String methodName;

    orgMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @Override
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
