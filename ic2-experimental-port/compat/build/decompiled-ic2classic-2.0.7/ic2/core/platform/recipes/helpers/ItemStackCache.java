/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.core.NonNullList
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Tuple
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.eventbus.api.Event
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.platform.recipes.helpers;

import ic2.core.inventory.filter.FluidFilter;
import ic2.core.inventory.filter.IFilter;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import java.util.Map;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemStackCache {
    static final ObjectArrayList<ItemStack> EMPTY = ObjectArrayList.wrap((Object[])new ItemStack[0]);
    public static final ItemStackCache INSTANCE = new ItemStackCache();
    ObjectArrayList<ItemStack> cache = ObjectArrayList.wrap((Object[])new ItemStack[0]);
    Map<ResourceLocation, ObjectArrayList<ItemStack>> requestedFilter = CollectionUtils.createLinkedMap();
    Map<Fluid, ObjectArrayList<ItemStack>> fluidFilter = CollectionUtils.createLinkedMap();

    public void init() {
        int i;
        int m;
        IFilter filter;
        ObjectArrayList list;
        this.cache.clear();
        NonNullList helper = NonNullList.m_122779_();
        for (Item item : ForgeRegistries.ITEMS) {
            if (item == Items.f_41852_) continue;
            helper.clear();
            item.m_6787_(CreativeModeTab.f_40754_, helper);
            for (ItemStack stack : helper) {
                this.cache.add((Object)stack.m_41777_());
            }
        }
        this.fluidFilter.clear();
        this.requestedFilter.clear();
        ItemStack[] elements = (ItemStack[])this.cache.elements();
        ObjectList filterList = CollectionUtils.createList();
        MinecraftForge.EVENT_BUS.post((Event)new StackCacheGeneratorEvent((List<Tuple<ResourceLocation, IFilter>>)filterList));
        for (Tuple entry : filterList) {
            list = ObjectArrayList.wrap((Object[])new ItemStack[50], (int)0);
            filter = (IFilter)entry.m_14419_();
            m = this.cache.size();
            for (i = 0; i < m; ++i) {
                if (!filter.matches(elements[i])) continue;
                list.add((Object)elements[i]);
            }
            this.requestedFilter.put((ResourceLocation)entry.m_14418_(), (ObjectArrayList<ItemStack>)list);
        }
        for (Fluid fluid : ForgeRegistries.FLUIDS) {
            if (!fluid.m_7444_(fluid.m_76145_()) || fluid == Fluids.f_76191_) continue;
            list = ObjectArrayList.wrap((Object[])new ItemStack[10], (int)0);
            filter = new FluidFilter(fluid);
            m = this.cache.size();
            for (i = 0; i < m; ++i) {
                if (!filter.matches(elements[i])) continue;
                list.add((Object)elements[i]);
            }
            this.fluidFilter.put(fluid, (ObjectArrayList<ItemStack>)list);
        }
    }

    public ObjectArrayList<ItemStack> getCustomCache(ResourceLocation location) {
        return this.requestedFilter.getOrDefault(location, EMPTY);
    }

    public ObjectArrayList<ItemStack> getFluidContainers(Fluid fluid) {
        return this.fluidFilter.getOrDefault(fluid, EMPTY);
    }

    public int size() {
        return this.cache.size();
    }

    public ObjectArrayList<ItemStack> getCache() {
        return this.cache;
    }

    public ItemStack[] getRawCache() {
        return (ItemStack[])this.cache.elements();
    }

    public static class StackCacheGeneratorEvent
    extends Event {
        final List<Tuple<ResourceLocation, IFilter>> list;

        public StackCacheGeneratorEvent(List<Tuple<ResourceLocation, IFilter>> list) {
            this.list = list;
        }

        public void addSearchFilter(String mod, String name, IFilter filter) {
            this.addSearchFilter(new ResourceLocation(mod, name), filter);
        }

        public void addSearchFilter(ResourceLocation id, IFilter filter) {
            if (filter == null || id == null) {
                throw new RuntimeException("Nulls are not allowed. " + id + " : " + filter);
            }
            this.list.add((Tuple<ResourceLocation, IFilter>)new Tuple((Object)id, (Object)filter));
        }
    }
}

