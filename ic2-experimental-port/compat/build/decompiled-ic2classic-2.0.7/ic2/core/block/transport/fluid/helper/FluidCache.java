/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraftforge.fluids.capability.IFluidHandler
 */
package ic2.core.block.transport.fluid.helper;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.api.util.DirectionList;
import ic2.core.block.base.cache.ICache;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.block.transport.fluid.graph.FluidNet;
import ic2.core.block.transport.fluid.graph.IFluidPipe;
import ic2.core.block.transport.fluid.helper.FluidCacheEntry;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.NBTListWrapper;
import ic2.core.utils.helpers.NBTUtils;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class FluidCache<T extends BaseTileEntity>
implements INetworkDataBuffer {
    final FluidCacheEntry[] entries = new FluidCacheEntry[6];
    final String variableName;
    final T tile;
    final ICache<IFluidHandler> cache;
    final BiPredicate<Direction, IFluidPipe> canConnectCheck;
    boolean addedToFluidNet;
    boolean updatingFluidNet;
    int tanks;

    public FluidCache(String variableName, T tile, ICache<IFluidHandler> cache, BiPredicate<Direction, IFluidPipe> canConnectCheck) {
        this.variableName = variableName;
        this.tile = tile;
        this.cache = cache;
        this.canConnectCheck = canConnectCheck;
        for (Direction dir : DirectionList.ALL) {
            this.entries[dir.m_122411_()] = new FluidCacheEntry((IFluidPipe)tile, dir, cache);
        }
    }

    public void remove() {
        if (((BaseTileEntity)this.tile).isRendering() || !this.addedToFluidNet) {
            return;
        }
        FluidNet.INSTANCE.removePipe((IFluidPipe)this.tile);
        this.addedToFluidNet = false;
    }

    public void add() {
        if (((BaseTileEntity)this.tile).isRendering() || this.addedToFluidNet) {
            return;
        }
        this.updatingFluidNet = true;
        FluidNet.INSTANCE.addPipe((IFluidPipe)this.tile);
        this.updatingFluidNet = false;
        this.addedToFluidNet = true;
    }

    public void update() {
        this.updatingFluidNet = true;
        FluidNet.INSTANCE.updatePipe((IFluidPipe)this.tile);
        this.updatingFluidNet = false;
    }

    public void protect(Runnable run) {
        this.updatingFluidNet = true;
        run.run();
        this.updatingFluidNet = false;
    }

    public boolean isAdded() {
        return this.addedToFluidNet;
    }

    public void setAdded(boolean value) {
        this.addedToFluidNet = value;
    }

    public int updateConnectivity() {
        DirectionList dirs = DirectionList.EMPTY;
        for (Direction dir : DirectionList.ALL) {
            IFluidPipe pipe = FluidNet.INSTANCE.getPipe(this.tile.m_58904_(), this.tile.m_58899_().m_121945_(dir));
            if (pipe == null || !this.canConnectCheck.test(dir, pipe)) continue;
            dirs = dirs.add(dir);
        }
        int newTanks = this.cache.getPresentSides().getCode();
        if (this.addedToFluidNet && !this.updatingFluidNet && this.tanks != newTanks) {
            this.update();
            this.tanks = newTanks;
            if (this.variableName != null) {
                ((BaseTileEntity)this.tile).updateTileField(this.variableName);
            }
        }
        return dirs.getCode();
    }

    public List<IFluidPipe.FluidOutput> getOutputs() {
        for (Direction dir : DirectionList.ofNumber(this.tanks).remove(this.cache.getPresentSides())) {
            this.entries[dir.m_122411_()].updateOutput();
        }
        if (this.cache.isEmpty()) {
            return Collections.emptyList();
        }
        ObjectList outs = CollectionUtils.createList();
        DirectionList newConnections = DirectionList.EMPTY;
        for (Direction dir : this.cache.getPresentSides()) {
            IFluidPipe pipe = FluidNet.INSTANCE.getPipe(this.tile.m_58904_(), this.tile.m_58899_().m_121945_(dir));
            FluidCacheEntry entry = this.entries[dir.m_122411_()];
            if (pipe != null && !pipe.isFluidAcceptor() || !entry.updateOutput()) continue;
            newConnections = newConnections.add(dir);
            if (!entry.hasOutput()) continue;
            outs.add((IFluidPipe.FluidOutput)entry.getOutput());
        }
        if (newConnections.getCode() != this.tanks) {
            this.tanks = newConnections.getCode();
            if (this.variableName != null) {
                ((BaseTileEntity)this.tile).updateTileField(this.variableName);
            }
        }
        return outs;
    }

    public FluidCacheEntry get(int index) {
        return this.entries[index];
    }

    public IFluidPipe.FluidOutput get(Direction dir) {
        return this.entries[dir.m_122411_()].getOutput();
    }

    public int getTanks() {
        return this.tanks;
    }

    @Override
    public void write(IOutputBuffer buffer) {
        buffer.writeByte((byte)this.tanks);
    }

    @Override
    public void read(IInputBuffer buffer) {
        this.tanks = buffer.readByte();
    }

    public CompoundTag save(CompoundTag nbt, boolean hasFluid) {
        NBTUtils.putByte(nbt, "tanks", this.tanks, 0);
        ListTag list = new ListTag();
        for (int i = 0; i < 6; ++i) {
            CompoundTag data = this.entries[i].save(new CompoundTag());
            if (data.m_128456_()) continue;
            data.m_128344_("index", (byte)i);
            list.add((Object)data);
        }
        NBTUtils.putIf(nbt, "settings", list, hasFluid);
        return nbt;
    }

    public void load(CompoundTag nbt) {
        this.tanks = nbt.m_128451_("tanks");
        for (int i = 0; i < 6; ++i) {
            this.entries[i].clear();
        }
        for (CompoundTag data : NBTListWrapper.wrap(nbt.m_128437_("settings", 10), CompoundTag.class)) {
            int index = data.m_128451_("index");
            if (index < 0 || index >= 6) continue;
            this.entries[index].load(data);
        }
    }
}

