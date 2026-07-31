/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.material.Fluid
 */
package ic2.api.recipe;

import ic2.api.recipe.ILiquidAcceptManager;
import ic2.core.fluid.Ic2FluidStack;
import java.util.Map;
import net.minecraft.world.level.material.Fluid;

public interface IFermenterRecipeManager
extends ILiquidAcceptManager {
    public void addRecipe(Fluid var1, int var2, int var3, Fluid var4, int var5);

    public FermentationProperty getFermentationInformation(Fluid var1);

    public Ic2FluidStack getOutput(Fluid var1);

    public Map<Fluid, FermentationProperty> getRecipeMap();

    public static final class FermentationProperty {
        public final int inputAmount;
        public final int heat;
        public final Fluid output;
        public final int outputAmount;

        public FermentationProperty(int n, int n2, Fluid fluid, int n3) {
            this.inputAmount = n;
            this.heat = n2;
            this.output = fluid;
            this.outputAmount = n3;
        }

        public Ic2FluidStack getOutput() {
            return this.output == null ? null : Ic2FluidStack.create(this.output, this.outputAmount);
        }
    }
}

