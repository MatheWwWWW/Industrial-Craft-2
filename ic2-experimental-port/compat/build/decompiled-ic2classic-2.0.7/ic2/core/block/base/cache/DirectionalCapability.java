/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraftforge.common.capabilities.Capability
 *  net.minecraftforge.common.capabilities.ICapabilityProvider
 *  net.minecraftforge.common.util.LazyOptional
 */
package ic2.core.block.base.cache;

import ic2.api.util.DirectionList;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class DirectionalCapability<K>
implements ICapabilityProvider {
    LazyOptional<K>[] optionals = new LazyOptional[6];
    Capability<K> cap;
    K value;
    DirectionList dirs = null;

    public DirectionalCapability(Capability<K> cap, K value) {
        this(cap, value, DirectionList.ALL);
    }

    public DirectionalCapability(Capability<K> cap, K value, DirectionList initials) {
        this.cap = cap;
        this.value = value;
        this.dirs = initials;
        for (Direction dir : DirectionList.ALL) {
            this.optionals[dir.m_122411_()] = initials.contains(dir) ? LazyOptional.of(() -> value) : null;
        }
    }

    public void setActiveDirections(DirectionList newDirs) {
        if (this.dirs.getCode() == newDirs.getCode()) {
            return;
        }
        DirectionList toRemove = this.dirs.remove(newDirs);
        DirectionList toAdd = newDirs.remove(this.dirs);
        for (Direction dir : toRemove) {
            if (this.optionals[dir.m_122411_()] == null) continue;
            this.optionals[dir.m_122411_()].invalidate();
            this.optionals[dir.m_122411_()] = null;
        }
        for (Direction dir : toAdd) {
            if (this.optionals[dir.m_122411_()] != null) continue;
            this.optionals[dir.m_122411_()] = LazyOptional.of(() -> this.value);
        }
        this.dirs = newDirs;
    }

    public void unload() {
        for (Direction dir : this.dirs) {
            if (this.optionals[dir.m_122411_()] == null) continue;
            this.optionals[dir.m_122411_()].invalidate();
            this.optionals[dir.m_122411_()] = null;
        }
    }

    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap != this.cap || side == null || this.optionals[side.m_122411_()] == null ? LazyOptional.empty() : this.optionals[side.m_122411_()].cast();
    }
}

