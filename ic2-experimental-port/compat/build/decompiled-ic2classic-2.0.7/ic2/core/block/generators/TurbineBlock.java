/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.generators;

import ic2.core.block.base.drops.IBlockDropProvider;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.block.generators.BaseGeneratorBlock;
import ic2.core.block.generators.tiles.SteamTunnelTileEntity;
import ic2.core.block.rendering.block.TurbineBladeModel;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.IRenderType;
import ic2.core.platform.rendering.features.ITextureProvider;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.platform.rendering.features.block.ICustomBlockModel;
import ic2.core.platform.rendering.models.BaseModel;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TurbineBlock
extends BaseGeneratorBlock
implements IBlockModel,
ICustomBlockModel,
IRenderType {
    public static final IntegerProperty FORMED = IC2Properties.FORMED_STATE3X3_FLAT;

    public TurbineBlock() {
        super("steam_tunnel", IBlockDropProvider.SELF, ITextureProvider.noStateIC2("electric/generator/steam_tunnel/base"), IC2Tiles.STEAM_TUNNEL);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        super.m_7926_(builder);
        builder.m_61104_(new Property[]{FORMED});
    }

    @Override
    public BlockItem createItem() {
        return new IC2BlockItem((Block)this);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSpriteForState(BlockState state, Direction side) {
        return super.getSpriteForState(state, side);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public RenderType getType() {
        return RenderType.m_110463_();
    }

    @Override
    public void onStateUpdate(Level world, BlockPos pos, BlockState state, BaseTileEntity tile) {
        SteamTunnelTileEntity tunnel = (SteamTunnelTileEntity)tile;
        tile.setState((BlockState)((BlockState)((BlockState)state.m_61124_((Property)FACING, (Comparable)tile.getFacing())).m_61124_((Property)ACTIVE, (Comparable)Boolean.valueOf(tile.isActive()))).m_61124_((Property)FORMED, (Comparable)Integer.valueOf(tunnel.isValid ? 5 : 0)));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public BaseModel getForCustomState(BlockState state) {
        return new TurbineBladeModel(state, this, IC2Textures.getMappedEntriesBlockIC2("electric/generator/steam_tunnel").get((Boolean)state.m_61143_((Property)ACTIVE) != false ? "active_overlay" : "inactive_overlay"));
    }

    @Override
    public List<BlockState> getModelStates() {
        return Collections.emptyList();
    }
}

