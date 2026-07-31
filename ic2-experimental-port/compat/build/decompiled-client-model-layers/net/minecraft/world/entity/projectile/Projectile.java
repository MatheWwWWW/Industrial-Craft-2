/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.MoreObjects
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.projectile;

import com.google.common.base.MoreObjects;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public abstract class Projectile
extends Entity {
    @Nullable
    private UUID f_37244_;
    @Nullable
    private Entity f_150163_;
    private boolean f_37246_;
    private boolean f_150164_;

    Projectile(EntityType<? extends Projectile> p_37248_, Level p_37249_) {
        super(p_37248_, p_37249_);
    }

    public void m_5602_(@Nullable Entity p_37263_) {
        if (p_37263_ != null) {
            this.f_37244_ = p_37263_.m_20148_();
            this.f_150163_ = p_37263_;
        }
    }

    @Nullable
    public Entity m_37282_() {
        if (this.f_150163_ != null && !this.f_150163_.m_213877_()) {
            return this.f_150163_;
        }
        if (this.f_37244_ != null && this.f_19853_ instanceof ServerLevel) {
            this.f_150163_ = ((ServerLevel)this.f_19853_).m_8791_(this.f_37244_);
            return this.f_150163_;
        }
        return null;
    }

    public Entity m_150173_() {
        return (Entity)MoreObjects.firstNonNull((Object)this.m_37282_(), (Object)this);
    }

    @Override
    protected void m_7380_(CompoundTag p_37265_) {
        if (this.f_37244_ != null) {
            p_37265_.m_128362_("Owner", this.f_37244_);
        }
        if (this.f_37246_) {
            p_37265_.m_128379_("LeftOwner", true);
        }
        p_37265_.m_128379_("HasBeenShot", this.f_150164_);
    }

    protected boolean m_150171_(Entity p_150172_) {
        return p_150172_.m_20148_().equals(this.f_37244_);
    }

    @Override
    protected void m_7378_(CompoundTag p_37262_) {
        if (p_37262_.m_128403_("Owner")) {
            this.f_37244_ = p_37262_.m_128342_("Owner");
        }
        this.f_37246_ = p_37262_.m_128471_("LeftOwner");
        this.f_150164_ = p_37262_.m_128471_("HasBeenShot");
    }

    @Override
    public void m_8119_() {
        if (!this.f_150164_) {
            this.m_146852_(GameEvent.f_157778_, this.m_37282_());
            this.f_150164_ = true;
        }
        if (!this.f_37246_) {
            this.f_37246_ = this.m_37276_();
        }
        super.m_8119_();
    }

    private boolean m_37276_() {
        Entity $$0 = this.m_37282_();
        if ($$0 != null) {
            for (Entity $$1 : this.f_19853_.m_6249_(this, this.m_20191_().m_82369_(this.m_20184_()).m_82400_(1.0), p_37272_ -> !p_37272_.m_5833_() && p_37272_.m_6087_())) {
                if ($$1.m_20201_() != $$0.m_20201_()) continue;
                return false;
            }
        }
        return true;
    }

    public void m_6686_(double p_37266_, double p_37267_, double p_37268_, float p_37269_, float p_37270_) {
        Vec3 $$5 = new Vec3(p_37266_, p_37267_, p_37268_).m_82541_().m_82520_(this.f_19796_.m_216328_(0.0, 0.0172275 * (double)p_37270_), this.f_19796_.m_216328_(0.0, 0.0172275 * (double)p_37270_), this.f_19796_.m_216328_(0.0, 0.0172275 * (double)p_37270_)).m_82490_(p_37269_);
        this.m_20256_($$5);
        double $$6 = $$5.m_165924_();
        this.m_146922_((float)(Mth.m_14136_($$5.f_82479_, $$5.f_82481_) * 57.2957763671875));
        this.m_146926_((float)(Mth.m_14136_($$5.f_82480_, $$6) * 57.2957763671875));
        this.f_19859_ = this.m_146908_();
        this.f_19860_ = this.m_146909_();
    }

    public void m_37251_(Entity p_37252_, float p_37253_, float p_37254_, float p_37255_, float p_37256_, float p_37257_) {
        float $$6 = -Mth.m_14031_(p_37254_ * ((float)Math.PI / 180)) * Mth.m_14089_(p_37253_ * ((float)Math.PI / 180));
        float $$7 = -Mth.m_14031_((p_37253_ + p_37255_) * ((float)Math.PI / 180));
        float $$8 = Mth.m_14089_(p_37254_ * ((float)Math.PI / 180)) * Mth.m_14089_(p_37253_ * ((float)Math.PI / 180));
        this.m_6686_($$6, $$7, $$8, p_37256_, p_37257_);
        Vec3 $$9 = p_37252_.m_20184_();
        this.m_20256_(this.m_20184_().m_82520_($$9.f_82479_, p_37252_.m_20096_() ? 0.0 : $$9.f_82480_, $$9.f_82481_));
    }

    protected void m_6532_(HitResult p_37260_) {
        HitResult.Type $$1 = p_37260_.m_6662_();
        if ($$1 == HitResult.Type.ENTITY) {
            this.m_5790_((EntityHitResult)p_37260_);
            this.f_19853_.m_214171_(GameEvent.f_157777_, p_37260_.m_82450_(), GameEvent.Context.m_223719_(this, null));
        } else if ($$1 == HitResult.Type.BLOCK) {
            BlockHitResult $$2 = (BlockHitResult)p_37260_;
            this.m_8060_($$2);
            BlockPos $$3 = $$2.m_82425_();
            this.f_19853_.m_220407_(GameEvent.f_157777_, $$3, GameEvent.Context.m_223719_(this, this.f_19853_.m_8055_($$3)));
        }
    }

    protected void m_5790_(EntityHitResult p_37259_) {
    }

    protected void m_8060_(BlockHitResult p_37258_) {
        BlockState $$1 = this.f_19853_.m_8055_(p_37258_.m_82425_());
        $$1.m_60669_(this.f_19853_, $$1, p_37258_, this);
    }

    @Override
    public void m_6001_(double p_37279_, double p_37280_, double p_37281_) {
        this.m_20334_(p_37279_, p_37280_, p_37281_);
        if (this.f_19860_ == 0.0f && this.f_19859_ == 0.0f) {
            double $$3 = Math.sqrt(p_37279_ * p_37279_ + p_37281_ * p_37281_);
            this.m_146926_((float)(Mth.m_14136_(p_37280_, $$3) * 57.2957763671875));
            this.m_146922_((float)(Mth.m_14136_(p_37279_, p_37281_) * 57.2957763671875));
            this.f_19860_ = this.m_146909_();
            this.f_19859_ = this.m_146908_();
            this.m_7678_(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_146908_(), this.m_146909_());
        }
    }

    protected boolean m_5603_(Entity p_37250_) {
        if (p_37250_.m_5833_() || !p_37250_.m_6084_() || !p_37250_.m_6087_()) {
            return false;
        }
        Entity $$1 = this.m_37282_();
        return $$1 == null || this.f_37246_ || !$$1.m_20365_(p_37250_);
    }

    protected void m_37283_() {
        Vec3 $$0 = this.m_20184_();
        double $$1 = $$0.m_165924_();
        this.m_146926_(Projectile.m_37273_(this.f_19860_, (float)(Mth.m_14136_($$0.f_82480_, $$1) * 57.2957763671875)));
        this.m_146922_(Projectile.m_37273_(this.f_19859_, (float)(Mth.m_14136_($$0.f_82479_, $$0.f_82481_) * 57.2957763671875)));
    }

    protected static float m_37273_(float p_37274_, float p_37275_) {
        while (p_37275_ - p_37274_ < -180.0f) {
            p_37274_ -= 360.0f;
        }
        while (p_37275_ - p_37274_ >= 180.0f) {
            p_37274_ += 360.0f;
        }
        return Mth.m_14179_(0.2f, p_37274_, p_37275_);
    }

    @Override
    public Packet<?> m_5654_() {
        Entity $$0 = this.m_37282_();
        return new ClientboundAddEntityPacket(this, $$0 == null ? 0 : $$0.m_19879_());
    }

    @Override
    public void m_141965_(ClientboundAddEntityPacket p_150170_) {
        super.m_141965_(p_150170_);
        Entity $$1 = this.f_19853_.m_6815_(p_150170_.m_131509_());
        if ($$1 != null) {
            this.m_5602_($$1);
        }
    }

    @Override
    public boolean m_142265_(Level p_150167_, BlockPos p_150168_) {
        Entity $$2 = this.m_37282_();
        if ($$2 instanceof Player) {
            return $$2.m_142265_(p_150167_, p_150168_);
        }
        return $$2 == null || p_150167_.m_46469_().m_46207_(GameRules.f_46132_);
    }
}

