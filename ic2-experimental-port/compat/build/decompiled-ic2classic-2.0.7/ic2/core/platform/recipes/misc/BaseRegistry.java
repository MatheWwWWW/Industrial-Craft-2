/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 */
package ic2.core.platform.recipes.misc;

import ic2.api.recipes.registries.IListenableRegistry;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import java.util.function.Consumer;

public abstract class BaseRegistry<T extends IListenableRegistry<T>>
implements IListenableRegistry<T> {
    List<Consumer<T>> listeners = ObjectLists.synchronize(CollectionUtils.createList());

    @Override
    public void registerListener(Consumer<T> listener) {
        if (listener == null) {
            return;
        }
        this.listeners.add(listener);
    }

    protected List<Consumer<T>> getListeners() {
        return this.listeners;
    }

    public void reload() {
        this.reloadInternals();
        for (Consumer<BaseRegistry> consumer : this.listeners) {
            consumer.accept(this);
        }
        this.onAfterReload();
    }

    protected abstract void reloadInternals();

    protected void onAfterReload() {
    }
}

