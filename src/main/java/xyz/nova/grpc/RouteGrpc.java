package xyz.nova.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.51.0)",
    comments = "Source: service/config.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class RouteGrpc {

  private RouteGrpc() {}

  public static final String SERVICE_NAME = "helium.config.route";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<Config.route_list_req_v1,
      Config.route_list_res_v1> getListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "list",
      requestType = Config.route_list_req_v1.class,
      responseType = Config.route_list_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Config.route_list_req_v1,
      Config.route_list_res_v1> getListMethod() {
    io.grpc.MethodDescriptor<Config.route_list_req_v1, Config.route_list_res_v1> getListMethod;
    if ((getListMethod = RouteGrpc.getListMethod) == null) {
      synchronized (RouteGrpc.class) {
        if ((getListMethod = RouteGrpc.getListMethod) == null) {
          RouteGrpc.getListMethod = getListMethod =
              io.grpc.MethodDescriptor.<Config.route_list_req_v1, Config.route_list_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "list"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Config.route_list_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Config.route_list_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new routeMethodDescriptorSupplier("list"))
              .build();
        }
      }
    }
    return getListMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Config.route_get_req_v1,
      Config.route_v1> getGetMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "get",
      requestType = Config.route_get_req_v1.class,
      responseType = Config.route_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Config.route_get_req_v1,
      Config.route_v1> getGetMethod() {
    io.grpc.MethodDescriptor<Config.route_get_req_v1, Config.route_v1> getGetMethod;
    if ((getGetMethod = RouteGrpc.getGetMethod) == null) {
      synchronized (RouteGrpc.class) {
        if ((getGetMethod = RouteGrpc.getGetMethod) == null) {
          RouteGrpc.getGetMethod = getGetMethod =
              io.grpc.MethodDescriptor.<Config.route_get_req_v1, Config.route_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "get"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Config.route_get_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Config.route_v1.getDefaultInstance()))
              .setSchemaDescriptor(new routeMethodDescriptorSupplier("get"))
              .build();
        }
      }
    }
    return getGetMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Config.route_create_req_v1,
      Config.route_v1> getCreateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "create",
      requestType = Config.route_create_req_v1.class,
      responseType = Config.route_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Config.route_create_req_v1,
      Config.route_v1> getCreateMethod() {
    io.grpc.MethodDescriptor<Config.route_create_req_v1, Config.route_v1> getCreateMethod;
    if ((getCreateMethod = RouteGrpc.getCreateMethod) == null) {
      synchronized (RouteGrpc.class) {
        if ((getCreateMethod = RouteGrpc.getCreateMethod) == null) {
          RouteGrpc.getCreateMethod = getCreateMethod =
              io.grpc.MethodDescriptor.<Config.route_create_req_v1, Config.route_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "create"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Config.route_create_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Config.route_v1.getDefaultInstance()))
              .setSchemaDescriptor(new routeMethodDescriptorSupplier("create"))
              .build();
        }
      }
    }
    return getCreateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Config.route_update_req_v1,
      Config.route_v1> getUpdateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "update",
      requestType = Config.route_update_req_v1.class,
      responseType = Config.route_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Config.route_update_req_v1,
      Config.route_v1> getUpdateMethod() {
    io.grpc.MethodDescriptor<Config.route_update_req_v1, Config.route_v1> getUpdateMethod;
    if ((getUpdateMethod = RouteGrpc.getUpdateMethod) == null) {
      synchronized (RouteGrpc.class) {
        if ((getUpdateMethod = RouteGrpc.getUpdateMethod) == null) {
          RouteGrpc.getUpdateMethod = getUpdateMethod =
              io.grpc.MethodDescriptor.<Config.route_update_req_v1, Config.route_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "update"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Config.route_update_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Config.route_v1.getDefaultInstance()))
              .setSchemaDescriptor(new routeMethodDescriptorSupplier("update"))
              .build();
        }
      }
    }
    return getUpdateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Config.route_delete_req_v1,
      Config.route_v1> getDeleteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "delete",
      requestType = Config.route_delete_req_v1.class,
      responseType = Config.route_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Config.route_delete_req_v1,
      Config.route_v1> getDeleteMethod() {
    io.grpc.MethodDescriptor<Config.route_delete_req_v1, Config.route_v1> getDeleteMethod;
    if ((getDeleteMethod = RouteGrpc.getDeleteMethod) == null) {
      synchronized (RouteGrpc.class) {
        if ((getDeleteMethod = RouteGrpc.getDeleteMethod) == null) {
          RouteGrpc.getDeleteMethod = getDeleteMethod =
              io.grpc.MethodDescriptor.<Config.route_delete_req_v1, Config.route_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "delete"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Config.route_delete_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Config.route_v1.getDefaultInstance()))
              .setSchemaDescriptor(new routeMethodDescriptorSupplier("delete"))
              .build();
        }
      }
    }
    return getDeleteMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Config.route_stream_req_v1,
      Config.route_stream_res_v1> getStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "stream",
      requestType = Config.route_stream_req_v1.class,
      responseType = Config.route_stream_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<Config.route_stream_req_v1,
      Config.route_stream_res_v1> getStreamMethod() {
    io.grpc.MethodDescriptor<Config.route_stream_req_v1, Config.route_stream_res_v1> getStreamMethod;
    if ((getStreamMethod = RouteGrpc.getStreamMethod) == null) {
      synchronized (RouteGrpc.class) {
        if ((getStreamMethod = RouteGrpc.getStreamMethod) == null) {
          RouteGrpc.getStreamMethod = getStreamMethod =
              io.grpc.MethodDescriptor.<Config.route_stream_req_v1, Config.route_stream_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "stream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Config.route_stream_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Config.route_stream_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new routeMethodDescriptorSupplier("stream"))
              .build();
        }
      }
    }
    return getStreamMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static routeStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<routeStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<routeStub>() {
        @Override
        public routeStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new routeStub(channel, callOptions);
        }
      };
    return routeStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static routeBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<routeBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<routeBlockingStub>() {
        @Override
        public routeBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new routeBlockingStub(channel, callOptions);
        }
      };
    return routeBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static routeFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<routeFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<routeFutureStub>() {
        @Override
        public routeFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new routeFutureStub(channel, callOptions);
        }
      };
    return routeFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class routeImplBase implements io.grpc.BindableService {

    /**
     */
    public void list(Config.route_list_req_v1 request,
        io.grpc.stub.StreamObserver<Config.route_list_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListMethod(), responseObserver);
    }

    /**
     */
    public void get(Config.route_get_req_v1 request,
        io.grpc.stub.StreamObserver<Config.route_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMethod(), responseObserver);
    }

    /**
     */
    public void create(Config.route_create_req_v1 request,
        io.grpc.stub.StreamObserver<Config.route_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateMethod(), responseObserver);
    }

    /**
     */
    public void update(Config.route_update_req_v1 request,
        io.grpc.stub.StreamObserver<Config.route_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateMethod(), responseObserver);
    }

    /**
     */
    public void delete(Config.route_delete_req_v1 request,
        io.grpc.stub.StreamObserver<Config.route_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteMethod(), responseObserver);
    }

    /**
     */
    public void stream(Config.route_stream_req_v1 request,
        io.grpc.stub.StreamObserver<Config.route_stream_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getStreamMethod(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getListMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                Config.route_list_req_v1,
                Config.route_list_res_v1>(
                  this, METHODID_LIST)))
          .addMethod(
            getGetMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                Config.route_get_req_v1,
                Config.route_v1>(
                  this, METHODID_GET)))
          .addMethod(
            getCreateMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                Config.route_create_req_v1,
                Config.route_v1>(
                  this, METHODID_CREATE)))
          .addMethod(
            getUpdateMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                Config.route_update_req_v1,
                Config.route_v1>(
                  this, METHODID_UPDATE)))
          .addMethod(
            getDeleteMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                Config.route_delete_req_v1,
                Config.route_v1>(
                  this, METHODID_DELETE)))
          .addMethod(
            getStreamMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                Config.route_stream_req_v1,
                Config.route_stream_res_v1>(
                  this, METHODID_STREAM)))
          .build();
    }
  }

  /**
   */
  public static final class routeStub extends io.grpc.stub.AbstractAsyncStub<routeStub> {
    private routeStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected routeStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new routeStub(channel, callOptions);
    }

    /**
     */
    /*
    public void list(Config.route_list_req_v1 request) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListMethod(), getCallOptions()), request, responseObserver);
    }
*/
    /**
     */
    public void get(Config.route_get_req_v1 request,
        io.grpc.stub.StreamObserver<Config.route_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void create(Config.route_create_req_v1 request,
        io.grpc.stub.StreamObserver<Config.route_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void update(Config.route_update_req_v1 request,
        io.grpc.stub.StreamObserver<Config.route_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void delete(Config.route_delete_req_v1 request,
        io.grpc.stub.StreamObserver<Config.route_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void stream(Config.route_stream_req_v1 request,
        io.grpc.stub.StreamObserver<Config.route_stream_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getStreamMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class routeBlockingStub extends io.grpc.stub.AbstractBlockingStub<routeBlockingStub> {
    private routeBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected routeBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new routeBlockingStub(channel, callOptions);
    }

    /**
     */
    public Config.route_list_res_v1 list(Config.route_list_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListMethod(), getCallOptions(), request);
    }

    /**
     */
    public Config.route_v1 get(Config.route_get_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetMethod(), getCallOptions(), request);
    }

    /**
     */
    public Config.route_v1 create(Config.route_create_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateMethod(), getCallOptions(), request);
    }

    /**
     */
    public Config.route_v1 update(Config.route_update_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateMethod(), getCallOptions(), request);
    }

    /**
     */
    public Config.route_v1 delete(Config.route_delete_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<Config.route_stream_res_v1> stream(
        Config.route_stream_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getStreamMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class routeFutureStub extends io.grpc.stub.AbstractFutureStub<routeFutureStub> {
    private routeFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected routeFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new routeFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Config.route_list_res_v1> list(
        Config.route_list_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Config.route_v1> get(
        Config.route_get_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Config.route_v1> create(
        Config.route_create_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Config.route_v1> update(
        Config.route_update_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Config.route_v1> delete(
        Config.route_delete_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LIST = 0;
  private static final int METHODID_GET = 1;
  private static final int METHODID_CREATE = 2;
  private static final int METHODID_UPDATE = 3;
  private static final int METHODID_DELETE = 4;
  private static final int METHODID_STREAM = 5;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final routeImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(routeImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LIST:
          serviceImpl.list((Config.route_list_req_v1) request,
              (io.grpc.stub.StreamObserver<Config.route_list_res_v1>) responseObserver);
          break;
        case METHODID_GET:
          serviceImpl.get((Config.route_get_req_v1) request,
              (io.grpc.stub.StreamObserver<Config.route_v1>) responseObserver);
          break;
        case METHODID_CREATE:
          serviceImpl.create((Config.route_create_req_v1) request,
              (io.grpc.stub.StreamObserver<Config.route_v1>) responseObserver);
          break;
        case METHODID_UPDATE:
          serviceImpl.update((Config.route_update_req_v1) request,
              (io.grpc.stub.StreamObserver<Config.route_v1>) responseObserver);
          break;
        case METHODID_DELETE:
          serviceImpl.delete((Config.route_delete_req_v1) request,
              (io.grpc.stub.StreamObserver<Config.route_v1>) responseObserver);
          break;
        case METHODID_STREAM:
          serviceImpl.stream((Config.route_stream_req_v1) request,
              (io.grpc.stub.StreamObserver<Config.route_stream_res_v1>) responseObserver);
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

  private static abstract class routeBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    routeBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return Config.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("route");
    }
  }

  private static final class routeFileDescriptorSupplier
      extends routeBaseDescriptorSupplier {
    routeFileDescriptorSupplier() {}
  }

  private static final class routeMethodDescriptorSupplier
      extends routeBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    routeMethodDescriptorSupplier(String methodName) {
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
      synchronized (RouteGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new routeFileDescriptorSupplier())
              .addMethod(getListMethod())
              .addMethod(getGetMethod())
              .addMethod(getCreateMethod())
              .addMethod(getUpdateMethod())
              .addMethod(getDeleteMethod())
              .addMethod(getStreamMethod())
              .build();
        }
      }
    }
    return result;
  }
}
