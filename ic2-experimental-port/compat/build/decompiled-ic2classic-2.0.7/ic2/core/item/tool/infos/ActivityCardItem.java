/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.item.tool.infos;

import ic2.api.tiles.display.IDisplayInfo;
import ic2.api.tiles.display.impl.StringDisplayInfo;
import ic2.api.tiles.readers.IActivityProvider;
import ic2.api.tiles.readers.ReaderProvider;
import ic2.core.item.tool.infos.BaseTileInfoProvider;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.math.ColorUtils;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ActivityCardItem
extends BaseTileInfoProvider {
    public static final int FLAG_SHOW_TEXT = 2;

    public ActivityCardItem() {
        super("activity_card", "tools/monitor", "activity_card");
    }

    @Override
    public void provideInfo(ItemStack stack, Consumer<IDisplayInfo> infos) {
        BlockEntity tile = this.getTileEntity(stack);
        IActivityProvider provider = ReaderProvider.getActivityProvider(tile);
        if (provider != null) {
            int flags = this.getFlags(stack);
            BooleanSupplier alive = this.createAliveTester(tile);
            if ((flags & 1) != 0) {
                infos.accept(new StringDisplayInfo(new BaseTileInfoProvider.NameProvider(this.getTileName(stack)), alive));
            }
            if ((flags & 2) != 0) {
                infos.accept(new StringDisplayInfo(new InfoProvider(provider, ActivityCardItem.getColor(stack, 2, ColorUtils.GREEN), ActivityCardItem.getColor(stack, 3, ColorUtils.RED), ActivityCardItem.getColor(stack, 1, -1)), alive));
            }
            return;
        }
        infos.accept(this.createTileError(stack, tile));
    }

    @Override
    protected List<BaseTileInfoProvider.SettingEntry> createSettings() {
        ObjectList entries = CollectionUtils.createList();
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(new ItemStack((ItemLike)Items.f_42614_), "gui.ic2.cards.show_name", 0, BaseTileInfoProvider.SettingType.SIMPLE, 7, 50));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(new ItemStack((ItemLike)Items.f_42451_), "gui.ic2.cards.show_state", 1, BaseTileInfoProvider.SettingType.SIMPLE, 7, 67));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(null, "gui.ic2.cards.text_color", 0, BaseTileInfoProvider.SettingType.COLOR, 24, 50));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(null, "gui.ic2.cards.text_color", 1, BaseTileInfoProvider.SettingType.COLOR, 24, 67));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(null, "gui.ic2.cards.active", 2, BaseTileInfoProvider.SettingType.COLOR, 41, 67));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(null, "gui.ic2.cards.inactive", 3, BaseTileInfoProvider.SettingType.COLOR, 58, 67));
        return entries;
    }

    @Override
    protected boolean isValidTile(BlockEntity tile) {
        return ReaderProvider.getActivityProvider(tile) != null;
    }

    @Override
    protected int createDefaultFlags() {
        return 3;
    }

    @Override
    protected int[] createDefaultColors() {
        return new int[]{0, 0, DyeColor.LIME.m_41060_(), DyeColor.RED.m_41060_()};
    }

    public static class InfoProvider
    implements Supplier<Component> {
        int onColor;
        int offColor;
        int textColor;
        IActivityProvider storage;

        public InfoProvider(IActivityProvider storage, int onColor, int offColor, int textColor) {
            this.storage = storage;
            this.onColor = onColor;
            this.offColor = offColor;
            this.textColor = textColor;
        }

        @Override
        public Component get() {
            return Component.m_237115_((String)"tooltip.item.ic2.activity_card.state").m_130948_(ColorUtils.toColor(this.textColor)).m_7220_((Component)(this.storage.isActivated() ? Component.m_237115_((String)"tooltip.item.ic2.activity_card.active").m_6270_(ColorUtils.toColor(this.onColor)) : Component.m_237115_((String)"tooltip.item.ic2.activity_card.inactive").m_6270_(ColorUtils.toColor(this.offColor))));
        }
    }
}

