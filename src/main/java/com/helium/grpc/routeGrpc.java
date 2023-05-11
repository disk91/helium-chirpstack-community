package com.helium.grpc;

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
  private static volatile io.grpc.MethodDescriptor<route_list_req_v1,
      route_list_res_v1> getListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "list",
      requestType = route_list_req_v1.class,
      responseType = route_list_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<route_list_req_v1,
      route_list_res_v1> getListMethod() {
    io.grpc.MethodDescriptor<route_list_req_v1, route_list_res_v1> getListMethod;
    if ((getListMethod = routeGrpc.getListMethod) == null) {
      synchronized (routeGrpc.class) {
        if ((getListMethod = routeGrpc.getListMethod) == null) {
          routeGrpc.getListMethod = getListMethod =
              io.grpc.MethodDescriptor.<route_list_req_v1, route_list_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "list"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  route_list_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  route_list_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new routeMethodDescriptorSupplier("list"))
              .build();
        }
      }
    }
    return getListMethod;
  }

  private static volatile io.grpc.MethodDescriptor<route_get_req_v1,
      route_res_v1> getGetMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "get",
      requestType = route_get_req_v1.class,
      responseType = route_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<route_get_req_v1,
      route_res_v1> getGetMethod() {
    io.grpc.MethodDescriptor<route_get_req_v1, route_res_v1> getGetMethod;
    if ((getGetMethod = routeGrpc.getGetMethod) == null) {
      synchronized (routeGrpc.class) {
        if ((getGetMethod = routeGrpc.getGetMethod) == null) {
          routeGrpc.getGetMethod = getGetMethod =
              io.grpc.MethodDescriptor.<route_get_req_v1, route_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "get"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  route_get_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  route_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new routeMethodDescriptorSupplier("get"))
              .build();
        }
      }
    }
    return getGetMethod;
  }

  private static volatile io.grpc.MethodDescriptor<route_create_req_v1,
      route_res_v1> getCreateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "create",
      requestType = route_create_req_v1.class,
      responseType = route_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<route_create_req_v1,
      route_res_v1> getCreateMethod() {
    io.grpc.MethodDescriptor<route_create_req_v1, route_res_v1> getCreateMethod;
    if ((getCreateMethod = routeGrpc.getCreateMethod) == null) {
      synchronized (routeGrpc.class) {
        if ((getCreateMethod = routeGrpc.getCreateMethod) == null) {
          routeGrpc.getCreateMethod = getCreateMethod =
              io.grpc.MethodDescriptor.<route_create_req_v1, route_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "create"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  route_create_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  route_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new routeMethodDescriptorSupplier("create"))
              .build();
        }
      }
    }
    return getCreateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<route_update_req_v1,
      route_res_v1> getUpdateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "update",
      requestType = route_update_req_v1.class,
      responseType = route_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<route_update_req_v1,
      route_res_v1> getUpdateMethod() {
    io.grpc.MethodDescriptor<route_update_req_v1, route_res_v1> getUpdateMethod;
    if ((getUpdateMethod = routeGrpc.getUpdateMethod) == null) {
      synchronized (routeGrpc.class) {
        if ((getUpdateMethod = routeGrpc.getUpdateMethod) == null) {
          routeGrpc.getUpdateMethod = getUpdateMethod =
              io.grpc.MethodDescriptor.<route_update_req_v1, route_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "update"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  route_update_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  route_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new routeMethodDescriptorSupplier("update"))
              .build();
        }
      }
    }
    return getUpdateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<route_delete_req_v1,
      route_res_v1> getDeleteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "delete",
      requestType = route_delete_req_v1.class,
      responseType = route_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<route_delete_req_v1,
      route_res_v1> getDeleteMethod() {
    io.grpc.MethodDescriptor<route_delete_req_v1, route_res_v1> getDeleteMethod;
    if ((getDeleteMethod = routeGrpc.getDeleteMethod) == null) {
      synchronized (routeGrpc.class) {
        if ((getDeleteMethod = routeGrpc.getDeleteMethod) == null) {
          routeGrpc.getDeleteMethod = getDeleteMethod =
              io.grpc.MethodDescriptor.<route_delete_req_v1, route_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "delete"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  route_delete_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  route_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new routeMethodDescriptorSupplier("delete"))
              .build();
        }
      }
    }
    return getDeleteMethod;
  }

  private static volatile io.grpc.MethodDescriptor<route_stream_req_v1,
      route_stream_res_v1> getStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "stream",
      requestType = route_stream_req_v1.class,
      responseType = route_stream_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<route_stream_req_v1,
      route_stream_res_v1> getStreamMethod() {
    io.grpc.MethodDescriptor<route_stream_req_v1, route_stream_res_v1> getStreamMethod;
    if ((getStreamMethod = routeGrpc.getStreamMethod) == null) {
      synchronized (routeGrpc.class) {
        if ((getStreamMethod = routeGrpc.getStreamMethod) == null) {
          routeGrpc.getStreamMethod = getStreamMethod =
              io.grpc.MethodDescriptor.<route_stream_req_v1, route_stream_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "stream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  route_stream_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  route_stream_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new routeMethodDescriptorSupplier("stream"))
              .build();
        }
      }
    }
    return getStreamMethod;
  }

  private static volatile io.grpc.MethodDescriptor<route_get_euis_req_v1,
      eui_pair_v1> getGetEuisMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "get_euis",
      requestType = route_get_euis_req_v1.class,
      responseType = eui_pair_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<route_get_euis_req_v1,
      eui_pair_v1> getGetEuisMethod() {
    io.grpc.MethodDescriptor<route_get_euis_req_v1, eui_pair_v1> getGetEuisMethod;
    if ((getGetEuisMethod = routeGrpc.getGetEuisMethod) == null) {
      synchronized (routeGrpc.class) {
        if ((getGetEuisMethod = routeGrpc.getGetEuisMethod) == null) {
          routeGrpc.getGetEuisMethod = getGetEuisMethod =
              io.grpc.MethodDescriptor.<route_get_euis_req_v1, eui_pair_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "get_euis"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  route_get_euis_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  eui_pair_v1.getDefaultInstance()))
              .setSchemaDescriptor(new routeMethodDescriptorSupplier("get_euis"))
              .build();
        }
      }
    }
    return getGetEuisMethod;
  }

  private static volatile io.grpc.MethodDescriptor<route_update_euis_req_v1,
      route_euis_res_v1> getUpdateEuisMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "update_euis",
      requestType = route_update_euis_req_v1.class,
      responseType = route_euis_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<route_update_euis_req_v1,
      route_euis_res_v1> getUpdateEuisMethod() {
    io.grpc.MethodDescriptor<route_update_euis_req_v1, route_euis_res_v1> getUpdateEuisMethod;
    if ((getUpdateEuisMethod = routeGrpc.getUpdateEuisMethod) == null) {
      synchronized (routeGrpc.class) {
        if ((getUpdateEuisMethod = routeGrpc.getUpdateEuisMethod) == null) {
          routeGrpc.getUpdateEuisMethod = getUpdateEuisMethod =
              io.grpc.MethodDescriptor.<route_update_euis_req_v1, route_euis_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "update_euis"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  route_update_euis_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  route_euis_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new routeMethodDescriptorSupplier("update_euis"))
              .build();
        }
      }
    }
    return getUpdateEuisMethod;
  }

  private static volatile io.grpc.MethodDescriptor<route_get_devaddr_ranges_req_v1,
      devaddr_range_v1> getGetDevaddrRangesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "get_devaddr_ranges",
      requestType = route_get_devaddr_ranges_req_v1.class,
      responseType = devaddr_range_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<route_get_devaddr_ranges_req_v1,
      devaddr_range_v1> getGetDevaddrRangesMethod() {
    io.grpc.MethodDescriptor<route_get_devaddr_ranges_req_v1, devaddr_range_v1> getGetDevaddrRangesMethod;
    if ((getGetDevaddrRangesMethod = routeGrpc.getGetDevaddrRangesMethod) == null) {
      synchronized (routeGrpc.class) {
        if ((getGetDevaddrRangesMethod = routeGrpc.getGetDevaddrRangesMethod) == null) {
          routeGrpc.getGetDevaddrRangesMethod = getGetDevaddrRangesMethod =
              io.grpc.MethodDescriptor.<route_get_devaddr_ranges_req_v1, devaddr_range_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "get_devaddr_ranges"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  route_get_devaddr_ranges_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  devaddr_range_v1.getDefaultInstance()))
              .setSchemaDescriptor(new routeMethodDescriptorSupplier("get_devaddr_ranges"))
              .build();
        }
      }
    }
    return getGetDevaddrRangesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<route_update_devaddr_ranges_req_v1,
      route_devaddr_ranges_res_v1> getUpdateDevaddrRangesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "update_devaddr_ranges",
      requestType = route_update_devaddr_ranges_req_v1.class,
      responseType = route_devaddr_ranges_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<route_update_devaddr_ranges_req_v1,
      route_devaddr_ranges_res_v1> getUpdateDevaddrRangesMethod() {
    io.grpc.MethodDescriptor<route_update_devaddr_ranges_req_v1, route_devaddr_ranges_res_v1> getUpdateDevaddrRangesMethod;
    if ((getUpdateDevaddrRangesMethod = routeGrpc.getUpdateDevaddrRangesMethod) == null) {
      synchronized (routeGrpc.class) {
        if ((getUpdateDevaddrRangesMethod = routeGrpc.getUpdateDevaddrRangesMethod) == null) {
          routeGrpc.getUpdateDevaddrRangesMethod = getUpdateDevaddrRangesMethod =
              io.grpc.MethodDescriptor.<route_update_devaddr_ranges_req_v1, route_devaddr_ranges_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "update_devaddr_ranges"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  route_update_devaddr_ranges_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  route_devaddr_ranges_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new routeMethodDescriptorSupplier("update_devaddr_ranges"))
              .build();
        }
      }
    }
    return getUpdateDevaddrRangesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<route_skf_list_req_v1,
      skf_v1> getListSkfsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "list_skfs",
      requestType = route_skf_list_req_v1.class,
      responseType = skf_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<route_skf_list_req_v1,
      skf_v1> getListSkfsMethod() {
    io.grpc.MethodDescriptor<route_skf_list_req_v1, skf_v1> getListSkfsMethod;
    if ((getListSkfsMethod = routeGrpc.getListSkfsMethod) == null) {
      synchronized (routeGrpc.class) {
        if ((getListSkfsMethod = routeGrpc.getListSkfsMethod) == null) {
          routeGrpc.getListSkfsMethod = getListSkfsMethod =
              io.grpc.MethodDescriptor.<route_skf_list_req_v1, skf_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "list_skfs"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  route_skf_list_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  skf_v1.getDefaultInstance()))
              .setSchemaDescriptor(new routeMethodDescriptorSupplier("list_skfs"))
              .build();
        }
      }
    }
    return getListSkfsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<route_skf_get_req_v1,
      skf_v1> getGetSkfsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "get_skfs",
      requestType = route_skf_get_req_v1.class,
      responseType = skf_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<route_skf_get_req_v1,
      skf_v1> getGetSkfsMethod() {
    io.grpc.MethodDescriptor<route_skf_get_req_v1, skf_v1> getGetSkfsMethod;
    if ((getGetSkfsMethod = routeGrpc.getGetSkfsMethod) == null) {
      synchronized (routeGrpc.class) {
        if ((getGetSkfsMethod = routeGrpc.getGetSkfsMethod) == null) {
          routeGrpc.getGetSkfsMethod = getGetSkfsMethod =
              io.grpc.MethodDescriptor.<route_skf_get_req_v1, skf_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "get_skfs"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  route_skf_get_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  skf_v1.getDefaultInstance()))
              .setSchemaDescriptor(new routeMethodDescriptorSupplier("get_skfs"))
              .build();
        }
      }
    }
    return getGetSkfsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<route_skf_update_req_v1,
      route_skf_update_res_v1> getUpdateSkfsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "update_skfs",
      requestType = route_skf_update_req_v1.class,
      responseType = route_skf_update_res_v1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<route_skf_update_req_v1,
      route_skf_update_res_v1> getUpdateSkfsMethod() {
    io.grpc.MethodDescriptor<route_skf_update_req_v1, route_skf_update_res_v1> getUpdateSkfsMethod;
    if ((getUpdateSkfsMethod = routeGrpc.getUpdateSkfsMethod) == null) {
      synchronized (routeGrpc.class) {
        if ((getUpdateSkfsMethod = routeGrpc.getUpdateSkfsMethod) == null) {
          routeGrpc.getUpdateSkfsMethod = getUpdateSkfsMethod =
              io.grpc.MethodDescriptor.<route_skf_update_req_v1, route_skf_update_res_v1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "update_skfs"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  route_skf_update_req_v1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  route_skf_update_res_v1.getDefaultInstance()))
              .setSchemaDescriptor(new routeMethodDescriptorSupplier("update_skfs"))
              .build();
        }
      }
    }
    return getUpdateSkfsMethod;
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
     * <pre>
     * List Routes for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void list(route_list_req_v1 request,
                     io.grpc.stub.StreamObserver<route_list_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListMethod(), responseObserver);
    }

    /**
     * <pre>
     * Get Route for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void get(route_get_req_v1 request,
                    io.grpc.stub.StreamObserver<route_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMethod(), responseObserver);
    }

    /**
     * <pre>
     * Create Route for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void create(route_create_req_v1 request,
                       io.grpc.stub.StreamObserver<route_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateMethod(), responseObserver);
    }

    /**
     * <pre>
     * Update Route for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void update(route_update_req_v1 request,
                       io.grpc.stub.StreamObserver<route_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateMethod(), responseObserver);
    }

    /**
     * <pre>
     * Delete Route for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void delete(route_delete_req_v1 request,
                       io.grpc.stub.StreamObserver<route_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteMethod(), responseObserver);
    }

    /**
     * <pre>
     * Stream Routes update (auth admin only)
     * </pre>
     */
    public void stream(route_stream_req_v1 request,
                       io.grpc.stub.StreamObserver<route_stream_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getStreamMethod(), responseObserver);
    }

    /**
     * <pre>
     * Get EUIs for a Route (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void getEuis(route_get_euis_req_v1 request,
                        io.grpc.stub.StreamObserver<eui_pair_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetEuisMethod(), responseObserver);
    }

    /**
     * <pre>
     * Update (single add or remove) EUIs for a Route (auth
     * delegate_keys/owner/admin)
     * </pre>
     */
    public io.grpc.stub.StreamObserver<route_update_euis_req_v1> updateEuis(
        io.grpc.stub.StreamObserver<route_euis_res_v1> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getUpdateEuisMethod(), responseObserver);
    }

    /**
     * <pre>
     * Get DevAddr Ranges for a Route (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void getDevaddrRanges(route_get_devaddr_ranges_req_v1 request,
                                 io.grpc.stub.StreamObserver<devaddr_range_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetDevaddrRangesMethod(), responseObserver);
    }

    /**
     * <pre>
     * Update (single add or remove) DevAddr Ranges for a Route (auth
     * delegate_keys/owner/admin)
     * </pre>
     */
    public io.grpc.stub.StreamObserver<route_update_devaddr_ranges_req_v1> updateDevaddrRanges(
        io.grpc.stub.StreamObserver<route_devaddr_ranges_res_v1> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getUpdateDevaddrRangesMethod(), responseObserver);
    }

    /**
     * <pre>
     * List Filters for a Route (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void listSkfs(route_skf_list_req_v1 request,
                         io.grpc.stub.StreamObserver<skf_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListSkfsMethod(), responseObserver);
    }

    /**
     * <pre>
     * List Filters for a DevAddr (auth delegate_keys/owner/admin
     * </pre>
     */
    public void getSkfs(route_skf_get_req_v1 request,
                        io.grpc.stub.StreamObserver<skf_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetSkfsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Update Filters for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void updateSkfs(route_skf_update_req_v1 request,
                           io.grpc.stub.StreamObserver<route_skf_update_res_v1> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateSkfsMethod(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getListMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                route_list_req_v1,
                route_list_res_v1>(
                  this, METHODID_LIST)))
          .addMethod(
            getGetMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                route_get_req_v1,
                route_res_v1>(
                  this, METHODID_GET)))
          .addMethod(
            getCreateMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                route_create_req_v1,
                route_res_v1>(
                  this, METHODID_CREATE)))
          .addMethod(
            getUpdateMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                route_update_req_v1,
                route_res_v1>(
                  this, METHODID_UPDATE)))
          .addMethod(
            getDeleteMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                route_delete_req_v1,
                route_res_v1>(
                  this, METHODID_DELETE)))
          .addMethod(
            getStreamMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                route_stream_req_v1,
                route_stream_res_v1>(
                  this, METHODID_STREAM)))
          .addMethod(
            getGetEuisMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                route_get_euis_req_v1,
                eui_pair_v1>(
                  this, METHODID_GET_EUIS)))
          .addMethod(
            getUpdateEuisMethod(),
            io.grpc.stub.ServerCalls.asyncClientStreamingCall(
              new MethodHandlers<
                route_update_euis_req_v1,
                route_euis_res_v1>(
                  this, METHODID_UPDATE_EUIS)))
          .addMethod(
            getGetDevaddrRangesMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                route_get_devaddr_ranges_req_v1,
                devaddr_range_v1>(
                  this, METHODID_GET_DEVADDR_RANGES)))
          .addMethod(
            getUpdateDevaddrRangesMethod(),
            io.grpc.stub.ServerCalls.asyncClientStreamingCall(
              new MethodHandlers<
                route_update_devaddr_ranges_req_v1,
                route_devaddr_ranges_res_v1>(
                  this, METHODID_UPDATE_DEVADDR_RANGES)))
          .addMethod(
            getListSkfsMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                route_skf_list_req_v1,
                skf_v1>(
                  this, METHODID_LIST_SKFS)))
          .addMethod(
            getGetSkfsMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                route_skf_get_req_v1,
                skf_v1>(
                  this, METHODID_GET_SKFS)))
          .addMethod(
            getUpdateSkfsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                route_skf_update_req_v1,
                route_skf_update_res_v1>(
                  this, METHODID_UPDATE_SKFS)))
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
     * <pre>
     * List Routes for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void list(route_list_req_v1 request,
                     io.grpc.stub.StreamObserver<route_list_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Get Route for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void get(route_get_req_v1 request,
                    io.grpc.stub.StreamObserver<route_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Create Route for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void create(route_create_req_v1 request,
                       io.grpc.stub.StreamObserver<route_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Update Route for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void update(route_update_req_v1 request,
                       io.grpc.stub.StreamObserver<route_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Delete Route for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void delete(route_delete_req_v1 request,
                       io.grpc.stub.StreamObserver<route_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Stream Routes update (auth admin only)
     * </pre>
     */
    public void stream(route_stream_req_v1 request,
                       io.grpc.stub.StreamObserver<route_stream_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getStreamMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Get EUIs for a Route (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void getEuis(route_get_euis_req_v1 request,
                        io.grpc.stub.StreamObserver<eui_pair_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGetEuisMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Update (single add or remove) EUIs for a Route (auth
     * delegate_keys/owner/admin)
     * </pre>
     */
    public io.grpc.stub.StreamObserver<route_update_euis_req_v1> updateEuis(
        io.grpc.stub.StreamObserver<route_euis_res_v1> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getUpdateEuisMethod(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     * Get DevAddr Ranges for a Route (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void getDevaddrRanges(route_get_devaddr_ranges_req_v1 request,
                                 io.grpc.stub.StreamObserver<devaddr_range_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGetDevaddrRangesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Update (single add or remove) DevAddr Ranges for a Route (auth
     * delegate_keys/owner/admin)
     * </pre>
     */
    public io.grpc.stub.StreamObserver<route_update_devaddr_ranges_req_v1> updateDevaddrRanges(
        io.grpc.stub.StreamObserver<route_devaddr_ranges_res_v1> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getUpdateDevaddrRangesMethod(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     * List Filters for a Route (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void listSkfs(route_skf_list_req_v1 request,
                         io.grpc.stub.StreamObserver<skf_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getListSkfsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * List Filters for a DevAddr (auth delegate_keys/owner/admin
     * </pre>
     */
    public void getSkfs(route_skf_get_req_v1 request,
                        io.grpc.stub.StreamObserver<skf_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGetSkfsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Update Filters for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public void updateSkfs(route_skf_update_req_v1 request,
                           io.grpc.stub.StreamObserver<route_skf_update_res_v1> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateSkfsMethod(), getCallOptions()), request, responseObserver);
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
     * <pre>
     * List Routes for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public route_list_res_v1 list(route_list_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Get Route for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public route_res_v1 get(route_get_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Create Route for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public route_res_v1 create(route_create_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Update Route for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public route_res_v1 update(route_update_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Delete Route for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public route_res_v1 delete(route_delete_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Stream Routes update (auth admin only)
     * </pre>
     */
    public java.util.Iterator<route_stream_res_v1> stream(
        route_stream_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getStreamMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Get EUIs for a Route (auth delegate_keys/owner/admin)
     * </pre>
     */
    public java.util.Iterator<eui_pair_v1> getEuis(
        route_get_euis_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGetEuisMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Get DevAddr Ranges for a Route (auth delegate_keys/owner/admin)
     * </pre>
     */
    public java.util.Iterator<devaddr_range_v1> getDevaddrRanges(
        route_get_devaddr_ranges_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGetDevaddrRangesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * List Filters for a Route (auth delegate_keys/owner/admin)
     * </pre>
     */
    public java.util.Iterator<skf_v1> listSkfs(
        route_skf_list_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getListSkfsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * List Filters for a DevAddr (auth delegate_keys/owner/admin
     * </pre>
     */
    public java.util.Iterator<skf_v1> getSkfs(
        route_skf_get_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGetSkfsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Update Filters for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public route_skf_update_res_v1 updateSkfs(route_skf_update_req_v1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateSkfsMethod(), getCallOptions(), request);
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
     * <pre>
     * List Routes for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<route_list_res_v1> list(
        route_list_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Get Route for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<route_res_v1> get(
        route_get_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Create Route for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<route_res_v1> create(
        route_create_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Update Route for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<route_res_v1> update(
        route_update_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Delete Route for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<route_res_v1> delete(
        route_delete_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Update Filters for an Org (auth delegate_keys/owner/admin)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<route_skf_update_res_v1> updateSkfs(
        route_skf_update_req_v1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateSkfsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LIST = 0;
  private static final int METHODID_GET = 1;
  private static final int METHODID_CREATE = 2;
  private static final int METHODID_UPDATE = 3;
  private static final int METHODID_DELETE = 4;
  private static final int METHODID_STREAM = 5;
  private static final int METHODID_GET_EUIS = 6;
  private static final int METHODID_GET_DEVADDR_RANGES = 7;
  private static final int METHODID_LIST_SKFS = 8;
  private static final int METHODID_GET_SKFS = 9;
  private static final int METHODID_UPDATE_SKFS = 10;
  private static final int METHODID_UPDATE_EUIS = 11;
  private static final int METHODID_UPDATE_DEVADDR_RANGES = 12;

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
          serviceImpl.list((route_list_req_v1) request,
              (io.grpc.stub.StreamObserver<route_list_res_v1>) responseObserver);
          break;
        case METHODID_GET:
          serviceImpl.get((route_get_req_v1) request,
              (io.grpc.stub.StreamObserver<route_res_v1>) responseObserver);
          break;
        case METHODID_CREATE:
          serviceImpl.create((route_create_req_v1) request,
              (io.grpc.stub.StreamObserver<route_res_v1>) responseObserver);
          break;
        case METHODID_UPDATE:
          serviceImpl.update((route_update_req_v1) request,
              (io.grpc.stub.StreamObserver<route_res_v1>) responseObserver);
          break;
        case METHODID_DELETE:
          serviceImpl.delete((route_delete_req_v1) request,
              (io.grpc.stub.StreamObserver<route_res_v1>) responseObserver);
          break;
        case METHODID_STREAM:
          serviceImpl.stream((route_stream_req_v1) request,
              (io.grpc.stub.StreamObserver<route_stream_res_v1>) responseObserver);
          break;
        case METHODID_GET_EUIS:
          serviceImpl.getEuis((route_get_euis_req_v1) request,
              (io.grpc.stub.StreamObserver<eui_pair_v1>) responseObserver);
          break;
        case METHODID_GET_DEVADDR_RANGES:
          serviceImpl.getDevaddrRanges((route_get_devaddr_ranges_req_v1) request,
              (io.grpc.stub.StreamObserver<devaddr_range_v1>) responseObserver);
          break;
        case METHODID_LIST_SKFS:
          serviceImpl.listSkfs((route_skf_list_req_v1) request,
              (io.grpc.stub.StreamObserver<skf_v1>) responseObserver);
          break;
        case METHODID_GET_SKFS:
          serviceImpl.getSkfs((route_skf_get_req_v1) request,
              (io.grpc.stub.StreamObserver<skf_v1>) responseObserver);
          break;
        case METHODID_UPDATE_SKFS:
          serviceImpl.updateSkfs((route_skf_update_req_v1) request,
              (io.grpc.stub.StreamObserver<route_skf_update_res_v1>) responseObserver);
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
        case METHODID_UPDATE_EUIS:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.updateEuis(
              (io.grpc.stub.StreamObserver<route_euis_res_v1>) responseObserver);
        case METHODID_UPDATE_DEVADDR_RANGES:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.updateDevaddrRanges(
              (io.grpc.stub.StreamObserver<route_devaddr_ranges_res_v1>) responseObserver);
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
      return IotConfig.getDescriptor();
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
              .addMethod(getStreamMethod())
              .addMethod(getGetEuisMethod())
              .addMethod(getUpdateEuisMethod())
              .addMethod(getGetDevaddrRangesMethod())
              .addMethod(getUpdateDevaddrRangesMethod())
              .addMethod(getListSkfsMethod())
              .addMethod(getGetSkfsMethod())
              .addMethod(getUpdateSkfsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
