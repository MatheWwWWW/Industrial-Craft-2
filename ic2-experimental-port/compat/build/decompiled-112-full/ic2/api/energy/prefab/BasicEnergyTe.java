/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.tileentity.TileEntity
 */
package ic2.api.energy.prefab;

import ic2.api.energy.prefab.BasicEnergyTile;
import ic2.api.energy.prefab.BasicSink;
import ic2.api.energy.prefab.BasicSource;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class BasicEnergyTe<T extends BasicEnergyTile>
extends TileEntity {
    protected T energyBuffer;

    protected BasicEnergyTe() {
    }

    public T getEnergyBuffer() {
        return this.energyBuffer;
    }

    public void onLoad() {
        ((BasicEnergyTile)this.energyBuffer).onLoad();
    }

    public void func_145843_s() {
        super.func_145843_s();
        ((BasicEnergyTile)this.energyBuffer).invalidate();
    }

    public void onChunkUnload() {
        ((BasicEnergyTile)this.energyBuffer).onChunkUnload();
    }

    public void func_145839_a(NBTTagCompound nbt) {
        super.func_145839_a(nbt);
        ((BasicEnergyTile)this.energyBuffer).readFromNBT(nbt);
    }

    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        return ((BasicEnergyTile)this.energyBuffer).writeToNBT(super.func_189515_b(nbt));
    }

    public static class Source
    extends BasicEnergyTe<BasicSource> {
        public Source(int capacity, int tier) {
            this.energyBuffer = new BasicSource(this, (double)capacity, tier);
        }
    }

    public static class Sink
    extends BasicEnergyTe<BasicSink> {
        public Sink(int capacity, int tier) {
            this.energyBuffer = new BasicSink(this, (double)capacity, tier);
        }
    }
}

