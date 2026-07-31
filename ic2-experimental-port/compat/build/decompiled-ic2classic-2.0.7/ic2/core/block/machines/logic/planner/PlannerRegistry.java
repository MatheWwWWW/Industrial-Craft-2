/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.shorts.ShortArrayList
 *  it.unimi.dsi.fastutil.shorts.ShortComparator
 *  it.unimi.dsi.fastutil.shorts.ShortList
 *  net.minecraft.core.NonNullList
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.block.machines.logic.planner;

import ic2.api.reactor.IReactorPlannerComponent;
import ic2.api.reactor.planner.SimulatedStack;
import ic2.core.block.machines.logic.planner.ComponentInfo;
import ic2.core.block.machines.logic.planner.encoder.EncoderRegistry;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.shorts.ShortArrayList;
import it.unimi.dsi.fastutil.shorts.ShortComparator;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class PlannerRegistry {
    static final Map<Item, ComponentInfo> DATA = CollectionUtils.createLinkedMap();
    static final NonNullList<ItemStack> REGISTRY = NonNullList.m_122780_((int)Short.MAX_VALUE, (Object)ItemStack.f_41583_);
    static ItemStack[] REACTOR_COMPONENTS;
    static ItemStack[] STEAM_COMPONENTS;
    static final Object2ObjectMap<IReactorPlannerComponent.ComponentType, ShortList>[] TYPES;
    static final Function<IReactorPlannerComponent.ComponentType, ShortList> CREATOR;
    static final ComponentSorter SORTER;
    static final TypeSorter TYPE_SORTER;

    public static void init() {
        List<K>[] lists = CollectionUtils.createLists(2);
        NonNullList helper = NonNullList.m_122779_();
        for (Item item : ForgeRegistries.ITEMS) {
            if (!(item instanceof IReactorPlannerComponent)) continue;
            IReactorPlannerComponent planner = (IReactorPlannerComponent)item;
            planner.provideComponents((NonNullList<ItemStack>)helper);
            if (helper.size() != 1) {
                throw new IllegalStateException("Reactor Planner Items are supposed only to have 1 ItemStack per Item");
            }
            ItemStack stack = (ItemStack)helper.get(0);
            SimulatedStack sim = planner.createSimulationComponent(stack);
            if (sim == null) {
                throw new IllegalStateException("Reactor Planner Item couldn't create a Simulated Stack");
            }
            ComponentInfo info = new ComponentInfo(sim);
            if (!((ItemStack)REGISTRY.get((int)info.getId())).m_41619_()) {
                throw new IllegalStateException("ID: " + info.getId() + ". is Already Used by " + ((ItemStack)REGISTRY.get((int)info.getId())).m_41786_().getString() + " in the Planner Registry");
            }
            stack = stack.m_41777_();
            DATA.put(stack.m_41720_(), info);
            REGISTRY.set((int)info.getId(), (Object)stack);
            IReactorPlannerComponent.ReactorType type = sim.getValidType();
            if (type == IReactorPlannerComponent.ReactorType.ELECTRIC || type == IReactorPlannerComponent.ReactorType.UNIVERSAL) {
                lists[0].add(stack);
                ((ShortList)TYPES[1].computeIfAbsent((Object)info.getType(), CREATOR)).add(info.getId());
            }
            if (type == IReactorPlannerComponent.ReactorType.STEAM || type == IReactorPlannerComponent.ReactorType.UNIVERSAL) {
                lists[1].add(stack);
                ((ShortList)TYPES[2].computeIfAbsent((Object)info.getType(), CREATOR)).add(info.getId());
            }
            ((ShortList)TYPES[0].computeIfAbsent((Object)info.getType(), CREATOR)).add(info.getId());
            helper.clear();
        }
        lists[0].sort(SORTER);
        lists[1].sort(SORTER);
        for (ShortList type : TYPES[0].values()) {
            type.sort((ShortComparator)TYPE_SORTER);
        }
        for (ShortList type : TYPES[1].values()) {
            type.sort((ShortComparator)TYPE_SORTER);
        }
        for (ShortList type : TYPES[2].values()) {
            type.sort((ShortComparator)TYPE_SORTER);
        }
        REACTOR_COMPONENTS = lists[0].toArray(new ItemStack[lists[0].size()]);
        STEAM_COMPONENTS = lists[1].toArray(new ItemStack[lists[1].size()]);
        EncoderRegistry.INSTANCE.init();
    }

    public static ComponentInfo getInfo(ItemStack stack) {
        return DATA.get(stack.m_41720_());
    }

    public static ItemStack getByID(int id) {
        return ((ItemStack)REGISTRY.get(id)).m_41777_();
    }

    public static short getId(ItemStack stack) {
        ComponentInfo info = PlannerRegistry.getInfo(stack);
        return info == null ? (short)-1 : (short)info.getId();
    }

    public static IReactorPlannerComponent.ComponentType getType(ItemStack stack) {
        ComponentInfo info = PlannerRegistry.getInfo(stack);
        return info == null ? null : info.getType();
    }

    public static int getStackSize(ItemStack stack) {
        return stack.m_41720_() instanceof IReactorPlannerComponent ? ((IReactorPlannerComponent)stack.m_41720_()).getStackSize(stack) : stack.m_41613_();
    }

    public static ItemStack setStackSize(ItemStack stack, int count) {
        if (stack.m_41720_() instanceof IReactorPlannerComponent) {
            return ((IReactorPlannerComponent)stack.m_41720_()).applyStackSize(stack, count);
        }
        stack.m_41764_(Math.min(count, stack.m_41741_()));
        return stack;
    }

    public static ItemStack[] getByType(IReactorPlannerComponent.ComponentType type, boolean steam) {
        if (type == null) {
            return steam ? STEAM_COMPONENTS : REACTOR_COMPONENTS;
        }
        ShortList list = (ShortList)TYPES[steam ? 2 : 1].get((Object)type);
        if (list == null || list.isEmpty()) {
            return new ItemStack[0];
        }
        ItemStack[] items = new ItemStack[list.size()];
        int m = list.size();
        for (int i = 0; i < m; ++i) {
            items[i] = (ItemStack)REGISTRY.get((int)list.getShort(i));
        }
        return items;
    }

    static {
        TYPES = CollectionUtils.createMaps(3, true);
        CREATOR = T -> new ShortArrayList();
        SORTER = new ComponentSorter();
        TYPE_SORTER = new TypeSorter();
    }

    public static class ComponentSorter
    implements Comparator<ItemStack> {
        @Override
        public int compare(ItemStack o1, ItemStack o2) {
            ComponentInfo a = PlannerRegistry.getInfo(o1);
            ComponentInfo b = PlannerRegistry.getInfo(o2);
            if (a == null && b == null) {
                return 0;
            }
            if (a == null || b == null) {
                return a == null ? 1 : 0;
            }
            int sorted = Integer.compare(a.getType().getIndex(), b.getType().getIndex());
            return sorted != 0 ? sorted : Integer.compare(a.getId(), b.getId());
        }
    }

    public static class TypeSorter
    implements ShortComparator {
        public int compare(short k1, short k2) {
            return SORTER.compare((ItemStack)REGISTRY.get((int)k1), (ItemStack)REGISTRY.get((int)k2));
        }
    }
}

