/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ic2.core.IC2
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.ModLoadingContext
 *  net.minecraftforge.fml.common.Mod
 *  net.minecraftforge.fml.config.IConfigSpec
 *  net.minecraftforge.fml.config.ModConfig$Type
 *  net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
 *  net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
 *  net.minecraftforge.registries.ForgeRegistries$Keys
 *  net.minecraftforge.registries.RegisterEvent
 */
package trinsdar.advancedsolars;

import ic2.core.IC2;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.IConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import trinsdar.advancedsolars.AdvancedSolarsWiki;
import trinsdar.advancedsolars.util.AdvancedSolarsConfig;
import trinsdar.advancedsolars.util.AdvancedSolarsRecipes;
import trinsdar.advancedsolars.util.Registry;

@Mod(value="advanced_solars")
public class AdvancedSolarsClassic {
    public static final String MODID = "advanced_solars";

    public AdvancedSolarsClassic() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, (IConfigSpec)AdvancedSolarsConfig.COMMON_SPEC);
        FMLJavaModLoadingContext.get().getModEventBus().register((Object)this);
        IC2.EVENT_BUS.addListener(AdvancedSolarsWiki::onWikiEvent);
    }

    @SubscribeEvent
    public void onRegister(RegisterEvent event) {
        if (event.getRegistryKey().equals((Object)ForgeRegistries.Keys.BLOCKS)) {
            Registry.init();
        }
    }

    @SubscribeEvent
    public void onCommonSetup(FMLCommonSetupEvent event) {
        AdvancedSolarsRecipes.init();
    }
}

