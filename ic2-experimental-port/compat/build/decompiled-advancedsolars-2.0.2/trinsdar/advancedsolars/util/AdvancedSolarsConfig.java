/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.common.ForgeConfigSpec
 *  net.minecraftforge.common.ForgeConfigSpec$Builder
 *  net.minecraftforge.common.ForgeConfigSpec$DoubleValue
 *  net.minecraftforge.common.ForgeConfigSpec$EnumValue
 *  net.minecraftforge.common.ForgeConfigSpec$IntValue
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber$Bus
 *  net.minecraftforge.fml.config.ModConfig
 *  net.minecraftforge.fml.event.config.ModConfigEvent
 *  org.apache.commons.lang3.tuple.Pair
 */
package trinsdar.advancedsolars.util;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

@Mod.EventBusSubscriber(modid="advanced_solars", bus=Mod.EventBusSubscriber.Bus.MOD)
public class AdvancedSolarsConfig {
    public static final PowerGeneration POWER_GENERATION = new PowerGeneration();
    public static final PowerValues POWER_VALUES = new PowerValues();
    public static final Misc MISC = new Misc();
    public static final EnabledItems ENABLED_ITEMS = new EnabledItems();
    public static final CommonConfig COMMON_CONFIG;
    public static final ForgeConfigSpec COMMON_SPEC;

    @SubscribeEvent
    public static void onModConfigEvent(ModConfigEvent e) {
        AdvancedSolarsConfig.onModConfigEvent(e.getConfig());
    }

    public static void onModConfigEvent(ModConfig e) {
        if (e.getModId().equals("advanced_solars") && e.getSpec() == COMMON_SPEC) {
            AdvancedSolarsConfig.bakeCommonConfig();
        }
    }

    private static void bakeCommonConfig() {
        AdvancedSolarsConfig.POWER_GENERATION.ADVANCED_SOLAR_GENERATION_MULTIPLIER = (Double)AdvancedSolarsConfig.COMMON_CONFIG.ADVANCED_SOLAR_GENERATION_MULTIPLIER.get();
        AdvancedSolarsConfig.POWER_GENERATION.HYBRID_SOLAR_GENERATION_MULTIPLIER = (Double)AdvancedSolarsConfig.COMMON_CONFIG.HYBRID_SOLAR_GENERATION_MULTIPLIER.get();
        AdvancedSolarsConfig.POWER_GENERATION.ULTIMATE_HYBRID_SOLAR_GENERATION_MULTIPLIER = (Double)AdvancedSolarsConfig.COMMON_CONFIG.ULTIMATE_HYBRID_SOLAR_GENERATION_MULTIPLIER.get();
        AdvancedSolarsConfig.POWER_VALUES.ADVANCED_SOLAR_HELMET_STORAGE = (Integer)AdvancedSolarsConfig.COMMON_CONFIG.ADVANCED_SOLAR_HELMET_STORAGE.get();
        AdvancedSolarsConfig.POWER_VALUES.HYBRID_SOLAR_HELMET_STORAGE = (Integer)AdvancedSolarsConfig.COMMON_CONFIG.HYBRID_SOLAR_HELMET_STORAGE.get();
        AdvancedSolarsConfig.POWER_VALUES.ULTIMATE_HYBRID_SOLAR_HELMET_STORAGE = (Integer)AdvancedSolarsConfig.COMMON_CONFIG.ULTIMATE_HYBRID_SOLAR_HELMET_STORAGE.get();
        AdvancedSolarsConfig.POWER_VALUES.ADVANCED_SOLAR_HELMET_TRANSFER = (Integer)AdvancedSolarsConfig.COMMON_CONFIG.ADVANCED_SOLAR_HELMET_TRANSFER.get();
        AdvancedSolarsConfig.POWER_VALUES.HYBRID_SOLAR_HELMET_TRANSFER = (Integer)AdvancedSolarsConfig.COMMON_CONFIG.HYBRID_SOLAR_HELMET_TRANSFER.get();
        AdvancedSolarsConfig.POWER_VALUES.ULTIMATE_HYBRID_SOLAR_HELMET_TRANSFER = (Integer)AdvancedSolarsConfig.COMMON_CONFIG.ULTIMATE_HYBRID_SOLAR_HELMET_TRANSFER.get();
        AdvancedSolarsConfig.MISC.INGOT_IN_IRRADIANT_URANIUM = (Misc.CenterIngot)((Object)AdvancedSolarsConfig.COMMON_CONFIG.INGOT_IN_IRRADIANT_URANIUM.get());
    }

    static {
        Pair COMMON_PAIR = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        COMMON_CONFIG = (CommonConfig)COMMON_PAIR.getLeft();
        COMMON_SPEC = (ForgeConfigSpec)COMMON_PAIR.getRight();
    }

    public static class PowerGeneration {
        public Double ADVANCED_SOLAR_GENERATION_MULTIPLIER;
        public Double HYBRID_SOLAR_GENERATION_MULTIPLIER;
        public Double ULTIMATE_HYBRID_SOLAR_GENERATION_MULTIPLIER;
    }

    public static class CommonConfig {
        public final ForgeConfigSpec.IntValue ADVANCED_SOLAR_HELMET_STORAGE;
        public final ForgeConfigSpec.IntValue HYBRID_SOLAR_HELMET_STORAGE;
        public final ForgeConfigSpec.IntValue ULTIMATE_HYBRID_SOLAR_HELMET_STORAGE;
        public final ForgeConfigSpec.IntValue ADVANCED_SOLAR_HELMET_TRANSFER;
        public final ForgeConfigSpec.IntValue HYBRID_SOLAR_HELMET_TRANSFER;
        public final ForgeConfigSpec.IntValue ULTIMATE_HYBRID_SOLAR_HELMET_TRANSFER;
        public final ForgeConfigSpec.DoubleValue ADVANCED_SOLAR_GENERATION_MULTIPLIER;
        public final ForgeConfigSpec.DoubleValue HYBRID_SOLAR_GENERATION_MULTIPLIER;
        public final ForgeConfigSpec.DoubleValue ULTIMATE_HYBRID_SOLAR_GENERATION_MULTIPLIER;
        public final ForgeConfigSpec.EnumValue<Misc.CenterIngot> INGOT_IN_IRRADIANT_URANIUM;

        public CommonConfig(ForgeConfigSpec.Builder builder) {
            builder.push("PowerGeneration");
            this.ADVANCED_SOLAR_GENERATION_MULTIPLIER = builder.comment("Base energy generation multiplier values for advanced solar - increase them for higher yields.").translation("advanced_solarsconfig.advanced_solar_generation_multiplier").defineInRange("ADVANCED_SOLAR_GENERATION_MULTIPLIER", 1.0, 0.0, 4.0);
            this.HYBRID_SOLAR_GENERATION_MULTIPLIER = builder.comment("Base energy generation multiplier values for hybrid solar - increase them for higher yields.").translation("advanced_solarsconfig.hybrid_solar_generation_multiplier").defineInRange("HYBRID_SOLAR_GENERATION_MULTIPLIER", 1.0, 0.0, 4.0);
            this.ULTIMATE_HYBRID_SOLAR_GENERATION_MULTIPLIER = builder.comment("Base energy generation multiplier values for ultimate hybrid solar - increase them for higher yields.").translation("advanced_solarsconfig.ultimate_hybrid_solar_generation_multiplier").defineInRange("ULTIMATE_HYBRID_SOLAR_GENERATION_MULTIPLIER", 1.0, 0.0, 4.0);
            builder.pop();
            builder.push("PowerValues");
            this.ADVANCED_SOLAR_HELMET_STORAGE = builder.defineInRange("ADVANCED_SOLAR_HELMET_STORAGE", 100000, 1, Integer.MAX_VALUE);
            this.HYBRID_SOLAR_HELMET_STORAGE = builder.defineInRange("HYBRID_SOLAR_HELMET_STORAGE", 1000000, 1, Integer.MAX_VALUE);
            this.ULTIMATE_HYBRID_SOLAR_HELMET_STORAGE = builder.defineInRange("ULTIMATE_HYBRID_SOLAR_HELMET_STORAGE", 10000000, 1, Integer.MAX_VALUE);
            this.ADVANCED_SOLAR_HELMET_TRANSFER = builder.defineInRange("ADVANCED_SOLAR_HELMET_TRANSFER", 100, 1, Integer.MAX_VALUE);
            this.HYBRID_SOLAR_HELMET_TRANSFER = builder.defineInRange("HYBRID_SOLAR_HELMET_TRANSFER", 1000, 1, Integer.MAX_VALUE);
            this.ULTIMATE_HYBRID_SOLAR_HELMET_TRANSFER = builder.defineInRange("ULTIMATE_HYBRID_SOLAR_HELMET_TRANSFER", 4000, 1, Integer.MAX_VALUE);
            builder.pop();
            builder.push("Misc");
            this.INGOT_IN_IRRADIANT_URANIUM = builder.comment(new String[]{"Determines what ingot is used in the center of the irradiant uranium recipe", "If the selected option does not exist it will fall back to the default choice EnderPearl", "Items associated with values: ENDERPEARL_URANIUM: Ic2c enderpearl enriched uranium,", "URANIUM: #forge:ingots/uranium, URANIUM235: #forge:ingots/uranium235, URANIUM233: #forge:ingots/uranium235"}).translation("advanced_solarsconfig.ingot_in_irradiant_uranium").defineEnum("INGOT_IN_IRRADIANT_URANIUM", (Enum)Misc.CenterIngot.ENDERPEARL_URANIUM);
            builder.pop();
        }
    }

    public static class PowerValues {
        public int ADVANCED_SOLAR_HELMET_STORAGE;
        public int HYBRID_SOLAR_HELMET_STORAGE;
        public int ULTIMATE_HYBRID_SOLAR_HELMET_STORAGE;
        public int ADVANCED_SOLAR_HELMET_TRANSFER;
        public int HYBRID_SOLAR_HELMET_TRANSFER;
        public int ULTIMATE_HYBRID_SOLAR_HELMET_TRANSFER;
    }

    public static class Misc {
        public CenterIngot INGOT_IN_IRRADIANT_URANIUM = CenterIngot.ENDERPEARL_URANIUM;

        static enum CenterIngot {
            ENDERPEARL_URANIUM,
            URANIUM,
            URANIUM235,
            URANIUM233;

        }
    }

    public static class EnabledItems {
        public boolean enableAdvancedSolarHelmet = true;
        public boolean enableHybridSolarHelmet = true;
        public boolean enableUltimateHybridSolarHelmet = true;
        public boolean enableAdvancedSolarPanel = true;
        public boolean enableHybridSolarPanel = true;
        public boolean enableUltimateHybridSolarPanel = true;
        public boolean enableMiscCraftingItems = true;
    }
}

