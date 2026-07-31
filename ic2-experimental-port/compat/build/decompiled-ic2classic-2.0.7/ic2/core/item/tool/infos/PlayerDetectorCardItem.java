/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
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
import ic2.core.block.misc.tiles.PlayerDetectorTileEntity;
import ic2.core.inventory.base.IHasHeldSlotInventory;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.tool.infos.ActivityCardItem;
import ic2.core.item.tool.infos.BaseTileInfoProvider;
import ic2.core.item.tool.infos.components.ViewerLogInfo;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.math.ColorUtils;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntity;

public class PlayerDetectorCardItem
extends BaseTileInfoProvider
implements IHasHeldSlotInventory {
    public static final int FLAG_SHOW_TEXT = 2;
    public static final int FLAG_SHOW_VIEWERS = 4;

    public PlayerDetectorCardItem() {
        super("detector_card", new PropertiesBuilder().maxStackSize(1), "tools/monitor", "detector_card");
    }

    @Override
    public void provideInfo(ItemStack stack, Consumer<IDisplayInfo> infos) {
        BlockEntity tile = this.getTileEntity(stack);
        if (tile instanceof PlayerDetectorTileEntity) {
            int flags = this.getFlags(stack);
            BooleanSupplier alive = this.createAliveTester(tile);
            if ((flags & 1) != 0) {
                infos.accept(new StringDisplayInfo(new BaseTileInfoProvider.NameProvider(this.getTileName(stack)), alive));
            }
            if ((flags & 2) != 0) {
                infos.accept(new StringDisplayInfo(new ActivityCardItem.InfoProvider((IActivityProvider)tile, PlayerDetectorCardItem.getColor(stack, 2, ColorUtils.GREEN), PlayerDetectorCardItem.getColor(stack, 3, ColorUtils.RED), PlayerDetectorCardItem.getColor(stack, 1, -1)), alive));
            }
            if ((flags & 4) != 0) {
                infos.accept(new ViewerLogInfo(() -> ((PlayerDetectorTileEntity)tile).trackerId, alive, PlayerDetectorCardItem.getColor(stack, 4, -1)));
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
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(new ItemStack((ItemLike)Items.f_42517_), "gui.ic2.cards.show_visitors", 1, BaseTileInfoProvider.SettingType.SIMPLE, 7, 84));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(null, "gui.ic2.cards.text_color", 0, BaseTileInfoProvider.SettingType.COLOR, 24, 50));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(null, "gui.ic2.cards.text_color", 1, BaseTileInfoProvider.SettingType.COLOR, 24, 67));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(null, "gui.ic2.cards.text_color", 4, BaseTileInfoProvider.SettingType.COLOR, 24, 84));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(null, "gui.ic2.cards.active", 2, BaseTileInfoProvider.SettingType.COLOR, 41, 67));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(null, "gui.ic2.cards.inactive", 3, BaseTileInfoProvider.SettingType.COLOR, 58, 67));
        return entries;
    }

    @Override
    protected int[] createDefaultColors() {
        return new int[]{0, 0, DyeColor.LIME.m_41060_(), DyeColor.RED.m_41060_(), 0};
    }

    @Override
    protected boolean isValidTile(BlockEntity tile) {
        return tile instanceof PlayerDetectorTileEntity;
    }

    @Override
    protected int createDefaultFlags() {
        return 7;
    }
}

