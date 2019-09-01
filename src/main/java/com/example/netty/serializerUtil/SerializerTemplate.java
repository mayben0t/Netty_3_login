package com.example.netty.serializerUtil;

public abstract class SerializerTemplate {
    protected int SerializerVersion;
    protected Object obj;

    public int getSerializerVersion(){
        return this.SerializerVersion;
    }

    protected abstract void setSerializerVersion();

    public abstract int getContentBytesLength();

    public abstract byte[] SerializeContent(Object obj);
}
