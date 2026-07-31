/*
 * Decompiled with CFR 0.152.
 */
package ic2.api.recipe;

import ic2.api.recipe.MachineRecipe;

public class MachineRecipeResult<RI, RO, I> {
    private final MachineRecipe<RI, RO> recipe;
    private final I adjustedInput;

    public MachineRecipeResult(MachineRecipe<RI, RO> recipe, I adjustedInput) {
        this.recipe = recipe;
        this.adjustedInput = adjustedInput;
    }

    public MachineRecipe<RI, RO> getRecipe() {
        return this.recipe;
    }

    public RO getOutput() {
        return this.recipe.getOutput();
    }

    public I getAdjustedInput() {
        return this.adjustedInput;
    }
}

