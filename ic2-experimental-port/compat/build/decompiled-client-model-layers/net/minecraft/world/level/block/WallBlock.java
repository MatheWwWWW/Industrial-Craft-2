/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 */
package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WallBlock
extends Block
implements SimpleWaterloggedBlock {
    public static final BooleanProperty f_57949_ = BlockStateProperties.f_61366_;
    public static final EnumProperty<WallSide> f_57950_ = BlockStateProperties.f_61378_;
    public static final EnumProperty<WallSide> f_57951_ = BlockStateProperties.f_61379_;
    public static final EnumProperty<WallSide> f_57952_ = BlockStateProperties.f_61380_;
    public static final EnumProperty<WallSide> f_57953_ = BlockStateProperties.f_61381_;
    public static final BooleanProperty f_57954_ = BlockStateProperties.f_61362_;
    private final Map<BlockState, VoxelShape> f_57955_;
    private final Map<BlockState, VoxelShape> f_57956_;
    private static final int f_154876_ = 3;
    private static final int f_154877_ = 14;
    private static final int f_154878_ = 4;
    private static final int f_154879_ = 1;
    private static final int f_154880_ = 7;
    private static final int f_154881_ = 9;
    private static final VoxelShape f_57957_ = Block.m_49796_(7.0, 0.0, 7.0, 9.0, 16.0, 9.0);
    private static final VoxelShape f_57958_ = Block.m_49796_(7.0, 0.0, 0.0, 9.0, 16.0, 9.0);
    private static final VoxelShape f_57959_ = Block.m_49796_(7.0, 0.0, 7.0, 9.0, 16.0, 16.0);
    private static final VoxelShape f_57960_ = Block.m_49796_(0.0, 0.0, 7.0, 9.0, 16.0, 9.0);
    private static final VoxelShape f_57961_ = Block.m_49796_(7.0, 0.0, 7.0, 16.0, 16.0, 9.0);

    public WallBlock(BlockBehaviour.Properties p_57964_) {
        super(p_57964_);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_57949_, true)).m_61124_(f_57951_, WallSide.NONE)).m_61124_(f_57950_, WallSide.NONE)).m_61124_(f_57952_, WallSide.NONE)).m_61124_(f_57953_, WallSide.NONE)).m_61124_(f_57954_, false));
        this.f_57955_ = this.m_57965_(4.0f, 3.0f, 16.0f, 0.0f, 14.0f, 16.0f);
        this.f_57956_ = this.m_57965_(4.0f, 3.0f, 24.0f, 0.0f, 24.0f, 24.0f);
    }

    private static VoxelShape m_58033_(VoxelShape p_58034_, WallSide p_58035_, VoxelShape p_58036_, VoxelShape p_58037_) {
        if (p_58035_ == WallSide.TALL) {
            return Shapes.m_83110_(p_58034_, p_58037_);
        }
        if (p_58035_ == WallSide.LOW) {
            return Shapes.m_83110_(p_58034_, p_58036_);
        }
        return p_58034_;
    }

    private Map<BlockState, VoxelShape> m_57965_(float p_57966_, float p_57967_, float p_57968_, float p_57969_, float p_57970_, float p_57971_) {
        float $$6 = 8.0f - p_57966_;
        float $$7 = 8.0f + p_57966_;
        float $$8 = 8.0f - p_57967_;
        float $$9 = 8.0f + p_57967_;
        VoxelShape $$10 = Block.m_49796_($$6, 0.0, $$6, $$7, p_57968_, $$7);
        VoxelShape $$11 = Block.m_49796_($$8, p_57969_, 0.0, $$9, p_57970_, $$9);
        VoxelShape $$12 = Block.m_49796_($$8, p_57969_, $$8, $$9, p_57970_, 16.0);
        VoxelShape $$13 = Block.m_49796_(0.0, p_57969_, $$8, $$9, p_57970_, $$9);
        VoxelShape $$14 = Block.m_49796_($$8, p_57969_, $$8, 16.0, p_57970_, $$9);
        VoxelShape $$15 = Block.m_49796_($$8, p_57969_, 0.0, $$9, p_57971_, $$9);
        VoxelShape $$16 = Block.m_49796_($$8, p_57969_, $$8, $$9, p_57971_, 16.0);
        VoxelShape $$17 = Block.m_49796_(0.0, p_57969_, $$8, $$9, p_57971_, $$9);
        VoxelShape $$18 = Block.m_49796_($$8, p_57969_, $$8, 16.0, p_57971_, $$9);
        ImmutableMap.Builder $$19 = ImmutableMap.builder();
        for (Boolean $$20 : f_57949_.m_6908_()) {
            for (WallSide $$21 : f_57950_.m_6908_()) {
                for (WallSide $$22 : f_57951_.m_6908_()) {
                    for (WallSide $$23 : f_57953_.m_6908_()) {
                        for (WallSide $$24 : f_57952_.m_6908_()) {
                            VoxelShape $$25 = Shapes.m_83040_();
                            $$25 = WallBlock.m_58033_($$25, $$21, $$14, $$18);
                            $$25 = WallBlock.m_58033_($$25, $$23, $$13, $$17);
                            $$25 = WallBlock.m_58033_($$25, $$22, $$11, $$15);
                            $$25 = WallBlock.m_58033_($$25, $$24, $$12, $$16);
                            if ($$20.booleanValue()) {
                                $$25 = Shapes.m_83110_($$25, $$10);
                            }
                            BlockState $$26 = (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_(f_57949_, $$20)).m_61124_(f_57950_, $$21)).m_61124_(f_57953_, $$23)).m_61124_(f_57951_, $$22)).m_61124_(f_57952_, $$24);
                            $$19.put((Object)((BlockState)$$26.m_61124_(f_57954_, false)), (Object)$$25);
                            $$19.put((Object)((BlockState)$$26.m_61124_(f_57954_, true)), (Object)$$25);
                        }
                    }
                }
            }
        }
        return $$19.build();
    }

    @Override
    public VoxelShape m_5940_(BlockState p_58050_, BlockGetter p_58051_, BlockPos p_58052_, CollisionContext p_58053_) {
        return this.f_57955_.get(p_58050_);
    }

    @Override
    public VoxelShape m_5939_(BlockState p_58055_, BlockGetter p_58056_, BlockPos p_58057_, CollisionContext p_58058_) {
        return this.f_57956_.get(p_58055_);
    }

    @Override
    public boolean m_7357_(BlockState p_57996_, BlockGetter p_57997_, BlockPos p_57998_, PathComputationType p_57999_) {
        return false;
    }

    private boolean m_58020_(BlockState p_58021_, boolean p_58022_, Direction p_58023_) {
        Block $$3 = p_58021_.m_60734_();
        boolean $$4 = $$3 instanceof FenceGateBlock && FenceGateBlock.m_53378_(p_58021_, p_58023_);
        return p_58021_.m_204336_(BlockTags.f_13032_) || !WallBlock.m_152463_(p_58021_) && p_58022_ || $$3 instanceof IronBarsBlock || $$4;
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_57973_) {
        Level $$1 = p_57973_.m_43725_();
        BlockPos $$2 = p_57973_.m_8083_();
        FluidState $$3 = p_57973_.m_43725_().m_6425_(p_57973_.m_8083_());
        BlockPos $$4 = $$2.m_122012_();
        BlockPos $$5 = $$2.m_122029_();
        BlockPos $$6 = $$2.m_122019_();
        BlockPos $$7 = $$2.m_122024_();
        BlockPos $$8 = $$2.m_7494_();
        BlockState $$9 = $$1.m_8055_($$4);
        BlockState $$10 = $$1.m_8055_($$5);
        BlockState $$11 = $$1.m_8055_($$6);
        BlockState $$12 = $$1.m_8055_($$7);
        BlockState $$13 = $$1.m_8055_($$8);
        boolean $$14 = this.m_58020_($$9, $$9.m_60783_($$1, $$4, Direction.SOUTH), Direction.SOUTH);
        boolean $$15 = this.m_58020_($$10, $$10.m_60783_($$1, $$5, Direction.WEST), Direction.WEST);
        boolean $$16 = this.m_58020_($$11, $$11.m_60783_($$1, $$6, Direction.NORTH), Direction.NORTH);
        boolean $$17 = this.m_58020_($$12, $$12.m_60783_($$1, $$7, Direction.EAST), Direction.EAST);
        BlockState $$18 = (BlockState)this.m_49966_().m_61124_(f_57954_, $$3.m_76152_() == Fluids.f_76193_);
        return this.m_57979_($$1, $$18, $$8, $$13, $$14, $$15, $$16, $$17);
    }

    @Override
    public BlockState m_7417_(BlockState p_58014_, Direction p_58015_, BlockState p_58016_, LevelAccessor p_58017_, BlockPos p_58018_, BlockPos p_58019_) {
        if (p_58014_.m_61143_(f_57954_).booleanValue()) {
            p_58017_.m_186469_(p_58018_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_58017_));
        }
        if (p_58015_ == Direction.DOWN) {
            return super.m_7417_(p_58014_, p_58015_, p_58016_, p_58017_, p_58018_, p_58019_);
        }
        if (p_58015_ == Direction.UP) {
            return this.m_57974_(p_58017_, p_58014_, p_58019_, p_58016_);
        }
        return this.m_57988_(p_58017_, p_58018_, p_58014_, p_58019_, p_58016_, p_58015_);
    }

    private static boolean m_58010_(BlockState p_58011_, Property<WallSide> p_58012_) {
        return p_58011_.m_61143_(p_58012_) != WallSide.NONE;
    }

    private static boolean m_58038_(VoxelShape p_58039_, VoxelShape p_58040_) {
        return !Shapes.m_83157_(p_58040_, p_58039_, BooleanOp.f_82685_);
    }

    private BlockState m_57974_(LevelReader p_57975_, BlockState p_57976_, BlockPos p_57977_, BlockState p_57978_) {
        boolean $$4 = WallBlock.m_58010_(p_57976_, f_57951_);
        boolean $$5 = WallBlock.m_58010_(p_57976_, f_57950_);
        boolean $$6 = WallBlock.m_58010_(p_57976_, f_57952_);
        boolean $$7 = WallBlock.m_58010_(p_57976_, f_57953_);
        return this.m_57979_(p_57975_, p_57976_, p_57977_, p_57978_, $$4, $$5, $$6, $$7);
    }

    private BlockState m_57988_(LevelReader p_57989_, BlockPos p_57990_, BlockState p_57991_, BlockPos p_57992_, BlockState p_57993_, Direction p_57994_) {
        Direction $$6 = p_57994_.m_122424_();
        boolean $$7 = p_57994_ == Direction.NORTH ? this.m_58020_(p_57993_, p_57993_.m_60783_(p_57989_, p_57992_, $$6), $$6) : WallBlock.m_58010_(p_57991_, f_57951_);
        boolean $$8 = p_57994_ == Direction.EAST ? this.m_58020_(p_57993_, p_57993_.m_60783_(p_57989_, p_57992_, $$6), $$6) : WallBlock.m_58010_(p_57991_, f_57950_);
        boolean $$9 = p_57994_ == Direction.SOUTH ? this.m_58020_(p_57993_, p_57993_.m_60783_(p_57989_, p_57992_, $$6), $$6) : WallBlock.m_58010_(p_57991_, f_57952_);
        boolean $$10 = p_57994_ == Direction.WEST ? this.m_58020_(p_57993_, p_57993_.m_60783_(p_57989_, p_57992_, $$6), $$6) : WallBlock.m_58010_(p_57991_, f_57953_);
        BlockPos $$11 = p_57990_.m_7494_();
        BlockState $$12 = p_57989_.m_8055_($$11);
        return this.m_57979_(p_57989_, p_57991_, $$11, $$12, $$7, $$8, $$9, $$10);
    }

    private BlockState m_57979_(LevelReader p_57980_, BlockState p_57981_, BlockPos p_57982_, BlockState p_57983_, boolean p_57984_, boolean p_57985_, boolean p_57986_, boolean p_57987_) {
        VoxelShape $$8 = p_57983_.m_60812_(p_57980_, p_57982_).m_83263_(Direction.DOWN);
        BlockState $$9 = this.m_58024_(p_57981_, p_57984_, p_57985_, p_57986_, p_57987_, $$8);
        return (BlockState)$$9.m_61124_(f_57949_, this.m_58006_($$9, p_57983_, $$8));
    }

    private boolean m_58006_(BlockState p_58007_, BlockState p_58008_, VoxelShape p_58009_) {
        boolean $$13;
        boolean $$12;
        boolean $$3;
        boolean bl = $$3 = p_58008_.m_60734_() instanceof WallBlock && p_58008_.m_61143_(f_57949_) != false;
        if ($$3) {
            return true;
        }
        WallSide $$4 = p_58007_.m_61143_(f_57951_);
        WallSide $$5 = p_58007_.m_61143_(f_57952_);
        WallSide $$6 = p_58007_.m_61143_(f_57950_);
        WallSide $$7 = p_58007_.m_61143_(f_57953_);
        boolean $$8 = $$5 == WallSide.NONE;
        boolean $$9 = $$7 == WallSide.NONE;
        boolean $$10 = $$6 == WallSide.NONE;
        boolean $$11 = $$4 == WallSide.NONE;
        boolean bl2 = $$12 = $$11 && $$8 && $$9 && $$10 || $$11 != $$8 || $$9 != $$10;
        if ($$12) {
            return true;
        }
        boolean bl3 = $$13 = $$4 == WallSide.TALL && $$5 == WallSide.TALL || $$6 == WallSide.TALL && $$7 == WallSide.TALL;
        if ($$13) {
            return false;
        }
        return p_58008_.m_204336_(BlockTags.f_13081_) || WallBlock.m_58038_(p_58009_, f_57957_);
    }

    private BlockState m_58024_(BlockState p_58025_, boolean p_58026_, boolean p_58027_, boolean p_58028_, boolean p_58029_, VoxelShape p_58030_) {
        return (BlockState)((BlockState)((BlockState)((BlockState)p_58025_.m_61124_(f_57951_, this.m_58041_(p_58026_, p_58030_, f_57958_))).m_61124_(f_57950_, this.m_58041_(p_58027_, p_58030_, f_57961_))).m_61124_(f_57952_, this.m_58041_(p_58028_, p_58030_, f_57959_))).m_61124_(f_57953_, this.m_58041_(p_58029_, p_58030_, f_57960_));
    }

    private WallSide m_58041_(boolean p_58042_, VoxelShape p_58043_, VoxelShape p_58044_) {
        if (p_58042_) {
            if (WallBlock.m_58038_(p_58043_, p_58044_)) {
                return WallSide.TALL;
            }
            return WallSide.LOW;
        }
        return WallSide.NONE;
    }

    @Override
    public FluidState m_5888_(BlockState p_58060_) {
        if (p_58060_.m_61143_(f_57954_).booleanValue()) {
            return Fluids.f_76193_.m_76068_(false);
        }
        return super.m_5888_(p_58060_);
    }

    @Override
    public boolean m_7420_(BlockState p_58046_, BlockGetter p_58047_, BlockPos p_58048_) {
        return p_58046_.m_61143_(f_57954_) == false;
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_58032_) {
        p_58032_.m_61104_(f_57949_, f_57951_, f_57950_, f_57953_, f_57952_, f_57954_);
    }

    @Override
    public BlockState m_6843_(BlockState p_58004_, Rotation p_58005_) {
        switch (p_58005_) {
            case CLOCKWISE_180: {
                return (BlockState)((BlockState)((BlockState)((BlockState)p_58004_.m_61124_(f_57951_, p_58004_.m_61143_(f_57952_))).m_61124_(f_57950_, p_58004_.m_61143_(f_57953_))).m_61124_(f_57952_, p_58004_.m_61143_(f_57951_))).m_61124_(f_57953_, p_58004_.m_61143_(f_57950_));
            }
            case COUNTERCLOCKWISE_90: {
                return (BlockState)((BlockState)((BlockState)((BlockState)p_58004_.m_61124_(f_57951_, p_58004_.m_61143_(f_57950_))).m_61124_(f_57950_, p_58004_.m_61143_(f_57952_))).m_61124_(f_57952_, p_58004_.m_61143_(f_57953_))).m_61124_(f_57953_, p_58004_.m_61143_(f_57951_));
            }
            case CLOCKWISE_90: {
                return (BlockState)((BlockState)((BlockState)((BlockState)p_58004_.m_61124_(f_57951_, p_58004_.m_61143_(f_57953_))).m_61124_(f_57950_, p_58004_.m_61143_(f_57951_))).m_61124_(f_57952_, p_58004_.m_61143_(f_57950_))).m_61124_(f_57953_, p_58004_.m_61143_(f_57952_));
            }
        }
        return p_58004_;
    }

    @Override
    public BlockState m_6943_(BlockState p_58001_, Mirror p_58002_) {
        switch (p_58002_) {
            case LEFT_RIGHT: {
                return (BlockState)((BlockState)p_58001_.m_61124_(f_57951_, p_58001_.m_61143_(f_57952_))).m_61124_(f_57952_, p_58001_.m_61143_(f_57951_));
            }
            case FRONT_BACK: {
                return (BlockState)((BlockState)p_58001_.m_61124_(f_57950_, p_58001_.m_61143_(f_57953_))).m_61124_(f_57953_, p_58001_.m_61143_(f_57950_));
            }
        }
        return super.m_6943_(p_58001_, p_58002_);
    }
}

