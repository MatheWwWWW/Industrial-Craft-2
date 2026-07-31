/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.material.Material
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.machines;

import ic2.core.block.base.blocks.BaseActivityBlock;
import ic2.core.block.base.drops.IBlockDropProvider;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.platform.rendering.features.IRenderType;
import ic2.core.platform.rendering.features.ITextureProvider;
import ic2.core.utils.helpers.Tool;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BaseMachineBlock
extends BaseActivityBlock<BaseTileEntity>
implements IRenderType {
    public static final BlockBehaviour.Properties BASE_MACHINE = BlockBehaviour.Properties.m_60939_((Material)Material.f_76279_).m_60918_(SoundType.f_56743_).m_60913_(5.0f, 25.0f).m_60999_();
    protected boolean cutout = false;

    public BaseMachineBlock(String blockName, IBlockDropProvider drop, ITextureProvider provider, BlockEntityType<? extends BaseTileEntity> type) {
        super(blockName, BASE_MACHINE, provider, type);
        this.setDropProvider(drop);
        this.setHarvestTool(Tool.PICKAXE.withLevel(1));
    }

    public BaseMachineBlock(String blockName, BlockBehaviour.Properties properties, IBlockDropProvider drop, ITextureProvider provider, BlockEntityType<? extends BlockEntity> tile) {
        super(blockName, properties, provider, tile);
        this.setDropProvider(drop);
        this.setHarvestTool(Tool.PICKAXE.withLevel(1));
    }

    public BaseMachineBlock setCutout() {
        this.cutout = true;
        return this;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public RenderType getType() {
        return this.cutout ? RenderType.m_110463_() : RenderType.m_110451_();
    }
}

