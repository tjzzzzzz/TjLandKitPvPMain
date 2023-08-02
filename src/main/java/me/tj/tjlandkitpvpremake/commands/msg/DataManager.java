package me.tj.tjlandkitpvpremake.commands.msg;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataManager {
    private Map<UUID, PlayerData> playerDataMap;

    public DataManager() {
        this.playerDataMap = new HashMap<>();
    }

    public PlayerData getPlayerData(UUID playerId) {
        return playerDataMap.computeIfAbsent(playerId, k -> new PlayerData());
    }
}