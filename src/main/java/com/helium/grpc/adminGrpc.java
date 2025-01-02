package com.helium.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@io.grpc.stub.annotations.GrpcGenerated
public final class adminGrpc {

  private adminGrpc() {}

  public static final java.lang.String SERVICE_NAME = "helium.iot_config.admin";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.helium.grpc.admin_add_key_req_v1,
      com.helium.grpc.admin_key_res_v1> getAddKeyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "add_key",
      requestType = com.helium.grpc.admin_add_key_req_v1.class,
      responseType = com.helium.grpc.admin_key_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.helium.grpc.admin_add_key_req_v1,
      com.helium.grpc.admin_key_res_v1> getAddKeyMethod() {
    io.grpc.MethodDescriptor<com.helium.grpc.admin_add_key_req_v1, com.helium.grpc.admin_key_res_v1> getAddKeyMethod;
    if ((getAddKeyMethod = adminGrpc.getAddKeyMethod) == null) {
      synchronized (adminGrpc.class) {
        if ((getAddKeyMethod = adminGrpc.getAddKeyMethod) == null) {
          adminGrpc.getAddKeyMethod = getAddKeyMethod =
              io.grpc.MethodDescriptor.<com.helium.grpc.admin_add_key_req_v1, com.helium.grpc.admin_key_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "add_key"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.helium.grpc.admin_add_key_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.helium.grpc.admin_key_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new adminMethodDescriptorSupplier("add_key"))
              .build();
        }
      }
    }
    return getAddKeyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.helium.grpc.admin_remove_key_req_v1,
      com.helium.grpc.admin_key_res_v1> getRemoveKeyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "remove_key",
      requestType = com.helium.grpc.admin_remove_key_req_v1.class,
      responseType = com.helium.grpc.admin_key_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.helium.grpc.admin_remove_key_req_v1,
      com.helium.grpc.admin_key_res_v1> getRemoveKeyMethod() {
    io.grpc.MethodDescriptor<com.helium.grpc.admin_remove_key_req_v1, com.helium.grpc.admin_key_res_v1> getRemoveKeyMethod;
    if ((getRemoveKeyMethod = adminGrpc.getRemoveKeyMethod) == null) {
      synchronized (adminGrpc.class) {
        if ((getRemoveKeyMethod = adminGrpc.getRemoveKeyMethod) == null) {
          adminGrpc.getRemoveKeyMethod = getRemoveKeyMethod =
              io.grpc.MethodDescriptor.<com.helium.grpc.admin_remove_key_req_v1, com.helium.grpc.admin_key_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "remove_key"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.helium.grpc.admin_remove_key_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.helium.grpc.admin_key_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new adminMethodDescriptorSupplier("remove_key"))
              .build();
        }
      }
    }
    return getRemoveKeyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.helium.grpc.admin_load_region_req_v1,
      com.helium.grpc.admin_load_region_res_v1> getLoadRegionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "load_region",
      requestType = com.helium.grpc.admin_load_region_req_v1.class,
      responseType = com.helium.grpc.admin_load_region_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.helium.grpc.admin_load_region_req_v1,
      com.helium.grpc.admin_load_region_res_v1> getLoadRegionMethod() {
    io.grpc.MethodDescriptor<com.helium.grpc.admin_load_region_req_v1, com.helium.grpc.admin_load_region_res_v1> getLoadRegionMethod;
    if ((getLoadRegionMethod = adminGrpc.getLoadRegionMethod) == null) {
      synchronized (adminGrpc.class) {
        if ((getLoadRegionMethod = adminGrpc.getLoadRegionMethod) == null) {
          adminGrpc.getLoadRegionMethod = getLoadRegionMethod =
              io.grpc.MethodDescriptor.<com.helium.grpc.admin_load_region_req_v1, com.helium.grpc.admin_load_region_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "load_region"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.helium.grpc.admin_load_region_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.helium.grpc.admin_load_region_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new adminMethodDescriptorSupplier("load_region"))
              .build();
        }
      }
    }
    return getLoadRegionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.helium.grpc.region_params_req_v1,
      com.helium.grpc.region_params_res_v1> getRegionParamsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "region_params",
      requestType = com.helium.grpc.region_params_req_v1.class,
      responseType = com.helium.grpc.region_params_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.helium.grpc.region_params_req_v1,
      com.helium.grpc.region_params_res_v1> getRegionParamsMethod() {
    io.grpc.MethodDescriptor<com.helium.grpc.region_params_req_v1, com.helium.grpc.region_params_res_v1> getRegionParamsMethod;
    if ((getRegionParamsMethod = adminGrpc.getRegionParamsMethod) == null) {
      synchronized (adminGrpc.class) {
        if ((getRegionParamsMethod = adminGrpc.getRegionParamsMethod) == null) {
          adminGrpc.getRegionParamsMethod = getRegionParamsMethod =
              io.grpc.MethodDescriptor.<com.helium.grpc.region_params_req_v1, com.helium.grpc.region_params_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "region_params"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.helium.grpc.region_params_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.helium.grpc.region_params_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new adminMethodDescriptorSupplier("region_params"))
              .build();
        }
      }
    }
    return getRegionParamsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static adminStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<adminStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<adminStub>() {
        @java.lang.Override
        public adminStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new adminStub(channel, callOptions);
        }
      };
    return adminStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static adminBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<adminBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<adminBlockingStub>() {
        @java.lang.Override
        public adminBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new adminBlockingStub(channel, callOptions);
        }
      };
    return adminBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static adminFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<adminFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<adminFutureStub>() {
        @java.lang.Override
        public adminFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new adminFutureStub(channel, callOptions);
        }
      };
    return adminFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     * <pre>
     * Authorize a public key for validating trusted rpcs
     * </pre>
     */
    default void addKey(com.helium.grpc.admin_add_key_req_v1 request,
        io.grpc.stub.StreamObserver<com.helium.grpc.admin_key_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAddKeyMethod(), responseObserver);
    }

    /**
     * <pre>
     * Deauthorize a public key for validating trusted rpcs
     * </pre>
     */
    default void removeKey(com.helium.grpc.admin_remove_key_req_v1 request,
        io.grpc.stub.StreamObserver<com.helium.grpc.admin_key_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRemoveKeyMethod(), responseObserver);
    }

    /**
     * <pre>
     * Load params and cell indexes for a region into the config service (auth
     * admin only)
     * </pre>
     */
    default void loadRegion(com.helium.grpc.admin_load_region_req_v1 request,
        io.grpc.stub.StreamObserver<com.helium.grpc.admin_load_region_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getLoadRegionMethod(), responseObserver);
    }

    /**
     * <pre>
     * Return the region params for the specified region
     * </pre>
     */
    default void regionParams(com.helium.grpc.region_params_req_v1 request,
        io.grpc.stub.StreamObserver<com.helium.grpc.region_params_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegionParamsMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service admin.
   */
  public static abstract class adminImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return adminGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service admin.
   */
  public static final class adminStub
      extends io.grpc.stub.AbstractAsyncStub<adminStub> {
    private adminStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected adminStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new adminStub(channel, callOptions);
    }

    /**
     * <pre>
     * Authorize a public key for validating trusted rpcs
     * </pre>
     */
    public void addKey(com.helium.grpc.admin_add_key_req_v1 request,
        io.grpc.stub.StreamObserver<com.helium.grpc.admin_key_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAddKeyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Deauthorize a public key for validating trusted rpcs
     * </pre>
     */
    public void removeKey(com.helium.grpc.admin_remove_key_req_v1 request,
        io.grpc.stub.StreamObserver<com.helium.grpc.admin_key_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRemoveKeyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Load params and cell indexes for a region into the config service (auth
     * admin only)
     * </pre>
     */
    public void loadRegion(com.helium.grpc.admin_load_region_req_v1 request,
        io.grpc.stub.StreamObserver<com.helium.grpc.admin_load_region_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getLoadRegionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Return the region params for the specified region
     * </pre>
     */
    public void regionParams(com.helium.grpc.region_params_req_v1 request,
        io.grpc.stub.StreamObserver<com.helium.grpc.region_params_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegionParamsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service admin.
   */
  public static final class adminBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<adminBlockingStub> {
    private adminBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected adminBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new adminBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Authorize a public key for validating trusted rpcs
     * </pre>
     */
    public com.helium.grpc.admin_key_res_v1 addKey(com.helium.grpc.admin_add_key_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAddKeyMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Deauthorize a public key for validating trusted rpcs
     * </pre>
     */
    public com.helium.grpc.admin_key_res_v1 removeKey(com.helium.grpc.admin_remove_key_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRemoveKeyMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Load params and cell indexes for a region into the config service (auth
     * admin only)
     * </pre>
     */
    public com.helium.grpc.admin_load_region_res_v1 loadRegion(com.helium.grpc.admin_load_region_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getLoadRegionMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Return the region params for the specified region
     * </pre>
     */
    public com.helium.grpc.region_params_res_v1 regionParams(com.helium.grpc.region_params_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegionParamsMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service admin.
   */
  public static final class adminFutureStub
      extends io.grpc.stub.AbstractFutureStub<adminFutureStub> {
    private adminFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected adminFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new adminFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Authorize a public key for validating trusted rpcs
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.helium.grpc.admin_key_res_v1> addKey(
        com.helium.grpc.admin_add_key_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAddKeyMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Deauthorize a public key for validating trusted rpcs
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.helium.grpc.admin_key_res_v1> removeKey(
        com.helium.grpc.admin_remove_key_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRemoveKeyMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Load params and cell indexes for a region into the config service (auth
     * admin only)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.helium.grpc.admin_load_region_res_v1> loadRegion(
        com.helium.grpc.admin_load_region_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getLoadRegionMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Return the region params for the specified region
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.helium.grpc.region_params_res_v1> regionParams(
        com.helium.grpc.region_params_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegionParamsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ADD_KEY = 0;
  private static final int METHODID_REMOVE_KEY = 1;
  private static final int METHODID_LOAD_REGION = 2;
  private static final int METHODID_REGION_PARAMS = 3;

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
        case METHODID_ADD_KEY:
          serviceImpl.addKey((com.helium.grpc.admin_add_key_req_v1) request,
              (io.grpc.stub.StreamObserver<com.helium.grpc.admin_key_res_v1>) responseObserver);
          break;
        case METHODID_REMOVE_KEY:
          serviceImpl.removeKey((com.helium.grpc.admin_remove_key_req_v1) request,
              (io.grpc.stub.StreamObserver<com.helium.grpc.admin_key_res_v1>) responseObserver);
          break;
        case METHODID_LOAD_REGION:
          serviceImpl.loadRegion((com.helium.grpc.admin_load_region_req_v1) request,
              (io.grpc.stub.StreamObserver<com.helium.grpc.admin_load_region_res_v1>) responseObserver);
          break;
        case METHODID_REGION_PARAMS:
          serviceImpl.regionParams((com.helium.grpc.region_params_req_v1) request,
              (io.grpc.stub.StreamObserver<com.helium.grpc.region_params_res_v1>) responseObserver);
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
          getAddKeyMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.helium.grpc.admin_add_key_req_v1,
              com.helium.grpc.admin_key_res_v1>(
                service, METHODID_ADD_KEY)))
        .addMethod(
          getRemoveKeyMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.helium.grpc.admin_remove_key_req_v1,
              com.helium.grpc.admin_key_res_v1>(
                service, METHODID_REMOVE_KEY)))
        .addMethod(
          getLoadRegionMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.helium.grpc.admin_load_region_req_v1,
              com.helium.grpc.admin_load_region_res_v1>(
                service, METHODID_LOAD_REGION)))
        .addMethod(
          getRegionParamsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.helium.grpc.region_params_req_v1,
              com.helium.grpc.region_params_res_v1>(
                service, METHODID_REGION_PARAMS)))
        .build();
  }

  private static abstract class adminBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    adminBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.helium.grpc.IotConfig.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("admin");
    }
  }

  private static final class adminFileDescriptorSupplier
      extends adminBaseDescriptorSupplier {
    adminFileDescriptorSupplier() {}
  }

  private static final class adminMethodDescriptorSupplier
      extends adminBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    adminMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (adminGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new adminFileDescriptorSupplier())
              .addMethod(getAddKeyMethod())
              .addMethod(getRemoveKeyMethod())
              .addMethod(getLoadRegionMethod())
              .addMethod(getRegionParamsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
