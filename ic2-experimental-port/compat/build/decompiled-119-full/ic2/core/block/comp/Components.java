/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.block.comp;

import ic2.core.block.comp.ComparatorEmitter;
import ic2.core.block.comp.Energy;
import ic2.core.block.comp.FluidReactorLookup;
import ic2.core.block.comp.Fluids;
import ic2.core.block.comp.Obscuration;
import ic2.core.block.comp.Process;
import ic2.core.block.comp.Redstone;
import ic2.core.block.comp.RedstoneEmitter;
import ic2.core.block.comp.TileEntityComponent;
import ic2.core.block.transport.cover.Covers;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

public class Components {
    private static final Map<String, Class<? extends TileEntityComponent>> idComponentMap = new HashMap<String, Class<? extends TileEntityComponent>>();
    private static final Map<Class<? extends TileEntityComponent>, String> componentIdMap = new IdentityHashMap<Class<? extends TileEntityComponent>, String>();

    public static void init() {
        Components.register(Energy.class, "energy");
        Components.register(Fluids.class, "fluid");
        Components.register(FluidReactorLookup.class, "fluidReactorLookup");
        Components.register(Obscuration.class, "obscuration");
        Components.register(Process.class, "process");
        Components.register(Redstone.class, "redstone");
        Components.register(RedstoneEmitter.class, "redstoneEmitter");
        Components.register(ComparatorEmitter.class, "comparatorEmitter");
        Components.register(Covers.class, "covers");
    }

    public static void register(Class<? extends TileEntityComponent> clazz, String string) {
        if (idComponentMap.put(string, clazz) != null) {
            throw new IllegalStateException("duplicate id: " + string);
        }
        if (componentIdMap.put(clazz, string) != null) {
            throw new IllegalStateException("duplicate component: " + clazz.getName());
        }
    }

    public static <T extends TileEntityComponent> Class<T> getClass(String string) {
        return idComponentMap.get(string);
    }

    public static String getId(Class<? extends TileEntityComponent> clazz) {
        return componentIdMap.get(clazz);
    }
}

