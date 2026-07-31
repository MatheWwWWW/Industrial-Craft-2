/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 */
package ic2.core.block.machines.logic.planner.newLogic;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.core.inventory.base.INBTSavable;
import ic2.core.inventory.inv.SimpleInventory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

public class ReactorBackup
implements INetworkDataBuffer,
INBTSavable {
    public SimpleInventory inventory = new SimpleInventory(54);
    public int customTicks;
    public int customHeat;
    public int reactorSize;
    public boolean hasBackup = false;
    public boolean isSteam;

    @Override
    public CompoundTag save(CompoundTag nbt) {
        nbt.m_128405_("CustomTicks", this.customTicks);
        nbt.m_128405_("CustomHeat", this.customHeat);
        nbt.m_128405_("Size", this.reactorSize);
        nbt.m_128379_("HasBackup", this.hasBackup);
        nbt.m_128379_("isSteam", this.isSteam);
        nbt.m_128365_("setup", (Tag)this.inventory.save(new CompoundTag()));
        return nbt;
    }

    @Override
    public void load(CompoundTag nbt) {
        this.customTicks = nbt.m_128451_("CustomTicks");
        this.customHeat = nbt.m_128451_("CustomHeat");
        this.reactorSize = nbt.m_128451_("Size");
        this.hasBackup = nbt.m_128471_("HasBackup");
        this.isSteam = nbt.m_128471_("isSteam");
        this.inventory.load(nbt.m_128469_("setup"));
    }

    @Override
    public void read(IInputBuffer buffer) {
        this.hasBackup = buffer.readBoolean();
        this.reactorSize = buffer.readByte();
    }

    @Override
    public void write(IOutputBuffer buffer) {
        buffer.writeBoolean(this.hasBackup);
        buffer.writeByte((byte)this.reactorSize);
    }
}

