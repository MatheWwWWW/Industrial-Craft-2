/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.LongArrayList
 *  it.unimi.dsi.fastutil.longs.LongCollection
 *  it.unimi.dsi.fastutil.longs.LongIterator
 *  it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.ShortTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.base.misc;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.core.block.base.tiles.BaseLinkingTileEntity;
import ic2.core.block.base.tiles.BaseMultiBlockTileEntity;
import ic2.core.inventory.base.INBTSavable;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.NBTListWrapper;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LinkStorage
implements INBTSavable,
INetworkDataBuffer {
    final BaseMultiBlockTileEntity master;
    final String name;
    LongSet positions = new LongLinkedOpenHashSet();
    List<BaseLinkingTileEntity> tiles = CollectionUtils.createList();

    public LinkStorage(String name, BaseMultiBlockTileEntity tile) {
        this.name = name;
        this.master = tile;
    }

    public void add(BaseLinkingTileEntity tile) {
        if (tile == null) {
            return;
        }
        if (this.positions.add(tile.m_58899_().m_121878_())) {
            this.tiles.add(tile);
        }
    }

    public boolean contains(BlockPos pos) {
        return this.positions.contains(pos.m_121878_());
    }

    public void refreshReferences() {
        if (this.tiles.isEmpty() && !this.positions.isEmpty()) {
            BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
            LongIterator longIterator = this.positions.iterator();
            while (longIterator.hasNext()) {
                long entry = (Long)longIterator.next();
                BlockEntity tile = this.master.m_58904_().m_7702_((BlockPos)pos.m_122188_(entry));
                if (!(tile instanceof BaseLinkingTileEntity)) continue;
                this.tiles.add((BaseLinkingTileEntity)tile);
            }
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    public List<BaseLinkingTileEntity> getClientTiles() {
        ObjectList base = CollectionUtils.createList();
        LongIterator longIterator = this.positions.iterator();
        while (longIterator.hasNext()) {
            long pos = (Long)longIterator.next();
            BlockEntity tile = this.master.m_58904_().m_7702_(BlockPos.m_122022_((long)pos));
            if (!(tile instanceof BaseLinkingTileEntity)) continue;
            base.add((BaseLinkingTileEntity)((BaseLinkingTileEntity)tile));
        }
        return base;
    }

    public List<BaseLinkingTileEntity> getTiles() {
        return this.tiles;
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        if (!this.positions.isEmpty()) {
            LongArrayList list = LongArrayList.wrap((long[])this.master.structure.structurePositions);
            if (list.size() > Short.MAX_VALUE) {
                nbt.m_128388_(this.name, this.positions.toLongArray());
            } else if (list.size() > 255) {
                ListTag type = new ListTag();
                LongIterator iter = this.positions.iterator();
                while (iter.hasNext()) {
                    type.add((Object)ShortTag.m_129258_((short)((short)list.indexOf(iter.nextLong()))));
                }
                nbt.m_128365_(this.name, (Tag)type);
            } else {
                byte[] data = new byte[this.positions.size()];
                int index = 0;
                LongIterator iter = this.positions.iterator();
                while (iter.hasNext()) {
                    data[index] = (byte)(list.indexOf(iter.nextLong()) - 128);
                    ++index;
                }
                nbt.m_128382_(this.name, data);
            }
        }
        return nbt;
    }

    @Override
    public void load(CompoundTag nbt) {
        long[] data = this.master.structure.structurePositions;
        if (data == null) {
            return;
        }
        if (data.length > Short.MAX_VALUE) {
            this.positions.addAll((LongCollection)LongArrayList.wrap((long[])nbt.m_128467_(this.name)));
        } else if (data.length > 255) {
            for (ShortTag value : NBTListWrapper.wrap(nbt.m_128437_(this.name, 2), ShortTag.class)) {
                this.positions.add(data[value.m_7047_()]);
            }
        } else {
            byte[] array = nbt.m_128463_(this.name);
            int m = array.length;
            for (int i = 0; i < m; ++i) {
                this.positions.add(data[array[i] + 128]);
            }
        }
    }

    public void finish() {
        this.refreshReferences();
        int m = this.tiles.size();
        for (int i = 0; i < m; ++i) {
            this.tiles.get(i).setMaster(this.master);
        }
    }

    public void setActive(boolean active) {
        int m = this.tiles.size();
        for (int i = 0; i < m; ++i) {
            this.tiles.get(i).setActive(active);
        }
    }

    public void clear(boolean removeMaster) {
        if (removeMaster) {
            this.refreshReferences();
            for (BaseLinkingTileEntity tile : new ObjectArrayList(this.tiles)) {
                tile.clearMaster(this.master.m_58899_());
            }
        }
        this.positions.clear();
        this.tiles.clear();
    }

    @Override
    public void write(IOutputBuffer buffer) {
        if (this.positions.isEmpty() || this.master.structure.structurePositions == null) {
            buffer.writeBoolean(false);
            return;
        }
        buffer.writeBoolean(true);
        LongArrayList list = LongArrayList.wrap((long[])this.master.structure.structurePositions);
        buffer.writeVarInt(this.positions.size());
        if (list.size() > Short.MAX_VALUE) {
            buffer.writeByte((byte)2);
            LongIterator iter = this.positions.iterator();
            while (iter.hasNext()) {
                buffer.writeLong(iter.nextLong());
            }
        } else if (list.size() > 255) {
            buffer.writeByte((byte)1);
            LongIterator iter = this.positions.iterator();
            while (iter.hasNext()) {
                buffer.writeShort((short)list.indexOf(iter.nextLong()));
            }
        } else {
            buffer.writeByte((byte)0);
            LongIterator iter = this.positions.iterator();
            while (iter.hasNext()) {
                buffer.writeByte((byte)(list.indexOf(iter.nextLong()) - 128));
            }
        }
    }

    @Override
    public void read(IInputBuffer buffer) {
        this.positions.clear();
        if (buffer.readBoolean()) {
            long[] data = this.master.structure.structurePositions;
            if (data == null) {
                return;
            }
            int size = buffer.readVarInt();
            byte type = buffer.readByte();
            block5: for (int i = 0; i < size; ++i) {
                switch (type) {
                    case 0: {
                        this.positions.add(data[buffer.readByte() + 128]);
                        continue block5;
                    }
                    case 1: {
                        this.positions.add(data[buffer.readShort()]);
                        continue block5;
                    }
                    case 2: {
                        this.positions.add(buffer.readLong());
                    }
                }
            }
        }
    }
}

