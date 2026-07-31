/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2FloatMap
 *  it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.WorldlyContainerHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ComposterBlock
extends Block
implements WorldlyContainerHolder {
    public static final int f_153088_ = 8;
    public static final int f_153089_ = 0;
    public static final int f_153090_ = 7;
    public static final IntegerProperty f_51913_ = BlockStateProperties.f_61419_;
    public static final Object2FloatMap<ItemLike> f_51914_ = new Object2FloatOpenHashMap();
    private static final int f_153091_ = 2;
    private static final VoxelShape f_51915_ = Shapes.m_83144_();
    private static final VoxelShape[] f_51916_ = Util.m_137469_(new VoxelShape[9], p_51967_ -> {
        for (int $$1 = 0; $$1 < 8; ++$$1) {
            p_51967_[$$1] = Shapes.m_83113_(f_51915_, Block.m_49796_(2.0, Math.max(2, 1 + $$1 * 2), 2.0, 14.0, 16.0, 14.0), BooleanOp.f_82685_);
        }
        p_51967_[8] = p_51967_[7];
    });

    public static void m_51988_() {
        f_51914_.defaultReturnValue(-1.0f);
        float $$0 = 0.3f;
        float $$1 = 0.5f;
        float $$2 = 0.65f;
        float $$3 = 0.85f;
        float $$4 = 1.0f;
        ComposterBlock.m_51920_(0.3f, Items.f_41899_);
        ComposterBlock.m_51920_(0.3f, Items.f_41896_);
        ComposterBlock.m_51920_(0.3f, Items.f_41897_);
        ComposterBlock.m_51920_(0.3f, Items.f_41901_);
        ComposterBlock.m_51920_(0.3f, Items.f_41900_);
        ComposterBlock.m_51920_(0.3f, Items.f_41898_);
        ComposterBlock.m_51920_(0.3f, Items.f_151009_);
        ComposterBlock.m_51920_(0.3f, Items.f_220178_);
        ComposterBlock.m_51920_(0.3f, Items.f_42799_);
        ComposterBlock.m_51920_(0.3f, Items.f_42800_);
        ComposterBlock.m_51920_(0.3f, Items.f_42801_);
        ComposterBlock.m_51920_(0.3f, Items.f_41826_);
        ComposterBlock.m_51920_(0.3f, Items.f_41827_);
        ComposterBlock.m_51920_(0.3f, Items.f_41828_);
        ComposterBlock.m_51920_(0.3f, Items.f_220175_);
        ComposterBlock.m_51920_(0.3f, Items.f_42733_);
        ComposterBlock.m_51920_(0.3f, Items.f_42576_);
        ComposterBlock.m_51920_(0.3f, Items.f_41864_);
        ComposterBlock.m_51920_(0.3f, Items.f_41910_);
        ComposterBlock.m_51920_(0.3f, Items.f_42578_);
        ComposterBlock.m_51920_(0.3f, Items.f_42577_);
        ComposterBlock.m_51920_(0.3f, Items.f_41867_);
        ComposterBlock.m_51920_(0.3f, Items.f_42780_);
        ComposterBlock.m_51920_(0.3f, Items.f_151079_);
        ComposterBlock.m_51920_(0.3f, Items.f_42404_);
        ComposterBlock.m_51920_(0.3f, Items.f_151015_);
        ComposterBlock.m_51920_(0.3f, Items.f_151019_);
        ComposterBlock.m_51920_(0.3f, Items.f_151017_);
        ComposterBlock.m_51920_(0.3f, Items.f_220180_);
        ComposterBlock.m_51920_(0.5f, Items.f_42515_);
        ComposterBlock.m_51920_(0.5f, Items.f_42210_);
        ComposterBlock.m_51920_(0.5f, Items.f_186362_);
        ComposterBlock.m_51920_(0.5f, Items.f_41982_);
        ComposterBlock.m_51920_(0.5f, Items.f_41909_);
        ComposterBlock.m_51920_(0.5f, Items.f_42029_);
        ComposterBlock.m_51920_(0.5f, Items.f_41906_);
        ComposterBlock.m_51920_(0.5f, Items.f_41907_);
        ComposterBlock.m_51920_(0.5f, Items.f_41908_);
        ComposterBlock.m_51920_(0.5f, Items.f_42575_);
        ComposterBlock.m_51920_(0.5f, Items.f_151025_);
        ComposterBlock.m_51920_(0.65f, Items.f_41868_);
        ComposterBlock.m_51920_(0.65f, Items.f_42094_);
        ComposterBlock.m_51920_(0.65f, Items.f_42046_);
        ComposterBlock.m_51920_(0.65f, Items.f_42047_);
        ComposterBlock.m_51920_(0.65f, Items.f_42028_);
        ComposterBlock.m_51920_(0.65f, Items.f_42410_);
        ComposterBlock.m_51920_(0.65f, Items.f_42732_);
        ComposterBlock.m_51920_(0.65f, Items.f_42619_);
        ComposterBlock.m_51920_(0.65f, Items.f_42533_);
        ComposterBlock.m_51920_(0.65f, Items.f_42620_);
        ComposterBlock.m_51920_(0.65f, Items.f_42405_);
        ComposterBlock.m_51920_(0.65f, Items.f_41952_);
        ComposterBlock.m_51920_(0.65f, Items.f_41953_);
        ComposterBlock.m_51920_(0.65f, Items.f_42024_);
        ComposterBlock.m_51920_(0.65f, Items.f_41954_);
        ComposterBlock.m_51920_(0.65f, Items.f_41955_);
        ComposterBlock.m_51920_(0.65f, Items.f_42588_);
        ComposterBlock.m_51920_(0.65f, Items.f_41956_);
        ComposterBlock.m_51920_(0.65f, Items.f_41957_);
        ComposterBlock.m_51920_(0.65f, Items.f_42783_);
        ComposterBlock.m_51920_(0.65f, Items.f_41939_);
        ComposterBlock.m_51920_(0.65f, Items.f_41940_);
        ComposterBlock.m_51920_(0.65f, Items.f_41941_);
        ComposterBlock.m_51920_(0.65f, Items.f_41942_);
        ComposterBlock.m_51920_(0.65f, Items.f_41943_);
        ComposterBlock.m_51920_(0.65f, Items.f_41944_);
        ComposterBlock.m_51920_(0.65f, Items.f_41945_);
        ComposterBlock.m_51920_(0.65f, Items.f_41946_);
        ComposterBlock.m_51920_(0.65f, Items.f_41947_);
        ComposterBlock.m_51920_(0.65f, Items.f_41948_);
        ComposterBlock.m_51920_(0.65f, Items.f_41949_);
        ComposterBlock.m_51920_(0.65f, Items.f_41950_);
        ComposterBlock.m_51920_(0.65f, Items.f_41951_);
        ComposterBlock.m_51920_(0.65f, Items.f_41865_);
        ComposterBlock.m_51920_(0.65f, Items.f_42206_);
        ComposterBlock.m_51920_(0.65f, Items.f_42207_);
        ComposterBlock.m_51920_(0.65f, Items.f_42208_);
        ComposterBlock.m_51920_(0.65f, Items.f_42209_);
        ComposterBlock.m_51920_(0.65f, Items.f_42211_);
        ComposterBlock.m_51920_(0.65f, Items.f_151014_);
        ComposterBlock.m_51920_(0.65f, Items.f_151012_);
        ComposterBlock.m_51920_(0.65f, Items.f_151016_);
        ComposterBlock.m_51920_(0.65f, Items.f_151018_);
        ComposterBlock.m_51920_(0.85f, Items.f_42129_);
        ComposterBlock.m_51920_(0.85f, Items.f_42022_);
        ComposterBlock.m_51920_(0.85f, Items.f_42023_);
        ComposterBlock.m_51920_(0.85f, Items.f_42259_);
        ComposterBlock.m_51920_(0.85f, Items.f_42260_);
        ComposterBlock.m_51920_(0.85f, Items.f_151013_);
        ComposterBlock.m_51920_(0.85f, Items.f_42406_);
        ComposterBlock.m_51920_(0.85f, Items.f_42674_);
        ComposterBlock.m_51920_(0.85f, Items.f_42572_);
        ComposterBlock.m_51920_(1.0f, Items.f_42502_);
        ComposterBlock.m_51920_(1.0f, Items.f_42687_);
    }

    private static void m_51920_(float p_51921_, ItemLike p_51922_) {
        f_51914_.put((Object)p_51922_.m_5456_(), p_51921_);
    }

    public ComposterBlock(BlockBehaviour.Properties p_51919_) {
        super(p_51919_);
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_51913_, 0));
    }

    public static void m_51923_(Level p_51924_, BlockPos p_51925_, boolean p_51926_) {
        BlockState $$3 = p_51924_.m_8055_(p_51925_);
        p_51924_.m_7785_(p_51925_.m_123341_(), p_51925_.m_123342_(), p_51925_.m_123343_(), p_51926_ ? SoundEvents.f_11765_ : SoundEvents.f_11764_, SoundSource.BLOCKS, 1.0f, 1.0f, false);
        double $$4 = $$3.m_60808_(p_51924_, p_51925_).m_83290_(Direction.Axis.Y, 0.5, 0.5) + 0.03125;
        double $$5 = 0.13125f;
        double $$6 = 0.7375f;
        RandomSource $$7 = p_51924_.m_213780_();
        for (int $$8 = 0; $$8 < 10; ++$$8) {
            double $$9 = $$7.m_188583_() * 0.02;
            double $$10 = $$7.m_188583_() * 0.02;
            double $$11 = $$7.m_188583_() * 0.02;
            p_51924_.m_7106_(ParticleTypes.f_123749_, (double)p_51925_.m_123341_() + (double)0.13125f + (double)0.7375f * (double)$$7.m_188501_(), (double)p_51925_.m_123342_() + $$4 + (double)$$7.m_188501_() * (1.0 - $$4), (double)p_51925_.m_123343_() + (double)0.13125f + (double)0.7375f * (double)$$7.m_188501_(), $$9, $$10, $$11);
        }
    }

    @Override
    public VoxelShape m_5940_(BlockState p_51973_, BlockGetter p_51974_, BlockPos p_51975_, CollisionContext p_51976_) {
        return f_51916_[p_51973_.m_61143_(f_51913_)];
    }

    @Override
    public VoxelShape m_6079_(BlockState p_51969_, BlockGetter p_51970_, BlockPos p_51971_) {
        return f_51915_;
    }

    @Override
    public VoxelShape m_5939_(BlockState p_51990_, BlockGetter p_51991_, BlockPos p_51992_, CollisionContext p_51993_) {
        return f_51916_[0];
    }

    @Override
    public void m_6807_(BlockState p_51978_, Level p_51979_, BlockPos p_51980_, BlockState p_51981_, boolean p_51982_) {
        if (p_51978_.m_61143_(f_51913_) == 7) {
            p_51979_.m_186460_(p_51980_, p_51978_.m_60734_(), 20);
        }
    }

    @Override
    public InteractionResult m_6227_(BlockState p_51949_, Level p_51950_, BlockPos p_51951_, Player p_51952_, InteractionHand p_51953_, BlockHitResult p_51954_) {
        int $$6 = p_51949_.m_61143_(f_51913_);
        ItemStack $$7 = p_51952_.m_21120_(p_51953_);
        if ($$6 < 8 && f_51914_.containsKey((Object)$$7.m_41720_())) {
            if ($$6 < 7 && !p_51950_.f_46443_) {
                BlockState $$8 = ComposterBlock.m_51983_(p_51949_, p_51950_, p_51951_, $$7);
                p_51950_.m_46796_(1500, p_51951_, p_51949_ != $$8 ? 1 : 0);
                p_51952_.m_36246_(Stats.f_12982_.m_12902_($$7.m_41720_()));
                if (!p_51952_.m_150110_().f_35937_) {
                    $$7.m_41774_(1);
                }
            }
            return InteractionResult.m_19078_(p_51950_.f_46443_);
        }
        if ($$6 == 8) {
            ComposterBlock.m_51998_(p_51949_, p_51950_, p_51951_);
            return InteractionResult.m_19078_(p_51950_.f_46443_);
        }
        return InteractionResult.PASS;
    }

    public static BlockState m_51929_(BlockState p_51930_, ServerLevel p_51931_, ItemStack p_51932_, BlockPos p_51933_) {
        int $$4 = p_51930_.m_61143_(f_51913_);
        if ($$4 < 7 && f_51914_.containsKey((Object)p_51932_.m_41720_())) {
            BlockState $$5 = ComposterBlock.m_51983_(p_51930_, p_51931_, p_51933_, p_51932_);
            p_51932_.m_41774_(1);
            return $$5;
        }
        return p_51930_;
    }

    public static BlockState m_51998_(BlockState p_51999_, Level p_52000_, BlockPos p_52001_) {
        if (!p_52000_.f_46443_) {
            float $$3 = 0.7f;
            double $$4 = (double)(p_52000_.f_46441_.m_188501_() * 0.7f) + (double)0.15f;
            double $$5 = (double)(p_52000_.f_46441_.m_188501_() * 0.7f) + 0.06000000238418579 + 0.6;
            double $$6 = (double)(p_52000_.f_46441_.m_188501_() * 0.7f) + (double)0.15f;
            ItemEntity $$7 = new ItemEntity(p_52000_, (double)p_52001_.m_123341_() + $$4, (double)p_52001_.m_123342_() + $$5, (double)p_52001_.m_123343_() + $$6, new ItemStack(Items.f_42499_));
            $$7.m_32060_();
            p_52000_.m_7967_($$7);
        }
        BlockState $$8 = ComposterBlock.m_52002_(p_51999_, p_52000_, p_52001_);
        p_52000_.m_5594_(null, p_52001_, SoundEvents.f_11763_, SoundSource.BLOCKS, 1.0f, 1.0f);
        return $$8;
    }

    static BlockState m_52002_(BlockState p_52003_, LevelAccessor p_52004_, BlockPos p_52005_) {
        BlockState $$3 = (BlockState)p_52003_.m_61124_(f_51913_, 0);
        p_52004_.m_7731_(p_52005_, $$3, 3);
        return $$3;
    }

    static BlockState m_51983_(BlockState p_51984_, LevelAccessor p_51985_, BlockPos p_51986_, ItemStack p_51987_) {
        int $$4 = p_51984_.m_61143_(f_51913_);
        float $$5 = f_51914_.getFloat((Object)p_51987_.m_41720_());
        if ($$4 == 0 && $$5 > 0.0f || p_51985_.m_213780_().m_188500_() < (double)$$5) {
            int $$6 = $$4 + 1;
            BlockState $$7 = (BlockState)p_51984_.m_61124_(f_51913_, $$6);
            p_51985_.m_7731_(p_51986_, $$7, 3);
            if ($$6 == 7) {
                p_51985_.m_186460_(p_51986_, p_51984_.m_60734_(), 20);
            }
            return $$7;
        }
        return p_51984_;
    }

    @Override
    public void m_213897_(BlockState p_221015_, ServerLevel p_221016_, BlockPos p_221017_, RandomSource p_221018_) {
        if (p_221015_.m_61143_(f_51913_) == 7) {
            p_221016_.m_7731_(p_221017_, (BlockState)p_221015_.m_61122_(f_51913_), 3);
            p_221016_.m_5594_(null, p_221017_, SoundEvents.f_11766_, SoundSource.BLOCKS, 1.0f, 1.0f);
        }
    }

    @Override
    public boolean m_7278_(BlockState p_51928_) {
        return true;
    }

    @Override
    public int m_6782_(BlockState p_51945_, Level p_51946_, BlockPos p_51947_) {
        return p_51945_.m_61143_(f_51913_);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_51965_) {
        p_51965_.m_61104_(f_51913_);
    }

    @Override
    public boolean m_7357_(BlockState p_51940_, BlockGetter p_51941_, BlockPos p_51942_, PathComputationType p_51943_) {
        return false;
    }

    @Override
    public WorldlyContainer m_5840_(BlockState p_51956_, LevelAccessor p_51957_, BlockPos p_51958_) {
        int $$3 = p_51956_.m_61143_(f_51913_);
        if ($$3 == 8) {
            return new OutputContainer(p_51956_, p_51957_, p_51958_, new ItemStack(Items.f_42499_));
        }
        if ($$3 < 7) {
            return new InputContainer(p_51956_, p_51957_, p_51958_);
        }
        return new EmptyContainer();
    }

    static class OutputContainer
    extends SimpleContainer
    implements WorldlyContainer {
        private final BlockState f_52037_;
        private final LevelAccessor f_52038_;
        private final BlockPos f_52039_;
        private boolean f_52040_;

        public OutputContainer(BlockState p_52042_, LevelAccessor p_52043_, BlockPos p_52044_, ItemStack p_52045_) {
            super(p_52045_);
            this.f_52037_ = p_52042_;
            this.f_52038_ = p_52043_;
            this.f_52039_ = p_52044_;
        }

        @Override
        public int m_6893_() {
            return 1;
        }

        @Override
        public int[] m_7071_(Direction p_52053_) {
            int[] nArray;
            if (p_52053_ == Direction.DOWN) {
                int[] nArray2 = new int[1];
                nArray = nArray2;
                nArray2[0] = 0;
            } else {
                nArray = new int[]{};
            }
            return nArray;
        }

        @Override
        public boolean m_7155_(int p_52049_, ItemStack p_52050_, @Nullable Direction p_52051_) {
            return false;
        }

        @Override
        public boolean m_7157_(int p_52055_, ItemStack p_52056_, Direction p_52057_) {
            return !this.f_52040_ && p_52057_ == Direction.DOWN && p_52056_.m_150930_(Items.f_42499_);
        }

        @Override
        public void m_6596_() {
            ComposterBlock.m_52002_(this.f_52037_, this.f_52038_, this.f_52039_);
            this.f_52040_ = true;
        }
    }

    static class InputContainer
    extends SimpleContainer
    implements WorldlyContainer {
        private final BlockState f_52017_;
        private final LevelAccessor f_52018_;
        private final BlockPos f_52019_;
        private boolean f_52020_;

        public InputContainer(BlockState p_52022_, LevelAccessor p_52023_, BlockPos p_52024_) {
            super(1);
            this.f_52017_ = p_52022_;
            this.f_52018_ = p_52023_;
            this.f_52019_ = p_52024_;
        }

        @Override
        public int m_6893_() {
            return 1;
        }

        @Override
        public int[] m_7071_(Direction p_52032_) {
            int[] nArray;
            if (p_52032_ == Direction.UP) {
                int[] nArray2 = new int[1];
                nArray = nArray2;
                nArray2[0] = 0;
            } else {
                nArray = new int[]{};
            }
            return nArray;
        }

        @Override
        public boolean m_7155_(int p_52028_, ItemStack p_52029_, @Nullable Direction p_52030_) {
            return !this.f_52020_ && p_52030_ == Direction.UP && f_51914_.containsKey((Object)p_52029_.m_41720_());
        }

        @Override
        public boolean m_7157_(int p_52034_, ItemStack p_52035_, Direction p_52036_) {
            return false;
        }

        @Override
        public void m_6596_() {
            ItemStack $$0 = this.m_8020_(0);
            if (!$$0.m_41619_()) {
                this.f_52020_ = true;
                BlockState $$1 = ComposterBlock.m_51983_(this.f_52017_, this.f_52018_, this.f_52019_, $$0);
                this.f_52018_.m_46796_(1500, this.f_52019_, $$1 != this.f_52017_ ? 1 : 0);
                this.m_8016_(0);
            }
        }
    }

    static class EmptyContainer
    extends SimpleContainer
    implements WorldlyContainer {
        public EmptyContainer() {
            super(0);
        }

        @Override
        public int[] m_7071_(Direction p_52012_) {
            return new int[0];
        }

        @Override
        public boolean m_7155_(int p_52008_, ItemStack p_52009_, @Nullable Direction p_52010_) {
            return false;
        }

        @Override
        public boolean m_7157_(int p_52014_, ItemStack p_52015_, Direction p_52016_) {
            return false;
        }
    }
}

