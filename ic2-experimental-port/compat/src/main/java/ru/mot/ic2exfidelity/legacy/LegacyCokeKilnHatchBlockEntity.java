package ru.mot.ic2exfidelity.legacy;

import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.network.GrowingBuffer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import ru.mot.ic2exfidelity.integration.RestoredLegacyContent;

/** Single input hatch used by the IC2 2.8.222 coke-kiln structure. */
public final class LegacyCokeKilnHatchBlockEntity extends TileEntityInventory implements IHasGui {
    public final InvSlot inventory;

    public LegacyCokeKilnHatchBlockEntity(BlockPos pos, BlockState state) {
        super(RestoredLegacyContent.COKE_KILN_HATCH_BLOCK_ENTITY.get(), pos, state);
        inventory = new InvSlot(this, "inventory", InvSlot.Access.I, 1, InvSlot.InvSide.ANY);
    }

    @Override
    public boolean m_7155_(int slot, ItemStack stack, Direction side) {
        return side == getFacing() && super.m_7155_(slot, stack, side);
    }

    @Override
    public boolean m_7157_(int slot, ItemStack stack, Direction side) {
        return false;
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int syncId, Player player) {
        return DynamicContainer.create(syncId, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(
            int syncId, Inventory inventory, GrowingBuffer data) {
        return DynamicContainer.create(syncId, inventory, this);
    }
}
