package ru.mot.ic2exfidelity.gravisuit;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/** IC2 Experimental-compatible replacement for the installed Gravisuit Classic 2.2 jar. */
@Mod(LegacyGravisuitMod.MOD_ID)
public final class LegacyGravisuitMod {
    public static final String MOD_ID = "gravisuit";

    public LegacyGravisuitMod() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        LegacyGravisuitPrerequisites.register(modBus);
        LegacyGravisuitContent.register(modBus);
        modBus.addListener(this::onCommonSetup);
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            LegacyGravisuitFlight.install();
            LegacyRelocatorNetwork.install();
        });
    }
}
