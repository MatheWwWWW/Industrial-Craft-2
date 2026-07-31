/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.inv.inventory;

import ic2.api.crops.ICrop;
import ic2.api.crops.ICropRegistry;
import ic2.api.crops.ICropSeed;
import ic2.core.block.crops.CropRegistry;
import ic2.core.block.crops.CropTileEntity;
import ic2.core.inventory.base.IHasHeldGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.inv.PortableInventory;
import ic2.core.inventory.inv.SimpleInventory;
import ic2.core.item.inv.container.CropBreederContainer;
import ic2.core.item.misc.CropSeedItem;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class CropBreederInventory
extends PortableInventory {
    public CropList allCrops;
    public ResultList results;
    public boolean waterlogged = false;

    public CropBreederInventory(Player owner, IHasHeldGui held, ItemStack stack, Slot slot) {
        super(owner, held, stack, slot);
    }

    public void addBreed(ItemStack stack) {
        if (!stack.m_41619_()) {
            for (int i = 0; i < 4; ++i) {
                if (!((ItemStack)this.inventory.get(i)).m_41619_()) continue;
                this.inventory.set(i, (Object)stack.m_41777_());
                this.markDirty();
                return;
            }
        }
    }

    public void breed() {
        int i;
        ObjectList breeding = CollectionUtils.createList();
        for (int i2 = 0; i2 < 4; ++i2) {
            ICrop crop;
            ItemStack stack = (ItemStack)this.inventory.get(i2);
            if (!(stack.m_41720_() instanceof ICropSeed) || (crop = ((ICropSeed)stack.m_41720_()).getCrop(stack)) == null) continue;
            breeding.add((ICrop)crop);
        }
        if (breeding.size() < 2) {
            return;
        }
        List<ICrop> crops = ICropRegistry.INSTANCE.getCrops();
        crops.remove(ICropRegistry.WEED);
        crops.remove(ICropRegistry.SEA_WEED);
        int[] ratios = new int[crops.size()];
        int total = 0;
        Object2ObjectMap<ICrop, BreedResult> resultMap = CollectionUtils.createMap();
        int m = crops.size();
        for (i = 0; i < m; ++i) {
            ICrop card = crops.get(i);
            BreedResult result = new BreedResult(card);
            int n = breeding.size();
            for (int j = 0; j < n; ++j) {
                int[] points = CropTileEntity.calculateRatiosFor(card, (ICrop)breeding.get(j), this.waterlogged);
                int n2 = i;
                ratios[n2] = ratios[n2] + points[0];
                total += points[0];
                result.add(points);
            }
            resultMap.put(card, result);
        }
        this.results.clear();
        m = crops.size();
        for (i = 0; i < m; ++i) {
            if (ratios[i] <= 0) continue;
            this.results.addResult(((BreedResult)resultMap.get(crops.get(i))).setMax(total));
        }
        this.results.sort();
        this.markDirty();
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new CropBreederContainer(this, player, this.getID(), windowID);
    }

    @Override
    public int getSlotCount() {
        return 4;
    }

    @Override
    public void save(CompoundTag nbt) {
        CompoundTag data = new CompoundTag();
        super.save(data);
        nbt.m_128365_("inv", (Tag)data);
        nbt.m_128379_("water", this.waterlogged);
        nbt.m_128365_("results", (Tag)this.results.save(new CompoundTag()));
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt.m_128469_("inv"));
        this.allCrops = new CropList();
        this.results = new ResultList();
        this.allCrops.init();
        this.breed();
        this.results.init();
        this.results.load(nbt.m_128469_("results"));
        this.waterlogged = nbt.m_128471_("water");
    }

    public class BreedResult {
        ICrop crop;
        int max;
        int[] points = new int[4];

        public BreedResult(CompoundTag data, int max) {
            this.crop = CropRegistry.INSTANCE.getCrop(ResourceLocation.m_135820_((String)data.m_128461_("id")));
            this.points = data.m_128465_("stats");
            this.max = max;
        }

        public BreedResult(ICrop crop) {
            this.crop = crop;
        }

        public BreedResult setMax(int max) {
            this.max = max;
            return this;
        }

        public void add(int[] data) {
            for (int i = 0; i < 4; ++i) {
                int n = i;
                this.points[n] = this.points[n] + data[i];
            }
        }

        public CompoundTag save() {
            CompoundTag nbt = new CompoundTag();
            nbt.m_128359_("id", this.crop.id().toString());
            nbt.m_128385_("stats", this.points);
            return nbt;
        }

        public ItemStack createStack() {
            ItemStack stack = CropSeedItem.createStack(this.crop, 0, 0, 0, 1);
            CompoundTag nbt = stack.m_41784_();
            nbt.m_128405_("points", this.points[0]);
            nbt.m_128385_("breed", new int[]{this.max, this.points[0], this.points[1], this.points[2], this.points[3]});
            CropSeedItem.setVisible(stack, true);
            return stack;
        }
    }

    public class ResultList
    extends CropList {
        protected NonNullList<BreedResult> results;

        public ResultList() {
            this.results = NonNullList.m_122779_();
        }

        @Override
        public void init() {
        }

        public void sort() {
            this.allCrops.sort((Comparator)new Comparator<ItemStack>(){

                @Override
                public int compare(ItemStack o1, ItemStack o2) {
                    return Integer.compare(this.getPoints(o2), this.getPoints(o1));
                }

                public int getPoints(ItemStack stack) {
                    return StackUtil.getNbtData(stack).m_128451_("points");
                }
            });
            this.updateSlots();
        }

        public void addResult(BreedResult result) {
            this.results.add((Object)result);
            this.allCrops.add((Object)result.createStack());
        }

        @Override
        public void clear() {
            this.allCrops.clear();
            this.results.clear();
            this.offset = 0;
            this.updateSlots();
        }

        @Override
        public CompoundTag save(CompoundTag nbt) {
            nbt.m_128405_("offset", this.offset);
            return nbt;
        }

        @Override
        public void load(CompoundTag nbt) {
            this.offset = nbt.m_128451_("offset");
            this.updateSlots();
        }
    }

    public class CropList
    extends SimpleInventory {
        protected NonNullList<ItemStack> allCrops;
        protected int offset;

        public CropList() {
            super(9);
            this.allCrops = NonNullList.m_122779_();
            this.offset = 0;
        }

        @Override
        public int getMaxStackSize(int slot) {
            return 1;
        }

        public void init() {
            for (ICrop card : ICropRegistry.INSTANCE.getCrops()) {
                if (card == ICropRegistry.WEED || card == CropRegistry.SEA_WEED) continue;
                ItemStack stack = CropSeedItem.createStack(card, 0, 0, 0, 1);
                CropSeedItem.setVisible(stack, true);
                this.allCrops.add((Object)stack);
            }
            this.offset(0);
        }

        public void updateSlots() {
            for (int i = 0; i < 9; ++i) {
                if (i + this.offset >= this.allCrops.size()) {
                    this.setStackInSlot(i, ItemStack.f_41583_);
                    continue;
                }
                this.setStackInSlot(i, (ItemStack)this.allCrops.get(i + this.offset));
            }
            CropBreederInventory.this.markDirty();
        }

        public void offset(int extra) {
            this.offset = Mth.m_14045_((int)(this.offset + extra), (int)0, (int)Math.max(0, this.allCrops.size() - 9));
            this.updateSlots();
        }

        @Override
        public boolean canInsert(int slot, ItemStack stack) {
            return false;
        }

        @Override
        public boolean canExtract(int slot, ItemStack stack) {
            return false;
        }

        @Override
        public CompoundTag save(CompoundTag nbt) {
            super.save(nbt);
            nbt.m_128405_("offset", this.offset);
            return nbt;
        }

        @Override
        public void load(CompoundTag nbt) {
            super.load(nbt);
            this.offset = nbt.m_128451_("offset");
        }
    }
}

