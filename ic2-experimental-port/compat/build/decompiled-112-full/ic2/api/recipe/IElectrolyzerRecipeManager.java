/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nonnull
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.util.EnumFacing
 *  net.minecraftforge.fluids.Fluid
 *  net.minecraftforge.fluids.FluidRegistry
 *  net.minecraftforge.fluids.FluidStack
 *  org.apache.commons.lang3.tuple.Pair
 */
package ic2.api.recipe;

import ic2.api.recipe.ILiquidAcceptManager;
import java.util.HashSet;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.tuple.Pair;

public interface IElectrolyzerRecipeManager
extends ILiquidAcceptManager {
    public void addRecipe(@Nonnull String var1, int var2, int var3, ElectrolyzerOutput ... var4);

    public void addRecipe(@Nonnull String var1, int var2, int var3, int var4, ElectrolyzerOutput ... var5);

    public ElectrolyzerRecipe getElectrolysisInformation(Fluid var1);

    public ElectrolyzerOutput[] getOutput(Fluid var1);

    public Map<String, ElectrolyzerRecipe> getRecipeMap();

    public static final class ElectrolyzerRecipe {
        public final int inputAmount;
        public final int EUaTick;
        public final int ticksNeeded;
        public final ElectrolyzerOutput[] outputs;

        public ElectrolyzerRecipe(int inputAmount, int EUaTick, int ticksNeeded, ElectrolyzerOutput ... outputs) {
            this.inputAmount = inputAmount;
            this.EUaTick = EUaTick;
            this.ticksNeeded = ticksNeeded;
            this.outputs = this.validateOutputs(outputs);
        }

        private ElectrolyzerOutput[] validateOutputs(ElectrolyzerOutput[] outputs) {
            if (outputs.length < 1 || outputs.length > 5) {
                throw new RuntimeException("Cannot have " + outputs.length + " outputs of an Electrolzer recipe, must be between 1 and 5");
            }
            HashSet<EnumFacing> directions = new HashSet<EnumFacing>(outputs.length * 2, 0.5f);
            for (ElectrolyzerOutput output : outputs) {
                if (directions.add(output.tankDirection)) continue;
                throw new RuntimeException("Duplicate direction in Electrolzer outputs (" + output.tankDirection + ')');
            }
            return outputs;
        }
    }

    @ParametersAreNonnullByDefault
    public static final class ElectrolyzerOutput {
        public final String fluidName;
        public final int fluidAmount;
        public final EnumFacing tankDirection;

        public ElectrolyzerOutput(String fluidName, int fluidAmount, EnumFacing tankDirection) {
            this.fluidName = fluidName;
            this.fluidAmount = fluidAmount;
            this.tankDirection = tankDirection;
        }

        public FluidStack getOutput() {
            return FluidRegistry.getFluid((String)this.fluidName) == null ? null : new FluidStack(FluidRegistry.getFluid((String)this.fluidName), this.fluidAmount);
        }

        public Pair<FluidStack, EnumFacing> getFullOutput() {
            return Pair.of((Object)this.getOutput(), (Object)this.tankDirection);
        }
    }
}

