/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Registry
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.Component$Serializer
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
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
 *  org.apache.commons.lang3.time.DurationFormatUtils
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
import ic2.core.item.inv.container.CardContainer;
import ic2.core.item.inv.inventory.CardInventory;
import ic2.core.item.tool.infos.BaseTileInfoProvider;
import ic2.core.item.tool.infos.components.TimeDisplayInfo;
import ic2.core.networking.buffers.data.StringDataBuffer;
import ic2.core.platform.player.KeyHelper;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.IItemModel;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.SanityHelper;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.math.ColorUtils;
import ic2.core.utils.tooltips.ToolTipHelper;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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
import org.apache.commons.lang3.time.DurationFormatUtils;

public class TimeCardItem
extends IC2SimpleItem
implements IDisplayProvider,
IItemModel,
INetworkItemEvent,
INetworkItemBufferEvent<StringDataBuffer>,
IHasHeldSlotInventory {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    public static final int FLAG_SHOW_NAME = 1;
    public static final int FLAG_DAY_TIME = 2;
    public static final int FLAG_SAVE_TIME = 4;
    public static final int FLAG_SERVER_TIME = 8;
    public static final int FLAG_REAL_TIME = 16;
    public static final int FLAG_ONLY_TIME_DAY = 32;
    public static final int FLAG_ONLY_TIME_SAVE = 64;
    public static final int FLAG_ONLY_TIME_SERVER = 128;
    public static final int FLAG_ONLY_TIME_REAL = 256;

    public TimeCardItem() {
        super("time_card", new PropertiesBuilder().maxStackSize(1), "tools/monitor", "active_time_card");
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
        return 31;
    }

    @Override
    public int getModelIndexForStack(ItemStack stack, LivingEntity entity) {
        return StackUtil.getNbtData(stack).m_128441_("world") ? 1 : 0;
    }

    @Override
    public List<ItemStack> getModelTypes() {
        return ObjectLists.singleton((Object)new ItemStack((ItemLike)this));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSprite(ItemStack input) {
        return IC2Textures.getMappedEntriesItemIC2(this.textureFolder).get("inactive_time_card");
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
        if (player.m_6144_()) {
            this.storeDimension(player.m_21120_(hand), world);
            return InteractionResultHolder.m_19090_((Object)player.m_21120_(hand));
        }
        if (IC2.KEYBOARD.isSideInventoryKeyDown(player)) {
            ItemStack stack = player.m_21120_(hand);
            IC2.PLATFORM.launchGui(player, hand, Direction.NORTH, this.getInventory(player, hand, stack));
            return InteractionResultHolder.m_19090_((Object)stack);
        }
        return InteractionResultHolder.m_19098_((Object)player.m_21120_(hand));
    }

    @Override
    public void provideInfo(ItemStack stack, Consumer<IDisplayInfo> infos) {
        Level world = this.getWorld(stack);
        if (world != null) {
            int flags = this.getFlags(stack);
            if ((flags & 1) != 0) {
                infos.accept(new StringDisplayInfo(() -> this.getTileName(stack), () -> true));
            }
            if ((flags & 2) != 0) {
                infos.accept(new StringDisplayInfo(new GameTimeProvider(world, TimeCardItem.getColor(stack, 1, -1), (flags & 0x20) != 0, true), () -> true));
            }
            if ((flags & 4) != 0) {
                infos.accept(new StringDisplayInfo(new GameTimeProvider(world, TimeCardItem.getColor(stack, 2, -1), (flags & 0x40) != 0, false), () -> true));
            }
            if ((flags & 8) != 0) {
                infos.accept(new StringDisplayInfo(() -> this.withColor(stack, 3, this.getServerTime((flags & 0x80) != 0)), () -> true));
            }
            if ((flags & 0x10) != 0) {
                infos.accept(new TimeDisplayInfo(TimeCardItem.getColor(stack, 4, -1), (flags & 0x100) != 0));
            }
            return;
        }
        infos.accept(new StringDisplayInfo(() -> this.translate("tooltip.item.ic2.time_card.no_world"), () -> true));
    }

    protected List<BaseTileInfoProvider.SettingEntry> createSettings() {
        ObjectList entries = CollectionUtils.createList();
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(new ItemStack((ItemLike)Items.f_42614_), "gui.ic2.cards.show_name", 0, BaseTileInfoProvider.SettingType.SIMPLE, 7, 50));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(new ItemStack((ItemLike)Items.f_42517_), "gui.ic2.lag_card.lag", 1, BaseTileInfoProvider.SettingType.SIMPLE, 7, 67));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(new ItemStack((ItemLike)Items.f_41860_), "gui.ic2.lag_card.lag_progress", 2, BaseTileInfoProvider.SettingType.SIMPLE, 7, 84));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(new ItemStack((ItemLike)Items.f_42524_), "gui.ic2.lag_card.lag_progress", 3, BaseTileInfoProvider.SettingType.SIMPLE, 80, 50));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(new ItemStack((ItemLike)Items.f_42524_), "gui.ic2.lag_card.lag_progress", 4, BaseTileInfoProvider.SettingType.SIMPLE, 80, 67));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(null, "gui.ic2.cards.text_color", 0, BaseTileInfoProvider.SettingType.COLOR, 24, 50));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(null, "gui.ic2.cards.text_color", 1, BaseTileInfoProvider.SettingType.COLOR, 24, 67));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(null, "gui.ic2.cards.text_color", 2, BaseTileInfoProvider.SettingType.COLOR, 24, 84));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(null, "gui.ic2.cards.text_color", 3, BaseTileInfoProvider.SettingType.COLOR, 97, 50));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(null, "gui.ic2.cards.text_color", 4, BaseTileInfoProvider.SettingType.COLOR, 97, 67));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(this.string("P"), "gui.ic2.time_card.disable_prefix", 5, BaseTileInfoProvider.SettingType.TEXT, 41, 67));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(this.string("P"), "gui.ic2.time_card.disable_prefix", 6, BaseTileInfoProvider.SettingType.TEXT, 41, 84));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(this.string("P"), "gui.ic2.time_card.disable_prefix", 7, BaseTileInfoProvider.SettingType.TEXT, 114, 50));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(this.string("P"), "gui.ic2.time_card.disable_prefix", 8, BaseTileInfoProvider.SettingType.TEXT, 114, 67));
        return entries;
    }

    @Override
    public IPortableInventory getInventory(Player player, InteractionHand hand, ItemStack stack) {
        return new CardInventory(player, this, stack, null, 5, this.createSettings()).load(stack);
    }

    @Override
    public IPortableInventory getInventory(Player player, ItemStack stack, Slot slot) {
        return new CardInventory(player, this, stack, slot, 5, this.createSettings()).load(stack);
    }

    public Level getWorld(ItemStack stack) {
        CompoundTag data = StackUtil.getNbtData(stack);
        return data.m_128441_("world") ? IC2.PLATFORM.getWorld((ResourceKey<Level>)ResourceKey.m_135785_((ResourceKey)Registry.f_122819_, (ResourceLocation)ResourceLocation.m_135820_((String)data.m_128461_("world")))) : null;
    }

    public static int getColor(ItemStack stack, int colorIndex, int defaultValue) {
        int[] colors = StackUtil.getNbtData(stack).m_128465_("colors");
        return colors == null || colors.length <= colorIndex ? defaultValue : DyeColor.m_41053_((int)colors[colorIndex]).m_41071_();
    }

    private MutableComponent getServerTime(boolean cut) {
        if (cut) {
            return this.string(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        }
        return this.translate("tooltip.item.ic2.time_card.server", new Object[]{LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))});
    }

    public Component withColor(ItemStack stack, int index, MutableComponent comp) {
        return comp.m_130948_(ColorUtils.toColor(TimeCardItem.getColor(stack, index, -1)));
    }

    public Component getTileName(ItemStack stack) {
        CompoundTag data = StackUtil.getNbtData(stack);
        return this.withColor(stack, 0, data.m_128441_("name") ? Component.Serializer.m_130701_((String)data.m_128461_("name")) : (data.m_128441_("base_name") ? Component.Serializer.m_130701_((String)data.m_128461_("base_name")) : this.string("Error")));
    }

    public int getFlags(ItemStack stack) {
        return StackUtil.getNbtData(stack).m_128451_("flags");
    }

    public void storeDimension(ItemStack stack, Level world) {
        CompoundTag data = stack.m_41784_();
        data.m_128359_("world", world.m_46472_().m_135782_().toString());
        if (!data.m_128441_("flags")) {
            data.m_128405_("flags", this.createDefaultFlags());
        }
        if (!data.m_128441_("colors")) {
            data.m_128385_("colors", new int[5]);
        }
        data.m_128359_("base_name", Component.Serializer.m_130703_((Component)this.string(SanityHelper.toPascalCase(world.m_46472_().m_135782_().m_135815_()))));
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

    public static class GameTimeProvider
    implements Supplier<Component> {
        Level world;
        int color;
        boolean cut;
        boolean dayTime;

        public GameTimeProvider(Level world, int color, boolean cut, boolean dayTime) {
            this.world = world;
            this.color = color;
            this.cut = cut;
            this.dayTime = dayTime;
        }

        @Override
        public Component get() {
            if (this.dayTime) {
                if (this.cut) {
                    return Component.m_237113_((String)this.format()).m_130948_(ColorUtils.toColor(this.color));
                }
                return Component.m_237110_((String)"tooltip.item.ic2.time_card.day", (Object[])new Object[]{this.format()}).m_130948_(ColorUtils.toColor(this.color));
            }
            if (this.cut) {
                return Component.m_237113_((String)this.format()).m_130948_(ColorUtils.toColor(this.color));
            }
            return Component.m_237110_((String)"tooltip.item.ic2.time_card.game", (Object[])new Object[]{this.format()}).m_130948_(ColorUtils.toColor(this.color));
        }

        public String format() {
            return DurationFormatUtils.formatDuration((long)this.getTime(), (String)(this.dayTime ? "HH:mm" : "HH:mm:ss"));
        }

        public long getTime() {
            if (this.dayTime) {
                return Duration.ofSeconds((long)((double)((this.world.m_46468_() + 6000L) % 24000L) / 24000.0 * 86400.0)).toMillis();
            }
            return this.world.m_46467_() * 50L;
        }
    }
}

