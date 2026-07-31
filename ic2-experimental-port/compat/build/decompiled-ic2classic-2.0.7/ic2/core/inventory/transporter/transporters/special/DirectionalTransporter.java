/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.inventory.transporter.transporters.special;

import ic2.core.inventory.filter.IFilter;
import ic2.core.inventory.transporter.IItemTransporter;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

public class DirectionalTransporter
implements IItemTransporter {
    IItemTransporter transporter;
    Direction direction;

    public DirectionalTransporter(IItemTransporter transporter, Direction direction) {
        this.transporter = transporter;
        this.direction = direction;
    }

    @Override
    public int addItem(ItemStack stack, Direction dir, boolean simulate) {
        return stack.m_41619_() ? 0 : this.transporter.addItem(stack, this.direction, simulate);
    }

    @Override
    public ItemStack removeItem(IFilter filter, Direction dir, int amount, boolean simulate) {
        return amount <= 0 ? ItemStack.f_41583_ : this.transporter.removeItem(filter, this.direction, amount, simulate);
    }

    @Override
    public int getInventorySize(Direction dir) {
        return this.transporter.getInventorySize(this.direction);
    }

    @Override
    public Object2IntMap<ItemStack> getAllItems(Direction dir, boolean compareNBT) {
        return this.transporter.getAllItems(this.direction, compareNBT);
    }

    @Override
    public IItemTransporter.InvResult getInventory(Direction dir, boolean compareNBT) {
        return this.transporter.getInventory(this.direction, compareNBT);
    }
}

