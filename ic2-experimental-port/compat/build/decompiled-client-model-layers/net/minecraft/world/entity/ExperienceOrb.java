/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity;

import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddExperienceOrbPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ExperienceOrb
extends Entity {
    private static final int f_147073_ = 6000;
    private static final int f_147074_ = 20;
    private static final int f_147075_ = 8;
    private static final int f_147076_ = 40;
    private static final double f_147077_ = 0.5;
    private int f_20767_;
    private int f_20769_ = 5;
    private int f_20770_;
    private int f_147072_ = 1;
    private Player f_20771_;

    public ExperienceOrb(Level p_20776_, double p_20777_, double p_20778_, double p_20779_, int p_20780_) {
        this((EntityType<? extends ExperienceOrb>)EntityType.f_20570_, p_20776_);
        this.m_6034_(p_20777_, p_20778_, p_20779_);
        this.m_146922_((float)(this.f_19796_.m_188500_() * 360.0));
        this.m_20334_((this.f_19796_.m_188500_() * (double)0.2f - (double)0.1f) * 2.0, this.f_19796_.m_188500_() * 0.2 * 2.0, (this.f_19796_.m_188500_() * (double)0.2f - (double)0.1f) * 2.0);
        this.f_20770_ = p_20780_;
    }

    public ExperienceOrb(EntityType<? extends ExperienceOrb> p_20773_, Level p_20774_) {
        super(p_20773_, p_20774_);
    }

    @Override
    protected Entity.MovementEmission m_142319_() {
        return Entity.MovementEmission.NONE;
    }

    @Override
    protected void m_8097_() {
    }

    @Override
    public void m_8119_() {
        Vec3 $$0;
        double $$1;
        super.m_8119_();
        this.f_19854_ = this.m_20185_();
        this.f_19855_ = this.m_20186_();
        this.f_19856_ = this.m_20189_();
        if (this.m_204029_(FluidTags.f_13131_)) {
            this.m_20803_();
        } else if (!this.m_20068_()) {
            this.m_20256_(this.m_20184_().m_82520_(0.0, -0.03, 0.0));
        }
        if (this.f_19853_.m_6425_(this.m_20183_()).m_205070_(FluidTags.f_13132_)) {
            this.m_20334_((this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.2f, 0.2f, (this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.2f);
        }
        if (!this.f_19853_.m_45772_(this.m_20191_())) {
            this.m_20314_(this.m_20185_(), (this.m_20191_().f_82289_ + this.m_20191_().f_82292_) / 2.0, this.m_20189_());
        }
        if (this.f_19797_ % 20 == 1) {
            this.m_147103_();
        }
        if (this.f_20771_ != null && (this.f_20771_.m_5833_() || this.f_20771_.m_21224_())) {
            this.f_20771_ = null;
        }
        if (this.f_20771_ != null && ($$1 = ($$0 = new Vec3(this.f_20771_.m_20185_() - this.m_20185_(), this.f_20771_.m_20186_() + (double)this.f_20771_.m_20192_() / 2.0 - this.m_20186_(), this.f_20771_.m_20189_() - this.m_20189_())).m_82556_()) < 64.0) {
            double $$2 = 1.0 - Math.sqrt($$1) / 8.0;
            this.m_20256_(this.m_20184_().m_82549_($$0.m_82541_().m_82490_($$2 * $$2 * 0.1)));
        }
        this.m_6478_(MoverType.SELF, this.m_20184_());
        float $$3 = 0.98f;
        if (this.f_19861_) {
            $$3 = this.f_19853_.m_8055_(new BlockPos(this.m_20185_(), this.m_20186_() - 1.0, this.m_20189_())).m_60734_().m_49958_() * 0.98f;
        }
        this.m_20256_(this.m_20184_().m_82542_($$3, 0.98, $$3));
        if (this.f_19861_) {
            this.m_20256_(this.m_20184_().m_82542_(1.0, -0.9, 1.0));
        }
        ++this.f_20767_;
        if (this.f_20767_ >= 6000) {
            this.m_146870_();
        }
    }

    private void m_147103_() {
        if (this.f_20771_ == null || this.f_20771_.m_20280_(this) > 64.0) {
            this.f_20771_ = this.f_19853_.m_45930_(this, 8.0);
        }
        if (this.f_19853_ instanceof ServerLevel) {
            List<ExperienceOrb> $$0 = this.f_19853_.m_142425_(EntityTypeTest.m_156916_(ExperienceOrb.class), this.m_20191_().m_82400_(0.5), this::m_147086_);
            for (ExperienceOrb $$1 : $$0) {
                this.m_147100_($$1);
            }
        }
    }

    public static void m_147082_(ServerLevel p_147083_, Vec3 p_147084_, int p_147085_) {
        while (p_147085_ > 0) {
            int $$3 = ExperienceOrb.m_20782_(p_147085_);
            p_147085_ -= $$3;
            if (ExperienceOrb.m_147096_(p_147083_, p_147084_, $$3)) continue;
            p_147083_.m_7967_(new ExperienceOrb(p_147083_, p_147084_.m_7096_(), p_147084_.m_7098_(), p_147084_.m_7094_(), $$3));
        }
    }

    private static boolean m_147096_(ServerLevel p_147097_, Vec3 p_147098_, int p_147099_) {
        AABB $$3 = AABB.m_165882_(p_147098_, 1.0, 1.0, 1.0);
        int $$4 = p_147097_.m_213780_().m_188503_(40);
        List<ExperienceOrb> $$5 = p_147097_.m_142425_(EntityTypeTest.m_156916_(ExperienceOrb.class), $$3, p_147081_ -> ExperienceOrb.m_147088_(p_147081_, $$4, p_147099_));
        if (!$$5.isEmpty()) {
            ExperienceOrb $$6 = $$5.get(0);
            ++$$6.f_147072_;
            $$6.f_20767_ = 0;
            return true;
        }
        return false;
    }

    private boolean m_147086_(ExperienceOrb p_147087_) {
        return p_147087_ != this && ExperienceOrb.m_147088_(p_147087_, this.m_19879_(), this.f_20770_);
    }

    private static boolean m_147088_(ExperienceOrb p_147089_, int p_147090_, int p_147091_) {
        return !p_147089_.m_213877_() && (p_147089_.m_19879_() - p_147090_) % 40 == 0 && p_147089_.f_20770_ == p_147091_;
    }

    private void m_147100_(ExperienceOrb p_147101_) {
        this.f_147072_ += p_147101_.f_147072_;
        this.f_20767_ = Math.min(this.f_20767_, p_147101_.f_20767_);
        p_147101_.m_146870_();
    }

    private void m_20803_() {
        Vec3 $$0 = this.m_20184_();
        this.m_20334_($$0.f_82479_ * (double)0.99f, Math.min($$0.f_82480_ + (double)5.0E-4f, (double)0.06f), $$0.f_82481_ * (double)0.99f);
    }

    @Override
    protected void m_5841_() {
    }

    @Override
    public boolean m_6469_(DamageSource p_20785_, float p_20786_) {
        if (this.m_6673_(p_20785_)) {
            return false;
        }
        if (this.f_19853_.f_46443_) {
            return true;
        }
        this.m_5834_();
        this.f_20769_ = (int)((float)this.f_20769_ - p_20786_);
        if (this.f_20769_ <= 0) {
            this.m_146870_();
        }
        return true;
    }

    @Override
    public void m_7380_(CompoundTag p_20796_) {
        p_20796_.m_128376_("Health", (short)this.f_20769_);
        p_20796_.m_128376_("Age", (short)this.f_20767_);
        p_20796_.m_128376_("Value", (short)this.f_20770_);
        p_20796_.m_128405_("Count", this.f_147072_);
    }

    @Override
    public void m_7378_(CompoundTag p_20788_) {
        this.f_20769_ = p_20788_.m_128448_("Health");
        this.f_20767_ = p_20788_.m_128448_("Age");
        this.f_20770_ = p_20788_.m_128448_("Value");
        this.f_147072_ = Math.max(p_20788_.m_128451_("Count"), 1);
    }

    @Override
    public void m_6123_(Player p_20792_) {
        if (this.f_19853_.f_46443_) {
            return;
        }
        if (p_20792_.f_36101_ == 0) {
            p_20792_.f_36101_ = 2;
            p_20792_.m_7938_(this, 1);
            int $$1 = this.m_147092_(p_20792_, this.f_20770_);
            if ($$1 > 0) {
                p_20792_.m_6756_($$1);
            }
            --this.f_147072_;
            if (this.f_147072_ == 0) {
                this.m_146870_();
            }
        }
    }

    private int m_147092_(Player p_147093_, int p_147094_) {
        Map.Entry<EquipmentSlot, ItemStack> $$2 = EnchantmentHelper.m_44839_(Enchantments.f_44962_, p_147093_, ItemStack::m_41768_);
        if ($$2 != null) {
            ItemStack $$3 = $$2.getValue();
            int $$4 = Math.min(this.m_20798_(this.f_20770_), $$3.m_41773_());
            $$3.m_41721_($$3.m_41773_() - $$4);
            int $$5 = p_147094_ - this.m_20793_($$4);
            if ($$5 > 0) {
                return this.m_147092_(p_147093_, $$5);
            }
            return 0;
        }
        return p_147094_;
    }

    private int m_20793_(int p_20794_) {
        return p_20794_ / 2;
    }

    private int m_20798_(int p_20799_) {
        return p_20799_ * 2;
    }

    public int m_20801_() {
        return this.f_20770_;
    }

    public int m_20802_() {
        if (this.f_20770_ >= 2477) {
            return 10;
        }
        if (this.f_20770_ >= 1237) {
            return 9;
        }
        if (this.f_20770_ >= 617) {
            return 8;
        }
        if (this.f_20770_ >= 307) {
            return 7;
        }
        if (this.f_20770_ >= 149) {
            return 6;
        }
        if (this.f_20770_ >= 73) {
            return 5;
        }
        if (this.f_20770_ >= 37) {
            return 4;
        }
        if (this.f_20770_ >= 17) {
            return 3;
        }
        if (this.f_20770_ >= 7) {
            return 2;
        }
        if (this.f_20770_ >= 3) {
            return 1;
        }
        return 0;
    }

    public static int m_20782_(int p_20783_) {
        if (p_20783_ >= 2477) {
            return 2477;
        }
        if (p_20783_ >= 1237) {
            return 1237;
        }
        if (p_20783_ >= 617) {
            return 617;
        }
        if (p_20783_ >= 307) {
            return 307;
        }
        if (p_20783_ >= 149) {
            return 149;
        }
        if (p_20783_ >= 73) {
            return 73;
        }
        if (p_20783_ >= 37) {
            return 37;
        }
        if (p_20783_ >= 17) {
            return 17;
        }
        if (p_20783_ >= 7) {
            return 7;
        }
        if (p_20783_ >= 3) {
            return 3;
        }
        return 1;
    }

    @Override
    public boolean m_6097_() {
        return false;
    }

    @Override
    public Packet<?> m_5654_() {
        return new ClientboundAddExperienceOrbPacket(this);
    }

    @Override
    public SoundSource m_5720_() {
        return SoundSource.AMBIENT;
    }
}

