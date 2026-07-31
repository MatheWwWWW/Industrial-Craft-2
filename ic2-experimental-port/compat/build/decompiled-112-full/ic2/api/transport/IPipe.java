/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.EnumFacing
 */
package ic2.api.transport;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public interface IPipe {
    public TileEntity getTile();

    public boolean isConnected(EnumFacing var1);

    public void flipConnection(EnumFacing var1);
}

