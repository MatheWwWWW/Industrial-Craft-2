/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Vec3i
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.tool;

import ic2.core.IC2;
import ic2.core.block.transport.item.TubeAction;
import ic2.core.block.transport.item.TubeTileEntity;
import ic2.core.item.base.IC2SimpleItem;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.platform.player.KeyHelper;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.tooltips.ToolTipHelper;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TubeTool
extends IC2SimpleItem {
    public TubeTool() {
        super("tube_tool", new PropertiesBuilder().maxStackSize(1), "tools", "tube_tool");
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.RIGHT_CLICK, KeyHelper.MODE_KEY, "tooltip.item.ic2.tube_tool.switch_mode", new Object[0]));
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.BLOCK_CLICK, KeyHelper.SNEAK_KEY, "tooltip.item.ic2.tube_tool.use", new Object[0]));
        helper.addSimpleToolTip(this.getAction(stack).getName());
        helper.addSimpleToolTip(this.getAction(stack).getDesc());
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
        if (IC2.KEYBOARD.isModeSwitchKeyDown(player)) {
            ItemStack stack = player.m_21120_(hand);
            CompoundTag data = stack.m_41784_();
            TubeAction action = TubeAction.getNextAction(data.m_128461_("action"));
            data.m_128359_("action", action.getId());
            if (IC2.PLATFORM.isSimulating()) {
                player.m_5661_((Component)action.getName(), false);
            }
            return InteractionResultHolder.m_19090_((Object)stack);
        }
        return super.m_7203_(world, player, hand);
    }

    public InteractionResult m_6225_(UseOnContext context) {
        BlockEntity tile;
        if (context.m_43723_() != null && context.m_7078_() && (tile = context.m_43723_().f_19853_.m_7702_(context.m_8083_())) instanceof TubeTileEntity && ((TubeTileEntity)tile).doTubeAction(context.m_43719_(), context.m_43720_().m_82546_(Vec3.m_82528_((Vec3i)context.m_8083_())), context.m_43723_(), this.getAction(context.m_43722_()))) {
            return InteractionResult.SUCCESS;
        }
        return super.m_6225_(context);
    }

    public TubeAction getAction(ItemStack stack) {
        return TubeAction.byIdOrDefault(StackUtil.getNbtData(stack).m_128461_("action"));
    }
}

