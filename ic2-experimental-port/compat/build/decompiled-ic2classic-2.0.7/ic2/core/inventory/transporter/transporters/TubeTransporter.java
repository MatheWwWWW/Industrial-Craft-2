/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMaps
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.inventory.transporter.transporters;

import ic2.api.tiles.tubes.ITube;
import ic2.core.inventory.filter.IFilter;
import ic2.core.inventory.transporter.IItemTransporter;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

public class TubeTransporter
implements IItemTransporter {
    ITube tube;

    public TubeTransporter(ITube tube) {
        this.tube = tube;
    }

    @Override
    public int addItem(ItemStack stack, Direction dir, boolean simulate) {
        if (!simulate) {
            this.tube.addItem(stack.m_41777_(), dir);
        }
        return stack.m_41613_();
    }

    @Override
    public ItemStack removeItem(IFilter filter, Direction dir, int amount, boolean simulate) {
        return ItemStack.f_41583_;
    }

    @Override
    public int getInventorySize(Direction dir) {
        return 0;
    }

    @Override
    public Object2IntMap<ItemStack> getAllItems(Direction dir, boolean compareNBT) {
        return Object2IntMaps.emptyMap();
    }

    @Override
    public IItemTransporter.InvResult getInventory(Direction dir, boolean compareNBT) {
        return new IItemTransporter.InvResult(compareNBT);
    }
}

