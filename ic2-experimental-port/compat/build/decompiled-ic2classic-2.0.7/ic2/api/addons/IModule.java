/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.eventbus.api.IEventBus
 */
package ic2.api.addons;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;

public interface IModule {
    public boolean canLoad(Dist var1);

    default public void loadConfigs() {
    }

    default public void preInit(IEventBus modBus) {
    }

    default public void postInit() {
    }
}

