/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.ModContainer
 *  net.minecraftforge.fml.ModList
 *  net.minecraftforge.fml.ModLoadingContext
 */
package ic2.core.utils.helpers;

import java.util.Optional;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;

public class ModCache {
    ModContainer cached = null;

    public void setActiveMod(String name) {
        Optional container = ModList.get().getModContainerById(name);
        if (!container.isPresent() || ModLoadingContext.get().getActiveNamespace().equals(name)) {
            return;
        }
        if (this.cached != null) {
            throw new IllegalStateException("Cache already set");
        }
        ModLoadingContext context = ModLoadingContext.get();
        this.cached = context.getActiveContainer();
        context.setActiveContainer((ModContainer)container.orElse(null));
    }

    public void reset() {
        if (this.cached == null) {
            return;
        }
        ModLoadingContext.get().setActiveContainer(this.cached);
        this.cached = null;
    }
}

