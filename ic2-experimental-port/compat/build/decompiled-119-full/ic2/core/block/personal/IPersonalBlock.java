/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.world.Container
 */
package ic2.core.block.personal;

import com.mojang.authlib.GameProfile;
import net.minecraft.world.Container;

public interface IPersonalBlock {
    public boolean permitsAccess(GameProfile var1);

    public Container getPrivilegedInventory(GameProfile var1);

    public GameProfile getOwner();

    public void setOwner(GameProfile var1);
}

