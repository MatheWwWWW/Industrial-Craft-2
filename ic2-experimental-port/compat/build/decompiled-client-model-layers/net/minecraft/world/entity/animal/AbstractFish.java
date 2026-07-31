/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.animal;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractFish
extends WaterAnimal
implements Bucketable {
    private static final EntityDataAccessor<Boolean> f_27458_ = SynchedEntityData.m_135353_(AbstractFish.class, EntityDataSerializers.f_135035_);

    public AbstractFish(EntityType<? extends AbstractFish> p_27461_, Level p_27462_) {
        super((EntityType<? extends WaterAnimal>)p_27461_, p_27462_);
        this.f_21342_ = new FishMoveControl(this);
    }

    @Override
    protected float m_6431_(Pose p_27474_, EntityDimensions p_27475_) {
        return p_27475_.f_20378_ * 0.65f;
    }

    public static AttributeSupplier.Builder m_27495_() {
        return Mob.m_21552_().m_22268_(Attributes.f_22276_, 3.0);
    }

    @Override
    public boolean m_8023_() {
        return super.m_8023_() || this.m_27487_();
    }

    @Override
    public boolean m_6785_(double p_27492_) {
        return !this.m_27487_() && !this.m_8077_();
    }

    @Override
    public int m_5792_() {
        return 8;
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_27458_, false);
    }

    @Override
    public boolean m_27487_() {
        return this.f_19804_.m_135370_(f_27458_);
    }

    @Override
    public void m_27497_(boolean p_27498_) {
        this.f_19804_.m_135381_(f_27458_, p_27498_);
    }

    @Override
    public void m_7380_(CompoundTag p_27485_) {
        super.m_7380_(p_27485_);
        p_27485_.m_128379_("FromBucket", this.m_27487_());
    }

    @Override
    public void m_7378_(CompoundTag p_27465_) {
        super.m_7378_(p_27465_);
        this.m_27497_(p_27465_.m_128471_("FromBucket"));
    }

    @Override
    protected void m_8099_() {
        super.m_8099_();
        this.f_21345_.m_25352_(0, new PanicGoal(this, 1.25));
        this.f_21345_.m_25352_(2, new AvoidEntityGoal<Player>(this, Player.class, 8.0f, 1.6, 1.4, EntitySelector.f_20408_::test));
        this.f_21345_.m_25352_(4, new FishSwimGoal(this));
    }

    @Override
    protected PathNavigation m_6037_(Level p_27480_) {
        return new WaterBoundPathNavigation(this, p_27480_);
    }

    @Override
    public void m_7023_(Vec3 p_27490_) {
        if (this.m_6142_() && this.m_20069_()) {
            this.m_19920_(0.01f, p_27490_);
            this.m_6478_(MoverType.SELF, this.m_20184_());
            this.m_20256_(this.m_20184_().m_82490_(0.9));
            if (this.m_5448_() == null) {
                this.m_20256_(this.m_20184_().m_82520_(0.0, -0.005, 0.0));
            }
        } else {
            super.m_7023_(p_27490_);
        }
    }

    @Override
    public void m_8107_() {
        if (!this.m_20069_() && this.f_19861_ && this.f_19863_) {
            this.m_20256_(this.m_20184_().m_82520_((this.f_19796_.m_188501_() * 2.0f - 1.0f) * 0.05f, 0.4f, (this.f_19796_.m_188501_() * 2.0f - 1.0f) * 0.05f));
            this.f_19861_ = false;
            this.f_19812_ = true;
            this.m_5496_(this.m_5699_(), this.m_6121_(), this.m_6100_());
        }
        super.m_8107_();
    }

    @Override
    protected InteractionResult m_6071_(Player p_27477_, InteractionHand p_27478_) {
        return Bucketable.m_148828_(p_27477_, p_27478_, this).orElse(super.m_6071_(p_27477_, p_27478_));
    }

    @Override
    public void m_6872_(ItemStack p_27494_) {
        Bucketable.m_148822_(this, p_27494_);
    }

    @Override
    public void m_142278_(CompoundTag p_148708_) {
        Bucketable.m_148825_(this, p_148708_);
    }

    @Override
    public SoundEvent m_142623_() {
        return SoundEvents.f_11782_;
    }

    protected boolean m_6004_() {
        return true;
    }

    protected abstract SoundEvent m_5699_();

    @Override
    protected SoundEvent m_5501_() {
        return SoundEvents.f_11938_;
    }

    @Override
    protected void m_7355_(BlockPos p_27482_, BlockState p_27483_) {
    }

    static class FishMoveControl
    extends MoveControl {
        private final AbstractFish f_27499_;

        FishMoveControl(AbstractFish p_27501_) {
            super(p_27501_);
            this.f_27499_ = p_27501_;
        }

        @Override
        public void m_8126_() {
            if (this.f_27499_.m_204029_(FluidTags.f_13131_)) {
                this.f_27499_.m_20256_(this.f_27499_.m_20184_().m_82520_(0.0, 0.005, 0.0));
            }
            if (this.f_24981_ != MoveControl.Operation.MOVE_TO || this.f_27499_.m_21573_().m_26571_()) {
                this.f_27499_.m_7910_(0.0f);
                return;
            }
            float $$0 = (float)(this.f_24978_ * this.f_27499_.m_21133_(Attributes.f_22279_));
            this.f_27499_.m_7910_(Mth.m_14179_(0.125f, this.f_27499_.m_6113_(), $$0));
            double $$1 = this.f_24975_ - this.f_27499_.m_20185_();
            double $$2 = this.f_24976_ - this.f_27499_.m_20186_();
            double $$3 = this.f_24977_ - this.f_27499_.m_20189_();
            if ($$2 != 0.0) {
                double $$4 = Math.sqrt($$1 * $$1 + $$2 * $$2 + $$3 * $$3);
                this.f_27499_.m_20256_(this.f_27499_.m_20184_().m_82520_(0.0, (double)this.f_27499_.m_6113_() * ($$2 / $$4) * 0.1, 0.0));
            }
            if ($$1 != 0.0 || $$3 != 0.0) {
                float $$5 = (float)(Mth.m_14136_($$3, $$1) * 57.2957763671875) - 90.0f;
                this.f_27499_.m_146922_(this.m_24991_(this.f_27499_.m_146908_(), $$5, 90.0f));
                this.f_27499_.f_20883_ = this.f_27499_.m_146908_();
            }
        }
    }

    static class FishSwimGoal
    extends RandomSwimmingGoal {
        private final AbstractFish f_27503_;

        public FishSwimGoal(AbstractFish p_27505_) {
            super(p_27505_, 1.0, 40);
            this.f_27503_ = p_27505_;
        }

        @Override
        public boolean m_8036_() {
            return this.f_27503_.m_6004_() && super.m_8036_();
        }
    }
}

