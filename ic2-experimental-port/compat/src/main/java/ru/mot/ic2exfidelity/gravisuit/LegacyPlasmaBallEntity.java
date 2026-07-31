package ru.mot.ic2exfidelity.gravisuit;

import ic2.api.item.ElectricItem;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

/** Zero-gravity translocator/portal projectile from GraviSuite 2.2. */
public final class LegacyPlasmaBallEntity extends ThrowableProjectile {
    private LegacyRelocatorData target;
    private ItemStack relocator = ItemStack.f_41583_;

    public LegacyPlasmaBallEntity(
            EntityType<LegacyPlasmaBallEntity> type, Level level) {
        super(type, level);
    }

    public LegacyPlasmaBallEntity(
            Level level, Player shooter, LegacyRelocatorData target,
            InteractionHand hand) {
        super(LegacyGravisuitContent.PLASMA_BALL.get(), level);
        this.target = target;
        relocator = shooter.m_21120_(hand);
        m_5602_(shooter);
        double y = shooter.m_20186_() + shooter.m_20192_() - 0.1;
        double yaw = Math.toRadians(shooter.m_146908_());
        double pitch = Math.toRadians(shooter.m_146909_());
        m_6034_(shooter.m_20185_() - Math.cos(yaw) * 0.16,
                y, shooter.m_20189_() - Math.sin(yaw) * 0.16);
        setHeading(-Math.sin(yaw) * Math.cos(pitch),
                -Math.sin(pitch), Math.cos(yaw) * Math.cos(pitch), 1.0);
    }

    @Override
    protected float m_7139_() {
        return 0.0F;
    }

    private void setHeading(double x, double y, double z, double speed) {
        double factor = speed / Math.sqrt(x * x + y * y + z * z);
        m_20334_(x * factor, y * factor, z * factor);
        Vec3 motion = m_20184_();
        f_19859_ = (float) Math.toDegrees(Math.atan2(
                motion.m_7096_(), motion.m_7094_()));
        m_146922_(f_19859_);
        f_19860_ = (float) Math.toDegrees(Math.atan2(
                motion.m_7098_(), Math.sqrt(
                        motion.m_7096_() * motion.m_7096_()
                                + motion.m_7094_() * motion.m_7094_())));
        m_146926_(f_19860_);
    }

    @Override
    protected void m_8097_() {
    }

    @Override
    protected void m_5790_(EntityHitResult hit) {
        Entity entity = hit.m_82443_();
        if (!f_19853_.f_46443_
                && entity instanceof LivingEntity living
                && target != null
                && relocator.m_41720_() instanceof LegacyRelocatorItem item
                && relocator.m_41784_().m_128445_("mode") == 1) {
            item.teleportEntity(
                    living, target, entity.m_6374_(), relocator, false);
            m_146870_();
        }
    }

    @Override
    protected void m_8060_(BlockHitResult hit) {
        if (!f_19853_.f_46443_ && target != null
                && relocator.m_41720_() instanceof LegacyRelocatorItem
                && ElectricItem.manager.canUse(
                        relocator, LegacyRelocatorItem.PORTAL_COST)
                && relocator.m_41784_().m_128445_("mode") == 2) {
            createPortalPair(hit);
        }
        m_146870_();
    }

    private void createPortalPair(BlockHitResult hit) {
        BlockPos originPosition = hit.m_82425_().m_121945_(hit.m_82434_());
        f_19853_.m_7731_(originPosition,
                LegacyGravisuitContent.PLASMA_PORTAL.get().m_49966_(), 3);
        BlockEntity originEntity = f_19853_.m_7702_(originPosition);
        if (originEntity instanceof LegacyPlasmaPortalBlockEntity originPortal) {
            originPortal.setOtherEnd(target);
        }

        ServerLevel destinationLevel = target.resolve(m_20194_());
        if (destinationLevel == null) {
            return;
        }
        BlockPos targetPosition = target.blockPosition();
        destinationLevel.m_7731_(targetPosition,
                LegacyGravisuitContent.PLASMA_PORTAL.get().m_49966_(), 3);
        BlockEntity targetEntity = destinationLevel.m_7702_(targetPosition);
        LegacyRelocatorData origin = new LegacyRelocatorData(
                originPosition.m_121878_(),
                f_19853_.m_46472_().m_135782_().toString(), "origin");
        if (targetEntity instanceof LegacyPlasmaPortalBlockEntity targetPortal) {
            targetPortal.setOtherEnd(origin);
        }

        CompoundTag data = relocator.m_41784_();
        if (data.m_128403_("lastPosition")) {
            LegacyRelocatorData previous = LegacyRelocatorData.read(
                    data.m_128469_("lastPosition"), "lastPosition");
            ServerLevel previousLevel = previous.resolve(m_20194_());
            if (previousLevel != null
                    && previousLevel.m_8055_(previous.blockPosition()).m_60734_()
                            == LegacyGravisuitContent.PLASMA_PORTAL.get()) {
                previousLevel.m_7731_(previous.blockPosition(),
                        Blocks.f_50016_.m_49966_(), 3);
            }
        }
        data.m_128365_("lastPosition", origin.write());
    }

    @Override
    protected void m_7380_(CompoundTag tag) {
        super.m_7380_(tag);
        tag.m_128365_("relocator", relocator.m_41739_(new CompoundTag()));
        if (target != null) {
            CompoundTag targetTag = target.write();
            targetTag.m_128359_("name", target.name());
            tag.m_128365_("target", targetTag);
        }
    }

    @Override
    protected void m_7378_(CompoundTag tag) {
        super.m_7378_(tag);
        relocator = ItemStack.m_41712_(tag.m_128469_("relocator"));
        if (tag.m_128403_("target")) {
            CompoundTag targetTag = tag.m_128469_("target");
            target = LegacyRelocatorData.read(
                    targetTag, targetTag.m_128461_("name"));
        }
    }
}
