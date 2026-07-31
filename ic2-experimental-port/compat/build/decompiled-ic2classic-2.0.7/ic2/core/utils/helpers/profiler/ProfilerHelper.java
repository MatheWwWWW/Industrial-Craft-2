/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.Stack
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.utils.helpers.profiler;

import ic2.core.block.base.features.ITickListener;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.profiler.ProfilerEntry;
import it.unimi.dsi.fastutil.Stack;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Map;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ProfilerHelper {
    public static final ProfilerHelper INSTANCE = new ProfilerHelper();
    Map<Level, Long2ObjectMap<ProfilerInstance>> tracked = CollectionUtils.createLinkedMap();

    private ProfilerHelper() {
    }

    public ProfilerInstance getOrCreate(BlockEntity tile) {
        ProfilerInstance instance;
        Long2ObjectOpenHashMap dataMap = this.tracked.get(tile.m_58904_());
        if (dataMap == null) {
            dataMap = new Long2ObjectOpenHashMap();
            this.tracked.put(tile.m_58904_(), (Long2ObjectMap<ProfilerInstance>)dataMap);
        }
        if ((instance = (ProfilerInstance)dataMap.get(tile.m_58899_().m_121878_())) == null) {
            instance = new ProfilerInstance();
            dataMap.put(tile.m_58899_().m_121878_(), (Object)instance);
        }
        return instance;
    }

    public void onTickEnded() {
        for (Long2ObjectMap<ProfilerInstance> entry : this.tracked.values()) {
            for (ProfilerInstance instance : entry.values()) {
                instance.onTickFinished();
            }
        }
        for (ITickListener.GlobalLag lag : ITickListener.GLOBAL_LAG.values()) {
            lag.onTickFinished();
        }
        ITickListener.SERVER_LAG.onTickFinished();
    }

    public void onServerStopped() {
        this.tracked.clear();
    }

    public void onWorldUnload(Level world) {
        this.tracked.remove(world);
    }

    public static class ProfilerInstance {
        Object2ObjectLinkedOpenHashMap<String, ProfilerEntry> cache = new Object2ObjectLinkedOpenHashMap();
        Stack<ProfilerEntry> activeProfilers = new ObjectArrayList();

        public ProfilerEntry getMain() {
            if (this.cache.isEmpty()) {
                return null;
            }
            ProfilerEntry entry = (ProfilerEntry)this.cache.get(this.cache.firstKey());
            while (entry.getParent() != null) {
                entry = entry.getParent();
            }
            return entry;
        }

        public void next(String name) {
            this.stop();
            this.start(name);
        }

        public void start(String name) {
            String entry = (String)(this.activeProfilers.isEmpty() ? "" : ((ProfilerEntry)this.activeProfilers.top()).getPathName() + "/") + name;
            ProfilerEntry active = (ProfilerEntry)this.cache.get((Object)entry);
            if (active == null) {
                active = new ProfilerEntry(name, this);
                if (!this.activeProfilers.isEmpty()) {
                    ((ProfilerEntry)this.activeProfilers.top()).addChild(active);
                }
                this.cache.put((Object)entry, (Object)active);
            }
            this.activeProfilers.push((Object)active);
            active.start();
        }

        public void stop() {
            if (this.activeProfilers.isEmpty()) {
                return;
            }
            ((ProfilerEntry)this.activeProfilers.pop()).stop();
        }

        public void onTickFinished() {
            for (ProfilerEntry entry : this.cache.values()) {
                entry.onFrameFinished();
            }
        }
    }
}

