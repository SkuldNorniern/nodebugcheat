package com.nornity.no_debug_cheat.mixin;

import com.nornity.no_debug_cheat.ClientDebugState;
import com.nornity.no_debug_cheat.Config;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.overlay.DebugScreenOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Prevents the F3 debug overlay from rendering for players without the
 * required permission level. Also forces options.renderDebug back to false
 * so that isShowingDebugScreen() returns the correct value to other code.
 *
 * Target class: net.minecraft.client.gui.overlay.DebugScreenOverlay
 * If the class moved in your Minecraft version, update the @Mixin target
 * and the mixins.json entry accordingly.
 */
@Mixin(DebugScreenOverlay.class)
public class DebugScreenOverlayMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRender(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (Config.BLOCK_DEBUG_OVERLAY.getAsBoolean() && !ClientDebugState.isDebugAllowed()) {
            // Clear the flag so the game does not treat the overlay as active
            Minecraft.getInstance().options.renderDebug = false;
            ci.cancel();
        }
    }
}
