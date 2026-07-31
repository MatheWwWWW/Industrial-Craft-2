/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block.entity;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ConduitBlockEntity
extends BlockEntity {
    private static final int f_155390_ = 2;
    private static final int f_155391_ = 13;
    private static final float f_155392_ = -0.0375f;
    private static final int f_155393_ = 16;
    private static final int f_155394_ = 42;
    private static final int f_155395_ = 8;
    private static final Block[] f_59184_ = new Block[]{Blocks.f_50377_, Blocks.f_50378_, Blocks.f_50386_, Blocks.f_50379_};
    public int f_59183_;
    private float f_59185_;
    private boolean f_59186_;
    private boolean f_59187_;
    private final List<BlockPos> f_59188_ = Lists.newArrayList();
    @Nullable
    private LivingEntity f_59189_;
    @Nullable
    private UUID f_59190_;
    private long f_59191_;

    public ConduitBlockEntity(BlockPos p_155397_, BlockState p_155398_) {
        super(BlockEntityType.f_58941_, p_155397_, p_155398_);
    }

    @Override
    public void m_142466_(CompoundTag p_155437_) {
        super.m_142466_(p_155437_);
        this.f_59190_ = p_155437_.m_128403_("Target") ? p_155437_.m_128342_("Target") : null;
    }

    @Override
    protected void m_183515_(CompoundTag p_187495_) {
        super.m_183515_(p_187495_);
        if (this.f_59189_ != null) {
            p_187495_.m_128362_("Target", this.f_59189_.m_20148_());
        }
    }

    public ClientboundBlockEntityDataPacket m_58483_() {
        return ClientboundBlockEntityDataPacket.m_195640_(this);
    }

    @Override
    public CompoundTag m_5995_() {
        return this.m_187482_();
    }

    public static void m_155403_(Level p_155404_, BlockPos p_155405_, BlockState p_155406_, ConduitBlockEntity p_155407_) {
        ++p_155407_.f_59183_;
        long $$4 = p_155404_.m_46467_();
        List<BlockPos> $$5 = p_155407_.f_59188_;
        if ($$4 % 40L == 0L) {
            p_155407_.f_59186_ = ConduitBlockEntity.m_155414_(p_155404_, p_155405_, $$5);
            ConduitBlockEntity.m_155428_(p_155407_, $$5);
        }
        ConduitBlockEntity.m_155399_(p_155404_, p_155405_, p_155407_);
        ConduitBlockEntity.m_155418_(p_155404_, p_155405_, $$5, p_155407_.f_59189_, p_155407_.f_59183_);
        if (p_155407_.m_59216_()) {
            p_155407_.f_59185_ += 1.0f;
        }
    }

    public static void m_155438_(Level p_155439_, BlockPos p_155440_, BlockState p_155441_, ConduitBlockEntity p_155442_) {
        ++p_155442_.f_59183_;
        long $$4 = p_155439_.m_46467_();
        List<BlockPos> $$5 = p_155442_.f_59188_;
        if ($$4 % 40L == 0L) {
            boolean $$6 = ConduitBlockEntity.m_155414_(p_155439_, p_155440_, $$5);
            if ($$6 != p_155442_.f_59186_) {
                SoundEvent $$7 = $$6 ? SoundEvents.f_11767_ : SoundEvents.f_11824_;
                p_155439_.m_5594_(null, p_155440_, $$7, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
            p_155442_.f_59186_ = $$6;
            ConduitBlockEntity.m_155428_(p_155442_, $$5);
            if ($$6) {
                ConduitBlockEntity.m_155443_(p_155439_, p_155440_, $$5);
                ConduitBlockEntity.m_155408_(p_155439_, p_155440_, p_155441_, $$5, p_155442_);
            }
        }
        if (p_155442_.m_59216_()) {
            if ($$4 % 80L == 0L) {
                p_155439_.m_5594_(null, p_155440_, SoundEvents.f_11768_, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
            if ($$4 > p_155442_.f_59191_) {
                p_155442_.f_59191_ = $$4 + 60L + (long)p_155439_.m_213780_().m_188503_(40);
                p_155439_.m_5594_(null, p_155440_, SoundEvents.f_11822_, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
        }
    }

    private static void m_155428_(ConduitBlockEntity p_155429_, List<BlockPos> p_155430_) {
        p_155429_.m_59214_(p_155430_.size() >= 42);
    }

    private static boolean m_155414_(Level p_155415_, BlockPos p_155416_, List<BlockPos> p_155417_) {
        p_155417_.clear();
        for (int $$3 = -1; $$3 <= 1; ++$$3) {
            for (int $$4 = -1; $$4 <= 1; ++$$4) {
                for (int $$5 = -1; $$5 <= 1; ++$$5) {
                    BlockPos $$6 = p_155416_.m_7918_($$3, $$4, $$5);
                    if (p_155415_.m_46801_($$6)) continue;
                    return false;
                }
            }
        }
        for (int $$7 = -2; $$7 <= 2; ++$$7) {
            for (int $$8 = -2; $$8 <= 2; ++$$8) {
                for (int $$9 = -2; $$9 <= 2; ++$$9) {
                    int $$10 = Math.abs($$7);
                    int $$11 = Math.abs($$8);
                    int $$12 = Math.abs($$9);
                    if ($$10 <= 1 && $$11 <= 1 && $$12 <= 1 || ($$7 != 0 || $$11 != 2 && $$12 != 2) && ($$8 != 0 || $$10 != 2 && $$12 != 2) && ($$9 != 0 || $$10 != 2 && $$11 != 2)) continue;
                    BlockPos $$13 = p_155416_.m_7918_($$7, $$8, $$9);
                    BlockState $$14 = p_155415_.m_8055_($$13);
                    for (Block $$15 : f_59184_) {
                        if (!$$14.m_60713_($$15)) continue;
                        p_155417_.add($$13);
                    }
                }
            }
        }
        return p_155417_.size() >= 16;
    }

    private static void m_155443_(Level p_155444_, BlockPos p_155445_, List<BlockPos> p_155446_) {
        int $$7;
        int $$6;
        int $$3 = p_155446_.size();
        int $$4 = $$3 / 7 * 16;
        int $$5 = p_155445_.m_123341_();
        AABB $$8 = new AABB($$5, $$6 = p_155445_.m_123342_(), $$7 = p_155445_.m_123343_(), $$5 + 1, $$6 + 1, $$7 + 1).m_82400_($$4).m_82363_(0.0, p_155444_.m_141928_(), 0.0);
        List<Player> $$9 = p_155444_.m_45976_(Player.class, $$8);
        if ($$9.isEmpty()) {
            return;
        }
        for (Player $$10 : $$9) {
            if (!p_155445_.m_123314_($$10.m_20183_(), $$4) || !$$10.m_20070_()) continue;
            $$10.m_7292_(new MobEffectInstance(MobEffects.f_19592_, 260, 0, true, true));
        }
    }

    private static void m_155408_(Level p_155409_, BlockPos p_155410_, BlockState p_155411_, List<BlockPos> p_155412_, ConduitBlockEntity p_155413_) {
        LivingEntity $$5 = p_155413_.f_59189_;
        int $$6 = p_155412_.size();
        if ($$6 < 42) {
            p_155413_.f_59189_ = null;
        } else if (p_155413_.f_59189_ == null && p_155413_.f_59190_ != null) {
            p_155413_.f_59189_ = ConduitBlockEntity.m_155424_(p_155409_, p_155410_, p_155413_.f_59190_);
            p_155413_.f_59190_ = null;
        } else if (p_155413_.f_59189_ == null) {
            List<LivingEntity> $$7 = p_155409_.m_6443_(LivingEntity.class, ConduitBlockEntity.m_155431_(p_155410_), p_59213_ -> p_59213_ instanceof Enemy && p_59213_.m_20070_());
            if (!$$7.isEmpty()) {
                p_155413_.f_59189_ = $$7.get(p_155409_.f_46441_.m_188503_($$7.size()));
            }
        } else if (!p_155413_.f_59189_.m_6084_() || !p_155410_.m_123314_(p_155413_.f_59189_.m_20183_(), 8.0)) {
            p_155413_.f_59189_ = null;
        }
        if (p_155413_.f_59189_ != null) {
            p_155409_.m_6263_(null, p_155413_.f_59189_.m_20185_(), p_155413_.f_59189_.m_20186_(), p_155413_.f_59189_.m_20189_(), SoundEvents.f_11823_, SoundSource.BLOCKS, 1.0f, 1.0f);
            p_155413_.f_59189_.m_6469_(DamageSource.f_19319_, 4.0f);
        }
        if ($$5 != p_155413_.f_59189_) {
            p_155409_.m_7260_(p_155410_, p_155411_, p_155411_, 2);
        }
    }

    private static void m_155399_(Level p_155400_, BlockPos p_155401_, ConduitBlockEntity p_155402_) {
        if (p_155402_.f_59190_ == null) {
            p_155402_.f_59189_ = null;
        } else if (p_155402_.f_59189_ == null || !p_155402_.f_59189_.m_20148_().equals(p_155402_.f_59190_)) {
            p_155402_.f_59189_ = ConduitBlockEntity.m_155424_(p_155400_, p_155401_, p_155402_.f_59190_);
            if (p_155402_.f_59189_ == null) {
                p_155402_.f_59190_ = null;
            }
        }
    }

    private static AABB m_155431_(BlockPos p_155432_) {
        int $$1 = p_155432_.m_123341_();
        int $$2 = p_155432_.m_123342_();
        int $$3 = p_155432_.m_123343_();
        return new AABB($$1, $$2, $$3, $$1 + 1, $$2 + 1, $$3 + 1).m_82400_(8.0);
    }

    @Nullable
    private static LivingEntity m_155424_(Level p_155425_, BlockPos p_155426_, UUID p_155427_) {
        List<LivingEntity> $$3 = p_155425_.m_6443_(LivingEntity.class, ConduitBlockEntity.m_155431_(p_155426_), p_155435_ -> p_155435_.m_20148_().equals(p_155427_));
        if ($$3.size() == 1) {
            return $$3.get(0);
        }
        return null;
    }

    private static void m_155418_(Level p_155419_, BlockPos p_155420_, List<BlockPos> p_155421_, @Nullable Entity p_155422_, int p_155423_) {
        RandomSource $$5 = p_155419_.f_46441_;
        double $$6 = Mth.m_14031_((float)(p_155423_ + 35) * 0.1f) / 2.0f + 0.5f;
        $$6 = ($$6 * $$6 + $$6) * (double)0.3f;
        Vec3 $$7 = new Vec3((double)p_155420_.m_123341_() + 0.5, (double)p_155420_.m_123342_() + 1.5 + $$6, (double)p_155420_.m_123343_() + 0.5);
        for (BlockPos $$8 : p_155421_) {
            if ($$5.m_188503_(50) != 0) continue;
            BlockPos $$9 = $$8.m_121996_(p_155420_);
            float $$10 = -0.5f + $$5.m_188501_() + (float)$$9.m_123341_();
            float $$11 = -2.0f + $$5.m_188501_() + (float)$$9.m_123342_();
            float $$12 = -0.5f + $$5.m_188501_() + (float)$$9.m_123343_();
            p_155419_.m_7106_(ParticleTypes.f_123775_, $$7.f_82479_, $$7.f_82480_, $$7.f_82481_, $$10, $$11, $$12);
        }
        if (p_155422_ != null) {
            Vec3 $$13 = new Vec3(p_155422_.m_20185_(), p_155422_.m_20188_(), p_155422_.m_20189_());
            float $$14 = (-0.5f + $$5.m_188501_()) * (3.0f + p_155422_.m_20205_());
            float $$15 = -1.0f + $$5.m_188501_() * p_155422_.m_20206_();
            float $$16 = (-0.5f + $$5.m_188501_()) * (3.0f + p_155422_.m_20205_());
            Vec3 $$17 = new Vec3($$14, $$15, $$16);
            p_155419_.m_7106_(ParticleTypes.f_123775_, $$13.f_82479_, $$13.f_82480_, $$13.f_82481_, $$17.f_82479_, $$17.f_82480_, $$17.f_82481_);
        }
    }

    public boolean m_59216_() {
        return this.f_59186_;
    }

    public boolean m_59217_() {
        return this.f_59187_;
    }

    private void m_59214_(boolean p_59215_) {
        this.f_59187_ = p_59215_;
    }

    public float m_59197_(float p_59198_) {
        return (this.f_59185_ + p_59198_) * -0.0375f;
    }

    public /* synthetic */ Packet m_58483_() {
        return this.m_58483_();
    }
}

