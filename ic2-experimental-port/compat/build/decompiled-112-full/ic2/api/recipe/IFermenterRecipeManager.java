/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fluids.Fluid
 *  net.minecraftforge.fluids.FluidRegistry
 *  net.minecraftforge.fluids.FluidStack
 */
package ic2.api.recipe;

import ic2.api.recipe.ILiquidAcceptManager;
import java.util.Map;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public interface IFermenterRecipeManager
extends ILiquidAcceptManager {
    public void addRecipe(String var1, int var2, int var3, String var4, int var5);

    public FermentationProperty getFermentationInformation(Fluid var1);

    public FluidStack getOutput(Fluid var1);

    public Map<String, FermentationProperty> getRecipeMap();

    public static final class FermentationProperty {
        public final int inputAmount;
        public final int heat;
        public final String output;
        public final int outputAmount;

        public FermentationProperty(int inputAmount, int heat, String output, int outputAmount) {
            this.inputAmount = inputAmount;
            this.heat = heat;
            this.output = output;
            this.outputAmount = outputAmount;
        }

        public FluidStack getOutput() {
            return FluidRegistry.getFluid((String)this.output) == null ? null : new FluidStack(FluidRegistry.getFluid((String)this.output), this.outputAmount);
        }
    }
}

