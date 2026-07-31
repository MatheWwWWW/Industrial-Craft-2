/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 */
package ic2.api.util;

import net.minecraft.entity.player.EntityPlayer;

public interface IKeyboard {
    public boolean isAltKeyDown(EntityPlayer var1);

    public boolean isBoostKeyDown(EntityPlayer var1);

    public boolean isForwardKeyDown(EntityPlayer var1);

    public boolean isJumpKeyDown(EntityPlayer var1);

    public boolean isModeSwitchKeyDown(EntityPlayer var1);

    public boolean isSideinventoryKeyDown(EntityPlayer var1);

    public boolean isHudModeKeyDown(EntityPlayer var1);

    public boolean isSneakKeyDown(EntityPlayer var1);
}

