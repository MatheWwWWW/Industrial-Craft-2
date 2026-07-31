/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import java.util.stream.IntStream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StairBlock
extends Block
implements SimpleWaterloggedBlock {
    public static final DirectionProperty f_56841_ = HorizontalDirectionalBlock.f_54117_;
    public static final EnumProperty<Half> f_56842_ = BlockStateProperties.f_61402_;
    public static final EnumProperty<StairsShape> f_56843_ = BlockStateProperties.f_61398_;
    public static final BooleanProperty f_56844_ = BlockStateProperties.f_61362_;
    protected static final VoxelShape f_56845_ = SlabBlock.f_56356_;
    protected static final VoxelShape f_56846_ = SlabBlock.f_56355_;
    protected static final VoxelShape f_56847_ = Block.m_49796_(0.0, 0.0, 0.0, 8.0, 8.0, 8.0);
    protected static final VoxelShape f_56848_ = Block.m_49796_(0.0, 0.0, 8.0, 8.0, 8.0, 16.0);
    protected static final VoxelShape f_56849_ = Block.m_49796_(0.0, 8.0, 0.0, 8.0, 16.0, 8.0);
    protected static final VoxelShape f_56850_ = Block.m_49796_(0.0, 8.0, 8.0, 8.0, 16.0, 16.0);
    protected static final VoxelShape f_56851_ = Block.m_49796_(8.0, 0.0, 0.0, 16.0, 8.0, 8.0);
    protected static final VoxelShape f_56852_ = Block.m_49796_(8.0, 0.0, 8.0, 16.0, 8.0, 16.0);
    protected static final VoxelShape f_56853_ = Block.m_49796_(8.0, 8.0, 0.0, 16.0, 16.0, 8.0);
    protected static final VoxelShape f_56854_ = Block.m_49796_(8.0, 8.0, 8.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape[] f_56855_ = StairBlock.m_56933_(f_56845_, f_56847_, f_56851_, f_56848_, f_56852_);
    protected static final VoxelShape[] f_56856_ = StairBlock.m_56933_(f_56846_, f_56849_, f_56853_, f_56850_, f_56854_);
    private static final int[] f_56857_ = new int[]{12, 5, 3, 10, 14, 13, 7, 11, 13, 7, 11, 14, 8, 4, 1, 2, 4, 1, 2, 8};
    private final Block f_56858_;
    private final BlockState f_56859_;

    private static VoxelShape[] m_56933_(VoxelShape p_56934_, VoxelShape p_56935_, VoxelShape p_56936_, VoxelShape p_56937_, VoxelShape p_56938_) {
        return (VoxelShape[])IntStream.range(0, 16).mapToObj(p_56945_ -> StairBlock.m_56864_(p_56945_, p_56934_, p_56935_, p_56936_, p_56937_, p_56938_)).toArray(VoxelShape[]::new);
    }

    private static VoxelShape m_56864_(int p_56865_, VoxelShape p_56866_, VoxelShape p_56867_, VoxelShape p_56868_, VoxelShape p_56869_, VoxelShape p_56870_) {
        VoxelShape $$6 = p_56866_;
        if ((p_56865_ & 1) != 0) {
            $$6 = Shapes.m_83110_($$6, p_56867_);
        }
        if ((p_56865_ & 2) != 0) {
            $$6 = Shapes.m_83110_($$6, p_56868_);
        }
        if ((p_56865_ & 4) != 0) {
            $$6 = Shapes.m_83110_($$6, p_56869_);
        }
        if ((p_56865_ & 8) != 0) {
            $$6 = Shapes.m_83110_($$6, p_56870_);
        }
        return $$6;
    }

    protected StairBlock(BlockState p_56862_, BlockBehaviour.Properties p_56863_) {
        super(p_56863_);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_56841_, Direction.NORTH)).m_61124_(f_56842_, Half.BOTTOM)).m_61124_(f_56843_, StairsShape.STRAIGHT)).m_61124_(f_56844_, false));
        this.f_56858_ = p_56862_.m_60734_();
        this.f_56859_ = p_56862_;
    }

    @Override
    public boolean m_7923_(BlockState p_56967_) {
        return true;
    }

    @Override
    public VoxelShape m_5940_(BlockState p_56956_, BlockGetter p_56957_, BlockPos p_56958_, CollisionContext p_56959_) {
        return (p_56956_.m_61143_(f_56842_) == Half.TOP ? f_56855_ : f_56856_)[f_56857_[this.m_56982_(p_56956_)]];
    }

    private int m_56982_(BlockState p_56983_) {
        return p_56983_.m_61143_(f_56843_).ordinal() * 4 + p_56983_.m_61143_(f_56841_).m_122416_();
    }

    @Override
    public void m_214162_(BlockState p_222518_, Level p_222519_, BlockPos p_222520_, RandomSource p_222521_) {
        this.f_56858_.m_214162_(p_222518_, p_222519_, p_222520_, p_222521_);
    }

    @Override
    public void m_6256_(BlockState p_56896_, Level p_56897_, BlockPos p_56898_, Player p_56899_) {
        this.f_56859_.m_60686_(p_56897_, p_56898_, p_56899_);
    }

    @Override
    public void m_6786_(LevelAccessor p_56882_, BlockPos p_56883_, BlockState p_56884_) {
        this.f_56858_.m_6786_(p_56882_, p_56883_, p_56884_);
    }

    @Override
    public float m_7325_() {
        return this.f_56858_.m_7325_();
    }

    @Override
    public void m_6807_(BlockState p_56961_, Level p_56962_, BlockPos p_56963_, BlockState p_56964_, boolean p_56965_) {
        if (p_56961_.m_60713_(p_56961_.m_60734_())) {
            return;
        }
        p_56962_.m_213960_(this.f_56859_, p_56963_, Blocks.f_50016_, p_56963_, false);
        this.f_56858_.m_6807_(this.f_56859_, p_56962_, p_56963_, p_56964_, false);
    }

    @Override
    public void m_6810_(BlockState p_56908_, Level p_56909_, BlockPos p_56910_, BlockState p_56911_, boolean p_56912_) {
        if (p_56908_.m_60713_(p_56911_.m_60734_())) {
            return;
        }
        this.f_56859_.m_60753_(p_56909_, p_56910_, p_56911_, p_56912_);
    }

    @Override
    public void m_141947_(Level p_154720_, BlockPos p_154721_, BlockState p_154722_, Entity p_154723_) {
        this.f_56858_.m_141947_(p_154720_, p_154721_, p_154722_, p_154723_);
    }

    @Override
    public boolean m_6724_(BlockState p_56947_) {
        return this.f_56858_.m_6724_(p_56947_);
    }

    @Override
    public void m_213898_(BlockState p_222523_, ServerLevel p_222524_, BlockPos p_222525_, RandomSource p_222526_) {
        this.f_56858_.m_213898_(p_222523_, p_222524_, p_222525_, p_222526_);
    }

    @Override
    public void m_213897_(BlockState p_222513_, ServerLevel p_222514_, BlockPos p_222515_, RandomSource p_222516_) {
        this.f_56858_.m_213897_(p_222513_, p_222514_, p_222515_, p_222516_);
    }

    @Override
    public InteractionResult m_6227_(BlockState p_56901_, Level p_56902_, BlockPos p_56903_, Player p_56904_, InteractionHand p_56905_, BlockHitResult p_56906_) {
        return this.f_56859_.m_60664_(p_56902_, p_56904_, p_56905_, p_56906_);
    }

    @Override
    public void m_7592_(Level p_56878_, BlockPos p_56879_, Explosion p_56880_) {
        this.f_56858_.m_7592_(p_56878_, p_56879_, p_56880_);
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_56872_) {
        Direction $$1 = p_56872_.m_43719_();
        BlockPos $$2 = p_56872_.m_8083_();
        FluidState $$3 = p_56872_.m_43725_().m_6425_($$2);
        BlockState $$4 = (BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_(f_56841_, p_56872_.m_8125_())).m_61124_(f_56842_, $$1 == Direction.DOWN || $$1 != Direction.UP && p_56872_.m_43720_().f_82480_ - (double)$$2.m_123342_() > 0.5 ? Half.TOP : Half.BOTTOM)).m_61124_(f_56844_, $$3.m_76152_() == Fluids.f_76193_);
        return (BlockState)$$4.m_61124_(f_56843_, StairBlock.m_56976_($$4, p_56872_.m_43725_(), $$2));
    }

    @Override
    public BlockState m_7417_(BlockState p_56925_, Direction p_56926_, BlockState p_56927_, LevelAccessor p_56928_, BlockPos p_56929_, BlockPos p_56930_) {
        if (p_56925_.m_61143_(f_56844_).booleanValue()) {
            p_56928_.m_186469_(p_56929_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_56928_));
        }
        if (p_56926_.m_122434_().m_122479_()) {
            return (BlockState)p_56925_.m_61124_(f_56843_, StairBlock.m_56976_(p_56925_, p_56928_, p_56929_));
        }
        return super.m_7417_(p_56925_, p_56926_, p_56927_, p_56928_, p_56929_, p_56930_);
    }

    private static StairsShape m_56976_(BlockState p_56977_, BlockGetter p_56978_, BlockPos p_56979_) {
        Direction $$7;
        Direction $$5;
        Direction $$3 = p_56977_.m_61143_(f_56841_);
        BlockState $$4 = p_56978_.m_8055_(p_56979_.m_121945_($$3));
        if (StairBlock.m_56980_($$4) && p_56977_.m_61143_(f_56842_) == $$4.m_61143_(f_56842_) && ($$5 = $$4.m_61143_(f_56841_)).m_122434_() != p_56977_.m_61143_(f_56841_).m_122434_() && StairBlock.m_56970_(p_56977_, p_56978_, p_56979_, $$5.m_122424_())) {
            if ($$5 == $$3.m_122428_()) {
                return StairsShape.OUTER_LEFT;
            }
            return StairsShape.OUTER_RIGHT;
        }
        BlockState $$6 = p_56978_.m_8055_(p_56979_.m_121945_($$3.m_122424_()));
        if (StairBlock.m_56980_($$6) && p_56977_.m_61143_(f_56842_) == $$6.m_61143_(f_56842_) && ($$7 = $$6.m_61143_(f_56841_)).m_122434_() != p_56977_.m_61143_(f_56841_).m_122434_() && StairBlock.m_56970_(p_56977_, p_56978_, p_56979_, $$7)) {
            if ($$7 == $$3.m_122428_()) {
                return StairsShape.INNER_LEFT;
            }
            return StairsShape.INNER_RIGHT;
        }
        return StairsShape.STRAIGHT;
    }

    private static boolean m_56970_(BlockState p_56971_, BlockGetter p_56972_, BlockPos p_56973_, Direction p_56974_) {
        BlockState $$4 = p_56972_.m_8055_(p_56973_.m_121945_(p_56974_));
        return !StairBlock.m_56980_($$4) || $$4.m_61143_(f_56841_) != p_56971_.m_61143_(f_56841_) || $$4.m_61143_(f_56842_) != p_56971_.m_61143_(f_56842_);
    }

    public static boolean m_56980_(BlockState p_56981_) {
        return p_56981_.m_60734_() instanceof StairBlock;
    }

    @Override
    public BlockState m_6843_(BlockState p_56922_, Rotation p_56923_) {
        return (BlockState)p_56922_.m_61124_(f_56841_, p_56923_.m_55954_(p_56922_.m_61143_(f_56841_)));
    }

    @Override
    public BlockState m_6943_(BlockState p_56919_, Mirror p_56920_) {
        Direction $$2 = p_56919_.m_61143_(f_56841_);
        StairsShape $$3 = p_56919_.m_61143_(f_56843_);
        switch (p_56920_) {
            case LEFT_RIGHT: {
                if ($$2.m_122434_() != Direction.Axis.Z) break;
                switch ($$3) {
                    case INNER_LEFT: {
                        return (BlockState)p_56919_.m_60717_(Rotation.CLOCKWISE_180).m_61124_(f_56843_, StairsShape.INNER_RIGHT);
                    }
                    case INNER_RIGHT: {
                        return (BlockState)p_56919_.m_60717_(Rotation.CLOCKWISE_180).m_61124_(f_56843_, StairsShape.INNER_LEFT);
                    }
                    case OUTER_LEFT: {
                        return (BlockState)p_56919_.m_60717_(Rotation.CLOCKWISE_180).m_61124_(f_56843_, StairsShape.OUTER_RIGHT);
                    }
                    case OUTER_RIGHT: {
                        return (BlockState)p_56919_.m_60717_(Rotation.CLOCKWISE_180).m_61124_(f_56843_, StairsShape.OUTER_LEFT);
                    }
                }
                return p_56919_.m_60717_(Rotation.CLOCKWISE_180);
            }
            case FRONT_BACK: {
                if ($$2.m_122434_() != Direction.Axis.X) break;
                switch ($$3) {
                    case INNER_LEFT: {
                        return (BlockState)p_56919_.m_60717_(Rotation.CLOCKWISE_180).m_61124_(f_56843_, StairsShape.INNER_LEFT);
                    }
                    case INNER_RIGHT: {
                        return (BlockState)p_56919_.m_60717_(Rotation.CLOCKWISE_180).m_61124_(f_56843_, StairsShape.INNER_RIGHT);
                    }
                    case OUTER_LEFT: {
                        return (BlockState)p_56919_.m_60717_(Rotation.CLOCKWISE_180).m_61124_(f_56843_, StairsShape.OUTER_RIGHT);
                    }
                    case OUTER_RIGHT: {
                        return (BlockState)p_56919_.m_60717_(Rotation.CLOCKWISE_180).m_61124_(f_56843_, StairsShape.OUTER_LEFT);
                    }
                    case STRAIGHT: {
                        return p_56919_.m_60717_(Rotation.CLOCKWISE_180);
                    }
                }
                break;
            }
        }
        return super.m_6943_(p_56919_, p_56920_);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_56932_) {
        p_56932_.m_61104_(f_56841_, f_56842_, f_56843_, f_56844_);
    }

    @Override
    public FluidState m_5888_(BlockState p_56969_) {
        if (p_56969_.m_61143_(f_56844_).booleanValue()) {
            return Fluids.f_76193_.m_76068_(false);
        }
        return super.m_5888_(p_56969_);
    }

    @Override
    public boolean m_7357_(BlockState p_56891_, BlockGetter p_56892_, BlockPos p_56893_, PathComputationType p_56894_) {
        return false;
    }
}

