/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.gameevent.GameEvent
 */
package ic2.core.ref;

import ic2.core.IC2;
import net.minecraft.world.level.gameevent.GameEvent;

public class Ic2GameEvents {
    public static final GameEvent TOOL_USE = Ic2GameEvents.register("tool_use");
    public static final GameEvent GENERATOR_ACTIVATE = Ic2GameEvents.register("generator_activate");
    public static final GameEvent GENERATOR_DEACTIVATE = Ic2GameEvents.register("generator_deactivate");
    public static final GameEvent MACHINE_ACTIVATE = Ic2GameEvents.register("machine_activate");
    public static final GameEvent MACHINE_DEACTIVATE = Ic2GameEvents.register("machine_deactivate");

    private static GameEvent register(String string) {
        return Ic2GameEvents.register(string, 16);
    }

    private static GameEvent register(String string, int n) {
        return IC2.envProxy.registerGameEvent(string, n);
    }

    public static void init() {
    }
}

