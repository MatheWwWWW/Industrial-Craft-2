/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.upgrades.inv;

import ic2.core.IC2;
import ic2.core.item.base.IC2SimpleItem;
import ic2.core.item.base.features.IHandlerItem;
import ic2.core.platform.player.PlayerHandler;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.tooltips.ToolTipHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemLimiterUpgradeItem
extends IC2SimpleItem
implements IHandlerItem {
    public ItemLimiterUpgradeItem() {
        super("item_limiter", "upgrades/inventory", "item_limiter");
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        helper.addSimpleToolTip("Max Stacksize: " + ItemLimiterUpgradeItem.getMax(stack), new Object[0]);
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.m_21120_(hand);
        if (IC2.PLATFORM.isSimulating() && player.m_6144_()) {
            ItemLimiterUpgradeItem.setMax(stack, Mth.m_14045_((int)(ItemLimiterUpgradeItem.getMax(stack) + (PlayerHandler.getHandler((Player)player).altKeyDown ? -1 : 1)), (int)0, (int)512));
            return InteractionResultHolder.m_19090_((Object)stack);
        }
        return InteractionResultHolder.m_19098_((Object)stack);
    }

    @Override
    public void handleInventory(ItemStack stack, IHandlerItem.IConfigurableInventory inv) {
        inv.setDefaultMaxStackSize(ItemLimiterUpgradeItem.getMax(stack));
    }

    public static int getMax(ItemStack stack) {
        CompoundTag data = StackUtil.getNbtData(stack);
        return data.m_128441_("max") ? data.m_128451_("max") : 64;
    }

    public static void setMax(ItemStack stack, int max) {
        stack.m_41784_().m_128405_("max", max);
    }
}

