/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.material.Fluid
 *  org.apache.commons.lang3.tuple.Pair
 */
package ic2.api.recipe;

import ic2.api.recipe.ILiquidAcceptManager;
import ic2.core.fluid.Ic2FluidStack;
import java.util.HashSet;
import java.util.Map;
import net.minecraft.core.Direction;
import net.minecraft.world.level.material.Fluid;
import org.apache.commons.lang3.tuple.Pair;

public interface IElectrolyzerRecipeManager
extends ILiquidAcceptManager {
    public void addRecipe(Fluid var1, int var2, int var3, ElectrolyzerOutput ... var4);

    public void addRecipe(Fluid var1, int var2, int var3, int var4, ElectrolyzerOutput ... var5);

    public ElectrolyzerRecipe getElectrolysisInformation(Fluid var1);

    public ElectrolyzerOutput[] getOutput(Fluid var1);

    public Map<Fluid, ElectrolyzerRecipe> getRecipeMap();

    public static final class ElectrolyzerRecipe {
        public final int inputAmount;
        public final int EUaTick;
        public final int ticksNeeded;
        public final ElectrolyzerOutput[] outputs;

        public ElectrolyzerRecipe(int n, int n2, int n3, ElectrolyzerOutput ... electrolyzerOutputArray) {
            this.inputAmount = n;
            this.EUaTick = n2;
            this.ticksNeeded = n3;
            this.outputs = this.validateOutputs(electrolyzerOutputArray);
        }

        private ElectrolyzerOutput[] validateOutputs(ElectrolyzerOutput[] electrolyzerOutputArray) {
            if (electrolyzerOutputArray.length < 1 || electrolyzerOutputArray.length > 5) {
                throw new RuntimeException("Cannot have " + electrolyzerOutputArray.length + " outputs of an Electrolzer recipe, must be between 1 and 5");
            }
            HashSet<Direction> hashSet = new HashSet<Direction>(electrolyzerOutputArray.length * 2, 0.5f);
            for (ElectrolyzerOutput electrolyzerOutput : electrolyzerOutputArray) {
                if (hashSet.add(electrolyzerOutput.tankDirection)) continue;
                throw new RuntimeException("Duplicate direction in Electrolzer outputs (" + electrolyzerOutput.tankDirection + ")");
            }
            return electrolyzerOutputArray;
        }
    }

    public static final class ElectrolyzerOutput {
        public final Fluid fluid;
        public final int fluidAmount;
        public final Direction tankDirection;

        public ElectrolyzerOutput(Fluid fluid, int n, Direction direction) {
            this.fluid = fluid;
            this.fluidAmount = n;
            this.tankDirection = direction;
        }

        public Ic2FluidStack getOutput() {
            return this.fluid == null ? null : Ic2FluidStack.create(this.fluid, this.fluidAmount);
        }

        public Pair<Ic2FluidStack, Direction> getFullOutput() {
            return Pair.of((Object)this.getOutput(), (Object)this.tankDirection);
        }
    }
}

