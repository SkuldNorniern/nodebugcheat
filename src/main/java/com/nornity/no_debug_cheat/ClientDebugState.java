package com.nornity.no_debug_cheat;

/**
 * Holds the client's current debug-access permission as synced from the server.
 * Default is DENIED so the client cannot exploit debug features before the
 * server has confirmed the player's op level.
 *
 * This class intentionally has no client-only imports so it is safe to load
 * on a dedicated server JVM (needed because the payload handler lambda
 * references it from common registration code).
 */
public class ClientDebugState {

    private static volatile boolean debugAllowed = false;

    private static long lastBlockedMessageTime = 0L;
    private static final long MESSAGE_COOLDOWN_MS = 3_000L;

    public static boolean isDebugAllowed() {
        return debugAllowed;
    }

    public static void setDebugAllowed(boolean allowed) {
        debugAllowed = allowed;
    }

    /** Returns true at most once every 3 seconds, to avoid chat spam. */
    public static boolean shouldShowBlockedMessage() {
        long now = System.currentTimeMillis();
        if (now - lastBlockedMessageTime > MESSAGE_COOLDOWN_MS) {
            lastBlockedMessageTime = now;
            return true;
        }
        return false;
    }
}
