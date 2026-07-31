/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Vec3i
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.api.tiles;

import ic2.api.util.ILocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

public interface ITerraformer
extends ILocation {
    default public Holder<Biome> getBiome(Level world, BlockPos pos) {
        return world.m_204166_(pos);
    }

    public boolean setBiome(Level var1, BlockPos var2, ResourceLocation var3);

    default public BlockPos getFirstSolidBlockFrom(Level world, BlockPos pos) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos().m_122190_((Vec3i)pos);
        while (mutable.m_123342_() > -1) {
            if (world.m_8055_((BlockPos)mutable).m_60796_((BlockGetter)world, (BlockPos)mutable)) {
                return mutable.m_7949_();
            }
            mutable.m_122173_(Direction.DOWN);
        }
        return mutable.m_7949_();
    }

    default public BlockPos getFirstBlockFrom(Level world, BlockPos pos) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos().m_122190_((Vec3i)pos);
        while (mutable.m_123342_() > 0) {
            if (!world.m_46859_((BlockPos)mutable)) {
                return mutable.m_7949_();
            }
            mutable.m_122173_(Direction.DOWN);
        }
        mutable.m_142448_(-1);
        return mutable.m_7949_();
    }

    default public boolean switchGround(Level world, BlockPos pos, BlockState from, BlockState to, boolean upwards, boolean stateCompare) {
        BlockState state;
        if (upwards) {
            BlockState state2;
            BlockPos.MutableBlockPos targetPos = new BlockPos.MutableBlockPos().m_122159_((Vec3i)pos, Direction.UP);
            BlockPos.MutableBlockPos checkPos = new BlockPos.MutableBlockPos().m_122190_((Vec3i)pos);
            BlockPos up = targetPos.m_7949_();
            while (!(world.m_46859_((BlockPos)checkPos) || (state2 = world.m_8055_((BlockPos)checkPos)).m_60734_() != from.m_60734_() || stateCompare && state2 != from)) {
                targetPos.m_122173_(Direction.DOWN);
                checkPos.m_122173_(Direction.DOWN);
            }
            if (targetPos.m_123342_() == up.m_123342_()) {
                return false;
            }
            world.m_46597_(targetPos.m_7949_(), to);
            return true;
        }
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos().m_122190_((Vec3i)pos);
        while (!(world.m_46859_((BlockPos)mutable) || (state = world.m_8055_((BlockPos)mutable)).m_60734_() != to.m_60734_() || stateCompare && state != from)) {
            mutable.m_122173_(Direction.DOWN);
        }
        if (mutable.m_123342_() < 0 || world.m_46859_((BlockPos)mutable)) {
            return false;
        }
        state = world.m_8055_((BlockPos)mutable);
        if (state.m_60734_() != from.m_60734_() || stateCompare && state != from) {
            return false;
        }
        world.m_46597_(mutable.m_7949_(), to);
        return true;
    }
}

