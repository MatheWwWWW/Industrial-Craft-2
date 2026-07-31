/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NBTTagCompound
 */
package ic2.api.recipe;

import ic2.api.recipe.MachineRecipeResult;
import net.minecraft.nbt.NBTTagCompound;

public class MachineRecipe<I, O> {
    private final I input;
    private final O output;
    private final NBTTagCompound meta;

    public MachineRecipe(I input, O output) {
        this(input, output, null);
    }

    public MachineRecipe(I input, O output, NBTTagCompound meta) {
        this.input = input;
        this.output = output;
        this.meta = meta;
    }

    public I getInput() {
        return this.input;
    }

    public O getOutput() {
        return this.output;
    }

    public NBTTagCompound getMetaData() {
        return this.meta;
    }

    public <AI> MachineRecipeResult<I, O, AI> getResult(AI adjustedInput) {
        return new MachineRecipeResult(this, adjustedInput);
    }
}

