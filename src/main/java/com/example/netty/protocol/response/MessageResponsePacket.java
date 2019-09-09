package com.example.netty.protocol.response;

import com.example.netty.protocol.Packet;
import lombok.Data;

import static com.example.netty.protocol.command.Command.MESSAGE_RESPONSE;

@Data
public class MessageResponsePacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }
}
