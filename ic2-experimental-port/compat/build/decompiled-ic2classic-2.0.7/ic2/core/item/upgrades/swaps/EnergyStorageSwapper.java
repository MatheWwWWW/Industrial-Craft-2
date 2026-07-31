/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.item.upgrades.swaps;

import ic2.core.block.base.tiles.impls.BaseEnergyStorageTileEntity;
import ic2.core.item.upgrades.swaps.ISwapper;
import net.minecraft.world.level.block.entity.BlockEntity;

public class EnergyStorageSwapper
implements ISwapper {
    public static final ISwapper INSTANCE = new EnergyStorageSwapper();

    @Override
    public void transfer(BlockEntity from, BlockEntity to) {
        if (!(from instanceof BaseEnergyStorageTileEntity) || !(to instanceof BaseEnergyStorageTileEntity)) {
            return;
        }
        BaseEnergyStorageTileEntity fromStorage = (BaseEnergyStorageTileEntity)from;
        BaseEnergyStorageTileEntity toStorage = (BaseEnergyStorageTileEntity)to;
        toStorage.energy = fromStorage.energy;
        toStorage.setFacing(fromStorage.getFacing());
        toStorage.redstoneMode = fromStorage.redstoneMode;
        int m = Math.min(toStorage.inventorySize, fromStorage.inventorySize);
        for (int i = 0; i < m; ++i) {
            toStorage.setStackInSlot(i, fromStorage.getStackInSlot(i));
        }
    }
}

