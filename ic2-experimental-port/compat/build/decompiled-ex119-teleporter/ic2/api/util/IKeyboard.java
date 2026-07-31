/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Player
 */
package ic2.api.util;

import net.minecraft.world.entity.player.Player;

public interface IKeyboard {
    public boolean isAltKeyDown(Player var1);

    public boolean isBoostKeyDown(Player var1);

    public boolean isForwardKeyDown(Player var1);

    public boolean isJumpKeyDown(Player var1);

    public boolean isModeSwitchKeyDown(Player var1);

    public boolean isSideinventoryKeyDown(Player var1);

    public boolean isHudModeKeyDown(Player var1);

    public boolean isSneakKeyDown(Player var1);
}

