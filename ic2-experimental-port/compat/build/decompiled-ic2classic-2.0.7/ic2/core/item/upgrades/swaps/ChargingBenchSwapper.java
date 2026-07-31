/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.item.upgrades.swaps;

import ic2.core.block.base.tiles.impls.BaseChargingBenchTileEntity;
import ic2.core.item.upgrades.swaps.ISwapper;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ChargingBenchSwapper
implements ISwapper {
    public static final ISwapper INSTANCE = new ChargingBenchSwapper();

    @Override
    public void transfer(BlockEntity from, BlockEntity to) {
        if (!(from instanceof BaseChargingBenchTileEntity) || !(to instanceof BaseChargingBenchTileEntity)) {
            return;
        }
        BaseChargingBenchTileEntity fromCharge = (BaseChargingBenchTileEntity)from;
        BaseChargingBenchTileEntity toCharge = (BaseChargingBenchTileEntity)to;
        toCharge.sortedMode = fromCharge.sortedMode;
        toCharge.energy = fromCharge.energy;
        for (int i = 0; i < 17; ++i) {
            toCharge.setStackInSlot(i, fromCharge.getStackInSlot(i));
        }
    }
}

