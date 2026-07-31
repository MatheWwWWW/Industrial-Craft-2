package ru.mot.ic2exfidelity.legacy;

import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.comp.Fluids;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.network.GrowingBuffer;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import ru.mot.ic2exfidelity.integration.RestoredLegacyContent;

/** 64-bucket creosote output grate from IC2 2.8.222. */
public final class LegacyCokeKilnGrateBlockEntity extends TileEntityInventory implements IHasGui {
    public final Fluids fluids;
    public final Fluids.InternalFluidTank fluidTank;

    public LegacyCokeKilnGrateBlockEntity(BlockPos pos, BlockState state) {
        super(RestoredLegacyContent.COKE_KILN_GRATE_BLOCK_ENTITY.get(), pos, state);
        fluids = addComponent(new Fluids(this));
        fluidTank = fluids.addTank(
                "fluidTank", 64_000, InvSlot.Access.O, InvSlot.InvSide.ANY);
    }

    @Override
    protected void onLoaded() {
        fluids.changeConnectivity(fluidTank, List.of(), Set.of(getFacing()));
        super.onLoaded();
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
