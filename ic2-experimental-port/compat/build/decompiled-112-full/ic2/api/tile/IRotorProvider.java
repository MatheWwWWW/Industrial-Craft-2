/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.ResourceLocation
 */
package ic2.api.tile;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public interface IRotorProvider {
    public int getRotorDiameter();

    public EnumFacing getFacing();

    public float getAngle();

    public ResourceLocation getRotorRenderTexture();
}

