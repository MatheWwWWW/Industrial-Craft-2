/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.MoverType
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ClipContext
 *  net.minecraft.world.level.ClipContext$Block
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.network.NetworkHooks
 */
package ic2.core.entity.explosion;

import ic2.core.IC2;
import ic2.core.entity.explosion.PointExplosion;
import ic2.core.entity.renderer.IRenderableItem;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.utils.math.geometry.Box;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

public class DynamiteEntity
extends Entity
implements IRenderableItem {
    public static final EntityDataAccessor<ItemStack> ITEM = SynchedEntityData.m_135353_(DynamiteEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135033_);
    public static final EntityDataAccessor<Boolean> STUCK = SynchedEntityData.m_135353_(DynamiteEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    public static final EntityDataAccessor<Boolean> STICKY = SynchedEntityData.m_135353_(DynamiteEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private int fuse = 100;
    private int ticksInGround = 0;
    private Vec3 stuckPosition;
    private BlockPos stuckBlock;

    public DynamiteEntity(EntityType<?> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    public DynamiteEntity(EntityType<?> entityTypeIn, Level worldIn, double x, double y, double z, BlockPos pos) {
        super(entityTypeIn, worldIn);
        this.m_6034_(x, y, z);
        this.setSticky(pos != null);
        this.setISStuck(pos != null);
        this.stuckPosition = new Vec3(x, y, z);
        this.stuckBlock = pos;
    }

    public DynamiteEntity(EntityType<?> entityTypeIn, Level worldIn, LivingEntity entityLiving) {
        this(entityTypeIn, worldIn);
        this.m_6034_(entityLiving.m_20185_() - (double)(Mth.m_14089_((float)(entityLiving.m_146908_() / 180.0f * 3.141593f)) * 0.16f), entityLiving.m_20186_() + (double)entityLiving.m_20192_(), entityLiving.m_20189_() - (double)(Mth.m_14031_((float)(entityLiving.m_146908_() / 180.0f * 3.141593f)) * 0.16f));
        this.m_20334_(-Mth.m_14031_((float)(entityLiving.m_146908_() / 180.0f * 3.141593f)) * Mth.m_14089_((float)(entityLiving.m_146909_() / 180.0f * 3.141593f)), -Mth.m_14031_((float)(entityLiving.m_146909_() / 180.0f * 3.141593f)), Mth.m_14089_((float)(entityLiving.m_146908_() / 180.0f * 3.141593f)) * Mth.m_14089_((float)(entityLiving.m_146909_() / 180.0f * 3.141593f)));
        Vec3 motion = this.m_20184_();
        this.shoot(motion.f_82479_, motion.f_82480_, motion.f_82481_, 1.0f, 1.0f);
    }

    protected void m_7378_(CompoundTag compound) {
        this.fuse = compound.m_128451_("fuse");
        this.setISStuck(compound.m_128471_("stuck"));
        this.f_19804_.m_135381_(STICKY, (Object)compound.m_128471_("sticky"));
        this.f_19797_ = compound.m_128451_("in_ground");
        CompoundTag nbt = compound.m_128469_("stuck_at");
        this.stuckPosition = nbt.m_128456_() ? null : new Vec3(nbt.m_128459_("x"), nbt.m_128459_("y"), nbt.m_128459_("z"));
        this.stuckBlock = this.stuckPosition == null ? null : BlockPos.m_122022_((long)nbt.m_128454_("pos"));
    }

    protected void m_7380_(CompoundTag compound) {
        compound.m_128405_("fuse", this.fuse);
        compound.m_128379_("stuck", this.isStuck());
        compound.m_128379_("sticky", ((Boolean)this.f_19804_.m_135370_(STICKY)).booleanValue());
        if (this.stuckPosition != null) {
            CompoundTag nbt = new CompoundTag();
            nbt.m_128347_("x", this.stuckPosition.m_7096_());
            nbt.m_128347_("y", this.stuckPosition.m_7098_());
            nbt.m_128347_("z", this.stuckPosition.m_7094_());
            nbt.m_128356_("pos", this.stuckBlock.m_121878_());
            compound.m_128365_("stuck_at", (Tag)nbt);
        }
        compound.m_128405_("in_ground", this.ticksInGround);
    }

    public DynamiteEntity setSticky(boolean sticky) {
        this.f_19804_.m_135381_(STICKY, (Object)sticky);
        return this;
    }

    public DynamiteEntity setItem(ItemStack stack) {
        this.f_19804_.m_135381_(ITEM, (Object)stack.m_41777_());
        return this;
    }

    public DynamiteEntity setFuse(int fuse) {
        this.fuse = fuse;
        return this;
    }

    public void setISStuck(boolean isStuck) {
        this.f_19804_.m_135381_(STUCK, (Object)isStuck);
    }

    public boolean isStuck() {
        return (Boolean)this.f_19804_.m_135370_(STUCK);
    }

    public boolean isSticky() {
        return (Boolean)this.f_19804_.m_135370_(STICKY);
    }

    @Override
    public ItemStack getStack() {
        return (ItemStack)this.f_19804_.m_135370_(ITEM);
    }

    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        float f2 = (float)Math.sqrt(x * x + y * y + z * z);
        float effect = 0.0075f * inaccuracy;
        this.m_20334_((x / (double)f2 + this.f_19796_.m_188583_() * (double)effect) * (double)velocity, (y / (double)f2 + this.f_19796_.m_188583_() * (double)effect) * (double)velocity, (z / (double)f2 + this.f_19796_.m_188583_() * (double)effect) * (double)velocity);
        x = this.m_20184_().m_7096_();
        z = this.m_20184_().m_7094_();
        float n = (float)(Math.atan2(x, z) * 180.0 / Math.PI);
        this.m_146922_(n);
        this.f_19859_ = n;
        float n2 = (float)(Math.atan2(y, (float)Math.sqrt(x * x + z * z)) * 180.0 / Math.PI);
        this.m_146926_(n2);
        this.f_19860_ = n2;
        this.ticksInGround = 0;
    }

    @OnlyIn(value=Dist.CLIENT)
    public void m_6001_(double x, double y, double z) {
        super.m_6001_(x, y, z);
        if (this.f_19860_ == 0.0f && this.f_19859_ == 0.0f) {
            float f = (float)Math.sqrt(x * x + z * z);
            float n = (float)(Math.atan2(x, z) * 180.0 / 3.141592741012573);
            this.m_146922_(n);
            this.f_19859_ = n;
            this.m_146926_((float)(Math.atan2(y, f) * 180.0 / 3.141592741012573));
            this.f_19860_ = this.m_146909_();
            this.m_7678_(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_146908_(), this.m_146909_());
            this.ticksInGround = 0;
        }
    }

    protected void m_8097_() {
        this.f_19804_.m_135372_(ITEM, (Object)new ItemStack((ItemLike)IC2Blocks.DYNAMITE));
        this.f_19804_.m_135372_(STUCK, (Object)false);
        this.f_19804_.m_135372_(STICKY, (Object)false);
    }

    public void explode() {
        PointExplosion explosion = new PointExplosion(this.f_19853_, this, this.m_20183_(), 1.0f, 1.0f, 0.8f);
        explosion.doExplosionA(new Box(-1, -1, -1, 1, 1, 1));
        explosion.doExplosionB(true);
    }

    public void m_8119_() {
        Vec3 end;
        Vec3 motion;
        super.m_8119_();
        if (this.f_19860_ == 0.0f && this.f_19859_ == 0.0f) {
            motion = this.m_20184_();
            float n = (float)(Math.atan2(motion.f_82479_, motion.f_82481_) * 180.0 / 3.141592741012573);
            this.m_146922_(n);
            this.f_19859_ = n;
            float n2 = (float)(Math.atan2(motion.f_82480_, (float)Math.sqrt(motion.f_82479_ * motion.f_82479_ + motion.f_82481_ * motion.f_82481_)) * 180.0 / 3.141592741012573);
            this.m_146926_(n2);
            this.f_19860_ = n2;
        }
        if (this.fuse-- <= 0) {
            this.explode();
            this.m_142687_(Entity.RemovalReason.DISCARDED);
        } else if (this.fuse < 100 && this.fuse % 2 == 0) {
            this.f_19853_.m_7106_((ParticleOptions)ParticleTypes.f_123762_, this.m_20185_(), this.m_20186_() + 0.5, this.m_20189_(), 0.0, 0.0, 0.0);
        }
        if (this.isStuck()) {
            ++this.ticksInGround;
            if (this.ticksInGround >= 200) {
                this.m_142687_(Entity.RemovalReason.DISCARDED);
            }
            if (this.isSticky()) {
                this.fuse -= 3;
                this.m_20334_(0.0, 0.0, 0.0);
                if (IC2.PLATFORM.isRendering()) {
                    return;
                }
                if (this.stuckPosition != null) {
                    this.m_6034_(this.stuckPosition.m_7096_(), this.stuckPosition.m_7098_(), this.stuckPosition.m_7094_());
                    if (this.f_19853_.m_8055_(this.stuckBlock).m_60734_() != Blocks.f_50016_) {
                        return;
                    }
                    this.stuckBlock = null;
                    this.stuckPosition = null;
                    this.setISStuck(false);
                }
            }
        }
        motion = this.m_20184_();
        Vec3 start = this.m_20182_();
        BlockHitResult raceTrace = this.f_19853_.m_45547_(new ClipContext(start, end = start.m_82549_(motion), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, (Entity)this));
        if (raceTrace.m_6662_() == HitResult.Type.BLOCK) {
            float remainX = (float)(raceTrace.m_82450_().f_82479_ - this.m_20185_());
            float remainY = (float)(raceTrace.m_82450_().f_82480_ - this.m_20186_());
            float remainZ = (float)(raceTrace.m_82450_().f_82481_ - this.m_20189_());
            float f2 = Mth.m_14116_((float)(remainX * remainX + remainY * remainY + remainZ * remainZ));
            this.stuckBlock = raceTrace.m_82425_().m_7949_();
            this.stuckPosition = this.m_20182_();
            this.m_6478_(MoverType.SELF, new Vec3((double)(-(remainX / f2 * 0.05f - remainX)), (double)(-(remainY / f2 * 0.05f - remainY)), (double)(-(remainZ / f2 * 0.05f - remainZ))));
            motion = motion.m_82559_(new Vec3(0.75 - (double)this.f_19796_.m_188501_(), -0.3, 0.75 - (double)this.f_19796_.m_188501_()));
            this.setISStuck(true);
        } else {
            this.m_6478_(MoverType.SELF, motion);
            this.setISStuck(false);
        }
        float f3 = (float)Math.sqrt(motion.f_82479_ * motion.f_82479_ + motion.f_82481_ * motion.f_82481_);
        this.m_146922_((float)(Math.atan2(motion.f_82479_, motion.f_82481_) * 180.0 / 3.141592741012573));
        this.m_146926_((float)(Math.atan2(motion.f_82480_, f3) * 180.0 / 3.141592741012573));
        if (this.m_146909_() - this.f_19860_ < -180.0f) {
            this.f_19860_ -= 360.0f;
        }
        if (this.m_146909_() - this.f_19860_ >= 180.0f) {
            this.f_19860_ += 360.0f;
        }
        if (this.m_146908_() - this.f_19859_ < -180.0f) {
            this.f_19859_ -= 360.0f;
        }
        if (this.m_146908_() - this.f_19859_ >= 180.0f) {
            this.f_19859_ += 360.0f;
        }
        this.m_146926_(this.f_19860_ + (this.m_146909_() - this.f_19860_) * 0.2f);
        this.m_146922_(this.f_19859_ + (this.m_146908_() - this.f_19859_) * 0.2f);
        float f4 = 0.98f;
        float f5 = 0.04f;
        if (this.m_20069_()) {
            this.fuse += 2000;
            for (int i1 = 0; i1 < 4; ++i1) {
                float f6 = 0.25f;
                this.f_19853_.m_7106_((ParticleOptions)ParticleTypes.f_123795_, this.m_20185_() - motion.f_82479_ * (double)f6, this.m_20186_() - motion.f_82480_ * (double)f6, this.m_20189_() - motion.f_82481_ * (double)f6, motion.f_82479_, motion.f_82480_, motion.f_82481_);
            }
            f4 = 0.75f;
        }
        this.m_20256_(motion.m_82542_((double)f4, (double)f4, (double)f4).m_82492_(0.0, (double)f5, 0.0));
        this.m_6034_(this.m_20185_(), this.m_20186_(), this.m_20189_());
    }

    public Packet<?> m_5654_() {
        return NetworkHooks.getEntitySpawningPacket((Entity)this);
    }
}

