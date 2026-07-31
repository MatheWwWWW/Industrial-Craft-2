/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.common.capabilities.Capability
 *  net.minecraftforge.fluids.capability.CapabilityFluidHandler
 */
package ic2.core.block.transport.cover;

import ic2.core.block.transport.cover.ICoverItem;
import java.util.Collection;
import java.util.Collections;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public interface IFluidConsumingCover
extends ICoverItem {
    @Override
    default public Collection<? extends Capability<?>> getProvidedCapabilities() {
        return Collections.singleton(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY);
    }
}

