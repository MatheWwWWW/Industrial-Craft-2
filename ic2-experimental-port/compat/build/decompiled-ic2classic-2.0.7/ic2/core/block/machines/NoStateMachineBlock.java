/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 */
package ic2.core.block.machines;

import ic2.core.block.base.blocks.BaseFacingBlock;
import ic2.core.block.base.drops.IBlockDropProvider;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.block.machines.BaseMachineBlock;
import ic2.core.platform.rendering.features.ITextureProvider;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class NoStateMachineBlock
extends BaseFacingBlock<BaseTileEntity> {
    public NoStateMachineBlock(String blockName, IBlockDropProvider drops, ITextureProvider provider, BlockEntityType<? extends BaseTileEntity> type) {
        super(blockName, BaseMachineBlock.BASE_MACHINE, provider, type);
        this.setDropProvider(drops);
    }

    public NoStateMachineBlock(String blockName, IBlockDropProvider drops, ITextureProvider provider, BlockBehaviour.Properties prop, BlockEntityType<? extends BaseTileEntity> type) {
        super(blockName, prop, provider, type);
        this.setDropProvider(drops);
    }
}

