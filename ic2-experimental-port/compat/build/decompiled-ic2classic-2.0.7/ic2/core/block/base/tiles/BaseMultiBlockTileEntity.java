/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.LongArrayList
 *  it.unimi.dsi.fastutil.longs.LongArrays
 *  it.unimi.dsi.fastutil.longs.LongCollection
 *  it.unimi.dsi.fastutil.longs.LongList
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.common.capabilities.Capability
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.common.util.LazyOptional
 */
package ic2.core.block.base.tiles;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.network.tile.INetworkEventListener;
import ic2.api.network.tile.PacketRange;
import ic2.api.util.DirectionList;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.multiblock.IMultiBlockClickable;
import ic2.core.block.base.features.multiblock.IStructureListener;
import ic2.core.block.base.misc.LinkStorage;
import ic2.core.block.base.tiles.BaseInventoryTileEntity;
import ic2.core.block.base.tiles.BaseLinkingTileEntity;
import ic2.core.block.rendering.world.impl.BlockHighlighter;
import ic2.core.inventory.base.INBTSavable;
import ic2.core.platform.events.MultiBlockManager;
import ic2.core.platform.events.StructureManager;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.helpers.capabilities.IToggleableCapabilityProvider;
import ic2.core.utils.helpers.capabilities.StructureCapabilityProvider;
import ic2.core.utils.math.StructureBuilder;
import ic2.core.utils.math.geometry.Box;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongArrays;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;

public abstract class BaseMultiBlockTileEntity
extends BaseInventoryTileEntity
implements IStructureListener,
ITickListener,
INetworkEventListener {
    @NetworkInfo
    public boolean isValid = false;
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_16)
    public StructurePos structure = new StructurePos();
    @NetworkInfo
    public LinkStorage children = new LinkStorage("children", this);
    protected boolean registered = false;
    protected boolean dynamic = false;
    protected Box boundingBox;
    Map<Capability<?>, IToggleableCapabilityProvider> capabilities = CollectionUtils.createMap();

    public BaseMultiBlockTileEntity(BlockPos pos, BlockState state, int size) {
        super(pos, state, size);
        this.addNetworkFields("isValid", "structure");
        this.addCapability(ForgeCapabilities.ITEM_HANDLER, this.handler);
    }

    @Override
    public void addCapability(Capability<?> cap, Object value) {
        this.addCapability(cap, StructureCapabilityProvider.create(cap, value));
    }

    public void addCapability(Capability<?> cap, IToggleableCapabilityProvider provider) {
        this.capabilities.put(cap, provider);
    }

    @Override
    public void removeCapability(Capability<?> cap) {
        IToggleableCapabilityProvider entry = this.capabilities.remove(cap);
        if (entry != null) {
            entry.setActive(false);
        }
    }

    @Override
    public <T> T getInternalCapability(Capability<T> cap) {
        IToggleableCapabilityProvider entry = this.capabilities.get(cap);
        return (T)(entry != null ? entry.getCapability(cap).orElse(null) : null);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        IToggleableCapabilityProvider entry = this.capabilities.get(cap);
        return entry != null ? entry.getCapability(cap, side) : super.getCapability(cap, side);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putBoolean(compound, "structure_valid", this.isValid, true);
        if (this.boundingBox != null) {
            compound.m_128365_("bounds", (Tag)this.boundingBox.saveRelative());
        }
        this.structure.save(compound);
        this.children.save(compound);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.isValid = NBTUtils.getBoolean(compound, "structure_valid", true);
        this.boundingBox = compound.m_128441_("bounds") ? Box.relative(compound.m_128463_("bounds")) : null;
        this.structure.load(compound);
        this.children.load(compound);
    }

    public abstract boolean isStateStillValid(BlockPos var1, BlockPos var2, BlockState var3);

    public abstract boolean testStructure(StructureBuilder var1);

    public void onStructureInvalidated(boolean wasValid, boolean removeMaster) {
        this.children.clear(wasValid);
    }

    public void onStructureValidated(boolean revalidation) {
        if (revalidation) {
            this.children.refreshReferences();
        } else {
            this.children.finish();
            this.onInitialValidation();
            this.sendToClient(0, 0, PacketRange.CHUNK_TRACKED);
        }
    }

    public void onStructureTick() {
    }

    protected void onInitialValidation() {
    }

    public boolean doesNeedTickAfterValidation() {
        return true;
    }

    @OnlyIn(value=Dist.CLIENT)
    public AABB getRenderBoundingBox() {
        return this.boundingBox != null ? this.boundingBox.toInclusiveAxis() : super.getRenderBoundingBox();
    }

    public Box getBox() {
        return !this.isValid ? new Box() : this.boundingBox.copy();
    }

    public boolean isDynamic() {
        return this.dynamic;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onNetworkFieldChanged(Set<String> fields, Player player) {
        super.onNetworkFieldChanged(fields, player);
        if (fields.contains("isValid") || fields.contains("structure")) {
            if (this.isValid && this.structure.structurePositions != null) {
                if (this instanceof IMultiBlockClickable) {
                    MultiBlockManager.INSTANCE.addListeners(this.m_58904_(), this.f_58858_, this.structure.structurePositions, (IMultiBlockClickable)((Object)this));
                }
                this.boundingBox = Box.fromPosArray(this.structure.structurePositions);
            } else {
                MultiBlockManager.INSTANCE.removeListeners(this.m_58904_(), this.f_58858_);
                this.boundingBox = null;
            }
        }
    }

    @Override
    public void onServerDataReceived(int key, int value) {
        if (key == 0) {
            BlockHighlighter.INSTANCE.addHighlight(this.m_58899_(), 100, 0x78000000, true, T -> {
                if (this.m_58901_()) {
                    return false;
                }
                if (this.boundingBox == null) {
                    return true;
                }
                T.renderBox = this.boundingBox.toInclusiveAxis();
                return true;
            });
        }
    }

    @Override
    public void markStructureDirty(BlockPos pos, BlockState newState) {
        if (!this.isStateStillValid(pos, this.toRelativePosition(pos), newState)) {
            this.invalidateStructure();
        }
    }

    @Override
    public void handleRedstone() {
        if (this.needsRedstoneUpdate && this.sensitive) {
            this.needsRedstoneUpdate = false;
            if (this.directionalSignal) {
                byte max = 0;
                for (Direction dir : DirectionList.ALL) {
                    byte strength = (byte)Mth.m_14045_((int)this.f_58857_.m_46681_(this.m_58899_(), dir), (int)0, (int)15);
                    max = (byte)Math.max(max, strength);
                    this.sidedSignals[dir.m_122411_()] = strength;
                }
                for (BaseLinkingTileEntity tile : this.children.getTiles()) {
                    for (Direction dir : DirectionList.ALL) {
                        byte strength = (byte)Mth.m_14045_((int)this.f_58857_.m_46681_(tile.m_58899_(), dir), (int)0, (int)15);
                        max = (byte)Math.max(max, strength);
                        this.sidedSignals[dir.m_122411_()] = strength;
                    }
                }
                this.signal = max;
                return;
            }
            this.signal = (byte)Mth.m_14045_((int)this.f_58857_.m_46755_(this.f_58858_), (int)0, (int)15);
            if (this.signal >= 15) {
                return;
            }
            List<BaseLinkingTileEntity> tiles = this.children.getTiles();
            int m = tiles.size();
            for (int i = 0; i < m && this.signal < 15; ++i) {
                this.signal = (byte)Math.max(this.signal, Mth.m_14045_((int)this.f_58857_.m_46755_(tiles.get(i).m_58899_()), (int)0, (int)15));
            }
        }
    }

    @Override
    protected boolean handleComparators(boolean ignoreTick) {
        boolean result = super.handleComparators(ignoreTick);
        if (result && this.isSimulating()) {
            for (BaseLinkingTileEntity tile : this.children.getTiles()) {
                this.f_58857_.m_46717_(tile.m_58899_(), tile.m_58900_().m_60734_());
            }
        }
        return result;
    }

    protected final BlockPos toRelativePosition(BlockPos origin) {
        return origin.m_121996_((Vec3i)this.m_58899_());
    }

    @Override
    public void onTick() {
        if ((!this.isValid || this.dynamic) && this.clock(512)) {
            this.checkStructure();
        }
        if (this.isValid) {
            this.onStructureTick();
            this.handleComparators();
        }
    }

    @Override
    public void onLoaded() {
        if (this.isSimulating()) {
            this.checkStructure();
        }
        super.onLoaded();
    }

    @Override
    public void onUnloaded(boolean chunk) {
        for (IToggleableCapabilityProvider provider : this.capabilities.values()) {
            provider.setActive(false);
        }
        this.capabilities.clear();
        if (this.isValid) {
            if (this.isSimulating()) {
                StructureManager.INSTANCE.clearListeners(this.m_58904_(), (LongCollection)new LongOpenHashSet(this.structure.structurePositions));
                this.registered = false;
                if (!chunk) {
                    this.onStructureInvalidated(true, true);
                }
            }
            MultiBlockManager.INSTANCE.removeListeners(this.m_58904_(), this.f_58858_);
        }
        super.onUnloaded(chunk);
    }

    protected void checkStructure() {
        boolean skip = false;
        if (this.registered) {
            StructureManager.INSTANCE.clearListeners(this.m_58904_(), (LongCollection)new LongOpenHashSet(this.structure.structurePositions));
            this.registered = false;
            skip = this.dynamic;
        }
        if ((this.isValid || this.structure.structurePositions != null) && !skip) {
            boolean bl = this.isValid = !StructureManager.INSTANCE.isBlocked(this.m_58904_(), (LongList)LongArrayList.wrap((long[])this.structure.structurePositions));
            if (this.isValid) {
                if (this.boundingBox == null) {
                    this.boundingBox = Box.fromPosArray(this.structure.structurePositions).offset(-this.f_58858_.m_123341_(), -this.f_58858_.m_123342_(), -this.f_58858_.m_123343_());
                }
                this.validateStructure(true);
            }
            return;
        }
        StructureBuilder builder = new StructureBuilder(this.m_58904_(), this.m_58899_(), this.getFacing().m_122424_());
        if (this.testStructure(builder)) {
            this.structure.setStructurePos(builder.getPositions());
            this.boundingBox = Box.fromPosArray(this.structure.structurePositions).offset(-this.f_58858_.m_123341_(), -this.f_58858_.m_123342_(), -this.f_58858_.m_123343_());
            boolean bl = this.isValid = !StructureManager.INSTANCE.isBlocked(this.m_58904_(), (LongList)LongArrayList.wrap((long[])this.structure.structurePositions));
            if (this.isValid) {
                this.validateStructure(false);
            }
            this.updateTileFields("structure", "isValid");
            return;
        }
        this.invalidateStructure();
    }

    protected void validateStructure(boolean revalidate) {
        StructureManager.INSTANCE.addListeners(this.m_58904_(), (LongCollection)LongArrayList.wrap((long[])this.structure.structurePositions), this);
        if (this instanceof IMultiBlockClickable) {
            MultiBlockManager.INSTANCE.addListeners(this.m_58904_(), this.f_58858_, this.structure.structurePositions, (IMultiBlockClickable)((Object)this));
        }
        for (IToggleableCapabilityProvider provider : this.capabilities.values()) {
            provider.setActive(true);
        }
        if (!this.doesNeedTickAfterValidation()) {
            this.removeFromTick();
        }
        this.onStructureValidated(revalidate);
        this.registered = true;
        this.notifyChanges(false, DirectionList.ALL);
    }

    protected void invalidateStructure() {
        boolean lastValid = this.isValid;
        if (this.isValid) {
            StructureManager.INSTANCE.clearListeners(this.m_58904_(), (LongCollection)new LongOpenHashSet(this.structure.structurePositions));
            MultiBlockManager.INSTANCE.removeListeners(this.m_58904_(), this.f_58858_);
            this.registered = false;
            this.isValid = false;
            this.updateTileField("isValid");
            if (!this.doesNeedTickAfterValidation()) {
                this.addToTick();
            }
        }
        for (IToggleableCapabilityProvider provider : this.capabilities.values()) {
            provider.setActive(false);
        }
        this.structure.setStructurePos(null);
        this.boundingBox = null;
        this.updateTileField("structure");
        this.onStructureInvalidated(lastValid, false);
        this.notifyChanges(false, DirectionList.ALL);
    }

    public class StructurePos
    implements INBTSavable,
    INetworkDataBuffer {
        public long[] structurePositions;

        public void setStructurePos(long[] array) {
            this.structurePositions = array;
            if (this.structurePositions != null) {
                LongArrays.quickSort((long[])array, this::sort);
            }
        }

        private int sort(long key, long value) {
            int result = Integer.compare(BlockPos.m_122015_((long)key), BlockPos.m_122015_((long)value));
            if (result != 0) {
                return result;
            }
            result = Integer.compare(BlockPos.m_122008_((long)key), BlockPos.m_122008_((long)value));
            if (result != 0) {
                return result;
            }
            return Integer.compare(BlockPos.m_121983_((long)key), BlockPos.m_121983_((long)value));
        }

        public boolean isFullyUsed() {
            if (this.structurePositions == null) {
                return false;
            }
            return BaseMultiBlockTileEntity.this.boundingBox != null && BaseMultiBlockTileEntity.this.boundingBox.size() - 1 == this.structurePositions.length;
        }

        public boolean isFrameUsed() {
            if (this.structurePositions == null) {
                return false;
            }
            return BaseMultiBlockTileEntity.this.boundingBox != null && BaseMultiBlockTileEntity.this.boundingBox.hollowSize() - 1 == this.structurePositions.length;
        }

        @Override
        public void write(IOutputBuffer buffer) {
            if (this.structurePositions == null) {
                buffer.writeByte((byte)0);
                return;
            }
            if (this.isFullyUsed()) {
                buffer.writeByte((byte)1);
                buffer.writeBytes(BaseMultiBlockTileEntity.this.boundingBox.saveRelative().m_128227_());
                return;
            }
            if (this.isFrameUsed()) {
                buffer.writeByte((byte)2);
                buffer.writeBytes(BaseMultiBlockTileEntity.this.boundingBox.saveRelative().m_128227_());
                return;
            }
            buffer.writeByte((byte)3);
            buffer.writeVarInt(this.structurePositions.length);
            for (long pos : this.structurePositions) {
                buffer.writeByte((byte)(BlockPos.m_121983_((long)pos) - BaseMultiBlockTileEntity.this.m_58899_().m_123341_()));
                buffer.writeByte((byte)(BlockPos.m_122008_((long)pos) - BaseMultiBlockTileEntity.this.m_58899_().m_123342_()));
                buffer.writeByte((byte)(BlockPos.m_122015_((long)pos) - BaseMultiBlockTileEntity.this.m_58899_().m_123343_()));
            }
        }

        @Override
        public void read(IInputBuffer buffer) {
            byte value = buffer.readByte();
            if (value == 1) {
                this.structurePositions = Box.relative(buffer.readBytes()).asLongArray(BaseMultiBlockTileEntity.this.m_58899_());
            } else if (value == 2) {
                this.structurePositions = Box.relative(buffer.readBytes()).asHollowArray(BaseMultiBlockTileEntity.this.m_58899_());
            } else if (value == 3) {
                int size = buffer.readVarInt();
                this.structurePositions = new long[size];
                long base = BaseMultiBlockTileEntity.this.m_58899_().m_121878_();
                for (int i = 0; i < size; ++i) {
                    this.structurePositions[i] = BlockPos.m_121910_((long)base, (int)buffer.readByte(), (int)buffer.readByte(), (int)buffer.readByte());
                }
            }
        }

        @Override
        public CompoundTag save(CompoundTag nbt) {
            if (this.structurePositions == null) {
                nbt.m_128473_("structure_pos");
            } else if (this.isFullyUsed()) {
                nbt.m_128379_("fullyused", true);
            } else if (this.isFrameUsed()) {
                nbt.m_128379_("frameused", true);
            } else {
                byte[] data = new byte[this.structurePositions.length * 3];
                for (long pos : this.structurePositions) {
                    data[i * 3] = (byte)(BlockPos.m_121983_((long)pos) - BaseMultiBlockTileEntity.this.m_58899_().m_123341_());
                    data[i * 3 + 1] = (byte)(BlockPos.m_122008_((long)pos) - BaseMultiBlockTileEntity.this.m_58899_().m_123342_());
                    data[i * 3 + 2] = (byte)(BlockPos.m_122015_((long)pos) - BaseMultiBlockTileEntity.this.m_58899_().m_123343_());
                }
                nbt.m_128382_("structure_pos", data);
                nbt.m_128344_("structureSize", (byte)this.structurePositions.length);
            }
            return nbt;
        }

        @Override
        public void load(CompoundTag nbt) {
            if (nbt.m_128441_("fullyused")) {
                this.structurePositions = BaseMultiBlockTileEntity.this.boundingBox.asLongArray(BaseMultiBlockTileEntity.this.m_58899_());
                return;
            }
            if (nbt.m_128441_("frameused")) {
                this.structurePositions = BaseMultiBlockTileEntity.this.boundingBox.asHollowArray(BaseMultiBlockTileEntity.this.m_58899_());
                return;
            }
            byte[] array = nbt.m_128463_("structure_pos");
            if (array.length > 0) {
                long base = BaseMultiBlockTileEntity.this.m_58899_().m_121878_();
                this.structurePositions = new long[array.length / 3];
                int m = array.length / 3;
                for (int i = 0; i < m; ++i) {
                    this.structurePositions[i] = BlockPos.m_121910_((long)base, (int)array[i * 3], (int)array[i * 3 + 1], (int)array[i * 3 + 2]);
                }
            }
        }
    }
}

