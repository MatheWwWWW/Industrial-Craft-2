/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.crops;

import ic2.api.crops.ICropTile;
import net.minecraft.world.item.ItemStack;

public interface ISeedCrop {
    public boolean isDroppingSeeds(ICropTile var1);

    public ItemStack[] getSeedDrops(ICropTile var1);
}

