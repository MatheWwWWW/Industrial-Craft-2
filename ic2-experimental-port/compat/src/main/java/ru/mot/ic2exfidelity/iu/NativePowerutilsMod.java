package ru.mot.ic2exfidelity.iu;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
@Mod("powerutils")
public final class NativePowerutilsMod {
    public NativePowerutilsMod() { NativeIUContent.register("powerutils", FMLJavaModLoadingContext.get().getModEventBus()); }
}
