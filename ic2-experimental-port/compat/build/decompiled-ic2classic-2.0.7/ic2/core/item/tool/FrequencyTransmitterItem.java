/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
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
 */
package ic2.core.item.tool;

import ic2.api.tiles.teleporter.ITeleporterTarget;
import ic2.api.tiles.teleporter.TeleporterTarget;
import ic2.core.IC2;
import ic2.core.block.storage.tiles.tank.TeleportValveEntity;
import ic2.core.item.base.IC2Item;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.platform.player.KeyHelper;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.tooltips.ToolTipHelper;
import java.util.List;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
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

public class FrequencyTransmitterItem
extends IC2Item
implements ISimpleItemModel {
    public FrequencyTransmitterItem() {
        super("frequency_transmitter", new PropertiesBuilder().maxStackSize(1));
    }

    @OnlyIn(value=Dist.CLIENT)
    public void m_7373_(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        TeleporterTarget target = TeleporterTarget.read(StackUtil.getNbtData(stack).m_128469_("target"));
        if (target != null) {
            tooltip.add((Component)this.translate("tooltip.item.ic2.frequency_transmitter.pos", new Object[]{this.pos(target.getDimension(), target.getTargetPosition())}));
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        super.addToolTip(stack, player, type, helper);
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.ALT_KEY, "tooltip.item.ic2.frequency_transmitter.clear_key", new Object[0]));
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (IC2.PLATFORM.isSimulating() && IC2.KEYBOARD.isAltKeyDown(playerIn)) {
            ItemStack stack = playerIn.m_21120_(handIn);
            CompoundTag nbt = StackUtil.getNbtData(stack);
            if (nbt.m_128441_("target")) {
                nbt.m_128473_("target");
                playerIn.m_5661_((Component)this.translate("tooltip.item.ic2.frequency_transmitter.clear"), false);
                if (nbt.m_128456_()) {
                    stack.m_41751_(null);
                }
            }
            return InteractionResultHolder.m_19090_((Object)stack);
        }
        return super.m_7203_(worldIn, playerIn, handIn);
    }

    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        if (IC2.PLATFORM.isSimulating()) {
            BlockEntity tile = context.m_43725_().m_7702_(context.m_8083_());
            if (tile instanceof ITeleporterTarget) {
                BlockEntity targetTile;
                ITeleporterTarget receiver = (ITeleporterTarget)tile;
                CompoundTag nbt = stack.m_41784_();
                TeleporterTarget target = TeleporterTarget.read(nbt.m_128469_("target"));
                BlockEntity blockEntity = targetTile = target == null ? null : target.getTile();
                if (target != null && !(targetTile instanceof ITeleporterTarget)) {
                    target = null;
                }
                if (target == null) {
                    target = new TeleporterTarget(tile);
                    context.m_43723_().m_5661_((Component)this.translate("tooltip.item.ic2.frequency_transmitter.set"), false);
                } else if (target.isSame(tile)) {
                    context.m_43723_().m_5661_((Component)this.translate("tooltip.item.ic2.frequency_transmitter.same"), false);
                } else {
                    receiver.setTarget(target);
                    ITeleporterTarget targetInter = (ITeleporterTarget)targetTile;
                    TeleporterTarget other = new TeleporterTarget(tile);
                    if (!targetInter.hasTarget(other)) {
                        targetInter.setTarget(other);
                    }
                    context.m_43723_().m_5661_((Component)this.translate("tooltip.item.ic2.frequency_transmitter.applied"), false);
                }
                nbt.m_128365_("target", (Tag)target.write(new CompoundTag()));
                return InteractionResult.SUCCESS;
            }
            if (tile instanceof TeleportValveEntity) {
                BlockEntity targetTile;
                TeleportValveEntity valve = (TeleportValveEntity)tile;
                CompoundTag nbt = stack.m_41784_();
                TeleporterTarget target = TeleporterTarget.read(nbt.m_128469_("target"));
                BlockEntity blockEntity = targetTile = target == null ? null : target.getTile();
                if (target != null && !(targetTile instanceof TeleportValveEntity)) {
                    target = null;
                }
                if (target == null) {
                    target = new TeleporterTarget(tile);
                    context.m_43723_().m_5661_((Component)this.translate("tooltip.item.ic2.frequency_transmitter.valve"), false);
                } else if (target.isSame(tile)) {
                    context.m_43723_().m_5661_((Component)this.translate("tooltip.item.ic2.frequency_transmitter.same"), false);
                } else {
                    valve.setTarget(target);
                    TeleportValveEntity targetInter = (TeleportValveEntity)targetTile;
                    TeleporterTarget other = new TeleporterTarget(tile);
                    if (!targetInter.hasTarget(other)) {
                        targetInter.setTarget(other);
                    }
                    context.m_43723_().m_5661_((Component)this.translate("tooltip.item.ic2.frequency_transmitter.applied"), false);
                }
                nbt.m_128365_("target", (Tag)target.write(new CompoundTag()));
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItemIC2("tools").get("frequency_transmitter");
    }
}

