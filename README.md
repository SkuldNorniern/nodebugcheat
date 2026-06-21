# NoDebugCheat

NoDebugCheat is a NeoForge mod that blocks F3 debug access for players without permission.

Only opped or authorized users can use F3 and F3 key combinations, including hitboxes, chunk borders, copied location data, copied block/entity data, and gamemode swap shortcuts.

Regular players can still view coordinates by holding a compass, keeping navigation fair without using the debug screen as a cheat.

## Config

- `opPermissionLevel`: minimum vanilla permission level required for debug access.
- `authorizedUuids`: extra player UUIDs allowed to use debug tools.
- `showCompassCoordinates`: show coordinates while holding a compass.
- `compassExactCoordinates`: show decimal coordinates instead of block coordinates.

Generated config example:

```toml
blockDebugOverlay = true
blockHitboxes = true
blockChunkBorders = true

opPermissionLevel = 2
authorizedUuids = ["00000000-0000-0000-0000-000000000000"]

showCompassCoordinates = true
compassExactCoordinates = false
```
