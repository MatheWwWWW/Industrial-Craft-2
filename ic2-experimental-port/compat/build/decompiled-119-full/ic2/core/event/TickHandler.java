/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.Level
 */
package ic2.core.event;

import ic2.core.IC2;
import ic2.core.event.IWorldTickCallback;
import ic2.core.event.WorldData;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.world.level.Level;

public class TickHandler {
    private static final boolean debugupdate = System.getProperty("ic2.debugupdate") != null;
    private static final Map<IWorldTickCallback, Throwable> debugTraces = debugupdate ? new WeakHashMap() : null;
    private static Throwable lastDebugTrace;

    public static void onWorldTickStart(Level level) {
        if (level.f_46443_) {
            return;
        }
        WorldData worldData = WorldData.get(level, false);
        if (worldData == null) {
            return;
        }
        level.m_46473_().m_6180_("updates");
        TickHandler.processUpdates(level, worldData);
        level.m_46473_().m_6182_("Wind");
        worldData.windSim.updateWind();
        level.m_46473_().m_6182_("energynet");
        worldData.energyNet.onTickStart();
        level.m_46473_().m_7238_();
    }

    public static void onWorldTickEnd(Level level) {
        if (level.f_46443_) {
            return;
        }
        WorldData worldData = WorldData.get(level, false);
        if (worldData == null) {
            return;
        }
        level.m_46473_().m_6180_("energynet");
        worldData.energyNet.onTickEnd();
        level.m_46473_().m_6182_("Networking");
        IC2.network.get(true).onTickEnd(worldData);
        level.m_46473_().m_7238_();
    }

    public static void onServerTick() {
    }

    public static void onClientTick() {
        IC2.keyboard.sendKeyUpdate();
        IC2.audioManager.onTick();
        IC2.soundManager.tick();
        Level level = IC2.sideProxy.getPlayerWorld();
        if (level != null) {
            TickHandler.processUpdates(level, WorldData.get(level));
        }
    }

    public static void requestSingleWorldTick(Level level, IWorldTickCallback iWorldTickCallback) {
        WorldData.get((Level)level).singleUpdates.add(iWorldTickCallback);
        if (debugupdate) {
            debugTraces.put(iWorldTickCallback, new Throwable());
        }
    }

    public static void requestContinuousWorldTick(Level level, IWorldTickCallback iWorldTickCallback) {
        WorldData worldData = WorldData.get(level);
        if (!worldData.continuousUpdatesInUse) {
            worldData.continuousUpdates.add(iWorldTickCallback);
        } else {
            worldData.continuousUpdatesToRemove.remove(iWorldTickCallback);
            worldData.continuousUpdatesToAdd.add(iWorldTickCallback);
        }
        if (debugupdate) {
            debugTraces.put(iWorldTickCallback, new Throwable());
        }
    }

    public static void removeContinuousWorldTick(Level level, IWorldTickCallback iWorldTickCallback) {
        WorldData worldData = WorldData.get(level);
        if (!worldData.continuousUpdatesInUse) {
            worldData.continuousUpdates.remove(iWorldTickCallback);
        } else {
            worldData.continuousUpdatesToAdd.remove(iWorldTickCallback);
            worldData.continuousUpdatesToRemove.add(iWorldTickCallback);
        }
    }

    public static Throwable getLastDebugTrace() {
        return lastDebugTrace;
    }

    private static void processUpdates(Level level, WorldData worldData) {
        IWorldTickCallback iWorldTickCallback;
        level.m_46473_().m_6180_("single-update");
        while ((iWorldTickCallback = worldData.singleUpdates.poll()) != null) {
            if (debugupdate) {
                lastDebugTrace = debugTraces.remove(iWorldTickCallback);
            }
            iWorldTickCallback.onTick(level);
        }
        level.m_46473_().m_6182_("cont-update");
        worldData.continuousUpdatesInUse = true;
        for (IWorldTickCallback iWorldTickCallback2 : worldData.continuousUpdates) {
            if (debugupdate) {
                lastDebugTrace = debugTraces.remove(iWorldTickCallback2);
            }
            iWorldTickCallback2.onTick(level);
        }
        worldData.continuousUpdatesInUse = false;
        if (debugupdate) {
            lastDebugTrace = null;
        }
        worldData.continuousUpdates.addAll(worldData.continuousUpdatesToAdd);
        worldData.continuousUpdatesToAdd.clear();
        worldData.continuousUpdates.removeAll(worldData.continuousUpdatesToRemove);
        worldData.continuousUpdatesToRemove.clear();
        level.m_46473_().m_7238_();
    }
}

