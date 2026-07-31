package ru.mot.ic2exfidelity.advancedsolars;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/** Experimental-compatible replacement for Advanced Solars Classic 2.0.2. */
@Mod(LegacyAdvancedSolarsMod.MOD_ID)
public final class LegacyAdvancedSolarsMod {
    public static final String MOD_ID = "advanced_solars";

    public LegacyAdvancedSolarsMod() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        LegacyAdvancedSolarsContent.register(modBus);
        LegacyAdvancedSolarsPrerequisites.register(modBus);
    }
}
