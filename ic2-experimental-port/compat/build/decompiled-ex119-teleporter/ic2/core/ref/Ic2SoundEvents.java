/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.sounds.SoundEvent
 */
package ic2.core.ref;

import ic2.core.IC2;
import net.minecraft.sounds.SoundEvent;

public class Ic2SoundEvents {
    public static final SoundEvent ITEM_TREETAP_USE = Ic2SoundEvents.register("item.treetap.use");
    public static final SoundEvent ITEM_WRENCH_USE = Ic2SoundEvents.register("item.wrench.use");
    public static final SoundEvent ITEM_CUTTER_USE = Ic2SoundEvents.register("item.cutter.use");
    public static final SoundEvent ITEM_PAINTER_USE = Ic2SoundEvents.register("item.painter.use");
    public static final SoundEvent ITEM_ELECTRIC_SHUTDOWN = Ic2SoundEvents.register("item.electric.shutdown");
    public static final SoundEvent ITEM_BATTERY_USE = Ic2SoundEvents.register("item.battery.use");
    public static final SoundEvent ITEM_TREETAP_ELECTRIC_USE = Ic2SoundEvents.register("item.treetap.electric.use");
    public static final SoundEvent ITEM_CHAINSAW_IDLE = Ic2SoundEvents.register("item.chainsaw.idle");
    public static final SoundEvent ITEM_CHAINSAW_STOP = Ic2SoundEvents.register("item.chainsaw.stop");
    public static final SoundEvent ITEM_CHAINSAW_USE1 = Ic2SoundEvents.register("item.chainsaw.use1");
    public static final SoundEvent ITEM_CHAINSAW_USE2 = Ic2SoundEvents.register("item.chainsaw.use2");
    public static final SoundEvent ITEM_DRILL_IDLE = Ic2SoundEvents.register("item.drill.idle");
    public static final SoundEvent ITEM_DRILL_HARD = Ic2SoundEvents.register("item.drill.hard");
    public static final SoundEvent ITEM_DRILL_SOFT = Ic2SoundEvents.register("item.drill.soft");
    public static final SoundEvent ITEM_LASER_SHOOT = Ic2SoundEvents.register("item.laser.shoot");
    public static final SoundEvent ITEM_LASER_EXPLOSIVE = Ic2SoundEvents.register("item.laser.explosive");
    public static final SoundEvent ITEM_LASER_LONG_RANGE = Ic2SoundEvents.register("item.laser.long_range");
    public static final SoundEvent ITEM_LASER_LOW_FOCUS = Ic2SoundEvents.register("item.laser.low_focus");
    public static final SoundEvent ITEM_LASER_SCATTER = Ic2SoundEvents.register("item.laser.scatter");
    public static final SoundEvent ITEM_NANOSABER_IDLE = Ic2SoundEvents.register("item.nanosaber.idle");
    public static final SoundEvent ITEM_NANOSABER_POWER_UP = Ic2SoundEvents.register("item.nanosaber.power_up");
    public static final SoundEvent ITEM_NANOSABER_SWING1 = Ic2SoundEvents.register("item.nanosaber.swing1");
    public static final SoundEvent ITEM_NANOSABER_SWING2 = Ic2SoundEvents.register("item.nanosaber.swing2");
    public static final SoundEvent ITEM_NANOSABER_SWING3 = Ic2SoundEvents.register("item.nanosaber.swing3");
    public static final SoundEvent ITEM_SCANNER_USE = Ic2SoundEvents.register("item.scanner.use");
    public static final SoundEvent GENERATOR_GENERATOR_LOOP = Ic2SoundEvents.register("generator.generator.loop");
    public static final SoundEvent GENERATOR_GEOTHERMAL_LOOP = Ic2SoundEvents.register("generator.geothermal.loop");
    public static final SoundEvent GENERATOR_WATER_LOOP = Ic2SoundEvents.register("generator.water.loop");
    public static final SoundEvent GENERATOR_WIND_LOOP = Ic2SoundEvents.register("generator.wind.loop");
    public static final SoundEvent GENERATOR_NUCLEAR_LOOP = Ic2SoundEvents.register("generator.nuclear.loop");
    public static final SoundEvent GENERATOR_NUCLEAR_LOW_POWER = Ic2SoundEvents.register("generator.nuclear.power.low");
    public static final SoundEvent GENERATOR_NUCLEAR_MEDIUM_POWER = Ic2SoundEvents.register("generator.nuclear.power.medium");
    public static final SoundEvent GENERATOR_NUCLEAR_HIGH_POWER = Ic2SoundEvents.register("generator.nuclear.power.high");
    public static final SoundEvent MACHINE_OVERLOAD = Ic2SoundEvents.register("machine.overload");
    public static final SoundEvent MACHINE_INTERRUPT1 = Ic2SoundEvents.register("machine.interrupt1");
    public static final SoundEvent MACHINE_CANNER_OPERATE = Ic2SoundEvents.register("machine.canner.operate");
    public static final SoundEvent MACHINE_CANNER_REVERSE = Ic2SoundEvents.register("machine.canner.reverse");
    public static final SoundEvent MACHINE_COMPRESSOR_OPERATE = Ic2SoundEvents.register("machine.compressor.operate");
    public static final SoundEvent MACHINE_ELECTROLYZER_LOOP = Ic2SoundEvents.register("machine.electrolyzer.loop");
    public static final SoundEvent MACHINE_EXTRACTOR_OPERATE = Ic2SoundEvents.register("machine.extractor.operate");
    public static final SoundEvent MACHINE_FABRICATOR_LOOP = Ic2SoundEvents.register("machine.fabricator.loop");
    public static final SoundEvent MACHINE_FABRICATOR_SCRAP = Ic2SoundEvents.register("machine.fabricator.scrap");
    public static final SoundEvent MACHINE_FURNACE_ELECTRIC_START = Ic2SoundEvents.register("machine.furnace.electric.start");
    public static final SoundEvent MACHINE_FURNACE_ELECTRIC_STOP = Ic2SoundEvents.register("machine.furnace.electric.stop");
    public static final SoundEvent MACHINE_FURNACE_ELECTRIC_LOOP = Ic2SoundEvents.register("machine.furnace.electric.loop");
    public static final SoundEvent MACHINE_FURNACE_INDUCTION_START = Ic2SoundEvents.register("machine.furnace.induction.start");
    public static final SoundEvent MACHINE_FURNACE_INDUCTION_STOP = Ic2SoundEvents.register("machine.furnace.induction.stop");
    public static final SoundEvent MACHINE_FURNACE_INDUCTION_LOOP = Ic2SoundEvents.register("machine.furnace.induction.loop");
    public static final SoundEvent MACHINE_MACERATOR_OPERATE = Ic2SoundEvents.register("machine.macerator.operate");
    public static final SoundEvent MACHINE_PUMP_OPERATE = Ic2SoundEvents.register("machine.pump.operate");
    public static final SoundEvent MACHINE_RECYCLER_OPERATE = Ic2SoundEvents.register("machine.recycler.operate");
    public static final SoundEvent BLOCK_NUKE_EXPLODE = Ic2SoundEvents.register("block.nuke.explode");

    public static void init() {
    }

    private static SoundEvent register(String string) {
        return IC2.envProxy.registerSoundEvent(string);
    }
}

