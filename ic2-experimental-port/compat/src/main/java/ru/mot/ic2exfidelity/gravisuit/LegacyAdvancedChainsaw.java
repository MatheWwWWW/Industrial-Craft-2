package ru.mot.ic2exfidelity.gravisuit;

import ic2.api.item.ElectricItem;
import ic2.core.item.tool.ItemElectricToolChainsaw;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

/** IC2 Classic advanced chainsaw EU tier and 16-speed cutting profile. */
public final class LegacyAdvancedChainsaw extends ItemElectricToolChainsaw {
    public LegacyAdvancedChainsaw(Item.Properties properties) {
        super(properties);
        maxCharge = 10_000;
        transferLimit = 100;
        tier = 3;
        operationEnergyCost = 100;
    }

    @Override
    public float m_8102_(ItemStack stack, BlockState state) {
        return super.m_8102_(stack, state) > 1.0F ? 16.0F : 1.0F;
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(
            Level level, Player player, InteractionHand hand) {
        return new InteractionResultHolder<>(
                InteractionResult.PASS, player.m_21120_(hand));
    }

    public boolean onBlockStartBreak(ItemStack stack, BlockPos position, Player player) {
        Level level = player.m_9236_();
        BlockState origin = level.m_8055_(position);
        if (!origin.m_204336_(BlockTags.f_13106_)) {
            return false;
        }
        Block trunk = origin.m_60734_();
        int energyLimit = (int) (
                ElectricItem.manager.getCharge(stack) / operationEnergyCost);
        if (energyLimit <= 0) {
            return false;
        }
        ArrayDeque<BlockPos> queue = new ArrayDeque<>();
        Set<BlockPos> visited = new HashSet<>();
        List<BlockPos> logs = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            queue.add(position.m_121945_(direction).m_7949_());
        }
        int accepted = 0;
        boolean foundLeaves = false;
        while (!queue.isEmpty() && accepted < 200) {
            BlockPos target = queue.removeFirst();
            if (Math.abs(target.m_123341_() - position.m_123341_()) > 20
                    || Math.abs(target.m_123342_() - position.m_123342_()) > 20
                    || Math.abs(target.m_123343_() - position.m_123343_()) > 20
                    || !visited.add(target)) {
                continue;
            }
            BlockState state = level.m_8055_(target);
            boolean sameLog = state.m_60734_() == trunk;
            boolean naturalLeaves = state.m_60734_() instanceof LeavesBlock
                    && state.m_61143_(LeavesBlock.f_54418_) == 1;
            if (!sameLog && !naturalLeaves) {
                continue;
            }
            accepted++;
            if (sameLog) {
                logs.add(target.m_7949_());
            } else {
                foundLeaves = true;
            }
            for (Direction direction : Direction.values()) {
                BlockPos next = target.m_121945_(direction);
                if (!visited.contains(next)) {
                    queue.addLast(next.m_7949_());
                }
            }
        }
        if (!foundLeaves) {
            return false;
        }

        int removed = 0;
        for (BlockPos target : logs) {
            if (removed >= energyLimit) {
                break;
            }
            BlockState state = level.m_8055_(target);
            if (!state.m_60795_()
                    && state.m_60800_(level, target) >= 0.0F
                    && LegacyVajra.destroyBlock(
                            level, target, true, player, stack)) {
                ElectricItem.manager.use(stack, operationEnergyCost, player);
                removed++;
            }
        }
        return removed > 0;
    }
}
