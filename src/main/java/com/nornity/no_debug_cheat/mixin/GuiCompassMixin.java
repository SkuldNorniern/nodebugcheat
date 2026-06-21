package com.nornity.no_debug_cheat.mixin;

import com.nornity.no_debug_cheat.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Shows compass coordinates via the overlay-message slot (same API as the
 * vanilla action bar) while the local player holds a compass. Runs each
 * game tick so the message stays visible as long as the compass is held.
 *
 * setOverlayMessage is the vanilla mechanism used for persistent center-screen
 * messages. Calling it every tick keeps the timer alive; releasing the compass
 * lets the existing timer run out naturally.
 */
@Mixin(Minecraft.class)
public class GuiCompassMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        if (!Config.SHOW_COMPASS_COORDINATES.getAsBoolean()) return;

        Minecraft mc = (Minecraft) (Object) this;
        LocalPlayer player = mc.player;
        if (player == null || mc.options.hideGui) return;

        boolean holdingCompass = player.getMainHandItem().is(Items.COMPASS)
                || player.getOffhandItem().is(Items.COMPASS);
        if (!holdingCompass) return;

        Component coords;
        if (Config.COMPASS_EXACT_COORDINATES.getAsBoolean()) {
            coords = Component.literal(String.format("X: %.1f  Y: %.1f  Z: %.1f",
                    player.getX(), player.getY(), player.getZ()));
        } else {
            BlockPos pos = player.blockPosition();
            coords = Component.literal(String.format("X: %d  Y: %d  Z: %d",
                    pos.getX(), pos.getY(), pos.getZ()));
        }

        mc.gui.setOverlayMessage(coords, false);
    }
}
