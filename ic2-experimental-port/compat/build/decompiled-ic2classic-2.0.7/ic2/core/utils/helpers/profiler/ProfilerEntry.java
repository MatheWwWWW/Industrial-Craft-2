/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.LongArrayFIFOQueue
 *  it.unimi.dsi.fastutil.longs.LongPriorityQueue
 *  mcjty.theoneprobe.api.IProbeInfo
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 */
package ic2.core.utils.helpers.profiler;

import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.Formatters;
import ic2.core.utils.helpers.profiler.ProfilerHelper;
import ic2.core.utils.math.MathUtils;
import it.unimi.dsi.fastutil.longs.LongArrayFIFOQueue;
import it.unimi.dsi.fastutil.longs.LongPriorityQueue;
import java.util.List;
import mcjty.theoneprobe.api.IProbeInfo;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class ProfilerEntry {
    String name;
    String pathName;
    ProfilerHelper.ProfilerInstance owner;
    ProfilerEntry parent;
    List<ProfilerEntry> children = CollectionUtils.createList();
    int depth = 0;
    int totalTicks = 0;
    long maxTime = Long.MIN_VALUE;
    long minTime = Long.MAX_VALUE;
    long average = 0L;
    LongPriorityQueue entries = new LongArrayFIFOQueue();
    long startTime = 0L;
    long currentTime = 0L;

    public ProfilerEntry(String name, ProfilerHelper.ProfilerInstance owner) {
        this.name = name;
        this.pathName = name;
        this.owner = owner;
    }

    public void addChild(ProfilerEntry child) {
        if (child != null) {
            this.children.add(child);
            child.setParent(this);
        }
    }

    private void setParent(ProfilerEntry parent) {
        this.parent = parent;
        this.depth = parent.depth + 1;
        this.pathName = parent.pathName + "/" + this.name;
    }

    public void start() {
        this.startTime = System.nanoTime();
    }

    public void stop() {
        this.currentTime += System.nanoTime() - this.startTime;
    }

    public void onFrameFinished() {
        this.average += this.currentTime;
        this.entries.enqueue(this.currentTime);
        if (this.entries.size() > 20) {
            this.average -= this.entries.dequeueLong();
        }
        this.currentTime = this.average / (long)this.entries.size();
        this.minTime = Math.min(this.minTime, this.currentTime);
        this.maxTime = Math.max(this.maxTime, this.currentTime);
        if (this.totalTicks++ >= 2000) {
            this.minTime = this.currentTime;
            this.maxTime = this.currentTime;
            this.totalTicks = 0;
        }
        this.currentTime = 0L;
    }

    public String getName() {
        return this.name;
    }

    public String getPathName() {
        return this.pathName;
    }

    public long getMinTime() {
        return this.minTime;
    }

    public long getNanoTime() {
        return this.entries.isEmpty() ? 0L : this.average / (long)this.entries.size();
    }

    public long getMaxTime() {
        return this.maxTime;
    }

    public int getDepth() {
        return this.depth;
    }

    public int getChildCount() {
        return this.children.size();
    }

    public ProfilerEntry getChild(int index) {
        return this.children.get(index);
    }

    public ProfilerEntry getParent() {
        return this.parent;
    }

    public ProfilerHelper.ProfilerInstance getOwner() {
        return this.owner;
    }

    private long getSelf() {
        long base = this.getNanoTime();
        for (ProfilerEntry entry : this.children) {
            base -= entry.getNanoTime();
        }
        return base;
    }

    public void print(IProbeInfo info) {
        info.mcText(this.getData(this.name, this.depth));
        if (this.children.isEmpty()) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.depth + 1; ++i) {
            builder.append("    ");
        }
        builder.append(MathUtils.toLagStringProbe("Self", this.getSelf()));
        info.mcText((Component)Component.m_237113_((String)builder.toString()).m_130940_(ChatFormatting.AQUA));
    }

    public Component getData(String name, int depth) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < depth; ++i) {
            builder.append("    ");
        }
        builder.append(name).append(" [Min=").append(this.getTime(this.getMinTime())).append(", Avg=").append(this.getTime(this.getNanoTime()));
        builder.append(", Max=").append(this.getTime(this.getMaxTime())).append("]");
        return Component.m_237113_((String)builder.toString()).m_130940_(this.children.isEmpty() ? ChatFormatting.AQUA : ChatFormatting.GREEN);
    }

    private String getTime(long time) {
        if (time >= 1000000L) {
            return Formatters.EU_READER_FORMAT.format((double)time / 1000000.0) + "ms";
        }
        if (time >= 1000L) {
            return Formatters.EU_READER_FORMAT.format((double)time / 1000.0) + "qs";
        }
        return Formatters.EU_READER_FORMAT.format(time) + "ns";
    }
}

