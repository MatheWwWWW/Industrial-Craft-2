/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.inventory.custom;

import ic2.core.inventory.base.IHasCustomGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.custom.container.FriendsContainer;
import net.minecraft.world.entity.player.Player;

public class FriendsHandler
implements IHasCustomGui {
    @Override
    public boolean hasGui(Player player) {
        return true;
    }

    @Override
    public IC2Container createContainer(Player player, int windowID) {
        return new FriendsContainer(player, windowID);
    }
}

