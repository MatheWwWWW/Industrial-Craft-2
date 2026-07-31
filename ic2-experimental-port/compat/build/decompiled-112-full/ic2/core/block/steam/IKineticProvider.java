/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.EnumFacing
 */
package ic2.core.block.steam;

import net.minecraft.util.EnumFacing;

public interface IKineticProvider {
    public int getProvidedPower(EnumFacing var1);

    public int getMaxPower();
}

