/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMaps
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.SculkSensorPhase;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SculkSensorBlock
extends BaseEntityBlock
implements SimpleWaterloggedBlock {
    public static final int f_154383_ = 40;
    public static final int f_154384_ = 1;
    public static final Object2IntMap<GameEvent> f_222121_ = Object2IntMaps.unmodifiable((Object2IntMap)((Object2IntMap)Util.m_137469_(new Object2IntOpenHashMap(), p_238254_ -> {
        p_238254_.put((Object)GameEvent.f_157785_, 1);
        p_238254_.put((Object)GameEvent.f_157815_, 2);
        p_238254_.put((Object)GameEvent.f_157786_, 3);
        p_238254_.put((Object)GameEvent.f_223705_, 4);
        p_238254_.put((Object)GameEvent.f_157770_, 5);
        p_238254_.put((Object)GameEvent.f_238175_, 5);
        p_238254_.put((Object)GameEvent.f_157784_, 6);
        p_238254_.put((Object)GameEvent.f_223710_, 6);
        p_238254_.put((Object)GameEvent.f_157792_, 6);
        p_238254_.put((Object)GameEvent.f_223699_, 6);
        p_238254_.put((Object)GameEvent.f_157778_, 7);
        p_238254_.put((Object)GameEvent.f_223704_, 7);
        p_238254_.put((Object)GameEvent.f_157776_, 7);
        p_238254_.put((Object)GameEvent.f_157777_, 8);
        p_238254_.put((Object)GameEvent.f_157806_, 8);
        p_238254_.put((Object)GameEvent.f_223708_, 8);
        p_238254_.put((Object)GameEvent.f_223706_, 8);
        p_238254_.put((Object)GameEvent.f_157811_, 9);
        p_238254_.put((Object)GameEvent.f_157781_, 9);
        p_238254_.put((Object)GameEvent.f_223709_, 9);
        p_238254_.put((Object)GameEvent.f_157793_, 10);
        p_238254_.put((Object)GameEvent.f_223703_, 10);
        p_238254_.put((Object)GameEvent.f_157795_, 10);
        p_238254_.put((Object)GameEvent.f_157804_, 10);
        p_238254_.put((Object)GameEvent.f_157796_, 11);
        p_238254_.put((Object)GameEvent.f_223702_, 11);
        p_238254_.put((Object)GameEvent.f_157791_, 11);
        p_238254_.put((Object)GameEvent.f_157810_, 12);
        p_238254_.put((Object)GameEvent.f_157797_, 12);
        p_238254_.put((Object)GameEvent.f_157769_, 12);
        p_238254_.put((Object)GameEvent.f_223707_, 13);
        p_238254_.put((Object)GameEvent.f_157794_, 13);
        p_238254_.put((Object)GameEvent.f_157816_, 13);
        p_238254_.put((Object)GameEvent.f_223697_, 14);
        p_238254_.put((Object)GameEvent.f_157802_, 14);
        p_238254_.put((Object)GameEvent.f_157774_, 14);
        p_238254_.put((Object)GameEvent.f_157775_, 15);
        p_238254_.put((Object)GameEvent.f_157803_, 15);
        p_238254_.put((Object)GameEvent.f_223698_, 15);
        p_238254_.put((Object)GameEvent.f_157812_, 15);
        p_238254_.put((Object)GameEvent.f_157772_, 15);
        p_238254_.put((Object)GameEvent.f_223696_, 15);
    })));
    public static final EnumProperty<SculkSensorPhase> f_154386_ = BlockStateProperties.f_155999_;
    public static final IntegerProperty f_154387_ = BlockStateProperties.f_61426_;
    public static final BooleanProperty f_154388_ = BlockStateProperties.f_61362_;
    protected static final VoxelShape f_154389_ = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
    private final int f_154390_;

    public SculkSensorBlock(BlockBehaviour.Properties p_154393_, int p_154394_) {
        super(p_154393_);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_154386_, SculkSensorPhase.INACTIVE)).m_61124_(f_154387_, 0)).m_61124_(f_154388_, false));
        this.f_154390_ = p_154394_;
    }

    public int m_154482_() {
        return this.f_154390_;
    }

    @Override
    @Nullable
    public BlockState m_5573_(BlockPlaceContext p_154396_) {
        BlockPos $$1 = p_154396_.m_8083_();
        FluidState $$2 = p_154396_.m_43725_().m_6425_($$1);
        return (BlockState)this.m_49966_().m_61124_(f_154388_, $$2.m_76152_() == Fluids.f_76193_);
    }

    @Override
    public FluidState m_5888_(BlockState p_154479_) {
        if (p_154479_.m_61143_(f_154388_).booleanValue()) {
            return Fluids.f_76193_.m_76068_(false);
        }
        return super.m_5888_(p_154479_);
    }

    @Override
    public void m_213897_(BlockState p_222137_, ServerLevel p_222138_, BlockPos p_222139_, RandomSource p_222140_) {
        if (SculkSensorBlock.m_154487_(p_222137_) != SculkSensorPhase.ACTIVE) {
            if (SculkSensorBlock.m_154487_(p_222137_) == SculkSensorPhase.COOLDOWN) {
                p_222138_.m_7731_(p_222139_, (BlockState)p_222137_.m_61124_(f_154386_, SculkSensorPhase.INACTIVE), 3);
            }
            return;
        }
        SculkSensorBlock.m_154407_(p_222138_, p_222139_, p_222137_);
    }

    @Override
    public void m_141947_(Level p_222132_, BlockPos p_222133_, BlockState p_222134_, Entity p_222135_) {
        if (!p_222132_.m_5776_() && SculkSensorBlock.m_154489_(p_222134_) && p_222135_.m_6095_() != EntityType.f_217015_) {
            BlockEntity $$4 = p_222132_.m_7702_(p_222133_);
            if ($$4 instanceof SculkSensorBlockEntity) {
                SculkSensorBlockEntity $$5 = (SculkSensorBlockEntity)$$4;
                $$5.m_222800_(f_222121_.get((Object)GameEvent.f_157785_));
            }
            SculkSensorBlock.m_222125_(p_222135_, p_222132_, p_222133_, p_222134_, 15);
        }
        super.m_141947_(p_222132_, p_222133_, p_222134_, p_222135_);
    }

    @Override
    public void m_6807_(BlockState p_154471_, Level p_154472_, BlockPos p_154473_, BlockState p_154474_, boolean p_154475_) {
        if (p_154472_.m_5776_() || p_154471_.m_60713_(p_154474_.m_60734_())) {
            return;
        }
        if (p_154471_.m_61143_(f_154387_) > 0 && !p_154472_.m_183326_().m_183582_(p_154473_, this)) {
            p_154472_.m_7731_(p_154473_, (BlockState)p_154471_.m_61124_(f_154387_, 0), 18);
        }
        p_154472_.m_186460_(new BlockPos(p_154473_), p_154471_.m_60734_(), 1);
    }

    @Override
    public void m_6810_(BlockState p_154446_, Level p_154447_, BlockPos p_154448_, BlockState p_154449_, boolean p_154450_) {
        if (p_154446_.m_60713_(p_154449_.m_60734_())) {
            return;
        }
        if (SculkSensorBlock.m_154487_(p_154446_) == SculkSensorPhase.ACTIVE) {
            SculkSensorBlock.m_154404_(p_154447_, p_154448_);
        }
        super.m_6810_(p_154446_, p_154447_, p_154448_, p_154449_, p_154450_);
    }

    @Override
    public BlockState m_7417_(BlockState p_154457_, Direction p_154458_, BlockState p_154459_, LevelAccessor p_154460_, BlockPos p_154461_, BlockPos p_154462_) {
        if (p_154457_.m_61143_(f_154388_).booleanValue()) {
            p_154460_.m_186469_(p_154461_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_154460_));
        }
        return super.m_7417_(p_154457_, p_154458_, p_154459_, p_154460_, p_154461_, p_154462_);
    }

    private static void m_154404_(Level p_154405_, BlockPos p_154406_) {
        p_154405_.m_46672_(p_154406_, Blocks.f_152500_);
        p_154405_.m_46672_(p_154406_.m_121945_(Direction.UP.m_122424_()), Blocks.f_152500_);
    }

    @Override
    @Nullable
    public BlockEntity m_142194_(BlockPos p_154466_, BlockState p_154467_) {
        return new SculkSensorBlockEntity(p_154466_, p_154467_);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> GameEventListener m_214009_(ServerLevel p_222123_, T p_222124_) {
        if (p_222124_ instanceof SculkSensorBlockEntity) {
            return ((SculkSensorBlockEntity)p_222124_).m_155655_();
        }
        return null;
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_154401_, BlockState p_154402_, BlockEntityType<T> p_154403_) {
        if (!p_154401_.f_46443_) {
            return SculkSensorBlock.m_152132_(p_154403_, BlockEntityType.f_155257_, (p_154417_, p_154418_, p_154419_, p_154420_) -> p_154420_.m_155655_().m_157898_(p_154417_));
        }
        return null;
    }

    @Override
    public RenderShape m_7514_(BlockState p_154477_) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape m_5940_(BlockState p_154432_, BlockGetter p_154433_, BlockPos p_154434_, CollisionContext p_154435_) {
        return f_154389_;
    }

    @Override
    public boolean m_7899_(BlockState p_154484_) {
        return true;
    }

    @Override
    public int m_6378_(BlockState p_154437_, BlockGetter p_154438_, BlockPos p_154439_, Direction p_154440_) {
        return p_154437_.m_61143_(f_154387_);
    }

    public static SculkSensorPhase m_154487_(BlockState p_154488_) {
        return p_154488_.m_61143_(f_154386_);
    }

    public static boolean m_154489_(BlockState p_154490_) {
        return SculkSensorBlock.m_154487_(p_154490_) == SculkSensorPhase.INACTIVE;
    }

    public static void m_154407_(Level p_154408_, BlockPos p_154409_, BlockState p_154410_) {
        p_154408_.m_7731_(p_154409_, (BlockState)((BlockState)p_154410_.m_61124_(f_154386_, SculkSensorPhase.COOLDOWN)).m_61124_(f_154387_, 0), 3);
        p_154408_.m_186460_(p_154409_, p_154410_.m_60734_(), 1);
        if (!p_154410_.m_61143_(f_154388_).booleanValue()) {
            p_154408_.m_5594_(null, p_154409_, SoundEvents.f_144213_, SoundSource.BLOCKS, 1.0f, p_154408_.f_46441_.m_188501_() * 0.2f + 0.8f);
        }
        SculkSensorBlock.m_154404_(p_154408_, p_154409_);
    }

    public static void m_222125_(@Nullable Entity p_222126_, Level p_222127_, BlockPos p_222128_, BlockState p_222129_, int p_222130_) {
        p_222127_.m_7731_(p_222128_, (BlockState)((BlockState)p_222129_.m_61124_(f_154386_, SculkSensorPhase.ACTIVE)).m_61124_(f_154387_, p_222130_), 3);
        p_222127_.m_186460_(p_222128_, p_222129_.m_60734_(), 40);
        SculkSensorBlock.m_154404_(p_222127_, p_222128_);
        p_222127_.m_142346_(p_222126_, GameEvent.f_223700_, p_222128_);
        if (!p_222129_.m_61143_(f_154388_).booleanValue()) {
            p_222127_.m_6263_(null, (double)p_222128_.m_123341_() + 0.5, (double)p_222128_.m_123342_() + 0.5, (double)p_222128_.m_123343_() + 0.5, SoundEvents.f_144212_, SoundSource.BLOCKS, 1.0f, p_222127_.f_46441_.m_188501_() * 0.2f + 0.8f);
        }
    }

    @Override
    public void m_214162_(BlockState p_222148_, Level p_222149_, BlockPos p_222150_, RandomSource p_222151_) {
        if (SculkSensorBlock.m_154487_(p_222148_) != SculkSensorPhase.ACTIVE) {
            return;
        }
        Direction $$4 = Direction.m_235672_(p_222151_);
        if ($$4 == Direction.UP || $$4 == Direction.DOWN) {
            return;
        }
        double $$5 = (double)p_222150_.m_123341_() + 0.5 + ($$4.m_122429_() == 0 ? 0.5 - p_222151_.m_188500_() : (double)$$4.m_122429_() * 0.6);
        double $$6 = (double)p_222150_.m_123342_() + 0.25;
        double $$7 = (double)p_222150_.m_123343_() + 0.5 + ($$4.m_122431_() == 0 ? 0.5 - p_222151_.m_188500_() : (double)$$4.m_122431_() * 0.6);
        double $$8 = (double)p_222151_.m_188501_() * 0.04;
        p_222149_.m_7106_(DustColorTransitionOptions.f_175752_, $$5, $$6, $$7, 0.0, $$8, 0.0);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_154464_) {
        p_154464_.m_61104_(f_154386_, f_154387_, f_154388_);
    }

    @Override
    public boolean m_7278_(BlockState p_154481_) {
        return true;
    }

    @Override
    public int m_6782_(BlockState p_154442_, Level p_154443_, BlockPos p_154444_) {
        BlockEntity $$3 = p_154443_.m_7702_(p_154444_);
        if ($$3 instanceof SculkSensorBlockEntity) {
            SculkSensorBlockEntity $$4 = (SculkSensorBlockEntity)$$3;
            return SculkSensorBlock.m_154487_(p_154442_) == SculkSensorPhase.ACTIVE ? $$4.m_155656_() : 0;
        }
        return 0;
    }

    @Override
    public boolean m_7357_(BlockState p_154427_, BlockGetter p_154428_, BlockPos p_154429_, PathComputationType p_154430_) {
        return false;
    }

    @Override
    public boolean m_7923_(BlockState p_154486_) {
        return true;
    }

    @Override
    public void m_213646_(BlockState p_222142_, ServerLevel p_222143_, BlockPos p_222144_, ItemStack p_222145_, boolean p_222146_) {
        super.m_213646_(p_222142_, p_222143_, p_222144_, p_222145_, p_222146_);
        if (p_222146_) {
            this.m_220822_(p_222143_, p_222144_, p_222145_, ConstantInt.m_146483_(5));
        }
    }
}

