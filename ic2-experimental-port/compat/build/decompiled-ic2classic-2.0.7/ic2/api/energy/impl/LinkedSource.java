/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.api.energy.impl;

import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergyEmitter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class LinkedSource
implements IEnergyEmitter {
    Level world;
    BlockPos pos;
    int directions;

    public LinkedSource(Level world, BlockPos pos, Iterable<Direction> directions) {
        this.world = world;
        this.pos = pos;
        for (Direction dir : directions) {
            this.directions |= 1 << dir.m_122411_();
        }
    }

    public LinkedSource(Level world, BlockPos pos, Direction ... directions) {
        this.world = world;
        this.pos = pos;
        for (int i = 0; i < directions.length; ++i) {
            this.directions |= 1 << directions[i].m_122411_();
        }
    }

    public LinkedSource(Level world, BlockPos pos, int directions) {
        this.world = world;
        this.pos = pos;
        this.directions = directions;
    }

    public LinkedSource(Level world, BlockPos pos) {
        this.world = world;
        this.pos = pos;
        this.directions = 63;
    }

    public LinkedSource(BlockEntity tile, int directions) {
        this.world = tile.m_58904_();
        this.pos = tile.m_58899_();
        this.directions = directions;
    }

    public LinkedSource(BlockEntity tile) {
        this.world = tile.m_58904_();
        this.pos = tile.m_58899_();
        this.directions = 63;
    }

    @Override
    public Level getWorldObj() {
        return this.world;
    }

    @Override
    public BlockPos getPosition() {
        return this.pos;
    }

    @Override
    public boolean canEmitEnergy(IEnergyAcceptor acceptor, Direction side) {
        return (this.directions & 1 << side.m_122411_()) != 0;
    }
}

