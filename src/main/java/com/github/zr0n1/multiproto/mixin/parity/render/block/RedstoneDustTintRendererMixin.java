package com.github.zr0n1.multiproto.mixin.parity.render.block;

import com.github.zr0n1.multiproto.Config;
import com.github.zr0n1.multiproto.Multiproto;
import com.github.zr0n1.multiproto.protocol.ProtocolVersion;
import com.github.zr0n1.multiproto.protocol.ProtocolVersionManager;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.block.BlockRenderManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockRenderManager.class)
public abstract class RedstoneDustTintRendererMixin {

    @Redirect(method = "renderRedstoneDust", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/render/Tessellator;color(FFF)V", ordinal = 0))
    private void applyTintParity(Tessellator t, float r, float g, float b, @Local(name = "var8") float luminance,
                                 @Local(name = "var6") int meta) {
        if (isBeforeWithAlphaPlace(ProtocolVersion.BETA_11) && meta == 0 && Config.config.textureParity)
            r = 0F;
        if (isBeforeWithAlphaPlace(ProtocolVersion.BETA_9) && Config.config.textureParity)
            r = g = b = luminance;
        t.color(r, g, b);
    }

    private static boolean isBeforeWithAlphaPlace(ProtocolVersion target) {
        return ProtocolVersionManager.isAlphaPlace() || ProtocolVersionManager.isBefore(target);
    }
}
