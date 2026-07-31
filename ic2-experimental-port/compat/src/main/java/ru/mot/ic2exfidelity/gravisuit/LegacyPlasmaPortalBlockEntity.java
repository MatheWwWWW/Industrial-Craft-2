package ru.mot.ic2exfidelity.gravisuit;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/** Queues collisions for safe end-of-tick teleport and expires after 500 ticks. */
public final class LegacyPlasmaPortalBlockEntity extends BlockEntity {
    private LegacyRelocatorData otherEnd;
    private final List<LivingEntity> entitiesToTeleport = new ArrayList<>();
    private int ticker;

    public LegacyPlasmaPortalBlockEntity(BlockPos position, BlockState state) {
        super(LegacyGravisuitContent.PLASMA_PORTAL_BLOCK_ENTITY.get(),
                position, state);
    }

    public void setOtherEnd(LegacyRelocatorData otherEnd) {
        this.otherEnd = otherEnd;
        m_6596_();
    }

    public boolean hasTarget() {
        return otherEnd != null;
    }

    public void addEntityToTeleport(LivingEntity entity) {
        entitiesToTeleport.add(entity);
    }

    public static void tick(
            Level level, BlockPos position, BlockState state,
            LegacyPlasmaPortalBlockEntity portal) {
        portal.ticker++;
        if (portal.otherEnd != null && !portal.entitiesToTeleport.isEmpty()) {
            List<LivingEntity> queued = new ArrayList<>(portal.entitiesToTeleport);
            portal.entitiesToTeleport.clear();
            for (LivingEntity entity : queued) {
                ServerLevel destination = portal.otherEnd.resolve(entity.m_20194_());
                if (destination != null
                        && LegacyTeleportUtil.getWeightOfEntity(entity, true) != 0) {
                    LegacyTeleportUtil.teleportEntity(
                            entity, destination, portal.otherEnd.blockPosition(),
                            entity.m_6350_());
                }
            }
        }
        if (portal.ticker >= 500) {
            level.m_7731_(position, Blocks.f_50016_.m_49966_(), 3);
        }
    }
}
