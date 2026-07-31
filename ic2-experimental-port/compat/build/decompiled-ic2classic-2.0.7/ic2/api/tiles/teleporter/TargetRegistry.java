/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap
 */
package ic2.api.tiles.teleporter;

import ic2.api.tiles.teleporter.TeleporterTarget;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import java.util.Map;

public class TargetRegistry {
    public static final TargetRegistry INSTANCE = new TargetRegistry();
    Map<TeleporterTarget, String> names = new Object2ObjectLinkedOpenHashMap();

    public void addTarget(TeleporterTarget target, String name) {
        this.names.put(target, name);
    }

    public void removeTarget(TeleporterTarget target) {
        this.names.remove(target);
    }

    public String getTargetName(TeleporterTarget target) {
        return this.names.get(target);
    }
}

