package ru.mot.ic2exfidelity.legacy;

import ic2.core.IHasGui;
import ic2.core.item.IHandHeldInventory;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

/** Twelve-slot portable inventory restricted to radioactive IC2 items. */
public final class LegacyContainmentBox extends Item implements IHandHeldInventory {
    public LegacyContainmentBox(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(
            Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.m_21120_(hand);
        if (!level.f_46443_) {
            getInventory(player, hand, stack).openManagedItem(player, hand, null);
        }
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }

    @Override
    public Rarity m_41460_(ItemStack stack) {
        return Rarity.UNCOMMON;
    }

    @Override
    public IHasGui getInventory(Player player, InteractionHand hand, ItemStack stack) {
        return new LegacyHandHeldContainmentBox(player, hand, stack);
    }
}
