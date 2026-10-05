package ru.mot.ic2exfidelity.iu;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
@Mod("quantumgenerators")
public final class NativeQuantumgeneratorsMod {
    public NativeQuantumgeneratorsMod() { NativeIUContent.register("quantumgenerators", FMLJavaModLoadingContext.get().getModEventBus()); }
}
