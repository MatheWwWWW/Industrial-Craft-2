/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.platform.player;

import ic2.core.platform.player.KeyHelper;
import ic2.core.platform.player.PlayerHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;

public class Keyboard {
    public boolean isAltKeyDown(Player player) {
        return this.getHandler((Player)player).altKeyDown;
    }

    public boolean isBoostKeyDown(Player player) {
        return this.getHandler((Player)player).boostKeyDown;
    }

    public boolean isModeSwitchKeyDown(Player player) {
        return this.getHandler((Player)player).modeSwitchKeyDown;
    }

    public boolean isSideInventoryKeyDown(Player player) {
        return this.getHandler((Player)player).sideInventoryKeyDown;
    }

    public boolean isHudModeKeyDown(Player player) {
        return this.getHandler((Player)player).hudModeKeyDown;
    }

    public boolean isSwitchKeyDown(Player player) {
        return this.getHandler((Player)player).toggleKeyDown;
    }

    public boolean isSneakKeyDown(Player player) {
        return player.m_6144_();
    }

    public boolean isForwardKeyDown(Player player) {
        return this.getHandler((Player)player).forwardKeyDown;
    }

    public boolean isJumpKeyDown(Player player) {
        return this.getHandler((Player)player).jumpKeyDown;
    }

    public PlayerHandler getHandler(Player player) {
        return PlayerHandler.getHandler(player);
    }

    public MutableComponent getKeyName(KeyHelper helper) {
        switch (helper) {
            case BLOCK_CLICK: {
                return Component.m_237115_((String)"tooltip.ic2.block_click");
            }
            case BLOCK_LEFT_CLICK: {
                return Component.m_237115_((String)"tooltip.ic2.block_left_click");
            }
        }
        return Component.m_237113_((String)"I AM ERROR");
    }

    public int getKey(KeyHelper helper) {
        return -1;
    }

    public void sendKeyUpdate() {
    }

    public void processKeyUpdate(Player player, int keyState) {
        this.getHandler(player).onKeyChanged(keyState);
    }

    public void init() {
    }
}

