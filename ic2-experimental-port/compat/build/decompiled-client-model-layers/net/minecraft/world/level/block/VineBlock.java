/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VineBlock
extends Block {
    public static final BooleanProperty f_57833_ = PipeBlock.f_55152_;
    public static final BooleanProperty f_57834_ = PipeBlock.f_55148_;
    public static final BooleanProperty f_57835_ = PipeBlock.f_55149_;
    public static final BooleanProperty f_57836_ = PipeBlock.f_55150_;
    public static final BooleanProperty f_57837_ = PipeBlock.f_55151_;
    public static final Map<Direction, BooleanProperty> f_57838_ = PipeBlock.f_55154_.entrySet().stream().filter(p_57886_ -> p_57886_.getKey() != Direction.DOWN).collect(Util.m_137448_());
    protected static final float f_154875_ = 1.0f;
    private static final VoxelShape f_57839_ = Block.m_49796_(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape f_57840_ = Block.m_49796_(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
    private static final VoxelShape f_57841_ = Block.m_49796_(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape f_57842_ = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
    private static final VoxelShape f_57843_ = Block.m_49796_(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
    private final Map<BlockState, VoxelShape> f_57844_;

    public VineBlock(BlockBehaviour.Properties p_57847_) {
        super(p_57847_);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_57833_, false)).m_61124_(f_57834_, false)).m_61124_(f_57835_, false)).m_61124_(f_57836_, false)).m_61124_(f_57837_, false));
        this.f_57844_ = ImmutableMap.copyOf(this.f_49792_.m_61056_().stream().collect(Collectors.toMap(Function.identity(), VineBlock::m_57905_)));
    }

    private static VoxelShape m_57905_(BlockState p_57906_) {
        VoxelShape $$1 = Shapes.m_83040_();
        if (p_57906_.m_61143_(f_57833_).booleanValue()) {
            $$1 = f_57839_;
        }
        if (p_57906_.m_61143_(f_57834_).booleanValue()) {
            $$1 = Shapes.m_83110_($$1, f_57842_);
        }
        if (p_57906_.m_61143_(f_57836_).booleanValue()) {
            $$1 = Shapes.m_83110_($$1, f_57843_);
        }
        if (p_57906_.m_61143_(f_57835_).booleanValue()) {
            $$1 = Shapes.m_83110_($$1, f_57841_);
        }
        if (p_57906_.m_61143_(f_57837_).booleanValue()) {
            $$1 = Shapes.m_83110_($$1, f_57840_);
        }
        return $$1.m_83281_() ? Shapes.m_83144_() : $$1;
    }

    @Override
    public VoxelShape m_5940_(BlockState p_57897_, BlockGetter p_57898_, BlockPos p_57899_, CollisionContext p_57900_) {
        return this.f_57844_.get(p_57897_);
    }

    @Override
    public boolean m_7420_(BlockState p_181239_, BlockGetter p_181240_, BlockPos p_181241_) {
        return true;
    }

    @Override
    public boolean m_7898_(BlockState p_57861_, LevelReader p_57862_, BlockPos p_57863_) {
        return this.m_57907_(this.m_57901_(p_57861_, p_57862_, p_57863_));
    }

    private boolean m_57907_(BlockState p_57908_) {
        return this.m_57909_(p_57908_) > 0;
    }

    private int m_57909_(BlockState p_57910_) {
        int $$1 = 0;
        for (BooleanProperty $$2 : f_57838_.values()) {
            if (!p_57910_.m_61143_($$2).booleanValue()) continue;
            ++$$1;
        }
        return $$1;
    }

    private boolean m_57887_(BlockGetter p_57888_, BlockPos p_57889_, Direction p_57890_) {
        if (p_57890_ == Direction.DOWN) {
            return false;
        }
        BlockPos $$3 = p_57889_.m_121945_(p_57890_);
        if (VineBlock.m_57853_(p_57888_, $$3, p_57890_)) {
            return true;
        }
        if (p_57890_.m_122434_() != Direction.Axis.Y) {
            BooleanProperty $$4 = f_57838_.get(p_57890_);
            BlockState $$5 = p_57888_.m_8055_(p_57889_.m_7494_());
            return $$5.m_60713_(this) && $$5.m_61143_($$4) != false;
        }
        return false;
    }

    public static boolean m_57853_(BlockGetter p_57854_, BlockPos p_57855_, Direction p_57856_) {
        return MultifaceBlock.m_153829_(p_57854_, p_57856_, p_57855_, p_57854_.m_8055_(p_57855_));
    }

    private BlockState m_57901_(BlockState p_57902_, BlockGetter p_57903_, BlockPos p_57904_) {
        BlockPos $$3 = p_57904_.m_7494_();
        if (p_57902_.m_61143_(f_57833_).booleanValue()) {
            p_57902_ = (BlockState)p_57902_.m_61124_(f_57833_, VineBlock.m_57853_(p_57903_, $$3, Direction.DOWN));
        }
        BlockBehaviour.BlockStateBase $$4 = null;
        for (Direction $$5 : Direction.Plane.HORIZONTAL) {
            BooleanProperty $$6 = VineBlock.m_57883_($$5);
            if (!p_57902_.m_61143_($$6).booleanValue()) continue;
            boolean $$7 = this.m_57887_(p_57903_, p_57904_, $$5);
            if (!$$7) {
                if ($$4 == null) {
                    $$4 = p_57903_.m_8055_($$3);
                }
                $$7 = $$4.m_60713_(this) && $$4.m_61143_($$6) != false;
            }
            p_57902_ = (BlockState)p_57902_.m_61124_($$6, $$7);
        }
        return p_57902_;
    }

    @Override
    public BlockState m_7417_(BlockState p_57875_, Direction p_57876_, BlockState p_57877_, LevelAccessor p_57878_, BlockPos p_57879_, BlockPos p_57880_) {
        if (p_57876_ == Direction.DOWN) {
            return super.m_7417_(p_57875_, p_57876_, p_57877_, p_57878_, p_57879_, p_57880_);
        }
        BlockState $$6 = this.m_57901_(p_57875_, p_57878_, p_57879_);
        if (!this.m_57907_($$6)) {
            return Blocks.f_50016_.m_49966_();
        }
        return $$6;
    }

    @Override
    public void m_213898_(BlockState p_222655_, ServerLevel p_222656_, BlockPos p_222657_, RandomSource p_222658_) {
        BlockState $$20;
        BlockState $$19;
        BlockPos $$17;
        BlockState $$18;
        if (p_222658_.m_188503_(4) != 0) {
            return;
        }
        Direction $$4 = Direction.m_235672_(p_222658_);
        BlockPos $$5 = p_222657_.m_7494_();
        if ($$4.m_122434_().m_122479_() && !p_222655_.m_61143_(VineBlock.m_57883_($$4)).booleanValue()) {
            if (!this.m_57850_(p_222656_, p_222657_)) {
                return;
            }
            BlockPos $$6 = p_222657_.m_121945_($$4);
            BlockState $$7 = p_222656_.m_8055_($$6);
            if ($$7.m_60795_()) {
                Direction $$8 = $$4.m_122427_();
                Direction $$9 = $$4.m_122428_();
                boolean $$10 = p_222655_.m_61143_(VineBlock.m_57883_($$8));
                boolean $$11 = p_222655_.m_61143_(VineBlock.m_57883_($$9));
                BlockPos $$12 = $$6.m_121945_($$8);
                BlockPos $$13 = $$6.m_121945_($$9);
                if ($$10 && VineBlock.m_57853_(p_222656_, $$12, $$8)) {
                    p_222656_.m_7731_($$6, (BlockState)this.m_49966_().m_61124_(VineBlock.m_57883_($$8), true), 2);
                } else if ($$11 && VineBlock.m_57853_(p_222656_, $$13, $$9)) {
                    p_222656_.m_7731_($$6, (BlockState)this.m_49966_().m_61124_(VineBlock.m_57883_($$9), true), 2);
                } else {
                    Direction $$14 = $$4.m_122424_();
                    if ($$10 && p_222656_.m_46859_($$12) && VineBlock.m_57853_(p_222656_, p_222657_.m_121945_($$8), $$14)) {
                        p_222656_.m_7731_($$12, (BlockState)this.m_49966_().m_61124_(VineBlock.m_57883_($$14), true), 2);
                    } else if ($$11 && p_222656_.m_46859_($$13) && VineBlock.m_57853_(p_222656_, p_222657_.m_121945_($$9), $$14)) {
                        p_222656_.m_7731_($$13, (BlockState)this.m_49966_().m_61124_(VineBlock.m_57883_($$14), true), 2);
                    } else if ((double)p_222658_.m_188501_() < 0.05 && VineBlock.m_57853_(p_222656_, $$6.m_7494_(), Direction.UP)) {
                        p_222656_.m_7731_($$6, (BlockState)this.m_49966_().m_61124_(f_57833_, true), 2);
                    }
                }
            } else if (VineBlock.m_57853_(p_222656_, $$6, $$4)) {
                p_222656_.m_7731_(p_222657_, (BlockState)p_222655_.m_61124_(VineBlock.m_57883_($$4), true), 2);
            }
            return;
        }
        if ($$4 == Direction.UP && p_222657_.m_123342_() < p_222656_.m_151558_() - 1) {
            if (this.m_57887_(p_222656_, p_222657_, $$4)) {
                p_222656_.m_7731_(p_222657_, (BlockState)p_222655_.m_61124_(f_57833_, true), 2);
                return;
            }
            if (p_222656_.m_46859_($$5)) {
                if (!this.m_57850_(p_222656_, p_222657_)) {
                    return;
                }
                BlockState $$15 = p_222655_;
                for (Direction $$16 : Direction.Plane.HORIZONTAL) {
                    if (!p_222658_.m_188499_() && VineBlock.m_57853_(p_222656_, $$5.m_121945_($$16), $$16)) continue;
                    $$15 = (BlockState)$$15.m_61124_(VineBlock.m_57883_($$16), false);
                }
                if (this.m_57911_($$15)) {
                    p_222656_.m_7731_($$5, $$15, 2);
                }
                return;
            }
        }
        if (p_222657_.m_123342_() > p_222656_.m_141937_() && (($$18 = p_222656_.m_8055_($$17 = p_222657_.m_7495_())).m_60795_() || $$18.m_60713_(this)) && ($$19 = $$18.m_60795_() ? this.m_49966_() : $$18) != ($$20 = this.m_222650_(p_222655_, $$19, p_222658_)) && this.m_57911_($$20)) {
            p_222656_.m_7731_($$17, $$20, 2);
        }
    }

    private BlockState m_222650_(BlockState p_222651_, BlockState p_222652_, RandomSource p_222653_) {
        for (Direction $$3 : Direction.Plane.HORIZONTAL) {
            BooleanProperty $$4;
            if (!p_222653_.m_188499_() || !p_222651_.m_61143_($$4 = VineBlock.m_57883_($$3)).booleanValue()) continue;
            p_222652_ = (BlockState)p_222652_.m_61124_($$4, true);
        }
        return p_222652_;
    }

    private boolean m_57911_(BlockState p_57912_) {
        return p_57912_.m_61143_(f_57834_) != false || p_57912_.m_61143_(f_57835_) != false || p_57912_.m_61143_(f_57836_) != false || p_57912_.m_61143_(f_57837_) != false;
    }

    private boolean m_57850_(BlockGetter p_57851_, BlockPos p_57852_) {
        int $$2 = 4;
        Iterable<BlockPos> $$3 = BlockPos.m_121976_(p_57852_.m_123341_() - 4, p_57852_.m_123342_() - 1, p_57852_.m_123343_() - 4, p_57852_.m_123341_() + 4, p_57852_.m_123342_() + 1, p_57852_.m_123343_() + 4);
        int $$4 = 5;
        for (BlockPos $$5 : $$3) {
            if (!p_57851_.m_8055_($$5).m_60713_(this) || --$$4 > 0) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean m_6864_(BlockState p_57858_, BlockPlaceContext p_57859_) {
        BlockState $$2 = p_57859_.m_43725_().m_8055_(p_57859_.m_8083_());
        if ($$2.m_60713_(this)) {
            return this.m_57909_($$2) < f_57838_.size();
        }
        return super.m_6864_(p_57858_, p_57859_);
    }

    @Override
    @Nullable
    public BlockState m_5573_(BlockPlaceContext p_57849_) {
        BlockState $$1 = p_57849_.m_43725_().m_8055_(p_57849_.m_8083_());
        boolean $$2 = $$1.m_60713_(this);
        BlockState $$3 = $$2 ? $$1 : this.m_49966_();
        for (Direction $$4 : p_57849_.m_6232_()) {
            boolean $$6;
            if ($$4 == Direction.DOWN) continue;
            BooleanProperty $$5 = VineBlock.m_57883_($$4);
            boolean bl = $$6 = $$2 && $$1.m_61143_($$5) != false;
            if ($$6 || !this.m_57887_(p_57849_.m_43725_(), p_57849_.m_8083_(), $$4)) continue;
            return (BlockState)$$3.m_61124_($$5, true);
        }
        return $$2 ? $$3 : null;
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_57882_) {
        p_57882_.m_61104_(f_57833_, f_57834_, f_57835_, f_57836_, f_57837_);
    }

    @Override
    public BlockState m_6843_(BlockState p_57868_, Rotation p_57869_) {
        switch (p_57869_) {
            case CLOCKWISE_180: {
                return (BlockState)((BlockState)((BlockState)((BlockState)p_57868_.m_61124_(f_57834_, p_57868_.m_61143_(f_57836_))).m_61124_(f_57835_, p_57868_.m_61143_(f_57837_))).m_61124_(f_57836_, p_57868_.m_61143_(f_57834_))).m_61124_(f_57837_, p_57868_.m_61143_(f_57835_));
            }
            case COUNTERCLOCKWISE_90: {
                return (BlockState)((BlockState)((BlockState)((BlockState)p_57868_.m_61124_(f_57834_, p_57868_.m_61143_(f_57835_))).m_61124_(f_57835_, p_57868_.m_61143_(f_57836_))).m_61124_(f_57836_, p_57868_.m_61143_(f_57837_))).m_61124_(f_57837_, p_57868_.m_61143_(f_57834_));
            }
            case CLOCKWISE_90: {
                return (BlockState)((BlockState)((BlockState)((BlockState)p_57868_.m_61124_(f_57834_, p_57868_.m_61143_(f_57837_))).m_61124_(f_57835_, p_57868_.m_61143_(f_57834_))).m_61124_(f_57836_, p_57868_.m_61143_(f_57835_))).m_61124_(f_57837_, p_57868_.m_61143_(f_57836_));
            }
        }
        return p_57868_;
    }

    @Override
    public BlockState m_6943_(BlockState p_57865_, Mirror p_57866_) {
        switch (p_57866_) {
            case LEFT_RIGHT: {
                return (BlockState)((BlockState)p_57865_.m_61124_(f_57834_, p_57865_.m_61143_(f_57836_))).m_61124_(f_57836_, p_57865_.m_61143_(f_57834_));
            }
            case FRONT_BACK: {
                return (BlockState)((BlockState)p_57865_.m_61124_(f_57835_, p_57865_.m_61143_(f_57837_))).m_61124_(f_57837_, p_57865_.m_61143_(f_57835_));
            }
        }
        return super.m_6943_(p_57865_, p_57866_);
    }

    public static BooleanProperty m_57883_(Direction p_57884_) {
        return f_57838_.get(p_57884_);
    }
}

