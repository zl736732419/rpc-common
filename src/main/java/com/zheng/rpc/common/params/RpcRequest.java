package com.zheng.rpc.common.params;

import java.io.Serializable;

/**
 * 客户端调用远程服务端提供的服务所需要提供的方法调用参数信息
 * Created by zhenglian on 2017/10/16.
 */
public class RpcRequest implements Serializable {
    /**
     * 请求id
     */
    private String requestId;
    /**
     * 目标类简称，用于从rpcserver的map中获取注册的rpcservice
     */
    private String className;
    /**
     * 调用的目标类方法名称
     */
    private String methodName;
    /**
     * 调用方法的参数类型列表
     */
    private Class<?>[] parameterTypes;
    /**
     * 调用方法的参数列表
     */
    private Object[] parameters;

    /**
     * Gets request id.
     *
     * @return the request id
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Sets request id.
     *
     * @param requestId the request id
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    /**
     * Gets class name.
     *
     * @return the class name
     */
    public String getClassName() {
        return className;
    }

    /**
     * Sets class name.
     *
     * @param className the class name
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Gets method name.
     *
     * @return the method name
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Sets method name.
     *
     * @param methodName the method name
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * Get parameter types class [ ].
     *
     * @return the class [ ]
     */
    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    /**
     * Sets parameter types.
     *
     * @param parameterTypes the parameter types
     */
    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    /**
     * Get parameters object [ ].
     *
     * @return the object [ ]
     */
    public Object[] getParameters() {
        return parameters;
    }

    /**
     * Sets parameters.
     *
     * @param parameters the parameters
     */
    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
