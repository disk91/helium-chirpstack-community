package xyz.nova.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.53.0)",
    comments = "Source: service/iot_config.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class routeGrpc {

  private routeGrpc() {}

  public static final String SERVICE_NAME = "helium.iot_config.route";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<xyz.nova.grpc.route_list_req_v1,
      xyz.nova.grpc.route_list_res_v1> getListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "list",
      requestType = xyz.nova.grpc.route_list_req_v1.class,
      responseType = xyz.nova.grpc.route_list_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<xyz.nova.grpc.route_list_req_v1,
      xyz.nova.grpc.route_list_res_v1> getListMethod() {
    io.grpc.MethodDescriptor<xyz.nova.grpc.route_list_req_v1, xyz.nova.grpc.route_list_res_v1> getListMethod;
    if ((getListMethod = routeGrpc.getListMethod) == null) {
      synchronized (routeGrpc.class) {
        if ((getListMethod = routeGrpc.getListMethod) == null) {
          routeGrpc.getListMethod = getListMethod =
              io.grpc.MethodDescriptor.<xyz.nova.grpc.route_list_req_v1, xyz.nova.grpc.route_list_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "list"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.route_list_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.route_list_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new routeMethodDescriptorSupplier("list"))
              .build();
        }
      }
    }
    return getListMethod;
  }

  private static volatile io.grpc.MethodDescriptor<xyz.nova.grpc.route_get_req_v1,
      xyz.nova.grpc.route_v1> getGetMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "get",
      requestType = xyz.nova.grpc.route_get_req_v1.class,
      responseType = xyz.nova.grpc.route_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<xyz.nova.grpc.route_get_req_v1,
      xyz.nova.grpc.route_v1> getGetMethod() {
    io.grpc.MethodDescriptor<xyz.nova.grpc.route_get_req_v1, xyz.nova.grpc.route_v1> getGetMethod;
    if ((getGetMethod = routeGrpc.getGetMethod) == null) {
      synchronized (routeGrpc.class) {
        if ((getGetMethod = routeGrpc.getGetMethod) == null) {
          routeGrpc.getGetMethod = getGetMethod =
              io.grpc.MethodDescriptor.<xyz.nova.grpc.route_get_req_v1, xyz.nova.grpc.route_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "get"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.route_get_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.route_v1.getDefaultInstance()))
              .setSchemaDescriptor(new routeMethodDescriptorSupplier("get"))
              .build();
        }
      }
    }
    return getGetMethod;
  }

  private static volatile io.grpc.MethodDescriptor<xyz.nova.grpc.route_create_req_v1,
      xyz.nova.grpc.route_v1> getCreateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "create",
      requestType = xyz.nova.grpc.route_create_req_v1.class,
      responseType = xyz.nova.grpc.route_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<xyz.nova.grpc.route_create_req_v1,
      xyz.nova.grpc.route_v1> getCreateMethod() {
    io.grpc.MethodDescriptor<xyz.nova.grpc.route_create_req_v1, xyz.nova.grpc.route_v1> getCreateMethod;
    if ((getCreateMethod = routeGrpc.getCreateMethod) == null) {
      synchronized (routeGrpc.class) {
        if ((getCreateMethod = routeGrpc.getCreateMethod) == null) {
          routeGrpc.getCreateMethod = getCreateMethod =
              io.grpc.MethodDescriptor.<xyz.nova.grpc.route_create_req_v1, xyz.nova.grpc.route_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "create"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.route_create_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.route_v1.getDefaultInstance()))
              .setSchemaDescriptor(new routeMethodDescriptorSupplier("create"))
              .build();
        }
      }
    }
    return getCreateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<xyz.nova.grpc.route_update_req_v1,
      xyz.nova.grpc.route_v1> getUpdateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "update",
      requestType = xyz.nova.grpc.route_update_req_v1.class,
      responseType = xyz.nova.grpc.route_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<xyz.nova.grpc.route_update_req_v1,
      xyz.nova.grpc.route_v1> getUpdateMethod() {
    io.grpc.MethodDescriptor<xyz.nova.grpc.route_update_req_v1, xyz.nova.grpc.route_v1> getUpdateMethod;
    if ((getUpdateMethod = routeGrpc.getUpdateMethod) == null) {
      synchronized (routeGrpc.class) {
        if ((getUpdateMethod = routeGrpc.getUpdateMethod) == null) {
          routeGrpc.getUpdateMethod = getUpdateMethod =
              io.grpc.MethodDescriptor.<xyz.nova.grpc.route_update_req_v1, xyz.nova.grpc.route_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "update"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.route_update_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.route_v1.getDefaultInstance()))
              .setSchemaDescriptor(new routeMethodDescriptorSupplier("update"))
              .build();
        }
      }
    }
    return getUpdateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<xyz.nova.grpc.route_delete_req_v1,
      xyz.nova.grpc.route_v1> getDeleteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "delete",
      requestType = xyz.nova.grpc.route_delete_req_v1.class,
      responseType = xyz.nova.grpc.route_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<xyz.nova.grpc.route_delete_req_v1,
      xyz.nova.grpc.route_v1> getDeleteMethod() {
    io.grpc.MethodDescriptor<xyz.nova.grpc.route_delete_req_v1, xyz.nova.grpc.route_v1> getDeleteMethod;
    if ((getDeleteMethod = routeGrpc.getDeleteMethod) == null) {
      synchronized (routeGrpc.class) {
        if ((getDeleteMethod = routeGrpc.getDeleteMethod) == null) {
          routeGrpc.getDeleteMethod = getDeleteMethod =
              io.grpc.MethodDescriptor.<xyz.nova.grpc.route_delete_req_v1, xyz.nova.grpc.route_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "delete"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.route_delete_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.route_v1.getDefaultInstance()))
              .setSchemaDescriptor(new routeMethodDescriptorSupplier("delete"))
              .build();
        }
      }
    }
    return getDeleteMethod;
  }

  private static volatile io.grpc.MethodDescriptor<xyz.nova.grpc.route_get_euis_req_v1,
      xyz.nova.grpc.eui_pair_v1> getGetEuisMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "get_euis",
      requestType = xyz.nova.grpc.route_get_euis_req_v1.class,
      responseType = xyz.nova.grpc.eui_pair_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<xyz.nova.grpc.route_get_euis_req_v1,
      xyz.nova.grpc.eui_pair_v1> getGetEuisMethod() {
    io.grpc.MethodDescriptor<xyz.nova.grpc.route_get_euis_req_v1, xyz.nova.grpc.eui_pair_v1> getGetEuisMethod;
    if ((getGetEuisMethod = routeGrpc.getGetEuisMethod) == null) {
      synchronized (routeGrpc.class) {
        if ((getGetEuisMethod = routeGrpc.getGetEuisMethod) == null) {
          routeGrpc.getGetEuisMethod = getGetEuisMethod =
              io.grpc.MethodDescriptor.<xyz.nova.grpc.route_get_euis_req_v1, xyz.nova.grpc.eui_pair_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "get_euis"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.route_get_euis_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.eui_pair_v1.getDefaultInstance()))
              .setSchemaDescriptor(new routeMethodDescriptorSupplier("get_euis"))
              .build();
        }
      }
    }
    return getGetEuisMethod;
  }

  private static volatile io.grpc.MethodDescriptor<xyz.nova.grpc.route_update_euis_req_v1,
      xyz.nova.grpc.route_euis_res_v1> getUpdateEuisMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "update_euis",
      requestType = xyz.nova.grpc.route_update_euis_req_v1.class,
      responseType = xyz.nova.grpc.route_euis_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<xyz.nova.grpc.route_update_euis_req_v1,
      xyz.nova.grpc.route_euis_res_v1> getUpdateEuisMethod() {
    io.grpc.MethodDescriptor<xyz.nova.grpc.route_update_euis_req_v1, xyz.nova.grpc.route_euis_res_v1> getUpdateEuisMethod;
    if ((getUpdateEuisMethod = routeGrpc.getUpdateEuisMethod) == null) {
      synchronized (routeGrpc.class) {
        if ((getUpdateEuisMethod = routeGrpc.getUpdateEuisMethod) == null) {
          routeGrpc.getUpdateEuisMethod = getUpdateEuisMethod =
              io.grpc.MethodDescriptor.<xyz.nova.grpc.route_update_euis_req_v1, xyz.nova.grpc.route_euis_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "update_euis"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.route_update_euis_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.route_euis_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new routeMethodDescriptorSupplier("update_euis"))
              .build();
        }
      }
    }
    return getUpdateEuisMethod;
  }

  private static volatile io.grpc.MethodDescriptor<xyz.nova.grpc.route_delete_euis_req_v1,
      xyz.nova.grpc.route_euis_res_v1> getDeleteEuisMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "delete_euis",
      requestType = xyz.nova.grpc.route_delete_euis_req_v1.class,
      responseType = xyz.nova.grpc.route_euis_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<xyz.nova.grpc.route_delete_euis_req_v1,
      xyz.nova.grpc.route_euis_res_v1> getDeleteEuisMethod() {
    io.grpc.MethodDescriptor<xyz.nova.grpc.route_delete_euis_req_v1, xyz.nova.grpc.route_euis_res_v1> getDeleteEuisMethod;
    if ((getDeleteEuisMethod = routeGrpc.getDeleteEuisMethod) == null) {
      synchronized (routeGrpc.class) {
        if ((getDeleteEuisMethod = routeGrpc.getDeleteEuisMethod) == null) {
          routeGrpc.getDeleteEuisMethod = getDeleteEuisMethod =
              io.grpc.MethodDescriptor.<xyz.nova.grpc.route_delete_euis_req_v1, xyz.nova.grpc.route_euis_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "delete_euis"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.route_delete_euis_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.route_euis_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new routeMethodDescriptorSupplier("delete_euis"))
              .build();
        }
      }
    }
    return getDeleteEuisMethod;
  }

  private static volatile io.grpc.MethodDescriptor<xyz.nova.grpc.route_get_devaddr_ranges_req_v1,
      xyz.nova.grpc.devaddr_range_v1> getGetDevaddrRangesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "get_devaddr_ranges",
      requestType = xyz.nova.grpc.route_get_devaddr_ranges_req_v1.class,
      responseType = xyz.nova.grpc.devaddr_range_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<xyz.nova.grpc.route_get_devaddr_ranges_req_v1,
      xyz.nova.grpc.devaddr_range_v1> getGetDevaddrRangesMethod() {
    io.grpc.MethodDescriptor<xyz.nova.grpc.route_get_devaddr_ranges_req_v1, xyz.nova.grpc.devaddr_range_v1> getGetDevaddrRangesMethod;
    if ((getGetDevaddrRangesMethod = routeGrpc.getGetDevaddrRangesMethod) == null) {
      synchronized (routeGrpc.class) {
        if ((getGetDevaddrRangesMethod = routeGrpc.getGetDevaddrRangesMethod) == null) {
          routeGrpc.getGetDevaddrRangesMethod = getGetDevaddrRangesMethod =
              io.grpc.MethodDescriptor.<xyz.nova.grpc.route_get_devaddr_ranges_req_v1, xyz.nova.grpc.devaddr_range_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "get_devaddr_ranges"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.route_get_devaddr_ranges_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.devaddr_range_v1.getDefaultInstance()))
              .setSchemaDescriptor(new routeMethodDescriptorSupplier("get_devaddr_ranges"))
              .build();
        }
      }
    }
    return getGetDevaddrRangesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<xyz.nova.grpc.route_update_devaddr_ranges_req_v1,
      xyz.nova.grpc.route_devaddr_ranges_res_v1> getUpdateDevaddrRangesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "update_devaddr_ranges",
      requestType = xyz.nova.grpc.route_update_devaddr_ranges_req_v1.class,
      responseType = xyz.nova.grpc.route_devaddr_ranges_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<xyz.nova.grpc.route_update_devaddr_ranges_req_v1,
      xyz.nova.grpc.route_devaddr_ranges_res_v1> getUpdateDevaddrRangesMethod() {
    io.grpc.MethodDescriptor<xyz.nova.grpc.route_update_devaddr_ranges_req_v1, xyz.nova.grpc.route_devaddr_ranges_res_v1> getUpdateDevaddrRangesMethod;
    if ((getUpdateDevaddrRangesMethod = routeGrpc.getUpdateDevaddrRangesMethod) == null) {
      synchronized (routeGrpc.class) {
        if ((getUpdateDevaddrRangesMethod = routeGrpc.getUpdateDevaddrRangesMethod) == null) {
          routeGrpc.getUpdateDevaddrRangesMethod = getUpdateDevaddrRangesMethod =
              io.grpc.MethodDescriptor.<xyz.nova.grpc.route_update_devaddr_ranges_req_v1, xyz.nova.grpc.route_devaddr_ranges_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "update_devaddr_ranges"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.route_update_devaddr_ranges_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.route_devaddr_ranges_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new routeMethodDescriptorSupplier("update_devaddr_ranges"))
              .build();
        }
      }
    }
    return getUpdateDevaddrRangesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<xyz.nova.grpc.route_delete_devaddr_ranges_req_v1,
      xyz.nova.grpc.route_devaddr_ranges_res_v1> getDeleteDevaddrRangesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "delete_devaddr_ranges",
      requestType = xyz.nova.grpc.route_delete_devaddr_ranges_req_v1.class,
      responseType = xyz.nova.grpc.route_devaddr_ranges_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<xyz.nova.grpc.route_delete_devaddr_ranges_req_v1,
      xyz.nova.grpc.route_devaddr_ranges_res_v1> getDeleteDevaddrRangesMethod() {
    io.grpc.MethodDescriptor<xyz.nova.grpc.route_delete_devaddr_ranges_req_v1, xyz.nova.grpc.route_devaddr_ranges_res_v1> getDeleteDevaddrRangesMethod;
    if ((getDeleteDevaddrRangesMethod = routeGrpc.getDeleteDevaddrRangesMethod) == null) {
      synchronized (routeGrpc.class) {
        if ((getDeleteDevaddrRangesMethod = routeGrpc.getDeleteDevaddrRangesMethod) == null) {
          routeGrpc.getDeleteDevaddrRangesMethod = getDeleteDevaddrRangesMethod =
              io.grpc.MethodDescriptor.<xyz.nova.grpc.route_delete_devaddr_ranges_req_v1, xyz.nova.grpc.route_devaddr_ranges_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "delete_devaddr_ranges"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.route_delete_devaddr_ranges_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.route_devaddr_ranges_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new routeMethodDescriptorSupplier("delete_devaddr_ranges"))
              .build();
        }
      }
    }
    return getDeleteDevaddrRangesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<xyz.nova.grpc.route_stream_req_v1,
      xyz.nova.grpc.route_stream_res_v1> getStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "stream",
      requestType = xyz.nova.grpc.route_stream_req_v1.class,
      responseType = xyz.nova.grpc.route_stream_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<xyz.nova.grpc.route_stream_req_v1,
      xyz.nova.grpc.route_stream_res_v1> getStreamMethod() {
    io.grpc.MethodDescriptor<xyz.nova.grpc.route_stream_req_v1, xyz.nova.grpc.route_stream_res_v1> getStreamMethod;
    if ((getStreamMethod = routeGrpc.getStreamMethod) == null) {
      synchronized (routeGrpc.class) {
        if ((getStreamMethod = routeGrpc.getStreamMethod) == null) {
          routeGrpc.getStreamMethod = getStreamMethod =
              io.grpc.MethodDescriptor.<xyz.nova.grpc.route_stream_req_v1, xyz.nova.grpc.route_stream_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "stream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.route_stream_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  xyz.nova.grpc.route_stream_res_v1.getDefaultInstance()))
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
        @java.lang.Override
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
        @java.lang.Override
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
        @java.lang.Override
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
     * <pre>
     * List Routes for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void list(xyz.nova.grpc.route_list_req_v1 request,
        io.grpc.stub.StreamObserver<xyz.nova.grpc.route_list_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListMethod(), responseObserver);
    }

    /**
     * <pre>
     * Get Route for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void get(xyz.nova.grpc.route_get_req_v1 request,
        io.grpc.stub.StreamObserver<xyz.nova.grpc.route_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMethod(), responseObserver);
    }

    /**
     * <pre>
     * Create Route for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void create(xyz.nova.grpc.route_create_req_v1 request,
        io.grpc.stub.StreamObserver<xyz.nova.grpc.route_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateMethod(), responseObserver);
    }

    /**
     * <pre>
     * Update Route for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void update(xyz.nova.grpc.route_update_req_v1 request,
        io.grpc.stub.StreamObserver<xyz.nova.grpc.route_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateMethod(), responseObserver);
    }

    /**
     * <pre>
     * Delete Route for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void delete(xyz.nova.grpc.route_delete_req_v1 request,
        io.grpc.stub.StreamObserver<xyz.nova.grpc.route_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteMethod(), responseObserver);
    }

    /**
     * <pre>
     * Get EUIs for a Route (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void getEuis(xyz.nova.grpc.route_get_euis_req_v1 request,
        io.grpc.stub.StreamObserver<xyz.nova.grpc.eui_pair_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetEuisMethod(), responseObserver);
    }

    /**
     * <pre>
     * Update (single add or remove) EUIs for a Route (auth
     * delegate_keys/owner/admin)
     * </pre>
     */
    public io.grpc.stub.StreamObserver<xyz.nova.grpc.route_update_euis_req_v1> updateEuis(
        io.grpc.stub.StreamObserver<xyz.nova.grpc.route_euis_res_v1> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getUpdateEuisMethod(), responseObserver);
    }

    /**
     * <pre>
     * Delete all EUIs for a Route (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void deleteEuis(xyz.nova.grpc.route_delete_euis_req_v1 request,
        io.grpc.stub.StreamObserver<xyz.nova.grpc.route_euis_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteEuisMethod(), responseObserver);
    }

    /**
     * <pre>
     * Get DevAddr Ranges for a Route (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void getDevaddrRanges(xyz.nova.grpc.route_get_devaddr_ranges_req_v1 request,
        io.grpc.stub.StreamObserver<xyz.nova.grpc.devaddr_range_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetDevaddrRangesMethod(), responseObserver);
    }

    /**
     * <pre>
     * Update (single add or remove) DevAddr Ranges for a Route (auth
     * delegate_keys/owner/admin)
     * </pre>
     */
    public io.grpc.stub.StreamObserver<xyz.nova.grpc.route_update_devaddr_ranges_req_v1> updateDevaddrRanges(
        io.grpc.stub.StreamObserver<xyz.nova.grpc.route_devaddr_ranges_res_v1> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getUpdateDevaddrRangesMethod(), responseObserver);
    }

    /**
     * <pre>
     * Delete all DevAddr Ranges for a Route (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void deleteDevaddrRanges(xyz.nova.grpc.route_delete_devaddr_ranges_req_v1 request,
        io.grpc.stub.StreamObserver<xyz.nova.grpc.route_devaddr_ranges_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteDevaddrRangesMethod(), responseObserver);
    }

    /**
     * <pre>
     * Stream Routes update (auth admin only)
     * </pre>
     */
    public void stream(xyz.nova.grpc.route_stream_req_v1 request,
        io.grpc.stub.StreamObserver<xyz.nova.grpc.route_stream_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getStreamMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getListMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                xyz.nova.grpc.route_list_req_v1,
                xyz.nova.grpc.route_list_res_v1>(
                  this, METHODID_LIST)))
          .addMethod(
            getGetMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                xyz.nova.grpc.route_get_req_v1,
                xyz.nova.grpc.route_v1>(
                  this, METHODID_GET)))
          .addMethod(
            getCreateMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                xyz.nova.grpc.route_create_req_v1,
                xyz.nova.grpc.route_v1>(
                  this, METHODID_CREATE)))
          .addMethod(
            getUpdateMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                xyz.nova.grpc.route_update_req_v1,
                xyz.nova.grpc.route_v1>(
                  this, METHODID_UPDATE)))
          .addMethod(
            getDeleteMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                xyz.nova.grpc.route_delete_req_v1,
                xyz.nova.grpc.route_v1>(
                  this, METHODID_DELETE)))
          .addMethod(
            getGetEuisMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                xyz.nova.grpc.route_get_euis_req_v1,
                xyz.nova.grpc.eui_pair_v1>(
                  this, METHODID_GET_EUIS)))
          .addMethod(
            getUpdateEuisMethod(),
            io.grpc.stub.ServerCalls.asyncClientStreamingCall(
              new MethodHandlers<
                xyz.nova.grpc.route_update_euis_req_v1,
                xyz.nova.grpc.route_euis_res_v1>(
                  this, METHODID_UPDATE_EUIS)))
          .addMethod(
            getDeleteEuisMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                xyz.nova.grpc.route_delete_euis_req_v1,
                xyz.nova.grpc.route_euis_res_v1>(
                  this, METHODID_DELETE_EUIS)))
          .addMethod(
            getGetDevaddrRangesMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                xyz.nova.grpc.route_get_devaddr_ranges_req_v1,
                xyz.nova.grpc.devaddr_range_v1>(
                  this, METHODID_GET_DEVADDR_RANGES)))
          .addMethod(
            getUpdateDevaddrRangesMethod(),
            io.grpc.stub.ServerCalls.asyncClientStreamingCall(
              new MethodHandlers<
                xyz.nova.grpc.route_update_devaddr_ranges_req_v1,
                xyz.nova.grpc.route_devaddr_ranges_res_v1>(
                  this, METHODID_UPDATE_DEVADDR_RANGES)))
          .addMethod(
            getDeleteDevaddrRangesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                xyz.nova.grpc.route_delete_devaddr_ranges_req_v1,
                xyz.nova.grpc.route_devaddr_ranges_res_v1>(
                  this, METHODID_DELETE_DEVADDR_RANGES)))
          .addMethod(
            getStreamMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                xyz.nova.grpc.route_stream_req_v1,
                xyz.nova.grpc.route_stream_res_v1>(
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

    @java.lang.Override
    protected routeStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new routeStub(channel, callOptions);
    }

    /**
     * <pre>
     * List Routes for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void list(xyz.nova.grpc.route_list_req_v1 request,
        io.grpc.stub.StreamObserver<xyz.nova.grpc.route_list_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Get Route for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void get(xyz.nova.grpc.route_get_req_v1 request,
        io.grpc.stub.StreamObserver<xyz.nova.grpc.route_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Create Route for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void create(xyz.nova.grpc.route_create_req_v1 request,
        io.grpc.stub.StreamObserver<xyz.nova.grpc.route_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Update Route for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void update(xyz.nova.grpc.route_update_req_v1 request,
        io.grpc.stub.StreamObserver<xyz.nova.grpc.route_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Delete Route for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void delete(xyz.nova.grpc.route_delete_req_v1 request,
        io.grpc.stub.StreamObserver<xyz.nova.grpc.route_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Get EUIs for a Route (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void getEuis(xyz.nova.grpc.route_get_euis_req_v1 request,
        io.grpc.stub.StreamObserver<xyz.nova.grpc.eui_pair_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGetEuisMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Update (single add or remove) EUIs for a Route (auth
     * delegate_keys/owner/admin)
     * </pre>
     */
    public io.grpc.stub.StreamObserver<xyz.nova.grpc.route_update_euis_req_v1> updateEuis(
        io.grpc.stub.StreamObserver<xyz.nova.grpc.route_euis_res_v1> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getUpdateEuisMethod(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     * Delete all EUIs for a Route (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void deleteEuis(xyz.nova.grpc.route_delete_euis_req_v1 request,
        io.grpc.stub.StreamObserver<xyz.nova.grpc.route_euis_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteEuisMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Get DevAddr Ranges for a Route (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void getDevaddrRanges(xyz.nova.grpc.route_get_devaddr_ranges_req_v1 request,
        io.grpc.stub.StreamObserver<xyz.nova.grpc.devaddr_range_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGetDevaddrRangesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Update (single add or remove) DevAddr Ranges for a Route (auth
     * delegate_keys/owner/admin)
     * </pre>
     */
    public io.grpc.stub.StreamObserver<xyz.nova.grpc.route_update_devaddr_ranges_req_v1> updateDevaddrRanges(
        io.grpc.stub.StreamObserver<xyz.nova.grpc.route_devaddr_ranges_res_v1> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getUpdateDevaddrRangesMethod(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     * Delete all DevAddr Ranges for a Route (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void deleteDevaddrRanges(xyz.nova.grpc.route_delete_devaddr_ranges_req_v1 request,
        io.grpc.stub.StreamObserver<xyz.nova.grpc.route_devaddr_ranges_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteDevaddrRangesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Stream Routes update (auth admin only)
     * </pre>
     */
    public void stream(xyz.nova.grpc.route_stream_req_v1 request,
        io.grpc.stub.StreamObserver<xyz.nova.grpc.route_stream_res_v1> responseObserver) {
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

    @java.lang.Override
    protected routeBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new routeBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * List Routes for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public xyz.nova.grpc.route_list_res_v1 list(xyz.nova.grpc.route_list_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Get Route for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public xyz.nova.grpc.route_v1 get(xyz.nova.grpc.route_get_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Create Route for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public xyz.nova.grpc.route_v1 create(xyz.nova.grpc.route_create_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Update Route for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public xyz.nova.grpc.route_v1 update(xyz.nova.grpc.route_update_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Delete Route for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public xyz.nova.grpc.route_v1 delete(xyz.nova.grpc.route_delete_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Get EUIs for a Route (auth delegate_keys/owner/admin)
     * </pre>
     */
    public java.util.Iterator<xyz.nova.grpc.eui_pair_v1> getEuis(
        xyz.nova.grpc.route_get_euis_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGetEuisMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Delete all EUIs for a Route (auth delegate_keys/owner/admin)
     * </pre>
     */
    public xyz.nova.grpc.route_euis_res_v1 deleteEuis(xyz.nova.grpc.route_delete_euis_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteEuisMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Get DevAddr Ranges for a Route (auth delegate_keys/owner/admin)
     * </pre>
     */
    public java.util.Iterator<xyz.nova.grpc.devaddr_range_v1> getDevaddrRanges(
        xyz.nova.grpc.route_get_devaddr_ranges_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGetDevaddrRangesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Delete all DevAddr Ranges for a Route (auth delegate_keys/owner/admin)
     * </pre>
     */
    public xyz.nova.grpc.route_devaddr_ranges_res_v1 deleteDevaddrRanges(xyz.nova.grpc.route_delete_devaddr_ranges_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteDevaddrRangesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Stream Routes update (auth admin only)
     * </pre>
     */
    public java.util.Iterator<xyz.nova.grpc.route_stream_res_v1> stream(
        xyz.nova.grpc.route_stream_req_v1 request) {
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

    @java.lang.Override
    protected routeFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new routeFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * List Routes for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<xyz.nova.grpc.route_list_res_v1> list(
        xyz.nova.grpc.route_list_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Get Route for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<xyz.nova.grpc.route_v1> get(
        xyz.nova.grpc.route_get_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Create Route for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<xyz.nova.grpc.route_v1> create(
        xyz.nova.grpc.route_create_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Update Route for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<xyz.nova.grpc.route_v1> update(
        xyz.nova.grpc.route_update_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Delete Route for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<xyz.nova.grpc.route_v1> delete(
        xyz.nova.grpc.route_delete_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Delete all EUIs for a Route (auth delegate_keys/owner/admin)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<xyz.nova.grpc.route_euis_res_v1> deleteEuis(
        xyz.nova.grpc.route_delete_euis_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteEuisMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Delete all DevAddr Ranges for a Route (auth delegate_keys/owner/admin)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<xyz.nova.grpc.route_devaddr_ranges_res_v1> deleteDevaddrRanges(
        xyz.nova.grpc.route_delete_devaddr_ranges_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteDevaddrRangesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LIST = 0;
  private static final int METHODID_GET = 1;
  private static final int METHODID_CREATE = 2;
  private static final int METHODID_UPDATE = 3;
  private static final int METHODID_DELETE = 4;
  private static final int METHODID_GET_EUIS = 5;
  private static final int METHODID_DELETE_EUIS = 6;
  private static final int METHODID_GET_DEVADDR_RANGES = 7;
  private static final int METHODID_DELETE_DEVADDR_RANGES = 8;
  private static final int METHODID_STREAM = 9;
  private static final int METHODID_UPDATE_EUIS = 10;
  private static final int METHODID_UPDATE_DEVADDR_RANGES = 11;

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

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LIST:
          serviceImpl.list((xyz.nova.grpc.route_list_req_v1) request,
              (io.grpc.stub.StreamObserver<xyz.nova.grpc.route_list_res_v1>) responseObserver);
          break;
        case METHODID_GET:
          serviceImpl.get((xyz.nova.grpc.route_get_req_v1) request,
              (io.grpc.stub.StreamObserver<xyz.nova.grpc.route_v1>) responseObserver);
          break;
        case METHODID_CREATE:
          serviceImpl.create((xyz.nova.grpc.route_create_req_v1) request,
              (io.grpc.stub.StreamObserver<xyz.nova.grpc.route_v1>) responseObserver);
          break;
        case METHODID_UPDATE:
          serviceImpl.update((xyz.nova.grpc.route_update_req_v1) request,
              (io.grpc.stub.StreamObserver<xyz.nova.grpc.route_v1>) responseObserver);
          break;
        case METHODID_DELETE:
          serviceImpl.delete((xyz.nova.grpc.route_delete_req_v1) request,
              (io.grpc.stub.StreamObserver<xyz.nova.grpc.route_v1>) responseObserver);
          break;
        case METHODID_GET_EUIS:
          serviceImpl.getEuis((xyz.nova.grpc.route_get_euis_req_v1) request,
              (io.grpc.stub.StreamObserver<xyz.nova.grpc.eui_pair_v1>) responseObserver);
          break;
        case METHODID_DELETE_EUIS:
          serviceImpl.deleteEuis((xyz.nova.grpc.route_delete_euis_req_v1) request,
              (io.grpc.stub.StreamObserver<xyz.nova.grpc.route_euis_res_v1>) responseObserver);
          break;
        case METHODID_GET_DEVADDR_RANGES:
          serviceImpl.getDevaddrRanges((xyz.nova.grpc.route_get_devaddr_ranges_req_v1) request,
              (io.grpc.stub.StreamObserver<xyz.nova.grpc.devaddr_range_v1>) responseObserver);
          break;
        case METHODID_DELETE_DEVADDR_RANGES:
          serviceImpl.deleteDevaddrRanges((xyz.nova.grpc.route_delete_devaddr_ranges_req_v1) request,
              (io.grpc.stub.StreamObserver<xyz.nova.grpc.route_devaddr_ranges_res_v1>) responseObserver);
          break;
        case METHODID_STREAM:
          serviceImpl.stream((xyz.nova.grpc.route_stream_req_v1) request,
              (io.grpc.stub.StreamObserver<xyz.nova.grpc.route_stream_res_v1>) responseObserver);
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
        case METHODID_UPDATE_EUIS:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.updateEuis(
              (io.grpc.stub.StreamObserver<xyz.nova.grpc.route_euis_res_v1>) responseObserver);
        case METHODID_UPDATE_DEVADDR_RANGES:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.updateDevaddrRanges(
              (io.grpc.stub.StreamObserver<xyz.nova.grpc.route_devaddr_ranges_res_v1>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class routeBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    routeBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return xyz.nova.grpc.IotConfig.getDescriptor();
    }

    @java.lang.Override
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

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (routeGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new routeFileDescriptorSupplier())
              .addMethod(getListMethod())
              .addMethod(getGetMethod())
              .addMethod(getCreateMethod())
              .addMethod(getUpdateMethod())
              .addMethod(getDeleteMethod())
              .addMethod(getGetEuisMethod())
              .addMethod(getUpdateEuisMethod())
              .addMethod(getDeleteEuisMethod())
              .addMethod(getGetDevaddrRangesMethod())
              .addMethod(getUpdateDevaddrRangesMethod())
              .addMethod(getDeleteDevaddrRangesMethod())
              .addMethod(getStreamMethod())
              .build();
        }
      }
    }
    return result;
  }
}
