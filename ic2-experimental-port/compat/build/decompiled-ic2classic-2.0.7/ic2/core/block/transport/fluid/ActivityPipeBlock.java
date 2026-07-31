/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.transport.fluid;

import ic2.core.block.transport.fluid.PipeBlock;
import ic2.core.block.transport.fluid.tiles.PipeTileEntity;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.rendering.IC2Textures;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ActivityPipeBlock
extends PipeBlock {
    public static final BooleanProperty ACTIVE = IC2Properties.ACTIVE;
    String activeTexture;
    String inactiveTexture;

    public ActivityPipeBlock(String blockName, BlockEntityType<? extends BlockEntity> type, String texture) {
        super(blockName, type, "");
        this.m_49959_((BlockState)this.m_49966_().m_61124_((Property)ACTIVE, (Comparable)Boolean.valueOf(false)));
        this.activeTexture = texture + "_active";
        this.inactiveTexture = texture + "_inactive";
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture(BlockState state, boolean center, Direction dir, Direction side) {
        return IC2Textures.getMappedEntriesBlockIC2("transport/pipes").get((Boolean)state.m_61143_((Property)ACTIVE) != false ? this.activeTexture : this.inactiveTexture);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        super.m_7926_(builder);
        builder.m_61104_(new Property[]{ACTIVE});
    }

    @Override
    public void onStateUpdate(Level world, BlockPos pos, BlockState state, PipeTileEntity tile) {
        tile.setState((BlockState)((BlockState)((BlockState)((BlockState)state.m_61124_((Property)FOAMED, (Comparable)Integer.valueOf(tile.foamed))).m_61124_((Property)WATER, (Comparable)Boolean.valueOf((Boolean)state.m_61143_((Property)WATER) != false && tile.foamed == 0))).m_61124_((Property)LAVA, (Comparable)Boolean.valueOf((Boolean)state.m_61143_((Property)LAVA) != false && tile.foamed == 0))).m_61124_((Property)ACTIVE, (Comparable)Boolean.valueOf(tile.isActive())));
    }
}

