package xyz.nova.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.51.0)",
    comments = "Source: service/config.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class OrgGrpc {

  private OrgGrpc() {}

  public static final String SERVICE_NAME = "helium.config.org";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<Config.org_list_req_v1,
      Config.org_list_res_v1> getListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "list",
      requestType = Config.org_list_req_v1.class,
      responseType = Config.org_list_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Config.org_list_req_v1,
      Config.org_list_res_v1> getListMethod() {
    io.grpc.MethodDescriptor<Config.org_list_req_v1, Config.org_list_res_v1> getListMethod;
    if ((getListMethod = OrgGrpc.getListMethod) == null) {
      synchronized (OrgGrpc.class) {
        if ((getListMethod = OrgGrpc.getListMethod) == null) {
          OrgGrpc.getListMethod = getListMethod =
              io.grpc.MethodDescriptor.<Config.org_list_req_v1, Config.org_list_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "list"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Config.org_list_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Config.org_list_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new orgMethodDescriptorSupplier("list"))
              .build();
        }
      }
    }
    return getListMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Config.org_get_req_v1,
      Config.org_res_v1> getGetMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "get",
      requestType = Config.org_get_req_v1.class,
      responseType = Config.org_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Config.org_get_req_v1,
      Config.org_res_v1> getGetMethod() {
    io.grpc.MethodDescriptor<Config.org_get_req_v1, Config.org_res_v1> getGetMethod;
    if ((getGetMethod = OrgGrpc.getGetMethod) == null) {
      synchronized (OrgGrpc.class) {
        if ((getGetMethod = OrgGrpc.getGetMethod) == null) {
          OrgGrpc.getGetMethod = getGetMethod =
              io.grpc.MethodDescriptor.<Config.org_get_req_v1, Config.org_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "get"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Config.org_get_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Config.org_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new orgMethodDescriptorSupplier("get"))
              .build();
        }
      }
    }
    return getGetMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Config.org_create_helium_req_v1,
      Config.org_res_v1> getCreateHeliumMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "create_helium",
      requestType = Config.org_create_helium_req_v1.class,
      responseType = Config.org_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Config.org_create_helium_req_v1,
      Config.org_res_v1> getCreateHeliumMethod() {
    io.grpc.MethodDescriptor<Config.org_create_helium_req_v1, Config.org_res_v1> getCreateHeliumMethod;
    if ((getCreateHeliumMethod = OrgGrpc.getCreateHeliumMethod) == null) {
      synchronized (OrgGrpc.class) {
        if ((getCreateHeliumMethod = OrgGrpc.getCreateHeliumMethod) == null) {
          OrgGrpc.getCreateHeliumMethod = getCreateHeliumMethod =
              io.grpc.MethodDescriptor.<Config.org_create_helium_req_v1, Config.org_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "create_helium"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Config.org_create_helium_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Config.org_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new orgMethodDescriptorSupplier("create_helium"))
              .build();
        }
      }
    }
    return getCreateHeliumMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Config.org_create_roamer_req_v1,
      Config.org_res_v1> getCreateRoamerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "create_roamer",
      requestType = Config.org_create_roamer_req_v1.class,
      responseType = Config.org_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Config.org_create_roamer_req_v1,
      Config.org_res_v1> getCreateRoamerMethod() {
    io.grpc.MethodDescriptor<Config.org_create_roamer_req_v1, Config.org_res_v1> getCreateRoamerMethod;
    if ((getCreateRoamerMethod = OrgGrpc.getCreateRoamerMethod) == null) {
      synchronized (OrgGrpc.class) {
        if ((getCreateRoamerMethod = OrgGrpc.getCreateRoamerMethod) == null) {
          OrgGrpc.getCreateRoamerMethod = getCreateRoamerMethod =
              io.grpc.MethodDescriptor.<Config.org_create_roamer_req_v1, Config.org_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "create_roamer"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Config.org_create_roamer_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Config.org_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new orgMethodDescriptorSupplier("create_roamer"))
              .build();
        }
      }
    }
    return getCreateRoamerMethod;
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
     */
    public void list(Config.org_list_req_v1 request,
        io.grpc.stub.StreamObserver<Config.org_list_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListMethod(), responseObserver);
    }

    /**
     */
    public void get(Config.org_get_req_v1 request,
        io.grpc.stub.StreamObserver<Config.org_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMethod(), responseObserver);
    }

    /**
     */
    public void createHelium(Config.org_create_helium_req_v1 request,
        io.grpc.stub.StreamObserver<Config.org_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateHeliumMethod(), responseObserver);
    }

    /**
     */
    public void createRoamer(Config.org_create_roamer_req_v1 request,
        io.grpc.stub.StreamObserver<Config.org_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateRoamerMethod(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getListMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                Config.org_list_req_v1,
                Config.org_list_res_v1>(
                  this, METHODID_LIST)))
          .addMethod(
            getGetMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                Config.org_get_req_v1,
                Config.org_res_v1>(
                  this, METHODID_GET)))
          .addMethod(
            getCreateHeliumMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                Config.org_create_helium_req_v1,
                Config.org_res_v1>(
                  this, METHODID_CREATE_HELIUM)))
          .addMethod(
            getCreateRoamerMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                Config.org_create_roamer_req_v1,
                Config.org_res_v1>(
                  this, METHODID_CREATE_ROAMER)))
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
     */
    public void list(Config.org_list_req_v1 request,
        io.grpc.stub.StreamObserver<Config.org_list_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void get(Config.org_get_req_v1 request,
        io.grpc.stub.StreamObserver<Config.org_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void createHelium(Config.org_create_helium_req_v1 request,
        io.grpc.stub.StreamObserver<Config.org_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateHeliumMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void createRoamer(Config.org_create_roamer_req_v1 request,
        io.grpc.stub.StreamObserver<Config.org_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateRoamerMethod(), getCallOptions()), request, responseObserver);
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
     */
    public Config.org_list_res_v1 list(Config.org_list_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListMethod(), getCallOptions(), request);
    }

    /**
     */
    public Config.org_res_v1 get(Config.org_get_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetMethod(), getCallOptions(), request);
    }

    /**
     */
    public Config.org_res_v1 createHelium(Config.org_create_helium_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateHeliumMethod(), getCallOptions(), request);
    }

    /**
     */
    public Config.org_res_v1 createRoamer(Config.org_create_roamer_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateRoamerMethod(), getCallOptions(), request);
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
     */
    public com.google.common.util.concurrent.ListenableFuture<Config.org_list_res_v1> list(
        Config.org_list_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Config.org_res_v1> get(
        Config.org_get_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Config.org_res_v1> createHelium(
        Config.org_create_helium_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateHeliumMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Config.org_res_v1> createRoamer(
        Config.org_create_roamer_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateRoamerMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LIST = 0;
  private static final int METHODID_GET = 1;
  private static final int METHODID_CREATE_HELIUM = 2;
  private static final int METHODID_CREATE_ROAMER = 3;

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
          serviceImpl.list((Config.org_list_req_v1) request,
              (io.grpc.stub.StreamObserver<Config.org_list_res_v1>) responseObserver);
          break;
        case METHODID_GET:
          serviceImpl.get((Config.org_get_req_v1) request,
              (io.grpc.stub.StreamObserver<Config.org_res_v1>) responseObserver);
          break;
        case METHODID_CREATE_HELIUM:
          serviceImpl.createHelium((Config.org_create_helium_req_v1) request,
              (io.grpc.stub.StreamObserver<Config.org_res_v1>) responseObserver);
          break;
        case METHODID_CREATE_ROAMER:
          serviceImpl.createRoamer((Config.org_create_roamer_req_v1) request,
              (io.grpc.stub.StreamObserver<Config.org_res_v1>) responseObserver);
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
      return Config.getDescriptor();
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
      synchronized (OrgGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new orgFileDescriptorSupplier())
              .addMethod(getListMethod())
              .addMethod(getGetMethod())
              .addMethod(getCreateHeliumMethod())
              .addMethod(getCreateRoamerMethod())
              .build();
        }
      }
    }
    return result;
  }
}
