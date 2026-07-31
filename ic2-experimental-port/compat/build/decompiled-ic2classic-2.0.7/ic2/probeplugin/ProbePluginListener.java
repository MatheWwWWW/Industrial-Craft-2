/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.eventbus.api.IEventBus
 *  net.minecraftforge.fml.ModList
 *  net.minecraftforge.registries.ForgeRegistries$Keys
 *  net.minecraftforge.registries.RegisterEvent
 */
package ic2.probeplugin;

import ic2.api.addons.IC2Plugin;
import ic2.api.addons.IModule;
import ic2.core.platform.recipes.misc.AdvRecipeRegistry;
import ic2.probeplugin.base.ProbePlugin;
import ic2.probeplugin.item.ElectricProbeItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

@IC2Plugin(id="one_probe", name="The-One-Probe-Plugin", version="1.0")
public class ProbePluginListener
implements IModule {
    public static Item PROBE;

    @Override
    public boolean canLoad(Dist side) {
        return ModList.get().isLoaded("theoneprobe");
    }

    @Override
    public void preInit(IEventBus modBus) {
        modBus.addListener(this::addItems);
        AdvRecipeRegistry.INSTANCE.registerListener(ProbePlugin::loadRecipe);
    }

    private void addItems(RegisterEvent event) {
        if (event.getRegistryKey().equals((Object)ForgeRegistries.Keys.ITEMS)) {
            PROBE = new ElectricProbeItem();
            event.getForgeRegistry().register(new ResourceLocation("ic2:electric_probe"), (Object)PROBE);
        }
    }

    @Override
    public void postInit() {
        ProbePlugin.loadPlugin();
    }
}

