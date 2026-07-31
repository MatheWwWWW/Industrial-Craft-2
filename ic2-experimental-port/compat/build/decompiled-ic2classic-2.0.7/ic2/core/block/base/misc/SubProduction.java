/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 */
package ic2.core.block.base.misc;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.core.inventory.base.INBTSavable;
import ic2.core.utils.helpers.NBTUtils;
import net.minecraft.nbt.CompoundTag;

public class SubProduction
implements INetworkDataBuffer,
INBTSavable {
    int production = -1;
    int stored;

    public boolean needsInit() {
        return this.production == -1;
    }

    public void setProduction(int points) {
        this.production = points;
    }

    public boolean update(int divider) {
        if (this.production <= 0) {
            return false;
        }
        this.stored += this.production;
        return this.stored > divider;
    }

    public void add(int points) {
        this.stored += points;
    }

    public int consume(int divider, boolean doConsume) {
        int result = this.stored / divider;
        if (result > 0 && doConsume) {
            this.stored -= result * divider;
        }
        return result;
    }

    public int getProduction() {
        return this.production;
    }

    public float getProduction(float divider) {
        return (float)this.production / divider;
    }

    public float getProduction(float remover, float divider) {
        return ((float)this.production - remover) / divider;
    }

    @Override
    public void write(IOutputBuffer buffer) {
        buffer.writeVarInt(this.stored);
        buffer.writeVarInt(this.production);
    }

    @Override
    public void read(IInputBuffer buffer) {
        this.stored = buffer.readVarInt();
        this.production = buffer.readVarInt();
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        NBTUtils.putInt(nbt, "stored", this.stored, 0);
        NBTUtils.putInt(nbt, "production", this.production, 0);
        return nbt;
    }

    @Override
    public void load(CompoundTag nbt) {
        this.stored = nbt.m_128451_("stored");
        this.production = nbt.m_128451_("production");
    }
}

