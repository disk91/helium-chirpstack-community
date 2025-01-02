package com.helium.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@io.grpc.stub.annotations.GrpcGenerated
public final class gatewayGrpc {

  private gatewayGrpc() {}

  public static final java.lang.String SERVICE_NAME = "helium.iot_config.gateway";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.helium.grpc.gateway_region_params_req_v1,
      com.helium.grpc.gateway_region_params_res_v1> getRegionParamsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "region_params",
      requestType = com.helium.grpc.gateway_region_params_req_v1.class,
      responseType = com.helium.grpc.gateway_region_params_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.helium.grpc.gateway_region_params_req_v1,
      com.helium.grpc.gateway_region_params_res_v1> getRegionParamsMethod() {
    io.grpc.MethodDescriptor<com.helium.grpc.gateway_region_params_req_v1, com.helium.grpc.gateway_region_params_res_v1> getRegionParamsMethod;
    if ((getRegionParamsMethod = gatewayGrpc.getRegionParamsMethod) == null) {
      synchronized (gatewayGrpc.class) {
        if ((getRegionParamsMethod = gatewayGrpc.getRegionParamsMethod) == null) {
          gatewayGrpc.getRegionParamsMethod = getRegionParamsMethod =
              io.grpc.MethodDescriptor.<com.helium.grpc.gateway_region_params_req_v1, com.helium.grpc.gateway_region_params_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "region_params"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.helium.grpc.gateway_region_params_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.helium.grpc.gateway_region_params_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new gatewayMethodDescriptorSupplier("region_params"))
              .build();
        }
      }
    }
    return getRegionParamsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.helium.grpc.gateway_location_req_v1,
      com.helium.grpc.gateway_location_res_v1> getLocationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "location",
      requestType = com.helium.grpc.gateway_location_req_v1.class,
      responseType = com.helium.grpc.gateway_location_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.helium.grpc.gateway_location_req_v1,
      com.helium.grpc.gateway_location_res_v1> getLocationMethod() {
    io.grpc.MethodDescriptor<com.helium.grpc.gateway_location_req_v1, com.helium.grpc.gateway_location_res_v1> getLocationMethod;
    if ((getLocationMethod = gatewayGrpc.getLocationMethod) == null) {
      synchronized (gatewayGrpc.class) {
        if ((getLocationMethod = gatewayGrpc.getLocationMethod) == null) {
          gatewayGrpc.getLocationMethod = getLocationMethod =
              io.grpc.MethodDescriptor.<com.helium.grpc.gateway_location_req_v1, com.helium.grpc.gateway_location_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "location"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.helium.grpc.gateway_location_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.helium.grpc.gateway_location_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new gatewayMethodDescriptorSupplier("location"))
              .build();
        }
      }
    }
    return getLocationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.helium.grpc.gateway_info_req_v1,
      com.helium.grpc.gateway_info_res_v1> getInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "info",
      requestType = com.helium.grpc.gateway_info_req_v1.class,
      responseType = com.helium.grpc.gateway_info_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.helium.grpc.gateway_info_req_v1,
      com.helium.grpc.gateway_info_res_v1> getInfoMethod() {
    io.grpc.MethodDescriptor<com.helium.grpc.gateway_info_req_v1, com.helium.grpc.gateway_info_res_v1> getInfoMethod;
    if ((getInfoMethod = gatewayGrpc.getInfoMethod) == null) {
      synchronized (gatewayGrpc.class) {
        if ((getInfoMethod = gatewayGrpc.getInfoMethod) == null) {
          gatewayGrpc.getInfoMethod = getInfoMethod =
              io.grpc.MethodDescriptor.<com.helium.grpc.gateway_info_req_v1, com.helium.grpc.gateway_info_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "info"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.helium.grpc.gateway_info_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.helium.grpc.gateway_info_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new gatewayMethodDescriptorSupplier("info"))
              .build();
        }
      }
    }
    return getInfoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.helium.grpc.gateway_info_stream_req_v1,
      com.helium.grpc.gateway_info_stream_res_v1> getInfoStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "info_stream",
      requestType = com.helium.grpc.gateway_info_stream_req_v1.class,
      responseType = com.helium.grpc.gateway_info_stream_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.helium.grpc.gateway_info_stream_req_v1,
      com.helium.grpc.gateway_info_stream_res_v1> getInfoStreamMethod() {
    io.grpc.MethodDescriptor<com.helium.grpc.gateway_info_stream_req_v1, com.helium.grpc.gateway_info_stream_res_v1> getInfoStreamMethod;
    if ((getInfoStreamMethod = gatewayGrpc.getInfoStreamMethod) == null) {
      synchronized (gatewayGrpc.class) {
        if ((getInfoStreamMethod = gatewayGrpc.getInfoStreamMethod) == null) {
          gatewayGrpc.getInfoStreamMethod = getInfoStreamMethod =
              io.grpc.MethodDescriptor.<com.helium.grpc.gateway_info_stream_req_v1, com.helium.grpc.gateway_info_stream_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "info_stream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.helium.grpc.gateway_info_stream_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.helium.grpc.gateway_info_stream_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new gatewayMethodDescriptorSupplier("info_stream"))
              .build();
        }
      }
    }
    return getInfoStreamMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static gatewayStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<gatewayStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<gatewayStub>() {
        @java.lang.Override
        public gatewayStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new gatewayStub(channel, callOptions);
        }
      };
    return gatewayStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static gatewayBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<gatewayBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<gatewayBlockingStub>() {
        @java.lang.Override
        public gatewayBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new gatewayBlockingStub(channel, callOptions);
        }
      };
    return gatewayBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static gatewayFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<gatewayFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<gatewayFutureStub>() {
        @java.lang.Override
        public gatewayFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new gatewayFutureStub(channel, callOptions);
        }
      };
    return gatewayFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     * <pre>
     * Return the region params for the asserted location of the signed gateway
     * address (no auth, but signature validated)
     * </pre>
     */
    default void regionParams(com.helium.grpc.gateway_region_params_req_v1 request,
        io.grpc.stub.StreamObserver<com.helium.grpc.gateway_region_params_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegionParamsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Get H3 Location for a gateway (auth admin only)
     * </pre>
     */
    default void location(com.helium.grpc.gateway_location_req_v1 request,
        io.grpc.stub.StreamObserver<com.helium.grpc.gateway_location_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getLocationMethod(), responseObserver);
    }

    /**
     * <pre>
     * Get info for the specified gateway
     * </pre>
     */
    default void info(com.helium.grpc.gateway_info_req_v1 request,
        io.grpc.stub.StreamObserver<com.helium.grpc.gateway_info_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getInfoMethod(), responseObserver);
    }

    /**
     * <pre>
     * Get a stream of gateway info
     * </pre>
     */
    default void infoStream(com.helium.grpc.gateway_info_stream_req_v1 request,
        io.grpc.stub.StreamObserver<com.helium.grpc.gateway_info_stream_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getInfoStreamMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service gateway.
   */
  public static abstract class gatewayImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return gatewayGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service gateway.
   */
  public static final class gatewayStub
      extends io.grpc.stub.AbstractAsyncStub<gatewayStub> {
    private gatewayStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected gatewayStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new gatewayStub(channel, callOptions);
    }

    /**
     * <pre>
     * Return the region params for the asserted location of the signed gateway
     * address (no auth, but signature validated)
     * </pre>
     */
    public void regionParams(com.helium.grpc.gateway_region_params_req_v1 request,
        io.grpc.stub.StreamObserver<com.helium.grpc.gateway_region_params_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegionParamsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Get H3 Location for a gateway (auth admin only)
     * </pre>
     */
    public void location(com.helium.grpc.gateway_location_req_v1 request,
        io.grpc.stub.StreamObserver<com.helium.grpc.gateway_location_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getLocationMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Get info for the specified gateway
     * </pre>
     */
    public void info(com.helium.grpc.gateway_info_req_v1 request,
        io.grpc.stub.StreamObserver<com.helium.grpc.gateway_info_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getInfoMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Get a stream of gateway info
     * </pre>
     */
    public void infoStream(com.helium.grpc.gateway_info_stream_req_v1 request,
        io.grpc.stub.StreamObserver<com.helium.grpc.gateway_info_stream_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getInfoStreamMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service gateway.
   */
  public static final class gatewayBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<gatewayBlockingStub> {
    private gatewayBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected gatewayBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new gatewayBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Return the region params for the asserted location of the signed gateway
     * address (no auth, but signature validated)
     * </pre>
     */
    public com.helium.grpc.gateway_region_params_res_v1 regionParams(com.helium.grpc.gateway_region_params_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegionParamsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Get H3 Location for a gateway (auth admin only)
     * </pre>
     */
    public com.helium.grpc.gateway_location_res_v1 location(com.helium.grpc.gateway_location_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getLocationMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Get info for the specified gateway
     * </pre>
     */
    public com.helium.grpc.gateway_info_res_v1 info(com.helium.grpc.gateway_info_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getInfoMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Get a stream of gateway info
     * </pre>
     */
    public java.util.Iterator<com.helium.grpc.gateway_info_stream_res_v1> infoStream(
        com.helium.grpc.gateway_info_stream_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getInfoStreamMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service gateway.
   */
  public static final class gatewayFutureStub
      extends io.grpc.stub.AbstractFutureStub<gatewayFutureStub> {
    private gatewayFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected gatewayFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new gatewayFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Return the region params for the asserted location of the signed gateway
     * address (no auth, but signature validated)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.helium.grpc.gateway_region_params_res_v1> regionParams(
        com.helium.grpc.gateway_region_params_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegionParamsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Get H3 Location for a gateway (auth admin only)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.helium.grpc.gateway_location_res_v1> location(
        com.helium.grpc.gateway_location_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getLocationMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Get info for the specified gateway
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.helium.grpc.gateway_info_res_v1> info(
        com.helium.grpc.gateway_info_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getInfoMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REGION_PARAMS = 0;
  private static final int METHODID_LOCATION = 1;
  private static final int METHODID_INFO = 2;
  private static final int METHODID_INFO_STREAM = 3;

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
        case METHODID_REGION_PARAMS:
          serviceImpl.regionParams((com.helium.grpc.gateway_region_params_req_v1) request,
              (io.grpc.stub.StreamObserver<com.helium.grpc.gateway_region_params_res_v1>) responseObserver);
          break;
        case METHODID_LOCATION:
          serviceImpl.location((com.helium.grpc.gateway_location_req_v1) request,
              (io.grpc.stub.StreamObserver<com.helium.grpc.gateway_location_res_v1>) responseObserver);
          break;
        case METHODID_INFO:
          serviceImpl.info((com.helium.grpc.gateway_info_req_v1) request,
              (io.grpc.stub.StreamObserver<com.helium.grpc.gateway_info_res_v1>) responseObserver);
          break;
        case METHODID_INFO_STREAM:
          serviceImpl.infoStream((com.helium.grpc.gateway_info_stream_req_v1) request,
              (io.grpc.stub.StreamObserver<com.helium.grpc.gateway_info_stream_res_v1>) responseObserver);
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
          getRegionParamsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.helium.grpc.gateway_region_params_req_v1,
              com.helium.grpc.gateway_region_params_res_v1>(
                service, METHODID_REGION_PARAMS)))
        .addMethod(
          getLocationMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.helium.grpc.gateway_location_req_v1,
              com.helium.grpc.gateway_location_res_v1>(
                service, METHODID_LOCATION)))
        .addMethod(
          getInfoMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.helium.grpc.gateway_info_req_v1,
              com.helium.grpc.gateway_info_res_v1>(
                service, METHODID_INFO)))
        .addMethod(
          getInfoStreamMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              com.helium.grpc.gateway_info_stream_req_v1,
              com.helium.grpc.gateway_info_stream_res_v1>(
                service, METHODID_INFO_STREAM)))
        .build();
  }

  private static abstract class gatewayBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    gatewayBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.helium.grpc.IotConfig.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("gateway");
    }
  }

  private static final class gatewayFileDescriptorSupplier
      extends gatewayBaseDescriptorSupplier {
    gatewayFileDescriptorSupplier() {}
  }

  private static final class gatewayMethodDescriptorSupplier
      extends gatewayBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    gatewayMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (gatewayGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new gatewayFileDescriptorSupplier())
              .addMethod(getRegionParamsMethod())
              .addMethod(getLocationMethod())
              .addMethod(getInfoMethod())
              .addMethod(getInfoStreamMethod())
              .build();
        }
      }
    }
    return result;
  }
}
