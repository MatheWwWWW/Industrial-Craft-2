/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.generator.tileentity;

import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
import ic2.core.block.invslot.InvSlotConsumable;
import ic2.core.block.invslot.InvSlotConsumableItemStack;
import ic2.core.init.MainConfig;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2Items;
import ic2.core.util.ConfigUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;

@NotClassic
public class TileEntityRTGenerator
extends TileEntityBaseGenerator {
    public final InvSlotConsumable fuelSlot = new InvSlotConsumableItemStack(this, "fuel", 6, new ItemStack((ItemLike)Ic2Items.RTG_PELLET));
    private static final float efficiency = ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/radioisotope");

    public TileEntityRTGenerator(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.RT_GENERATOR, blockPos, blockState, Math.round(16.0f * efficiency), 1, 20000);
        this.fuelSlot.setStackSizeLimit(1);
    }

    @Override
    public boolean gainEnergy() {
        int n = 0;
        for (int i = 0; i < this.fuelSlot.size(); ++i) {
            if (this.fuelSlot.isEmpty(i)) continue;
            ++n;
        }
        if (n == 0) {
            return false;
        }
        this.energy.addEnergy(Math.pow(2.0, n - 1) * (double)efficiency);
        return true;
    }

    @Override
    public boolean gainFuel() {
        return false;
    }

    @Override
    public boolean needsFuel() {
        return false;
    }

    @Override
    protected boolean delayActiveUpdate() {
        return true;
    }
}

