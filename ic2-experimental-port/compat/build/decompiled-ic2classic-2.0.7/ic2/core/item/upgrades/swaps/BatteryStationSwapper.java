/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.item.upgrades.swaps;

import ic2.core.block.base.tiles.impls.BaseBatteryStationTileEntity;
import ic2.core.item.upgrades.swaps.ISwapper;
import net.minecraft.world.level.block.entity.BlockEntity;

public class BatteryStationSwapper
implements ISwapper {
    public static final ISwapper INSTANCE = new BatteryStationSwapper();

    @Override
    public void transfer(BlockEntity from, BlockEntity to) {
        if (!(from instanceof BaseBatteryStationTileEntity) || !(to instanceof BaseBatteryStationTileEntity)) {
            return;
        }
        BaseBatteryStationTileEntity fromCharge = (BaseBatteryStationTileEntity)from;
        BaseBatteryStationTileEntity toCharge = (BaseBatteryStationTileEntity)to;
        toCharge.energy = fromCharge.energy;
        for (int i = 0; i < 17; ++i) {
            toCharge.setStackInSlot(i, fromCharge.getStackInSlot(i));
        }
    }
}

