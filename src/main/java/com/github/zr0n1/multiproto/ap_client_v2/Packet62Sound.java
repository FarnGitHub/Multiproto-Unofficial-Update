package com.github.zr0n1.multiproto.ap_client_v2;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet62Sound extends Packet {
	public String sound;
	public double locX;
	public double locY;
	public double locZ;
	public float f;
	public float f1;

	public void read(DataInputStream var1) {
		try {
			this.sound = var1.readUTF();
			this.locX = var1.readDouble();
			this.locY = var1.readDouble();
			this.locZ = var1.readDouble();
			this.f = var1.readFloat();
			this.f1 = var1.readFloat();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void write(DataOutputStream var1) {
	}

	public void apply(NetworkHandler var1) {
		((Minecraft)FabricLoader.getInstance().getGameInstance()).worldRenderer.playSound(sound, locX, locY, locZ, f, f1);
	}

	public int size() {
		return this.sound.length() + 24 + 8;
	}
}
