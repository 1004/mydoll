package com.fy.catchdoll.module.support.java_websocket.handshake;

public interface ClientHandshakeBuilder extends HandshakeBuilder, ClientHandshake {
	public void setResourceDescriptor(String resourceDescriptor);
}
