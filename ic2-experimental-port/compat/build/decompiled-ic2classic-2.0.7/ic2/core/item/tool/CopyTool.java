/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
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
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.item.tool;

import ic2.api.tiles.ICopyableSettings;
import ic2.core.IC2;
import ic2.core.item.base.IC2SimpleItem;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.platform.player.KeyHelper;
import ic2.core.utils.tooltips.ToolTipHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

public class CopyTool
extends IC2SimpleItem {
    public CopyTool() {
        super("copy_tool", new PropertiesBuilder().maxStackSize(1), "tools", "copy_tool");
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        super.addToolTip(stack, player, type, helper);
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.ALT_KEY, "tooltip.item.ic2.copy_tool.toClear", new Object[0]));
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.BLOCK_CLICK, "tooltip.item.ic2.copy_tool.toApply", new Object[0]));
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.BLOCK_CLICK, KeyHelper.SNEAK_KEY, "tooltip.item.ic2.copy_tool.toCopy", new Object[0]));
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand hand) {
        if (IC2.KEYBOARD.isAltKeyDown(player)) {
            ItemStack stack = player.m_21120_(hand);
            stack.m_41751_(null);
            return InteractionResultHolder.m_19090_((Object)stack);
        }
        return super.m_7203_(level, player, hand);
    }

    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        BlockPos pos;
        Level level = context.m_43725_();
        BlockEntity entity = level.m_7702_(pos = context.m_8083_());
        if (entity instanceof ICopyableSettings) {
            ICopyableSettings settings = (ICopyableSettings)entity;
            if (context.m_7078_()) {
                CompoundTag tag = new CompoundTag();
                settings.saveSettings(tag);
                stack.m_41700_(ForgeRegistries.BLOCK_ENTITY_TYPES.getKey((Object)entity.m_58903_()).toString(), (Tag)tag);
                if (!level.m_5776_()) {
                    context.m_43723_().m_213846_((Component)this.translate("tooltip.item.ic2.copy_tool.copied"));
                }
                return InteractionResult.SUCCESS;
            }
            String id = ForgeRegistries.BLOCK_ENTITY_TYPES.getKey((Object)entity.m_58903_()).toString();
            if (stack.m_41783_() == null || !stack.m_41783_().m_128441_(id)) {
                if (!level.m_5776_()) {
                    context.m_43723_().m_213846_((Component)this.translate("tooltip.item.ic2.copy_tool.nothing_to_applied"));
                }
                return InteractionResult.FAIL;
            }
            settings.loadSettings(stack.m_41737_(id));
            if (!level.m_5776_()) {
                context.m_43723_().m_213846_((Component)this.translate("tooltip.item.ic2.copy_tool.applied"));
            }
            return InteractionResult.SUCCESS;
        }
        if (context.m_43723_() != null) {
            if (!level.m_5776_()) {
                context.m_43723_().m_213846_((Component)this.translate(context.m_7078_() ? "tooltip.item.ic2.copy_tool.nothing_to_copy" : "tooltip.item.ic2.copy_tool.nothing_to_applied"));
            }
            return InteractionResult.FAIL;
        }
        return InteractionResult.PASS;
    }
}

