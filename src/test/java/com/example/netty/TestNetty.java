package com.example.netty;


import io.netty.buffer.ByteBuf;
import nettyp.byteBuf.Encode;
import nettyp.deSeializer.DecodeWork;
import nettyp.serializer.JsonSerializer;
import nettyp.serializer.RequestMsg;
import org.junit.Assert;
import org.junit.Test;

public class TestNetty {

    @Test
    public void test(){
        RequestMsg req = new RequestMsg(1, "王骁", 24);

        ByteBuf byteBuf = Encode.getByteBuf(req);
        RequestMsg requestMsg = DecodeWork.decodeByteBuf(byteBuf);

        Assert.assertEquals(req,requestMsg);
        System.out.println(requestMsg);

    }
}
