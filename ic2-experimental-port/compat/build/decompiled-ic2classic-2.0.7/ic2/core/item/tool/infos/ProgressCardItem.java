/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.util.Mth
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.item.tool.infos;

import ic2.api.tiles.display.IDisplayInfo;
import ic2.api.tiles.display.impl.ProgressDisplayInfo;
import ic2.api.tiles.display.impl.StringDisplayInfo;
import ic2.api.tiles.readers.IProgressMachine;
import ic2.api.tiles.readers.ReaderProvider;
import ic2.core.inventory.base.IHasHeldSlotInventory;
import ic2.core.item.tool.infos.BaseTileInfoProvider;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.math.ColorUtils;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ProgressCardItem
extends BaseTileInfoProvider
implements IHasHeldSlotInventory {
    public static final int FLAG_SHOW_TEXT = 2;
    public static final int FLAG_SHOW_PROGRESS = 4;
    public static final int FLAG_SHOW_PERCENT = 8;

    public ProgressCardItem() {
        super("progress_card", "tools/monitor", "progress_card");
    }

    @Override
    public void provideInfo(ItemStack stack, Consumer<IDisplayInfo> infos) {
        BlockEntity tile = this.getTileEntity(stack);
        IProgressMachine progress = ReaderProvider.getProgressProvider(tile);
        if (progress != null) {
            int flags = this.getFlags(stack);
            BooleanSupplier alive = this.createAliveTester(tile);
            if ((flags & 1) != 0) {
                infos.accept(new StringDisplayInfo(new BaseTileInfoProvider.NameProvider(this.getTileName(stack)), alive));
            }
            if ((flags & 2) != 0) {
                infos.accept(new StringDisplayInfo(new InfoProvider(flags, ProgressCardItem.getColor(stack, 1, -1), progress), alive));
            }
            if ((flags & 4) != 0) {
                infos.accept(new ProgressDisplayInfo(() -> this.createProgress(progress, ProgressCardItem.getColor(stack, 2, ColorUtils.BLUE)), alive));
            }
            return;
        }
        infos.accept(this.createTileError(stack, tile));
    }

    @Override
    protected boolean isValidTile(BlockEntity tile) {
        return ReaderProvider.getProgressProvider(tile) != null;
    }

    @Override
    protected int createDefaultFlags() {
        return 7;
    }

    @Override
    protected int[] createDefaultColors() {
        return new int[]{0, 0, DyeColor.LIGHT_BLUE.m_41060_()};
    }

    @Override
    protected List<BaseTileInfoProvider.SettingEntry> createSettings() {
        ObjectList entries = CollectionUtils.createList();
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(new ItemStack((ItemLike)Items.f_42614_), "gui.ic2.cards.show_name", 0, BaseTileInfoProvider.SettingType.SIMPLE, 7, 50));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(new ItemStack((ItemLike)Items.f_42517_), "gui.ic2.progress_card.progress", 1, BaseTileInfoProvider.SettingType.SIMPLE, 7, 67));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(new ItemStack((ItemLike)Items.f_41860_), "gui.ic2.progress_card.progress_progress", 2, BaseTileInfoProvider.SettingType.SIMPLE, 7, 84));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(null, "gui.ic2.cards.text_color", 0, BaseTileInfoProvider.SettingType.COLOR, 24, 50));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(null, "gui.ic2.cards.text_color", 1, BaseTileInfoProvider.SettingType.COLOR, 24, 67));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(null, "gui.ic2.cards.text_color", 2, BaseTileInfoProvider.SettingType.COLOR, 24, 84));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(this.string("%"), "gui.ic2.cards.percent", 3, BaseTileInfoProvider.SettingType.TEXT, 41, 67));
        return entries;
    }

    private static class InfoProvider
    implements Supplier<Component> {
        int flags;
        int color;
        IProgressMachine storage;

        public InfoProvider(int flags, int color, IProgressMachine storage) {
            this.flags = flags;
            this.color = color;
            this.storage = storage;
        }

        @Override
        public Component get() {
            MutableComponent text = Component.m_237110_((String)"tooltip.item.ic2.energy_card.progress", (Object[])new Object[]{(int)this.storage.getProgress(), (int)this.storage.getMaxProgress()}).m_130948_(ColorUtils.toColor(this.color));
            return (this.flags & 8) == 0 ? text : text.m_130946_(" " + Mth.m_14107_((double)((double)this.storage.getProgress() / (double)this.storage.getMaxProgress() * 100.0)) + "%");
        }
    }
}

