/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.SpawnerBlockEntity
 */
package ic2.core.utils.compat;

import ic2.api.tiles.readers.IActivityProvider;
import ic2.api.tiles.readers.IProgressMachine;
import ic2.core.platform.corehacks.mixins.server.info.SpawnerMixin;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;

public class MobSpawnerHelper
implements IActivityProvider,
IProgressMachine {
    Level level;
    BlockPos pos;
    SpawnerMixin tile;

    public MobSpawnerHelper(BlockEntity tile) {
        this((SpawnerBlockEntity)tile);
    }

    public MobSpawnerHelper(SpawnerBlockEntity tile) {
        this.level = tile.m_58904_();
        this.pos = tile.m_58899_();
        this.tile = (SpawnerMixin)tile.m_59801_();
    }

    @Override
    public float getProgress() {
        return this.tile.getMaxDelay() - this.tile.getDelay();
    }

    @Override
    public float getMaxProgress() {
        return this.tile.getMaxDelay();
    }

    @Override
    public boolean isActivated() {
        return this.tile.isActive(this.level, this.pos);
    }
}

