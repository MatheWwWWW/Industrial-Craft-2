/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.level.Level
 */
package ic2.core.block.comp;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IChargingSlot;
import ic2.api.energy.tile.IDischargingSlot;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.energy.tile.IMultiEnergySource;
import ic2.api.info.ILocatable;
import ic2.core.IC2;
import ic2.core.block.comp.TileEntityComponent;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.network.GrowingBuffer;
import ic2.core.util.LogCategory;
import ic2.core.util.Util;
import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public class Energy
extends TileEntityComponent {
    private static final boolean debugLoad = System.getProperty("ic2.comp.energy.debugload") != null;
    private double capacity;
    private double storage;
    private int sinkTier;
    private int sourceTier;
    private Set<Direction> sinkDirections;
    private Set<Direction> sourceDirections;
    private List<InvSlot> managedSlots;
    private boolean multiSource = false;
    private int sourcePackets = 1;
    private EnergyNetDelegate delegate;
    private boolean loaded;
    private boolean receivingDisabled;
    private boolean sendingSidabled;
    private final boolean fullEnergy;

    public static Energy asBasicSink(Ic2TileEntity ic2TileEntity, double d) {
        return Energy.asBasicSink(ic2TileEntity, d, 1);
    }

    public static Energy asBasicSink(Ic2TileEntity ic2TileEntity, double d, int n) {
        return new Energy(ic2TileEntity, d, Util.allFacings, Collections.emptySet(), n);
    }

    public static Energy asBasicSource(Ic2TileEntity ic2TileEntity, double d) {
        return Energy.asBasicSource(ic2TileEntity, d, 1);
    }

    public static Energy asBasicSource(Ic2TileEntity ic2TileEntity, double d, int n) {
        return new Energy(ic2TileEntity, d, Collections.emptySet(), Util.allFacings, n);
    }

    public Energy(Ic2TileEntity ic2TileEntity, double d) {
        this(ic2TileEntity, d, Collections.emptySet(), Collections.emptySet(), 1);
    }

    public Energy(Ic2TileEntity ic2TileEntity, double d, Set<Direction> set, Set<Direction> set2, int n) {
        this(ic2TileEntity, d, set, set2, n, n, false);
    }

    public Energy(Ic2TileEntity ic2TileEntity, double d, Set<Direction> set, Set<Direction> set2, int n, int n2, boolean bl) {
        super(ic2TileEntity);
        this.capacity = d;
        this.sinkTier = n;
        this.sourceTier = n2;
        this.sinkDirections = set;
        this.sourceDirections = set2;
        this.fullEnergy = bl;
    }

    public Energy addManagedSlot(InvSlot invSlot) {
        if (invSlot instanceof IChargingSlot || invSlot instanceof IDischargingSlot) {
            if (this.managedSlots == null) {
                this.managedSlots = new ArrayList<InvSlot>(4);
            }
        } else {
            throw new IllegalArgumentException("No charge/discharge slot.");
        }
        this.managedSlots.add(invSlot);
        return this;
    }

    public Energy setMultiSource(boolean bl) {
        this.multiSource = bl;
        if (!bl) {
            this.sourcePackets = 1;
        }
        return this;
    }

    @Override
    public void readFromNbt(CompoundTag compoundTag) {
        this.storage = compoundTag.m_128459_("storage");
    }

    @Override
    public CompoundTag writeToNbt() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.m_128347_("storage", this.storage);
        return compoundTag;
    }

    @Override
    public void onLoaded() {
        assert (this.delegate == null);
        if (!this.parent.m_58904_().f_46443_) {
            if (!this.sinkDirections.isEmpty() || !this.sourceDirections.isEmpty()) {
                if (debugLoad) {
                    IC2.log.debug(LogCategory.Component, "Energy onLoaded for %s at %s.", this.parent, Util.formatPosition(this.parent));
                }
                this.createDelegate();
                EnergyNet.instance.addLocatableTile(this.delegate);
            } else if (debugLoad) {
                IC2.log.debug(LogCategory.Component, "Skipping Energy onLoaded for %s at %s.", this.parent, Util.formatPosition(this.parent));
            }
            this.loaded = true;
        }
    }

    private void createDelegate() {
        if (this.delegate != null) {
            throw new IllegalStateException();
        }
        assert (!this.sinkDirections.isEmpty() || !this.sourceDirections.isEmpty());
        this.delegate = this.sinkDirections.isEmpty() ? new EnergyNetDelegateSource(this.parent) : (this.sourceDirections.isEmpty() ? new EnergyNetDelegateSink(this.parent) : new EnergyNetDelegateDual(this.parent));
    }

    @Override
    public void onUnloaded() {
        if (this.delegate != null) {
            if (debugLoad) {
                IC2.log.debug(LogCategory.Component, "Energy onUnloaded for %s at %s.", this.parent, Util.formatPosition(this.parent));
            }
            EnergyNet.instance.removeTile(this.delegate);
            this.delegate = null;
        } else if (debugLoad) {
            IC2.log.debug(LogCategory.Component, "Skipping Energy onUnloaded for %s at %s.", this.parent, Util.formatPosition(this.parent));
        }
        this.loaded = false;
    }

    @Override
    public void onContainerUpdate(ServerPlayer serverPlayer) {
        GrowingBuffer growingBuffer = new GrowingBuffer(16);
        growingBuffer.writeDouble(this.capacity);
        growingBuffer.writeDouble(this.storage);
        growingBuffer.flip();
        this.setNetworkUpdate(serverPlayer, growingBuffer);
    }

    @Override
    public void onNetworkUpdate(DataInput dataInput) throws IOException {
        this.capacity = dataInput.readDouble();
        this.storage = dataInput.readDouble();
    }

    @Override
    public boolean enableWorldTick() {
        return !this.parent.m_58904_().f_46443_ && this.managedSlots != null;
    }

    @Override
    public void onWorldTick() {
        for (InvSlot invSlot : this.managedSlots) {
            double d;
            if (invSlot instanceof IChargingSlot) {
                if (!(this.storage > 0.0)) continue;
                this.storage -= ((IChargingSlot)((Object)invSlot)).charge(this.storage);
                continue;
            }
            if (!(invSlot instanceof IDischargingSlot) || !((d = this.capacity - this.storage) > 0.0)) continue;
            this.storage += ((IDischargingSlot)((Object)invSlot)).discharge(d, false);
        }
    }

    public double getCapacity() {
        return this.capacity;
    }

    public void setCapacity(double d) {
        this.capacity = d;
    }

    public double getEnergy() {
        return this.storage;
    }

    public double getFreeEnergy() {
        return Math.max(0.0, this.capacity - this.storage);
    }

    public double getFillRatio() {
        return this.storage / this.capacity;
    }

    public int getComparatorValue() {
        return Math.min((int)(this.storage * 15.0 / this.capacity), 15);
    }

    public double addEnergy(double d) {
        d = Math.min(this.capacity - this.storage, d);
        this.storage += d;
        return d;
    }

    public void forceAddEnergy(double d) {
        this.storage += d;
    }

    public boolean canUseEnergy(double d) {
        return this.storage >= d;
    }

    public boolean useEnergy(double d) {
        if (this.storage >= d) {
            this.storage -= d;
            return true;
        }
        return false;
    }

    public double useEnergy(double d, boolean bl) {
        double d2 = Math.abs(Math.max(0.0, d - this.storage) - d);
        if (bl) {
            return d2;
        }
        this.storage -= d2;
        return d2;
    }

    public int getSinkTier() {
        return this.sinkTier;
    }

    public void setSinkTier(int n) {
        this.sinkTier = n;
    }

    public int getSourceTier() {
        return this.sourceTier;
    }

    public void setSourceTier(int n) {
        this.sourceTier = n;
    }

    public void setEnabled(boolean bl) {
        this.sendingSidabled = !bl;
        this.receivingDisabled = this.sendingSidabled;
    }

    public void setReceivingEnabled(boolean bl) {
        this.receivingDisabled = !bl;
    }

    public void setSendingEnabled(boolean bl) {
        this.sendingSidabled = !bl;
    }

    public boolean isMultiSource() {
        return this.multiSource;
    }

    public void setPacketOutput(int n) {
        if (this.multiSource) {
            this.sourcePackets = n;
        }
    }

    public int getPacketOutput() {
        return this.sourcePackets;
    }

    public void setDirections(Set<Direction> set, Set<Direction> set2) {
        if (set.equals(this.sinkDirections) && set2.equals(this.sourceDirections)) {
            if (debugLoad) {
                IC2.log.debug(LogCategory.Component, "Energy setDirections unchanged for %s at %s, sink: %s, source: %s.", this.parent, Util.formatPosition(this.parent), set, set2);
            }
            return;
        }
        if (this.delegate != null) {
            if (debugLoad) {
                IC2.log.debug(LogCategory.Component, "Energy setDirections unload for %s at %s.", this.parent, Util.formatPosition(this.parent));
            }
            assert (!this.parent.m_58904_().f_46443_);
            EnergyNet.instance.removeTile(this.delegate);
        }
        this.sinkDirections = set;
        this.sourceDirections = set2;
        if (set.isEmpty() && set2.isEmpty()) {
            this.delegate = null;
        } else if (this.delegate == null && this.loaded) {
            this.createDelegate();
        }
        if (this.delegate != null) {
            if (debugLoad) {
                IC2.log.debug(LogCategory.Component, "Energy setDirections load for %s at %s, sink: %s, source: %s.", this.parent, Util.formatPosition(this.parent), set, set2);
            }
            assert (!this.parent.m_58904_().f_46443_);
            EnergyNet.instance.addLocatableTile(this.delegate);
        } else if (debugLoad) {
            IC2.log.debug(LogCategory.Component, "Skipping Energy setDirections load for %s at %s, sink: %s, source: %s, loaded: %b.", this.parent, Util.formatPosition(this.parent), set, set2, this.loaded);
        }
    }

    public Set<Direction> getSourceDirs() {
        return Collections.unmodifiableSet(this.sourceDirections);
    }

    public Set<Direction> getSinkDirs() {
        return Collections.unmodifiableSet(this.sinkDirections);
    }

    public IEnergyTile getDelegate() {
        return this.delegate;
    }

    private double getSourceEnergy() {
        if (this.fullEnergy) {
            return this.storage >= EnergyNet.instance.getPowerFromTier(this.sourceTier) ? this.storage : 0.0;
        }
        return this.storage;
    }

    private int getPacketCount() {
        if (this.fullEnergy) {
            return Math.min(this.sourcePackets, (int)Math.floor(this.storage / EnergyNet.instance.getPowerFromTier(this.sourceTier)));
        }
        return this.sourcePackets;
    }

    private abstract class EnergyNetDelegate
    implements ILocatable,
    IEnergyTile {
        private final Ic2TileEntity parent;

        protected EnergyNetDelegate(Ic2TileEntity ic2TileEntity) {
            this.parent = ic2TileEntity;
        }

        @Override
        public Level getWorldObj() {
            return this.parent.m_58904_();
        }

        @Override
        public BlockPos getPosition() {
            return this.parent.m_58899_();
        }
    }

    private class EnergyNetDelegateSource
    extends EnergyNetDelegate
    implements IMultiEnergySource {
        protected EnergyNetDelegateSource(Ic2TileEntity ic2TileEntity) {
            super(ic2TileEntity);
        }

        @Override
        public int getSourceTier() {
            return Energy.this.sourceTier;
        }

        @Override
        public boolean emitsEnergyTo(IEnergyAcceptor iEnergyAcceptor, Direction direction) {
            return Energy.this.sourceDirections.contains(direction);
        }

        @Override
        public double getOfferedEnergy() {
            assert (!Energy.this.sourceDirections.isEmpty());
            return !Energy.this.sendingSidabled ? Energy.this.getSourceEnergy() : 0.0;
        }

        @Override
        public void drawEnergy(double d) {
            assert (d <= Energy.this.storage);
            Energy.this.storage -= d;
        }

        @Override
        public boolean sendMultipleEnergyPackets() {
            return Energy.this.multiSource;
        }

        @Override
        public int getMultipleEnergyPacketAmount() {
            return Energy.this.getPacketCount();
        }
    }

    private class EnergyNetDelegateSink
    extends EnergyNetDelegate
    implements IEnergySink {
        protected EnergyNetDelegateSink(Ic2TileEntity ic2TileEntity) {
            super(ic2TileEntity);
        }

        @Override
        public int getSinkTier() {
            return Energy.this.sinkTier;
        }

        @Override
        public boolean acceptsEnergyFrom(IEnergyEmitter iEnergyEmitter, Direction direction) {
            return Energy.this.sinkDirections.contains(direction);
        }

        @Override
        public double getDemandedEnergy() {
            assert (!Energy.this.sinkDirections.isEmpty());
            return !Energy.this.receivingDisabled && Energy.this.storage < Energy.this.capacity ? Energy.this.capacity - Energy.this.storage : 0.0;
        }

        @Override
        public double injectEnergy(Direction direction, double d, double d2) {
            Energy.this.storage += d;
            return 0.0;
        }
    }

    private class EnergyNetDelegateDual
    extends EnergyNetDelegate
    implements IEnergySink,
    IMultiEnergySource {
        protected EnergyNetDelegateDual(Ic2TileEntity ic2TileEntity) {
            super(ic2TileEntity);
        }

        @Override
        public boolean acceptsEnergyFrom(IEnergyEmitter iEnergyEmitter, Direction direction) {
            return Energy.this.sinkDirections.contains(direction);
        }

        @Override
        public boolean emitsEnergyTo(IEnergyAcceptor iEnergyAcceptor, Direction direction) {
            return Energy.this.sourceDirections.contains(direction);
        }

        @Override
        public double getDemandedEnergy() {
            return !Energy.this.receivingDisabled && !Energy.this.sinkDirections.isEmpty() && Energy.this.storage < Energy.this.capacity ? Energy.this.capacity - Energy.this.storage : 0.0;
        }

        @Override
        public double getOfferedEnergy() {
            return !Energy.this.sendingSidabled && !Energy.this.sourceDirections.isEmpty() ? Energy.this.getSourceEnergy() : 0.0;
        }

        @Override
        public int getSinkTier() {
            return Energy.this.sinkTier;
        }

        @Override
        public int getSourceTier() {
            return Energy.this.sourceTier;
        }

        @Override
        public double injectEnergy(Direction direction, double d, double d2) {
            Energy.this.storage += d;
            return 0.0;
        }

        @Override
        public void drawEnergy(double d) {
            assert (d <= Energy.this.storage);
            Energy.this.storage -= d;
        }

        @Override
        public boolean sendMultipleEnergyPackets() {
            return Energy.this.multiSource;
        }

        @Override
        public int getMultipleEnergyPacketAmount() {
            return Energy.this.getPacketCount();
        }
    }
}

