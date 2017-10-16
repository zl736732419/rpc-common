package com.zheng.rpc.common.encrypt;

import com.zheng.rpc.common.utils.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 响应输出编码 out
 * Created by zhenglian on 2017/10/16.
 */
public class RpcEncoder extends MessageToByteEncoder {
    private Class<?> targetClazz;
    
    public RpcEncoder(Class<?> targetClazz) {
        this.targetClazz = targetClazz;
    }
    
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        if (!(targetClazz.isInstance(msg))) {
            return;
        }
        
        byte[] bytes = SerializationUtil.serialize(msg);
        out.writeInt(bytes.length); // 先写入字节长度
        out.writeBytes(bytes); // 再写入字节内容
    }
}
