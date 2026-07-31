package ru.mot.ic2exfidelity.gravisuit;

import ic2.core.IC2;
import ic2.core.item.ElectricItemTooltipHandler;
import ic2.api.item.ElectricItem;
import ic2.core.item.tool.ItemTreetap;
import ic2.core.item.tool.ItemToolWrenchElectric;
import ic2.core.ref.Ic2Items;
import ic2.core.ref.Ic2Blocks;
import ic2.core.util.StackUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import ru.mot.ic2exfidelity.integration.RestoredLegacyContent;

/** Gravisuit 2.2 wrench/hoe/treetap/screwdriver mode tool. */
public final class LegacyGravitool extends ItemToolWrenchElectric {
    private static final String[] MODE_KEYS = {
            "item_info.wrench", "item_info.hoe", "item_info.treetap", "item_info.screwdriver"
    };
    private static final String[] MESSAGE_KEYS = {
            "message.wrench", "message.hoe", "message.treetap", "message.screwdriver"
    };

    public LegacyGravitool(Item.Properties properties) {
        super(properties);
        maxCharge = 50_000;
        transferLimit = 400;
        tier = 2;
    }

    public byte getMode(ItemStack stack) {
        return (byte) Math.floorMod(stack.m_41784_().m_128445_("mode"), 4);
    }

    /** Classic Gravitool inherits the 50 EU wrench-unit cost, not precision's 100. */
    @Override
    public boolean canTakeDamage(ItemStack stack, double amount) {
        return ElectricItem.manager.getCharge(stack) >= amount * 50.0;
    }

    @Override
    public boolean consumeEnergy(ItemStack stack, double amount, LivingEntity entity) {
        return ic2.core.item.tool.ItemElectricTool.consumeEnergy(
                stack, amount * 50.0, tier, entity);
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(
            Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.m_21120_(hand);
        if (IC2.keyboard.isModeSwitchKeyDown(player)) {
            byte mode = (byte) ((getMode(stack) + 1) % 4);
            if (!level.f_46443_) {
                stack.m_41784_().m_128344_("mode", mode);
                player.m_5661_(Component.m_237115_(MESSAGE_KEYS[mode]), false);
            }
            return InteractionResultHolder.m_19090_(stack);
        }
        return super.m_7203_(level, player, hand);
    }

    @Override
    public InteractionResult m_6225_(UseOnContext context) {
        return switch (getMode(context.m_43722_())) {
            case 1 -> RestoredLegacyContent.ELECTRIC_HOE.get().m_6225_(context);
            case 2 -> useTreetap(context);
            case 3 -> LegacyGravisuitRotationHelper.rotateBlock(
                            context.m_43725_(),
                            context.m_8083_(),
                            context.m_43723_() != null
                                    && context.m_43723_().m_6144_()
                                            != context.m_43719_().equals(net.minecraft.core.Direction.DOWN))
                    ? InteractionResult.SUCCESS
                    : InteractionResult.PASS;
            default -> super.m_6225_(context);
        };
    }

    private InteractionResult useTreetap(UseOnContext context) {
        ItemStack stack = context.m_43722_();
        if (!stack.m_41784_().m_128471_("inv_import")) {
            return Ic2Items.ELECTRIC_TREETAP.m_6225_(context);
        }
        net.minecraft.world.level.block.state.BlockState state =
                context.m_43725_().m_8055_(context.m_8083_());
        Player player = context.m_43723_();
        if (player == null || state.m_60734_() != Ic2Blocks.RUBBER_LOG
                || !ElectricItem.manager.canUse(stack, 50.0)) {
            return InteractionResult.PASS;
        }
        ArrayList<ItemStack> drops = new ArrayList<>();
        boolean extracted = ItemTreetap.attemptExtract(
                player, context.m_43725_(), context.m_8083_(),
                context.m_43719_(), state, drops, true);
        if (!extracted) {
            return InteractionResult.FAIL;
        }
        if (!context.m_43725_().f_46443_) {
            ElectricItem.manager.use(stack, 50.0, player);
            for (ItemStack drop : drops) {
                if (!player.m_150109_().m_36054_(drop)) {
                    StackUtil.dropAsEntity(context.m_43725_(), player.m_20183_(), drop);
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public float m_8102_(ItemStack stack, net.minecraft.world.level.block.state.BlockState state) {
        if (getMode(stack) == 1) {
            return RestoredLegacyContent.ELECTRIC_HOE.get().m_8102_(stack, state);
        }
        return super.m_8102_(stack, state);
    }

    public boolean canPerformAction(ItemStack stack, ToolAction action) {
        return getMode(stack) == 1 && ToolActions.DEFAULT_HOE_ACTIONS.contains(action);
    }

    @Override
    public void m_7373_(
            ItemStack stack,
            Level level,
            List<Component> tooltip,
            TooltipFlag flag) {
        ElectricItemTooltipHandler.addTooltip(stack, tooltip);
        tooltip.add(Component.m_237110_(
                "item_info.toolMode", Component.m_237115_(MODE_KEYS[getMode(stack)])));
        if (stack.m_41782_() && stack.m_41783_().m_128471_("inv_import")) {
            tooltip.add(Component.m_237115_("tooltip.item.ic2.electric_tree_tap.inv_import"));
        }
    }
}
