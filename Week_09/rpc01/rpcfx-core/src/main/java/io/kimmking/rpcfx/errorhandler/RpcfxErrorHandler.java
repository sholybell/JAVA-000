package io.kimmking.rpcfx.errorhandler;

@FunctionalInterface
public interface RpcfxErrorHandler {
    Object handleError(Throwable throwable);
}
