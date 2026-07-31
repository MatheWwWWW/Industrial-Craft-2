/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class AbstractGlassBlock
extends HalfTransparentBlock {
    protected AbstractGlassBlock(BlockBehaviour.Properties p_48729_) {
        super(p_48729_);
    }

    @Override
    public VoxelShape m_5909_(BlockState p_48735_, BlockGetter p_48736_, BlockPos p_48737_, CollisionContext p_48738_) {
        return Shapes.m_83040_();
    }

    @Override
    public float m_7749_(BlockState p_48731_, BlockGetter p_48732_, BlockPos p_48733_) {
        return 1.0f;
    }

    @Override
    public boolean m_7420_(BlockState p_48740_, BlockGetter p_48741_, BlockPos p_48742_) {
        return true;
    }
}

