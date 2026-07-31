/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 */
package ic2.core.block.machines.customBlocks;

import ic2.core.block.base.drops.IBlockDropProvider;
import ic2.core.block.machines.BaseMachineBlock;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.platform.rendering.features.ITextureProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class CropMatronBlock
extends BaseMachineBlock {
    public CropMatronBlock() {
        super("cropmatron", IBlockDropProvider.SELF_OR_MACHINE, ITextureProvider.toggleIC2("machine/mv/crop_matron"), IC2Tiles.CROP_MATRON);
    }

    @Override
    protected void setDefaultState() {
        this.m_49959_((BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_((Property)FACING, (Comparable)Direction.NORTH)).m_61124_((Property)ACTIVE, (Comparable)Boolean.valueOf(false))).m_61124_((Property)BlockStateProperties.f_61362_, (Comparable)Boolean.valueOf(false)));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        super.m_7926_(builder);
        builder.m_61104_(new Property[]{BlockStateProperties.f_61362_});
    }

    public FluidState m_5888_(BlockState state) {
        return (Boolean)state.m_61143_((Property)BlockStateProperties.f_61362_) != false ? Fluids.f_76193_.m_76145_() : Fluids.f_76191_.m_76145_();
    }
}

