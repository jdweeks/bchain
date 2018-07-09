package com.github.jdweeks.bchain;

public interface Block {
	
	public byte[] calcHash(long timeStamp, byte[] prevHash, byte[] data);
}
