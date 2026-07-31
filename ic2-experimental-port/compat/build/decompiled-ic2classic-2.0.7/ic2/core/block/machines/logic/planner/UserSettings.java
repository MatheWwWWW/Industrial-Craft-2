/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 */
package ic2.core.block.machines.logic.planner;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.core.inventory.base.INBTSavable;
import net.minecraft.nbt.CompoundTag;

public class UserSettings
implements INetworkDataBuffer,
INBTSavable {
    public int maxTicks = 0;
    public int startingHeat = 0;
    public int ticksPerTick = 1;

    @Override
    public void read(IInputBuffer buffer) {
        this.maxTicks = buffer.readInt();
        this.startingHeat = buffer.readInt();
        this.ticksPerTick = buffer.readInt();
    }

    @Override
    public void write(IOutputBuffer buffer) {
        buffer.writeInt(this.maxTicks);
        buffer.writeInt(this.startingHeat);
        buffer.writeInt(this.ticksPerTick);
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        nbt.m_128405_("Max", this.maxTicks);
        nbt.m_128405_("StartingHeat", this.startingHeat);
        nbt.m_128405_("Ticks", this.ticksPerTick);
        return nbt;
    }

    @Override
    public void load(CompoundTag nbt) {
        this.maxTicks = nbt.m_128451_("Max");
        this.startingHeat = nbt.m_128451_("StartingHeat");
        this.ticksPerTick = nbt.m_128451_("Ticks");
    }
}

