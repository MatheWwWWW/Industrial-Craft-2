package ru.mot.ic2exfidelity.gravisuit;

import ic2.core.IC2;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/** IC2 Classic memory stick behavior needed by the original Relocator recipe. */
public final class LegacyMemoryStick extends Item {
    public LegacyMemoryStick(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(
            Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.m_21120_(hand);
        if (IC2.keyboard.isAltKeyDown(player)) {
            stack.m_41751_(null);
            return InteractionResultHolder.m_19090_(stack);
        }
        return super.m_7203_(level, player, hand);
    }
}
