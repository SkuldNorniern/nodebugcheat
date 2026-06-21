package com.nornity.no_debug_cheat;

import java.util.UUID;

import net.minecraft.server.permissions.Permission;
import net.minecraft.server.permissions.PermissionLevel;
import net.minecraft.world.entity.player.Player;

public class DebugPermissionHelper {

    /**
     * Returns true when the player holds at least the configured permission level.
     * PermissionLevel values: 1=MODERATORS, 2=GAMEMASTERS (standard op), 3=ADMINS, 4=OWNERS.
     */
    public static boolean isAllowed(Player player) {
        if (isAuthorizedByConfig(player)) {
            return true;
        }

        PermissionLevel required = PermissionLevel.byId(Config.OP_PERMISSION_LEVEL.getAsInt());
        return player.permissions().hasPermission(new Permission.HasCommandLevel(required));
    }

    private static boolean isAuthorizedByConfig(Player player) {
        UUID playerUuid = player.getUUID();
        return Config.AUTHORIZED_UUIDS.get().stream()
                .map(UUID::fromString)
                .anyMatch(playerUuid::equals);
    }
}
