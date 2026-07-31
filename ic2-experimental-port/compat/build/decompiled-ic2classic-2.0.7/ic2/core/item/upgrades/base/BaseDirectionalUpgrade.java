/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.common.capabilities.Capability
 *  net.minecraftforge.common.capabilities.ICapabilityProvider
 */
package ic2.core.item.upgrades.base;

import ic2.api.items.IUpgradeItem;
import ic2.api.util.DirectionList;
import ic2.core.inventory.base.IHasHeldGui;
import ic2.core.item.upgrades.base.BaseUpgradeItem;
import ic2.core.platform.player.KeyHelper;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.tooltips.ToolTipHelper;
import java.util.List;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public abstract class BaseDirectionalUpgrade
extends BaseUpgradeItem.SimpleUpgradeItem {
    public static final String FACING_TAG = "facing";

    public BaseDirectionalUpgrade(String itemName) {
        super(itemName);
        this.functions.add(IUpgradeItem.Functions.TICK);
        this.functions.add(IUpgradeItem.Functions.RECIPE);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        if (this instanceof IHasHeldGui) {
            helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.RIGHT_CLICK, KeyHelper.SIDE_INV_KEY, "tooltip.ic2.open_item_inventory", new Object[0]));
        }
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.BLOCK_CLICK, "tooltip.item.ic2.upgrade.set_side", new Object[0]));
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.BLOCK_CLICK, KeyHelper.SNEAK_KEY, "tooltip.item.ic2.upgrade.set_opposite_side", new Object[0]));
    }

    public void m_7373_(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        Direction facing = this.getFacing(stack);
        if (facing != null) {
            tooltip.add((Component)this.translate("tooltip.item.ic2.direction", new Object[]{DirectionList.getName(facing)}));
        }
    }

    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        if (context.m_43723_() != null) {
            this.setFacing(stack, context.m_7078_() ? context.m_43719_().m_122424_() : context.m_43719_());
            if (!context.m_43725_().f_46443_) {
                context.m_43723_().m_5661_((Component)this.translate("tooltip.item.ic2.set_direction", new Object[]{DirectionList.getName(this.getFacing(stack))}), false);
            }
            return InteractionResult.SUCCESS;
        }
        return super.onItemUseFirst(stack, context);
    }

    public void setFacing(ItemStack stack, Direction facing) {
        if (stack.m_41619_()) {
            return;
        }
        if (facing == null) {
            if (stack.m_41783_() != null) {
                stack.m_41783_().m_128473_(FACING_TAG);
                if (stack.m_41783_().m_128456_()) {
                    stack.m_41751_(null);
                }
            }
            return;
        }
        stack.m_41784_().m_128344_(FACING_TAG, (byte)facing.m_122411_());
    }

    public Direction getFacing(ItemStack stack) {
        CompoundTag nbt = StackUtil.getNbtData(stack);
        return nbt.m_128441_(FACING_TAG) ? Direction.m_122376_((int)nbt.m_128451_(FACING_TAG)) : null;
    }

    public <T> T getCapability(Object obj, Direction side, Capability<T> cap) {
        return (T)(obj instanceof ICapabilityProvider ? ((ICapabilityProvider)obj).getCapability(cap, side).orElse(null) : null);
    }
}

