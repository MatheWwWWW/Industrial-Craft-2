/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Wearable;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;

public abstract class AbstractSkullBlock
extends BaseEntityBlock
implements Wearable {
    private final SkullBlock.Type f_48743_;

    public AbstractSkullBlock(SkullBlock.Type p_48745_, BlockBehaviour.Properties p_48746_) {
        super(p_48746_);
        this.f_48743_ = p_48745_;
    }

    @Override
    public BlockEntity m_142194_(BlockPos p_151996_, BlockState p_151997_) {
        return new SkullBlockEntity(p_151996_, p_151997_);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_151992_, BlockState p_151993_, BlockEntityType<T> p_151994_) {
        if (p_151992_.f_46443_ && (p_151993_.m_60713_(Blocks.f_50320_) || p_151993_.m_60713_(Blocks.f_50321_))) {
            return AbstractSkullBlock.m_152132_(p_151994_, BlockEntityType.f_58931_, SkullBlockEntity::m_155733_);
        }
        return null;
    }

    public SkullBlock.Type m_48754_() {
        return this.f_48743_;
    }

    @Override
    public boolean m_7357_(BlockState p_48750_, BlockGetter p_48751_, BlockPos p_48752_, PathComputationType p_48753_) {
        return false;
    }
}

