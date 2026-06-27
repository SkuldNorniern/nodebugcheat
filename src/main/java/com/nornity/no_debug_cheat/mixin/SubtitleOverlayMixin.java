package com.nornity.no_debug_cheat.mixin;

import com.nornity.no_debug_cheat.ClientDebugState;
import com.nornity.no_debug_cheat.Config;
import java.util.List;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.SubtitleOverlay;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.WeighedSoundEvents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Prevents subtitles from acting as directional sound radar for players who
 * are not allowed to use debug-style information.
 */
@Mixin(SubtitleOverlay.class)
public class SubtitleOverlayMixin {

    @Shadow
    @Final
    private List<?> subtitles;

    @Shadow
    @Final
    private List<?> audibleSubtitles;

    @Inject(method = "extractRenderState", at = @At("HEAD"), cancellable = true)
    private void onExtractRenderState(GuiGraphicsExtractor graphics, CallbackInfo ci) {
        if (shouldBlockSoundCaptions()) {
            this.subtitles.clear();
            this.audibleSubtitles.clear();
            ci.cancel();
        }
    }

    @Inject(method = "onPlaySound", at = @At("HEAD"), cancellable = true)
    private void onPlaySound(SoundInstance sound, WeighedSoundEvents soundEvent, float range, CallbackInfo ci) {
        if (shouldBlockSoundCaptions()) {
            ci.cancel();
        }
    }

    private static boolean shouldBlockSoundCaptions() {
        return Config.BLOCK_SOUND_CAPTIONS.getAsBoolean() && !ClientDebugState.isDebugAllowed();
    }
}
