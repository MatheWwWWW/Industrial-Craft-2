/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.ModList
 */
package ic2.jeiplugin;

import ic2.api.addons.IC2Plugin;
import ic2.api.addons.IModule;
import ic2.jeiplugin.core.JEIPlugin;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;

@IC2Plugin(id="jei", name="JEI-Plugin", version="1.0")
public class JEIModule
implements IModule {
    public static boolean ALLOWS_LOADING = false;

    @Override
    public boolean canLoad(Dist side) {
        return ModList.get().isLoaded("jei");
    }

    @Override
    public void loadConfigs() {
        ALLOWS_LOADING = true;
        MinecraftForge.EVENT_BUS.register(JEIPlugin.class);
    }
}

