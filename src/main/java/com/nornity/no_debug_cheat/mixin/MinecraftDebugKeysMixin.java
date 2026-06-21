package com.nornity.no_debug_cheat.mixin;

import com.nornity.no_debug_cheat.ClientDebugState;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Blocks all F3+X debug key combinations for players without the required
 * permission level. Covers hitboxes (F3+B), chunk borders (F3+G),
 * crash/copy-location (F3+C), copy block/entity data (F3+I), gamemode
 * swap (F3+N), and every other combination routed through this method.
 *
 * Target class:  net.minecraft.client.KeyboardHandler  (MC 26.x moved
 * debug key dispatch out of Minecraft into KeyboardHandler)
 * Target method: private boolean handleDebugKeys(KeyEvent)
 */
@Mixin(KeyboardHandler.class)
public class MinecraftDebugKeysMixin {

    @Inject(method = "handleDebugKeys", at = @At("HEAD"), cancellable = true)
    private void onHandleDebugKeys(KeyEvent event, CallbackInfoReturnable<Boolean> cir) {
        if (!ClientDebugState.isDebugAllowed()) {
            if (ClientDebugState.shouldShowBlockedMessage()) {
                Minecraft mc = Minecraft.getInstance();
                mc.gui.setOverlayMessage(
                        Component.translatable("nodebugcheat.debug.blocked"),
                        false
                );
            }
            cir.setReturnValue(false);
        }
    }
}
