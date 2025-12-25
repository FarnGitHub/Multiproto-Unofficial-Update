package com.github.zr0n1.multiproto.mixin.network.packet;

import com.github.zr0n1.multiproto.ap_client_v2.APServerSoundPacket;
import com.github.zr0n1.multiproto.ap_client_v2.APServerDiggingEffectPacket;
import com.github.zr0n1.multiproto.protocol.ProtocolVersion;
import com.github.zr0n1.multiproto.protocol.ProtocolVersionManager;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Mixin(Packet.class)
public abstract class PacketMixin {

    @Shadow static void register(int rawId, boolean clientBound, boolean serverBound, Class type) {
        throw new AssertionError();
    }

    @Inject(method = "readString", at = @At("HEAD"), cancellable = true)
    private static void readUTFIfOldVersion(DataInputStream stream, int maxLength, CallbackInfoReturnable<String> cir)
            throws IOException {
        if (ProtocolVersionManager.isBefore(ProtocolVersion.BETA_11)) {
            String s = stream.readUTF();
            if (s.length() > maxLength) {
                throw new IOException("Received string length longer than maximum allowed (" + s.length() + " > " + maxLength + ")");
            }
            cir.setReturnValue(s);
        }
    }

    @Inject(method = "writeString", at = @At(value = "INVOKE", target = "Ljava/io/DataOutputStream;writeShort(I)V"), cancellable = true)
    private static void writeUTFIfOldVersion(String string, DataOutputStream stream, CallbackInfo ci) throws IOException {
        if (ProtocolVersionManager.isBefore(ProtocolVersion.BETA_11)) {
            stream.writeUTF(string);
            ci.cancel();
        }
    }

    @Inject(method="<clinit>", at = @At("TAIL"))
    private static void addAlphaplaceClientPacker(CallbackInfo ci) {
        register(62, true, false, APServerSoundPacket.class);
        register(63, true, false, APServerDiggingEffectPacket.class);
    }
}
