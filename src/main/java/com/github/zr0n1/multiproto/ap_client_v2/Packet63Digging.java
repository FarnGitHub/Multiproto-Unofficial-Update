package com.github.zr0n1.multiproto.ap_client_v2;

import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Packet63Digging extends Packet {
	public int x;
	public int y;
	public int z;
	public int face;
	public float progress;
	public long timestamp;

	public void read(DataInputStream var1) {
		try {
			this.x = var1.readInt();
			this.y = var1.readInt();
			this.z = var1.readInt();
			this.face = var1.readByte();
			this.progress = var1.readFloat();
			this.timestamp = System.currentTimeMillis();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void write(DataOutputStream var1) {
	}

	public void apply(NetworkHandler var1) {
	}

	public int size() {
		return 17;
	}
}
