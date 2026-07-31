package ru.mot.ic2exfidelity.legacy;

import ic2.api.event.ExplosionEvent;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.EntityBasedExplosionDamageCalculator;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import ru.mot.ic2exfidelity.integration.RestoredLegacyContent;

/** The 1.12.2 IC2 dynamite projectile, adapted to the 1.19.2 entity API. */
public final class LegacyDynamiteEntity extends ThrowableItemProjectile {
    private boolean sticky;
    private boolean inGround;
    private BlockPos stickPos;
    private int ticksInGround;
    private int fuse = 100;

    public LegacyDynamiteEntity(EntityType<? extends LegacyDynamiteEntity> type, Level level) {
        super(type, level);
    }

    public LegacyDynamiteEntity(Level level, LivingEntity owner, boolean sticky) {
        super(RestoredLegacyContent.DYNAMITE_ENTITY.get(), owner, level);
        this.sticky = sticky;
        m_37446_(new ItemStack(sticky
                ? RestoredLegacyContent.DYNAMITE_STICKY.get()
                : RestoredLegacyContent.DYNAMITE.get()));
    }

    public LegacyDynamiteEntity(Level level, double x, double y, double z, boolean sticky) {
        super(RestoredLegacyContent.DYNAMITE_ENTITY.get(), x, y, z, level);
        this.sticky = sticky;
        m_37446_(new ItemStack(sticky
                ? RestoredLegacyContent.DYNAMITE_STICKY.get()
                : RestoredLegacyContent.DYNAMITE.get()));
    }

    public void setFuse(int fuse) {
        this.fuse = fuse;
    }

    public int getFuse() {
        return fuse;
    }

    public boolean isSticky() {
        return sticky;
    }

    @Override
    protected Item m_7881_() {
        return RestoredLegacyContent.DYNAMITE.get();
    }

    @Override
    protected boolean m_5603_(Entity entity) {
        // The original ray-traced blocks only; dynamite passes through entities.
        return false;
    }

    @Override
    public void m_8119_() {
        int previousFuse = fuse--;
        if (previousFuse <= 0) {
            m_146870_();
            if (!f_19853_.f_46443_) {
                explode();
            }
            return;
        }

        if (fuse < 100 && fuse % 2 == 0) {
            f_19853_.m_7106_(ParticleTypes.f_123762_,
                    m_20185_(), m_20186_() + 0.5D, m_20189_(), 0.0D, 0.0D, 0.0D);
        }

        if (inGround) {
            ticksInGround++;
            if (ticksInGround >= 200) {
                m_146870_();
                return;
            }
            if (sticky) {
                fuse -= 3;
                m_20334_(0.0D, 0.0D, 0.0D);
                if (stickPos != null && !f_19853_.m_8055_(stickPos).m_60795_()) {
                    return;
                }
            }
        }

        // A non-sticky charge (or a sticky charge whose support vanished)
        // leaves the collision state unless this tick hits another block.
        inGround = false;
        super.m_8119_();

        if (m_20069_()) {
            // IC2 adds 2000 ticks every submerged tick, effectively quenching it.
            fuse += 2_000;
        }

        if (!inGround) {
            ticksInGround = 0;
        }
    }

    @Override
    protected void m_8060_(BlockHitResult hit) {
        super.m_8060_(hit);
        Vec3 point = hit.m_82450_();
        Vec3 motion = m_20184_();
        double length = motion.m_82553_();
        if (length > 1.0E-7D) {
            m_6034_(
                    point.f_82479_ - motion.f_82479_ / length * 0.05D,
                    point.f_82480_ - motion.f_82480_ / length * 0.05D,
                    point.f_82481_ - motion.f_82481_ / length * 0.05D);
        }

        Direction face = hit.m_82434_();
        double x = motion.f_82479_;
        double y = motion.f_82480_;
        double z = motion.f_82481_;
        if (face.m_122434_() == Direction.Axis.X) {
            x *= -0.3D;
        } else if (face.m_122434_() == Direction.Axis.Y) {
            y *= -0.3D;
        } else {
            z *= -0.3D;
        }
        if (sticky) {
            x = 0.0D;
            y = 0.0D;
            z = 0.0D;
        } else {
            x *= 0.75D - f_19796_.m_188501_();
            z *= 0.75D - f_19796_.m_188501_();
        }
        m_20334_(x, y, z);
        stickPos = hit.m_82425_();
        inGround = true;
    }

    private void explode() {
        LivingEntity igniter = m_37282_() instanceof LivingEntity living ? living : null;
        ExplosionEvent event = new ExplosionEvent(
                f_19853_, this, new Vec3(m_20185_(), m_20186_(), m_20189_()),
                1.0D, igniter, 0, 1.0D);
        if (MinecraftForge.EVENT_BUS.post(event)) {
            return;
        }

        List<BlockPos> affected = new ArrayList<>(27);
        Explosion probe = new Explosion(f_19853_, this, m_20185_(), m_20186_(), m_20189_(), 1.0F);
        EntityBasedExplosionDamageCalculator resistance = new EntityBasedExplosionDamageCalculator(this);
        int centerX = (int) Math.floor(m_20185_());
        int centerY = (int) Math.floor(m_20186_());
        int centerZ = (int) Math.floor(m_20189_());
        for (int x = centerX - 1; x <= centerX + 1; x++) {
            for (int y = centerY - 1; y <= centerY + 1; y++) {
                for (int z = centerZ - 1; z <= centerZ + 1; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    BlockState state = f_19853_.m_8055_(pos);
                    float value = resistance.m_6617_(
                            probe, f_19853_, pos, state, state.m_60819_()).orElse(0.0F);
                    if (value < 10.0F) {
                        affected.add(pos);
                    }
                }
            }
        }

        Explosion explosion = new Explosion(
                f_19853_, this, m_20185_(), m_20186_(), m_20189_(),
                1.0F, false, Explosion.BlockInteraction.BREAK, affected);
        AABB damageBox = new AABB(
                m_20185_() - 2.0D, m_20186_() - 2.0D, m_20189_() - 2.0D,
                m_20185_() + 2.0D, m_20186_() + 2.0D, m_20189_() + 2.0D);
        DamageSource source = DamageSource.m_19358_(explosion);
        for (Entity entity : f_19853_.m_6249_(this, damageBox, candidate -> true)) {
            entity.m_6469_(source, 20.0F);
        }
        explosion.m_46075_(true);
    }

    @Override
    public void m_7380_(CompoundTag tag) {
        super.m_7380_(tag);
        tag.m_128379_("sticky", sticky);
        tag.m_128379_("inGround", inGround);
        tag.m_128405_("fuse", fuse);
        tag.m_128405_("ticksInGround", ticksInGround);
        if (stickPos != null) {
            tag.m_128405_("stickX", stickPos.m_123341_());
            tag.m_128405_("stickY", stickPos.m_123342_());
            tag.m_128405_("stickZ", stickPos.m_123343_());
        }
    }

    @Override
    public void m_7378_(CompoundTag tag) {
        super.m_7378_(tag);
        sticky = tag.m_128471_("sticky");
        inGround = tag.m_128471_("inGround");
        fuse = tag.m_128425_("fuse", 3) ? tag.m_128451_("fuse") : 100;
        ticksInGround = tag.m_128451_("ticksInGround");
        if (tag.m_128425_("stickX", 3)) {
            stickPos = new BlockPos(
                    tag.m_128451_("stickX"), tag.m_128451_("stickY"), tag.m_128451_("stickZ"));
        }
    }
}
