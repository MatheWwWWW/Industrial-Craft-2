/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.entity.projectile;

import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

public class FishingHook
extends Projectile {
    private static final Logger f_201977_ = LogUtils.getLogger();
    private final RandomSource f_37098_ = RandomSource.m_216327_();
    private boolean f_37099_;
    private int f_37100_;
    private static final int f_150136_ = 10;
    private static final EntityDataAccessor<Integer> f_37101_ = SynchedEntityData.m_135353_(FishingHook.class, EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Boolean> f_37102_ = SynchedEntityData.m_135353_(FishingHook.class, EntityDataSerializers.f_135035_);
    private int f_37103_;
    private int f_37089_;
    private int f_37090_;
    private int f_37091_;
    private float f_37092_;
    private boolean f_37093_ = true;
    @Nullable
    private Entity f_37094_;
    private FishHookState f_37095_ = FishHookState.FLYING;
    private final int f_37096_;
    private final int f_37097_;

    private FishingHook(EntityType<? extends FishingHook> p_150141_, Level p_150142_, int p_150143_, int p_150144_) {
        super((EntityType<? extends Projectile>)p_150141_, p_150142_);
        this.f_19811_ = true;
        this.f_37096_ = Math.max(0, p_150143_);
        this.f_37097_ = Math.max(0, p_150144_);
    }

    public FishingHook(EntityType<? extends FishingHook> p_150138_, Level p_150139_) {
        this(p_150138_, p_150139_, 0, 0);
    }

    public FishingHook(Player p_37106_, Level p_37107_, int p_37108_, int p_37109_) {
        this(EntityType.f_20533_, p_37107_, p_37108_, p_37109_);
        this.m_5602_(p_37106_);
        float $$4 = p_37106_.m_146909_();
        float $$5 = p_37106_.m_146908_();
        float $$6 = Mth.m_14089_(-$$5 * ((float)Math.PI / 180) - (float)Math.PI);
        float $$7 = Mth.m_14031_(-$$5 * ((float)Math.PI / 180) - (float)Math.PI);
        float $$8 = -Mth.m_14089_(-$$4 * ((float)Math.PI / 180));
        float $$9 = Mth.m_14031_(-$$4 * ((float)Math.PI / 180));
        double $$10 = p_37106_.m_20185_() - (double)$$7 * 0.3;
        double $$11 = p_37106_.m_20188_();
        double $$12 = p_37106_.m_20189_() - (double)$$6 * 0.3;
        this.m_7678_($$10, $$11, $$12, $$5, $$4);
        Vec3 $$13 = new Vec3(-$$7, Mth.m_14036_(-($$9 / $$8), -5.0f, 5.0f), -$$6);
        double $$14 = $$13.m_82553_();
        $$13 = $$13.m_82542_(0.6 / $$14 + this.f_19796_.m_216328_(0.5, 0.0103365), 0.6 / $$14 + this.f_19796_.m_216328_(0.5, 0.0103365), 0.6 / $$14 + this.f_19796_.m_216328_(0.5, 0.0103365));
        this.m_20256_($$13);
        this.m_146922_((float)(Mth.m_14136_($$13.f_82479_, $$13.f_82481_) * 57.2957763671875));
        this.m_146926_((float)(Mth.m_14136_($$13.f_82480_, $$13.m_165924_()) * 57.2957763671875));
        this.f_19859_ = this.m_146908_();
        this.f_19860_ = this.m_146909_();
    }

    @Override
    protected void m_8097_() {
        this.m_20088_().m_135372_(f_37101_, 0);
        this.m_20088_().m_135372_(f_37102_, false);
    }

    @Override
    public void m_7350_(EntityDataAccessor<?> p_37153_) {
        if (f_37101_.equals(p_37153_)) {
            int $$1 = this.m_20088_().m_135370_(f_37101_);
            Entity entity = this.f_37094_ = $$1 > 0 ? this.f_19853_.m_6815_($$1 - 1) : null;
        }
        if (f_37102_.equals(p_37153_)) {
            this.f_37099_ = this.m_20088_().m_135370_(f_37102_);
            if (this.f_37099_) {
                this.m_20334_(this.m_20184_().f_82479_, -0.4f * Mth.m_216267_(this.f_37098_, 0.6f, 1.0f), this.m_20184_().f_82481_);
            }
        }
        super.m_7350_(p_37153_);
    }

    @Override
    public boolean m_6783_(double p_37125_) {
        double $$1 = 64.0;
        return p_37125_ < 4096.0;
    }

    @Override
    public void m_6453_(double p_37127_, double p_37128_, double p_37129_, float p_37130_, float p_37131_, int p_37132_, boolean p_37133_) {
    }

    @Override
    public void m_8119_() {
        boolean $$4;
        this.f_37098_.m_188584_(this.m_20148_().getLeastSignificantBits() ^ this.f_19853_.m_46467_());
        super.m_8119_();
        Player $$0 = this.m_37168_();
        if ($$0 == null) {
            this.m_146870_();
            return;
        }
        if (!this.f_19853_.f_46443_ && this.m_37136_($$0)) {
            return;
        }
        if (this.f_19861_) {
            ++this.f_37103_;
            if (this.f_37103_ >= 1200) {
                this.m_146870_();
                return;
            }
        } else {
            this.f_37103_ = 0;
        }
        float $$1 = 0.0f;
        BlockPos $$2 = this.m_20183_();
        FluidState $$3 = this.f_19853_.m_6425_($$2);
        if ($$3.m_205070_(FluidTags.f_13131_)) {
            $$1 = $$3.m_76155_(this.f_19853_, $$2);
        }
        boolean bl = $$4 = $$1 > 0.0f;
        if (this.f_37095_ == FishHookState.FLYING) {
            if (this.f_37094_ != null) {
                this.m_20256_(Vec3.f_82478_);
                this.f_37095_ = FishHookState.HOOKED_IN_ENTITY;
                return;
            }
            if ($$4) {
                this.m_20256_(this.m_20184_().m_82542_(0.3, 0.2, 0.3));
                this.f_37095_ = FishHookState.BOBBING;
                return;
            }
            this.m_37171_();
        } else {
            if (this.f_37095_ == FishHookState.HOOKED_IN_ENTITY) {
                if (this.f_37094_ != null) {
                    if (this.f_37094_.m_213877_() || this.f_37094_.f_19853_.m_46472_() != this.f_19853_.m_46472_()) {
                        this.m_150157_(null);
                        this.f_37095_ = FishHookState.FLYING;
                    } else {
                        this.m_6034_(this.f_37094_.m_20185_(), this.f_37094_.m_20227_(0.8), this.f_37094_.m_20189_());
                    }
                }
                return;
            }
            if (this.f_37095_ == FishHookState.BOBBING) {
                Vec3 $$5 = this.m_20184_();
                double $$6 = this.m_20186_() + $$5.f_82480_ - (double)$$2.m_123342_() - (double)$$1;
                if (Math.abs($$6) < 0.01) {
                    $$6 += Math.signum($$6) * 0.1;
                }
                this.m_20334_($$5.f_82479_ * 0.9, $$5.f_82480_ - $$6 * (double)this.f_19796_.m_188501_() * 0.2, $$5.f_82481_ * 0.9);
                this.f_37093_ = this.f_37089_ > 0 || this.f_37091_ > 0 ? this.f_37093_ && this.f_37100_ < 10 && this.m_37158_($$2) : true;
                if ($$4) {
                    this.f_37100_ = Math.max(0, this.f_37100_ - 1);
                    if (this.f_37099_) {
                        this.m_20256_(this.m_20184_().m_82520_(0.0, -0.1 * (double)this.f_37098_.m_188501_() * (double)this.f_37098_.m_188501_(), 0.0));
                    }
                    if (!this.f_19853_.f_46443_) {
                        this.m_37145_($$2);
                    }
                } else {
                    this.f_37100_ = Math.min(10, this.f_37100_ + 1);
                }
            }
        }
        if (!$$3.m_205070_(FluidTags.f_13131_)) {
            this.m_20256_(this.m_20184_().m_82520_(0.0, -0.03, 0.0));
        }
        this.m_6478_(MoverType.SELF, this.m_20184_());
        this.m_37283_();
        if (this.f_37095_ == FishHookState.FLYING && (this.f_19861_ || this.f_19862_)) {
            this.m_20256_(Vec3.f_82478_);
        }
        double $$7 = 0.92;
        this.m_20256_(this.m_20184_().m_82490_(0.92));
        this.m_20090_();
    }

    private boolean m_37136_(Player p_37137_) {
        ItemStack $$1 = p_37137_.m_21205_();
        ItemStack $$2 = p_37137_.m_21206_();
        boolean $$3 = $$1.m_150930_(Items.f_42523_);
        boolean $$4 = $$2.m_150930_(Items.f_42523_);
        if (p_37137_.m_213877_() || !p_37137_.m_6084_() || !$$3 && !$$4 || this.m_20280_(p_37137_) > 1024.0) {
            this.m_146870_();
            return true;
        }
        return false;
    }

    private void m_37171_() {
        HitResult $$0 = ProjectileUtil.m_37294_(this, this::m_5603_);
        this.m_6532_($$0);
    }

    @Override
    protected boolean m_5603_(Entity p_37135_) {
        return super.m_5603_(p_37135_) || p_37135_.m_6084_() && p_37135_ instanceof ItemEntity;
    }

    @Override
    protected void m_5790_(EntityHitResult p_37144_) {
        super.m_5790_(p_37144_);
        if (!this.f_19853_.f_46443_) {
            this.m_150157_(p_37144_.m_82443_());
        }
    }

    @Override
    protected void m_8060_(BlockHitResult p_37142_) {
        super.m_8060_(p_37142_);
        this.m_20256_(this.m_20184_().m_82541_().m_82490_(p_37142_.m_82448_(this)));
    }

    private void m_150157_(@Nullable Entity p_150158_) {
        this.f_37094_ = p_150158_;
        this.m_20088_().m_135381_(f_37101_, p_150158_ == null ? 0 : p_150158_.m_19879_() + 1);
    }

    private void m_37145_(BlockPos p_37146_) {
        ServerLevel $$1 = (ServerLevel)this.f_19853_;
        int $$2 = 1;
        BlockPos $$3 = p_37146_.m_7494_();
        if (this.f_19796_.m_188501_() < 0.25f && this.f_19853_.m_46758_($$3)) {
            ++$$2;
        }
        if (this.f_19796_.m_188501_() < 0.5f && !this.f_19853_.m_45527_($$3)) {
            --$$2;
        }
        if (this.f_37089_ > 0) {
            --this.f_37089_;
            if (this.f_37089_ <= 0) {
                this.f_37090_ = 0;
                this.f_37091_ = 0;
                this.m_20088_().m_135381_(f_37102_, false);
            }
        } else if (this.f_37091_ > 0) {
            this.f_37091_ -= $$2;
            if (this.f_37091_ > 0) {
                double $$9;
                double $$8;
                this.f_37092_ += (float)this.f_19796_.m_216328_(0.0, 9.188);
                float $$4 = this.f_37092_ * ((float)Math.PI / 180);
                float $$5 = Mth.m_14031_($$4);
                float $$6 = Mth.m_14089_($$4);
                double $$7 = this.m_20185_() + (double)($$5 * (float)this.f_37091_ * 0.1f);
                BlockState $$10 = $$1.m_8055_(new BlockPos($$7, ($$8 = (double)((float)Mth.m_14107_(this.m_20186_()) + 1.0f)) - 1.0, $$9 = this.m_20189_() + (double)($$6 * (float)this.f_37091_ * 0.1f)));
                if ($$10.m_60713_(Blocks.f_49990_)) {
                    if (this.f_19796_.m_188501_() < 0.15f) {
                        $$1.m_8767_(ParticleTypes.f_123795_, $$7, $$8 - (double)0.1f, $$9, 1, $$5, 0.1, $$6, 0.0);
                    }
                    float $$11 = $$5 * 0.04f;
                    float $$12 = $$6 * 0.04f;
                    $$1.m_8767_(ParticleTypes.f_123816_, $$7, $$8, $$9, 0, $$12, 0.01, -$$11, 1.0);
                    $$1.m_8767_(ParticleTypes.f_123816_, $$7, $$8, $$9, 0, -$$12, 0.01, $$11, 1.0);
                }
            } else {
                this.m_5496_(SoundEvents.f_11940_, 0.25f, 1.0f + (this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.4f);
                double $$13 = this.m_20186_() + 0.5;
                $$1.m_8767_(ParticleTypes.f_123795_, this.m_20185_(), $$13, this.m_20189_(), (int)(1.0f + this.m_20205_() * 20.0f), this.m_20205_(), 0.0, this.m_20205_(), 0.2f);
                $$1.m_8767_(ParticleTypes.f_123816_, this.m_20185_(), $$13, this.m_20189_(), (int)(1.0f + this.m_20205_() * 20.0f), this.m_20205_(), 0.0, this.m_20205_(), 0.2f);
                this.f_37089_ = Mth.m_216271_(this.f_19796_, 20, 40);
                this.m_20088_().m_135381_(f_37102_, true);
            }
        } else if (this.f_37090_ > 0) {
            this.f_37090_ -= $$2;
            float $$14 = 0.15f;
            if (this.f_37090_ < 20) {
                $$14 += (float)(20 - this.f_37090_) * 0.05f;
            } else if (this.f_37090_ < 40) {
                $$14 += (float)(40 - this.f_37090_) * 0.02f;
            } else if (this.f_37090_ < 60) {
                $$14 += (float)(60 - this.f_37090_) * 0.01f;
            }
            if (this.f_19796_.m_188501_() < $$14) {
                double $$19;
                double $$18;
                float $$15 = Mth.m_216267_(this.f_19796_, 0.0f, 360.0f) * ((float)Math.PI / 180);
                float $$16 = Mth.m_216267_(this.f_19796_, 25.0f, 60.0f);
                double $$17 = this.m_20185_() + (double)(Mth.m_14031_($$15) * $$16) * 0.1;
                BlockState $$20 = $$1.m_8055_(new BlockPos($$17, ($$18 = (double)((float)Mth.m_14107_(this.m_20186_()) + 1.0f)) - 1.0, $$19 = this.m_20189_() + (double)(Mth.m_14089_($$15) * $$16) * 0.1));
                if ($$20.m_60713_(Blocks.f_49990_)) {
                    $$1.m_8767_(ParticleTypes.f_123769_, $$17, $$18, $$19, 2 + this.f_19796_.m_188503_(2), 0.1f, 0.0, 0.1f, 0.0);
                }
            }
            if (this.f_37090_ <= 0) {
                this.f_37092_ = Mth.m_216267_(this.f_19796_, 0.0f, 360.0f);
                this.f_37091_ = Mth.m_216271_(this.f_19796_, 20, 80);
            }
        } else {
            this.f_37090_ = Mth.m_216271_(this.f_19796_, 100, 600);
            this.f_37090_ -= this.f_37097_ * 20 * 5;
        }
    }

    private boolean m_37158_(BlockPos p_37159_) {
        OpenWaterType $$1 = OpenWaterType.INVALID;
        for (int $$2 = -1; $$2 <= 2; ++$$2) {
            OpenWaterType $$3 = this.m_37147_(p_37159_.m_7918_(-2, $$2, -2), p_37159_.m_7918_(2, $$2, 2));
            switch ($$3) {
                case INVALID: {
                    return false;
                }
                case ABOVE_WATER: {
                    if ($$1 != OpenWaterType.INVALID) break;
                    return false;
                }
                case INSIDE_WATER: {
                    if ($$1 != OpenWaterType.ABOVE_WATER) break;
                    return false;
                }
            }
            $$1 = $$3;
        }
        return true;
    }

    private OpenWaterType m_37147_(BlockPos p_37148_, BlockPos p_37149_) {
        return BlockPos.m_121990_(p_37148_, p_37149_).map(this::m_37163_).reduce((p_37139_, p_37140_) -> p_37139_ == p_37140_ ? p_37139_ : OpenWaterType.INVALID).orElse(OpenWaterType.INVALID);
    }

    private OpenWaterType m_37163_(BlockPos p_37164_) {
        BlockState $$1 = this.f_19853_.m_8055_(p_37164_);
        if ($$1.m_60795_() || $$1.m_60713_(Blocks.f_50196_)) {
            return OpenWaterType.ABOVE_WATER;
        }
        FluidState $$2 = $$1.m_60819_();
        if ($$2.m_205070_(FluidTags.f_13131_) && $$2.m_76170_() && $$1.m_60812_(this.f_19853_, p_37164_).m_83281_()) {
            return OpenWaterType.INSIDE_WATER;
        }
        return OpenWaterType.INVALID;
    }

    public boolean m_37166_() {
        return this.f_37093_;
    }

    @Override
    public void m_7380_(CompoundTag p_37161_) {
    }

    @Override
    public void m_7378_(CompoundTag p_37151_) {
    }

    public int m_37156_(ItemStack p_37157_) {
        Player $$1 = this.m_37168_();
        if (this.f_19853_.f_46443_ || $$1 == null || this.m_37136_($$1)) {
            return 0;
        }
        int $$2 = 0;
        if (this.f_37094_ != null) {
            this.m_150155_(this.f_37094_);
            CriteriaTriggers.f_10553_.m_40416_((ServerPlayer)$$1, p_37157_, this, Collections.emptyList());
            this.f_19853_.m_7605_(this, (byte)31);
            $$2 = this.f_37094_ instanceof ItemEntity ? 3 : 5;
        } else if (this.f_37089_ > 0) {
            LootContext.Builder $$3 = new LootContext.Builder((ServerLevel)this.f_19853_).m_78972_(LootContextParams.f_81460_, this.m_20182_()).m_78972_(LootContextParams.f_81463_, p_37157_).m_78972_(LootContextParams.f_81455_, this).m_230911_(this.f_19796_).m_78963_((float)this.f_37096_ + $$1.m_36336_());
            LootTable $$4 = this.f_19853_.m_7654_().m_129898_().m_79217_(BuiltInLootTables.f_78720_);
            ObjectArrayList<ItemStack> $$5 = $$4.m_230922_($$3.m_78975_(LootContextParamSets.f_81414_));
            CriteriaTriggers.f_10553_.m_40416_((ServerPlayer)$$1, p_37157_, this, (Collection<ItemStack>)$$5);
            for (ItemStack $$6 : $$5) {
                ItemEntity $$7 = new ItemEntity(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), $$6);
                double $$8 = $$1.m_20185_() - this.m_20185_();
                double $$9 = $$1.m_20186_() - this.m_20186_();
                double $$10 = $$1.m_20189_() - this.m_20189_();
                double $$11 = 0.1;
                $$7.m_20334_($$8 * 0.1, $$9 * 0.1 + Math.sqrt(Math.sqrt($$8 * $$8 + $$9 * $$9 + $$10 * $$10)) * 0.08, $$10 * 0.1);
                this.f_19853_.m_7967_($$7);
                $$1.f_19853_.m_7967_(new ExperienceOrb($$1.f_19853_, $$1.m_20185_(), $$1.m_20186_() + 0.5, $$1.m_20189_() + 0.5, this.f_19796_.m_188503_(6) + 1));
                if (!$$6.m_204117_(ItemTags.f_13156_)) continue;
                $$1.m_36222_(Stats.f_12939_, 1);
            }
            $$2 = 1;
        }
        if (this.f_19861_) {
            $$2 = 2;
        }
        this.m_146870_();
        return $$2;
    }

    @Override
    public void m_7822_(byte p_37123_) {
        if (p_37123_ == 31 && this.f_19853_.f_46443_ && this.f_37094_ instanceof Player && ((Player)this.f_37094_).m_7578_()) {
            this.m_150155_(this.f_37094_);
        }
        super.m_7822_(p_37123_);
    }

    protected void m_150155_(Entity p_150156_) {
        Entity $$1 = this.m_37282_();
        if ($$1 == null) {
            return;
        }
        Vec3 $$2 = new Vec3($$1.m_20185_() - this.m_20185_(), $$1.m_20186_() - this.m_20186_(), $$1.m_20189_() - this.m_20189_()).m_82490_(0.1);
        p_150156_.m_20256_(p_150156_.m_20184_().m_82549_($$2));
    }

    @Override
    protected Entity.MovementEmission m_142319_() {
        return Entity.MovementEmission.NONE;
    }

    @Override
    public void m_142687_(Entity.RemovalReason p_150146_) {
        this.m_150147_(null);
        super.m_142687_(p_150146_);
    }

    @Override
    public void m_142036_() {
        this.m_150147_(null);
    }

    @Override
    public void m_5602_(@Nullable Entity p_150154_) {
        super.m_5602_(p_150154_);
        this.m_150147_(this);
    }

    private void m_150147_(@Nullable FishingHook p_150148_) {
        Player $$1 = this.m_37168_();
        if ($$1 != null) {
            $$1.f_36083_ = p_150148_;
        }
    }

    @Nullable
    public Player m_37168_() {
        Entity $$0 = this.m_37282_();
        return $$0 instanceof Player ? (Player)$$0 : null;
    }

    @Nullable
    public Entity m_37170_() {
        return this.f_37094_;
    }

    @Override
    public boolean m_6072_() {
        return false;
    }

    @Override
    public Packet<?> m_5654_() {
        Entity $$0 = this.m_37282_();
        return new ClientboundAddEntityPacket(this, $$0 == null ? this.m_19879_() : $$0.m_19879_());
    }

    @Override
    public void m_141965_(ClientboundAddEntityPacket p_150150_) {
        super.m_141965_(p_150150_);
        if (this.m_37168_() == null) {
            int $$1 = p_150150_.m_131509_();
            f_201977_.error("Failed to recreate fishing hook on client. {} (id: {}) is not a valid owner.", (Object)this.f_19853_.m_6815_($$1), (Object)$$1);
            this.m_6074_();
        }
    }

    static final class FishHookState
    extends Enum<FishHookState> {
        public static final /* enum */ FishHookState FLYING = new FishHookState();
        public static final /* enum */ FishHookState HOOKED_IN_ENTITY = new FishHookState();
        public static final /* enum */ FishHookState BOBBING = new FishHookState();
        private static final /* synthetic */ FishHookState[] $VALUES;

        public static FishHookState[] values() {
            return (FishHookState[])$VALUES.clone();
        }

        public static FishHookState valueOf(String p_37184_) {
            return Enum.valueOf(FishHookState.class, p_37184_);
        }

        private static /* synthetic */ FishHookState[] m_150159_() {
            return new FishHookState[]{FLYING, HOOKED_IN_ENTITY, BOBBING};
        }

        static {
            $VALUES = FishHookState.m_150159_();
        }
    }

    static final class OpenWaterType
    extends Enum<OpenWaterType> {
        public static final /* enum */ OpenWaterType ABOVE_WATER = new OpenWaterType();
        public static final /* enum */ OpenWaterType INSIDE_WATER = new OpenWaterType();
        public static final /* enum */ OpenWaterType INVALID = new OpenWaterType();
        private static final /* synthetic */ OpenWaterType[] $VALUES;

        public static OpenWaterType[] values() {
            return (OpenWaterType[])$VALUES.clone();
        }

        public static OpenWaterType valueOf(String p_37195_) {
            return Enum.valueOf(OpenWaterType.class, p_37195_);
        }

        private static /* synthetic */ OpenWaterType[] m_150160_() {
            return new OpenWaterType[]{ABOVE_WATER, INSIDE_WATER, INVALID};
        }

        static {
            $VALUES = OpenWaterType.m_150160_();
        }
    }
}

