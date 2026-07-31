/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.base.blocks;

import ic2.api.util.DirectionList;
import ic2.core.block.base.IC2Block;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.utils.math.ConnectionState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BaseCTBlock
extends IC2Block
implements IBlockModel {
    public static final IntegerProperty CONNECTION = IntegerProperty.m_61631_((String)"connections", (int)0, (int)63);
    protected TagKey<Block> validBlocks;
    protected String texturePath;

    public BaseCTBlock(String blockName, BlockBehaviour.Properties properties, String texturePath, TagKey<Block> validBlocks) {
        super(blockName, properties);
        this.m_49959_((BlockState)this.m_49966_().m_61124_((Property)CONNECTION, (Comparable)Integer.valueOf(0)));
        this.texturePath = texturePath;
        this.validBlocks = validBlocks;
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        builder.m_61104_(new Property[]{CONNECTION});
    }

    @Override
    public BlockItem createItem() {
        return new IC2BlockItem(this);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSpriteForState(BlockState state, Direction side) {
        return IC2Textures.getMappedEntriesBlockIC2(this.texturePath).get(ConnectionState.fromList(DirectionList.ofNumber((Integer)state.m_61143_((Property)CONNECTION)), side).getTypeName());
    }

    public BlockState m_5573_(BlockPlaceContext context) {
        return (BlockState)this.m_49966_().m_61124_((Property)CONNECTION, (Comparable)Integer.valueOf(this.getConnections(context.m_43725_(), context.m_8083_()).getCode()));
    }

    public void m_6861_(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        int code = this.getConnections(worldIn, pos).getCode();
        if (code != (Integer)state.m_61143_((Property)CONNECTION)) {
            worldIn.m_46597_(pos, (BlockState)state.m_61124_((Property)CONNECTION, (Comparable)Integer.valueOf(code)));
        }
    }

    public DirectionList getConnections(Level world, BlockPos pos) {
        DirectionList connections = DirectionList.EMPTY;
        for (Direction dir : DirectionList.ALL) {
            if (!this.isBlockValid(world.m_8055_(pos.m_121945_(dir)))) continue;
            connections = connections.add(dir);
        }
        return connections;
    }

    protected boolean isBlockValid(BlockState other) {
        return other.m_60713_((Block)this) || this.validBlocks != null && other.m_204336_(this.validBlocks);
    }
}

