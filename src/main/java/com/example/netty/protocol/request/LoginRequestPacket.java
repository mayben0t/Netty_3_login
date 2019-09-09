package com.example.netty.protocol.request;

import com.example.netty.protocol.Packet;
import lombok.Data;

import static com.example.netty.protocol.command.Command.LOGIN_REQUEST;

@Data
public class LoginRequestPacket extends Packet {
    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
