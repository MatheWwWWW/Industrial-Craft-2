/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NbtUtils
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.levelgen.structure.BoundingBox
 *  net.minecraftforge.common.util.FakePlayerFactory
 */
package ic2.core.block.machines.logic.miner;

import ic2.core.block.machines.logic.miner.IMiningTarget;
import ic2.core.block.machines.tiles.lv.MinerTileEntity;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraftforge.common.util.FakePlayerFactory;

public class BlockMiningTarget
implements IMiningTarget {
    BlockPos pos;
    BlockState state;

    public BlockMiningTarget(CompoundTag nbt) {
        this(BlockPos.m_122022_((long)nbt.m_128454_("position")), NbtUtils.m_129241_((CompoundTag)nbt));
    }

    public BlockMiningTarget(BlockPos pos, BlockState state) {
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
            return miner.canMine(this.pos, this.state, miner.hasPumps());
        }
        catch (Exception e) {
            this.state = miner.m_58904_().m_8055_(this.pos);
            return this.canMine(miner);
        }
    }

    @Override
    public boolean canContinue(MinerTileEntity miner) {
        return true;
    }

    @Override
    public List<ItemStack> createDrops(MinerTileEntity miner) {
        ObjectList drops = CollectionUtils.createList();
        drops.addAll(Block.m_49874_((BlockState)this.state, (ServerLevel)((ServerLevel)miner.m_58904_()), (BlockPos)this.pos, (BlockEntity)miner.m_58904_().m_7702_(this.pos), (Entity)FakePlayerFactory.getMinecraft((ServerLevel)((ServerLevel)miner.m_58904_())), (ItemStack)miner.getStackInSlot(miner.getDrillSlot()).m_41777_()));
        miner.m_58904_().m_7731_(this.pos, Blocks.f_50016_.m_49966_(), 3);
        ((ServerLevel)miner.m_58904_()).m_183324_().m_193234_(BoundingBox.m_162375_((Vec3i)this.pos.m_7918_(-2, -2, -2), (Vec3i)this.pos.m_7918_(2, 2, 2)));
        return drops;
    }

    @Override
    public CompoundTag save() {
        CompoundTag nbt = NbtUtils.m_129202_((BlockState)this.state);
        nbt.m_128356_("position", this.pos.m_121878_());
        return nbt;
    }

    @Override
    public byte getID() {
        return 0;
    }
}

