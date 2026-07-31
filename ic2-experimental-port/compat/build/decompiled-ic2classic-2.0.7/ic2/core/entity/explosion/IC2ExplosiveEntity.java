/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$MovementEmission
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.MoverType
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.network.NetworkHooks
 */
package ic2.core.entity.explosion;

import com.mojang.authlib.GameProfile;
import ic2.core.entity.explosion.IC2Explosion;
import java.util.Optional;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;

public class IC2ExplosiveEntity
extends Entity {
    private static final EntityDataAccessor<Integer> FUSE = SynchedEntityData.m_135353_(IC2ExplosiveEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Optional<BlockState>> BLOCK = SynchedEntityData.m_135353_(IC2ExplosiveEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135034_);
    protected DamageSource damageSource;
    protected GameProfile igniter;
    protected int fuse = 80;
    protected float power = 4.0f;
    protected float dropRate = 0.3f;

    public IC2ExplosiveEntity(EntityType<?> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
        this.f_19850_ = true;
    }

    public IC2ExplosiveEntity(EntityType<?> entityTypeIn, Level worldIn, double x, double y, double z, int fuse, float power, float dropRate, BlockState state, DamageSource source) {
        this(entityTypeIn, worldIn);
        this.m_6034_(x, y, z);
        this.setFuse(fuse);
        this.power = power;
        this.dropRate = dropRate;
        if (state != null) {
            this.f_19804_.m_135381_(BLOCK, Optional.of(state));
        }
        this.damageSource = source;
    }

    public IC2ExplosiveEntity setIgniter(Player player) {
        return player == null ? this : this.setIgniter(player.m_36316_());
    }

    public IC2ExplosiveEntity setIgniter(GameProfile pro) {
        this.igniter = pro;
        return this;
    }

    protected void m_8097_() {
        this.f_19804_.m_135372_(FUSE, (Object)80);
        this.f_19804_.m_135372_(BLOCK, Optional.of(Blocks.f_50493_.m_49966_()));
    }

    protected float m_6380_(Pose poseIn, EntityDimensions sizeIn) {
        return 0.0f;
    }

    protected Entity.MovementEmission m_142319_() {
        return Entity.MovementEmission.NONE;
    }

    public boolean m_6087_() {
        return this.m_6084_();
    }

    public void m_8119_() {
        super.m_8119_();
        this.f_19854_ = this.m_20185_();
        this.f_19855_ = this.m_20186_();
        this.f_19856_ = this.m_20189_();
        if (!this.m_20068_()) {
            this.m_20256_(this.m_20184_().m_82520_(0.0, -0.04, 0.0));
        }
        this.m_6478_(MoverType.SELF, this.m_20184_());
        this.m_20256_(this.m_20184_().m_82490_(0.98));
        if (this.f_19861_) {
            this.m_20256_(this.m_20184_().m_82542_(0.7, -0.5, 0.7));
        }
        --this.fuse;
        if (this.fuse <= 0) {
            this.m_142687_(Entity.RemovalReason.DISCARDED);
            if (!this.f_19853_.f_46443_) {
                this.explode();
            }
        } else {
            this.m_5844_();
            this.f_19853_.m_7106_((ParticleOptions)ParticleTypes.f_123762_, this.m_20185_(), this.m_20186_() + 0.5, this.m_20189_(), 0.0, 0.0, 0.0);
        }
    }

    private void explode() {
        IC2Explosion explosion = new IC2Explosion(this.f_19853_, this, this.m_20182_(), this.power, this.dropRate, this.damageSource).setIgniter(this.igniter);
        Optional state = (Optional)this.f_19804_.m_135370_(BLOCK);
        explosion.isBeacon = state.isPresent() && ((BlockState)state.get()).m_60734_() == Blocks.f_50273_;
        explosion.doExplosion();
    }

    protected void m_7378_(CompoundTag compound) {
        this.setFuse(compound.m_128451_("fuse"));
        this.dropRate = compound.m_128457_("drop");
        this.power = compound.m_128457_("power");
    }

    protected void m_7380_(CompoundTag compound) {
        compound.m_128405_("fuse", this.getFuseTime());
        compound.m_128350_("power", this.power);
        compound.m_128350_("drop", this.dropRate);
    }

    public void m_7350_(EntityDataAccessor<?> key) {
        super.m_7350_(key);
        if (key.equals(FUSE)) {
            this.fuse = (Integer)this.f_19804_.m_135370_(FUSE);
        }
    }

    public int getFuseTime() {
        return this.fuse;
    }

    public void setFuse(int fuse) {
        this.f_19804_.m_135381_(FUSE, (Object)fuse);
        this.fuse = fuse;
    }

    public BlockState getState() {
        return ((Optional)this.f_19804_.m_135370_(BLOCK)).orElse(Blocks.f_50493_.m_49966_());
    }

    public Packet<?> m_5654_() {
        return NetworkHooks.getEntitySpawningPacket((Entity)this);
    }
}

