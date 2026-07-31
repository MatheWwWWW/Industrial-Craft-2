package ru.mot.ic2exfidelity.legacy;

import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.network.GrowingBuffer;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import ru.mot.ic2exfidelity.integration.RestoredLegacyContent;

/** Exact 2.8.222 Compact Item Buffer (`te#item_buffer_2`). */
public final class LegacyCompactItemBufferBlockEntity extends TileEntityInventory
        implements IHasGui, IUpgradableBlock {
    public final InvSlot bufferSlot = new InvSlot(
            this, "buffer", InvSlot.Access.IO, 9, InvSlot.InvSide.ANY);
    public final InvSlotUpgrade upgradeSlot = new InvSlotUpgrade(this, "upgrade", 4);

    public LegacyCompactItemBufferBlockEntity(BlockPos position, BlockState state) {
        super(RestoredLegacyContent.ITEM_BUFFER_2_BLOCK_ENTITY.get(), position, state);
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        upgradeSlot.tick();
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(
                UpgradableProperty.ItemProducing,
                UpgradableProperty.ItemConsuming);
    }

    @Override
    public double getEnergy() {
        return 0.0D;
    }

    @Override
    public boolean useEnergy(double amount) {
        return true;
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int syncId, Player player) {
        return DynamicContainer.create(syncId, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(
            int syncId, Inventory inventory, GrowingBuffer buffer) {
        return DynamicContainer.create(syncId, inventory, this);
    }
}
