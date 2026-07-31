package ru.mot.ic2exfidelity.legacy;

import ic2.api.item.IBoxable;
import ic2.core.IC2;
import ic2.core.block.misc.FoamBlock;
import ic2.core.block.wiring.CableBlock;
import ic2.core.fluid.Ic2FluidItem;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.fluid.StandardFluidItem;
import ic2.core.ref.Ic2Blocks;
import ic2.core.ref.Ic2Fluids;
import ic2.core.ref.Ic2Items;
import ic2.core.util.LiquidUtil;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/** 8-bucket construction-foam sprayer with the legacy 1/10-block modes. */
public final class LegacyFoamSprayer extends Item
        implements StandardFluidItem, IBoxable {
    private static final String MODE_TAG = "mode";
    private static final int CAPACITY_MB = 8_000;
    private static final int MB_PER_BLOCK = 100;

    private enum Target {
        ANY,
        SCAFFOLD,
        CABLE
    }

    public LegacyFoamSprayer(Properties properties) {
        super(properties);
    }

    @Override
    public int getCapacityMb(ItemStack stack) {
        return CAPACITY_MB;
    }

    @Override
    public boolean canFill(ItemStack stack, Ic2FluidStack fluid) {
        return fluid != null && fluid.hasExactFluid(Ic2Fluids.CONSTRUCTION_FOAM.still);
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.m_21120_(hand);
        if (!level.f_46443_ && IC2.keyboard.isModeSwitchKeyDown(player)) {
            boolean single = !isSingleMode(stack);
            stack.m_41784_().m_128379_(MODE_TAG, single);
            player.m_5661_(Component.m_237110_(
                    "ic2.tooltip.mode",
                    Component.m_237115_(single
                            ? "ic2.tooltip.mode.single"
                            : "ic2.tooltip.mode.normal")), true);
        }
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }

    @Override
    public InteractionResult m_6225_(UseOnContext context) {
        Player player = context.m_43723_();
        if (player == null || IC2.keyboard.isModeSwitchKeyDown(player)) {
            return InteractionResult.PASS;
        }

        Level level = context.m_43725_();
        BlockPos clicked = context.m_8083_();
        if (!level.f_46443_ && LiquidUtil.drainWorldFluidBlockToContainer(
                level, clicked, player, context.m_43724_())) {
            return InteractionResult.SUCCESS;
        }
        if (level.f_46443_) {
            return InteractionResult.SUCCESS;
        }

        ItemStack sprayer = context.m_43722_();
        ItemStack pack = player.m_150109_().f_35975_.get(2);
        if (pack.m_41720_() != Ic2Items.CF_PACK) {
            pack = ItemStack.f_41583_;
        }

        int available = fluidBlocks(sprayer) + fluidBlocks(pack);
        if (available <= 0) {
            return InteractionResult.FAIL;
        }
        available = Math.min(available, isSingleMode(sprayer) ? 1 : 10);

        BlockState clickedState = level.m_8055_(clicked);
        Target target;
        BlockPos start;
        if (isScaffold(clickedState)) {
            target = Target.SCAFFOLD;
            start = clicked;
        } else if (isCable(clickedState)) {
            target = Target.CABLE;
            start = clicked;
        } else {
            target = Target.ANY;
            start = clicked.m_121945_(context.m_43719_());
        }

        var look = player.m_20154_();
        Direction excluded = Direction.m_122372_(
                (float) look.m_7096_(), (float) look.m_7098_(), (float) look.m_7094_()).m_122424_();
        int placed = spray(level, start, excluded, target, available);
        if (placed <= 0) {
            return InteractionResult.PASS;
        }

        int toDrain = placed * MB_PER_BLOCK;
        toDrain -= drain(pack, toDrain);
        if (toDrain > 0) {
            drain(sprayer, toDrain);
        }
        return InteractionResult.SUCCESS;
    }

    private int spray(Level level, BlockPos start, Direction excluded, Target target, int limit) {
        if (!canPlace(level, start, target)) {
            return 0;
        }
        Queue<BlockPos> pending = new ArrayDeque<>();
        Set<BlockPos> selected = new HashSet<>();
        pending.add(start);
        while (!pending.isEmpty() && selected.size() < limit) {
            BlockPos position = pending.poll();
            if (!canPlace(level, position, target) || !selected.add(position)) {
                continue;
            }
            for (Direction direction : Direction.values()) {
                if (direction != excluded) {
                    pending.add(position.m_121945_(direction));
                }
            }
        }

        int placed = 0;
        for (BlockPos position : selected) {
            BlockState state = level.m_8055_(position);
            BlockState replacement = replacementFor(state, target);
            if (replacement != null && level.m_46597_(position, replacement)) {
                if (target == Target.SCAFFOLD) {
                    Block.m_49840_(level, position, new ItemStack(state.m_60734_()));
                }
                placed++;
            }
        }
        return placed;
    }

    private static boolean canPlace(Level level, BlockPos position, Target target) {
        BlockState state = level.m_8055_(position);
        return switch (target) {
            case ANY -> state.m_60795_();
            case SCAFFOLD -> isScaffold(state);
            case CABLE -> isCable(state);
        };
    }

    private static BlockState replacementFor(BlockState state, Target target) {
        if (target == Target.CABLE && state.m_60734_() instanceof CableBlock cable) {
            return cable.getFoamCableBlock().m_49966_();
        }
        if (target == Target.SCAFFOLD) {
            boolean reinforced = state.m_60734_() == Ic2Blocks.REINFORCED_WOODEN_SCAFFOLD
                    || state.m_60734_() == Ic2Blocks.REINFORCED_IRON_SCAFFOLD;
            return Ic2Blocks.FOAM.m_49966_().m_61124_(
                    FoamBlock.typeProperty,
                    reinforced ? FoamBlock.FoamType.reinforced : FoamBlock.FoamType.normal);
        }
        return target == Target.ANY
                ? Ic2Blocks.FOAM.m_49966_().m_61124_(FoamBlock.typeProperty, FoamBlock.FoamType.normal)
                : null;
    }

    private static boolean isScaffold(BlockState state) {
        Block block = state.m_60734_();
        return block == Ic2Blocks.WOODEN_SCAFFOLD
                || block == Ic2Blocks.REINFORCED_WOODEN_SCAFFOLD
                || block == Ic2Blocks.IRON_SCAFFOLD
                || block == Ic2Blocks.REINFORCED_IRON_SCAFFOLD;
    }

    private static boolean isCable(BlockState state) {
        return state.m_60734_() instanceof CableBlock cable && !cable.isFoam();
    }

    private static int fluidBlocks(ItemStack stack) {
        if (stack.m_41619_()) {
            return 0;
        }
        Ic2FluidStack fluid = Ic2FluidStack.get(stack);
        return fluid == null || !fluid.hasExactFluid(Ic2Fluids.CONSTRUCTION_FOAM.still)
                ? 0 : fluid.getAmountMb() / MB_PER_BLOCK;
    }

    private static int drain(ItemStack stack, int amount) {
        if (stack.m_41619_() || !(stack.m_41720_() instanceof Ic2FluidItem fluidItem)) {
            return 0;
        }
        Ic2FluidStack drained = fluidItem.drainMb(stack, amount, false, null);
        return drained == null ? 0 : drained.getAmountMb();
    }

    private static boolean isSingleMode(ItemStack stack) {
        return stack.m_41783_() != null && stack.m_41783_().m_128471_(MODE_TAG);
    }

    @Override
    public void m_6787_(CreativeModeTab tab, NonNullList<ItemStack> items) {
        if (!m_220152_(tab)) {
            return;
        }
        ItemStack filled = new ItemStack(this);
        fillMb(filled, Ic2FluidStack.create(Ic2Fluids.CONSTRUCTION_FOAM.still, CAPACITY_MB), false, null);
        items.add(filled);
        items.add(new ItemStack(this));
    }

    @Override
    public void m_7373_(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        Ic2FluidStack fluid = Ic2FluidStack.get(stack);
        int amount = fluid == null ? 0 : fluid.getAmountMb();
        tooltip.add(Component.m_237113_(amount + " / " + CAPACITY_MB + " mB"));
        tooltip.add(Component.m_237110_(
                "ic2.tooltip.mode",
                Component.m_237115_(isSingleMode(stack)
                        ? "ic2.tooltip.mode.single"
                        : "ic2.tooltip.mode.normal")));
    }

    @Override
    public boolean canBeStoredInToolbox(ItemStack stack) {
        return true;
    }
}
