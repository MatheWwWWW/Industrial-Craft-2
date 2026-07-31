/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  org.jetbrains.annotations.Nullable
 */
package ic2.api.recipe;

import ic2.api.recipe.MachineRecipeResult;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.Nullable;

public class MachineRecipe<I, O> {
    private final I input;
    private final O output;
    private final CompoundTag meta;

    public MachineRecipe(I i, O o) {
        this(i, o, null);
    }

    public MachineRecipe(I i, O o, CompoundTag compoundTag) {
        this.input = i;
        this.output = o;
        this.meta = compoundTag;
    }

    public I getInput() {
        return this.input;
    }

    public O getOutput() {
        return this.output;
    }

    @Nullable
    public CompoundTag getMetaData() {
        return this.meta;
    }

    public <AI> MachineRecipeResult<I, O, AI> getResult(AI AI) {
        return new MachineRecipeResult(this, AI);
    }
}

