/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 */
package ic2.core.item.upgrades.base;

import ic2.api.items.ITagItem;
import ic2.api.items.IUpgradeItem;
import ic2.api.network.item.INetworkItemEvent;
import ic2.core.IC2;
import ic2.core.inventory.base.IHasHeldGui;
import ic2.core.inventory.filter.ArrayOrFilter;
import ic2.core.inventory.filter.IFilter;
import ic2.core.inventory.filter.InvertedFilter;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.filter.special.TransferFilter;
import ic2.core.item.inv.container.ImportExportContainer;
import ic2.core.item.inv.inventory.ImportExportInventory;
import ic2.core.item.upgrades.base.BaseDirectionalUpgrade;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.Iterator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;

public class BaseTransportUpgrade
extends BaseDirectionalUpgrade
implements INetworkItemEvent {
    public static final String TRANSFER_SIZE = "transfer_size";
    public static final String FILTERS = "filters";
    public static final String FLAGS = "filter_flags";
    public static final String DURABILITY = "filter_durability";
    public static final String INVERTED = "filter_inverted";

    public BaseTransportUpgrade(String itemName) {
        super(itemName);
    }

    @Override
    public IUpgradeItem.UpgradeType getType(ItemStack stack) {
        return IUpgradeItem.UpgradeType.TRANSPORT_MOD;
    }

    @Override
    public void onEventReceived(ItemStack stack, Player player, int key, int value, Dist target) {
        if (target.isDedicatedServer() && player.f_36096_ instanceof ImportExportContainer) {
            ((ImportExportInventory)((ImportExportContainer)player.f_36096_).getHolder()).onDataReceived(key, value);
        }
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (this instanceof IHasHeldGui && IC2.KEYBOARD.isSideInventoryKeyDown(playerIn)) {
            ItemStack stack = playerIn.m_21120_(handIn);
            if (IC2.PLATFORM.isSimulating()) {
                IC2.PLATFORM.launchGui(playerIn, handIn, null, ((IHasHeldGui)((Object)this)).getInventory(playerIn, handIn, stack));
            }
            return InteractionResultHolder.m_19090_((Object)stack);
        }
        return super.m_7203_(worldIn, playerIn, handIn);
    }

    public int getTransferSize(ItemStack stack) {
        CompoundTag nbt = StackUtil.getNbtData(stack);
        return nbt.m_128441_(TRANSFER_SIZE) ? nbt.m_128451_(TRANSFER_SIZE) : 64;
    }

    public IFilter getFilter(ItemStack stack) {
        ObjectList filters = CollectionUtils.createList();
        CompoundTag nbt = StackUtil.getNbtData(stack);
        int flags = nbt.m_128451_(FLAGS) | 4;
        int durability = nbt.m_128451_(DURABILITY);
        Iterator iterator = NBTUtils.getItems(stack, FILTERS).iterator();
        while (iterator.hasNext()) {
            ItemStack item;
            filters.add((TransferFilter)new TransferFilter(item, flags | ((item = (ItemStack)iterator.next()).m_41720_() instanceof ITagItem ? 32 : 0), durability));
        }
        if (filters.size() > 0) {
            return !NBTUtils.getBoolean(nbt, INVERTED, true) ? new InvertedFilter(new ArrayOrFilter((Iterable<IFilter>)filters)) : new ArrayOrFilter((Iterable<IFilter>)filters);
        }
        return SpecialFilters.ANY_ITEM;
    }
}

