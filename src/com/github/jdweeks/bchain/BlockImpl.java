package com.github.jdweeks.bchain;

import java.util.Date;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BlockImpl implements Block {

	public byte[] hash;
	public byte[] prevHash;
	private byte[] data;
	private long   timeStamp;
	
	public BlockImpl(byte[] prevHash, byte[] data) {
		this.data = data;
		this.prevHash = prevHash;
		this.timeStamp = new Date().getTime(); // use Unix time
		this.hash = calcHash(this.timeStamp, this.prevHash, this.data);
	}
	
	public byte[] calcHash(long timeStamp, byte[] prevHash, byte[] data) {
		MessageDigest md;
		ByteBuffer bb;
		
		byte[] hash = null;
		try {
			// use SHA-2
			md = MessageDigest.getInstance("SHA-256");
			bb = ByteBuffer.allocate((prevHash.length + data.length)*Byte.SIZE + (Long.SIZE / Byte.SIZE));
			
			// include all parts of the block
			bb.put(prevHash);
			bb.put(data, prevHash.length, data.length);
			bb.putLong(prevHash.length + data.length, timeStamp);
			
			hash = md.digest(bb.array());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return hash;
	}
}
