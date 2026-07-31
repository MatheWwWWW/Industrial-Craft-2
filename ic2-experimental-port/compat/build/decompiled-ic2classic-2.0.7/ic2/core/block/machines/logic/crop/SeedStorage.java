/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMaps
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectArrays
 *  it.unimi.dsi.fastutil.objects.ObjectCollection
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.IntTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.LongTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.machines.logic.crop;

import ic2.api.crops.ICrop;
import ic2.api.crops.ICropRegistry;
import ic2.core.block.machines.logic.crop.ISeedEntry;
import ic2.core.block.machines.logic.crop.SeedEntry;
import ic2.core.block.machines.logic.crop.SeedSyncer;
import ic2.core.block.machines.logic.crop.WrapperEntry;
import ic2.core.inventory.inv.SimpleInventory;
import ic2.core.item.misc.CropSeedItem;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.NBTListWrapper;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public class SeedStorage {
    final SimpleInventory inventory;
    final int typeLimit;
    final int statLimit;
    final int sizeLimit;
    Object2ObjectMap<ICrop, ObjectList<ISeedEntry>> crops = CollectionUtils.createLinkedMap();
    LinkedList<ICrop> sorter = new LinkedList();
    SeedSyncer syncer = new SeedSyncer();
    int offset = 0;
    boolean active = true;

    public SeedStorage(int typeLimit, int statLimit, int sizeLimit) {
        this.inventory = new SimpleInventory(25);
        this.typeLimit = typeLimit;
        this.statLimit = statLimit;
        this.sizeLimit = sizeLimit;
        this.crops.put(null, (Object)ObjectArrayList.wrap((Object[])new ISeedEntry[0]));
    }

    public int getSizeLimit() {
        return this.sizeLimit;
    }

    public int getStatLimit() {
        return this.statLimit;
    }

    public int getTypeLimit() {
        return this.typeLimit;
    }

    public SeedSyncer getSyncer() {
        return this.syncer;
    }

    public NonNullList<ItemStack> getTypes() {
        NonNullList items = NonNullList.m_122779_();
        for (Object2ObjectMap.Entry entry : this.crops.object2ObjectEntrySet()) {
            if (entry.getKey() == null) continue;
            ItemStack stack = CropSeedItem.createStack((ICrop)entry.getKey(), 0, 0, 0, 4);
            CropSeedItem.setVisible(stack, true);
            for (ISeedEntry subEntry : (ObjectList)entry.getValue()) {
                stack.m_41769_(subEntry.getCount());
            }
            stack.m_41774_(1);
            items.add((Object)stack);
        }
        return items;
    }

    public void addDrops(List<ItemStack> list) {
        ObjectList seeds = (ObjectList)this.crops.get(null);
        if (seeds == null) {
            return;
        }
        for (ISeedEntry entry : seeds) {
            int toRemove;
            ItemStack stack = CropSeedItem.createStack(entry.getCrop(null), entry.getGrowth(), entry.getGain(), entry.getResistance(), 4);
            if (stack.m_41619_()) continue;
            for (int left = entry.getCount(); left > 0; left -= toRemove) {
                toRemove = Math.min(left, 64);
                list.add(StackUtil.copyWithSize(stack, toRemove));
            }
        }
    }

    public SimpleInventory getInventory() {
        return this.inventory;
    }

    public boolean setActive(boolean value) {
        if (this.active != value) {
            this.active = value;
            this.syncer.active = value;
            if (!value) {
                this.inventory.clear();
            }
            return true;
        }
        return false;
    }

    public void markAllDirty(ICrop current) {
        this.sorter.clear();
        ObjectArrayList allCrops = new ObjectArrayList((ObjectCollection)this.crops.keySet());
        allCrops.remove(current);
        allCrops.add(0, current);
        this.sorter.addAll((Collection<ICrop>)allCrops);
    }

    public void markCurrentDirty(ICrop crop) {
        this.markDirty(crop);
    }

    public void offset(ICrop crop, int offset) {
        if (!this.active) {
            offset = 0;
            return;
        }
        ObjectList list = (ObjectList)this.crops.get((Object)crop);
        this.offset = Mth.m_14045_((int)(this.offset + offset), (int)0, (int)(list.isEmpty() ? 0 : (list.size() - 1) / 5));
    }

    public boolean addCrop(ICrop crop, ISeedEntry stats) {
        if (!this.active) {
            return false;
        }
        ObjectList entry = (ObjectList)this.crops.get((Object)crop);
        if (entry == null) {
            if (this.typeLimit > 0 && this.crops.size() >= this.typeLimit + 1) {
                return false;
            }
            entry = ObjectArrayList.wrap((Object[])new ISeedEntry[0]);
            this.crops.put((Object)crop, (Object)entry);
            if (crop != null && crop.id() != null) {
                this.syncer.existingCrops.add(crop.id());
            }
            ++this.syncer.cropCount;
        }
        int m = entry.size();
        for (int i = 0; i < m; ++i) {
            ISeedEntry present = (ISeedEntry)entry.get(i);
            if (present.hashCode() != stats.hashCode()) continue;
            if (this.sizeLimit > 0 && present.getCount() >= this.sizeLimit) {
                return false;
            }
            this.syncer.itemCount = this.sizeLimit < 0 ? (this.syncer.itemCount += present.grow(stats.drain(stats.getCount(), true))) : (this.syncer.itemCount += present.grow(stats.drain(Math.min(stats.getCount(), this.sizeLimit - present.getCount()), true)));
            this.markDirty(crop);
            return true;
        }
        if (this.statLimit > 0 && entry.size() >= this.statLimit) {
            return false;
        }
        SeedEntry injected = new SeedEntry(stats.getGrowth(), stats.getGain(), stats.getResistance(), stats.getTier(), Math.min(this.sizeLimit, stats.getCount()));
        stats.drain(injected.getCount(), true);
        entry.add((Object)injected);
        ((ObjectList)this.crops.get(null)).add((Object)new WrapperEntry(crop, injected));
        ++this.syncer.statCount;
        this.syncer.itemCount += injected.getCount();
        this.markDirty(crop);
        return true;
    }

    protected void markDirty(ICrop crop) {
        this.sorter.remove(crop);
        this.sorter.add(crop);
        this.sorter.remove(null);
        this.sorter.add(null);
    }

    public ItemStack remove(ICrop crop, int slot, int requested, boolean doDrain) {
        int amount;
        ISeedEntry entry;
        block6: {
            block7: {
                ObjectList list;
                block8: {
                    if (!this.active) {
                        return ItemStack.f_41583_;
                    }
                    list = (ObjectList)this.crops.get((Object)crop);
                    int slotIndex = this.offset * 5 + slot;
                    if (list == null || list.size() <= slotIndex) {
                        return ItemStack.f_41583_;
                    }
                    entry = (ISeedEntry)list.get(slotIndex);
                    amount = entry.drain(requested, doDrain);
                    if (!doDrain) break block6;
                    this.syncer.itemCount -= amount;
                    if (!entry.isEmpty()) break block7;
                    --this.syncer.statCount;
                    list.remove(slotIndex);
                    if (list.isEmpty() && crop != null) {
                        this.crops.remove((Object)crop);
                        --this.syncer.cropCount;
                        if (crop.id() != null) {
                            this.syncer.existingCrops.remove(crop.id());
                        }
                    }
                    if (!(entry instanceof WrapperEntry)) break block8;
                    ICrop subCrop = entry.getCrop(crop);
                    list = (ObjectList)this.crops.get((Object)subCrop);
                    int m = list.size();
                    for (int i = 0; i < m; ++i) {
                        if (!((ISeedEntry)list.get(i)).isEmpty()) continue;
                        list.remove(i--);
                        --m;
                    }
                    if (!list.isEmpty() || subCrop == null) break block7;
                    this.crops.remove((Object)subCrop);
                    --this.syncer.cropCount;
                    if (subCrop.id() == null) break block7;
                    this.syncer.existingCrops.remove(subCrop.id());
                    break block7;
                }
                list = (ObjectList)this.crops.get(null);
                int m = list.size();
                for (int i = 0; i < m; ++i) {
                    if (!((ISeedEntry)list.get(i)).isEmpty()) continue;
                    list.remove(i--);
                    --m;
                }
            }
            this.sorter.add(crop);
        }
        ItemStack stack = CropSeedItem.createStack(entry.getCrop(crop), entry.getGrowth(), entry.getGain(), entry.getResistance(), 4);
        stack.m_41764_(amount);
        return stack;
    }

    public boolean processSorting(ICrop displayCrop, Comparator<ISeedEntry> comparator) {
        if (this.sorter.isEmpty() || comparator == null) {
            return false;
        }
        ICrop toSort = this.sorter.removeFirst();
        ObjectList seeds = (ObjectList)this.crops.get((Object)toSort);
        if (seeds == null) {
            return false;
        }
        ObjectArrays.parallelQuickSort((Object[])((ISeedEntry[])((ObjectArrayList)seeds).elements()), (int)0, (int)seeds.size(), comparator);
        return toSort == displayCrop;
    }

    public void updateDisplay(ICrop currentCrop) {
        this.inventory.clear();
        List seeds = (List)this.crops.get((Object)currentCrop);
        if (seeds == null || seeds.isEmpty() || !this.active) {
            return;
        }
        int index = this.offset * 5;
        int m = seeds.size() - index;
        for (int i = 0; i < m && i < 25; ++i) {
            ISeedEntry entry = (ISeedEntry)seeds.get(i + index);
            ItemStack stack = CropSeedItem.createStack(entry.getCrop(currentCrop), entry.getGrowth(), entry.getGain(), entry.getResistance(), 4);
            CropSeedItem.setVisible(stack, true);
            stack.m_41764_(Mth.m_14045_((int)entry.getCount(), (int)1, (int)127));
            stack.m_41700_("crop_count", (Tag)IntTag.m_128679_((int)entry.getCount()));
            this.inventory.setStackInSlot(i, stack);
        }
    }

    public boolean containsCrop(ICrop crop) {
        return this.crops.get((Object)crop) != null;
    }

    public void read(CompoundTag base) {
        ObjectList nullSeeds = (ObjectList)this.crops.get(null);
        for (CompoundTag data : NBTListWrapper.wrap(base.m_128437_("crops", 10), CompoundTag.class)) {
            ICrop crop = ICropRegistry.INSTANCE.getCrop(new ResourceLocation(data.m_128461_("crop")));
            if (crop == null) continue;
            ObjectArrayList list = ObjectArrayList.wrap((Object[])new ISeedEntry[0]);
            for (LongTag value : NBTListWrapper.wrap(data.m_128437_("types", 4), LongTag.class)) {
                SeedEntry entry = new SeedEntry(value.m_7046_());
                list.add((Object)entry);
                nullSeeds.add((Object)new WrapperEntry(crop, entry));
            }
            this.crops.put((Object)crop, (Object)list);
        }
        this.offset = base.m_128451_("offset");
        this.syncer.read(base.m_128469_("syncer"));
    }

    public CompoundTag write(CompoundTag base) {
        ListTag list = new ListTag();
        for (Object2ObjectMap.Entry entry : Object2ObjectMaps.fastIterable(this.crops)) {
            if (entry.getKey() == null || entry.getValue() == null || ((ObjectList)entry.getValue()).isEmpty()) continue;
            CompoundTag data = new CompoundTag();
            ListTag types = new ListTag();
            ObjectList entries = (ObjectList)entry.getValue();
            int m = entries.size();
            for (int i = 0; i < m; ++i) {
                types.add((Object)((ISeedEntry)entries.get(i)).write());
            }
            data.m_128359_("crop", ((ICrop)entry.getKey()).id().toString());
            data.m_128365_("types", (Tag)types);
            list.add((Object)data);
        }
        NBTUtils.put(base, "crops", list);
        NBTUtils.putInt(base, "offset", this.offset, 0);
        NBTUtils.put(base, "syncer", this.syncer.write());
        return base;
    }
}

