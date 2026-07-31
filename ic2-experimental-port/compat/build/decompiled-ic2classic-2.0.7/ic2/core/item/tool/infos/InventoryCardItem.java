/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.items.IItemHandler
 */
package ic2.core.item.tool.infos;

import ic2.api.tiles.display.IDisplayInfo;
import ic2.api.tiles.display.impl.StringDisplayInfo;
import ic2.core.inventory.inv.HandlerInventory;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.tool.infos.BaseTileInfoProvider;
import ic2.core.item.tool.infos.components.InventoryDisplayInfo;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;

public class InventoryCardItem
extends BaseTileInfoProvider {
    public static final int FLAG_SHOW_COUNT = 2;
    public static final int FLAG_SHOW_COUNT_SIDE = 4;
    public static final int FLAG_SHOW_ITEM_NAME = 8;
    public static final int FLAG_MERGE_INV = 16;

    public InventoryCardItem() {
        super("inv_card", new PropertiesBuilder().maxStackSize(1), "tools/monitor", "inv_card");
    }

    @Override
    public void provideInfo(ItemStack stack, Consumer<IDisplayInfo> infos) {
        IItemHandler handler;
        BlockEntity tile = this.getTileEntity(stack);
        if (tile != null && (handler = (IItemHandler)tile.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(null)) != null) {
            BooleanSupplier alive = this.createAliveTester(tile);
            if ((this.getFlags(stack) & 1) != 0) {
                infos.accept(new StringDisplayInfo(new BaseTileInfoProvider.NameProvider(this.getTileName(stack)), alive));
            }
            infos.accept(new InventoryDisplayInfo(() -> new HandlerInventory(handler), () -> this.create(this.getFlags(stack)), alive));
            return;
        }
        infos.accept(this.createTileError(stack, tile));
    }

    private InventoryDisplayInfo.InvSettings create(int flags) {
        return new InventoryDisplayInfo.InvSettings((flags & 2) != 0, (flags & 4) != 0, (flags & 8) != 0, (flags & 0x10) != 0);
    }

    @Override
    protected boolean isValidTile(BlockEntity tile) {
        return tile.getCapability(ForgeCapabilities.ITEM_HANDLER).isPresent();
    }

    @Override
    protected List<BaseTileInfoProvider.SettingEntry> createSettings() {
        ObjectList entries = CollectionUtils.createList();
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(new ItemStack((ItemLike)Items.f_42614_), "gui.ic2.cards.show_name", 0, BaseTileInfoProvider.SettingType.SIMPLE, 7, 50));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(null, "gui.ic2.cards.text_color", 0, BaseTileInfoProvider.SettingType.COLOR, 24, 50));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(this.string("C"), "gui.ic2.inv_card.show_count", 1, BaseTileInfoProvider.SettingType.TEXT, 7, 67));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(this.string("S"), "gui.ic2.inv_card.show_count_side", 2, BaseTileInfoProvider.SettingType.TEXT, 24, 67));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(this.string("N"), "gui.ic2.inv_card.show_name", 3, BaseTileInfoProvider.SettingType.TEXT, 41, 67));
        entries.add((BaseTileInfoProvider.SettingEntry)new BaseTileInfoProvider.SettingEntry(this.string("M"), "gui.ic2.inv_card.mergeInv", 4, BaseTileInfoProvider.SettingType.TEXT, 58, 67));
        return entries;
    }

    @Override
    protected int[] createDefaultColors() {
        return new int[]{0};
    }

    @Override
    protected int createDefaultFlags() {
        return 27;
    }
}

