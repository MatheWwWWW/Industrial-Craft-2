/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2ObjectFunction
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Rotation
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.chunk.LevelChunk
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 */
package ic2.core.utils.math;

import ic2.core.platform.registries.IC2Properties;
import it.unimi.dsi.fastutil.longs.Long2ObjectFunction;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class StructureBuilder {
    LongSet finalPositions = new LongLinkedOpenHashSet();
    Level world;
    BlockPos defaultPos = BlockPos.f_121853_;
    BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
    Direction facing;
    Long2ObjectMap<LevelChunk> chunks = new Long2ObjectOpenHashMap();
    Long2ObjectFunction<LevelChunk> getter = T -> this.world.m_6325_(ChunkPos.m_45592_((long)T), ChunkPos.m_45602_((long)T));

    public StructureBuilder(Level world, BlockPos start, Direction facing) {
        this.world = world;
        this.pos.m_122190_((Vec3i)start);
        this.defaultPos = BlockPos.m_122022_((long)start.m_121878_());
        this.facing = facing;
    }

    public StructureBuilder(Level world, int x, int y, int z, Direction facing) {
        this.world = world;
        this.pos.m_122178_(x, y, z);
        this.defaultPos = new BlockPos(x, y, z);
        this.facing = facing;
    }

    public long[] getPositions() {
        return this.finalPositions.toLongArray();
    }

    public Direction getFacing() {
        return this.facing;
    }

    private BlockPos mark(BlockPos pos) {
        this.finalPositions.add(pos.m_121878_());
        return pos;
    }

    public StructureBuilder addMark() {
        this.finalPositions.add(this.pos.m_121878_());
        return this;
    }

    public StructureBuilder removeMark() {
        this.finalPositions.remove(this.pos.m_121878_());
        return this;
    }

    public StructureBuilder resetPosition() {
        this.pos.m_122190_((Vec3i)this.defaultPos);
        return this;
    }

    public boolean isDefaultPos() {
        return this.pos.equals((Object)this.defaultPos);
    }

    public StructureBuilder left() {
        return this.offset(this.facing.m_122428_());
    }

    public StructureBuilder left(int steps) {
        return this.offset(this.facing.m_122428_(), steps);
    }

    public StructureBuilder right() {
        return this.offset(this.facing.m_122427_());
    }

    public StructureBuilder right(int steps) {
        return this.offset(this.facing.m_122427_(), steps);
    }

    public StructureBuilder forward() {
        return this.offset(this.facing);
    }

    public StructureBuilder forward(int steps) {
        return this.offset(this.facing, steps);
    }

    public StructureBuilder back() {
        return this.offset(this.facing.m_122424_());
    }

    public StructureBuilder back(int steps) {
        return this.offset(this.facing.m_122424_(), steps);
    }

    public StructureBuilder up() {
        return this.offset(Direction.UP);
    }

    public StructureBuilder up(int steps) {
        return this.offset(Direction.UP, steps);
    }

    public StructureBuilder down() {
        return this.offset(Direction.DOWN);
    }

    public StructureBuilder down(int steps) {
        return this.offset(Direction.DOWN, steps);
    }

    public StructureBuilder move(Rotation rotation) {
        return this.move(rotation, 1);
    }

    public StructureBuilder move(Rotation rotation, int steps) {
        return this.offset(rotation.m_55954_(this.facing), steps);
    }

    public StructureBuilder set(BlockPos pos) {
        this.pos.m_122190_((Vec3i)pos);
        return this;
    }

    public StructureBuilder setOffset(BlockPos pos) {
        this.pos.m_122178_(this.defaultPos.m_123341_() + pos.m_123341_(), this.defaultPos.m_123342_() + pos.m_123342_(), this.defaultPos.m_123343_() + pos.m_123343_());
        return this;
    }

    public StructureBuilder offset(Direction facing) {
        return this.offset(facing, 1);
    }

    public StructureBuilder offset(Direction facing, int steps) {
        if (steps != 0 && facing != null) {
            this.pos.m_122175_(facing, steps);
        }
        return this;
    }

    public boolean isBlock(Block block) {
        return this.getChunk().m_8055_(this.mark((BlockPos)this.pos)).m_60713_(block);
    }

    public boolean isBlock(BlockState state) {
        return this.isBlock(state.m_60734_());
    }

    public boolean isBlock(Predicate<Block> test) {
        return test.test(this.getChunk().m_8055_(this.mark((BlockPos)this.pos)).m_60734_());
    }

    public boolean isBlock(TagKey<Block> test) {
        return this.getChunk().m_8055_(this.mark((BlockPos)this.pos)).m_204336_(test);
    }

    public boolean isState(BlockState state) {
        return this.getChunk().m_8055_(this.mark((BlockPos)this.pos)) == state;
    }

    public boolean isState(Predicate<BlockState> state) {
        return state.test(this.getChunk().m_8055_(this.mark((BlockPos)this.pos)));
    }

    public boolean isAir() {
        return this.getChunk().m_8055_((BlockPos)this.pos).m_60795_();
    }

    public boolean isWaterlogged() {
        BlockState state = this.getChunk().m_8055_((BlockPos)this.pos);
        if (state.m_61138_((Property)BlockStateProperties.f_61362_) && ((Boolean)state.m_61143_((Property)BlockStateProperties.f_61362_)).booleanValue()) {
            return true;
        }
        FluidState fluid = state.m_60819_();
        return fluid.m_76170_() && fluid.m_76152_() == Fluids.f_76193_;
    }

    public boolean isLavaLogged() {
        BlockState state = this.getChunk().m_8055_((BlockPos)this.pos);
        if (state.m_61138_((Property)IC2Properties.LAVA_LOGGED) && ((Boolean)state.m_61143_((Property)IC2Properties.LAVA_LOGGED)).booleanValue()) {
            return true;
        }
        FluidState fluid = state.m_60819_();
        return fluid.m_76170_() && fluid.m_76152_() == Fluids.f_76195_;
    }

    public long getCurrentPos() {
        return this.mark((BlockPos)this.pos).m_121878_();
    }

    public BlockState getState() {
        return this.getChunk().m_8055_(this.mark((BlockPos)this.pos));
    }

    public BlockEntity getTile() {
        return this.getChunk().m_7702_(this.mark((BlockPos)this.pos));
    }

    public <T> T getTile(Class<T> clz) {
        BlockEntity tile = this.getTile();
        return clz.isInstance(tile) ? (T)clz.cast(tile) : null;
    }

    public boolean cubicStructure(int width, int height, int depth, BooleanSupplier run) {
        int total = width * height * depth;
        int yOffset = width * depth;
        for (int i = 1; i < total; ++i) {
            this.resetPosition().right(i % width).forward(i / width % depth).down(i / yOffset);
            if (run.getAsBoolean()) continue;
            return false;
        }
        return true;
    }

    public boolean circleSquareStructure(int width, int depth, BooleanSupplier run) {
        int i;
        for (i = 0; i < width; ++i) {
            if (!run.getAsBoolean()) {
                return false;
            }
            this.right();
        }
        this.left();
        for (i = 0; i < depth; ++i) {
            if (!run.getAsBoolean()) {
                return false;
            }
            this.back();
        }
        this.forward();
        for (i = 0; i < width; ++i) {
            if (!run.getAsBoolean()) {
                return false;
            }
            this.left();
        }
        this.right();
        for (i = 0; i < depth; ++i) {
            if (!run.getAsBoolean()) {
                return false;
            }
            this.forward();
        }
        this.back();
        return true;
    }

    public boolean cubicStructure(int index, int width, int height, int depth) {
        this.resetPosition().right(index % width).forward(index / width % depth).down(index / (width * depth));
        return index < width * height * depth;
    }

    public boolean cubicStructure(int index, int width, int height, int depth, BlockPos.MutableBlockPos pos) {
        int x = index % width;
        int y = index / (width * depth);
        int z = index / width % depth;
        this.resetPosition().right(x).forward(z).down(y);
        pos.m_122178_(x, y, z);
        return index < width * height * depth;
    }

    public int findLength(int maxRadius, boolean reset, Direction dir, Predicate<StructureBuilder> builder) {
        int walk;
        this.resetPosition();
        for (walk = 0; builder.test(this.offset(dir)) && walk < maxRadius; ++walk) {
        }
        this.resetPosition();
        if (reset) {
            this.finalPositions.clear();
        }
        return walk;
    }

    public int findRadius(int maxRadius, boolean reset, Predicate<StructureBuilder> builder) {
        int walk;
        this.resetPosition();
        int result = Integer.MAX_VALUE;
        for (walk = 0; builder.test(this.right()) && walk < maxRadius; ++walk) {
        }
        result = Math.min(walk, result);
        this.resetPosition();
        for (walk = 0; builder.test(this.forward()) && walk < maxRadius; ++walk) {
        }
        result = Math.min(walk, result);
        this.resetPosition();
        for (walk = 0; builder.test(this.down()) && walk < maxRadius; ++walk) {
        }
        result = Math.min(walk, result);
        this.resetPosition();
        if (reset) {
            this.finalPositions.clear();
        }
        return result;
    }

    private LevelChunk getChunk() {
        return (LevelChunk)this.chunks.computeIfAbsent(ChunkPos.m_45589_((int)(this.pos.m_123341_() >> 4), (int)(this.pos.m_123343_() >> 4)), this.getter);
    }
}

