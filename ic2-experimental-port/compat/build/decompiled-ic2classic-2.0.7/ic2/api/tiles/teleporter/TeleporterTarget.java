/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Objects
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Registry
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraftforge.server.ServerLifecycleHooks
 */
package ic2.api.tiles.teleporter;

import com.google.common.base.Objects;
import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.server.ServerLifecycleHooks;

public class TeleporterTarget
implements INetworkDataBuffer {
    ResourceKey<Level> dimension;
    BlockPos targetPosition;

    private TeleporterTarget() {
    }

    public TeleporterTarget(TeleporterTarget target) {
        this.dimension = target.getDimension();
        this.targetPosition = target.getTargetPosition().m_7949_();
    }

    public TeleporterTarget(BlockEntity tile) {
        this(tile.m_58904_(), tile.m_58899_());
    }

    public TeleporterTarget(Level world, BlockPos pos) {
        this.dimension = world.m_46472_();
        this.targetPosition = pos.m_7949_();
    }

    @Override
    public void write(IOutputBuffer buffer) {
        buffer.writeRegistryKey(this.dimension);
        buffer.writeLong(this.targetPosition.m_121878_());
    }

    @Override
    public void read(IInputBuffer buffer) {
        this.dimension = buffer.readRegistryKey(Registry.f_122819_);
        this.targetPosition = BlockPos.m_122022_((long)buffer.readLong());
    }

    public ResourceKey<Level> getDimension() {
        return this.dimension;
    }

    public BlockPos getTargetPosition() {
        return this.targetPosition;
    }

    public ServerLevel getWorld() {
        return ServerLifecycleHooks.getCurrentServer().m_129880_(this.dimension);
    }

    public BlockEntity getTile() {
        return this.getWorld().m_7702_(this.getTargetPosition());
    }

    public boolean isSame(BlockEntity tile) {
        return this.isSame((ResourceKey<Level>)tile.m_58904_().m_46472_(), tile.m_58899_());
    }

    public boolean isSame(ResourceKey<Level> type, BlockPos pos) {
        return this.dimension == type && this.targetPosition.equals((Object)pos);
    }

    public int hashCode() {
        return Objects.hashCode((Object[])new Object[]{this.dimension.m_135782_().hashCode(), this.targetPosition.hashCode()});
    }

    public boolean equals(Object obj) {
        if (obj instanceof TeleporterTarget) {
            TeleporterTarget target = (TeleporterTarget)obj;
            return target.dimension == this.dimension && target.targetPosition.equals((Object)this.targetPosition);
        }
        return false;
    }

    public static TeleporterTarget readFromBuffer(IInputBuffer buffer) {
        TeleporterTarget target = new TeleporterTarget();
        target.read(buffer);
        return target.dimension == null ? null : target;
    }

    public static TeleporterTarget read(CompoundTag nbt) {
        if (nbt.m_128456_()) {
            return null;
        }
        TeleporterTarget target = new TeleporterTarget();
        target.dimension = ResourceKey.m_135785_((ResourceKey)Registry.f_122819_, (ResourceLocation)new ResourceLocation(nbt.m_128461_("id")));
        target.targetPosition = BlockPos.m_122022_((long)nbt.m_128454_("pos"));
        return target;
    }

    public CompoundTag write() {
        return this.write(new CompoundTag());
    }

    public CompoundTag write(CompoundTag nbt) {
        nbt.m_128359_("id", this.dimension.m_135782_().toString());
        nbt.m_128356_("pos", this.targetPosition.m_121878_());
        return nbt;
    }
}

