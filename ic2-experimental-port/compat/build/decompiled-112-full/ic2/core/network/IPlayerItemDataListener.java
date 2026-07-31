/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 */
package ic2.core.network;

import net.minecraft.entity.player.EntityPlayer;

public interface IPlayerItemDataListener {
    public void onPlayerItemNetworkData(EntityPlayer var1, int var2, Object ... var3);
}

