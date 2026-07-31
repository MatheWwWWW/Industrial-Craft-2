/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraftforge.common.capabilities.Capability
 *  net.minecraftforge.common.util.LazyOptional
 */
package ic2.core.utils.helpers.capabilities;

import ic2.core.utils.helpers.capabilities.IToggleableCapabilityProvider;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public class StructureCapabilityProvider<Y>
implements IToggleableCapabilityProvider {
    Capability<Y> capability;
    LazyOptional<Y> value;
    Y entry;
    boolean active;

    public StructureCapabilityProvider(Capability<Y> capability, Y entry) {
        this(capability, entry, true);
    }

    public StructureCapabilityProvider(Capability<Y> capability, Y entry, boolean active) {
        this.capability = capability;
        this.entry = entry;
        this.active = active;
        this.value = active && entry != null ? LazyOptional.of(() -> entry) : null;
    }

    public static <T> StructureCapabilityProvider<T> create(Capability<T> cap, Object entry) {
        return new StructureCapabilityProvider<Object>(cap, entry);
    }

    public static <T> StructureCapabilityProvider<T> create(Capability<T> cap, Object entry, boolean active) {
        return new StructureCapabilityProvider<Object>(cap, entry, active);
    }

    @Override
    public void setActive(boolean value) {
        if (this.active != value) {
            this.active = value;
            if (this.active && this.value == null) {
                this.value = LazyOptional.of(() -> this.entry);
            } else if (!this.active && this.value != null) {
                this.value.invalidate();
                this.value = null;
            }
        }
    }

    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return this.active && this.capability == cap && this.value != null ? this.value.cast() : LazyOptional.empty();
    }

    @Override
    public boolean isActive() {
        return this.active;
    }
}

