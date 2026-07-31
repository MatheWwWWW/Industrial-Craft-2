/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.LongIterator
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.LongTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machine.tileentity;

import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ChunkLoaderLogic;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.comp.Energy;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotDischarge;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.machine.container.ContainerChunkLoader;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.init.MainConfig;
import ic2.core.network.GrowingBuffer;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.util.ConfigUtil;
import ic2.core.util.LogCategory;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

@NotClassic
public class TileEntityChunkloader
extends TileEntityInventory
implements INetworkClientTileEntityEventListener,
IHasGui,
IUpgradableBlock {
    private final LongSet loadedChunks = new LongOpenHashSet();
    public final InvSlotUpgrade upgradeSlot;
    public final InvSlotDischarge dischargeSlot;
    public final Energy energy;
    private static final int defaultTier = 1;
    private static final int defaultEnergyStorage = 2500;
    private static final int range = 4;
    private final double euPerChunk = ConfigUtil.getFloat(MainConfig.get(), "balance/euPerChunk");

    public TileEntityChunkloader(BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityInventory>)Ic2BlockEntities.CHUNK_LOADER, blockPos, blockState);
        this.upgradeSlot = new InvSlotUpgrade(this, "upgrade", 4);
        this.dischargeSlot = new InvSlotDischarge(this, InvSlot.Access.IO, 1, true, InvSlot.InvSide.ANY);
        this.energy = this.addComponent(Energy.asBasicSink(this, 2500.0, 1).addManagedSlot(this.dischargeSlot));
    }

    @Override
    public void updateEntityServer() {
        super.updateEntityServer();
        boolean bl = this.energy.useEnergy((double)this.getLoadedChunks().size() * this.euPerChunk);
        if (bl != this.getActive()) {
            this.setActive(bl);
        }
        this.upgradeSlot.tick();
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        ListTag listTag = compoundTag.m_128437_("loadedChunks", 4);
        this.loadedChunks.clear();
        for (int i = 0; i < listTag.size(); ++i) {
            this.loadedChunks.add(((LongTag)listTag.get(i)).m_7046_());
        }
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        ListTag listTag = new ListTag();
        compoundTag.m_128365_("loadedChunks", (Tag)listTag);
        LongIterator longIterator = this.loadedChunks.iterator();
        while (longIterator.hasNext()) {
            long l = (Long)longIterator.next();
            listTag.add((Object)LongTag.m_128882_((long)l));
        }
    }

    @Override
    public void setActive(boolean bl) {
        Level level = this.m_58904_();
        if (!level.f_46443_ && this.getActive() != bl) {
            if (bl) {
                ChunkLoaderLogic.addChunkLoader((ServerLevel)level, this.f_58858_, this.loadedChunks);
            } else {
                ChunkLoaderLogic.removeChunkLoader((ServerLevel)level, this.f_58858_);
            }
        }
        super.setActive(bl);
    }

    @Override
    public void onLoaded() {
        super.onLoaded();
        Level level = this.m_58904_();
        if (!level.f_46443_) {
            this.setOverclockRates();
            if (this.getActive()) {
                ChunkLoaderLogic.addChunkLoader((ServerLevel)level, this.f_58858_, this.loadedChunks);
            }
        }
    }

    @Override
    public void onPlaced(ItemStack itemStack, LivingEntity livingEntity, Direction direction) {
        super.onPlaced(itemStack, livingEntity, direction);
        this.loadedChunks.add(ChunkPos.m_151388_((BlockPos)this.f_58858_));
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        return new ContainerChunkLoader(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerChunkLoader(n, inventory, this);
    }

    public void addChunkToLoaded(ChunkPos chunkPos) {
        if (this.f_58857_.f_46443_) {
            new RuntimeException("Something tried to change the ChunkLoaderState on the client.").printStackTrace();
            return;
        }
        if (!this.isChunkInRange(chunkPos)) {
            IC2.log.warn(LogCategory.Block, "Trying to add a Chunk to loaded, however the chunk is too far away. Aborting.");
            return;
        }
        if (this.loadedChunks.add(chunkPos.m_45588_())) {
            ChunkLoaderLogic.updateChunkLoader((ServerLevel)this.f_58857_, this.f_58858_, this.loadedChunks);
            this.m_6596_();
        }
    }

    public void removeChunkFromLoaded(ChunkPos chunkPos) {
        if (this.f_58857_.f_46443_) {
            new RuntimeException("Something tried to change the ChunkLoaderState on the client.").printStackTrace();
            return;
        }
        if (ChunkPos.m_151388_((BlockPos)this.f_58858_) == chunkPos.m_45588_()) {
            return;
        }
        if (this.loadedChunks.remove(chunkPos.m_45588_())) {
            ChunkLoaderLogic.updateChunkLoader((ServerLevel)this.f_58857_, this.f_58858_, this.loadedChunks);
            this.m_6596_();
        }
    }

    public LongSet getLoadedChunks() {
        return this.loadedChunks;
    }

    public boolean isChunkInRange(ChunkPos chunkPos) {
        ChunkPos chunkPos2 = new ChunkPos(this.f_58858_);
        return Math.abs(chunkPos.f_45578_ - chunkPos2.f_45578_) <= 4 && Math.abs(chunkPos.f_45579_ - chunkPos2.f_45579_) <= 4;
    }

    public int getMaxChunks() {
        return 9;
    }

    @Override
    public void onNetworkEvent(Player player, int n) {
        int n2 = (n & 0xF) - 8;
        int n3 = (n >> 4 & 0xF) - 8;
        ChunkPos chunkPos = new ChunkPos(this.f_58858_);
        ChunkPos chunkPos2 = new ChunkPos(chunkPos.f_45578_ + n2, chunkPos.f_45579_ + n3);
        if (this.isChunkInRange(chunkPos2)) {
            if (this.getLoadedChunks().contains(chunkPos2.m_45588_())) {
                this.removeChunkFromLoaded(chunkPos2);
            } else {
                this.addChunkToLoaded(chunkPos2);
            }
        }
    }

    @Override
    protected void onBlockBreak() {
        super.onBlockBreak();
        ChunkLoaderLogic.removeChunkLoader((ServerLevel)this.f_58857_, this.f_58858_);
    }

    @Override
    public double getEnergy() {
        return this.energy.getEnergy();
    }

    @Override
    public boolean useEnergy(double d) {
        return this.energy.useEnergy(d);
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing, UpgradableProperty.Transformer);
    }

    public void setOverclockRates() {
        this.upgradeSlot.onChanged();
        int n = this.upgradeSlot.getTier(1);
        this.energy.setSinkTier(n);
        this.dischargeSlot.setTier(n);
        this.energy.setCapacity(this.upgradeSlot.getEnergyStorage(2500, 0, 0));
    }

    @Override
    public void m_6596_() {
        super.m_6596_();
        if (IC2.sideProxy.isSimulating()) {
            this.setOverclockRates();
        }
    }
}

