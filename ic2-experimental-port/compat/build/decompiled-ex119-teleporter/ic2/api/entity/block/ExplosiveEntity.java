/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientboundAddEntityPacket
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$MovementEmission
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.MoverType
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.Nullable
 */
package ic2.api.entity.block;

import ic2.core.Ic2Explosion;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public abstract class ExplosiveEntity
extends Entity {
    private static final EntityDataAccessor<Integer> FUSE = SynchedEntityData.m_135353_(ExplosiveEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private static final int DEFAULT_FUSE = 80;
    public LivingEntity causingEntity;
    public float explosivePower = 4.0f;
    public int radiationRange = 0;
    public float dropRate = 0.3f;
    public float damageVsEntities = 1.0f;
    public BlockState renderBlockState;

    public ExplosiveEntity(EntityType<? extends Entity> entityType, Level level, double d, double d2, double d3, int n, float f, float f2, float f3, BlockState blockState, int n2) {
        this(entityType, level);
        this.m_20248_(d, d2, d3);
        double d4 = Math.PI * 2 * level.f_46441_.m_188500_();
        this.m_20334_(-Math.sin(d4) * 0.02, 0.2, -Math.cos(d4) * 0.02);
        this.f_19854_ = d;
        this.f_19855_ = d2;
        this.f_19856_ = d3;
        this.setFuse(n);
        this.explosivePower = f;
        this.radiationRange = n2;
        this.dropRate = f2;
        this.damageVsEntities = f3;
        this.renderBlockState = blockState;
    }

    public ExplosiveEntity(EntityType<? extends Entity> entityType, Level level) {
        super(entityType, level);
        this.f_19850_ = true;
    }

    protected void m_8097_() {
        this.f_19804_.m_135372_(FUSE, (Object)80);
    }

    public boolean m_5829_() {
        return !this.m_213877_();
    }

    public void m_8119_() {
        if (!this.m_20068_()) {
            this.m_20256_(this.m_20184_().m_82520_(0.0, -0.04, 0.0));
        }
        this.m_6478_(MoverType.SELF, this.m_20184_());
        this.m_20256_(this.m_20184_().m_82490_(0.98));
        if (this.f_19861_) {
            this.m_20256_(this.m_20184_().m_82542_(0.7, -0.5, 0.7));
        }
        int n = this.getFuse() - 1;
        this.setFuse(n);
        if (n <= 0) {
            this.m_146870_();
            if (!this.f_19853_.f_46443_) {
                this.explode();
            }
        } else {
            this.m_20073_();
            if (this.f_19853_.f_46443_) {
                this.f_19853_.m_7106_((ParticleOptions)ParticleTypes.f_123762_, this.m_20185_(), this.m_20186_() + 0.5, this.m_20189_(), 0.0, 0.0, 0.0);
            }
        }
    }

    private void explode() {
        Ic2Explosion ic2Explosion = new Ic2Explosion(this.m_20193_(), this, this.m_20185_(), this.m_20186_(), this.m_20189_(), this.explosivePower, this.dropRate, this.radiationRange > 0 ? Ic2Explosion.Type.Nuclear : Ic2Explosion.Type.Normal, this.causingEntity, this.radiationRange);
        ic2Explosion.doExplosion();
    }

    @Nullable
    public LivingEntity getCausingEntity() {
        return this.causingEntity;
    }

    protected Entity.MovementEmission m_142319_() {
        return Entity.MovementEmission.NONE;
    }

    public boolean m_6087_() {
        return !this.m_213877_();
    }

    protected void m_7380_(CompoundTag compoundTag) {
        compoundTag.m_128376_("Fuse", (short)this.getFuse());
    }

    protected void m_7378_(CompoundTag compoundTag) {
        this.setFuse(compoundTag.m_128448_("Fuse"));
    }

    protected float m_6380_(Pose pose, EntityDimensions entityDimensions) {
        return 0.15f;
    }

    public void setFuse(int n) {
        this.f_19804_.m_135381_(FUSE, (Object)n);
    }

    public int getFuse() {
        return (Integer)this.f_19804_.m_135370_(FUSE);
    }

    public Packet<?> m_5654_() {
        return new ClientboundAddEntityPacket((Entity)this);
    }

    public ExplosiveEntity setCausingEntity(LivingEntity livingEntity) {
        this.causingEntity = livingEntity;
        return this;
    }
}

