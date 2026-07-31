/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.floats.Float2FloatFunction
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.AbstractChestBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ChestBlock
extends AbstractChestBlock<ChestBlockEntity>
implements SimpleWaterloggedBlock {
    public static final DirectionProperty f_51478_ = HorizontalDirectionalBlock.f_54117_;
    public static final EnumProperty<ChestType> f_51479_ = BlockStateProperties.f_61392_;
    public static final BooleanProperty f_51480_ = BlockStateProperties.f_61362_;
    public static final int f_153051_ = 1;
    protected static final int f_153052_ = 1;
    protected static final int f_153053_ = 14;
    protected static final VoxelShape f_51481_ = Block.m_49796_(1.0, 0.0, 0.0, 15.0, 14.0, 15.0);
    protected static final VoxelShape f_51482_ = Block.m_49796_(1.0, 0.0, 1.0, 15.0, 14.0, 16.0);
    protected static final VoxelShape f_51483_ = Block.m_49796_(0.0, 0.0, 1.0, 15.0, 14.0, 15.0);
    protected static final VoxelShape f_51484_ = Block.m_49796_(1.0, 0.0, 1.0, 16.0, 14.0, 15.0);
    protected static final VoxelShape f_51485_ = Block.m_49796_(1.0, 0.0, 1.0, 15.0, 14.0, 15.0);
    private static final DoubleBlockCombiner.Combiner<ChestBlockEntity, Optional<Container>> f_51486_ = new DoubleBlockCombiner.Combiner<ChestBlockEntity, Optional<Container>>(){

        @Override
        public Optional<Container> m_6959_(ChestBlockEntity p_51591_, ChestBlockEntity p_51592_) {
            return Optional.of(new CompoundContainer(p_51591_, p_51592_));
        }

        @Override
        public Optional<Container> m_7693_(ChestBlockEntity p_51589_) {
            return Optional.of(p_51589_);
        }

        @Override
        public Optional<Container> m_6502_() {
            return Optional.empty();
        }

        @Override
        public /* synthetic */ Object m_6502_() {
            return this.m_6502_();
        }
    };
    private static final DoubleBlockCombiner.Combiner<ChestBlockEntity, Optional<MenuProvider>> f_51487_ = new DoubleBlockCombiner.Combiner<ChestBlockEntity, Optional<MenuProvider>>(){

        @Override
        public Optional<MenuProvider> m_6959_(final ChestBlockEntity p_51604_, final ChestBlockEntity p_51605_) {
            final CompoundContainer $$2 = new CompoundContainer(p_51604_, p_51605_);
            return Optional.of(new MenuProvider(){

                @Override
                @Nullable
                public AbstractContainerMenu m_7208_(int p_51622_, Inventory p_51623_, Player p_51624_) {
                    if (p_51604_.m_7525_(p_51624_) && p_51605_.m_7525_(p_51624_)) {
                        p_51604_.m_59640_(p_51623_.f_35978_);
                        p_51605_.m_59640_(p_51623_.f_35978_);
                        return ChestMenu.m_39246_(p_51622_, p_51623_, $$2);
                    }
                    return null;
                }

                @Override
                public Component m_5446_() {
                    if (p_51604_.m_8077_()) {
                        return p_51604_.m_5446_();
                    }
                    if (p_51605_.m_8077_()) {
                        return p_51605_.m_5446_();
                    }
                    return Component.m_237115_("container.chestDouble");
                }
            });
        }

        @Override
        public Optional<MenuProvider> m_7693_(ChestBlockEntity p_51602_) {
            return Optional.of(p_51602_);
        }

        @Override
        public Optional<MenuProvider> m_6502_() {
            return Optional.empty();
        }

        @Override
        public /* synthetic */ Object m_6502_() {
            return this.m_6502_();
        }
    };

    protected ChestBlock(BlockBehaviour.Properties p_51490_, Supplier<BlockEntityType<? extends ChestBlockEntity>> p_51491_) {
        super(p_51490_, p_51491_);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_51478_, Direction.NORTH)).m_61124_(f_51479_, ChestType.SINGLE)).m_61124_(f_51480_, false));
    }

    public static DoubleBlockCombiner.BlockType m_51582_(BlockState p_51583_) {
        ChestType $$1 = p_51583_.m_61143_(f_51479_);
        if ($$1 == ChestType.SINGLE) {
            return DoubleBlockCombiner.BlockType.SINGLE;
        }
        if ($$1 == ChestType.RIGHT) {
            return DoubleBlockCombiner.BlockType.FIRST;
        }
        return DoubleBlockCombiner.BlockType.SECOND;
    }

    @Override
    public RenderShape m_7514_(BlockState p_51567_) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public BlockState m_7417_(BlockState p_51555_, Direction p_51556_, BlockState p_51557_, LevelAccessor p_51558_, BlockPos p_51559_, BlockPos p_51560_) {
        if (p_51555_.m_61143_(f_51480_).booleanValue()) {
            p_51558_.m_186469_(p_51559_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_51558_));
        }
        if (p_51557_.m_60713_(this) && p_51556_.m_122434_().m_122479_()) {
            ChestType $$6 = p_51557_.m_61143_(f_51479_);
            if (p_51555_.m_61143_(f_51479_) == ChestType.SINGLE && $$6 != ChestType.SINGLE && p_51555_.m_61143_(f_51478_) == p_51557_.m_61143_(f_51478_) && ChestBlock.m_51584_(p_51557_) == p_51556_.m_122424_()) {
                return (BlockState)p_51555_.m_61124_(f_51479_, $$6.m_61486_());
            }
        } else if (ChestBlock.m_51584_(p_51555_) == p_51556_) {
            return (BlockState)p_51555_.m_61124_(f_51479_, ChestType.SINGLE);
        }
        return super.m_7417_(p_51555_, p_51556_, p_51557_, p_51558_, p_51559_, p_51560_);
    }

    @Override
    public VoxelShape m_5940_(BlockState p_51569_, BlockGetter p_51570_, BlockPos p_51571_, CollisionContext p_51572_) {
        if (p_51569_.m_61143_(f_51479_) == ChestType.SINGLE) {
            return f_51485_;
        }
        switch (ChestBlock.m_51584_(p_51569_)) {
            default: {
                return f_51481_;
            }
            case SOUTH: {
                return f_51482_;
            }
            case WEST: {
                return f_51483_;
            }
            case EAST: 
        }
        return f_51484_;
    }

    public static Direction m_51584_(BlockState p_51585_) {
        Direction $$1 = p_51585_.m_61143_(f_51478_);
        return p_51585_.m_61143_(f_51479_) == ChestType.LEFT ? $$1.m_122427_() : $$1.m_122428_();
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_51493_) {
        Direction $$6;
        ChestType $$1 = ChestType.SINGLE;
        Direction $$2 = p_51493_.m_8125_().m_122424_();
        FluidState $$3 = p_51493_.m_43725_().m_6425_(p_51493_.m_8083_());
        boolean $$4 = p_51493_.m_7078_();
        Direction $$5 = p_51493_.m_43719_();
        if ($$5.m_122434_().m_122479_() && $$4 && ($$6 = this.m_51494_(p_51493_, $$5.m_122424_())) != null && $$6.m_122434_() != $$5.m_122434_()) {
            $$2 = $$6;
            ChestType chestType = $$1 = $$2.m_122428_() == $$5.m_122424_() ? ChestType.RIGHT : ChestType.LEFT;
        }
        if ($$1 == ChestType.SINGLE && !$$4) {
            if ($$2 == this.m_51494_(p_51493_, $$2.m_122427_())) {
                $$1 = ChestType.LEFT;
            } else if ($$2 == this.m_51494_(p_51493_, $$2.m_122428_())) {
                $$1 = ChestType.RIGHT;
            }
        }
        return (BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_(f_51478_, $$2)).m_61124_(f_51479_, $$1)).m_61124_(f_51480_, $$3.m_76152_() == Fluids.f_76193_);
    }

    @Override
    public FluidState m_5888_(BlockState p_51581_) {
        if (p_51581_.m_61143_(f_51480_).booleanValue()) {
            return Fluids.f_76193_.m_76068_(false);
        }
        return super.m_5888_(p_51581_);
    }

    @Nullable
    private Direction m_51494_(BlockPlaceContext p_51495_, Direction p_51496_) {
        BlockState $$2 = p_51495_.m_43725_().m_8055_(p_51495_.m_8083_().m_121945_(p_51496_));
        return $$2.m_60713_(this) && $$2.m_61143_(f_51479_) == ChestType.SINGLE ? $$2.m_61143_(f_51478_) : null;
    }

    @Override
    public void m_6402_(Level p_51503_, BlockPos p_51504_, BlockState p_51505_, LivingEntity p_51506_, ItemStack p_51507_) {
        BlockEntity $$5;
        if (p_51507_.m_41788_() && ($$5 = p_51503_.m_7702_(p_51504_)) instanceof ChestBlockEntity) {
            ((ChestBlockEntity)$$5).m_58638_(p_51507_.m_41786_());
        }
    }

    @Override
    public void m_6810_(BlockState p_51538_, Level p_51539_, BlockPos p_51540_, BlockState p_51541_, boolean p_51542_) {
        if (p_51538_.m_60713_(p_51541_.m_60734_())) {
            return;
        }
        BlockEntity $$5 = p_51539_.m_7702_(p_51540_);
        if ($$5 instanceof Container) {
            Containers.m_19002_(p_51539_, p_51540_, (Container)((Object)$$5));
            p_51539_.m_46717_(p_51540_, this);
        }
        super.m_6810_(p_51538_, p_51539_, p_51540_, p_51541_, p_51542_);
    }

    @Override
    public InteractionResult m_6227_(BlockState p_51531_, Level p_51532_, BlockPos p_51533_, Player p_51534_, InteractionHand p_51535_, BlockHitResult p_51536_) {
        if (p_51532_.f_46443_) {
            return InteractionResult.SUCCESS;
        }
        MenuProvider $$6 = this.m_7246_(p_51531_, p_51532_, p_51533_);
        if ($$6 != null) {
            p_51534_.m_5893_($$6);
            p_51534_.m_36246_(this.m_7699_());
            PiglinAi.m_34873_(p_51534_, true);
        }
        return InteractionResult.CONSUME;
    }

    protected Stat<ResourceLocation> m_7699_() {
        return Stats.f_12988_.m_12902_(Stats.f_12968_);
    }

    public BlockEntityType<? extends ChestBlockEntity> m_153066_() {
        return (BlockEntityType)this.f_48675_.get();
    }

    @Nullable
    public static Container m_51511_(ChestBlock p_51512_, BlockState p_51513_, Level p_51514_, BlockPos p_51515_, boolean p_51516_) {
        return p_51512_.m_5641_(p_51513_, p_51514_, p_51515_, p_51516_).m_5649_(f_51486_).orElse(null);
    }

    @Override
    public DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> m_5641_(BlockState p_51544_, Level p_51545_, BlockPos p_51546_, boolean p_51547_) {
        BiPredicate<LevelAccessor, BlockPos> $$5;
        if (p_51547_) {
            BiPredicate<LevelAccessor, BlockPos> $$4 = (p_51578_, p_51579_) -> false;
        } else {
            $$5 = ChestBlock::m_51508_;
        }
        return DoubleBlockCombiner.m_52822_((BlockEntityType)this.f_48675_.get(), ChestBlock::m_51582_, ChestBlock::m_51584_, f_51478_, p_51544_, p_51545_, p_51546_, $$5);
    }

    @Override
    @Nullable
    public MenuProvider m_7246_(BlockState p_51574_, Level p_51575_, BlockPos p_51576_) {
        return this.m_5641_(p_51574_, p_51575_, p_51576_, false).m_5649_(f_51487_).orElse(null);
    }

    public static DoubleBlockCombiner.Combiner<ChestBlockEntity, Float2FloatFunction> m_51517_(final LidBlockEntity p_51518_) {
        return new DoubleBlockCombiner.Combiner<ChestBlockEntity, Float2FloatFunction>(){

            @Override
            public Float2FloatFunction m_6959_(ChestBlockEntity p_51633_, ChestBlockEntity p_51634_) {
                return p_51638_ -> Math.max(p_51633_.m_6683_(p_51638_), p_51634_.m_6683_(p_51638_));
            }

            @Override
            public Float2FloatFunction m_7693_(ChestBlockEntity p_51631_) {
                return p_51631_::m_6683_;
            }

            @Override
            public Float2FloatFunction m_6502_() {
                return p_51518_::m_6683_;
            }

            @Override
            public /* synthetic */ Object m_6502_() {
                return this.m_6502_();
            }
        };
    }

    @Override
    public BlockEntity m_142194_(BlockPos p_153064_, BlockState p_153065_) {
        return new ChestBlockEntity(p_153064_, p_153065_);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_153055_, BlockState p_153056_, BlockEntityType<T> p_153057_) {
        return p_153055_.f_46443_ ? ChestBlock.m_152132_(p_153057_, this.m_153066_(), ChestBlockEntity::m_155343_) : null;
    }

    public static boolean m_51508_(LevelAccessor p_51509_, BlockPos p_51510_) {
        return ChestBlock.m_51499_(p_51509_, p_51510_) || ChestBlock.m_51563_(p_51509_, p_51510_);
    }

    private static boolean m_51499_(BlockGetter p_51500_, BlockPos p_51501_) {
        BlockPos $$2 = p_51501_.m_7494_();
        return p_51500_.m_8055_($$2).m_60796_(p_51500_, $$2);
    }

    private static boolean m_51563_(LevelAccessor p_51564_, BlockPos p_51565_) {
        List<Cat> $$2 = p_51564_.m_45976_(Cat.class, new AABB(p_51565_.m_123341_(), p_51565_.m_123342_() + 1, p_51565_.m_123343_(), p_51565_.m_123341_() + 1, p_51565_.m_123342_() + 2, p_51565_.m_123343_() + 1));
        if (!$$2.isEmpty()) {
            for (Cat $$3 : $$2) {
                if (!$$3.m_21825_()) continue;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean m_7278_(BlockState p_51520_) {
        return true;
    }

    @Override
    public int m_6782_(BlockState p_51527_, Level p_51528_, BlockPos p_51529_) {
        return AbstractContainerMenu.m_38938_(ChestBlock.m_51511_(this, p_51527_, p_51528_, p_51529_, false));
    }

    @Override
    public BlockState m_6843_(BlockState p_51552_, Rotation p_51553_) {
        return (BlockState)p_51552_.m_61124_(f_51478_, p_51553_.m_55954_(p_51552_.m_61143_(f_51478_)));
    }

    @Override
    public BlockState m_6943_(BlockState p_51549_, Mirror p_51550_) {
        return p_51549_.m_60717_(p_51550_.m_54846_(p_51549_.m_61143_(f_51478_)));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_51562_) {
        p_51562_.m_61104_(f_51478_, f_51479_, f_51480_);
    }

    @Override
    public boolean m_7357_(BlockState p_51522_, BlockGetter p_51523_, BlockPos p_51524_, PathComputationType p_51525_) {
        return false;
    }

    @Override
    public void m_213897_(BlockState p_220958_, ServerLevel p_220959_, BlockPos p_220960_, RandomSource p_220961_) {
        BlockEntity $$4 = p_220959_.m_7702_(p_220960_);
        if ($$4 instanceof ChestBlockEntity) {
            ((ChestBlockEntity)$$4).m_155350_();
        }
    }
}

