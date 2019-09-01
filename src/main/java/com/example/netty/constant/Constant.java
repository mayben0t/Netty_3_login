package com.example.netty.constant;

/**
 * 魔数   version  JsonVersion   command  contentBytesLenght         content
 *  4        1         1            1               4                  N
 */


public class Constant {
    public static final int MAGIC_NUM =  9527;
    public static final byte PROTOCOL_VERSION = 1;
    public static final byte COMMAND = 7;
}
