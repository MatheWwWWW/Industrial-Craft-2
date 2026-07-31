/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.material.Material
 */
package ic2.core.block.generators;

import ic2.core.block.base.blocks.BaseActivityBlock;
import ic2.core.block.base.drops.IBlockDropProvider;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.platform.rendering.features.ITextureProvider;
import ic2.core.utils.helpers.Tool;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class BaseGeneratorBlock
extends BaseActivityBlock<BaseTileEntity> {
    public static final BlockBehaviour.Properties BASE_GENERATOR = BlockBehaviour.Properties.m_60939_((Material)Material.f_76279_).m_60918_(SoundType.f_56743_).m_60913_(5.0f, 25.0f).m_60999_();

    public BaseGeneratorBlock(String blockName, IBlockDropProvider drops, ITextureProvider provider, BlockEntityType<? extends BlockEntity> tile) {
        super(blockName, BASE_GENERATOR, provider, tile);
        this.setHarvestTool(Tool.PICKAXE.withLevel(1));
        this.setDropProvider(drops);
    }
}

