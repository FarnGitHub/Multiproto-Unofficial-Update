package com.github.zr0n1.multiproto.parity;

import com.github.zr0n1.multiproto.Multiproto;
import com.github.zr0n1.multiproto.mixin.parity.block.BlockAccessor;
import com.github.zr0n1.multiproto.protocol.ProtocolVersion;
import com.github.zr0n1.multiproto.protocol.ProtocolVersionManager;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockParityHelper {

    public static void applyParity() {
        // set cobweb fields
        ((BlockAccessor) Block.COBWEB).setMaterial(isBeforeWithAlphaPlace(ProtocolVersion.BETA_14) ? Material.WOOL : Material.COBWEB);
        Block.COBWEB.setHardness(isBeforeWithAlphaPlace(ProtocolVersion.BETA_14) ? 0F : 4F);
        Block.COBWEB.setOpacity(isBeforeWithAlphaPlace(ProtocolVersion.BETA_14) ? 0 : 1);
        Block.BLOCKS_OPAQUE[Block.COBWEB.id] = isBeforeWithAlphaPlace(ProtocolVersion.BETA_14);
        // set glowstone fields
        ((BlockAccessor) Block.GLOWSTONE).setMaterial(isBeforeWithAlphaPlace(ProtocolVersion.BETA_13) ? Material.GLASS : Material.STONE);
        Block.GLOWSTONE.setHardness(isBeforeWithAlphaPlace(ProtocolVersion.BETA_14) ? 0.1F : 0.3F);
        Multiproto.LOGGER.info("Applied version block parity");
    }

    private static boolean isBeforeWithAlphaPlace(ProtocolVersion target) {
        return ProtocolVersionManager.isAlphaPlace() || ProtocolVersionManager.isBefore(target);
    }
}
