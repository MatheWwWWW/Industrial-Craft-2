/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 */
package ic2.core.block.personal.trade;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.core.block.personal.trade.Trade;
import ic2.core.utils.helpers.NBTUtils;
import net.minecraft.nbt.CompoundTag;

public class EnergyTrade
extends Trade {
    public int offeredEnergy = 0;

    @Override
    public void write(IOutputBuffer buffer) {
        buffer.writeVarInt(this.offeredEnergy);
        super.write(buffer);
    }

    @Override
    public void read(IInputBuffer buffer) {
        this.offeredEnergy = buffer.readVarInt();
        super.read(buffer);
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        super.save(nbt);
        NBTUtils.putInt(nbt, "energy", this.offeredEnergy, 0);
        return nbt;
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.offeredEnergy = nbt.m_128451_("energy");
    }

    public boolean canAdd(int alreadyOffered) {
        return (long)alreadyOffered + (long)this.offeredEnergy < Integer.MAX_VALUE;
    }
}

