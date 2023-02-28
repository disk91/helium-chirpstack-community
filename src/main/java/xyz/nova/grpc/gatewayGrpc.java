package xyz.nova.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.53.0)",
    comments = "Source: service/iot_config.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class gatewayGrpc {

  private gatewayGrpc() {}

  public static final String SERVICE_NAME = "helium.iot_config.gateway";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<xyz.nova.grpc.gateway_region_params_req_v1,
      xyz.nova.grpc.gateway_region_params_res_v1> getRegionParamsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "region_params",
      requestType = xyz.nova.grpc.gateway_region_params_req_v1.class,
      responseType = xyz.nova.grpc.gateway_region_params_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<xyz.nova.grpc.gateway_region_params_req_v1,
      xyz.nova.grpc.gateway_region_params_res_v1> getRegionParamsMethod() {
    io.grpc.MethodDescriptor<xyz.nova.grpc.gateway_region_params_req_v1, xyz.nova.grpc.gateway_region_params_res_v1> getRegionParamsMethod;
    if ((getRegionParamsMethod = gatewayGrpc.getRegionParamsMethod) == null) {
      synchronized (gatewayGrpc.class) {
        if ((getRegionParamsMethod = gatewayGrpc.getRegionParamsMethod) == null) {
          gatewayGrpc.getRegionParamsMethod = getRegionParamsMethod =
              io.grpc.MethodDescriptor.<xyz.nova.grpc.gateway_region_params_req_v1, xyz.nova.grpc.gateway_region_params_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "region_params"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.gateway_region_params_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.gateway_region_params_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new gatewayMethodDescriptorSupplier("region_params"))
              .build();
        }
      }
    }
    return getRegionParamsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<xyz.nova.grpc.gateway_load_region_req_v1,
      xyz.nova.grpc.gateway_load_region_res_v1> getLoadRegionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "load_region",
      requestType = xyz.nova.grpc.gateway_load_region_req_v1.class,
      responseType = xyz.nova.grpc.gateway_load_region_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<xyz.nova.grpc.gateway_load_region_req_v1,
      xyz.nova.grpc.gateway_load_region_res_v1> getLoadRegionMethod() {
    io.grpc.MethodDescriptor<xyz.nova.grpc.gateway_load_region_req_v1, xyz.nova.grpc.gateway_load_region_res_v1> getLoadRegionMethod;
    if ((getLoadRegionMethod = gatewayGrpc.getLoadRegionMethod) == null) {
      synchronized (gatewayGrpc.class) {
        if ((getLoadRegionMethod = gatewayGrpc.getLoadRegionMethod) == null) {
          gatewayGrpc.getLoadRegionMethod = getLoadRegionMethod =
              io.grpc.MethodDescriptor.<xyz.nova.grpc.gateway_load_region_req_v1, xyz.nova.grpc.gateway_load_region_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "load_region"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.gateway_load_region_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.gateway_load_region_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new gatewayMethodDescriptorSupplier("load_region"))
              .build();
        }
      }
    }
    return getLoadRegionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<xyz.nova.grpc.gateway_location_req_v1,
      xyz.nova.grpc.gateway_location_res_v1> getLocationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "location",
      requestType = xyz.nova.grpc.gateway_location_req_v1.class,
      responseType = xyz.nova.grpc.gateway_location_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<xyz.nova.grpc.gateway_location_req_v1,
      xyz.nova.grpc.gateway_location_res_v1> getLocationMethod() {
    io.grpc.MethodDescriptor<xyz.nova.grpc.gateway_location_req_v1, xyz.nova.grpc.gateway_location_res_v1> getLocationMethod;
    if ((getLocationMethod = gatewayGrpc.getLocationMethod) == null) {
      synchronized (gatewayGrpc.class) {
        if ((getLocationMethod = gatewayGrpc.getLocationMethod) == null) {
          gatewayGrpc.getLocationMethod = getLocationMethod =
              io.grpc.MethodDescriptor.<xyz.nova.grpc.gateway_location_req_v1, xyz.nova.grpc.gateway_location_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "location"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.gateway_location_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.gateway_location_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new gatewayMethodDescriptorSupplier("location"))
              .build();
        }
      }
    }
    return getLocationMethod;
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
  public static abstract class gatewayImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Return the region params for the asserted location of the signed gateway
     * address (no auth, but signature validated)
     * </pre>
     */
    public void regionParams(xyz.nova.grpc.gateway_region_params_req_v1 request,
        io.grpc.stub.StreamObserver<xyz.nova.grpc.gateway_region_params_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegionParamsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Load params and cell indexes for a region into the config service (auth
     * admin only)
     * </pre>
     */
    public void loadRegion(xyz.nova.grpc.gateway_load_region_req_v1 request,
        io.grpc.stub.StreamObserver<xyz.nova.grpc.gateway_load_region_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getLoadRegionMethod(), responseObserver);
    }

    /**
     * <pre>
     * Get H3 Location for a gateway (auth admin only)
     * </pre>
     */
    public void location(xyz.nova.grpc.gateway_location_req_v1 request,
        io.grpc.stub.StreamObserver<xyz.nova.grpc.gateway_location_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getLocationMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getRegionParamsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                xyz.nova.grpc.gateway_region_params_req_v1,
                xyz.nova.grpc.gateway_region_params_res_v1>(
                  this, METHODID_REGION_PARAMS)))
          .addMethod(
            getLoadRegionMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                xyz.nova.grpc.gateway_load_region_req_v1,
                xyz.nova.grpc.gateway_load_region_res_v1>(
                  this, METHODID_LOAD_REGION)))
          .addMethod(
            getLocationMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                xyz.nova.grpc.gateway_location_req_v1,
                xyz.nova.grpc.gateway_location_res_v1>(
                  this, METHODID_LOCATION)))
          .build();
    }
  }

  /**
   */
  public static final class gatewayStub extends io.grpc.stub.AbstractAsyncStub<gatewayStub> {
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
    public void regionParams(xyz.nova.grpc.gateway_region_params_req_v1 request,
        io.grpc.stub.StreamObserver<xyz.nova.grpc.gateway_region_params_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegionParamsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Load params and cell indexes for a region into the config service (auth
     * admin only)
     * </pre>
     */
    public void loadRegion(xyz.nova.grpc.gateway_load_region_req_v1 request,
        io.grpc.stub.StreamObserver<xyz.nova.grpc.gateway_load_region_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getLoadRegionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Get H3 Location for a gateway (auth admin only)
     * </pre>
     */
    public void location(xyz.nova.grpc.gateway_location_req_v1 request,
        io.grpc.stub.StreamObserver<xyz.nova.grpc.gateway_location_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getLocationMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class gatewayBlockingStub extends io.grpc.stub.AbstractBlockingStub<gatewayBlockingStub> {
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
    public xyz.nova.grpc.gateway_region_params_res_v1 regionParams(xyz.nova.grpc.gateway_region_params_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegionParamsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Load params and cell indexes for a region into the config service (auth
     * admin only)
     * </pre>
     */
    public xyz.nova.grpc.gateway_load_region_res_v1 loadRegion(xyz.nova.grpc.gateway_load_region_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getLoadRegionMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Get H3 Location for a gateway (auth admin only)
     * </pre>
     */
    public xyz.nova.grpc.gateway_location_res_v1 location(xyz.nova.grpc.gateway_location_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getLocationMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class gatewayFutureStub extends io.grpc.stub.AbstractFutureStub<gatewayFutureStub> {
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
    public com.google.common.util.concurrent.ListenableFuture<xyz.nova.grpc.gateway_region_params_res_v1> regionParams(
        xyz.nova.grpc.gateway_region_params_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegionParamsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Load params and cell indexes for a region into the config service (auth
     * admin only)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<xyz.nova.grpc.gateway_load_region_res_v1> loadRegion(
        xyz.nova.grpc.gateway_load_region_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getLoadRegionMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Get H3 Location for a gateway (auth admin only)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<xyz.nova.grpc.gateway_location_res_v1> location(
        xyz.nova.grpc.gateway_location_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getLocationMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REGION_PARAMS = 0;
  private static final int METHODID_LOAD_REGION = 1;
  private static final int METHODID_LOCATION = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final gatewayImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(gatewayImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REGION_PARAMS:
          serviceImpl.regionParams((xyz.nova.grpc.gateway_region_params_req_v1) request,
              (io.grpc.stub.StreamObserver<xyz.nova.grpc.gateway_region_params_res_v1>) responseObserver);
          break;
        case METHODID_LOAD_REGION:
          serviceImpl.loadRegion((xyz.nova.grpc.gateway_load_region_req_v1) request,
              (io.grpc.stub.StreamObserver<xyz.nova.grpc.gateway_load_region_res_v1>) responseObserver);
          break;
        case METHODID_LOCATION:
          serviceImpl.location((xyz.nova.grpc.gateway_location_req_v1) request,
              (io.grpc.stub.StreamObserver<xyz.nova.grpc.gateway_location_res_v1>) responseObserver);
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

  private static abstract class gatewayBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    gatewayBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return xyz.nova.grpc.IotConfig.getDescriptor();
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
    private final String methodName;

    gatewayMethodDescriptorSupplier(String methodName) {
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
              .addMethod(getLoadRegionMethod())
              .addMethod(getLocationMethod())
              .build();
        }
      }
    }
    return result;
  }
}
