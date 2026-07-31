package ru.mot.ic2exfidelity.gravisuit;

import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.item.tool.ItemDrill;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

/** IC2 Classic advanced drill power, speed and mode state. */
public final class LegacyAdvancedDrill extends ItemDrill {
    private static final int ENERGY_COST = 80;

    public LegacyAdvancedDrill(Item.Properties properties) {
        super(properties, ENERGY_COST, Tiers.DIAMOND, 10_000, 100, 3, 30.0F);
    }

    @Override
    public float m_8102_(ItemStack stack, BlockState state) {
        if (!ElectricItem.manager.canUse(stack, ENERGY_COST) || !m_8096_(state)) {
            return 1.0F;
        }
        return isMultiMining(stack) ? 270.0F : 30.0F;
    }

    @Override
    public boolean m_8096_(BlockState state) {
        return state.m_204336_(BlockTags.f_144282_)
                || state.m_204336_(BlockTags.f_144283_);
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(
            Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.m_21120_(hand);
        if (IC2.keyboard.isModeSwitchKeyDown(player)) {
            CompoundTag data = stack.m_41784_();
            boolean multi = !data.m_128471_("multi");
            data.m_128379_("multi", multi);
            if (!level.f_46443_) {
                player.m_5661_(Component.m_237115_(multi
                        ? "tooltip.item.ic2.multi_mine.enable"
                        : "tooltip.item.ic2.multi_mine.disable"), false);
            }
            return InteractionResultHolder.m_19090_(stack);
        }
        return super.m_7203_(level, player, hand);
    }

    public boolean isMultiMining(ItemStack stack) {
        return stack.m_41784_().m_128471_("multi");
    }

    public boolean canMultiMine(ItemStack stack) {
        return ElectricItem.manager.canUse(stack, ENERGY_COST * 9.0);
    }

    public boolean onBlockStartBreak(ItemStack stack, BlockPos position, Player player) {
        if (!isMultiMining(stack) || !canMultiMine(stack)) {
            return false;
        }
        Level level = player.m_9236_();
        BlockHitResult hit = m_41435_(level, player, ClipContext.Fluid.NONE);
        net.minecraft.core.Direction.Axis axis = hit.m_82434_().m_122434_();
        int removed = 0;
        for (int first = -1; first <= 1; first++) {
            for (int second = -1; second <= 1; second++) {
                BlockPos target = switch (axis) {
                    case X -> position.m_7918_(0, first, second);
                    case Y -> position.m_7918_(first, 0, second);
                    case Z -> position.m_7918_(first, second, 0);
                };
                BlockState state = level.m_8055_(target);
                if (state.m_60795_()
                        || state.m_60800_(level, target) < 0.0F
                        || !ElectricItem.manager.canUse(stack, ENERGY_COST)) {
                    continue;
                }
                if (LegacyVajra.destroyBlock(level, target, true, player, stack)) {
                    ElectricItem.manager.use(stack, ENERGY_COST, player);
                    removed++;
                }
            }
        }
        return removed > 0;
    }

    @Override
    public int energyUse(ItemStack stack, Level level, BlockPos position, BlockState state) {
        return ENERGY_COST;
    }

    @Override
    public int breakTime(ItemStack stack, Level level, BlockPos position, BlockState state) {
        return 30;
    }

    @Override
    public boolean breakBlock(ItemStack stack, Level level, BlockPos position, BlockState state) {
        return ElectricItem.manager.use(stack, ENERGY_COST, null);
    }
}
