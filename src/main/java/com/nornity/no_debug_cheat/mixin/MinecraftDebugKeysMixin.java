package com.nornity.no_debug_cheat.mixin;

import com.nornity.no_debug_cheat.ClientDebugState;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Blocks all F3+X debug key combinations for players without the required
 * permission level. This covers F3+B (hitboxes), F3+G (chunk borders),
 * F3+C (crash / copy-location), F3+I (copy targeted block/entity data),
 * F3+N (gamemode swap), and every other combination handled by this method.
 *
 * Target method: net.minecraft.client.Minecraft#handleDebugKeys(int)
 * The method name uses Mojang's official mappings as provided by NeoForge.
 * If your version uses a different mapped name, search for the private method
 * in Minecraft.java that switches on GLFW key codes while F3 is held.
 */
@Mixin(Minecraft.class)
public class MinecraftDebugKeysMixin {

    @Inject(method = "handleDebugKeys", at = @At("HEAD"), cancellable = true)
    private void onHandleDebugKeys(int key, CallbackInfoReturnable<Boolean> cir) {
        if (!ClientDebugState.isDebugAllowed()) {
            if (ClientDebugState.shouldShowBlockedMessage()) {
                Minecraft mc = (Minecraft) (Object) this;
                if (mc.player != null) {
                    mc.player.displayClientMessage(
                            Component.translatable("nodebugcheat.debug.blocked"),
                            true  // action bar, not chat
                    );
                }
            }
            cir.setReturnValue(false);
        }
    }
}
