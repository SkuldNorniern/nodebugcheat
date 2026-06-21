package com.nornity.no_debug_cheat;

import java.util.List;
import java.util.UUID;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue BLOCK_DEBUG_OVERLAY = BUILDER
            .comment("Block the F3 debug overlay for players below the required permission level")
            .define("blockDebugOverlay", true);

    public static final ModConfigSpec.BooleanValue BLOCK_HITBOXES = BUILDER
            .comment("Block F3+B hitbox visualization for players below the required permission level")
            .define("blockHitboxes", true);

    public static final ModConfigSpec.BooleanValue BLOCK_CHUNK_BORDERS = BUILDER
            .comment("Block F3+G chunk border rendering for players below the required permission level")
            .define("blockChunkBorders", true);

    public static final ModConfigSpec.IntValue OP_PERMISSION_LEVEL = BUILDER
            .comment("Minimum vanilla permission level required to use debug features (1–4; 2 = standard op)")
            .defineInRange("opPermissionLevel", 2, 1, 4);

    public static final ModConfigSpec.ConfigValue<List<? extends String>> AUTHORIZED_UUIDS = BUILDER
            .comment("Player UUIDs that may use debug features even if they are below the configured op permission level")
            .defineListAllowEmpty("authorizedUuids", List.of(), () -> "", Config::validateUuid);

    public static final ModConfigSpec.BooleanValue SHOW_COMPASS_COORDINATES = BUILDER
            .comment("Show player coordinates as a HUD overlay while holding a compass")
            .define("showCompassCoordinates", true);

    public static final ModConfigSpec.BooleanValue COMPASS_EXACT_COORDINATES = BUILDER
            .comment("Show exact decimal coordinates on the compass HUD instead of block coordinates")
            .define("compassExactCoordinates", false);

    static final ModConfigSpec SPEC = BUILDER.build();

    private static boolean validateUuid(Object value) {
        if (!(value instanceof String uuid)) {
            return false;
        }

        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException ignored) {
            return false;
        }
    }
}
