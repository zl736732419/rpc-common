package com.zheng.rpc.common.params;

import java.io.Serializable;
import java.util.Optional;

/**
 * 客户端远程调用后响应结果
 * Created by zhenglian on 2017/10/16.
 */
public class RpcResponse implements Serializable {
    private String requestId;
    private Object result;
    private Throwable error;
    
    public boolean isError() {
        return !Optional.ofNullable(error).isPresent();
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
