/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 */
package ic2.core.wiki.recipes.providers;

import ic2.core.wiki.recipes.renderers.IRecipeRenderer;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;

public interface IRecipeReference {
    public List<IRecipeRenderer> getInputs();

    public List<IRecipeRenderer> getOutputs();

    public void setListener(Runnable var1);

    public void addSeachInfo(Consumer<Component> var1);
}

