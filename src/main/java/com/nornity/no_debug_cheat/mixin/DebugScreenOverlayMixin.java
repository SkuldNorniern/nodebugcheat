package com.nornity.no_debug_cheat.mixin;

import com.nornity.no_debug_cheat.ClientDebugState;
import com.nornity.no_debug_cheat.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.DebugScreenOverlay;
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

    @Inject(method = "extractRenderState", at = @At("HEAD"), cancellable = true)
    private void onExtractRenderState(GuiGraphicsExtractor graphics, CallbackInfo ci) {
        if (Config.BLOCK_DEBUG_OVERLAY.getAsBoolean() && !ClientDebugState.isDebugAllowed()) {
            // Reset the visible flag so isShowingDebugScreen() returns false
            Minecraft.getInstance().debugEntries.setOverlayVisible(false);
            ci.cancel();
        }
    }
}
