/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraftforge.common.capabilities.Capability
 *  net.minecraftforge.common.util.LazyOptional
 */
package ic2.core.block.base.features.personal;

import ic2.api.util.ILocation;
import ic2.core.platform.player.friends.Action;
import java.util.UUID;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public interface IPersonalTile
extends ILocation {
    public void setOwner(UUID var1);

    public UUID getOwner();

    public boolean canDoAction(UUID var1, Action var2, boolean var3);

    default public <T> LazyOptional<T> getPersonalCapability(UUID requester, Capability<T> cap, Direction dir) {
        return LazyOptional.empty();
    }
}

