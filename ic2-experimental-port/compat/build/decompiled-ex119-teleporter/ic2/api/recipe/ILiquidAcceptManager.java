/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.material.Fluid
 */
package ic2.api.recipe;

import java.util.Set;
import net.minecraft.world.level.material.Fluid;

public interface ILiquidAcceptManager {
    public boolean acceptsFluid(Fluid var1);

    public Set<Fluid> getAcceptedFluids();
}

