/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machines.tiles.mv;

import ic2.core.block.base.tiles.impls.BaseCropLibraryTileEntity;
import ic2.core.block.machines.containers.mv.SimpleCropLibraryContainer;
import ic2.core.block.machines.logic.crop.SeedStorage;
import ic2.core.inventory.container.IC2Container;
import ic2.core.platform.registries.IC2Tiles;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SimpleCropLibraryTileEntity
extends BaseCropLibraryTileEntity {
    public SimpleCropLibraryTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, new SeedStorage(20, 20, 128));
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.SIMPLE_CROP_LIBRARY;
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new SimpleCropLibraryContainer(this, player, windowID);
    }

    @Override
    public double getDropRate(Player player) {
        return 0.8;
    }

    @Override
    public void addDrops(List<ItemStack> drops) {
        super.addDrops(drops);
        this.storage.addDrops(drops);
    }
}

