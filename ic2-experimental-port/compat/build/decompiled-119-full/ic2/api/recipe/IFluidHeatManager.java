/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.material.Fluid
 */
package ic2.api.recipe;

import ic2.api.recipe.ILiquidAcceptManager;
import java.util.Map;
import net.minecraft.world.level.material.Fluid;

public interface IFluidHeatManager
extends ILiquidAcceptManager {
    public void addFluid(Fluid var1, int var2, int var3);

    public BurnProperty getBurnProperty(Fluid var1);

    public Map<Fluid, BurnProperty> getBurnProperties();

    public static class BurnProperty {
        public final int amount;
        public final int heat;

        public BurnProperty(int n, int n2) {
            this.amount = n;
            this.heat = n2;
        }
    }
}

