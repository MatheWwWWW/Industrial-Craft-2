/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.tiles.tubes;

import ic2.api.tiles.tubes.ITube;
import java.util.UUID;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

public interface IRequestTube
extends ITube {
    public void provideRequests(ITubeRequester var1);

    public void onRequestsReset();

    public void onRequestFulfilled(ItemStack var1, int var2);

    public UUID getRequestId();

    public void onRequestLost(ItemStack var1, int var2);

    public long getRequestSource();

    public static interface ITubeRequester {
        public void requestItems(ItemStack var1, int var2, DyeColor var3, UUID var4);

        public int validateRequest(ItemStack var1, int var2, DyeColor var3);
    }
}

