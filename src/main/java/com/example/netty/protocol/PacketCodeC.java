package com.example.netty.protocol;

import com.example.netty.protocol.request.LoginRequestPacket;
import com.example.netty.protocol.request.MessageRequestPacket;
import com.example.netty.protocol.response.LoginResponsePacket;
import com.example.netty.protocol.response.MessageResponsePacket;
import com.example.netty.serialize.Serializer;
import com.example.netty.serialize.impl.JSONSerializer;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import javax.lang.model.util.AbstractTypeVisitor6;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.netty.protocol.command.Command.*;

public class PacketCodeC {
    static class Inner{
        public static PacketCodeC getInstance(){
            return new PacketCodeC();
        }
    }

    private static final int MAGIC_NUMBER = 0x12345678;
    public static final PacketCodeC INSTANCE = PacketCodeC.Inner.getInstance();

    private final Map<Byte,Class<? extends Packet>> packetTypeMap;
    private final Map<Byte, Serializer> serializaerMap;


    private PacketCodeC(){
        packetTypeMap = Maps.newHashMap();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(MESSAGE_RESPONSE, MessageResponsePacket.class);

        serializaerMap = Maps.newHashMap();
        Serializer serializer = new JSONSerializer();
        serializaerMap.put(serializer.getSerializerAlogrithm(),serializer);
    }

    public ByteBuf encode(ByteBufAllocator byteBufAllocator,Packet packet){
        //1. 创建ByteBuf对象
        ByteBuf byteBuf = byteBufAllocator.ioBuffer();
        //2.序列化java对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        //3.实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlogrithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf){
        byteBuf.skipBytes(4); //skip magic_num
        byteBuf.skipBytes(1); //skip packet_version
        byte serializerAlgorithm = byteBuf.readByte();//skip serializer_version
        byte command = byteBuf.readByte();//skip packet command
        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializerAlgorithm);

        if(requestType!=null && serializer!=null){
            return serializer.deserialize(requestType,bytes);
        }

        return null;
    }
    
    private Serializer getSerializer(byte serializeAlgorithm){
        return serializaerMap.get(serializeAlgorithm);
    }
    
    private Class<? extends Packet> getRequestType(byte command){
        return packetTypeMap.get(command);
    }
    

}
