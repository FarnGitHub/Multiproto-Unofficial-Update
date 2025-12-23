package com.github.zr0n1.multiproto;
import com.github.zr0n1.multiproto.parity.optional.TranslationParityHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.glasslauncher.mods.gcapi3.api.ConfigEntry;
import net.glasslauncher.mods.gcapi3.api.ConfigRoot;
import net.glasslauncher.mods.gcapi3.api.PreConfigSavedListener;
import net.glasslauncher.mods.gcapi3.impl.EventStorage;
import net.glasslauncher.mods.gcapi3.impl.GlassYamlFile;
import net.minecraft.client.Minecraft;

public class Config implements PreConfigSavedListener {

    @ConfigRoot(value = "multiproto_config", visibleName = "Multiproto Config")
    public static final Instance config = new Instance();

    public static class Instance {
        @ConfigEntry(name = "Version name parity", description = "Shows version name on HUD < Beta 1.6")
        public Boolean showVersion = true;

        @ConfigEntry(name="\u200BTexture parity", description = "Changes textures to match version")
        public Boolean textureParity = true;

        @ConfigEntry(name="\u200B\u200BLighting parity", description = "Toggles smooth lighting to match version")
        public Boolean lightingParity = true;

        @ConfigEntry(name="\u200B\u200B\u200BName rendering parity", description = "Renders player names larger < Beta 1.3")
        public Boolean nameRenderParity = true;

        @ConfigEntry(name="\u200B\u200B\u200B\u200BTooltip name parity", description = "Changes tooltip names to match version")
        public Boolean translationParity = true;

        @ConfigEntry(name = "\u200B\u200B\u200B\u200B\u200BCustom version name", description = "Shows custom version name on HUD")
        public String customVersionName = "";

        @ConfigEntry(name = "Protocal Version Info's Y offset", description = "Y offset of the protocal info for debug screen", maxValue = 4096)
        public Integer debugYoffset = 100;
    }

    @Override
    public void onPreConfigSaved(int source, GlassYamlFile oldValues, GlassYamlFile newValues) {
        boolean textureParityA = oldValues.getBoolean("textureParity", true);
        boolean textureParityB = newValues.getBoolean("textureParity", false);
        boolean lightingParityA = oldValues.getBoolean("lightingParity", true);
        boolean lightingParityB = newValues.getBoolean("lightingParity", false);
        boolean translationParityA = oldValues.getBoolean("translationParity", true);
        boolean translationParityB = newValues.getBoolean("translationParity", false);
        if (source == EventStorage.EventSource.USER_SAVE) {
            Minecraft mc = (Minecraft) FabricLoader.getInstance().getGameInstance();
            if (textureParityA != textureParityB) {
                config.textureParity = textureParityB;
                mc.textureManager.reload();
            }
            if (lightingParityA != lightingParityB && mc.isWorldRemote()) {
                config.lightingParity = lightingParityB;
                mc.worldRenderer.reload();
            }
            if (translationParityA != translationParityB) {
                config.translationParity = translationParityB;
                TranslationParityHelper.applyParity();
            }
        }
    }
}
