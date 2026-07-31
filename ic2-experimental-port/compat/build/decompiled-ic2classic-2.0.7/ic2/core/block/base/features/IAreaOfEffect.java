/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.phys.AABB
 */
package ic2.core.block.base.features;

import net.minecraft.world.phys.AABB;

public interface IAreaOfEffect {
    public AABB getAreaOfEffect();

    public int getAreaOfEffectColor();

    public void setVisualizationId(int var1);

    public int getVisualizationId();
}

