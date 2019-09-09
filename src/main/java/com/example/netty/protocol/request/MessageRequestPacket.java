package com.example.netty.protocol.request;

import com.example.netty.protocol.Packet;
import lombok.Data;

import static com.example.netty.protocol.command.Command.MESSAGE_REQUEST;

@Data
public class MessageRequestPacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
