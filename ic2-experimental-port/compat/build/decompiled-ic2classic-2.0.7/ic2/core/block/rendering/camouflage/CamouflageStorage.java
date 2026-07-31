/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.Unpooled
 *  it.unimi.dsi.fastutil.bytes.ByteArrayList
 *  it.unimi.dsi.fastutil.bytes.ByteList
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMaps
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  it.unimi.dsi.fastutil.objects.ObjectCollection
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.rendering.camouflage;

import ic2.api.events.RetextureEvent;
import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.api.util.DirectionList;
import ic2.core.block.rendering.camouflage.ColorQuadCache;
import ic2.core.block.rendering.camouflage.IQuadCache;
import ic2.core.block.rendering.camouflage.TextureQuadCache;
import ic2.core.block.rendering.camouflage.shape.CamouflageShape;
import ic2.core.networking.buffers.InputBuffer;
import ic2.core.networking.buffers.OutputBuffer;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.NBTListWrapper;
import ic2.core.utils.helpers.NBTUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.bytes.ByteArrayList;
import it.unimi.dsi.fastutil.bytes.ByteList;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import java.util.Arrays;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CamouflageStorage
implements INetworkDataBuffer {
    ColorQuadCache[] colorCache = CamouflageStorage.createColor();
    TextureQuadCache[] textureCache = CamouflageStorage.createTexture();
    boolean[] useColor = new boolean[6];
    final CamouflageShape shape;

    public CamouflageStorage(CamouflageShape shape) {
        this.shape = shape;
        Arrays.fill(this.useColor, true);
    }

    public CamouflageShape getShape() {
        return this.shape;
    }

    public void setColor(DirectionList sides, DyeColor color) {
        for (Direction dir : sides) {
            this.useColor[dir.m_122411_()] = true;
            this.colorCache[dir.m_122411_()].setColor(color);
        }
    }

    public CamouflageStorage setTexture(DirectionList sides, Block block) {
        this.setTexture(sides, new RetextureEvent.TextureContainer(block.m_49966_(), Direction.NORTH, new RetextureEvent.Rotation[]{RetextureEvent.Rotation.ROTATION_0}, new int[]{-1}));
        return this;
    }

    public void setTexture(DirectionList sides, RetextureEvent.TextureContainer container) {
        for (Direction dir : sides) {
            this.useColor[dir.m_122411_()] = false;
            this.textureCache[dir.m_122411_()].setData(container);
        }
    }

    public boolean canApply(DirectionList list, RetextureEvent.TextureContainer container) {
        for (Direction dir : list) {
            if (!this.useColor[dir.m_122411_()] && !this.textureCache[dir.m_122411_()].canSetData(container)) continue;
            return true;
        }
        return false;
    }

    public boolean canApply(Direction dir, RetextureEvent.TextureContainer container) {
        return this.useColor[dir.m_122411_()] || this.textureCache[dir.m_122411_()].canSetData(container);
    }

    public boolean canApply(DirectionList dirs, DyeColor color) {
        if (color == null) {
            return false;
        }
        for (Direction dir : dirs) {
            if (this.useColor[dir.m_122411_()] && this.colorCache[dir.m_122411_()].getColor() == color) continue;
            return true;
        }
        return false;
    }

    public DyeColor isSingleColor() {
        DyeColor color = null;
        for (Direction dir : DirectionList.ALL) {
            if (!this.useColor[dir.m_122411_()]) {
                return null;
            }
            if (color == null) {
                color = this.colorCache[dir.m_122411_()].getColor();
                continue;
            }
            if (this.colorCache[dir.m_122411_()].getColor() == color) continue;
            return null;
        }
        return color;
    }

    public IQuadCache getCache(Direction side) {
        return this.useColor[side.m_122411_()] ? this.colorCache[side.m_122411_()] : this.textureCache[side.m_122411_()];
    }

    public boolean isColored(Direction dir) {
        return this.useColor[dir.m_122411_()];
    }

    @Override
    public void write(IOutputBuffer buffer) {
        ObjectCollection slots = this.map().values();
        buffer.writeByte((byte)slots.size());
        for (ByteList list : slots) {
            byte index;
            if (list.size() == 1) {
                index = list.getByte(0);
                buffer.writeBoolean(true);
                buffer.writeByte((byte)(index << 1 | (this.useColor[index] ? 1 : 0)));
                (this.useColor[index] ? this.colorCache[index] : this.textureCache[index]).write(buffer);
                continue;
            }
            buffer.writeBoolean(false);
            buffer.writeBytes(list.toByteArray());
            index = list.getByte(0);
            buffer.writeBoolean(this.useColor[index]);
            ByteBuf out = Unpooled.buffer();
            (this.useColor[index] ? this.colorCache[index] : this.textureCache[index]).write(new OutputBuffer(out));
            byte[] data = new byte[out.writerIndex()];
            out.readBytes(data);
            buffer.writeBytes(data);
        }
    }

    @Override
    public void read(IInputBuffer buffer) {
        int size = buffer.readByte();
        for (int i = 0; i < size; ++i) {
            if (buffer.readBoolean()) {
                byte data = buffer.readByte();
                int index = data >> 1;
                this.useColor[index] = (data & 1) != 0;
                (this.useColor[index] ? this.colorCache[index] : this.textureCache[index]).read(buffer);
                continue;
            }
            byte[] slots = buffer.readBytes();
            boolean color = buffer.readBoolean();
            IQuadCache[] cache = color ? this.colorCache : this.textureCache;
            byte[] data = buffer.readBytes();
            for (byte index : slots) {
                this.useColor[index] = color;
                cache[index].read(new InputBuffer(data));
            }
        }
    }

    private Object2ObjectMap<CompoundTag, ByteList> map() {
        Object2ObjectSortedMap mapped = CollectionUtils.createLinkedMap();
        for (int i = 0; i < 6; ++i) {
            CompoundTag data = new CompoundTag();
            (this.useColor[i] ? this.colorCache[i] : this.textureCache[i]).write(data);
            NBTUtils.putBoolean(data, "type", this.useColor[i], false);
            ByteList list = (ByteList)mapped.get((Object)data);
            if (list == null) {
                list = new ByteArrayList();
                mapped.put((Object)data, (Object)list);
            }
            list.add((byte)i);
        }
        return mapped;
    }

    public CompoundTag save(CompoundTag nbt) {
        ListTag list = new ListTag();
        for (Object2ObjectMap.Entry entry : Object2ObjectMaps.fastIterable(this.map())) {
            CompoundTag data = (CompoundTag)entry.getKey();
            ByteList slots = (ByteList)entry.getValue();
            if (slots.size() > 1) {
                data.m_128382_("slots", slots.toByteArray());
            } else {
                if (slots.size() != 1) continue;
                data.m_128344_("slot", slots.getByte(0));
            }
            list.add((Object)data);
        }
        NBTUtils.put(nbt, "cam", list);
        return nbt;
    }

    public void load(CompoundTag nbt) {
        for (CompoundTag data : NBTListWrapper.wrap(nbt.m_128437_("cam", 10), CompoundTag.class)) {
            if (data.m_128441_("slot")) {
                boolean color;
                int index = data.m_128451_("slot");
                this.useColor[index] = color = data.m_128471_("type");
                (color ? this.colorCache[index] : this.textureCache[index]).read(data);
                continue;
            }
            boolean color = data.m_128471_("type");
            byte[] slots = data.m_128463_("slots");
            IQuadCache[] cache = color ? this.colorCache : this.textureCache;
            for (byte index : slots) {
                this.useColor[index] = color;
                cache[index].read(data);
            }
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    public CamouflageShape.QuadResults getQuads(BlockState state, Level world, BlockPos pos, RenderType type) {
        return this.shape.generateQuads(this, state, world, pos, type);
    }

    static ColorQuadCache[] createColor() {
        ColorQuadCache[] array = new ColorQuadCache[6];
        for (Direction dir : DirectionList.ALL) {
            array[dir.m_122411_()] = new ColorQuadCache(dir);
        }
        return array;
    }

    static TextureQuadCache[] createTexture() {
        TextureQuadCache[] array = new TextureQuadCache[6];
        for (Direction dir : DirectionList.ALL) {
            array[dir.m_122411_()] = new TextureQuadCache(dir);
        }
        return array;
    }
}

