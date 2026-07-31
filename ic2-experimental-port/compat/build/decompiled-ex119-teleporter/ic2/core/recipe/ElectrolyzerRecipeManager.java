/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.material.Fluid
 */
package ic2.core.recipe;

import ic2.api.recipe.IElectrolyzerRecipeManager;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import net.minecraft.world.level.material.Fluid;

public class ElectrolyzerRecipeManager
implements IElectrolyzerRecipeManager {
    private final Map<Fluid, IElectrolyzerRecipeManager.ElectrolyzerRecipe> fluidMap = new IdentityHashMap<Fluid, IElectrolyzerRecipeManager.ElectrolyzerRecipe>();

    @Override
    public void addRecipe(Fluid fluid, int n, int n2, IElectrolyzerRecipeManager.ElectrolyzerOutput ... electrolyzerOutputArray) {
        this.addRecipe(fluid, n, n2, 200, electrolyzerOutputArray);
    }

    @Override
    public void addRecipe(Fluid fluid, int n, int n2, int n3, IElectrolyzerRecipeManager.ElectrolyzerOutput ... electrolyzerOutputArray) {
        if (this.fluidMap.containsKey(fluid)) {
            throw new RuntimeException("The fluid " + fluid + " already has an output assigned.");
        }
        this.fluidMap.put(fluid, new IElectrolyzerRecipeManager.ElectrolyzerRecipe(n, n2, n3, electrolyzerOutputArray));
    }

    @Override
    public IElectrolyzerRecipeManager.ElectrolyzerRecipe getElectrolysisInformation(Fluid fluid) {
        return fluid == null ? null : this.fluidMap.get(fluid);
    }

    @Override
    public IElectrolyzerRecipeManager.ElectrolyzerOutput[] getOutput(Fluid fluid) {
        IElectrolyzerRecipeManager.ElectrolyzerRecipe electrolyzerRecipe = this.getElectrolysisInformation(fluid);
        return electrolyzerRecipe == null ? null : electrolyzerRecipe.outputs;
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
    public Map<Fluid, IElectrolyzerRecipeManager.ElectrolyzerRecipe> getRecipeMap() {
        return Collections.unmodifiableMap(this.fluidMap);
    }
}

