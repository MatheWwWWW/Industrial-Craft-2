/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fluids.Fluid
 */
package ic2.api.recipe;

import ic2.api.recipe.ILiquidAcceptManager;
import java.util.Map;
import net.minecraftforge.fluids.Fluid;

public interface ILiquidHeatExchangerManager
extends ILiquidAcceptManager {
    public void addFluid(String var1, String var2, int var3);

    public HeatExchangeProperty getHeatExchangeProperty(Fluid var1);

    public Map<String, HeatExchangeProperty> getHeatExchangeProperties();

    public ILiquidAcceptManager getSingleDirectionLiquidManager();

    public static class HeatExchangeProperty {
        public final Fluid outputFluid;
        public final int huPerMB;

        public HeatExchangeProperty(Fluid outputFluid, int huPerMB) {
            this.outputFluid = outputFluid;
            this.huPerMB = huPerMB;
        }
    }
}

