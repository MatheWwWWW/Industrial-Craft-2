/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.common.ForgeConfigSpec
 *  net.minecraftforge.common.ForgeConfigSpec$BooleanValue
 *  net.minecraftforge.common.ForgeConfigSpec$Builder
 *  net.minecraftforge.common.ForgeConfigSpec$EnumValue
 *  net.minecraftforge.common.ForgeConfigSpec$IntValue
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber$Bus
 *  net.minecraftforge.fml.config.ModConfig
 *  net.minecraftforge.fml.event.config.ModConfigEvent
 *  org.apache.commons.lang3.tuple.Pair
 */
package trinsdar.gravisuit.util;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

@Mod.EventBusSubscriber(modid="gravisuit", bus=Mod.EventBusSubscriber.Bus.MOD)
public class GravisuitConfig {
    public static final Client CLIENT = new Client();
    public static final EnabledItems ENABLED_ITEMS = new EnabledItems();
    public static final PowerValues POWER_VALUES = new PowerValues();
    public static final Misc MISC = new Misc();
    public static final ClientConfig CLIENT_CONFIG;
    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final CommonConfig COMMON_CONFIG;
    public static final ForgeConfigSpec COMMON_SPEC;

    @SubscribeEvent
    public static void onModConfigEvent(ModConfigEvent e) {
        GravisuitConfig.onModConfigEvent(e.getConfig());
    }

    public static void onModConfigEvent(ModConfig e) {
        if (e.getModId().equals("gravisuit")) {
            if (e.getSpec() == CLIENT_SPEC) {
                GravisuitConfig.bakeClientConfig();
            } else if (e.getSpec() == COMMON_SPEC) {
                GravisuitConfig.bakeCommonConfig();
            }
        }
    }

    private static void bakeClientConfig() {
        GravisuitConfig.CLIENT.POSITIONS = (Client.Positions)((Object)GravisuitConfig.CLIENT_CONFIG.POSITONS.get());
    }

    private static void bakeCommonConfig() {
        GravisuitConfig.POWER_VALUES.ADVANCED_ELECTRIC_JETPACK_STORAGE = (Integer)GravisuitConfig.COMMON_CONFIG.ADVANCED_ELECTRIC_JETPACK_STORAGE.get();
        GravisuitConfig.POWER_VALUES.ADVANCED_NUCLEAR_JETPACK_STORAGE = (Integer)GravisuitConfig.COMMON_CONFIG.ADVANCED_NUCLEAR_JETPACK_STORAGE.get();
        GravisuitConfig.POWER_VALUES.ADVANCED_LAPPACK_STORAGE = (Integer)GravisuitConfig.COMMON_CONFIG.ADVANCED_LAPPACK_STORAGE.get();
        GravisuitConfig.POWER_VALUES.ULTIMATE_LAPPACK_STORAGE = (Integer)GravisuitConfig.COMMON_CONFIG.ULTIMATE_LAPPACK_STORAGE.get();
        GravisuitConfig.POWER_VALUES.GRAVITATION_JETPACK_STORAGE = (Integer)GravisuitConfig.COMMON_CONFIG.GRAVITATION_JETPACK_STORAGE.get();
        GravisuitConfig.POWER_VALUES.NUCLEAR_GRAVITATION_JETPACK_STORAGE = (Integer)GravisuitConfig.COMMON_CONFIG.NUCLEAR_GRAVITATION_JETPACK_STORAGE.get();
        GravisuitConfig.POWER_VALUES.GRAVITOOL_STORAGE = (Integer)GravisuitConfig.COMMON_CONFIG.GRAVITOOL_STORAGE.get();
        GravisuitConfig.POWER_VALUES.VAJRA_STORAGE = (Integer)GravisuitConfig.COMMON_CONFIG.VAJRA_STORAGE.get();
        GravisuitConfig.POWER_VALUES.RELOCATOR_STORAGE = (Integer)GravisuitConfig.COMMON_CONFIG.RELOCATOR_STORAGE.get();
        GravisuitConfig.POWER_VALUES.ADVANCED_ELECTRIC_JETPACK_TRANSFER = (Integer)GravisuitConfig.COMMON_CONFIG.ADVANCED_ELECTRIC_JETPACK_TRANSFER.get();
        GravisuitConfig.POWER_VALUES.ADVANCED_LAPPACK_TRANSFER = (Integer)GravisuitConfig.COMMON_CONFIG.ADVANCED_LAPPACK_TRANSFER.get();
        GravisuitConfig.POWER_VALUES.ULTIMATE_LAPPACK_TRANSFER = (Integer)GravisuitConfig.COMMON_CONFIG.ULTIMATE_LAPPACK_TRANSFER.get();
        GravisuitConfig.POWER_VALUES.GRAVITATION_JETPACK_TRANSFER = (Integer)GravisuitConfig.COMMON_CONFIG.GRAVITATION_JETPACK_TRANSFER.get();
        GravisuitConfig.POWER_VALUES.GRAVITOOL_TRANSFER = (Integer)GravisuitConfig.COMMON_CONFIG.GRAVITOOL_TRANSFER.get();
        GravisuitConfig.POWER_VALUES.VAJRA_TRANSFER = (Integer)GravisuitConfig.COMMON_CONFIG.VAJRA_TRANSFER.get();
        GravisuitConfig.POWER_VALUES.RELOCATOR_TRANSFER = (Integer)GravisuitConfig.COMMON_CONFIG.RELOCATOR_TRANSFER.get();
        GravisuitConfig.MISC.ADVANCED_JETPACK_PROVIDE_ENERGY = (Boolean)GravisuitConfig.COMMON_CONFIG.ADVANCED_JETPACK_PROVIDE_ENERGY.get();
        GravisuitConfig.MISC.ADVANCED_N_JETPACK_PROVIDE_ENERGY = (Boolean)GravisuitConfig.COMMON_CONFIG.ADVANCED_N_JETPACK_PROVIDE_ENERGY.get();
        GravisuitConfig.MISC.COMPACTED_JETPACK_PROVIDE_ENERGY = (Boolean)GravisuitConfig.COMMON_CONFIG.COMPACTED_JETPACK_PROVIDE_ENERGY.get();
        GravisuitConfig.MISC.COMPACTED_N_JETPACK_PROVIDE_ENERGY = (Boolean)GravisuitConfig.COMMON_CONFIG.COMPACTED_N_JETPACK_PROVIDE_ENERGY.get();
        GravisuitConfig.MISC.GRAVITATION_JETPACK_PROVIDE_ENERGY = (Boolean)GravisuitConfig.COMMON_CONFIG.GRAVITATION_JETPACK_PROVIDE_ENERGY.get();
        GravisuitConfig.MISC.GRAVITATION_N_JETPACK_PROVIDE_ENERGY = (Boolean)GravisuitConfig.COMMON_CONFIG.GRAVITATION_N_JETPACK_PROVIDE_ENERGY.get();
    }

    static {
        Pair COMMON_PAIR = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        Pair CLIENT_PAIR = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        CLIENT_CONFIG = (ClientConfig)CLIENT_PAIR.getLeft();
        CLIENT_SPEC = (ForgeConfigSpec)CLIENT_PAIR.getRight();
        COMMON_CONFIG = (CommonConfig)COMMON_PAIR.getLeft();
        COMMON_SPEC = (ForgeConfigSpec)COMMON_PAIR.getRight();
    }

    public static class Client {
        public Positions POSITIONS;

        public static enum Positions {
            TOPLEFT,
            TOPRIGHT,
            TOPMIDDLE,
            BOTTOMLEFT,
            BOTTOMRIGHT;

        }
    }

    public static class ClientConfig {
        public final ForgeConfigSpec.EnumValue<Client.Positions> POSITONS;

        public ClientConfig(ForgeConfigSpec.Builder builder) {
            this.POSITONS = builder.comment("Position of the gravisuit Hud").translation("gravisuit.config.position").defineEnum("POSITION", (Enum)Client.Positions.TOPLEFT);
        }
    }

    public static class PowerValues {
        public int ADVANCED_ELECTRIC_JETPACK_STORAGE;
        public int ADVANCED_NUCLEAR_JETPACK_STORAGE;
        public int ADVANCED_LAPPACK_STORAGE;
        public int ULTIMATE_LAPPACK_STORAGE;
        public int GRAVITATION_JETPACK_STORAGE;
        public int NUCLEAR_GRAVITATION_JETPACK_STORAGE;
        public int GRAVITOOL_STORAGE;
        public int VAJRA_STORAGE;
        public int RELOCATOR_STORAGE;
        public int ADVANCED_ELECTRIC_JETPACK_TRANSFER;
        public int ADVANCED_LAPPACK_TRANSFER;
        public int ULTIMATE_LAPPACK_TRANSFER;
        public int GRAVITATION_JETPACK_TRANSFER;
        public int GRAVITOOL_TRANSFER;
        public int VAJRA_TRANSFER;
        public int RELOCATOR_TRANSFER;
    }

    public static class CommonConfig {
        public final ForgeConfigSpec.IntValue ADVANCED_ELECTRIC_JETPACK_STORAGE;
        public final ForgeConfigSpec.IntValue ADVANCED_NUCLEAR_JETPACK_STORAGE;
        public final ForgeConfigSpec.IntValue ADVANCED_LAPPACK_STORAGE;
        public final ForgeConfigSpec.IntValue ULTIMATE_LAPPACK_STORAGE;
        public final ForgeConfigSpec.IntValue GRAVITATION_JETPACK_STORAGE;
        public final ForgeConfigSpec.IntValue NUCLEAR_GRAVITATION_JETPACK_STORAGE;
        public final ForgeConfigSpec.IntValue GRAVITOOL_STORAGE;
        public final ForgeConfigSpec.IntValue VAJRA_STORAGE;
        public final ForgeConfigSpec.IntValue RELOCATOR_STORAGE;
        public final ForgeConfigSpec.IntValue ADVANCED_ELECTRIC_JETPACK_TRANSFER;
        public final ForgeConfigSpec.IntValue ADVANCED_LAPPACK_TRANSFER;
        public final ForgeConfigSpec.IntValue ULTIMATE_LAPPACK_TRANSFER;
        public final ForgeConfigSpec.IntValue GRAVITATION_JETPACK_TRANSFER;
        public final ForgeConfigSpec.IntValue GRAVITOOL_TRANSFER;
        public final ForgeConfigSpec.IntValue VAJRA_TRANSFER;
        public final ForgeConfigSpec.IntValue RELOCATOR_TRANSFER;
        public final ForgeConfigSpec.BooleanValue ADVANCED_JETPACK_PROVIDE_ENERGY;
        public final ForgeConfigSpec.BooleanValue ADVANCED_N_JETPACK_PROVIDE_ENERGY;
        public final ForgeConfigSpec.BooleanValue COMPACTED_JETPACK_PROVIDE_ENERGY;
        public final ForgeConfigSpec.BooleanValue COMPACTED_N_JETPACK_PROVIDE_ENERGY;
        public final ForgeConfigSpec.BooleanValue GRAVITATION_JETPACK_PROVIDE_ENERGY;
        public final ForgeConfigSpec.BooleanValue GRAVITATION_N_JETPACK_PROVIDE_ENERGY;

        public CommonConfig(ForgeConfigSpec.Builder builder) {
            builder.push("PowerValues");
            builder.push("Storage");
            this.ADVANCED_ELECTRIC_JETPACK_STORAGE = builder.defineInRange("ADVANCED_ELECTRIC_JETPACK_STORAGE", 200000, 1, Integer.MAX_VALUE);
            this.ADVANCED_NUCLEAR_JETPACK_STORAGE = builder.defineInRange("ADVANCED_NUCLEAR_JETPACK_STORAGE", 200000, 1, Integer.MAX_VALUE);
            this.ADVANCED_LAPPACK_STORAGE = builder.defineInRange("ADVANCED_LAPPACK_STORAGE", 600000, 1, Integer.MAX_VALUE);
            this.ULTIMATE_LAPPACK_STORAGE = builder.defineInRange("ULTIMATE_LAPPACK_STORAGE", 10000000, 1, Integer.MAX_VALUE);
            this.GRAVITATION_JETPACK_STORAGE = builder.defineInRange("GRAVITATION_JETPACK_STORAGE", 500000, 1, Integer.MAX_VALUE);
            this.NUCLEAR_GRAVITATION_JETPACK_STORAGE = builder.defineInRange("NUCLEAR_GRAVITATION_JETPACK_STORAGE", 500000, 1, Integer.MAX_VALUE);
            this.GRAVITOOL_STORAGE = builder.defineInRange("GRAVITOOL_STORAGE", 50000, 1, Integer.MAX_VALUE);
            this.VAJRA_STORAGE = builder.defineInRange("VAJRA_STORAGE", 3000000, 1, Integer.MAX_VALUE);
            this.RELOCATOR_STORAGE = builder.defineInRange("RELOCATOR_STORAGE", 50000000, 1, Integer.MAX_VALUE);
            builder.pop();
            builder.push("Transfer");
            this.ADVANCED_ELECTRIC_JETPACK_TRANSFER = builder.defineInRange("ADVANCED_ELECTRIC_JETPACK_TRANSFER", 500, 1, Integer.MAX_VALUE);
            this.ADVANCED_LAPPACK_TRANSFER = builder.defineInRange("ADVANCED_LAPPACK_TRANSFER", 500, 1, Integer.MAX_VALUE);
            this.ULTIMATE_LAPPACK_TRANSFER = builder.defineInRange("ULTIMATE_LAPPACK_TRANSFER", 4000, 1, Integer.MAX_VALUE);
            this.GRAVITATION_JETPACK_TRANSFER = builder.defineInRange("GRAVITATION_JETPACK_TRANSFER", 1000, 1, Integer.MAX_VALUE);
            this.GRAVITOOL_TRANSFER = builder.defineInRange("GRAVITOOL_TRANSFER", 400, 1, Integer.MAX_VALUE);
            this.VAJRA_TRANSFER = builder.defineInRange("VAJRA_TRANSFER", 1000, 1, Integer.MAX_VALUE);
            this.RELOCATOR_TRANSFER = builder.defineInRange("RELOCATOR_TRANSFER", 25000, 1, Integer.MAX_VALUE);
            builder.pop();
            builder.pop();
            builder.push("Misc");
            this.ADVANCED_JETPACK_PROVIDE_ENERGY = builder.comment("Enables the Advanced Electric jetpack charging items. Default: false").define("ADVANCED_JETPACK_PROVIDE_ENERGY", false);
            this.ADVANCED_N_JETPACK_PROVIDE_ENERGY = builder.comment("Enables the Advanced Nuclear jetpack charging items. Default: true").define("ADVANCED_N_JETPACK_PROVIDE_ENERGY", true);
            this.COMPACTED_JETPACK_PROVIDE_ENERGY = builder.comment("Enables the Compacted jetpack charging items. Default: false").define("COMPACTEd_JETPACK_PROVIDE_ENERGY", false);
            this.COMPACTED_N_JETPACK_PROVIDE_ENERGY = builder.comment("Enables the Compacted Nuclear jetpack charging items. Default: true").define("COMPACTEd_N_JETPACK_PROVIDE_ENERGY", true);
            this.GRAVITATION_JETPACK_PROVIDE_ENERGY = builder.comment("Enables the Gravitation jetpack charging items. Default: false").define("GRAVITATION_JETPACK_PROVIDE_ENERGY", false);
            this.GRAVITATION_N_JETPACK_PROVIDE_ENERGY = builder.comment("Enables the Gravitation Nuclear jetpack charging items. Default: true").define("GRAVITATION_N_JETPACK_PROVIDE_ENERGY", true);
            builder.pop();
        }
    }

    public static class Misc {
        public boolean ADVANCED_JETPACK_PROVIDE_ENERGY;
        public boolean ADVANCED_N_JETPACK_PROVIDE_ENERGY;
        public boolean COMPACTED_JETPACK_PROVIDE_ENERGY;
        public boolean COMPACTED_N_JETPACK_PROVIDE_ENERGY;
        public boolean GRAVITATION_JETPACK_PROVIDE_ENERGY;
        public boolean GRAVITATION_N_JETPACK_PROVIDE_ENERGY;
    }

    public static class EnabledItems {
        public boolean enableAdvancedElectricJetpack = true;
        public boolean enableAdvancedNuclearJetpack = true;
        public boolean enableAdvancedLappack = true;
        public boolean enableUltimateLappack = true;
        public boolean enableGravitationJetpack = true;
        public boolean enableNuclearGravitationJetpack = true;
        public boolean enableGravitool = true;
        public boolean enableVajra = true;
        public boolean enableMiscCraftingItems = true;
    }
}

