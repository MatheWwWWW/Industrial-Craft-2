/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceLocation
 */
package ic2.api.tile;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

public interface IRotorProvider {
    public int getRotorDiameter();

    public Direction getFacing();

    public float getAngle();

    public ResourceLocation getRotorRenderTexture();
}

