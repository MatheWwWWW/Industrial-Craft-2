/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.LongIterator
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.core.Vec3i
 *  net.minecraft.nbt.ByteArrayTag
 *  net.minecraft.nbt.IntArrayTag
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.phys.AABB
 */
package ic2.core.utils.math.geometry;

import ic2.api.util.DirectionList;
import ic2.core.utils.collection.IterableWrapper;
import it.unimi.dsi.fastutil.longs.LongIterator;
import java.util.Iterator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;

public class Box
implements Iterable<BlockPos> {
    int minX;
    int minY;
    int minZ;
    int maxX;
    int maxY;
    int maxZ;

    public Box() {
        this(0, 0, 0, 0, 0, 0);
    }

    public Box(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        this.minX = Math.min(minX, maxX);
        this.minY = Math.min(minY, maxY);
        this.minZ = Math.min(minZ, maxZ);
        this.maxX = Math.max(minX, maxX);
        this.maxY = Math.max(minY, maxY);
        this.maxZ = Math.max(minZ, maxZ);
    }

    public static Box fromNBT(IntArrayTag nbt) {
        return Box.fromArray(nbt.m_128648_());
    }

    public static Box relative(byte[] array) {
        if (array.length != 6) {
            throw new IllegalStateException("Array has to be size of 6");
        }
        return new Box(array[0], array[1], array[2], array[3], array[4], array[5]);
    }

    public static Box fromArray(int[] array) {
        if (array.length != 6) {
            throw new IllegalStateException("Array has to be size of 6");
        }
        return new Box(array[0], array[1], array[2], array[3], array[4], array[5]);
    }

    public static Box fromPos(BlockPos pos) {
        return Box.fromPos(pos, pos.m_7918_(1, 1, 1));
    }

    public static Box fromPos(BlockPos pos, boolean center) {
        return Box.fromPos(pos, center ? pos : pos.m_7918_(1, 1, 1));
    }

    public static Box fromPos(BlockPos from, BlockPos to) {
        return new Box(from.m_123341_(), from.m_123342_(), from.m_123343_(), to.m_123341_(), to.m_123342_(), to.m_123343_());
    }

    public static Box withRange(BlockPos pos, int range) {
        return Box.fromPos(pos.m_7918_(-range, -range, -range), pos.m_7918_(range, range, range));
    }

    public static Box fromAABB(AABB box) {
        return new Box((int)Math.floor(box.f_82288_), (int)Math.floor(box.f_82289_), (int)Math.floor(box.f_82290_), (int)Math.floor(box.f_82291_), (int)Math.floor(box.f_82292_), (int)Math.floor(box.f_82293_));
    }

    public static Box fromPosArray(long[] position) {
        int xMin = Integer.MAX_VALUE;
        int yMin = Integer.MAX_VALUE;
        int zMin = Integer.MAX_VALUE;
        int xMax = Integer.MIN_VALUE;
        int yMax = Integer.MIN_VALUE;
        int zMax = Integer.MIN_VALUE;
        int m = position.length;
        for (int i = 0; i < m; ++i) {
            int x = BlockPos.m_121983_((long)position[i]);
            int y = BlockPos.m_122008_((long)position[i]);
            int z = BlockPos.m_122015_((long)position[i]);
            xMin = Math.min(x, xMin);
            yMin = Math.min(y, yMin);
            zMin = Math.min(z, zMin);
            xMax = Math.max(x, xMax);
            yMax = Math.max(y, yMax);
            zMax = Math.max(z, zMax);
        }
        return new Box(xMin, yMin, zMin, xMax, yMax, zMax);
    }

    public static Box fromPosArray(LongIterator iter) {
        int xMin = Integer.MAX_VALUE;
        int yMin = Integer.MAX_VALUE;
        int zMin = Integer.MAX_VALUE;
        int xMax = Integer.MIN_VALUE;
        int yMax = Integer.MIN_VALUE;
        int zMax = Integer.MIN_VALUE;
        while (iter.hasNext()) {
            long pos = iter.nextLong();
            int x = BlockPos.m_121983_((long)pos);
            int y = BlockPos.m_122008_((long)pos);
            int z = BlockPos.m_122015_((long)pos);
            xMin = Math.min(x, xMin);
            yMin = Math.min(y, yMin);
            zMin = Math.min(z, zMin);
            xMax = Math.max(x, xMax);
            yMax = Math.max(y, yMax);
            zMax = Math.max(z, zMax);
        }
        return new Box(xMin, yMin, zMin, xMax, yMax, zMax);
    }

    public static Box fromList(Iterable<BlockPos> set) {
        int xMin = Integer.MAX_VALUE;
        int yMin = Integer.MAX_VALUE;
        int zMin = Integer.MAX_VALUE;
        int xMax = Integer.MIN_VALUE;
        int yMax = Integer.MIN_VALUE;
        int zMax = Integer.MIN_VALUE;
        for (BlockPos pos : set) {
            xMin = Math.min(pos.m_123341_(), xMin);
            yMin = Math.min(pos.m_123342_(), yMin);
            zMin = Math.min(pos.m_123343_(), zMin);
            xMax = Math.max(pos.m_123341_(), xMax);
            yMax = Math.max(pos.m_123342_(), yMax);
            zMax = Math.max(pos.m_123343_(), zMax);
        }
        return new Box(xMin, yMin, zMin, xMax, yMax, zMax);
    }

    public Box copy() {
        return new Box(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
    }

    public AABB toAxis() {
        return new AABB((double)this.minX, (double)this.minY, (double)this.minZ, (double)this.maxX, (double)this.maxY, (double)this.maxZ);
    }

    public Box toInclusiveBox() {
        return new Box(this.minX, this.minY, this.minZ, this.maxX + 1, this.maxY + 1, this.maxZ + 1);
    }

    public AABB toInclusiveAxis() {
        return new AABB((double)this.minX, (double)this.minY, (double)this.minZ, (double)(this.maxX + 1), (double)(this.maxY + 1), (double)(this.maxZ + 1));
    }

    public Box expandSide(Direction.Axis axis, int amount) {
        return this.expand(DirectionList.ofAxis(axis).invert(), amount);
    }

    public Box expand(Direction.Axis axis, int amount) {
        return this.expand(DirectionList.ofAxis(axis), amount);
    }

    public Box expand(Direction dir, int amount) {
        return this.expand(DirectionList.ofFacing(dir), amount);
    }

    public Box expand(DirectionList dir, int amount) {
        this.minX -= dir.contains(Direction.WEST) ? amount : 0;
        this.minY -= dir.contains(Direction.DOWN) ? amount : 0;
        this.minZ -= dir.contains(Direction.NORTH) ? amount : 0;
        this.maxX += dir.contains(Direction.EAST) ? amount : 0;
        this.maxY += dir.contains(Direction.UP) ? amount : 0;
        this.maxZ += dir.contains(Direction.SOUTH) ? amount : 0;
        return this;
    }

    public Box setMaxValue(BlockPos pos) {
        this.maxX = Math.min(pos.m_123341_(), this.maxX);
        this.maxY = Math.min(pos.m_123342_(), this.maxY);
        this.maxZ = Math.min(pos.m_123343_(), this.maxZ);
        return this;
    }

    public Box setMinValue(BlockPos pos) {
        this.minX = Math.max(pos.m_123341_(), this.minX);
        this.minY = Math.max(pos.m_123342_(), this.minY);
        this.minZ = Math.max(pos.m_123343_(), this.minZ);
        return this;
    }

    public Box setValue(DirectionList dir, int value) {
        this.minX = dir.contains(Direction.WEST) ? value : this.minX;
        this.minY = dir.contains(Direction.DOWN) ? value : this.minY;
        this.minZ = dir.contains(Direction.NORTH) ? value : this.minZ;
        this.maxX = dir.contains(Direction.EAST) ? value : this.maxX;
        this.maxY = dir.contains(Direction.UP) ? value : this.maxY;
        int n = this.maxZ = dir.contains(Direction.SOUTH) ? value : this.maxZ;
        if (this.maxX < this.minX) {
            value = this.minX;
            this.minX = this.maxX;
            this.minX = value;
        }
        if (this.maxY < this.minY) {
            value = this.minY;
            this.minY = this.maxY;
            this.minY = value;
        }
        if (this.maxZ < this.minZ) {
            value = this.minZ;
            this.minZ = this.maxZ;
            this.minZ = value;
        }
        return this;
    }

    public Box offset(Vec3i pos) {
        return this.offset(pos.m_123341_(), pos.m_123342_(), pos.m_123343_());
    }

    public Box offset(Direction dir) {
        return this.offset(dir.m_122436_());
    }

    public Box offset(int x, int y, int z) {
        this.minX += x;
        this.minY += y;
        this.minZ += z;
        this.maxX += x;
        this.maxY += y;
        this.maxZ += z;
        return this;
    }

    public int size() {
        int x = Math.abs(this.maxX - this.minX) + 1;
        int y = Math.abs(this.maxY - this.minY) + 1;
        int z = Math.abs(this.maxZ - this.minZ) + 1;
        return x * y * z;
    }

    public String toString() {
        return "BoundingBox[minX=" + this.minX + ", minY=" + this.minY + ", minZ=" + this.minZ + ", maxX=" + this.maxX + ", maxY=" + this.maxY + ", maxZ=" + this.maxZ + "]";
    }

    public int get(Direction side) {
        switch (side) {
            case DOWN: {
                return this.minY;
            }
            case UP: {
                return this.maxY;
            }
            case EAST: {
                return this.minX;
            }
            case WEST: {
                return this.maxX;
            }
            case NORTH: {
                return this.minZ;
            }
            case SOUTH: {
                return this.maxZ;
            }
        }
        return 0;
    }

    public int get(Direction.Axis axis) {
        switch (axis) {
            case X: {
                return this.getWidth();
            }
            case Y: {
                return this.getHeight();
            }
            case Z: {
                return this.getDepth();
            }
        }
        return 0;
    }

    public int getMin(Direction.Axis axis) {
        switch (axis) {
            case X: {
                return this.minX;
            }
            case Y: {
                return this.minY;
            }
            case Z: {
                return this.minZ;
            }
        }
        return 0;
    }

    public int getMax(Direction.Axis axis) {
        switch (axis) {
            case X: {
                return this.maxX;
            }
            case Y: {
                return this.maxY;
            }
            case Z: {
                return this.maxZ;
            }
        }
        return 0;
    }

    public int getMinX() {
        return this.minX;
    }

    public int getMinY() {
        return this.minY;
    }

    public int getMinZ() {
        return this.minZ;
    }

    public BlockPos getMin() {
        return new BlockPos(this.minX, this.minY, this.minZ);
    }

    public int getMaxX() {
        return this.maxX;
    }

    public int getMaxY() {
        return this.maxY;
    }

    public int getMaxZ() {
        return this.maxZ;
    }

    public BlockPos getMax() {
        return new BlockPos(this.maxX, this.maxY, this.maxZ);
    }

    public BlockPos get(int x, int y, int z) {
        return new BlockPos(this.minX + x, this.minY + y, this.minZ + z);
    }

    public BlockPos getCenter() {
        return new BlockPos(this.minX + (this.maxX - this.minX) / 2, this.minY + (this.maxY - this.minY) / 2, this.minZ + (this.maxZ - this.minZ) / 2);
    }

    public int getWidth() {
        return Math.abs(this.maxX - this.minX);
    }

    public int getHeight() {
        return Math.abs(this.maxY - this.minY);
    }

    public int getDepth() {
        return Math.abs(this.maxZ - this.minZ);
    }

    public boolean isCube(int size) {
        return this.getWidth() > size && this.getHeight() > size && this.getDepth() > size;
    }

    public int hollowSize() {
        this.expand(DirectionList.ALL, -1);
        int smallerSize = this.size();
        this.expand(DirectionList.ALL, 1);
        return this.size() - smallerSize;
    }

    public boolean intersectsWith(BlockPos pos) {
        return pos.m_123341_() >= this.minX && pos.m_123341_() <= this.maxX && pos.m_123342_() >= this.minY && pos.m_123342_() <= this.maxY && pos.m_123343_() >= this.minZ && pos.m_123343_() <= this.maxZ;
    }

    public boolean intersectsWith(BlockEntity tile) {
        return this.intersectsWith(tile.m_58899_());
    }

    public boolean intersectsWith(Entity entity) {
        return this.intersectsWith(entity.m_20183_());
    }

    public boolean intersectsWith(Box other) {
        return (other.minX >= this.minX && other.minX <= this.maxX || other.maxX >= this.minX && other.maxX <= this.maxX) && (other.minY >= this.minY && other.minY <= this.maxY || other.maxY >= this.minY && other.maxY <= this.maxY) && (other.minZ >= this.minZ && other.minZ <= this.maxZ || other.maxZ >= this.minZ && other.maxZ <= this.maxZ);
    }

    public boolean isInSingleChunk() {
        return this.minX >> 4 == this.maxX >> 4 && this.minZ >> 4 == this.maxZ >> 4;
    }

    public boolean isLoaded(Level world) {
        int xMax = this.maxX >> 4;
        for (int xMin = this.minX >> 4; xMin <= xMax; ++xMin) {
            int zMax = this.maxZ >> 4;
            for (int zMin = this.minZ >> 4; zMin <= zMax; ++zMin) {
                if (world.m_7232_(xMin, zMin)) continue;
                return false;
            }
        }
        return true;
    }

    public Direction getBorderSide(BlockPos pos) {
        if (this.isEdge(pos)) {
            return null;
        }
        if (pos.m_123341_() == this.minX) {
            return Direction.WEST;
        }
        if (pos.m_123341_() == this.maxX) {
            return Direction.EAST;
        }
        if (pos.m_123342_() == this.minY) {
            return Direction.DOWN;
        }
        if (pos.m_123342_() == this.maxY) {
            return Direction.UP;
        }
        if (pos.m_123341_() == this.minZ) {
            return Direction.NORTH;
        }
        if (pos.m_123341_() == this.maxZ) {
            return Direction.SOUTH;
        }
        return null;
    }

    public boolean isBorder(BlockPos pos) {
        return pos.m_123341_() == this.minX || pos.m_123341_() == this.maxX || pos.m_123342_() == this.minY || pos.m_123342_() == this.maxY || pos.m_123343_() == this.minZ || pos.m_123343_() == this.maxZ;
    }

    public boolean isEdge(BlockPos pos) {
        if (pos.m_123341_() == this.minX || pos.m_123341_() == this.maxX) {
            return pos.m_123342_() == this.minY || pos.m_123342_() == this.maxY || pos.m_123343_() == this.minZ || pos.m_123343_() == this.maxZ;
        }
        if (pos.m_123342_() == this.minY || pos.m_123342_() == this.maxY) {
            return pos.m_123341_() == this.minX || pos.m_123341_() == this.maxX || pos.m_123343_() == this.minZ || pos.m_123343_() == this.maxZ;
        }
        if (pos.m_123343_() == this.minZ || pos.m_123343_() == this.maxZ) {
            return pos.m_123341_() == this.minX || pos.m_123341_() == this.maxX || pos.m_123342_() == this.minY || pos.m_123342_() == this.maxY;
        }
        return false;
    }

    public boolean isCorner(BlockPos pos) {
        return !(pos.m_123341_() != this.minX && pos.m_123341_() != this.maxX || pos.m_123342_() != this.minY && pos.m_123342_() != this.maxY || pos.m_123343_() != this.minZ && pos.m_123343_() != this.maxZ);
    }

    public IntArrayTag save() {
        return new IntArrayTag(new int[]{this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ});
    }

    public ByteArrayTag saveRelative() {
        return new ByteArrayTag(new byte[]{(byte)this.minX, (byte)this.minY, (byte)this.minZ, (byte)this.maxX, (byte)this.maxY, (byte)this.maxZ});
    }

    public long[] asLongArray() {
        long[] data = new long[this.size()];
        int i = 0;
        for (int y = this.minY; y <= this.maxY; ++y) {
            for (int z = this.minZ; z <= this.maxZ; ++z) {
                for (int x = this.minX; x <= this.maxX; ++x) {
                    data[i++] = BlockPos.m_121882_((int)x, (int)y, (int)z);
                }
            }
        }
        return data;
    }

    public long[] asLongArray(BlockPos offset) {
        this.offset((Vec3i)offset);
        long[] data = new long[this.size() - 1];
        int i = 0;
        for (int y = this.minY; y <= this.maxY; ++y) {
            for (int z = this.minZ; z <= this.maxZ; ++z) {
                for (int x = this.minX; x <= this.maxX; ++x) {
                    if (x == offset.m_123341_() && y == offset.m_123342_() && z == offset.m_123343_()) continue;
                    data[i++] = BlockPos.m_121882_((int)x, (int)y, (int)z);
                }
            }
        }
        this.offset(-offset.m_123341_(), -offset.m_123342_(), -offset.m_123343_());
        return data;
    }

    public long[] asHollowArray() {
        long[] data = new long[this.hollowSize()];
        int i = 0;
        int add = this.maxX - this.minX - 1;
        for (int y = this.minY; y <= this.maxY; ++y) {
            for (int z = this.minZ; z <= this.maxZ; ++z) {
                for (int x = this.minX; x <= this.maxX; ++x) {
                    data[i++] = BlockPos.m_121882_((int)x, (int)y, (int)z);
                    if (z == this.minZ || z == this.maxZ || y == this.minY || y == this.maxY || x != this.minX) continue;
                    x += add;
                }
            }
        }
        return data;
    }

    public long[] asHollowArray(BlockPos offset) {
        this.offset((Vec3i)offset);
        long[] data = new long[this.hollowSize() - 1];
        int i = 0;
        for (int z = this.minZ; z <= this.maxZ; ++z) {
            for (int y = this.minY; y <= this.maxY; ++y) {
                for (int x = this.minX; x <= this.maxX; ++x) {
                    if (x == offset.m_123341_() && y == offset.m_123342_() && z == offset.m_123343_() || x != this.minX && x != this.maxX && y != this.minY && y != this.maxY && z != this.minZ && z != this.maxZ) continue;
                    data[i++] = BlockPos.m_121882_((int)x, (int)y, (int)z);
                }
            }
        }
        this.offset(-offset.m_123341_(), -offset.m_123342_(), -offset.m_123343_());
        return data;
    }

    @Override
    public Iterator<BlockPos> iterator() {
        return new Iterator<BlockPos>(){
            int x;
            int y;
            int z;
            boolean hasNext;
            BlockPos.MutableBlockPos pos;
            {
                this.x = Box.this.minX;
                this.y = Box.this.minY;
                this.z = Box.this.minZ;
                this.hasNext = true;
                this.pos = new BlockPos.MutableBlockPos(this.x, this.y, this.z);
            }

            @Override
            public boolean hasNext() {
                return this.hasNext;
            }

            @Override
            public BlockPos next() {
                this.pos.m_122178_(this.x, this.y, this.z);
                if (++this.x > Box.this.maxX) {
                    this.x = Box.this.minX;
                    if (++this.z > Box.this.maxZ) {
                        this.z = Box.this.minZ;
                        if (++this.y > Box.this.maxY) {
                            this.y = Box.this.minY;
                            this.hasNext = false;
                        }
                    }
                }
                return this.pos;
            }
        };
    }

    public Iterator<BlockPos> asInfiniteIterator() {
        return new Iterator<BlockPos>(){
            int x;
            int y;
            int z;
            BlockPos.MutableBlockPos pos;
            {
                this.x = Box.this.minX;
                this.y = Box.this.minY;
                this.z = Box.this.minZ;
                this.pos = new BlockPos.MutableBlockPos(this.x, this.y, this.z);
            }

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public BlockPos next() {
                this.pos.m_122178_(this.x, this.y, this.z);
                if (++this.x > Box.this.maxX) {
                    this.x = Box.this.minX;
                    if (++this.z > Box.this.maxZ) {
                        this.z = Box.this.minZ;
                        if (++this.y > Box.this.maxY) {
                            this.y = Box.this.minY;
                        }
                    }
                }
                return this.pos;
            }
        };
    }

    public Iterable<BlockPos> getHollowHorizontalIterator() {
        return IterableWrapper.wrap(new Iterator<BlockPos>(){
            boolean hasNext = true;
            int add;
            int x;
            int y;
            int z;
            BlockPos.MutableBlockPos pos;
            {
                this.add = Box.this.maxX - Box.this.minX - 1;
                this.x = Box.this.minX;
                this.y = Box.this.minY;
                this.z = Box.this.minZ;
                this.pos = new BlockPos.MutableBlockPos();
            }

            @Override
            public boolean hasNext() {
                return this.hasNext;
            }

            @Override
            public BlockPos next() {
                this.pos.m_122178_(this.x, this.y, this.z);
                if (this.z != Box.this.minZ && this.z != Box.this.maxZ && this.x == Box.this.minX) {
                    this.x += this.add;
                }
                if (++this.x > Box.this.maxX) {
                    this.x = Box.this.minX;
                    if (++this.z > Box.this.maxZ) {
                        this.z = Box.this.minZ;
                        this.hasNext = false;
                    }
                }
                return this.pos;
            }
        });
    }

    public Iterable<BlockPos> getHollowIterator() {
        return IterableWrapper.wrap(new Iterator<BlockPos>(){
            boolean hasNext = true;
            int add;
            int x;
            int y;
            int z;
            BlockPos.MutableBlockPos pos;
            {
                this.add = Box.this.maxX - Box.this.minX - 1;
                this.x = Box.this.minX;
                this.y = Box.this.minY;
                this.z = Box.this.minZ;
                this.pos = new BlockPos.MutableBlockPos();
            }

            @Override
            public boolean hasNext() {
                return this.hasNext;
            }

            @Override
            public BlockPos next() {
                this.pos.m_122178_(this.x, this.y, this.z);
                if (this.z != Box.this.minZ && this.z != Box.this.maxZ && this.y != Box.this.minY && this.y != Box.this.maxY && this.x == Box.this.minX) {
                    this.x += this.add;
                }
                if (++this.x > Box.this.maxX) {
                    this.x = Box.this.minX;
                    if (++this.z > Box.this.maxZ) {
                        this.z = Box.this.minZ;
                        if (++this.y > Box.this.maxY) {
                            this.y = Box.this.minY;
                            this.hasNext = false;
                        }
                    }
                }
                return this.pos;
            }
        });
    }
}

