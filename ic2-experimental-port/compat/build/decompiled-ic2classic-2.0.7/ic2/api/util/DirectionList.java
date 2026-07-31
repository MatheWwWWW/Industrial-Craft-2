/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectArrays
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.util.Mth
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.Rotation
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.common.capabilities.Capability
 *  net.minecraftforge.common.util.LazyOptional
 */
package ic2.api.util;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public final class DirectionList
implements Iterable<Direction>,
Predicate<Direction> {
    static final Random RAND = new Random();
    static final DirectionList[] DIRECTIONS = DirectionList.generateArray();
    public static final DirectionList DOWN = DirectionList.ofFacing(Direction.DOWN);
    public static final DirectionList UP = DirectionList.ofFacing(Direction.UP);
    public static final DirectionList NORTH = DirectionList.ofFacing(Direction.NORTH);
    public static final DirectionList SOUTH = DirectionList.ofFacing(Direction.SOUTH);
    public static final DirectionList EAST = DirectionList.ofFacing(Direction.EAST);
    public static final DirectionList WEST = DirectionList.ofFacing(Direction.WEST);
    public static final DirectionList VERTICAL = DOWN.add(UP);
    public static final DirectionList HORIZONTAL = DirectionList.ofFacings(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST);
    public static final DirectionList POSITIVE = DirectionList.ofFacings(Direction.UP, Direction.SOUTH, Direction.EAST);
    public static final DirectionList NEGATIVE = DirectionList.ofFacings(Direction.DOWN, Direction.NORTH, Direction.WEST);
    public static final DirectionList X_AXIS = EAST.add(WEST);
    public static final DirectionList Z_AXIS = NORTH.add(SOUTH);
    public static final DirectionList XY_AXIS = X_AXIS.add(VERTICAL);
    public static final DirectionList YZ_AXIS = Z_AXIS.add(VERTICAL);
    public static final DirectionList P_CORNER = SOUTH.add(EAST);
    public static final DirectionList N_CORNER = NORTH.add(WEST);
    public static final DirectionList ALL = DirectionList.ofNumber(63);
    public static final DirectionList EMPTY = DirectionList.ofNumber(0);
    final int code;
    final int size;
    BlockPos offset;
    Direction[] array;
    Map<Direction, Direction> forwardHelper;
    Map<Direction, Direction> backwardHelper;

    private DirectionList() {
        throw new RuntimeException("NOT ALLOWED!");
    }

    private DirectionList(int index) {
        this.code = (byte)Mth.m_14045_((int)index, (int)0, (int)63);
        BlockPos.MutableBlockPos helper = new BlockPos.MutableBlockPos();
        ObjectArrayList list = new ObjectArrayList();
        for (int i = 0; i < 6; ++i) {
            if ((this.code & 1 << i) == 0) continue;
            Direction dir = Direction.m_122376_((int)i);
            helper.m_122173_(dir);
            list.add((Object)dir);
        }
        this.offset = helper.m_7949_();
        this.size = list.size();
        this.array = (Direction[])list.toArray((Object[])new Direction[this.size]);
        if (this.size == 0) {
            this.forwardHelper = ImmutableMap.of();
            return;
        }
        Object2ObjectOpenHashMap forward = new Object2ObjectOpenHashMap();
        Object2ObjectOpenHashMap backward = new Object2ObjectOpenHashMap();
        for (int i = 0; i < this.array.length; ++i) {
            forward.put(this.array[i], this.array[(i + 1) % this.array.length]);
            backward.put(this.array[(i + 1) % this.array.length], this.array[i]);
        }
        this.forwardHelper = ImmutableMap.copyOf((Map)forward);
        this.backwardHelper = ImmutableMap.copyOf((Map)backward);
    }

    public static DirectionList ofFacing(Direction facing) {
        return DIRECTIONS[1 << facing.m_122411_()];
    }

    public static DirectionList ofFacings(Direction ... facings) {
        return DIRECTIONS[DirectionList.toNumber(facings)];
    }

    public static DirectionList ofFacings(Collection<Direction> facings) {
        return DIRECTIONS[DirectionList.toNumber(facings)];
    }

    public static DirectionList ofAxis(Direction.Axis axis) {
        int value = 0;
        for (Direction dir : ALL) {
            if (!axis.test(dir)) continue;
            value |= 1 << dir.m_122411_();
        }
        return DIRECTIONS[value];
    }

    public static DirectionList ofFlags(boolean ... flags) {
        return DIRECTIONS[DirectionList.toNumber(flags)];
    }

    public static DirectionList ofNumber(int index) {
        return DIRECTIONS[Mth.m_14045_((int)index, (int)0, (int)63)];
    }

    public static MutableComponent getName(Direction dir) {
        return Component.m_237115_((String)("misc.ic2.side." + dir.m_122433_()));
    }

    public DirectionList rotate(int amount) {
        int value = 0;
        for (int i = 0; i < 6; ++i) {
            if ((this.code & 1 << i) == 0) continue;
            value |= 1 << Direction.m_122376_((int)(i + amount)).m_122411_();
        }
        return DIRECTIONS[value & 0x3F];
    }

    public DirectionList invert() {
        return DIRECTIONS[63 - this.code];
    }

    public DirectionList opposite() {
        int value = 0;
        for (int i = 0; i < 6; ++i) {
            if ((this.code & 1 << i) == 0) continue;
            value |= 1 << Direction.m_122376_((int)i).m_122424_().m_122411_();
        }
        return DIRECTIONS[value & 0x3F];
    }

    public DirectionList add(Direction facing) {
        return DIRECTIONS[this.code | 1 << facing.m_122411_()];
    }

    public DirectionList add(DirectionList facings) {
        return DIRECTIONS[this.code | facings.code];
    }

    public DirectionList remove(Direction facing) {
        return DIRECTIONS[this.code & ~(1 << facing.m_122411_())];
    }

    public DirectionList remove(DirectionList facings) {
        return DIRECTIONS[this.code & ~facings.code];
    }

    public DirectionList set(Direction facing, boolean value) {
        return DIRECTIONS[value ? this.code | 1 << facing.m_122411_() : this.code & ~(1 << facing.m_122411_())];
    }

    public DirectionList keep(DirectionList facings) {
        return DIRECTIONS[this.code & facings.code];
    }

    public DirectionList flip(Direction dir) {
        return DIRECTIONS[this.code ^ 1 << dir.m_122411_()];
    }

    public boolean contains(Direction direction) {
        return (this.code & 1 << direction.m_122411_()) != 0;
    }

    public boolean contains(DirectionList facings) {
        return (this.code & facings.code) == facings.code;
    }

    public boolean containsAny(DirectionList facings) {
        return (this.code & facings.code) != 0;
    }

    public boolean notContains(Direction direction) {
        return (this.code & 1 << direction.m_122411_()) == 0;
    }

    public boolean containsNot(DirectionList facings) {
        return (this.code & facings.code) == 0;
    }

    public DirectionList invertFacing(Direction facing) {
        return this.contains(facing) ? this.remove(facing).add(facing.m_122424_()) : this;
    }

    public DirectionList replace(Direction oldFacing, Direction newFacing) {
        return this.contains(oldFacing) ? this.remove(oldFacing).add(newFacing) : this;
    }

    public Direction getNextFacing(Direction facing) {
        if (this.contains(facing)) {
            return this.forwardHelper.get(facing);
        }
        return this.code == 0 ? facing : this.array[0];
    }

    public Direction getPrevFacing(Direction facing) {
        if (this.contains(facing)) {
            return this.backwardHelper.get(facing);
        }
        return this.code == 0 ? facing : this.array[this.array.length - 1];
    }

    public BlockPos getOffset() {
        return this.offset;
    }

    public int getCode() {
        return this.code;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.code == 0;
    }

    public boolean isFull() {
        return this.code == 63;
    }

    public String toString() {
        return ObjectArrayList.wrap((Object[])this.array).toString();
    }

    public Set<Direction> toFacings() {
        return this.code == 0 ? EnumSet.noneOf(Direction.class) : EnumSet.copyOf(ObjectArrayList.wrap((Object[])this.array));
    }

    public Direction getRandomFacing() {
        return this.getRandomFacing(Direction.NORTH);
    }

    public Direction getRandomFacing(Direction defaultValue) {
        return this.code == 0 ? defaultValue : this.array[RAND.nextInt(this.size)];
    }

    public Direction getDefaultFacing() {
        return this.code == 0 ? Direction.NORTH : this.array[0];
    }

    public boolean[] toFlags() {
        boolean[] flags = new boolean[6];
        for (int i = 0; i < 6; ++i) {
            flags[i] = (this.code & 1 << i) != 0;
        }
        return flags;
    }

    public static BlockState getNeighborState(BlockEntity tile, Direction dir) {
        return DirectionList.getNeighborState(tile.m_58904_(), tile.m_58899_(), dir);
    }

    public static BlockState getNeighborState(Level world, BlockPos pos, Direction dir) {
        return world.m_46749_(pos = pos.m_121945_(dir)) ? world.m_8055_(pos) : Blocks.f_50016_.m_49966_();
    }

    public static BlockEntity getNeighborTile(BlockEntity tile, Direction dir) {
        return DirectionList.getNeighborTile(tile.m_58904_(), tile.m_58899_(), dir);
    }

    public static BlockEntity getNeighborTile(Level world, BlockPos pos, Direction dir) {
        return world.m_46749_(pos = pos.m_121945_(dir)) ? world.m_7702_(pos) : null;
    }

    public static <T> LazyOptional<T> getNeighborCapability(BlockEntity tile, Direction dir, Capability<T> capability) {
        BlockEntity neighbor = DirectionList.getNeighborTile(tile, dir);
        return neighbor == null ? LazyOptional.empty() : neighbor.getCapability(capability, dir.m_122424_());
    }

    public static <T> LazyOptional<T> getNeighborCapability(Level world, BlockPos pos, Direction dir, Capability<T> capability) {
        BlockEntity neighbor = DirectionList.getNeighborTile(world, pos, dir);
        return neighbor == null ? LazyOptional.empty() : neighbor.getCapability(capability, dir.m_122424_());
    }

    public static <T> T getNeighborInterface(BlockEntity tile, Direction dir, Class<T> clazz) {
        BlockEntity neighbor = DirectionList.getNeighborTile(tile, dir);
        return clazz.isInstance(neighbor) ? (T)clazz.cast(neighbor) : null;
    }

    public static <T> T getNeighborInterface(Level world, BlockPos pos, Direction dir, Class<T> clazz) {
        BlockEntity neighbor = DirectionList.getNeighborTile(world, pos, dir);
        return clazz.isInstance(neighbor) ? (T)clazz.cast(neighbor) : null;
    }

    public static Rotation getRotation(Direction source, Direction other) {
        int index = source.m_122416_();
        int otherIndex = other.m_122416_();
        if (index == -1 || otherIndex == -1) {
            throw new IllegalStateException("Horizontal Only!");
        }
        if (source == other) {
            return Rotation.NONE;
        }
        if (source == other.m_122424_()) {
            return Rotation.CLOCKWISE_180;
        }
        if (source == other.m_122427_()) {
            return Rotation.COUNTERCLOCKWISE_90;
        }
        return Rotation.CLOCKWISE_90;
    }

    public static Direction rotateAround(Direction dir, Direction.Axis axis) {
        switch (axis) {
            case X: {
                if (dir != Direction.WEST && dir != Direction.EAST) {
                    return DirectionList.rotateX(dir);
                }
                return dir;
            }
            case Y: {
                if (dir != Direction.UP && dir != Direction.DOWN) {
                    return DirectionList.rotateY(dir);
                }
                return dir;
            }
            case Z: {
                if (dir != Direction.NORTH && dir != Direction.SOUTH) {
                    return DirectionList.rotateZ(dir);
                }
                return dir;
            }
        }
        throw new IllegalStateException("Unable to get CW facing for axis " + axis);
    }

    public static Direction rotateY(Direction dir) {
        switch (dir) {
            case NORTH: {
                return Direction.EAST;
            }
            case EAST: {
                return Direction.SOUTH;
            }
            case SOUTH: {
                return Direction.WEST;
            }
            case WEST: {
                return Direction.NORTH;
            }
        }
        throw new IllegalStateException("Unable to get Y-rotated facing of " + dir);
    }

    private static Direction rotateX(Direction dir) {
        switch (dir) {
            case NORTH: {
                return Direction.DOWN;
            }
            default: {
                throw new IllegalStateException("Unable to get X-rotated facing of " + dir);
            }
            case SOUTH: {
                return Direction.UP;
            }
            case UP: {
                return Direction.NORTH;
            }
            case DOWN: 
        }
        return Direction.SOUTH;
    }

    private static Direction rotateZ(Direction dir) {
        switch (dir) {
            case EAST: {
                return Direction.DOWN;
            }
            default: {
                throw new IllegalStateException("Unable to get Z-rotated facing of " + dir);
            }
            case WEST: {
                return Direction.UP;
            }
            case UP: {
                return Direction.EAST;
            }
            case DOWN: 
        }
        return Direction.WEST;
    }

    public static int toNumber(Direction ... facings) {
        int value = 0;
        for (Direction facing : facings) {
            value |= 1 << facing.m_122411_();
        }
        return value & 0x3F;
    }

    public static int toNumber(Collection<Direction> facings) {
        int value = 0;
        for (Direction facing : facings) {
            value |= 1 << facing.m_122411_();
        }
        return value & 0x3F;
    }

    public static int toNumber(boolean ... facings) {
        return (facings[0] ? 1 : 0) | (facings[1] ? 1 : 0) << 1 | (facings[2] ? 1 : 0) << 2 | (facings[3] ? 1 : 0) << 3 | (facings[4] ? 1 : 0) << 4 | (facings[5] ? 1 : 0) << 5;
    }

    public static boolean[] toFlags(Direction ... facings) {
        boolean[] array = new boolean[6];
        for (Direction face : facings) {
            array[face.m_122411_()] = true;
        }
        return array;
    }

    @Override
    public boolean test(Direction t) {
        return this.contains(t);
    }

    @Override
    public Iterator<Direction> iterator() {
        return new Iterator<Direction>(){
            int index = 0;

            @Override
            public boolean hasNext() {
                return this.index < DirectionList.this.size;
            }

            @Override
            public Direction next() {
                return DirectionList.this.array[this.index++];
            }
        };
    }

    public Iterable<Direction> getRandomIterator() {
        return () -> new Iterator<Direction>(){
            Direction[] data;
            int index;
            {
                this.data = (Direction[])ObjectArrays.shuffle((Object[])((Direction[])ObjectArrays.copy((Object[])DirectionList.this.array)), (Random)RAND);
                this.index = 0;
            }

            @Override
            public boolean hasNext() {
                return this.index < this.data.length;
            }

            @Override
            public Direction next() {
                return this.data[this.index++];
            }
        };
    }

    static DirectionList[] generateArray() {
        DirectionList[] rotations = new DirectionList[64];
        for (int i = 0; i < 64; ++i) {
            rotations[i] = new DirectionList(i);
        }
        return rotations;
    }
}

