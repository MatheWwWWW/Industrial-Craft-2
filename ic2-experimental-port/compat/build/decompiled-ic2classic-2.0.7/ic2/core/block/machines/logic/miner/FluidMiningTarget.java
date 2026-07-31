/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NbtUtils
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.levelgen.structure.BoundingBox
 *  net.minecraftforge.fluids.FluidStack
 */
package ic2.core.block.machines.logic.miner;

import ic2.core.block.machines.logic.miner.IMiningTarget;
import ic2.core.block.machines.tiles.lv.MinerTileEntity;
import java.util.Collections;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraftforge.fluids.FluidStack;

public class FluidMiningTarget
implements IMiningTarget {
    BlockPos pos;
    BlockState state;
    FluidStack cache = null;

    public FluidMiningTarget(CompoundTag nbt) {
        this(BlockPos.m_122022_((long)nbt.m_128454_("position")), NbtUtils.m_129241_((CompoundTag)nbt));
    }

    public FluidMiningTarget(BlockPos pos, BlockState state) {
        this.pos = pos;
        this.state = state;
    }

    @Override
    public BlockPos getPos() {
        return this.pos;
    }

    @Override
    public BlockState getState() {
        return this.state;
    }

    @Override
    public boolean canMine(MinerTileEntity miner) {
        try {
            if (!miner.hasPumps()) {
                return false;
            }
            if (miner.canMine(this.pos, this.state, miner.hasPumps())) {
                return miner.pumpFluid(this.getCache(), false) != InteractionResult.FAIL;
            }
        }
        catch (Exception e) {
            this.state = miner.m_58904_().m_8055_(this.pos);
            this.cache = null;
            return this.canMine(miner);
        }
        return false;
    }

    @Override
    public boolean canContinue(MinerTileEntity miner) {
        if (!miner.hasPumps()) {
            return true;
        }
        return miner.pumpFluid(this.getCache(), false) == InteractionResult.SUCCESS;
    }

    @Override
    public List<ItemStack> createDrops(MinerTileEntity miner) {
        if (miner.hasPumps()) {
            miner.pumpFluid(this.getCache(), true);
        }
        miner.m_58904_().m_7731_(this.pos, Blocks.f_50016_.m_49966_(), 11);
        ((ServerLevel)miner.m_58904_()).m_183324_().m_193234_(BoundingBox.m_162375_((Vec3i)this.pos.m_7918_(-2, -2, -2), (Vec3i)this.pos.m_7918_(2, 2, 2)));
        return Collections.emptyList();
    }

    @Override
    public CompoundTag save() {
        CompoundTag nbt = NbtUtils.m_129202_((BlockState)this.state);
        nbt.m_128356_("position", this.pos.m_121878_());
        return nbt;
    }

    @Override
    public byte getID() {
        return 1;
    }

    public FluidStack getCache() {
        if (this.cache == null) {
            this.cache = new FluidStack(this.state.m_60819_().m_76152_(), 1000);
        }
        return this.cache;
    }
}

