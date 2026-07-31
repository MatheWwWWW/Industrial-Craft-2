package ru.mot.ic2exfidelity.legacy;

import ic2.api.item.ElectricItem;
import ic2.api.item.IBoxable;
import ic2.core.item.ItemBattery;
import java.util.List;
import java.util.Locale;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

/** Faithful 1.12.2 charging-battery hotbar behaviour on the ex119 energy API. */
public final class LegacyChargingBattery extends ItemBattery implements IBoxable {
    private static final String MODE_TAG = "mode";

    private enum Mode {
        ENABLED,
        NOT_IN_HAND,
        DISABLED
    }

    public LegacyChargingBattery(Item.Properties properties, double capacity, double transfer, int tier) {
        super(properties, capacity, transfer, tier);
    }

    @Override
    public void m_6883_(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        Mode mode = getMode(stack);
        if (!(entity instanceof Player player) || level.f_46443_ || mode == Mode.DISABLED
                || level.m_46467_() % 10L >= getTier(stack)) {
            return;
        }

        double remaining = getTransferLimit(stack);
        int batteryTier = getTier(stack);
        for (int hotbarSlot = 0; hotbarSlot < 9 && remaining > 0.0; hotbarSlot++) {
            ItemStack target = player.m_150109_().f_35974_.get(hotbarSlot);
            if (target.m_41619_() || target == stack || target.m_41720_() instanceof LegacyChargingBattery
                    || mode == Mode.NOT_IN_HAND && hotbarSlot == player.m_150109_().f_35977_) {
                continue;
            }

            double accepted = ElectricItem.manager.charge(target, remaining, batteryTier, false, true);
            if (accepted <= 0.0) {
                continue;
            }
            double discharged = ElectricItem.manager.discharge(
                    stack, accepted, batteryTier, true, false, false);
            if (discharged <= 0.0) {
                break;
            }
            ElectricItem.manager.charge(target, discharged, batteryTier, true, false);
            remaining -= discharged;
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.m_21120_(hand);
        if (!level.f_46443_) {
            Mode[] modes = Mode.values();
            Mode mode = modes[(getMode(stack).ordinal() + 1) % modes.length];
            stack.m_41784_().m_128344_(MODE_TAG, (byte) mode.ordinal());
            player.m_5661_(Component.m_237110_(
                    "ic2.tooltip.mode",
                    Component.m_237115_(modeTranslationKey(mode))), true);
        }
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }

    @Override
    public void m_7373_(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.m_7373_(stack, level, tooltip, flag);
        tooltip.add(Component.m_237110_(
                "ic2.tooltip.mode",
                Component.m_237115_(modeTranslationKey(getMode(stack)))));
    }

    @Override
    public boolean canBeStoredInToolbox(ItemStack stack) {
        return getMode(stack) == Mode.DISABLED;
    }

    private static Mode getMode(ItemStack stack) {
        CompoundTag tag = stack.m_41783_();
        if (tag == null || !tag.m_128425_(MODE_TAG, 1)) {
            return Mode.ENABLED;
        }
        int value = tag.m_128445_(MODE_TAG);
        Mode[] modes = Mode.values();
        return value >= 0 && value < modes.length ? modes[value] : Mode.ENABLED;
    }

    private static String modeTranslationKey(Mode mode) {
        return "ic2.tooltip.mode." + mode.name().toLowerCase(Locale.ROOT);
    }
}
