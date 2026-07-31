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

public interface ISemiFluidFuelManager
extends ILiquidAcceptManager {
    public void addFluid(String var1, long var2, long var4);

    public void removeFluid(String var1);

    public FuelProperty getFuelProperty(Fluid var1);

    public Map<String, FuelProperty> getFuelProperties();

    public static final class FuelProperty {
        public final long energyPerMb;
        public final long energyPerTick;

        public FuelProperty(long energyPerMb, long energyPerTick) {
            this.energyPerMb = energyPerMb;
            this.energyPerTick = energyPerTick;
        }
    }
}

