/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.material.Fluid
 */
package ic2.core.recipe;

import ic2.api.recipe.IFermenterRecipeManager;
import ic2.core.fluid.Ic2FluidStack;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import net.minecraft.world.level.material.Fluid;

public class FermenterRecipeManager
implements IFermenterRecipeManager {
    private final Map<Fluid, IFermenterRecipeManager.FermentationProperty> fluidMap = new IdentityHashMap<Fluid, IFermenterRecipeManager.FermentationProperty>();

    @Override
    public void addRecipe(Fluid fluid, int n, int n2, Fluid fluid2, int n3) {
        if (this.fluidMap.containsKey(fluid)) {
            throw new RuntimeException("The fluid " + fluid + " already has an output assigned.");
        }
        this.fluidMap.put(fluid, new IFermenterRecipeManager.FermentationProperty(n, n2, fluid2, n3));
    }

    @Override
    public IFermenterRecipeManager.FermentationProperty getFermentationInformation(Fluid fluid) {
        return fluid == null ? null : this.fluidMap.get(fluid);
    }

    @Override
    public Ic2FluidStack getOutput(Fluid fluid) {
        IFermenterRecipeManager.FermentationProperty fermentationProperty = this.getFermentationInformation(fluid);
        if (fermentationProperty == null) {
            return null;
        }
        return fermentationProperty.output == null ? null : Ic2FluidStack.create(fermentationProperty.output, fermentationProperty.outputAmount);
    }

    @Override
    public boolean acceptsFluid(Fluid fluid) {
        return fluid != null && this.fluidMap.containsKey(fluid);
    }

    @Override
    public Set<Fluid> getAcceptedFluids() {
        return Collections.unmodifiableSet(this.fluidMap.keySet());
    }

    @Override
    public Map<Fluid, IFermenterRecipeManager.FermentationProperty> getRecipeMap() {
        return Collections.unmodifiableMap(this.fluidMap);
    }
}

