/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.tool.infos;

import ic2.api.items.IDisplayProvider;
import ic2.api.network.item.INetworkItemBufferEvent;
import ic2.api.network.item.INetworkItemEvent;
import ic2.api.tiles.display.IDisplayInfo;
import ic2.api.tiles.display.impl.StringDisplayInfo;
import ic2.core.IC2;
import ic2.core.inventory.base.IHasHeldGui;
import ic2.core.inventory.base.IHasHeldSlotInventory;
import ic2.core.inventory.base.IPortableInventory;
import ic2.core.item.base.IC2SimpleItem;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.inv.container.TextCardContainer;
import ic2.core.item.inv.inventory.TextCardInventory;
import ic2.core.networking.buffers.data.StringDataBuffer;
import ic2.core.platform.player.KeyHelper;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.math.ColorUtils;
import ic2.core.utils.tooltips.ToolTipHelper;
import java.util.function.Consumer;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TextCardItem
extends IC2SimpleItem
implements IDisplayProvider,
INetworkItemEvent,
INetworkItemBufferEvent<StringDataBuffer>,
IHasHeldSlotInventory {
    public TextCardItem() {
        super("text_card", new PropertiesBuilder().maxStackSize(1), "tools/monitor", "active_text_card");
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        if (this instanceof IHasHeldGui) {
            helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.RIGHT_CLICK, KeyHelper.SIDE_INV_KEY, "tooltip.ic2.open_item_inventory", new Object[0]));
        }
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
        if (IC2.KEYBOARD.isSideInventoryKeyDown(player)) {
            ItemStack stack = player.m_21120_(hand);
            IC2.PLATFORM.launchGui(player, hand, Direction.NORTH, this.getInventory(player, hand, stack));
            return InteractionResultHolder.m_19090_((Object)stack);
        }
        return InteractionResultHolder.m_19098_((Object)player.m_21120_(hand));
    }

    @Override
    public IPortableInventory getInventory(Player player, InteractionHand hand, ItemStack stack) {
        return new TextCardInventory(player, this, stack, null).load(stack);
    }

    @Override
    public IPortableInventory getInventory(Player player, ItemStack stack, Slot slot) {
        return new TextCardInventory(player, this, stack, slot).load(stack);
    }

    @Override
    public void onEventReceived(ItemStack stack, Player player, int key, int value, Dist target) {
        if (target.isDedicatedServer()) {
            TextCardContainer container = (TextCardContainer)this.getContainer(player, TextCardContainer.class);
            if (container == null) {
                return;
            }
            ((TextCardInventory)container.getHolder()).onDataReceived(key, value);
        }
    }

    @Override
    public void onDataBufferReceived(ItemStack stack, Player player, String id, StringDataBuffer buffer, Dist targetSide) {
        if (!id.equalsIgnoreCase("text") || targetSide.isClient()) {
            return;
        }
        TextCardContainer container = (TextCardContainer)this.getContainer(player, TextCardContainer.class);
        if (container == null) {
            return;
        }
        ((TextCardInventory)container.getHolder()).onDataReceived(buffer);
    }

    @Override
    public void provideInfo(ItemStack stack, Consumer<IDisplayInfo> infos) {
        infos.accept(new StringDisplayInfo(() -> this.getText(stack), () -> true));
    }

    private Component getText(ItemStack stack) {
        CompoundTag data = StackUtil.getNbtData(stack);
        return Component.m_237113_((String)data.m_128461_("text")).m_130948_(ColorUtils.toColor(NBTUtils.getEnum(data, "color", DyeColor.class, DyeColor.WHITE).m_41071_()));
    }
}

