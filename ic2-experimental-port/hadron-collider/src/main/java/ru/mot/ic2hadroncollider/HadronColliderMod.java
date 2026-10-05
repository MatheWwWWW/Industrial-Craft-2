package ru.mot.ic2hadroncollider;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/** IC2 Experimental addon: Hadron Collider, a high-voltage liquid UU-matter producer. */
@Mod(HadronColliderMod.MOD_ID)
public final class HadronColliderMod {
    public static final String MOD_ID = "ic2_hadron_collider";

    public HadronColliderMod() {
        HadronColliderContent.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
