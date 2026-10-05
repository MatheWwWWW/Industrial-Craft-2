package ru.mot.ic2exfidelity.iu;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
@Mod("diamondvein")
public final class NativeDiamondveinMod {
    public NativeDiamondveinMod() { NativeIUContent.register("diamondvein", FMLJavaModLoadingContext.get().getModEventBus()); }
}
