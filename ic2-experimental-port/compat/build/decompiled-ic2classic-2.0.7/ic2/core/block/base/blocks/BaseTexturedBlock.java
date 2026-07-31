/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.base.blocks;

import ic2.core.block.base.IC2ContainerBlock;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.platform.rendering.features.ITextureProvider;
import ic2.core.platform.rendering.features.block.IBlockModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BaseTexturedBlock
extends IC2ContainerBlock
implements IBlockModel {
    protected BlockEntityType<? extends BlockEntity> type;
    protected ITextureProvider provider;

    public BaseTexturedBlock(String blockName, BlockBehaviour.Properties properties, ITextureProvider provider, BlockEntityType<? extends BlockEntity> type) {
        super(blockName, properties);
        this.provider = provider;
        this.type = type;
    }

    @Override
    public BlockItem createItem() {
        return new IC2BlockItem((Block)this);
    }

    @Override
    public BlockEntity m_142194_(BlockPos pos, BlockState state) {
        return this.type.m_155264_(pos, state);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSpriteForState(BlockState state, Direction side) {
        return this.provider.getTexture(state, side);
    }
}

