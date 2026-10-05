package ru.mot.ic2exfidelity.iu;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.item.reactor.ItemReactorUranium;
import ic2.core.item.reactor.ItemReactorVent;
import ic2.core.item.reactor.ItemReactorHeatSwitch;
import ic2.core.item.reactor.ItemReactorVentSpread;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/** Reactor Plus parts translated to Experimental's heat-component API. */
public final class NativeReactorItems {
    private NativeReactorItems() {}
    public static void register(DeferredRegister<Item> items) {
        String[] prefixes = {"", "adv_", "imp_", "per_"};
        int[] ventCapacity = {4000, 5000, 6000, 8000}, ventRate = {10, 14, 20, 25}, repair = {7, 11, 15, 20};
        int[] exchangeCapacity = {5000, 7500, 10000, 15000}, exchangeRate = {12, 15, 20, 25}, componentRate = {4, 5, 7, 9};
        for (int index = 0; index < 4; index++) {
            int i = index;
            put(items, prefixes[i] + "vent", () -> new ItemReactorVent(properties(), ventCapacity[i], repair[i] * ventRate[i], ventRate[i]));
            put(items, prefixes[i] + "heat_exchange", () -> new ItemReactorHeatSwitch(properties(), exchangeCapacity[i], exchangeRate[i], exchangeRate[i]));
            put(items, prefixes[i] + "component_vent", () -> new ItemReactorVentSpread(properties(), componentRate[i]));
        }
        fuel(items, "proton", 760, 6, 3);
        fuel(items, "toriy", 400, 3, 2);
        fuel(items, "americium", 640, 4.5, 2);
        fuel(items, "neptunium", 520, 3.5, 2);
        fuel(items, "curium", 800, 9.5, 3);
        fuel(items, "california", 960, 18, 3);
        fuel(items, "fermium", 1840, 26, 4);
        fuel(items, "mendelevium", 2100, 36, 4);
        fuel(items, "nobelium", 2400, 49, 4);
        fuel(items, "lawrencium", 2600, 60, 4);
        fuel(items, "berkelium", 1200, 20, 4);
        fuel(items, "einsteinium", 1440, 23, 4);
        fuel(items, "uran233", 280, 3, 1);
        fuel(items, "uranium", 200, 1.5, 1);
        fuel(items, "mox", 320, 3.5, 1);
    }
    private static Item.Properties properties() { return NativeIUContent.itemProperties().m_41487_(1); }
    private static RegistryObject<Item> put(DeferredRegister<Item> items, String name, java.util.function.Supplier<Item> factory) {
        String path = "reactorsplus/" + name;
        RegistryObject<Item> item = items.register(path, factory);
        NativeIUContent.ITEMS.put(new ResourceLocation("reactorplus", path), item);
        return item;
    }
    private static void fuel(DeferredRegister<Item> items, String name, int heat, double power, int level) {
        RegistryObject<Item> spent = put(items, "depleted_" + name + "_fuel_rod", () -> new Item(properties()));
        if (!name.equals("uranium") && !name.equals("mox")) put(items, name + "_pellet", () -> new Item(NativeIUContent.itemProperties()));
        for (int cells : new int[]{8, 16}) {
            String path = name.equals("uranium") || name.equals("mox") ? (cells == 8 ? "dual_" : "quad_") + name + "_fuel_rod" : "reactor" + name + (cells == 8 ? "dual" : "quad");
            put(items, path, () -> new Fuel(properties(), cells, heat * (cells / 8), power, level, spent));
        }
    }
    public static final class Fuel extends ItemReactorUranium {
        private final int heat;
        private final double production;
        private final RegistryObject<Item> spent;
        Fuel(Item.Properties properties, int cells, int heat, double power, int level, RegistryObject<Item> spent) {
            super(properties, cells);
            this.heat = heat;
            this.production = (cells == 8 ? 200 : 500) * power * level * 1.25;
            this.spent = spent;
        }
        @Override public void processChamber(ItemStack stack, IReactor reactor, int x, int y, boolean heatRun) {
            if (!reactor.produceEnergy()) return;
            if (!heatRun) {
                reactor.addOutput((float)(production / 5));
                if (getUse(stack) >= getMaxUse() - 1) reactor.setItemAt(x, y, new ItemStack(spent.get()));
                else incrementUse(stack);
                return;
            }
            List<int[]> acceptors = new ArrayList<>();
            for (int[] offset : new int[][]{{-1,0},{1,0},{0,-1},{0,1}}) {
                ItemStack adjacent = reactor.getItemAt(x + offset[0], y + offset[1]);
                if (adjacent != null && adjacent.m_41720_() instanceof IReactorComponent component && component.canStoreHeat(adjacent, reactor, x + offset[0], y + offset[1])) acceptors.add(offset);
            }
            int remaining = heat;
            for (int i = 0; i < acceptors.size(); i++) {
                int[] offset = acceptors.get(i);
                ItemStack adjacent = reactor.getItemAt(x + offset[0], y + offset[1]);
                int share = remaining / (acceptors.size() - i);
                remaining -= share;
                remaining += ((IReactorComponent)adjacent.m_41720_()).alterHeat(adjacent, reactor, x + offset[0], y + offset[1], share);
            }
            if (remaining > 0) reactor.addHeat(remaining);
        }
        @Override public boolean acceptUraniumPulse(ItemStack stack, IReactor reactor, ItemStack other, int x, int y, int sourceX, int sourceY, boolean heatRun) { return true; }
        @Override public void m_7373_(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
            super.m_7373_(stack, world, tooltip, flag);
            tooltip.add(Component.m_237110_("iu_native.fuel.info", production, heat));
        }
    }
}
