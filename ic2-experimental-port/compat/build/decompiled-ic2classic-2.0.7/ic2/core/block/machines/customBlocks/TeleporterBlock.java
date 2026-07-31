/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 */
package ic2.core.block.machines.customBlocks;

import ic2.core.block.base.drops.IBlockDropProvider;
import ic2.core.block.machines.BaseMachineBlock;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.platform.rendering.features.ITextureProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class TeleporterBlock
extends BaseMachineBlock {
    public TeleporterBlock() {
        super("teleporter", IBlockDropProvider.SELF_OR_ADV_MACHINE, ITextureProvider.toggleIC2("machine/hv/teleporter"), IC2Tiles.TELEPORTER);
    }

    @Override
    protected void setDefaultState() {
        this.m_49959_((BlockState)((BlockState)this.m_49966_().m_61124_((Property)FACING, (Comparable)Direction.UP)).m_61124_((Property)ACTIVE, (Comparable)Boolean.valueOf(false)));
    }

    @Override
    protected Direction getFacing(BlockPlaceContext context) {
        return Direction.UP;
    }
}

