package ru.mot.ic2exfidelity.iu;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
@Mod("reactorplus")
public final class NativeReactorplusMod {
    public NativeReactorplusMod() { NativeIUContent.register("reactorplus", FMLJavaModLoadingContext.get().getModEventBus()); }
}
