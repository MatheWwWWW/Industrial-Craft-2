package ru.mot.ic2exfidelity.iu;

import ic2.core.fluid.Ic2FluidStack;
import ic2.core.fluid.StandardFluidItem;
import ic2.core.gui.dynamic.GuiParser;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

/** Runs only in the launcher's disposable copied world, before installation. */
public final class NativeIUChecks {
    private NativeIUChecks() {}
    private static void require(boolean value, String message) { if (!value) throw new IllegalStateException("Native IU check: " + message); }
    private static void equal(double actual, double expected, String message) { require(Math.abs(actual - expected) < .00001, message + " (" + actual + ", expected " + expected + ")"); }
    public static void run(ServerLevel level) {
        require(!ModList.get().isLoaded("industrialupgrade"), "Industrial Upgrade must be absent");
        require(level.m_7465_().m_44073_().filter(id -> id.m_135815_().startsWith("native/") && java.util.Set.of("diamondvein", "powerutils", "quantumgenerators", "reactorplus", "simplyquarries", "wateringcan").contains(id.m_135827_())).count() == 97, "all 97 native recipes loaded");
        var quadRecipe = (net.minecraft.world.item.crafting.ShapedRecipe)level.m_7465_().m_44043_(new ResourceLocation("reactorplus:native/uranium_quad")).orElseThrow();
        var grid = new net.minecraft.world.inventory.CraftingContainer(new net.minecraft.world.inventory.AbstractContainerMenu(null, -1) {
            @Override public ItemStack m_7648_(net.minecraft.world.entity.player.Player player, int slot) { return ItemStack.f_41583_; }
            @Override public boolean m_6875_(net.minecraft.world.entity.player.Player player) { return true; }
        }, 3, 3);
        ItemStack usedRod = new ItemStack(NativeIUContent.ITEMS.get(new ResourceLocation("reactorplus", "reactorsplus/dual_uranium_fuel_rod")).get());
        usedRod.m_41784_().m_128405_("use", 19000);
        grid.m_6836_(3, usedRod); grid.m_6836_(5, usedRod.m_41777_());
        ItemStack crafted = quadRecipe.m_5874_(grid);
        require(crafted.m_41783_() != null && crafted.m_41783_().m_128451_("use") == 19000, "crafting cannot refresh fuel usage");
        int offset = 0;
        for (Map.Entry<ResourceLocation, NativeIUContent.Machine> entry : NativeIUContent.MACHINES.entrySet()) {
            BlockPos pos = new BlockPos(64 + offset++ * 32, level.m_151558_() - 3, 64);
            level.m_46745_(pos);
            var state = ForgeRegistries.BLOCKS.getValue(entry.getKey()).m_49966_();
            level.m_7731_(pos, Blocks.f_50016_.m_49966_(), 3);
            require(level.m_7731_(pos, state, 3), "place " + entry.getKey());
            NativeMachine machine = (NativeMachine)level.m_7702_(pos);
            GuiParser.parse(entry.getKey(), NativeMachine.class);
            if (machine.kind("fe")) {
                machine.energy.addEnergy(100);
                machine.updateEntityServer();
                equal(machine.forgeEnergy, 400, "EU to FE");
                equal(machine.energy.getEnergy(), 0, "EU debit");
                IEnergyStorage storage = machine.getCapability(ForgeCapabilities.ENERGY).orElseThrow(() -> new IllegalStateException("Missing FE capability"));
                require(storage.extractEnergy(100, true) == 100 && machine.forgeEnergy == 400, "FE extraction simulation");
                require(storage.extractEnergy(-1, false) == 0 && machine.forgeEnergy == 400, "negative FE extraction");
                machine.onNetworkEvent(0);
                require(storage.receiveEnergy(40, true) == 40 && machine.forgeEnergy == 400, "FE receiving simulation");
                machine.updateEntityServer();
                equal(machine.energy.getEnergy(), 100, "FE to EU");
                equal(machine.forgeEnergy, 0, "FE debit");
            } else if (machine.kind("qe")) {
                machine.energy.addEnergy(160);
                machine.updateEntityServer();
                equal(machine.quantum, 10, "EU to QE");
                machine.onNetworkEvent(0);
                machine.updateEntityServer();
                equal(machine.energy.getEnergy(), 100, "QE conversion loss");
                equal(machine.quantum, 0, "QE debit");
            } else if (machine.kind("generator")) {
                machine.updateEntityServer();
                equal(machine.quantum, machine.generation(), "generator output");
                machine.installedCores = 8;
                machine.updateEntityServer();
                equal(machine.quantum, 5 * Math.pow(3, entry.getValue().tier() - 1) / 16 * 4, "eight-core output");
            } else {
                int top = level.m_151558_() - 5;
                machine.minY = top; machine.maxY = top;
                BlockPos ore = new BlockPos((pos.m_123341_() >> 4) * 16, top, (pos.m_123343_() >> 4) * 16);
                level.m_7731_(ore, Blocks.f_50089_.m_49966_(), 3);
                machine.energy.addEnergy(100000);
                for (int i = 0; i < machine.output.size(); i++) machine.output.put(i, new ItemStack(Blocks.f_50069_, 64));
                machine.updateEntityServer();
                require(machine.cursor == 0 && level.m_8055_(ore).m_60734_() == Blocks.f_50089_, "quarry full buffer preserves ore and cursor");
                equal(machine.energy.getEnergy(), 100000, "full buffer preserves EU");
                machine.output.clear();
                machine.updateEntityServer();
                require(level.m_8055_(ore).m_60795_(), "quarry mines ore");
                require(!machine.output.isEmpty(), "quarry keeps drops");
                level.m_7731_(ore, Blocks.f_50016_.m_49966_(), 3);
            }
            CompoundTag saved = machine.m_187482_();
            NativeMachine restored = new NativeMachine(pos, state);
            restored.m_142466_(saved);
            equal(restored.quantum, machine.quantum, "persistent QE");
            equal(restored.forgeEnergy, machine.forgeEnergy, "persistent FE");
            require(restored.installedCores == machine.installedCores && restored.cursor == machine.cursor && restored.forward == machine.forward, "persistent machine settings");
            ItemStack drop = machine.adjustDrop(new ItemStack(state.m_60734_()), true);
            NativeMachine placed = new NativeMachine(pos, state);
            placed.onPlaced(drop, null, net.minecraft.core.Direction.UP);
            equal(placed.quantum, machine.quantum, "wrench preserves QE");
            require(placed.installedCores == machine.installedCores, "wrench preserves cores");
            level.m_7731_(pos, Blocks.f_50016_.m_49966_(), 3);
        }
        for (String id : new String[]{"simple_watering_can", "adv_watering_can", "imp_watering_can"}) {
            NativeWateringCan can = (NativeWateringCan)NativeIUContent.ITEMS.get(new ResourceLocation("wateringcan", id)).get();
            ItemStack stack = new ItemStack(can);
            require(!can.canFill(stack, Ic2FluidStack.create(net.minecraft.world.level.material.Fluids.f_76195_, 1000)), "cans reject lava");
            StandardFluidItem.setFs(stack, Ic2FluidStack.create(net.minecraft.world.level.material.Fluids.f_76193_, can.getCapacityMb(stack)));
            can.drainMb(stack, 100, false, null);
            equal(Ic2FluidStack.get(stack).getAmountMb(), can.getCapacityMb(stack) - 100, "watering water debit");
        }
        checkWaterGrowth(level);
        checkReactor();
        System.out.println("[IC2-NATIVE-IU-CHECK] passed=true machines=" + offset + " recipes=97 conversion=FE,QE persistence=true wrench=true quarryDrops=true fullBuffer=true menus=true waterGrowth=true reactor=true IU=false");
    }
    private static void checkWaterGrowth(ServerLevel level) {
        BlockPos pos = new BlockPos(64, level.m_151558_() - 3, 96);
        level.m_46745_(pos);
        level.m_7731_(pos, Blocks.f_50016_.m_49966_(), 3);
        level.m_7731_(pos.m_7495_(), ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft:farmland")).m_49966_(), 3);
        level.m_7731_(pos, ic2.core.ref.Ic2Blocks.WHEAT_CROP.m_49966_(), 3);
        var crop = (ic2.core.crop.TileEntityCrop)level.m_7702_(pos);
        crop.setCurrentAge(1);
        int duration = crop.getCrop().getGrowthDuration(crop);
        crop.setGrowthPoints(duration - 1);
        NativeWateringCan can = (NativeWateringCan)NativeIUContent.ITEMS.get(new ResourceLocation("wateringcan", "simple_watering_can")).get();
        ItemStack stack = new ItemStack(can);
        StandardFluidItem.setFs(stack, Ic2FluidStack.create(net.minecraft.world.level.material.Fluids.f_76193_, 1000));
        var player = net.minecraftforge.common.util.FakePlayerFactory.getMinecraft(level);
        player.m_20260_(true);
        player.m_21008_(net.minecraft.world.InteractionHand.MAIN_HAND, stack);
        var context = new net.minecraft.world.item.context.UseOnContext(player, net.minecraft.world.InteractionHand.MAIN_HAND, new net.minecraft.world.phys.BlockHitResult(net.minecraft.world.phys.Vec3.m_82512_(pos), net.minecraft.core.Direction.UP, pos, false));
        can.onItemUseFirst(stack, context);
        require(crop.getCurrentAge() == 2 && crop.getGrowthPoints() == 0, "watering advances a growth stage");
        equal(Ic2FluidStack.get(stack).getAmountMb(), 900, "growth costs exactly 100 mB");
        player.m_20260_(false);
        player.m_21008_(net.minecraft.world.InteractionHand.MAIN_HAND, ItemStack.f_41583_);
        level.m_7731_(pos, Blocks.f_50016_.m_49966_(), 3);
        level.m_7731_(pos.m_7495_(), Blocks.f_50016_.m_49966_(), 3);
    }
    private static void checkReactor() {
        double[] power = {0};
        int[] heat = {0};
        ItemStack[] depleted = {ItemStack.f_41583_};
        var reactor = (ic2.api.reactor.IReactor)java.lang.reflect.Proxy.newProxyInstance(NativeIUChecks.class.getClassLoader(), new Class<?>[]{ic2.api.reactor.IReactor.class}, (proxy, method, args) -> {
            return switch (method.getName()) {
                case "produceEnergy" -> true;
                case "getHeat" -> heat[0];
                case "getMaxHeat" -> 10000;
                case "addHeat" -> heat[0] += (Integer)args[0];
                case "setHeat" -> { heat[0] = (Integer)args[0]; yield null; }
                case "addOutput" -> { power[0] += (Float)args[0]; yield (float)power[0]; }
                case "getItemAt" -> ItemStack.f_41583_;
                case "setItemAt" -> { depleted[0] = (ItemStack)args[2]; yield null; }
                case "addEmitHeat" -> null;
                default -> null;
            };
        });
        var fuel = (NativeReactorItems.Fuel)NativeIUContent.ITEMS.get(new ResourceLocation("reactorplus", "reactorsplus/dual_uranium_fuel_rod")).get();
        ItemStack rod = new ItemStack(fuel);
        fuel.processChamber(rod, reactor, 0, 0, false);
        equal(power[0] * 5, 375, "enhanced uranium production");
        fuel.processChamber(rod, reactor, 0, 0, true);
        equal(heat[0], 200, "enhanced uranium heat");
        fuel.setUse(rod, 19999);
        fuel.processChamber(rod, reactor, 0, 0, false);
        require(depleted[0].m_41720_() == NativeIUContent.ITEMS.get(new ResourceLocation("reactorplus", "reactorsplus/depleted_uranium_fuel_rod")).get(), "fuel depletes after 20,000 cycles");
        var vent = (ic2.api.reactor.IReactorComponent)NativeIUContent.ITEMS.get(new ResourceLocation("reactorplus", "reactorsplus/vent")).get();
        ItemStack ventStack = new ItemStack((net.minecraft.world.item.Item)vent);
        require(vent.canStoreHeat(ventStack, reactor, 0, 0), "native reactor accepts vent");
        vent.alterHeat(ventStack, reactor, 0, 0, 100);
        int before = vent.getCurrentHeat(ventStack, reactor, 0, 0);
        vent.processChamber(ventStack, reactor, 0, 0, true);
        require(vent.getCurrentHeat(ventStack, reactor, 0, 0) < before, "native vent cools stored heat");
    }
}
