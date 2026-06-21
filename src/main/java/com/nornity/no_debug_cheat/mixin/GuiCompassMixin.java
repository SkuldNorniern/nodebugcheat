package com.nornity.no_debug_cheat.mixin;

import com.nornity.no_debug_cheat.Config;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Draws compass-coordinate text at the top-center of the screen whenever
 * the local player holds a compass in main hand or offhand.
 */
@Mixin(Gui.class)
public class GuiCompassMixin {

    @Inject(method = "extractRenderState", at = @At("RETURN"))
    private void onExtractRenderState(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (!Config.SHOW_COMPASS_COORDINATES.getAsBoolean()) return;

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
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

        graphics.centeredText(mc.font, coords, graphics.guiWidth() / 2, 4, 0xFFFFFF);
    }
}
