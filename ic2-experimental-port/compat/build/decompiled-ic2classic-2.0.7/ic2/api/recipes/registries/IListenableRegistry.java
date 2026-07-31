/*
 * Decompiled with CFR 0.152.
 */
package ic2.api.recipes.registries;

import java.util.function.Consumer;

public interface IListenableRegistry<T extends IListenableRegistry<T>> {
    public void registerListener(Consumer<T> var1);
}

