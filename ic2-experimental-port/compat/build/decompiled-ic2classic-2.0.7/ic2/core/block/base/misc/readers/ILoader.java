/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.block.base.misc.readers;

import ic2.api.tiles.IEnergyStorage;

public interface ILoader {
    public boolean hasMinecart();

    public int getTransferred();

    public IEnergyStorage getMinecart();
}

