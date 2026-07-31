/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent
 */
package ic2.core.platform.registries;

import ic2.api.items.armor.IArmorModule;
import ic2.api.tiles.INotifiableMachine;
import ic2.api.tiles.tubes.ITube;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class IC2Capabilities {
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(INotifiableMachine.class);
        event.register(ITube.class);
        event.register(IArmorModule.IArmorCapability.class);
    }
}

