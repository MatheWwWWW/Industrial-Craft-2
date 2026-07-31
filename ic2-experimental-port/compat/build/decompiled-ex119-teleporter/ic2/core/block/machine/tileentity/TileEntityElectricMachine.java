/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machine.tileentity;

import ic2.core.block.comp.Energy;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotDischarge;
import ic2.core.block.tileentity.TileEntityBase;
import ic2.core.block.tileentity.TileEntityInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class TileEntityElectricMachine
extends TileEntityBase {
    protected final Energy energy;
    public final InvSlotDischarge dischargeSlot;

    public TileEntityElectricMachine(BlockEntityType<? extends TileEntityElectricMachine> blockEntityType, BlockPos blockPos, BlockState blockState, int n, int n2) {
        this(blockEntityType, blockPos, blockState, n, n2, true);
    }

    public TileEntityElectricMachine(BlockEntityType<? extends TileEntityElectricMachine> blockEntityType, BlockPos blockPos, BlockState blockState, int n, int n2, boolean bl) {
        super((BlockEntityType<? extends TileEntityInventory>)blockEntityType, blockPos, blockState);
        this.dischargeSlot = new InvSlotDischarge(this, InvSlot.Access.NONE, n2, bl, InvSlot.InvSide.ANY);
        this.energy = this.addComponent(Energy.asBasicSink(this, n, n2).addManagedSlot(this.dischargeSlot));
    }

    public boolean hasEnergy() {
        return this.energy.getEnergy() > 0.0;
    }
}

