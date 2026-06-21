package com.nornity.no_debug_cheat;

import com.nornity.no_debug_cheat.network.DebugAllowedPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

@Mod(NoDebugCheat.MODID)
public class NoDebugCheat {

    public static final String MODID = "nodebugcheat";
    public static final Logger LOGGER = LogUtils.getLogger();

    public NoDebugCheat(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::registerPayloads);
        NeoForge.EVENT_BUS.register(this);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(MODID);
        // Handler runs on the logical client only — ClientDebugState has no client-only
        // imports, so it is safe to reference from this common registration class.
        registrar.playToClient(
                DebugAllowedPayload.TYPE,
                DebugAllowedPayload.STREAM_CODEC,
                (payload, ctx) -> ctx.enqueueWork(() -> ClientDebugState.setDebugAllowed(payload.allowed()))
        );
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            sendPermission(player);
        }
    }

    /** Sends the current debug-allowed state to a specific client. */
    public static void sendPermission(ServerPlayer player) {
        boolean allowed = DebugPermissionHelper.isAllowed(player);
        PacketDistributor.sendToPlayer(player, new DebugAllowedPayload(allowed));
    }
}
