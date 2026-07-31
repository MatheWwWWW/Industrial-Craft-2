/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.FloatTag
 *  net.minecraft.nbt.IntTag
 *  net.minecraft.nbt.NumericTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.util.Mth
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.reactor;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.api.reactor.planner.SimulatedStack;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.text.DecimalFormat;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public interface IReactorPlannerComponent
extends IReactorComponent {
    public static final DecimalFormat FORMAT = new DecimalFormat("###,###.##");
    public static final NumericTag NULL_VALUE = IntTag.m_128679_((int)0);

    default public ItemStack applyStackSize(ItemStack newStack, int size) {
        newStack.m_41764_(Mth.m_14045_((int)size, (int)0, (int)newStack.m_41741_()));
        return newStack;
    }

    default public int getStackSize(ItemStack input) {
        return input.m_41613_();
    }

    public void provideComponents(NonNullList<ItemStack> var1);

    public short getComponentID(ItemStack var1);

    public SimulatedStack createSimulationComponent(ItemStack var1);

    default public void addToolTip(ItemStack stack, Consumer<Component> tooltips) {
    }

    public void addAffectedSlots(int var1, int var2, BiPredicate<Integer, Integer> var3);

    public ReactorType getSupportedReactor(ItemStack var1);

    public ComponentType getType(ItemStack var1);

    public List<ReactorStat> getStats(ItemStack var1);

    public NumericTag getReactorStat(ReactorStat var1, ItemStack var2);

    default public NumericTag getReactorStat(ReactorStat stat, ItemStack stack, IReactor planner, int x, int y) {
        return this.getReactorStat(stat, stack);
    }

    public static enum ReactorStat {
        HEAT_PRODUCTION(false, "gui.ic2.reactor_planner.stat.heat_production"),
        ENERGY_PRODUCTION(true, "gui.ic2.reactor_planner.stat.energy_production"),
        STEAM_PRODUCTION(true, "gui.ic2.reactor_planner.stat.steam_production"),
        ROD_COUNT(false, "gui.ic2.reactor_planner.stat.rod_count"),
        PULSE_COUNT(false, "gui.ic2.reactor_planner.stat.pulse_count"),
        SELF_COOLING(false, "gui.ic2.reactor_planner.stat.self_cooling"),
        REACTOR_COOLING(false, "gui.ic2.reactor_planner.stat.reactor_cooling"),
        PART_COOLING(false, "gui.ic2.reactor_planner.stat.part_cooling"),
        PART_BALANCING(false, "gui.ic2.reactor_planner.stat.part_balance"),
        REACTOR_BALANCING(false, "gui.ic2.reactor_planner.stat.reactor_balance"),
        HEAT_STORAGE(false, "gui.ic2.reactor_planner.stat.heat_storage"),
        MAX_HEAT_STORAGE(false, "gui.ic2.reactor_planner.stat.max_heat_storage"),
        REACTOR_HEAT_EFFECT_MODIFIER(true, "gui.ic2.reactor_planner.stat.reactor_hem"),
        REACTOR_HEAT_EFFECT_MULTIPLIER(true, "gui.ic2.reactor_planner.stat.reactor_hem_mul"),
        MAX_COMPONENT_DURABILITY(false, "gui.ic2.reactor_planner.stat.max_durability"),
        RECHARGEABLE(false, "gui.ic2.reactor_planner.stat.rechargeable"),
        ENERGY_USAGE(true, "gui.ic2.reactor_planner.stat.energy_usage"),
        WATER_CONSUMPTION(true, "gui.ic2.reactor_planner.stat.water_consumption"),
        WATER_STORAGE(true, "gui.ic2.reactor_planner.stat.water_storage");

        boolean isFloat;
        String name;

        private ReactorStat(boolean isFloat, String name) {
            this.isFloat = isFloat;
            this.name = name;
        }

        public NumericTag createStat(Number nr) {
            if (this.isFloat) {
                return FloatTag.m_128566_((float)nr.floatValue());
            }
            return IntTag.m_128679_((int)nr.intValue());
        }

        public boolean isFloat() {
            return this.isFloat;
        }

        public Component getName(NumericTag nbt) {
            return Component.m_237110_((String)this.name, (Object[])new Object[]{this.isFloat ? FORMAT.format(nbt.m_7061_()) : FORMAT.format(nbt.m_7046_())});
        }
    }

    public static class ComponentType {
        static final List<ComponentType> TYPES = ObjectLists.synchronize(CollectionUtils.createList());
        public static final ComponentType FUEL_ROD = new ComponentType("gui.ic2.reactor_planner.component.fuel_rod");
        public static final ComponentType COOLANT_CELL = new ComponentType("gui.ic2.reactor_planner.component.coolant_cell");
        public static final ComponentType CONDENSATOR = new ComponentType("gui.ic2.reactor_planner.component.condensator");
        public static final ComponentType HEAT_PACK = new ComponentType("gui.ic2.reactor_planner.component.heat_pack");
        public static final ComponentType HEAT_VENT = new ComponentType("gui.ic2.reactor_planner.component.heat_vent");
        public static final ComponentType HEAT_SPREAD = new ComponentType("gui.ic2.reactor_planner.component.heat_spread");
        public static final ComponentType HEAT_EXCHANGER = new ComponentType("gui.ic2.reactor_planner.component.heat_exchanger");
        public static final ComponentType HEAT_PUMP = new ComponentType("gui.ic2.reactor_planner.component.heat_pump");
        public static final ComponentType PLATING = new ComponentType("gui.ic2.reactor_planner.component.plating");
        public static final ComponentType REFLECTORS = new ComponentType("gui.ic2.reactor_planner.component.reflectors");
        public static final ComponentType ISOTOPE_CELL = new ComponentType("gui.ic2.reactor_planner.component.isotope_cell");
        public static final ComponentType UNDEFINED = new ComponentType("gui.ic2.reactor_planner.component.undefined");
        int index;
        Component name;

        public ComponentType(String name) {
            this.name = Component.m_237115_((String)name);
            this.index = TYPES.size();
            TYPES.add(this);
        }

        public Component getName() {
            return this.name;
        }

        public int getIndex() {
            return this.index;
        }

        public static int size() {
            return TYPES.size();
        }

        public static ComponentType byID(int index) {
            return TYPES.get(index % TYPES.size());
        }
    }

    public static enum ReactorType {
        ELECTRIC,
        STEAM,
        UNIVERSAL;

    }
}

