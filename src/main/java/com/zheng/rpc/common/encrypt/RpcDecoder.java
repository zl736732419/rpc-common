package com.zheng.rpc.common.encrypt;

import com.zheng.rpc.common.utils.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 消息解码，handler type (IN)
 * Created by zhenglian on 2017/10/16.
 */
public class RpcDecoder extends ByteToMessageDecoder {
    /**
     * 需要将消息解码的目标对象类型
     */
    private Class<?> targetClazz;
    
    public RpcDecoder(Class<?> targetClazz) {
        this.targetClazz = targetClazz;
    }
    
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 4) { // 通信协议限制
            return;
        }
        byteBuf.markReaderIndex();
        int length = byteBuf.readInt();
        if (length < 0) {
            ctx.close();
        }
        if (byteBuf.readableBytes() < length) {
            byteBuf.resetReaderIndex();
        }
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes); // 从byteBuf中读取byte[]
        Object object = SerializationUtil.deserialize(bytes, targetClazz);
        list.add(object);
    }
}
