package ru.mot.ic2exfidelity.gravisuit;

import ic2.core.ContainerBase;
import ic2.core.item.tool.HandHeldInventory;
import ic2.core.network.GrowingBuffer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/** Managed 25-slot inventory stored on a nuclear jetpack ItemStack. */
public final class LegacyNuclearJetpackInventory extends HandHeldInventory {
    public LegacyNuclearJetpackInventory(
            Player player, InteractionHand hand, ItemStack stack) {
        super(player, hand, stack, LegacyNuclearJetpackReactor.SLOT_COUNT);
    }

    @Override
    public boolean m_7013_(int slot, ItemStack stack) {
        return new LegacyNuclearJetpackReactor(containerStack, player)
                .isUsefulItem(stack);
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int syncId, Player player) {
        return new LegacyContainerNuclearJetpack(syncId, this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(
            int syncId, Inventory inventory, GrowingBuffer data) {
        return new LegacyContainerNuclearJetpack(syncId, this);
    }
}
