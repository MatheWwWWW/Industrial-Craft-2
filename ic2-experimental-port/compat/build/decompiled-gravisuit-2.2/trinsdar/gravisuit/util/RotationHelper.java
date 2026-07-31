/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.ChestBlock
 *  net.minecraft.world.level.block.Rotation
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.ChestType
 *  net.minecraft.world.level.block.state.properties.Property
 */
package trinsdar.gravisuit.util;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.state.properties.Property;

public class RotationHelper {
    public static final List<RotationBlacklistEntry> blacklist = new ArrayList<RotationBlacklistEntry>();

    public static boolean rotateBlock(Level world, BlockPos pos, boolean inverse) {
        return RotationHelper.rotateBlock(world, pos, inverse ? Rotation.COUNTERCLOCKWISE_90 : Rotation.CLOCKWISE_90);
    }

    public static boolean rotateBlock(Level world, BlockPos pos, Rotation rotation) {
        for (RotationBlacklistEntry e : blacklist) {
            if (e.blockRotation(world, pos)) continue;
            return false;
        }
        BlockState state = world.m_8055_(pos);
        BlockState newState = state.rotate((LevelAccessor)world, pos, rotation);
        if (newState != state) {
            BlockState otherState;
            BlockPos otherPos;
            world.m_46597_(pos, newState);
            for (Direction d : Direction.values()) {
                otherPos = pos.m_121945_(d);
                otherState = world.m_8055_(otherPos);
                BlockState nextState = newState.m_60728_(d, otherState, (LevelAccessor)world, pos, otherPos);
                if (nextState == newState) continue;
                if (!nextState.m_60795_()) {
                    world.m_46597_(pos, nextState);
                    newState = nextState;
                    continue;
                }
                world.m_46597_(pos, state);
                return false;
            }
            for (Direction d : Direction.values()) {
                otherPos = pos.m_121945_(d);
                otherState = world.m_8055_(otherPos);
                BlockState nextOther = otherState.m_60728_(d.m_122424_(), newState, (LevelAccessor)world, otherPos, pos);
                if (nextOther == otherState) continue;
                world.m_46597_(otherPos, nextOther);
            }
            return true;
        }
        return false;
    }

    static {
        blacklist.add((w, pos) -> {
            BlockState state = w.m_8055_(pos);
            return state.m_60734_() != Blocks.f_50087_ || state.m_61143_((Property)ChestBlock.f_51479_) == ChestType.SINGLE;
        });
    }

    @FunctionalInterface
    public static interface RotationBlacklistEntry {
        public boolean blockRotation(Level var1, BlockPos var2);
    }
}

