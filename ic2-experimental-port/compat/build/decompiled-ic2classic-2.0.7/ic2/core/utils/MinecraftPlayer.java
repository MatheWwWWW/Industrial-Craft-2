/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.util.UUIDTypeAdapter
 */
package ic2.core.utils;

import com.mojang.util.UUIDTypeAdapter;
import java.util.UUID;

public class MinecraftPlayer {
    private UUID UUID;
    private String name;

    public MinecraftPlayer(String UUID2, String name) {
        this.UUID = UUIDTypeAdapter.fromString((String)UUID2);
        this.name = name;
    }

    public UUID getUUID() {
        return this.UUID;
    }

    public String getName() {
        return this.name;
    }
}

