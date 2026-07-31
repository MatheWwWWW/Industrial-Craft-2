/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraftforge.common.capabilities.Capability
 *  net.minecraftforge.common.capabilities.ICapabilityProvider
 *  net.minecraftforge.common.util.LazyOptional
 */
package ic2.core.utils.helpers.capabilities;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class CapabilitySupplier<K>
implements ICapabilityProvider {
    Capability<K> cap;
    LazyOptional<K> data;

    public CapabilitySupplier(Capability<K> cap, K data) {
        this(cap, LazyOptional.of(() -> data));
    }

    public CapabilitySupplier(Capability<K> cap, LazyOptional<K> data) {
        this.cap = cap;
        this.data = data;
    }

    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return this.cap == cap ? this.data.cast() : LazyOptional.empty();
    }
}

