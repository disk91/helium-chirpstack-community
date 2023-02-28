package xyz.nova.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.53.0)",
    comments = "Source: service/iot_config.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class session_key_filterGrpc {

  private session_key_filterGrpc() {}

  public static final String SERVICE_NAME = "helium.iot_config.session_key_filter";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<xyz.nova.grpc.session_key_filter_list_req_v1,
      xyz.nova.grpc.session_key_filter_v1> getListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "list",
      requestType = xyz.nova.grpc.session_key_filter_list_req_v1.class,
      responseType = xyz.nova.grpc.session_key_filter_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<xyz.nova.grpc.session_key_filter_list_req_v1,
      xyz.nova.grpc.session_key_filter_v1> getListMethod() {
    io.grpc.MethodDescriptor<xyz.nova.grpc.session_key_filter_list_req_v1, xyz.nova.grpc.session_key_filter_v1> getListMethod;
    if ((getListMethod = session_key_filterGrpc.getListMethod) == null) {
      synchronized (session_key_filterGrpc.class) {
        if ((getListMethod = session_key_filterGrpc.getListMethod) == null) {
          session_key_filterGrpc.getListMethod = getListMethod =
              io.grpc.MethodDescriptor.<xyz.nova.grpc.session_key_filter_list_req_v1, xyz.nova.grpc.session_key_filter_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "list"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.session_key_filter_list_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.session_key_filter_v1.getDefaultInstance()))
              .setSchemaDescriptor(new session_key_filterMethodDescriptorSupplier("list"))
              .build();
        }
      }
    }
    return getListMethod;
  }

  private static volatile io.grpc.MethodDescriptor<xyz.nova.grpc.session_key_filter_get_req_v1,
      xyz.nova.grpc.session_key_filter_v1> getGetMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "get",
      requestType = xyz.nova.grpc.session_key_filter_get_req_v1.class,
      responseType = xyz.nova.grpc.session_key_filter_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<xyz.nova.grpc.session_key_filter_get_req_v1,
      xyz.nova.grpc.session_key_filter_v1> getGetMethod() {
    io.grpc.MethodDescriptor<xyz.nova.grpc.session_key_filter_get_req_v1, xyz.nova.grpc.session_key_filter_v1> getGetMethod;
    if ((getGetMethod = session_key_filterGrpc.getGetMethod) == null) {
      synchronized (session_key_filterGrpc.class) {
        if ((getGetMethod = session_key_filterGrpc.getGetMethod) == null) {
          session_key_filterGrpc.getGetMethod = getGetMethod =
              io.grpc.MethodDescriptor.<xyz.nova.grpc.session_key_filter_get_req_v1, xyz.nova.grpc.session_key_filter_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "get"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.session_key_filter_get_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.session_key_filter_v1.getDefaultInstance()))
              .setSchemaDescriptor(new session_key_filterMethodDescriptorSupplier("get"))
              .build();
        }
      }
    }
    return getGetMethod;
  }

  private static volatile io.grpc.MethodDescriptor<xyz.nova.grpc.session_key_filter_update_req_v1,
      xyz.nova.grpc.session_key_filter_update_res_v1> getUpdateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "update",
      requestType = xyz.nova.grpc.session_key_filter_update_req_v1.class,
      responseType = xyz.nova.grpc.session_key_filter_update_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<xyz.nova.grpc.session_key_filter_update_req_v1,
      xyz.nova.grpc.session_key_filter_update_res_v1> getUpdateMethod() {
    io.grpc.MethodDescriptor<xyz.nova.grpc.session_key_filter_update_req_v1, xyz.nova.grpc.session_key_filter_update_res_v1> getUpdateMethod;
    if ((getUpdateMethod = session_key_filterGrpc.getUpdateMethod) == null) {
      synchronized (session_key_filterGrpc.class) {
        if ((getUpdateMethod = session_key_filterGrpc.getUpdateMethod) == null) {
          session_key_filterGrpc.getUpdateMethod = getUpdateMethod =
              io.grpc.MethodDescriptor.<xyz.nova.grpc.session_key_filter_update_req_v1, xyz.nova.grpc.session_key_filter_update_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "update"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.session_key_filter_update_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.session_key_filter_update_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new session_key_filterMethodDescriptorSupplier("update"))
              .build();
        }
      }
    }
    return getUpdateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<xyz.nova.grpc.session_key_filter_stream_req_v1,
      xyz.nova.grpc.session_key_filter_stream_res_v1> getStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "stream",
      requestType = xyz.nova.grpc.session_key_filter_stream_req_v1.class,
      responseType = xyz.nova.grpc.session_key_filter_stream_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<xyz.nova.grpc.session_key_filter_stream_req_v1,
      xyz.nova.grpc.session_key_filter_stream_res_v1> getStreamMethod() {
    io.grpc.MethodDescriptor<xyz.nova.grpc.session_key_filter_stream_req_v1, xyz.nova.grpc.session_key_filter_stream_res_v1> getStreamMethod;
    if ((getStreamMethod = session_key_filterGrpc.getStreamMethod) == null) {
      synchronized (session_key_filterGrpc.class) {
        if ((getStreamMethod = session_key_filterGrpc.getStreamMethod) == null) {
          session_key_filterGrpc.getStreamMethod = getStreamMethod =
              io.grpc.MethodDescriptor.<xyz.nova.grpc.session_key_filter_stream_req_v1, xyz.nova.grpc.session_key_filter_stream_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "stream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.session_key_filter_stream_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.session_key_filter_stream_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new session_key_filterMethodDescriptorSupplier("stream"))
              .build();
        }
      }
    }
    return getStreamMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static session_key_filterStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<session_key_filterStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<session_key_filterStub>() {
        @java.lang.Override
        public session_key_filterStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new session_key_filterStub(channel, callOptions);
        }
      };
    return session_key_filterStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static session_key_filterBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<session_key_filterBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<session_key_filterBlockingStub>() {
        @java.lang.Override
        public session_key_filterBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new session_key_filterBlockingStub(channel, callOptions);
        }
      };
    return session_key_filterBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static session_key_filterFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<session_key_filterFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<session_key_filterFutureStub>() {
        @java.lang.Override
        public session_key_filterFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new session_key_filterFutureStub(channel, callOptions);
        }
      };
    return session_key_filterFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class session_key_filterImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * List Filters for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void list(xyz.nova.grpc.session_key_filter_list_req_v1 request,
        io.grpc.stub.StreamObserver<xyz.nova.grpc.session_key_filter_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListMethod(), responseObserver);
    }

    /**
     * <pre>
     * List Filters for a DevAddr (auth delegate_keys/owner/admin
     * </pre>
     */
    public void get(xyz.nova.grpc.session_key_filter_get_req_v1 request,
        io.grpc.stub.StreamObserver<xyz.nova.grpc.session_key_filter_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMethod(), responseObserver);
    }

    /**
     * <pre>
     * Update Filters for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public io.grpc.stub.StreamObserver<xyz.nova.grpc.session_key_filter_update_req_v1> update(
        io.grpc.stub.StreamObserver<xyz.nova.grpc.session_key_filter_update_res_v1> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getUpdateMethod(), responseObserver);
    }

    /**
     * <pre>
     * Stream Filter update (auth admin only)
     * </pre>
     */
    public void stream(xyz.nova.grpc.session_key_filter_stream_req_v1 request,
        io.grpc.stub.StreamObserver<xyz.nova.grpc.session_key_filter_stream_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getStreamMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getListMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                xyz.nova.grpc.session_key_filter_list_req_v1,
                xyz.nova.grpc.session_key_filter_v1>(
                  this, METHODID_LIST)))
          .addMethod(
            getGetMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                xyz.nova.grpc.session_key_filter_get_req_v1,
                xyz.nova.grpc.session_key_filter_v1>(
                  this, METHODID_GET)))
          .addMethod(
            getUpdateMethod(),
            io.grpc.stub.ServerCalls.asyncClientStreamingCall(
              new MethodHandlers<
                xyz.nova.grpc.session_key_filter_update_req_v1,
                xyz.nova.grpc.session_key_filter_update_res_v1>(
                  this, METHODID_UPDATE)))
          .addMethod(
            getStreamMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                xyz.nova.grpc.session_key_filter_stream_req_v1,
                xyz.nova.grpc.session_key_filter_stream_res_v1>(
                  this, METHODID_STREAM)))
          .build();
    }
  }

  /**
   */
  public static final class session_key_filterStub extends io.grpc.stub.AbstractAsyncStub<session_key_filterStub> {
    private session_key_filterStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected session_key_filterStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new session_key_filterStub(channel, callOptions);
    }

    /**
     * <pre>
     * List Filters for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void list(xyz.nova.grpc.session_key_filter_list_req_v1 request,
        io.grpc.stub.StreamObserver<xyz.nova.grpc.session_key_filter_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getListMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * List Filters for a DevAddr (auth delegate_keys/owner/admin
     * </pre>
     */
    public void get(xyz.nova.grpc.session_key_filter_get_req_v1 request,
        io.grpc.stub.StreamObserver<xyz.nova.grpc.session_key_filter_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGetMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Update Filters for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public io.grpc.stub.StreamObserver<xyz.nova.grpc.session_key_filter_update_req_v1> update(
        io.grpc.stub.StreamObserver<xyz.nova.grpc.session_key_filter_update_res_v1> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getUpdateMethod(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     * Stream Filter update (auth admin only)
     * </pre>
     */
    public void stream(xyz.nova.grpc.session_key_filter_stream_req_v1 request,
        io.grpc.stub.StreamObserver<xyz.nova.grpc.session_key_filter_stream_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getStreamMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class session_key_filterBlockingStub extends io.grpc.stub.AbstractBlockingStub<session_key_filterBlockingStub> {
    private session_key_filterBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected session_key_filterBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new session_key_filterBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * List Filters for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public java.util.Iterator<xyz.nova.grpc.session_key_filter_v1> list(
        xyz.nova.grpc.session_key_filter_list_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getListMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * List Filters for a DevAddr (auth delegate_keys/owner/admin
     * </pre>
     */
    public java.util.Iterator<xyz.nova.grpc.session_key_filter_v1> get(
        xyz.nova.grpc.session_key_filter_get_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGetMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Stream Filter update (auth admin only)
     * </pre>
     */
    public java.util.Iterator<xyz.nova.grpc.session_key_filter_stream_res_v1> stream(
        xyz.nova.grpc.session_key_filter_stream_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getStreamMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class session_key_filterFutureStub extends io.grpc.stub.AbstractFutureStub<session_key_filterFutureStub> {
    private session_key_filterFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected session_key_filterFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new session_key_filterFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_LIST = 0;
  private static final int METHODID_GET = 1;
  private static final int METHODID_STREAM = 2;
  private static final int METHODID_UPDATE = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final session_key_filterImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(session_key_filterImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LIST:
          serviceImpl.list((xyz.nova.grpc.session_key_filter_list_req_v1) request,
              (io.grpc.stub.StreamObserver<xyz.nova.grpc.session_key_filter_v1>) responseObserver);
          break;
        case METHODID_GET:
          serviceImpl.get((xyz.nova.grpc.session_key_filter_get_req_v1) request,
              (io.grpc.stub.StreamObserver<xyz.nova.grpc.session_key_filter_v1>) responseObserver);
          break;
        case METHODID_STREAM:
          serviceImpl.stream((xyz.nova.grpc.session_key_filter_stream_req_v1) request,
              (io.grpc.stub.StreamObserver<xyz.nova.grpc.session_key_filter_stream_res_v1>) responseObserver);
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
        case METHODID_UPDATE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.update(
              (io.grpc.stub.StreamObserver<xyz.nova.grpc.session_key_filter_update_res_v1>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class session_key_filterBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    session_key_filterBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return xyz.nova.grpc.IotConfig.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("session_key_filter");
    }
  }

  private static final class session_key_filterFileDescriptorSupplier
      extends session_key_filterBaseDescriptorSupplier {
    session_key_filterFileDescriptorSupplier() {}
  }

  private static final class session_key_filterMethodDescriptorSupplier
      extends session_key_filterBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    session_key_filterMethodDescriptorSupplier(String methodName) {
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
      synchronized (session_key_filterGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new session_key_filterFileDescriptorSupplier())
              .addMethod(getListMethod())
              .addMethod(getGetMethod())
              .addMethod(getUpdateMethod())
              .addMethod(getStreamMethod())
              .build();
        }
      }
    }
    return result;
  }
}
