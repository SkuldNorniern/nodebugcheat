package com.nornity.no_debug_cheat;

import net.minecraft.server.permissions.Permission;
import net.minecraft.server.permissions.PermissionLevel;
import net.minecraft.world.entity.player.Player;

public class DebugPermissionHelper {

    /**
     * Returns true when the player holds at least the configured permission level.
     * PermissionLevel values: 1=MODERATORS, 2=GAMEMASTERS (standard op), 3=ADMINS, 4=OWNERS.
     */
    public static boolean isAllowed(Player player) {
        PermissionLevel required = PermissionLevel.byId(Config.OP_PERMISSION_LEVEL.getAsInt());
        return player.permissions().hasPermission(new Permission.HasCommandLevel(required));
    }
}
