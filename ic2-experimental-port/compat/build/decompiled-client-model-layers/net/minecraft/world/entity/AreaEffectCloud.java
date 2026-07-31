/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.commands.arguments.ParticleArgument;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import org.slf4j.Logger;

public class AreaEffectCloud
extends Entity {
    private static final Logger f_19696_ = LogUtils.getLogger();
    private static final int f_146782_ = 5;
    private static final EntityDataAccessor<Float> f_19697_ = SynchedEntityData.m_135353_(AreaEffectCloud.class, EntityDataSerializers.f_135029_);
    private static final EntityDataAccessor<Integer> f_19698_ = SynchedEntityData.m_135353_(AreaEffectCloud.class, EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Boolean> f_19699_ = SynchedEntityData.m_135353_(AreaEffectCloud.class, EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<ParticleOptions> f_19700_ = SynchedEntityData.m_135353_(AreaEffectCloud.class, EntityDataSerializers.f_135036_);
    private static final float f_146781_ = 32.0f;
    private Potion f_19701_ = Potions.f_43598_;
    private final List<MobEffectInstance> f_19685_ = Lists.newArrayList();
    private final Map<Entity, Integer> f_19686_ = Maps.newHashMap();
    private int f_19687_ = 600;
    private int f_19688_ = 20;
    private int f_19689_ = 20;
    private boolean f_19690_;
    private int f_19691_;
    private float f_19692_;
    private float f_19693_;
    @Nullable
    private LivingEntity f_19694_;
    @Nullable
    private UUID f_19695_;

    public AreaEffectCloud(EntityType<? extends AreaEffectCloud> p_19704_, Level p_19705_) {
        super(p_19704_, p_19705_);
        this.f_19794_ = true;
        this.m_19712_(3.0f);
    }

    public AreaEffectCloud(Level p_19707_, double p_19708_, double p_19709_, double p_19710_) {
        this((EntityType<? extends AreaEffectCloud>)EntityType.f_20476_, p_19707_);
        this.m_6034_(p_19708_, p_19709_, p_19710_);
    }

    @Override
    protected void m_8097_() {
        this.m_20088_().m_135372_(f_19698_, 0);
        this.m_20088_().m_135372_(f_19697_, Float.valueOf(0.5f));
        this.m_20088_().m_135372_(f_19699_, false);
        this.m_20088_().m_135372_(f_19700_, ParticleTypes.f_123811_);
    }

    public void m_19712_(float p_19713_) {
        if (!this.f_19853_.f_46443_) {
            this.m_20088_().m_135381_(f_19697_, Float.valueOf(Mth.m_14036_(p_19713_, 0.0f, 32.0f)));
        }
    }

    @Override
    public void m_6210_() {
        double $$0 = this.m_20185_();
        double $$1 = this.m_20186_();
        double $$2 = this.m_20189_();
        super.m_6210_();
        this.m_6034_($$0, $$1, $$2);
    }

    public float m_19743_() {
        return this.m_20088_().m_135370_(f_19697_).floatValue();
    }

    public void m_19722_(Potion p_19723_) {
        this.f_19701_ = p_19723_;
        if (!this.f_19690_) {
            this.m_19750_();
        }
    }

    private void m_19750_() {
        if (this.f_19701_ == Potions.f_43598_ && this.f_19685_.isEmpty()) {
            this.m_20088_().m_135381_(f_19698_, 0);
        } else {
            this.m_20088_().m_135381_(f_19698_, PotionUtils.m_43564_(PotionUtils.m_43561_(this.f_19701_, this.f_19685_)));
        }
    }

    public void m_19716_(MobEffectInstance p_19717_) {
        this.f_19685_.add(p_19717_);
        if (!this.f_19690_) {
            this.m_19750_();
        }
    }

    public int m_19744_() {
        return this.m_20088_().m_135370_(f_19698_);
    }

    public void m_19714_(int p_19715_) {
        this.f_19690_ = true;
        this.m_20088_().m_135381_(f_19698_, p_19715_);
    }

    public ParticleOptions m_19745_() {
        return this.m_20088_().m_135370_(f_19700_);
    }

    public void m_19724_(ParticleOptions p_19725_) {
        this.m_20088_().m_135381_(f_19700_, p_19725_);
    }

    protected void m_19730_(boolean p_19731_) {
        this.m_20088_().m_135381_(f_19699_, p_19731_);
    }

    public boolean m_19747_() {
        return this.m_20088_().m_135370_(f_19699_);
    }

    public int m_19748_() {
        return this.f_19687_;
    }

    public void m_19734_(int p_19735_) {
        this.f_19687_ = p_19735_;
    }

    @Override
    public void m_8119_() {
        block20: {
            ArrayList $$24;
            float $$1;
            block21: {
                boolean $$23;
                boolean $$0;
                block19: {
                    float $$6;
                    int $$5;
                    super.m_8119_();
                    $$0 = this.m_19747_();
                    $$1 = this.m_19743_();
                    if (!this.f_19853_.f_46443_) break block19;
                    if ($$0 && this.f_19796_.m_188499_()) {
                        return;
                    }
                    ParticleOptions $$2 = this.m_19745_();
                    if ($$0) {
                        int $$3 = 2;
                        float $$4 = 0.2f;
                    } else {
                        $$5 = Mth.m_14167_((float)Math.PI * $$1 * $$1);
                        $$6 = $$1;
                    }
                    for (int $$7 = 0; $$7 < $$5; ++$$7) {
                        double $$22;
                        double $$21;
                        double $$20;
                        float $$8 = this.f_19796_.m_188501_() * ((float)Math.PI * 2);
                        float $$9 = Mth.m_14116_(this.f_19796_.m_188501_()) * $$6;
                        double $$10 = this.m_20185_() + (double)(Mth.m_14089_($$8) * $$9);
                        double $$11 = this.m_20186_();
                        double $$12 = this.m_20189_() + (double)(Mth.m_14031_($$8) * $$9);
                        if ($$2.m_6012_() == ParticleTypes.f_123811_) {
                            int $$13 = $$0 && this.f_19796_.m_188499_() ? 0xFFFFFF : this.m_19744_();
                            double $$14 = (float)($$13 >> 16 & 0xFF) / 255.0f;
                            double $$15 = (float)($$13 >> 8 & 0xFF) / 255.0f;
                            double $$16 = (float)($$13 & 0xFF) / 255.0f;
                        } else if ($$0) {
                            double $$17 = 0.0;
                            double $$18 = 0.0;
                            double $$19 = 0.0;
                        } else {
                            $$20 = (0.5 - this.f_19796_.m_188500_()) * 0.15;
                            $$21 = 0.01f;
                            $$22 = (0.5 - this.f_19796_.m_188500_()) * 0.15;
                        }
                        this.f_19853_.m_7107_($$2, $$10, $$11, $$12, $$20, $$21, $$22);
                    }
                    break block20;
                }
                if (this.f_19797_ >= this.f_19688_ + this.f_19687_) {
                    this.m_146870_();
                    return;
                }
                boolean bl = $$23 = this.f_19797_ < this.f_19688_;
                if ($$0 != $$23) {
                    this.m_19730_($$23);
                }
                if ($$23) {
                    return;
                }
                if (this.f_19693_ != 0.0f) {
                    if (($$1 += this.f_19693_) < 0.5f) {
                        this.m_146870_();
                        return;
                    }
                    this.m_19712_($$1);
                }
                if (this.f_19797_ % 5 != 0) break block20;
                this.f_19686_.entrySet().removeIf(p_146784_ -> this.f_19797_ >= (Integer)p_146784_.getValue());
                $$24 = Lists.newArrayList();
                for (MobEffectInstance $$25 : this.f_19701_.m_43488_()) {
                    $$24.add(new MobEffectInstance($$25.m_19544_(), $$25.m_19557_() / 4, $$25.m_19564_(), $$25.m_19571_(), $$25.m_19572_()));
                }
                $$24.addAll(this.f_19685_);
                if (!$$24.isEmpty()) break block21;
                this.f_19686_.clear();
                break block20;
            }
            List<LivingEntity> $$26 = this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_());
            if ($$26.isEmpty()) break block20;
            for (LivingEntity $$27 : $$26) {
                double $$29;
                double $$28;
                double $$30;
                if (this.f_19686_.containsKey($$27) || !$$27.m_5801_() || !(($$30 = ($$28 = $$27.m_20185_() - this.m_20185_()) * $$28 + ($$29 = $$27.m_20189_() - this.m_20189_()) * $$29) <= (double)($$1 * $$1))) continue;
                this.f_19686_.put($$27, this.f_19797_ + this.f_19689_);
                for (MobEffectInstance $$31 : $$24) {
                    if ($$31.m_19544_().m_8093_()) {
                        $$31.m_19544_().m_19461_(this, this.m_19749_(), $$27, $$31.m_19564_(), 0.5);
                        continue;
                    }
                    $$27.m_147207_(new MobEffectInstance($$31), this);
                }
                if (this.f_19692_ != 0.0f) {
                    if (($$1 += this.f_19692_) < 0.5f) {
                        this.m_146870_();
                        return;
                    }
                    this.m_19712_($$1);
                }
                if (this.f_19691_ == 0) continue;
                this.f_19687_ += this.f_19691_;
                if (this.f_19687_ > 0) continue;
                this.m_146870_();
                return;
            }
        }
    }

    public float m_146787_() {
        return this.f_19692_;
    }

    public void m_19732_(float p_19733_) {
        this.f_19692_ = p_19733_;
    }

    public float m_146788_() {
        return this.f_19693_;
    }

    public void m_19738_(float p_19739_) {
        this.f_19693_ = p_19739_;
    }

    public int m_146789_() {
        return this.f_19691_;
    }

    public void m_146785_(int p_146786_) {
        this.f_19691_ = p_146786_;
    }

    public int m_146790_() {
        return this.f_19688_;
    }

    public void m_19740_(int p_19741_) {
        this.f_19688_ = p_19741_;
    }

    public void m_19718_(@Nullable LivingEntity p_19719_) {
        this.f_19694_ = p_19719_;
        this.f_19695_ = p_19719_ == null ? null : p_19719_.m_20148_();
    }

    @Nullable
    public LivingEntity m_19749_() {
        Entity $$0;
        if (this.f_19694_ == null && this.f_19695_ != null && this.f_19853_ instanceof ServerLevel && ($$0 = ((ServerLevel)this.f_19853_).m_8791_(this.f_19695_)) instanceof LivingEntity) {
            this.f_19694_ = (LivingEntity)$$0;
        }
        return this.f_19694_;
    }

    @Override
    protected void m_7378_(CompoundTag p_19727_) {
        this.f_19797_ = p_19727_.m_128451_("Age");
        this.f_19687_ = p_19727_.m_128451_("Duration");
        this.f_19688_ = p_19727_.m_128451_("WaitTime");
        this.f_19689_ = p_19727_.m_128451_("ReapplicationDelay");
        this.f_19691_ = p_19727_.m_128451_("DurationOnUse");
        this.f_19692_ = p_19727_.m_128457_("RadiusOnUse");
        this.f_19693_ = p_19727_.m_128457_("RadiusPerTick");
        this.m_19712_(p_19727_.m_128457_("Radius"));
        if (p_19727_.m_128403_("Owner")) {
            this.f_19695_ = p_19727_.m_128342_("Owner");
        }
        if (p_19727_.m_128425_("Particle", 8)) {
            try {
                this.m_19724_(ParticleArgument.m_103944_(new StringReader(p_19727_.m_128461_("Particle"))));
            }
            catch (CommandSyntaxException $$1) {
                f_19696_.warn("Couldn't load custom particle {}", (Object)p_19727_.m_128461_("Particle"), (Object)$$1);
            }
        }
        if (p_19727_.m_128425_("Color", 99)) {
            this.m_19714_(p_19727_.m_128451_("Color"));
        }
        if (p_19727_.m_128425_("Potion", 8)) {
            this.m_19722_(PotionUtils.m_43577_(p_19727_));
        }
        if (p_19727_.m_128425_("Effects", 9)) {
            ListTag $$2 = p_19727_.m_128437_("Effects", 10);
            this.f_19685_.clear();
            for (int $$3 = 0; $$3 < $$2.size(); ++$$3) {
                MobEffectInstance $$4 = MobEffectInstance.m_19560_($$2.m_128728_($$3));
                if ($$4 == null) continue;
                this.m_19716_($$4);
            }
        }
    }

    @Override
    protected void m_7380_(CompoundTag p_19737_) {
        p_19737_.m_128405_("Age", this.f_19797_);
        p_19737_.m_128405_("Duration", this.f_19687_);
        p_19737_.m_128405_("WaitTime", this.f_19688_);
        p_19737_.m_128405_("ReapplicationDelay", this.f_19689_);
        p_19737_.m_128405_("DurationOnUse", this.f_19691_);
        p_19737_.m_128350_("RadiusOnUse", this.f_19692_);
        p_19737_.m_128350_("RadiusPerTick", this.f_19693_);
        p_19737_.m_128350_("Radius", this.m_19743_());
        p_19737_.m_128359_("Particle", this.m_19745_().m_5942_());
        if (this.f_19695_ != null) {
            p_19737_.m_128362_("Owner", this.f_19695_);
        }
        if (this.f_19690_) {
            p_19737_.m_128405_("Color", this.m_19744_());
        }
        if (this.f_19701_ != Potions.f_43598_) {
            p_19737_.m_128359_("Potion", Registry.f_122828_.m_7981_(this.f_19701_).toString());
        }
        if (!this.f_19685_.isEmpty()) {
            ListTag $$1 = new ListTag();
            for (MobEffectInstance $$2 : this.f_19685_) {
                $$1.add($$2.m_19555_(new CompoundTag()));
            }
            p_19737_.m_128365_("Effects", $$1);
        }
    }

    @Override
    public void m_7350_(EntityDataAccessor<?> p_19729_) {
        if (f_19697_.equals(p_19729_)) {
            this.m_6210_();
        }
        super.m_7350_(p_19729_);
    }

    public Potion m_146791_() {
        return this.f_19701_;
    }

    @Override
    public PushReaction m_7752_() {
        return PushReaction.IGNORE;
    }

    @Override
    public Packet<?> m_5654_() {
        return new ClientboundAddEntityPacket(this);
    }

    @Override
    public EntityDimensions m_6972_(Pose p_19721_) {
        return EntityDimensions.m_20395_(this.m_19743_() * 2.0f, 0.5f);
    }
}

