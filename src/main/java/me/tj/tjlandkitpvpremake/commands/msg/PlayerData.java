package me.tj.tjlandkitpvpremake.commands.msg;

import java.util.UUID;

public class PlayerData {
    private UUID lastMessagedPlayer;

    public PlayerData() {
        this.lastMessagedPlayer = null;
    }

    public UUID getLastMessagedPlayer() {
        return lastMessagedPlayer;
    }

    public void setLastMessagedPlayer(UUID lastMessagedPlayer) {
        this.lastMessagedPlayer = lastMessagedPlayer;
    }
}
