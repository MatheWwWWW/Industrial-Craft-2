package ru.mot.ic2exfidelity.iu;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
@Mod("simplyquarries")
public final class NativeSimplyquarriesMod {
    public NativeSimplyquarriesMod() { NativeIUContent.register("simplyquarries", FMLJavaModLoadingContext.get().getModEventBus()); }
}
