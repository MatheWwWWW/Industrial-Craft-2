package ru.mot.ic2exfidelity.gravisuit;

import ic2.core.ContainerBase;
import ic2.core.item.tool.HandHeldInventory;
import ic2.core.network.GrowingBuffer;
import java.io.IOException;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/** Zero-slot managed-item carrier for the two exact relocator screens. */
public final class LegacyRelocatorInventory extends HandHeldInventory {
    private boolean addMode;

    public LegacyRelocatorInventory(
            Player player, InteractionHand hand, ItemStack stack) {
        super(player, hand, stack, 0);
        addMode = player.m_6047_() && stack.m_41784_().m_128445_("mode") == 0;
    }

    public boolean isAddMode() {
        return addMode;
    }

    public InteractionHand hand() {
        return hand;
    }

    @Override
    public void writeScreenOpenData(
            Player player, InteractionHand hand, GrowingBuffer buffer)
            throws IOException {
        buffer.writeBoolean(addMode);
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int syncId, Player player) {
        return new LegacyContainerRelocator(syncId, this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(
            int syncId, Inventory inventory, GrowingBuffer data) {
        addMode = data.readBoolean();
        return new LegacyContainerRelocator(syncId, this);
    }
}
