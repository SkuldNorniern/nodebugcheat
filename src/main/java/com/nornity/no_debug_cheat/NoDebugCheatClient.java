package com.nornity.no_debug_cheat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = NoDebugCheat.MODID, dist = Dist.CLIENT)
public class NoDebugCheatClient {

    public NoDebugCheatClient(IEventBus modEventBus, ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        NeoForge.EVENT_BUS.addListener(NoDebugCheatClient::onRenderGui);
    }

    private static void onRenderGui(RenderGuiEvent.Post event) {
        if (!Config.SHOW_COMPASS_COORDINATES.getAsBoolean()) return;

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null || mc.options.hideGui) return;

        boolean holdingCompass = player.getMainHandItem().is(Items.COMPASS)
                || player.getOffhandItem().is(Items.COMPASS);
        if (!holdingCompass) return;

        String coords;
        if (Config.COMPASS_EXACT_COORDINATES.getAsBoolean()) {
            coords = String.format("X: %.1f  Y: %.1f  Z: %.1f",
                    player.getX(), player.getY(), player.getZ());
        } else {
            BlockPos pos = player.blockPosition();
            coords = String.format("X: %d  Y: %d  Z: %d", pos.getX(), pos.getY(), pos.getZ());
        }

        GuiGraphics gui = event.getGuiGraphics();
        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int textWidth = mc.font.width(coords);
        gui.drawString(mc.font, coords, (screenWidth - textWidth) / 2, 4, 0xFFFFFF);
    }
}
