package com.nornity.no_debug_cheat.mixin;

import com.nornity.no_debug_cheat.ClientDebugState;
import com.nornity.no_debug_cheat.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Prevents the F3 debug overlay from rendering for players without the
 * required permission level.
 *
 * Target class: net.minecraft.client.gui.components.DebugScreenOverlay
 * Target method: extractRenderState(GuiGraphicsExtractor) — the MC 26.x
 * equivalent of the old render() call, invoked each frame by Gui.
 */
@Mixin(DebugScreenOverlay.class)
public class DebugScreenOverlayMixin {

    @Inject(
        method = "extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;)V",
        at = @At("HEAD"), cancellable = true
    )
    private void onExtractRenderState(GuiGraphicsExtractor graphics, CallbackInfo ci) {
        if (Config.BLOCK_DEBUG_OVERLAY.getAsBoolean() && !ClientDebugState.isDebugAllowed()) {
            Minecraft mc = Minecraft.getInstance();
            // Reset the visible flag so isShowingDebugScreen() returns false
            mc.debugEntries.setOverlayVisible(false);
            if (ClientDebugState.shouldShowBlockedMessage()) {
                mc.gui.setOverlayMessage(
                        Component.translatable("nodebugcheat.debug.blocked"),
                        false
                );
            }
            ci.cancel();
        }
    }
}
