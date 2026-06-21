package com.nornity.no_debug_cheat;

import net.minecraft.world.entity.player.Player;

public class DebugPermissionHelper {

    /**
     * Returns true when the player holds at least the configured op permission level.
     * Works for both dedicated-server operators and singleplayer owners with cheats.
     */
    public static boolean isAllowed(Player player) {
        return player.hasPermissions(Config.OP_PERMISSION_LEVEL.getAsInt());
    }
}
