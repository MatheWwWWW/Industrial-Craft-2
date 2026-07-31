/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ComparisonChain
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.effect;

import com.google.common.collect.ComparisonChain;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import org.slf4j.Logger;

public class MobEffectInstance
implements Comparable<MobEffectInstance> {
    private static final Logger f_19501_ = LogUtils.getLogger();
    private final MobEffect f_19502_;
    int f_19503_;
    private int f_19504_;
    private boolean f_19506_;
    private boolean f_19507_;
    private boolean f_19508_;
    private boolean f_19509_;
    @Nullable
    private MobEffectInstance f_19510_;
    private Optional<FactorData> f_216885_;

    public MobEffectInstance(MobEffect p_19513_) {
        this(p_19513_, 0, 0);
    }

    public MobEffectInstance(MobEffect p_19515_, int p_19516_) {
        this(p_19515_, p_19516_, 0);
    }

    public MobEffectInstance(MobEffect p_19518_, int p_19519_, int p_19520_) {
        this(p_19518_, p_19519_, p_19520_, false, true);
    }

    public MobEffectInstance(MobEffect p_19522_, int p_19523_, int p_19524_, boolean p_19525_, boolean p_19526_) {
        this(p_19522_, p_19523_, p_19524_, p_19525_, p_19526_, p_19526_);
    }

    public MobEffectInstance(MobEffect p_19528_, int p_19529_, int p_19530_, boolean p_19531_, boolean p_19532_, boolean p_19533_) {
        this(p_19528_, p_19529_, p_19530_, p_19531_, p_19532_, p_19533_, null, p_19528_.m_216881_());
    }

    public MobEffectInstance(MobEffect p_216887_, int p_216888_, int p_216889_, boolean p_216890_, boolean p_216891_, boolean p_216892_, @Nullable MobEffectInstance p_216893_, Optional<FactorData> p_216894_) {
        this.f_19502_ = p_216887_;
        this.f_19503_ = p_216888_;
        this.f_19504_ = p_216889_;
        this.f_19506_ = p_216890_;
        this.f_19508_ = p_216891_;
        this.f_19509_ = p_216892_;
        this.f_19510_ = p_216893_;
        this.f_216885_ = p_216894_;
    }

    public MobEffectInstance(MobEffectInstance p_19543_) {
        this.f_19502_ = p_19543_.f_19502_;
        this.f_216885_ = this.f_19502_.m_216881_();
        this.m_19548_(p_19543_);
    }

    public Optional<FactorData> m_216895_() {
        return this.f_216885_;
    }

    void m_19548_(MobEffectInstance p_19549_) {
        this.f_19503_ = p_19549_.f_19503_;
        this.f_19504_ = p_19549_.f_19504_;
        this.f_19506_ = p_19549_.f_19506_;
        this.f_19508_ = p_19549_.f_19508_;
        this.f_19509_ = p_19549_.f_19509_;
    }

    public boolean m_19558_(MobEffectInstance p_19559_) {
        if (this.f_19502_ != p_19559_.f_19502_) {
            f_19501_.warn("This method should only be called for matching effects!");
        }
        int $$1 = this.f_19503_;
        boolean $$2 = false;
        if (p_19559_.f_19504_ > this.f_19504_) {
            if (p_19559_.f_19503_ < this.f_19503_) {
                MobEffectInstance $$3 = this.f_19510_;
                this.f_19510_ = new MobEffectInstance(this);
                this.f_19510_.f_19510_ = $$3;
            }
            this.f_19504_ = p_19559_.f_19504_;
            this.f_19503_ = p_19559_.f_19503_;
            $$2 = true;
        } else if (p_19559_.f_19503_ > this.f_19503_) {
            if (p_19559_.f_19504_ == this.f_19504_) {
                this.f_19503_ = p_19559_.f_19503_;
                $$2 = true;
            } else if (this.f_19510_ == null) {
                this.f_19510_ = new MobEffectInstance(p_19559_);
            } else {
                this.f_19510_.m_19558_(p_19559_);
            }
        }
        if (!p_19559_.f_19506_ && this.f_19506_ || $$2) {
            this.f_19506_ = p_19559_.f_19506_;
            $$2 = true;
        }
        if (p_19559_.f_19508_ != this.f_19508_) {
            this.f_19508_ = p_19559_.f_19508_;
            $$2 = true;
        }
        if (p_19559_.f_19509_ != this.f_19509_) {
            this.f_19509_ = p_19559_.f_19509_;
            $$2 = true;
        }
        if ($$1 != this.f_19503_) {
            this.f_216885_.ifPresent(p_216898_ -> p_216898_.f_216912_ += this.f_19503_ - $$1);
            $$2 = true;
        }
        return $$2;
    }

    public MobEffect m_19544_() {
        return this.f_19502_;
    }

    public int m_19557_() {
        return this.f_19503_;
    }

    public int m_19564_() {
        return this.f_19504_;
    }

    public boolean m_19571_() {
        return this.f_19506_;
    }

    public boolean m_19572_() {
        return this.f_19508_;
    }

    public boolean m_19575_() {
        return this.f_19509_;
    }

    public boolean m_19552_(LivingEntity p_19553_, Runnable p_19554_) {
        if (this.f_19503_ > 0) {
            if (this.f_19502_.m_6584_(this.f_19503_, this.f_19504_)) {
                this.m_19550_(p_19553_);
            }
            this.m_19579_();
            if (this.f_19503_ == 0 && this.f_19510_ != null) {
                this.m_19548_(this.f_19510_);
                this.f_19510_ = this.f_19510_.f_19510_;
                p_19554_.run();
            }
        }
        this.f_216885_.ifPresent(p_216900_ -> p_216900_.m_216930_(this));
        return this.f_19503_ > 0;
    }

    private int m_19579_() {
        if (this.f_19510_ != null) {
            this.f_19510_.m_19579_();
        }
        return --this.f_19503_;
    }

    public void m_19550_(LivingEntity p_19551_) {
        if (this.f_19503_ > 0) {
            this.f_19502_.m_6742_(p_19551_, this.f_19504_);
        }
    }

    public String m_19576_() {
        return this.f_19502_.m_19481_();
    }

    public String toString() {
        String $$1;
        if (this.f_19504_ > 0) {
            String $$0 = this.m_19576_() + " x " + (this.f_19504_ + 1) + ", Duration: " + this.f_19503_;
        } else {
            $$1 = this.m_19576_() + ", Duration: " + this.f_19503_;
        }
        if (!this.f_19508_) {
            $$1 = $$1 + ", Particles: false";
        }
        if (!this.f_19509_) {
            $$1 = $$1 + ", Show Icon: false";
        }
        return $$1;
    }

    public boolean equals(Object p_19574_) {
        if (this == p_19574_) {
            return true;
        }
        if (p_19574_ instanceof MobEffectInstance) {
            MobEffectInstance $$1 = (MobEffectInstance)p_19574_;
            return this.f_19503_ == $$1.f_19503_ && this.f_19504_ == $$1.f_19504_ && this.f_19506_ == $$1.f_19506_ && this.f_19502_.equals($$1.f_19502_);
        }
        return false;
    }

    public int hashCode() {
        int $$0 = this.f_19502_.hashCode();
        $$0 = 31 * $$0 + this.f_19503_;
        $$0 = 31 * $$0 + this.f_19504_;
        $$0 = 31 * $$0 + (this.f_19506_ ? 1 : 0);
        return $$0;
    }

    public CompoundTag m_19555_(CompoundTag p_19556_) {
        p_19556_.m_128405_("Id", MobEffect.m_19459_(this.m_19544_()));
        this.m_19567_(p_19556_);
        return p_19556_;
    }

    private void m_19567_(CompoundTag p_19568_) {
        p_19568_.m_128344_("Amplifier", (byte)this.m_19564_());
        p_19568_.m_128405_("Duration", this.m_19557_());
        p_19568_.m_128379_("Ambient", this.m_19571_());
        p_19568_.m_128379_("ShowParticles", this.m_19572_());
        p_19568_.m_128379_("ShowIcon", this.m_19575_());
        if (this.f_19510_ != null) {
            CompoundTag $$1 = new CompoundTag();
            this.f_19510_.m_19555_($$1);
            p_19568_.m_128365_("HiddenEffect", $$1);
        }
        this.f_216885_.ifPresent(p_216903_ -> FactorData.f_216907_.encodeStart((DynamicOps)NbtOps.f_128958_, p_216903_).resultOrPartial(arg_0 -> ((Logger)f_19501_).error(arg_0)).ifPresent(p_216906_ -> p_19568_.m_128365_("FactorCalculationData", (Tag)p_216906_)));
    }

    @Nullable
    public static MobEffectInstance m_19560_(CompoundTag p_19561_) {
        int $$1 = p_19561_.m_128451_("Id");
        MobEffect $$2 = MobEffect.m_19453_($$1);
        if ($$2 == null) {
            return null;
        }
        return MobEffectInstance.m_19545_($$2, p_19561_);
    }

    private static MobEffectInstance m_19545_(MobEffect p_19546_, CompoundTag p_19547_) {
        Optional<FactorData> $$9;
        byte $$2 = p_19547_.m_128445_("Amplifier");
        int $$3 = p_19547_.m_128451_("Duration");
        boolean $$4 = p_19547_.m_128471_("Ambient");
        boolean $$5 = true;
        if (p_19547_.m_128425_("ShowParticles", 1)) {
            $$5 = p_19547_.m_128471_("ShowParticles");
        }
        boolean $$6 = $$5;
        if (p_19547_.m_128425_("ShowIcon", 1)) {
            $$6 = p_19547_.m_128471_("ShowIcon");
        }
        MobEffectInstance $$7 = null;
        if (p_19547_.m_128425_("HiddenEffect", 10)) {
            $$7 = MobEffectInstance.m_19545_(p_19546_, p_19547_.m_128469_("HiddenEffect"));
        }
        if (p_19547_.m_128425_("FactorCalculationData", 10)) {
            Optional $$8 = FactorData.f_216907_.parse(new Dynamic((DynamicOps)NbtOps.f_128958_, (Object)p_19547_.m_128469_("FactorCalculationData"))).resultOrPartial(arg_0 -> ((Logger)f_19501_).error(arg_0));
        } else {
            $$9 = Optional.empty();
        }
        return new MobEffectInstance(p_19546_, $$3, Math.max($$2, 0), $$4, $$5, $$6, $$7, $$9);
    }

    public void m_19562_(boolean p_19563_) {
        this.f_19507_ = p_19563_;
    }

    public boolean m_19577_() {
        return this.f_19507_;
    }

    @Override
    public int compareTo(MobEffectInstance p_19566_) {
        int $$1 = 32147;
        if (this.m_19557_() > 32147 && p_19566_.m_19557_() > 32147 || this.m_19571_() && p_19566_.m_19571_()) {
            return ComparisonChain.start().compare(Boolean.valueOf(this.m_19571_()), Boolean.valueOf(p_19566_.m_19571_())).compare(this.m_19544_().m_19484_(), p_19566_.m_19544_().m_19484_()).result();
        }
        return ComparisonChain.start().compare(Boolean.valueOf(this.m_19571_()), Boolean.valueOf(p_19566_.m_19571_())).compare(this.m_19557_(), p_19566_.m_19557_()).compare(this.m_19544_().m_19484_(), p_19566_.m_19544_().m_19484_()).result();
    }

    @Override
    public /* synthetic */ int compareTo(Object object) {
        return this.compareTo((MobEffectInstance)object);
    }

    public static class FactorData {
        public static final Codec<FactorData> f_216907_ = RecordCodecBuilder.create(p_216933_ -> p_216933_.group((App)ExtraCodecs.f_144628_.fieldOf("padding_duration").forGetter(p_216945_ -> p_216945_.f_216908_), (App)Codec.FLOAT.fieldOf("factor_start").orElse((Object)Float.valueOf(0.0f)).forGetter(p_216943_ -> Float.valueOf(p_216943_.f_216909_)), (App)Codec.FLOAT.fieldOf("factor_target").orElse((Object)Float.valueOf(1.0f)).forGetter(p_216941_ -> Float.valueOf(p_216941_.f_216910_)), (App)Codec.FLOAT.fieldOf("factor_current").orElse((Object)Float.valueOf(0.0f)).forGetter(p_216939_ -> Float.valueOf(p_216939_.f_216911_)), (App)ExtraCodecs.f_144628_.fieldOf("effect_changed_timestamp").orElse((Object)0).forGetter(p_216937_ -> p_216937_.f_216912_), (App)Codec.FLOAT.fieldOf("factor_previous_frame").orElse((Object)Float.valueOf(0.0f)).forGetter(p_216935_ -> Float.valueOf(p_216935_.f_216913_)), (App)Codec.BOOL.fieldOf("had_effect_last_tick").orElse((Object)false).forGetter(p_216929_ -> p_216929_.f_216914_)).apply((Applicative)p_216933_, FactorData::new));
        private final int f_216908_;
        private float f_216909_;
        private float f_216910_;
        private float f_216911_;
        int f_216912_;
        private float f_216913_;
        private boolean f_216914_;

        public FactorData(int p_216919_, float p_216920_, float p_216921_, float p_216922_, int p_216923_, float p_216924_, boolean p_216925_) {
            this.f_216908_ = p_216919_;
            this.f_216909_ = p_216920_;
            this.f_216910_ = p_216921_;
            this.f_216911_ = p_216922_;
            this.f_216912_ = p_216923_;
            this.f_216913_ = p_216924_;
            this.f_216914_ = p_216925_;
        }

        public FactorData(int p_216917_) {
            this(p_216917_, 0.0f, 1.0f, 0.0f, 0, 0.0f, false);
        }

        public void m_216930_(MobEffectInstance p_216931_) {
            boolean $$1;
            this.f_216913_ = this.f_216911_;
            boolean bl = $$1 = p_216931_.f_19503_ > this.f_216908_;
            if (this.f_216914_ != $$1) {
                this.f_216914_ = $$1;
                this.f_216912_ = p_216931_.f_19503_;
                this.f_216909_ = this.f_216911_;
                this.f_216910_ = $$1 ? 1.0f : 0.0f;
            }
            float $$2 = Mth.m_14036_(((float)this.f_216912_ - (float)p_216931_.f_19503_) / (float)this.f_216908_, 0.0f, 1.0f);
            this.f_216911_ = Mth.m_14179_($$2, this.f_216909_, this.f_216910_);
        }

        public float m_238413_(LivingEntity p_238414_, float p_238415_) {
            if (p_238414_.m_213877_()) {
                this.f_216913_ = this.f_216911_;
            }
            return Mth.m_14179_(p_238415_, this.f_216913_, this.f_216911_);
        }
    }
}

