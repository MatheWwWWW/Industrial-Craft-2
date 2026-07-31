package ru.mot.ic2exfidelity.legacy;

import ic2.core.ContainerBase;
import ic2.core.item.tool.HandHeldInventory;
import ic2.core.network.GrowingBuffer;
import ic2.core.slot.SlotRadioactive;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public final class LegacyHandHeldContainmentBox extends HandHeldInventory {
    public LegacyHandHeldContainmentBox(Player player, InteractionHand hand, ItemStack stack) {
        super(player, hand, stack, 12);
    }

    @Override
    public boolean m_7013_(int slot, ItemStack stack) {
        return new SlotRadioactive(this, slot, 0, 0).m_5857_(stack);
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int syncId, Player player) {
        return new LegacyContainerContainmentBox(syncId, this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(
            int syncId, Inventory inventory, GrowingBuffer data) {
        return new LegacyContainerContainmentBox(syncId, this);
    }
}
