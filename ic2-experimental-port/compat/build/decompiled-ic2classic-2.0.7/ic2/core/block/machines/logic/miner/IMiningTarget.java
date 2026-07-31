/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machines.logic.miner;

import ic2.core.block.machines.tiles.lv.MinerTileEntity;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public interface IMiningTarget {
    public BlockPos getPos();

    public BlockState getState();

    default public boolean isValid(MinerTileEntity miner) {
        return this.getPos().m_123342_() >= miner.m_58904_().m_141937_() && this.getState() == miner.m_58904_().m_8055_(this.getPos());
    }

    public boolean canMine(MinerTileEntity var1);

    public boolean canContinue(MinerTileEntity var1);

    public List<ItemStack> createDrops(MinerTileEntity var1);

    public CompoundTag save();

    public byte getID();
}

