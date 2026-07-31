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

public interface ILiquidHeatExchangerManager
extends ILiquidAcceptManager {
    public void addFluid(Fluid var1, Fluid var2, int var3);

    public HeatExchangeProperty getHeatExchangeProperty(Fluid var1);

    public Map<Fluid, HeatExchangeProperty> getHeatExchangeProperties();

    public ILiquidAcceptManager getSingleDirectionLiquidManager();

    public static class HeatExchangeProperty {
        public final Fluid outputFluid;
        public final int huPerMB;

        public HeatExchangeProperty(Fluid fluid, int n) {
            this.outputFluid = fluid;
            this.huPerMB = n;
        }
    }
}

