/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class MultifaceBlock
extends Block {
    private static final float f_153807_ = 1.0f;
    private static final VoxelShape f_153808_ = Block.m_49796_(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape f_153809_ = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
    private static final VoxelShape f_153810_ = Block.m_49796_(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
    private static final VoxelShape f_153811_ = Block.m_49796_(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape f_153812_ = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
    private static final VoxelShape f_153813_ = Block.m_49796_(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
    private static final Map<Direction, BooleanProperty> f_153814_ = PipeBlock.f_55154_;
    private static final Map<Direction, VoxelShape> f_153815_ = Util.m_137469_(Maps.newEnumMap(Direction.class), p_153923_ -> {
        p_153923_.put(Direction.NORTH, f_153812_);
        p_153923_.put(Direction.EAST, f_153811_);
        p_153923_.put(Direction.SOUTH, f_153813_);
        p_153923_.put(Direction.WEST, f_153810_);
        p_153923_.put(Direction.UP, f_153808_);
        p_153923_.put(Direction.DOWN, f_153809_);
    });
    protected static final Direction[] f_153806_ = Direction.values();
    private final ImmutableMap<BlockState, VoxelShape> f_153816_;
    private final boolean f_153817_;
    private final boolean f_153818_;
    private final boolean f_153819_;

    public MultifaceBlock(BlockBehaviour.Properties p_153822_) {
        super(p_153822_);
        this.m_49959_(MultifaceBlock.m_153918_(this.f_49792_));
        this.f_153816_ = this.m_152458_(MultifaceBlock::m_153958_);
        this.f_153817_ = Direction.Plane.HORIZONTAL.m_122557_().allMatch(this::m_153920_);
        this.f_153818_ = Direction.Plane.HORIZONTAL.m_122557_().filter(Direction.Axis.X).filter(this::m_153920_).count() % 2L == 0L;
        this.f_153819_ = Direction.Plane.HORIZONTAL.m_122557_().filter(Direction.Axis.Z).filter(this::m_153920_).count() % 2L == 0L;
    }

    public static Set<Direction> m_221584_(BlockState p_221585_) {
        if (!(p_221585_.m_60734_() instanceof MultifaceBlock)) {
            return Set.of();
        }
        EnumSet<Direction> $$1 = EnumSet.noneOf(Direction.class);
        for (Direction $$2 : Direction.values()) {
            if (!MultifaceBlock.m_153900_(p_221585_, $$2)) continue;
            $$1.add($$2);
        }
        return $$1;
    }

    public static Set<Direction> m_221569_(byte p_221570_) {
        EnumSet<Direction> $$1 = EnumSet.noneOf(Direction.class);
        for (Direction $$2 : Direction.values()) {
            if ((p_221570_ & (byte)(1 << $$2.ordinal())) <= 0) continue;
            $$1.add($$2);
        }
        return $$1;
    }

    public static byte m_221576_(Collection<Direction> p_221577_) {
        byte $$1 = 0;
        for (Direction $$2 : p_221577_) {
            $$1 = (byte)($$1 | 1 << $$2.ordinal());
        }
        return $$1;
    }

    protected boolean m_153920_(Direction p_153921_) {
        return true;
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_153917_) {
        for (Direction $$1 : f_153806_) {
            if (!this.m_153920_($$1)) continue;
            p_153917_.m_61104_(MultifaceBlock.m_153933_($$1));
        }
    }

    @Override
    public BlockState m_7417_(BlockState p_153904_, Direction p_153905_, BlockState p_153906_, LevelAccessor p_153907_, BlockPos p_153908_, BlockPos p_153909_) {
        if (!MultifaceBlock.m_153960_(p_153904_)) {
            return Blocks.f_50016_.m_49966_();
        }
        if (!MultifaceBlock.m_153900_(p_153904_, p_153905_) || MultifaceBlock.m_153829_(p_153907_, p_153905_, p_153909_, p_153906_)) {
            return p_153904_;
        }
        return MultifaceBlock.m_153897_(p_153904_, MultifaceBlock.m_153933_(p_153905_));
    }

    @Override
    public VoxelShape m_5940_(BlockState p_153851_, BlockGetter p_153852_, BlockPos p_153853_, CollisionContext p_153854_) {
        return (VoxelShape)this.f_153816_.get((Object)p_153851_);
    }

    @Override
    public boolean m_7898_(BlockState p_153888_, LevelReader p_153889_, BlockPos p_153890_) {
        boolean $$3 = false;
        for (Direction $$4 : f_153806_) {
            if (!MultifaceBlock.m_153900_(p_153888_, $$4)) continue;
            BlockPos $$5 = p_153890_.m_121945_($$4);
            if (!MultifaceBlock.m_153829_(p_153889_, $$4, $$5, p_153889_.m_8055_($$5))) {
                return false;
            }
            $$3 = true;
        }
        return $$3;
    }

    @Override
    public boolean m_6864_(BlockState p_153848_, BlockPlaceContext p_153849_) {
        return MultifaceBlock.m_153962_(p_153848_);
    }

    @Override
    @Nullable
    public BlockState m_5573_(BlockPlaceContext p_153824_) {
        Level $$1 = p_153824_.m_43725_();
        BlockPos $$2 = p_153824_.m_8083_();
        BlockState $$3 = $$1.m_8055_($$2);
        return Arrays.stream(p_153824_.m_6232_()).map(p_153865_ -> this.m_153940_($$3, $$1, $$2, (Direction)p_153865_)).filter(Objects::nonNull).findFirst().orElse(null);
    }

    public boolean m_221571_(BlockGetter p_221572_, BlockState p_221573_, BlockPos p_221574_, Direction p_221575_) {
        if (!this.m_153920_(p_221575_) || p_221573_.m_60713_(this) && MultifaceBlock.m_153900_(p_221573_, p_221575_)) {
            return false;
        }
        BlockPos $$4 = p_221574_.m_121945_(p_221575_);
        return MultifaceBlock.m_153829_(p_221572_, p_221575_, $$4, p_221572_.m_8055_($$4));
    }

    @Nullable
    public BlockState m_153940_(BlockState p_153941_, BlockGetter p_153942_, BlockPos p_153943_, Direction p_153944_) {
        BlockState $$6;
        if (!this.m_221571_(p_153942_, p_153941_, p_153943_, p_153944_)) {
            return null;
        }
        if (p_153941_.m_60713_(this)) {
            BlockState $$4 = p_153941_;
        } else if (this.m_153964_() && p_153941_.m_60819_().m_164512_(Fluids.f_76193_)) {
            BlockState $$5 = (BlockState)this.m_49966_().m_61124_(BlockStateProperties.f_61362_, true);
        } else {
            $$6 = this.m_49966_();
        }
        return (BlockState)$$6.m_61124_(MultifaceBlock.m_153933_(p_153944_), true);
    }

    @Override
    public BlockState m_6843_(BlockState p_153895_, Rotation p_153896_) {
        if (!this.f_153817_) {
            return p_153895_;
        }
        return this.m_153910_(p_153895_, p_153896_::m_55954_);
    }

    @Override
    public BlockState m_6943_(BlockState p_153892_, Mirror p_153893_) {
        if (p_153893_ == Mirror.FRONT_BACK && !this.f_153818_) {
            return p_153892_;
        }
        if (p_153893_ == Mirror.LEFT_RIGHT && !this.f_153819_) {
            return p_153892_;
        }
        return this.m_153910_(p_153892_, p_153893_::m_54848_);
    }

    private BlockState m_153910_(BlockState p_153911_, Function<Direction, Direction> p_153912_) {
        BlockState $$2 = p_153911_;
        for (Direction $$3 : f_153806_) {
            if (!this.m_153920_($$3)) continue;
            $$2 = (BlockState)$$2.m_61124_(MultifaceBlock.m_153933_(p_153912_.apply($$3)), p_153911_.m_61143_(MultifaceBlock.m_153933_($$3)));
        }
        return $$2;
    }

    public static boolean m_153900_(BlockState p_153901_, Direction p_153902_) {
        BooleanProperty $$2 = MultifaceBlock.m_153933_(p_153902_);
        return p_153901_.m_61138_($$2) && p_153901_.m_61143_($$2) != false;
    }

    public static boolean m_153829_(BlockGetter p_153830_, Direction p_153831_, BlockPos p_153832_, BlockState p_153833_) {
        return Block.m_49918_(p_153833_.m_60816_(p_153830_, p_153832_), p_153831_.m_122424_()) || Block.m_49918_(p_153833_.m_60812_(p_153830_, p_153832_), p_153831_.m_122424_());
    }

    private boolean m_153964_() {
        return this.f_49792_.m_61092_().contains(BlockStateProperties.f_61362_);
    }

    private static BlockState m_153897_(BlockState p_153898_, BooleanProperty p_153899_) {
        BlockState $$2 = (BlockState)p_153898_.m_61124_(p_153899_, false);
        if (MultifaceBlock.m_153960_($$2)) {
            return $$2;
        }
        return Blocks.f_50016_.m_49966_();
    }

    public static BooleanProperty m_153933_(Direction p_153934_) {
        return f_153814_.get(p_153934_);
    }

    private static BlockState m_153918_(StateDefinition<Block, BlockState> p_153919_) {
        BlockState $$1 = p_153919_.m_61090_();
        for (BooleanProperty $$2 : f_153814_.values()) {
            if (!$$1.m_61138_($$2)) continue;
            $$1 = (BlockState)$$1.m_61124_($$2, false);
        }
        return $$1;
    }

    private static VoxelShape m_153958_(BlockState p_153959_) {
        VoxelShape $$1 = Shapes.m_83040_();
        for (Direction $$2 : f_153806_) {
            if (!MultifaceBlock.m_153900_(p_153959_, $$2)) continue;
            $$1 = Shapes.m_83110_($$1, f_153815_.get($$2));
        }
        return $$1.m_83281_() ? Shapes.m_83144_() : $$1;
    }

    protected static boolean m_153960_(BlockState p_153961_) {
        return Arrays.stream(f_153806_).anyMatch(p_221583_ -> MultifaceBlock.m_153900_(p_153961_, p_221583_));
    }

    private static boolean m_153962_(BlockState p_153963_) {
        return Arrays.stream(f_153806_).anyMatch(p_221580_ -> !MultifaceBlock.m_153900_(p_153963_, p_221580_));
    }

    public abstract MultifaceSpreader m_213612_();
}

