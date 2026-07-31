/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.Component$Serializer
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.server.ServerLifecycleHooks
 */
package ic2.core.item.tool.infos;

import ic2.api.items.IDisplayProvider;
import ic2.api.network.item.INetworkItemBufferEvent;
import ic2.api.network.item.INetworkItemEvent;
import ic2.api.tiles.display.IDisplayInfo;
import ic2.api.tiles.display.impl.ProgressDisplayInfo;
import ic2.api.tiles.display.impl.StringDisplayInfo;
import ic2.core.IC2;
import ic2.core.inventory.base.IHasHeldGui;
import ic2.core.inventory.base.IHasHeldSlotInventory;
import ic2.core.inventory.base.IPortableInventory;
import ic2.core.item.base.IC2SimpleItem;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.inv.container.CardContainer;
import ic2.core.item.inv.inventory.CardInventory;
import ic2.core.item.tool.infos.BaseTileInfoProvider;
import ic2.core.networking.buffers.data.StringDataBuffer;
import ic2.core.platform.player.KeyHelper;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.IItemModel;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.math.ColorUtils;
import ic2.core.utils.tooltips.ToolTipHelper;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.server.ServerLifecycleHooks;

public class LagCardItem
extends IC2SimpleItem
implements IDisplayProvider,
IItemModel,
IHasHeldSlotInventory,
INetworkItemEvent,
INetworkItemBufferEvent<StringDataBuffer> {
    public static final int FLAG_SHOW_NAME = 1;
    public static final int FLAG_SHOW_TEXT = 2;
    public static final int FLAG_SHOW_PROGRESS = 4;

    public LagCardItem() {
        super("lag_card", new PropertiesBuilder().maxStackSize(1), "tools/monitor", "active_lag_card");
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        if (this instanceof IHasHeldGui) {
            helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.RIGHT_CLICK, KeyHelper.SIDE_INV_KEY, "tooltip.ic2.open_item_inventory", new Object[0]));
        }
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.RIGHT_CLICK, KeyHelper.SNEAK_KEY, "tooltip.item.ic2.card.init", new Object[0]));
    }

    protected int createDefaultFlags() {
        return 7;
    }

    @Override
    public int getModelIndexForStack(ItemStack stack, LivingEntity entity) {
        return StackUtil.getNbtData(stack).m_128465_("colors").length != 0 ? 1 : 0;
    }

    @Override
    public List<ItemStack> getModelTypes() {
        return ObjectLists.singleton((Object)new ItemStack((ItemLike)this));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSprite(ItemStack input) {
        return IC2Textures.getMappedEntriesItemIC2(this.textureFolder).get("inactive_lag_card");
    }

    @Override
    public void provideInfo(ItemStack stack, Consumer<IDisplayInfo> infos) {
        int flags = this.getFlags(stack);
        if ((flags & 1) != 0) {
            infos.accept(new StringDisplayInfo(() -> this.getTileName(stack), () -> true));
        }
        if ((flags & 2) != 0) {
            infos.accept(new StringDisplayInfo(new LagTextProvider(LagCardItem.getColor(stack, 1, -1)), () -> true));
        }
        if ((flags & 4) != 0) {
            infos.accept(new ProgressDisplayInfo(new LagProvider(LagCardItem.getColor(stack, 2, -1)), () -> true));
        }
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
        if (IC2.KEYBOARD.isSideInventoryKeyDown(player)) {
            ItemStack stack = player.m_21120_(hand);
            IC2.PLATFORM.launchGui(player, hand, Direction.NORTH, this.getInventory(player, hand, stack));
            return InteractionResultHolder.m_19090_((Object)stack);
        }
        return player.m_6144_() ? InteractionResultHolder.m_19090_((Object)this.init(player.m_21120_(hand))) : super.m_7203_(world, player, hand);
    }

    protected ItemStack init(ItemStack stack) {
        CompoundTag data = stack.m_41784_();
        if (!data.m_128441_("flags")) {
            data.m_128405_("flags", this.createDefaultFlags());
        }
        if (!data.m_128441_("colors")) {
            data.m_128385_("colors", new int[3]);
        }
        data.m_128359_("base_name", Component.Serializer.m_130703_((Component)this.string("Server Lag")));
        return stack;
    }

    public int getFlags(ItemStack stack) {
        return NBTUtils.getInt(StackUtil.getNbtData(stack), "flags", this.createDefaultFlags());
    }

    public Component getTileName(ItemStack stack) {
        CompoundTag data = StackUtil.getNbtData(stack);
        return LagCardItem.withColor(stack, 0, data.m_128441_("name") ? Component.Serializer.m_130701_((String)data.m_128461_("name")) : (data.m_128441_("base_name") ? Component.Serializer.m_130701_((String)data.m_128461_("base_name")) : this.string("Error")));
    }

    public static int getColor(ItemStack stack, int colorIndex, int defaultValue) {
        int[] colors = StackUtil.getNbtData(stack).m_128465_("colors");
        return colors == null || colors.length <= colorIndex ? defaultValue : DyeColor.m_41053_((int)colors[colorIndex]).m_41071_();
    }

    public static Component withColor(ItemStack stack, int index, MutableComponent comp) {
        return comp.m_130948_(ColorUtils.toColor(LagCardItem.getColor(stack, index, -1)));
    }

    protected List<BaseTileInfoProvider.SettingEntry> createSettings() {
        ObjectList entries = CollectionUtils.createList();
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(new ItemStack((ItemLike)Items.f_42614_), "gui.ic2.cards.show_name", 0, BaseTileInfoProvider.SettingType.SIMPLE, 7, 50));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(new ItemStack((ItemLike)Items.f_42517_), "gui.ic2.lag_card.lag", 1, BaseTileInfoProvider.SettingType.SIMPLE, 7, 67));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(new ItemStack((ItemLike)Items.f_41860_), "gui.ic2.lag_card.lag_progress", 2, BaseTileInfoProvider.SettingType.SIMPLE, 7, 84));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(null, "gui.ic2.cards.text_color", 0, BaseTileInfoProvider.SettingType.COLOR, 24, 50));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(null, "gui.ic2.cards.text_color", 1, BaseTileInfoProvider.SettingType.COLOR, 24, 67));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(null, "gui.ic2.cards.text_color", 2, BaseTileInfoProvider.SettingType.COLOR, 24, 84));
        return entries;
    }

    @Override
    public IPortableInventory getInventory(Player player, InteractionHand hand, ItemStack stack) {
        return new CardInventory(player, this, stack, null, 3, this.createSettings()).load(stack);
    }

    @Override
    public IPortableInventory getInventory(Player player, ItemStack stack, Slot slot) {
        return new CardInventory(player, this, stack, slot, 3, this.createSettings()).load(stack);
    }

    @Override
    public void onEventReceived(ItemStack stack, Player player, int key, int value, Dist target) {
        if (target.isDedicatedServer()) {
            CardContainer container = (CardContainer)this.getContainer(player, CardContainer.class);
            if (container == null) {
                return;
            }
            ((CardInventory)container.getHolder()).onDataReceived(key, value);
        }
    }

    @Override
    public void onDataBufferReceived(ItemStack stack, Player player, String id, StringDataBuffer buffer, Dist targetSide) {
        if (!id.equalsIgnoreCase("customName") || targetSide.isClient()) {
            return;
        }
        CardContainer container = (CardContainer)this.getContainer(player, CardContainer.class);
        if (container == null) {
            return;
        }
        ((CardInventory)container.getHolder()).updateName(buffer.getValue());
    }

    private static long getAverageLag() {
        long total = 0L;
        long[] data = ServerLifecycleHooks.getCurrentServer().f_129748_;
        int i = 0;
        int m = data.length;
        while (i < m) {
            total += data[i++];
        }
        return total /= (long)data.length;
    }

    public static class LagTextProvider
    implements Supplier<Component> {
        int color;

        public LagTextProvider(int color) {
            this.color = color;
        }

        @Override
        public Component get() {
            long lag = LagCardItem.getAverageLag() / 1000L;
            long ms = lag / 1000L;
            return Component.m_237113_((String)(ms + "ms / 50ms, (" + lag + "\u00b5s, TPS: " + Math.min(20, Mth.m_14165_((double)((1.0 - (double)lag / 1000000.0) * 20.0))) + ")")).m_130948_(ColorUtils.toColor(this.color));
        }
    }

    private static class LagProvider
    implements Supplier<ProgressDisplayInfo.Progress> {
        private static final double MAX = 5.0E7;
        int color;

        public LagProvider(int color) {
            this.color = color;
        }

        @Override
        public ProgressDisplayInfo.Progress get() {
            return new ProgressDisplayInfo.Progress((double)LagCardItem.getAverageLag() / 5.0E7, this.color, ColorUtils.darker(this.color));
        }
    }
}

