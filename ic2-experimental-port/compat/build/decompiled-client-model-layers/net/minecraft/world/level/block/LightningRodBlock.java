/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RodBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class LightningRodBlock
extends RodBlock
implements SimpleWaterloggedBlock {
    public static final BooleanProperty f_153702_ = BlockStateProperties.f_61362_;
    public static final BooleanProperty f_153703_ = BlockStateProperties.f_61448_;
    private static final int f_153705_ = 8;
    public static final int f_153704_ = 128;
    private static final int f_153706_ = 200;

    public LightningRodBlock(BlockBehaviour.Properties p_153709_) {
        super(p_153709_);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_52588_, Direction.UP)).m_61124_(f_153702_, false)).m_61124_(f_153703_, false));
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_153711_) {
        FluidState $$1 = p_153711_.m_43725_().m_6425_(p_153711_.m_8083_());
        boolean $$2 = $$1.m_76152_() == Fluids.f_76193_;
        return (BlockState)((BlockState)this.m_49966_().m_61124_(f_52588_, p_153711_.m_43719_())).m_61124_(f_153702_, $$2);
    }

    @Override
    public BlockState m_7417_(BlockState p_153739_, Direction p_153740_, BlockState p_153741_, LevelAccessor p_153742_, BlockPos p_153743_, BlockPos p_153744_) {
        if (p_153739_.m_61143_(f_153702_).booleanValue()) {
            p_153742_.m_186469_(p_153743_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_153742_));
        }
        return super.m_7417_(p_153739_, p_153740_, p_153741_, p_153742_, p_153743_, p_153744_);
    }

    @Override
    public FluidState m_5888_(BlockState p_153759_) {
        if (p_153759_.m_61143_(f_153702_).booleanValue()) {
            return Fluids.f_76193_.m_76068_(false);
        }
        return super.m_5888_(p_153759_);
    }

    @Override
    public int m_6378_(BlockState p_153723_, BlockGetter p_153724_, BlockPos p_153725_, Direction p_153726_) {
        return p_153723_.m_61143_(f_153703_) != false ? 15 : 0;
    }

    @Override
    public int m_6376_(BlockState p_153748_, BlockGetter p_153749_, BlockPos p_153750_, Direction p_153751_) {
        if (p_153748_.m_61143_(f_153703_).booleanValue() && p_153748_.m_61143_(f_52588_) == p_153751_) {
            return 15;
        }
        return 0;
    }

    public void m_153760_(BlockState p_153761_, Level p_153762_, BlockPos p_153763_) {
        p_153762_.m_7731_(p_153763_, (BlockState)p_153761_.m_61124_(f_153703_, true), 3);
        this.m_153764_(p_153761_, p_153762_, p_153763_);
        p_153762_.m_186460_(p_153763_, this, 8);
        p_153762_.m_46796_(3002, p_153763_, p_153761_.m_61143_(f_52588_).m_122434_().ordinal());
    }

    private void m_153764_(BlockState p_153765_, Level p_153766_, BlockPos p_153767_) {
        p_153766_.m_46672_(p_153767_.m_121945_(p_153765_.m_61143_(f_52588_).m_122424_()), this);
    }

    @Override
    public void m_213897_(BlockState p_221400_, ServerLevel p_221401_, BlockPos p_221402_, RandomSource p_221403_) {
        p_221401_.m_7731_(p_221402_, (BlockState)p_221400_.m_61124_(f_153703_, false), 3);
        this.m_153764_(p_221400_, p_221401_, p_221402_);
    }

    @Override
    public void m_214162_(BlockState p_221405_, Level p_221406_, BlockPos p_221407_, RandomSource p_221408_) {
        if (!p_221406_.m_46470_() || (long)p_221406_.f_46441_.m_188503_(200) > p_221406_.m_46467_() % 200L || p_221407_.m_123342_() != p_221406_.m_6924_(Heightmap.Types.WORLD_SURFACE, p_221407_.m_123341_(), p_221407_.m_123343_()) - 1) {
            return;
        }
        ParticleUtils.m_144967_(p_221405_.m_61143_(f_52588_).m_122434_(), p_221406_, p_221407_, 0.125, ParticleTypes.f_175830_, UniformInt.m_146622_(1, 2));
    }

    @Override
    public void m_6810_(BlockState p_153728_, Level p_153729_, BlockPos p_153730_, BlockState p_153731_, boolean p_153732_) {
        if (p_153728_.m_60713_(p_153731_.m_60734_())) {
            return;
        }
        if (p_153728_.m_61143_(f_153703_).booleanValue()) {
            this.m_153764_(p_153728_, p_153729_, p_153730_);
        }
        super.m_6810_(p_153728_, p_153729_, p_153730_, p_153731_, p_153732_);
    }

    @Override
    public void m_6807_(BlockState p_153753_, Level p_153754_, BlockPos p_153755_, BlockState p_153756_, boolean p_153757_) {
        if (p_153753_.m_60713_(p_153756_.m_60734_())) {
            return;
        }
        if (p_153753_.m_61143_(f_153703_).booleanValue() && !p_153754_.m_183326_().m_183582_(p_153755_, this)) {
            p_153754_.m_7731_(p_153755_, (BlockState)p_153753_.m_61124_(f_153703_, false), 18);
        }
    }

    @Override
    public void m_5581_(Level p_153713_, BlockState p_153714_, BlockHitResult p_153715_, Projectile p_153716_) {
        BlockPos $$4;
        if (p_153713_.m_46470_() && p_153716_ instanceof ThrownTrident && ((ThrownTrident)p_153716_).m_150194_() && p_153713_.m_45527_($$4 = p_153715_.m_82425_())) {
            LightningBolt $$5 = EntityType.f_20465_.m_20615_(p_153713_);
            $$5.m_20219_(Vec3.m_82539_($$4.m_7494_()));
            Entity $$6 = p_153716_.m_37282_();
            $$5.m_20879_($$6 instanceof ServerPlayer ? (ServerPlayer)$$6 : null);
            p_153713_.m_7967_($$5);
            p_153713_.m_5594_(null, $$4, SoundEvents.f_12521_, SoundSource.WEATHER, 5.0f, 1.0f);
        }
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_153746_) {
        p_153746_.m_61104_(f_52588_, f_153703_, f_153702_);
    }

    @Override
    public boolean m_7899_(BlockState p_153769_) {
        return true;
    }
}

