/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.crafting.RecipeManager
 *  net.minecraft.world.level.Level
 */
package ic2.core.recipe.v2;

import ic2.api.recipe.Recipes;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Function;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;

public class RecipeManagerGetter<T>
implements Recipes.IGetter<T> {
    private final Function<RecipeManager, T> factory;
    private final Map<RecipeManager, T> cache = new WeakHashMap<RecipeManager, T>();

    public RecipeManagerGetter(Function<RecipeManager, T> function) {
        this.factory = function;
    }

    @Override
    public T get(Level level) {
        if (level.m_5776_()) {
            return this.factory.apply(level.m_7465_());
        }
        return this.cache.computeIfAbsent(level.m_7465_(), this.factory);
    }
}

