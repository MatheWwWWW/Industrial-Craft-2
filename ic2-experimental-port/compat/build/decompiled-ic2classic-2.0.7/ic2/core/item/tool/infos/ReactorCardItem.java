/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  org.apache.commons.lang3.time.DurationFormatUtils
 */
package ic2.core.item.tool.infos;

import ic2.api.energy.tile.IEnergySource;
import ic2.api.network.item.INetworkItemBufferEvent;
import ic2.api.network.item.INetworkItemEvent;
import ic2.api.reactor.IChamberReactor;
import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorChamber;
import ic2.api.reactor.IReactorComponent;
import ic2.api.reactor.IReactorPlannerComponent;
import ic2.api.reactor.ISteamReactor;
import ic2.api.tiles.display.IDisplayInfo;
import ic2.api.tiles.display.impl.ProgressDisplayInfo;
import ic2.api.tiles.display.impl.StringDisplayInfo;
import ic2.api.tiles.readers.IActivityProvider;
import ic2.core.inventory.base.IHasHeldSlotInventory;
import ic2.core.inventory.base.IPortableInventory;
import ic2.core.item.inv.inventory.ReactorCardInventory;
import ic2.core.item.reactor.ReactorUraniumRod;
import ic2.core.item.tool.infos.ActivityCardItem;
import ic2.core.item.tool.infos.BaseTileInfoProvider;
import ic2.core.networking.buffers.data.StringDataBuffer;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.math.ColorUtils;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.apache.commons.lang3.time.DurationFormatUtils;

public class ReactorCardItem
extends BaseTileInfoProvider
implements IHasHeldSlotInventory,
INetworkItemEvent,
INetworkItemBufferEvent<StringDataBuffer> {
    public static final int FLAG_SHOW_HEAT = 2;
    public static final int FLAG_SHOW_HEAT_PROGRESS = 4;
    public static final int FLAG_SHOW_STATE = 8;
    public static final int FLAG_SHOW_PRODUCTION = 16;
    public static final int FLAG_SHOW_RUNTIME = 32;
    public static final int FLAG_SHOW_RUNTIME_PROGRESS = 64;
    public static final int FLAG_SHOW_TIME_LEFT = 128;
    public static final int FLAG_SHOW_PART_HEALTH = 256;
    public static final int FLAG_SHOW_FULL_TEXT = 512;
    public static final int FLAG_SHOW_RUNTIME_PERCENT = 1024;

    public ReactorCardItem() {
        super("reactor_card", "tools/monitor", "reactor_card");
    }

    @Override
    protected int createDefaultFlags() {
        return 1023;
    }

    @Override
    protected int[] createDefaultColors() {
        int[] colors = new int[13];
        colors[2] = DyeColor.LIME.m_41060_();
        colors[9] = DyeColor.YELLOW.m_41060_();
        colors[10] = DyeColor.RED.m_41060_();
        colors[11] = DyeColor.LIME.m_41060_();
        colors[12] = DyeColor.RED.m_41060_();
        return colors;
    }

    @Override
    public void provideInfo(ItemStack stack, Consumer<IDisplayInfo> infos) {
        IReactor reactor;
        BlockEntity tile = this.getTileEntity(stack);
        IReactor iReactor = tile instanceof IReactor ? (IReactor)tile : (reactor = tile instanceof IReactorChamber ? ((IReactorChamber)tile).getReactor() : null);
        if (reactor != null) {
            int flags = this.getFlags(stack);
            BooleanSupplier alive = this.createAliveTester(reactor instanceof BlockEntity ? (BlockEntity)reactor : tile);
            if ((flags & 1) != 0) {
                infos.accept(new StringDisplayInfo(new BaseTileInfoProvider.NameProvider(this.getTileName(stack)), alive));
            }
            if ((flags & 2) != 0) {
                infos.accept(new StringDisplayInfo(() -> this.withColor(stack, 1, ReactorCardItem.createHeatText(stack, reactor, flags)), alive));
            }
            if ((flags & 4) != 0) {
                infos.accept(new ProgressDisplayInfo(() -> ReactorCardItem.createHeatProgress(stack, reactor), alive));
            }
            if ((flags & 8) != 0 && reactor instanceof IActivityProvider) {
                infos.accept(new StringDisplayInfo(new ActivityCardItem.InfoProvider((IActivityProvider)((Object)reactor), ReactorCardItem.getColor(stack, 11, ColorUtils.GREEN), ReactorCardItem.getColor(stack, 12, ColorUtils.RED), ReactorCardItem.getColor(stack, 3, -1)), alive));
            }
            if ((flags & 0x10) != 0) {
                infos.accept(new StringDisplayInfo(() -> this.withColor(stack, 4, ReactorCardItem.getProduction(reactor)), alive));
            }
            if ((flags & 0x20) != 0) {
                infos.accept(new StringDisplayInfo(() -> this.withColor(stack, 5, ReactorCardItem.getRuntime(reactor, flags)), alive));
            }
            if ((flags & 0x40) != 0) {
                infos.accept(new ProgressDisplayInfo(() -> ReactorCardItem.getRuntime(reactor, stack), alive));
            }
            if ((flags & 0x80) != 0) {
                infos.accept(new StringDisplayInfo(() -> this.withColor(stack, 7, ReactorCardItem.getUraniumLeft(reactor)), alive));
            }
            if ((flags & 0x100) != 0 && reactor instanceof IChamberReactor) {
                infos.accept(new StringDisplayInfo(() -> this.withColor(stack, 8, ReactorCardItem.getComponentHeat((IChamberReactor)reactor)), alive));
            }
            return;
        }
        infos.accept(this.createTileError(stack, tile));
    }

    @Override
    public IPortableInventory getInventory(Player player, InteractionHand hand, ItemStack stack) {
        return new ReactorCardInventory(player, this, stack, null, this.createDefaultColors().length, this.createSettings()).load(stack);
    }

    @Override
    public IPortableInventory getInventory(Player player, ItemStack stack, Slot slot) {
        return new ReactorCardInventory(player, this, stack, slot, this.createDefaultColors().length, this.createSettings()).load(stack);
    }

    @Override
    protected List<BaseTileInfoProvider.SettingEntry> createSettings() {
        ObjectList entries = CollectionUtils.createList();
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(new ItemStack((ItemLike)Items.f_42614_), "gui.ic2.cards.show_name", 0, BaseTileInfoProvider.SettingType.SIMPLE, 7, 50));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(new ItemStack((ItemLike)Items.f_42517_), "gui.ic2.reactor_card.show_heat", 1, BaseTileInfoProvider.SettingType.SIMPLE, 7, 67));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(new ItemStack((ItemLike)Items.f_41860_), "gui.ic2.reactor_card.show_heat_progress", 2, BaseTileInfoProvider.SettingType.SIMPLE, 7, 84));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(new ItemStack((ItemLike)Items.f_41978_), "gui.ic2.cards.show_state", 3, BaseTileInfoProvider.SettingType.SIMPLE, 7, 101));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(new ItemStack((ItemLike)IC2Items.RE_BATTERY), "gui.ic2.reactor_card.production", 4, BaseTileInfoProvider.SettingType.SIMPLE, 80, 50));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(new ItemStack((ItemLike)Items.f_42524_), "gui.ic2.reactor_card.runtime", 5, BaseTileInfoProvider.SettingType.SIMPLE, 80, 67));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(new ItemStack((ItemLike)Items.f_42161_), "gui.ic2.reactor_card.runtime_progress", 6, BaseTileInfoProvider.SettingType.SIMPLE, 80, 84));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(new ItemStack((ItemLike)Items.f_42524_), "gui.ic2.reactor_card.left", 7, BaseTileInfoProvider.SettingType.SIMPLE, 80, 101));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(new ItemStack((ItemLike)IC2Items.RUBBER_BOAT_BROKEN), "gui.ic2.reactor_card.durability", 8, BaseTileInfoProvider.SettingType.SIMPLE, 80, 118));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(this.string("F"), "gui.ic2.cards.full_text", 9, BaseTileInfoProvider.SettingType.TEXT, 41, 67));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(this.string("%"), "gui.ic2.cards.percent", 10, BaseTileInfoProvider.SettingType.TEXT, 114, 67));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(null, "gui.ic2.cards.text_color", 0, BaseTileInfoProvider.SettingType.COLOR, 24, 50));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(null, "gui.ic2.cards.text_color", 1, BaseTileInfoProvider.SettingType.COLOR, 24, 67));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(null, "gui.ic2.reactor_card.heat_low_color", 2, BaseTileInfoProvider.SettingType.COLOR, 24, 84));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(null, "gui.ic2.reactor_card.heat_medium_color", 9, BaseTileInfoProvider.SettingType.COLOR, 41, 84));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(null, "gui.ic2.reactor_card.heat_high_color", 10, BaseTileInfoProvider.SettingType.COLOR, 58, 84));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(null, "gui.ic2.cards.text_color", 3, BaseTileInfoProvider.SettingType.COLOR, 24, 101));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(null, "gui.ic2.cards.active", 11, BaseTileInfoProvider.SettingType.COLOR, 41, 101));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(null, "gui.ic2.cards.inactive", 12, BaseTileInfoProvider.SettingType.COLOR, 58, 101));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(null, "gui.ic2.cards.text_color", 4, BaseTileInfoProvider.SettingType.COLOR, 97, 50));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(null, "gui.ic2.cards.text_color", 5, BaseTileInfoProvider.SettingType.COLOR, 97, 67));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(null, "gui.ic2.cards.text_color", 6, BaseTileInfoProvider.SettingType.COLOR, 97, 84));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(null, "gui.ic2.cards.text_color", 7, BaseTileInfoProvider.SettingType.COLOR, 97, 101));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(null, "gui.ic2.cards.text_color", 8, BaseTileInfoProvider.SettingType.COLOR, 97, 118));
        return entries;
    }

    public static int getMaxHeat(ItemStack stack, IReactor reactor) {
        return NBTUtils.getInt(StackUtil.getNbtData(stack), "max_heat", reactor.getMaxHeat());
    }

    @Override
    protected boolean isValidTile(BlockEntity tile) {
        return tile instanceof IReactor || tile instanceof IReactorChamber;
    }

    private static MutableComponent getRuntime(IReactor reactor, int flags) {
        ItemStack stack;
        boolean percent = (flags & 0x400) != 0;
        ItemStack itemStack = stack = reactor instanceof IChamberReactor ? ReactorCardItem.getUraniumRod((IChamberReactor)reactor, percent) : ItemStack.f_41583_;
        if (stack.m_41619_()) {
            return Component.m_237115_((String)"tooltip.item.ic2.reactor_card.runtime.no_uranium");
        }
        return Component.m_237110_((String)"tooltip.item.ic2.reactor_card.runtime.time", (Object[])new Object[]{percent ? Mth.m_14107_((double)((double)stack.m_41773_() / (double)stack.m_41776_() * 100.0)) + "%" : DurationFormatUtils.formatDuration((long)(stack.m_41773_() * 1000), (String)"HH:mm:ss")});
    }

    public static ProgressDisplayInfo.Progress getRuntime(IReactor reactor, ItemStack stack) {
        int color = ReactorCardItem.getColor(stack, 6, -1);
        ItemStack rod = reactor instanceof IChamberReactor ? ReactorCardItem.getUraniumRod((IChamberReactor)reactor, true) : ItemStack.f_41583_;
        return new ProgressDisplayInfo.Progress((double)rod.m_41773_() / (double)rod.m_41776_(), color, ColorUtils.darker(color));
    }

    private static MutableComponent getUraniumLeft(IReactor reactor) {
        ItemStack stack;
        ItemStack itemStack = stack = reactor instanceof IChamberReactor ? ReactorCardItem.getUraniumRod((IChamberReactor)reactor, true) : ItemStack.f_41583_;
        if (stack.m_41619_()) {
            return Component.m_237115_((String)"tooltip.item.ic2.reactor_card.time_left.no_uranium");
        }
        return Component.m_237110_((String)"tooltip.item.ic2.reactor_card.time_left.time", (Object[])new Object[]{DurationFormatUtils.formatDuration((long)((stack.m_41776_() - stack.m_41773_()) * 1000), (String)"HH:mm:ss")});
    }

    private static MutableComponent getProduction(IReactor reactor) {
        if (reactor instanceof ISteamReactor) {
            return Component.m_237110_((String)"tooltip.item.ic2.reactor_card.production.fluid", (Object[])new Object[]{(int)(reactor.getEnergyOutput() * (double)3.2f)});
        }
        if (reactor instanceof IEnergySource) {
            return Component.m_237110_((String)"tooltip.item.ic2.reactor_card.production.energy", (Object[])new Object[]{((IEnergySource)((Object)reactor)).getProvidedEnergy()});
        }
        return Component.m_237115_((String)"tooltip.item.ic2.reactor_card.production.none");
    }

    private static MutableComponent createHeatText(ItemStack stack, IReactor reactor, int flags) {
        int heat = reactor.getHeat();
        if ((flags & 0x200) != 0) {
            return Component.m_237110_((String)"tooltip.item.ic2.reactor_card.heat.full", (Object[])new Object[]{heat, ReactorCardItem.getMaxHeat(stack, reactor)});
        }
        return Component.m_237110_((String)"tooltip.item.ic2.reactor_card.heat.simple", (Object[])new Object[]{heat});
    }

    private static ProgressDisplayInfo.Progress createHeatProgress(ItemStack stack, IReactor reactor) {
        double progress = (double)reactor.getHeat() / (double)ReactorCardItem.getMaxHeat(stack, reactor);
        int baseColor = progress < 0.33 ? ReactorCardItem.getColor(stack, 2, ColorUtils.GREEN) : (progress < 0.66 ? ReactorCardItem.getColor(stack, 9, ColorUtils.YELLOW) : ReactorCardItem.getColor(stack, 10, ColorUtils.RED));
        return new ProgressDisplayInfo.Progress(progress, baseColor, ColorUtils.darker(baseColor));
    }

    private static MutableComponent getComponentHeat(IChamberReactor reactor) {
        double value = 0.0;
        int total = 0;
        int m = reactor.getWidth();
        for (int x = 0; x < m; ++x) {
            int n = reactor.getHeight();
            for (int y = 0; y < n; ++y) {
                IReactorComponent comp;
                double max;
                ItemStack stack = reactor.getStackInReactor(x, y);
                if (!(stack.m_41720_() instanceof IReactorComponent) || !((max = (double)(comp = (IReactorComponent)stack.m_41720_()).getMaxStoredHeat(stack, reactor, x, y)) > 0.0)) continue;
                value += (double)comp.getStoredHeat(stack, reactor, x, y) / max;
                ++total;
            }
        }
        return Component.m_237110_((String)"tooltip.item.ic2.reactor_card.part_health", (Object[])new Object[]{Mth.m_14107_((double)(total > 0 ? 100.0 - value / (double)total * 100.0 : 100.0))});
    }

    private static ItemStack getUraniumRod(IChamberReactor reactor, boolean findLargest) {
        int durabilityUsed = findLargest ? Integer.MAX_VALUE : 0;
        int maxDurability = 0;
        ItemStack result = ItemStack.f_41583_;
        int m = reactor.getWidth();
        for (int x = 0; x < m; ++x) {
            int n = reactor.getHeight();
            for (int y = 0; y < n; ++y) {
                ItemStack stack = reactor.getStackInReactor(x, y);
                if (stack.m_41619_() || !(stack.m_41720_() instanceof ReactorUraniumRod) && (!(stack.m_41720_() instanceof IReactorPlannerComponent) || ((IReactorPlannerComponent)stack.m_41720_()).getType(stack) != IReactorPlannerComponent.ComponentType.FUEL_ROD)) continue;
                int damage = stack.m_41773_();
                if (findLargest) {
                    int maxDamage = stack.m_41776_();
                    if (maxDamage < maxDurability || damage >= durabilityUsed) continue;
                    result = stack;
                    durabilityUsed = damage;
                    maxDamage = maxDurability;
                    continue;
                }
                if (damage <= durabilityUsed) continue;
                result = stack;
                durabilityUsed = damage;
            }
        }
        return result;
    }
}

