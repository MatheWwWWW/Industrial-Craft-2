/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.LongIterator
 *  it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ClipContext
 *  net.minecraft.world.level.ClipContext$Block
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.Explosion
 *  net.minecraft.world.level.Explosion$BlockInteraction
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.entity.explosion;

import ic2.core.IC2;
import ic2.core.utils.math.geometry.Box;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class PointExplosion {
    Level world;
    BlockPos position;
    Entity exploder;
    RandomSource rand;
    float power;
    float dropRate;
    float damage;
    LongSet toDestroy = new LongLinkedOpenHashSet();
    Explosion explosion;
    DamageSource source;

    public PointExplosion(Level world, Entity entity, BlockPos pos, float power, float drop, float entityDamage) {
        this.rand = world.f_46441_;
        this.world = world;
        this.exploder = entity;
        this.power = power;
        this.dropRate = drop;
        this.damage = entityDamage;
        this.position = pos.m_7918_(pos.m_123341_() < 0 ? -1 : 0, 0, pos.m_123343_() < 0 ? -1 : 0);
        this.explosion = new Explosion(world, entity, DamageSource.f_19318_, null, (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), power, false, Explosion.BlockInteraction.BREAK);
        this.source = DamageSource.m_19358_((Explosion)this.explosion);
    }

    public void doExplosionA(Box box) {
        for (BlockPos pos : box.offset((Vec3i)this.position)) {
            if (this.world.m_46859_(pos) || !(this.power >= this.world.m_8055_(pos).getExplosionResistance((BlockGetter)this.world, pos, this.explosion) / 50.0f)) continue;
            this.toDestroy.add(pos.m_121878_());
        }
        this.power *= 2.0f;
        Vec3 Vector3d = new Vec3((double)this.position.m_123341_(), (double)this.position.m_123342_(), (double)this.position.m_123343_());
        for (Entity entity : this.world.m_45933_(this.exploder, new AABB(this.position).m_82400_((double)(this.power + 1.0f)))) {
            if (entity instanceof ItemEntity || !(Math.sqrt(entity.m_20275_((double)this.position.m_123341_(), (double)this.position.m_123342_(), (double)this.position.m_123343_())) / (double)this.power <= 1.0)) continue;
            double x = entity.m_20185_() - (double)this.position.m_123341_();
            double y = entity.m_20186_() - (double)this.position.m_123342_();
            double z = entity.m_20189_() - (double)this.position.m_123343_();
            double d5 = Math.sqrt(x * x + y * y + z * z);
            double d7 = (1.0 - (z /= d5)) * (double)this.getDensity(Vector3d, entity.m_20191_());
            entity.m_6469_(this.source, (float)((int)(((d7 * d7 + d7) / 16.0 * (double)this.power + 1.0) * (double)this.damage)));
            entity.m_20256_(entity.m_20184_().m_82520_((x /= d5) * d7, (y /= d5) * d7, z * d7));
        }
    }

    public float getDensity(Vec3 vec, AABB box) {
        double d0 = 1.0 / ((box.f_82291_ - box.f_82288_) * 2.0 + 1.0);
        double d1 = 1.0 / ((box.f_82292_ - box.f_82289_) * 2.0 + 1.0);
        double d2 = 1.0 / ((box.f_82293_ - box.f_82290_) * 2.0 + 1.0);
        double d3 = (1.0 - Math.floor(1.0 / d0) * d0) / 2.0;
        double d4 = (1.0 - Math.floor(1.0 / d2) * d2) / 2.0;
        if (d0 >= 0.0 && d1 >= 0.0 && d2 >= 0.0) {
            int j2 = 0;
            int k2 = 0;
            float f = 0.0f;
            while (f <= 1.0f) {
                float f1 = 0.0f;
                while (f1 <= 1.0f) {
                    float f2 = 0.0f;
                    while (f2 <= 1.0f) {
                        double d5 = box.f_82288_ + (box.f_82291_ - box.f_82288_) * (double)f;
                        double d6 = box.f_82289_ + (box.f_82292_ - box.f_82289_) * (double)f1;
                        double d7 = box.f_82290_ + (box.f_82293_ - box.f_82290_) * (double)f2;
                        if (this.world.m_45547_(new ClipContext(new Vec3(d5 + d3, d6, d7 + d4), vec, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this.exploder)).m_6662_() == HitResult.Type.MISS) {
                            ++j2;
                        }
                        ++k2;
                        f2 = (float)((double)f2 + d2);
                    }
                    f1 = (float)((double)f1 + d1);
                }
                f = (float)((double)f + d0);
            }
            return (float)j2 / (float)k2;
        }
        return 0.0f;
    }

    public void doExplosionB(boolean spawnParticles) {
        this.world.m_5594_(null, this.position, SoundEvents.f_11913_, SoundSource.AMBIENT, 4.0f, (1.0f + (this.world.f_46441_.m_188501_() - this.world.f_46441_.m_188501_()) * 0.2f) * 0.7f);
        boolean isServer = IC2.PLATFORM.isSimulating();
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        LongIterator longIterator = this.toDestroy.iterator();
        while (longIterator.hasNext()) {
            long entry = (Long)longIterator.next();
            pos.m_122188_(entry);
            if (spawnParticles) {
                double baseX = (float)pos.m_123341_() + this.rand.m_188501_();
                double baseY = (float)pos.m_123342_() + this.rand.m_188501_();
                double baseZ = (float)pos.m_123343_() + this.rand.m_188501_();
                double x = baseX - (double)this.position.m_123341_();
                double y = baseY - (double)this.position.m_123342_();
                double z = baseZ - (double)this.position.m_123343_();
                double d7 = Math.sqrt(x * x + y * y + z * z);
                x /= d7;
                y /= d7;
                z /= d7;
                double d8 = 0.5 / (d7 / (double)this.power + 0.1) * (double)this.rand.m_188501_() * (double)this.rand.m_188501_() + (double)0.3f;
                this.world.m_7106_((ParticleOptions)ParticleTypes.f_123813_, (baseX + (double)this.position.m_123341_() * 1.0) / 2.0, (baseY + (double)this.position.m_123342_() * 1.0) / 2.0, (baseZ + (double)this.position.m_123343_() * 1.0) / 2.0, x *= d8, y *= d8, z *= d8);
                this.world.m_7106_((ParticleOptions)ParticleTypes.f_123762_, baseX, baseY, baseZ, x, y, z);
            }
            if (!isServer || this.world.m_46859_((BlockPos)pos)) continue;
            BlockState state = this.world.m_8055_((BlockPos)pos);
            if (state.canDropFromExplosion((BlockGetter)this.world, (BlockPos)pos, this.explosion)) {
                Block.m_49892_((BlockState)state, (LevelAccessor)this.world, (BlockPos)pos, (BlockEntity)this.world.m_7702_((BlockPos)pos));
            }
            state.onBlockExploded(this.world, (BlockPos)pos, this.explosion);
        }
    }
}

