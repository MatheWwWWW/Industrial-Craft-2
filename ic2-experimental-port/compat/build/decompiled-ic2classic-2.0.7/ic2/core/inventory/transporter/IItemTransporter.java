/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.AbstractObject2IntMap$BasicEntry
 *  it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenCustomHashMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2IntMaps
 *  it.unimi.dsi.fastutil.objects.Object2LongLinkedOpenCustomHashMap
 *  it.unimi.dsi.fastutil.objects.Object2LongMaps
 *  it.unimi.dsi.fastutil.objects.ObjectIterator
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.inventory.transporter;

import ic2.core.block.machines.recipes.ItemStackStrategy;
import ic2.core.inventory.filter.IFilter;
import ic2.core.utils.collection.IterableWrapper;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.objects.AbstractObject2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2LongLinkedOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.Object2LongMaps;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Iterator;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

public interface IItemTransporter {
    public int addItem(ItemStack var1, Direction var2, boolean var3);

    public ItemStack removeItem(IFilter var1, Direction var2, int var3, boolean var4);

    public int getInventorySize(Direction var1);

    public Object2IntMap<ItemStack> getAllItems(Direction var1, boolean var2);

    public InvResult getInventory(Direction var1, boolean var2);

    public static class InvResult {
        Object2IntLinkedOpenCustomHashMap<ItemStack> found;
        Object2LongLinkedOpenCustomHashMap<ItemStack> room;

        public InvResult(boolean nbt) {
            this.found = new Object2IntLinkedOpenCustomHashMap(ItemStackStrategy.getStrategy(nbt));
            this.room = new Object2LongLinkedOpenCustomHashMap(ItemStackStrategy.getStrategy(nbt));
        }

        public Object2IntLinkedOpenCustomHashMap<ItemStack> getFound() {
            return this.found;
        }

        public Object2LongLinkedOpenCustomHashMap<ItemStack> getRoom() {
            return this.room;
        }

        public int getSpace(ItemStack stack) {
            return (int)(this.room.getOrDefault((Object)stack, Integer.MAX_VALUE) - (long)this.found.getInt((Object)stack));
        }

        public long getFree() {
            return this.room.getLong((Object)ItemStack.f_41583_);
        }

        public Iterable<Object2IntMap.Entry<ItemStack>> getFreeRoomIterator() {
            return IterableWrapper.wrap(new Iterator<Object2IntMap.Entry<ItemStack>>(){
                ObjectIterator<Object2IntMap.Entry<ItemStack>> iter;
                {
                    this.iter = Object2IntMaps.fastIterator(found);
                }

                @Override
                public boolean hasNext() {
                    return this.iter.hasNext();
                }

                @Override
                public Object2IntMap.Entry<ItemStack> next() {
                    Object2IntMap.Entry entry = (Object2IntMap.Entry)this.iter.next();
                    ItemStack stack = (ItemStack)entry.getKey();
                    return new AbstractObject2IntMap.BasicEntry((Object)stack, (int)Math.min(room.getLong((Object)stack), (long)entry.getIntValue()));
                }
            });
        }

        public void add(InvResult result) {
            for (Object2IntMap.Entry entry : Object2IntMaps.fastIterable(result.getFound())) {
                this.found.addTo((Object)((ItemStack)entry.getKey()), entry.getIntValue());
            }
            for (Object2IntMap.Entry entry : Object2LongMaps.fastIterable(result.getRoom())) {
                this.room.addTo((Object)((ItemStack)entry.getKey()), entry.getLongValue());
            }
        }

        public void add(ItemStack item, int max) {
            if (item.m_41619_()) {
                this.room.put((Object)ItemStack.f_41583_, (long)max);
                return;
            }
            ItemStack stack = StackUtil.copyWithSize(item, 1);
            this.found.addTo((Object)stack, item.m_41613_());
            this.room.addTo((Object)stack, (long)max);
        }
    }
}

