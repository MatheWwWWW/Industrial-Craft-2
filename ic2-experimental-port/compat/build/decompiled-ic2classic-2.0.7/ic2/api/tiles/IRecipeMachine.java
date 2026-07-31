/*
 * Decompiled with CFR 0.152.
 */
package ic2.api.tiles;

import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.api.tiles.IInputMachine;

public interface IRecipeMachine
extends IInputMachine {
    public IMachineRecipeList getRecipeList();
}

