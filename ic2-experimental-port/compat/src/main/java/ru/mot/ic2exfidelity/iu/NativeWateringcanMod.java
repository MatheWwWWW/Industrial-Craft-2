package ru.mot.ic2exfidelity.iu;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
@Mod("wateringcan")
public final class NativeWateringcanMod {
    public NativeWateringcanMod() { NativeIUContent.register("wateringcan", FMLJavaModLoadingContext.get().getModEventBus()); }
}
